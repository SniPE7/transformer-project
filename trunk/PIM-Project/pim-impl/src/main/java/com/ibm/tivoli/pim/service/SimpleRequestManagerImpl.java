/**
 * 
 */
package com.ibm.tivoli.pim.service;

import java.util.ArrayList;
import java.util.List;

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
public class SimpleRequestManagerImpl implements RequestManager {

  /**
   * 
   */
  public SimpleRequestManagerImpl() {
    super();
  }

  public SubmitResponse submit(AccountRequest request) {
    return new SubmitResponse(SubmitResponse.CODE_SUCCESS);
  }

  public ApprovalReponse approval(User approver, String requestId, String comment) {
    return new ApprovalReponse(ApprovalReponse.CODE_SUCCESS);
  }

  public RejectReponse reject(User rejector, String requestId, String comment) {
    return new RejectReponse(RejectReponse.CODE_SUCCESS);
  }

  public List<AccountRequest> getPendingRequestsByRequester(User requester) {
    return new ArrayList<AccountRequest>();
  }

  public List<AccountRequest> getPendingRequestsByTargetService(String serviceName) {
    return new ArrayList<AccountRequest>();
  }

  public List<AccountRequest> getPendingRequestsByTargetAccount(String serviceName, String accountId) {
    return new ArrayList<AccountRequest>();
  }

  public List<AccountRequest> getPendingRequestsByApprover(User approver) {
    return new ArrayList<AccountRequest>();
  }

  public List<CheckInAccount> getAllCheckInAccount(TimeRange timeRange) {
    return new ArrayList<CheckInAccount>();
  }

}
