/**
 * 
 */
package com.ibm.siam.am.idp.audit;

import com.ibm.siam.am.idp.authn.LoginContextEvent;
import com.ibm.siam.am.idp.authn.LoginModuleEvent;


/**
 * @author Administrator
 *
 */
public interface AuditEventRecognizer {
  /**
   * @param event
   */
  public void recognizeAndRecord(LoginModuleEvent event);
  /**
   * @param event
   */
  public void recognizeAndRecord(LoginContextEvent event);
}
