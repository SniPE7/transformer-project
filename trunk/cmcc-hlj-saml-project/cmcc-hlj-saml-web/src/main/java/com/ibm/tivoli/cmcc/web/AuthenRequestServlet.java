package com.ibm.tivoli.cmcc.web;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.tivoli.cmcc.service.auth.AuthenRequestService;

/**
 * 1. 接收来自应用的SAML认证请求
 * 
 * @author zhaodonglu
 *
 */
public class AuthenRequestServlet extends HttpServlet {
  
  private static Log log = LogFactory.getLog(AuthenRequestServlet.class);

  /**
   * 
   */
  private static final long serialVersionUID = -7812159369032202041L;
  
  private String login_page_style = "cmcc_1";
  //private String login_page_style = "simple";

  private boolean challengeLoginByApp = true;

  /**
   * Constructor of the object.
   */
  public AuthenRequestServlet() {
    super();
  }

  /**
   * @see Servlet#init(ServletConfig)
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    String t = config.getInitParameter("ChallengeLoginByApp");
    if (t != null) {
       this.challengeLoginByApp = Boolean.parseBoolean(t);
    }
  }
  
  /**
   * Destruction of the servlet. <br>
   */
  public void destroy() {
    super.destroy(); 
  }

  /**
   * The doGet method of the servlet. <br>
   *
   * This method is called when a form has its tag value method equals to get.
   * 
   * @param request the request send by the client to the server
   * @param response the response send by the server to the client
   * @throws ServletException if an error occurred
   * @throws IOException if an error occurred
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    try {
      // Tracking RelayState
      String relayState = request.getParameter("");
      if (relayState != null) {
         HttpSession session = request.getSession(true);
         session.setAttribute("RELAY_STATE", relayState);
      }
      ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
      AuthenRequestService service = (AuthenRequestService) context.getBean("authenRequestService", AuthenRequestService.class);
      service.validate(request);
      boolean authenticated = service.isAuthenticated(request, response);
      if (!authenticated) {
         boolean isChallengeLoginByApp = (request.getParameter("ChallengeLoginByApp") != null)?Boolean.parseBoolean(request.getParameter("ChallengeLoginByApp")):this.challengeLoginByApp;
         if (!isChallengeLoginByApp) {
            // Redirect to login box
            log.debug("unauthenticate, redirect to loginbox.");
            this.getServletConfig().getServletContext().getRequestDispatcher("/service/authen/showlogin").forward(request, response);
         } else {
           // Redirect to SAML response for SAMLRequest
           this.getServletConfig().getServletContext().getRequestDispatcher("/service/authen/response").forward(request, response);
         }
         return;
      } else {
        this.getServletConfig().getServletContext().getRequestDispatcher("/service/authen/response").forward(request, response);
        return;
      }
    } catch (RuntimeException e) {
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
   * @param request the request send by the client to the server
   * @param response the response send by the server to the client
   * @throws ServletException if an error occurred
   * @throws IOException if an error occurred
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.doGet(request, response);
  }
  
  /**
   * Initialization of the servlet. <br>
   *
   * @throws ServletException if an error occurs
   */
  public void init() throws ServletException {
    // Put your code here
  }

}
