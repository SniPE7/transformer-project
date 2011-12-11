package com.ibm.cmcc.test.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.cmcc.test.util.Base64;
import com.ibm.cmcc.test.util.IDGenerator;
import com.ibm.tivoli.cmcc.client.ArtifactResolvServiceClient;
import com.ibm.tivoli.cmcc.client.ArtifactResolvServiceClientImpl;
import com.ibm.tivoli.cmcc.client.ClientException;
import com.ibm.tivoli.cmcc.connector.NetworkConnectorManager;
import com.ibm.tivoli.cmcc.connector.SimpleNetworkConnectorImpl;
import com.ibm.tivoli.cmcc.response.ArtifactResolvResponse;
import com.ibm.tivoli.cmcc.server.utils.Helper;

/**
 * Servlet implementation class WelcomeServlet
 */
public class WelcomeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private String ssoSAMLAuthRequestURL;
  private String ssoLoginBoxURL;
  private String ssoLoginReturnURL;
  private String keyStorePath;
  private char[] keyPassword;
  private String trustStorePath;
  private char[] trustStorePassword;
  private char[] keyStorePassword;
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
    this.ssoLoginReturnURL = config.getInitParameter("SsoLoginReturnURL");
    this.samlServer = config.getInitParameter("SAML.server.hostname");
    this.samlPort = config.getInitParameter("SAML.server.port");
    this.samlProtocol = config.getInitParameter("SAML.server.protocol");
    this.keyStorePath =       config.getInitParameter("SAML.client.key.store.path");
    this.keyStorePassword =   config.getInitParameter("SAML.client.key.store.store.password").toCharArray();
    this.keyPassword =        config.getInitParameter("SAML.client.key.store.key.password").toCharArray();
    this.trustStorePath =     config.getInitParameter("SAML.client.trust.store.path");
    this.trustStorePassword = config.getInitParameter("SAML.client.trust.store.password").toCharArray();
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
    if (session != null && session.getAttribute("USER_UID") != null) {
      this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/mypage_cmcc_embed.jsp").forward(request, response);
      return;
    } else {
      String artifact = request.getParameter("SAMLart");
      String relayState = request.getParameter("RelayState");
      if (artifact == null || artifact.trim().length() == 0) {
        if (relayState == null) {
          // Unauthenticate and send SAMLRequest
          // Generate RelayState
          relayState = IDGenerator.generateID(64);
          // Get return url
          // Generate SAMLRequest XML
          String xml = "<samlp:AuthnRequest\n" + "    xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"\n"
              + "    xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\"\n" + "    ID=\"id_1\"\n" + "    Version=\"2.0\"\n" + "    IssueInstant=\""
              + Helper.formatDate4SAML(new Date()) + "\">\n" + "    <saml:Issuer>" + caculateURL(request, ssoLoginReturnURL) + "</saml:Issuer>\n" + "    <samlp:NameIDPolicy\n"
              + "        AllowCreate=\"true\"\n" + "        Format=\"urn:oasis:names:tc:SAML:2.0:nameid-format:transient\"/>\n" + "</samlp:AuthnRequest>\n";
          request.setAttribute("RelayState", relayState);
          request.setAttribute("SAMLRequest", new String(Base64.encode(xml.getBytes())));
          request.setAttribute("ssoSAMLAuthRequestURL", caculateURL(request, this.ssoSAMLAuthRequestURL));
          this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/query_auth_state.jsp").forward(request, response);
          return;
        } else {
          // Redirect to SSO loginbox url
          response.sendRedirect(appendParameter(caculateURL(request, ssoLoginBoxURL), "continue", caculateURL(request, ssoLoginReturnURL)));
          return;
        }
      } else {
        NetworkConnectorManager conMgr = new NetworkConnectorManager();
        conMgr.setConnectorClassName(SimpleNetworkConnectorImpl.class.getName());
        conMgr.setProtocol(this.samlProtocol);
        conMgr.setServerName(this.samlServer);
        conMgr.setServerPort(Integer.parseInt(this.samlPort));
        conMgr.setKeyStorePath(keyStorePath);
        conMgr.setKeyStoreKeyPassword(keyPassword);
        conMgr.setKeyStorePassword(keyStorePassword);
        conMgr.setTrustCertsStorePath(this.trustStorePath);
        conMgr.setKeyStorePassword(this.trustStorePassword);

        ArtifactResolvServiceClient client = new ArtifactResolvServiceClientImpl(conMgr, new Properties());
        try {
          ArtifactResolvResponse samlResp = client.submitAndParse(artifact);
          if (samlResp != null && "urn:oasis:names:tc:SAML:2.0:status:Success".equals(samlResp.getStatusCode())) {
            // Already login
            String msisdn = samlResp.getAttributeByIndex(0);
            session = request.getSession(true);
            session.setAttribute("USER_UID", msisdn);
            Person personDTO = new Person();
            personDTO.setBrand(samlResp.getAttributeByIndex(3));
            personDTO.setCommonName(samlResp.getAttributeByIndex(2));
            personDTO.setCurrentPoint(samlResp.getAttributeByIndex(5));
            personDTO.setFetionStatus(samlResp.getAttributeByIndex(8));
            personDTO.setLastName(samlResp.getAttributeByIndex(2));
            personDTO.setMail139Status(samlResp.getAttributeByIndex(7));
            personDTO.setMsisdn(samlResp.getAttributeByIndex(0));
            personDTO.setNickname(samlResp.getAttributeByIndex(6));
            personDTO.setProvince(samlResp.getAttributeByIndex(1));
            personDTO.setStatus(samlResp.getAttributeByIndex(4));
            personDTO.setUserLevel(samlResp.getAttributeByIndex(9));
            
            session.setAttribute("SESSION_PERSON", personDTO);
            session.setAttribute("ARTIFACT_ID", artifact);
            // Show login succes page
            this.getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/mypage_cmcc_embed.jsp").forward(request, response);
            return;
          }
        } catch (ClientException e) {
          throw new IOException(e.getMessage(), e);
        }
      }
      // Redirect to SSO loginbox url
      response.sendRedirect(appendParameter(caculateURL(request, ssoLoginBoxURL), "continue", caculateURL(request, ssoLoginReturnURL)));
      return;
    }
  }
  
  private static String appendParameter(String url, String key, String value) throws UnsupportedEncodingException {
    if (url == null) {
       return null;
    }
    if (url.indexOf('?') > 0) {
       return url + "&" + key + "=" + URLEncoder.encode(value, "UTF-8");
    } else {
      return url + "?" + key + "=" + URLEncoder.encode(value, "UTF-8");
    }
  }

  /**
   * @param request
   * @return
   */
  private static String getServerURL(HttpServletRequest request) {
    String serverURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    return serverURL;
  }
  
  /**
   * @param request
   * @param url
   * @return
   */
  private static String caculateURL(HttpServletRequest request, String url) {
    if (url != null && url.toLowerCase().startsWith("http")) {
       return url;
    }
    if (url != null && url.toLowerCase().startsWith("/")) {
       return getServerURL(request) + url;
    }
    if (url != null) {
      return getServerURL(request) + request.getContextPath() + "/" + url;
    }
    return null;
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.doGet(request, response);
  }

}
