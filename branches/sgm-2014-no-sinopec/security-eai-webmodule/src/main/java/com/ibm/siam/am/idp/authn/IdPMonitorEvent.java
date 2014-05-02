/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import javax.security.auth.callback.CallbackHandler;

/**
 * @author Booker
 *
 */
public class IdPMonitorEvent {
  /**
   * Status of login process
   */
  private IdPMonitorEventStatus status = null;
  
  /**
   * CallbackHandler for this event
   */
  private CallbackHandler callbackHandler = null;

  /**
   * Constructor
   * @param targetLoginModuleEntry
   * @param phase
   * @param status
   */
  public IdPMonitorEvent(CallbackHandler callbackHandler, IdPMonitorEventStatus status) {
    super();
    this.callbackHandler = callbackHandler;
    this.status = status;
  }

  @Override
  public String toString() {
    return String.format("IdPMonitorEvent, callbackHandler=%s, status=%s]", callbackHandler, getStatus());
  }

  /**
   * @return the status
   */
  public IdPMonitorEventStatus getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(IdPMonitorEventStatus status) {
    this.status = status;
  }

  /**
   * @return the callbackHandler
   */
  public CallbackHandler getCallbackHandler() {
    return callbackHandler;
  }

  /**
   * @param callbackHandler the callbackHandler to set
   */
  public void setCallbackHandler(CallbackHandler callbackHandler) {
    this.callbackHandler = callbackHandler;
  }
}
