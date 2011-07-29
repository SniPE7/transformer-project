/**
 * 
 */
package com.ibm.tivoli.pim.service;

import com.ibm.itim.apps.PlatformContext;

/**
 * @author Administrator
 *
 */
public interface PlatformContextAware {
  
  public void setPlatformContext(PlatformContext platform);
}
