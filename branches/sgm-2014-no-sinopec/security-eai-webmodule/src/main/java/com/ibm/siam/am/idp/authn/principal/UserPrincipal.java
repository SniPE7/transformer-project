package com.ibm.siam.am.idp.authn.principal;

import java.security.Principal;

import org.opensaml.xml.util.DatatypeHelper;

/**
 * TAM LDAP Principal
 * 
 * @author zhangxianwen
 * @since 2012-6-15 ÉÏÎç11:49:27
 */

public class UserPrincipal implements Principal {

  private String name;

  /**
   * 
   */
  public UserPrincipal() {
    super();
  }

  /**
   * @param name
   */
  public UserPrincipal(String name) {
    super();
    this.name = name;
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof UserPrincipal)) {
      return false;
    }

    UserPrincipal principal = (UserPrincipal) obj;

    if (!DatatypeHelper.safeEquals(name, principal.name)) {
      return false;
    }

    return true;
  }

  public void setName(String name) {
    this.name = name;
  }

  /** {@inheritDoc} */
  public String getName() {
    return name;
  }

}
