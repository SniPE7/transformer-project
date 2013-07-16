package com.sinopec.siam.am.idp.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.siam.am.idp.authn.entity.CardRegisterEntity;
import com.sinopec.siam.am.idp.authn.service.LdapUserServiceImpl;
import com.sinopec.siam.am.idp.authn.service.UserPassService;
import com.sinopec.siam.am.idp.authn.service.UserService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;

@Controller
public class CardController extends BaseController {
	/** UserService Bean ID */
	private String userServiceBeanId = "tamLdapUserService";

	private String tamAdMappingFilter = "(&(sprolelist={})(objectclass=inetorgperson))";

	@RequestMapping(value = "/card/insert.do", method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView insert(HttpServletRequest request) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("cn", "root"));
		filter.and(new EqualsFilter("ca", "root2"));
		filter.and(new EqualsFilter("cb", "root3"));

		String ss = filter.toString();
		
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
		cardRegisterEntity.setCardATR("");
		// TODO get match code
		cardRegisterEntity.setMatchCode("");
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
		
		UserService userService = getUserService();
		List<LdapUserEntity> ldapUserEntitys = userService
				.searchByFilter(filter.toString());
		if (ldapUserEntitys == null || ldapUserEntitys.size()==0){
			session.setAttribute("errorMessage", "用户不存在。");
			ModelAndView mav = new ModelAndView("/card/error");
			super.setModelAndView(mav, request);
			return mav;
		}
		
		LdapUserEntity ldapUserEntity = ldapUserEntitys.get(0);
		
		String username = ldapUserEntity.getValueAsString("sn");
		String employeeNumber = ldapUserEntity.getValueAsString("employeeNumber");
		String name = ldapUserEntity.getValueAsString("givenName");
		
		cardRegisterEntity.setUsername(username);
		cardRegisterEntity.setEmployeeNumber(employeeNumber);
		cardRegisterEntity.setName(name);
		session.setAttribute("cardRegisterEntity", cardRegisterEntity);

		String loginFlag = ldapUserEntity.getValueAsString("SGMeOnboardTime");
		ModelAndView mav;
		if ("1".equals(loginFlag)) {
			mav = new ModelAndView("/card/newreg_verify_user");
		} else {
			mav = new ModelAndView("/card/newreg_regist");			
		}
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
		if (optype==0) {
			cardRegisterEntity.setPassword((String)request.getParameter("newPassword"));
			
			// TODO write ldap
		} else {
			String password =(String)request.getParameter("j_password");
			String captcha =(String)request.getParameter("j_checkcode");
			
			// TODO verify user by ldap
			AndFilter filter = new AndFilter();
			filter.and(new EqualsFilter("sn", cardRegisterEntity.getUsername()));
			filter.and(new EqualsFilter("userPassword", password));
			
			UserService userService = getUserService();
			List<LdapUserEntity> ldapUserEntitys = userService
					.searchByFilter(filter.toString());
			if (ldapUserEntitys == null || ldapUserEntitys.size()==0){
				session.setAttribute("errorMessage", "用户口令不正确。");
				ModelAndView mav = new ModelAndView("/card/error");
				super.setModelAndView(mav, request);
				return mav;
			}
			
			// TODO write ldap
		}
		session.setAttribute("cardRegisterEntity", cardRegisterEntity);
		
		// TODO write ldap
		
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
		String captcha =(String)request.getParameter("j_checkcode");
			
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("mobile", mobile));
		filter.and(new EqualsFilter("userPassword", password));
		
		UserService userService = getUserService();
		List<LdapUserEntity> ldapUserEntitys = userService
				.searchByFilter(filter.toString());
		if (ldapUserEntitys == null || ldapUserEntitys.size()==0){
			session.setAttribute("errorMessage", "手机号或者口令不正确。");
			ModelAndView mav = new ModelAndView("/card/error");
			super.setModelAndView(mav, request);
			return mav;
		}
			
		// TODO write ldap for the new card uid
		
		ModelAndView mav = new ModelAndView("/card/chgreg_success");
		super.setModelAndView(mav, request);
		return mav;
	}
	
	@RequestMapping(value = "/card/chgreg_success.do", method = {
			RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	public ModelAndView chgreg_success(HttpServletRequest request) {
		HttpSession session = request.getSession();

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
}
