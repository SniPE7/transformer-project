package com.ibm.tivoli.cmcc.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

  /**
   * 
   */
  private static final long serialVersionUID = -7812159369032202041L;

  /**
   * Constructor of the object.
   */
  public AuthenRequestServlet() {
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
   * @param request the request send by the client to the server
   * @param response the response send by the server to the client
   * @throws ServletException if an error occurred
   * @throws IOException if an error occurred
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    try {
      ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
      AuthenRequestService service = (AuthenRequestService) context.getBean("authenRequestService", AuthenRequestService.class);
      service.validate(request);
      boolean authenticated = service.isAuthenticated(request);
      if (!authenticated) {
         this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/authen/login_form.jsp").forward(request, response);
      } else {
        this.getServletConfig().getServletContext().getRequestDispatcher("/service/authen/response").forward(request, response);
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
