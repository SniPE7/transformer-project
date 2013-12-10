package com.sinopec.siam.am.idp.authn.module;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;

import com.sinopec.siam.am.idp.authn.module.CommonLdapAuthLoginModule.DnAndAttributes;
import com.sinopec.siam.am.idp.authn.principal.UserPrincipal;
import com.sinopec.siam.am.idp.authn.provider.RequestCallback;
import com.sinopec.siam.am.idp.authn.provider.ResponseCallback;

import edu.internet2.middleware.shibboleth.idp.util.DatatypeHelper;

/**
 * 人员手机号绑定提醒LoginModule（基本Ldap实现）
 * 
 * @author zhoutengfei
 * @since 2012-9-13 下午9:18:00
 */

public class UserMobileBindingLdapLoginModule extends AbstractSpringLoginModule {
	
  public static final SimpleDateFormat DATE_FORMAT_LAST_REMIND_TIME = new SimpleDateFormat("yyMMddHHmmssZ");

  public static final String ATTR_LAST_REMIND_TIME = "MOBILE_REG_LAST_REMIND_TIME";

  private static Log logger = LogFactory.getLog(UserMobileBindingLdapLoginModule.class);

	private static final String DATE_FORMAT = "yyyyMMddHHmm'Z'";

  /** 人员操作服务Bean ID */
  private String ldapTemplateBeanName = "tamLdapTemplate";
  
  /** 人员手机号有效期 (毫秒)*/
  private long personMobilePastDueTime = 2592000000L;
  
  /**
   * LDAP baseDN
   */
  private String baseDn = "";
  
  /**
   * Filter to find user
   */
  private String userFilter = "(&(uid={0})(objectclass=inetOrgPerson))";
  
  
  /**
   * LDAP mobileAttrName
   */
  private String mobileAttrName = "mobile";
  
  
  /**
   * LDAP mobileUpdateAttrName
   */
  private String mobileUpdateAttrName = "SGMMobileTime";
  

	/**
	 * 提醒的间隔时间，在此时间范围能，将不再重复提醒
	 */
	private int remindIntervalInSeconds = 3600 * 24;

