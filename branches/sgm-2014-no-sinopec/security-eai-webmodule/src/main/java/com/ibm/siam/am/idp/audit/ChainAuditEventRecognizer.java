/**
 * 
 */
package com.ibm.siam.am.idp.audit;

import java.util.ArrayList;
import java.util.List;

import com.ibm.siam.am.idp.authn.LoginContextEvent;
import com.ibm.siam.am.idp.authn.LoginModuleEvent;

/**
 * @author zhaodonglu
 *
 */
public class ChainAuditEventRecognizer implements AuditEventRecognizer {
  private List<AuditEventRecognizer> recognizers = new ArrayList<AuditEventRecognizer>();

  /**
   * 
   */
  public ChainAuditEventRecognizer() {
    super();
  }

  public ChainAuditEventRecognizer(List<AuditEventRecognizer> recognizers) {
    super();
    this.recognizers = recognizers;
  }

  public List<AuditEventRecognizer> getRecognizers() {
    return recognizers;
  }

  public void setRecognizers(List<AuditEventRecognizer> recognizers) {
    this.recognizers = recognizers;
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.audit.AuditEventRecognizer#recognizeAndRecord(com.ibm.siam.am.idp.authn.LoginModuleEvent)
   */
  public void recognizeAndRecord(LoginModuleEvent event) {
    for (AuditEventRecognizer recognizer: this.getRecognizers()) {
        recognizer.recognizeAndRecord(event);
    }
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.audit.AuditEventRecognizer#recognizeAndRecord(com.ibm.siam.am.idp.authn.LoginContextEvent)
   */
  public void recognizeAndRecord(LoginContextEvent event) {
    for (AuditEventRecognizer recognizer: this.getRecognizers()) {
        recognizer.recognizeAndRecord(event);
    }
  }

}
