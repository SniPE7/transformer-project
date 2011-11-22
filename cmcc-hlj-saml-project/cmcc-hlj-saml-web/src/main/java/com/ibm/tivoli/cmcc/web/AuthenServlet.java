package com.ibm.tivoli.cmcc.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.tivoli.cmcc.ldap.PersonDAO;
import com.ibm.tivoli.cmcc.request.AuthenRequest;
import com.ibm.tivoli.cmcc.server.utils.Helper;

/**
 * 1. 接收来自应用的SAML认证请求
 * 2. 提取登录状态或展示登录界面，供用户登录
 * 3. 登录成功后携带票据跳转回应用
 * 
 * @author zhaodonglu
 *
 */
public class AuthenServlet extends HttpServlet {

  /**
   * Constructor of the object.
   */
  public AuthenServlet() {
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
      String samlRequestB64 = request.getParameter("SAMLRequest");
      String username = request.getParameter("username");
      
      if (!StringUtils.isEmpty(samlRequestB64)) {
        doSAMLRequest(request, response);
      } else if (!StringUtils.isEmpty(username)) {
        doLogin(request, response);
      } else {
        this.getServletConfig().getServletContext().getRequestDispatcher("/authen/unkonw_oper.jsp").forward(request, response);
      }
    } catch (BeansException e) {
      throw new ServletException(e);
    } catch (Exception e) {
      throw new ServletException(e);
    }
    
  }

  private void saveUniqueIdIntoSession(HttpServletResponse response, String uniqueIdentifier) {
    Cookie cookie = new Cookie("cmtokenid", uniqueIdentifier + "@" + "ac.10086.cn");
    cookie.setDomain("ac.10086.cn");
    cookie.setPath("/");
    response.addCookie(cookie);
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
  
  public void doSAMLRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String samlRequestB64 = request.getParameter("SAMLRequest");
    if (StringUtils.isEmpty(samlRequestB64)) {
      throw new RuntimeException("Missing SAMLRequest!");
    }
    String relayState = request.getParameter("RelayState");
    if (StringUtils.isEmpty(relayState)) {
      throw new RuntimeException("Missing RelayState!");
    }

    // Parsing SAMLRequest
    AuthenRequest authnReq = null;
    
    // Track SAML req and relayState
    HttpSession session = request.getSession(true);
    session.setAttribute("SAMLAuthenRequest", authnReq);
    session.setAttribute("RelayState", relayState);
    this.getServletConfig().getServletContext().getRequestDispatcher("/authen/login_form.jsp").forward(request, response);
  }

  /**
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  public void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String username = request.getParameter("username");
      if (StringUtils.isEmpty(username)) {
        throw new RuntimeException("Missing username!");
      }
      
      String password = request.getParameter("password");
      if (StringUtils.isEmpty(username)) {
        throw new RuntimeException("Missing password!");
      }
      
      // Checking username and password
      
      // Update state to session management service
      ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
      PersonDAO dao = (PersonDAO)context.getBean("ldapDao");
      
      String uniqueIdentifier =  Helper.generatorID();
      uniqueIdentifier = dao.insertUniqueIdentifier("", username, uniqueIdentifier );
      if (uniqueIdentifier == null) {
         throw new IOException("failure to create or update ldap entry.");
      }
      
      // Update to Cookies
      this.saveUniqueIdIntoSession(response, uniqueIdentifier);
      
      request.setAttribute("artifact", uniqueIdentifier);
      this.getServletConfig().getServletContext().getRequestDispatcher("/authen/rtn_app_form.jsp").forward(request, response);
      
    } catch (BeansException e) {
      throw new ServletException(e);
    } catch (Exception e) {
      throw new ServletException(e);
    }
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
