package com.sinopec.siam.am.idp.authn.module;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

/**
 * 用户手机号更新
 * @author zhaodonglu
 *
 */
public class MobileUpdateOperationStatusCallback implements Callback, Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -7154362637494646750L;

  /**
   * 是否更新过手机号且更新成功
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
