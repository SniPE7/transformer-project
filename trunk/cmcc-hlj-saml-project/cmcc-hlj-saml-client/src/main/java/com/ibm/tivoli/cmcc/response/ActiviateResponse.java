/**
 * 
 */
package com.ibm.tivoli.cmcc.response;

import com.ibm.tivoli.cmcc.request.ActiviateRequest;

public class ActiviateResponse {
  private String samlId = null;
  private String issueInstant = null;
  private String inResponseTo = null;
  private String samlIssuer = null;
  private String statusCode = "urn:oasis:names:tc:SAML:2.0:status:Success";
  private String nameId = null;
  
  public ActiviateResponse(ActiviateRequest req) {
    this.inResponseTo = req.getSamlId();
  }
  
  public String getNameId() {
    return nameId;
  }

  public void setNameId(String nameId) {
    this.nameId = nameId;
  }

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
  public String getInResponseTo() {
    return inResponseTo;
  }
  public void setInResponseTo(String inResponseTo) {
    this.inResponseTo = inResponseTo;
  }
  public String getSamlIssuer() {
    return samlIssuer;
  }
  public void setSamlIssuer(String samlIssuer) {
    this.samlIssuer = samlIssuer;
  }
  public String getStatusCode() {
    return statusCode;
  }
  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }
  
}