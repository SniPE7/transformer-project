/**
 * 
 */
package com.sinopec.siam.am.idp.audit;

import com.sinopec.siam.am.idp.authn.LoginContextEvent;
import com.sinopec.siam.am.idp.authn.LoginModuleEvent;
import com.sinopec.siam.am.idp.authn.module.UserPassResetLdapLoginModule;
import com.sinopec.siam.audit.model.W7Event;

/**
 * @author Administrator
 *
 */
public class PassResetLdapAuditEventRecognizer extends AbstractAuditEventRecognizer {

  /**
   * 
   */
  public PassResetLdapAuditEventRecognizer() {
    super();
  }

  /* (non-Javadoc)
   * @see com.sinopec.siam.am.idp.audit.AbstractAuditEventRecognizer#isAuditEvent(com.sinopec.siam.am.idp.authn.LoginModuleEvent)
   */
  @Override
  protected boolean isAuditEvent(LoginModuleEvent event) {
    return event.getPhase().name().equals("LOGIN") 
        && event.getLoginModuleEntry().getLoginModuleName().equals(UserPassResetLdapLoginModule.class.getName());
  }

  /* (non-Javadoc)
   * @see com.sinopec.siam.am.idp.audit.AbstractAuditEventRecognizer#isAuditEvent(com.sinopec.siam.am.idp.authn.LoginContextEvent)
   */
  @Override
  protected boolean isAuditEvent(LoginContextEvent event) {
    return false;
  }

  /* (non-Javadoc)
   * @see com.sinopec.siam.am.idp.audit.AbstractAuditEventRecognizer#completeAuditEvent(com.sinopec.siam.am.idp.authn.LoginModuleEvent, com.sinopec.siam.audit.model.W7Event)
   */
  @Override
  protected void completeAuditEvent(LoginModuleEvent event, W7Event w7Event) {
    w7Event.getWhat().setVerb(readValue(UserPassResetLdapLoginModule.class.getSimpleName()));

  }

  /* (non-Javadoc)
   * @see com.sinopec.siam.am.idp.audit.AbstractAuditEventRecognizer#completeAuditEvent(com.sinopec.siam.am.idp.authn.LoginContextEvent, com.sinopec.siam.audit.model.W7Event)
   */
  @Override
  protected void completeAuditEvent(LoginContextEvent event, W7Event w7Event) {
    // TODO Auto-generated method stub

  }

}
