/**
 * 
 */
package com.ibm.tivoli.pim.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.ibm.tivoli.pim.entity.AccountRequest;
import com.ibm.tivoli.pim.entity.CheckInAccount;

/**
 * @author zhaodonglu
 *
 */
@WebService(targetNamespace="http://backendservice.pim.tivoli.ibm.com/")
public interface BackEndService {

  @WebMethod(operationName = "checkIn")
  @WebResult(name="checkInResp")
  public abstract CheckInResponse checkIn(@WebParam(name = "request")AccountRequest request);

  @WebMethod(operationName = "checkOut")
  @WebResult(name="checkOutResp")
  public abstract CheckOutResponse checkOut(@WebParam(name = "request")AccountRequest request);

}
