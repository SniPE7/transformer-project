/**
 * 
 */
package com.ibm.tivoli.cmcc.request;

public class QueryAttributeRequest {
  String samlId = null;
  private String issueInstant = null;
  private String samlIssuer = null;
  private String nameId = null;
  private String attribute = null;
  
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
  public String getNameId() {
    return nameId;
  }
  public void setNameId(String nameId) {
    this.nameId = nameId;
  }
  public String getAttribute() {
    return attribute;
  }
  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }
}