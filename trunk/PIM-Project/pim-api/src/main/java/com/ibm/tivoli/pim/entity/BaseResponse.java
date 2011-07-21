package com.ibm.tivoli.pim.entity;

public abstract class BaseResponse {

  private String code = null;
  private String cause = null;

  protected BaseResponse() {
    super();
  }

  protected BaseResponse(String code, String cause) {
    super();
    this.code = code;
    this.cause = cause;
  }

  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * @return the cause
   */
  public String getCause() {
    return cause;
  }

  /**
   * @param cause the cause to set
   */
  public void setCause(String cause) {
    this.cause = cause;
  }

}