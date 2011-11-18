/**
 * 
 */
package com.ibm.tivoli.cmcc.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Zhao Dong Lu
 * 
 */
public class Bootstrap {

  /**
   * 
   */
  public Bootstrap() {
    super();
  }

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {

    ClassPathXmlApplicationContext factory = new ClassPathXmlApplicationContext(new String[] { "/com/ibm/tivoli/cmcc/server/spring/mainBean.xml" });
    SAMLSocketServer server = (SAMLSocketServer) factory.getBean("samlServer");
    server.start();
  }

}
