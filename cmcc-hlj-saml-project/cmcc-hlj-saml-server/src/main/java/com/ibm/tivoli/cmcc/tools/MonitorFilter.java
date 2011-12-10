package com.ibm.tivoli.cmcc.tools;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MonitorFilter implements Filter {
  private static Log log = LogFactory.getLog(MonitorFilter.class);

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    String url = "";
    String clientAddress = "";
    
    if (request instanceof HttpServletRequest) {
       HttpServletRequest hr = (HttpServletRequest) request;
       url = hr.getRequestURI();
       clientAddress = hr.getRemoteAddr();
    }
    long startTime = System.currentTimeMillis();
    chain.doFilter(request, response);
    long stopTime = System.currentTimeMillis();
    log.debug(String.format("RECV:[url: %s]:[client: %s]:[elapse: %s ms]" , url, clientAddress, (stopTime - startTime)));

  }

  public void init(FilterConfig filterConfig) throws ServletException {
  }

  public void destroy() {
  }

}
