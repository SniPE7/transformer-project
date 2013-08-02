package com.sinopec.siam.am.idp.authn.provider.abs;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.opensaml.util.storage.StorageService;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.ibm.siam.am.idp.authn.AuthenticationEngine;
import com.sinopec.siam.am.idp.authn.LoginContextManager;
import com.sinopec.siam.am.idp.authn.principal.abs.ActiveDirectoryPrincipal;
import com.sinopec.siam.am.idp.authn.principal.abs.PersonBundleStatusPrincipal;
import com.sinopec.siam.am.idp.authn.principal.abs.PersonPrincipal;
import com.sinopec.siam.am.idp.authn.service.abs.PersonBundleStatus;
import com.sinopec.siam.am.idp.authn.service.abs.PersonService;
import com.sinopec.siam.am.idp.authn.service.abs.PersonServiceFactory;
import com.sinopec.siam.am.idp.management.IdPMonitor;
import com.sinopec.siam.am.idp.management.IdPMonitorFactory;
import com.sinopec.siam.am.utils.DesUtils;
import com.sinopec.siam.audit.appender.W7LogFactory;
import com.sinopec.siam.audit.logger.W7Logger;
import com.sinopec.siam.audit.model.W7Event;
import com.sinopec.siam.audit.model.W7OnWhat;
import com.sinopec.siam.audit.model.W7What;
import com.sinopec.siam.audit.model.W7Where;
import com.sinopec.siam.audit.model.W7Who;
import com.sinopec.siam.audit.util.Util;

import edu.internet2.middleware.shibboleth.idp.authn.AuthenticationException;
import edu.internet2.middleware.shibboleth.idp.authn.LoginContextEntry;
import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;
import edu.internet2.middleware.shibboleth.idp.authn.UsernamePrincipal;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * Servlet， 用于登陆框及登陆认证。
 * 
 * @author Booker
 * 
 */
public class ABSUserBindAndAuthenLoginServlet extends HttpServlet {

  /** Serial version UID. */
  private static final long serialVersionUID = -572799841125956990L;

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(ABSUserBindAndAuthenLoginServlet.class);

  /** The authentication method returned to the authentication engine. */
  private String authenticationMethod = "urn:oasis:names:tc:SAML:2.0:ac:classes:ABSUserBindAndAuthen";

  /** Name of JAAS configuration used to authenticate users. */
  private String jaasConfigName = "ABSUserBindAndAuthen";

  /**
   * init-param which can be passed to the servlet to override the default JAAS
   * config.
   */
  private final String jaasInitParam = "jaasConfigName";

  /** Login page name. */
  private String loginPage = "/login/abs/loginform.do";

  /** Reset password page name. */
  private String resetPasswordPage = "/resetpassword.jsp";

  /**
   * PersonService factory class name.
   */
  private String personServiceFactoryClassName;
  
  /**
   * IdPMonitor factory class name.
   */
  private String idPMonitorFactoryClassName;

  /**
   * init-param which can be passed to the servlet to override the default login
   * page.
   */
  private final String loginPageInitParam = "loginPage";

  /**
   * Init-param of reset password page.
   */
  private String resetPasswordPageInitParam = "resetPasswordPage";

  /**
   * Init-param of PersonServiceFactoryClass.
   */
  private String personServiceFactoryClassInitParam = "personServiceFactoryClassName";

  /**
   * Init-param of IdPMonitorFactoryClass.
   */
  private String idPMonitorFactoryClassInitParam = "idpMonitorFactoryClassName";

  /** Parameter name to indicate login failure. */
  private final String failureParam = "loginFailed";

  /** HTTP request parameter containing the user name. */
  private final String usernameAttribute = "j_username";

  /** HTTP request parameter containing the user's password. */
  private final String passwordAttribute = "j_password";

  /** HTTP request parameter containing the user's new password. */
  private final String newPasswordAttribute = "j_new_password";

  /** HTTP bind request parameter containing the return url. */
  private final String returnUrlAttribute = "returnUrl";

  /** HTTP bind request parameter containing the action operation. */
  private final String opAttribute = "op";

  /** Parameter name of adbind page. */
  private final String adBindPageInitParam = "adBindPage";

  /** Parameter name of crypt key. */
  private final String cryptKeyInitParam = "cryptKey";

  /** Parameter name of w7 properties file. */
  private final String w7PropertiesFileParam = "w7LogPropertiesFile";

  /** w7 properties file. */
  private String w7PropertiesFile;

  /** Bind page name. */
  private String adBindPage = "/bind.jsp";

