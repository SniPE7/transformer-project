package com.sinopec.siam.am.idp.authn.module;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;

import com.sinopec.siam.am.idp.authn.module.CommonLdapAuthLoginModule.DnAndAttributes;
import com.sinopec.siam.am.idp.authn.principal.UserPrincipal;

/**
 * 人员手机号绑定提醒LoginModule（基本Ldap实现）
 * 
 * @author zhoutengfei
 * @since 2012-9-13 下午9:18:00
 */

public class UserMobileBindingLdapLoginModule extends AbstractSpringLoginModule {
	
	private static Log logger = LogFactory.getLog(UserMobileBindingLdapLoginModule.class);
	private static final String DATE_FORMAT = "yyyyMMddHHmmssZ";

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

    Callback[] callbacks = new Callback[] {nameCallback, mobileUpdateCallback};
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
    
    if(mobileUpdateCallback.isMobileUpdated()){
      // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
      UserPrincipal principal = new UserPrincipal();
      principal.setName(username);

      principals.add(principal);
      authenticated = true;
      return true;
    }
    
    Date mobileLastDate = null;
    Object mobile = null;
    
    DnAndAttributes dnAndAttrs = this.searchUserDNByAccount(username);
    if (dnAndAttrs == null){
  	  log.warn(String.format("No authentication, username not found. username:%s ", username));
	      throw new LoginException(String.format("No authentication, username not found. username:%s ", username));
    } else {  	  
		try {
			mobile = dnAndAttrs.getAttributes().get(mobileAttrName).get(0);
			Attribute mobileUpdateAttr = dnAndAttrs.getAttributes().get(mobileUpdateAttrName);
			if (mobileUpdateAttr != null) {
			   mobileLastDate = convertPwdChangeTime((String)mobileUpdateAttr.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    if(null==mobile || "".equals(mobile) || null==mobileLastDate) {
    	throw new UserMobileBindingLoginException((String)sharedState.get(LOGIN_NAME), String.format("[%s]'s mobile not set, need to set mobile.", (String)sharedState.get(LOGIN_NAME)));
    }
    
    mobileLastDate.setTime(mobileLastDate.getTime() + personMobilePastDueTime);
    if (mobileLastDate.compareTo(new Date()) <= 0) {
      throw new UserMobileBindingLoginException((String)sharedState.get(LOGIN_NAME), String.format("[%s]'s mobile expired, need to change mobile.", (String)sharedState.get(LOGIN_NAME)));
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
	        s += "-0000";
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

}
