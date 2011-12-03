package com.ibm.tivoli.cmcc.client;

import java.util.Properties;

public interface Client {

  public abstract Properties getProperties();

  public abstract void setProperties(Properties properties);

  public abstract String getServerName();

  public abstract void setServerName(String serverName);

  public abstract int getServerPort();

  public abstract void setServerPort(int serverPort);

  public abstract int getTimeOut();

  public abstract void setTimeOut(int timeOut);

  public abstract String getCharset();

  public abstract void setCharset(String charset);

  /**
   * @return the keyStorePath
   */
  public abstract String getKeyStorePath();

  /**
   * @param keyStorePath the keyStorePath to set
   */
  public abstract void setKeyStorePath(String keyStorePath);

  /**
   * @return the storePassword
   */
  public abstract char[] getStorePassword();

  /**
   * @param storePassword the storePassword to set
   */
  public abstract void setStorePassword(char[] storePassword);

  /**
   * @return the keyPassword
   */
  public abstract char[] getKeyPassword();

  /**
   * @param keyPassword the keyPassword to set
   */
  public abstract void setKeyPassword(char[] keyPassword);

  /**
   * @return the keyManagerAlgorithm
   */
  public abstract String getKeyManagerAlgorithm();

  /**
   * @param keyManagerAlgorithm the keyManagerAlgorithm to set
   */
  public abstract void setKeyManagerAlgorithm(String keyManagerAlgorithm);

  /**
   * @return the protocol
   */
  public abstract String getProtocol();

  /**
   * @param protocol the protocol to set
   */
  public abstract void setProtocol(String protocol);

}