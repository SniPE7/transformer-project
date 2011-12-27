/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.util.EventObject;

/**
 * This is the class representing event notifications for changes to sessions
 * within a SAML Server.
 * 
 * @author zhaodonglu
 * 
 */
public class SessionEvent extends EventObject {

  /**
   * 
   */
  private static final long serialVersionUID = 7312268106465245680L;
  private SessionManager sessionManager = null;

  /**
   * 
   */
  public SessionEvent(SessionManager sessionManager, Session source) {
    super(source);
    this.sessionManager = sessionManager;
  }

  /**
   * Return the session that changed.
   */
  public Session getSession() {
    return (Session) super.getSource();
  }

  /**
   * @return the sessionManager
   */
  public SessionManager getSessionManager() {
    return sessionManager;
  }
}
