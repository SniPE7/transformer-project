/**
 * 
 */
package com.ibm.tivoli.pim.service;

import java.util.Hashtable;

import com.ibm.itim.apps.PlatformContext;

/**
 * @author zhaodonglu
 *
 */
public class SimplePlatformContext implements PlatformContext {

  private Hashtable env = new Hashtable();

  /**
   * 
   */
  public SimplePlatformContext() {
    super();
  }

  public SimplePlatformContext(Hashtable env) {
    this();
    this.env  = env;
  }

  /* (non-Javadoc)
   * @see com.ibm.itim.apps.PlatformContext#close()
   */
  public void close() {
  }

  /* (non-Javadoc)
   * @see com.ibm.itim.apps.PlatformContext#getEnvProperty(java.lang.Object)
   */
  public Object getEnvProperty(Object arg0) {
    return this.env.get(arg0);
  }

  /* (non-Javadoc)
   * @see com.ibm.itim.apps.PlatformContext#getEnvironment()
   */
  public Hashtable getEnvironment() {
    return this.env;
  }

  /* (non-Javadoc)
   * @see com.ibm.itim.apps.PlatformContext#getID()
   */
  public Long getID() {
    return new Long(this.hashCode());
  }

}
