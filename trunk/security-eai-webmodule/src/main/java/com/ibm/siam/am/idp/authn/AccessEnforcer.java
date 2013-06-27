package com.ibm.siam.am.idp.authn;

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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sinopec.siam.am.idp.themes.ThemesUtils;

import edu.internet2.middleware.shibboleth.common.profile.AbstractErrorHandler;
import edu.internet2.middleware.shibboleth.common.util.HttpHelper;
import edu.internet2.middleware.shibboleth.idp.authn.LoginContext;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;


/**
 * Servlet Filter implementation class SAMLProfileFilter
 */
public class AccessEnforcer implements Filter {

  private static Logger log = LoggerFactory.getLogger(AccessEnforcer.class);

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
   * EAI认证.
   */
  private boolean eaiAuthen = false;
  
  /**
   * WebSEAL EAI parameter: WebSEAL will set this parameter into http request, and request EAI challenge user for specified authen level.
   */
  private String AUTHN_LEVEL_FIELD  = "AUTHNLEVEL";
  
  /*
  static {
    java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());    
  }
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

    String t = this.filterConfig.getInitParameter("AlwaysReauthen");
    this.alwaysReauthen = (t == null)?false:Boolean.parseBoolean(t);
    log.info(String.format("[%s]:Always Re-authen {[%s]}", fConfig.getFilterName(), this.alwaysReauthen));
    
    String e = this.filterConfig.getInitParameter("eaiAuthen");
    this.eaiAuthen = (e == null)?false:Boolean.parseBoolean(e);
    log.info(String.format("[%s]:EAI Authen {[%s]}", fConfig.getFilterName(), this.eaiAuthen));
    
    String s = this.filterConfig.getInitParameter("AUTHN_LEVEL_FIELD");
    this.AUTHN_LEVEL_FIELD = (s != null && s.trim().length() > 0)?s:"AUTHNLEVEL";
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
    
    ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
    // Need to authenticate?
    if (needToAuthenticate(httpRequest)) {
      if (log.isDebugEnabled()) {
        log.debug("Need to authenticate ......");
      }

      AuthenLevelDirector authenLevelDirector = (AuthenLevelDirector)applicationContext.getBean("authenLevelDirector", AuthenLevelDirector.class);
      if (log.isDebugEnabled()) {
        log.debug(String.format("Need to authenticate: authenLevelDirector [%s].", authenLevelDirector));
      }
      doAuthentication(httpRequest, httpResponse, authenLevelDirector, this.authenticationMethod, this.AUTHN_LEVEL_FIELD, this.alwaysReauthen);
      return;
    }
    
    if (log.isDebugEnabled()) {
      log.debug("Authentication is ok.");
   }
    
    // Already authentication, call post
    try {
      // Set return params for WebSEAL EAI
      PostAuthenticationCallback handler = (PostAuthenticationCallback)applicationContext.getBean("siam.sp.webseal.eai.handler");
      handler.handle(httpRequest, httpResponse);
    } catch (BeansException e) {
      log.warn(String.format("[%s]:Could not found WebSEAL EAI decorator, EAI feature disabled", this.filterConfig.getFilterName()));
    } catch (Exception e) {
      log.error(String.format("[%s]:Fail to execute Post-Authenticatoin handler, cause: [%s]", this.filterConfig.getFilterName(), e.getMessage()), e);
    }

    // Authenticated and matched authen method, do business
    chain.doFilter(request, response);
    return;
  }

  /**
   * @param httpRequest
   * @param httpResponse
   * @param handlerManager
   * @param authenLevelDirector
   * @param defaultAuthenticationMethod
   * @param authenLevelHeaderName
   * @param alwaysReauthen
   */
  public static void doAuthentication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AuthenLevelDirector authenLevelDirector, String defaultAuthenticationMethod, String authenLevelHeaderName, boolean alwaysReauthen) {
    if (log.isDebugEnabled()) {
      log.debug("Do authenticate ......");
    }
		// Send authen request.
		String requiredAuthenticationMethod = getRequiredAuthenMethod(httpRequest, authenLevelDirector, defaultAuthenticationMethod, authenLevelHeaderName);
		if (log.isDebugEnabled()) {
			log.debug(String.format("Required authentication method:[%s].", requiredAuthenticationMethod));
		}
		String themeOfIdPLoginPage = getRequiredTheme(ThemeDirector.THEME_PARAM_NAME, httpRequest);
		if (log.isDebugEnabled()) {
			log.debug(String.format("Required authentication theme of Idp Login Page:[%s].", themeOfIdPLoginPage));
		}
		sendAuthenticationRequest(httpRequest, httpResponse, requiredAuthenticationMethod, alwaysReauthen, themeOfIdPLoginPage);
		return;
  }

  /**
   * 调试用途，记录HttpRequest到日志中.
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
           String name = (String)names.nextElement();
           String[] values = httpRequest.getParameterValues(name);
           log.debug(String.format("HTTP Req Param: [%s: %s]", name, values));
     }
  }

  /**
   * @param httpRequest
   * @param httpResponse
   * @param handlerManager
   * @param requiredAuthenticationMethod
   * @param alwaysReauthen
   */
  private static void sendAuthenticationRequest(HttpServletRequest httpRequest, HttpServletResponse httpResponse, 
      String requiredAuthenticationMethod, boolean alwaysReauthen, String themeOfIdPLoginPage) {
  	
    log.debug("Creating login context and transferring control to authentication engine");
    
    String authnEngineUrl = HttpServletHelper.getContextRelativeUrl(httpRequest, "/AuthnEngine").buildURL();

    LoginContext loginContext = new LoginContext(alwaysReauthen, false);
    loginContext.setAuthenticationEngineURL(authnEngineUrl);
    loginContext.setAccessEnforcerURL(HttpHelper.getRequestUriWithoutContext(httpRequest));
    loginContext.setDefaultAuthenticationMethod(requiredAuthenticationMethod);

    HttpServletHelper.bindLoginContext(loginContext, httpRequest.getSession().getServletContext(), httpRequest, httpResponse);
    
    String themeName = httpRequest.getParameter(ThemesUtils.THEME_PARAM_NAME);
    
    httpRequest.getSession().setAttribute(ThemesUtils.THEME_ATTR_NAME, ThemesUtils.getThemeByName(themeName));
    
    try {
      log.debug("Redirecting user to authentication engine at {}", authnEngineUrl);
      httpResponse.sendRedirect(authnEngineUrl);
    } catch (Exception e) {
      httpRequest.setAttribute(AbstractErrorHandler.ERROR_KEY, e);
      log.error("Failure to process SAML2 Authen request, cause: " + e.getMessage(), e);
    }
  }

  /**
   * 获取会话认证级别
   * 
   * @param httpRequest HTTPRequest请求对象
   * @return
   */
  private static String getRequiredAuthenLevel(String authenLevelHeaderName, HttpServletRequest httpRequest){
    if (authenLevelHeaderName == null) {
      return null;
    }
    String requiredAutheLevel = httpRequest.getParameter(authenLevelHeaderName);
    if (StringUtils.isEmpty(requiredAutheLevel)) {
      HttpSession session = httpRequest.getSession(false);
      if (session != null && session.getAttribute(SSOPrincipal.LAST_REQUIRED_AUTHEN_LEVEL_OF_SESSION_ATTR) != null) {
        requiredAutheLevel = (String)session.getAttribute(SSOPrincipal.LAST_REQUIRED_AUTHEN_LEVEL_OF_SESSION_ATTR);
        if (log.isDebugEnabled()) {
          log.debug(String.format("Get session authen level [%s]", requiredAutheLevel));
        }
      }
    }
    return requiredAutheLevel;
  }
  
  /**
   * 如果请求中包含了AUTHN_LEVEL的要求（目前是由WebSEAL EAI整合时，由WebSEAL提出的请求）在会话中暂存认证级别.
   * 
   * @param httpRequest HTTPRequest请求对象
   * @return
   */
  private static void setRequiredAuthenLevel(String authenLevelHeaderName, HttpServletRequest httpRequest){
    String requiredAutheLevel = httpRequest.getParameter(authenLevelHeaderName);
    if (!StringUtils.isEmpty(requiredAutheLevel)) {
      HttpSession session = httpRequest.getSession(true);
      session.setAttribute(SSOPrincipal.LAST_REQUIRED_AUTHEN_LEVEL_OF_SESSION_ATTR, requiredAutheLevel);
      if (log.isDebugEnabled()) {
        log.debug(String.format("Get request authen level [%s]", requiredAutheLevel));
        log.debug(String.format("Set session authen level [%s=%s]", SSOPrincipal.LAST_REQUIRED_AUTHEN_LEVEL_OF_SESSION_ATTR, requiredAutheLevel));
      }
    }
  }

  /**
   * 获取会话认证级别
   * 
   * @param httpRequest HTTPRequest请求对象
   * @return
   */
  private static String getRequiredTheme(String themeParamName, HttpServletRequest httpRequest){
    String themeOfIdpLoginPage = httpRequest.getParameter(themeParamName);
    if (StringUtils.isEmpty(themeOfIdpLoginPage)) {
      HttpSession session = httpRequest.getSession(false);
      if (session != null && session.getAttribute(ThemeDirector.THEME_ATTR_NAME) != null) {
        themeOfIdpLoginPage = (String)session.getAttribute(ThemeDirector.THEME_ATTR_NAME);
        if (log.isDebugEnabled()) {
          log.debug(String.format("Get session theme name [%s]", themeOfIdpLoginPage));
        }
      }
    }
    return themeOfIdpLoginPage;
  }
  
  /**
   * 如果请求中包含了Idp登录页面样式名称的要求，则在会话中暂存样式名称.
   * 
   * @param httpRequest HTTPRequest请求对象
   * @return
   */
  private static void setRequiredTheme(String themeParamName, HttpServletRequest httpRequest){
    String themeOfIdpLoginPage = httpRequest.getParameter(themeParamName);
    if (!StringUtils.isEmpty(themeOfIdpLoginPage)) {
      HttpSession session = httpRequest.getSession(true);
      session.setAttribute(ThemeDirector.THEME_ATTR_NAME, themeOfIdpLoginPage);
      if (log.isDebugEnabled()) {
        log.debug(String.format("Get request theme name [%s]", themeOfIdpLoginPage));
        log.debug(String.format("Set session theme name [%s=%s]", ThemeDirector.THEME_ATTR_NAME, themeOfIdpLoginPage));
      }
    }
  }
  
  /**
   * 获取请求认证方法
   * @param httpRequest HTTPRequest请求对象
   * @param authenLevelDirector 认证级别管理器
   * @return
   */
  private static String getRequiredAuthenMethod(HttpServletRequest httpRequest, AuthenLevelDirector authenLevelDirector, String defaultAuthenticationMethod, String authenLevelHeaderName) {
    String requiredAutheLevel = getRequiredAuthenLevel(authenLevelHeaderName, httpRequest);
    String requiredAuthenticationMethod = authenLevelDirector.getMatachedAuthenMethod(requiredAutheLevel);
    if (log.isDebugEnabled()) {
      log.debug(String.format("Request authen method [%s=%s]", requiredAutheLevel, requiredAuthenticationMethod));
    }
    if(requiredAuthenticationMethod == null || "".equals(requiredAuthenticationMethod)){
      // Use default authentication method
      requiredAuthenticationMethod = defaultAuthenticationMethod;
    }
    if (log.isDebugEnabled()) {
      log.debug(String.format("Set authen method [%s]", requiredAuthenticationMethod));
    }
    return requiredAuthenticationMethod;
  }
  
  /**
   * 根据当前的应用本地的会话状态及认证的级别判断是否需要进行认证。
   * 
   * @param session
   * @return
   */
  protected boolean needToAuthenticate(HttpServletRequest httpRequest) {
    boolean authenticated = this.isAuthenticated(httpRequest);
    if(this.eaiAuthen){
      boolean matchWithRequiredAuthenLevel = this.isMatchWithRequiredAuthenLevel(httpRequest);
      if (log.isDebugEnabled()) {
        log.debug(String.format("[%s]:check authen state,eaiAuthen=[%s], authenticated=[%s], matchAuthenLevel=[%s]", this.filterConfig.getFilterName(), this.eaiAuthen, authenticated, matchWithRequiredAuthenLevel));
     }
      return !authenticated || !matchWithRequiredAuthenLevel;
    }
    boolean matchWithRequiredAuthenMethod = isMatchWithRequiredAuthenMethod(httpRequest);
    if (log.isDebugEnabled()) {
      log.debug(String.format("[%s]:check authen state,eaiAuthen=[%s], authenticated=[%s], matchAuthenMethod=[%s]", this.filterConfig.getFilterName(), this.eaiAuthen, authenticated, matchWithRequiredAuthenMethod));
   }
    return !authenticated || !matchWithRequiredAuthenMethod;
  }

  /**
   * 判断当前用户是否已经认证
   * 
   * @param httpRequest
   * @return true 已经认证
   */
  protected boolean isAuthenticated(HttpServletRequest httpRequest) {
    HttpSession session = httpRequest.getSession(false);
    if (session != null && session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR) != null) {
      return true;
    }
    return false;
  }

  /**
   * 判断当前用户的认证级别是否满足被保护资源要求的认证方法。
   * 
   * @param httpRequest
   * @return true - 满足要求
   * 
   */
  protected boolean isMatchWithRequiredAuthenMethod(HttpServletRequest httpRequest) {
    HttpSession session = httpRequest.getSession(false);
    if (session == null || session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR) == null) {
      // Un-authen
      return false;
    }
    SSOPrincipal principal = (SSOPrincipal) session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR);
    return principal.containsAuthenMethod(this.authenticationMethod);
  }

  /**
   * 判断当前用户的认证级别是否满足被保护资源要求的级别。
   * 
   * @param httpRequest
   * @return true - 满足要求
   * 
   */
  protected boolean isMatchWithRequiredAuthenLevel(HttpServletRequest httpRequest) {
    // Get AUTHEN_LEVEL sent by WebSEAL
    String requiredAutheLevel = httpRequest.getParameter(this.AUTHN_LEVEL_FIELD);
    if (StringUtils.isEmpty(requiredAutheLevel)){
      return true;
    }
    
    HttpSession session = httpRequest.getSession(false);
    if (session == null) {
      return false;
    }
    
    SSOPrincipal principal = (SSOPrincipal)session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR);
    if (principal == null) {
       return false;
    }
    String maxSucceedAuthenLevel = principal.getMaxSucceedAuthenLevel();
    return requiredAutheLevel.compareTo(maxSucceedAuthenLevel) <= 0;
  }

}
