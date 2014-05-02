/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

/**
 * @author zhaodonglu
 *
 */
public class LoginContextEvent {
  /**
   * LoginContext which own this event
   */
  private LoginContext loginContext = null;
  
  /**
   * Phase of login cycle
   */
  private LoginContextEventPhase phase;
  
  /**
   * Status of login process
   */
  private LoginContextEventStatus status;


  /**
   * CallbackHandler for this event
   */
  private CallbackHandler callbackHandler = null;
  
  /**
   * The level of LoginContextEvent
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
  public LoginContextEvent(LoginContext loginContext, CallbackHandler callbackHandler, LoginContextEventPhase phase, LoginContextEventStatus status,String auditableClassHashCode) {
    super();
    this.loginContext = loginContext;
    this.callbackHandler = callbackHandler;
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

  public LoginContextEventPhase getPhase() {
    return phase;
  }

  public void setPhase(LoginContextEventPhase phase) {
    this.phase = phase;
  }

  public LoginContextEventStatus getStatus() {
    return status;
  }

  public void setStatus(LoginContextEventStatus status) {
    this.status = status;
  }

  @Override
  public String toString() {
    String loginContextName = "";
    if (this.getLoginContext() instanceof AuditableSpringLoginContext) {
      loginContextName = ((AuditableSpringLoginContext)this.getLoginContext()).getLoginContextName();
    }
    
    return String.format("LoginModuleEvent[%s], phase=%s, status=%s]", loginContextName, phase, status);
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
