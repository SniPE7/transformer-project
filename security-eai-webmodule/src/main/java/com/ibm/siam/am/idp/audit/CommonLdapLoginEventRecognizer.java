/**
 * 
 */
package com.ibm.siam.am.idp.audit;

import com.ibm.siam.am.idp.authn.LoginContextEvent;
import com.ibm.siam.am.idp.authn.LoginModuleEvent;
import com.ibm.siam.am.idp.authn.module.CommonLdapAuthLoginModule;
import com.sinopec.siam.audit.model.W7Event;

/**
 * @author Administrator
 *
 */
public class CommonLdapLoginEventRecognizer extends AbstractAuditEventRecognizer {

  /**
   * 
   */
  public CommonLdapLoginEventRecognizer() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.audit.AbstractAuditEventRecognizer#isAuditEvent(com.ibm.siam.am.idp.authn.LoginModuleEvent)
   */
  @Override
  protected boolean isAuditEvent(LoginModuleEvent event) {
    return event.getPhase().name().equals("LOGIN") 
        && event.getLoginModuleEntry().getLoginModuleName().equals(CommonLdapAuthLoginModule.class.getName());
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.audit.AbstractAuditEventRecognizer#isAuditEvent(com.ibm.siam.am.idp.authn.LoginContextEvent)
   */
  @Override
  protected boolean isAuditEvent(LoginContextEvent event) {
    return false;
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.audit.AbstractAuditEventRecognizer#completeAuditEvent(com.ibm.siam.am.idp.authn.LoginModuleEvent, com.sinopec.siam.audit.model.W7Event)
   */
  @Override
  protected void completeAuditEvent(LoginModuleEvent event, W7Event w7Event) {
    w7Event.getWhat().setVerb(readValue(CommonLdapAuthLoginModule.class.getSimpleName()));

  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.audit.AbstractAuditEventRecognizer#completeAuditEvent(com.ibm.siam.am.idp.authn.LoginContextEvent, com.sinopec.siam.audit.model.W7Event)
   */
  @Override
  protected void completeAuditEvent(LoginContextEvent event, W7Event w7Event) {
    // TODO Auto-generated method stub

  }

}
