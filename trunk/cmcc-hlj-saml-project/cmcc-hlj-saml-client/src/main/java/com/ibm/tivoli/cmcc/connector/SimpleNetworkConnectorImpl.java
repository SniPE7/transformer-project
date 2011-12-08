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

import javax.net.SocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.server.utils.Helper;

/**
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
  private int timeOut = 60;

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
    socket = getSocket();
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
    SocketFactory sf = context.getSocketFactory();

    // Make socket connect with SSL server
    Socket s = sf.createSocket(InetAddress.getByName(this.serverName), this.serverPort);
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
      KeyManager[] keyManagers = kmf.getKeyManagers();
      kmf.init(keystore, this.keyStoreKeyPassword);
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
    socket.connect(sockaddr, timeOut);
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

}
