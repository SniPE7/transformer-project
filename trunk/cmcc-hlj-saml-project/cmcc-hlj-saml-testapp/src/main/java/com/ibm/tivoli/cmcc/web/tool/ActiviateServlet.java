package com.ibm.tivoli.cmcc.web.tool;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.ibm.tivoli.cmcc.client.ActiviateServiceClient;
import com.ibm.tivoli.cmcc.client.ActiviateServiceClientImpl;
import com.ibm.tivoli.cmcc.connector.NetworkConnectorManager;

public class ActiviateServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 6887846462398365105L;

  /**
   * Constructor of the object.
   */
  public ActiviateServlet() {
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
      String samlId = request.getParameter("id");
      if (StringUtils.isEmpty(samlId)) {
        throw new RuntimeException("Missing ID!");
      }
      String hostname = request.getParameter("hostname");
      String port = request.getParameter("port");
      String protocol = request.getParameter("protocol");

      Properties properties = ConfigHelper.getConfigProperties();
      NetworkConnectorManager connectionManager = ConfigHelper.getNetworkConnectorManager(properties);
      ActiviateServiceClient client = new ActiviateServiceClientImpl(properties);
      connectionManager.setProtocol(protocol);
      if (StringUtils.isNotEmpty(hostname)) {
        connectionManager.setServerName(hostname);
      }
      
      if (StringUtils.isNotEmpty(port)) {
        connectionManager.setServerPort(Integer.parseInt(port));
      }
      
      String responseXML = client.submit(connectionManager.getConnector(), samlId);
      
      responseXML = StringUtils.replace(responseXML, "<", "&lt;");
      responseXML = StringUtils.replace(responseXML, ">", "&gt;");
      request.setAttribute("responseXML", responseXML);
      this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/test-tool/view_message.jsp").forward(request, response);
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
