/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import com.ibm.tivoli.cmcc.connector.Connector;


/**
 * @author Zhao Dong Lu
 *
 */
public interface LogoutServiceClient {



  /**
   * Submit SAML request to server
   * @param samlId
   * @return
   * @throws ClientException
   */
  public String submit(Connector connector, String samlId) throws ClientException;

}
