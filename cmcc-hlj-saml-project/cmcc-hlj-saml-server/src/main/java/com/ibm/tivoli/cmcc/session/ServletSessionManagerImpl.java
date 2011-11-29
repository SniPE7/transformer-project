/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.AttributeModificationException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ibm.tivoli.cmcc.dir.PersonAttributeMapper;
import com.ibm.tivoli.cmcc.server.utils.Helper;
import com.ibm.tivoli.cmcc.spi.PersonDTO;

/**
 * @author Zhao Dong Lu
 * 
 */
public class ServletSessionManagerImpl implements SessionManager {

  private static Log log = LogFactory.getLog(ServletSessionManagerImpl.class);

  public static final String SAML_SESSION_ATTR_NAME = "SAML_SESSION_ATTR_NAME";

  private String url = null;
  private String base = null;
  private String userName = null;
  private String password = null;
  private String ldapCtxFactory = "com.sun.jndi.ldap.LdapCtxFactory";

  private String provinceCode = null;

  private LdapTemplate ldapTemplate;

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

  public void setLdapTemplate(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  public String getLdapCtxFactory() {
    return ldapCtxFactory;
  }

  public void setLdapCtxFactory(String ldapCtxFactory) {
    this.ldapCtxFactory = ldapCtxFactory;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LdapTemplate getLdapTemplate() {
    return ldapTemplate;
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.dao.ContactDAO#updateContact(com.ibm.tivoli.cmcc.dao
   * .ContactDTO)
   */
  public Session create(String msisdn) throws SessionManagementException {
    log.debug(String.format("Creating session, msisdn: [%s]", msisdn));
    HttpServletRequest request = getHttpServletRequest();

    Hashtable<String, String> env = new Hashtable<String, String>();
    env.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory);
    env.put(Context.PROVIDER_URL, this.getUrl());
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, this.getUserName());
    env.put(Context.SECURITY_CREDENTIALS, this.getPassword());

    boolean success = false;
    DirContext ctx = null;
    try {
      ctx = new InitialDirContext(env);
      String filter = "(uid=" + msisdn + ")";
      List<String> entities = ldapTemplate.search(base, filter, new ContextMapper() {

        public Object mapFromContext(Object entity) {
          DirContextAdapter context = (DirContextAdapter) entity;
          String dn = context.getDn().toString();
          return dn;
        }
      });

      String artifactID = Helper.generatorID();
      if (entities != null && entities.size() > 0) {
        for (String dn : entities) {
          String targetDN = dn;
          try {
            // Modify attribute
            Attribute attr = new BasicAttribute("uniqueIdentifier", artifactID);
            ctx.modifyAttributes(targetDN, new ModificationItem[] { new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr) });
          } catch (AttributeModificationException e) {
            // Retry add attribute
            Attribute attr = new BasicAttribute("uniqueIdentifier", artifactID);
            ctx.modifyAttributes(targetDN, new ModificationItem[] { new ModificationItem(DirContext.ADD_ATTRIBUTE, attr) });
          }
          success = true;
          log.debug("add or update ldap attribute, dn:[" + targetDN + "], uniqueIdentifier:[" + artifactID + "]");
          log.debug(String.format("Created a session, msisdn: [%s], artifactID: [%s]", msisdn, artifactID));
        }

        // Load Person DTO
        List<PersonDTO> persons = ldapTemplate.search(base, filter, new PersonAttributeMapper());
        if (persons != null && persons.size() > 0) {
          PersonDTO personDTO = persons.get(0);
          personDTO.setProvince(this.getProvinceCode());
          log.debug(String.format("found ldap entity [uid=%s] artifactID: [%s], attrs:[%s]", personDTO.getMsisdn(), artifactID, personDTO.toString()));
          Session session = new Session(artifactID, personDTO.getMsisdn(), personDTO);
          HttpSession hSession = request.getSession(true);
          hSession.setMaxInactiveInterval((int) this.maxInactiveInterval);
          hSession.setAttribute(SAML_SESSION_ATTR_NAME, session);
          this.sessionMap.put(artifactID, hSession);
          return session;
        }
        return null;
      } else {
        throw new Exception("could not found entity from LDAP, filter: [" + filter + "]");
      }
    } catch (NamingException e) {
      throw new SessionManagementException(e);
    } catch (Exception e) {
      throw new SessionManagementException(e);
    } finally {
      if (ctx != null) {
        try {
          ctx.close();
        } catch (NamingException e) {
          throw new SessionManagementException(e);
        }
      }
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
