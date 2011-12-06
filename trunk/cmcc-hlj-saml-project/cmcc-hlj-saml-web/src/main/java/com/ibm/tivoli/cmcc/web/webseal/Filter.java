package com.ibm.tivoli.cmcc.web.webseal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Filter {
  /**
   * @param request
   * @param response
   */
  public void doAction(HttpServletRequest request, HttpServletResponse response);
}
