package group.tivoli.security.eai.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 密码过期提醒 Controller。
 * 
 * @author Booker
 * 
 */
@Controller
public class RemindPasswordController extends BaseController {
  /**
   * 返回页面。
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/remind_password.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView handleRequestInternal(HttpServletRequest request) {
    //return new ModelAndView("/remind_password", "", "");
    
    ModelAndView mav = new ModelAndView("/remind_password", "", "");

    super.setModelAndView(mav, request);
    return mav;
  }
  
  /**
   * 由修改密码提示到修改密码
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/remind_modify_password.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView remindModifyPassword(HttpServletRequest request) {
    request.setAttribute("j_username", request.getParameter("j_username"));
    request.setAttribute("show_username",  request.getParameter("show_username"));
    request.setAttribute("op", request.getParameter("op"));
    
    ModelAndView mav = new ModelAndView("/modify_password", "", "");

    super.setModelAndView(mav, request);
    return mav;
    
    //return new ModelAndView("/modify_password", "", "");
  }
}
