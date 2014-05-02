package com.ibm.siam.am.idp.authn.module;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

/**
 * �û��ֻ��Ÿ���
 * @author zhaodonglu
 *
 */
public class MobileUpdateOperationStatusCallback implements Callback, Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -7154362637494646750L;

  /**
   * �Ƿ���¹��ֻ����Ҹ��³ɹ�
   */
  private boolean mobileUpdated = false;

  public MobileUpdateOperationStatusCallback() {
    super();
  }

  public MobileUpdateOperationStatusCallback(boolean mobileUpdated) {
    super();
    this.mobileUpdated = mobileUpdated;
  }

  public boolean isMobileUpdated() {
    return mobileUpdated;
  }

  public void setMobileUpdated(boolean mobileUpdated) {
    this.mobileUpdated = mobileUpdated;
  }
  
}
