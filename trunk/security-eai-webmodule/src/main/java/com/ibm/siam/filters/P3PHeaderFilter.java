package com.ibm.siam.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * P3P写Header拦截器
 * @since 2012-12-13 下午6:57:41
 */

public class P3PHeaderFilter implements Filter {
  
  /** {@inheritDoc} */
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
          ServletException {
      HttpServletResponse httpResponse = (HttpServletResponse) response;

      //解决跨域获取不到Cookie问题
      httpResponse.setHeader("P3P", "CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
      chain.doFilter(request, response);
  }

  /** {@inheritDoc} */
  public void init(FilterConfig filterConfig) throws ServletException {
      // nothing to do here
  }

  /** {@inheritDoc} */
  public void destroy() {
      // nothing to do here
  }

}
