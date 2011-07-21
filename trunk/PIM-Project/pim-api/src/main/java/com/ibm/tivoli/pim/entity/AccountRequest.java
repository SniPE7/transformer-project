/**
 * 
 */
package com.ibm.tivoli.pim.entity;

/**
 * @author zhaodonglu
 *
 */
public class AccountRequest {
  private String id = null;
  
  /**
   * Target account service for this request
   */
  private Service service = null;

  /**
   * Target account for this request
   */
  private Account account = null;
  
  /**
   * Time range for this request
   */
  private TimeRange timeRange = null;
  
  /**
   * Requester
   */
  private User requester = null;
  /**
   * 
   */
  public AccountRequest() {
    super();
  }
  /**
   * @return the id
   */
  public String getId() {
    return id;
  }
  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
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
  public Account getAccount() {
    return account;
  }
  /**
   * @param account the account to set
   */
  public void setAccount(Account account) {
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

}
