/**
 * 
 */
package com.ibm.tivoli.cmcc.service.auth;

import java.io.IOException;
import java.io.StringReader;
import java.security.Principal;
import java.util.regex.Pattern;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.spi.LoginModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.module.CMCCLoginCallbackHandler;
import com.ibm.tivoli.cmcc.module.PrincipalAware;
import com.ibm.tivoli.cmcc.request.AuthenRequest;
import com.ibm.tivoli.cmcc.server.utils.CookieHelper;
import com.ibm.tivoli.cmcc.session.Session;
import com.ibm.tivoli.cmcc.session.SessionManagementException;
import com.ibm.tivoli.cmcc.session.SessionManager;
import com.ibm.tivoli.cmcc.spi.PersonDTO;
import com.ibm.tivoli.cmcc.util.Base64;

/**
 * @author zhaodonglu
 * 
 */
public class AuthenRequestServiceImpl implements ApplicationContextAware, AuthenRequestService {

  private static Log log = LogFactory.getLog(AuthenRequestServiceImpl.class);

  private String defaultCookieDomain = "ac.10086.cn";
  private ApplicationContext applicationContext;

  private String defaultArtifactDomain = "hl1.ac.10086.cn";

  /**
   * 
   */
  public AuthenRequestServiceImpl() {
    super();
  }

  /**
   * @return the defaultCookieDomain
   */
  public String getDefaultCookieDomain() {
    return defaultCookieDomain;
  }

  /**
   * @param defaultCookieDomain
   *          the defaultCookieDomain to set
   */
  public void setDefaultCookieDomain(String cookieDomain) {
    this.defaultCookieDomain = cookieDomain;
  }

  /**
   * @return the defaultArtifactDomain
   */
  public String getDefaultArtifactDomain() {
    return defaultArtifactDomain;
  }

  /**
   * @param defaultArtifactDomain
   *          the defaultArtifactDomain to set
   */
  public void setDefaultArtifactDomain(String defaultArtifactDomain) {
    this.defaultArtifactDomain = defaultArtifactDomain;
  }

  /**
   * @return the applicationContext
   */
  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  private Digester getDigester() {
    Digester digester = new Digester();
    digester.setNamespaceAware(false);
    digester.setValidating(false);

    digester.addSetProperties("*/samlp:AuthnRequest", "ID", "samlId");
    digester.addSetProperties("*/samlp:AuthnRequest", "IssueInstant", "issueInstant");
    digester.addBeanPropertySetter("*/samlp:AuthnRequest/saml:Issuer", "samlIssuer");
    digester.addSetProperties("*/samlp:AuthnRequest/samlp:NameIDPolicy", "AllowCreate", "allowCreate");
    return digester;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.web.AuthenRequestService#validate(javax.servlet.http
   * .HttpServletRequest)
   */
  public void validate(HttpServletRequest request) throws Exception {
    String relayState = request.getParameter("RelayState");
    if (StringUtils.isEmpty(relayState)) {
      throw new RuntimeException("Missing RelayState!");
    }
    this.parseRequest(request);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.web.AuthenRequestService#parseRequest(javax.servlet
   * .http.HttpServletRequest)
   */
  public AuthenRequest parseRequest(HttpServletRequest request) throws IOException, SAXException {
    String samlRequestB64 = request.getParameter("SAMLRequest");
    String samlContent = request.getParameter("SAMLRequestXML");

    if (StringUtils.isEmpty(samlRequestB64) && StringUtils.isEmpty(samlContent)) {
      throw new RuntimeException("Missing SAMLRequest!");
    }
    // Parsing SAMLRequest
    if (StringUtils.isNotEmpty(samlRequestB64)) {
      samlContent = new String(Base64.decode(samlRequestB64));
    }

    AuthenRequest result = new AuthenRequest();
    Digester digester = getDigester();
    digester.push(result);
    try {
      if (!samlContent.trim().toLowerCase().startsWith("<?xml")) {
        samlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + samlContent;
      }
      digester.parse(new StringReader(samlContent));
      return result;
    } catch (IOException e) {
      throw e;
    } catch (SAXException e) {
      throw e;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.web.AuthenRequestService#getRelayState(javax.servlet
   * .http.HttpServletRequest)
   */
  public String getRelayState(HttpServletRequest request) {
    return request.getParameter("RelayState");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.web.AuthenRequestService#isAuthenticated(javax.servlet
   * .http.HttpServletRequest)
   */
  public boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) {
    log.debug("Checking SAML login state.");
    // 检查是否已经在本地登录
    HttpSession hsession = request.getSession(false);
    if (hsession != null) {
      String artifactID = (String) hsession.getAttribute("ARTIFACT_ID");
      if (StringUtils.isNotEmpty(artifactID)) {
        log.debug(String.format("Local login session is validate, artifactID: {%s].", artifactID));
        return true;
      }
    }
    log.debug(String.format("Not found local login session, checking federated state."));
    // 检查是否已经在一级节点登录
    CallbackHandler callbackHandler = new CMCCLoginCallbackHandler(request);
    LoginModule loginModule = (LoginModule) this.applicationContext.getBean("federalLoginModule");
    loginModule.initialize(new Subject(), callbackHandler, null, null);
    try {
      boolean ok = loginModule.login();
      if (ok) {
        loginModule.commit();
        log.debug(String.format("Federated login state is validate.!"));
        if (loginModule instanceof PrincipalAware) {
          Principal principal = ((PrincipalAware) loginModule).getPrincipal();
          if (principal instanceof PersonDTOPrincipal) {
            // Build local session
            buildLocalSession(request, response, principal);
          }
        }
      }
      return ok;
    } catch (Exception e) {
      log.error("Failure to checking federated SSO state.", e);
    }
    return false;
  }

