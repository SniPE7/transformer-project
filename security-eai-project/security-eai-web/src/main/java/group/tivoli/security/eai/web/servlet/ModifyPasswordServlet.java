package group.tivoli.security.eai.web.servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.ibm.itim.ws.exceptions.WSApplicationException;
import com.ibm.itim.ws.exceptions.WSInvalidPasswordException;
import com.ibm.itim.ws.exceptions.WSPasswordRuleException;
import com.sinopec.siam.am.idp.authn.module.AbstractSpringLoginModule;
import com.sinopec.siam.am.idp.authn.service.UserPassService;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;
import group.tivoli.security.eai.web.sso.SSOPrincipal;

/**
 * 修改口令Servlet
 * 
 * @author zhangxianwen
 * @since 2012-9-6 下午6:10:23
 */

public class ModifyPasswordServlet extends HttpServlet {

  /** Serial version UID. */
  private static final long serialVersionUID = 8571614178637592368L;

  /** Parameter name to indicate login failure. */
  private final String failureParam = "loginFailed";

  /** Authentication page name. */
  private String authnPage = "/Authn/TAMUserPassAuth";
  
  private String authServlet = "/Authn/TAMUserPassAuth";

  /** HTTP request parameter containing the user name. */
  private String usernameAttribute = "j_username";

  /** HTTP request parameter containing the show user name. */
  private String showUsernameAttribute = "show_username";
  
  /** HTTP request parameter containing the user's password. */
  private String passwordAttribute = "j_password";

  /** HTTP request parameter containing the user's new password. */
  private String newPassAttribute = "j_new_password";

  /** HTTP request parameter containing the opt. */
  private String optFlagAttribute = "op";

  /** 修改密码操作的标识 */
  private String modifyPasswordFlag = "modifypassword";
  
  /**
   * Http Session的Attribute name, 用于存放LoginContext shareState中用于在HttpSession层级共享的信息.
   */
  private static final String LOGIN_MODULE_SHARED_STATE_IN_SESSION = "__LOGIN_MODULE_SHARED_STATE";

  /** Modify page name. */
  private String modifyPasswordPage = "/modify_password.do";

  /** 用户口令服务Bean ID */
  private String userPassServiceBeanId = "timUserPassService";

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(ModifyPasswordServlet.class);

  /** {@inheritDoc} */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    if (getInitParameter("authnPage") != null) {
      authnPage = getInitParameter("authnPage");
    }
    if (getInitParameter("authServlet") != null) {
      authServlet = getInitParameter("authServlet");
    }
    if (getInitParameter("usernameAttribute") != null) {
      usernameAttribute = getInitParameter("usernameAttribute");
    }
    
    if (getInitParameter("showUsernameAttribute") != null) {
      showUsernameAttribute = getInitParameter("showUsernameAttribute");
    }
    
    if (getInitParameter("passwordAttribute") != null) {
      passwordAttribute = getInitParameter("passwordAttribute");
    }

    if (getInitParameter("newPassAttribute") != null) {
      newPassAttribute = getInitParameter("newPassAttribute");
    }

    if (getInitParameter("optFlagAttribute") != null) {
      optFlagAttribute = getInitParameter("optFlagAttribute");
    }

    if (getInitParameter("modifyPasswordFlag") != null) {
      modifyPasswordFlag = getInitParameter("modifyPasswordFlag");
    }

    if (getInitParameter("modifyPasswordPage") != null) {
      modifyPasswordPage = getInitParameter("modifyPasswordPage");
    }

