package com.ibm.tivoli.cmcc.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.connector.Connector;
import com.ibm.tivoli.cmcc.connector.ConnectorManager;

public abstract class BaseServiceClient {

  private static Log log = LogFactory.getLog(BaseServiceClient.class);

  private String charset = "UTF-8";

  private ConnectorManager networkConnectorManager;
  private Properties properties = new Properties();


  protected BaseServiceClient() {
    super();
  }

  protected BaseServiceClient(ConnectorManager networkConnectorManager, Properties properties) {
    super();
    this.networkConnectorManager = networkConnectorManager;
  }

  /**
   * @return the networkConnectorManager
   */
  public ConnectorManager getConnectorManager() {
    return networkConnectorManager;
  }

  /**
   * @param networkConnectorManager the networkConnectorManager to set
   */
  public void setConnectorManager(ConnectorManager networkConnectorManager) {
    this.networkConnectorManager = networkConnectorManager;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#getCharset()
   */
  public String getCharset() {
    return charset;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#setCharset(java.lang.String)
   */
  public void setCharset(String charset) {
    this.charset = charset;
  }

  /**
   * @return the properties
   */
  public Properties getProperties() {
    return properties;
  }

  /**
   * @param properties the properties to set
   */
  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public String submit(String id) throws ClientException {
    Connector connector = null;
    try {
      connector = this.networkConnectorManager.getConnector();
      connector.open();
      OutputStream out = connector.getOutput();
      InputStream in = connector.getInput();
      
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, charset));

      Object request = doBusiness(id);

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

  protected String getTemplate(String templateFile) throws IOException {
    InputStream ins = null;
    if (templateFile.toLowerCase().startsWith("classpath:")) {
      ins = this.getClass().getResourceAsStream(templateFile.substring("classpath:".length()));
    } else {
      String file = templateFile;
      if (templateFile.toLowerCase().startsWith("file:")) {
        file = templateFile.substring("file:".length());
      }
      ins = new FileInputStream(file);
    }
    BufferedReader template = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
    StringWriter out = new StringWriter();
    String line = template.readLine();
    while (line != null) {
      out.write(line + "\n");
      line = template.readLine();
    }
    String result = out.toString();
    return result;
  }

  private String evaluateTemplate(String template, String nameSpace, Map map) {
    Set names = map.keySet();
    Iterator i = names.iterator();
    while (i.hasNext()) {
      String name = (String) i.next();
      String value = "";
      if (null != map.get(name)) {
        value = map.get(name).toString();
      }
      String macroName = (StringUtils.isEmpty(nameSpace)) ? "${" + name + "}" : "${" + nameSpace + "." + name + "}";
      template = StringUtils.replace(template, macroName, value);
    }
    return template;
  }

  protected String generateRequestXML(Object request) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    String template = this.getTemplate(this.getTemplateFile());
    String result = this.evaluateTemplate(template, "", BeanUtils.describe(request));
    return result;
  }

  protected abstract String getTemplateFile() throws IOException;

  public abstract Object doBusiness(String id) throws ClientException;

}