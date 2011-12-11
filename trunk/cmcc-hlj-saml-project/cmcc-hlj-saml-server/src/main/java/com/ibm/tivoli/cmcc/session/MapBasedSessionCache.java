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
public class MapBasedSessionCache<V> implements SessionCache<V> {

  private Map<String, V> sessionMap = new ConcurrentHashMap<String, V>();

  /**
   * 
   */
  public MapBasedSessionCache() {
    super();
  }

  public void put(String artifactID, V value) {
    this.sessionMap.put(artifactID, value);
  }

  public V get(String artifactID) {
    return this.sessionMap.get(artifactID);
  }

  public void remove(String artifactID) {
    this.sessionMap.remove(artifactID);
  }

}
