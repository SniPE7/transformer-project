package com.sinopec.siam.am.idp.authn.provider.ad;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.opensaml.util.storage.StorageService;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.ibm.siam.am.idp.authn.AuthenticationEngine;
import com.sinopec.siam.am.idp.authn.LoginContextManager;
import com.sinopec.siam.am.idp.authn.principal.UserPrincipal;
import com.sinopec.siam.am.idp.authn.provider.SimpleCallbackHandler;
import com.sinopec.siam.am.idp.authn.service.UserService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;
import com.sinopec.siam.am.idp.management.IdPMonitor;
import com.sinopec.siam.am.idp.management.IdPMonitorFactory;
import com.sinopec.siam.am.idp.utils.message.I18NMessageUtils;
import com.sinopec.siam.audit.appender.W7LogFactory;
import com.sinopec.siam.audit.logger.W7Logger;
import com.sinopec.siam.audit.model.W7Event;
import com.sinopec.siam.audit.model.W7OnWhat;
import com.sinopec.siam.audit.model.W7What;
import com.sinopec.siam.audit.model.W7Where;
import com.sinopec.siam.audit.model.W7Who;
import com.sinopec.siam.audit.util.Util;
import com.sinopec.siam.utils.ExpressionEvaluator;
import com.sinopec.siam.utils.VariableResolver;

import edu.internet2.middleware.shibboleth.idp.authn.LoginContextEntry;
import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;
import edu.internet2.middleware.shibboleth.idp.authn.UsernamePrincipal;
import edu.internet2.middleware.shibboleth.idp.authn.provider.UsernamePasswordCredential;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;
import edu.vt.middleware.ldap.bean.LdapAttribute;
import edu.vt.middleware.ldap.jaas.LdapPrincipal;

/**
 * AD 用户名、口令登录
 * @author zhangxianwen
 * @since 2012-7-26 下午2:29:16
 */

public class ADUsernamePasswordLoginServlet extends HttpServlet {

  /** Serial version UID. */
  private static final long serialVersionUID = -4434018370876056615L;
  
  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(ADUsernamePasswordLoginServlet.class);
  
  /** The authentication method returned to the authentication engine. */
  private String authenticationMethod = "urn:oasis:names:tc:SAML:2.0:ac:classes:ADUsernamePassword";

  /** Name of JAAS configuration used to authenticate users. */
  private String jaasConfigName = "ClientCertAuth";

  /**
   * init-param which can be passed to the servlet to override the default JAAS
   * config.
   */
  private final String jaasInitParam = "jaasConfigName";

  /** Login page name. */
  private String loginPage = "login.jsp";

  /**
   * init-param which can be passed to the servlet to override the default login
   * page.
   */
  private final String loginPageInitParam = "loginPage";

  /** Parameter name to indicate login failure. */
  private String failureParam = "loginFailed";
  
  private final String failureInitParam = "failureParam";
  
  /** HTTP request parameter containing the user name. */
  private String usernameAttribute = "j_username";
  
  private final String usernameAttributeParam = "usernameAttributeParam";

  /** HTTP request parameter containing the user's password. */
  private String passwordAttribute = "j_password";
  
  private final String passwordAttributeParam = "passwordAttributeParam";
  
  /** Search TAM LDAP Filter */
  private String userFilter = "(&(sprolelist={cn})(objectclass=inetorgperson))";
  
  private final String userFilterInitParam = "userFilterInitParam";

  /** UserService Bean ID */
  private String userServiceId = "adTamLdapUserService";
  
  private final String userServiceIdInitParam = "userServiceIdInitParam";
  
  /** Parameter name of w7 properties file. */
  private final String w7PropertiesFileParam = "w7LogPropertiesFile";

  /** w7 properties file. */
  private String w7PropertiesFile;

  /** W7 Logger instance. */
  private W7Logger w7Logger;

  /** W7 event. */
  private W7Event event;

  /**
   * IdPMonitor factory class name.
   */
  private String idPMonitorFactoryClassName;

  /**
   * Init-param of IdPMonitorFactoryClass.
   */
  private String idPMonitorFactoryClassInitParam = "idpMonitorFactoryClassName";
  
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
    
