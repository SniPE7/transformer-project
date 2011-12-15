package com.ibm.cmcc.appplugin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.cmcc.test.web.Person;
import com.ibm.tivoli.cmcc.client.ActiviateServiceClient;
import com.ibm.tivoli.cmcc.client.ActiviateServiceClientImpl;
import com.ibm.tivoli.cmcc.client.ArtifactResolvServiceClient;
import com.ibm.tivoli.cmcc.client.ArtifactResolvServiceClientImpl;
import com.ibm.tivoli.cmcc.client.ClientException;
import com.ibm.tivoli.cmcc.connector.ConnectorManager;
import com.ibm.tivoli.cmcc.connector.NetworkConnectorManager;
import com.ibm.tivoli.cmcc.connector.SimpleNetworkConnectorImpl;
import com.ibm.tivoli.cmcc.response.ArtifactResolvResponse;
import com.ibm.tivoli.cmcc.util.Base64;
import com.ibm.tivoli.cmcc.util.Helper;

/**
 * 应用端认证和报活功能的Filter
 * @author zhaodonglu
 *
 */
public class SAMLApplicationFilter implements Filter {
  private static Log log = LogFactory.getLog(SAMLApplicationFilter.class);

  private static final long serialVersionUID = 1L;

  private String ssoSAMLAuthRequestURL;
  private String ssoLoginBoxURL;
  private String keyStorePath;
  private char[] keyPassword;
  private String trustStorePath;
  private char[] trustStorePassword;
  private char[] keyStorePassword;
  private String samlServer;
  private String samlPort;
  private String samlProtocol;

  private FilterConfig filterConfig;

  /**
   * 发送报活信息的间隔时间
   */
  private int activiateInterval = 180;

  /**
   * Default constructor.
   */
  public SAMLApplicationFilter() {
  }

  /**
   * @see Filter#init(FilterConfig)
   */
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
    this.ssoSAMLAuthRequestURL = filterConfig.getInitParameter("SsoSAMLAuthRequestURL");
    this.ssoLoginBoxURL = filterConfig.getInitParameter("SsoLoginBoxURL");
    this.samlServer = filterConfig.getInitParameter("SAML.server.hostname");
    this.samlPort = filterConfig.getInitParameter("SAML.server.port");
    this.samlProtocol = filterConfig.getInitParameter("SAML.server.protocol");
    this.keyStorePath = filterConfig.getInitParameter("SAML.client.key.store.path");
    this.keyStorePassword = filterConfig.getInitParameter("SAML.client.key.store.store.password").toCharArray();
    this.keyPassword = filterConfig.getInitParameter("SAML.client.key.store.key.password").toCharArray();
    this.trustStorePath = filterConfig.getInitParameter("SAML.client.trust.store.path");
    this.trustStorePassword = filterConfig.getInitParameter("SAML.client.trust.store.password").toCharArray();
    
