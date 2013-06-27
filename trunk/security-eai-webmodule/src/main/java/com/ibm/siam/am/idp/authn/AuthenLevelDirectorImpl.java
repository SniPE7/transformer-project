package com.ibm.siam.am.idp.authn;

import java.util.List;

import com.ibm.siam.am.idp.authn.entity.AuthenLevelMethodEntity;

/**
 * 认证级别管理实现类
 * @author zhangxianwen
 * @since 2012-8-3 上午10:15:01
 */

public class AuthenLevelDirectorImpl implements AuthenLevelDirector {
  
  private List<AuthenLevelMethodEntity> authenLevelMethodEntitys;
  
  /**
   * @return the authenLevelMethodEntitys
   */
  public List<AuthenLevelMethodEntity> getAuthenLevelMethodEntitys() {
    return authenLevelMethodEntitys;
  }

  /**
   * @param authenLevelMethodEntitys the authenLevelMethodEntitys to set
   */
  public void setAuthenLevelMethodEntitys(List<AuthenLevelMethodEntity> authenLevelMethodEntitys) {
    this.authenLevelMethodEntitys = authenLevelMethodEntitys;
  }

  /** {@inheritDoc} */
  public String getMatachedAuthenMethod(String authenLevel) {
    for(AuthenLevelMethodEntity authenLevelMethodEntity : authenLevelMethodEntitys) {
      if(authenLevelMethodEntity.getAuthenLevel() != null && authenLevelMethodEntity.getAuthenLevel().equals(authenLevel)) {
        if(authenLevelMethodEntity.getAuthenMethods() == null || authenLevelMethodEntity.getAuthenMethods().size() == 0) {
          return "";
        }
        return authenLevelMethodEntity.getAuthenMethods().get(0);
      }
    }
    return null;
  }

  /** {@inheritDoc} */
  public String getAuthenLevel(String authenMethod) {
    for(AuthenLevelMethodEntity authenLevelMethodEntity : authenLevelMethodEntitys) {
      if(authenLevelMethodEntity.getAuthenMethods() == null || authenLevelMethodEntity.getAuthenMethods().size() == 0){
        continue;
      }
      for(String strAuthenMethod : authenLevelMethodEntity.getAuthenMethods()){
        if(strAuthenMethod.equals(authenMethod)){
          return authenLevelMethodEntity.getAuthenLevel();
        }
      }
    }
    return "1";
  }

  /** {@inheritDoc} */
  public List<String> getMatachedAuthenMethodList(String authenLevel) {
    for(AuthenLevelMethodEntity authenLevelMethodEntity : authenLevelMethodEntitys) {
      if(authenLevelMethodEntity.getAuthenLevel() != null && authenLevelMethodEntity.getAuthenLevel().equals(authenLevel)) {
        return authenLevelMethodEntity.getAuthenMethods();
      }
    }
    return null;
  }

}
