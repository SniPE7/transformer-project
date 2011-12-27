/**
 * 
 */
package com.ibm.tivoli.cmcc.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.security.Security;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.apache.mina.filter.SSLFilter;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import com.ibm.tivoli.cmcc.handler.MyLoggingFilter;
import com.ibm.tivoli.cmcc.handler.SAMLRequestHanlder;
import com.ibm.tivoli.cmcc.handler.SSLHandshakeErrorFilter;
import com.ibm.tivoli.cmcc.ssl.SSLContextFactory;

/**
 * @author Zhao Dong Lu
 *
 */
public class SAMLSSLServer {
  
  private static Log log = LogFactory.getLog(SAMLSSLServer.class);
  
  private int port = 8443;
  private String charset = "UTF-8";

  // NOTE: The keystore was generated using keytool:
  // keytool -genkey -alias bogus -keysize 512 -validity 3650 -keyalg RSA -dname "CN=bogus.com, OU=XXX CA, O=Bogus Inc, L=Stockholm, S=Stockholm, C=SE" -keypass boguspw -storepass boguspw -keystore bogus.cert

  /**
   * Server certificate keystore file name.
   */
  private String keyStorePath = "/certs/server_pwd_importkey.jks";

  private char[] keyStorePassword = "importkey".toCharArray();

  private char[] keyPassword = "importkey".toCharArray();

  /**
   * Trust certificate store path
   */
  private String trustCertsStorePath = "/certs/client_pwd_importkey.jks";

  /**
   * Trust certificate store password
   */
  private char[] trustCertsStorePassword = "importkey".toCharArray();

  private String protocol = "TLS";

  private String keyManagerAlgorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
  
  private IoHandlerAdapter requestHandler = new SAMLRequestHanlder();

  private IoAcceptor acceptor;
  /**
   * 
   */
  public SAMLSSLServer() {
    super();
  }
  
  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
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
  public void setKeyStorePassword(char[] storePasswords) {
    this.keyStorePassword = storePasswords;
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
  public void setKeyPassword(char[] keyPasswords) {
    this.keyPassword = keyPasswords;
  }

  /**
   * @return the trustCertsStorePath
   */
  public String getTrustCertsStorePath() {
    return trustCertsStorePath;
  }

  /**
   * @param trustCertsStorePath the trustCertsStorePath to set
   */
  public void setTrustCertsStorePath(String trustCertsStorePath) {
    this.trustCertsStorePath = trustCertsStorePath;
  }

  /**
   * @return the trustCertsStorePassword
   */
  public char[] getTrustCertsStorePassword() {
    return trustCertsStorePassword;
  }

  /**
   * @param trustCertsStorePassword the trustCertsStorePassword to set
   */
  public void setTrustCertsStorePassword(char[] trustCertsStorePassword) {
    this.trustCertsStorePassword = trustCertsStorePassword;
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
   * @param keyManagerAlgorithm the keyManagerAlgorithm to set
   */
  public void setKeyManagerAlgorithm(String keyManagerAlgorithm) {
    this.keyManagerAlgorithm = keyManagerAlgorithm;
  }

  public IoHandlerAdapter getRequestHandler() {
    return requestHandler;
  }

  public void setRequestHandler(IoHandlerAdapter requestHandler) {
    this.requestHandler = requestHandler;
  }

  public void start() throws IOException {
    if (this.acceptor != null) {
       log.info("CMCC SAML SSL server is running.");  
       return;
    }
    
    ByteBuffer.setUseDirectBuffers(false);
    ByteBuffer.setAllocator(new SimpleByteBufferAllocator());

    acceptor = new SocketAcceptor();

    SocketAcceptorConfig cfg = new SocketAcceptorConfig();
    if (log.isDebugEnabled()) {
       cfg.getFilterChain().addLast("logger", new MyLoggingFilter());
    }
    
    try {
      log.info(String.format("Reading SSL KeyStore from: [%s]", keyStorePath));
      SSLContext sslContextFactory = SSLContextFactory.getServerSSLContext(protocol, this.getKeyManagerAlgorithm(), keyStorePath, "JKS", keyStorePassword, keyPassword, this.trustCertsStorePath, "JKS", this.trustCertsStorePassword);
      SSLHandshakeErrorFilter handeShakeFilter = new SSLHandshakeErrorFilter();
      cfg.getFilterChain().addLast("sslHandshakeErrorFilter", handeShakeFilter);
      SSLFilter sslFilter = new SSLFilter(sslContextFactory);
      cfg.getFilterChain().addLast("sslFilter", sslFilter);
      //cfg.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName(this.charset))));
      //cfg.getFilterChain().addLast("codec", new ProtocolCodecFilter(new XMLCodecFactory(Charset.forName(this.charset))));

      acceptor.bind(new InetSocketAddress(this.port), requestHandler, cfg);
      log.info("CMCC SAML SSL server started, listening on " + this.port);
    } catch (GeneralSecurityException e) {
      log.error("Failure to enable SSL, cause: " + e.getMessage(), e);
    }
    
  }
  
  public void stop() throws IOException {
    if (this.acceptor != null) {
      this.acceptor.unbindAll();
      log.info("CMCC SAML server stopped");
    }
  }

}
