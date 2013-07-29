package com.sinopec.siam.am.idp.authn.module.tamldap;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.google.code.kaptcha.Constants;
import com.ibm.siam.am.idp.authn.AuthenticationEngine;
import com.sinopec.siam.am.idp.authn.LoginContextManager;
import com.sinopec.siam.am.idp.authn.module.AbstractSpringLoginModule;
import com.sinopec.siam.am.idp.authn.module.DetailLoginException;
import com.sinopec.siam.am.idp.authn.module.MissingPasswordHintLoginException;
import com.sinopec.siam.am.idp.authn.module.NeedChangePasswordLoginException;
import com.sinopec.siam.am.idp.authn.module.PasswordExpiredLoginException;
import com.sinopec.siam.am.idp.authn.module.PasswordReminderLoginException;
import com.sinopec.siam.am.idp.authn.module.UpdatePasswordHintLoginException;
import com.sinopec.siam.am.idp.authn.module.UserMobileBindingLoginException;
import com.sinopec.siam.am.idp.authn.provider.tamldap.TAMCallbackHandler;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;
import edu.internet2.middleware.shibboleth.idp.authn.provider.UsernamePasswordCredential;

/**
 * Servlet�� ����TAMLoginModule��ʾ��¼���沢���Ƶ�¼
 * 
 * @author zhangxianwen
 * @since 2012-6-25 ����3:34:29
 */

public class TAMLoginServlet extends HttpServlet {

  /**
   * Http Session��Attribute name, ���ڴ��LoginContext shareState��������HttpSession�㼶�������Ϣ.
   */
  private static final String LOGIN_MODULE_SHARED_STATE_IN_SESSION = "__LOGIN_MODULE_SHARED_STATE";

  private static final long serialVersionUID = -4980199746586492039L;

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(TAMLoginServlet.class);

  /** The authentication method returned to the authentication engine. */
  private String authenticationMethod = "urn:oasis:names:tc:SAML:2.0:ac:classes:TAMUsernamePassword";

  /** Name of JAAS configuration used to authenticate users. */
  private String jaasConfigName = "TamLdapUserPassAuth";

  /**
   * init-param which can be passed to the servlet to override the default JAAS
   * config.
   */
  private final String jaasInitParam = "jaasConfigName";

  /** Login page name. */
  private String loginPage = "/login/tam/loginform.do";

  /** Parameter name to indicate login failure. */
  private String failureParam = "loginFailed";

  /** {@inheritDoc} */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    if (getInitParameter(jaasInitParam) != null) {
      jaasConfigName = getInitParameter(jaasInitParam);
    }

    if (getInitParameter("loginPage") != null) {
      loginPage = getInitParameter("loginPage");
    }

    if (getInitParameter(failureParam) != null) {
      failureParam = getInitParameter(failureParam);
    }

