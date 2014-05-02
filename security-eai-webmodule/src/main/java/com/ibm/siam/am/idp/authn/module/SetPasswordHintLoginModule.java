package com.ibm.siam.am.idp.authn.module;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.apache.commons.lang.StringUtils;

import com.ibm.siam.am.idp.authn.principal.UserAccountPrincipal;
import com.ibm.siam.am.idp.authn.principal.UserPrincipal;
import com.ibm.siam.am.idp.authn.provider.FormOperationCallback;
import com.ibm.siam.am.idp.authn.provider.PasswordHintQuestionAndAnswerCallback;
import com.ibm.siam.am.idp.authn.service.UserPassService;
import com.ibm.siam.am.idp.entity.LdapUserEntity;

/**
 * 检查用户是否设置了密码提示问题。
 * 
 * @author Booker
 */

public class SetPasswordHintLoginModule extends AbstractSpringLoginModule {

  /** 用户口令服务Bean ID */
  private String userPassServiceBeanId = null;
  
  /** Tim Ldap用户口令服务Bean ID */
  private String timUserPassServiceBeanId = null;

  private String setPassHintFlag = "setPasswordHint";

  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    this.userPassServiceBeanId = (String) options.get("userPassServiceBeanId");
    
    this.timUserPassServiceBeanId = (String) options.get("timUserPassServiceBeanId");

