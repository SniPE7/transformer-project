/**
 * 
 */
package com.ibm.tivoli.pim.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.ibm.tivoli.pim.entity.CheckInAccount;
import com.ibm.tivoli.pim.entity.TimeRange;

/**
 * @author zhaodonglu
 *
 */
@WebService(targetNamespace="http://checkinmanager.pim.tivoli.ibm.com/")
public interface CheckInManager {

  @WebMethod(operationName = "getAllCheckInAccount")
  @WebResult(name="getAllCheckInAccountResp")
  public abstract List<CheckInAccount> getAllCheckInAccount(TimeRange timeRange);
}
