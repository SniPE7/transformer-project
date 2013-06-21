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
public class ModifyPasswordController extends BaseController {
  /**
   * 修改密码
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/modify_password.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView handleRequestInternal(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/modify_password", "", "");

    super.setModelAndView(mav, request);
    return mav;
  }
  
}