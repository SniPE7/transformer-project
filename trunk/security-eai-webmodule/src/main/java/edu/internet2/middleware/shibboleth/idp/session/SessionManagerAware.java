package edu.internet2.middleware.shibboleth.idp.session;

import edu.internet2.middleware.shibboleth.common.session.SessionManager;

public interface SessionManagerAware {

  /**
   * @param sessionManager
   */
  public abstract void setSessionManager(SessionManager<Session> sessionManager);

}