/**
 * 
 */
package com.ibm.siam.am.idp.authn;

/**
 * @author zhaodonglu
 *
 */
public interface LoginContextEventListenerAware {
  
  /**
   * Set LoginModuleEventListener
   * @param listener
   */
  public abstract void setLoginContextEventListener(LoginContextEventListener listener);

}
