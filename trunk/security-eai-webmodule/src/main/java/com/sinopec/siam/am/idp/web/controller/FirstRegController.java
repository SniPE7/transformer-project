package com.sinopec.siam.am.idp.web.controller;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.ibm.siam.am.idp.authn.SSOPrincipal;
import com.ibm.siam.am.idp.authn.SSOPrincipalImpl;
import com.ibm.siam.am.idp.authn.service.MatchCodeService;
import com.sinopec.siam.am.idp.authn.module.WebSEALEAIPostAuthenHandler;
import com.sinopec.siam.am.idp.authn.service.MultiplePersonFoundException;
import com.sinopec.siam.am.idp.authn.service.PersonNotFoundException;
import com.sinopec.siam.am.idp.authn.service.PersonService;
import com.sinopec.siam.am.idp.authn.service.PersonServiceException;
import com.sinopec.siam.am.idp.authn.service.UserService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;

import edu.internet2.middleware.shibboleth.common.profile.ProfileException;
import edu.internet2.middleware.shibboleth.idp.authn.LoginContextEntry;
import edu.internet2.middleware.shibboleth.idp.util.HttpServletHelper;

/**
 * 首次登陆 Controller。
 * 
 * @author Booker
 * 
 */
@Controller
public class FirstRegController extends BaseController {
	
	private final Logger log = LoggerFactory.getLogger(FirstRegController.class);
	
	@Autowired
	@Qualifier("personService")
	PersonService personService;
	
    @Autowired
    private MatchCodeService matchCodeService;
    
    @Autowired
    WebSEALEAIPostAuthenHandler websealService;
    
	@Autowired
    @Qualifier("tamLdapUserService")
    private UserService userService;
	
	@Value("#{beanProperties['eai.reg.user.search.idcardandidfilter']}")
    private String idCardAndIdFilter = "(&(|(uid={0})(SGMBadgeUID={0})(SGMBadgeCode={0}))(employeenumber={1})(objectclass=inetOrgPerson))";
	
	@Value("#{beanProperties['eai.loginmodule.user.search.mobileattrname']}")
    private String userMobileAttribute = "mobile";
	
	@Value("#{beanProperties['eai.loginmodule.user.search.mobileupdateattrname']}")
    private String userMobileTimeAttribute = "SGMMobileTime";
    
    @Value("#{beanProperties['eai.loginmodule.user.search.displayattrname']}")
    private String displayNameAttribute = "displayName";
    
    @Value("#{beanProperties['eai.loginmodule.user.search.mobileattrname']}")
    private String mobileTag = "mobile";
    
    @Value("#{beanProperties['eai.loginmodule.user.search.mobileupdateattrname']}")
    private String mobileUpdateTag = "mobileTime";
    
    private static final String DATE_FORMAT = "yyyyMMddHHmm'Z'";
	
