package com.ibm.tivoli.cmcc.web.tool;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.tivoli.cmcc.client.ClientException;
import com.ibm.tivoli.cmcc.client.PasswordResetClient;
import com.ibm.tivoli.cmcc.connector.NetworkConnectorManager;

public class PasswordResetServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 162966896737175375L;

  /**
   * Constructor of the object.
   */
  public PasswordResetServlet() {
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
      String userName = request.getParameter("userName");
      if (StringUtils.isEmpty(userName)) {
        throw new RuntimeException("Missing userName!");
      }
      
      String serviceCode = request.getParameter("serviceCode");
      if (StringUtils.isEmpty(serviceCode)) {
        throw new RuntimeException("Missing serviceCode!");
      }
      
      String networkPassword = request.getParameter("networkPassword");
      if (StringUtils.isEmpty(networkPassword)) {
        throw new RuntimeException("Missing networkPassword!");
      }
      
      String hostname = request.getParameter("hostname");
      String port = request.getParameter("port");
      String protocol = request.getParameter("protocol");

      ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
      NetworkConnectorManager connectionManager = (NetworkConnectorManager)context.getBean("connectorManager4Test");;
      PasswordResetClient client = (PasswordResetClient)context.getBean("passwordResetClient4Test");
      connectionManager.setProtocol(protocol);

      if (StringUtils.isNotEmpty(hostname)) {
        connectionManager.setServerName(hostname);
      }
      
      if (StringUtils.isNotEmpty(port)) {
        connectionManager.setServerPort(Integer.parseInt(port));
      }
      
      String responseXML = client.submit(connectionManager.getConnector(), userName, serviceCode, networkPassword);
      
      responseXML = StringUtils.replace(responseXML, "<", "&lt;");
      responseXML = StringUtils.replace(responseXML, ">", "&gt;");
      request.setAttribute("responseXML", responseXML);
      this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/test/view_message.jsp").forward(request, response);
      //response.setContentType("text/xml;charset=UTF-8");
      //PrintWriter writer = response.getWriter();
      //writer.write(responseXML);
      //writer.flush();
    } catch (BeansException e) {
      throw new ServletException(e);
    } catch (ClientException e) {
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
