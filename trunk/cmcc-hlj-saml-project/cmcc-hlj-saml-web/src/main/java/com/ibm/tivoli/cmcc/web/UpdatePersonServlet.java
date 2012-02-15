package com.ibm.tivoli.cmcc.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.tivoli.cmcc.dir.LDAPPersonDAO;

/**
 * 1. 接收来自应用的SAML认证请求
 * 
 * @author zhaodonglu
 * 
 */
public class UpdatePersonServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = -7812159369032202041L;

  private String login_page_style = "cmcc_embed";

  /**
   * Constructor of the object.
   */
  public UpdatePersonServlet() {
    super();
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
      String msisdn = request.getParameter("msisdn");
      ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
      LDAPPersonDAO personDao = context.getBean("personDao", LDAPPersonDAO.class);
      personDao.update(msisdn, "displayName", request.getParameter("displayName"));
      personDao.update(msisdn, "cn", request.getParameter("cn"));
      personDao.update(msisdn, "erhljmccstatus", request.getParameter("erhljmccstatus"));
      personDao.update(msisdn, "erhljmccFetionStatus", request.getParameter("erhljmccFetionStatus"));
      personDao.update(msisdn, "erhljmcc139MailStatus", request.getParameter("erhljmcc139MailStatus"));
      personDao.update(msisdn, "erhljmccuserlevel", request.getParameter("erhljmccuserlevel"));
      personDao.update(msisdn, "erhljmcccurrentpoint", request.getParameter("erhljmcccurrentpoint"));
      personDao.update(msisdn, "erhljmccAuthThreshold", request.getParameter("erhljmcccurrentpoint"));
      personDao.update(msisdn, "erhljmccAuthTimes", request.getParameter("erhljmccAuthTimes"));
      
      response.sendRedirect(request.getContextPath() + "/service/authen/mypage");
      return;
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
