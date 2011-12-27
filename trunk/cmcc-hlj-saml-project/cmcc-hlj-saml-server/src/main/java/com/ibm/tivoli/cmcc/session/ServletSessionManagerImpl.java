/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ibm.tivoli.cmcc.spi.PersonDAO;
import com.ibm.tivoli.cmcc.spi.PersonDTO;
import com.ibm.tivoli.cmcc.util.Helper;

/**
 * @author Zhao Dong Lu
 * 
 */
public class ServletSessionManagerImpl implements SessionManager {

  private static Log log = LogFactory.getLog(ServletSessionManagerImpl.class);

  private PersonDAO personDAO = null;

  private SessionListener sessionListener = null;

  /**
   * ArtifactID -> HttpSessionID<br/>
   * Need to replicate in all nodes of cluster
   */
  private SessionCache<String> samlID2HttpSessionIDCache = new MapBasedSessionCache<String>();

  /**
   * HttpSessionID -> HttpSession<br/>
   * Local, not need replication
   */
  private SessionCache<HttpSession> httpSessionCache = new MapBasedSessionCache<HttpSession>();

  /**
   * SAMLSessionID -> SAMLSession <br/>
   * Need to replicate into all nodes of cluster
   */
  private SessionCache<Session> samlSessionCache = new MapBasedSessionCache<Session>();

  /**
   * Specifies the time, in seconds, between client requests before the servlet
   * container will invalidate this session.
   */
  private long maxInactiveInterval = 30 * 60;

  /**
   * 
   */
  public ServletSessionManagerImpl() {
    super();
  }

  /**
   * @return the maxInactiveInterval
   */
  public long getMaxInactiveInterval() {
    return maxInactiveInterval;
  }

  /**
   * @param maxInactiveInterval
   *          the maxInactiveInterval to set
   */
  public void setMaxInactiveInterval(long maxInactiveInterval) {
    this.maxInactiveInterval = maxInactiveInterval;
  }

  /**
   * @return the personDAO
   */
  public PersonDAO getPersonDAO() {
    return personDAO;
  }

  /**
   * @param personDAO
   *          the personDAO to set
   */
  public void setPersonDAO(PersonDAO personDAO) {
    this.personDAO = personDAO;
  }

  /**
   * @return the samlID2HttpSessionIDCache
   */
  public SessionCache<String> getSamlID2HttpSessionIDCache() {
    return samlID2HttpSessionIDCache;
  }

  /**
   * @param samlID2HttpSessionIDCache
   *          the samlID2HttpSessionIDCache to set
   */
  public void setSamlID2HttpSessionIDCache(SessionCache<String> samlID2HttpSessionIDCache) {
    this.samlID2HttpSessionIDCache = samlID2HttpSessionIDCache;
  }

  /**
   * @return the httpSessionCache
   */
  public SessionCache<HttpSession> getHttpSessionCache() {
    return httpSessionCache;
  }

  /**
   * @param httpSessionCache
   *          the httpSessionCache to set
   */
  public void setHttpSessionCache(SessionCache<HttpSession> httpSessionCache) {
    this.httpSessionCache = httpSessionCache;
  }

  /**
   * @return the samlSessionCache
   */
  public SessionCache<Session> getSamlSessionCache() {
    return samlSessionCache;
  }

  /**
   * @param samlSessionCache
   *          the samlSessionCache to set
   */
  public void setSamlSessionCache(SessionCache<Session> samlSessionCache) {
    this.samlSessionCache = samlSessionCache;
  }

  /**
   * @return the sessionListener
   */
  public SessionListener getSessionListener() {
    return sessionListener;
  }

  /**
   * @param sessionListener
   *          the sessionListener to set
   */
  public void setSessionListener(SessionListener sessionListener) {
    this.sessionListener = sessionListener;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.dao.ContactDAO#updateContact(com.ibm.tivoli.cmcc.dao
   * .ContactDTO)
   */
  public Session create(String msisdn, boolean original, String artifactDomain) throws SessionManagementException {
    String artifactID = Helper.generatorID();
    return create(msisdn, artifactID, original, artifactDomain);
  }

  /**
   * Get HttpServlet from thread context (maintainenced by Spring)
   * 
   * @return
   */
  private HttpServletRequest getHttpServletRequest() {
    ServletRequestAttributes srAttr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = srAttr.getRequest();
    return request;
  }

