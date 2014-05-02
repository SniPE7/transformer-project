package com.ibm.siam.am.idp.themes;

import org.apache.commons.lang.StringUtils;

/**
 * ����Ƥ������
 * @author ZhouTengfei
 * @since 2012-11-3 ����3:31:31
 */

public class ThemesUtils {
  /**
   * IdP��¼ҳ��Ĭ��������ʽ
   */
  private final static String DEFAULT_THEME = "default";
  /**
   * IdP��¼ҳ��������ʽ����
   */
  public final static String THEME_PARAM_NAME = "__theme_of_idp_login_page";
  
  /**
   * ������ʽ��������
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
