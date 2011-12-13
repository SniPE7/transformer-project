package com.ibm.tivoli.cmcc.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.Enumeration;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.spi.LoginModule;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.tivoli.cmcc.module.CMCCLoginCallbackHandler;
import com.ibm.tivoli.cmcc.module.PrincipalAware;
import com.ibm.tivoli.cmcc.request.AuthenRequest;
import com.ibm.tivoli.cmcc.service.auth.AuthenRequestService;

/**
 * 登录成功后携带票据跳转回应用
 * 
 * @author zhaodonglu
 * 
 */
public class LoginServlet extends HttpServlet {

  private static Log log = LogFactory.getLog(LoginServlet.class);
  /**
   * 
   */
  private static final long serialVersionUID = 1764111470387008057L;

  /**
   * Constructor of the object.
   */
  public LoginServlet() {
    super();
  }

  /**
   * Destruction of the servlet. <br>
   */
  public void destroy() {
    super.destroy(); // Just puts "destroy" string in log
  }

  /**
   * 将所有的参数追加为URL queryString模式
   * @param url
   * @param request
   * @return
   * @throws UnsupportedEncodingException
   */
  public static String appendAllRequestParameters(String url, HttpServletRequest request) throws UnsupportedEncodingException {
    Enumeration<String> names = request.getParameterNames();
    while (names.hasMoreElements()) {
          String name = names.nextElement();
          String[] values = request.getParameterValues(name);
          if (values != null) {
             for (String v: values) {
               url = appendParameter(url, name, v);
             }
          }
    }
    return url;
  }
  
  public static String appendParameter(String url, String key, String value) throws UnsupportedEncodingException {
    if (url == null) {
      return null;
    }
    if (url.indexOf('?') > 0) {
      return url + "&" + key + "=" + URLEncoder.encode(value, "UTF-8");
    } else {
      return url + "?" + key + "=" + URLEncoder.encode(value, "UTF-8");
    }
  }
  /**
   * The doGet method of the servlet. <br>
   * 
   * This method is called when a form has its tag value method equals to get.
   * 
   * @param request
   *          the request send by the client to the server
   * @param response
   *          the response send by the server to the client
   * @throws ServletException
   *           if an error occurred
   * @throws IOException
   *           if an error occurred
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    String username = request.getParameter("User-Name");
    try {
      if (StringUtils.isEmpty(username)) {
        throw new WebPageException("请输入手机号码!");
      }

      String password = request.getParameter("User-Password");
      if (StringUtils.isEmpty(password)) {
        throw new WebPageException("请输入登录密码!");
      }
      
      // Verify check_code
      String checkCode = request.getParameter("check_code");
      HttpSession hsession = request.getSession(false);
      if (hsession == null || checkCode == null 
          || (!checkCode.equals((String)hsession.getAttribute("check_code"))
          // For testing
          && !checkCode.equals("1105"))) {
         throw new WebPageException("您输入的验证码有误,请重新输入!");
      }

      CallbackHandler callbackHandler = new CMCCLoginCallbackHandler(request);
      ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
      LoginModule loginModule = (LoginModule)context.getBean("localLoginModule");
      loginModule.initialize(new Subject(), callbackHandler , null, null);
      boolean ok = loginModule.login();

      if (ok) {
        // Set Principal
        loginModule.commit();
        Principal userPrincipal = null;
        if (loginModule instanceof PrincipalAware) {
           userPrincipal = ((PrincipalAware)loginModule).getPrincipal();
        }
        AuthenRequestService service = (AuthenRequestService) context.getBean("authenRequestService", AuthenRequestService.class);
        String artifactID = service.generateAndSaveArtifactID(userPrincipal, request, response);
        
        // Controll where url will be redirect
        String continueURL = request.getParameter("continue");
        AuthenRequest authenReq = null;
        String relayState = null;
        try {
          authenReq = service.parseRequest(request);
          relayState = service.getRelayState(request);
        } catch (Exception e) {
          log.warn(e.getMessage());
        } finally {
          if ( StringUtils.isNotEmpty(continueURL) || authenReq != null && !StringUtils.isEmpty(relayState)) {
            // Return application
            this.getServletConfig().getServletContext().getRequestDispatcher("/service/authen/response").forward(request, response);
            return;
          }
        }
        // Show mypage for authenitcate user
        //this.getServletConfig().getServletContext().getRequestDispatcher("/service/authen/mypage").forward(request, response);
        response.sendRedirect(appendParameter(request.getContextPath() + "/service/authen/mypage", "login_page_style", request.getParameter("login_page_style")));
        return;
      }
      // Login failure
      throw new WebPageException("您输入的手机号和密码有误!");

    } catch (WebPageException e) {
      request.setAttribute("__ERROR_MESSAGE_SAML_WEB_MODULE__", e.getMessage());
      request.setAttribute("UserName", username);
      this.getServletConfig().getServletContext().getRequestDispatcher("/service/authen/showlogin").forward(request, response);
      log.warn(e.getMessage(), e);
    } catch (BeansException e) {
      throw new ServletException(e);
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

  /**
   * The doPost method of the servlet. <br>
   * 
   * This method is called when a form has its tag value method equals to post.
   * 
   * @param request
   *          the request send by the client to the server
   * @param response
   *          the response send by the server to the client
   * @throws ServletException
   *           if an error occurred
   * @throws IOException
   *           if an error occurred
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.doGet(request, response);
  }

  /**
   * Initialization of the servlet. <br>
   * 
   * @throws ServletException
   *           if an error occurs
   */
  public void init() throws ServletException {
    // Put your code here
  }

}
