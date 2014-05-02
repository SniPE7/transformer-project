package com.ibm.siam.am.idp.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * ��¼�� Controller��
 * 
 * @author Booker
 * 
 */
@Controller
public class ModifyPasswordController extends BaseController {
  /**
   * �޸�����
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
