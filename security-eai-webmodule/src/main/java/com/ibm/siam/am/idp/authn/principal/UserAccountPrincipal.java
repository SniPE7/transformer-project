package com.ibm.siam.am.idp.authn.principal;

import java.security.Principal;
import java.util.Date;

import org.opensaml.xml.util.DatatypeHelper;

/**
 * TIM LDAP Principal
 * 
 * @author ZhouTengfei
 * @since 2012-9-22 ÉÏÎç10:31:33
 */

public class UserAccountPrincipal implements Principal {
  private String name;
  private Date passLastChanged;
  private boolean passResetState;
  private String[] passRecoveryQuestion;
  
  public UserAccountPrincipal() {
    super();
  }

  /**
   * @param name
   */
  public UserAccountPrincipal(String name) {
    super();
    this.name = name;
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof UserAccountPrincipal)) {
      return false;
    }

    UserAccountPrincipal principal = (UserAccountPrincipal) obj;

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

  /**
   * @return the passLastChanged
   */
  public Date getPassLastChanged() {
    return passLastChanged;
  }

  /**
   * @param passLastChanged the passLastChanged to set
   */
  public void setPassLastChanged(Date passLastChanged) {
    this.passLastChanged = passLastChanged;
  }

  /**
   * @return the passResetState
   */
  public boolean isPassResetState() {
    return passResetState;
  }

  /**
   * @param passResetState the passResetState to set
   */
  public void setPassResetState(boolean passResetState) {
    this.passResetState = passResetState;
  }

  /**
   * @return the passRecoveryQuestion
   */
  public String[] getPassRecoveryQuestion() {
    return passRecoveryQuestion;
  }

  /**
   * @param passRecoveryQuestion the passRecoveryQuestion to set
   */
  public void setPassRecoveryQuestion(String[] passRecoveryQuestion) {
    this.passRecoveryQuestion = passRecoveryQuestion;
  }
  
}
