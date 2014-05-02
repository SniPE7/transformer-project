package com.ibm.siam.am.idp.authn.module.tamldap;

import java.util.Date;

public class PerformanceMonitor {
  
  private int success = 0;
  private int failure = 0;

  private long startTime = System.currentTimeMillis();
  
  private long totalElapseTime = 0;
  private long maxElapseTime = 0;
  private long minElapseTime = 0;
  
  public PerformanceMonitor() {
    super();
  }
  
  public synchronized void incSuccess() {
    if (this.success == 0) {
       this.startTime = System.currentTimeMillis();
    }
    this.success++;
  }
  
  public synchronized void incFailure() {
    this.failure++;
  }

  public synchronized int getSuccess() {
    return success;
  }

  public synchronized int getFailure() {
    return failure;
  }

  @Override
  public String toString() {
    return String.format("PerformanceMonitor [%s] [total success=%s, total failure=%s, TPS success=%s(Login/Sec), avg elapseTime=%s(ms), max elapseTime=%s(ms), min elapseTime=%s(ms)]", 
                                         new Date(), 
                                         success, 
                                         failure, 
                                         ((int)success * 1000 /(System.currentTimeMillis() - this.startTime)), 
                                         (this.success != 0)?(this.totalElapseTime / this.success):0,
                                         this.maxElapseTime,
                                         this.minElapseTime
                                         );
  }

  public synchronized void addElapseTime(long elapseTime) {
    this.totalElapseTime += elapseTime;
    if (elapseTime > this.maxElapseTime) {
       this.maxElapseTime = elapseTime;
    }
    if ( this.minElapseTime == 0 || elapseTime < this.minElapseTime) {
       this.minElapseTime = elapseTime;
    }
  }

}
