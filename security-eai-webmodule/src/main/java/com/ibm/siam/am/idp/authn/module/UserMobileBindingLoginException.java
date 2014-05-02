/**
 * 
 */
package com.ibm.siam.am.idp.authn.module;

import javax.security.auth.login.LoginException;

/**
 * @author zhaodonglu
 * 
 */
public class UserMobileBindingLoginException extends LoginException {

  private String username;
  
  private String loginErrorKey = null;

  private String loginErrorArgs = null;
  /**
   * 
   */
  private static final long serialVersionUID = -7093150055855178217L;

  /**
   * 
   */
  protected UserMobileBindingLoginException() {
    super();
  }

  /**
   * @param msg
   */
  public UserMobileBindingLoginException(String loginErrorKey, String loginErrorArgs, String msg) {
    super(msg);
    this.loginErrorKey = loginErrorKey;
    this.loginErrorArgs = loginErrorArgs;
  }
  
  public UserMobileBindingLoginException(String targetUsername, String loginErrorKey, String loginErrorArgs, String msg) {
	    super(msg);
	    this.loginErrorKey = loginErrorKey;
	    this.loginErrorArgs = loginErrorArgs;
	    this.username = targetUsername;
	  }
  
  /**
   * @param msg
   */
  public UserMobileBindingLoginException(String targetUsername, String msg) {
    super(msg);
    this.username = targetUsername;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getLoginErrorKey() {
    return loginErrorKey;
  }

  public String getLoginErrorArgs() {
    return loginErrorArgs;
  }

}
