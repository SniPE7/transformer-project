/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import javax.security.auth.login.LoginException;

/**
 * Present login module status.
 * @author zhaodonglu
 * 
 */
public class LoginContextEventStatus {

  /**
   * true - login success
   */
  private boolean success = false;
  
  /**
   * Where fail, present exception.
   */
  private LoginException loginException = null;

  /**
   * Default constructor
   */
  public LoginContextEventStatus() {
    super();
  }

  /**
   * Constructor with status.
   * @param success
   */
  public LoginContextEventStatus(boolean success) {
    super();
    this.success = success;
  }

  /**
   * Constructor with status and exception.
   * @param success
   * @param loginException
   */
  public LoginContextEventStatus(boolean success, LoginException loginException) {
    super();
    this.success = success;
    this.loginException = loginException;
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

  /**
   * @return
   */
  public LoginException getLoginException() {
    return loginException;
  }

  /**
   * @param loginException
   */
  public void setLoginException(LoginException loginException) {
    this.loginException = loginException;
  }

  @Override
  public String toString() {
    return String.format("LoginContextEventStatus [success=%s, loginException=%s]", success, loginException);
  }

}
