package com.sinopec.siam.am.idp.authn.module.cert;

import java.io.IOException;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.opensaml.util.storage.StorageService;
import org.opensaml.xml.util.DatatypeHelper;

import com.sinopec.siam.am.idp.authn.module.AbstractLoginModule;
import com.sinopec.siam.am.idp.authn.module.AbstractSpringLoginModule;
import com.sinopec.siam.am.idp.authn.provider.RequestCallback;
import com.sinopec.siam.am.idp.authn.provider.cert.ClientCertCallback;
import com.sinopec.siam.am.idp.authn.service.UserService;
import com.sinopec.siam.am.idp.authn.service.cert.VerifyCertService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;
import com.sinopec.siam.utils.ExpressionEvaluator;
import com.sinopec.siam.utils.VariableResolver;

import edu.internet2.middleware.shibboleth.idp.authn.LoginContext;
import edu.internet2.middleware.shibboleth.idp.authn.LoginContextEntry;
import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;
import edu.internet2.middleware.shibboleth.idp.authn.UsernamePrincipal;
import edu.internet2.middleware.shibboleth.idp.session.AuthenticationMethodInformation;
import edu.internet2.middleware.shibboleth.idp.session.Session;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;
import edu.vt.middleware.ldap.bean.LdapAttributes;
import edu.vt.middleware.ldap.bean.LdapBeanProvider;

/**
 * @description �û��ͻ���֤���¼�����֤
 * @author zhangxianwen
 * @version 1.0
 * @Copyright (c) 2012
 * @create 2012-3-12 ����5:05:04
 */

public class ClientCertLoginModule extends AbstractSpringLoginModule {

  /** Ldap��ѯ�û��������������ã� */
  private String userFilter;
  /** ֤��ʹ�����д���AD�������� */
  private String adAttrName = "EMAILADDRESS";
  /**
   * Set returned attributes
   */
  private String[] returnAttributeNames = null;
  /** ���֤����Ч�� */
  private boolean checkCertValidity = true;
  /** ���֤���Ƿ��֤��ǩ�� */
  private boolean checkCertRootCert = true;
  /** ���֤���CRL */
  private boolean checkCertCrl = true;
  /** �û�����Bean ID */
  private String userServiceBeanId = null;
  /** ֤��У�����Bean ID */
  private String verifyCertServiceBeanId = null;
  
  /** storageService Bean ID*/
  private String storageServiceBeanId = "shibboleth.StorageService";

  /**
   * @description ��ʼ���� LoginModule
   * @param subject
   *          Ҫ������֤�� Subject
   * @param callbackHandler
   *          �����������û�ͨ�ŵ� CallbackHandler�����磬��ʾҪ���û��������룩
   * @param sharedState
   *          �����������õ� LoginModule �����״̬
   * @param options
   *          �ڵ�¼ Configuration ��Ϊ���ض��� LoginModule ָ����ѡ��
   */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    this.userFilter = (String) options.get("userFilter");
    if (this.userFilter == null || "".equals(this.userFilter)) {
      throw new RuntimeException("userFilter is null");
    }
    
    if (options.get("adAttrName") != null) {
      this.adAttrName = (String) options.get("adAttrName");
    }
    
    if (options.get("returnAttributeNames") != null) {
      this.returnAttributeNames = StringUtils.split((String) options.get("returnAttributeNames"), ',');
    }
    
    if (options.get("checkCertValidity") != null) {
      this.checkCertValidity = Boolean.valueOf((String) options.get("checkCertValidity"));
    }

    if (options.get("checkCertRootCert") != null) {
      this.checkCertRootCert = Boolean.valueOf((String) options.get("checkCertRootCert"));
    }

    if (options.get("checkCertCrl") != null) {
      this.checkCertCrl = Boolean.valueOf((String) options.get("checkCertCrl"));
    }

    this.userServiceBeanId = (String) options.get("userServiceBeanId");
    if (this.userServiceBeanId == null || "".equals(this.userServiceBeanId)) {
      throw new RuntimeException("userServiceBeanId is null");
    }

