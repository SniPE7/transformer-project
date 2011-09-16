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
@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
public class PIMAccount {
  
  private String username = null;

  /**
   * 
   */
  public PIMAccount() {
    super();
  }

  public PIMAccount(String username) {
    super();
    this.username = username;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Account [username=");
    builder.append(username);
    builder.append("]");
    return builder.toString();
  }

}
