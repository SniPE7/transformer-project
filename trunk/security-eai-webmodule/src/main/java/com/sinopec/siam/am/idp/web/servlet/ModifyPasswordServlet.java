package com.sinopec.siam.am.idp.web.servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;
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
import com.sinopec.siam.am.idp.authn.service.MultiplePersonFoundException;
import com.sinopec.siam.am.idp.authn.service.PersonNotFoundException;
import com.sinopec.siam.am.idp.authn.service.PersonService;
import com.sinopec.siam.am.idp.authn.service.PersonServiceException;
import com.sinopec.siam.am.idp.authn.service.UserService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;

/**
 * �޸Ŀ���Servlet
 * 
 * @author zhangxianwen
 * @since 2012-9-6 ����6:10:23
 */

public class ModifyPasswordServlet extends HttpServlet {

  /** Serial version UID. */
  private static final long serialVersionUID = 8571614178637592368L;

  /**
   * Http Session��Attribute name, ���ڴ��LoginContext shareState��������HttpSession�㼶�������Ϣ.
   */
  private static final String LOGIN_MODULE_SHARED_STATE_IN_SESSION = "__LOGIN_MODULE_SHARED_STATE";
  
  /** Parameter name to indicate login failure. */
  private final String failureParam = "loginFailed";

  /** Authentication page name. */
  private String authnPage = "/Authn/TAMUserPassAuth";

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

  /** �޸���������ı�ʶ */
  private String modifyPasswordFlag = "modifypassword";

  /** Modify page name. */
  private String modifyPasswordPage = "/modify_password.do";

  /** �û�����Bean ID */
  private String personServiceBeanId = "personService";

  /**
   * tamLdapUserService Bean ID
   */
  private String tamLdapUserServiceBeanId = "tamLdapUserService";

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(ModifyPasswordServlet.class);

  /** {@inheritDoc} */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    if (getInitParameter("authnPage") != null) {
      authnPage = getInitParameter("authnPage");
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

    if (getInitParameter("personServiceBeanId") != null) {
    	personServiceBeanId = getInitParameter("personServiceBeanId");
    }

    if (getInitParameter("tamLdapUserServiceBeanId") != null) {
      tamLdapUserServiceBeanId = getInitParameter("tamLdapUserServiceBeanId");
  }

  }

