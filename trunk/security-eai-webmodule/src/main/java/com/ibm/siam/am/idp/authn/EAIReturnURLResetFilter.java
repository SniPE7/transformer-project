package com.ibm.siam.am.idp.authn;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter implementation class EAIReturnURLResetFilter
 */
public class EAIReturnURLResetFilter implements Filter {

  private static Logger log = LoggerFactory.getLogger(AccessEnforcer.class);

  private FilterConfig filterConfig;
  private String forceHttpsHost;

  /**
   * Default constructor.
   */
  public EAIReturnURLResetFilter() {
    super();
  }

  /**
   * @see Filter#init(FilterConfig)
   */
  public void init(FilterConfig fConfig) throws ServletException {
    this.filterConfig = fConfig;
    String forceHttpsHost = this.filterConfig.getServletContext().getInitParameter("ForceHttpsHost");
    this.forceHttpsHost = (forceHttpsHost == null) ? "" : forceHttpsHost.toLowerCase();
  }

  /**
   * @see Filter#destroy()
   */
  public void destroy() {
  }

  /**
   * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
   */
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    // Cast to HttpServletXXXX
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    
    if (request.getParameter("eaiReturnUrlInPage") != null && request.getParameter("eaiReturnUrlInPage").trim().length() > 0) {
      // 总是用页面总记录的信息覆盖Session中最后记录的EAI Return Url
      log.debug(String.format("Recover EAI Return URL in HttpSession to [%s]", request.getParameter("eaiReturnUrlInPage")));
      AccessEnforcer.remeberEAIReturnUrl(httpRequest, request.getParameter("eaiReturnUrlInPage"), this.forceHttpsHost);
    }
    chain.doFilter(request, response);
  }

}
