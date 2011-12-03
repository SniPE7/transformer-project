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
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseServiceClient {

  private static Log log = LogFactory.getLog(LogoutServiceClientImpl.class);

  /**
   * Server certificate keystore file name.
   */
  private String keyStorePath = "/certs/client_pwd_importkey.jks";

  private char[] storePassword = "importkey".toCharArray();

  private char[] keyPassword = "importkey".toCharArray();

  private String keyManagerAlgorithm;

  private String protocol = "TCP";
  private String serverName = null;
  private int serverPort = 8080;

  /**
   * In seconds
   */
  private int timeOut = 60;
  private String charset = "UTF-8";

  private Properties properties = System.getProperties();


  protected BaseServiceClient() {
    super();
  }

  protected BaseServiceClient(String serverName, int serverPort, Properties properties) {
    super();
    this.serverName = serverName;
    this.serverPort = serverPort;
    this.properties = properties;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#getProperties()
   */
  public Properties getProperties() {
    return properties;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#setProperties(java.util.Properties)
   */
  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#getServerName()
   */
  public String getServerName() {
    return serverName;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#setServerName(java.lang.String)
   */
  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#getServerPort()
   */
  public int getServerPort() {
    return serverPort;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#setServerPort(int)
   */
  public void setServerPort(int serverPort) {
    this.serverPort = serverPort;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#getTimeOut()
   */
  public int getTimeOut() {
    return timeOut;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#setTimeOut(int)
   */
  public void setTimeOut(int timeOut) {
    this.timeOut = timeOut;
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

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#getKeyStorePath()
   */
  public String getKeyStorePath() {
    return keyStorePath;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#setKeyStorePath(java.lang.String)
   */
  public void setKeyStorePath(String keyStorePath) {
    this.keyStorePath = keyStorePath;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#getStorePassword()
   */
  public char[] getStorePassword() {
    return storePassword;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#setStorePassword(char[])
   */
  public void setStorePassword(char[] storePassword) {
    this.storePassword = storePassword;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#getKeyPassword()
   */
  public char[] getKeyPassword() {
    return keyPassword;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#setKeyPassword(char[])
   */
  public void setKeyPassword(char[] keyPassword) {
    this.keyPassword = keyPassword;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#getKeyManagerAlgorithm()
   */
  public String getKeyManagerAlgorithm() {
    return keyManagerAlgorithm;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#setKeyManagerAlgorithm(java.lang.String)
   */
  public void setKeyManagerAlgorithm(String keyManagerAlgorithm) {
    this.keyManagerAlgorithm = keyManagerAlgorithm;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#getProtocol()
   */
  public String getProtocol() {
    return protocol;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.client.Client#setProtocol(java.lang.String)
   */
  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String submit(String id) throws ClientException {
    try {
      // Create a socket with server
      Socket socket = getSocket();
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
    }

  }

  /**
   * @return
   * @throws UnknownHostException
   * @throws IOException
   * @throws KeyStoreException 
   * @throws CertificateException 
   * @throws NoSuchAlgorithmException 
   * @throws KeyManagementException 
   */
  private Socket getSocket() throws UnknownHostException, IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException, KeyManagementException {
    if (StringUtils.isEmpty(protocol) || protocol.equalsIgnoreCase("TCP")) {
      return getTCPSocket();
    } else if (protocol.equalsIgnoreCase("TLS") || protocol.equalsIgnoreCase("SSL")) {
      return getSSLTLSSocket();
    } else {
      throw new RuntimeException("Unkonw protocol type: " + this.protocol);
    }
  }

  /**
   * @return
   * @throws KeyStoreException
   * @throws IOException
   * @throws NoSuchAlgorithmException
   * @throws CertificateException
   * @throws KeyManagementException
   * @throws UnknownHostException
   */
  private Socket getSSLTLSSocket() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, KeyManagementException,
      UnknownHostException {
    // Load key store
    KeyStore keystore = KeyStore.getInstance("JKS");
    InputStream in = this.getClass().getResourceAsStream(this.keyStorePath);
    if (in == null) {
       throw new IOException(String.format("Could not reading from : [%s]", this.keyStorePath));
    }
    keystore.load(in, this.storePassword);

    // Initialize trust manager factory and set trusted CA list using keystore
    if (System.getProperty("java.vm.vendor", "").startsWith("IBM")) {
      this.keyManagerAlgorithm = "IbmX509";
    } else {
      this.keyManagerAlgorithm = "SunX509";
    }
    TrustManagerFactory tmf = TrustManagerFactory.getInstance(this.keyManagerAlgorithm);
    tmf.init(keystore);

    // Get SSL Context and initialize context
    SSLContext context = SSLContext.getInstance(this.protocol);
    TrustManager[] trustManagers = tmf.getTrustManagers();
    context.init(null, trustManagers, null);

    // Get SSL socket factory
    SocketFactory sf = context.getSocketFactory();

    // Make socket connect with SSL server
    Socket s = sf.createSocket(InetAddress.getByName(this.serverName), this.serverPort);
    return s;
  }

  /**
   * @return
   * @throws UnknownHostException
   * @throws IOException
   */
  private Socket getTCPSocket() throws UnknownHostException, IOException {
    SocketAddress sockaddr = new InetSocketAddress(InetAddress.getByName(this.serverName), this.serverPort);
    // Create an unbound socket
    Socket socket = new Socket();
    // This method will block no more than timeoutMs.
    // If the timeout occurs, SocketTimeoutException is thrown.
    socket.connect(sockaddr, timeOut);
    return socket;
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