/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

/**
 * @author zhaodonglu
 *
 */
public interface SessionListener {
  
  
  /**
   * @param event
   */
  public abstract void afterSessionCreate(SessionEvent event);

  /**
   * @param event
   * @param broadcastToOtherIDPs  表示是否需要向其它的IDP广播通知这个销毁事件.
   *          true - 表示需要通知其它IDP, 用户已经注销
   *          false - 表示不需要通知其它IDP, 用户已经注销. 此情况多为接收到其它IDP的事件, 不需要再次广播, 避免造成回路.
   */
  public abstract void beforeSessionDestroyed(SessionEvent event, boolean broadcastToOtherIDPs);

  /**
   * @param event
   * @param broadcastToOtherIDPs  表示是否需要向其它的IDP广播通知这个销毁事件.
   *          true - 表示需要通知其它IDP, 用户已经注销
   *          false - 表示不需要通知其它IDP, 用户已经注销. 此情况多为接收到其它IDP的事件, 不需要再次广播, 避免造成回路.
   */
  public abstract void afterSessionDestroyed(SessionEvent event, boolean broadcastToOtherIDPs);

  /**
   * Fired after a SAML session lastAccessTime is changed
   * @param event
   */
  public abstract void beforeSessionTouch(SessionEvent event);

  /**
   * Fired after a SAML session lastAccessTime is changed
   * @param event
   */
  public abstract void afterSessionTouch(SessionEvent event);
}
