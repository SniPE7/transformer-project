/**
 * 
 */
package com.ibm.tivoli.pim.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Administrator
 *
 */
@XmlType(namespace="http://loginResponse.entity.pim.tivoli.ibm.com")
@XmlRootElement(name = "LoginResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginResponse extends BaseResponse {
  
  private String cn = null;
  private String username = null;

  /**
   * 
   */
  public LoginResponse() {
    super();
  }

  /**
   * @param code
   * @param cause
   */
  public LoginResponse(String code, String cause) {
    super(code, cause);
  }

  /**
   * @param code
   */
  public LoginResponse(String code) {
    super(code);
  }

  public String getCn() {
    return cn;
  }

  public void setCn(String cn) {
    this.cn = cn;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
