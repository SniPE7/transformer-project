/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaodonglu
 * 
 */
public class ChainSessionListener implements SessionListener {

  private List<SessionListener> listeners = new ArrayList<SessionListener>();

  /**
   * 
   */
  public ChainSessionListener() {
    super();
  }

  /**
   * @return the listeners
   */
  public List<SessionListener> getListeners() {
    return listeners;
  }

  /**
   * @param listeners
   *          the listeners to set
   */
  public void setListeners(List<SessionListener> listeners) {
    this.listeners = listeners;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.session.SessionListener#sessionCreated(com.ibm.tivoli
   * .cmcc.session.SessionEvent)
   */
  public void afterSessionCreate(SessionEvent event) {
    for (SessionListener listener : this.listeners) {
      listener.afterSessionCreate(event);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.session.SessionListener#beforeSessionDestroyed(com.
   * ibm.tivoli.cmcc.session.SessionEvent)
   */
  public void beforeSessionDestroyed(SessionEvent event, boolean broadcastToOtherIDPs) {
    for (SessionListener listener : this.listeners) {
      listener.beforeSessionDestroyed(event, broadcastToOtherIDPs);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.session.SessionListener#afterSessionDestroyed(com.ibm
   * .tivoli.cmcc.session.SessionEvent)
   */
  public void afterSessionDestroyed(SessionEvent event, boolean broadcastToOtherIDPs) {
    for (SessionListener listener : this.listeners) {
      listener.afterSessionDestroyed(event, broadcastToOtherIDPs);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.session.SessionListener#sessionTouched(com.ibm.tivoli
   * .cmcc.session.SessionEvent)
   */
  public void afterSessionTouch(SessionEvent event) {
    for (SessionListener listener : this.listeners) {
      listener.afterSessionTouch(event);
    }
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionListener#beforeSessionTouch(com.ibm.tivoli.cmcc.session.SessionEvent)
   */
  public void beforeSessionTouch(SessionEvent event) {
    for (SessionListener listener : this.listeners) {
      listener.beforeSessionTouch(event);
    }
  }

}
