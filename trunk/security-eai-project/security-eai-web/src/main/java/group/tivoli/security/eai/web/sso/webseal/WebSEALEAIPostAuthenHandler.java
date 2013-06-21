/**
 * 
 */
package group.tivoli.security.eai.web.sso.webseal;

import edu.internet2.middleware.shibboleth.common.profile.ProfileException;
import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;
import edu.internet2.middleware.shibboleth.idp.authn.UsernamePrincipal;
import edu.vt.middleware.ldap.bean.LdapAttribute;
import edu.vt.middleware.ldap.bean.LdapAttributes;
import edu.vt.middleware.ldap.jaas.LdapPrincipal;
import group.tivoli.security.eai.web.auth.AuthenticationConstant;
import group.tivoli.security.eai.web.sso.AuthenLevelDirector;
import group.tivoli.security.eai.web.sso.PostAuthenticationCallback;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhaodonglu
 * 
 */
public class WebSEALEAIPostAuthenHandler implements PostAuthenticationCallback {
  private static Log log = LogFactory.getLog(WebSEALEAIPostAuthenHandler.class);

  private String eaiXAttrsName = "am-eai-xattrs";
  private String tagValuePrefix = "tagvalue_";
  private String eaiUseridName = "am-eai-user-id";;
  private String eaiAuthenLevenName = "am-eai-auth-level";
  
  private String eaiRedirUrlName = "am-eai-redir-url";
  private String eaiRedirUrl = "";
  

  private AuthenLevelDirector authenLevelDirector = null;

  private String charset = "UTF-8";

  // private Set<String> ignoredAttributeNames = new HashSet<String>();
  /**
   * 
   */
  public WebSEALEAIPostAuthenHandler() {
    super();
  }

