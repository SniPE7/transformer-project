/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.client.ClientException;
import com.ibm.tivoli.cmcc.client.LogoutServiceClient;
import com.ibm.tivoli.cmcc.connector.ConnectorManager;
import com.ibm.tivoli.cmcc.server.connector.ConnectorManagerSupplier;

/**
 * @author zhaodonglu
 * 
 */
public class NotifyLogout2FederationIDPSessionListener implements SessionListener {
  private static Log log = LogFactory.getLog(NotifyLogout2FederationIDPSessionListener.class);

  private LogoutServiceClient logoutClient = null;

  private ConnectorManagerSupplier connectorManagerSupplier;
  /**
   * 
   */
  public NotifyLogout2FederationIDPSessionListener() {
    super();
  }

  /**
   * @return the logoutClient
   */
  public LogoutServiceClient getLogoutClient() {
    return logoutClient;
  }

  /**
   * @param logoutClient
   *          the logoutClient to set
   */
  public void setLogoutClient(LogoutServiceClient logoutClient) {
    this.logoutClient = logoutClient;
  }

  /**
   * @return the connectorManagerSupplier
   */
  public ConnectorManagerSupplier getConnectorManagerSupplier() {
    return connectorManagerSupplier;
  }

  /**
   * @param connectorManagerSupplier the connectorManagerSupplier to set
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
  }

  public void beforeSessionDestroyed(SessionEvent event, boolean broadcastToOtherIDPs) {
    String artifactID = null;
    try {
      Session session = (Session) event.getSession();
      artifactID = session.getArtifactID();
      // 判断是否需要通知所有Federal IDP
      if (session != null && broadcastToOtherIDPs) {
        // 检查标志, 判断是否已经发送过一次, 发送过则不再发送
        Boolean hasSent = (Boolean) session.getAttribute("HAS_SENT_SAML_LOGOUT_TO_IDP_FLAG");
        if (hasSent == null || !hasSent.booleanValue()) {
          log.debug(String.format("Propare to notify logout envent to top authen center, artifactId: [%s]", artifactID));
          String artifactDomain = session.getArtifactDomain();
          ConnectorManager conMgr = this.connectorManagerSupplier.getConnectorManager(artifactDomain);
          String resp = logoutClient.submit(conMgr.getConnector(), artifactID);
          log.debug(String.format("Received logout event reponse from top authen center, artifactId: [%s], response: [%s]", artifactID, resp));
          session.setAttribute("HAS_SENT_SAML_LOGOUT_TO_IDP_FLAG", true);
          event.getSessionManager().update(session);
        }
      }
    } catch (ClientException e) {
      log.error(String.format("Failure to notify logout event, artifactId: [%s]", artifactID), e);
    } catch (Throwable e) {
      log.error(String.format("Failure to notify logout event, artifactId: [%s]", artifactID), e);
    }
  }

  public void afterSessionDestroyed(SessionEvent event, boolean broadcastToOtherIDPs) {
  }

}
