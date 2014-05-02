/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhaodonglu
 * 
 */
public class SpringBasedLoginContextManager implements LoginContextManager, ApplicationContextAware, LoginModuleEventListenerAware, LoginContextEventListenerAware {

  private static Log log = LogFactory.getLog(SpringBasedLoginContextManager.class);

  private Configuration configuration = null;

  private String loginContextClassName = AuditableSpringLoginContext.class.getName();

  /**
   * Spring Bean Factory
   */
  private ApplicationContext applicationContext = null;

  /**
   * LoginModuleEventListener
   */
  private LoginModuleEventListener loginModuleEventListener = null;

  /**
   * LoginContextEventListener
   */
  private LoginContextEventListener loginContextEventListener = null;

  /**
   * 
   */
  public SpringBasedLoginContextManager() {

  }

  public SpringBasedLoginContextManager(Configuration configuration) {
    super();
    this.configuration = configuration;
  }

  public Configuration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  public String getLoginContextClassName() {
    return loginContextClassName;
  }

  public void setLoginContextClassName(String loginContextClassName) {
    this.loginContextClassName = loginContextClassName;
  }

  public void setLoginModuleEventListener(LoginModuleEventListener loginModuleEventListener) {
    this.loginModuleEventListener = loginModuleEventListener;
  }

  public void setLoginContextEventListener(LoginContextEventListener loginContextEventListener) {
    this.loginContextEventListener = loginContextEventListener;
  }

  private Class<? extends LoginContext> loadLoginContextClass() throws ClassNotFoundException {
    Class<? extends LoginContext> clazz = (Class<? extends LoginContext>) Class.forName(this.loginContextClassName);
    return clazz;
  }

  private LoginContext newLoginContextInstance(String loginModuleName, Subject subject, CallbackHandler callbackHandler, Configuration config) throws ClassNotFoundException,
      NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    Class<? extends LoginContext> clazz = loadLoginContextClass();
    Constructor<? extends LoginContext> constructor = clazz.getConstructor(String.class, Subject.class, CallbackHandler.class, Configuration.class);
    LoginContext lc = constructor.newInstance(loginModuleName, subject, callbackHandler, config);
    return lc;
  }

  private LoginContext newLoginContextInstance(String loginModuleName, Subject subject, CallbackHandler callbackHandler, Map<?, ?> state) throws ClassNotFoundException,
      NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    Class<? extends LoginContext> clazz = loadLoginContextClass();
    Constructor<? extends LoginContext> constructor = clazz.getConstructor(String.class, Subject.class, CallbackHandler.class, Configuration.class, Map.class);
    LoginContext lc = constructor.newInstance(loginModuleName, subject, callbackHandler, this.configuration, state);
    return lc;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.siam.am.idp.authn.LoginContextManager#getLoginContext(java.lang
   * .String, javax.security.auth.Subject,
   * javax.security.auth.callback.CallbackHandler)
   */
  public LoginContext getLoginContext(String loginModuleName, Subject subject, CallbackHandler callbackHandler, Map<?, ?> state) throws LoginException {
    if (log.isDebugEnabled()) {
      log.debug(String.format(" Get login context begin.loginModuleName:%s", loginModuleName));
    }
    try {
      LoginContext lc = newLoginContextInstance(loginModuleName, subject, callbackHandler, state);
      if (lc instanceof ApplicationContextAware) {
        ((ApplicationContextAware) lc).setApplicationContext(this.applicationContext);
      }
      if (lc instanceof LoginContextEventListenerAware) {
        ((LoginContextEventListenerAware) lc).setLoginContextEventListener(this.loginContextEventListener);
      }
      if (lc instanceof LoginModuleEventListenerAware) {
        ((LoginModuleEventListenerAware) lc).setLoginModuleEventListener(this.loginModuleEventListener);
      }
      return lc;
    } catch (ClassNotFoundException e) {
      log.error(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()), e);
      throw new LoginException(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()));
    } catch (NoSuchMethodException e) {
      log.error(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()), e);
      throw new LoginException(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()));
    } catch (InstantiationException e) {
      log.error(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()), e);
      throw new LoginException(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()));
    } catch (IllegalAccessException e) {
      log.error(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()), e);
      throw new LoginException(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()));
    } catch (InvocationTargetException e) {
      log.error(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()), e);
      throw new LoginException(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()));
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.siam.am.idp.authn.LoginContextManager#getLoginContext(java.
   * lang.String, javax.security.auth.Subject,
   * javax.security.auth.callback.CallbackHandler)
   */
  public LoginContext getLoginContext(String loginModuleName, Subject subject, CallbackHandler callbackHandler) throws LoginException {
    if (log.isDebugEnabled()) {
      log.debug(String.format(" Get login context begin.loginModuleName:%s", loginModuleName));
    }
    try {
      LoginContext lc = newLoginContextInstance(loginModuleName, subject, callbackHandler, this.configuration);
      // LoginContext lc = new SpringLoginContextAddAudit(loginModuleName,
      // subject, callbackHandler, this.configuration);
      if (lc instanceof ApplicationContextAware) {
        ((ApplicationContextAware) lc).setApplicationContext(this.applicationContext);
      }
      if (lc instanceof LoginContextEventListenerAware) {
        ((LoginContextEventListenerAware) lc).setLoginContextEventListener(this.loginContextEventListener);
      }
      if (lc instanceof LoginModuleEventListenerAware) {
        ((LoginModuleEventListenerAware) lc).setLoginModuleEventListener(this.loginModuleEventListener);
      }
      return lc;
    } catch (ClassNotFoundException e) {
      log.error(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()), e);
      throw new LoginException(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()));
    } catch (NoSuchMethodException e) {
      log.error(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()), e);
      throw new LoginException(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()));
    } catch (InstantiationException e) {
      log.error(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()), e);
      throw new LoginException(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()));
    } catch (IllegalAccessException e) {
      log.error(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()), e);
      throw new LoginException(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()));
    } catch (InvocationTargetException e) {
      log.error(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()), e);
      throw new LoginException(String.format("failure to instance LoginContext [%s], cause: %s", this.loginContextClassName, e.getMessage()));
    }
  }
}
