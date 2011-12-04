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
import java.security.cert.CertificateException;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhaodonglu
 *
 */
public class SimpleNetworkConnectorImpl implements Connector {

  private static Log log = LogFactory.getLog(SimpleNetworkConnectorImpl.class);
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
   * @return the storePassword
   */
  public char[] getStorePassword() {
    return storePassword;
  }

  /**
   * @param storePassword the storePassword to set
   */
  public void setStorePassword(char[] storePassword) {
    this.storePassword = storePassword;
  }

  /**
   * @return the keyPassword
   */
  public char[] getKeyPassword() {
    return keyPassword;
  }

  /**
   * @param keyPassword the keyPassword to set
   */
  public void setKeyPassword(char[] keyPassword) {
    this.keyPassword = keyPassword;
  }

  /**
   * @return the keyManagerAlgorithm
   */
  public String getKeyManagerAlgorithm() {
    return keyManagerAlgorithm;
  }

  /**
   * @param keyManagerAlgorithm the keyManagerAlgorithm to set
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
   * @param protocol the protocol to set
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
   * @param serverName the serverName to set
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
   * @param serverPort the serverPort to set
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
   * @param timeOut the timeOut to set
   */
  public void setTimeOut(int timeOut) {
    this.timeOut = timeOut;
  }

  /**
   * @param socket the socket to set
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
