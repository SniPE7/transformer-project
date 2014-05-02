package com.ibm.siam.am.idp.authn.module;

/*
 $Id: CompareAuthenticationHandler.java 1330 2010-05-23 22:10:53Z dfisher $

 Copyright (C) 2003-2010 Virginia Tech.
 All rights reserved.

 SEE LICENSE FOR MORE INFORMATION

 Author:  Middleware Services
 Email:   middleware@vt.edu
 Version: $Revision: 1330 $
 Updated: $Date: 2010-05-23 18:10:53 -0400 (Sun, 23 May 2010) $
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchResult;

import edu.vt.middleware.ldap.LdapConfig;
import edu.vt.middleware.ldap.LdapUtil;
import edu.vt.middleware.ldap.auth.AuthenticatorConfig;
import edu.vt.middleware.ldap.auth.handler.AbstractAuthenticationHandler;
import edu.vt.middleware.ldap.auth.handler.AuthenticationCriteria;
import edu.vt.middleware.ldap.handler.ConnectionHandler;

/**
 * <code>CompareAuthenticationHandler</code> provides an LDAP authentication
 * implementation that leverages a compare operation against the userPassword
 * attribute. The default password scheme used is 'SHA'.
 * 
 * @author Middleware Services
 * @version $Revision: 1330 $
 */
public class CompareAuthenticationHandler extends AbstractAuthenticationHandler {

  /** Maximum digest size. Value is {@value} . */
  private static final int DIGEST_SIZE = 256;

  /** Password scheme. Default value is {@value} . */
  private String passwordScheme = "SHA";

  /** Default constructor. */
  public CompareAuthenticationHandler() {
  }

  /**
   * Creates a new <code>CompareAuthenticationHandler</code> with the supplied
   * authenticator config.
   * 
   * @param ac
   *          authenticator config
   */
  public CompareAuthenticationHandler(final AuthenticatorConfig ac) {
    this.setAuthenticatorConfig(ac);
  }

  /**
   * Returns the password scheme.
   * 
   * @return password scheme
   */
  public String getPasswordScheme() {
    return this.passwordScheme;
  }

  /**
   * Sets the password scheme. Must equal a known message digest algorithm.
   * 
   * @param s
   *          password scheme
   */
  public void setPasswordScheme(final String s) {
    this.passwordScheme = s;
  }

  /** {@inheritDoc} */
  public void authenticate(final ConnectionHandler ch, final AuthenticationCriteria ac) throws NamingException {
    ch.connect(this.config.getBindDn(), this.config.getBindCredential());

    NamingEnumeration<SearchResult> en = null;
    try {
      en = ch.getLdapContext().search(ac.getDn(), "userPassword={0}", new Object[] { (new String((char[])ac.getCredential()).getBytes())},
          LdapConfig.getCompareSearchControls());
      if (!en.hasMore()) {
        throw new AuthenticationException("Compare authentication failed.");
      }
    } finally {
      if (en != null) {
        en.close();
      }
    }
  }

  /** {@inheritDoc} */
  public CompareAuthenticationHandler newInstance() {
    return new CompareAuthenticationHandler(this.config);
  }
}
