package com.ibm.siam.am.idp.authn;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessControlContext;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.AuthPermission;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 */

public class AuditableSpringLoginContext extends LoginContext implements ApplicationContextAware, LoginModuleEventListenerAware, LoginContextEventListenerAware {

  private static Logger log = LoggerFactory.getLogger(AuditableSpringLoginContext.class);

  private static final String INIT_METHOD = "initialize";
  private static final String LOGIN_METHOD = "login";
  private static final String COMMIT_METHOD = "commit";
  private static final String ABORT_METHOD = "abort";
  private static final String LOGOUT_METHOD = "logout";
  private static final String OTHER = "other";
  private static final String DEFAULT_HANDLER = "auth.login.defaultCallbackHandler";

  private Subject subject = null;
  private boolean subjectProvided = false;
  private boolean loginSucceeded = false;
  private CallbackHandler callbackHandler;
  private Map state = new HashMap();
  private Configuration config;
  private boolean configProvided = false;
  private AccessControlContext creatorAcc = null;
  private ModuleInfo[] moduleStack;
  private ClassLoader contextClassLoader = null;
  private static final Class[] PARAMS = {};
  private int moduleIndex = 0;
  private LoginException firstError = null;
  private LoginException firstRequiredError = null;
  private boolean success = false;
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
   * Name of login context
   */
  private String loginContextName = null;

  /**
   * Instantiate a new <code>LoginContext</code> object with a name.
   * 
   * @param name
   *          the name used as the index into the <code>Configuration</code>.
   * 
   * @exception LoginException
   *              if the caller-specified <code>name</code> does not appear in
   *              the <code>Configuration</code> and there is no
   *              <code>Configuration</code> entry for "<i>other</i>", or if the
   *              <i>auth.login.defaultCallbackHandler</i> security property was
   *              set, but the implementation class could not be loaded.
   *              <p>
   * @exception SecurityException
   *              if a SecurityManager is set and the caller does not have
   *              AuthPermission("createLoginContext.<i>name</i>"), or if a
   *              configuration entry for <i>name</i> does not exist and the
   *              caller does not additionally have
   *              AuthPermission("createLoginContext.other")
   */
  public AuditableSpringLoginContext(String name) throws LoginException {
    super(name);
    init(name);
    loadDefaultCallbackHandler();
  }

  /**
   * Instantiate a new <code>LoginContext</code> object with a name and a
   * <code>Subject</code> object.
   * 
   * <p>
   * 
   * @param name
   *          the name used as the index into the <code>Configuration</code>.
   *          <p>
   * 
   * @param subject
   *          the <code>Subject</code> to authenticate.
   * 
   * @exception LoginException
   *              if the caller-specified <code>name</code> does not appear in
   *              the <code>Configuration</code> and there is no
   *              <code>Configuration</code> entry for "<i>other</i>", if the
   *              caller-specified <code>subject</code> is <code>null</code>, or
   *              if the <i>auth.login.defaultCallbackHandler</i> security
   *              property was set, but the implementation class could not be
   *              loaded.
   *              <p>
   * @exception SecurityException
   *              if a SecurityManager is set and the caller does not have
   *              AuthPermission("createLoginContext.<i>name</i>"), or if a
   *              configuration entry for <i>name</i> does not exist and the
   *              caller does not additionally have
   *              AuthPermission("createLoginContext.other")
   */
  public AuditableSpringLoginContext(String name, Subject subject) throws LoginException {
    super(name, subject);
    init(name);
    if (subject == null)
      throw new LoginException("invalid null Subject provided");
    this.subject = subject;
    subjectProvided = true;
    loadDefaultCallbackHandler();
  }

