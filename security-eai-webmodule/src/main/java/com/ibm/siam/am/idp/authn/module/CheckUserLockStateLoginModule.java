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

import com.ibm.siam.am.idp.authn.service.AbstractUserLockService;
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

public class CheckUserLockStateLoginModule extends AbstractSpringLoginModule {

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

    NameCallback nameCallback = new NameCallback("User Name: ");

    Callback[] callbacks = new Callback[] {nameCallback};
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
    boolean userLocked = false;
    try {
      userLocked = userLockStateService.isLocked(username);
    } catch (Exception e) {
      log.error("Get User state exception", e);
      throw new DetailLoginException("login.form.error.username.stateErr", String.format("Get User state exception:%s.", e.getMessage()));
    }
    if (userLocked) {
      log.info(String.format("User are being locked,usernsme:%s.", username));
      // ��ʾ����ʱ��
      Date unlockDate = userLockStateService.getUnlockTime(username);
      String errorKeyArgs = null;
      if (unlockDate != null) {
        long unlockTime = unlockDate.getTime() - (new Date().getTime());
        int unlockM = Long.valueOf(unlockTime / 60000).intValue();
        if ((unlockTime % 60000) >= 30000 || unlockM == 0) {
          unlockM++;
        }
        errorKeyArgs = userLockStateService.getMaxFailedNumber() + "," + unlockM;
      }
      throw new DetailLoginException("login.form.error.username.locked", errorKeyArgs, String.format("User are being locked,usernsme:%s.", username));
    }

    // �û�У�飬ͨ��LADP��ѯ������Աuid��Ϣ���洢Subject
    UserPrincipal principal = new UserPrincipal();
    principal.setName(username);

    principals.add(principal);

    authenticated = true;
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
