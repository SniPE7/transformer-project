package com.ibm.tivoli.cmcc.server.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class CookieHelper {

  private static final String CM_TOKENID = "cmtokenid";

  public static void saveArtifactIdIntoCookies(HttpServletResponse response, String artifactID, String artifactDomain, String cookieDomain) {
    if (!StringUtils.isEmpty(System.getProperty("catalina.home"))) {
      saveToCookies4Tomcat(response, cookieDomain, CM_TOKENID, artifactID + "@" + artifactDomain);
    } else {
      saveToCookies(response, cookieDomain, CM_TOKENID, artifactID + "@" + artifactDomain);
    }
  }

  /**
   * Save value into cookies
   * 
   * @param response
   * @param key
   * @param value
   */
  public static void saveToCookies(HttpServletResponse response, String cookieDomain, String key, String value) {
    Cookie cookie = new Cookie(key, value);
    cookie.setDomain(cookieDomain);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  /**
   * 由于移动规范中定义的Cookie值中包含@特殊字符, 此字符不被Cookie允许, 所以造成Tomcat会为其增加一对", 为了克服此问题, 需绕过Tomcat的处理机制, 直接输出Cookie.
   * WebSphere行为目前没有测试.
   * 
   * @param response
   * @param key
   * @param value
   */
  public static void saveToCookies4Tomcat(HttpServletResponse response, String cookieDomain, String key, String value) {
    response.addHeader("Set-Cookie", String.format("%s=%s; domain=%s; path=/", key, value, cookieDomain));
  }

  /**
   * Extract value from cookie
   * 
   * @param request
   * @param key
   * @return
   */
  public static String[] getFromCookies(HttpServletRequest request, String key) {
    List<String> result = new ArrayList<String>();
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return result.toArray(new String[0]);
    }
    for (Cookie cookie : cookies) {
      String name = cookie.getName();
      if (name.equals(key)) {
        result.add(cookie.getValue());
      }
    }
    return result.toArray(new String[0]);
  }

  /**
   * @param request
   * @return
   */
  public static String getArtifactIDFromCookies(HttpServletRequest request) {
    String[] values = getFromCookies(request, CM_TOKENID);
    if (values != null) {
      for (String v : values) {
        int index = v.indexOf('@');
        if (index > 0) {
          return v.substring(0, index);
        } else {
          return v;
        }
      }
    }
    return null;
  }

  /**
   * @param request
   * @return
   */
  public static String getArtifactDomainFromCookies(HttpServletRequest request) {
    String[] values = getFromCookies(request, CM_TOKENID);
    if (values != null) {
      for (String v : values) {
        int index = v.indexOf('@');
        if (index > 0) {
          return v.substring(index + 1);
        } else {
          return null;
        }
      }
    }
    return null;
  }
}
