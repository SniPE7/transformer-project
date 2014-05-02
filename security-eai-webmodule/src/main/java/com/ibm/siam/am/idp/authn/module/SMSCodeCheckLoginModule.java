package com.ibm.siam.am.idp.authn.module;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import com.ibm.siam.am.idp.authn.principal.UserPrincipal;
import com.ibm.siam.am.idp.authn.provider.FormOperationCallback;
import com.ibm.siam.am.idp.authn.provider.SMSCodeCallback;
import com.ibm.siam.am.idp.authn.service.PersonService;
import com.ibm.siam.am.idp.authn.util.dyncpwd.DyncUtil;

/**
 * 短信码校验LoginModule
 * <p>
 * 对用户输入的短信码和算法产生的短信码进行比较，如果不一致则登录失败
 * </p>
 * 
 * @author zhoutengfei
 * @since 2012-8-18 上午11:53:08
 */

public class SMSCodeCheckLoginModule extends AbstractSpringLoginModule {

  /** 登录操作的标识 */
  private String loginOptFlag = "login";

  /** 万能短信验证码 */
  private String powerfulSMSCode = "null";
  
  private boolean checkMatchCode = false;
  
  private String personServiceBeanName = null;

  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    final Iterator<String> i = options.keySet().iterator();
    while (i.hasNext()) {
      final String key = i.next();
      final String value = (String) options.get(key);
      if (key.equalsIgnoreCase("powerfulSMSCode")) {
        this.powerfulSMSCode = value;
      } else if (key.equalsIgnoreCase("loginOptFlag")) {
    	  this.loginOptFlag = value;
      } else if (key.equalsIgnoreCase("checkMatchCode")) {
        this.checkMatchCode = Boolean.valueOf(value);
      }	else if (key.equalsIgnoreCase("personServiceBeanName")) {
			this.personServiceBeanName = value;
	  }
    }
  }

  /** {@inheritDoc} */
  /* (non-Javadoc)
   * @see javax.security.auth.spi.LoginModule#login()
   */
  public boolean login() throws LoginException {
    if (callbackHandler == null) {
      log.error("No CallbackHandler");
      return false;
    }

    NameCallback nameCallback = new NameCallback("User Name: ");
    SMSCodeCallback smsCodeCallback = new SMSCodeCallback();
    FormOperationCallback formOperationCallback = new FormOperationCallback(this.loginOptFlag);

    Callback[] callbacks = new Callback[] { nameCallback, smsCodeCallback, formOperationCallback };
    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    } catch (UnsupportedCallbackException e) {
      log.error(e.getMessage(), e);
      return false;
    }
    
    String userid = nameCallback.getName();

    String smscode = smsCodeCallback.getCodeFromInput();
    String smsCodeCache = smsCodeCallback.getSMSCode();
    
    
    log.debug(String.format("verifySMSCode smsCode:%s, smsCodeCache:%s.", smscode, smsCodeCache));
    String opt = formOperationCallback.getOperation();

    if (opt != null && !loginOptFlag.equals(opt) && smscode == null) {
    	smscode = smsCodeCache;
    }

    if (smscode == null || "".equals(smscode)) {
      throw new DetailLoginException("login.form.error.smsCodeIsNull", String.format("Could not get smscode from user request, missing sms code"));
    }
    if (smsCodeCache == null || "".equals(smsCodeCache)) {
      throw new DetailLoginException("login.form.error.smsCodeCacheIsNull", String.format("Could not get pre-generated-smscode from user session, missing pre-generated-smscode."));
    }
    
    /*if (!smsCodeCache.equals(smscode) && !(powerfulSMSCode != null && smscode.equals(powerfulSMSCode))) {
        throw new DetailLoginException("login.form.error.smsCodeFailed", String.format("SMScode not match, pre-gen-code: [%s], user-code: [%s], powerful-code: [%s]", smsCodeCache, smscode, powerfulSMSCode));
      }*/

    if (!DyncUtil.checkPwd(userid, smscode) && !(powerfulSMSCode != null && smscode.equals(powerfulSMSCode))) {
      throw new DetailLoginException("login.form.error.smsCodeFailed", String.format("SMScode not match, pre-gen-code: [%s], user-code: [%s], powerful-code: [%s]", smsCodeCache, smscode, powerfulSMSCode));
    }
    

    //处理matchcode逻辑
    if(checkMatchCode) {
	    String matchCodeCache = smsCodeCallback.getMatchCode();
	    if(matchCodeCache!=null && !"".equals(matchCodeCache)) {
	    	
	      // Update shareState for LoginStateSetLoginModule
		  this.sharedState.put(SGMMatchCodeAuthLoginModule.LOGIN_MATCHCODE, matchCodeCache);
	      
	     // Success, and update cardUID into person entry
	  	 /* PersonService personService = this.getPersonService();
	  		Map<String, String> attrs = new HashMap<String, String>();
	  		attrs.put("sgmBadgeId", this.cardUID);
	  	  try {
	  	    personService.updatePerson(this.uid, attrs);
	      } catch (PersonNotFoundException e) {
	  	    log.warn(String.format("Failure to update sgmBadgeId[%s] for uid[%s]", this.cardUID, this.uid));
	      } catch (MultiplePersonFoundException e) {
	  	    log.warn(String.format("Failure to update sgmBadgeId[%s] for uid[%s]", this.cardUID, this.uid));
	      } catch (PersonServiceException e) {
	  	    log.warn(String.format("Failure to update sgmBadgeId[%s] for uid[%s]", this.cardUID, this.uid));
	      }*/
	  	  
	    }
	    
	    //this.setSessionLevelState(AbstractSpringLoginModule.PRINCIPAL_PASSWORD_KEY, "xxx".toCharArray());
	    this.sharedState.put(LOGIN_PASSWORD, LOGIN_PASSWORD.toCharArray());
    }

    
    this.setSessionLevelState(LoginStateSetLoginModule.LOGIN_AUTH_SMS_OK, "true");

    // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
    UserPrincipal principal = new UserPrincipal();
    principal.setName(nameCallback.getName());

    principals.add(principal);
    authenticated = true;
    return true;
  }
  
  /**
	 * @return
	 */
	protected PersonService getPersonService() {
		return (PersonService) this.applicationContext.getBean(personServiceBeanName, PersonService.class);
	}

}
