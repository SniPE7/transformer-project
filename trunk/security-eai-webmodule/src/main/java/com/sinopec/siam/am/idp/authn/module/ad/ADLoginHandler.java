package com.sinopec.siam.am.idp.authn.module.ad;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.internet2.middleware.shibboleth.idp.authn.provider.AbstractLoginHandler;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * LoginHandler ADÊµÏÖ
 * @author zhangxianwen
 * @since 2012-7-26 ÏÂÎç4:17:34
 */

public class ADLoginHandler extends AbstractLoginHandler {

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(ADLoginHandler.class);

  /** The context-relative path of the servlet used to perform authentication. */
  private String authenticationServletPath;

  /**
   * Constructor.
   * 
   * @param authenticationServletPath
   */
  public ADLoginHandler(String authenticationServletPath) {
    super();
    this.authenticationServletPath = authenticationServletPath;
  }

  /** {@inheritDoc} */
  public void login(ServletContext servletContext, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
    // forward control to the servlet.
    try {
      String authnServletUrl = HttpServletHelper.getContextRelativeUrl(httpRequest, authenticationServletPath)
          .buildURL();
      authnServletUrl = super.setAuthnServletUrl(authnServletUrl, httpRequest);
      log.debug("Redirecting to {}", authnServletUrl);
      httpResponse.sendRedirect(authnServletUrl);
      return;
    } catch (IOException ex) {
      log.error("Unable to redirect to authentication servlet.", ex);
    }
  }

}
