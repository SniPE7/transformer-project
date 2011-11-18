package com.ibm.tivoli.cmcc.handler;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class AbstractProcessor {

  private Properties properties = System.getProperties();
  private ApplicationContext applicationContext;

  public AbstractProcessor() {
    super();
  }

  public AbstractProcessor(Properties properties) {
    super();
    this.properties = properties;
  }

  public Properties getProperties() {
    return properties;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }
  
  
}