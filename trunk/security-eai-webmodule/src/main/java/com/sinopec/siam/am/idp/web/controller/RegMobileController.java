package com.sinopec.siam.am.idp.web.controller;

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
 * ע���ֻ��� Controller��
 * 
 * @author Booker
 * 
 */
@Controller
public class RegMobileController extends BaseController {
	
	private final Logger log = LoggerFactory.getLogger(RegMobileController.class);
	
	@Autowired
	@Qualifier("personService")
	PersonService personService;
	
  /**
   * ����ҳ�档
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
	 * �޸��û��ֻ��Žӿ�
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
			smsInfo.put("msg", "��֤�����");
			smsInfo.put("status", "fail");
			
			return smsInfo;
		}

		//������֤��
		//session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, "clearcode");
		
		//change password
		// �޸�TIM���API��
	    try {
	    	Map<String, String> attrs = new HashMap<String, String>();
	    	attrs.put("mobile", newMobile);
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
