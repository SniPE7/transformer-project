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
 * @author zhaodonglu
 * 
 */
public class HttpSessionListenerImpl implements HttpSessionListener {

  private static Log log = LogFactory.getLog(ServletSessionManagerImpl.class);

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
        SessionManager sm = (SessionManager)ctx.getBean("sessionManager");
        try {
          sm.destroy(artifactID);
        } catch (SessionManagementException e) {
          log.error(String.format("Failure to destroy session, artifactId: [%s]", artifactID));
        }
      }
    }
  }

}
