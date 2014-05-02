package com.ibm.siam.am.idp.authn.provider;

import java.io.Serializable;

import javax.security.auth.callback.Callback;
import javax.servlet.http.HttpServletRequest;

/**
 * Request�ỰCallBack
 * @author zhangxianwen
 * @since 2012-6-20 ����10:02:09
 */

public class RequestCallback implements Callback, Serializable {

  private static final long serialVersionUID = -459624516134480085L;
  
  /** HttpServletRequest�Ự���� */
  private HttpServletRequest request;

  public HttpServletRequest getRequest() {
    return request;
  }

  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }
  
}
