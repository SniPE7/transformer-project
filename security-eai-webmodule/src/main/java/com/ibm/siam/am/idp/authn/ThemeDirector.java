package com.ibm.siam.am.idp.authn;


/**
 * 主题样式管理接口
 * @author ZhouTengfei
 * @since 2012-11-7 下午1:24:05
 */

public interface ThemeDirector {
  /**  主题样式参数名称**/
  public final String THEME_PARAM_NAME = "__theme_of_idp_login_page";
  
  /**  主题样式参数名称**/
  public final static String THEME_ATTR_NAME = "__theme_of_idp_login_page";
  
  /**  Idp登录页面默认主题样式**/
  public final static String DEFAULT_THEME = "default";
  
  public String getThemeOfIdPLoginPage();

}
