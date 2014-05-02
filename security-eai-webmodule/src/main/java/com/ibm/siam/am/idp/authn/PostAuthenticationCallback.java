/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaodonglu
 *
 */
public interface PostAuthenticationCallback {
  
  /**
   * @param context
   * @param request
   * @param response
   * @throws Exception
   */
  public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
  
}