  /**
   * Return a HTTPSession which boudled with samlSesionID.
   * 
   * @param samlSessionID
   * @return
   */
  private HttpSession getHttpSessionBySAMLSessionID(String samlSessionID) {
    String httpSessionID = this.samlID2HttpSessionIDCache.get(samlSessionID);
    if (StringUtils.isNotEmpty(httpSessionID)) {
      HttpSession hSession = this.httpSessionCache.get(httpSessionID);
      return hSession;
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.cmcc.session.SessionManager#create(java.lang.String,
   * java.lang.String)
   */
  public Session create(String msisdn, String artifactID, boolean original, String artifactDomain) throws SessionManagementException {
    log.debug(String.format("Creating session, msisdn: [%s], artifactID: [%s]", msisdn, artifactID));
    HttpServletRequest request = getHttpServletRequest();

    try {
      PersonDTO personDTO = personDAO.getPersonByMsisdn(msisdn);
      if (personDTO != null) {
        log.debug(String.format("Found user [uid=%s] artifactID: [%s], attrs:[%s]", personDTO.getMsisdn(), artifactID, personDTO.toString()));
        HttpSession hSession = request.getSession(true);
        Session session = new Session(artifactID, artifactDomain, hSession.getId(), personDTO.getMsisdn(), personDTO, original);
        // Refresh HttpSession
        hSession.setMaxInactiveInterval((int) this.maxInactiveInterval);
        // Set a refer to SAML session into HttpSession
        hSession.setAttribute(HttpSessionListenerImpl.SAML_SESSION_ID_ATTR_NAME, session.getArtifactID());
        // Cache [SAML Session ID -> HttpSessionID
        this.samlID2HttpSessionIDCache.put(artifactID, hSession.getId());
        // Cache [HttpSessionID -> HttpSession]
        this.httpSessionCache.put(hSession.getId(), hSession);
        // Cache [SAMLSessionID -> SAMLSession]
        this.samlSessionCache.put(artifactID, session);

        // Notify listener
        if (this.sessionListener != null) {
          this.sessionListener.sessionCreated(new SessionEvent(this, session));
        }
        return session;
      }
      throw new SessionManagementException(String.format("Failure to create session, cause: could not found user infor by msisdn: %s", msisdn));
    } catch (Exception e) {
      throw new SessionManagementException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.dao.ContactDAO#updateContact(com.ibm.tivoli.cmcc.dao
   * .ContactDTO)
   */
  public boolean touch(String artifactID) throws SessionManagementException {
    log.debug(String.format("Touch session, artifactId: [%s]", artifactID));
    HttpSession hSession = this.getHttpSessionBySAMLSessionID(artifactID);
    if (hSession != null) {
      log.debug(String.format("Before touching session, artifactID: [%s], Last access time: [%s]", artifactID, new Date(hSession.getLastAccessedTime())));
      hSession.setMaxInactiveInterval((int) (this.maxInactiveInterval + (System.currentTimeMillis() - hSession.getLastAccessedTime()) / 1000));
      Session session = this.get(artifactID);
      if (session != null) {
        // 由于使用Memory Cache的原因, 总是不能修改原有对象的lastAccessTime, 但可以创建一个新的来覆盖
        Session newSession = new Session(session.getArtifactID(), session.getArtifactDomain(), session.getHttpSessionId(), session.getUid(),
            session.getPersonDTO(), session.isOringinal());
        this.update(newSession);
        // Notify listener
        if (this.sessionListener != null) {
          this.sessionListener.sessionTouched(new SessionEvent(this, session));
        }
      }
      log.debug(String.format("After touching session, artifactID: [%s], Last access time: [%s]", artifactID, new Date(hSession.getLastAccessedTime())));
      return true;
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.dao.ContactDAO#updateContact(com.ibm.tivoli.cmcc.dao
   * .ContactDTO)
   */
  public boolean destroy(String artifactID, boolean broadcastToOtherIDPs) throws SessionManagementException {
    log.debug(String.format("Destroy session, artifactId: [%s]", artifactID));
    Session session = this.samlSessionCache.get(artifactID);

    // Notify listener
    if (this.sessionListener != null && session != null) {
       this.sessionListener.beforeSessionDestroyed(new SessionEvent(this, session), broadcastToOtherIDPs);
    }

    HttpSession hSession = this.getHttpSessionBySAMLSessionID(artifactID);
    if (hSession != null) {
      Boolean flag = (Boolean)hSession.getAttribute(HttpSessionListenerImpl.NOT_NEED_TO_INVALIDATE);
      if (flag == null || !flag.booleanValue()) {
         // 判断是否从HttpSessionListener中触发的销毁事件, 如果是, 则无需重复调用invalidate
         hSession.invalidate();
      }
      this.httpSessionCache.remove(hSession.getId());
    }
    // Remove everything
    this.samlID2HttpSessionIDCache.remove(artifactID);
    this.samlSessionCache.remove(artifactID);
    // Notify listener
    if (this.sessionListener != null && session != null) {
       this.sessionListener.afterSessionDestroyed(new SessionEvent(this, session), broadcastToOtherIDPs);
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.cmcc.dao.SessionManager#get(java.lang.String)
   */
  public Session get(String artifactId) throws SessionManagementException {
    log.debug(String.format("Get session, artifactId: [%s]", artifactId));
    Session session = this.samlSessionCache.get(artifactId);
    return session;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.session.SessionManager#update(com.ibm.tivoli.cmcc.session
   * .Session)
   */
  public void update(Session session) throws SessionManagementException {
    if (session != null && StringUtils.isNotEmpty(session.getArtifactID())) {
      this.samlSessionCache.put(session.getArtifactID(), session);
    }
  }

}
