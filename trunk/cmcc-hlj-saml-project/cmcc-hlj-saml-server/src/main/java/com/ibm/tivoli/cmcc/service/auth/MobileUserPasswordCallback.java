/**
 * 
 */
package com.ibm.tivoli.cmcc.service.auth;

import javax.security.auth.callback.Callback;

/**
 * @author zhaodonglu
 *
 */
public class MobileUserPasswordCallback implements Callback {
  
  private String msisdn = null;
  /**
   * PasswordType：密码类型。取值范围：
   * 1：互联网密码
   * 2：服务密码
   */
  private String passworType = null;
  private char[] password = null;

  /**
   * 
   */
  public MobileUserPasswordCallback() {
    super();
  }

  /**
   * @param msisdn
   * @param passworType
   * @param password
   */
  public MobileUserPasswordCallback(String msisdn, String passworType, char[] password) {
    super();
    this.msisdn = msisdn;
    this.passworType = passworType;
    this.password = password;
  }

  /**
   * @return the msisdn
   */
  public String getMsisdn() {
    return msisdn;
  }

  /**
   * @param msisdn the msisdn to set
   */
  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }

  /**
   * @return the passworType
   */
  public String getPassworType() {
    return passworType;
  }

  /**
   * @param passworType the passworType to set
   */
  public void setPassworType(String passworType) {
    this.passworType = passworType;
  }

  /**
   * @return the password
   */
  public char[] getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(char[] password) {
    this.password = password;
  }

  /**
   * For secure issue from memory, clear memory bytes
   */
  public void clearPassword() {
    if (this.password != null) {
       return;
    }
    for (int i = 0; i < password.length; i++)
      password[i] = ' ';
  }
}
