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
   * Registe a SessionListener
   * @param sessionListener
   */
  public void setSessionListener(SessionListener sessionListener);

  /**
   * Create a session with artifactID
   * @param msisdn UserID
   * @param artifactID  SAMLSessionID
   * @param original  true 表示用户先从本地IDP登录
   * @return
   * @throws SessionManagementException
   */
  public Session create(String msisdn, String artifactID, boolean original, String artifactDomain) throws SessionManagementException;

  /**
   * Create a session
   * @param msisdn UserID
   * @param original  true 表示用户先从本地IDP登录
   * @return
   * @throws SessionManagementException
   */
  public Session create(String msisdn, boolean original, String artifactDomain) throws SessionManagementException;

  /**
   * Touch and active a session
   * @param artifactId
   * @return
   */
  public boolean touch(String artifactId) throws SessionManagementException;
  
  /**
   * Destroy a Session
   * @param artifactId
   * @param broadcastToOtherIDPs  表示是否需要向其它的IDP广播通知这个销毁事件.
   *          true - 表示需要通知其它IDP, 用户已经注销
   *          false - 表示不需要通知其它IDP, 用户已经注销. 此情况多为接收到其它IDP的事件, 不需要再次广播, 避免造成回路.
   * @return
   * @throws SessionManagementException
   */
  public boolean destroy(String artifactId, boolean broadcastToOtherIDPs) throws SessionManagementException;
  
  /**
   * Return a session object
   * @param artifactId
   * @return
   */
  public Session get(String artifactId) throws SessionManagementException;

  /**
   * Update session and refresh session into cache
   * @param session
   * @throws SessionManagementException
   */
  public void update(Session session) throws SessionManagementException;
  
}