    if (options.get("setPassHintFlag") != null) {
      this.setPassHintFlag = (String) options.get("setPassHintFlag");
    }

  }

  /** {@inheritDoc} */
  public boolean login() throws LoginException {
    if (callbackHandler == null) {
      log.error("No CallbackHandler");
      return false;
    }

    NameCallback nameCallback = new NameCallback("User Name: ");
    FormOperationCallback formOptCallback = new FormOperationCallback();
    PasswordHintQuestionAndAnswerCallback answerCallback = new PasswordHintQuestionAndAnswerCallback();
    

    Callback[] callbacks = new Callback[] { nameCallback, formOptCallback, answerCallback};
    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    } catch (UnsupportedCallbackException e) {
      log.error(e.getMessage(), e);
      return false;
    }

    String username = (String) sharedState.get(LOGIN_NAME);
    if (username == null || "".equals(username)) {
      throw new LoginException(String.format("username is null; usernsme:%s.", username));
    }

    String opt = formOptCallback.getOperation();

    if (opt != null && opt.equals(setPassHintFlag)) {
      // Update password hint
      updatePasswordHint(answerCallback);
    } else {
      String[] hints = getPasswordHints(username);

      if (hints == null || hints.length == 0) {
        // Need to set recover password Q&A
        MissingPasswordHintLoginException e = new MissingPasswordHintLoginException(String.format("Missing password hint: [%s]", username));
        e.setUsername(username);
        e.setQuestionCandidateAttributes(this.getExistsPasswordHint());
        throw e;
      }
    }

    // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
    UserPrincipal principal = new UserPrincipal();
    principal.setName(username);

    principals.add(principal);
    authenticated = true;
    return true;
  }

  /**
   * 修改用户口令（TIM API）
   * 
   * @param request
   * @throws LoginException
   */
  private void updatePasswordHint(PasswordHintQuestionAndAnswerCallback pcb) throws LoginException {
    String username = pcb.getUsername();
    String hintQuestion = pcb.getQuestion();
    String hintAnswer = pcb.getAnswer();

    if (StringUtils.isEmpty(username)) {
      throw new LoginException(String.format("Failure to update password hint, missing username."));
    }

    if (StringUtils.isEmpty(hintQuestion) || StringUtils.isEmpty(hintAnswer)) {
      UpdatePasswordHintLoginException e = new UpdatePasswordHintLoginException("passhint.form.error.hintRequired", String.format("Failure to update [%s]'s password hint, missing hint information.", username));
      e.setUsername(username);
      e.setQuestionCandidateAttributes(this.getExistsPasswordHint());
      throw e;
    }

    // 设置密码提示问题及答案
    UserPassService userPassService = getTimUserPassService();
    boolean success = false;
    
    if (userPassService != null) {
      success = userPassService.setPassRecoveryQuestion(username, hintQuestion, hintAnswer);
      userPassService = null;
    }
    
    if (!success) {
      log.error(String.format("Set pass recovery question failed in Tim Ldap, username[%s]; hintQuestion:%s, hintAnswer:%s",
          username, hintQuestion, hintAnswer));

      UpdatePasswordHintLoginException e = new UpdatePasswordHintLoginException("passhint.form.error.failure", String.format("Failure to update [%s]'s password hint.", username));
      e.setUsername(username);
      e.setQuestionCandidateAttributes(this.getExistsPasswordHint());
      throw e;
    }
    
    userPassService = getUserPassService();
    
    if (userPassService != null) {
      success = userPassService.setPassRecoveryQuestion(username, hintQuestion, hintAnswer);
    }
    
    if (!success) {
      log.error(String.format("Set pass recovery question failed in DB, username[%s]; hintQuestion:%s, hintAnswer:%s",
          username, hintQuestion, hintAnswer));
    }
  }

  /**
   * 获取用户密码服务Bean
   * 
   * @return UserPassService
   */
  private UserPassService getUserPassService() {
    if (this.userPassServiceBeanId != null && !"".equals(this.userPassServiceBeanId)) {
      return this.applicationContext.getBean(this.userPassServiceBeanId, UserPassService.class);
    }
    return null;
  }

  /**
   * 获取Tim Ldap用户密码服务Bean
   * 
   * @return UserPassService
   */
  private UserPassService getTimUserPassService() {
    if (this.timUserPassServiceBeanId != null && !"".equals(this.timUserPassServiceBeanId)) {
      return this.applicationContext.getBean(this.timUserPassServiceBeanId, UserPassService.class);
    }
    return null;
  }
  
  /**
   * 获取指定用户密码提示问题及答案。
   * 
   * @param username
   * @return
   * @throws LoginException
   */
  @SuppressWarnings("unchecked")
  private String[] getPasswordHints(String username) throws LoginException {
    String[] passHint = null;
    UserAccountPrincipal userAccountPrincipal = (UserAccountPrincipal) sharedState.get(AbstractLoginModule.LOGIN_USER_ACCOUNT_PRINCIPAL);
    try {
      if (userAccountPrincipal == null) {
        UserPassService userPassService = getUserPassService();
        LdapUserEntity ldapUserEntity  = userPassService.getLdapUserEntityByUsername(username);
        userAccountPrincipal = new UserAccountPrincipal(username);
        userAccountPrincipal.setPassRecoveryQuestion(userPassService.getPassRecoveryQuestion(ldapUserEntity));
        userAccountPrincipal.setPassLastChanged(userPassService.getPassLastChanged(ldapUserEntity));
        userAccountPrincipal.setPassResetState(userPassService.getPassResetState(ldapUserEntity));
        sharedState.put(AbstractLoginModule.LOGIN_USER_ACCOUNT_PRINCIPAL, userAccountPrincipal);
      }
      passHint = userAccountPrincipal.getPassRecoveryQuestion();
    } catch (Exception e) {
      log.error(String.format("Get user password hints exception. username:%s", username), e);
      throw new LoginException(String.format("Get user password hints exception:%s. username:%s", e.getMessage(),
          username));
    }
    return passHint;
  }

  /**
   * 获取用户密码所有提示问题。
   * 
   * @return
   * @throws LoginException
   */
  private String[] getExistsPasswordHint() throws LoginException {
    
    UserPassService userPassService = getUserPassService();
    try {
      return userPassService.getAllPassQuestion();
    } catch (Exception e) {
      String errorDetail = String.format("Get all user pass question exception :%s", e.getMessage());
      log.error(errorDetail, e);
      throw new LoginException(errorDetail);
    }
  }
}
