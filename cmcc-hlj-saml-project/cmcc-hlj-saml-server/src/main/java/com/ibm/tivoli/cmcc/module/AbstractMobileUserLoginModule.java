package com.ibm.tivoli.cmcc.module;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.service.auth.NamePrincipal;

public abstract class AbstractMobileUserLoginModule {

  private static Log log = LogFactory.getLog(LDAPMobileUserLoginModule.class);
  private Subject subject = new Subject();
  private CallbackHandler callbackHandler;
  private boolean debug = false;
  private boolean succeeded = false;
  private boolean commitSucceeded = false;
  private String username;
  private char[] password;
  private NamePrincipal userPrincipal;

  public AbstractMobileUserLoginModule() {
    super();
  }

  /**
   * @return the callbackHandler
   */
  public CallbackHandler getCallbackHandler() {
    return callbackHandler;
  }

  /**
   * @param callbackHandler the callbackHandler to set
   */
  public void setCallbackHandler(CallbackHandler callbackHandler) {
    this.callbackHandler = callbackHandler;
  }

  /**
   * @return the debug
   */
  public boolean isDebug() {
    return debug;
  }

  /**
   * @param debug the debug to set
   */
  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> arg2, Map<String, ?> arg3) {
    this.callbackHandler = callbackHandler;
  }

  public boolean abort() throws LoginException {
    if (succeeded == false) {
      return false;
    } else if (succeeded == true && commitSucceeded == false) {
      // login succeeded but overall authentication failed
      succeeded = false;
      username = null;
      if (password != null) {
        for (int i = 0; i < password.length; i++)
          password[i] = ' ';
        password = null;
      }
      userPrincipal = null;
    } else {
      // overall authentication succeeded and commit succeeded,
      // but someone else's commit failed
      logout();
    }
    return true;
  }

  public boolean commit() throws LoginException {
    if (succeeded == false) {
      return false;
    } else {
      // add a Principal (authenticated identity)
      // to the Subject
  
      // assume the user we authenticated is the NamePrincipal
      userPrincipal = new NamePrincipal(username);
      if (!subject.getPrincipals().contains(userPrincipal))
        subject.getPrincipals().add(userPrincipal);
  
      if (debug) {
        log.info("\t\t[LDAPMobileUserLoginModule] " + "added NamePrincipal to Subject");
      }
  
      // in any case, clean out state
      username = null;
      for (int i = 0; i < password.length; i++)
        password[i] = ' ';
      password = null;
  
      commitSucceeded = true;
      return true;
    }
  }

  public boolean login() throws LoginException {
    // prompt for a user name and password
    if (callbackHandler == null)
      throw new LoginException("Error: no CallbackHandler available " + "to garner authentication information from the user");
  
    Callback[] callbacks = new Callback[1];
    callbacks[0] = new MobileUserPasswordCallback();
  
    String passwordType = null;
    try {
      callbackHandler.handle(callbacks);
      username = ((MobileUserPasswordCallback) callbacks[0]).getMsisdn();
      passwordType = ((MobileUserPasswordCallback) callbacks[0]).getPassworType();
      char[] tmpPassword = ((MobileUserPasswordCallback) callbacks[0]).getPassword();
      
      if (tmpPassword == null) {
        // treat a NULL password as an empty password
        tmpPassword = new char[0];
      }
      password = new char[tmpPassword.length];
      System.arraycopy(tmpPassword, 0, password, 0, tmpPassword.length);
      ((MobileUserPasswordCallback) callbacks[0]).clearPassword();
  
    } catch (java.io.IOException ioe) {
      throw new LoginException(ioe.toString());
    } catch (UnsupportedCallbackException uce) {
      throw new LoginException("Error: " + uce.getCallback().toString() + " not available to garner authentication information " + "from the user");
    }
  
    // print debugging information
    if (debug) {
      log.info("\t\t[LDAPMobileUserLoginModule] " + "user entered user name: " + username);
      //log.info("\t\t[LDAPMobileUserLoginModule] " + "user entered password: ");
    }
  
    // verify the username/password
    boolean correct;
    try {
      correct = authenticate(username, passwordType, password);
    } catch (Exception e) {
      log.error("Failure to check user password.", e);
      throw new LoginException(e.getMessage());
    }
    if (correct) {
      // authentication succeeded!!!
      if (debug)
        log.info("\t\t[LDAPMobileUserLoginModule] [" + username + "] authentication succeeded");
      succeeded = true;
      return true;
    } else {
  
      // authentication failed -- clean out state
      if (debug)
        log.info("\t\t[LDAPMobileUserLoginModule] [" + username + "] authentication failed");
      succeeded = false;
      username = null;
      for (int i = 0; i < password.length; i++)
        password[i] = ' ';
      password = null;
      return false;
    }
  }

  public boolean logout() throws LoginException {
  
    subject.getPrincipals().remove(userPrincipal);
    succeeded = false;
    succeeded = commitSucceeded;
    username = null;
    if (password != null) {
      for (int i = 0; i < password.length; i++)
        password[i] = ' ';
      password = null;
    }
    userPrincipal = null;
    return true;
  }

  /**
   * @param passwordType
   * @return
   * @throws Exception
   */
  protected abstract boolean authenticate(String username, String passwordType, char[] password) throws Exception;
}