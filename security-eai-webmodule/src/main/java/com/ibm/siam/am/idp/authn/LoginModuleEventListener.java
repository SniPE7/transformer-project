/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import javax.security.auth.login.LoginException;

/**
 * @author zhaodonglu
 *
 */
public interface LoginModuleEventListener {

  /**
   * Fire an event.
   * @param event
   * @throws LoginException 
   */
  public abstract void fireEvent(LoginModuleEvent event);
}
