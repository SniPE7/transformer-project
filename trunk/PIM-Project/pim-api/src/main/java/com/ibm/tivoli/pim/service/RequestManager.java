/**
 * 
 */
package com.ibm.tivoli.pim.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
@WebService(targetNamespace="http://reqmgr.service.pim.tivoli.ibm.com/")
@Path(value = "/request_manager_service")  
public interface RequestManager {
  @WebMethod(operationName = "submit")
  @WebResult(name="submitResponse")
  @POST
  @Path(value = "/submit")
  @Produces({"application/xml", "application/json"})
  @Consumes({"application/xml", "application/json"})
  public abstract SubmitResponse submit(@WebParam(name = "request")AccountRequest request);
  
  @WebMethod(operationName = "approval")
  @WebResult(name="approvalResponse")
  @POST
  @Path(value = "/approval")
  @Produces({"application/xml", "application/json"})
  @Consumes({"application/xml", "application/json"})
  public abstract ApprovalReponse approval(@WebParam(name = "approver")User approver, @WebParam(name = "requestId")String requestId, @WebParam(name = "comment")String comment);
  
  @WebMethod(operationName = "reject")
  @WebResult(name="rejectReponse")
  @POST
  @Path(value = "/reject")
  @Produces({"application/xml", "application/json"})
  @Consumes({"application/xml", "application/json"})
  public abstract RejectReponse reject(@WebParam(name = "rejector")User rejector, @WebParam(name = "requestId")String requestId, @WebParam(name = "comment")String comment);
  
  @WebMethod(operationName = "getPendingRequestsByRequester")
  @WebResult(name="getPendingRequestsByRequesterResp")
  @POST
  @Path(value = "/pendings_by_requestor")
  @Produces({"application/xml", "application/json"})
  @Consumes({"application/xml", "application/json"})
  public abstract List<AccountRequest> getPendingRequestsByRequester(@WebParam(name = "requester")User requester);
  
  @WebMethod(operationName = "getPendingRequestsByTargetService")
  @WebResult(name="getPendingRequestsByTargetServiceResp")
  @GET
  @Path(value = "/pendings_by_service/{serviceName}/result")
  @Produces({"application/xml", "application/json"})
  @Consumes({"application/xml", "application/json"})
  public abstract List<AccountRequest> getPendingRequestsByTargetService(@WebParam(name = "serviceName") @PathParam("serviceName")String serviceName);
  
  @WebMethod(operationName = "getPendingRequestsByTargetAccount")
  @WebResult(name="getPendingRequestsByTargetAccountResp")
  public abstract List<AccountRequest> getPendingRequestsByTargetAccount(@WebParam(name = "serviceName")String serviceName, @WebParam(name = "accountId")String accountId);
  
  @WebMethod(operationName = "getPendingRequestsByApprover")
  @WebResult(name="getPendingRequestsByApproverResp")
  public abstract List<AccountRequest> getPendingRequestsByApprover(@WebParam(name = "approver")User approver);
  
  public abstract List<CheckInAccount> getAllCheckInAccount(TimeRange timeRange);

}
