/**
 * 
 */
package com.sinopec.siam.am.idp.authn.module.itim;

import java.io.Serializable;
import java.security.Principal;

/**
 * @author zhaodonglu
 *
 */
public class ItimPrincipal implements Principal, Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -3764754712602167126L;
  
  private String name = null;

  /**
   * 
   */
  public ItimPrincipal() {
    super();
  }

  /* (non-Javadoc)
   * @see java.security.Principal#getName()
   */
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("ItimPrincipal [name=%s]", name);
  }

}
