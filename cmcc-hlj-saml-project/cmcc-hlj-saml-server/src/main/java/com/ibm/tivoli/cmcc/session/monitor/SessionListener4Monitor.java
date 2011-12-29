/**
 * 
 */
package com.ibm.tivoli.cmcc.session.monitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.session.Session;
import com.ibm.tivoli.cmcc.session.SessionEvent;
import com.ibm.tivoli.cmcc.session.SessionListener;
import com.ibm.tivoli.cmcc.session.SessionManagementException;
import com.ibm.tivoli.cmcc.session.SessionManager;

/**
 * @author zhaodonglu
 *
 */
public class SessionListener4Monitor implements SessionListener, SessionMonitor {
  
  private Log log = LogFactory.getLog(SessionListener4Monitor.class);
  
  private SessionManager sessionManager = null;
  
  /**
   * 
   */
  private List<String> trackIDs = new ArrayList<String>();

  /**
   * 
   */
  public SessionListener4Monitor() {
    super();
  }

  /**
   * @return the sessionManager
   */
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  /**
   * @param sessionManager the sessionManager to set
   */
  public void setSessionManager(SessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionListener#afterSessionCreate(com.ibm.tivoli.cmcc.session.SessionEvent)
   */
  public void afterSessionCreate(SessionEvent event) {
    if (event == null || event.getSession().getArtifactID() == null) {
       return;
    }
    trackIDs.add(event.getSession().getArtifactID());
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionListener#beforeSessionDestroyed(com.ibm.tivoli.cmcc.session.SessionEvent, boolean)
   */
  public void beforeSessionDestroyed(SessionEvent event, boolean broadcastToOtherIDPs) {
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionListener#afterSessionDestroyed(com.ibm.tivoli.cmcc.session.SessionEvent, boolean)
   */
  public void afterSessionDestroyed(SessionEvent event, boolean broadcastToOtherIDPs) {
    if (event == null || event.getSession().getArtifactID() == null) {
      return;
    }
    this.trackIDs.remove(event.getSession().getArtifactID());
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionListener#beforeSessionTouch(com.ibm.tivoli.cmcc.session.SessionEvent)
   */
  public void beforeSessionTouch(SessionEvent event) {
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionListener#afterSessionTouch(com.ibm.tivoli.cmcc.session.SessionEvent)
   */
  public void afterSessionTouch(SessionEvent event) {
  }
  
  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionMonitor#getAllSessions()
   */
  public List<Session> getAllSessions() {
    List<Session> result = new ArrayList<Session>();
    try {
      for (String sessionID: this.trackIDs) {
          result.add(this.sessionManager.get(sessionID));
      }
    } catch (SessionManagementException e) {
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionMonitor#getSession(java.lang.String)
   */
  public Session getSession(String sessionID) {
    try {
      return this.sessionManager.get(sessionID);
    } catch (SessionManagementException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

}
