package com.ibm.tivoli.cmcc.module;

import java.security.Principal;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cmcc.service.auth.PersonDTOPrincipal;
import com.ibm.tivoli.cmcc.spi.PersonDTO;

public abstract class AbstractMobileUserLoginModule implements LoginModule, PrincipalAware {

  private static Log log = LogFactory.getLog(UserPasswordLoginModule.class);
  private Subject subject = new Subject();
  private CallbackHandler callbackHandler;
  private boolean debug = false;
  private boolean succeeded = false;
  private boolean commitSucceeded = false;
  private String username;
  private char[] password;
  private Principal principal;
  private PersonDTO personDTO = null;

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

  /**
   * @return the principal
   */
  public Principal getPrincipal() {
    return principal;
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
      principal = null;
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
      principal = new PersonDTOPrincipal(this.username, this.personDTO);;
      if (!subject.getPrincipals().contains(principal))
        subject.getPrincipals().add(principal);
  
      if (debug) {
        log.info("\t\t[UserPasswordLoginModule] " + "added NamePrincipal to Subject");
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
  
    Callback[] callbacks = new Callback[2];
    callbacks[0] = new MobileUserPasswordCallback();
    callbacks[1] = new CMCCArtifactIDCallback();
  
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
  
    } catch (java.io.IOException e) {
      log.error(e.getMessage(), e);
      throw new LoginException(e.toString());
    } catch (UnsupportedCallbackException e) {
      log.error(e.getMessage(), e);
      throw new LoginException("Error: " + e.getCallback().toString() + " not available to garner authentication information " + "from the user");
    }
  
    // print debugging information
    if (debug) {
      log.info("\t\t[UserPasswordLoginModule] " + "user entered user name: " + username);
      //log.info("\t\t[UserPasswordLoginModule] " + "user entered password: ");
    }
  
    // verify the username/password
    try {
      personDTO = authenticate(username, passwordType, password);
    } catch (Exception e) {
      log.error("Failure to check user password.", e);
      throw new LoginException(e.getMessage());
    }
    if (personDTO != null && personDTO.getMsisdn() != null) {
      // authentication succeeded!!!
      if (debug)
        log.info("\t\t[UserPasswordLoginModule] [" + username + "] authentication succeeded");
      succeeded = true;
      return true;
    } else {
  
      // authentication failed -- clean out state
      if (debug)
        log.info("\t\t[UserPasswordLoginModule] [" + username + "] authentication failed");
      succeeded = false;
      username = null;
      for (int i = 0; i < password.length; i++)
        password[i] = ' ';
      password = null;
      return false;
    }
  }

  public boolean logout() throws LoginException {
  
    subject.getPrincipals().remove(principal);
    succeeded = false;
    succeeded = commitSucceeded;
    username = null;
    if (password != null) {
      for (int i = 0; i < password.length; i++)
        password[i] = ' ';
      password = null;
    }
    principal = null;
    return true;
  }

  /**
   * @param passwordType
   * @return
   * @throws Exception
   */
  protected abstract PersonDTO authenticate(String username, String passwordType, char[] password) throws Exception;
}