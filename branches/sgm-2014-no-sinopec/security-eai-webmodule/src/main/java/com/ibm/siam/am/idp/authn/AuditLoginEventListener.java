/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.siam.am.idp.audit.AuditEventRecognizer;

/**
 * @author zhaodonglu
 *
 */
public class AuditLoginEventListener implements LoginContextEventListener, LoginModuleEventListener {

  private static Log log = LogFactory.getLog(AuditLoginEventListener.class);

  private AuditEventRecognizer auditEventRecognizer = null;

  /**
   * 
   */
  public AuditLoginEventListener() {
    super();
  }

  public AuditEventRecognizer getAuditEventRecognizer() {
    return auditEventRecognizer;
  }

  public void setAuditEventRecognizer(AuditEventRecognizer auditEventRecognizer) {
    this.auditEventRecognizer = auditEventRecognizer;
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.authn.LoginModuleEventListener#fireEvent(com.ibm.siam.am.idp.authn.LoginModuleEvent)
   */
  public void fireEvent(LoginModuleEvent event){
    this.auditEventRecognizer.recognizeAndRecord(event);
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.authn.LoginContextEventListener#fireEvent(com.ibm.siam.am.idp.authn.LoginContextEvent)
   */
  public void fireEvent(LoginContextEvent event) {
    this.auditEventRecognizer.recognizeAndRecord(event);
  }

}
