package com.sinopec.siam.am.idp.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * ����������� Controller��
 * 
 * @author Booker
 * 
 */
@Controller
public class RemindPasswordController extends BaseController {
  /**
   * ����ҳ�档
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/remind_password.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView handleRequestInternal(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/remind_password");
    super.setModelAndView(mav, request);
    return mav;
  }
  
  /**
   * ���޸�������ʾ���޸�����
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/remind_modify_password.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView remindModifyPassword(HttpServletRequest request) {
    request.setAttribute("j_username", request.getParameter("j_username"));
    request.setAttribute("show_username",  request.getParameter("show_username"));
    request.setAttribute("op", request.getParameter("op"));
    ModelAndView mav = new ModelAndView("/modify_password");
    super.setModelAndView(mav, request);
    return mav;
  }
}
