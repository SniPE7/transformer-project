package com.ibm.siam.am.idp.authn;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.siam.am.idp.AbstractErrorHandler;
import com.ibm.siam.am.idp.themes.ThemesUtils;

import edu.internet2.middleware.shibboleth.idp.authn.LoginContext;
import edu.internet2.middleware.shibboleth.idp.util.HttpHelper;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * Servlet Filter implementation class SAMLProfileFilter
 */
public class AccessEnforcer implements Filter {

  private static Logger log = LoggerFactory.getLogger(AccessEnforcer.class);

  public static final String ATTR_NAME_AUTHEN_REQUEST_CONTEXT = "__AGENT_AUTHEN_REQUEST_CONEXT";

  public static final String SESSION_ATTR_NAME_EAI_RETURN_URL = "eai-redir-url-header";

  private FilterConfig filterConfig;

  /**
   * Default Authentication Method
   */
  private String authenticationMethod = "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified";
  /**
   * ����Ҫ��IdP��������֤.
   */
  private boolean alwaysReauthen = false;

  /**
   * EAI��֤.
   */
  private boolean eaiAuthen = false;

  /**
   * WebSEAL EAI parameter: WebSEAL will set this parameter into http request,
   * and request EAI challenge user for specified authen level.
   */
  private String AUTHN_LEVEL_FIELD = "AUTHNLEVEL";

  private String defaultWebSEALURL = "/";

  private String logoutCustomeizedPageURI = "/html/%s";

  private String forceHttpsHost = "";

  private String pdSessionCookieName = "PD-H-SESSION-ID";

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

    String t = this.filterConfig.getInitParameter("AlwaysReauthen");
    this.alwaysReauthen = (t == null) ? false : Boolean.parseBoolean(t);
    log.info(String.format("[%s]:Always Re-authen {[%s]}", fConfig.getFilterName(), this.alwaysReauthen));

    String e = this.filterConfig.getInitParameter("eaiAuthen");
    this.eaiAuthen = (e == null) ? false : Boolean.parseBoolean(e);
    log.info(String.format("[%s]:EAI Authen {[%s]}", fConfig.getFilterName(), this.eaiAuthen));

    String u = this.filterConfig.getInitParameter("AfterAuthenDefaultURL");
    this.defaultWebSEALURL = (u == null) ? "/" : u;
    log.info(String.format("[%s]:EAI Authen default redirect URL{[%s]}", fConfig.getFilterName(), this.defaultWebSEALURL));

    String s = this.filterConfig.getInitParameter("AUTHN_LEVEL_FIELD");
    this.AUTHN_LEVEL_FIELD = (s != null && s.trim().length() > 0) ? s : "AUTHNLEVEL";
    log.info(String.format("[%s]:EAI Authen AUTHN_LEVEL_FIELD {[%s]}", fConfig.getFilterName(), this.AUTHN_LEVEL_FIELD));

    String tmp = this.filterConfig.getInitParameter("LogoutCustomeizedHtmlURI");
    this.logoutCustomeizedPageURI = (tmp == null) ? "/html/%s" : tmp;
    log.info(String.format("[%s]:EAI Logout redirect URL{[%s]}", fConfig.getFilterName(), this.logoutCustomeizedPageURI));

    String forceHttpsHost = this.filterConfig.getServletContext().getInitParameter("ForceHttpsHost");
    this.forceHttpsHost = (forceHttpsHost == null) ? "" : forceHttpsHost.toLowerCase();

    String pdSessionCookieName = this.filterConfig.getInitParameter("pdSessionCookieName");
    this.pdSessionCookieName = (pdSessionCookieName == null) ? "" : pdSessionCookieName;

