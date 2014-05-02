/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.LoginContext;

/**
 * @author zhaodonglu
 *
 */
public class LoginModuleEvent {
  /**
   * LoginContext which own login module
   */
  private LoginContext loginContext = null;
  
  /**
   * LoginModule which own this event
   */
  private AppConfigurationEntry loginModuleEntry = null;
  /**
   * Phase of login cycle
   */
  private LoginModuleEventPhase phase;
  
  /**
   * Status of login process
   */
  private LoginModuleEventStatus status;
  
  /**
   * CallbackHandler for this event
   */
  private CallbackHandler callbackHandler = null;
  
  /**
   * The level of LoginModuleEvent
   */
  private String logLevel = null;
  
  /**
   * auditableClassHashCode is used to discriminate events
   */
  private String auditableClassHashCode = null;


  /**
   * Constructor
   * @param targetLoginModuleEntry
   * @param phase
   * @param status
   */
  public LoginModuleEvent(LoginContext loginContext, CallbackHandler callbackHandler, AppConfigurationEntry targetLoginModuleEntry, LoginModuleEventPhase phase, LoginModuleEventStatus status,String auditableClassHashCode) {
    super();
    this.loginContext = loginContext;
    this.callbackHandler = callbackHandler;
    this.loginModuleEntry = targetLoginModuleEntry;
    this.phase = phase;
    this.status = status;
    this.auditableClassHashCode = auditableClassHashCode;
  }

  public LoginContext getLoginContext() {
    return loginContext;
  }

  public void setLoginContext(LoginContext loginContext) {
    this.loginContext = loginContext;
  }

  public CallbackHandler getCallbackHandler() {
    return callbackHandler;
  }

  public void setCallbackHandler(CallbackHandler callbackHandler) {
    this.callbackHandler = callbackHandler;
  }

  public AppConfigurationEntry getLoginModuleEntry() {
    return loginModuleEntry;
  }

  public void setLoginModuleEntry(AppConfigurationEntry loginModuleEntry) {
    this.loginModuleEntry = loginModuleEntry;
  }

  public LoginModuleEventPhase getPhase() {
    return phase;
  }

  public void setPhase(LoginModuleEventPhase phase) {
    this.phase = phase;
  }

  public LoginModuleEventStatus getStatus() {
    return status;
  }

  public void setStatus(LoginModuleEventStatus status) {
    this.status = status;
  }

  @Override
  public String toString() {
    String loginContextName = "";
    if (this.getLoginContext() instanceof AuditableSpringLoginContext) {
      loginContextName = ((AuditableSpringLoginContext)this.getLoginContext()).getLoginContextName();
    }
    
    return String.format("LoginModuleEvent[%s] [loginModuleEntry=[%s, %s], phase=%s, status=%s]", loginContextName, loginModuleEntry.getLoginModuleName(), loginModuleEntry.getControlFlag().toString(), phase, status);
  }

  public String getLogLevel() {
    return logLevel;
  }

  public void setLogLevel(String logLevel) {
    this.logLevel = logLevel;
  }

  public String getAuditableClassHashCode() {
    return auditableClassHashCode;
  }

  public void setAuditableClassHashCode(String auditableClassHashCode) {
    this.auditableClassHashCode = auditableClassHashCode;
  }

}
