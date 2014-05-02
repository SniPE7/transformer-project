package com.ibm.siam.am.idp.authn.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ʶ����ͷ���ʵ����
 * @author zhangxianwen
 * @since 2012-8-3 ����10:01:00
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
   * �����֤����
   * @param authenMethod ��֤����
   */
  public void addAuthenMethod(String authenMethod) {
    if(this.authenMethods == null){
      this.authenMethods = new ArrayList<String>();
    }
    this.authenMethods.add(authenMethod);
  }
  
}
