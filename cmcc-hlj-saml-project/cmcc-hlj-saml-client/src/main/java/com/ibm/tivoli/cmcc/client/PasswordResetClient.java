/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import com.ibm.tivoli.cmcc.connector.Connector;


/**
 * @author Zhao Dong Lu
 *
 */
public interface PasswordResetClient {

  /**
   * Submit SAML request to server
   * @param userName
   * @param serviceCode
   * @param networkPassword
   * @return
   * @throws ClientException
   */
  public String submit(Connector connector, String userName, String serviceCode, String networkPassword) throws ClientException;

}
