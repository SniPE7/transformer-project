/**
 * 
 */
package com.ibm.tivoli.cmcc.dir;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Zhao Dong Lu
 * 
 */
public class LDAPHelper {

  private static Log log = LogFactory.getLog(LDAPHelper.class);

  private String url = "ldap://10.110.21.58:389";
  private String base = "dc=hljcmcc";
  private String userName = "cn=root";
  private String password = "passw0rd";
  private String ldapCtxFactory = "com.sun.jndi.ldap.LdapCtxFactory";

  /**
   * 
   */
  public LDAPHelper() {
    super();
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

  /**
   * @param msisdn
   * @param serviceCode
   * @param networkPassword
   * @return
   * @throws NamingException
   */
  public boolean updatePassword(String msisdn, String serviceCode, String networkPassword) throws NamingException {
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
      ctx = new InitialDirContext(env);
      boolean checkingServiceCode = this.checkPassword(ctx, msisdn, "2", serviceCode.toCharArray());
      if (checkingServiceCode) {
        String targetDN = getUserDNByMsisdn(msisdn);
        ctx.modifyAttributes(targetDN, mods);
        success = true;
        log.debug(String.format("Update network password, msisdn: [%s], service password: [%s]", msisdn, networkPassword));
      }
    } catch (NamingException e) {
      throw e;
    } catch (UnsupportedEncodingException e) {
      throw new NamingException(e.getMessage());
    } finally {
      if (ctx != null) {
        ctx.close();
      }
    }
    return success;
  }

  private String getUserDNByMsisdn(String msisdn) {
    String dn = "cn=" + msisdn + ",cn=user," + this.base;
    return dn;
  }

  /**
   * @param msisdn
   * @param passwordType
   *          1：互联网密码, 2：服务密码
   * @param password
   * @return
   * @throws Exception
   */
  public boolean checkMobileUserPassword(String msisdn, String passwordType, char[] password) throws Exception {
    log.debug(String.format("Check mobile user password, msisdn: [%s], passwordType: [%s]", msisdn, passwordType));
    Hashtable<String, String> env = new Hashtable<String, String>();

    env.put(Context.INITIAL_CONTEXT_FACTORY, ldapCtxFactory);

    env.put(Context.PROVIDER_URL, this.getUrl());
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, this.getUserName());
    env.put(Context.SECURITY_CREDENTIALS, this.getPassword());
    DirContext ctx = null;
    try {
      ctx = new InitialDirContext(env);
      return checkPassword(ctx, msisdn, passwordType, password);
    } catch (NamingException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    } finally {
      if (ctx != null) {
        ctx.close();
      }
    }
  }

  private boolean checkPassword(DirContext ctx, String msisdn, String passwordType, char[] password) throws UnsupportedEncodingException, NamingException {
    if ("1".equals(passwordType)) {
      log.debug(String.format("Check mobile user network password, msisdn: [%s], passwordType: [%s]", msisdn, passwordType));
      // Verify network password
      SearchControls cons = new SearchControls();
      cons.setReturningAttributes(new String[0]); // Return no attrs
      cons.setSearchScope(SearchControls.OBJECT_SCOPE); // Search object only

      // Get User DN by MSISDN
      String userDn = this.getUserDNByMsisdn(msisdn);
      byte[] bytes = (password != null) ? new String(password).getBytes("iso8859-1") : new byte[0];
      String filterPattern = ("1".equals(passwordType)) ? "(userPassword={0})" : "(userPassword={0})";
      NamingEnumeration answer = ctx.search(userDn, filterPattern, new Object[] { bytes }, cons);
      if (answer == null || !answer.hasMoreElements()) {
        throw new NamingException("Wrong password");
      }
      answer.close();
      return true;
    } else {
      // Verify service code
      log.debug(String.format("Check mobile user service password, msisdn: [%s], passwordType: [%s]", msisdn, passwordType));
      String filter = "(&(uid=" + msisdn + ")(erhljmccServiceCode=" + new String(password) + "))";
      SearchControls sc = new SearchControls();
      sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
      NamingEnumeration<SearchResult> entities = ctx.search(base, filter, sc);
      if (entities != null && entities.hasMore()) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) throws Exception {
    LDAPHelper h = new LDAPHelper();
    boolean ok = h.checkMobileUserPassword("13804511234", "2", "123456".toCharArray());
    System.out.println("Check service code: " + ok);

    ok = h.updatePassword("13804511234", "123456", "666666");
    System.out.println("Update password: " + ok);

    ok = h.checkMobileUserPassword("13804511234", "1", "666666".toCharArray());
    System.out.println("Check network password: " + ok);

    ok = h.updatePassword("13804511234", "222222", "666666");
    System.out.println("Update password: " + ok);

    ok = h.checkMobileUserPassword("13804511234", "2", "666666".toCharArray());
    System.out.println("Check service code: " + ok);
  }

}
