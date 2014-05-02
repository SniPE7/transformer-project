/**
 * 
 */
package com.ibm.siam.am.idp.authn;

/**
 * @author zhaodonglu
 *
 */
public interface LoginModuleEventListenerAware {
  
  /**
   * Set LoginModuleEventListener
   * @param listener
   */
  public abstract void setLoginModuleEventListener(LoginModuleEventListener listener);

}
