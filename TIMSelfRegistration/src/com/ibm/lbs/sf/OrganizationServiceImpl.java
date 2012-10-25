/**
 * 
 */
package com.ibm.lbs.sf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * @author Administrator
 * 
 */
public class OrganizationServiceImpl implements OrganizationService {

  private String ldapUrl = "ldap://127.0.0.1:3890";
  private String ldapBaseDn = "ou=jke, DC=ITIM";
  private String ldapBindDn = null;
  private String ldapBindPassword = null;

  /**
   * 
   */
  public OrganizationServiceImpl() {
    super();
  }

  public OrganizationServiceImpl(String ldapUrl, String ldapBaseDn, String ldapBindDn, String ldapBindPassword) {
    super();
    this.ldapUrl = ldapUrl;
    this.ldapBaseDn = ldapBaseDn;
    this.ldapBindDn = ldapBindDn;
    this.ldapBindPassword = ldapBindPassword;
  }

  public String getLdapUrl() {
    return ldapUrl;
  }

  public void setLdapUrl(String ldapUrl) {
    this.ldapUrl = ldapUrl;
  }

  public String getLdapBaseDn() {
    return ldapBaseDn;
  }

  public void setLdapBaseDn(String ldapBaseDn) {
    this.ldapBaseDn = ldapBaseDn;
  }

  public String getLdapBindDn() {
    return ldapBindDn;
  }

  public void setLdapBindDn(String ldapBindDn) {
    this.ldapBindDn = ldapBindDn;
  }

  public String getLdapBindPassword() {
    return ldapBindPassword;
  }

  public void setLdapBindPassword(String ldapBindPassword) {
    this.ldapBindPassword = ldapBindPassword;
  }

  /**
   * @param r
   * @param attrID
   * @return
   * @throws NamingException
   */
  private String getAttributeValueAsSingleValue(SearchResult r, String attrID) throws NamingException {
    Attribute attribute = r.getAttributes().get(attrID);
    if (attribute != null && attribute.size() > 0) {
      String serviceName = attribute.get(0).toString();
      return serviceName;
    } else {
      return null;
    }
  }

  private List<Organization> getAllOrgs(DirContext ctx) throws NamingException {
    Map<String, Organization> orgsIndexMap = new HashMap<String, Organization>();

    List<Organization> result = new ArrayList<Organization>();
    SearchControls searchControls = new SearchControls();
    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    searchControls.setReturningAttributes(new String[] { "l", "ou", "o", "erparent", "erglobalid" });
    NamingEnumeration<SearchResult> i = ctx.search(this.ldapBaseDn, "(|(objectclass=erOrganizationItem)(objectclass=erOrgUnitItem)(objectclass=erLocationItem))", searchControls);
    while (i != null && i.hasMore()) {
      SearchResult r = i.next();
      String dn = r.getNameInNamespace();
      String erglobalId = getAttributeValueAsSingleValue(r, "erglobalid");
      String erparent = getAttributeValueAsSingleValue(r, "erparent");
      String name = getAttributeValueAsSingleValue(r, "ou");
      if (name == null) {
         name = this.getAttributeValueAsSingleValue(r, "l");
      }
      if (name == null) {
        name = this.getAttributeValueAsSingleValue(r, "o");
      }
      Organization o = new Organization();
      o.setDn(dn);
      o.setId(erglobalId);
      o.setName(name);
      
      Organization parrent = orgsIndexMap.get(erparent);
      if (parrent != null) {
         o.setParrent(parrent);
      } else {
        result.add(o);
      }
      
      orgsIndexMap.put(o.getDn(), o);
    }
    return result;
  }

  /**
   * @return
   * @throws NamingException
   */
  private DirContext getDirContext() throws NamingException {
    // Set up the environment for creating the initial context
    Hashtable<String, String> env = new Hashtable<String, String>();
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, this.ldapUrl);
    env.put("com.sun.jndi.ldap.connect.pool", "true");
    if (this.ldapBindDn != null) {
      env.put(Context.SECURITY_AUTHENTICATION, "simple");
      env.put(Context.SECURITY_PRINCIPAL, this.ldapBindDn);
      env.put(Context.SECURITY_CREDENTIALS, this.ldapBindPassword);
    }

    // Create the initial context
    DirContext ctx = new InitialDirContext(env);
    return ctx;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.lbs.sf.OrgnizationService#getAllOrganization()
   */
  @Override
  public List<Organization> getAllOrganization() throws Exception {
    DirContext ctx = this.getDirContext();
    List<Organization> result = getAllOrgs(ctx);
    ctx.close();
    return result;
  }

}
