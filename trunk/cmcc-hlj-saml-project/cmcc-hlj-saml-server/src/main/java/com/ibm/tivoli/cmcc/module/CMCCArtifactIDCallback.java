/**
 * 
 */
package com.ibm.tivoli.cmcc.module;

import javax.security.auth.callback.Callback;

/**
 * @author zhaodonglu
 * 
 */
public class CMCCArtifactIDCallback implements Callback {

  private String artifactID = null;
  private String artifactDomain = null;
  private String remoteIPAddress = null;

  /**
   * 
   */
  public CMCCArtifactIDCallback() {
    super();
  }

  /**
   * @return the artifactID
   */
  public String getArtifactID() {
    return artifactID;
  }

  /**
   * @param artifactID
   *          the artifactID to set
   */
  public void setArtifactID(String artifactID) {
    this.artifactID = artifactID;
  }

  /**
   * @return the artifactDomain
   */
  public String getArtifactDomain() {
    return artifactDomain;
  }

  /**
   * @param artifactDomain
   *          the artifactDomain to set
   */
  public void setArtifactDomain(String artifactDomain) {
    this.artifactDomain = artifactDomain;
  }

  /**
   * @return the remoteIPAddress
   */
  public String getRemoteIPAddress() {
    return remoteIPAddress;
  }

  /**
   * @param remoteIPAddress
   *          the remoteIPAddress to set
   */
  public void setRemoteIPAddress(String remoteIPAddress) {
    this.remoteIPAddress = remoteIPAddress;
  }

}
