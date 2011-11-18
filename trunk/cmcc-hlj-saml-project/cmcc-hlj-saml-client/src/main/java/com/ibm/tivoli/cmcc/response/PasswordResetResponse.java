/**
 * 
 */
package com.ibm.tivoli.cmcc.response;


public class PasswordResetResponse {

  private String userName = null;
  private String resultCode = null;
  private String description = null;

  public PasswordResetResponse() {
    super();
  }

  public PasswordResetResponse(String userName, String resultCode, String description) {
    super();
    this.userName = userName;
    this.resultCode = resultCode;
    this.description = description;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getResultCode() {
    return resultCode;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}