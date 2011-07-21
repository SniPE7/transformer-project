/**
 * 
 */
package com.ibm.tivoli.pim.service;

import com.ibm.tivoli.pim.entity.BaseResponse;

/**
 * @author zhaodonglu
 *
 */
public class CheckOutResponse extends BaseResponse {

  /**
   * 
   */
  public CheckOutResponse() {
    super();
  }

  /**
   * @param code
   * @param cause
   */
  public CheckOutResponse(String code, String cause) {
    super(code, cause);
  }

}
