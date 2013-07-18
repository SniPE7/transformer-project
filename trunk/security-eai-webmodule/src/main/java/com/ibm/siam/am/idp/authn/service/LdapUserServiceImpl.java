package com.ibm.siam.am.idp.authn.service;

import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.NameClassPairCallbackHandler;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;

import com.sinopec.siam.am.idp.authn.service.UserService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;
import com.sinopec.siam.am.idp.ldap.DirectoryEntityContextMapper;

/**
 * TAM LDAP 用户服务类
 * 
 * @author zhangxianwen
 * @since 2012-6-15 上午10:00:49
 */

public class LdapUserServiceImpl implements UserService {

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(LdapUserServiceImpl.class);

  private LdapTemplate template;

  private String userBaseDN;

  /**
   * @return the template
   */
  public LdapTemplate getTemplate() {
    return template;
  }

  /**
   * @param template
   *          the template to set
   */
  public void setTemplate(LdapTemplate template) {
    this.template = template;
  }

  /**
   * @return the userBaseDN
   */
  public String getUserBaseDN() {
    return userBaseDN;
  }

  /**
   * @param userBaseDN
   *          the userBaseDN to set
   */
  public void setUserBaseDN(String userBaseDN) {
    this.userBaseDN = userBaseDN;
  }

  /** {@inheritDoc} */
  public List<LdapUserEntity> searchByUid(String uid) {
    log.debug(String.format("search user by uid[%s]", uid));
    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("uid", uid));
    return searchByFilter(andFilter.encode());
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  public List<LdapUserEntity> searchByFilter(String filter) {
    log.debug(String.format("search user by filter[%s]", filter));
    return template.search(userBaseDN, filter, new DirectoryEntityContextMapper(LdapUserEntity.class));
  }

  /** {@inheritDoc} */
  public boolean authenticateByUserDnPassword(String userDn, String password) {
    log.debug(String.format("authenticate user by userDn[%s] password[%s]", userDn, password));
    
    // Execute authentication with LDAP Compare Mode
    SearchControls cons = new SearchControls();
    cons.setReturningAttributes(new String[0]); // Return no attrs
    cons.setSearchScope(SearchControls.OBJECT_SCOPE); // Search object only

    try {
	    NamingEnumeration answer = this.template.getContextSource().getReadOnlyContext().search(userDn, "(userpassword={0})", new Object[] { (new String(password)).getBytes() }, cons);
	    if (answer != null && answer.hasMoreElements()) {
	    	return true;
	    }
    } catch (org.springframework.ldap.NamingException e) {
	    log.error(String.format("fail to compare password for authentication: [%s], cause[%s]", userDn, e.getMessage()), e);
    } catch (NamingException e) {
	    log.error(String.format("fail to compare password for authentication: [%s], cause[%s]", userDn, e.getMessage()), e);
    }
    return false;
  }

}
