/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhaodonglu
 *
 */
public class SimpleLoginModuleEventListener implements LoginModuleEventListener, LoginContextEventListener {
  
  private static Log log = LogFactory.getLog(SimpleLoginModuleEventListener.class);

  /**
   * 
   */
  public SimpleLoginModuleEventListener() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.authn.LoginModuleEventListener#fireEvent(com.ibm.siam.am.idp.authn.LoginModuleEvent)
   */
  public void fireEvent(LoginModuleEvent event) {
    log.info(event);
  }

  public void fireEvent(LoginContextEvent event) {
    log.info(event);
  }

}