    this.verifyCertServiceBeanId = (String) options.get("verifyCertServiceBeanId");
    if (this.verifyCertServiceBeanId == null || "".equals(this.verifyCertServiceBeanId)) {
      throw new RuntimeException("verifyCertServiceBeanId is null");
    }
    
    if (options.get("storageServiceBeanId") != null) {
      this.storageServiceBeanId = (String) options.get("storageServiceBeanId");
    }
  }

  /**
   * @description �� Subject ������֤�ķ���
   * @return boolean �����֤�ɹ����򷵻� true�����Ӧ�ú��Դ� LoginModule���򷵻� false�������֤ʧ�����׳��쳣��Ϣ
   * @throws LoginException
   *           �����֤ʧ��
   */
  @SuppressWarnings("unchecked")
  public boolean login() throws LoginException {
    if (callbackHandler == null) {
      log.error("No CallbackHandler for this ClientCertLoginModule");
      return false;
    }

    RequestCallback requestCallback = new RequestCallback();
    // �ͻ����ύ֤��
    ClientCertCallback callback = new ClientCertCallback();
    Callback[] callbacks = new Callback[2];
    callbacks[0] = callback;
    callbacks[1] = requestCallback;
    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    } catch (UnsupportedCallbackException e) {
      log.error(e.getMessage(), e);
      return false;
    }

    X509Certificate clientCert = callback.getClientCert();
    HttpServletRequest request = requestCallback.getRequest();

    // 1. Verify cert
    VerifyCertService verifyCertService = getVerifyCertService();
    if (!verifyCertService.VerifyCert(clientCert, checkCertValidity, checkCertRootCert, checkCertCrl)) {
      log.error("ClientCert verify failure");
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "login.form.error.cert.verifyFailure");
      throw new LoginException("ClientCert verify failure");
    }

    // ��ȡ֤������
    Principal clientCertSubject = clientCert.getSubjectDN();
    LdapUserEntity ldapUserEntity = getLdapUserEntityByCertSubject(clientCertSubject, request);
    
    LdapAttributes attributes = LdapBeanProvider.getLdapBeanFactory().newLdapAttributes();
    if(returnAttributeNames != null) {
      for(String attrName : returnAttributeNames) {
        attributes.addAttribute(attrName, ldapUserEntity.getValue(attrName.toLowerCase()));
      }
    }
    // �û�У�飬ͨ��LADP��ѯ������Աuid��Ϣ���洢Subject
    /* UsernamePrincipal����PrincipalΪAttribute-resolver��ѯ��¼�û�����ʱ��Ӧ */
    UsernamePrincipal principal = new UsernamePrincipal(ldapUserEntity.getUid());
    principal.setLdapAttributes(attributes);
    
    validateForcedReauthentication(request, principal);
    
    sharedState.put(AbstractLoginModule.LOGIN_NAME, ldapUserEntity.getUid());
    principals.add(principal);
    authenticated = true;
    return true;
  }

  /**
   * @description ��ȡ֤��ʹ������Ϣ
   * @return Map<String, String> ʹ������Ϣ��
   */
  private Map<String, String> convertSubjectToMap(Principal principal) {
    Map<String, String> certSubInfo = new HashMap<String, String>();
    String[] subInfo = principal.toString().split(",");
    for (String sub : subInfo) {
      String[] subs = sub.split("=");
      certSubInfo.put(subs[0].trim(), subs[1].trim());
    }
    return certSubInfo;
  }

  /**
   * ����֤��������Ԥ�����õ�LDAP Filter����ģ�壬�����֤���Ӧ�û���LDAP Filter��ѯ����.
   * 
   * @param certSubInfo
   *          ֤�����û���Ϣ��
   * @throws LoginException
   */
  private String evaludateLdapFilter(Principal subject) throws LoginException {

    Map<String, String> namesMap = convertSubjectToMap(subject);

    ExpressionEvaluator<Map<String, String>> evaluator = new ExpressionEvaluator<Map<String, String>>(
        new VariableResolver<Map<String, String>>() {

          public String resolve(Map<String, String> contextMap, String name) {
            String str = contextMap.get(name);
            if (str == null) {
              str = "";
            }
            if(adAttrName.equals(name) && (str.indexOf("@") > 0)){
              str = str.substring(0, str.indexOf("@"));
            }
            return str;
          }
        });

    return evaluator.evaluate(this.userFilter, namesMap);
  }

  /**
   * @description ��ȡ�û���Ϣ
   * @param subject
   *          ֤������
   * @param request
   * @return LdapUserEntity �û���Ϣ
   * @throws LoginException
   */
  private LdapUserEntity getLdapUserEntityByCertSubject(Principal subject, HttpServletRequest request) throws LoginException {
    /* ����Ldap��ѯ���� */
    String filter = evaludateLdapFilter(subject);

    UserService userService = getUserService();
    List<LdapUserEntity> ldapUserEntitys = userService.searchByFilter(filter);
    
    if (ldapUserEntitys == null || ldapUserEntitys.size() == 0) {
      log.error(String.format("Could not find any user by filter [%s], cert subject: [%s].", filter, subject));
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "login.form.error.cert.noUser");
      throw new LoginException(String.format("Could not find any user by filter [%s], cert subject: [%s].", filter,
          subject));
    } else if (ldapUserEntitys.size() > 1) {
      log.error(String.format("Find more than one user by filter [%s], cert subject: [%s].", filter, subject));
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "login.form.error.cert.moreUser");
      throw new LoginException(String.format("Find more than one user by filter [%s], cert subject: [%s].", filter,
          subject));
    }

    return ldapUserEntitys.get(0);
  }
  
  /**
   * If forced authentication was required this method checks to ensure that the
   * re-authenticated subject contains a principal name that is equal to the
   * principal name associated with the authentication method. If this is the
   * first time the subject has authenticated with this method than this check
   * always passes.
   * 
   * @param request
   *          HttpServletRequest
   * @param UsernamePrincipal
   *          principal that was authenticated
   * @throws LoginException 
   * 
   */
  private void validateForcedReauthentication(HttpServletRequest request, UsernamePrincipal principal) throws LoginException {
    LoginContext loginContext = getLoginContext(request);
    if (loginContext.isForceAuthRequired()) {
      Session idpSession = (Session) request.getAttribute(Session.HTTP_SESSION_BINDING_ATTRIBUTE);
      String authnMethod = DatatypeHelper.safeTrimOrNullString((String)request.getAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY));
      if (authnMethod != null) {
        if (!loginContext.getRequestedAuthenticationMethods().isEmpty()
            && !loginContext.getRequestedAuthenticationMethods().contains(authnMethod)) {
          String msg = "Relying patry required an authentication method of " + loginContext.getRequestedAuthenticationMethods() + " but the login handler performed " + authnMethod;
          log.error(msg);
          request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "login.form.error.authnMethod.illegality");
          throw new LoginException(msg);
        }
      } else {
        authnMethod = loginContext.getAttemptedAuthnMethod();
      }
      if (idpSession != null) {
        AuthenticationMethodInformation authnMethodInfo = idpSession.getAuthenticationMethodByName(authnMethod);
        if (authnMethodInfo != null && authnMethodInfo.getAuthenticationPrincipal().equals(principal)) {
          return;
        }
        String msg = "Authenticated principal does not match previously authenticated principal";
        log.error(msg);
        request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "login.form.error.username.notMatchAuthed");
        throw new LoginException(msg);
      }
    }
  }
  
  /**
   * ��ȡLoginContext
   * @param request
   * @return
   */
  @SuppressWarnings("unchecked")
  private LoginContext getLoginContext(HttpServletRequest request){
    StorageService<String, LoginContextEntry> storageService = this.applicationContext.getBean(storageServiceBeanId, StorageService.class);
    return HttpServletHelper.getLoginContext(storageService, request.getSession().getServletContext(), request);
  }

  /**
   * @description ��ȡ�û���Ϣ�������
   * @return userService
   */
  private UserService getUserService() {
    return this.applicationContext.getBean(this.userServiceBeanId, UserService.class);
  }

  /**
   * @description ��ȡ֤��У��������
   * @return verifyCertService
   */
  private VerifyCertService getVerifyCertService() {
    return this.applicationContext.getBean(this.verifyCertServiceBeanId, VerifyCertService.class);
  }

}
