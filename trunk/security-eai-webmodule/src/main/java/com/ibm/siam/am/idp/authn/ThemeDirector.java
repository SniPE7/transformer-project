package com.ibm.siam.am.idp.authn;


/**
 * ������ʽ����ӿ�
 * @author ZhouTengfei
 * @since 2012-11-7 ����1:24:05
 */

public interface ThemeDirector {
  /**  ������ʽ��������**/
  public final String THEME_PARAM_NAME = "__theme_of_idp_login_page";
  
  /**  ������ʽ��������**/
  public final static String THEME_ATTR_NAME = "__theme_of_idp_login_page";
  
  /**  Idp��¼ҳ��Ĭ��������ʽ**/
  public final static String DEFAULT_THEME = "default";
  
  public String getThemeOfIdPLoginPage();

}
