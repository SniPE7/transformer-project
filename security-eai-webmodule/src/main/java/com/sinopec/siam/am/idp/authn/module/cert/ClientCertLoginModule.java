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
 * @description 用户客户端证书登录身份验证
 * @author zhangxianwen
 * @version 1.0
 * @Copyright (c) 2012
 * @create 2012-3-12 下午5:05:04
 */

public class ClientCertLoginModule extends AbstractSpringLoginModule {

  /** Ldap查询用户过滤条件（配置） */
  private String userFilter;
  /** 证书使用者中代表AD的属性名 */
  private String adAttrName = "EMAILADDRESS";
  /**
   * Set returned attributes
   */
  private String[] returnAttributeNames = null;
  /** 检查证书有效期 */
  private boolean checkCertValidity = true;
  /** 检查证书是否根证书签发 */
  private boolean checkCertRootCert = true;
  /** 检查证书的CRL */
  private boolean checkCertCrl = true;
  /** 用户服务Bean ID */
  private String userServiceBeanId = null;
  /** 证书校验服务Bean ID */
  private String verifyCertServiceBeanId = null;
  
  /** storageService Bean ID*/
  private String storageServiceBeanId = "shibboleth.StorageService";

  /**
   * @description 初始化此 LoginModule
   * @param subject
   *          要进行验证的 Subject
   * @param callbackHandler
   *          用来与最终用户通信的 CallbackHandler（例如，提示要求用户名和密码）
   * @param sharedState
   *          与其他已配置的 LoginModule 共享的状态
   * @param options
   *          在登录 Configuration 中为此特定的 LoginModule 指定的选项
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
   * @description 对 Subject 进行验证的方法
   * @return boolean 如果验证成功，则返回 true；如果应该忽略此 LoginModule，则返回 false；如果验证失败则抛出异常信息
   * @throws LoginException
   *           如果验证失败
   */
  @SuppressWarnings("unchecked")
  public boolean login() throws LoginException {
    if (callbackHandler == null) {
      log.error("No CallbackHandler for this ClientCertLoginModule");
      return false;
    }

    RequestCallback requestCallback = new RequestCallback();
    // 客户端提交证书
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

    // 提取证书主题
    Principal clientCertSubject = clientCert.getSubjectDN();
    LdapUserEntity ldapUserEntity = getLdapUserEntityByCertSubject(clientCertSubject, request);
    
    LdapAttributes attributes = LdapBeanProvider.getLdapBeanFactory().newLdapAttributes();
    if(returnAttributeNames != null) {
      for(String attrName : returnAttributeNames) {
        attributes.addAttribute(attrName, ldapUserEntity.getValue(attrName.toLowerCase()));
      }
    }
    // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
    /* UsernamePrincipal类型Principal为Attribute-resolver查询登录用户属性时供应 */
    UsernamePrincipal principal = new UsernamePrincipal(ldapUserEntity.getUid());
    principal.setLdapAttributes(attributes);
    
    validateForcedReauthentication(request, principal);
    
    sharedState.put(AbstractLoginModule.LOGIN_NAME, ldapUserEntity.getUid());
    principals.add(principal);
    authenticated = true;
    return true;
  }

  /**
   * @description 获取证书使用者信息
   * @return Map<String, String> 使用者信息集
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
   * 根据证书的主题和预先设置的LDAP Filter配置模板，运算此证书对应用户的LDAP Filter查询条件.
   * 
   * @param certSubInfo
   *          证书中用户信息集
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
   * @description 获取用户信息
   * @param subject
   *          证书主题
   * @param request
   * @return LdapUserEntity 用户信息
   * @throws LoginException
   */
  private LdapUserEntity getLdapUserEntityByCertSubject(Principal subject, HttpServletRequest request) throws LoginException {
    /* 设置Ldap查询条件 */
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
   * 获取LoginContext
   * @param request
   * @return
   */
  @SuppressWarnings("unchecked")
  private LoginContext getLoginContext(HttpServletRequest request){
    StorageService<String, LoginContextEntry> storageService = this.applicationContext.getBean(storageServiceBeanId, StorageService.class);
    return HttpServletHelper.getLoginContext(storageService, request.getSession().getServletContext(), request);
  }

  /**
   * @description 获取用户信息服务对象
   * @return userService
   */
  private UserService getUserService() {
    return this.applicationContext.getBean(this.userServiceBeanId, UserService.class);
  }

  /**
   * @description 获取证书校验服务对象
   * @return verifyCertService
   */
  private VerifyCertService getVerifyCertService() {
    return this.applicationContext.getBean(this.verifyCertServiceBeanId, VerifyCertService.class);
  }

}
