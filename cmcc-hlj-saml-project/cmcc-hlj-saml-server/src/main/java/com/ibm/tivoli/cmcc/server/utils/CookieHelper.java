package com.ibm.tivoli.cmcc.server.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {

  private static final String CM_TOKENID = "cmtokenid";

  public static void saveArtifactIdIntoCookies(HttpServletResponse response, String artifactID, String artifactDomain, String cookieDomain) {
    saveToCookies(response, cookieDomain, CM_TOKENID, artifactID + "@" + artifactDomain);
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
}
