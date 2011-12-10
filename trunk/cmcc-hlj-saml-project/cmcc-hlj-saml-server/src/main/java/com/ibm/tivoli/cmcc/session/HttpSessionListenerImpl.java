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

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.tivoli.cmcc.client.ClientException;
import com.ibm.tivoli.cmcc.client.LogoutServiceClient;
import com.ibm.tivoli.cmcc.server.utils.MyPropertyPlaceholderConfigurer;

/**
 * @author zhaodonglu
 * 
 */
public class HttpSessionListenerImpl implements HttpSessionListener {

  private static Log log = LogFactory.getLog(HttpSessionListenerImpl.class);

  /**
   * 
   */
  public HttpSessionListenerImpl() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http
   * .HttpSessionEvent)
   */
  public void sessionCreated(HttpSessionEvent se) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http
   * .HttpSessionEvent)
   */
  public void sessionDestroyed(HttpSessionEvent se) {
    HttpSession hSession = se.getSession();
    if (hSession != null) {
      Session session = (Session) hSession.getAttribute(ServletSessionManagerImpl.SAML_SESSION_ATTR_NAME);
      if (session != null) {
        log.debug(String.format("Destroy SAML session: [%s]", session.toString()));
        String artifactID = session.getArtifactID();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(hSession.getServletContext());
        // 销毁SAML Session 
        destroySAMLSession(artifactID, ctx);
        // 判读是否需要向顶级发送LogoutRequest, 通知用户已经注销
        boolean needToNotifyTopAuthenCenter = !session.isOringinal();
        if (needToNotifyTopAuthenCenter) {
          notifyFederedIDP(ctx, artifactID);
        }

        // 向已经登录的应用发送LogoutRequest, 通知应用用户已经注销
        // TODO 修改临时实现
        MyPropertyPlaceholderConfigurer propsCfg = (MyPropertyPlaceholderConfigurer) ctx.getBean("propertyPlaceholderConfigurer");
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
      }
    }
  }

  /**
   * @param ctx
   * @param artifactID
   */
  private void notifyFederedIDP(ApplicationContext ctx, String artifactID) {
    LogoutServiceClient logoutClient = (LogoutServiceClient) ctx.getBean("logoutClient");
    try {
      log.debug(String.format("Propare to notify logout envent to top authen center, artifactId: [%s]", artifactID));
      String resp = logoutClient.submit(artifactID);
      log.debug(String.format("Received logout event reponse from top authen center, artifactId: [%s], response: [%s]", artifactID, resp));
    } catch (ClientException e) {
      log.error(String.format("Failure to notify top authen center, artifactId: [%s]", artifactID), e);
    }
  }

  /**
   * @param artifactID
   * @param ctx
   */
  private void destroySAMLSession(String artifactID, ApplicationContext ctx) {
    SessionManager sm = (SessionManager) ctx.getBean("sessionManager");
    try {
      sm.destroy(artifactID);
    } catch (SessionManagementException e) {
      log.error(String.format("Failure to destroy session, artifactId: [%s]", artifactID), e);
    }
  }

}
