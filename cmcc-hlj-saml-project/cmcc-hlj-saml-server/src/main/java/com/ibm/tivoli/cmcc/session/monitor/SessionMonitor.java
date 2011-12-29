/**
 * 
 */
package com.ibm.tivoli.cmcc.session.monitor;

import java.util.List;

import com.ibm.tivoli.cmcc.session.Session;

/**
 * @author zhaodonglu
 *
 */
public interface SessionMonitor {

  /**
   * @return
   */
  public abstract List<Session> getAllSessions();

  /**
   * @param sessionID
   * @return
   */
  public abstract Session getSession(String sessionID);

}
