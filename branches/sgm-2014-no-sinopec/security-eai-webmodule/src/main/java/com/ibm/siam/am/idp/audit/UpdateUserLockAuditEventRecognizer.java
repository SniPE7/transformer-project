/**
 * 
 */
package com.ibm.siam.am.idp.audit;

import com.ibm.siam.am.idp.authn.LoginContextEvent;
import com.ibm.siam.am.idp.authn.LoginModuleEvent;
import com.ibm.siam.am.idp.authn.module.UpdateUserLockStateLoginModule;
import com.sinopec.siam.audit.model.W7Event;

/**
 * @author jiaodongliang
 *
 */
public class UpdateUserLockAuditEventRecognizer extends AbstractAuditEventRecognizer {

  /**
   * 
   */
  public UpdateUserLockAuditEventRecognizer() {
    super();
  }

  @Override
  protected boolean isAuditEvent(LoginModuleEvent event) {
    return event.getPhase().name().equals("LOGIN") 
        && event.getLoginModuleEntry().getLoginModuleName().equals(UpdateUserLockStateLoginModule.class.getName());
  }

  @Override
  protected boolean isAuditEvent(LoginContextEvent event) {
    return false;
  }

  @Override
  protected void completeAuditEvent(LoginModuleEvent event, W7Event w7Event) {
    w7Event.getWhat().setVerb(readValue(UpdateUserLockStateLoginModule.class.getSimpleName()));    
  }

  @Override
  protected void completeAuditEvent(LoginContextEvent event, W7Event w7Event) {
    // TODO Auto-generated method stub
    
  }

}