	/** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    
    super.initialize(subject, callbackHandler, sharedState, options);

    if (logger.isTraceEnabled()) {
      logger.trace("Begin initialize");
    }
    this.subject = subject;
    this.callbackHandler = callbackHandler;
    this.sharedState = sharedState;

    final Iterator<String> i = options.keySet().iterator();
    while (i.hasNext()) {
      final String key = i.next();
      final String value = (String) options.get(key);
      
      if (key.equalsIgnoreCase("userFilter")) {
        this.userFilter = value;
      } else if (key.equalsIgnoreCase("ldapTemplateBeanName")) {
        this.ldapTemplateBeanName = value;
      } else if (key.equalsIgnoreCase("baseDn")) {
        this.baseDn = value;
      } else if (key.equalsIgnoreCase("mobileAttrName")) {
          this.mobileAttrName = value;
      } else if (key.equalsIgnoreCase("mobileUpdateAttrName")) {
            this.mobileUpdateAttrName = value;
      } else if (key.equalsIgnoreCase("personMobilePastDueTime")) {
	        this.personMobilePastDueTime = Long.parseLong(value);
	  } 
    }

    if (options.get("remindIntervalInSeconds") != null) {
      this.remindIntervalInSeconds = Integer.valueOf((String) options.get("remindIntervalInSeconds"));
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  public boolean login() throws LoginException, PasswordReminderLoginException {
    if (callbackHandler == null) {
      log.error("No CallbackHandler");
      return false;
    }

    NameCallback nameCallback = new NameCallback("User Name: ");
    MobileUpdateOperationStatusCallback mobileUpdateCallback = new MobileUpdateOperationStatusCallback();
    RequestCallback httpRequestCallback = new RequestCallback();
    ResponseCallback httpResponseCallback = new ResponseCallback();

    Callback[] callbacks = new Callback[] {nameCallback, mobileUpdateCallback, httpRequestCallback, httpResponseCallback};
    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    } catch (UnsupportedCallbackException e) {
      log.error(e.getMessage(), e);
      return false;
    }

    String username = (String)sharedState.get(LOGIN_NAME);
    if (username == null || "".equals(username)) {
      throw new DetailLoginException("login.form.error.username.isNull", String.format("username is null; usernsme:%s.", username));
    }
    
    if(mobileUpdateCallback.isMobileUpdated() || !needToRemindBaseLastStatus(httpRequestCallback)){
      // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
      UserPrincipal principal = new UserPrincipal();
      principal.setName(username);

      principals.add(principal);
      authenticated = true;
      return true;
    }
    
    Date mobileLastDate = null;
    String mobile = null;
    
    DnAndAttributes dnAndAttrs = this.searchUserDNByAccount(username);
    if (dnAndAttrs == null){
  	  log.warn(String.format("No authentication, username not found. username:%s ", username));
	      throw new LoginException(String.format("No authentication, username not found. username:%s ", username));
    } else {  	  
		try {
			if(null!=dnAndAttrs.getAttributes().get(mobileAttrName)) {
				mobile = (String)dnAndAttrs.getAttributes().get(mobileAttrName).get(0);
			}
			
	    	if(checkMobileUpdateTime()) {
				Attribute mobileUpdateAttr = dnAndAttrs.getAttributes().get(mobileUpdateAttrName);
				if (mobileUpdateAttr != null) {
				   mobileLastDate = convertPwdChangeTime((String)mobileUpdateAttr.get(0));
				}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    if(null==mobile || "".equals(mobile)) {
    	//throw new UserMobileBindingLoginException((String)sharedState.get(LOGIN_NAME), String.format("[%s]'s mobile not set, need to set mobile.", (String)sharedState.get(LOGIN_NAME)));
      // Set remind time to Cookie
      
        //Cookie cookie = new Cookie(ATTR_LAST_REMIND_TIME, DATE_FORMAT_LAST_REMIND_TIME.format(new Date()));
        //cookie.setMaxAge(this.remindIntervalInSeconds * 2);
	    //httpResponseCallback.getResponse().addCookie(cookie);
    	throw new UserMobileBindingLoginException(username, "regmobile.info.remind.no", "",  String.format("[%s]'s mobile have not bind, need to bind mobile.", username));
    }
    
    try {
        //mask the mobile
        mobile = mobile.substring(0, 3) + "-XXXX-" + mobile.substring(8);
    } catch(Exception err) {
    }
    
    
    if(checkMobileUpdateTime()) {
    	if(mobileLastDate==null) {
  	      //throw new UserMobileBindingLoginException((String)sharedState.get(LOGIN_NAME), String.format("[%s]'s mobile expired, need to change mobile.", (String)sharedState.get(LOGIN_NAME)));
        Cookie cookie = new Cookie(ATTR_LAST_REMIND_TIME, DATE_FORMAT_LAST_REMIND_TIME.format(new Date()));
        cookie.setMaxAge(this.remindIntervalInSeconds * 2);
  			httpResponseCallback.getResponse().addCookie(cookie);
    		throw new UserMobileBindingLoginException(username, "regmobile.info.remind.expire",(String) mobile,  String.format("[%s]'s mobile expired, need to change mobile.", username));
    	}
    	
	    mobileLastDate.setTime(mobileLastDate.getTime() + personMobilePastDueTime);
	    if (mobileLastDate.compareTo(new Date()) <= 0) {
	      //throw new UserMobileBindingLoginException((String)sharedState.get(LOGIN_NAME), String.format("[%s]'s mobile expired, need to change mobile.", (String)sharedState.get(LOGIN_NAME)));
	      Cookie cookie = new Cookie(ATTR_LAST_REMIND_TIME, DATE_FORMAT_LAST_REMIND_TIME.format(new Date()));
	      cookie.setMaxAge(this.remindIntervalInSeconds * 2);
				httpResponseCallback.getResponse().addCookie(cookie);
    		throw new UserMobileBindingLoginException(username, "regmobile.info.remind.expire",(String) mobile,  String.format("[%s]'s mobile expired, need to change mobile.", username));
	    }
    }
    //mobileLastDate.setTime(mobileLastDate.getTime() - personMobilePastDueTime);
    
    
    // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
    UserPrincipal principal = new UserPrincipal();
    principal.setName(username);

    principals.add(principal);
    authenticated = true;
    return true;
  }
  
  /**
   * Get LDAP dn
   * 
   * @param userName
   * @return
   */
  private DnAndAttributes searchUserDNByAccount(String userName) {
    String filter = MessageFormat.format(this.userFilter, userName);

    try {
      if (logger.isDebugEnabled()) {
        logger.debug(String.format("Search user DN by filter [%s], base [%s]", filter, baseDn));
      }
      SearchControls controls = new SearchControls();
      controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
      
      List<DnAndAttributes> result = (List<DnAndAttributes>)this.getLdapTemplate().search(baseDn, filter, controls, new AbstractContextMapper() {

        @Override
        protected Object doMapFromContext(DirContextOperations ctx) {
          String dn = ctx.getNameInNamespace();
          Attributes attrs = ctx.getAttributes();
          return new DnAndAttributes(dn, attrs);
        }
      });
      if (result.size() == 1) {
         return result.get(0);
      } else {
        return null;
      }
    } catch (EmptyResultDataAccessException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(String.format("Search user DN exception", e.getMessage()));
      }
      return null;
    }
  }
  
  protected Date convertPwdChangeTime(String dateTimeStr) {
	    // Date sample: 201208170328Z
	    SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
	    try {
	      String s = dateTimeStr;
	      if (s.endsWith(".0Z")) {
	        s = s.substring(0, s.length() - 3);
	        //s += "-0000";
	        s += "Z";
	      }
	      return df.parse(s);
	    } catch (ParseException e) {
	      log.warn(String.format("Failure to parse date string: [%s], pattern: [%s]", dateTimeStr, DATE_FORMAT), e);
	      return null;
	    }
	  }

  /**
   * @return
   */
  private LdapTemplate getLdapTemplate() {
    return (LdapTemplate) this.applicationContext.getBean(ldapTemplateBeanName, LdapTemplate.class);
  }
  
  private boolean checkMobileUpdateTime() {
	  if(mobileUpdateAttrName!=null && !"".equals(mobileUpdateAttrName)) {
		  return true;
	  }
	  
	  return false;
  }

	/**
	 * 提取是否已经提醒过用户，并据此时间来计算是否此次还需要提醒
	 * @param httpRequestCallback
	 * @return
	 */
	private boolean needToRemindBaseLastStatus(RequestCallback httpRequestCallback) {
	  Cookie lastRemindTimeCookie = getCookie(httpRequestCallback.getRequest(), ATTR_LAST_REMIND_TIME);
    if (lastRemindTimeCookie != null && lastRemindTimeCookie.getValue() != null) {
    	String v = lastRemindTimeCookie.getValue();
    	Date lastRemindTime = null;
    	try {
	      lastRemindTime = DATE_FORMAT_LAST_REMIND_TIME.parse(v);
      } catch (ParseException e) {
      	log.debug("failure to parse lastRemindTime: " + v);
      }
    	if (lastRemindTime != null && System.currentTimeMillis() - lastRemindTime.getTime() < remindIntervalInSeconds  * 1000) {
    		return false;
    	}
    }
    return true;
  }

  /**
   * Gets the first {@link Cookie} whose name matches the given name.
   * 
   * @param cookieName the cookie name
   * @param httpRequest HTTP request from which the cookie should be extracted
   * 
   * @return the cookie or null if no cookie with that name was given
   */
  private static Cookie getCookie(HttpServletRequest httpRequest, String cookieName) {
      Cookie[] requestCookies = httpRequest.getCookies();
      if (requestCookies != null) {
          for (Cookie requestCookie : requestCookies) {
              if (requestCookie != null && DatatypeHelper.safeEquals(requestCookie.getName(), cookieName)) {
                  return requestCookie;
              }
          }
      }
      return null;
  }
}
