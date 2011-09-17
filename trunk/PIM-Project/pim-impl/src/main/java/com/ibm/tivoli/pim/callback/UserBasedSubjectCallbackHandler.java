/**
 * 
 */
package com.ibm.tivoli.pim.callback;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.jaas.callback.PlatformCallbackHandler;
import com.ibm.tivoli.pim.entity.User;

/**
 * @author Administrator
 *
 */
public class UserBasedSubjectCallbackHandler implements SubjectCallbackHandler {
  
  private static Log log = LogFactory.getLog(UserBasedSubjectCallbackHandler.class);
  
  public UserBasedSubjectCallbackHandler() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.pim.service.SubjectCallbackHandler#getSubject(java.lang.String, com.ibm.itim.apps.PlatformContext, javax.xml.ws.WebServiceContext, com.ibm.tivoli.pim.entity.User)
   */
  public Subject getSubject(String LOGIN_CONTEXT, PlatformContext platformContext, HttpServletRequest request, User user) throws LoginException {
    if (user == null || user.getPassword() == null || user.getUsername() == null) {
      return null;
    }

    // create the ITIM JAAS CallbackHandler
    PlatformCallbackHandler handler = new PlatformCallbackHandler(user.getUsername(), user.getPassword());
    handler.setPlatformContext(platformContext);

    // Associate the CallbackHandler with a LoginContext,
    // then try to authenticate the user with the platform
    log.info("Logging in...");
    LoginContext lc = new LoginContext(LOGIN_CONTEXT, handler);
    lc.login();
    log.info("Done");

    // Extract the authenticated JAAS Subject from the LoginContext
    log.info("Getting subject... ");
    Subject subject = lc.getSubject();
    return subject;
  }
}
