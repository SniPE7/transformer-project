package group.tivoli.security.eai.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录用 Controller。
 * 
 * @author Booker
 * 
 */
@Controller
public class LoginController extends BaseController {
  
  /**
   * 用户名密码登录页面。
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/login/userpass/loginform.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView login(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/login/userpass/loginform");
    super.setModelAndView(mav, request);
    return mav;
  }
  
  /**
   * ABS登录页面。
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/login/abs/loginform.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView abslogin(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/login/abs/loginform");
    super.setModelAndView(mav, request);
    return mav;
  }

  /**
   * 证书登录界面
   * @param request
   * @return uri(certlogin)
   */
  @RequestMapping(value = "/login/cert/loginform.do", method = RequestMethod.GET)
  public ModelAndView certLogin(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/login/cert/loginform");
    super.setModelAndView(mav, request);
    return mav;
  }

  /**
   * 证书登录界面
   * @param request
   * @return uri(certlogin)
   */
  @RequestMapping(value = "/login/itim/loginform.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView itimLogin(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/login/itim/loginform");
    super.setModelAndView(mav, request);
    return mav;
  }
  
  /**
   * TAM用户名口令登录界面
   * @param request
   * @return uri(tamlogin)
   */
  @RequestMapping(value = "/login/tam/loginform.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView tamLogin(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/login/tam/loginform");
    super.setModelAndView(mav, request);
    return mav;
  }
  
  /**
   * AD用户名口令登录界面
   * @param request
   * @return uri(adlogin)
   */
  @RequestMapping(value = "/login/ad/loginform.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView adLogin(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/login/ad/loginform");
    super.setModelAndView(mav, request);
    return mav;
  }
  
  /**
   * IDP登录信息
   * @param request
   * @return uri(info)
   */
  @RequestMapping(value = "/login/info.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView info(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/login/info");
    super.setModelAndView(mav, request);
    return mav;
  }
  
}
