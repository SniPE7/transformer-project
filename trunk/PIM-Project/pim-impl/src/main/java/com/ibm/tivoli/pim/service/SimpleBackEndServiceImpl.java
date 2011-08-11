/**
 * 
 */
package com.ibm.tivoli.pim.service;

import com.ibm.tivoli.pim.entity.AccountRequest;

/**
 * @author zhaodonglu
 *
 */
public class SimpleBackEndServiceImpl implements BackEndService {

  /**
   * 
   */
  public SimpleBackEndServiceImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.pim.service.BackEndService#checkIn(com.ibm.tivoli.pim.entity.AccountRequest)
   */
  public CheckInResponse checkIn(AccountRequest request) {
    return new CheckInResponse(CheckInResponse.CODE_SUCCESS);
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.pim.service.BackEndService#checkOut(com.ibm.tivoli.pim.entity.AccountRequest)
   */
  public CheckOutResponse checkOut(AccountRequest request) {
    return new CheckOutResponse(CheckOutResponse.CODE_SUCCESS);
  }

}