  /**
   * Instantiate a new <code>LoginContext</code> object with a name and a
   * <code>CallbackHandler</code> object.
   * 
   * <p>
   * 
   * @param name
   *          the name used as the index into the <code>Configuration</code>.
   *          <p>
   * 
   * @param callbackHandler
   *          the <code>CallbackHandler</code> object used by LoginModules to
   *          communicate with the user.
   * 
   * @exception LoginException
   *              if the caller-specified <code>name</code> does not appear in
   *              the <code>Configuration</code> and there is no
   *              <code>Configuration</code> entry for "<i>other</i>", or if the
   *              caller-specified <code>callbackHandler</code> is
   *              <code>null</code>.
   *              <p>
   * @exception SecurityException
   *              if a SecurityManager is set and the caller does not have
   *              AuthPermission("createLoginContext.<i>name</i>"), or if a
   *              configuration entry for <i>name</i> does not exist and the
   *              caller does not additionally have
   *              AuthPermission("createLoginContext.other")
   */
  public AuditableSpringLoginContext(String name, CallbackHandler callbackHandler) throws LoginException {
    super(name, callbackHandler);
    init(name);
    if (callbackHandler == null)
      throw new LoginException("invalid null CallbackHandler provided");
    this.callbackHandler = new SecureCallbackHandler(java.security.AccessController.getContext(), callbackHandler);
  }

  /**
   * Instantiate a new <code>LoginContext</code> object with a name, a
   * <code>Subject</code> to be authenticated, and a
   * <code>CallbackHandler</code> object.
   * 
   * <p>
   * 
   * @param name
   *          the name used as the index into the <code>Configuration</code>.
   *          <p>
   * 
   * @param subject
   *          the <code>Subject</code> to authenticate.
   *          <p>
   * 
   * @param callbackHandler
   *          the <code>CallbackHandler</code> object used by LoginModules to
   *          communicate with the user.
   * 
   * @exception LoginException
   *              if the caller-specified <code>name</code> does not appear in
   *              the <code>Configuration</code> and there is no
   *              <code>Configuration</code> entry for "<i>other</i>", or if the
   *              caller-specified <code>subject</code> is <code>null</code>, or
   *              if the caller-specified <code>callbackHandler</code> is
   *              <code>null</code>.
   *              <p>
   * @exception SecurityException
   *              if a SecurityManager is set and the caller does not have
   *              AuthPermission("createLoginContext.<i>name</i>"), or if a
   *              configuration entry for <i>name</i> does not exist and the
   *              caller does not additionally have
   *              AuthPermission("createLoginContext.other")
   */
  public AuditableSpringLoginContext(String name, Subject subject, CallbackHandler callbackHandler) throws LoginException {
    this(name, subject);
    if (callbackHandler == null)
      throw new LoginException("invalid null CallbackHandler provided");
    this.callbackHandler = new SecureCallbackHandler(java.security.AccessController.getContext(), callbackHandler);
  }

  /**
   * Instantiate a new <code>LoginContext</code> object with a name, a
   * <code>Subject</code> to be authenticated, a <code>CallbackHandler</code>
   * object, and a login <code>Configuration</code>.
   * 
   * <p>
   * 
   * @param name
   *          the name used as the index into the caller-specified
   *          <code>Configuration</code>.
   *          <p>
   * 
   * @param subject
   *          the <code>Subject</code> to authenticate, or <code>null</code>.
   *          <p>
   * 
   * @param callbackHandler
   *          the <code>CallbackHandler</code> object used by LoginModules to
   *          communicate with the user, or <code>null</code>.
   *          <p>
   * 
   * @param config
   *          the <code>Configuration</code> that lists the login modules to be
   *          called to perform the authentication, or <code>null</code>.
   * 
   * @exception LoginException
   *              if the caller-specified <code>name</code> does not appear in
   *              the <code>Configuration</code> and there is no
   *              <code>Configuration</code> entry for "<i>other</i>".
   *              <p>
   * @exception SecurityException
   *              if a SecurityManager is set, <i>config</i> is
   *              <code>null</code>, and either the caller does not have
   *              AuthPermission("createLoginContext.<i>name</i>"), or if a
   *              configuration entry for <i>name</i> does not exist and the
   *              caller does not additionally have
   *              AuthPermission("createLoginContext.other")
   * 
   * @since 1.5
   */
  public AuditableSpringLoginContext(String name, Subject subject, CallbackHandler callbackHandler, Configuration config) throws LoginException {
    super(name, subject, callbackHandler, config);
    this.config = config;
    configProvided = (config != null) ? true : false;
    if (configProvided) {
      creatorAcc = java.security.AccessController.getContext();
    }

    init(name);
    if (subject != null) {
      this.subject = subject;
      subjectProvided = true;
    }
    if (callbackHandler == null) {
      loadDefaultCallbackHandler();
    } else if (!configProvided) {
      this.callbackHandler = new SecureCallbackHandler(java.security.AccessController.getContext(), callbackHandler);
    } else {
      this.callbackHandler = callbackHandler;
    }
  }

