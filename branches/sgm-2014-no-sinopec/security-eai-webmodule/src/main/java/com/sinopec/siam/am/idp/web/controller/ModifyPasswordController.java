package com.sinopec.siam.am.idp.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * µÇÂ¼ÓÃ Controller¡£
 * 
 * @author Booker
 * 
 */
@Controller
public class ModifyPasswordController extends BaseController {
  /**
   * ÐÞ¸ÄÃÜÂë
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/modify_password.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView handleRequestInternal(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/modify_password");
    super.setModelAndView(mav, request);
    return mav;
  }
  
}
