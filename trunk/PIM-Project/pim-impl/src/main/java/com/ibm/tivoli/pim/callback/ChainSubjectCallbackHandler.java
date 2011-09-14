/**
 * 
 */
package com.ibm.tivoli.pim.callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.itim.apps.PlatformContext;
import com.ibm.tivoli.pim.entity.User;

/**
 * @author Administrator
 * 
 */
public class ChainSubjectCallbackHandler implements SubjectCallbackHandler {

  private Log log = LogFactory.getLog(ChainSubjectCallbackHandler.class);
  
  private List<SubjectCallbackHandler> callbackHandlers = new ArrayList<SubjectCallbackHandler>();

  /**
   * 
   */
  public ChainSubjectCallbackHandler() {
    super();
  }

  /**
   * 
   */
  public ChainSubjectCallbackHandler(SubjectCallbackHandler handler) {
    super();
    this.callbackHandlers.add(handler);
  }

  /**
   * 
   */
  public ChainSubjectCallbackHandler(SubjectCallbackHandler... handlers) {
    super();
    this.callbackHandlers.addAll(Arrays.asList(handlers));
  }

  public List<SubjectCallbackHandler> getCallbackHandlers() {
    return callbackHandlers;
  }

  public void setCallbackHandlers(List<SubjectCallbackHandler> callbackHandlers) {
    this.callbackHandlers = callbackHandlers;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.pim.callback.SubjectCallbackHandler#getSubject(java.lang
   * .String, com.ibm.itim.apps.PlatformContext, javax.xml.ws.WebServiceContext,
   * com.ibm.tivoli.pim.entity.User)
   */
  public Subject getSubject(String LOGIN_CONTEXT, PlatformContext platformContext, WebServiceContext webServiceContext, User user) throws LoginException {
    for (SubjectCallbackHandler callback : this.callbackHandlers) {
      try {
        log.debug(String.format("Get Subject from: [%s]", callback.getClass().getName()));
        Subject subject = callback.getSubject(LOGIN_CONTEXT, platformContext, webServiceContext, user);
        if (subject != null) {
          return subject;
        }
      } catch (Exception e) {
        log.info(String.format("Failure to get Subject from: [%s], cause: %s", callback.getClass().getName(), e.getMessage()), e);
      }
    }
    throw new LoginException("Failure to get subject, all callback handlers return null.");
  }

}
