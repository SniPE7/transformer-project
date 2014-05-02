/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.siam.am.idp.authn.module.AbstractSpringLoginModule;

/**
 * @author zhaodonglu
 * 
 */
public class LoginContextBasedLoginModule extends AbstractSpringLoginModule {
  private static Log log = LogFactory.getLog(LoginContextBasedLoginModule.class);

  private String jaasConfigName = null;
  private String loginContextManagerBeanName = null;

  /**
   * 
   */
  public LoginContextBasedLoginModule() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * javax.security.auth.spi.LoginModule#initialize(javax.security.auth.Subject,
   * javax.security.auth.callback.CallbackHandler, java.util.Map, java.util.Map)
   */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);
    this.jaasConfigName = (String) options.get("jaasConfigName");
    this.loginContextManagerBeanName = (String) options.get("loginContextManagerBeanName");
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.security.auth.spi.LoginModule#login()
   */
  public boolean login() throws LoginException {
    LoginContextManager loginContextManager = this.applicationContext.getBean(loginContextManagerBeanName, LoginContextManager.class);
    LoginContext jaasLoginCtx = loginContextManager.getLoginContext(jaasConfigName, this.subject, this.callbackHandler, this.sharedState);
    try {
      jaasLoginCtx.login();
      return true;
    } catch (LoginException e) {
      throw e;
    } catch (Exception e) {
      log.error(String.format("failure to login, cause: %s", e.getMessage()), e);
      throw new LoginException(e.getMessage());
    }
  }
}
