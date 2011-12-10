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
  public PersonDTO getPersonByMsisdn(String msisdn) {
    log.debug(String.format("Get detail user info: [msisdn]", msisdn));
    String filter = "(uid=" + msisdn + ")";
    List<PersonDTO> persons = ldapTemplate.search(base, filter, new PersonAttributeMapper());
    if (persons != null && persons.size() > 0) {
      PersonDTO personDTO = persons.get(0);
      log.debug(String.format("Got detail info: %s", personDTO.toString()));
      return personDTO;
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.dao.ContactDAO#updateContact(com.ibm.tivoli.cmcc.dao
   * .ContactDTO)
   */
  public boolean updatePassword(String msisdn, String serviceCode, String networkPassword) {
    if (serviceCode == null || serviceCode.length() < 6) {
      // 检查密码策略
      throw new RuntimeException("无效服务代码!");
    }
    if (networkPassword == null || networkPassword.length() < 6) {
      // 检查密码策略
      throw new RuntimeException("密码强度不足!");
    }
    String attributeName = "userPassword";

    Hashtable<String, String> env = new Hashtable<String, String>();

    env.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory);

    env.put(Context.PROVIDER_URL, this.getUrl());
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, this.getUserName());
    env.put(Context.SECURITY_CREDENTIALS, this.getPassword());

    ModificationItem[] mods = new ModificationItem[1];
    Attribute mod0 = new BasicAttribute(attributeName, networkPassword);
    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);

    boolean success = false;
    DirContext ctx = null;
    try {
      log.debug(String.format("Verify service password, msisdn: [%s], service password: [%s]", msisdn, serviceCode));
      ctx = new InitialDirContext(env);
      AndFilter filter = new AndFilter();
      filter.and(new EqualsFilter("uid", msisdn));
      filter.and(new EqualsFilter("erhljmccServiceCode", serviceCode));
      List<String> entities = ldapTemplate.search(base, filter.encode(), new ContextMapper() {

        public Object mapFromContext(Object entity) {
          DirContextAdapter context = (DirContextAdapter) entity;
          String dn = context.getDn().toString();
          return dn;
        }
      });
      
      if (entities == null || entities.size() == 0) {
        throw new RuntimeException("服务代码错误或手机号不存在!");
      }

      if (entities != null && entities.size() > 0) {
        for (String dn : entities) {
          try {
            String targetDN = dn;
            ctx.modifyAttributes(targetDN, mods);
            success = true;
            log.debug(String.format("Update network password, msisdn: [%s], service password: [%s]", msisdn, networkPassword));
          } catch (NamingException e) {
            log.error(e.getMessage(), e);
          }
        }
      } else {
        log.debug("could not found eniity for reset password, filter: [" + filter + "]");
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

    return (String) ldapTemplate.searchForObject("", filter.encode(), new AbstractContextMapper() {

      @Override
      protected Object doMapFromContext(DirContextOperations ctx) {
        return ctx.getNameInNamespace();
      }
    });
  }

  private boolean verifyPassword(String msisdn, String passwordType, char[] password) throws Exception {
    log.debug(String.format("Check mobile user password, msisdn: [%s], passwordType: [%s]", msisdn, passwordType));
    DirContext ctx = null;
    try {
      ctx = ldapTemplate.getContextSource().getReadOnlyContext();
      if ("1".equals(passwordType)) {
        log.debug(String.format("Check mobile user network password, msisdn: [%s], passwordType: [%s]", msisdn, passwordType));
        // Verify network password
        SearchControls cons = new SearchControls();
        cons.setReturningAttributes(new String[0]); // Return no attrs
        cons.setSearchScope(SearchControls.OBJECT_SCOPE); // Search object only

        // Get User DN by MSISDN
        String userDn = this.searchUserDNByMsisdn(msisdn);
        byte[] bytes = (password != null) ? new String(password).getBytes("iso8859-1") : new byte[0];
        String filterPattern = ("1".equals(passwordType)) ? "(userPassword={0})" : "(userPassword={0})";
        NamingEnumeration answer = ctx.search(userDn, filterPattern, new Object[] { bytes }, cons);
        if (answer == null || !answer.hasMoreElements()) {
          throw LdapUtils.convertLdapException(new NamingException("Wrong password"));
        }
        answer.close();
        return true;
      } else {
        // Verify service code
        log.debug(String.format("Check mobile user service password, msisdn: [%s], passwordType: [%s]", msisdn, passwordType));
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("uid", msisdn));
        filter.and(new EqualsFilter("erhljmccServiceCode", new String(password)));
        List<String> entities = ldapTemplate.search(base, filter.encode(), new ContextMapper() {

          public Object mapFromContext(Object entity) {
            DirContextAdapter context = (DirContextAdapter) entity;
            String dn = context.getDn().toString();
            return dn;
          }
        });
        if (entities != null && entities.size() > 0) {
          return true;
        }        
      }
      return false;
    } catch (NamingException e) {
      throw LdapUtils.convertLdapException(e);
    } catch (Exception e) {
      throw e;
    } finally {
      LdapUtils.closeContext(ctx);
    }
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.spi.PersonDAO#verifyPasswordAndQueryUserInfo(java.lang.String, java.lang.String, char[])
   */
  public PersonDTO verifyPasswordAndQueryUserInfo(String msisdn, String passwordType, char[] password) throws Exception {
    log.debug(String.format("Check mobile user password, msisdn: [%s], passwordType: [%s]", msisdn, passwordType));
    if (this.verifyPassword(msisdn, passwordType, password)) {
       return this.getPersonByMsisdn(msisdn);
    }
    return null;
  }
}
