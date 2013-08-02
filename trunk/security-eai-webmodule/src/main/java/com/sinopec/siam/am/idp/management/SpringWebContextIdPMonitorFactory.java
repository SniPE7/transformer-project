package com.sinopec.siam.am.idp.management;

import org.springframework.web.context.ContextLoader;

/**
 * PersonServiceFactory Spring  µœ÷¿‡°£
 * 
 * @author Booker
 * 
 */
public class SpringWebContextIdPMonitorFactory implements IdPMonitorFactory {

  /*
   * (non-Javadoc)
   * 
   * @see com.sinopec.siam.am.idp.management.IdPMonitorFactory#getIdPMonitor()
   */
  public IdPMonitor getIdPMonitor() {
    IdPMonitor monitor = ContextLoader.getCurrentWebApplicationContext().getBean("jmxIdPMonitorBean", IdPMonitor.class);
    return monitor;
  }

}
