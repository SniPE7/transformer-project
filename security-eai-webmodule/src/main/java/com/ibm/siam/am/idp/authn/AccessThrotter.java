package com.ibm.siam.am.idp.authn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class AccessThrotter {

  public static final String COOKIE_NAME = "_EAI_THROTTER_";

  public static class ThrotterInfo {
    
    private List<Long> accesses = new ArrayList<Long>();

    public ThrotterInfo() {
      super();
    }

    public ThrotterInfo(String[] vs) {
      if (vs != null) {
         for (String s: vs) {
             try {
              accesses.add(Long.parseLong(s));
            } catch (NumberFormatException e) {
            }
         }
      }
    }

    public static ThrotterInfo decode(String value) {
      if (value == null) {
        return new ThrotterInfo();
      }
      
      String[] vs = StringUtils.split(value, ",");
      return new ThrotterInfo(vs);
    }

    public long getBegin() {
      if (this.accesses.size() > 0) {
        return this.accesses.get(0);
      }
      return 0;
    }

    public int getTotalAccess() {
      return this.accesses.size();
    }

    public void addAccess(int maxAccess) {
      this.accesses.add(System.currentTimeMillis() / 1000);
      // 确保保存的访问次数不超过最大次数-1
      while (this.accesses.size() >  maxAccess - 1) {
        this.accesses.remove(0);
      }
    }

    public String encode() {
      String result = "";
      for (int i = 0; i < this.accesses.size(); i++) {
           result += this.accesses.get(i) + ",";
      }
      return result;
    }
    
    

  }

  /**
   * 指定时间内允许访问的最大次数
   */
  private int maxAccess = 10;

  /**
   * 最大访问次数的时间范围（秒）
   */
  private int timeIntervalInSeconds = 60;

  public AccessThrotter() {
    super();
  }

  public AccessThrotter(int timeIntervalInSeconds, int maxAccess) {
    super();
    this.timeIntervalInSeconds = timeIntervalInSeconds;
    this.maxAccess = maxAccess;
  }

  public int getMaxAccess() {
    return maxAccess;
  }

  public void setMaxAccess(int maxAccess) {
    this.maxAccess = maxAccess;
  }

  public int getTimeIntervalInSeconds() {
    return timeIntervalInSeconds;
  }

  public void setTimeIntervalInSeconds(int timeIntervalInSeconds) {
    this.timeIntervalInSeconds = timeIntervalInSeconds;
  }

  public boolean isOverLoad(HttpServletRequest req, HttpServletResponse resp) {
    Cookie foundCookie = getCookie(req);
    if (foundCookie == null) {
       foundCookie = new Cookie(COOKIE_NAME, "");
    }
    ThrotterInfo info = ThrotterInfo.decode(foundCookie.getValue());
    if (info != null) {
      if ((info.getTotalAccess() + 1 >= this.maxAccess) && (System.currentTimeMillis() / 1000 - info.getBegin()) < this.timeIntervalInSeconds) {
         // 超过指定次数
         // 清除Cookie
         foundCookie.setValue("");
         resp.addCookie(foundCookie);
         return true;
      }
    }
    info.addAccess(this.maxAccess);
    foundCookie.setValue(info.encode());
    resp.addCookie(foundCookie);
    return false;
  }

  /**
   * @param req
   * @return
   */
  private Cookie getCookie(HttpServletRequest req) {
    Cookie foundCookie = null;
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie != null && cookie.getName().equals(COOKIE_NAME)) {
          foundCookie = cookie;
          break;
        }
      }
    }
    return foundCookie;
  }

}
