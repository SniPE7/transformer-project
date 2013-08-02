package com.sinopec.siam.am.idp.authn.provider.abs;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * CallbackHandler 用户名密码回调。
 * @author Booker
 *
 */
public class ABSCallbackHandler implements CallbackHandler {

    /** Name of the user. */
    private String uname;

    /** User's password. */
    private String pass;

    /**
     * Constructor.
     * 
     * @param username
     *          The username
     * @param password
     *          The password
     */
    public ABSCallbackHandler(String username, String password) {
      uname = username;
      pass = password;
    }

    /**
     * Handle a callback.
     * 
     * @param callbacks
     *          The list of callbacks to process.
     * 
     * @throws UnsupportedCallbackException
     *           If callbacks has a callback other than {@link NameCallback} or
     *           {@link PasswordCallback}.
     */
    public void handle(final Callback[] callbacks) throws UnsupportedCallbackException {

      if (callbacks == null || callbacks.length == 0) {
        return;
      }

      for (Callback cb : callbacks) {
        if (cb instanceof NameCallback) {
          NameCallback ncb = (NameCallback) cb;
          ncb.setName(uname);
        } else if (cb instanceof PasswordCallback) {
          PasswordCallback pcb = (PasswordCallback) cb;
          pcb.setPassword(pass.toCharArray());
        }
      }
    }
  }