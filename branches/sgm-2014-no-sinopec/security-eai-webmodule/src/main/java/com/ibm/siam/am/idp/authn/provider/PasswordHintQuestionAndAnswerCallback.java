/**
 * 
 */
package com.ibm.siam.am.idp.authn.provider;

import java.io.Serializable;

import javax.security.auth.callback.Callback;

/**
 * @author zhaodonglu
 *
 */
public class PasswordHintQuestionAndAnswerCallback implements Callback, Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -1931167587843414054L;
  
  private String username = null;
  private String question = null;
  private String answer = null;

  /**
   * 
   */
  public PasswordHintQuestionAndAnswerCallback() {
    super();
  }

  public PasswordHintQuestionAndAnswerCallback(String username, String question, String answer) {
    super();
    this.username = username;
    this.question = question;
    this.answer = answer;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

}
