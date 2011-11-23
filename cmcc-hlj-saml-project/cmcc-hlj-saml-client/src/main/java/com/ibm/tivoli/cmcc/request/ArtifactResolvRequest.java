/**
 * 
 */
package com.ibm.tivoli.cmcc.request;

public class ArtifactResolvRequest {
  private String samlId = null;
  private String version = null;
  private String issueInstant = null;
  private String samlIssuer = null;
  private String artifact = null;

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
   * @return the artifact
   */
  public String getArtifact() {
    return artifact;
  }

  /**
   * @param artifact
   *          the artifact to set
   */
  public void setArtifact(String artifact) {
    this.artifact = artifact;
  }

  /**
   * @return the version
   */
  public String getVersion() {
    return version;
  }

  /**
   * @param version the version to set
   */
  public void setVersion(String version) {
    this.version = version;
  }

}