    log.info(String.format("[%s]:EAI Force transfer access app by https{[%s]}", fConfig.getFilterName(), this.forceHttpsHost));
  }

  /**
   * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
   */
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    // Cast to HttpServletXXXX
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String pro = request.getParameter("PROTOCOL");
    String hst = request.getParameter("HOSTNAME");

    String tamOp = httpRequest.getParameter("TAM_OP");
    String reURL = request.getParameter("URL");

    // ������ظ��ύ�����ж���ʾ����
    if (isDecorateReturnURL(reURL)) {
      terminateAccess(request, httpRequest, httpResponse);
      return;
    }
    // ����WebSEAL��ԭ��, �����/pkms*֮ǰ������ĳЩjunction, ����/junction/pkmslogout,
    // �����߼����ǹ��˵�ǰ���junction
    if (reURL != null && reURL.indexOf("/pkms") > 0) {
      // ����/junction/pkmslogout��Junctionǰ׺�����Ϊ/pkmslogout
      String newReURL = reURL.substring(reURL.indexOf("/pkms"));
      log.debug(String.format("transfer EAI Return URL: [%s] -> [%s]", reURL, newReURL));
      reURL = newReURL;
    }

    if ("error".equals(tamOp)) {
      handleErrorOP(request, httpRequest, httpResponse);
      return;
    } else if ("logout".equals(tamOp)) {
      // Logout Operation
      if (this.isAuthenticated(httpRequest)) {
        if (log.isDebugEnabled()) {
          log.debug("TAM_OP=logout, destroy current session.");
        }
        httpRequest.getSession(false).invalidate();
      }
      // ֧��pkmslogout, ȷ������֧��pkmslogout?filename�ķ�ʽ
      if (reURL.toLowerCase().indexOf("pkmslogout") >= 0) {
        if (reURL.toLowerCase().indexOf("?filename=") >= 0) {
          // To WebSEAL Req: pkmslogout?filename=cilogout.html
          // To EAI Req:
          // http://localhost/eaiweb/login/info.do?TAM_OP=logout&USERNAME=s6uqqg&ERROR_CODE=0x00000000&ERROR_TEXT=HPDBA0521I%20%20%20Successful%20completion&METHOD=GET&URL=%2Fpkmslogout%3Ffilename%3Dcilogout.html&REFERER=&HOSTNAME=10.203.2.180&AUTHNLEVEL=&FAILREASON=&PROTOCOL=http&OLDSESSION=
          String filename = reURL.substring("/pkmslogout?filename=".length());
          reURL = String.format(logoutCustomeizedPageURI, filename);
        } else {
          // reURL = "/";
          reURL = defaultWebSEALURL;
        }
        // String redirectUrl = request.getParameter("PROTOCOL") + "://" +
        // request.getParameter("HOSTNAME") + reURL;
        String redirectUrl = (isForceHttpsHost(this.forceHttpsHost, hst) ? "https" : pro) + "://" + hst + reURL;
        if (log.isDebugEnabled()) {
          log.debug(String.format("TAM_OP=logout, redirect to URL: [%s]", redirectUrl));
        }
        // httpResponse.sendRedirect(redirectUrl);
        httpResponse.setContentType("text/html");
        // httpResponse.getWriter().println(String.format("<script language='javascript'>var protocol='%s';var host='%s';var uri='%s';window.location=protocol + '://' + host + uri;</script>",
        // request.getParameter("PROTOCOL"), request.getParameter("HOSTNAME"),
        // reURL));
        httpResponse.getWriter().println(
            String.format("<script language='javascript'>var protocol='%s';var host='%s';var uri='%s';window.location=protocol + '://' + host + uri;</script>",
                (isForceHttpsHost(this.forceHttpsHost, hst) ? "https" : pro), request.getParameter("HOSTNAME"), reURL));

        httpResponse.flushBuffer();
        return;
      }
    } else if ("help".equals(tamOp) && "/pkmspasswd".equals(reURL)) {
      // httpRequest.getSession().setAttribute("j_username", "jsmith");
      if (httpRequest.getSession(false) != null && httpRequest.getSession(false).getAttribute("j_username") != null) {
        String username = (String) httpRequest.getSession(false).getAttribute("j_username");
        request.setAttribute("j_username", username);
        request.setAttribute("show_username", username);
        // request.setAttribute(LoginHandler.AUTHENTICATION_INFO_KEY,
        // "modifyPass.info.userpass.changePwdByUser");

        request.setAttribute("op", "changePwdByUser");
        request.setAttribute("actionUrl", buildServletUrl((HttpServletRequest) request));
        request.getRequestDispatcher("/modify_password.do").forward(request, response);
        return;
      }
    } else if ("login".equals(tamOp) && !containePDSessionCookie((HttpServletRequest) request)) {
      // WebSEALҪ���¼����ȱ��PDSession��ص�Cookie
      String redirectUrl = (isForceHttpsHost(this.forceHttpsHost, hst) ? "https" : pro) + "://" + hst + reURL;
      httpResponse.sendRedirect(redirectUrl);
      return;
    }

    if (tamOp != null && tamOp.equals("logout")) {
      if (this.isAuthenticated(httpRequest)) {
        if (log.isDebugEnabled()) {
          log.debug("TAM_OP=logout, destroy current session.");
        }
        httpRequest.getSession(false).invalidate();
      }
    } else if (tamOp != null && tamOp.equals("login_success")) {
      if (this.isAuthenticated(httpRequest)) {
        String redirectUrl = defaultWebSEALURL;
        if (!redirectUrl.toLowerCase().startsWith("http")) {
          redirectUrl = String.format("%s://%s%s", request.getParameter("PROTOCOL"), request.getParameter("HOSTNAME"), defaultWebSEALURL);
        }
        if (log.isDebugEnabled()) {
          log.debug(String.format("TAM_OP=login_success, redirect to default URL: [%s]", redirectUrl));
        }
        httpResponse.sendRedirect(redirectUrl);
        return;
      }
    }

    String level = request.getParameter("AUTHNLEVEL");
    if (level != null && reURL != null && !"".equals(reURL)) {
      String appUrl = (isForceHttpsHost(forceHttpsHost, httpRequest.getParameter("HOSTNAME")) ? "https" : httpRequest.getParameter("PROTOCOL")) + "://"
          + httpRequest.getParameter("HOSTNAME") + decorateReturnURL(reURL);
      remeberEAIReturnUrl(httpRequest, appUrl, this.forceHttpsHost);
    }
    log.debug(String.format("EAI Return url: %s", pro + "://" + hst + reURL + "[" + level + "]"));

    if (log.isDebugEnabled()) {
      dumpToLog(httpRequest);
    }

    // ǿ��ת����https �� ֧�� word�����ύ
