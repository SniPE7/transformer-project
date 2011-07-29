/**
 * 
 */
package com.ibm.tivoli.pim.entity;

/**
 * @author zhaodonglu
 *
 */
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
