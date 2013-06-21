package group.tivoli.security.eai.loginmodule.tam;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import com.sinopec.siam.am.idp.authn.module.AbstractSpringLoginModule;
import com.sinopec.siam.am.idp.authn.module.DetailLoginException;
import com.sinopec.siam.am.idp.authn.principal.UserPrincipal;
import com.sinopec.siam.am.idp.authn.service.UserService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;

/**
 * <code>LdapLoginModule</code> 检测账户是否在TAM cn=Users,SECAUTHORITY=DEFAULT 节点中存在.<br/>
 * 
 * <pre>
 * 配置参数:
 * javax.security.auth.login.name     - 用户登录的uid
 * </pre>
 * <pre>
 * 返回参数:
 *    secDN   - 如存在则存储secDN到shareState对象中
 * </pre>
 */
public class TamUserCheckExistLoginModule extends AbstractSpringLoginModule implements LoginModule {
  
  /** UserService Bean ID */
  private String userServiceBeanId = "tamLdapUserSecAuthrityService";
  
  /** secDN对应ldap属性名称 */
  private String userSecDNAttr = "secDN";
  
  /** userName对应ldap属性名称 */
  private String userNameAttr = "principalName";
  
  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    if (options.get("userServiceBeanId") != null) {
      this.userServiceBeanId = (String) options.get("userServiceBeanId");
    }
    
    if (options.get("userNameAttr") != null) {
      this.userNameAttr = (String) options.get("userNameAttr");
    }

  }

  /** {@inheritDoc} */
  public boolean login() throws LoginException {
    
    if (callbackHandler == null) {
      log.error("No CallbackHandler");
      return false;
    }

    NameCallback nameCallback = new NameCallback("User Name: ");

    Callback[] callbacks = new Callback[] {nameCallback};
    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    } catch (UnsupportedCallbackException e) {
      log.error(e.getMessage(), e);
      return false;
    }

    String username = nameCallback.getName();
    

   // String username = (String)sharedState.get(LOGIN_NAME);
    if (username == null || "".equals(username)) {
      throw new DetailLoginException("login.form.error.username.isNull", String.format("username is null; usernsme:%s.", username));
    }
    
    //检测是否在tam中存在，如果存在返回secdn值
    String userSecDN = checkUserIsExist(username);

    //存储到共享空间，供后面loginmodule使用
    this.storeUserSecDN(userSecDN);
    
    // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
    UserPrincipal principal = new UserPrincipal();
    principal.setName(username);

    principals.add(principal);
    authenticated = true;
    return true;
  }
  
  /**
   * 检查登录用户账号是否在TAM中存在，如果账号不存在抛出异常
   * @param username 登录统一ID
   * @param request
   * @throws LoginException
   */
  private String checkUserIsExist(String username) throws LoginException {
    UserService userService = getUserService();
    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter(userNameAttr, username));
    List<LdapUserEntity> ldapUserEntitys = userService.searchByFilter(andFilter.encode());
    if (ldapUserEntitys.size() == 0) {
      log.info(String.format("Username not exists, filter: %s.", andFilter.encode()));
      throw new DetailLoginException("login.form.error.username.notExists", String.format("Username not exists, filter: %s.", andFilter.encode()));
    } 
    
    return ldapUserEntitys.get(0).getValueAsString(userSecDNAttr);
  }
  
  /**
   * 获取UserService Bean
   * 
   * @return UserService
   */
  private UserService getUserService() {
    return this.applicationContext.getBean(userServiceBeanId, UserService.class);
  }
  
  /**
   * This will store  entry secdn in the stored
   * state map. storePass must be set for this method to have any affect.
   *
   * @param secDN
   *          to store
   */
  @SuppressWarnings("unchecked")
  protected void storeUserSecDN(final String secDN) {
      if (secDN != null) {
        this.sharedState.put(userSecDNAttr, secDN);
      }
  }

}
