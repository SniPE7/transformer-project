/**
 * 
 */
package com.ibm.tivoli.cmcc.session;



/**
 * @author Zhao Dong Lu
 *
 */
public interface SessionManager {

  /**
   * Create a session
   * @param msisdn
   * @return ArtifactID (Session ID)
   * @throws Exception
   */
  public String create(String msisdn) throws SessionManagementException;

  /**
   * Touch and active a session
   * @param artifactId
   * @return
   */
  public boolean touch(String artifactId) throws SessionManagementException;
  
  /**
   * Destroy a Session
   * @param base
   * @param filter
   * @param uniqueIdentifier
   * @return
   */
  public boolean destroy(String artifactId) throws SessionManagementException;
  
  /**
   * Return a session object
   * @param artifactId
   * @return
   */
  public Session get(String artifactId) throws SessionManagementException;
  
}

