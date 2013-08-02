package com.sinopec.siam.am.idp.authn.module.itim;

import java.io.IOException;
import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.util.storage.StorageService;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.ibm.siam.am.idp.authn.AuthenticationEngine;
import com.sinopec.siam.am.idp.authn.LoginContextManager;
import com.sinopec.siam.am.idp.management.IdPMonitor;
import com.sinopec.siam.am.idp.management.IdPMonitorFactory;

import edu.internet2.middleware.shibboleth.idp.authn.AuthenticationException;
import edu.internet2.middleware.shibboleth.idp.authn.LoginContextEntry;
import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;
import edu.internet2.middleware.shibboleth.idp.authn.UsernamePrincipal;
import edu.internet2.middleware.shibboleth.idp.authn.provider.UsernamePasswordCredential;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * Servlet， 用于显示登录界面并控制登录
 * 
 * @author Booker
 * 
 */
public class ITIMLoginServlet extends HttpServlet {

  /** Serial version UID. */
  private static final long serialVersionUID = 8726381973243843216L;

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(ITIMLoginServlet.class);

  /** The authentication method returned to the authentication engine. */
  private String authenticationMethod = "urn:oasis:names:tc:SAML:2.0:ac:classes:ITIMUsernamePassword";

  /** Name of JAAS configuration used to authenticate users. */
  private String jaasConfigName = "IdpUserPassAuth";

  /**
   * init-param which can be passed to the servlet to override the default JAAS
   * config.
   */
  private final String jaasInitParam = "jaasConfigName";

  /** Login page name. */
  private String loginPage = "/login/itim/loginform.do";

  /** Parameter name to indicate login failure. */
  private final String failureParam = "loginFailed";

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

    String method = DatatypeHelper
        .safeTrimOrNullString(config.getInitParameter(LoginHandler.AUTHENTICATION_METHOD_KEY));
    if (method != null) {
      authenticationMethod = method;
    } else {
      authenticationMethod = AuthnContext.PPT_AUTHN_CTX;
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
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if (username == null) {
      // 没有提交用户名, 转入登录表单页面
      redirectToLoginPage(request, response);
      return;
    }

    // 获取LoginContext, 一边读取SPID 和 Authen Method
    StorageService<String, LoginContextEntry> storageS = HttpServletHelper.getLoginContextStorageService(getServletContext());
    edu.internet2.middleware.shibboleth.idp.authn.LoginContext loginContext = HttpServletHelper.getLoginContext(
        storageS, getServletContext(), request);

    try {
      authenticateUser(request, username, password);
      AuthenticationEngine.returnToAuthenticationEngine(this.getServletContext(), request, response);
    } catch (LoginException e) {
      request.setAttribute(failureParam, "true");
      request.setAttribute(LoginHandler.AUTHENTICATION_EXCEPTION_KEY, new AuthenticationException(e));
      redirectToLoginPage(request, response);
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
  protected void authenticateUser(HttpServletRequest request, String username, String password) throws LoginException {
    try {
      if(log.isDebugEnabled()){
        log.debug("Attempting to authenticate user {}", username);
      }

      ITIMCallbackHandler cbh = new ITIMCallbackHandler(username, password, request.getLocale());

      // Get Spring Bean Factory
      ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
      LoginContextManager loginContextManager = appContext.getBean("loginContextManager", LoginContextManager.class);
      LoginContext jaasLoginCtx = loginContextManager.getLoginContext(jaasConfigName, null, cbh);
      jaasLoginCtx.login();
      
      if(log.isDebugEnabled()){
        log.debug("Successfully authenticated user {}", username);
      }

      Subject loginSubject = jaasLoginCtx.getSubject();

      Set<Principal> principals = loginSubject.getPrincipals();
      principals.add(new UsernamePrincipal(username));

      Set<Object> publicCredentials = loginSubject.getPublicCredentials();

      Set<Object> privateCredentials = loginSubject.getPrivateCredentials();
      privateCredentials.add(new UsernamePasswordCredential(username, password));

      Subject userSubject = new Subject(false, principals, publicCredentials, privateCredentials);
      request.setAttribute(LoginHandler.SUBJECT_KEY, userSubject);
      request.setAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY, authenticationMethod);
    } catch (LoginException e) {
      if(log.isDebugEnabled()){
        log.debug("User authentication for " + username + " failed", e);
      }
      throw e;
    } catch (Throwable e) {
      if(log.isDebugEnabled()){
        log.debug("User authentication for " + username + " failed", e);
      }
      throw new LoginException("unknown authentication error");
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

}
