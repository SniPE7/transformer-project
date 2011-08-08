/**
 * 
 */
package com.ibm.tivoli.pim.entity;

import javax.xml.bind.annotation.XmlType;

/**
 * @author zhaodonglu
 *
 */
@XmlType(namespace="http://submitResponse.entity.pim.tivoli.ibm.com")
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

  public SubmitResponse(String requestId) {
    super();
    this.setCode("Success");
    this.requestId = requestId;
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("SubmitResponse [requestId=");
    builder.append(requestId);
    builder.append("]");
    return builder.toString();
  }

}
