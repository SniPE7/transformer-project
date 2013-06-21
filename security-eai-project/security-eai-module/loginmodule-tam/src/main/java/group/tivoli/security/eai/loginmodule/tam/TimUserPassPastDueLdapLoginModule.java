package group.tivoli.security.eai.loginmodule.tam;

import group.tivoli.security.eai.loginmodule.tam.service.TimUserLockService;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import com.sinopec.siam.am.idp.authn.module.AbstractLoginModule;
import com.sinopec.siam.am.idp.authn.module.DetailLoginException;
import com.sinopec.siam.am.idp.authn.module.PasswordExpiredLoginException;
import com.sinopec.siam.am.idp.authn.module.UserPassPastDueLdapLoginModule;
import com.sinopec.siam.am.idp.authn.principal.UserAccountPrincipal;
import com.sinopec.siam.am.idp.authn.principal.UserPrincipal;
import com.sinopec.siam.am.idp.authn.provider.PasswordUpdateOperationStatusCallback;
import com.sinopec.siam.am.idp.authn.service.UserPassService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;

/**
 * 用户密码过期，强制修改口令LoginModule（基本Ldap实现）
 * 
 * @author zhangxianwen
 * @since 2012-6-20 上午9:32:00
 */

public class TimUserPassPastDueLdapLoginModule extends UserPassPastDueLdapLoginModule {
  /** 用户口令服务Bean ID */
  private String userPassServiceBeanId = null;

  private String timConfigureBeanId = "";
  
  /** 用户口令有效期 (毫秒)*/
  private long userPassPastDueTime = 2592000000L;

  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);
    
    this.userPassServiceBeanId = (String) options.get("userPassServiceBeanId");
    if (this.userPassServiceBeanId == null || "".equals(this.userPassServiceBeanId)) {
      throw new RuntimeException("userPassServiceBeanId is null");
    }

    if (options.get("userPassPastDueTime") != null) {
      this.userPassPastDueTime = Long.valueOf((String) options.get("userPassPastDueTime"));
    }
    
    this.timConfigureBeanId = (String) options.get("timConfigureBeanId");
    if (this.timConfigureBeanId == null || "".equals(this.timConfigureBeanId)) {
      throw new RuntimeException("timConfigureBeanId is null");
    }
    
    //init duetime , day
    String dueTime = getTimConfigureService().getTimConfigPassExpiration();
    if(dueTime!=null && !"".equals(dueTime.trim())){
      this.userPassPastDueTime = Long.valueOf(dueTime) * 24 * 60 * 60 * 1000;
    }
  }
  
  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  public boolean login() throws LoginException, PasswordExpiredLoginException {
    if (callbackHandler == null) {
      log.error("No CallbackHandler");
      return false;
    }

    NameCallback nameCallback = new NameCallback("User Name: ");
    PasswordUpdateOperationStatusCallback pwdUpdateCallback = new PasswordUpdateOperationStatusCallback(false);

    Callback[] callbacks = new Callback[] { nameCallback, pwdUpdateCallback};
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

    String shareUsername = (String)sharedState.get(LOGIN_NAME);
    if (shareUsername == null || "".equals(shareUsername)) {
      throw new DetailLoginException("login.form.error.username.isNull", String.format("username is null; usernsme:%s.", shareUsername));
    }
    
    //TODO
    if (pwdUpdateCallback.isPasswordUpdated()){
      // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
      UserPrincipal principal = new UserPrincipal();
      principal.setName(shareUsername);

      principals.add(principal);
      authenticated = true;
      return true;
    }
    
    Date userPassLastDate = null;
    
    UserAccountPrincipal userAccountPrincipal = (UserAccountPrincipal) sharedState.get(AbstractLoginModule.LOGIN_USER_ACCOUNT_PRINCIPAL);
    UserPassService userPassService = getUserPassService();
    try {
      if (userAccountPrincipal == null) {
        LdapUserEntity ldapUserEntity  = userPassService.getLdapUserEntityByUsername(shareUsername);
        userAccountPrincipal = new UserAccountPrincipal(shareUsername);
        userAccountPrincipal.setPassRecoveryQuestion(userPassService.getPassRecoveryQuestion(ldapUserEntity));
        userAccountPrincipal.setPassLastChanged(userPassService.getPassLastChanged(ldapUserEntity));
        userAccountPrincipal.setPassResetState(userPassService.getPassResetState(ldapUserEntity));
        sharedState.put(AbstractLoginModule.LOGIN_USER_ACCOUNT_PRINCIPAL, userAccountPrincipal);
      }
      userPassLastDate = userAccountPrincipal.getPassLastChanged();
    } catch (Exception e) {
      log.error(String.format("Get user pass last changed exception. username:%s", shareUsername), e);
      throw new DetailLoginException("login.form.error.userpass.lastChangedErr", String.format("Get user pass last changed exception:%s. username:%s", e.getMessage(),
          shareUsername));
    }
    
    userPassLastDate.setTime(userPassLastDate.getTime() + userPassPastDueTime);
    if (userPassLastDate.compareTo(new Date()) <= 0) {
      throw new PasswordExpiredLoginException((String)sharedState.get(LOGIN_NAME), String.format("[%s]'s password expired, need to change password.", (String)sharedState.get(LOGIN_NAME)));
    }
    userPassLastDate.setTime(userPassLastDate.getTime() - userPassPastDueTime);

    // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
    UserPrincipal principal = new UserPrincipal();
    principal.setName(shareUsername);

    principals.add(principal);
    authenticated = true;
    return true;
  }
  
  /**
   * 获取用户密码服务Bean
   * 
   * @return UserPassService
   */
  private UserPassService getUserPassService() {
    return this.applicationContext.getBean(this.userPassServiceBeanId, UserPassService.class);
  }

  /**
   * 获取TIM Configure服务Bean
   * 
   * @return UserPassService
   */
  private TimUserLockService getTimConfigureService() {
    return this.applicationContext.getBean(this.timConfigureBeanId, TimUserLockService.class);
  }
}
