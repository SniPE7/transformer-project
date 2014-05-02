/**
 * 
 */
package com.ibm.siam.am.idp.authn;

/**
 * @author zhaodonglu
 *
 */
public interface LoginContextEventListener {

  /**
   * Fire an event.
   * @param event
   */
  public abstract void fireEvent(LoginContextEvent event);
}
