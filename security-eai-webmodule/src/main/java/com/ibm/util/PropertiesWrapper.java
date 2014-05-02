/**
 * 
 */
package com.ibm.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author zhaodonglu
 *
 */
public class PropertiesWrapper extends Properties {

  /**
   * 
   */
  private static final long serialVersionUID = -4715916391272849372L;

  /**
   * 
   */
  protected PropertiesWrapper() {
    super();
  }

  /**
   * @param defaults
   */
  protected PropertiesWrapper(Properties defaults) {
    super(defaults);
  }
  
  /**
   * @param defaults
   * @throws IOException 
   */
  protected PropertiesWrapper(MyPropertyPlaceholderConfigurer propertyPlaceholderConfigurer) throws IOException {
    super(propertyPlaceholderConfigurer.getProperties());
  }

}