    String method = config.getInitParameter(LoginHandler.AUTHENTICATION_METHOD_KEY);
    if (method != null) {
      authenticationMethod = method;
    }

  }

  /** {@inheritDoc} */
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("j_username");
    String password = request.getParameter("j_password");

    if (username == null) {
      // û���ύ�û���, ת���¼��ҳ��
      redirectToPage(request, response, loginPage);
      return;
    }

    username = username.trim();

    try {
      authenticateUser(request, response, username, password);
      //�ƶ���¼��������ʱʹ�õ�Sessionֵ
      request.getSession(false).removeAttribute(Constants.KAPTCHA_SESSION_KEY);
      request.getSession(false).removeAttribute(AbstractSpringLoginModule.PRINCIPAL_NAME_KEY);
      request.getSession(false).removeAttribute(AbstractSpringLoginModule.PRINCIPAL_DN_KEY);
      request.getSession(false).removeAttribute(AbstractSpringLoginModule.PRINCIPAL_PASSWORD_KEY);
      request.getSession(false).removeAttribute(LoginHandler.PRINCIPAL_UPDATE_PASSWORD_SUCCESS_KEY);
      
      AuthenticationEngine.returnToAuthenticationEngine(this.getServletContext(), request, response);
    } catch (PasswordReminderLoginException e) {
      // �����쳣��ת�Ƶ���������ڵ�����ҳ��
      // ͳһID
      request.setAttribute("j_username", e.getUsername());
      // ��¼ID
      request.setAttribute("show_username", username);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, e.getLoginErrorKey());
      request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, e.getLoginErrorArgs());
      request.getSession(false).setAttribute(LoginHandler.PRINCIPAL_UPDATE_PASSWORD_SUCCESS_KEY, "true");

      request.setAttribute("op", "remindpassword");
      request.setAttribute("actionUrl", buildServletUrl(request));
      redirectToPage(request, response, "/remind_password.do");
    } catch (UserMobileBindingLoginException e) {
        // �����쳣��ת�Ƶ��޸��ֻ��ŵ�����ҳ��
        // ͳһID
        request.setAttribute("j_username", e.getUsername());
        // ��¼ID
        request.setAttribute("show_username", username);
        request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, e.getLoginErrorKey());
        request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, e.getLoginErrorArgs());
        request.getSession(false).setAttribute(LoginHandler.PRINCIPAL_UPDATE_MOBILE_SUCCESS_KEY, "true");

        request.setAttribute("op", "modifymobile");
        request.setAttribute("actionUrl", buildServletUrl(request));
        redirectToPage(request, response, "/regmobile.do");
     } catch (MissingPasswordHintLoginException e) {
      request.setAttribute("j_username", e.getUsername());
      request.setAttribute("hintQuestionCandidate", e.getQuestionCandidateAttributes());
      request.setAttribute(LoginHandler.AUTHENTICATION_INFO_KEY, "passhint.form.info.hint.isNull");

      request.setAttribute("op", "setPasswordHint");
      request.setAttribute("actionUrl", buildServletUrl(request));
      redirectToPage(request, response, "/set_password_hint.do");
    } catch (UpdatePasswordHintLoginException e) {
      request.setAttribute("j_username", e.getUsername());
      request.setAttribute("hintQuestionCandidate", e.getQuestionCandidateAttributes());
      request.setAttribute(LoginHandler.AUTHENTICATION_INFO_KEY, e.getLoginErrorKey());

      request.setAttribute("op", "setPasswordHint");
      request.setAttribute("actionUrl", buildServletUrl(request));
      redirectToPage(request, response, "/set_password_hint.do");
    } catch (NeedChangePasswordLoginException e) {    
      request.setAttribute("j_username", e.getUsername());
      request.setAttribute("show_username", username);
      request.setAttribute(LoginHandler.AUTHENTICATION_INFO_KEY, "modifyPass.info.userpass.reset");

      request.setAttribute("op", "resetpasswordreset");
      request.setAttribute("actionUrl", buildServletUrl(request));
      redirectToPage(request, response, "/modify_password.do");
    } catch (PasswordExpiredLoginException e) {    
      request.setAttribute("j_username", e.getUsername());
      request.setAttribute("show_username", username);
      request.setAttribute(LoginHandler.AUTHENTICATION_INFO_KEY, "modifyPass.info.userpass.pastDue");

      request.setAttribute("op", "resetpassword");
      request.setAttribute("actionUrl", buildServletUrl(request));
      redirectToPage(request, response, "/modify_password.do");
    } catch (DetailLoginException e) {    
      request.setAttribute("j_username", username);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, e.getLoginErrorKey());
      request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, e.getLoginErrorArgs());
      request.setAttribute(failureParam, "true");
      redirectToPage(request, response, loginPage);
    } catch (LoginException e) {
      request.setAttribute("j_username", username);
      request.setAttribute(failureParam, "true");
      log.error(String.format("Authntication error : %s", e.getMessage()));
      redirectToPage(request, response, loginPage);
    }
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
   * Sends the user to the page.
   * 
   * @param request
   *          current request
   * @param response
   *          current response
   * @param redirectUrl
   *          redirectUrl
   */
  protected void redirectToPage(HttpServletRequest request, HttpServletResponse response, String redirectUrl) {
    try {
      request.getRequestDispatcher(redirectUrl).forward(request, response);
      log.debug("Redirecting to page {}", redirectUrl);
    } catch (IOException e) {
      log.error("Unable to redirect to page.", e);
    } catch (ServletException e) {
      log.error("Unable to redirect to page.", e);
    }
  }

  /**
   * Authenticate a username and password against JAAS. If authentication
   * succeeds the name of the first principal, or the username if that is empty,
   * and the subject are placed into the request in their respective attributes.
   * 
   * @param request
   *          current authentication request
   * @param response
   *          current authentication response
   * @param username
   *          the principal name of the user to be authenticated
   * @param password
   *          the password of the user to be authenticated
   * @throws LoginException
   *           thrown if there is a problem authenticating the user
   */
  protected void authenticateUser(HttpServletRequest request, HttpServletResponse response, String username, String password) throws LoginException {
    log.debug("Attempting to authenticate user [{}]", username);
    log.debug("Attempting to authenticate session [{}], code [{}]",request.getSession(false), request.getSession(false).getAttribute(Constants.KAPTCHA_SESSION_KEY));

    TAMCallbackHandler cbh = new TAMCallbackHandler(username, password, request);

    // Get Spring Bean Factory
    ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
    LoginContextManager loginContextManager = appContext.getBean("loginContextManager", LoginContextManager.class);
    // ����һ��LoginContext��shareState���󣬲���HttpSession�лָ���Ҫ�������Ϣ
    Map<?, ?> shareState = getLoginModuleShareState(request);
    LoginContext jaasLoginCtx = loginContextManager.getLoginContext(jaasConfigName, null, cbh, shareState);
    try {
      jaasLoginCtx.login();
      log.debug("Successfully authenticated user {}", username);

      Subject loginSubject = jaasLoginCtx.getSubject();
      Set<Principal> principals = loginSubject.getPrincipals();

      Set<Object> publicCredentials = loginSubject.getPublicCredentials();

      Set<Object> privateCredentials = loginSubject.getPrivateCredentials();
      privateCredentials.add(new UsernamePasswordCredential(username, password));

      Subject userSubject = new Subject(false, principals, publicCredentials, privateCredentials);
      request.setAttribute(LoginHandler.SUBJECT_KEY, userSubject);
      request.setAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY, authenticationMethod);
      request.getSession(true).removeAttribute(Constants.KAPTCHA_SESSION_KEY);
      // �����HttpSession�б����LoginContext session���Ĺ���״̬
      request.getSession(true).removeAttribute(LOGIN_MODULE_SHARED_STATE_IN_SESSION);
    } catch (LoginException e) {
      // LoginContext�������, �����shareState�Ƿ�����Ҫ��HttpSession�㼶�����״̬, ��������µ�HttpSession��
      updateShareState(request, shareState);
      throw e;
    }
  }

  /**
   * @param request
   * @param shareState
   */
  private void updateShareState(HttpServletRequest request, Map<?, ?> shareState) {
    Map<?, ?> sessionState = AbstractSpringLoginModule.getAllSessionLevelState(shareState);
    request.getSession(true).setAttribute(LOGIN_MODULE_SHARED_STATE_IN_SESSION, sessionState);
  }

  /**
   * @param request
   * @return
   */
  private Map<?, ?> getLoginModuleShareState(HttpServletRequest request) {
    Map<?, ?> shareState =  new HashMap<String, Object>();
    Map<?, ?> sessionState = (Map<?, ?>) request.getSession(true).getAttribute(LOGIN_MODULE_SHARED_STATE_IN_SESSION);
    if (sessionState != null && !sessionState.isEmpty()) {
      AbstractSpringLoginModule.setAllSessionLevelState(shareState, sessionState);
    }
    return shareState;
  }
}
