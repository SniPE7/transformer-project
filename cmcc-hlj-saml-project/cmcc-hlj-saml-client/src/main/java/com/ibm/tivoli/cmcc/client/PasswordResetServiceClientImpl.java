/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.connector.Connector;
import com.ibm.tivoli.cmcc.request.PasswordResetRequest;

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

  public PasswordResetServiceClientImpl(Properties properties) {
    super(properties);
  }


  protected String getTemplateFile() throws IOException {
    return this.getProperties().getProperty("messsage.template.PasswordReset.request", "classpath:/template/samlp.PasswordResetRequest.template.xml");
  }

  public String submit(Connector connector, String userName, String serviceCode, String networkPassword) throws ClientException {
    try {
      connector.open();
      OutputStream out = connector.getOutput();
      InputStream in = connector.getInput();
      
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, this.getCharset()));
      
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
    } catch (KeyManagementException e) {
      log.error(e.getMessage(), e);
      throw new ClientException(e);
    } catch (KeyStoreException e) {
      log.error(e.getMessage(), e);
      throw new ClientException(e);
    } catch (NoSuchAlgorithmException e) {
      log.error(e.getMessage(), e);
      throw new ClientException(e);
    } catch (CertificateException e) {
      log.error(e.getMessage(), e);
      throw new ClientException(e);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ClientException(e);
    } finally {
      if (connector != null) {
         connector.release();
      }
    }
  }

  private Object doBusiness(String userName, String serviceCode, String networkPassword) {
    PasswordResetRequest req = new PasswordResetRequest(userName, serviceCode, networkPassword);
    return req;
  }

  @Override
  public Object doBusiness(String id) throws ClientException {
    return null;
  }

}