    this.activiateInterval = Integer.parseInt(filterConfig.getInitParameter("SAML.activiate.interval.seconds"));
  }

  /**
   * @see Filter#destroy()
   */
  public void destroy() {
  }

  /**
   * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
   */
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;

    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("USER_UID") != null && session.getAttribute("SESSION_PERSON") != null) {
      // Authenticated and do filter
      String artifact = (String) session.getAttribute("ARTIFACT_ID");
      try {
        // Send activate to IDP
        ConnectorManager conMgr = getConnectorManager();
        Date lastActiviateTime = (Date) session.getAttribute("SAML_ACTIVIATE_TIME");
        if (lastActiviateTime == null || System.currentTimeMillis() - lastActiviateTime.getTime() > this.activiateInterval  * 1000) {
          ActiviateServiceClient client = new ActiviateServiceClientImpl(new Properties());
          String respXML = client.submit(conMgr.getConnector(), artifact);
          session.setAttribute("SAML_ACTIVIATE_TIME", new Date());
        }
      } catch (Throwable t) {
        log.error(String.format("Failure to send activiate request, artifact:[%s], cause: %s", artifact, t.getMessage()), t);
      } finally {
        chain.doFilter(req, resp);
      }
      return;
    }

    // 未登录
    try {

      String artifact = request.getParameter("SAMLart");
      String relayState = request.getParameter("RelayState");

      if (StringUtils.isEmpty(artifact) && StringUtils.isEmpty(relayState)) {
        // 没有收到Artifact, 且没有replayState, 发送SAMLRequest, 转移到相关页面
        sendSAMLRequest(request, response);
        return;
      }

      if (StringUtils.isNotEmpty(artifact)) {
        // 收到ArtifactID, 发送ArtifactResolv
        try {
          Person personDTO = receiveSAMLResponseAndResolvArtifact(artifact);
          String msisdn = personDTO.getMsisdn();
          if (msisdn != null && msisdn.trim().length() > 0) {
            // 登录成功
            session = request.getSession(true);
            session.setAttribute("USER_UID", msisdn);
            session.setAttribute("SESSION_PERSON", personDTO);
            session.setAttribute("ARTIFACT_ID", artifact);
            session.setAttribute("SAML_ACTIVIATE_TIME", new Date());
            // Show login succes page
            chain.doFilter(req, resp);
            return;
          }
        } catch (ClientException e) {
          throw new IOException(String.format("Failure to resolv artifact: [%s], cause: %s", artifact, e.getMessage()), e);
        }
      }
      redirect2SSOLoginBox(request, response);
      return;
    } catch (Throwable t) {
      log.error(t.getMessage(), t);
    }
  }

  /**
   * @param artifact
   * @return
   * @throws ClientException
   */
  private Person receiveSAMLResponseAndResolvArtifact(String artifact) throws ClientException {
    Person personDTO = null;
    ConnectorManager conMgr = getConnectorManager();
    ArtifactResolvServiceClient client = new ArtifactResolvServiceClientImpl(new Properties());
    ArtifactResolvResponse samlResp = client.submitAndParse(conMgr.getConnector(), artifact);
    if (samlResp != null && "urn:oasis:names:tc:SAML:2.0:status:Success".equals(samlResp.getStatusCode())) {
      // Already login
      personDTO = new Person();
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
    }
    return personDTO;
  }

  /**
   * @return
   */
  private ConnectorManager getConnectorManager() {
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
    return conMgr;
  }

  /**
   * @param request
   * @param response
   * @throws IOException
   * @throws UnsupportedEncodingException
   */
  private void redirect2SSOLoginBox(HttpServletRequest request, HttpServletResponse response) throws IOException, UnsupportedEncodingException {
    // Redirect to SSO loginbox url
    String ssoLoginReturnURL = getCurrentRequestURL(request);
    response.sendRedirect(appendParameter(caculateURL(request, ssoLoginBoxURL), "continue", caculateURL(request, ssoLoginReturnURL)));
    return;
  }

  /**
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  private void sendSAMLRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Unauthenticate and send SAMLRequest
    String ssoLoginReturnURL = getCurrentRequestURL(request);
    // Generate RelayState
    String relayState = IDGenerator.generateID(64);
    // Get return url
    // Generate SAMLRequest XML
    String xml = "<samlp:AuthnRequest\n" + "    xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"\n"
        + "    xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\"\n" + "    ID=\"id_1\"\n" + "    Version=\"2.0\"\n" + "    IssueInstant=\""
        + Helper.formatDate4SAML(new Date()) + "\">\n" + "    <saml:Issuer>" + caculateURL(request, ssoLoginReturnURL) + "</saml:Issuer>\n"
        + "    <samlp:NameIDPolicy\n" + "        AllowCreate=\"true\"\n" + "        Format=\"urn:oasis:names:tc:SAML:2.0:nameid-format:transient\"/>\n"
        + "</samlp:AuthnRequest>\n";
    request.setAttribute("RelayState", relayState);
    request.setAttribute("SAMLRequest", new String(Base64.encode(xml.getBytes())));
    request.setAttribute("ssoSAMLAuthRequestURL", caculateURL(request, this.ssoSAMLAuthRequestURL));
    this.filterConfig.getServletContext().getRequestDispatcher("/WEB-INF/jsp/query_auth_state.jsp").forward(request, response);
    return;
  }

  /**
   * @param request
   * @return
   */
  private String getCurrentRequestURL(HttpServletRequest request) {
    String url = String.format("%s://%s:%s%s", request.getScheme(), request.getServerName(), request.getServerPort(), request.getRequestURI());
    return url;
  }

  /**
   * 将所有的参数追加为URL queryString模式
   * @param url
   * @param request
   * @return
   * @throws UnsupportedEncodingException
   */
  public static String appendAllRequestParameters(String url, HttpServletRequest request) throws UnsupportedEncodingException {
    Enumeration<String> names = request.getParameterNames();
    while (names.hasMoreElements()) {
          String name = names.nextElement();
          String[] values = request.getParameterValues(name);
          if (values != null) {
             for (String v: values) {
               url = appendParameter(url, name, v);
             }
          }
    }
    return url;
  }
  
  public static String appendParameter(String url, String key, String value) throws UnsupportedEncodingException {
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
  public static String getServerURL(HttpServletRequest request) {
    String serverURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    return serverURL;
  }

  /**
   * @param request
   * @param url
   * @return
   */
  public static String caculateURL(HttpServletRequest request, String url) {
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

}
