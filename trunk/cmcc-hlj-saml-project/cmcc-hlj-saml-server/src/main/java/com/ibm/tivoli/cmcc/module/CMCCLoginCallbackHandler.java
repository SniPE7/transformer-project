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

import com.ibm.tivoli.cmcc.server.utils.CookieHelper;

/**
 * @author zhaodonglu
 *
 */
public class CMCCLoginCallbackHandler implements CallbackHandler {
  private static Log log = LogFactory.getLog(CMCCLoginCallbackHandler.class);
  private HttpServletRequest httpRequest;

  /**
   * 
   */
  public CMCCLoginCallbackHandler() {
    super();
  }

  /**
   * 
   */
  public CMCCLoginCallbackHandler(HttpServletRequest request) {
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
        callback.setRemoteIPAddress(this.httpRequest.getRemoteAddr());
      } else if (callbacks[i] instanceof CMCCArtifactIDCallback) {
          CMCCArtifactIDCallback callback = (CMCCArtifactIDCallback) callbacks[i];
          String artifactID = CookieHelper.getArtifactIDFromCookies(this.httpRequest);
          String artifactDomain = CookieHelper.getArtifactDomainFromCookies(this.httpRequest);
          callback.setArtifactID(artifactID);
          callback.setArtifactDomain(artifactDomain);
          callback.setRemoteIPAddress(this.httpRequest.getRemoteAddr());
      } else {
        throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
      }
    }
  }

}
