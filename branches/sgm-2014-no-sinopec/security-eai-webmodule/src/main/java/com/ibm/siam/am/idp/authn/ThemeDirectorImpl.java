package com.ibm.siam.am.idp.authn;

/**
 * 主题样式管理实现类
 * @author ZhouTengfei
 * @since 2012-11-7 下午1:25:50
 */

public class ThemeDirectorImpl implements ThemeDirector {
  private String themeOfIdPLoginPage = "default";

  public String getThemeOfIdPLoginPage() {
    return themeOfIdPLoginPage;
  }

  public void setThemeOfIdPLoginPage(String themeOfIdPLoginPage) {
    this.themeOfIdPLoginPage = themeOfIdPLoginPage;
  }

}
