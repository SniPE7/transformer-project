/**
 * 
 */
package com.ibm.siam.am.idp.authn.module;

import javax.security.auth.login.LoginException;

/**
 * @author zhaodonglu
 *
 */
public class PasswordExpiredLoginException extends LoginException {

  /**
   * 
   */
  private static final long serialVersionUID = -1965033415694348165L;

  private String username = null;

  /**
   * 
   */
  protected PasswordExpiredLoginException() {
    super();
  }

  /**
   * @param msg
   */
  public PasswordExpiredLoginException(String targetUsername, String msg) {
    super(msg);
    this.username = targetUsername;
  }

  public String getUsername() {
    return username;
  }

}
