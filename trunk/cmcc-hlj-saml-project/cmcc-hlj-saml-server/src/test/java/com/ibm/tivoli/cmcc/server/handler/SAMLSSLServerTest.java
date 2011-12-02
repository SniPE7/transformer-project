package com.ibm.tivoli.cmcc.server.handler;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
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
    char[] passphrase = "importkey".toCharArray();
    KeyStore keystore = KeyStore.getInstance("JKS");
    keystore.load(this.getClass().getResourceAsStream("/certs/server_pwd_importkey.jks"), passphrase);

    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
    tmf.init(keystore);

    SSLContext context = SSLContext.getInstance("SSL");
    TrustManager[] trustManagers = tmf.getTrustManagers();

    context.init(null, trustManagers, null);

    SSLSocketFactory sf = context.getSocketFactory();

    Socket s = sf.createSocket("127.0.0.1", 8443);
    OutputStream out = s.getOutputStream();
    out.write("<SOAP-ENV:Envelope   xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">   <SOAP-ENV:Body>     <samlp:ActivateRequest         xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"         xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\"         ID=\"i14fhcy071acvv8qdquo7nwr0la6d2h8\"         IssueInstant=\"2011-12-01T21:37:40+0800\"         Version=\"2.0\">         <saml:Issuer></saml:Issuer>         <saml:NameID Format=\"urn:oasis:names:tc:SAML:2.0:nameidformat:transient\">3b6ehrwiqnasq7fguybiaoxv87ug3470</saml:NameID>     </samlp:ActivateRequest>   </SOAP-ENV:Body> </SOAP-ENV:Envelope>\n\n".getBytes());

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

}
