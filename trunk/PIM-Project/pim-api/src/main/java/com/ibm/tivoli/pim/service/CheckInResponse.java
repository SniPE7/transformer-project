/**
 * 
 */
package com.ibm.tivoli.pim.service;

import com.ibm.tivoli.pim.entity.BaseResponse;

/**
 * @author zhaodonglu
 *
 */
public class CheckInResponse extends BaseResponse {

  /**
   * 
   */
  public CheckInResponse() {
    super();
  }

  /**
   * @param code
   * @param cause
   */
  public CheckInResponse(String code, String cause) {
    super(code, cause);
  }

}
