/**
 * 
 */
package com.ibm.tivoli.cmcc.sslproxy;

import junit.framework.TestCase;

/**
 * @author zhaodonglu
 * 
 */
public class SSLProxyServerTest extends TestCase {

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  public void setUp() throws Exception {
    super.setUp();
  }

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#tearDown()
   */
  public void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link com.ibm.tivoli.cmcc.sslproxy.SSLProxyServer#run()}.
   */
  public void testSSL2SSLProxy() throws Exception {
    SSLProxyServer tcpSSLServer = new SSLProxyServer();
    tcpSSLServer.setTargetIP("10.110.21.58");
    tcpSSLServer.setTargetPort(8443);
    tcpSSLServer.setServerPort(8443);

    tcpSSLServer.setServerProtocol("SSL");
    tcpSSLServer.setTargetProtocol("SSL");
    tcpSSLServer.setKeyStore("/certs/client_pwd_importkey.jks");
    tcpSSLServer.setKeyFilePass("importkey");
    tcpSSLServer.setKeyFilePass("importkey");

    Thread t2 = new Thread(tcpSSLServer);
    t2.start();

    Thread.sleep(10000000);
  }

  /**
   * Test method for {@link com.ibm.tivoli.cmcc.sslproxy.SSLProxyServer#run()}.
   */
  public void testSSL2TCPProxy() throws Exception {
    SSLProxyServer tcpSSLServer = new SSLProxyServer();
    tcpSSLServer.setTargetIP("10.110.252.210");
    tcpSSLServer.setTargetPort(8082);
    tcpSSLServer.setServerPort(18080);

    tcpSSLServer.setServerProtocol("TCP");
    tcpSSLServer.setTargetProtocol("SSL");
    tcpSSLServer.setKeyStore("/certs/client_pwd_importkey.jks");
    tcpSSLServer.setKeyFilePass("importkey");
    tcpSSLServer.setKeyFilePass("importkey");

    Thread t2 = new Thread(tcpSSLServer);
    t2.start();

    Thread.sleep(10000000);
  }

  /**
   * Test method for {@link com.ibm.tivoli.cmcc.sslproxy.SSLProxyServer#run()}.
   */
  public void testTCP2TCPProxy() throws Exception {
    SSLProxyServer tcpSSLServer = new SSLProxyServer();
    tcpSSLServer.setTargetIP("10.110.21.58");
    tcpSSLServer.setTargetPort(8081);
    tcpSSLServer.setServerPort(8081);

    tcpSSLServer.setServerProtocol("TCP");
    tcpSSLServer.setTargetProtocol("TCP");
    tcpSSLServer.setKeyStore("/certs/client_pwd_importkey.jks");
    tcpSSLServer.setKeyFilePass("importkey");
    tcpSSLServer.setKeyFilePass("importkey");

    Thread t2 = new Thread(tcpSSLServer);
    t2.start();

    Thread.sleep(10000000);
  }

  /**
   * Test method for {@link com.ibm.tivoli.cmcc.sslproxy.SSLProxyServer#run()}.
   */
  public void testTCP2SSLProxy() throws Exception {
    SSLProxyServer tcpSSLServer = new SSLProxyServer();
    tcpSSLServer.setTargetIP("hl1.ac.10086.cn");
    tcpSSLServer.setTargetPort(8081);
    tcpSSLServer.setTargetProtocol("SSL");

    tcpSSLServer.setServerPort(8082);
    tcpSSLServer.setServerProtocol("TCP");
    //tcpSSLServer.setKeyStore("/certs/ssl_channel.jks");
    //tcpSSLServer.setKeyFilePass("importkey");
    //tcpSSLServer.setKeyFilePass("importkey");

    Thread t2 = new Thread(tcpSSLServer);
    t2.start();

    Thread.sleep(10000000);
  }

  /**
   * Test method for {@link com.ibm.tivoli.cmcc.sslproxy.SSLProxyServer#run()}.
   */
  public void testSSLChannel() throws Exception {
    String diameterServer = "10.110.21.58";
    int diameterPort = 8081;
    int servicePort = 8081;

    int internalChannelPort = 18443;

    SSLProxyServer sslTCPServer = new SSLProxyServer();
    sslTCPServer.setTargetIP(diameterServer);
    sslTCPServer.setTargetPort(diameterPort);
    sslTCPServer.setServerPort(internalChannelPort);

    sslTCPServer.setServerProtocol("SSL");
    sslTCPServer.setTargetProtocol("TCP");
    sslTCPServer.setKeyStore("/certs/client_pwd_importkey.jks");
    sslTCPServer.setKeyFilePass("importkey");
    sslTCPServer.setKeyFilePass("importkey");

    SSLProxyServer tcpSSLServer = new SSLProxyServer();
    tcpSSLServer.setTargetIP("127.0.0.1");
    tcpSSLServer.setTargetPort(internalChannelPort);
    tcpSSLServer.setServerPort(servicePort);

    tcpSSLServer.setServerProtocol("TCP");
    tcpSSLServer.setTargetProtocol("SSL");
    tcpSSLServer.setKeyStore("/certs/client_pwd_importkey.jks");
    tcpSSLServer.setKeyFilePass("importkey");
    tcpSSLServer.setKeyFilePass("importkey");

    Thread t1 = new Thread(sslTCPServer);
    t1.start();
    Thread t2 = new Thread(tcpSSLServer);
    t2.start();

    Thread.sleep(10000000);
  }

}
