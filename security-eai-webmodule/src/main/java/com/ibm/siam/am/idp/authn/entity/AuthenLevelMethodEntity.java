package com.ibm.siam.am.idp.authn.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 认识级别和方法实体类
 * @author zhangxianwen
 * @since 2012-8-3 上午10:01:00
 */

public class AuthenLevelMethodEntity {

  private String authenLevel;
  
  private List<String> authenMethods;

  /**
   * @return the authenLevel
   */
  public String getAuthenLevel() {
    return authenLevel;
  }

  /**
   * @param authenLevel the authenLevel to set
   */
  public void setAuthenLevel(String authenLevel) {
    this.authenLevel = authenLevel;
  }

  /**
   * @return the authenMethods
   */
  public List<String> getAuthenMethods() {
    return authenMethods;
  }

  /**
   * @param authenMethods the authenMethods to set
   */
  public void setAuthenMethods(List<String> authenMethods) {
    this.authenMethods = authenMethods;
  }

  /**
   * 添加认证方法
   * @param authenMethod 认证方法
   */
  public void addAuthenMethod(String authenMethod) {
    if(this.authenMethods == null){
      this.authenMethods = new ArrayList<String>();
    }
    this.authenMethods.add(authenMethod);
  }
  
}
