package com.ibm.siam.am.idp.authn.module;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import com.ibm.siam.am.idp.authn.principal.UserAccountPrincipal;
import com.ibm.siam.am.idp.authn.principal.UserPrincipal;
import com.ibm.siam.am.idp.authn.provider.PasswordUpdateOperationStatusCallback;
import com.ibm.siam.am.idp.authn.service.UserPassService;
import com.ibm.siam.am.idp.entity.LdapUserEntity;

/**
 * �������ú�ǿ���޸Ŀ���LoginModule������Ldapʵ�֣�
 * 
 * @author zhangxianwen
 * @since 2012-6-28 ����3:19:29
 */

public class UserPassResetLdapLoginModule extends AbstractSpringLoginModule {

  /** �û��������Bean ID */
  private String userPassServiceBeanId = null;

  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    this.userPassServiceBeanId = (String) options.get("userPassServiceBeanId");
    if (this.userPassServiceBeanId == null || "".equals(this.userPassServiceBeanId)) {
      throw new RuntimeException("userPassServiceBeanId is null");
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  public boolean login() throws LoginException {
    if (callbackHandler == null) {
      log.error("No CallbackHandler");
      return false;
    }

    NameCallback nameCallback = new NameCallback("User Name: ");
    PasswordUpdateOperationStatusCallback pwdUpdateCallback = new PasswordUpdateOperationStatusCallback(false);

    Callback[] callbacks = new Callback[] {nameCallback, pwdUpdateCallback};
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

    //HttpServletRequest request = requestCallback.getRequest();

    String shareUsername = (String) sharedState.get(LOGIN_NAME);
    if (shareUsername == null || "".equals(shareUsername)) {
      throw new DetailLoginException("login.form.error.username.isNull", String.format("username is null; usernsme:%s.", shareUsername));
    }

    if (pwdUpdateCallback.isPasswordUpdated()) {
      // �û�У�飬ͨ��LADP��ѯ������Աuid��Ϣ���洢Subject
      UserPrincipal principal = new UserPrincipal();
      principal.setName(shareUsername);

      principals.add(principal);
      authenticated = true;
      return true;
    }

    boolean needChangePassword = false;

    UserAccountPrincipal userAccountPrincipal = (UserAccountPrincipal) sharedState
        .get(AbstractLoginModule.LOGIN_USER_ACCOUNT_PRINCIPAL);
    UserPassService userPassService = getUserPassService();
    try {
      if (userAccountPrincipal == null) {
        LdapUserEntity ldapUserEntity = userPassService.getLdapUserEntityByUsername(shareUsername);
        userAccountPrincipal = new UserAccountPrincipal(shareUsername);
        userAccountPrincipal.setPassRecoveryQuestion(userPassService.getPassRecoveryQuestion(ldapUserEntity));
        userAccountPrincipal.setPassLastChanged(userPassService.getPassLastChanged(ldapUserEntity));
        userAccountPrincipal.setPassResetState(userPassService.getPassResetState(ldapUserEntity));
        sharedState.put(AbstractLoginModule.LOGIN_USER_ACCOUNT_PRINCIPAL, userAccountPrincipal);
      }
      needChangePassword = userAccountPrincipal.isPassResetState();
    } catch (Exception e) {
      log.error(String.format("Get user pass reset state exception. username:%s", shareUsername), e);
      throw new DetailLoginException("login.form.error.userpass.resetStateErr", String.format("Get user pass reset state exception:%s. username:%s", e.getMessage(),
          shareUsername));
    }

    if (needChangePassword) {
      throw new NeedChangePasswordLoginException((String)sharedState.get(LOGIN_NAME), String.format("[%s]'s password had been reset, need to change password immediately.", shareUsername));

    }

    // �û�У�飬ͨ��LADP��ѯ������Աuid��Ϣ���洢Subject
    UserPrincipal principal = new UserPrincipal();
    principal.setName(shareUsername);

    principals.add(principal);
    authenticated = true;
    return true;
  }

  /**
   * ��ȡ�û��������Bean
   * 
   * @return UserPassService
   */
  private UserPassService getUserPassService() {
    return this.applicationContext.getBean(this.userPassServiceBeanId, UserPassService.class);
  }

}
