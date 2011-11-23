package com.ibm.tivoli.cmcc.web.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.tivoli.cmcc.client.ActiviateServiceClient;
import com.ibm.tivoli.cmcc.client.LogoutServiceClient;
import com.ibm.tivoli.cmcc.client.QueryAttributeServiceClient;

public class HomeServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = -7549002691920817472L;

  /**
   * Constructor of the object.
   */
  public HomeServlet() {
    super();
  }

  /**
   * Destruction of the servlet. <br>
   */
  public void destroy() {
    super.destroy(); // Just puts "destroy" string in log
    // Put your code here
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
      ActiviateServiceClient activiateClient = (ActiviateServiceClient)context.getBean("activiateClient");;
      LogoutServiceClient logoutClient = (LogoutServiceClient)context.getBean("logoutClient");;
      QueryAttributeServiceClient queryAttributeClient = (QueryAttributeServiceClient)context.getBean("queryAttributeClient");;

      request.setAttribute("activiateClient", activiateClient);
      request.setAttribute("logoutClient", logoutClient);
      request.setAttribute("queryAttributeClient", queryAttributeClient);
      this.getServletConfig().getServletContext().getRequestDispatcher("/form.jsp").forward(request, response);
    } catch (BeansException e) {
      throw new IOException(e.getMessage(), e);
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
