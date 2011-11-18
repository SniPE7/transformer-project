/**
 * 
 */
package com.ibm.tivoli.cmcc.request;

public class PasswordResetRequest {
  private String userName = null;
  private String serviceCode = null;
  private String networkPassword = null;

  public PasswordResetRequest() {
    super();
  }

  public PasswordResetRequest(String userName, String serviceCode, String networkPassword) {
    super();
    this.userName = userName;
    this.serviceCode = serviceCode;
    this.networkPassword = networkPassword;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getServiceCode() {
    return serviceCode;
  }

  public void setServiceCode(String serviceCode) {
    this.serviceCode = serviceCode;
  }

  public String getNetworkPassword() {
    return networkPassword;
  }

  public void setNetworkPassword(String networkPassword) {
    this.networkPassword = networkPassword;
  }
}