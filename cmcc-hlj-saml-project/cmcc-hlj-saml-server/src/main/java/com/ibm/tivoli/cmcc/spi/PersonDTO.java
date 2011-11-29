/**
 * 
 */
package com.ibm.tivoli.cmcc.spi;

/**
 * @author Zhao Dong Lu
 * 
 */
public class PersonDTO {
  String commonName;
  String lastName;
  String msisdn;
  String province;
  String brand;
  String status;
  String currentPoint;

  String nickname;

  String mail139Status;
  String fetionStatus;
  String userLevel;

  public String getUserLevel() {
    return userLevel;
  }

  public void setUserLevel(String userLevel) {
    this.userLevel = userLevel;
  }

  public String getCommonName() {
    return commonName;
  }

  public void setCommonName(String commonName) {
    this.commonName = commonName;
  }

  public String getMsisdn() {
    return msisdn;
  }

  public void setMsisdn(String description) {
    this.msisdn = description;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    if (status != null && status.toLowerCase().trim().equals("kaiji")) {
       this.status = "1";
    } else {
      this.status = status;
    }
  }

  public String getCurrentPoint() {
    return currentPoint;
  }

  public void setCurrentPoint(String currentPoint) {
    this.currentPoint = currentPoint;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getMail139Status() {
    return mail139Status;
  }

  public void setMail139Status(String mail139Status) {
    this.mail139Status = mail139Status;
  }

  public String getFetionStatus() {
    return fetionStatus;
  }

  public void setFetionStatus(String fetionStatus) {
    this.fetionStatus = fetionStatus;
  }
  
}
