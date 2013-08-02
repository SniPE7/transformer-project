package com.sinopec.siam.am.idp.authn.provider.cert;

import java.io.IOException;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
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

import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.ibm.siam.am.idp.authn.AuthenticationEngine;
import com.sinopec.siam.am.idp.authn.LoginContextManager;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;

/**
 * @description 客户端客户登录
 * @author zhangxianwen
 * @version 1.0
 * @Copyright (c) 2012
 * @create 2012-3-13 上午10:11:13
 */

/**
 * @author Booker
 * 
 */
public class ClientCertLoginServlet extends HttpServlet {

  /** Serial version UID. */
  private static final long serialVersionUID = -5968630085747203824L;

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(ClientCertLoginServlet.class);

  /** The authentication method returned to the authentication engine. */
  private String authenticationMethod;

  /** Name of JAAS configuration used to authenticate users. */
  private String jaasConfigName = "ClientCertAuth";

  /**
   * init-param which can be passed to the servlet to override the default JAAS
   * config.
   */
  private final String jaasInitParam = "jaasConfigName";

  /** Login page name. */
  private String loginPage = "login.jsp";

  /** rootCertSubjectDN */
  private String rootCertSubjectDN = "CN=CA, O=SINOPEC, C=CN";

  /**
   * init-param which can be passed to the servlet to override the default login
   * page.
   */
  private final String loginPageInitParam = "loginPage";

  /** Parameter name to indicate login failure. */
  private final String failureParam = "loginFailed";

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

    if (getInitParameter("rootCertSubjectDN") != null) {
      rootCertSubjectDN = getInitParameter("rootCertSubjectDN");
    }

    String method = DatatypeHelper
        .safeTrimOrNullString(config.getInitParameter(LoginHandler.AUTHENTICATION_METHOD_KEY));
    if (method != null) {
      authenticationMethod = method;
    } else {
      authenticationMethod = AuthnContext.TLS_CLIENT_AUTHN_CTX;
    }

  }

  /** {@inheritDoc} */
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
    if (certs == null || certs.length < 1) {
      request.setAttribute(failureParam, "true");
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "login.form.error.cert.isNull");
      redirectToLoginPage(request, response);
      return;
    }
    log.debug(String.format("client cert length:%s", certs.length));

    try {
      List<X509Certificate> clientCertChain = new ArrayList<X509Certificate>();
      for (X509Certificate cert : certs) {
        /* 如果是根证书则跳过 */
        if (rootCertSubjectDN.equals(cert.getSubjectDN().toString())) {
          continue;
        }
        clientCertChain.add(cert);
      }

      authenticateCert(request, clientCertChain.get(0));
      AuthenticationEngine.returnToAuthenticationEngine(this.getServletContext(), request, response);
    } catch (LoginException e) {
      request.setAttribute(failureParam, "true");
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
  /**
   * @description 认证证书
   * @param request
   *          HttpServletRequest请求
   * @param cert
   *          认证证书对象
   * @throws LoginException
   *           thrown if there is a problem authenticating the cert
   */
  protected void authenticateCert(HttpServletRequest request, X509Certificate cert) throws LoginException {
    log.debug(String.format("authenticate cert:%s", cert.getSubjectDN()));
    Subject subject = new Subject();
    ClientCertCallbackHandler cbh = new ClientCertCallbackHandler(cert, request);
    // Get Spring Bean Factory
    ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
    LoginContextManager loginContextManager = appContext.getBean("loginContextManager", LoginContextManager.class);
    LoginContext jaasLoginCtx = loginContextManager.getLoginContext(jaasConfigName, subject, cbh);
    jaasLoginCtx.login();

    // 创建 Subject key
    Set<Principal> principals = subject.getPrincipals();

    Set<Object> publicCredentials = subject.getPublicCredentials();
    publicCredentials.add(cert);

    Set<Object> privateCredentials = subject.getPrivateCredentials();

    Subject userSubject = new Subject(false, principals, publicCredentials, privateCredentials);

    request.setAttribute(LoginHandler.SUBJECT_KEY, userSubject);
    request.setAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY, authenticationMethod);

  }

}