//    if (httpRequest.getRequestURI().contains("login/info.do") && httpRequest.getQueryString() != null) {
//
//      String userAgent = request.getParameter("eaiuseragent");// user-agent
//      // ��ȡrequest �� user-agent
//      String curUserAgent = httpRequest.getHeader("User-Agent");
//
//      String redirectUrl = "https" + "://" + hst + httpRequest.getRequestURI();
//      String params = httpRequest.getQueryString();
//
//      if (userAgent == null) {
//        redirectUrl += "?" + params + "&eaiuseragent=" + curUserAgent + "&eaicount=0";
//        httpResponse.sendRedirect(redirectUrl);
//        return;
//      } else {
//        String count = request.getParameter("eaicount");// ������ count
//        if (userAgent.equals(curUserAgent) && "0".equals(count)) {
//          redirectUrl += "?" + params.replace("eaicount=0", "eaicount=1");
//          httpResponse.setContentType("text/html");
//          httpResponse.getWriter().println(String.format("<script language='javascript'>window.location='%s';</script>", redirectUrl));
//          httpResponse.flushBuffer();
//          return;
//        } else {
//          if (!userAgent.equals(curUserAgent)) {
//            httpResponse.sendRedirect((isForceHttpsHost(this.forceHttpsHost, hst) ? "https" : pro) + "://" + hst + reURL);
//            return;
//          }
//        }
//      }
//    }

