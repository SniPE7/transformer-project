package com.sinopec.siam.am.idp.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 设置密码问题 Controller。
 * 
 * @author Booker
 * 
 */
@Controller
public class SetPasswordHintController extends BaseController {
  /**
   * 返回页面。
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/set_password_hint.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView handleRequestInternal(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/set_password_hint");
    super.setModelAndView(mav, request);
    return mav;
  }
}
