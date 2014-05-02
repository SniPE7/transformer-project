package com.ibm.siam.am.idp.authn.provider;

import java.io.Serializable;

import javax.security.auth.callback.Callback;
import javax.servlet.http.HttpServletRequest;

/**
 * Request会话CallBack
 * @author zhangxianwen
 * @since 2012-6-20 上午10:02:09
 */

public class RequestCallback implements Callback, Serializable {

  private static final long serialVersionUID = -459624516134480085L;
  
  /** HttpServletRequest会话对象 */
  private HttpServletRequest request;

  public HttpServletRequest getRequest() {
    return request;
  }

  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }
  
}
