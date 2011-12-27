/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.client.ActiviateServiceClient;
import com.ibm.tivoli.cmcc.client.ClientException;
import com.ibm.tivoli.cmcc.connector.ConnectorManager;
import com.ibm.tivoli.cmcc.server.connector.ConnectorManagerSupplier;

/**
 * @author zhaodonglu
 * 
 */
public class NotifyActiviate2FederationIDPSessionListener implements SessionListener {
  private static Log log = LogFactory.getLog(NotifyActiviate2FederationIDPSessionListener.class);

  /**
   * 发送报活信息的时间间隔(秒)
   */
  private int activiateNotifyInterval = 180;

  private ConnectorManagerSupplier connectorManagerSupplier = null;

  private ActiviateServiceClient activiateClient = null;

  private String defaultIDPActiveService = null;

  /**
   * 
   */
  public NotifyActiviate2FederationIDPSessionListener() {
    super();
  }

  /**
   * @return the defaultIDPActiveService
   */
  public String getDefaultIDPActiveService() {
    return defaultIDPActiveService;
  }

  /**
   * @param defaultIDPActiveService
   *          the defaultIDPActiveService to set
   */
  public void setDefaultIDPActiveService(String defaultIDPActiveService) {
    this.defaultIDPActiveService = defaultIDPActiveService;
  }

  /**
   * @return the activiateNotifyInterval
   */
  public int getActiviateNotifyInterval() {
    return activiateNotifyInterval;
  }

  /**
   * @param activiateNotifyInterval
   *          the activiateNotifyInterval to set
   */
  public void setActiviateNotifyInterval(int activiateNotifyInterval) {
    this.activiateNotifyInterval = activiateNotifyInterval;
  }

  /**
   * @return the activiateClient
   */
  public ActiviateServiceClient getActiviateClient() {
    return activiateClient;
  }

  /**
   * @param activiateClient
   *          the activiateClient to set
   */
  public void setActiviateClient(ActiviateServiceClient activiateClient) {
    this.activiateClient = activiateClient;
  }

  /**
   * @return the connectorManagerSupplier
   */
  public ConnectorManagerSupplier getConnectorManagerSupplier() {
    return connectorManagerSupplier;
  }

  /**
   * @param connectorManagerSupplier
   *          the connectorManagerSupplier to set
   */
  public void setConnectorManagerSupplier(ConnectorManagerSupplier connectorManagerSupplier) {
    this.connectorManagerSupplier = connectorManagerSupplier;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.session.SessionListener#sessionCreated(com.ibm.tivoli
   * .cmcc.session.SessionEvent)
   */
  public void sessionCreated(SessionEvent event) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.session.SessionListener#sessionTouched(com.ibm.tivoli
   * .cmcc.session.SessionEvent)
   */
  public void sessionTouched(SessionEvent event) {
    try {
      processTouchEvent(event);
    } catch (Throwable e) {
      // Never throw anything
      log.error(e.getMessage(), e);
    }
  }

  /**
   * @param event
   */
  private void processTouchEvent(SessionEvent event) {
    // TODO 目前总是会向总部报活, 应该区别仅当已经在总部建立了Session后, 才需要向上级报活
    Session session = (Session) event.getSession();
    // Set a value to long long ago
    Date lastNotifyTime = new Date(0L);
    if (session != null && session.getAttribute("LAST_SENT_ACTIVIATE_TIME") != null) {
      lastNotifyTime = (Date) session.getAttribute("LAST_SENT_ACTIVIATE_TIME");
    }
    if (this.activiateNotifyInterval >= 0 && System.currentTimeMillis() - lastNotifyTime.getTime() > this.activiateNotifyInterval * 1000) {
      log.debug(String.format("SENT Activiate Request to Federation IDP, artifact: [%s]", session.getArtifactID()));
      if (activiateClient != null) {
        try {
          // Update Last SENT Time, 为了防止形成回路，在做提交之前，总是更新最后一次的报活时间，而不管是否后续能够报活成功.
          session.setAttribute("LAST_SENT_ACTIVIATE_TIME", new Date());
          try {
            event.getSessionManager().update(session);
          } catch (SessionManagementException e) {
            log.error(String.format("Failure to update SAML Session, artifact: [%s], cause: %s", session.getArtifactID(), e.getMessage()));
          }

          // 向目标服务器报活
          String artifactDomain = this.defaultIDPActiveService;
          /*
          // TODO 目前可能会造成回路循环，造成死循环, 自动发现Notify对端的方式暂时取消, 需要重新设计
          if (StringUtils.isEmpty(artifactDomain)) {
            artifactDomain = session.getArtifactDomain();
          }
          */
          ConnectorManager conMgr = this.connectorManagerSupplier.getConnectorManager(artifactDomain);
          activiateClient.submit(conMgr.getConnector(), session.getArtifactID());
        } catch (ClientException e) {
          log.error(String.format("Failure to SENT Activiate Request to Federation IDP, artifact: [%s], cause: %s", session.getArtifactID(), e.getMessage()));
        }
      }
    }
  }

  public void beforeSessionDestroyed(SessionEvent event, boolean broadcastToOtherIDPs) {
  }

  public void afterSessionDestroyed(SessionEvent event, boolean broadcastToOtherIDPs) {
  }

}
