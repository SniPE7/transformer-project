/**
 * 
 */
package com.sinopec.siam.am.idp.authn.module;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.siam.am.idp.authn.AuthenLevelDirector;
import com.ibm.siam.am.idp.authn.PostAuthenticationCallback;
import com.ibm.siam.am.idp.authn.SSOPrincipal;

import edu.internet2.middleware.shibboleth.common.profile.ProfileException;

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
    if (session == null || session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR) == null) {
      return;
    }
    // httpResponse.setContentType("text/html;charset=UTF-8");
    // httpResponse.setCharacterEncoding("UTF-8");

    SSOPrincipal principal = (SSOPrincipal) session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR);
    // Pass UID
    String userid = principal.getUid();
    if (log.isDebugEnabled()) {
      log.debug(String.format("Set EAI HTTP Header [%s=%s]", this.eaiUseridName, userid));
    }
    httpResponse.setHeader(this.eaiUseridName, userid);

    // Pass Extend Attrs
    List<String> names = principal.getAttributeNames();
    StringBuffer buf = new StringBuffer();
    for (int i = 0; names != null && i < names.size(); i++) {
      String name = names.get(i);
      if (name.equalsIgnoreCase("cn") || name.equalsIgnoreCase("givenName") || name.equalsIgnoreCase("displayname")) {
        // continue;
      }
      buf.append(this.tagValuePrefix);
      buf.append(name);
      if (i < names.size() - 1) {
        buf.append(',');
      }
    }
    if (log.isDebugEnabled()) {
      log.debug(String.format("Set EAI HTTP Header [%s=%s]", this.eaiXAttrsName, buf.toString()));
    }
    httpResponse.setHeader(this.eaiXAttrsName, buf.toString());

    for (int i = 0; i < names.size(); i++) {
      String name = names.get(i);
      if (name.equalsIgnoreCase("cn") || name.equalsIgnoreCase("givenName") || name.equalsIgnoreCase("displayname")) {
        // continue;
      }
      List<String> values = principal.getValueAsList(name);
      StringBuffer vbuf = new StringBuffer();
      for (int j = 0; values != null && j < values.size(); j++) {
        Object value = values.get(j);
        vbuf.append(value.toString());
        if (j < values.size() - 1) {
          vbuf.append(',');
        }
      }
      String heaherName = this.tagValuePrefix + name;
      if (log.isDebugEnabled()) {
        log.debug(String.format("Set EAI HTTP Header [%s=%s]", heaherName, vbuf.toString()));
      }
      // httpResponse.setHeader(heaherName, new
      // String(vbuf.toString().getBytes("UTF-8"), "iso8859-1"));
      try {
        httpResponse.setHeader(heaherName, URLEncoder.encode(vbuf.toString(), charset));
      } catch (UnsupportedEncodingException e) {
        log.error(String.format("error to encode EAI attribute use [%s]", this.charset));
      }
    }

    // Pass Authen Level

    String authenLevel = authenLevelDirector.getAuthenLevel(principal.getLastAuthenMethod());
    if (log.isDebugEnabled()) {
      log.debug(String.format("Get user authen method [%s]", principal.getLastAuthenMethod()));
      log.debug(String.format("Set EAI HTTP Header [%s=%s]", this.eaiAuthenLevenName, authenLevel));
    }
    httpResponse.setHeader(this.eaiAuthenLevenName, authenLevel);

  }

  public void handle(HttpServletRequest request, HttpServletResponse response) throws ProfileException {
    // Set return params for WebSEAL EAI
    this.decorate(request, response);
  }

}
