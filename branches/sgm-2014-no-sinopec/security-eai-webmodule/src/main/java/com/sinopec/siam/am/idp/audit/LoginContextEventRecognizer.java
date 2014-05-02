/**
 * 
 */
package com.sinopec.siam.am.idp.audit;

import com.sinopec.siam.am.idp.authn.LoginContextEvent;
import com.sinopec.siam.am.idp.authn.LoginModuleEvent;
import com.sinopec.siam.am.idp.authn.module.UserPassPastDueLdapLoginModule;
import com.sinopec.siam.audit.model.W7Event;

/**
 * @author Administrator
 *
 */
public class LoginContextEventRecognizer extends AbstractAuditEventRecognizer {

  /**
   * 
   */
  public LoginContextEventRecognizer() {
    super();
  }

  /* (non-Javadoc)
   * @see com.sinopec.siam.am.idp.audit.AbstractAuditEventRecognizer#isAuditEvent(com.sinopec.siam.am.idp.authn.LoginModuleEvent)
   */
  @Override
  protected boolean isAuditEvent(LoginModuleEvent event) {
    return false;
  }

  /* (non-Javadoc)
   * @see com.sinopec.siam.am.idp.audit.AbstractAuditEventRecognizer#isAuditEvent(com.sinopec.siam.am.idp.authn.LoginContextEvent)
   */
  @Override
  protected boolean isAuditEvent(LoginContextEvent event) {
    return true;
  }

  /* (non-Javadoc)
   * @see com.sinopec.siam.am.idp.audit.AbstractAuditEventRecognizer#completeAuditEvent(com.sinopec.siam.am.idp.authn.LoginModuleEvent, com.sinopec.siam.audit.model.W7Event)
   */
  @Override
  protected void completeAuditEvent(LoginModuleEvent event, W7Event w7Event) {
  }

  /* (non-Javadoc)
   * @see com.sinopec.siam.am.idp.audit.AbstractAuditEventRecognizer#completeAuditEvent(com.sinopec.siam.am.idp.authn.LoginContextEvent, com.sinopec.siam.audit.model.W7Event)
   */
  @Override
  protected void completeAuditEvent(LoginContextEvent event, W7Event w7Event) {
  }

}
