/**
 * 
 */
package com.ibm.tivoli.pim.entity;

/**
 * @author zhaodonglu
 *
 */
public class SubmitResponse extends BaseResponse {
  
  private String requestId = null;

  /**
   * 
   */
  public SubmitResponse() {
    super();
  }

  public SubmitResponse(String code, String cause) {
    super(code, cause);
  }

  /**
   * @return the requestId
   */
  public String getRequestId() {
    return requestId;
  }

  /**
   * @param requestId the requestId to set
   */
  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

}
