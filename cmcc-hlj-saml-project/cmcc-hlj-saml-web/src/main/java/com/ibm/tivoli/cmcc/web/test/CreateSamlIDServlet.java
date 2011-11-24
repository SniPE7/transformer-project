package com.ibm.tivoli.cmcc.web.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.tivoli.cmcc.client.ClientException;
import com.ibm.tivoli.cmcc.ldap.PersonDAO;
import com.ibm.tivoli.cmcc.server.utils.Helper;

public class CreateSamlIDServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 6581467109008767380L;

  /**
   * Constructor of the object.
   */
  public CreateSamlIDServlet() {
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
      String msisdn = request.getParameter("msisdn");
      if (StringUtils.isEmpty(msisdn)) {
        throw new RuntimeException("Missing MSISDN!");
      }

      ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
      PersonDAO dao = (PersonDAO)context.getBean("ldapDao");
      
      String uniqueIdentifier =  Helper.generatorID();
      uniqueIdentifier = dao.insertUniqueIdentifier(msisdn, uniqueIdentifier );
      if (uniqueIdentifier == null) {
         throw new IOException("failure to create or update ldap entry.");
      }
      
      request.setAttribute("msisdn", msisdn);
      request.setAttribute("uniqueIdentifier", uniqueIdentifier);
      
      Cookie cookie = new Cookie("cmtokenid", uniqueIdentifier + "@" + "ac.10086.cn");
      cookie.setDomain("ac.10086.cn");
      cookie.setPath("/");
      response.addCookie(cookie);

      this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/test/view_message.jsp").forward(request, response);
      
    } catch (BeansException e) {
      throw new ServletException(e);
    } catch (ClientException e) {
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
