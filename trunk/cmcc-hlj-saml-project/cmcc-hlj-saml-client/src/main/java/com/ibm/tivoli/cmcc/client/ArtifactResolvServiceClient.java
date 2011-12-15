/**
 * 
 */
package com.ibm.tivoli.cmcc.client;

import com.ibm.tivoli.cmcc.connector.Connector;
import com.ibm.tivoli.cmcc.response.ArtifactResolvResponse;

/**
 * @author Zhao Dong Lu
 *
 */
public interface ArtifactResolvServiceClient {
  
  public String submit(Connector connector, String samlId) throws ClientException;

  /**
   * Return a response object.
   * @param samId
   * @return
   * @throws ClientException
   */
  public ArtifactResolvResponse submitAndParse(Connector connector, String artifact) throws ClientException;

}
