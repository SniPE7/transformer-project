package com.ibm.siam.am.idp.authn.module;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.opensaml.xml.util.DatatypeHelper;

import com.sinopec.siam.am.idp.authn.module.AbstractLoginModule;
import com.sinopec.siam.am.idp.authn.module.AbstractSpringLoginModule;
import com.sinopec.siam.am.idp.authn.module.DetailLoginException;
import com.sinopec.siam.am.idp.authn.module.PasswordReminderLoginException;
import com.sinopec.siam.am.idp.authn.principal.UserAccountPrincipal;
import com.sinopec.siam.am.idp.authn.principal.UserPrincipal;
import com.sinopec.siam.am.idp.authn.provider.PasswordUpdateOperationStatusCallback;
import com.sinopec.siam.am.idp.authn.provider.RequestCallback;
import com.sinopec.siam.am.idp.authn.provider.ResponseCallback;
import com.sinopec.siam.am.idp.authn.service.UserPassService;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;

/**
 * �����û����뼴������LoginModule������Ldapʵ�֣�
 * 
 * @author zhoutengfei
 * @since 2012-9-13 ����9:18:00
 */

public class UserPassRemindLdapLoginModule extends AbstractSpringLoginModule {

  public static final SimpleDateFormat DATE_FORMAT_LAST_REMIND_TIME = new SimpleDateFormat("yyMMddHHmmssZ");

  public static final String ATTR_PWD_LAST_REMIND_TIME = "PWD_LAST_REMIND_TIME";

	/** �û��������Bean ID */
  private String userPassServiceBeanId = null;
  
  /** �û�������Ч�� (����)*/
  private long userPassPastDueTime = 2592000000L;
  
  /** �����������ʱ�䣨���룩 */
  private long remindPasswordTime = 25920000L;

	/**
	 * ���ѵļ��ʱ�䣬�ڴ�ʱ�䷶Χ�ܣ��������ظ�����
	 */
	private int remindIntervalInSeconds = 3600 * 24;

  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    this.userPassServiceBeanId = (String) options.get("userPassServiceBeanId");
    if (this.userPassServiceBeanId == null || "".equals(this.userPassServiceBeanId)) {
      throw new RuntimeException("userPassServiceBeanId is null");
    }

    if (options.get("userPassPastDueTime") != null) {
      this.userPassPastDueTime = Long.valueOf((String) options.get("userPassPastDueTime"));
    }

    if (options.get("remindPasswordTime") != null) {
      this.remindPasswordTime = Long.valueOf((String) options.get("remindPasswordTime"));
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
    PasswordUpdateOperationStatusCallback pwdUpdateCallback = new PasswordUpdateOperationStatusCallback();
    RequestCallback httpRequestCallback = new RequestCallback();
    ResponseCallback httpResponseCallback = new ResponseCallback();
    
    Callback[] callbacks = new Callback[] {nameCallback, pwdUpdateCallback, httpRequestCallback, httpResponseCallback};
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
    
    //modify by xuhong for !, if code is not need remind
    if(pwdUpdateCallback.isPasswordUpdated() || !needToRemindBaseLastStatus(httpRequestCallback)){
      // �û�У�飬ͨ��LADP��ѯ������Աuid��Ϣ���洢Subject
      UserPrincipal principal = new UserPrincipal();
      principal.setName(username);

      principals.add(principal);
      authenticated = true;
      return true;
    }
    
    Date userPassLastDate = null;
    boolean enablePolicy = true;

    UserAccountPrincipal userAccountPrincipal = (UserAccountPrincipal) sharedState.get(AbstractLoginModule.LOGIN_USER_ACCOUNT_PRINCIPAL);
    UserPassService userPassService = getUserPassService();
    try {
      if (userAccountPrincipal == null) {
        LdapUserEntity ldapUserEntity  = userPassService.getLdapUserEntityByUsername(username);
        userAccountPrincipal = new UserAccountPrincipal(username);
        userAccountPrincipal.setPassRecoveryQuestion(userPassService.getPassRecoveryQuestion(ldapUserEntity));
        userAccountPrincipal.setPassLastChanged(userPassService.getPassLastChanged(ldapUserEntity));
        userAccountPrincipal.setPassResetState(userPassService.getPassResetState(ldapUserEntity));
        
        // TAM LDAP ���ܽ������û��Ŀ������У��, ��ȡ��־
        String hasPolicy = ldapUserEntity.getValueAsString("secHasPolicy");
        if (hasPolicy != null && hasPolicy.equalsIgnoreCase("FALSE")) {
            enablePolicy = false;
        }
        
        sharedState.put(AbstractLoginModule.LOGIN_USER_ACCOUNT_PRINCIPAL, userAccountPrincipal);
      }
      userPassLastDate = userAccountPrincipal.getPassLastChanged();
    } catch (Exception e) {
      log.error(String.format("Get user pass last changed exception. username:%s", username), e);
      throw new DetailLoginException("login.form.error.userpass.lastChangedErr", String.format("Get user pass last changed exception:%s. username:%s", e.getMessage(),
          username));
    }
    
    userPassLastDate.setTime(userPassLastDate.getTime());
    Date nextPasswordExpiredTime = new Date(userPassLastDate.getTime() + userPassPastDueTime);
    long milliseconds = nextPasswordExpiredTime.getTime() - new Date().getTime();
    if(enablePolicy && milliseconds > 0 && milliseconds < remindPasswordTime){
      int days = (int) milliseconds / 86400000 + 1;
      PasswordReminderLoginException e = new PasswordReminderLoginException("modifyPass.info.userpass.remind", String.valueOf(days), String.format("[%s]'s password will expire after %s day(s)", username, days));
      e.setUsername(username);
      // Set remind time to Cookie
      Cookie cookie = new Cookie(ATTR_PWD_LAST_REMIND_TIME, DATE_FORMAT_LAST_REMIND_TIME.format(new Date()));
      cookie.setMaxAge(this.remindIntervalInSeconds * 2);
			httpResponseCallback.getResponse().addCookie(cookie);
      throw e;
    }
    
    // �û�У�飬ͨ��LADP��ѯ������Աuid��Ϣ���洢Subject
    UserPrincipal principal = new UserPrincipal();
    principal.setName(username);

    principals.add(principal);
    authenticated = true;
    return true;
  }

	/**
	 * ��ȡ�Ƿ��Ѿ����ѹ��û������ݴ�ʱ���������Ƿ�˴λ���Ҫ����
	 * @param httpRequestCallback
	 * @return
	 */
	private boolean needToRemindBaseLastStatus(RequestCallback httpRequestCallback) {
	  Cookie lastRemindTimeCookie = getCookie(httpRequestCallback.getRequest(), ATTR_PWD_LAST_REMIND_TIME);
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
  /**
   * ��ȡ�û��������Bean
   * 
   * @return UserPassService
   */
  private UserPassService getUserPassService() {
    return this.applicationContext.getBean(this.userPassServiceBeanId, UserPassService.class);
  }

}
