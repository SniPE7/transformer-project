/**
 * 
 */
package com.ibm.siam.am.idp.authn.module;

import javax.security.auth.login.LoginException;

/**
 * @author zhaodonglu
 *
 */
public class NeedChangePasswordLoginException extends LoginException {

  /**
   * 
   */
  private static final long serialVersionUID = -1965033415694348165L;

  private String username = null;

  /**
   * 
   */
  public NeedChangePasswordLoginException() {
    super();
  }

  /**
   * @param msg
   */
  public NeedChangePasswordLoginException(String targetUsername, String msg) {
    super(msg);
    this.username = targetUsername;
  }

  public String getUsername() {
    return username;
  }

}
