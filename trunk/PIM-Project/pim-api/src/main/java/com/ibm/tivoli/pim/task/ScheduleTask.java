/**
 * 
 */
package com.ibm.tivoli.pim.task;

/**
 * @author zhaodonglu
 *
 */
public abstract class ScheduleTask {

  /**
   * 
   */
  protected ScheduleTask() {
    super();
  }
  
  public void execute() {
    // Enable accounts
    enableAccounts();
    
    // Disable accounts
    disableAccounts();
    
  }

  /**
   * Enable all accounts
   */
  protected abstract void enableAccounts();

  /**
   * Disable all accounts.
   */
  protected abstract void disableAccounts();

}
