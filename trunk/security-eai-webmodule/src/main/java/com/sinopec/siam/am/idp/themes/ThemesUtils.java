package com.sinopec.siam.am.idp.themes;

import org.apache.commons.lang.StringUtils;

/**
 * 主题皮肤池类
 * @author ZhouTengfei
 * @since 2012-11-3 下午3:31:31
 */

public class ThemesUtils {
  /**
   * IdP登录页面默认主题样式
   */
  private final static String DEFAULT_THEME = "default";
  /**
   * IdP登录页面主题样式属性
   */
  public final static String THEME_PARAM_NAME = "__theme_of_idp_login_page";
  
  /**
   * 主题样式参数名称
  */
  public final static String THEME_ATTR_NAME = "__THEME_OF_IDP_LOGIN_PAGE";
  
  public static String getThemeByName(String themeName){
    if(StringUtils.isNotEmpty(themeName)){
      return themeName;
    }else{
      return DEFAULT_THEME;
    }
  }

}
