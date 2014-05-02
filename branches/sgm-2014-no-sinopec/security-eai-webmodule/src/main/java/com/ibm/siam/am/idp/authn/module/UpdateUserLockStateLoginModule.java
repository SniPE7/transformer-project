package com.ibm.siam.am.idp.authn.module;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.apache.commons.lang.StringUtils;

import com.ibm.siam.am.idp.authn.service.AbstractUserLockService;
import com.ibm.siam.am.idp.authn.service.UserLockState;
import com.ibm.siam.am.idp.authn.principal.UserPrincipal;

/**
 * �û�����״̬���LoginModule
 * <p>
 * �����������û������Զ��������ڣ���¼ʧ��
 * </p>
 * 
 * @author zhangxianwen
 * @since 2012-6-18 ����11:29:08
 */

public class UpdateUserLockStateLoginModule extends AbstractSpringLoginModule {

  /** �û���������Bean ID */
  private String userLockServiceBeanId = null;

  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    this.userLockServiceBeanId = (String) options.get("userLockServiceBeanId");
    if (this.userLockServiceBeanId == null || "".equals(this.userLockServiceBeanId)) {
      throw new RuntimeException("userLockServiceBeanId is null");
    }

  }

  /** {@inheritDoc} */
  public boolean login() throws LoginException {
    if (callbackHandler == null) {
      log.error("No CallbackHandler");
      return false;
    }
    
    // Authentication failure, increase failure counter
    NameCallback nameCallback = new NameCallback("User Name: ");

    Callback[] callbacks = new Callback[] { nameCallback };
    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    } catch (UnsupportedCallbackException e) {
      log.error(e.getMessage(), e);
      return false;
    }
    String username = nameCallback.getName();
    
    AbstractUserLockService userLockStateService = getUserLockStateService();

    // ���֮ǰ�Ƿ��Ѿ���֤�ɹ�
    String loginUsername = (String) this.sharedState.get(LOGIN_NAME);
    if (!StringUtils.isEmpty(loginUsername)) {
      // �û�У�飬ͨ��LADP��ѯ������Աuid��Ϣ���洢Subject
      UserPrincipal principal = new UserPrincipal();
      principal.setName(loginUsername);
      principals.add(principal);
      authenticated = true;
      
      //����û���¼ʧ����Ϣ
      try {
        UserLockState userLockState = userLockStateService.getUser(username);
        if(userLockState != null){
          userLockStateService.unLock(userLockState);
        }
      } catch (Exception e) {
        log.error(String.format("Get user lock state exception; usernsme:%s.", username), e);
      }
      
      return true;
    }

    Integer maxFailedNumber = 0;
    Integer failedNumber = 0;
    
    try {
      userLockStateService.loginFailed(username);
    } catch (Exception e1) {
      log.error(String.format("User login failure record exception; usernsme:%s.", username), e1);
    }
    try {
      failedNumber = userLockStateService.getFailedNumber(username);
    } catch (Exception e1) {
      log.error(String.format("Get user failed number exception; usernsme:%s.", username), e1);
    }
    maxFailedNumber = userLockStateService.getMaxFailedNumber();
    if (failedNumber == maxFailedNumber) {
      //��ʾ����ʱ��
      Date unlockDate = userLockStateService.getUnlockTime(username);
      String errorKeyArgs = null;
      if(unlockDate != null){
        long unlockTime = unlockDate.getTime() - (new Date().getTime());
        int unlockM =  Long.valueOf(unlockTime / 60000).intValue();
        if ((unlockTime % 60000) >= 30000 || unlockM == 0) {
          unlockM++;
        }
        errorKeyArgs = maxFailedNumber + "," + unlockM;
      }
      throw new DetailLoginException("login.form.error.username.locked", errorKeyArgs, String.format("Username is locked, username:%s", username));
    } else {
      throw new DetailLoginException("login.form.error.userpass.invalidNumber", failedNumber.toString() + "," + maxFailedNumber.toString(), String.format("Authenticate failed.The current failure %s time, common %s chance.",
          failedNumber.toString(), maxFailedNumber.toString()));
    }
  }

  /**
   * @description �ύ��֤���̵ķ���
   * @return boolean ����˷����ɹ����򷵻� true�����Ӧ�ú��Դ� LoginModule���򷵻�
   *         false������ύʧ�����׳��쳣��Ϣ
   * @throws LoginException
   *           ����ύʧ��
   */
  public boolean commit() throws LoginException {

    if (!authenticated) {
      committed = false;
      log.debug("Login failed");
      return false;
    }

    if (this.subject.isReadOnly()) {
      clearState();
      throw new LoginException("Subject is read-only.");
    }

    subject.getPrincipals().addAll(principals);
    clearState();
    committed = true;
    return true;
  }

  /**
   * @description ��ȡ�û������������
   * @return AbstractUserLockService
   */
  private AbstractUserLockService getUserLockStateService() {
    return this.applicationContext.getBean(this.userLockServiceBeanId, AbstractUserLockService.class);
  }

}
