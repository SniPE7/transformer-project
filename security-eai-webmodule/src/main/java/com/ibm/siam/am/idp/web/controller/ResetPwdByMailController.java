package com.ibm.siam.am.idp.web.controller;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ibm.siam.am.idp.authn.service.MatchCodeService;
import com.ibm.siam.am.idp.authn.service.MultiplePersonFoundException;
import com.ibm.siam.am.idp.authn.service.PersonNotFoundException;
import com.ibm.siam.am.idp.authn.service.PersonService;
import com.ibm.siam.am.idp.authn.service.PersonServiceException;
import com.ibm.siam.am.idp.authn.service.UserService;
import com.ibm.siam.am.idp.authn.util.CryptUtils;
import com.ibm.siam.am.idp.authn.util.Token;
import com.ibm.siam.am.idp.authn.util.email.EmailConfig;
import com.ibm.siam.am.idp.authn.util.email.EmailSendTool;
import com.ibm.siam.am.idp.entity.LdapUserEntity;

import edu.internet2.middleware.shibboleth.idp.authn.LoginContextEntry;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * 通过邮件方式重置忘记密码 Controller。
 * 
 * @author Booker
 * 
 */
@Controller
public class ResetPwdByMailController extends BaseController {
	
	private final Logger log = LoggerFactory.getLogger(ResetPwdByMailController.class);
	
	@Autowired
	@Qualifier("personService")
	PersonService userPassService;
	
	@Autowired
    @Qualifier("tamLdapUserService")
    private UserService userService;
	
	@Autowired
    private MatchCodeService matchCodeService;
	
	@Value("#{beanProperties['eai.loginmodule.user.search.filter']}")
	private String smsFilter = "(&(|(uid={0})(badgeid={0}))(objectclass=inetOrgPerson))";
	
    @Value("#{beanProperties['eai.loginmodule.user.search.displayattrname']}")
    private String displayNameAttribute = "displayName";
    
    private String userMailAttribute = "mail";
	
    @Value("#{beanProperties['eai.loginmodule.cry.key']}")
	private String key = "012=456789ab-def";

    @Value("#{beanProperties['eai.loginmodule.resetpwd.active.url']}")
	private String mailUrl = "https://192.168.134.146/eaiweb/setpwdbymail.do?sid=";
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Value("#{beanProperties['eai.loginmodule.resetpwd.mail.title']}")
	private String mailTitle = "用户密码修复";
	
	@Value("#{beanProperties['eai.loginmodule.resetpwd.mail.content']}")
	private String mailContent = "请您点击下面链接来修复登录密码:${activeurl}  为了确保您的帐号安全，该链接仅30分钟内访问有效。";
	
	@Value("#{beanProperties['eai.loginmodule.resetpwd.active.timeout']}")
	private Long mailPastDueTime = 1800000L;
	
	@Value("#{beanProperties['eai.loginmodule.mail.smtp']}")
	private String mailSmtp = "smtp.163.com";
	
	@Value("#{beanProperties['eai.loginmodule.mail.userid']}")
	private String mailUserId = "xrogzu@163.com";
	
	@Value("#{beanProperties['eai.loginmodule.mail.password']}")
	private String mailPasswd = "******";
	
	@Value("#{beanProperties['eai.loginmodule.mail.username']}")
	private String mailUserName = "用户管理员";
	
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;
	
