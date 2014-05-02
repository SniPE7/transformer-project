package com.ibm.siam.am.idp.authn.module;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import com.ibm.siam.am.idp.authn.principal.UserPrincipal;
import com.ibm.siam.am.idp.authn.provider.LastAuthenticatedPrincipalCallback;

/**
 * �����Ƿ���������֤
 * <p>
 * ������֤���û���session�е��û����бȽϣ������һ�����¼ʧ��
 * </p>
 * 
 * @author zhoutengfei
 * @since 2012-8-18 ����11:53:08
 */

public class ValidateReAuthenLoginModule extends AbstractSpringLoginModule {

  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);
  }

  /** {@inheritDoc} */
  public boolean login() throws LoginException {
    if (callbackHandler == null) {
      log.error("No CallbackHandler");
      return false;
    }

    NameCallback nameCallback = new NameCallback("User Name: ");
    LastAuthenticatedPrincipalCallback lastAuthedPrincipalCallback = new LastAuthenticatedPrincipalCallback();
    Callback[] callbacks = new Callback[]{nameCallback, lastAuthedPrincipalCallback};
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
    
    if (lastAuthedPrincipalCallback.getPrincipalName() != null && !lastAuthedPrincipalCallback.getPrincipalName().equals(username)) {
      String msg = "Authenticated principal does not match previously authenticated principal";
      log.error(msg);
      throw new DetailLoginException("login.form.error.username.notMatchAuthed", msg);
    }
    // �û�У�飬�洢Subject
    UserPrincipal principal = new UserPrincipal();
    principal.setName(username);
    
    principals.add(principal);
    authenticated = true;
    return true;
  }
  
}
