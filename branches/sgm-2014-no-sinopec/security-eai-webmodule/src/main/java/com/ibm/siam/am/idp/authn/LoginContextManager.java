/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

/**
 * @author zhaodonglu
 *
 */
public interface LoginContextManager {
  /**
   * Instantiate a new LoginContext object with a name, a Subject to be authenticated, a CallbackHandler object.
   *  
   * @param name the name used as the index into the caller-specified Configuration. 
   * @param subject the Subject to authenticate, or null. 
   * @param callbackHandler the CallbackHandler object used by LoginModules to communicate with the user, or null.
   * @return
   * @throws LoginException if the caller-specified name does not appear in the Configuration.
   */
  public LoginContext getLoginContext(String name, Subject subject, CallbackHandler callbackHandler) throws LoginException;
  
  /**
   * Instantiate a new LoginContext object with a name, a Subject to be authenticated, a CallbackHandler object.
   *  
   * @param name the name used as the index into the caller-specified Configuration. 
   * @param subject the Subject to authenticate, or null. 
   * @param callbackHandler the CallbackHandler object used by LoginModules to communicate with the user, or null.
   * @param state share state for LoginModule
   * @return
   * @throws LoginException if the caller-specified name does not appear in the Configuration.
   */
  public LoginContext getLoginContext(String name, Subject subject, CallbackHandler callbackHandler, Map<?, ?> state) throws LoginException;

}
