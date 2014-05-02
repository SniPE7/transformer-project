package com.ibm.siam.am.idp.authn.provider;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

/**
 * ���ڻ�ȡ�����޸�ģ���Ƿ�ɹ��޸Ŀ���
 * @author zhaodonglu
 *
 */
public class PasswordUpdateOperationStatusCallback implements Callback, Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -7154362637494646750L;

  /**
   * �Ƿ���¹������Ҹ��³ɹ�
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
