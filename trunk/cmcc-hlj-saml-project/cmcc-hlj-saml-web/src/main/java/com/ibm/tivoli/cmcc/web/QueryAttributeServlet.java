package com.ibm.tivoli.cmcc.web;

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
import com.ibm.tivoli.cmcc.client.QueryAttributeServiceClient;

public class QueryAttributeServlet extends HttpServlet {

  /**
   * Constructor of the object.
   */
  public QueryAttributeServlet() {
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
      
      ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
      QueryAttributeServiceClient client = (QueryAttributeServiceClient)context.getBean("queryAttributeClient");;
      if (StringUtils.isNotEmpty(hostname)) {
        client.setServerName(hostname);
      }
      
      if (StringUtils.isNotEmpty(port)) {
        client.setServerPort(Integer.parseInt(port));
      }      

      String responseXML = client.submit(samlId);
      
      responseXML = StringUtils.replace(responseXML, "<", "&lt;");
      responseXML = StringUtils.replace(responseXML, ">", "&gt;");
      request.setAttribute("responseXML", responseXML);
      this.getServletConfig().getServletContext().getRequestDispatcher("/view_message.jsp").forward(request, response);
      //response.setContentType("text/xml;charset=UTF-8");
      //PrintWriter writer = response.getWriter();
      //writer.write(responseXML);
      //writer.flush();
    } catch (BeansException e) {
      throw new IOException(e.getMessage(), e);
    } catch (ClientException e) {
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
