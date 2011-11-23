/**
 * 
 */
package com.ibm.tivoli.cmcc.service.auth;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.xml.sax.SAXException;

import com.ibm.tivoli.cmcc.ldap.PersonDAO;
import com.ibm.tivoli.cmcc.request.AuthenRequest;
import com.ibm.tivoli.cmcc.server.utils.Base64;
import com.ibm.tivoli.cmcc.server.utils.Helper;

/**
 * @author zhaodonglu
 *
 */
public class AuthenRequestServiceImpl implements ApplicationContextAware, AuthenRequestService {

  private static final String CM_TOKENID = "cmtokenid";
  private String cookieDomain = "ac.10086.cn";
  private ApplicationContext applicationContext;

  /**
   * 
   */
  public AuthenRequestServiceImpl() {
    super();
  }

  /**
   * @return the cookieDomain
   */
  public String getCookieDomain() {
    return cookieDomain;
  }

  /**
   * @param cookieDomain the cookieDomain to set
   */
  public void setCookieDomain(String cookieDomain) {
    this.cookieDomain = cookieDomain;
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

  private void saveArtifactIdIntoCookies(HttpServletResponse response, String value) {
    saveToCookies(response, CM_TOKENID, value + "@" + cookieDomain);
  }

  /**
   * Save value into cookies
   * @param response
   * @param key
   * @param value
   */
  private void saveToCookies(HttpServletResponse response, String key, String value) {
    Cookie cookie = new Cookie(key, value);
    cookie.setDomain("ac.10086.cn");
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  /**
   * Extract value from cookie
   * @param request
   * @param key
   * @return
   */
  private String getFromCookies(HttpServletRequest request, String key) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
       return null;
    }
    for (Cookie cookie: cookies) {
        String name = cookie.getName();
        if (name.equals(key)) {
           return cookie.getValue();
        }
    }
    return null;
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
  

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.web.AuthenRequestService#validate(javax.servlet.http.HttpServletRequest)
   */
  public void validate(HttpServletRequest request) throws Exception {
    String relayState = request.getParameter("RelayState");
    if (StringUtils.isEmpty(relayState)) {
      throw new RuntimeException("Missing RelayState!");
    }
    this.parseRequest(request);
  }
  
  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.web.AuthenRequestService#parseRequest(javax.servlet.http.HttpServletRequest)
   */
  public AuthenRequest parseRequest(HttpServletRequest request) throws IOException, SAXException {
    String samlRequestB64 = request.getParameter("SAMLRequest");
    if (StringUtils.isEmpty(samlRequestB64)) {
      throw new RuntimeException("Missing SAMLRequest!");
    }
    // Parsing SAMLRequest
    String samlContent = new String(Base64.decode(samlRequestB64));

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
  
  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.web.AuthenRequestService#getRelayState(javax.servlet.http.HttpServletRequest)
   */
  public String getRelayState(HttpServletRequest request) {
    return request.getParameter("RelayState");
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.web.AuthenRequestService#isAuthenticated(javax.servlet.http.HttpServletRequest)
   */
  public boolean isAuthenticated(HttpServletRequest request) {
    // 检查是否已经在本地登录
    HttpSession session = request.getSession(false);
    if (session != null) {
       String artifactID = (String)session.getAttribute("ARTIFACT_ID");
       if (StringUtils.isNotEmpty(artifactID)) {
          return true;
       }
    }
    // 检查是否已经在一级节点登录
    return false;
  }
  
  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.web.AuthenRequestService#getCurrentArtifactID(javax.servlet.http.HttpServletRequest)
   */
  public String getCurrentArtifactID(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
       String artifactID = (String)session.getAttribute("ARTIFACT_ID");
       if (StringUtils.isNotEmpty(artifactID)) {
          return artifactID;
       }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.web.AuthenRequestService#generateAndSaveArtifactID(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
   */
  public String generateAndSaveArtifactID(HttpServletRequest request, HttpServletResponse response, String username) throws Exception {
    PersonDAO dao = (PersonDAO)this.applicationContext.getBean("ldapDao");
    
    String artifactID =  Helper.generatorID();
    artifactID = dao.insertUniqueIdentifier("", username, artifactID );
    if (artifactID == null) {
       throw new IOException("failure to create or update ldap entry.");
    }
    
    // Update Session state
    HttpSession session = request.getSession(true);
    session.setAttribute("username", username);
    session.setAttribute("ARTIFACT_ID", artifactID);
    
    // Update to Cookies
    this.saveArtifactIdIntoCookies(response, artifactID);
    return artifactID;
  }

}