    if (getInitParameter("userPassServiceBeanId") != null) {
      userPassServiceBeanId = getInitParameter("userPassServiceBeanId");
    }

  }

  /** {@inheritDoc} */
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter(usernameAttribute);
    String showUsername = request.getParameter(showUsernameAttribute);
    String password = request.getParameter(passwordAttribute);
    Map<?, ?> sessionState = (Map<?, ?>)request.getSession(false).getAttribute(LOGIN_MODULE_SHARED_STATE_IN_SESSION);
    String sessionPassword = new String((char[]) sessionState.get(AbstractSpringLoginModule.PRINCIPAL_PASSWORD_KEY));
    String nPassword = request.getParameter(newPassAttribute);

    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(showUsername)) {
      request.setAttribute(failureParam, "true");
      request.setAttribute(usernameAttribute, username);
      request.setAttribute(showUsernameAttribute, showUsername);
      request.setAttribute(optFlagAttribute, modifyPasswordFlag);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.username.isNull");
      redirectToPage(request, response, modifyPasswordPage);
      return;
    }

    if (StringUtils.isEmpty(sessionPassword)) {
      request.setAttribute(failureParam, "true");
      request.setAttribute(usernameAttribute, username);
      request.setAttribute(showUsernameAttribute, showUsername);
      request.setAttribute(optFlagAttribute, modifyPasswordFlag);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.sessionpass.isNull");
      redirectToPage(request, response, modifyPasswordPage);
      return;
    }

    if (StringUtils.isEmpty(password)) {
      request.setAttribute(failureParam, "true");
      request.setAttribute(usernameAttribute, username);
      request.setAttribute(showUsernameAttribute, showUsername);
      request.setAttribute(optFlagAttribute, modifyPasswordFlag);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.userpass.isNull");
      redirectToPage(request, response, modifyPasswordPage);
      return;
    }

    if (StringUtils.isEmpty(nPassword)) {
      request.setAttribute(failureParam, "true");
      request.setAttribute(usernameAttribute, username);
      request.setAttribute(showUsernameAttribute, showUsername);
      request.setAttribute(optFlagAttribute, modifyPasswordFlag);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.usernewpass.isNull");
      redirectToPage(request, response, modifyPasswordPage);
      return;
    }

    if (!password.equals(sessionPassword)) {
      request.setAttribute(failureParam, "true");
      request.setAttribute(usernameAttribute, username);
      request.setAttribute(showUsernameAttribute, showUsername);
      request.setAttribute(optFlagAttribute, modifyPasswordFlag);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.userpass.isError");
      redirectToPage(request, response, modifyPasswordPage);
      return;
    }

    boolean modifyPassword = modifyPassword(request, username, password, nPassword);
    if (modifyPassword) {
      request.getSession(true).setAttribute(LoginHandler.PRINCIPAL_UPDATE_PASSWORD_SUCCESS_KEY, "true");
      
      //request.getSession(true).removeAttribute(LoginHandler.SUBJECT_KEY);
      //request.setAttribute(SSOPrincipal.URL_REQUEST_TARGET_TAG, request.getContextPath() + authnPage);
      //AuthenticationEngine.getInstance().returnToAuthenticationEngine(this.getServletContext(), request, response);
      
      request.setAttribute(SSOPrincipal.URL_REQUEST_TARGET_TAG, request.getContextPath() + authnPage);
      
      //httpResponse.sendRedirect(httpRequest.getContextPath() + this.AUTH_PAGE);
      request.getRequestDispatcher(authServlet).forward(request, response);
      
      //response.sendRedirect(request.getContextPath() + authnPage);
      //redirectToPage(request, response, authnPage);
      return;
    }

    request.setAttribute(failureParam, "true");
    request.setAttribute(usernameAttribute, username);
    request.setAttribute(showUsernameAttribute, showUsername);
    request.setAttribute(optFlagAttribute, modifyPasswordFlag);
    redirectToPage(request, response, modifyPasswordPage);
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
      if(log.isDebugEnabled()){
        log.debug("Redirecting to page {}", redirectUrl);
      }
    } catch (IOException e) {
      log.error("Unable to redirect to page.", e);
    } catch (ServletException e) {
      log.error("Unable to redirect to page.", e);
    }
  }

  private boolean modifyPassword(HttpServletRequest request, String username, String password, String nPassword) {

    UserPassService userPassService = getUserPassService();

    // 修改TIM口令（API）
    try {
      userPassService.updatePassword(username, nPassword);
    } catch (MalformedURLException e) {
      log.error(String.format("Create ITIMWebServiceFactory Exception. username:%s [error: %s]", username, e.toString()), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
      return false;
    } catch (ServiceException e) {
      log.error(String.format("Get WSItimService Exception. username:%s [error: %s]", username, e.toString()), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
      return false;
    } catch (WSInvalidPasswordException e) {
      log.error(String.format("Invalid Password Exception. username:%s [error: %s]", username, e.toString()), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.userpass.rule");
      return false;
    } catch (WSApplicationException e) {
      log.error(String.format("WSApplication Exception. username:%s [error: %s]", username, e.toString()), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
      return false;
    } catch (WSPasswordRuleException e) {
      log.error(String.format("Password Rule Exception. username:%s [error: %s]", username, e.toString()), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.userpass.rule");
      return false;
    } catch (RemoteException e) {
      log.error(String.format("Remote Exception. username:%s [error: %s]", username, e.toString()), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
      return false;
    }
    
    return true;
  }

  /**
   * 获取用户密码服务Bean
   * 
   * @return UserPassService
   */
  private UserPassService getUserPassService() {
    // Get Spring Bean Factory
    ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
    return appContext.getBean(this.userPassServiceBeanId, UserPassService.class);
  }

}