    if (getInitParameter(failureInitParam) != null) {
      failureParam = getInitParameter(failureInitParam);
    }
    
    if (getInitParameter(usernameAttributeParam) != null) {
      usernameAttribute = getInitParameter(usernameAttributeParam);
    }
    
    if (getInitParameter(passwordAttributeParam) != null) {
      passwordAttribute = getInitParameter(passwordAttributeParam);
    }

    String method = DatatypeHelper
        .safeTrimOrNullString(config.getInitParameter(LoginHandler.AUTHENTICATION_METHOD_KEY));

    if (method != null) {
      authenticationMethod = method;
    }

    if (getInitParameter(userFilterInitParam) != null) {
      userFilter = getInitParameter(userFilterInitParam);
    }
    
    if (getInitParameter(userServiceIdInitParam) != null) {
      userServiceId = getInitParameter(userServiceIdInitParam);
    }
    
    if (getInitParameter(w7PropertiesFileParam) != null) {
      w7PropertiesFile = getInitParameter(w7PropertiesFileParam);

      if (StringUtils.isEmpty(w7PropertiesFile)) {
        throw new RuntimeException("w7PropertiesFile is null");
      }

      w7Logger = W7LogFactory.getAppenderInstance(Util.loadProperties(w7PropertiesFile));
    }

