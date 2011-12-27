package com.ibm.tivoli.cmcc.server.connector;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.connector.ConnectorManager;
import com.ibm.tivoli.cmcc.connector.NetworkConnectorManager;
import com.ibm.tivoli.cmcc.connector.SimpleNetworkConnectorImpl;

public class ConnectorManagerSupplierImpl implements ConnectorManagerSupplier {
  
  private Log log = LogFactory.getLog(ConnectorManagerSupplierImpl.class);
  
  private boolean alwaysUseDefault = false;

  private String defaultConnectorClassName = SimpleNetworkConnectorImpl.class.getName();

  /**
   * Trust certificate store path
   */
  private String defaultTrustCertsStorePath = "/certs/client_pwd_importkey.jks";

  /**
   * Trust certificate store password
   */
  private char[] defaultTrustCertsStorePassword = "importkey".toCharArray();

  /**
   * Key store path
   */
  private String defaultKeyStorePath = "/certs/client_pwd_importkey.jks";

  /**
   * Key store password
   */
  private char[] defaultKeyStorePassword = "importkey".toCharArray();
  
  /**
   * Key password
   */
  private char[] defaultKeyStoreKeyPassword = "importkey".toCharArray();

  private String defaultKeyManagerAlgorithm = null;

  private String defaultProtocol = "TCP";
  
  private String defaultServerName = null;
  
  private int defaultServerPort = 8080;

  /**
   * In seconds
   */
  private int defaultTimeOut = 10;

  /**
   * @return the alwaysUseDefault
   */
  public boolean isAlwaysUseDefault() {
    return alwaysUseDefault;
  }

  /**
   * @param alwaysUseDefault the alwaysUseDefault to set
   */
  public void setAlwaysUseDefault(boolean alwaysUseDefault) {
    this.alwaysUseDefault = alwaysUseDefault;
  }

  /**
   * @return the defaultConnectorClassName
   */
  public String getDefaultConnectorClassName() {
    return defaultConnectorClassName;
  }

  /**
   * @param defaultConnectorClassName the defaultConnectorClassName to set
   */
  public void setDefaultConnectorClassName(String defaultConnectorClassName) {
    this.defaultConnectorClassName = defaultConnectorClassName;
  }

  /**
   * @return the defaultTrustCertsStorePath
   */
  public String getDefaultTrustCertsStorePath() {
    return defaultTrustCertsStorePath;
  }

  /**
   * @param defaultTrustCertsStorePath the defaultTrustCertsStorePath to set
   */
  public void setDefaultTrustCertsStorePath(String defaultTrustCertsStorePath) {
    this.defaultTrustCertsStorePath = defaultTrustCertsStorePath;
  }

  /**
   * @return the defaultTrustCertsStorePassword
   */
  public char[] getDefaultTrustCertsStorePassword() {
    return defaultTrustCertsStorePassword;
  }

  /**
   * @param defaultTrustCertsStorePassword the defaultTrustCertsStorePassword to set
   */
  public void setDefaultTrustCertsStorePassword(char[] defaultTrustCertsStorePassword) {
    this.defaultTrustCertsStorePassword = defaultTrustCertsStorePassword;
  }

  /**
   * @return the defaultKeyStorePath
   */
  public String getDefaultKeyStorePath() {
    return defaultKeyStorePath;
  }

  /**
   * @param defaultKeyStorePath the defaultKeyStorePath to set
   */
  public void setDefaultKeyStorePath(String defaultKeyStorePath) {
    this.defaultKeyStorePath = defaultKeyStorePath;
  }

  /**
   * @return the defaultKeyStorePassword
   */
  public char[] getDefaultKeyStorePassword() {
    return defaultKeyStorePassword;
  }

  /**
   * @param defaultKeyStorePassword the defaultKeyStorePassword to set
   */
  public void setDefaultKeyStorePassword(char[] defaultKeyStorePassword) {
    this.defaultKeyStorePassword = defaultKeyStorePassword;
  }

  /**
   * @return the defaultKeyStoreKeyPassword
   */
  public char[] getDefaultKeyStoreKeyPassword() {
    return defaultKeyStoreKeyPassword;
  }

  /**
   * @param defaultKeyStoreKeyPassword the defaultKeyStoreKeyPassword to set
   */
  public void setDefaultKeyStoreKeyPassword(char[] defaultKeyStoreKeyPassword) {
    this.defaultKeyStoreKeyPassword = defaultKeyStoreKeyPassword;
  }

  /**
   * @return the defaultKeyManagerAlgorithm
   */
  public String getDefaultKeyManagerAlgorithm() {
    return defaultKeyManagerAlgorithm;
  }

  /**
   * @param defaultKeyManagerAlgorithm the defaultKeyManagerAlgorithm to set
   */
  public void setDefaultKeyManagerAlgorithm(String defaultKeyManagerAlgorithm) {
    this.defaultKeyManagerAlgorithm = defaultKeyManagerAlgorithm;
  }

  /**
   * @return the defaultProtocol
   */
  public String getDefaultProtocol() {
    return defaultProtocol;
  }

  /**
   * @param defaultProtocol the defaultProtocol to set
   */
  public void setDefaultProtocol(String defaultProtocol) {
    this.defaultProtocol = defaultProtocol;
  }

  /**
   * @return the defaultServerName
   */
  public String getDefaultServerName() {
    return defaultServerName;
  }

  /**
   * @param defaultServerName the defaultServerName to set
   */
  public void setDefaultServerName(String defaultServerName) {
    this.defaultServerName = defaultServerName;
  }

  /**
   * @return the defaultServerPort
   */
  public int getDefaultServerPort() {
    return defaultServerPort;
  }

  /**
   * @param defaultServerPort the defaultServerPort to set
   */
  public void setDefaultServerPort(int defaultServerPort) {
    this.defaultServerPort = defaultServerPort;
  }

  /**
   * @return the defaultTimeOut
   */
  public int getDefaultTimeOut() {
    return defaultTimeOut;
  }

  /**
   * @param defaultTimeOut the defaultTimeOut to set
   */
  public void setDefaultTimeOut(int defaultTimeOut) {
    this.defaultTimeOut = defaultTimeOut;
  }

  public ConnectorManagerSupplierImpl() {
    super();
  }
  
  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.server.connector.ConnectorManagerSupplier#getConnectorManager(java.lang.String)
   */
  public ConnectorManager getConnectorManager(String artifactDomain) {
    NetworkConnectorManager mgr = new NetworkConnectorManager();
    try {
      BeanUtils.copyProperties(mgr, this);
      // All of Supplier  properties start with "default", so erase prefix "default"
      Map<String, Object> map = BeanUtils.describe(this);
      Map<String, Object> result = new HashMap<String, Object>(map);
      for (String key: map.keySet()) {
          if (key.startsWith("default")) {
             String newKey = key.substring("default".length());
             if (newKey.length() > 0) {
                newKey = newKey.substring(0, 1).toLowerCase() + newKey.substring(1);
             }
             result.put(newKey, map.get(key));
          }
      }
      BeanUtils.populate(mgr, result);
    } catch (IllegalAccessException e) {
      log.warn(e.getMessage(), e);
    } catch (InvocationTargetException e) {
      log.warn(e.getMessage(), e);
    } catch (NoSuchMethodException e) {
      log.warn(e.getMessage(), e);
    }
    if (!this.alwaysUseDefault || StringUtils.isEmpty(this.defaultServerName)) {
       mgr.setServerName(artifactDomain);
    }
    return mgr;
  }

}
