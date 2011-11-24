/**
 * 
 */
package com.ibm.tivoli.cmcc.service.auth;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.ldap.PersonDAO;

/**
 * @author zhaodonglu
 * 
 */
public class MobileUserLoginModule implements LoginModule {

  private static Log log = LogFactory.getLog(MobileUserLoginModule.class);

  private PersonDAO userDAO =  null;

  // initial state
  private Subject subject = new Subject();
  private CallbackHandler callbackHandler;

  // configurable option
  private boolean debug = false;

  // the authentication status
  private boolean succeeded = false;
  private boolean commitSucceeded = false;

  // username and password
  private String username;
  private char[] password;

  // testUser's NamePrincipal
  private NamePrincipal userPrincipal;

  /**
   * 
   */
  public MobileUserLoginModule() {
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
   * @return the userDAO
   */
  public PersonDAO getUserDAO() {
    return userDAO;
  }

  /**
   * @param userDAO the userDAO to set
   */
  public void setUserDAO(PersonDAO userDAO) {
    this.userDAO = userDAO;
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * javax.security.auth.spi.LoginModule#initialize(javax.security.auth.Subject,
   * javax.security.auth.callback.CallbackHandler, java.util.Map, java.util.Map)
   */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> arg2, Map<String, ?> arg3) {
    this.callbackHandler = callbackHandler;
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.security.auth.spi.LoginModule#abort()
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see javax.security.auth.spi.LoginModule#commit()
   */
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
        log.info("\t\t[MobileUserLoginModule] " + "added NamePrincipal to Subject");
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

  /*
   * (non-Javadoc)
   * 
   * @see javax.security.auth.spi.LoginModule#login()
   */
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
      log.info("\t\t[MobileUserLoginModule] " + "user entered user name: " + username);
      //log.info("\t\t[MobileUserLoginModule] " + "user entered password: ");
    }

    // verify the username/password
    boolean correct;
    try {
      correct = userDAO.checkMobileUserPassword(username, passwordType, password);
    } catch (Exception e) {
      log.error("Failure to check user password.", e);
      throw new LoginException(e.getMessage());
    }
    if (correct) {
      // authentication succeeded!!!
      if (debug)
        log.info("\t\t[MobileUserLoginModule] [" + username + "] authentication succeeded");
      succeeded = true;
      return true;
    } else {

      // authentication failed -- clean out state
      if (debug)
        log.info("\t\t[MobileUserLoginModule] [" + username + "] authentication failed");
      succeeded = false;
      username = null;
      for (int i = 0; i < password.length; i++)
        password[i] = ' ';
      password = null;
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.security.auth.spi.LoginModule#logout()
   */
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

}
