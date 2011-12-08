package com.ibm.cmcc.test.web;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.cmcc.test.util.IDGenerator;
import com.ibm.tivoli.cmcc.client.ArtifactResolvServiceClient;
import com.ibm.tivoli.cmcc.client.ArtifactResolvServiceClientImpl;
import com.ibm.tivoli.cmcc.client.ClientException;
import com.ibm.tivoli.cmcc.connector.NetworkConnectorManager;
import com.ibm.tivoli.cmcc.connector.SimpleNetworkConnectorImpl;
import com.ibm.tivoli.cmcc.response.ArtifactResolvResponse;
import com.ibm.tivoli.cmcc.server.utils.Helper;
import com.ibm.xml.enc.dom.Base64;

/**
 * Servlet implementation class WelcomeServlet
 */
public class WelcomeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private String ssoSAMLAuthRequestURL;
  private String ssoLoginBoxURL;
  private String keyStorePath;
  private char[] keyPassword;
  private char[] storePassword;
  private String samlServer;
  private String samlPort;
  private String samlProtocol;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public WelcomeServlet() {
    super();
  }

  /**
   * @see Servlet#init(ServletConfig)
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.ssoSAMLAuthRequestURL = config.getInitParameter("SsoSAMLAuthRequestURL");
    this.ssoLoginBoxURL = config.getInitParameter("SsoLoginBoxURL");
    this.samlServer = config.getInitParameter("SAML.server.hostname");
    this.samlPort = config.getInitParameter("SAML.server.port");
    this.samlProtocol = config.getInitParameter("SAML.server.protocol");
    this.keyStorePath = config.getInitParameter("SAML.client.key.store.path");
    this.keyPassword = config.getInitParameter("SAML.client.key.store.key.password").toCharArray();
    this.storePassword = config.getInitParameter("SAML.client.key.store.store.password").toCharArray();
  }

  /**
   * @see Servlet#destroy()
   */
  public void destroy() {
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("USER_UID") == null) {
      String artifact = request.getParameter("SAMLart");
      String relayState = request.getParameter("RelayState");
      if (artifact == null || artifact.trim().length() == 0) {
        if (relayState == null) {
          // Unauthenticate and send SAMLRequest
          // Generate RelayState
          relayState = IDGenerator.generateID(64);
          // Get return url
          String returnURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/welcome";
          // Generate SAMLRequest XML
          String xml = "<samlp:AuthnRequest\n" + "    xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"\n"
              + "    xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\"\n" + "    ID=\"id_1\"\n" + "    Version=\"2.0\"\n" + "    IssueInstant=\""
              + Helper.formatDate4SAML(new Date()) + "\">\n" + "    <saml:Issuer>" + returnURL + "</saml:Issuer>\n" + "    <samlp:NameIDPolicy\n"
              + "        AllowCreate=\"true\"\n" + "        Format=\"urn:oasis:names:tc:SAML:2.0:nameid-format:transient\"/>\n" + "</samlp:AuthnRequest>\n";
          request.setAttribute("RelayState", relayState);
          request.setAttribute("SAMLRequest", Base64.encode(xml.getBytes()));
          request.setAttribute("ssoSAMLAuthRequestURL", this.ssoSAMLAuthRequestURL);
          this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/query_auth_state.jsp").forward(request, response);
          return;
        } else {
          // Redirect to SSO loginbox url
          response.sendRedirect(ssoLoginBoxURL);
          return;
        }
      } else {
        NetworkConnectorManager conMgr = new NetworkConnectorManager();
        conMgr.setConnectorClassName(SimpleNetworkConnectorImpl.class.getName());
        conMgr.setProtocol(this.samlProtocol);
        conMgr.setServerName(this.samlServer);
        conMgr.setServerPort(Integer.parseInt(this.samlPort));
        conMgr.setKeyStorePath(keyStorePath);
        conMgr.setKeyPassword(keyPassword);
        conMgr.setStorePassword(storePassword);
        ArtifactResolvServiceClient client = new ArtifactResolvServiceClientImpl(conMgr, new Properties());
        try {
          ArtifactResolvResponse samlResp = client.submitAndParse(artifact);
          if (samlResp != null && "urn:oasis:names:tc:SAML:2.0:status:Success".equals(samlResp.getStatusCode())) {
            // Already login
            String msisdn = samlResp.getAttributeByIndex(0);
            session = request.getSession(true);
            session.setAttribute("USER_UID", msisdn);
            // Show login succes page
            this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/mypage.jsp").forward(request, response);
            return;
          }
        } catch (ClientException e) {
          throw new IOException(e.getMessage(), e);
        }
      }
      // Redirect to SSO loginbox url
      response.sendRedirect(ssoLoginBoxURL);
      return;
    } else {
      this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/mypage.jsp").forward(request, response);
      return;
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.doGet(request, response);
  }

}
