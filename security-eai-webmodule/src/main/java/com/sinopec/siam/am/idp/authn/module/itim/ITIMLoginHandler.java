package com.sinopec.siam.am.idp.authn.module.itim;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.internet2.middleware.shibboleth.idp.authn.provider.AbstractLoginHandler;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * LoginHandler ITIM µœ÷°£
 * @author Zhao Dong Lu
 *
 */
public class ITIMLoginHandler extends AbstractLoginHandler {

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(ITIMLoginHandler.class);

  /** The context-relative path of the servlet used to perform authentication. */
  private String authenticationServletPath;

  /**
   * Constructor.
   * 
   * @param servletPath
   *          context-relative path to the authentication servlet, may start
   *          with "/"
   */
  public ITIMLoginHandler(String servletPath) {
    super();
    authenticationServletPath = servletPath;
  }

  /** {@inheritDoc} */
  public void login(ServletContext servletContext, final HttpServletRequest httpRequest, final HttpServletResponse httpResponse) {
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
