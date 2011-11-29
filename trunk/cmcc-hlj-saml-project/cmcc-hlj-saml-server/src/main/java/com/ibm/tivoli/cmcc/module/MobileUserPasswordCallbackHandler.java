/**
 * 
 */
package com.ibm.tivoli.cmcc.module;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhaodonglu
 *
 */
public class MobileUserPasswordCallbackHandler implements CallbackHandler {
  private static Log log = LogFactory.getLog(MobileUserPasswordCallbackHandler.class);
  private HttpServletRequest httpRequest;

  /**
   * 
   */
  public MobileUserPasswordCallbackHandler() {
    super();
  }

  /**
   * 
   */
  public MobileUserPasswordCallbackHandler(HttpServletRequest request) {
    super();
    this.httpRequest = request;
  }

  /* (non-Javadoc)
   * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
   */
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    for (int i = 0; i < callbacks.length; i++) {
      if (callbacks[i] instanceof MobileUserPasswordCallback) {
        MobileUserPasswordCallback callback = (MobileUserPasswordCallback) callbacks[i];
        callback.setMsisdn(this.httpRequest.getParameter("User-Name"));
        callback.setPassworType(this.httpRequest.getParameter("Password-Type"));
        String password = this.httpRequest.getParameter("User-Password");
        if (password != null) {
           callback.setPassword(password.toCharArray());
        }
      } else {
        throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
      }
    }
  }

}
