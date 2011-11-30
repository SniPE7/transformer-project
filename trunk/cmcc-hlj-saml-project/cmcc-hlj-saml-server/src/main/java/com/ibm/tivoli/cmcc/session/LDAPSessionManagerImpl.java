/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.AttributeModificationException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;

import com.ibm.tivoli.cmcc.dir.PersonAttributeMapper;
import com.ibm.tivoli.cmcc.server.utils.Helper;
import com.ibm.tivoli.cmcc.spi.PersonDTO;

/**
 * @author Zhao Dong Lu
 * 
 */
public class LDAPSessionManagerImpl implements SessionManager {

  private static Log log = LogFactory.getLog(LDAPSessionManagerImpl.class);

  private String url = null;
  private String base = null;
  private String userName = null;
  private String password = null;
  private String ldapCtxFactory = "com.sun.jndi.ldap.LdapCtxFactory";
  
  private String provinceCode = null;

  private LdapTemplate ldapTemplate;

  /**
   * 
   */
  public LDAPSessionManagerImpl() {
    super();
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
   * @param provinceCode the provinceCode to set
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
    String artifactID = Helper.generatorID();
    return create(msisdn, artifactID);
   }

  public Session create(String msisdn, String artifactID) throws SessionManagementException {
    log.debug(String.format("Creating session, msisdn: [%s]", msisdn));
    /*
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = attr.getRequest();
    String username = request.getParameter("User-Name");
    if (StringUtils.isEmpty(username)) {
      throw new RuntimeException("Missing username!");
    }
    */
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
        List<PersonDTO> persons = ldapTemplate.search(base, filter , new PersonAttributeMapper());
        if (persons != null && persons.size() > 0) {
           PersonDTO personDTO  = persons.get(0);
           personDTO.setProvince(this.getProvinceCode());
           log.debug(String.format("found ldap entity [uid=%s] artifactID: [%s], attrs:[%s]", personDTO.getMsisdn(), artifactID, personDTO.toString()));
           return new Session(artifactID, personDTO.getMsisdn(), personDTO);
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.dao.ContactDAO#updateContact(com.ibm.tivoli.cmcc.dao
   * .ContactDTO)
   */
  public boolean touch(String artifactID) throws SessionManagementException {
    log.debug(String.format("Touch session, artifactId: [%s]", artifactID));
    String attributeName = "uniqueIdentifier";

    Hashtable<String, String> env = new Hashtable<String, String>();

    env.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory);

    env.put(Context.PROVIDER_URL, this.getUrl());
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, this.getUserName());
    env.put(Context.SECURITY_CREDENTIALS, this.getPassword());

    ModificationItem[] mods = new ModificationItem[1];

    Attribute mod0 = new BasicAttribute(attributeName, artifactID);
    // Attribute mod1 = new BasicAttribute("st", "AAA");

    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
    // mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod1);

    boolean success = false;
    DirContext ctx = null;
    try {
      ctx = new InitialDirContext(env);
      String filter = "(" + attributeName + "=" + artifactID + ")";
      List<String> entities = ldapTemplate.search(base, filter, new ContextMapper() {

        public Object mapFromContext(Object entity) {
          DirContextAdapter context = (DirContextAdapter) entity;
          String dn = context.getDn().toString();
          return dn;
        }
      });

      if (entities != null && entities.size() > 0) {
        for (String dn : entities) {
          try {
            String targetDN = dn;
            ctx.modifyAttributes(targetDN, mods);
            success = true;
            log.debug("touch ldap attribute, dn:[" + targetDN + "], uniqueIdentifier:[" + artifactID + "]");
          } catch (NamingException e) {
            log.error(e.getMessage(), e);
          }
        }
      } else {
        log.debug("could not found eniity from LDAP, filter: [" + filter + "]");
      }
    } catch (DataAccessException e) {
      throw new SessionManagementException(e);
    } catch (NamingException e) {
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
    return success;
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
    Hashtable<String, String> env = new Hashtable<String, String>();

    env.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory);

    env.put(Context.PROVIDER_URL, this.getUrl());
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, this.getUserName());
    env.put(Context.SECURITY_CREDENTIALS, this.getPassword());

    ModificationItem[] mods = new ModificationItem[1];

    Attribute mod0 = new BasicAttribute("uniqueIdentifier");
    // Attribute mod1 = new BasicAttribute("st", "AAA");

    mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, mod0);
    // mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod1);

    boolean success = false;
    DirContext ctx = null;
    try {
      ctx = new InitialDirContext(env);
      String filter = "(" + "uniqueIdentifier" + "=" + artifactID + ")";
      List<String> entities = ldapTemplate.search(base, filter, new ContextMapper() {

        public Object mapFromContext(Object entity) {
          DirContextAdapter context = (DirContextAdapter) entity;
          String dn = context.getDn().toString();
          return dn;
        }
      });

      if (entities != null && entities.size() > 0) {
        for (String dn : entities) {
          try {
            String targetDN = dn;
            ctx.modifyAttributes(targetDN, mods);
            success = true;
            log.debug("delete ldap attribute, dn:[" + targetDN + "], uniqueIdentifier:[" + artifactID + "]");
          } catch (NamingException e) {
            log.error(e.getMessage(), e);
          }
        }
      } else {
        log.debug("could not found eniity from LDAP, filter: [" + filter + "]");
      }
    } catch (DataAccessException e) {
      throw new SessionManagementException(e);
    } catch (NamingException e) {
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
    return success;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.dao.SessionManager#get(java.lang.String)
   */
  public Session get(String artifactId) throws SessionManagementException {
    log.debug(String.format("Get session, artifactId: [%s]", artifactId));
    try {
      String filter = "(uniqueIdentifier=" + artifactId + ")";
      List<PersonDTO> persons = ldapTemplate.search(base, filter , new PersonAttributeMapper());
      if (persons != null && persons.size() > 0) {
         PersonDTO personDTO  = persons.get(0);
         personDTO.setProvince(this.getProvinceCode());
         log.debug("found ldap entity [uid=" + personDTO.getMsisdn() + "] by samlID: " + artifactId);
         return new Session(personDTO);
      }
    } catch (Exception e) {
      throw new SessionManagementException(e);
    }
    return null;
  }
}
