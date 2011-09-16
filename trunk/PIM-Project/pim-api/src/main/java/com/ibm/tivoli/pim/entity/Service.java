/**
 * 
 */
package com.ibm.tivoli.pim.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zhaodonglu
 *
 */
@XmlRootElement(name = "service")
@XmlAccessorType(XmlAccessType.FIELD)
public class Service {
  private String name = null;
  /**
   * Name of the profile (NTAccount, Exchange Account, etc.) identifying the type of this account as listed in Configuration > Entities within the IBM Tivoli Idenitity Manager UI.
   */
  private String profileName = null;
  private String description = null;

  /**
   * 
   */
  public Service() {
    super();
  }

  public Service(String name, String profileName) {
    super();
    this.name = name;
    this.profileName = profileName;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  public String getProfileName() {
    return profileName;
  }

  public void setProfileName(String profileName) {
    this.profileName = profileName;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

}
