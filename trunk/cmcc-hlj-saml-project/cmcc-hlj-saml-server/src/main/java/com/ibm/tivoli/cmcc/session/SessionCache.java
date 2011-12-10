/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

/**
 * @author zhaodonglu
 * 
 */
public interface SessionCache<Value> {

  public abstract void put(String artifactID, Value value);

  public abstract Value get(String artifactID);

  public abstract void remove(String artifactID);

}
