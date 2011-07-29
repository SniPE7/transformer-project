/**
 * 
 */
package com.ibm.tivoli.pim.entity;

import java.util.Date;

/**
 * @author zhaodonglu
 *
 */
public class CheckInAccount {

  private String requestId = null;
  
  /**
   * Target account service for this request
   */
  private Service service = null;

  /**
   * Target account for this request
   */
  private PIMAccount account = null;
  
  /**
   * Time range for this request
   */
  private TimeRange timeRange = null;
  
  /**
   * Requester
   */
  private User requester = null;
  
  private User approver = null;
  
  private Date approvedTime = null;
  
  private Date checkOutTime = null;
  /**
   * 
   */
  public CheckInAccount() {
    super();
  }
  /**
   * @return the requestId
   */
  public String getRequestId() {
    return requestId;
  }
  /**
   * @param requestId the requestId to set
   */
  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }
  /**
   * @return the service
   */
  public Service getService() {
    return service;
  }
  /**
   * @param service the service to set
   */
  public void setService(Service service) {
    this.service = service;
  }
  /**
   * @return the account
   */
  public PIMAccount getAccount() {
    return account;
  }
  /**
   * @param account the account to set
   */
  public void setAccount(PIMAccount account) {
    this.account = account;
  }
  /**
   * @return the timeRange
   */
  public TimeRange getTimeRange() {
    return timeRange;
  }
  /**
   * @param timeRange the timeRange to set
   */
  public void setTimeRange(TimeRange timeRange) {
    this.timeRange = timeRange;
  }
  /**
   * @return the requester
   */
  public User getRequester() {
    return requester;
  }
  /**
   * @param requester the requester to set
   */
  public void setRequester(User requester) {
    this.requester = requester;
  }
  /**
   * @return the approver
   */
  public User getApprover() {
    return approver;
  }
  /**
   * @param approver the approver to set
   */
  public void setApprover(User approver) {
    this.approver = approver;
  }
  /**
   * @return the approvedTime
   */
  public Date getApprovedTime() {
    return approvedTime;
  }
  /**
   * @param approvedTime the approvedTime to set
   */
  public void setApprovedTime(Date approvedTime) {
    this.approvedTime = approvedTime;
  }
  /**
   * @return the checkOutTime
   */
  public Date getCheckOutTime() {
    return checkOutTime;
  }
  /**
   * @param checkOutTime the checkOutTime to set
   */
  public void setCheckOutTime(Date checkOutTime) {
    this.checkOutTime = checkOutTime;
  }

  
}
