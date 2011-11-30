package com.ibm.tivoli.cmcc.web;

import java.io.IOException;
import java.security.Principal;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.spi.LoginModule;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
    try {
      String username = request.getParameter("User-Name");
      if (StringUtils.isEmpty(username)) {
        throw new RuntimeException("Missing username!");
      }

      String password = request.getParameter("User-Password");
      if (StringUtils.isEmpty(password)) {
        throw new RuntimeException("Missing password!");
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
        try {
          AuthenRequest authenReq = service.parseRequest(request);
          String relayState = service.getRelayState(request);
          if (authenReq != null && StringUtils.isNotEmpty(relayState)) {
            // Return application
            this.getServletConfig().getServletContext().getRequestDispatcher("/service/authen/response").forward(request, response);
            return;
          }
        } catch (Exception e) {
        }
        // Show mypage for authenitcate user
        this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/authen/mypage.jsp").forward(request, response);
        return;
      }
      // Login failure
      this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/authen/login_form.jsp").forward(request, response);

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
