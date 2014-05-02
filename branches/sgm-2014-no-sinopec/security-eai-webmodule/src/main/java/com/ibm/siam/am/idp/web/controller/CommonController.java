package com.ibm.siam.am.idp.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Í¨ÓÃController¡£
 * 
 * @author Booker
 * 
 */
@Controller
public class CommonController extends BaseController {
  
  /**
   * ×¢ÏúµÇÂ¼
   * @param request
   * @return uri(info)
   */
  @RequestMapping(value = "/logout.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView info(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/logout");
    super.setModelAndView(mav, request);
    return mav;
  }
  
  /**
   * ´íÎóÒ³Ãæ
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
   * 404´íÎóÒ³Ãæ
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
   * ´íÎóÒ³Ãæ
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