  private final int AUTH_NEED_MODIFY_PASSWORD = 1;
  private final int AUTH_NEED_BIND = 2;
  private final int AUTH_COMPLETE = 0;

  private final String OP_LOGIN = "login";
  private final String OP_PASSWORD_RESET = "resetpassword";
  private final String OP_AD_BIND_OVER = "adbindover";

  /**
   * Des 密钥。
   */
  private long cryptKey = 0;

  /** W7 Logger instance. */
  private W7Logger w7Logger;
  
  /** Des Utils. */
  private DesUtils desUtils;

  /** {@inheritDoc} */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    if (getInitParameter(jaasInitParam) != null) {
      jaasConfigName = getInitParameter(jaasInitParam);
    }

    if (getInitParameter(loginPageInitParam) != null) {
      loginPage = getInitParameter(loginPageInitParam);
    }

    if (!loginPage.startsWith("/")) {
      loginPage = "/" + loginPage;
    }

    if (getInitParameter(resetPasswordPageInitParam) != null) {
      resetPasswordPage = getInitParameter(resetPasswordPageInitParam);
    }

    if (!resetPasswordPage.startsWith("/")) {
      resetPasswordPage = "/" + resetPasswordPage;
    }

    if (getInitParameter(adBindPageInitParam) != null) {
      adBindPage = getInitParameter(adBindPageInitParam);
    }

    String method = DatatypeHelper
        .safeTrimOrNullString(config.getInitParameter(LoginHandler.AUTHENTICATION_METHOD_KEY));

    if (method != null) {
      authenticationMethod = method;
    }

    if (getInitParameter(cryptKeyInitParam) != null) {
      try {
        cryptKey = Long.parseLong(getInitParameter(cryptKeyInitParam));
      } catch (NumberFormatException e) {
        log.warn(String.format("Crypt key format error. %s", cryptKey), e);
      }
    }

    if (getInitParameter(personServiceFactoryClassInitParam) != null) {
      personServiceFactoryClassName = getInitParameter(personServiceFactoryClassInitParam);

      if (personServiceFactoryClassName == null || "".equals(personServiceFactoryClassName)) {
        throw new RuntimeException("personServiceFactoryClassName is null");
      }
    }
    
    if (getInitParameter(idPMonitorFactoryClassInitParam) != null) {
      idPMonitorFactoryClassName = getInitParameter(idPMonitorFactoryClassInitParam);

      if (idPMonitorFactoryClassName == null || "".equals(idPMonitorFactoryClassName)) {
        throw new RuntimeException("idPMonitorFactoryClassName is null");
      }
    }

