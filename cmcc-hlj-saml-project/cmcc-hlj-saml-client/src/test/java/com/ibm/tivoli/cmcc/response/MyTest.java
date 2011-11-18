package com.ibm.tivoli.cmcc.response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import com.ibm.tivoli.cmcc.client.ClientException;

import junit.framework.TestCase;

public class MyTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public String submit(String id) throws Exception {
      SocketAddress sockaddr = new InetSocketAddress(InetAddress.getByName("211.137.254.147"), 8080);
      // Create an unbound socket
      Socket socket = new Socket();
      // This method will block no more than timeoutMs.
      // If the timeout occurs, SocketTimeoutException is thrown.
      socket.connect(sockaddr, 60 * 1000);
  
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
      
      String requestXML = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Body><samlp:ActivateRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\" ID=\"ca368a747597461cb4c38976655ca08d\" IssueInstant=\"2010-07-13\nT05:40:11:337Z\" Version=\"2.0\"> <saml:Issuer>http://10.1.249.46:7001/sso/SSO</saml:Issuer> <saml:NameID Format=\"urn:oasis:names:tc:SAML:2.0:nameidformat:transient\">37afc9fa5cf7412!abaccc6bf0d82a68</saml:NameID> </samlp:ActivateRequest></SOAP-ENV:Body></SOAP-ENV:Envelope>";

      writer.write(requestXML);
      writer.flush();
  
      // Get response
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      
      StringBuffer responseXML = new StringBuffer(); 
      String line;
      while ((line = reader.readLine()) != null) {
        responseXML.append(line + "\n");
        if (responseXML.indexOf("</SOAP-ENV:Envelope>") >= 0) {
           // End
           break;
        }
      }
      writer.close();
      reader.close();
      return responseXML.toString();
  }
  
  public void testCase1() throws Exception {
    this.submit("111111");
  }

}
