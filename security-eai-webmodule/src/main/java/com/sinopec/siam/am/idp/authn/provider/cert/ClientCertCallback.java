package com.sinopec.siam.am.idp.authn.provider.cert;

import java.io.Serializable;
import java.security.cert.X509Certificate;

import javax.security.auth.callback.Callback;

/**
 * @description ���ݶ���LoginModule��CallbackHandler
 * @author zhangxianwen
 * @version 1.0
 * @Copyright (c) 2012
 * @create 2012-3-14 ����9:39:01
 */

public class ClientCertCallback implements Callback, Serializable {

  /** Serial version UID. */
  private static final long serialVersionUID = 1254421798767966265L;

  /* ���ݶ��� */
  private X509Certificate clientCert;

  public X509Certificate getClientCert() {
    return clientCert;
  }

  public void setClientCert(X509Certificate clientCert) {
    this.clientCert = clientCert;
  }
}
