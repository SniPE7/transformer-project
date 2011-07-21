/**
 * 
 */
package com.ibm.tivoli.pim.entity;


/**
 * @author zhaodonglu
 *
 */
public class RejectReponse extends BaseResponse {

  /**
   * 
   */
  public RejectReponse() {
    super();
  }

  /**
   * @param code
   * @param cause
   */
  public RejectReponse(String code, String cause) {
    super(code, cause);
  }

}
