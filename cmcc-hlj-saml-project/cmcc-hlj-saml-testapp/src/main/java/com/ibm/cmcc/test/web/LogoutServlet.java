package com.ibm.cmcc.test.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public LogoutServlet() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String artifactID = request.getParameter("artifactid");
    if (!StringUtils.isEmpty(artifactID)) {
       // TODO 取消临时应用端接收Logout事件的逻辑, 重新编写
      Map<String, HttpSession> artifactMap = HttpSessionListenerImpl.getArtifactMap();
      HttpSession session = artifactMap.get(artifactID);
      if (session != null) {
         session.invalidate();
      }
    } else {
      HttpSession session = request.getSession(false);
      if (session != null) {
        session.invalidate();
      }
    }
    this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/logout.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.doGet(request, response);
  }

}
