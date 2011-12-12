/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 用于侦听SSO Web模块销毁HttpSession的情况, 并将此行为传播到SAML Session Manager
 * @author zhaodonglu
 * 
 */
public class HttpSessionListenerImpl implements HttpSessionListener {

  private static Log log = LogFactory.getLog(HttpSessionListenerImpl.class);
  
  static final String SAML_SESSION_ID_ATTR_NAME = "SAML_SESSION_ID_ATTR_NAME";
  static final String NOT_NEED_TO_INVALIDATE = "NOT_NEED_TO_INVALIDATE";


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
        
        // 销毁SAML Session
        SessionManager sm = (SessionManager) ctx.getBean("sessionManager");
        try {
          // 设置标志, 通知SAML Session Manager, 无需销毁HttpSession, 由WebContainer自行完成
          hSession.setAttribute(NOT_NEED_TO_INVALIDATE, true);
          sm.destroy(artifactID, true);
        } catch (SessionManagementException e) {
          log.error(String.format("Failure to destroy session, artifactId: [%s]", artifactID), e);
        }
      }
    }
  }


}
