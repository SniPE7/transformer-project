/**
 * 
 */
package com.ibm.tivoli.pim.entity;


/**
 * @author zhaodonglu
 *
 */
public class ApprovalReponse extends BaseResponse {

  /**
   * 
   */
  public ApprovalReponse() {
    super();
  }

  public ApprovalReponse(String code, String cause) {
    super(code, cause);
  }

  public ApprovalReponse(String code) {
    super(code);
  }

}
