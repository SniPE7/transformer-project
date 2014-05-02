package com.ibm.siam.am.idp.authn.module;

import javax.security.auth.login.LoginException;

public class DetailLoginException extends LoginException {

  /**
   * 
   */
  private static final long serialVersionUID = -5904444503964523315L;
  private String loginErrorKey = null;
  private String loginErrorArgs = null;
  

  protected DetailLoginException() {
    super();
  }

  protected DetailLoginException(String msg) {
    super(msg);
  }

  public DetailLoginException(String loginErrorKey, String loginErrorMessage) {
    this(loginErrorMessage);
    this.loginErrorKey  = loginErrorKey;
  }

  public DetailLoginException(String loginErrorKey, String loginErrorArgs, String loginErrorMessage) {
    super();
    this.loginErrorKey = loginErrorKey;
    this.loginErrorArgs = loginErrorArgs;
  }

  public String getLoginErrorKey() {
    return loginErrorKey;
  }

  public String getLoginErrorArgs() {
    return loginErrorArgs;
  }

  public void setLoginErrorArgs(String loginErrorArgs) {
    this.loginErrorArgs = loginErrorArgs;
  }

}
