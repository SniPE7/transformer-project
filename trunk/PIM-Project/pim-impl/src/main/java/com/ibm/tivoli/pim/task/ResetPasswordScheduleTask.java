/**
 * 
 */
package com.ibm.tivoli.pim.task;

import java.util.Collection;

import javax.security.auth.Subject;

import com.ibm.itim.apps.PlatformContext;
import com.ibm.itim.apps.provisioning.ServiceManager;
import com.ibm.itim.dataservices.model.domain.Service;

/**
 * @author zhaodonglu
 *
 */
public class ResetPasswordScheduleTask extends ScheduleTask {

  private PlatformContext platformContext = null;
  private Subject subject = null;

  /**
   * Name of the profile (NTAccount, Exchange Account, etc.) identifying the
   * type of this account as listed in Configuration > Entities within the IBM
   * Tivoli Idenitity Manager UI.
   */
  private String pimAccountProfileName = "PIMProfileAccount";

  /**
   * 
   */
  public ResetPasswordScheduleTask() {
    super();
  }

  /**
   * @return the platformContext
   */
  public PlatformContext getPlatformContext() {
    return platformContext;
  }

  /**
   * @param platformContext the platformContext to set
   */
  public void setPlatformContext(PlatformContext platformContext) {
    this.platformContext = platformContext;
  }

  /**
   * @return the subject
   */
  public Subject getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  @Override
  protected void enableAccounts() {
    
    ServiceManager sm = new ServiceManager(this.getPlatformContext(), this.getSubject());
    Collection<Service> services = sm.getServices(arg0, arg1);
    
  }

  @Override
  protected void disableAccounts() {
    
  }

}
