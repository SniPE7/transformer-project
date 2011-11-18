/**
 * 
 */
package com.ibm.tivoli.cmcc.response;

import com.ibm.tivoli.cmcc.request.LogoutRequest;

public class LogoutResponse {
  private String samlId = null;
  private String issueInstant = null;
  private String inResponseTo = null;
  private String samlIssuer = null;
  private String statusCode = "urn:oasis:names:tc:SAML:2.0:status:Success";
  
  public LogoutResponse(LogoutRequest req) {
    this.inResponseTo = req.getSamlId();
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