/**
 * 
 */
package com.ibm.tivoli.cmcc.dir;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;

import com.ibm.tivoli.cmcc.spi.PersonDAO;
import com.ibm.tivoli.cmcc.spi.PersonDTO;

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
   * @see com.ibm.tivoli.cmcc.dao.ContactDAO#getAllContactNames()
   */
  public List<PersonDTO> searchPerson(String filter) {
    return ldapTemplate.search(base, filter, new PersonAttributeMapper());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.dao.ContactDAO#updateContact(com.ibm.tivoli.cmcc.dao
   * .ContactDTO)
   */
  public boolean updatePassword(String msisdn, String password) {
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
          try {
            String targetDN = dn;
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
      throw LdapUtils.convertLdapException(e);
    } finally {
      LdapUtils.closeContext(ctx);
    }
    return success;
  }
  
  private String searchUserDNByMsisdn(String msisdn) {
    AndFilter filter = new AndFilter();
    
    filter.and(new EqualsFilter("uid", msisdn));
    
    return (String) ldapTemplate.searchForObject("", filter.encode(),
        new AbstractContextMapper() {
          
          @Override
          protected Object doMapFromContext(DirContextOperations ctx) {
            return ctx.getNameInNamespace();
          }
        }
    );
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.dao.SessionManager#checkMobileUserPassword(java.lang.String, java.lang.String, char[])
   */
  public boolean checkMobileUserPassword(String msisdn, String passwordType, char[] password) throws Exception {
    
    DirContext ctx = null;
    try {
      SearchControls cons = new SearchControls();
      cons.setReturningAttributes(new String[0]);       // Return no attrs
      cons.setSearchScope(SearchControls.OBJECT_SCOPE); // Search object only

      ctx = ldapTemplate.getContextSource().getReadOnlyContext();
      // Get User DN by MSISDN
      String userDn = this.searchUserDNByMsisdn(msisdn);
      byte[] bytes = (password != null)?new String(password).getBytes("iso8859-1"):new byte[0];
      String filterPattern = ("1".equals(passwordType))?"(userPassword={0})":"(userPassword={0})";
      NamingEnumeration answer = ctx.search(userDn, filterPattern, new Object[]{bytes}, cons);
      if(answer == null || !answer.hasMoreElements()){
        throw LdapUtils.convertLdapException(new NamingException("Wrong password"));
      }
      answer.close();
      return true;
    } catch (NamingException e) {
      throw LdapUtils.convertLdapException(e);
    } catch (Exception e) {
      throw e;
    } finally {
      LdapUtils.closeContext(ctx);
    }
  }
}
