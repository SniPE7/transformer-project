package com.ibm.tivoli.cmcc.response;

import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.TestCase;

import com.ibm.tivoli.cmcc.connector.Connector;
import com.ibm.tivoli.cmcc.connector.NetworkConnectorManager;
import com.ibm.tivoli.cmcc.connector.SimpleNetworkConnectorImpl;

public class NetworkConnectorManagerTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testTCPTimeout() throws Exception {
    NetworkConnectorManager mgr = new NetworkConnectorManager();
    mgr.setConnectorClassName(SimpleNetworkConnectorImpl.class.getName());
    mgr.setProtocol("TCP");
    mgr.setServerName("192.168.2.89");
    mgr.setServerPort(8080);
    mgr.setTimeOut(10);
    
    Connector con = mgr.getConnector();
    long begin = System.currentTimeMillis();
    try {
      con.open();
    } catch (Exception e) {
    }
    assertTrue(System.currentTimeMillis() - begin <= 12 * 1000);
  }

  public void testSSLTimeout() throws Exception {
    NetworkConnectorManager mgr = new NetworkConnectorManager();
    mgr.setConnectorClassName(SimpleNetworkConnectorImpl.class.getName());
    mgr.setProtocol("SSL");
    mgr.setServerName("192.168.2.89");
    mgr.setServerPort(8081);
    mgr.setTimeOut(10);
    
    Connector con = mgr.getConnector();
    long begin = System.currentTimeMillis();
    try {
      con.open();
    } catch (Exception e) {
    }
    assertTrue(System.currentTimeMillis() - begin <= 12 * 1000);
  }

  public void testSSLTimeout2() throws Exception {
    NetworkConnectorManager mgr = new NetworkConnectorManager();
    mgr.setConnectorClassName(SimpleNetworkConnectorImpl.class.getName());
    mgr.setProtocol("SSL");
    mgr.setServerName("127.0.0.1");
    mgr.setServerPort(8083);
    mgr.setTimeOut(10);
    
    Connector con = mgr.getConnector();
    long begin = System.currentTimeMillis();
    try {
      con.open();
      InputStream in = con.getInput();
      OutputStream out = con.getOutput();
      out.write("hello".getBytes());
      out.flush();
      int b = in.read();
      in.close();
      out.close();
      con.release();
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertTrue(System.currentTimeMillis() - begin <= 12 * 1000);
  }

}
