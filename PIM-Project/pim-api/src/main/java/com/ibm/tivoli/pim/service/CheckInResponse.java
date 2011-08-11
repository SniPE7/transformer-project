/**
 * 
 */
package com.ibm.tivoli.pim.service;

import javax.xml.bind.annotation.XmlType;

import com.ibm.tivoli.pim.entity.BaseResponse;

/**
 * @author zhaodonglu
 *
 */
@XmlType(namespace="http://CheckInResponse.entity.pim.tivoli.ibm.com")
public class CheckInResponse extends BaseResponse {

  /**
   * 
   */
  public CheckInResponse() {
    super();
  }

  public CheckInResponse(String code) {
    super(code);
  }

  /**
   * @param code
   * @param cause
   */
  public CheckInResponse(String code, String cause) {
    super(code, cause);
  }

}
