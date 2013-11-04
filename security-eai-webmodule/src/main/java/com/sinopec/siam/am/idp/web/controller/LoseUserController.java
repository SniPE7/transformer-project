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
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.sinopec.siam.am.idp.authn.service.UserService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;

import edu.internet2.middleware.shibboleth.idp.authn.LoginContextEntry;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * 忘记用户名 Controller。
 * 
 * @author Booker
 * 
 */
@Controller
public class LoseUserController extends BaseController {
	
	private final Logger log = LoggerFactory.getLogger(LoseUserController.class);
	
	@Value("#{beanProperties['eai.loginmodule.user.search.idcardfilter']}")
	private String idCardFilter = "(|(employeeNumber={0})(employeeNumber={1}))";

	@Value("#{beanProperties['eai.loginmodule.user.search.mobileattrname']}")
	private String userMobileAttribute = "mobile";
	
	@Value("#{beanProperties['eai.loginmodule.user.search.displayattrname']}")
	private String displayNameAttribute = "displayName";
	
	
	@Autowired
	@Qualifier("tamLdapUserService")
	private UserService userService;

	
  /**
   * 返回页面。
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/loseuser.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView handleRequestInternal(HttpServletRequest request) {
	  
	  HttpSession session = request.getSession(false);
	  if(session !=null) {
		  LoginContextEntry entry = (LoginContextEntry) session.getAttribute(HttpServletHelper.LOGIN_CTX_KEY_NAME);
		  if(entry!=null) {
			  String realUrl = "";
			  Object redirUrl = session.getAttribute("eai-redir-url-header");
              if(redirUrl!=null) {
                  realUrl = redirUrl.toString();
              } else {
                  realUrl = request.getContextPath() + "/" + entry.getLoginContext().getAccessEnforcerURL();
              }
			  request.setAttribute("gotoUrl", realUrl);
		  }
	  }
	  
	    ModelAndView mav = new ModelAndView("/lose/user");
	    super.setModelAndView(mav, request);
	    return mav;
  }
  
  /**
	 * 检测身份证号码，返回用户必要属性
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkidcard.do", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Map<String, String> checkIdCard(HttpServletRequest request, HttpServletResponse response) {
		String username = "";
		String displayName = "";
		String mobile = "";
		String msg = "";
		String checkCode = "";
		String status = "fail";
		
		String idCard = request.getParameter("j_idcard");
		String checkcode = request.getParameter("j_checkcode");
		String isSendMsg = request.getParameter("issend");
		
		HttpSession session = request.getSession(false);
		if(null==session || !checkcode.equalsIgnoreCase((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
			//return false
			Map<String, String> smsInfo = new HashMap<String, String>();
			smsInfo.put("msg", "验证码错误");
			smsInfo.put("status", "fail");
			
			return smsInfo;
		}

		// get mobileno from ldap by idcard
		String filter = MessageFormat.format(idCardFilter, idCard, idCard);

		List<LdapUserEntity> ldapUserEntitys = userService.searchByFilter(filter);
		if (ldapUserEntitys.size() == 0) {
			msg = String.format("idcard not exists, filter: %s.", filter);
			log.info(msg);
		} else if (ldapUserEntitys.size() > 1) {
			msg = String.format("Find more than one user by filter,filter:%s.", filter);
			log.info(msg);
		} else {
			mobile = ldapUserEntitys.get(0).getValueAsString(userMobileAttribute);
			displayName = ldapUserEntitys.get(0).getValueAsString(displayNameAttribute);
			username = ldapUserEntitys.get(0).getUid();
			// if null to display error info
			if (mobile == null || "".equals(mobile)) {
	
				msg = String.format("user'mobile is null, filter:%s.", filter);
				log.info(msg);
			} else {
				if("true".equalsIgnoreCase(isSendMsg)){
					//send username to mobile
					//send(username);
					status = "success";
				} else {
					status = "success";
				}
			}
		}
		
		Map<String, String> smsInfo = new HashMap<String, String>();
		smsInfo.put("mobile", mobile);
		smsInfo.put("username", username);
		smsInfo.put("displayname", displayName);
		//smsInfo.put("dn", ldapUserEntitys.get(0).getDn());
		smsInfo.put("msg", msg);
		smsInfo.put("status", status);

		return smsInfo;

	}
  
}
