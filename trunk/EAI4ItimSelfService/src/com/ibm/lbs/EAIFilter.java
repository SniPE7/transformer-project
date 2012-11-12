package com.ibm.lbs;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class EAIFilter
 */
public class EAIFilter implements Filter {

  private FilterConfig filterConfig = null;

  private String eaiUseridName = "am-eai-user-id";;
  private String eaiAuthenLevenName = "am-eai-auth-level";
  private String eaiAuthenLevel = "1";

  private boolean debug = false;
  /**
   * Default constructor.
   */
  public EAIFilter() {
    super();
  }

  /**
   * @see Filter#init(FilterConfig)
   */
  public void init(FilterConfig fConfig) throws ServletException {
    this.filterConfig  = fConfig;
    String s = this.filterConfig.getInitParameter("eai.userid.header.attr.name");
    if (s != null && s.trim().length() > 0) {
       this.eaiUseridName = s;
    }
    s = this.filterConfig.getInitParameter("eai.authenlevel.header.attr.name");
    if (s != null && s.trim().length() > 0) {
       this.eaiAuthenLevenName = s;
    }
    s = this.filterConfig.getInitParameter("eai.authenlevel");
    if (s != null && s.trim().length() > 0) {
       this.eaiAuthenLevel = s;
    }
    s = this.filterConfig.getInitParameter("debug");
    if ("true".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s)) {
       this.debug = true;
    }
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
    // pass the request along the filter chain
    chain.doFilter(request, response);

    // place your code here
    decorate4EAI((HttpServletRequest)request, (HttpServletResponse)response, chain);
  }

  private void decorate4EAI(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
    HttpSession session = request.getSession(false);
    if (session == null) {
       return;
    }
    // session.setAttribute("subject", subject)
    //Subject subject = (Subject)session.getAttribute("subject");
    // session.setAttribute("userid", userId)
    //String userId = ServerCallUtils.getLoggedInUser(session);
    // session.setAttribute("userdn"
    //String userDN = ServerCallUtils.getLoggedInUserDN(session);
    
    String userid = (String)session.getAttribute("userid");
    if (userid != null && userid.trim().length() > 0) {
       response.setHeader(this.eaiUseridName, userid);
       response.setHeader(this.eaiAuthenLevenName, this.eaiAuthenLevel);
       
       if (this.debug) {
          System.out.println(String.format("User authenticated, set EAI header [%s=%s]", this.eaiUseridName, userid));
          System.out.println(String.format("User authenticated, set EAI header [%s=%s]", this.eaiAuthenLevenName, this.eaiAuthenLevel));
       }
    }
  }

}
