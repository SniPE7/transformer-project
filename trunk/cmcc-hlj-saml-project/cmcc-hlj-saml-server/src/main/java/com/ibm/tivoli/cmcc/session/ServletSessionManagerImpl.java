/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ibm.tivoli.cmcc.server.utils.Helper;
import com.ibm.tivoli.cmcc.spi.PersonDAO;
import com.ibm.tivoli.cmcc.spi.PersonDTO;

/**
 * @author Zhao Dong Lu
 * 
 */
public class ServletSessionManagerImpl implements SessionManager {

  private static Log log = LogFactory.getLog(ServletSessionManagerImpl.class);

  public static final String SAML_SESSION_ATTR_NAME = "SAML_SESSION_ATTR_NAME";

  private PersonDAO personDAO = null;
  
  private String provinceCode = null;

  private Map<String, HttpSession> sessionMap = new ConcurrentHashMap<String, HttpSession>();

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
   * @return the provinceCode
   */
  public String getProvinceCode() {
    return provinceCode;
  }

  /**
   * @param provinceCode
   *          the provinceCode to set
   */
  public void setProvinceCode(String provinceCode) {
    this.provinceCode = provinceCode;
  }

  /**
   * @return the personDAO
   */
  public PersonDAO getPersonDAO() {
    return personDAO;
  }

  /**
   * @param personDAO the personDAO to set
   */
  public void setPersonDAO(PersonDAO personDAO) {
    this.personDAO = personDAO;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.dao.ContactDAO#updateContact(com.ibm.tivoli.cmcc.dao
   * .ContactDTO)
   */
  public Session create(String msisdn) throws SessionManagementException {
    String artifactID = Helper.generatorID();

    return create(msisdn, artifactID);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.cmcc.session.SessionManager#create(java.lang.String,
   * java.lang.String)
   */
  public Session create(String msisdn, String artifactID) throws SessionManagementException {
    log.debug(String.format("Creating session, msisdn: [%s], artifactID: [%s]", msisdn, artifactID));
    HttpServletRequest request = getHttpServletRequest();

    try {
      PersonDTO personDTO = personDAO.getPersonByMsisdn(msisdn);
      if (personDTO != null) {
          personDTO.setProvince(this.getProvinceCode());
          log.debug(String.format("found ldap entity [uid=%s] artifactID: [%s], attrs:[%s]", personDTO.getMsisdn(), artifactID, personDTO.toString()));
          Session session = new Session(artifactID, personDTO.getMsisdn(), personDTO);
          HttpSession hSession = request.getSession(true);
          hSession.setMaxInactiveInterval((int) this.maxInactiveInterval);
          hSession.setAttribute(SAML_SESSION_ATTR_NAME, session);
          this.sessionMap.put(artifactID, hSession);
          return session;
      }
      throw new SessionManagementException(String.format("Failure to create session, cause: could not found user infor by msisdn: %s", msisdn));
    } catch (Exception e) {
      throw new SessionManagementException(e);
    } finally {
    }
  }

  private HttpServletRequest getHttpServletRequest() {
    ServletRequestAttributes srAttr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = srAttr.getRequest();
    return request;
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
    HttpSession hSession = this.sessionMap.get(artifactID);
    if (hSession != null) {
      log.debug(String.format("Before touching session, artifactID: [%s], Last access time: [%s]", artifactID, new Date(hSession.getLastAccessedTime())));
      hSession.setMaxInactiveInterval((int) (this.maxInactiveInterval + (System.currentTimeMillis() - hSession.getLastAccessedTime()) / 1000));
      hSession.setAttribute("dummy", "");
      Session session = (Session) hSession.getAttribute(SAML_SESSION_ATTR_NAME);
      session.touch();
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
  public boolean destroy(String artifactID) throws SessionManagementException {
    log.debug(String.format("Destroy session, artifactId: [%s]", artifactID));
    HttpSession hSession = this.sessionMap.get(artifactID);
    if (hSession != null) {
      if (this.sessionMap.containsKey(artifactID)) {
        this.sessionMap.remove(artifactID);
      }
      hSession.invalidate();
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.cmcc.dao.SessionManager#get(java.lang.String)
   */
  public Session get(String artifactId) throws SessionManagementException {
    log.debug(String.format("Destroy session, artifactId: [%s]", artifactId));
    HttpSession hSession = this.sessionMap.get(artifactId);
    if (hSession != null) {
      Session session = (Session) hSession.getAttribute(SAML_SESSION_ATTR_NAME);
      return session;
    }
    return null;
  }

}