    if (getInitParameter(w7PropertiesFileParam) != null) {
      w7PropertiesFile = getInitParameter(w7PropertiesFileParam);

      if (StringUtils.isEmpty(w7PropertiesFile)) {
        throw new RuntimeException("w7PropertiesFile is null");
      }

      w7Logger = W7LogFactory.getAppenderInstance(Util.loadProperties(w7PropertiesFile));
    }
  }

  /** {@inheritDoc} */
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    session.getServletContext();

    String op = request.getParameter(opAttribute);

    if (OP_LOGIN.equals(op)) {
      processLogin(request, response);
    } else if (OP_PASSWORD_RESET.equals(op)) {
      processResetPassword(request, response);
    } else if (OP_AD_BIND_OVER.equals(op)) {
      processAdBindOver(request, response);
    } else {
      redirectToLoginPage(request, response);
      return;
    }
  }

  /**
   * 处理 AD 账号绑定返回。
   * 
   * @param request
   * @param response
   */
  private void processAdBindOver(HttpServletRequest request, HttpServletResponse response) {
    String username = request.getParameter(usernameAttribute);
    String password = request.getParameter(passwordAttribute);

    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
      redirectToLoginPage(request, response);
      return;
    }

    if ("GET".equalsIgnoreCase(request.getMethod())) {
      try {
        DesUtils des = new DesUtils(new Long(cryptKey).toString());
        password = des.decrypt(password);
      } catch (Exception e) {
        log.warn("Password decrypt error.", e);
      }
    }

    W7Event event = createW7Event(request);
    event.getWho().setAuthenName(username);

    Subject subject = null;

    try {
      subject = authenticateUser(username, password);
    } catch (LoginException e) {
      event.getWhat().setSuccess("F");
      event.setInfo("认证失败：" + e.getMessage());
      w7Logger.log(event);

      request.setAttribute(failureParam, "true");
      request.setAttribute(LoginHandler.AUTHENTICATION_EXCEPTION_KEY, new AuthenticationException(e.getMessage()));
      redirectToLoginPage(request, response);
      return;
    }

    completeAuthentication(request, subject, username, password);

    event.getWhat().setSuccess("S");
    event.setInfo("认证通过，进入应用系统。");
    event.getWho().setRealName(getUserRealName(subject));
    w7Logger.log(event);

    AuthenticationEngine.returnToAuthenticationEngine(this.getServletContext(), request, response);
  }

  /**
   * 处理密码重置。
   * 
   * @param request
   * @param response
   */
  private void processResetPassword(HttpServletRequest request, HttpServletResponse response) {
    String username = request.getParameter(usernameAttribute);
    String password = request.getParameter(passwordAttribute);
    String nPassword = request.getParameter(newPasswordAttribute);

    if (StringUtils.isEmpty(username)) {
      redirectToLoginPage(request, response);
      return;
    }

    if (StringUtils.isEmpty(password) || StringUtils.isEmpty(nPassword)) {
      request.setAttribute(failureParam, "true");
      request.setAttribute(LoginHandler.AUTHENTICATION_EXCEPTION_KEY, new AuthenticationException(
          "Password should not be null."));
      redirectToResetPasswordPage(request, response, username);
      return;
    }

    W7Event event = createW7Event(request);
    event.getWho().setAuthenName(username);
    Subject subject = null;

    try {
      subject = authenticateUser(username, password);
    } catch (LoginException e) {
      event.getWhat().setSuccess("F");
      event.setInfo("密码修改失败: " + e.getMessage());
      w7Logger.log(event);

      request.setAttribute(failureParam, "true");
      request.setAttribute(LoginHandler.AUTHENTICATION_EXCEPTION_KEY, new AuthenticationException(e.getMessage()));
      redirectToResetPasswordPage(request, response, username);
      return;
    }

    getPersonService().updateTamPassword(username, password.getBytes(), nPassword.getBytes());
    completeAuthentication(request, subject, username, password);

    event.getWhat().setSuccess("S");
    event.setInfo("密码修改成功。");
    event.getWho().setRealName(getUserRealName(subject));
    w7Logger.log(event);

    AuthenticationEngine.returnToAuthenticationEngine(this.getServletContext(), request, response);
  }

  /**
   * 处理登录事件。
   * 
   * @param request
   * @param response
   */
  private void processLogin(HttpServletRequest request, HttpServletResponse response) {
    String username = request.getParameter(usernameAttribute);
    String password = request.getParameter(passwordAttribute);

    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
      redirectToLoginPage(request, response);
      return;
    }

    W7Event event = createW7Event(request);
    event.getWho().setAuthenName(username);
    Subject subject = null;

    // 获取LoginContext, 一边读取SPID 和 Authen Method
    StorageService<String, LoginContextEntry> storageS = HttpServletHelper.getLoginContextStorageService(getServletContext());
    edu.internet2.middleware.shibboleth.idp.authn.LoginContext loginContext = HttpServletHelper.getLoginContext(
        storageS, getServletContext(), request);

    try {
      subject = authenticateUser(username, password);
    } catch (LoginException e) {
      event.getWhat().setSuccess("F");
      event.setInfo("认证失败：" + e.getMessage());
      w7Logger.log(event);

      request.setAttribute(failureParam, "true");
      request.setAttribute(LoginHandler.AUTHENTICATION_EXCEPTION_KEY, new AuthenticationException(e.getMessage()));
      redirectToLoginPage(request, response);

      return;
    }

    int res = checkUserStatus(subject);
    event.getWhat().setSuccess("S");
    event.getWho().setRealName(getUserRealName(subject));

    if (res == this.AUTH_COMPLETE) {
      event.setInfo("认证通过，进入应用系统。");
      w7Logger.log(event);

      completeAuthentication(request, subject, username, password);
      AuthenticationEngine.returnToAuthenticationEngine(this.getServletContext(), request, response);
      log.debug(String.format("Authenticate successful. %s/%s.", username, password));
    } else if (res == this.AUTH_NEED_BIND) {
      event.setInfo("认证通过，重定向到AD绑定页。");
      w7Logger.log(event);

      redirectToAdBindPage(request, response, username, password);
    } else if (res == this.AUTH_NEED_MODIFY_PASSWORD) {
      event.setInfo("认证通过，首次登录需修改密码。");
      w7Logger.log(event);

      redirectToResetPasswordPage(request, response, username);
    }
  }

  /**
   * 构建 Servlet 的Url。
   * 
   * @param request
   * @return
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
   * 跳转到重置密码页。
   * 
   * @param request
   * @param response
   */
  private void redirectToResetPasswordPage(HttpServletRequest request, HttpServletResponse response, String username) {
    request.setAttribute("actionUrl", buildServletUrl(request));

    try {
      request.setAttribute(usernameAttribute, username);
      request.getRequestDispatcher(resetPasswordPage).forward(request, response);
      if(log.isDebugEnabled()){
        log.debug("Redirecting to reset password page page {}", resetPasswordPage);
      }
    } catch (IOException ex) {
      log.error("Unable to redirect to reset password page.", ex);
    } catch (ServletException ex) {
      log.error("Unable to redirect to reset password page.", ex);
    }
  }

  /**
   * 如果AD用户未注册统一账户，在此绑定。
   * 
   * @param request
   * @param response
   * @param username
   * @throws LoginException
   */
  private void redirectToAdBindPage(HttpServletRequest request, HttpServletResponse response, String username,
      String password) {
    String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
        + this.buildServletUrl(request);
    StringBuffer redirectUrl = new StringBuffer();
    redirectUrl.append(adBindPage).append("?");
    redirectUrl.append(returnUrlAttribute).append("=").append(returnUrl).append("&");
    redirectUrl.append(opAttribute).append("=").append(OP_AD_BIND_OVER).append("&");
    redirectUrl.append(usernameAttribute).append("=").append(username).append("&");
    redirectUrl.append(passwordAttribute).append("=");
    // 密码经过3DES加密
    try {
      DesUtils des = new DesUtils(new Long(cryptKey).toString());
      redirectUrl.append(des.encrypt(password));
    } catch (Exception e) {
      redirectUrl.append(password);
      log.warn("Password encrypt error.", e);
    }
    

    try {
      response.sendRedirect(redirectUrl.toString());
    } catch (IOException e) {
      log.error("Unable to redirect to ad bind page.", e);
    }
  }

  /**
   * Sends the user to the login page.
   * 
   * @param request
   *          current request
   * @param response
   *          current response
   */
  private void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute("actionUrl", buildServletUrl(request));

    try {
      request.getRequestDispatcher(loginPage).forward(request, response);
      if(log.isDebugEnabled()){
        log.debug("Redirecting to login page {}", loginPage);
      }
    } catch (IOException ex) {
      log.error("Unable to redirect to login page.", ex);
    } catch (ServletException ex) {
      log.error("Unable to redirect to login page.", ex);
    }
  }

  /**
   * 通过用户名密码认证用户，返回认证结果的Subject。
   * 
   * @update zhangxianwen 2012-04-19 10:52 将LoginContext由Spring容器管理
   * 
   * @param username
   * @param password
   * @return
   * @throws LoginException
   */
  private Subject authenticateUser(String username, String password) throws LoginException {
    Subject subject = new Subject();
    ABSCallbackHandler cbh = new ABSCallbackHandler(username, password);
    // Get Spring Bean Factory
    ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
    LoginContextManager loginContextManager = appContext.getBean("loginContextManager", LoginContextManager.class);
    LoginContext jaasLoginCtx = loginContextManager.getLoginContext(jaasConfigName, subject, cbh);
    jaasLoginCtx.login();
    return subject;
  }

  /**
   * 检查Subject的状态，认证完成？需要绑定？需要重设密码？
   * 
   * @param subject
   * @return
   */
  private int checkUserStatus(Subject subject) {
    Set<Principal> principals = subject.getPrincipals();
    Iterator<Principal> principalIt = principals.iterator();
    Principal principal = null;

    while (principalIt.hasNext()) {
      principal = principalIt.next();

      if (principal instanceof PersonBundleStatusPrincipal) {
        if (PersonBundleStatus.ONLY_AD_ACCOUNT.equals(((PersonBundleStatusPrincipal) principal).getStatus())) {
          return AUTH_NEED_BIND;
        }
      } else if (principal instanceof PersonPrincipal) {
        if (((PersonPrincipal) principal).isNeedToChangePasswordInFirstLogin()) {
          return AUTH_NEED_MODIFY_PASSWORD;
        }
      }
    }

    return AUTH_COMPLETE;
  }

  private void completeAuthentication(HttpServletRequest request, Subject subject, String username, String password) {
    // 创建 Subject key
    Set<Principal> principals = subject.getPrincipals();
    principals.add(new UsernamePrincipal(username));

    Set<Object> publicCredentials = subject.getPublicCredentials();
    publicCredentials.add(username);

    Set<Object> privateCredentials = subject.getPrivateCredentials();
    privateCredentials.add(password);

    Subject userSubject = new Subject(false, principals, publicCredentials, privateCredentials);
    request.setAttribute(LoginHandler.SUBJECT_KEY, userSubject);
    request.setAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY, authenticationMethod);
  }

  /**
   * Get instance of PersonService.
   * 
   * @return
   */
  private PersonService getPersonService() {
    try {
      Class clazz = Class.forName(personServiceFactoryClassName);
      PersonServiceFactory factory = (PersonServiceFactory) clazz.newInstance();
      PersonService personService = factory.getPersonService();
      return personService;
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(String.format("Could not found PersonServiceFactory by class name %s, cause: %s",
          personServiceFactoryClassName, e.getMessage()));
    } catch (InstantiationException e) {
      throw new RuntimeException(String.format("Could instance PersonServiceFactory by class name %s, cause: %s",
          personServiceFactoryClassName, e.getMessage()));
    } catch (IllegalAccessException e) {
      throw new RuntimeException(String.format("Could instance PersonServiceFactory by class name %s, cause: %s",
          personServiceFactoryClassName, e.getMessage()));
    }
  }

  /**
   * Get instance of idp monitor.
   */
  private IdPMonitor getIdPMonitor() {
    try {
      Class clazz = Class.forName(idPMonitorFactoryClassName);
      IdPMonitorFactory factory = (IdPMonitorFactory) clazz.newInstance();
      IdPMonitor monitor = factory.getIdPMonitor();
      return monitor;
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(String.format("Could not found idPMonitorFactory by class name %s, cause: %s",
          idPMonitorFactoryClassName, e.getMessage()));
    } catch (InstantiationException e) {
      throw new RuntimeException(String.format("Could instance idPMonitorFactory by class name %s, cause: %s",
          idPMonitorFactoryClassName, e.getMessage()));
    } catch (IllegalAccessException e) {
      throw new RuntimeException(String.format("Could instance idPMonitorFactory by class name %s, cause: %s",
          idPMonitorFactoryClassName, e.getMessage()));
    }
  }

  /**
   * 返回用户的真实姓名。
   * 
   * @param subject
   * @return
   */
  private String getUserRealName(Subject subject) {
    String realname = null;

    Set<Principal> principals = subject.getPrincipals();
    Iterator<Principal> principalIt = principals.iterator();
    Principal principal = null;

    while (principalIt.hasNext()) {
      principal = principalIt.next();

      if (principal instanceof ActiveDirectoryPrincipal) {
        realname = ((ActiveDirectoryPrincipal) principal).getDn();
        break;
      } else if (principal instanceof PersonPrincipal) {
        realname = ((PersonPrincipal) principal).getDn();
        break;
      }
    }

    return realname;
  }

  /**
   * 构建审计日志。
   */
  private W7Event createW7Event(HttpServletRequest request) {
    StorageService<String, LoginContextEntry> storageS = HttpServletHelper.getLoginContextStorageService(getServletContext());
    edu.internet2.middleware.shibboleth.idp.authn.LoginContext loginContext = HttpServletHelper.getLoginContext(
        storageS, getServletContext(), request);
    // 登录时间
    Date when = new Date();
    // 账号、密码
    W7Who who = new W7Who();
    // 单点登录应用系统，成功/失败
    W7What what = new W7What();
    // 从某应用系统跳转到某应用系统ID和名称，单点登录类型SAML
    W7OnWhat onWhat = new W7OnWhat();
    // 通过浏览器访问的客户端IP
    W7Where where = new W7Where();
    // SP地址
    W7Where fromWhere = new W7Where();
    // IDP地址
    W7Where toWhere = new W7Where();

    where.setName(request.getRemoteAddr());
    where.setType("IP");
    what.setVerb("login");
    onWhat.setPath("IDP");
    fromWhere.setName("SP");

    if (loginContext != null) {
      who.setAuthenType(loginContext.getAttemptedAuthnMethod());
      String replayPartyId = loginContext.getRelyingPartyId();
      String idpId = HttpServletHelper.getRelyingPartyConfigurationManager(getServletContext())
          .getRelyingPartyConfiguration(replayPartyId).getProviderId();
      onWhat.setName(idpId);
      fromWhere.setName(replayPartyId);
    }

    W7Event event = new W7Event();
    event.setWhenTime(when);
    event.setWho(who);
    event.setWhat(what);
    event.setOnWhat(onWhat);
    event.setWhere(where);
    event.setWhereFrom(fromWhere);
    event.setWhereTo(toWhere);
    return event;
  }
}
