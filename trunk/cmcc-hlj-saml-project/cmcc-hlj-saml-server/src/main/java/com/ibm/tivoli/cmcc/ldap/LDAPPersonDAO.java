/**
 * 
 */
package com.ibm.tivoli.cmcc.ldap;

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
import org.springframework.ldap.ContextMapper;
import org.springframework.ldap.LdapTemplate;
import org.springframework.ldap.support.DirContextAdapter;

import com.ibm.tivoli.cmcc.server.utils.Helper;

/**
 * @author Zhao Dong Lu
 * 
 */
public class LDAPPersonDAO implements PersonDAO {

  private static Log log = LogFactory.getLog(LDAPPersonDAO.class);

  private String url = null;
  private String base = null;
  private String userName = null;
  private String password = null;
  private String ldapCtxFactory = "com.sun.jndi.ldap.LdapCtxFactory";

  private LdapTemplate ldapTemplate;

  /**
   * 
   */
  public LDAPPersonDAO() {
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

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.cmcc.ldap.ContactDAO#getAllContactNames()
   */
  public List<PersonDTO> searchPerson(String base, String filter) {
    return ldapTemplate.search(base, filter, new PersonAttributeMapper());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.ldap.ContactDAO#updateContact(com.ibm.tivoli.cmcc.ldap
   * .ContactDTO)
   */
  public boolean updatePassword(String base, String filter, String password) {
    String attributeName = "userPassword";

    Hashtable<String, String> env = new Hashtable<String, String>();

    env.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory);

    env.put(Context.PROVIDER_URL, this.getUrl());
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, this.getUserName());
    env.put(Context.SECURITY_CREDENTIALS, this.getPassword());

    ModificationItem[] mods = new ModificationItem[1];

    Attribute mod0 = new BasicAttribute(attributeName, password);
    // Attribute mod1 = new BasicAttribute("st", "AAA");

    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
    // mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod1);

    boolean success = false;
    try {
      DirContext ctx = new InitialDirContext(env);
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
            String targetDN = dn + ", " + this.base;
            ctx.modifyAttributes(targetDN, mods);
            success = true;
            log.debug("Reset password, dn:[" + targetDN + "], userPassword:[" + password + "]");
          } catch (NamingException e) {
            log.error(e.getMessage(), e);
          }
        }
      } else {
        log.debug("could not found eniity from LDAP, filter: [" + filter + "]");
      }
    } catch (DataAccessException e) {
      log.error(e.getMessage(), e);
    } catch (NamingException e) {
      log.error(e.getMessage(), e);
    }
    return success;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.ldap.ContactDAO#updateContact(com.ibm.tivoli.cmcc.ldap
   * .ContactDTO)
   */
  public boolean updateUniqueIdentifier(String base, String filter, String uniqueIdentifier) {
    String attributeName = "uniqueIdentifier";

    Hashtable<String, String> env = new Hashtable<String, String>();

    env.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory);

    env.put(Context.PROVIDER_URL, this.getUrl());
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, this.getUserName());
    env.put(Context.SECURITY_CREDENTIALS, this.getPassword());

    ModificationItem[] mods = new ModificationItem[1];

    Attribute mod0 = new BasicAttribute(attributeName, uniqueIdentifier);
    // Attribute mod1 = new BasicAttribute("st", "AAA");

    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
    // mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod1);

    boolean success = false;
    try {
      DirContext ctx = new InitialDirContext(env);
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
            String targetDN = dn + ", " + this.base;
            ctx.modifyAttributes(targetDN, mods);
            success = true;
            log.debug("touch ldap attribute, dn:[" + targetDN + "], uniqueIdentifier:[" + uniqueIdentifier + "]");
          } catch (NamingException e) {
            log.error(e.getMessage(), e);
          }
        }
      } else {
        log.debug("could not found eniity from LDAP, filter: [" + filter + "]");
      }
    } catch (DataAccessException e) {
      log.error(e.getMessage(), e);
    } catch (NamingException e) {
      log.error(e.getMessage(), e);
    }
    return success;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.ldap.ContactDAO#updateContact(com.ibm.tivoli.cmcc.ldap
   * .ContactDTO)
   */
  public boolean deleteUniqueIdentifier(String base, String filter, String uniqueIdentifier) {
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
    try {
      DirContext ctx = new InitialDirContext(env);
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
            String targetDN = dn + ", " + this.base;
            ctx.modifyAttributes(targetDN, mods);
            success = true;
            log.debug("delete ldap attribute, dn:[" + targetDN + "], uniqueIdentifier:[" + uniqueIdentifier + "]");
          } catch (NamingException e) {
            log.error(e.getMessage(), e);
          }
        }
      } else {
        log.debug("could not found eniity from LDAP, filter: [" + filter + "]");
      }
    } catch (DataAccessException e) {
      log.error(e.getMessage(), e);
    } catch (NamingException e) {
      log.error(e.getMessage(), e);
    }
    return success;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.ldap.ContactDAO#updateContact(com.ibm.tivoli.cmcc.ldap
   * .ContactDTO)
   */
  public String insertUniqueIdentifier(String base, String msisdn, String uniqueIdentifier) throws Exception {
    Hashtable<String, String> env = new Hashtable<String, String>();

    env.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory);

    env.put(Context.PROVIDER_URL, this.getUrl());
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, this.getUserName());
    env.put(Context.SECURITY_CREDENTIALS, this.getPassword());

    boolean success = false;
    DirContext ctx = new InitialDirContext(env);
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
        String targetDN = dn + ", " + this.base;
        try {
          // Modify attribute
          Attribute attr = new BasicAttribute("uniqueIdentifier", uniqueIdentifier);
          ctx.modifyAttributes(targetDN, new ModificationItem[] { new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr) });
        } catch (AttributeModificationException e) {
          // Retry add attribute
          Attribute attr = new BasicAttribute("uniqueIdentifier", uniqueIdentifier);
          ctx.modifyAttributes(targetDN, new ModificationItem[] { new ModificationItem(DirContext.ADD_ATTRIBUTE, attr) });
        }
        success = true;
        log.debug("add or update ldap attribute, dn:[" + targetDN + "], uniqueIdentifier:[" + uniqueIdentifier + "]");
      }
      return uniqueIdentifier;
    } else {
      throw new Exception("could not found eniity from LDAP, filter: [" + filter + "]");
    }
   }
}
