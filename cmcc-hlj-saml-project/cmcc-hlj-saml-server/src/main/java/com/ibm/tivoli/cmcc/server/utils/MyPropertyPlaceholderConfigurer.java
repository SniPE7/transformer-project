/**
 * 
 */
package com.ibm.tivoli.cmcc.server.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author Zhao Dong Lu
 * 
 */
public class MyPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

  /**
   * 
   */
  public MyPropertyPlaceholderConfigurer() {
    super();
  }

  public Properties getProperties() throws IOException {
    return this.mergeProperties();
  }

}
