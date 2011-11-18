package com.ibm.tivoli.cmcc.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseServiceClient {

  private static Log log = LogFactory.getLog(LogoutServiceClientImpl.class);
  
  private String serverName = null;
  private int serverPort = 8080;
  /**
   * In seconds
   */
  private int timeOut = 60;
  private String charset = "UTF-8";
  
  private Properties properties= System.getProperties();

  protected BaseServiceClient() {
    super();
  }

  protected BaseServiceClient(String serverName, int serverPort, Properties properties) {
    super();
    this.serverName = serverName;
    this.serverPort = serverPort;
    this.properties = properties;
  }

  public Properties getProperties() {
    return properties;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public String getServerName() {
    return serverName;
  }

  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  public int getServerPort() {
    return serverPort;
  }

  public void setServerPort(int serverPort) {
    this.serverPort = serverPort;
  }

  public int getTimeOut() {
    return timeOut;
  }

  public void setTimeOut(int timeOut) {
    this.timeOut = timeOut;
  }

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public String submit(String id) throws ClientException {
    try {
      
      SocketAddress sockaddr = new InetSocketAddress(InetAddress.getByName(this.serverName), this.serverPort);
      // Create an unbound socket
      Socket socket = new Socket();
      // This method will block no more than timeoutMs.
      // If the timeout occurs, SocketTimeoutException is thrown.
      socket.connect(sockaddr, timeOut);
  
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), charset));
      
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
          String name = (String)i.next();
          String value = "";
          if (null != map.get(name)) {
             value = map.get(name).toString();
          }
          String macroName = (StringUtils.isEmpty(nameSpace))?"${" + name + "}":"${" + nameSpace + "." + name + "}";
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