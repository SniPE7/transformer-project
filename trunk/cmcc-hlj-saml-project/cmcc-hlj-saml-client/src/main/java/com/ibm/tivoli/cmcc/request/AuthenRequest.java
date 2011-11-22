/**
 * 
 */
package com.ibm.tivoli.cmcc.request;

public class AuthenRequest {
  String samlId = null;
  private String issueInstant = null;
  private String samlIssuer = null;
  private boolean allowCreate = true;

  public String getSamlId() {
    return samlId;
  }

  public void setSamlId(String samlId) {
    this.samlId = samlId;
  }

  public String getIssueInstant() {
    return issueInstant;
  }

  public void setIssueInstant(String issueInstant) {
    this.issueInstant = issueInstant;
  }

  public String getSamlIssuer() {
    return samlIssuer;
  }

  public void setSamlIssuer(String samlIssuer) {
    this.samlIssuer = samlIssuer;
  }

  /**
   * @return the allowCreate
   */
  public boolean isAllowCreate() {
    return allowCreate;
  }

  /**
   * @param allowCreate
   *          the allowCreate to set
   */
  public void setAllowCreate(boolean allowCreate) {
    this.allowCreate = allowCreate;
  }
}