  /** {@inheritDoc} */
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String op = request.getParameter("op");
    if ("changePwdByUser".equals(op)) {
       // �û���������Ŀ����޸�
       String username = request.getParameter(usernameAttribute);
       String showUsername = request.getParameter(showUsernameAttribute);
       String password = request.getParameter(passwordAttribute);
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
       
       // Check old password
       UserService tamService = this.getTamLdapUserService();
       List<LdapUserEntity> users = tamService.searchByUid(username);
       if (users == null || users.size() != 1) {
         request.setAttribute(failureParam, "true");
         request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
         redirectToPage(request, response, modifyPasswordPage);
         return;
       }
       boolean oldPasswordSuccess = tamService.authenticateByUserDnPassword(users.get(0).getDn(), password);
       if (!oldPasswordSuccess) {
          // ԭʼ���������֤ʧ��
          request.setAttribute(failureParam, "true");
          request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "user.modify.password.wrongOldPassword");
          redirectToPage(request, response, modifyPasswordPage);
          return;
       }
       boolean modifyPassword = modifyPassword(request, username, nPassword);
       request.setAttribute(usernameAttribute, username);
       request.setAttribute(showUsernameAttribute, showUsername);
       request.setAttribute(optFlagAttribute, op);
       if (modifyPassword) {
         // �޸Ŀ���ɹ�
         request.getSession(false).setAttribute(LoginHandler.PRINCIPAL_UPDATE_PASSWORD_SUCCESS_KEY, "true");
         request.setAttribute(LoginHandler.AUTHENTICATION_INFO_KEY, "user.modify.password.success");
         redirectToPage(request, response, modifyPasswordPage);
         return;
       } else {
         // �޸Ŀ���ʧ��
         request.setAttribute(failureParam, "true");
         redirectToPage(request, response, modifyPasswordPage);
         return;
       }
    }
    
    // ��¼���̷�����ǿ���û��޸Ŀ���ȳ���.
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

    if (!password.equals(sessionPassword) && !"javax.security.auth.login.password".equals(sessionPassword)) {
      request.setAttribute(failureParam, "true");
      request.setAttribute(usernameAttribute, username);
      request.setAttribute(showUsernameAttribute, showUsername);
      request.setAttribute(optFlagAttribute, modifyPasswordFlag);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.userpass.isError");
      redirectToPage(request, response, modifyPasswordPage);
      return;
    }

    boolean modifyPassword = modifyPassword(request, username, nPassword);
    if (modifyPassword) {
      request.getSession(false).setAttribute(LoginHandler.PRINCIPAL_UPDATE_PASSWORD_SUCCESS_KEY, "true");
      //redirectToPage(request, response, authnPage);
      Object authnUrl = request.getSession(false).getAttribute("eai-authn-url");
      if(authnUrl!=null && !"".equals(authnUrl)) {
          String url = authnUrl.toString();
          redirectToPage(request, response, url.substring(url.indexOf("/", 1)));
      } else {
          redirectToPage(request, response, authnPage);
      }
      
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

  private boolean modifyPassword(HttpServletRequest request, String username, String nPassword) {

    PersonService personService = getPersonService();

    // �޸�TIM���API��
    try {
    	personService.updatePassword(username, nPassword);
    	/*
    } catch (MalformedURLException e) {
      log.error(String.format("Create ITIMWebServiceFactory Exception. username:%s", username), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
      return false;
    } catch (ServiceException e) {
      log.error(String.format("Get WSItimService Exception. username:%s", username), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
      return false;
    } catch (WSInvalidPasswordException e) {
      log.error(String.format("Invalid Password Exception. username:%s", username), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.userpass.rule");
      return false;
    } catch (WSApplicationException e) {
      log.error(String.format("WSApplication Exception. username:%s", username), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
      return false;
    } catch (WSPasswordRuleException e) {
      log.error(String.format("Password Rule Exception. username:%s", username), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.userpass.rule");
      return false;
    } catch (RemoteException e) {
      log.error(String.format("Remote Exception. username:%s", username), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
      return false;
    */
    } catch (PersonNotFoundException e) {
      log.error(String.format("Remote Exception. username:%s", username), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
      return false;
    } catch (MultiplePersonFoundException e) {
      log.error(String.format("Remote Exception. username:%s", username), e);
      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
      return false;
    } catch (PersonServiceException e) {
	    Throwable cause = e.getCause();
	    if (cause == null) {
	      log.error(String.format("Remote Exception. username:%s", username), e);
	      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
	      return false;
	    } else {
	    if (cause instanceof MalformedURLException) {
	      log.error(String.format("Create ITIMWebServiceFactory Exception. username:%s", username), e);
	      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
	      return false;
	    } else if (cause instanceof ServiceException ) {
	      log.error(String.format("Get WSItimService Exception. username:%s", username), e);
	      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
	      return false;
	    } else if (cause instanceof WSInvalidPasswordException ) {
	      log.error(String.format("Invalid Password Exception. username:%s", username), e);
	      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.userpass.rule");
	      return false;
	    } else if (cause instanceof WSApplicationException) {
	      log.error(String.format("WSApplication Exception. username:%s", username), e);
	      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
	      return false;
	    } else if (cause instanceof WSPasswordRuleException) {
	      log.error(String.format("Password Rule Exception. username:%s", username), e);
	      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.userpass.rule");
	      return false;
	    } else if (cause instanceof RemoteException ) {
	      log.error(String.format("Remote Exception. username:%s", username), e);
	      request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "modifyPass.error.failure");
	      return false;
	    }
	    }
    }
    return true;
  }

  private PersonService getPersonService() {
    // Get Spring Bean Factory
    ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
    return appContext.getBean(this.personServiceBeanId, PersonService.class);
  }

  private UserService getTamLdapUserService() {
    // Get Spring Bean Factory
    ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
    return appContext.getBean(this.tamLdapUserServiceBeanId , UserService.class);
  }
}
