/**
 * 
 */
package com.sinopec.siam.am.idp.audit;

import com.sinopec.siam.am.idp.authn.LoginContextEvent;
import com.sinopec.siam.am.idp.authn.LoginModuleEvent;


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
