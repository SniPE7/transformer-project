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
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
  /**
   * ITIM username
   */
  private String username = null;

  /**
   * ITIM password
   */
  private String password = null;

  /**
   * 
   */
  public User() {
    super();
  }

  public User(String username, String password) {
    super();
    this.username = username;
    this.password = password;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username
   *          the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password
   *          the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("User [username=");
    builder.append(username);
    builder.append("]");
    return builder.toString();
  }

}
