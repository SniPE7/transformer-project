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
 * 用于侦听SSO Web模块销毁HttpSession的情况, 并将此行为传播到SAML Session Manager
 * @author zhaodonglu
 * 
 */
public class HttpSessionListenerImpl implements HttpSessionListener {

  private static Log log = LogFactory.getLog(HttpSessionListenerImpl.class);
  
  static final String SAML_SESSION_ID_ATTR_NAME = "SAML_SESSION_ID_ATTR_NAME";

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
      String samlSessionID = (String) hSession.getAttribute(SAML_SESSION_ID_ATTR_NAME);
      if (samlSessionID != null) {
        log.debug(String.format("Destroy SAML session ID: [%s]", samlSessionID));
        String artifactID = samlSessionID;
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(hSession.getServletContext());
        
        // 判读是否需要向顶级发送LogoutRequest, 通知用户已经注销
        notifyFederedIDP(ctx, artifactID);

        // 销毁SAML Session
        destroySAMLSession(ctx, artifactID);

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
      SessionManager sm = (SessionManager) ctx.getBean("sessionManager");
      Session session = sm.get(artifactID);
      if (session != null && !session.isOringinal()) {
        log.debug(String.format("Propare to notify logout envent to top authen center, artifactId: [%s]", artifactID));
        String resp = logoutClient.submit(artifactID);
        log.debug(String.format("Received logout event reponse from top authen center, artifactId: [%s], response: [%s]", artifactID, resp));
      }
    } catch (ClientException e) {
      log.error(String.format("Failure to notify top authen center, artifactId: [%s]", artifactID), e);
    } catch (SessionManagementException e) {
      log.error(String.format("Failure to notify top authen center, artifactId: [%s]", artifactID), e);
    }
  }

  /**
   * @param ctx
   * @param artifactID
   */
  private void destroySAMLSession(ApplicationContext ctx, String artifactID) {
    SessionManager sm = (SessionManager) ctx.getBean("sessionManager");
    try {
      sm.destroy(artifactID);
    } catch (SessionManagementException e) {
      log.error(String.format("Failure to destroy session, artifactId: [%s]", artifactID), e);
    }
  }

}
