/**
 * 
 */
package com.ibm.tivoli.cmcc.connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.util.Helper;

/**
 * 目前无需配置keyStore相关参数, 也不支持keyStore相关参数.<br/>
 * 而trustStore可以配置服务器端的签发证书, 当配置trustCertStorePath参数后,则本客户端将验证服务器证书, 如果此参数不设置, 则不验证服务器端证书.
 * @author zhaodonglu
 * 
 */
public class SimpleNetworkConnectorImpl implements Connector {

  private static Log log = LogFactory.getLog(SimpleNetworkConnectorImpl.class);
  /**
   * Trust certificate store path
   */
  private String trustCertsStorePath = "/certs/client_pwd_importkey.jks";

  /**
   * Trust certificate store password
   */
  private char[] trustCertsStorePassword = "importkey".toCharArray();

  /**
   * Key store path
   */
  private String keyStorePath = "/certs/client_pwd_importkey.jks";

  /**
   * Key store password
   */
  private char[] keyStorePassword = "importkey".toCharArray();
  
  /**
   * Key password
   */
  private char[] keyStoreKeyPassword = "importkey".toCharArray();
  
  

  /**
   * Not need to set.
   */
  private String keyManagerAlgorithm = null;

  /**
   * Network protocol
   */
  private String protocol = "TCP";

  private String serverName = null;
  private int serverPort = 8080;

  private Socket socket = null;

  /**
   * In seconds
   */
  private int timeOut = 10;

  /**
   * 
   */
  public SimpleNetworkConnectorImpl() {
    super();
  }

  /**
   * @return the trustCertsStorePath
   */
  public String getTrustCertsStorePath() {
    return trustCertsStorePath;
  }

  /**
   * @param trustCertsStorePath
   *          the trustCertsStorePath to set
   */
  public void setTrustCertsStorePath(String keyStorePath) {
    this.trustCertsStorePath = keyStorePath;
  }

  /**
   * @return the trustCertsStorePassword
   */
  public char[] getTrustCertsStorePassword() {
    return trustCertsStorePassword;
  }

  /**
   * @param trustCertsStorePassword
   *          the trustCertsStorePassword to set
   */
  public void setTrustCertsStorePassword(char[] storePassword) {
    this.trustCertsStorePassword = storePassword;
  }

  /**
   * @return the keyManagerAlgorithm
   */
  public String getKeyManagerAlgorithm() {
    if (StringUtils.isEmpty(this.keyManagerAlgorithm)) {
      if (System.getProperty("java.vm.vendor", "").startsWith("IBM")) {
        this.keyManagerAlgorithm = "IbmX509";
      } else {
        this.keyManagerAlgorithm = "SunX509";
      }
    }
    return this.keyManagerAlgorithm;
  }

  /**
   * @param keyManagerAlgorithm
   *          the keyManagerAlgorithm to set
   */
  public void setKeyManagerAlgorithm(String keyManagerAlgorithm) {
    this.keyManagerAlgorithm = keyManagerAlgorithm;
  }

  /**
   * @return the protocol
   */
  public String getProtocol() {
    return protocol;
  }

  /**
   * @param protocol
   *          the protocol to set
   */
  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  /**
   * @return the serverName
   */
  public String getServerName() {
    return serverName;
  }

  /**
   * @param serverName
   *          the serverName to set
   */
  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  /**
   * @return the serverPort
   */
  public int getServerPort() {
    return serverPort;
  }

  /**
   * @param serverPort
   *          the serverPort to set
   */
  public void setServerPort(int serverPort) {
    this.serverPort = serverPort;
  }

  /**
   * @return the timeOut
   */
  public int getTimeOut() {
    return timeOut;
  }

  /**
   * @param timeOut
   *          the timeOut to set
   */
  public void setTimeOut(int timeOut) {
    this.timeOut = timeOut;
  }

  /**
   * @return the keyStorePath
   */
  public String getKeyStorePath() {
    return keyStorePath;
  }

  /**
   * @param keyStorePath the keyStorePath to set
   */
  public void setKeyStorePath(String keyStorePath) {
    this.keyStorePath = keyStorePath;
  }

  /**
   * @return the keyStorePassword
   */
  public char[] getKeyStorePassword() {
    return keyStorePassword;
  }

  /**
   * @param keyStorePassword the keyStorePassword to set
   */
  public void setKeyStorePassword(char[] keyStorePassword) {
    this.keyStorePassword = keyStorePassword;
  }

  /**
   * @return the keyStoreKeyPassword
   */
  public char[] getKeyStoreKeyPassword() {
    return keyStoreKeyPassword;
  }

  /**
   * @param keyStoreKeyPassword the keyStoreKeyPassword to set
   */
  public void setKeyStoreKeyPassword(char[] keyStoreKeyPassword) {
    this.keyStoreKeyPassword = keyStoreKeyPassword;
  }