//    String forceProtocol = request.getParameter("forceprotocol");
//    // ǿ���ж�httpsת��eaiweb������(����1)
//    if (httpRequest.getRequestURI().contains("login/info.do") && httpRequest.getQueryString() != null && !"https".equalsIgnoreCase(forceProtocol) && pro != null && hst != null
//        && !"https".equalsIgnoreCase(pro)) {
//      String redirectUrl = "https" + "://" + hst + httpRequest.getRequestURI();
//
//      String params = httpRequest.getQueryString();
//      if (params != null && !"".equals(params)) {
//        redirectUrl += "?" + params + "&forceprotocol=https";
//      } else {
//        redirectUrl += "?" + "forceprotocol=https";
//      }
//      httpResponse.sendRedirect(redirectUrl);
//      return;
//    }

    String via = httpRequest.getHeader("Via"); 
    //ǿ��ת��eaiweb������Ϊhttps
    if(httpRequest.getRequestURI().contains("login/info.do") && httpRequest.getQueryString()!=null && via!=null && !via.contains("443")) {
        String redirectUrl = "https" + "://" + via.substring(via.indexOf(" "), via.indexOf(":")) + httpRequest.getRequestURI();
        String params = httpRequest.getQueryString(); 

        redirectUrl += "?" + params; 
         
        httpResponse.sendRedirect(redirectUrl); 
        return; 
    }
    
    ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
    // Need to authenticate?
    if (needToAuthenticate(httpRequest)) {
      if (log.isDebugEnabled()) {
        log.debug("Need to authenticate ......");
      }

      AuthenLevelDirector authenLevelDirector = (AuthenLevelDirector) applicationContext.getBean("authenLevelDirector", AuthenLevelDirector.class);
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
      PostAuthenticationCallback handler = (PostAuthenticationCallback) applicationContext.getBean("siam.sp.webseal.eai.handler");
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
   * @param reURL
   */
  public static void remeberEAIReturnUrl(HttpServletRequest httpRequest, String appUrl, String forceHttpsHost) {
    HttpSession session = httpRequest.getSession(true);
    log.debug("set session's eai-redir-url-header=" + appUrl);
    session.setAttribute(AccessEnforcer.SESSION_ATTR_NAME_EAI_RETURN_URL, appUrl);
    httpRequest.setAttribute(AccessEnforcer.SESSION_ATTR_NAME_EAI_RETURN_URL, appUrl);
  }

  /**
   * @see Filter#destroy()
   */
  public void destroy() {

  }

  /**
   * forceHttpsHost���ŷָ�,������hostname,�����򶼱�ǿ��ת�� Ϊ https���� ����:
   * hostname1,hostname2,hostname3
   * 
   * @param hostName
   *          url �� hostname
   * @return
   */
  protected static boolean isForceHttpsHost(String forceHttpsHost, String hostName) {
    boolean bResult = false;

    if (forceHttpsHost.contains(hostName.toLowerCase())) {
      bResult = true;
    }
    return bResult;
  }

  // ���Ϊ�ظ�url
  protected static String decorateReturnURL(String url) {
    String sResult = url;
    if (url == null) {
      return sResult;
    }
    // ���Return URL�а���/pkms*, ����������װ��
    if (url.indexOf("/pkms") >= 0) {
      log.debug(String.format("Decorate EAI Return URL: found WebSEAL control URL, and keep original URL: [%s]", url));
      return sResult;
    }
    if (url != null && !"".equals(url) && url.indexOf("eairepeat=1") < 0) {
      if (url.indexOf("?") >= 0) {
        sResult = url + "&eairepeat=1";
      } else {
        sResult = url + "?eairepeat=1";
      }
    }
    log.debug(String.format("Decorate EAI Return URL: append 'eairepeater' URL: [%s] -> [%s]", url, sResult));
    return sResult;
  }

  // �ж��Ƿ�Ϊ�ظ�����
  protected boolean isDecorateReturnURL(String url) {
    boolean bResult = false;

    if (url != null && !"".equals(url) && url.indexOf("eairepeat=1") >= 0) {
      bResult = true;
    }

    return bResult;
  }

  private void terminateAccess(ServletRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
    String msg = null;
    if (request.getParameter("ERROR_CODE") == null || request.getParameter("ERROR_CODE").trim().length() == 0) {
        msg = String.format("URL=%s<br/>ERROR_TEXT=%s<br/>PROTOCOL=%s<br/>METHOD=%s<br/>HOSTNAME=%s<br/>ERROR_CODE=%s<br/>FAILREASON=%s<br/>",request.getParameter("URL"),
             "repeat action url",request.getParameter("PROTOCOL"), "eai auth",  request.getParameter("HOSTNAME"),"repeat action url" , "repeat action url" );
    
    } else {
      // Build message from WebSEAL URL parameter
      msg = this.buildErrorMessage(httpRequest);
    }
    Exception e = new Exception(msg);
    request.setAttribute(AbstractErrorHandler.ERROR_KEY, e);

    httpRequest.getRequestDispatcher("/error.do").forward(httpRequest, httpResponse);
  }

  private boolean containePDSessionCookie(HttpServletRequest request) {
    for (Cookie cookie : request.getCookies()) {
      if (StringUtils.isNotEmpty(this.pdSessionCookieName) && this.pdSessionCookieName.equals(cookie.getName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param request
   * @param httpRequest
   * @param httpResponse
   * @throws ServletException
   * @throws IOException
   */
  private void handleErrorOP(ServletRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
    String msg = buildErrorMessage(request);
    Exception e = new Exception(msg);
    request.setAttribute(AbstractErrorHandler.ERROR_KEY, e);

    // if ("Not Found".equals(request.getParameter("ERROR_TEXT"))) {
    // httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
    // }
    httpRequest.getRequestDispatcher("/error.do").forward(httpRequest, httpResponse);
  }

  /**
   * @param request
   * @return
   */
  private String buildErrorMessage(ServletRequest request) {
    String msg = String.format("URL=%s<br/>ERROR_TEXT=%s<br/>PROTOCOL=%s<br/>METHOD=%s<br/>HOSTNAME=%s<br/>ERROR_CODE=%s<br/>FAILREASON=%s<br/>",
        request.getParameter("URL"),request.getParameter("ERROR_TEXT"), request.getParameter("PROTOCOL"),  request.getParameter("METHOD"),
          request.getParameter("HOSTNAME"),request.getParameter("ERROR_CODE"), request.getParameter("FAILREASON"));
    return msg;
  }

  /**
   * ���� Servlet ��Url��
   * 
   * @param request
   * @return string url
   */
  private String buildServletUrl(HttpServletRequest request) {
    StringBuilder actionUrlBuilder = new StringBuilder();

    if (!"".equals(request.getContextPath())) {
      actionUrlBuilder.append(request.getContextPath());
    }

    actionUrlBuilder.append(request.getServletPath());
    return actionUrlBuilder.toString();
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
  public static void doAuthentication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AuthenLevelDirector authenLevelDirector,
      String defaultAuthenticationMethod, String authenLevelHeaderName, boolean alwaysReauthen) {
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
   * ������;����¼HttpRequest����־��.
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
   * @param httpRequest
   * @param httpResponse
   * @param handlerManager
   * @param requiredAuthenticationMethod
   * @param alwaysReauthen
   */
  private static void sendAuthenticationRequest(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String requiredAuthenticationMethod, boolean alwaysReauthen,
      String themeOfIdPLoginPage) {

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
      // httpResponse.sendRedirect(authnEngineUrl);
      httpRequest.getRequestDispatcher("/AuthnEngine").forward(httpRequest, httpResponse);
    } catch (Exception e) {
      httpRequest.setAttribute(AbstractErrorHandler.ERROR_KEY, e);
      log.error("Failure to process SAML2 Authen request, cause: " + e.getMessage(), e);
    }
  }

  /**
   * ��ȡ�Ự��֤����
   * 
   * @param httpRequest
   *          HTTPRequest�������
   * @return
   */
  private static String getRequiredAuthenLevel(String authenLevelHeaderName, HttpServletRequest httpRequest) {
    if (authenLevelHeaderName == null) {
      return null;
    }
    String requiredAutheLevel = httpRequest.getParameter(authenLevelHeaderName);
    if (StringUtils.isEmpty(requiredAutheLevel)) {
      HttpSession session = httpRequest.getSession(false);
      if (session != null && session.getAttribute(SSOPrincipal.LAST_REQUIRED_AUTHEN_LEVEL_OF_SESSION_ATTR) != null) {
        requiredAutheLevel = (String) session.getAttribute(SSOPrincipal.LAST_REQUIRED_AUTHEN_LEVEL_OF_SESSION_ATTR);
        if (log.isDebugEnabled()) {
          log.debug(String.format("Get session authen level [%s]", requiredAutheLevel));
        }
      }
    }
    return requiredAutheLevel;
  }

  /**
   * ��������а�����AUTHN_LEVEL��Ҫ��Ŀǰ����WebSEAL EAI����ʱ����WebSEAL����������ڻỰ���ݴ���֤����.
   * 
   * @param httpRequest
   *          HTTPRequest�������
   * @return
   */
  private static void setRequiredAuthenLevel(String authenLevelHeaderName, HttpServletRequest httpRequest) {
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
   * ��ȡ�Ự��֤����
   * 
   * @param httpRequest
   *          HTTPRequest�������
   * @return
   */
  private static String getRequiredTheme(String themeParamName, HttpServletRequest httpRequest) {
    String themeOfIdpLoginPage = httpRequest.getParameter(themeParamName);
    if (StringUtils.isEmpty(themeOfIdpLoginPage)) {
      HttpSession session = httpRequest.getSession(false);
      if (session != null && session.getAttribute(ThemeDirector.THEME_ATTR_NAME) != null) {
        themeOfIdpLoginPage = (String) session.getAttribute(ThemeDirector.THEME_ATTR_NAME);
        if (log.isDebugEnabled()) {
          log.debug(String.format("Get session theme name [%s]", themeOfIdpLoginPage));
        }
      }
    }
    return themeOfIdpLoginPage;
  }

  /**
   * ��������а�����Idp��¼ҳ����ʽ���Ƶ�Ҫ�����ڻỰ���ݴ���ʽ����.
   * 
   * @param httpRequest
   *          HTTPRequest�������
   * @return
   */
  private static void setRequiredTheme(String themeParamName, HttpServletRequest httpRequest) {
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
   * ��ȡ������֤����
   * 
   * @param httpRequest
   *          HTTPRequest�������
   * @param authenLevelDirector
   *          ��֤���������
   * @return
   */
  private static String getRequiredAuthenMethod(HttpServletRequest httpRequest, AuthenLevelDirector authenLevelDirector, String defaultAuthenticationMethod,
      String authenLevelHeaderName) {
    String requiredAutheLevel = getRequiredAuthenLevel(authenLevelHeaderName, httpRequest);
    String requiredAuthenticationMethod = authenLevelDirector.getMatachedAuthenMethod(requiredAutheLevel);
    if (log.isDebugEnabled()) {
      log.debug(String.format("Request authen method [%s=%s]", requiredAutheLevel, requiredAuthenticationMethod));
    }
    if (requiredAuthenticationMethod == null || "".equals(requiredAuthenticationMethod)) {
      // Use default authentication method
      requiredAuthenticationMethod = defaultAuthenticationMethod;
    }
    if (log.isDebugEnabled()) {
      log.debug(String.format("Set authen method [%s]", requiredAuthenticationMethod));
    }
    return requiredAuthenticationMethod;
  }

  /**
   * ���ݵ�ǰ��Ӧ�ñ��صĻỰ״̬����֤�ļ����ж��Ƿ���Ҫ������֤��
   * 
   * @param session
   * @return
   */
  protected boolean needToAuthenticate(HttpServletRequest httpRequest) {
    boolean authenticated = this.isAuthenticated(httpRequest);
    if (this.eaiAuthen) {
      boolean matchWithRequiredAuthenLevel = this.isMatchWithRequiredAuthenLevel(httpRequest);
      if (log.isDebugEnabled()) {
        log.debug(String.format("[%s]:check authen state,eaiAuthen=[%s], authenticated=[%s], matchAuthenLevel=[%s]", this.filterConfig.getFilterName(), this.eaiAuthen,
            authenticated, matchWithRequiredAuthenLevel));
      }
      return !authenticated || !matchWithRequiredAuthenLevel;
    }
    boolean matchWithRequiredAuthenMethod = isMatchWithRequiredAuthenMethod(httpRequest);
    if (log.isDebugEnabled()) {
      log.debug(String.format("[%s]:check authen state,eaiAuthen=[%s], authenticated=[%s], matchAuthenMethod=[%s]", this.filterConfig.getFilterName(), this.eaiAuthen,
          authenticated, matchWithRequiredAuthenMethod));
    }
    return !authenticated || !matchWithRequiredAuthenMethod;
  }

  /**
   * �жϵ�ǰ�û��Ƿ��Ѿ���֤
   * 
   * @param httpRequest
   * @return true �Ѿ���֤
   */
  protected boolean isAuthenticated(HttpServletRequest httpRequest) {
    HttpSession session = httpRequest.getSession(false);
    if (session != null && session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR) != null) {
      return true;
    }
    return false;
  }

  /**
   * �жϵ�ǰ�û�����֤�����Ƿ����㱻������ԴҪ�����֤������
   * 
   * @param httpRequest
   * @return true - ����Ҫ��
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
   * �жϵ�ǰ�û�����֤�����Ƿ����㱻������ԴҪ��ļ���
   * 
   * @param httpRequest
   * @return true - ����Ҫ��
   * 
   */
  protected boolean isMatchWithRequiredAuthenLevel(HttpServletRequest httpRequest) {
    // Get AUTHEN_LEVEL sent by WebSEAL
    String requiredAutheLevel = httpRequest.getParameter(this.AUTHN_LEVEL_FIELD);
    if (StringUtils.isEmpty(requiredAutheLevel)) {
      return true;
    }

    HttpSession session = httpRequest.getSession(false);
    if (session == null) {
      return false;
    }

    SSOPrincipal principal = (SSOPrincipal) session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR);
    if (principal == null) {
      return false;
    }
    String maxSucceedAuthenLevel = principal.getMaxSucceedAuthenLevel();
    return requiredAutheLevel.compareTo(maxSucceedAuthenLevel) <= 0;
  }

}
