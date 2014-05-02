package com.ibm.siam.am.idp.authn.provider;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

/**
 * 用于获取口令修改模块是否成功修改口令
 * @author zhaodonglu
 *
 */
public class PasswordUpdateOperationStatusCallback implements Callback, Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -7154362637494646750L;

  /**
   * 是否更新过口令且更新成功
   */
  private boolean passwordUpdated = false;

  public PasswordUpdateOperationStatusCallback() {
    super();
  }

  public PasswordUpdateOperationStatusCallback(boolean passwordUpdated) {
    super();
    this.passwordUpdated = passwordUpdated;
  }

  public boolean isPasswordUpdated() {
    return passwordUpdated;
  }

  public void setPasswordUpdated(boolean passwordUpdated) {
    this.passwordUpdated = passwordUpdated;
  }
  
}