  /**
   * @param socket
   *          the socket to set
   */
  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  /**
   * @throws UnknownHostException
   * @throws IOException
   * @throws KeyStoreException
   * @throws NoSuchAlgorithmException
   * @throws CertificateException
   * @throws KeyManagementException
   */
  public void open() throws Exception {
    // Create a socket with server
    try {
      socket = getSocket();
    } catch (Exception e) {
      log.error(String.format("Failure to create a socket to: [%s, %s, %s]", this.protocol, this.serverName, this.serverPort));
      throw e;
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
   * @throws UnrecoverableKeyException
   */
  private Socket getSocket() throws UnknownHostException, IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
      KeyManagementException, UnrecoverableKeyException {
    log.info(String.format("Creating a socket to: [%s, %s, %s]", this.protocol, this.serverName, this.serverPort));
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
   * @throws UnrecoverableKeyException
   */
  private Socket getSSLTLSSocket() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, KeyManagementException,
      UnknownHostException, UnrecoverableKeyException {
    TrustManager[] trustManagers = initTrustManagers();

    // Get SSL Context and initialize context
    SSLContext context = SSLContext.getInstance(this.protocol);
    KeyManager[] keyManagers = initKeyManagers();

    context.init(keyManagers, trustManagers, null);

    // Get SSL socket factory
    SSLSocketFactory sf = context.getSocketFactory();

    // Make socket connect with SSL server
    Socket s = sf.createSocket();
    SocketAddress sockaddr = new InetSocketAddress(InetAddress.getByName(this.serverName), this.serverPort);
    s.connect(sockaddr, this.timeOut * 1000);
    return s;
  }

  /**
   * @return
   * @throws NoSuchAlgorithmException
   * @throws KeyStoreException
   * @throws UnrecoverableKeyException
   * @throws IOException 
   * @throws CertificateException 
   */
  private KeyManager[] initKeyManagers() throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, IOException, CertificateException {
    // Initialize trust manager factory and set trusted CA list using keystore
    if (StringUtils.isEmpty(this.keyStorePath)) {
       log.info("Unset [keyStorePath] parameter, disable local private and certificate.");
       return null;
    } else {
      log.info(String.format("Loading private key and certificate from store: [%s]", this.keyStorePath));
      // Load key store
      KeyStore keystore = KeyStore.getInstance("JKS");
      InputStream in = Helper.getResourceAsStream(this.getClass(), this.keyStorePath);
      if (in == null) {
        throw new IOException(String.format("Could not reading from : [%s]", this.trustCertsStorePath));
      }
      keystore.load(in, this.keyStorePassword);

      KeyManagerFactory kmf = KeyManagerFactory.getInstance(this.getKeyManagerAlgorithm());
      kmf.init(keystore, this.keyStoreKeyPassword);
      log.info(String.format("Initialized key store: [%s]", this.keyStorePath));
      
      KeyManager[] keyManagers = kmf.getKeyManagers();
      return keyManagers;
    }
  }

  /**
   * @return
   * @throws KeyStoreException
   * @throws IOException
   * @throws NoSuchAlgorithmException
   * @throws CertificateException
   */
  private TrustManager[] initTrustManagers() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
    // Initialize trust manager factory and set trusted CA list using keystore
    if (StringUtils.isEmpty(this.trustCertsStorePath)) {
      // Unset trustCertsStorePath, disable trust manager
      log.info("Unset [trustCertsStorePath] parameter, disable TrustManager");
      TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
          return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
          log.debug(String.format("Client certs;[%s], authType: [%s]", certs, authType));
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
          log.debug(String.format("Client certs;[%s], authType: [%s]", certs, authType));
        }
      } };
      return trustAllCerts;
    } else {
      log.info(String.format("Loading trust certs from store: [%s]", this.trustCertsStorePath));
      // Load key store
      KeyStore keystore = KeyStore.getInstance("JKS");
      InputStream in = Helper.getResourceAsStream(this.getClass(), this.trustCertsStorePath);
      if (in == null) {
        throw new IOException(String.format("Could not reading from : [%s]", this.trustCertsStorePath));
      }
      keystore.load(in, this.trustCertsStorePassword);

      TrustManagerFactory tmf = TrustManagerFactory.getInstance(this.getKeyManagerAlgorithm());
      tmf.init(keystore);
      TrustManager[] trustManagers = tmf.getTrustManagers();
      return trustManagers;
    }
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
    socket.connect(sockaddr, timeOut * 1000);
    return socket;
  }

  /**
   * 
   */
  public void release() {
    if (socket != null && socket.isConnected()) {
      try {
        socket.close();
      } catch (IOException e) {
        log.error("Failure to close connection.", e);
      }
    }
  }

  /**
   * @return
   * @throws IOException
   */
  public InputStream getInput() throws IOException {
    InputStream in = socket.getInputStream();
    return in;
  }

  /**
   * @return
   * @throws IOException
   */
  public OutputStream getOutput() throws IOException {
    OutputStream out = socket.getOutputStream();
    return out;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int maxLen = 100;
    return String
        .format(
            "SimpleNetworkConnectorImpl [protocol=%s, serverName=%s, serverPort=%s, trustCertsStorePath=%s, trustCertsStorePassword=%s, keyStorePath=%s, keyStorePassword=%s, keyStoreKeyPassword=%s, keyManagerAlgorithm=%s]",
            protocol, serverName, serverPort, trustCertsStorePath,
            trustCertsStorePassword != null ? arrayToString(trustCertsStorePassword, trustCertsStorePassword.length, maxLen) : null, keyStorePath,
            keyStorePassword != null ? arrayToString(keyStorePassword, keyStorePassword.length, maxLen) : null,
            keyStoreKeyPassword != null ? arrayToString(keyStoreKeyPassword, keyStoreKeyPassword.length, maxLen) : null, keyManagerAlgorithm);
  }

  private String arrayToString(Object array, int len, int maxLen) {
    StringBuilder builder = new StringBuilder();
    len = Math.min(len, maxLen);
    builder.append("[");
    for (int i = 0; i < len; i++) {
      if (i > 0)
        builder.append(", ");
      if (array instanceof char[])
        builder.append(((char[]) array)[i]);
    }
    builder.append("]");
    return builder.toString();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.connector.Connector#reset()
   */
  public void reset() {
    // TODO Auto-generated method stub
    
  }

}