  /**
   * @param request
   * @param response
   * @param principal
   * @throws IOException
   * @throws SessionManagementException
   */
  private void buildLocalSession(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException, SessionManagementException {
    String username = ((PersonDTOPrincipal) principal).getPersonDTO().getMsisdn();
    log.debug(String.format("Federated login state is validate, msisdn: [%s].", username));
    // Create session and update state
    SessionManager sessionManager = (SessionManager) this.getApplicationContext().getBean("sessionManager");
    String artifactID = CookieHelper.getArtifactIDFromCookies(request);
    String artifactDomain = CookieHelper.getArtifactDomainFromCookies(request);
    if (artifactID == null) {
      throw new IOException("Failure to get artifactID from cookies.");
    }
    // 标记用户已经从总部登录， 为报活和全局注销提供支持.
    Session session = sessionManager.create(username, artifactID, false, artifactDomain);
    // 刷新本地登录状态
    updateSessionState(request, response, username, artifactID, ((PersonDTOPrincipal) principal).getPersonDTO());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.web.AuthenRequestService#getCurrentArtifactID(javax
   * .servlet.http.HttpServletRequest)
   */
  public String getCurrentArtifactID(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      String artifactID = (String) session.getAttribute("ARTIFACT_ID");
      if (StringUtils.isNotEmpty(artifactID)) {
        return artifactID;
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cmcc.web.AuthenRequestService#generateAndSaveArtifactID(
   * javax.servlet.http.HttpServletRequest,
   * javax.servlet.http.HttpServletResponse, java.lang.String)
   */
  public String generateAndSaveArtifactID(Principal principal, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String username = principal.getName();
    if (StringUtils.isEmpty(username)) {
      throw new RuntimeException("Missing username!");
    }
    PersonDTO person = null;
    if (principal instanceof PersonDTOPrincipal) {
      person = ((PersonDTOPrincipal) principal).getPersonDTO();
    }

    SessionManager dao = (SessionManager) this.getApplicationContext().getBean("sessionManager");
    // TODO 直接传入PersonDTO, 而不是Session内部再通过接口提取一次
    String artifactDomain = getCurrentArtifactDomain(request);
    Session session = dao.create(username, true, artifactDomain);
    String artifactID = session.getArtifactID();
    if (artifactID == null) {
      throw new IOException("failure to create or update ldap entry.");
    }

    // 刷新本地登录状态
    updateSessionState(request, response, username, artifactID, person);
    return artifactID;
  }

  private void updateSessionState(HttpServletRequest request, HttpServletResponse response, String username, String artifactID, PersonDTO person) {
    // Update Session state
    HttpSession httpSession = request.getSession(true);
    httpSession.setAttribute("username", username);
    httpSession.setAttribute("ARTIFACT_ID", artifactID);
    httpSession.setAttribute("SESSION_PERSON", person);

    // Update to Cookies
    String artifactDomain = getCurrentArtifactDomain(request);
    String cookieDomain = getCurrentCookieDomain(request);
    CookieHelper.saveArtifactIdIntoCookies(response, artifactID, artifactDomain, cookieDomain);
    // Save msisdn into cookie
    CookieHelper.saveToCookies(response, cookieDomain, "UID", username);
  }

  /**
   * @return
   */
  private String getCurrentCookieDomain(HttpServletRequest request) {
    if (StringUtils.isNotEmpty(this.defaultCookieDomain)) {
      return this.defaultCookieDomain;
    }
    String serverName = request.getServerName();
    if (!isIPAdress(serverName)) {
      int index = serverName.indexOf('.');
      if (index > 0) {
        String cookieDomain = serverName.substring(index + 1);
        return cookieDomain;
      }
    }
    return null;
  }

  /**
   * @param request
   * @return
   */
  private String getCurrentArtifactDomain(HttpServletRequest request) {
    String artifactDomain = this.defaultArtifactDomain;
    if (StringUtils.isEmpty(artifactDomain)) {
      log.debug("Missing parameter artifactDoamin, auto detect from HTTPServletRequest");
      artifactDomain = request.getServerName();
      log.info(String.format("Using artifact domain: [%s]", artifactDomain));
    }
    return artifactDomain;
  }

  /**
   * 判断是否是IP地址
   * 
   * @param str
   * @return
   */
  private static boolean isIPAdress(String str) {
    Pattern pattern = Pattern.compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
    return pattern.matcher(str).matches();
  }
}