    if (getInitParameter(idPMonitorFactoryClassInitParam) != null) {
      idPMonitorFactoryClassName = getInitParameter(idPMonitorFactoryClassInitParam);

      if (idPMonitorFactoryClassName == null || "".equals(idPMonitorFactoryClassName)) {
        throw new RuntimeException("idPMonitorFactoryClassName is null");
      }
    }
  }

  /** {@inheritDoc} */
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter(usernameAttribute);
    String password = request.getParameter(passwordAttribute);

    if (username == null || password == null) {
      redirectToLoginPage(request, response);
      return;
    }

    event = createW7Event(request);
    event.getWho().setAuthenName(username);
    // 获取LoginContext, 一边读取SPID 和 Authen Method
    StorageService<String, LoginContextEntry> storageS = HttpServletHelper.getLoginContextStorageService(getServletContext());
    edu.internet2.middleware.shibboleth.idp.authn.LoginContext loginContext = HttpServletHelper.getLoginContext(
        storageS, getServletContext(), request);

    try {
      authenticateUser(request, username, password);

      event.getWhat().setSuccess("S");
      event.setInfo("认证通过，进入应用系统。");
      w7Logger.log(event);

      AuthenticationEngine.returnToAuthenticationEngine(this.getServletContext(), request, response);
    } catch (LoginException e) {
      event.getWhat().setSuccess("F");
      event.setInfo("认证失败：" + e.getMessage());
      w7Logger.log(event);

      request.setAttribute(failureParam, "true");
      //request.setAttribute(LoginHandler.AUTHENTICATION_EXCEPTION_KEY, new AuthenticationException(e));
      redirectToLoginPage(request, response);
    }
  }
  
  /**
   * Authenticate a username and password against JAAS. If authentication
   * succeeds the name of the first principal, or the username if that is empty,
   * and the subject are placed into the request in their respective attributes.
   * 
   * @param request
   *          current authentication request
   * @param username
   *          the principal name of the user to be authenticated
   * @param password
   *          the password of the user to be authenticated
   * 
   * @throws LoginException
   *           thrown if there is a problem authenticating the user
   */
  protected void authenticateUser(HttpServletRequest request, String username, String password) throws LoginException{
    if(log.isDebugEnabled()){
      log.debug("Attempting to authenticate user {}", username);
    }

    SimpleCallbackHandler cbh = new SimpleCallbackHandler(username, password);

    // Get Spring Bean Factory
    ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
    LoginContextManager loginContextManager = appContext.getBean("loginContextManager", LoginContextManager.class);
    LoginContext jaasLoginCtx = null;
    try {
      jaasLoginCtx = loginContextManager.getLoginContext(jaasConfigName, null, cbh);
      jaasLoginCtx.login();
    } catch (LoginException e) {
      log.info(String.format("Username not exists, username: %s,password: %s.", username, password));
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY,
          I18NMessageUtils.getMessage("logon.form.error.default"));
      throw new LoginException(String.format("authenticated failed, username: %s,password: %s, exception: %s.", username, password, e.getMessage()));
    }
    if(log.isDebugEnabled()){
      log.debug("Successfully authenticated user {}", username);
    }
    Subject loginSubject = jaasLoginCtx.getSubject();

    // 审计日志
    event.getWho().setRealName(getUserRealName(loginSubject));

    Set<Principal> principals = loginSubject.getPrincipals();
    principals.add(new UsernamePrincipal(username));
    
    Set<Object> publicCredentials = loginSubject.getPublicCredentials();

    Set<Object> privateCredentials = loginSubject.getPrivateCredentials();
    privateCredentials.add(new UsernamePasswordCredential(username, password));

    LdapPrincipal ldapPrincipal = (LdapPrincipal)principals.iterator().next();
    
    ExpressionEvaluator<LdapPrincipal> evaluator = new ExpressionEvaluator<LdapPrincipal>(new VariableResolver<LdapPrincipal>() {
      public String resolve(LdapPrincipal ldapPrincipal, String name) {
        LdapAttribute ldapAttribute = ldapPrincipal.getLdapAttributes().getAttribute(name);
        if (ldapAttribute == null) {
          return "";
        }else{
          return ldapAttribute.getValues().iterator().next().toString();
        }
      }
    });
    String filter = evaluator.evaluate(this.userFilter, ldapPrincipal);
    
    String uid = getUid(request, appContext, filter);
    principals.clear();
    principals.add(new UserPrincipal(uid));
    
    Subject userSubject = new Subject(false, principals, publicCredentials, privateCredentials);
    
    request.setAttribute(LoginHandler.SUBJECT_KEY, userSubject);
    request.setAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY, authenticationMethod);
  }
  
  /**
   * 获取登录用户uid
   * @param request HttpServletRequest
   * @param appContext ApplicationContext容器
   * @param filter filter用户Ldap查询条件
   * @return String uid
   * @throws LoginException 
   */
  private String getUid(HttpServletRequest request, ApplicationContext appContext, String filter) throws LoginException{
    UserService userService = getUserService(appContext);
    List<LdapUserEntity> ldapUserEntitys = userService.searchByFilter(filter);
    if (ldapUserEntitys.size() == 0) {
      log.info(String.format("Username not exists, filter: %s.", filter));
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY,
          I18NMessageUtils.getMessage("login.form.error.username.notExists"));
      throw new LoginException(String.format("Username not exists, filter: %s.", filter));
    } else if (ldapUserEntitys.size() > 1) {
      log.info(String.format("Find more than one user by filter,filter:%s.", filter));
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY,
          I18NMessageUtils.getMessage("login.form.error.username.more"));
      throw new LoginException(String.format("Find more than one user by filter,filter:%s.", filter));
    }
    return ldapUserEntitys.get(0).getUid();
  }
  
  /**
   * 获取UserService Bean
   * @param appContext ApplicationContext容器
   * @return UserService
   */
  private UserService getUserService(ApplicationContext appContext){
    return appContext.getBean(userServiceId, UserService.class);
  }
  
  /**
   * 获取登录后姓名。
   * 
   * @param loginSubject
   * @return
   */
  private String getUserRealName(Subject subject) {
    String realname = null;

    Set<Principal> principals = subject.getPrincipals();
    Iterator<Principal> principalIt = principals.iterator();
    Principal principal = null;

    while (principalIt.hasNext()) {
      principal = principalIt.next();

      if (principal.getName() != null) {
        realname = principal.getName();
        break;
      }
    }

    return realname;
  }
  
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
   * Sends the user to the login page.
   * 
   * @param request
   *          current request
   * @param response
   *          current response
   */
  protected void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response) {

    StringBuilder actionUrlBuilder = new StringBuilder();
    if (!"".equals(request.getContextPath())) {
      actionUrlBuilder.append(request.getContextPath());
    }
    actionUrlBuilder.append(request.getServletPath());

    request.setAttribute("actionUrl", actionUrlBuilder.toString());

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

}
