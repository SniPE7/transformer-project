package com.ibm.siam.am.idp.authn.module.tamldap;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import com.ibm.siam.am.idp.authn.module.AbstractSpringLoginModule;
import com.ibm.siam.am.idp.authn.principal.UserPrincipal;

/**
 * 用户登录状态检查LoginModule
 * @author zhangxianwen
 * @since 2012-9-13 上午10:24:27
 */

public class LoginStateCheckerLoginModule extends AbstractSpringLoginModule {

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  public boolean login() throws LoginException {
    if (callbackHandler == null) {
      log.error("No CallbackHandler");
      return false;
    }

    NameCallback nameCallback = new NameCallback("User Name: ");
    Callback[] callbacks = new Callback[]{nameCallback};

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
    //String sessionUsername = lastPrincipalCallback.getPrincipalName();
    String sessionUsername = (String)this.getSessionLevelState(AbstractSpringLoginModule.PRINCIPAL_NAME_KEY);
    if(sessionUsername == null || !sessionUsername.equals(username)){
      log.warn(String.format("No authentication, session username is null or unequal usernmae. username:%s, sessionUsername:%s", username, sessionUsername));
      throw new LoginException(String.format("No authentication, session username is null or unequal usernmae. username:%s, sessionUsername:%s", username, sessionUsername));
    }
    
    //设置共享用户信息
    sharedState.put(LOGIN_NAME, username);
    sharedState.put(LOGIN_DN, this.getSessionLevelState(AbstractSpringLoginModule.PRINCIPAL_DN_KEY));
    sharedState.put(LOGIN_PASSWORD, this.getSessionLevelState(AbstractSpringLoginModule.PRINCIPAL_PASSWORD_KEY));
    
    // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
    UserPrincipal principal = new UserPrincipal();
    principal.setName(username);

    principals.add(principal);

    authenticated = true;
    return true;
  }
  
}
