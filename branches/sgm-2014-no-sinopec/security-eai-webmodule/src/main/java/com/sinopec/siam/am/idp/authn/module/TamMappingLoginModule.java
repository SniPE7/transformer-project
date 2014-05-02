package com.sinopec.siam.am.idp.authn.module;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.apache.commons.lang.StringUtils;

import com.sinopec.siam.am.idp.authn.module.AbstractSpringLoginModule;
import com.sinopec.siam.am.idp.authn.module.DetailLoginException;
import com.sinopec.siam.am.idp.authn.service.UserService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;
import com.sinopec.siam.utils.ExpressionEvaluator;
import com.sinopec.siam.utils.VariableResolver;

import edu.internet2.middleware.shibboleth.idp.authn.UsernamePrincipal;
import edu.vt.middleware.ldap.bean.LdapAttribute;
import edu.vt.middleware.ldap.bean.LdapAttributes;
import edu.vt.middleware.ldap.bean.LdapBeanProvider;
import edu.vt.middleware.ldap.jaas.LdapPrincipal;

/**
 * ��LDAP��¼�û���ӦͳһIDд���Ự��LoginModule
 * 
 * @author zhangxianwen
 * @since 2012-8-28 ����2:33:24
 */

public class TamMappingLoginModule extends AbstractSpringLoginModule {

  /** AD�û�BaseDN��׺ */
  private String adEntryDnSuffix = "OU=��֯����,DC=sinopec,DC=ad";

  /** Tam ldapӳ��ad�˺�filter */
  private String tamAdMappingFilter = "(&(sprolelist={})(objectclass=inetorgperson))";
  
  /**
   * Set returned attributes
   */
  private String[] returnAttributeNames = null;

  /** UserService Bean ID */
  private String userServiceBeanId = "tamLdapUserService";
  
  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    if (options.get("adEntryDnSuffix") != null) {
      this.adEntryDnSuffix = (String) options.get("adEntryDnSuffix");
    }

    if (options.get("tamAdMappingFilter") != null) {
      this.tamAdMappingFilter = (String) options.get("tamAdMappingFilter");
    }
    
    if (options.get("returnAttributeNames") != null) {
      this.returnAttributeNames = StringUtils.split((String) options.get("returnAttributeNames"), ',');
    }
    