  /**
   * @param loginModuleName
   * @param subject
   * @param callbackHandler
   * @param configuration
   * @param shareState
   * @throws LoginException
   */
  public AuditableSpringLoginContext(String loginModuleName, Subject subject, CallbackHandler callbackHandler, Configuration configuration, Map<?, ?> shareState)
      throws LoginException {
    this(loginModuleName, subject, callbackHandler, configuration);
    this.state = shareState;
  }

  public String getLoginContextName() {
    return loginContextName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.siam.am.idp.authn.LoginModuleEventListenerAware#
   * setLoginModuleEventListener
   * (com.ibm.siam.am.idp.authn.LoginModuleEventListener)
   */
  public void setLoginModuleEventListener(LoginModuleEventListener loginModuleEventListener) {
    this.loginModuleEventListener = loginModuleEventListener;
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.authn.LoginContextEventListenerAware#setLoginContextEventListener(com.ibm.siam.am.idp.authn.LoginContextEventListener)
   */
  public void setLoginContextEventListener(LoginContextEventListener loginContextEventListener) {
    this.loginContextEventListener = loginContextEventListener;
  }
  
  private void init(String name) throws LoginException {

    SecurityManager sm = System.getSecurityManager();
    if (sm != null && !configProvided) {
      sm.checkPermission(new AuthPermission("createLoginContext." + name));
    }

    if (name == null)
      throw new LoginException("Invalid null input: name");

    // get the Configuration
    if (config == null) {
      config = (Configuration) java.security.AccessController.doPrivileged(new java.security.PrivilegedAction() {
        public Object run() {
          return Configuration.getConfiguration();
        }
      });
    }
    
    this.loginContextName = name;

    // get the LoginModules configured for this application
    AppConfigurationEntry[] entries = config.getAppConfigurationEntry(name);
    if (entries == null) {

      if (sm != null && !configProvided) {
        sm.checkPermission(new AuthPermission("createLoginContext." + OTHER));
      }

      entries = config.getAppConfigurationEntry(OTHER);
      if (entries == null) {
        MessageFormat form = new MessageFormat("No LoginModules configured for name");
        Object[] source = { name };
        throw new LoginException(form.format(source));
      }
    }
    moduleStack = new ModuleInfo[entries.length];
    for (int i = 0; i < entries.length; i++) {
      // clone returned array
      moduleStack[i] = new ModuleInfo(new AppConfigurationEntry(entries[i].getLoginModuleName(), entries[i].getControlFlag(), entries[i].getOptions()), null);
    }

    contextClassLoader = (ClassLoader) java.security.AccessController.doPrivileged(new java.security.PrivilegedAction() {
      public Object run() {
        return Thread.currentThread().getContextClassLoader();
      }
    });

  }

  private void loadDefaultCallbackHandler() throws LoginException {

    // get the default handler class
    try {

      final ClassLoader finalLoader = contextClassLoader;

      this.callbackHandler = (CallbackHandler) java.security.AccessController.doPrivileged(new java.security.PrivilegedExceptionAction() {
        public Object run() throws Exception {
          String defaultHandler = java.security.Security.getProperty(DEFAULT_HANDLER);
          if (defaultHandler == null || defaultHandler.length() == 0)
            return null;
          Class c = Class.forName(defaultHandler, true, finalLoader);
          return c.newInstance();
        }
      });
    } catch (java.security.PrivilegedActionException pae) {
      throw new LoginException(pae.getException().toString());
    }

    // secure it with the caller's ACC
    if (this.callbackHandler != null && !configProvided) {
      this.callbackHandler = new SecureCallbackHandler(java.security.AccessController.getContext(), this.callbackHandler);
    }
  }

  /**
   * Perform the authentication.
   * 
   * <p>
   * This method invokes the <code>login</code> method for each LoginModule
   * configured for the <i>name</i> specified to the <code>LoginContext</code>
   * constructor, as determined by the login <code>Configuration</code>. Each
   * <code>LoginModule</code> then performs its respective type of
   * authentication (username/password, smart card pin verification, etc.).
   * 
   * <p>
   * This method completes a 2-phase authentication process by calling each
   * configured LoginModule's <code>commit</code> method if the overall
   * authentication succeeded (the relevant REQUIRED, REQUISITE, SUFFICIENT, and
   * OPTIONAL LoginModules succeeded), or by calling each configured
   * LoginModule's <code>abort</code> method if the overall authentication
   * failed. If authentication succeeded, each successful LoginModule's
   * <code>commit</code> method associates the relevant Principals and
   * Credentials with the <code>Subject</code>. If authentication failed, each
   * LoginModule's <code>abort</code> method removes/destroys any previously
   * stored state.
   * 
   * <p>
   * If the <code>commit</code> phase of the authentication process fails, then
   * the overall authentication fails and this method invokes the
   * <code>abort</code> method for each configured <code>LoginModule</code>.
   * 
   * <p>
   * If the <code>abort</code> phase fails for any reason, then this method
   * propagates the original exception thrown either during the
   * <code>login</code> phase or the <code>commit</code> phase. In either case,
   * the overall authentication fails.
   * 
   * <p>
   * In the case where multiple LoginModules fail, this method propagates the
   * exception raised by the first <code>LoginModule</code> which failed.
   * 
   * <p>
   * Note that if this method enters the <code>abort</code> phase (either the
   * <code>login</code> or <code>commit</code> phase failed), this method
   * invokes all LoginModules configured for the application regardless of their
   * respective <code>Configuration</code> flag parameters. Essentially this
   * means that <code>Requisite</code> and <code>Sufficient</code> semantics are
   * ignored during the <code>abort</code> phase. This guarantees that proper
   * cleanup and state restoration can take place.
   * 
   * <p>
   * 
   * @exception LoginException
   *              if the authentication fails.
   */
  public void login() throws LoginException {

    loginSucceeded = false;

    if (subject == null) {
      subject = new Subject();
    }

    try {
      if (configProvided) {
        // module invoked in doPrivileged with creatorAcc
        invokeCreatorPriv(LOGIN_METHOD);
        invokeCreatorPriv(COMMIT_METHOD);
      } else {
        // module invoked in doPrivileged
        invokePriv(LOGIN_METHOD);
        invokePriv(COMMIT_METHOD);
      }
      loginSucceeded = true;
      
      // Fire LoginContext Event
      if (this.loginContextEventListener != null) {
         this.loginContextEventListener.fireEvent(new LoginContextEvent(this, 
             this.callbackHandler, 
             LoginContextEventPhase.LOGIN, 
             new LoginContextEventStatus(true),
             new Integer(AuditableSpringLoginContext.class.hashCode()).toString()
             ));
      }
    } catch (LoginException le) {
      try {
        if (configProvided) {
          invokeCreatorPriv(ABORT_METHOD);
        } else {
          invokePriv(ABORT_METHOD);
        }
      } catch (LoginException le2) {
        throw le;
      }
      // Fire LoginContext Event
      if (this.loginContextEventListener != null) {
         this.loginContextEventListener.fireEvent(new LoginContextEvent(this, 
             this.callbackHandler, 
             LoginContextEventPhase.LOGIN, 
             new LoginContextEventStatus(false, le),
             new Integer(AuditableSpringLoginContext.class.hashCode()).toString()
         ));
      }
      throw le;
    }
  }

  /**
   * Logout the <code>Subject</code>.
   * 
   * <p>
   * This method invokes the <code>logout</code> method for each
   * <code>LoginModule</code> configured for this <code>LoginContext</code>.
   * Each <code>LoginModule</code> performs its respective logout procedure
   * which may include removing/destroying <code>Principal</code> and
   * <code>Credential</code> information from the <code>Subject</code> and state
   * cleanup.
   * 
   * <p>
   * Note that this method invokes all LoginModules configured for the
   * application regardless of their respective <code>Configuration</code> flag
   * parameters. Essentially this means that <code>Requisite</code> and
   * <code>Sufficient</code> semantics are ignored for this method. This
   * guarantees that proper cleanup and state restoration can take place.
   * 
   * <p>
   * 
   * @exception LoginException
   *              if the logout fails.
   */
  public void logout() throws LoginException {
    if (subject == null) {
      throw new LoginException("null subject - logout called before login");
    }

    if (configProvided) {
      // module invoked in doPrivileged with creatorAcc
      invokeCreatorPriv(LOGOUT_METHOD);
    } else {
      // module invoked in doPrivileged
      invokePriv(LOGOUT_METHOD);
    }
    
    // Fire LoginContext Event
    if (this.loginContextEventListener != null) {
       this.loginContextEventListener.fireEvent(new LoginContextEvent(this, 
           this.callbackHandler, 
           LoginContextEventPhase.LOGOUT, 
           new LoginContextEventStatus(true),
           new Integer(AuditableSpringLoginContext.class.hashCode()).toString()
       ));
    }
  }

  /**
   * Return the authenticated Subject.
   * 
   * <p>
   * 
   * @return the authenticated Subject. If the caller specified a Subject to
   *         this LoginContext's constructor, this method returns the
   *         caller-specified Subject. If a Subject was not specified and
   *         authentication succeeds, this method returns the Subject
   *         instantiated and used for authentication by this LoginContext. If a
   *         Subject was not specified, and authentication fails or has not been
   *         attempted, this method returns null.
   */
  public Subject getSubject() {
    if (!loginSucceeded && !subjectProvided)
      return null;
    return subject;
  }

  private void clearState() {
    moduleIndex = 0;
    firstError = null;
    firstRequiredError = null;
    success = false;
  }

  private void throwException(LoginException originalError, LoginException le) throws LoginException {

    // first clear state
    clearState();

    // throw the exception
    LoginException error = (le != null) ? le : originalError;
    throw error;
  }

  /**
   * Invokes the login, commit, and logout methods from a LoginModule inside a
   * doPrivileged block.
   * 
   * This version is called if the caller did not instantiate the LoginContext
   * with a Configuration object.
   */
  private void invokePriv(final String methodName) throws LoginException {
    try {
      java.security.AccessController.doPrivileged(new java.security.PrivilegedExceptionAction() {
        public Object run() throws LoginException {
          invoke(methodName);
          return null;
        }
      });
    } catch (java.security.PrivilegedActionException pae) {
      throw (LoginException) pae.getException();
    }
  }

  /**
   * Invokes the login, commit, and logout methods from a LoginModule inside a
   * doPrivileged block restricted by creatorAcc
   * 
   * This version is called if the caller instantiated the LoginContext with a
   * Configuration object.
   */
  private void invokeCreatorPriv(final String methodName) throws LoginException {
    try {
      java.security.AccessController.doPrivileged(new java.security.PrivilegedExceptionAction() {
        public Object run() throws LoginException {
          invoke(methodName);
          return null;
        }
      }, creatorAcc);
    } catch (java.security.PrivilegedActionException pae) {
      throw (LoginException) pae.getException();
    }
  }

  private void invoke(String methodName) throws LoginException {

    // start at moduleIndex
    // - this can only be non-zero if methodName is LOGIN_METHOD

    for (int i = moduleIndex; i < moduleStack.length; i++, moduleIndex++) {
      try {

        int mIndex = 0;
        Method[] methods = null;

        if (moduleStack[i].module != null) {
          methods = moduleStack[i].module.getClass().getMethods();
        } else {

          // instantiate the LoginModule
          Class c = Class.forName(moduleStack[i].entry.getLoginModuleName(), true, contextClassLoader);

          Constructor constructor = c.getConstructor(PARAMS);
          Object[] args = {};

          // allow any object to be a LoginModule
          // as long as it conforms to the interface
          moduleStack[i].module = constructor.newInstance(args);
          if (moduleStack[i].module instanceof ApplicationContextAware) {
            ((ApplicationContextAware) moduleStack[i].module).setApplicationContext(this.applicationContext);
          }

          methods = moduleStack[i].module.getClass().getMethods();

          // call the LoginModule's initialize method
          for (mIndex = 0; mIndex < methods.length; mIndex++) {
            if (methods[mIndex].getName().equals(INIT_METHOD))
              break;
          }

          Object[] initArgs = { subject, callbackHandler, state, moduleStack[i].entry.getOptions() };
          // invoke the LoginModule initialize method
          methods[mIndex].invoke(moduleStack[i].module, initArgs);
        }

        // find the requested method in the LoginModule
        for (mIndex = 0; mIndex < methods.length; mIndex++) {
          if (methods[mIndex].getName().equals(methodName))
            break;
        }

        // set up the arguments to be passed to the LoginModule method
        Object[] args = {};

        // invoke the LoginModule method
        boolean status = ((Boolean) methods[mIndex].invoke(moduleStack[i].module, args)).booleanValue();

        // Fire LoginModuleEvent for login failure
        if (this.loginModuleEventListener != null) {
          this.loginModuleEventListener
              .fireEvent(new LoginModuleEvent(this, 
                  this.callbackHandler, moduleStack[i].entry, 
                  LoginModuleEventPhase.valueByMethodName(methodName), 
                  new LoginModuleEventStatus(status),
                  new Integer(AuditableSpringLoginContext.class.hashCode()).toString()
                  ));
        }

        if (status == true) {

          // if SUFFICIENT, return if no prior REQUIRED errors
          if (!methodName.equals(ABORT_METHOD) && !methodName.equals(LOGOUT_METHOD)
              && moduleStack[i].entry.getControlFlag() == AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT && firstRequiredError == null) {

            // clear state
            clearState();

            if (log.isDebugEnabled())
              log.debug(methodName + " SUFFICIENT success");
            return;
          }

          if (log.isDebugEnabled())
            log.debug(methodName + " success");
          success = true;
        } else {
          if (log.isDebugEnabled())
            log.debug(methodName + " ignored");
        }

      } catch (NoSuchMethodException nsme) {
        MessageFormat form = new MessageFormat("unable to instantiate LoginModule, module, because " + "it does not provide a no-argument constructor");
        Object[] source = { moduleStack[i].entry.getLoginModuleName() };
        throwException(null, new LoginException(form.format(source)));

      } catch (InstantiationException ie) {
        throwException(null, new LoginException("unable to instantiate LoginModule: " + ie.getMessage()));
      } catch (ClassNotFoundException cnfe) {
        throwException(null, new LoginException("unable to find LoginModule class: " + cnfe.getMessage()));
      } catch (IllegalAccessException iae) {
        throwException(null, new LoginException("unable to access LoginModule: " + iae.getMessage()));
      } catch (InvocationTargetException ite) {

        // failure cases

        LoginException le;

        if (ite.getCause() instanceof LoginException) {

          le = (LoginException) ite.getCause();

        } else if (ite.getCause() instanceof SecurityException) {

          // do not want privacy leak
          // (e.g., sensitive file path in exception msg)

          le = new LoginException("Security Exception");
          le.initCause(new SecurityException());
          if (log.isDebugEnabled()) {
            log.debug("original security exception with detail msg " + "replaced by new exception with empty detail msg");
            log.debug("original security exception: " + ite.getCause().toString());
          }
        } else {

          // capture an unexpected LoginModule exception
          java.io.StringWriter sw = new java.io.StringWriter();
          ite.getCause().printStackTrace(new java.io.PrintWriter(sw));
          sw.flush();
          le = new LoginException(sw.toString());
        }
        
     // Fire LoginModuleEvent for login failure
        if (this.loginModuleEventListener != null) {
          this.loginModuleEventListener.fireEvent(new LoginModuleEvent(this, 
              this.callbackHandler, 
              moduleStack[i].entry, 
              LoginModuleEventPhase.valueByMethodName(methodName), 
              new LoginModuleEventStatus(false, le),
              new Integer(AuditableSpringLoginContext.class.hashCode()).toString()
          ));
        }

        if (moduleStack[i].entry.getControlFlag() == AppConfigurationEntry.LoginModuleControlFlag.REQUISITE) {

          if (log.isDebugEnabled())
            log.debug(methodName + " REQUISITE failure");

          // if REQUISITE, then immediately throw an exception
          if (methodName.equals(ABORT_METHOD) || methodName.equals(LOGOUT_METHOD)) {
            if (firstRequiredError == null)
              firstRequiredError = le;
          } else {
            throwException(firstRequiredError, le);
          }

        } else if (moduleStack[i].entry.getControlFlag() == AppConfigurationEntry.LoginModuleControlFlag.REQUIRED) {

          if (log.isDebugEnabled())
            log.debug(methodName + " REQUIRED failure");
          // mark down that a REQUIRED module failed
          if (firstRequiredError == null)
            firstRequiredError = le;

        } else {

          if (log.isDebugEnabled())
            log.debug(methodName + " OPTIONAL failure");
          // mark down that an OPTIONAL module failed
          if (firstError == null)
            firstError = le;
        }
        
      }
    }

    // we went thru all the LoginModules.
    if (firstRequiredError != null) {
      // a REQUIRED module failed -- return the error
      throwException(firstRequiredError, null);
    } else if (success == false && firstError != null) {
      // no module succeeded -- return the first error
      throwException(firstError, null);
    } else if (success == false) {
      // no module succeeded -- all modules were IGNORED
      throwException(new LoginException("Login Failure: all modules ignored"), null);
    } else {
      // success

      clearState();
      return;
    }
  }

  /**
   * Wrap the caller-specified CallbackHandler in our own and invoke it within a
   * privileged block, constrained by the caller's AccessControlContext.
   */
  private static class SecureCallbackHandler implements CallbackHandler {

    private final java.security.AccessControlContext acc;
    private final CallbackHandler ch;

    SecureCallbackHandler(java.security.AccessControlContext acc, CallbackHandler ch) {
      this.acc = acc;
      this.ch = ch;
    }

    public void handle(final Callback[] callbacks) throws java.io.IOException, UnsupportedCallbackException {
      try {
        java.security.AccessController.doPrivileged(new java.security.PrivilegedExceptionAction() {
          public Object run() throws java.io.IOException, UnsupportedCallbackException {
            ch.handle(callbacks);
            return null;
          }
        }, acc);
      } catch (java.security.PrivilegedActionException pae) {
        if (pae.getException() instanceof java.io.IOException) {
          throw (java.io.IOException) pae.getException();
        } else {
          throw (UnsupportedCallbackException) pae.getException();
        }
      }
    }
  }

  /**
   * LoginModule information - incapsulates Configuration info and actual module
   * instances
   */
  private static class ModuleInfo {
    AppConfigurationEntry entry;
    Object module;

    ModuleInfo(AppConfigurationEntry newEntry, Object newModule) {
      this.entry = newEntry;
      this.module = newModule;
    }
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
