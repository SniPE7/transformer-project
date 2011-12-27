package com.ibm.tivoli.cmcc.server.handler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import junit.framework.TestCase;

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;

import com.ibm.tivoli.cmcc.server.SAMLSSLServer;

public class SAMLSSLServerTest extends TestCase {

  public class TLSClientHandler extends IoHandlerAdapter {
    public void sessionCreated(IoSession session) throws Exception {
      System.out.println("[NIO Client]>> sessionCreated");
    }

    public void sessionOpened(IoSession session) throws Exception {
      System.out.println("[NIO Client]>> sessionOpened");
    }

    public void sessionClosed(IoSession session) throws Exception {
      System.out.println("[NIO Client]>> sessionClosed");
    }

    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
      System.out.println("[NIO Client]>> sessionIdle");
    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
      System.out.println("[NIO Client]>> exceptionCaught :");
      cause.printStackTrace();
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
      System.out.println("[NIO Client]>> messageReceived");
      System.out.println("[NIO Client Received]>>" + (String) message);
    }

    public void messageSent(IoSession session, Object message) throws Exception {
      System.out.println("[NIO Client]>> messageSent");
      System.out.println("[NIO Client messageSent]>> : " + (String) message);
    }
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testCaseServer() throws Exception {
    SAMLSSLServer server = new SAMLSSLServer();
    server.start();
    Thread.sleep(10000000);
  }

  public void testCaseSunClientWithTrustCA() throws Exception {
    // Load key store
    char[] passphrase = "importkey".toCharArray();
    KeyStore keystore = KeyStore.getInstance("JKS");
    keystore.load(this.getClass().getResourceAsStream("/certs/server_pwd_importkey.jks"), passphrase);

    // Initialize trust manager factory and set trusted CA list using keystore
    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
    tmf.init(keystore);

    // Get SSL Context and initialize context
    SSLContext context = SSLContext.getInstance("TLS");
    TrustManager[] trustManagers = tmf.getTrustManagers();
    context.init(null, trustManagers, null);

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

  public void testCase() throws Exception {
    // myselfsign_pwd_importkey.jks
    // Load key store
    char[] passphrase = "importkey".toCharArray();
    KeyStore keystore = KeyStore.getInstance("JKS");
    keystore.load(this.getClass().getResourceAsStream("/certs/myselfsign_pwd_importkey.jks"), passphrase);

    // Initialize trust manager factory and set trusted CA list using keystore
    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
    tmf.init(keystore);

    // Get SSL Context and initialize context
    SSLContext context = SSLContext.getInstance("TLS");
    TrustManager[] trustManagers = tmf.getTrustManagers();
    context.init(null, trustManagers, null);

    // Get SSL socket factory
    SocketFactory sf = context.getSocketFactory();

    // Make socket connect with SSL server
    Socket s = sf.createSocket("127.0.0.1", 8443);
    OutputStream out = s.getOutputStream();
    out.write("<SOAP-ENV:Envelope   xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">   <SOAP-ENV:Body>     <samlp:ActivateRequest         xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"         xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\"         ID=\"i14fhcy071acvv8qdquo7nwr0la6d2h8\"         IssueInstant=\"2011-12-01T21:37:40+0800\"         Version=\"2.0\">         <saml:Issuer></saml:Issuer>         <saml:NameID Format=\"urn:oasis:names:tc:SAML:2.0:nameidformat:transient\">3b6ehrwiqnasq7fguybiaoxv87ug3470</saml:NameID>     </samlp:ActivateRequest>   </SOAP-ENV:Body> </SOAP-ENV:Envelope>\n\n"
        .getBytes());

    int theCharacter = 0;
    theCharacter = System.in.read();
    while (theCharacter != '~') // The '~' is an escape character to exit
    {
      out.write(theCharacter);
      out.flush();
      theCharacter = System.in.read();
    }

    out.close();
    s.close();

  }

  public void testAsServer() throws Exception {
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
      InputStream in = this.getClass().getResourceAsStream("/certs/client_pwd_importkey.jks");
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

}
