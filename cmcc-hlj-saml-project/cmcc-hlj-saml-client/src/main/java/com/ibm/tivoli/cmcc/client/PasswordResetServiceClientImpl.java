/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.request.LogoutRequest;
import com.ibm.tivoli.cmcc.request.PasswordResetRequest;
import com.ibm.tivoli.cmcc.server.utils.Helper;

/**
 * @author Zhao Dong Lu
 * 
 */
public class PasswordResetServiceClientImpl extends BaseServiceClient implements PasswordResetClient {
  
  private static Log log = LogFactory.getLog(PasswordResetServiceClientImpl.class);
  /**
   * 
   */
  public PasswordResetServiceClientImpl() {
    super();
  }

  public PasswordResetServiceClientImpl(String serverName, int serverPort, Properties properties) {
    super(serverName, serverPort, properties);
  }

  public Object doBusiness(String id) throws ClientException {
    LogoutRequest request = new LogoutRequest();
    request.setSamlId(Helper.generatorID());
    request.setNameId(id);
    request.setSamlIssuer(this.getProperties().getProperty("message.saml.issuer"));
    request.setIssueInstant(Helper.formatDate4SAML(new Date()));
    return request;
  }

  protected String getTemplateFile() throws IOException {
    return this.getProperties().getProperty("messsage.template.PasswordReset.request", "classpath:/template/samlp.PasswordResetRequest.template.xml");
  }

  public String submit(String userName, String serviceCode, String networkPassword) throws ClientException {
    try {
      
      SocketAddress sockaddr = new InetSocketAddress(InetAddress.getByName(this.getServerName()), this.getServerPort());
      // Create an unbound socket
      Socket socket = new Socket();
      // This method will block no more than timeoutMs.
      // If the timeout occurs, SocketTimeoutException is thrown.
      socket.connect(sockaddr, this.getTimeOut());
  
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), this.getCharset()));
      
      Object request = doBusiness(userName, serviceCode, networkPassword);
      
      String requestXML = generateRequestXML(request);
      if (log.isDebugEnabled()) {
         log.debug("Sending request XML: " + requestXML);
      }
      
      // Trim cr cl
      requestXML = requestXML.replace('\n', ' ');
      requestXML = requestXML.replace('\r', ' ');
      
      writer.write(requestXML);
      writer.flush();
  
      // Get response
      InputStream in = socket.getInputStream();
      byte[] buf = new byte[2048];
      
      ByteArrayOutputStream responseXML = new ByteArrayOutputStream();
      
      int len = in.read(buf);
      while (len > 0) {
        responseXML.write(buf, 0, len);
        if (new String(responseXML.toByteArray()).indexOf("</SOAP-ENV:Envelope>") >= 0) {
           // End
           break;
        }
        len = in.read(buf);
      }
      writer.close();
      in.close();
      return responseXML.toString();
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage(), e);
      throw new ClientException(e);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new ClientException(e);
    } catch (IllegalAccessException e) {
      log.error(e.getMessage(), e);
      throw new ClientException(e);
    } catch (InvocationTargetException e) {
      log.error(e.getMessage(), e);
      throw new ClientException(e);
    } catch (NoSuchMethodException e) {
      log.error(e.getMessage(), e);
      throw new ClientException(e);
    }

  }

  private Object doBusiness(String userName, String serviceCode, String networkPassword) {
    PasswordResetRequest req = new PasswordResetRequest(userName, serviceCode, networkPassword);
    return req;
  }

}
