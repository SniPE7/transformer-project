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
@XmlType(namespace="http://CheckOutResponse.entity.pim.tivoli.ibm.com")
public class CheckOutResponse extends BaseResponse {

  /**
   * 
   */
  public CheckOutResponse() {
    super();
  }

  public CheckOutResponse(String code) {
    super(code);
  }

  /**
   * @param code
   * @param cause
   */
  public CheckOutResponse(String code, String cause) {
    super(code, cause);
  }

}
