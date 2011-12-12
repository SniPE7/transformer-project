/**
 * 
 */
package com.ibm.tivoli.cmcc.connector;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhaodonglu
 *
 */
public class NetworkConnectorManager implements ConnectorManager {

  private static Log log = LogFactory.getLog(NetworkConnectorManager.class);
  
  private String connectorClassName = SimpleNetworkConnectorImpl.class.getName();

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

  private String keyManagerAlgorithm = null;

  private String protocol = "TCP";
  
  private String serverName = null;
  
  private int serverPort = 8080;

  /**
   * In seconds
   */
  private int timeOut = 10;

  /**
   * 
   */
  public NetworkConnectorManager() {
    super();
  }
  
  /**
   * @return the connectorClassName
   */
  public String getConnectorClassName() {
    return connectorClassName;
  }

  /**
   * @param connectorClassName the connectorClassName to set
   */
  public void setConnectorClassName(String connectorClassName) {
    this.connectorClassName = connectorClassName;
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

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.connector.ConnectorManager#getConnector()
   */
  public Connector getConnector() {
    SimpleNetworkConnectorImpl connector = new SimpleNetworkConnectorImpl();
    try {
      BeanUtils.copyProperties(connector, this);
    } catch (IllegalAccessException e) {
      log.warn(e.getMessage(), e);
    } catch (InvocationTargetException e) {
      log.warn(e.getMessage(), e);
    }
    return connector;
  }

}
