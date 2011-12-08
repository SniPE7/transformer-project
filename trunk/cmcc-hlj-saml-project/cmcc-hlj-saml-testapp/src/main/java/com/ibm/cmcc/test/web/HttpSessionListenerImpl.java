/**
 * 
 */
package com.ibm.cmcc.test.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhaodonglu
 * 
 */
public class HttpSessionListenerImpl implements HttpSessionListener, HttpSessionAttributeListener {

  private static Log log = LogFactory.getLog(HttpSessionListenerImpl.class);
  private static Map<String, HttpSession> artifactMap = new HashMap<String, HttpSession>();

  /**
   * 
   */
  public HttpSessionListenerImpl() {
    super();
  }

  /**
   * @return the artifactMap
   */
  public static Map<String, HttpSession> getArtifactMap() {
    return artifactMap;
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
    for (String artifact: artifactMap.keySet()) {
        HttpSession s = artifactMap.get(artifact);
        if (s.getId().equals(se.getSession().getId())) {
           artifactMap.remove(artifact);
        }
    }
  }

  public void attributeAdded(HttpSessionBindingEvent se) {
    HttpSession session = se.getSession();
    String key = se.getName();
    if (key.equals("ARTIFACT_ID")) {
       String value = (String)se.getValue();
       artifactMap .put(value, session);
    }
  }

  public void attributeRemoved(HttpSessionBindingEvent se) {
  }

  public void attributeReplaced(HttpSessionBindingEvent se) {
    this.attributeAdded(se);
  }

}
