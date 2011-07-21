/**
 * 
 */
package com.ibm.tivoli.pim.entity;

import java.util.Date;

/**
 * @author zhaodonglu
 *
 */
public class TimeRange {
  
  private Date beginTime = null;
  private Date endTime = null;

  /**
   * 
   */
  public TimeRange() {
    super();
  }

  public TimeRange(Date beginTime, Date endTime) {
    super();
    this.beginTime = beginTime;
    this.endTime = endTime;
  }

  /**
   * @return the beginTime
   */
  public Date getBeginTime() {
    return beginTime;
  }

  /**
   * @param beginTime the beginTime to set
   */
  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  /**
   * @return the endTime
   */
  public Date getEndTime() {
    return endTime;
  }

  /**
   * @param endTime the endTime to set
   */
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

}
