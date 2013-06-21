package group.tivoli.security.eai.web.sso;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;

/**
 * Servlet Filter implementation class SAMLProfileFilter
 */
public class AccessEnforcer implements Filter {

  private static Log log = LogFactory.getLog(AccessEnforcer.class);

  public static final String ATTR_NAME_AUTHEN_REQUEST_CONTEXT = "__AGENT_AUTHEN_REQUEST_CONEXT";

  private FilterConfig filterConfig;

  /**
   * Default Authentication Method
   */
  private String authenticationMethod = "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified";
  /**
   * 总是要求IdP做重新认证.
   */
  private boolean alwaysReauthen = false;

  /**
   * WebSEAL EAI parameter: WebSEAL will set this parameter into http request,
   * and request EAI challenge user for specified authen level.
   */
  private String AUTHN_LEVEL_FIELD = "AUTHNLEVEL";
  
  private String AUTH_PAGE = "/AuthEngine";

  /*
   * static { java.security.Security.addProvider(new
   * org.bouncycastle.jce.provider.BouncyCastleProvider()); }
   */

  /**
   * Default constructor.
   */
  public AccessEnforcer() {
    super();
  }

  /**
   * @see Filter#init(FilterConfig)
   */
  public void init(FilterConfig fConfig) throws ServletException {
    this.filterConfig = fConfig;
    this.authenticationMethod = this.filterConfig.getInitParameter("AuthenticationMethod");
    log.info(String.format("[%s]:Authen Method {[%s]}", fConfig.getFilterName(), this.authenticationMethod));

    String alwaysReauthen = this.filterConfig.getInitParameter("AlwaysReauthen");
    this.alwaysReauthen = (alwaysReauthen == null) ? false : Boolean.parseBoolean(alwaysReauthen);
    log.info(String.format("[%s]:Always Re-authen {[%s]}", fConfig.getFilterName(), this.alwaysReauthen));

    String authLevel = this.filterConfig.getInitParameter("AUTHN_LEVEL_FIELD");
    this.AUTHN_LEVEL_FIELD = (authLevel != null && authLevel.trim().length() > 0) ? authLevel : "AUTHNLEVEL";
    
    String eaiAuthen = this.filterConfig.getInitParameter("eaiAuthen");
    
    this.AUTH_PAGE = this.filterConfig.getInitParameter("authPage");
  }

  /**
   * @see Filter#destroy()
   */
  public void destroy() {

  }

  /**
   * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
   */
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {

    // Cast to HttpServletXXXX
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    if (log.isDebugEnabled()) {
      dumpToLog(httpRequest);
    }

    // Need to authenticate
    if (needToAuthenticate(httpRequest)) {
      
      httpRequest.setAttribute(SSOPrincipal.URL_REQUEST_TARGET_TAG, httpRequest.getRequestURI());
      
      //httpResponse.sendRedirect(httpRequest.getContextPath() + this.AUTH_PAGE);
      httpRequest.getRequestDispatcher(this.AUTH_PAGE).forward(httpRequest, httpResponse);
      return;
    }


    // Authenticated and matched authen method, do business
    chain.doFilter(request, response);
    return;
  }
  
  
  private void doSSOAction(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
    // Already authentication, call post
    try {

      // Set return params for WebSEAL EAI
      PostAuthenticationCallback handler = (PostAuthenticationCallback) WebApplicationContextUtils
          .getWebApplicationContext(this.filterConfig.getServletContext()).getBean("siam.sp.webseal.eai.handler");
      handler.handle(httpRequest, httpResponse);
    } catch (BeansException e) {
      log.warn(String.format("[%s]:Could not found WebSEAL EAI decorator, EAI feature disabled", this.filterConfig.getFilterName()));
    } catch (Exception e) {
      log.error(
          String.format("[%s]:Fail to execute Post-Authenticatoin handler, cause: [%s]", this.filterConfig.getFilterName(),
              e.getMessage()), e);
    }
  }

  /**
   * 调试用途，记录HttpRequest到日志中.
   * 
   * @param httpRequest
   */
  private void dumpToLog(HttpServletRequest httpRequest) {
    // Dump Header
    Enumeration<String> names = httpRequest.getHeaderNames();
    while (names.hasMoreElements()) {
      String name = names.nextElement();
      String value = httpRequest.getHeader(name);
      log.debug(String.format("HTTP Header: [%s: %s]", name, value));
    }
    // Dump Request Parameter
    names = httpRequest.getParameterNames();
    while (names.hasMoreElements()) {
      String name = (String) names.nextElement();
      String[] values = httpRequest.getParameterValues(name);
      log.debug(String.format("HTTP Req Param: [%s: %s]", name, values));
    }
  }

  /**
   * 根据当前的应用本地的会话状态及认证的级别判断是否需要进行认证。
   * 
   * @param session
   * @return
   */
  protected boolean needToAuthenticate(HttpServletRequest httpRequest) {
    boolean authenticated = isAuthenticated(httpRequest);
    
    return !authenticated ;
  }

  /**
   * 判断当前用户是否已经认证
   * 
   * @param httpRequest
   * @return true 已经认证
   */
  protected boolean isAuthenticated(HttpServletRequest httpRequest) {
    HttpSession session = httpRequest.getSession(false);
    if (session != null && session.getAttribute(LoginHandler.SUBJECT_KEY) != null) {
      return true;
    }
    return false;
  }
  
  /**   session中设置业务属性
   * @param httpRequest
   * @param key
   * @param value
   */
  protected void setSessionKey(HttpServletRequest httpRequest, String key, Object value) {
    HttpSession session = httpRequest.getSession(true);
    if (session != null) {
      session.setAttribute(key, value);
    }
  }
}
