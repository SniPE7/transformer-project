package group.tivoli.security.eai.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 通用Controller。
 * 
 * @author Booker
 * 
 */
@Controller
public class CommonController extends BaseController {
  
  /**
   * 注销登录
   * @param request
   * @return uri(info)
   */
  @RequestMapping(value = "/logout.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView logout(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/logout");
    super.setModelAndView(mav, request);
    return mav;
  }
  
  /**
   * 错误页面
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/error.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView error(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/error");
    super.setModelAndView(mav, request);
    return mav;
  }
  
  /**
   * 404错误页面
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/error404.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView error400(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/error404");
    super.setModelAndView(mav, request);
    return mav;
  }
  
  /**
   * 错误页面
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/loginerror.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView loginerror(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/loginerror");
    super.setModelAndView(mav, request);
    return mav;
  }
}
