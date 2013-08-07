package com.sinopec.siam.am.idp.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.sinopec.siam.am.idp.authn.service.PersonService;

/**
 * 注册手机号 Controller。
 * 
 * @author Booker
 * 
 */
@Controller
public class RegMobileController extends BaseController {
	
	private final Logger log = LoggerFactory.getLogger(RegMobileController.class);
	
	private static final String DATE_FORMAT = "yyyyMMddHHmmssZ";

	@Autowired
	@Qualifier("personService")
	PersonService personService;
	
  /**
   * 返回页面。
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/regmobile.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView handleRequestInternal(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("/lose/regmobile");
    super.setModelAndView(mav, request);
    return mav;
  }
  

	
	/**
	 * 修改用户手机号接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changemobile.do", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Map<String, String> changeMobile(HttpServletRequest request, HttpServletResponse response) {
		
		String msg = "";
		String status = "success";
		
		String userid = request.getParameter("j_username");
		String checkcode = request.getParameter("j_checkcode");
		String newMobile = request.getParameter("j_mobile");
		
		//check kaptcha code
		HttpSession session = request.getSession(false);
		if(null==session || null==checkcode || !checkcode.equalsIgnoreCase((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
			//return false
			Map<String, String> smsInfo = new HashMap<String, String>();
			smsInfo.put("msg", "验证码错误");
			smsInfo.put("status", "fail");
			
			return smsInfo;
		}

		//清理验证码
		//session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, "clearcode");
		
		//change password
		// 修改TIM口令（API）
	    try {
	    	Map<String, String> attrs = new HashMap<String, String>();
	    	attrs.put("mobile", newMobile);
	    	SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
	    	attrs.put("SGMMobileTime", df.format(new Date()));
	    	personService.updatePerson(userid, attrs);
	    } catch (Exception e) {
	    	msg = String.format("update mobile Exception. username:%s", userid);
	    	log.error(String.format(msg, userid), e);
	    	
	    	status = "fail";
	    }

		Map<String, String> smsInfo = new HashMap<String, String>();
		smsInfo.put("msg", msg);
		smsInfo.put("status", status);

		return smsInfo;

	}
  
}
