/**
 * 
 */
package com.sinopec.siam.am.idp.authn;

import javax.security.auth.login.LoginException;

/**
 * Present Idp monitor event status.
 * 
 * @author Booker
 * 
 */
public class IdPMonitorEventStatus {

  /**
   * true - login success
   */
  private boolean success = false;

  /**
   * Default constructor
   */
  public IdPMonitorEventStatus() {
    super();
  }

  /**
   * Constructor with status.
   * 
   * @param success
   */
  public IdPMonitorEventStatus(boolean success) {
    super();
    this.success = success;
  }

  /**
   * @return
   */
  public boolean isSuccess() {
    return success;
  }

  /**
   * @param success
   */
  public void setSuccess(boolean success) {
    this.success = success;
  }

  @Override
  public String toString() {
    return String.format("IdPMonitorEventStatus [success=%s]", success);
  }

}