	@RequestMapping(value = "/setpwdchoose.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
	  public ModelAndView chooseTypeSetPwd(HttpServletRequest request, HttpServletResponse response) {
	      
	      HttpSession session = request.getSession(false);
	      if(session !=null) {
	          LoginContextEntry entry = (LoginContextEntry) session.getAttribute(HttpServletHelper.LOGIN_CTX_KEY_NAME);
	          if(entry!=null) {
	              String realUrl = "";
	              Object redirUrl = session.getAttribute("eai-redir-url-header");
	                if(redirUrl!=null) {
	                    realUrl = redirUrl.toString();
	                } else {
	                    realUrl = request.getContextPath() + entry.getLoginContext().getAccessEnforcerURL();
	                }
	                
	              request.setAttribute("gotoUrl", realUrl);
	          }
	      }
	      
	        ModelAndView mav = new ModelAndView("/lose/setpwdchoose");
	        super.setModelAndView(mav, request);
	        return mav;
	  }
	
  /**
   * 返回页面。
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/resetpwdbymail.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
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
			        realUrl = request.getContextPath() + entry.getLoginContext().getAccessEnforcerURL();
			    }
			    
			  request.setAttribute("gotoUrl", realUrl);
		  }
	  }
	  
	    ModelAndView mav = new ModelAndView("/lose/resetpwdbymail");
	    super.setModelAndView(mav, request);
	    return mav;
  }
  
  @RequestMapping(value = "/setpwdbymail.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
  public ModelAndView handleSetPwdByMail(HttpServletRequest request) {
      
      HttpSession session = request.getSession(true);
      if(session !=null) {
          LoginContextEntry entry = (LoginContextEntry) session.getAttribute(HttpServletHelper.LOGIN_CTX_KEY_NAME);
          if(entry!=null) {
              String realUrl = "";
              Object redirUrl = session.getAttribute("eai-redir-url-header");
                if(redirUrl!=null) {
                    realUrl = redirUrl.toString();
                } else {
                    realUrl = request.getContextPath() + entry.getLoginContext().getAccessEnforcerURL();
                }
                
              request.setAttribute("gotoUrl", realUrl);
          }
      }
      
      request.setAttribute("isavail", false);
      String sid = request.getParameter("sid");
      if(sid!=null && !"".equals(sid.trim())) {
          Token token = validateToken(sid);
          if(token!=null) {
              request.setAttribute("isavail", true);
              
              //set session
              session.setAttribute("reset-pwd-uid", token.getId());
          }
      }
      
    ModelAndView mav = new ModelAndView("/lose/setpwdbymail");
    super.setModelAndView(mav, request);
    return mav;
  }
  
  @RequestMapping(value = "/resetpwd/sendmail.do", method = { RequestMethod.GET, RequestMethod.POST})
  public @ResponseBody
  Map<String, String> handleSendMail(HttpServletRequest request, HttpServletResponse response) {
      String username = "";
      String displayName = "";
      String mail = "";
      String msg = "";
      String status = "fail";
      
      String mailUrl = "";
      
      String userid = request.getParameter("j_username");

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
          mail = ldapUserEntitys.get(0).getValueAsString(userMailAttribute);
          displayName = ldapUserEntitys.get(0).getValueAsString(displayNameAttribute);
          username = ldapUserEntitys.get(0).getUid();
          // if null to display error info
          if (mail == null || "".equals(mail)) {
  
              msg = String.format("user'mail is null, filter:%s.", filter);
              log.info(msg);
              
              msg = String.format("您的邮箱地址未绑定，请绑定后再试/Your email address is not binding, please try again after binding");
          } else {
              //send validate url email
              mailUrl = createValidityURL(createToken(username));
              
              sendMail(mail, mailTitle, mailContent.replace("${activeurl}", mailUrl));
              status = "success";
              
              log.info(String.format("send reset password active url to user mail: [%s]", mail));
          }
      }
      
      Map<String, String> smsInfo = new HashMap<String, String>();
      smsInfo.put("mail", mail);
      smsInfo.put("username", username);
      smsInfo.put("displayname", displayName);
      
      if(null!=cardMatchCode && !"".equals(cardMatchCode)) {
          smsInfo.put("matchcode", cardMatchCode);
      }
      
      if(null!=mailUrl && !"".equals(mailUrl)) {
          smsInfo.put("mailurl", mailUrl);
      }
      
      //smsInfo.put("dn", ldapUserEntitys.get(0).getDn());
      smsInfo.put("msg", msg);
      smsInfo.put("status", status);
      //smsInfo.put("code", smsCode);

      return smsInfo;

  }
  
	/**
	 * 用户忘记密码后修改密码接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/resetpwd/changepwd.do", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Map<String, String> changeLosePassword(HttpServletRequest request, HttpServletResponse response) {
		
		String msg = "";
		String status = "success";
		
		String newPassword = request.getParameter("j_npassword");
		
		HttpSession session = request.getSession(false);
		String userid = null;
		if(null!=session) {
		    userid = (String)session.getAttribute("reset-pwd-uid");
		    if(userid==null||"".equals(userid.trim())) {
    			//return false
    			Map<String, String> smsInfo = new HashMap<String, String>();
    			smsInfo.put("msg", "超时,验证错误/timeout and verification error");
    			smsInfo.put("status", "fail");
    			
    			return smsInfo;
		    }
		}
		
		//change password
		// 修改TIM口令（API）
	    try {
			userPassService.updatePassword(userid, newPassword);
			
			//清理uid
	        session.removeAttribute("reset-pwd-uid");
	        
	        log.info(String.format("reset password successful: uid[%s]", userid));
		} catch (PersonNotFoundException e) {
			msg = String.format("PersonNotFoundException Exception. username:%s", userid);
	    	log.error(String.format(msg, userid), e);
	    	
	    	status = "fail";
		} catch (MultiplePersonFoundException e) {
			msg = String.format("MultiplePersonFoundException Exception. username:%s", userid);
	    	log.error(String.format(msg, userid), e);
	    	
	    	status = "fail";
		} catch (PersonServiceException e) {
			msg = String.format("PersonServiceException Exception. username:%s", userid);
	    	log.error(String.format(msg, userid), e);
	    	
	    	msg = messageSource.getMessage("global.msg.error", null, LocaleContextHolder.getLocale()) + " "
	    	        + messageSource.getMessage("modifyPass.info.userpass.policy", null, LocaleContextHolder.getLocale());
	    	
	    	status = "fail";
		}
	    
		Map<String, String> smsInfo = new HashMap<String, String>();
		smsInfo.put("msg", msg);
		smsInfo.put("status", status);

		return smsInfo;

	}
	
	private Token validateToken(String tokenEnc) {
	    Token token = null;
        
        try {
            String tokenStr = CryptUtils.decrypt(tokenEnc, key);
            token = validateToken(JSON.parseObject(tokenStr, Token.class));

        } catch (Exception e) {
        }
        
        return token;
        
    }
	  
	private Date convertTime(String dateTimeStr) {
        // Date sample: 2014-02-25 12:20:21
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        try {
          return df.parse(dateTimeStr);
        } catch (ParseException e) {
          log.warn(String.format("Failure to parse date string: [%s], pattern: [%s]", dateTimeStr, DATE_FORMAT), e);
          return null;
        }
    }
	
	private String getDateNow() {
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        
        return df.format(now);
    }
	
	private Token validateToken(Token token) {
	    Date tokenTime = convertTime(token.getDate());
	    
	    if(tokenTime!=null) {
	        tokenTime.setTime(tokenTime.getTime() + mailPastDueTime);
	        if (tokenTime.compareTo(new Date()) >= 0) {
	            return token;
	        }
	    } 
	    
	    return null;
	}
	
	private Token createToken(String uid) {
	    Token token = new Token();
	    token.setId(uid);
	    token.setDate(getDateNow());
	    
	    return token;
	}
	
	private String createValidityURL(Token token) {
	    
	    String jsonString = JSON.toJSONString(token);
	    String desString = CryptUtils.encrypt(jsonString, key);
	    
	    return mailUrl + desString;
	}
	
	private boolean sendValidityMail(String url) {
	    boolean isSuccess = false;
	    
	    
	    return isSuccess;
	}
	
	private boolean parseInternetIP(String ip) {
	    boolean isInternet = false;
	    
	    return isInternet;
	}
	
	private boolean sendMail(String toMail, String title, String content) {
	    boolean success = false;
	    
	    EmailConfig mailConf = new EmailConfig();
        mailConf.setHost(mailSmtp);
        mailConf.setUsername(mailUserId);
        mailConf.setPassword(mailPasswd);
        mailConf.setPersonal(mailUserName);
                
        EmailSendTool sendEmail = new EmailSendTool(mailConf.getHost(),
                                                    mailConf.getUsername(), mailConf.getPassword(), toMail,
                                                    title, content, mailConf.getPersonal(), "", "");
        try {
            sendEmail.send();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return success;
	}
}
