/**
 * 
 */
package com.ibm.tivoli.cmcc.client;


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
  public String submit(String samlId) throws ClientException;

}
