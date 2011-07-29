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
  private PIMAccount account = null;

  /**
   * Time range for this request
   */
  private TimeRange timeRange = null;

  /**
   * Requester
   */
  private User requester = null;

  /**
   * Name of PIM Service
   */
  private String pimServiceName = null;

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
   * @param id
   *          the id to set
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
   * @param service
   *          the service to set
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
   * @param account
   *          the account to set
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
   * @param timeRange
   *          the timeRange to set
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
   * @param requester
   *          the requester to set
   */
  public void setRequester(User requester) {
    this.requester = requester;
  }

  public String getPimServiceName() {
    return pimServiceName;
  }

  public void setPimServiceName(String pimServiceName) {
    this.pimServiceName = pimServiceName;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AccountRequest [id=");
    builder.append(id);
    builder.append(", service=");
    builder.append(service);
    builder.append(", account=");
    builder.append(account);
    builder.append(", timeRange=");
    builder.append(timeRange);
    builder.append(", requester=");
    builder.append(requester);
    builder.append(", pimServiceName=");
    builder.append(pimServiceName);
    builder.append("]");
    return builder.toString();
  }

}
