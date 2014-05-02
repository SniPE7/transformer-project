package com.sinopec.siam.am.idp.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.internet2.middleware.shibboleth.idp.session.Session;

/**
 * ��¼�� Controller��
 * 
 * @author Booker
 * 
 */
@Controller
public class LoginController extends BaseController {

	/**
	 * �û��������¼ҳ�档
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
	 * ABS��¼ҳ�档
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
	 * ֤���¼����
	 * 
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
	 * ERP֤���¼����
	 * 
	 * @param request
	 * @return uri(erplogin)
	 */
	@RequestMapping(value = "/login/erp/loginform.do", method = RequestMethod.GET)
	public ModelAndView erpLogin(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/login/erp/loginform");
		super.setModelAndView(mav, request);
		return mav;
	}

	/**
	 * ֤���¼����
	 * 
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
	 * TAM�û��������¼����
	 * 
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
	 * UID/����+SMS OTP��¼ҳ��
	 * 
	 * @param request
	 * @return uri(smsuserlogin)
	 */
	@RequestMapping(value = "/login/smsuser/loginform.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView smsUserLogin(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/login/smsuser/loginform");
		super.setModelAndView(mav, request);
		return mav;
	}
	
	/**
	 * UID/����+����+SMS OTP��¼ҳ��
	 * 
	 * @param request
	 * @return uri(smspasslogin)
	 */
	@RequestMapping(value = "/login/smspass/loginform.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView smsPassLogin(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/login/smspass/loginform");
		super.setModelAndView(mav, request);
		return mav;
	}

	/**
	 * AD�û��������¼����
	 * 
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
	 * IDP��¼��Ϣ
	 * 
	 * @param request
	 * @return uri(info)
	 */
	@RequestMapping(value = "/login/info.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView info(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/login/info");

		HttpSession session = request.getSession(false);
		if (session != null) {
			Session userSession = (Session) session.getAttribute(Session.HTTP_SESSION_BINDING_ATTRIBUTE);
			if(userSession!=null) {
			    request.setAttribute("PrincipalName", userSession.getPrincipalName());
		        request.setAttribute("ServiceInformation", userSession.getServicesInformation());
			}
		}
		super.setModelAndView(mav, request);
		return mav;
	}

}
