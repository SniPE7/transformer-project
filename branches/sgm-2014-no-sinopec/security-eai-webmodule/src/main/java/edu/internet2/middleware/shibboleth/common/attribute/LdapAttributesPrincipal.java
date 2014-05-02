package edu.internet2.middleware.shibboleth.common.attribute;

import java.security.Principal;

import edu.vt.middleware.ldap.bean.LdapAttributes;

/**
 * LdapAttributes Principal.
 * @author Booker
 */
public interface LdapAttributesPrincipal extends Principal {
  /**
   * This returns the ldap attributes for this <code>LdapPrincipal</code>.
   *
   * @return  <code>LdapAttributes</code>
   */
  public LdapAttributes getLdapAttributes();
}
