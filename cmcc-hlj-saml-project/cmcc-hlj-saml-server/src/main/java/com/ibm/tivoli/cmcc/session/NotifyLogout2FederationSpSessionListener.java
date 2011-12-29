/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ibm.tivoli.cmcc.client.LogoutServiceClient;
import com.ibm.tivoli.cmcc.server.utils.MyPropertyPlaceholderConfigurer;

/**
 * @author zhaodonglu
 *
 */
public class NotifyLogout2FederationSpSessionListener implements SessionListener, ApplicationContextAware {
  private static Log log = LogFactory.getLog(NotifyLogout2FederationSpSessionListener.class);

  private LogoutServiceClient logoutClient = null;
  
  private ApplicationContext applicationContext = null;

  /**
   * 
   */
  public NotifyLogout2FederationSpSessionListener() {
    super();
  }

  /**
   * @return the logoutClient
   */
  public LogoutServiceClient getLogoutClient() {
    return logoutClient;
  }

  /**
   * @param logoutClient the logoutClient to set
   */
  public void setLogoutClient(LogoutServiceClient logoutServiceClient) {
    this.logoutClient = logoutServiceClient;
  }

  /**
   * @return the applicationContext
   */
  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  /**
   * @param applicationContext the applicationContext to set
   */
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionListener#sessionCreated(com.ibm.tivoli.cmcc.session.SessionEvent)
   */
  public void afterSessionCreate(SessionEvent event) {
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionListener#sessionTouched(com.ibm.tivoli.cmcc.session.SessionEvent)
   */
  public void afterSessionTouch(SessionEvent event) {
  }

  public void beforeSessionDestroyed(SessionEvent event, boolean broadcastToOtherIDPs) {
    String artifactID = null;
    try {
      Session session = (Session)event.getSession();
      artifactID = session.getArtifactID();
      // 向已经登录的应用发送LogoutRequest, 通知应用用户已经注销
      // TODO 修改临时实现
      MyPropertyPlaceholderConfigurer propsCfg = (MyPropertyPlaceholderConfigurer) applicationContext.getBean("propertyPlaceholderConfigurer");
      for (int i = 0; i < 10; i++) {
        try {
          Properties props = propsCfg.getProperties();
          String u = props.getProperty("app.logout.url." + i);
          if (StringUtils.isEmpty(u)) {
            continue;
          }
          URL url = new URL(u + "?artifactid=" + artifactID);
          log.debug("Send logout event to: " + u);
          URLConnection uc = url.openConnection();
          BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
          String inputLine;
          while ((inputLine = in.readLine()) != null) {
            log.debug(inputLine);
          }
          in.close();
        } catch (MalformedURLException e) {
          log.error(String.format("Failure to notify app, artifactId: [%s]", artifactID), e);
        } catch (IOException e) {
          log.error(String.format("Failure to notify app, artifactId: [%s]", artifactID), e);
        }
      }
    } catch (Throwable e) {
      log.error(String.format("Failure to notify logout event, artifactId: [%s]", artifactID), e);
    }
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionListener#afterSessionDestroyed(com.ibm.tivoli.cmcc.session.SessionEvent, boolean)
   */
  public void afterSessionDestroyed(SessionEvent event, boolean broadcastToOtherIDPs) {
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionListener#beforeSessionTouch(com.ibm.tivoli.cmcc.session.SessionEvent)
   */
  public void beforeSessionTouch(SessionEvent event) {
  }

}
