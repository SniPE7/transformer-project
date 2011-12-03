/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import java.util.Properties;

/**
 * @author Zhao Dong Lu
 *
 */
public interface ActiviateServiceClient extends Client {
  
  /**
   * Set properties
   * @param props
   */
  public void setProperties(Properties props);

  /**
   * Set SAML Server hostname or IP
   * @param serverName
   */
  public void setServerName(String serverName);
  
  /**
   * Return SAML Server hostname or IP 
   * @return
   */
  public String getServerName();
  
  /**
   * Set SAML Server tcp port
   * @param serverPort
   */
  public void setServerPort(int serverPort);

  /**
   * Return SAML Server tcp port
   * @param serverPort
   */
  public int getServerPort();

  /**
   * Set TCP timeout in seconds
   * @param timeOut
   */
  public void setTimeOut(int timeOut);

  /**
   * Set XML charset
   * @param charset
   */
  public void setCharset(String charset);

  /**
   * Submit SAML request to server
   * @param samlId
   * @return
   * @throws ClientException
   */
  public String submit(String samlId) throws ClientException;

}
