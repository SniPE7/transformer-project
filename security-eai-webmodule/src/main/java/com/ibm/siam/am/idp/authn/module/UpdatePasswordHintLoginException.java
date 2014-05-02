/**
 * 
 */
package com.ibm.siam.am.idp.authn.module;

import javax.security.auth.login.LoginException;

/**
 * @author zhaodonglu
 * 
 */
public class UpdatePasswordHintLoginException extends LoginException {

  private String username;
  private String[] questionCandidateAttributes;
  
  private String loginErrorKey = null;

  /**
   * 
   */
  private static final long serialVersionUID = -7093150055855178217L;

  /**
   * 
   */
  public UpdatePasswordHintLoginException() {
    super();
  }

  /**
   * @param msg
   */
  public UpdatePasswordHintLoginException(String loginErrorKey, String msg) {
    super(msg);
    this.loginErrorKey = loginErrorKey;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String formUserNameAttribute) {
    this.username = formUserNameAttribute;
  }

  public String[] getQuestionCandidateAttributes() {
    return questionCandidateAttributes;
  }

  public void setQuestionCandidateAttributes(String[] questionCandidateAttributes) {
    this.questionCandidateAttributes = questionCandidateAttributes;
  }

  public String getLoginErrorKey() {
    return loginErrorKey;
  }

  public void setLoginErrorKey(String loginErrorKey) {
    this.loginErrorKey = loginErrorKey;
  }

}
