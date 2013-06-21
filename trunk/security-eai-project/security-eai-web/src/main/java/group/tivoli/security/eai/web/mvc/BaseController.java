package group.tivoli.security.eai.web.mvc;

import group.tivoli.security.eai.web.themes.ThemesPool;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller基类
 * @author ZhouTengfei
 * @since 2012-11-5 下午4:04:55
 */

public class BaseController {
  @Autowired
  private ThemesPool themesPool;
  
  /**
   * IdP登录页面主题样式属性
   */
  private final static String THEME_OF_IDP_LOGIN_PAGE = "__theme_of_IdP_login_page";
  
  public ModelAndView setModelAndView(ModelAndView mav, HttpServletRequest request){
  
    //设置主题样式路径
    String themes = (String) request.getSession().getAttribute(THEME_OF_IDP_LOGIN_PAGE);
    mav.addObject(THEME_OF_IDP_LOGIN_PAGE, themesPool.getThemesByName(themes));
    return mav;
  }
}
