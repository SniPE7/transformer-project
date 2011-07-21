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
import com.ibm.tivoli.pim.entity.ApprovalReponse;
import com.ibm.tivoli.pim.entity.CheckInAccount;
import com.ibm.tivoli.pim.entity.RejectReponse;
import com.ibm.tivoli.pim.entity.SubmitResponse;
import com.ibm.tivoli.pim.entity.TimeRange;
import com.ibm.tivoli.pim.entity.User;

/**
 * @author zhaodonglu
 *
 */
@WebService(targetNamespace="http://requestmanager.pim.tivoli.ibm.com/")
public interface RequestManager {
  @WebMethod(operationName = "submit")
  @WebResult(name="submitResponse")
  public abstract SubmitResponse submit(@WebParam(name = "request")AccountRequest request);
  
  @WebMethod(operationName = "approval")
  @WebResult(name="submitResponse")
  public abstract ApprovalReponse approval(@WebParam(name = "approver")User approver, @WebParam(name = "requestId")String requestId);
  
  @WebMethod(operationName = "reject")
  @WebResult(name="rejectReponse")
  public abstract RejectReponse reject(@WebParam(name = "rejector")User rejector, @WebParam(name = "requestId")String requestId);
  
  @WebMethod(operationName = "getPendingRequestsByRequester")
  @WebResult(name="getPendingRequestsByRequesterResp")
  public abstract List<AccountRequest> getPendingRequestsByRequester(@WebParam(name = "approver")User requester);
  
  @WebMethod(operationName = "getPendingRequestsByTargetService")
  @WebResult(name="getPendingRequestsByTargetServiceResp")
  public abstract List<AccountRequest> getPendingRequestsByTargetService(@WebParam(name = "serviceName")String serviceName);
  
  @WebMethod(operationName = "getPendingRequestsByTargetAccount")
  @WebResult(name="getPendingRequestsByTargetAccountResp")
  public abstract List<AccountRequest> getPendingRequestsByTargetAccount(@WebParam(name = "serviceName")String serviceName, @WebParam(name = "accountId")String accountId);
  
  @WebMethod(operationName = "getPendingRequestsByApprover")
  @WebResult(name="getPendingRequestsByApproverResp")
  public abstract List<AccountRequest> getPendingRequestsByApprover(@WebParam(name = "approver")User approver);
  
  public abstract List<CheckInAccount> getAllCheckInAccount(TimeRange timeRange);

}
