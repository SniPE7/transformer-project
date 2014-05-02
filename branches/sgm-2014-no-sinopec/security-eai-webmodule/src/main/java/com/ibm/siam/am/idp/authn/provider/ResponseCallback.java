package com.ibm.siam.am.idp.authn.provider;

import java.io.Serializable;

import javax.security.auth.callback.Callback;
import javax.servlet.http.HttpServletResponse;

/**
 * Response会话CallBack
 * @author zhangxianwen
 * @since 2012-6-20 上午10:02:09
 */

public class ResponseCallback implements Callback, Serializable {

  private static final long serialVersionUID = -459624516134480085L;
  
  /** HttpServletResponse会话 */
  private HttpServletResponse response;

  /**
   * @return the response
   */
  public HttpServletResponse getResponse() {
    return response;
  }

  /**
   * @param response the response to set
   */
  public void setResponse(HttpServletResponse response) {
    this.response = response;
  }
  
}
