/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhaodonglu
 * 
 */
public class MapBasedSessionCache<HttpSession> implements SessionCache<HttpSession> {

  private Map<String, HttpSession> sessionMap = new ConcurrentHashMap<String, HttpSession>();

  /**
   * 
   */
  public MapBasedSessionCache() {
    super();
  }

  public void put(String artifactID, HttpSession value) {
    this.sessionMap.put(artifactID, value);
  }

  public HttpSession get(String artifactID) {
    return this.sessionMap.get(artifactID);
  }

  public void remove(String artifactID) {
    this.sessionMap.remove(artifactID);
  }

}