    if (options.get("userServiceBeanId") != null) {
      this.userServiceBeanId = (String) options.get("userServiceBeanId");
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  public boolean login() throws LoginException {

    //�ж��û��Ƿ��¼��
    String loginUserid = (String)sharedState.get(LOGIN_NAME);
    String userdn = (String) sharedState.get(LOGIN_DN);

    if (userdn == null) {
      log.info(String.format("sharedState.get(AbstractLoginModule.LOGIN_DN) is null, userdn: %s.", userdn));
      throw new DetailLoginException("login.form.error.username.isNull", String.format("sharedState.get(AbstractLoginModule.LOGIN_DN) is null, userdn: %s.",
          userdn));
    }
    
    Set<LdapPrincipal> subPrincipals = subject.getPrincipals(LdapPrincipal.class);
    LdapPrincipal ldapPrincipal = null;
    LdapAttributes attributes = null;
    if (subPrincipals != null && !subPrincipals.isEmpty()) {
      ldapPrincipal = (LdapPrincipal) subPrincipals.iterator().next();
      attributes = ldapPrincipal.getLdapAttributes();
    }
    
    if(ldapPrincipal==null && this.getSessionLevelState("SUBJECT.PRICIPAL.LDAPPRINCIPAL")!=null) {
    	ldapPrincipal = (LdapPrincipal)this.getSessionLevelState("SUBJECT.PRICIPAL.LDAPPRINCIPAL");
    	attributes = ldapPrincipal.getLdapAttributes();
    	principals.add(ldapPrincipal);
    }
    
    // ����û���TAMLDAP��userid
    String username = loginUserid;
    // Ŀǰʹ���û���DN���ж�֮ǰ�û��������¼�ɹ���AD��TAM LDAP��
    if (attributes == null) {
      attributes = LdapBeanProvider.getLdapBeanFactory().newLdapAttributes();
      //����ͳһID��ѯͳһ�û�
      LdapUserEntity ldapUserEntity = getLdapUserEntityByUid(loginUserid);
      if(returnAttributeNames != null) {
        for(String attrName : returnAttributeNames) {
          attributes.addAttribute(attrName, ldapUserEntity.getValue(attrName.toLowerCase()));
        }
      }
    }
    
    //�û�У�飬ͨ��LADP��ѯ������Աuid��Ϣ���洢Subject
    /* UsernamePrincipal����PrincipalΪAttribute-resolver��ѯ��¼�û�����ʱ��Ӧ */
    UsernamePrincipal principal = new UsernamePrincipal(username);
    principal.setLdapAttributes(attributes);

    // ��ͳһID��TAM LDAP username�����µ�ShareState��.
    sharedState.put(LOGIN_NAME, username);
    //����¼�û���Ϣ�洢��Session��
    this.setSessionLevelState(AbstractSpringLoginModule.PRINCIPAL_NAME_KEY, username);
    this.setSessionLevelState(AbstractSpringLoginModule.PRINCIPAL_DN_KEY, userdn);
    this.setSessionLevelState(AbstractSpringLoginModule.PRINCIPAL_PASSWORD_KEY, sharedState.get(LOGIN_PASSWORD));
    
    if(log.isDebugEnabled()){
      log.debug(String.format("Set authentication user's information to session, username:%s, userdn:%s", username, userdn));
    }
    
    principals.add(principal);
    authenticated = true;
    return true;
  }

  /**
   * ��ȡAD��¼�û���Ӧ��ͳһ�û�
   * 
   * @param request
   * @throws LoginException
   */
  private LdapUserEntity getLdapUserEntity(LdapPrincipal ldapPrincipal) throws LoginException {
    if(log.isDebugEnabled()){
      log.debug(String.format("Get LdapUserEntity , ldapPrincipal:%s", ldapPrincipal));
    }
    ExpressionEvaluator<LdapPrincipal> evaluator = new ExpressionEvaluator<LdapPrincipal>(
        new VariableResolver<LdapPrincipal>() {
          public String resolve(LdapPrincipal ldapPrincipal, String name) {
            LdapAttribute ldapAttribute = ldapPrincipal.getLdapAttributes().getAttribute(name);
            if (ldapAttribute == null) {
              return "";
            } else {
              return ldapAttribute.getValues().iterator().next().toString();
            }
          }
        });
    String filter = evaluator.evaluate(this.tamAdMappingFilter, ldapPrincipal);
    if(log.isDebugEnabled()){
      log.debug(String.format("Get LdapUserEntity , filter:%s", filter));
    }
    UserService userService = getUserService();
    List<LdapUserEntity> ldapUserEntitys = userService.searchByFilter(filter);
    if (ldapUserEntitys == null || ldapUserEntitys.size() == 0) {
      log.info(String.format("Could not find user by filter: [%s].", filter));
      throw new DetailLoginException("login.form.error.ad.notMatchUser", String.format("Could not find user by filter: [%s].", filter));
    } else if (ldapUserEntitys.size() > 1) {
      log.info(String.format("Find more than one user by filter [%s].", filter));
      throw new DetailLoginException("login.form.error.ad.matchMoreUser", String.format("Find more than one user by filter [%s].", filter));
    }
    return ldapUserEntitys.get(0);
  }
  
  /**
   * ����ͳһID��ѯͳһ�û�
   * @param request
   * @param uid
   * @return
   * @throws LoginException
   */
  private LdapUserEntity getLdapUserEntityByUid(String uid) throws LoginException {
    if(log.isDebugEnabled()){
      log.debug(String.format("Get LdapUserEntity by uid, uid:%s", uid));
    }
    UserService userService = getUserService();
    List<LdapUserEntity> ldapUserEntitys = userService.searchByUid(uid);
    if (ldapUserEntitys == null || ldapUserEntitys.size() == 0) {
      log.info(String.format("Could not find user by uid: [%s].", uid));
      throw new DetailLoginException("login.form.error.username.notExists", String.format("Could not find user by uid: [%s].", uid));
    } else if (ldapUserEntitys.size() > 1) {
      log.info(String.format("Find more than one user by uid [%s].", uid));
      throw new DetailLoginException("login.form.error.username.more", String.format("Find more than one user by uid [%s].", uid));
    }
    return ldapUserEntitys.get(0);
  }

  /**
   * ��ȡUserService Bean
   * 
   * @return UserService
   */
  private UserService getUserService() {
    return this.applicationContext.getBean(userServiceBeanId, UserService.class);
  }
  
}
