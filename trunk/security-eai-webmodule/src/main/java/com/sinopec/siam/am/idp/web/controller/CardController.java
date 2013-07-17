package com.sinopec.siam.am.idp.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.ibm.siam.am.idp.authn.entity.CardRegisterEntity;
import com.sinopec.siam.am.idp.authn.service.PersonService;
import com.sinopec.siam.am.idp.authn.service.UserService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;

import edu.internet2.middleware.shibboleth.idp.authn.LoginHandler;

@Controller
public class CardController extends BaseController {
	/** UserService Bean ID */
	private String userServiceBeanId = "tamLdapUserService";

	private String tamAdMappingFilter = "(&(sprolelist={})(objectclass=inetorgperson))";
	
	private String failureParam = "loginFailed";

	@Autowired
	@Qualifier("personService")
	PersonService personService;
	
	@RequestMapping(value = "/card/insert.do", method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView insert(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/card/insert");
		super.setModelAndView(mav, request);
		return mav;
	}

	@RequestMapping(value = "/card/selectop.do", method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView selectop(HttpServletRequest request) {
		HttpSession session = request.getSession();

		CardRegisterEntity cardRegisterEntity = (CardRegisterEntity) session
				.getAttribute("cardRegisterEntity");
		if (cardRegisterEntity == null) {
			cardRegisterEntity = new CardRegisterEntity();
		}
		String cardUid = (String) request.getParameter("carduid");
		cardRegisterEntity.setCardUid(cardUid);
		// TODO get match code
		cardRegisterEntity.setMatchCode("43729348");
		session.setAttribute("cardRegisterEntity", cardRegisterEntity);

		ModelAndView mav = new ModelAndView("/card/selectop");
		super.setModelAndView(mav, request);
		return mav;
	}

	@RequestMapping(value = "/card/newreg_input_number.do", method = {
			RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView newreg_input_number(HttpServletRequest request) {
		HttpSession session = request.getSession();

		CardRegisterEntity cardRegisterEntity = (CardRegisterEntity) session
				.getAttribute("cardRegisterEntity");
		if (cardRegisterEntity == null) {
			cardRegisterEntity = new CardRegisterEntity();
			cardRegisterEntity.setOptype(0);
			session.setAttribute("cardRegisterEntity", cardRegisterEntity);
		}

		ModelAndView mav = new ModelAndView("/card/newreg_input_number");
		super.setModelAndView(mav, request);
		return mav;
	}

	@RequestMapping(value = "/card/check_login_status.do", method = {
			RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView check_login_status(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		CardRegisterEntity cardRegisterEntity = (CardRegisterEntity) session
				.getAttribute("cardRegisterEntity");
		if (cardRegisterEntity == null) {
			cardRegisterEntity = new CardRegisterEntity();
		}
		cardRegisterEntity.setOptype(0);
		cardRegisterEntity.setIdNumber((String) request
				.getParameter("id_number"));
		cardRegisterEntity.setEmployeeNumber((String) request
				.getParameter("employee_number"));
		session.setAttribute("cardRegisterEntity", cardRegisterEntity);

		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("SGMCertID", cardRegisterEntity.getIdNumber()));
		filter.and(new EqualsFilter("employeeNumber", cardRegisterEntity.getEmployeeNumber()));
		filter.and(new EqualsFilter("objectclass", "SGMEmployeePerson"));
		
		UserService userService = getUserService();
		
		List<LdapUserEntity> ldapUserEntitys;
		try {
		ldapUserEntitys = userService
				.searchByFilter(filter.toString());
		} catch (Exception e)  {
			request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "card.error.ldapError");
			request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, null);
			request.setAttribute(failureParam, "true");
			
			ModelAndView mav = new ModelAndView("/card/newreg_input_number");
			super.setModelAndView(mav, request);
			return mav;
		}
		
		if (ldapUserEntitys == null || ldapUserEntitys.size()==0){
			request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "card.error.userNotExist");
			request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, null);
			request.setAttribute(failureParam, "true");
			
			ModelAndView mav = new ModelAndView("/card/newreg_input_number");
			super.setModelAndView(mav, request);
			return mav;
		}
		
		LdapUserEntity ldapUserEntity = ldapUserEntitys.get(0);
		
		String username = ldapUserEntity.getUid();
		String employeeNumber = ldapUserEntity.getValueAsString("employeeNumber");
		String name = ldapUserEntity.getValueAsString("givenName");
		
		cardRegisterEntity.setUsername(username);
		cardRegisterEntity.setEmployeeNumber(employeeNumber);
		cardRegisterEntity.setName(name);
		cardRegisterEntity.setDn(ldapUserEntity.getDn());
		session.setAttribute("cardRegisterEntity", cardRegisterEntity);

		String loginFlag = ldapUserEntity.getValueAsString("alloweprptservice");
		ModelAndView mav;
		if ("false".compareToIgnoreCase(loginFlag)==0) {
			cardRegisterEntity.setOptype(1);
			mav = new ModelAndView("/card/newreg_verify_user");
		} else {
			cardRegisterEntity.setOptype(0);
			mav = new ModelAndView("/card/newreg_regist");			
		}
		session.setAttribute("cardRegisterEntity", cardRegisterEntity);
		
		super.setModelAndView(mav, request);
		return mav;
	}

	@RequestMapping(value = "/card/newreg_regist.do", method = {
			RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView newreg_regist(HttpServletRequest request) {
		HttpSession session = request.getSession();

		CardRegisterEntity cardRegisterEntity = (CardRegisterEntity) session
				.getAttribute("cardRegisterEntity");
		if (cardRegisterEntity == null) {
			cardRegisterEntity = new CardRegisterEntity();
		}
		cardRegisterEntity.setOptype(0);
		session.setAttribute("cardRegisterEntity", cardRegisterEntity);
		
		ModelAndView mav = new ModelAndView("/card/newreg_regist");
		super.setModelAndView(mav, request);
		return mav;
	}

	@RequestMapping(value = "/card/newreg_verify_user.do", method = {
			RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView newreg_verify_user(HttpServletRequest request) {		
		HttpSession session = request.getSession();

		CardRegisterEntity cardRegisterEntity = (CardRegisterEntity) session
				.getAttribute("cardRegisterEntity");
		if (cardRegisterEntity == null) {
			cardRegisterEntity = new CardRegisterEntity();
		}
		cardRegisterEntity.setOptype(1);
		session.setAttribute("cardRegisterEntity", cardRegisterEntity);

		ModelAndView mav = new ModelAndView("/card/newreg_verify_user");
		super.setModelAndView(mav, request);
		return mav;
	}

	@RequestMapping(value = "/card/newreg_execute.do", method = {
			RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView newreg_execute(HttpServletRequest request) {
		HttpSession session = request.getSession();

		CardRegisterEntity cardRegisterEntity = (CardRegisterEntity) session
				.getAttribute("cardRegisterEntity");
		if (cardRegisterEntity == null) {
			cardRegisterEntity = new CardRegisterEntity();
		}
		int optype = cardRegisterEntity.getOptype();
		if (optype==0) { // new user
			cardRegisterEntity.setPassword((String)request.getParameter("newPassword"));
			
			// write ldap
			try {
		    	Map<String, String> attrs = new HashMap<String, String>();
		    	attrs.put("SGMBadgeUID", cardRegisterEntity.getCardUid());
		    	attrs.put("SGMBadgeCode", cardRegisterEntity.getMatchCode());
		    	attrs.put("SGMBadgeTime", getCurrentDate());
		    	personService.updatePerson(cardRegisterEntity.getUsername(), attrs);

		    	//personService.updatePassword(cardRegisterEntity.getUsername(), cardRegisterEntity.getPassword());
		    } catch (Exception e) {
		    	request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "card.error.updateUserInfo");
				request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, null);
				request.setAttribute(failureParam, "true");
				
				ModelAndView mav = new ModelAndView("/card/newreg_regist");
				super.setModelAndView(mav, request);
				return mav;
		    }
		} else { // already login user
			String password =(String)request.getParameter("j_password");
			String inputCaptcha =(String)request.getParameter("j_checkcode");
			
			String captcha = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
			
			if (!inputCaptcha.equals(captcha)) {
				request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "login.form.error.checkCodeFailed");
				request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, null);
				request.setAttribute(failureParam, "true");
				
				ModelAndView mav = new ModelAndView("/card/newreg_verify_user");
				super.setModelAndView(mav, request);
				return mav;
			}
			
			// verify user by ldap
			UserService userService = getUserService();
			
			boolean isUserPassed = userService.authenticateByUserDnPassword(cardRegisterEntity.getDn(), password);					
//			if (!isUserPassed){
//				request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "card.error.usernamePasswordError");
//				request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, null);
//				request.setAttribute(failureParam, "true");
//				
//				ModelAndView mav = new ModelAndView("/card/newreg_verify_user");
//				super.setModelAndView(mav, request);
//				return mav;
//			}
			
			try {
		    	Map<String, String> attrs = new HashMap<String, String>();
		    	attrs.put("SGMBadgeUID", cardRegisterEntity.getCardUid());
		    	attrs.put("SGMBadgeCode", cardRegisterEntity.getMatchCode());
		    	attrs.put("SGMBadgeTime", getCurrentDate());
		    	personService.updatePerson(cardRegisterEntity.getUsername(), attrs);
		    } catch (Exception e) {
		    	request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "card.error.updateUserInfo");
				request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, null);
				request.setAttribute(failureParam, "true");
				
				ModelAndView mav = new ModelAndView("/card/newreg_verify_user");
				super.setModelAndView(mav, request);
				return mav;
		    }
		}
		session.setAttribute("cardRegisterEntity", cardRegisterEntity);
		
		request.setAttribute(LoginHandler.AUTHENTICATION_INFO_KEY, "card.info.register.success");
		ModelAndView mav = new ModelAndView("/card/newreg_success");
		super.setModelAndView(mav, request);
		return mav;
	}

	@RequestMapping(value = "/card/newreg_success.do", method = {
			RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView newreg_success(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/card/newreg_success");
		super.setModelAndView(mav, request);
		return mav;
	}

	@RequestMapping(value = "/card/chgreg_verify_user.do", method = {
			RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView chgreg_verify_user(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/card/chgreg_verify_user");
		super.setModelAndView(mav, request);
		return mav;
	}

	@RequestMapping(value = "/card/chgreg_execute.do", method = {
			RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView chgreg_execute(HttpServletRequest request) {
		HttpSession session = request.getSession();

		CardRegisterEntity cardRegisterEntity = (CardRegisterEntity) session
				.getAttribute("cardRegisterEntity");

		String mobile =(String)request.getParameter("mobile");
		String password =(String)request.getParameter("j_password");
		String inputCaptcha =(String)request.getParameter("j_checkcode");
		
		String captcha = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		
		if (!inputCaptcha.equals(captcha)) {
			request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "login.form.error.checkCodeFailed");
			request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, null);
			request.setAttribute(failureParam, "true");
			
			ModelAndView mav = new ModelAndView("/card/chgreg_verify_user");
			super.setModelAndView(mav, request);
			return mav;
		}

		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("mobile", mobile));
		filter.and(new EqualsFilter("objectclass", "SGMEmployeePerson"));
		
		UserService userService = getUserService();
		List<LdapUserEntity> ldapUserEntitys = userService
				.searchByFilter(filter.toString());
		if (ldapUserEntitys == null || ldapUserEntitys.size()==0){
			request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "card.error.mobilePasswordError");
			request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, null);
			request.setAttribute(failureParam, "true");
			
			ModelAndView mav = new ModelAndView("/card/chgreg_verify_user");
			super.setModelAndView(mav, request);
			return mav;
		}
		
		LdapUserEntity ldapUserEntity = ldapUserEntitys.get(0);
		String username = ldapUserEntity.getUid();
		
		boolean isUserPassed = userService.authenticateByUserDnPassword(ldapUserEntity.getDn(), password);					
//		if (!isUserPassed){
//			request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "card.error.mobilePasswordError");
//			request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, null);
//			request.setAttribute(failureParam, "true");
//			
//			ModelAndView mav = new ModelAndView("/card/chgreg_verify_user");
//			super.setModelAndView(mav, request);
//			return mav;
//		}
			
		try {
	    	Map<String, String> attrs = new HashMap<String, String>();
	    	attrs.put("SGMBadgeUID", cardRegisterEntity.getCardUid());
	    	attrs.put("SGMBadgeCode", cardRegisterEntity.getMatchCode());
	    	attrs.put("SGMBadgeTime", getCurrentDate());
	    	personService.updatePerson(username, attrs);
	    } catch (Exception e) {
	    	request.setAttribute(LoginHandler.AUTHENTICATION_ERROR_TIP_KEY, "card.error.updateUserInfo");
			request.setAttribute(LoginHandler.AUTHENTICATION_ARGUMENTS_KEY, null);
			request.setAttribute(failureParam, "true");
			
			ModelAndView mav = new ModelAndView("/card/chgreg_verify_user");
			super.setModelAndView(mav, request);
			return mav;
	    }
		
		ModelAndView mav = new ModelAndView("/card/chgreg_success");
		super.setModelAndView(mav, request);
		return mav;
	}
	
	@RequestMapping(value = "/card/chgreg_success.do", method = {
			RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView chgreg_success(HttpServletRequest request) {
		HttpSession session = request.getSession();

		request.setAttribute(LoginHandler.AUTHENTICATION_INFO_KEY, "card.info.register.success");
		
		ModelAndView mav = new ModelAndView("/card/chgreg_success");
		super.setModelAndView(mav, request);
		return mav;
	}
	
	@RequestMapping(value = "/card/error.do", method = {
			RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView error(HttpServletRequest request) {
		HttpSession session = request.getSession();

		ModelAndView mav = new ModelAndView("/card/error");
		super.setModelAndView(mav, request);
		return mav;
	}

	private UserService getUserService() {
		ApplicationContext appContext = ContextLoader
				.getCurrentWebApplicationContext();
		return appContext.getBean(userServiceBeanId, UserService.class);
	}
	
	private String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String dateString = sdf.format(new Date());

		return (dateString + "Z");
	}
}
