/**
 * 
 */
package com.ibm.tivoli.pim.callback;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.jaas.callback.PlatformCallbackHandler;
import com.ibm.tivoli.pim.entity.User;

/**
 * @author Administrator
 *
 */
public class HttpSessionBasedSubjectCallbackHandler implements SubjectCallbackHandler {
  
  private static Log log = LogFactory.getLog(HttpSessionBasedSubjectCallbackHandler.class);
  
  public HttpSessionBasedSubjectCallbackHandler() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.pim.service.SubjectCallbackHandler#getSubject(java.lang.String, com.ibm.itim.apps.PlatformContext, javax.xml.ws.WebServiceContext, com.ibm.tivoli.pim.entity.User)
   */
  public Subject getSubject(String LOGIN_CONTEXT, PlatformContext platformContext, WebServiceContext webServiceContext, User user) throws LoginException {
    Subject subject = null;

    MessageContext mct = webServiceContext.getMessageContext();
    HttpServletRequest request = (HttpServletRequest) mct.get("HTTP.REQUEST");
    if (request == null) {
       throw new LoginException("Could not found HttpRequest");
    }
    HttpSession session = request.getSession(false);
    if (session == null) {
       throw new LoginException("Could not found HttpSession");
    }
    String username = (String)session.getAttribute("username");
    String password = (String)session.getAttribute("password");

    // create the ITIM JAAS CallbackHandler
    PlatformCallbackHandler handler = new PlatformCallbackHandler(username, password);
    handler.setPlatformContext(platformContext);

    // Associate the CallbackHandler with a LoginContext,
    // then try to authenticate the user with the platform
    log.info("Logging in...");
    LoginContext lc = new LoginContext(LOGIN_CONTEXT, handler);
    lc.login();
    log.info("Done");

    // Extract the authenticated JAAS Subject from the LoginContext
    log.info("Getting subject... ");
    subject = lc.getSubject();
    return subject;
  }
}
