/**
 * 
 */
package com.ibm.tivoli.cmcc.server.handler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import junit.framework.TestCase;

/**
 * @author zhaodonglu
 * 
 */
public class SSLTest extends TestCase {

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testCaseAsClientWithoutPrivateKey() throws Exception {
    TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      public void checkClientTrusted(X509Certificate[] certs, String authType) {
        System.out.println(String.format("Client certs;[%s], authType: [%s]", certs, authType));
      }

      public void checkServerTrusted(X509Certificate[] certs, String authType) {
        System.out.println(String.format("Client certs;[%s], authType: [%s]", certs, authType));
      }
    } };
    // Load key store
    char[] passphrase = "importkey".toCharArray();
    KeyStore keystore = KeyStore.getInstance("JKS");
    keystore.load(this.getClass().getResourceAsStream("/certs/cmcc_ssl.jks"), passphrase);

    // Initialize trust manager factory and set trusted CA list using keystore
    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
    tmf.init(keystore);

    // Get SSL Context and initialize context
    SSLContext context = SSLContext.getInstance("TLS");
    TrustManager[] trustManagers = tmf.getTrustManagers();
    context.init(null, trustAllCerts, null);

    // Get SSL socket factory
    SocketFactory sf = context.getSocketFactory();

    // Make socket connect with SSL server
    Socket s = sf.createSocket("127.0.0.1", 8080);
    // Send first
    OutputStream out = s.getOutputStream();
    out.write("<SOAP-ENV:Envelope   xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">   <SOAP-ENV:Body>     <samlp:ActivateRequest         xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"         xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\"         ID=\"i14fhcy071acvv8qdquo7nwr0la6d2h8\"         IssueInstant=\"2011-12-01T21:37:40+0800\"         Version=\"2.0\">         <saml:Issuer></saml:Issuer>         <saml:NameID Format=\"urn:oasis:names:tc:SAML:2.0:nameidformat:transient\">3b6ehrwiqnasq7fguybiaoxv87ug3470</saml:NameID>     </samlp:ActivateRequest>   </SOAP-ENV:Body> </SOAP-ENV:Envelope>\n\n"
        .getBytes());
    InputStream in = s.getInputStream();
    {
      byte[] buf = new byte[512];
      int len = in.read(buf);
      while (len > 0) {
        System.err.print(new String(buf, 0, len));
        len = in.read(buf);
      }
    }

    // Send second
    out.write("<SOAP-ENV:Envelope   xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">   <SOAP-ENV:Body>     <samlp:ActivateRequest         xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"         xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\"         ID=\"i14fhcy071acvv8qdquo7nwr0la6d2h8\"         IssueInstant=\"2011-12-01T21:37:40+0800\"         Version=\"2.0\">         <saml:Issuer></saml:Issuer>         <saml:NameID Format=\"urn:oasis:names:tc:SAML:2.0:nameidformat:transient\">3b6ehrwiqnasq7fguybiaoxv87ug3470</saml:NameID>     </samlp:ActivateRequest>   </SOAP-ENV:Body> </SOAP-ENV:Envelope>\n\n"
        .getBytes());
    {
      byte[] buf = new byte[512];
      int len = in.read(buf);
      while (len > 0) {
        System.err.print(new String(buf, 0, len));
        len = in.read(buf);
      }
    }

    out.close();
    s.close();
  }

  public void testCaseAsClientWithPrivateKey() throws Exception {
    TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      public void checkClientTrusted(X509Certificate[] certs, String authType) {
        System.out.println(String.format("Client certs;[%s], authType: [%s]", certs, authType));
      }

      public void checkServerTrusted(X509Certificate[] certs, String authType) {
        System.out.println(String.format("Client certs;[%s], authType: [%s]", certs, authType));
      }
    } };
    // Load key store
    char[] passphrase = "importkey".toCharArray();
    KeyStore keystore = KeyStore.getInstance("JKS");
    keystore.load(this.getClass().getResourceAsStream("/certs/client_pwd_importkey.jks"), passphrase);

    // Initialize trust manager factory and set trusted CA list using keystore
    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
    tmf.init(keystore);

    // Get SSL Context and initialize context
    SSLContext context = SSLContext.getInstance("TLS");
    TrustManager[] trustManagers = tmf.getTrustManagers();
    context.init(null, trustAllCerts, null);

    // Get SSL socket factory
    SocketFactory sf = context.getSocketFactory();

    // Make socket connect with SSL server
    Socket s = sf.createSocket("127.0.0.1", 8080);
    // Send first
    OutputStream out = s.getOutputStream();
    out.write("<SOAP-ENV:Envelope   xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">   <SOAP-ENV:Body>     <samlp:ActivateRequest         xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"         xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\"         ID=\"i14fhcy071acvv8qdquo7nwr0la6d2h8\"         IssueInstant=\"2011-12-01T21:37:40+0800\"         Version=\"2.0\">         <saml:Issuer></saml:Issuer>         <saml:NameID Format=\"urn:oasis:names:tc:SAML:2.0:nameidformat:transient\">3b6ehrwiqnasq7fguybiaoxv87ug3470</saml:NameID>     </samlp:ActivateRequest>   </SOAP-ENV:Body> </SOAP-ENV:Envelope>\n\n"
        .getBytes());
    InputStream in = s.getInputStream();
    {
      byte[] buf = new byte[512];
      int len = in.read(buf);
      while (len > 0) {
        System.err.print(new String(buf, 0, len));
        len = in.read(buf);
      }
    }

    // Send second
    out.write("<SOAP-ENV:Envelope   xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">   <SOAP-ENV:Body>     <samlp:ActivateRequest         xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"         xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\"         ID=\"i14fhcy071acvv8qdquo7nwr0la6d2h8\"         IssueInstant=\"2011-12-01T21:37:40+0800\"         Version=\"2.0\">         <saml:Issuer></saml:Issuer>         <saml:NameID Format=\"urn:oasis:names:tc:SAML:2.0:nameidformat:transient\">3b6ehrwiqnasq7fguybiaoxv87ug3470</saml:NameID>     </samlp:ActivateRequest>   </SOAP-ENV:Body> </SOAP-ENV:Envelope>\n\n"
        .getBytes());
    {
      byte[] buf = new byte[512];
      int len = in.read(buf);
      while (len > 0) {
        System.err.print(new String(buf, 0, len));
        len = in.read(buf);
      }
    }

    out.close();
    s.close();
  }

  public void testAsServerWithoutPrivateKey() throws Exception {
    int port = 8080;// 监听端口
    String keyFilePass = "importkey";
    String keyPass = "importkey";
    SSLServerSocket sslsocket = null;
    KeyStore ks;// 密钥库
    KeyManagerFactory kmf;// 密钥管理工厂
    SSLContext sslc = null;// 安全连接方式
    // 初始化安全连接的密钥
    try {
      ks = KeyStore.getInstance("JKS");
      InputStream in = this.getClass().getResourceAsStream("/testcerts/cmcc.jks");
      ks.load(in, keyFilePass.toCharArray());
      kmf = KeyManagerFactory.getInstance("SunX509");
      kmf.init(ks, keyPass.toCharArray());
      sslc = SSLContext.getInstance("SSLv3");
      sslc.init(kmf.getKeyManagers(), null, null);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    SSLServerSocketFactory sslssf = sslc.getServerSocketFactory();
    sslsocket = (SSLServerSocket) sslssf.createServerSocket(port);
    sslsocket.getChannel();
    System.out.println("Listening...");
    SSLSocket ssocket = (SSLSocket) sslsocket.accept();

    // 以下代码同socket通讯实例中的代码
    BufferedReader socketIn = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));
    BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
    PrintStream socketOut = new PrintStream(ssocket.getOutputStream());
    String s;
    while (true) {
      System.out.println("Please wait client 's message..");
      System.out.println("");
      s = socketIn.readLine();
      System.out.println("Client Message: " + s);
      if (s.trim().equals("BYE"))
        break;
      System.out.print("Server Message: ");
      s = userIn.readLine();
      socketOut.println(s);
      if (s.trim().equals("BYE"))
        break;
    }
    socketIn.close();
    socketOut.close();
    userIn.close();
    sslsocket.close();
  }

  public void testCase1() throws Exception {

  }

}
