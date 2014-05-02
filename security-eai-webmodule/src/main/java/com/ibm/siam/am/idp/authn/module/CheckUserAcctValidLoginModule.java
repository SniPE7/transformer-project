package com.ibm.siam.am.idp.authn.module;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import com.ibm.siam.am.idp.authn.principal.UserPrincipal;
import com.ibm.siam.am.idp.authn.service.UserService;
import com.ibm.siam.am.idp.entity.LdapUserEntity;

/**
 * 检查用户禁用状态LoginModule
 * @author zhangxianwen
 * @since 2012-8-29 下午4:53:07
 */

public class CheckUserAcctValidLoginModule extends AbstractSpringLoginModule {
  
  /** UserService Bean ID */
  private String userServiceBeanId = "tamLdapUserSecAuthrityService";
  
  /** acctValid对应ldap属性名称 */
  private String userAcctValidAttr = "secAcctValid";
  
  /** userName对应ldap属性名称 */
  private String userNameAttr = "principalName";
  
  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    if (options.get("userServiceBeanId") != null) {
      this.userServiceBeanId = (String) options.get("userServiceBeanId");
    }
    
    if (options.get("userAcctValidAttr") != null) {
      this.userAcctValidAttr = (String) options.get("userAcctValidAttr");
    }
    
    if (options.get("userNameAttr") != null) {
      this.userNameAttr = (String) options.get("userNameAttr");
    }

  }

  /** {@inheritDoc} */
  public boolean login() throws LoginException {
    
    Callback[] callbacks = new Callback[0];
    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    } catch (UnsupportedCallbackException e) {
      log.error(e.getMessage(), e);
      return false;
    }

    String username = (String)sharedState.get(LOGIN_NAME);
    if (username == null || "".equals(username)) {
      throw new DetailLoginException("login.form.error.username.isNull", String.format("username is null; usernsme:%s.", username));
    }
    
    checkUserAcctValid(username);
    
    // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
    UserPrincipal principal = new UserPrincipal();
    principal.setName(username);

    principals.add(principal);
    authenticated = true;
    return true;
  }
  
  /**
   * 检查登录用户账号状态，如果账号禁用抛出异常
   * @param username 登录统一ID
   * @param request
   * @throws LoginException
   */
  private void checkUserAcctValid(String username) throws LoginException {
    UserService userService = getUserService();
    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter(userNameAttr, username));
    List<LdapUserEntity> ldapUserEntitys = userService.searchByFilter(andFilter.encode());
    if (ldapUserEntitys.size() == 0) {
      log.info(String.format("Username not exists, filter: %s.", andFilter.encode()));
      throw new DetailLoginException("login.form.error.username.notExists", String.format("Username not exists, filter: %s.", andFilter.encode()));
    } else if (ldapUserEntitys.size() > 1) {
      log.info(String.format("Find more than one user by filter,filter:%s.", andFilter.encode()));
      throw new DetailLoginException("login.form.error.username.more", String.format("Find more than one user by filter,filter:%s.", andFilter.encode()));
    }
    boolean userAcctValid = Boolean.parseBoolean(ldapUserEntitys.get(0).getValueAsString(userAcctValidAttr));
    if(!userAcctValid){
      log.info(String.format("user account state is disabled,filter:%s.", andFilter.encode()));
      throw new DetailLoginException("login.form.error.username.isDisabled", String.format("user account state is disabled,filter:%s.", andFilter.encode()));
    }
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
