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
   * Create a session with artifactID
   * @param msisdn UserID
   * @param artifactID  SAMLSessionID
   * @param original  true 表示用户先从本地IDP登录
   * @return
   * @throws SessionManagementException
   */
  public Session create(String msisdn, String artifactID, boolean original) throws SessionManagementException;

  /**
   * Create a session
   * @param msisdn UserID
   * @param original  true 表示用户先从本地IDP登录
   * @return
   * @throws SessionManagementException
   */
  public Session create(String msisdn, boolean original) throws SessionManagementException;

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

