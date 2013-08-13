package com.sinopec.siam.am.idp.web.controller;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.ibm.siam.am.idp.authn.service.MatchCodeService;
import com.sinopec.siam.am.idp.authn.service.UserService;
import com.sinopec.siam.am.idp.authn.util.dyncpwd.DyncUtil;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;
import com.techcenter.SpringSMSClient;

/**
 * 提供页面ajax调用 Controller。
 * 
 * @author Booker
 * 
 */
@Controller
public class VerifyCodeController extends BaseController {

	private final Logger log = LoggerFactory.getLogger(VerifyCodeController.class);

	@Autowired
	@Qualifier("tamLdapUserService")
	private UserService userService;
	
	@Autowired
	private MatchCodeService matchCodeService;
	
	@Autowired
	private SpringSMSClient smsClient;
	
	@Value("#{beanProperties['eai.loginmodule.user.search.filter']}")
	private String smsFilter = "(&amp;(|(uid={0})(badgeid={0}))(objectclass=inetOrgPerson))";
	
	@Value("#{beanProperties['eai.loginmodule.user.search.mobileattrname']}")
	private String userMobileAttribute = "mobile";
	
	@Value("#{beanProperties['eai.loginmodule.user.search.displayattrname']}")
	private String displayNameAttribute = "displayName";

	/**
	 * 获取短信动态密码 登录获取短信动态密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getsmscode.do", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Map<String, String> getSMSCode(HttpServletRequest request, HttpServletResponse response) {
		String username = "";
		String displayName = "";
		String mobile = "";
		String msg = "";
		String smsCode = "";
		String status = "fail";
		
		String userid = request.getParameter("j_username");
		String checkcode = request.getParameter("j_checkcode");
		mobile = request.getParameter("j_mobile");
		
		HttpSession session = request.getSession(false);
		if(null==session || !checkcode.equalsIgnoreCase((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
			//return false
			Map<String, String> smsInfo = new HashMap<String, String>();
			smsInfo.put("msg", "验证码错误");
			smsInfo.put("status", "fail");
			
			return smsInfo;
		}

		// get mobileno from ldap by userid or badgeid
		String filter = MessageFormat.format(smsFilter, userid);
		List<LdapUserEntity> ldapUserEntitys = userService.searchByFilter(filter);
		
		// 支持通过MatchCode来查询用户
		String cardMatchCode = "";
		if (ldapUserEntitys==null || ldapUserEntitys.size() == 0) {
			String cardUID = userid;
			try {
				cardMatchCode = matchCodeService.getMatchCode(cardUID);
				//cardMatchCode = "123456789012345678";
				
				filter = MessageFormat.format(smsFilter, cardMatchCode);
				ldapUserEntitys = userService.searchByFilter(filter);
				
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug(String.format("Search user DN exception", e.getMessage()));
				}
			}
		}
		
		if (ldapUserEntitys==null || ldapUserEntitys.size() == 0) {
			msg = String.format("Username not exists, filter: %s.", filter);
			log.info(msg);
			
			msg = "用户不存在/User does not exist";
			
		} else if (ldapUserEntitys.size() > 1) {
			msg = String.format("Find more than one user by filter,filter:%s.", filter);
			log.info(msg);
		} else {
			if(mobile==null || "".equals(mobile)) {
				mobile = ldapUserEntitys.get(0).getValueAsString(userMobileAttribute);
			}
			
			displayName = ldapUserEntitys.get(0).getValueAsString(displayNameAttribute);
			username = ldapUserEntitys.get(0).getUid();
			// if null to display error info
			if (mobile == null || "".equals(mobile)) {
	
				msg = String.format("user'mobile is null, filter:%s.", filter);
				log.info(msg);
				
				msg = String.format("您的手机未绑定，请绑定后再试/Your phone is not binding, please try again after binding");
			} else {
				// if get it, send smscode to mobile of user, return mobileno. need callsms api
				smsCode = DyncUtil.getPassword(userid, "");
				//send sms
				String sendMsg = "您的短信验证是 " + smsCode;
				smsClient.getSubmitSender().send(mobile, sendMsg);
				
				log.info("get user sms code is :" + smsCode);
				status = "success";
			}
		}
		
		Map<String, String> smsInfo = new HashMap<String, String>();
		smsInfo.put("mobile", mobile);
		smsInfo.put("username", username);
		smsInfo.put("displayname", displayName);
		
		if(null!=cardMatchCode && !"".equals(cardMatchCode)) {
			smsInfo.put("matchcode", cardMatchCode);
		}
		//smsInfo.put("dn", ldapUserEntitys.get(0).getDn());
		smsInfo.put("msg", msg);
		smsInfo.put("status", status);
		smsInfo.put("code", smsCode);

		return smsInfo;

	}
	
	/**
	 * 检查短信动态密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checksmscode.do", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Map<String, String> checkSMSCode(HttpServletRequest request, HttpServletResponse response) {
		
		String msg = "";
		//String status = "fail";
		String smsCode = "";
		
		String userid = request.getParameter("j_username");
		String checkcode = request.getParameter("j_checkcode");
		String checksmsCode = request.getParameter("j_smscode");
		
		HttpSession session = request.getSession(false);
		if(null==session || null==checkcode || !checkcode.equalsIgnoreCase((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
			//return false
			Map<String, String> smsInfo = new HashMap<String, String>();
			smsInfo.put("msg", "验证码错误");
			smsInfo.put("status", "fail");
			
			return smsInfo;
		}

		smsCode = DyncUtil.getPassword(userid, "");
		if(null==checksmsCode || !checksmsCode.equalsIgnoreCase(smsCode)) {
			//return false
			Map<String, String> smsInfo = new HashMap<String, String>();
			smsInfo.put("msg", "短信码错误");
			smsInfo.put("status", "fail");
			
			return smsInfo;
		}
		
		Map<String, String> smsInfo = new HashMap<String, String>();
		smsInfo.put("msg", msg);
		smsInfo.put("status", "success");

		return smsInfo;

	}
	
	
	/**
	 * 检查验证码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkcode.do", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Map<String, String> checkCode(HttpServletRequest request, HttpServletResponse response) {
		
		String msg = "";
		//String status = "fail";
		
		String checkcode = request.getParameter("j_checkcode");
		
		HttpSession session = request.getSession(false);
		if(null==session || null==checkcode || !checkcode.equalsIgnoreCase((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
			//return false
			Map<String, String> smsInfo = new HashMap<String, String>();
			smsInfo.put("msg", "验证码错误");
			smsInfo.put("status", "fail");
			
			return smsInfo;
		}
		
		Map<String, String> smsInfo = new HashMap<String, String>();
		smsInfo.put("msg", msg);
		smsInfo.put("status", "success");

		return smsInfo;

	}
	
}
