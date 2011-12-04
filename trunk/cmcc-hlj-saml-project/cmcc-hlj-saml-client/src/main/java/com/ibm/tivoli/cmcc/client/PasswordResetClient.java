/**
 * 
 */
package com.ibm.tivoli.cmcc.client;


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
  public String submit(String userName, String serviceCode, String networkPassword) throws ClientException;

}