  /**
   * 返回页面。
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/reg/firstlogin.do", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD })
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
			        realUrl = "/";
			    }
			    
			  request.setAttribute("gotoUrl", realUrl);
		  } else {
		      request.setAttribute("gotoUrl", "/");
		  }
	  }
	  
	    ModelAndView mav = new ModelAndView("/reg/firstlogin");
	    super.setModelAndView(mav, request);
	    return mav;
  }
  
  @RequestMapping(value = "/reg/check.do", method = { RequestMethod.GET, RequestMethod.POST })
  public @ResponseBody
  Map<String, String> checkIdentity(HttpServletRequest request, HttpServletResponse response) {
      
      String username = "";
      String displayName = "";
      String mobile = "";
      String msg = "";
      String status = "fail";
      
      String userid = request.getParameter("j_username");
      String checkcode = request.getParameter("j_checkcode");
      String idcard = request.getParameter("j_idcard");
      
      //check kaptcha code
      HttpSession session = request.getSession(false);
      if(null==session || null==checkcode || !checkcode.equalsIgnoreCase((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
          //return false
          Map<String, String> smsInfo = new HashMap<String, String>();
          smsInfo.put("msg", "验证码错误/verification code error");
          smsInfo.put("status", "fail");
          
          return smsInfo;
      }
      
      // get mobileno from ldap by userid or badgeid
      String filter = MessageFormat.format(idCardAndIdFilter, userid, idcard);
      List<LdapUserEntity> ldapUserEntitys = userService.searchByFilter(filter);
      
      // 支持通过MatchCode来查询用户
      String cardMatchCode = "";
      if (ldapUserEntitys==null || ldapUserEntitys.size() == 0) {
          String cardUID = userid;
          try {
              cardMatchCode = matchCodeService.getMatchCode(cardUID);
              //cardMatchCode = "123456789012345678";
              
              filter = MessageFormat.format(idCardAndIdFilter, cardMatchCode, idcard);
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
          String mobileTime = ldapUserEntitys.get(0).getValueAsString(userMobileTimeAttribute);

          if(mobileTime==null) {
              mobile = ldapUserEntitys.get(0).getValueAsString(userMobileAttribute);
              displayName = ldapUserEntitys.get(0).getValueAsString(displayNameAttribute);
              username = ldapUserEntitys.get(0).getUid();
              // if null to display error info
              if (mobile == null || "".equals(mobile)) {
      
                  msg = String.format("user'mobile is null, filter:%s.", filter);
                  log.info(msg);
                  
                  msg = String.format("您的手机未绑定，请绑定后再试/Your phone is not binding, please try again after binding");
              }
              status = "success";
          } else {
              msg = "您非首次登陆注册，如需重置密码请访问'找回密码'功能，谢谢!";
          }
         
      }
      
      Map<String, String> userInfo = new HashMap<String, String>();
      userInfo.put("mobile", mobile);
      userInfo.put("username", username);
      userInfo.put("displayname", displayName);
      
      if(null!=cardMatchCode && !"".equals(cardMatchCode)) {
          userInfo.put("matchcode", cardMatchCode);
      }
      //smsInfo.put("dn", ldapUserEntitys.get(0).getDn());
      userInfo.put("msg", msg);
      userInfo.put("status", status);

      return userInfo;
  }
  
	/**
	 * 用户忘记密码后修改密码接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reg/change.do", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Map<String, String> changeLosePassword(HttpServletRequest request, HttpServletResponse response) {
		
		String msg = "";
		String status = "success";
		
		String userid = request.getParameter("j_useruid");
		String checkcode = request.getParameter("j_checkcode");
		String newPassword = request.getParameter("j_npassword");
	    String newMobile = request.getParameter("j_mobile");

		
		//check kaptcha code
		HttpSession session = request.getSession(false);
		if(null==session || null==checkcode || !checkcode.equalsIgnoreCase((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY))) {
			//return false
			Map<String, String> smsInfo = new HashMap<String, String>();
			smsInfo.put("msg", "超时,验证码错误/timeout and verification code error");
			smsInfo.put("status", "fail");
			
			return smsInfo;
		}

		//change password
		// 修改TIM口令（API）
	    try {
	        personService.updatePassword(userid, newPassword);
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
	    	
	    	status = "fail";
		}
	    
	    if(!"fail".equals(status)) {
	        try {
	            Map<String, String> attrs = new HashMap<String, String>();
	            attrs.put(mobileTag, newMobile);
	            
	            if(mobileUpdateTag!=null && !"".equals(mobileUpdateTag)) {
	                attrs.put(mobileUpdateTag, getDateNow());
	            }

	            personService.updatePerson(userid, attrs);
	            
	            doWebSealSSO(request, response, userid);
	            
	            //清理验证码
	            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
	            
	        } catch (Exception e) {
	            msg = String.format("update mobile Exception. username:%s", userid);
	            log.error(String.format(msg, userid), e);
	            
	            status = "fail";
	        }

	    }

		Map<String, String> smsInfo = new HashMap<String, String>();
		smsInfo.put("msg", msg);
		smsInfo.put("status", status);

		return smsInfo;

	}

    private String getDateNow() {
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);

        return df.format(now);
        // return "201308060308Z";
    }
    
    
    private void doWebSealSSO(HttpServletRequest request, HttpServletResponse response, String uid) {
        SSOPrincipalImpl principal = new SSOPrincipalImpl();
        principal.setUid(uid);
        //principal.setCn(uid);
        
        List<String> ls = new ArrayList<String>();
        ls.add(uid);
        principal.setAttribute("uid", ls);
        principal.addSuccessAuthenLevel("urn:oasis:names:tc:SAML:2.0:ac:classes:TAMUsernamePassword");
        
        HttpSession session = request.getSession(true);
        session.setAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR, principal);
        
        try {
            websealService.handle(request, response);
        } catch (ProfileException e) {
            e.printStackTrace();
        }
    }
}
