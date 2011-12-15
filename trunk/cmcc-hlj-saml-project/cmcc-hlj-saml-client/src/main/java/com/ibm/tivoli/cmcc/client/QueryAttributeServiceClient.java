/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import com.ibm.tivoli.cmcc.connector.Connector;
import com.ibm.tivoli.cmcc.response.QueryAttributeResponse;

/**
 * @author Zhao Dong Lu
 *
 */
public interface QueryAttributeServiceClient {

  /**
   * Submit SAML request to server
   * @param samlId
   * @return XML content
   * @throws ClientException
   */
  public String submit(Connector connector, String samlId) throws ClientException;
  
  /**
   * Return a response object.
   * @param samId
   * @return
   * @throws ClientException
   */
  public QueryAttributeResponse submitAndParse(Connector connector, String samlId) throws ClientException;

}
