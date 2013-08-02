package com.sinopec.siam.am.idp.authn.provider.cert;

import java.io.Serializable;
import java.security.cert.X509Certificate;

import javax.security.auth.callback.Callback;

/**
 * @description 传递对象到LoginModule的CallbackHandler
 * @author zhangxianwen
 * @version 1.0
 * @Copyright (c) 2012
 * @create 2012-3-14 上午9:39:01
 */

public class ClientCertCallback implements Callback, Serializable {

  /** Serial version UID. */
  private static final long serialVersionUID = 1254421798767966265L;

  /* 传递对象 */
  private X509Certificate clientCert;

  public X509Certificate getClientCert() {
    return clientCert;
  }

  public void setClientCert(X509Certificate clientCert) {
    this.clientCert = clientCert;
  }
}
