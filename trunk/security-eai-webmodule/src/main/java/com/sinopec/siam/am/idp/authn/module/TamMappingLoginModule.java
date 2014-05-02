package com.sinopec.siam.am.idp.authn.module;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.apache.commons.lang.StringUtils;

import com.ibm.util.ExpressionEvaluator;
import com.ibm.util.VariableResolver;
import com.sinopec.siam.am.idp.authn.service.UserService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;

import edu.internet2.middleware.shibboleth.idp.authn.UsernamePrincipal;
import edu.vt.middleware.ldap.bean.LdapAttribute;
import edu.vt.middleware.ldap.bean.LdapAttributes;
import edu.vt.middleware.ldap.bean.LdapBeanProvider;
import edu.vt.middleware.ldap.jaas.LdapPrincipal;

/**
 * 将LDAP登录用户对应统一ID写到会话中LoginModule
 * 
 * @author zhangxianwen
 * @since 2012-8-28 下午2:33:24
 */

public class TamMappingLoginModule extends AbstractSpringLoginModule {

  /** AD用户BaseDN后缀 */
  private String adEntryDnSuffix = "OU=组织机构,DC=sinopec,DC=ad";

  /** Tam ldap映射ad账号filter */
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

    //判断用户是否登录过
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
    
    // 存放用户在TAMLDAP的userid
    String username = loginUserid;
    // 目前使用用户的DN来判断之前用户在哪里登录成功（AD或TAM LDAP）
    if (attributes == null) {
      attributes = LdapBeanProvider.getLdapBeanFactory().newLdapAttributes();
      //根据统一ID查询统一用户
      LdapUserEntity ldapUserEntity = getLdapUserEntityByUid(loginUserid);
      if(returnAttributeNames != null) {
        for(String attrName : returnAttributeNames) {
          attributes.addAttribute(attrName, ldapUserEntity.getValue(attrName.toLowerCase()));
        }
      }
    }
    
    //用户校验，通过LADP查询捆绑人员uid信息，存储Subject
    /* UsernamePrincipal类型Principal为Attribute-resolver查询登录用户属性时供应 */
    UsernamePrincipal principal = new UsernamePrincipal(username);
    principal.setLdapAttributes(attributes);

    // 将统一ID（TAM LDAP username）更新到ShareState中.
    sharedState.put(LOGIN_NAME, username);
    //将登录用户信息存储到Session中
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
   * 获取AD登录用户对应的统一用户
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
   * 根据统一ID查询统一用户
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
   * 获取UserService Bean
   * 
   * @return UserService
   */
  private UserService getUserService() {
    return this.applicationContext.getBean(userServiceBeanId, UserService.class);
  }
  
}