  public String getCharset() {
    return charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public String getEaiXAttrsName() {
    return eaiXAttrsName;
  }

  public void setEaiXAttrsName(String eaiXAttrsName) {
    this.eaiXAttrsName = eaiXAttrsName;
  }

  public String getTagValuePrefix() {
    return tagValuePrefix;
  }

  public void setTagValuePrefix(String tagValuePrefix) {
    this.tagValuePrefix = tagValuePrefix;
  }

  public String getEaiUseridName() {
    return eaiUseridName;
  }

  public void setEaiUseridName(String eaiUseridName) {
    this.eaiUseridName = eaiUseridName;
  }
  
  public String getEaiRedirUrlName() {
    return eaiRedirUrlName;
  }

  public void setEaiRedirUrlName(String eaiRedirUrlName) {
    this.eaiRedirUrlName = eaiRedirUrlName;
  }

  public String getEaiRedirUrl() {
    return eaiRedirUrl;
  }

  public void setEaiRedirUrl(String eaiRedirUrl) {
    this.eaiRedirUrl = eaiRedirUrl;
  }

  public String getEaiAuthenLevenName() {
    return eaiAuthenLevenName;
  }

  public void setEaiAuthenLevenName(String eaiAuthenLevenName) {
    this.eaiAuthenLevenName = eaiAuthenLevenName;
  }

  public AuthenLevelDirector getAuthenLevelDirector() {
    return authenLevelDirector;
  }

  public void setAuthenLevelDirector(AuthenLevelDirector authenLevelDirector) {
    this.authenLevelDirector = authenLevelDirector;
  }

  /**
   * 向HTTPServletResponse中增加WebSEAL EAI所需要的TagValue扩展属性、认证用户信息及认证级别信息。
   * 
   * @param httpRequest
   * @param httpResponse
   * @throws UnsupportedEncodingException
   */
  private void decorate(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
    HttpSession session = httpRequest.getSession(false);
    if (session == null || session.getAttribute(LoginHandler.SUBJECT_KEY) == null) {
      return;
    }

    // httpResponse.setContentType("text/html;charset=UTF-8");
    // httpResponse.setCharacterEncoding("UTF-8");

    Subject userSubject = (Subject) session.getAttribute(LoginHandler.SUBJECT_KEY);
    
    // 设置认证等级
    String authenLevel = (String) session.getAttribute(LoginHandler.AUTHENTICATION_METHOD_KEY);
    if (log.isDebugEnabled()) {
      log.debug(String.format("Get user authen method [%s]", authenLevel));
      log.debug(String.format("Set EAI HTTP Header [%s=%s]", this.eaiAuthenLevenName, authenLevel));
    }
    httpResponse.setHeader(this.eaiAuthenLevenName, authenLevel);
    
    //redirect
    if(null != this.eaiRedirUrl && !"".equals(eaiRedirUrl) && !eaiRedirUrl.startsWith("$")){
      httpResponse.setHeader(this.eaiRedirUrlName, this.eaiRedirUrl);
    }
    
    // 设置ldapprincipal身份属性到header头
    if (AuthenticationConstant.AUTH_TYPE_TAM.equals(authenLevel)) {
      SetLdapPrincipalToHeader(httpResponse, userSubject);
      
    }else if(AuthenticationConstant.AUTH_TYPE_CERT.equals(authenLevel)){
      //设置cert UsernamePrincipal身份属性到header头
      
      SetUsernamePrincipalToHeader(httpResponse, userSubject);
    }
    
  }
  
  private void SetLdapAttributesToHeader(HttpServletResponse httpResponse, LdapAttributes attributes) {

    if (attributes == null) {
      return;
    }
    
    // 设置uid标签值
    String userId = this.getLdapAttributeValue(attributes, "uid");
    if (log.isDebugEnabled()) {
      log.debug(String.format("Set EAI HTTP Header [%s=%s]", this.eaiUseridName, userId));
    }
    httpResponse.setHeader(this.eaiUseridName, userId);

    // 设置属性名称字段
    StringBuffer buf = new StringBuffer();
    for (String name : attributes.getAttributeNames()) {
      buf.append(this.tagValuePrefix);
      buf.append(name);
      buf.append(',');
    }
    if (buf.length() > 0)
      buf.deleteCharAt(buf.length() - 1);

    if (log.isDebugEnabled()) {
      log.debug(String.format("Set EAI HTTP Header [%s=%s]", this.eaiXAttrsName, buf.toString()));
    }
    httpResponse.setHeader(this.eaiXAttrsName, buf.toString());

    // 设置属性值字段
    for (String name : attributes.getAttributeNames()) {
      StringBuffer vbuf = new StringBuffer();
      vbuf.append(getLdapAttributeValue(attributes, name));

      String heaherName = this.tagValuePrefix + name;
      if (log.isDebugEnabled()) {
        log.debug(String.format("Set EAI HTTP Header [%s=%s]", heaherName, vbuf.toString()));
      }

      try {
        httpResponse.setHeader(heaherName, URLEncoder.encode(vbuf.toString(), charset));
      } catch (UnsupportedEncodingException e) {
        log.error(String.format("error to encode EAI attribute use [%s]", this.charset));
      }
    }
  }
  
  private void SetUsernamePrincipalToHeader(HttpServletResponse httpResponse, Subject userSubject){
    Set<UsernamePrincipal> subPrincipals = userSubject.getPrincipals(UsernamePrincipal.class);
    
    UsernamePrincipal principal = null;
    LdapAttributes attributes = null;
    // 存在认证身份信息
    if (subPrincipals != null && !subPrincipals.isEmpty()) {
      principal = (UsernamePrincipal) subPrincipals.iterator().next();
      attributes = principal.getLdapAttributes();
      SetLdapAttributesToHeader(httpResponse, attributes);
    }
  }
  
  private void SetLdapPrincipalToHeader(HttpServletResponse httpResponse, Subject userSubject){
    Set<LdapPrincipal> subPrincipals = userSubject.getPrincipals(LdapPrincipal.class);
    
    LdapPrincipal principal = null;
    LdapAttributes attributes = null;
    // 存在认证身份信息
    if (subPrincipals != null && !subPrincipals.isEmpty()) {
      principal = (LdapPrincipal) subPrincipals.iterator().next();
      attributes = principal.getLdapAttributes();
      SetLdapAttributesToHeader(httpResponse, attributes);
    }
  }

  public void handle(HttpServletRequest request, HttpServletResponse response) throws ProfileException {
    // Set return params for WebSEAL EAI
    this.decorate(request, response);
  }

  /**
   * 获取 LdapAttributes中的属性值，多值 逗号 分隔
   * 
   * @param attributes
   *          属性集合
   * @param key
   *          属性名称
   * @return 值串
   */
  private String getLdapAttributeValue(LdapAttributes attributes, String key) {
    if (null == attributes)
      return "";
    return getLdapAttributeValue(attributes.getAttribute(key));
  }

  /**
   * 获取 LdapAttribute中的属性值，多值 逗号 分隔
   * 
   * @param attribute
   *          LdapAttribute属性对象
   * @return 值串
   */
  private String getLdapAttributeValue(LdapAttribute attribute) {
    StringBuffer retBuf = new StringBuffer();

    if (null == attribute)
      return "";

    for (String value : attribute.getStringValues()) {
      retBuf.append(value).append(",");
    }

    if (retBuf.length() > 0)
      retBuf.deleteCharAt(retBuf.length()-1);

    return retBuf.toString();
  }

}
