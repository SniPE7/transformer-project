/**
 * 
 */
package com.ibm.siam.am.idp.authn.module;

import javax.security.auth.login.LoginException;

/**
 * @author zhaodonglu
 * 
 */
public class MissingPasswordHintLoginException extends LoginException {

  private String username;
  private String[] questionCandidateAttributes;

  /**
   * 
   */
  private static final long serialVersionUID = -7093150055855178217L;

  /**
   * 
   */
  protected MissingPasswordHintLoginException() {
    super();
  }

  /**
   * @param msg
   */
  public MissingPasswordHintLoginException(String msg) {
    super(msg);
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

  public void setQuestionCandidateAttributes(String[] formHintQuestionCandidateAttribute) {
    this.questionCandidateAttributes = formHintQuestionCandidateAttribute;
  }

}
