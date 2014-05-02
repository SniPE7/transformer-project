package com.ibm.siam.am.idp.authn.module.tamldap;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.siam.am.idp.authn.AccessEnforcer;

import edu.internet2.middleware.shibboleth.idp.authn.provider.AbstractLoginHandler;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * LoginHandler TAMÊµÏÖ
 * 
 */

public class TAMLoginHandler extends AbstractLoginHandler {

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(TAMLoginHandler.class);

  public TAMLoginHandler() {
	  super();
  }

	/**
   * Constructor.
   * 
   * @param authenticationServletPath
   */
  public TAMLoginHandler(String authenticationServletPath) {
    super(authenticationServletPath);
  }
	/** {@inheritDoc} */
  public void login(ServletContext servletContext, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
    // forward control to the servlet.
    try {
      String authnServletUrl = HttpServletHelper.getContextRelativeUrl(httpRequest, this.getAuthenticationServletPath()).buildURL();
      authnServletUrl = super.setAuthnServletUrl(authnServletUrl, httpRequest);
      String eaiReturnUrl = (String)httpRequest.getAttribute(AccessEnforcer.SESSION_ATTR_NAME_EAI_RETURN_URL);
      if (eaiReturnUrl != null && eaiReturnUrl.trim().length() > 0) {
         if (authnServletUrl.indexOf('?') > 0) {
            authnServletUrl = authnServletUrl + "&eaiReturnUrlInPage=" + URLEncoder.encode(eaiReturnUrl, "UTF-8");
         } else {
           authnServletUrl = authnServletUrl + "?eaiReturnUrlInPage=" + URLEncoder.encode(eaiReturnUrl, "UTF-8");
         }
      }
      log.debug("Redirecting to {}", authnServletUrl);
      httpResponse.sendRedirect(authnServletUrl);
      return;
    } catch (IOException ex) {
      log.error("Unable to redirect to authentication servlet.", ex);
    }
  }

}
