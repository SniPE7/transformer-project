package group.tivoli.security.eai.loginmodule.tam;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import com.sinopec.siam.am.idp.authn.module.AbstractSpringLoginModule;
import com.sinopec.siam.am.idp.authn.module.DetailLoginException;
import com.sinopec.siam.am.idp.authn.principal.UserPrincipal;
import com.sinopec.siam.am.idp.authn.service.AbstractUserLockService;

/**
 * 用户锁定状态检查LoginModule
 * <p>
 * 对已锁定的用户，在自动解锁期内，登录失败
 * </p>
 * 
 * @author zhangxianwen
 * @since 2012-6-18 上午11:29:08
 */

public class CheckUserLockStateLoginModule extends AbstractSpringLoginModule {

  /** 用户锁定服务Bean ID */
  private String userLockServiceBeanId = null;
  
  private boolean isHintUnlockTime = false;

  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    this.userLockServiceBeanId = (String) options.get("userLockServiceBeanId");
    if (this.userLockServiceBeanId == null || "".equals(this.userLockServiceBeanId)) {
      throw new RuntimeException("userLockServiceBeanId is null");
    }
    
    String HintUnlockTime = (String) options.get("userLockServiceBeanId");
    if(null!=HintUnlockTime && "true".equals(HintUnlockTime.toLowerCase())){
      isHintUnlockTime = Boolean.parseBoolean(HintUnlockTime);
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
      
      // 提示解锁时间
      Date unlockDate = userLockStateService.getUnlockTime(username);
      String errorKeyArgs = "" + userLockStateService.getMaxFailedNumber();
      
      if (isHintUnlockTime && unlockDate != null) {
        long unlockTime = unlockDate.getTime() - (new Date().getTime());
        int unlockM = Long.valueOf(unlockTime / 60000).intValue();
        if ((unlockTime % 60000) >= 30000 || unlockM == 0) {
          unlockM++;
        }
        errorKeyArgs = userLockStateService.getMaxFailedNumber() + "," + unlockM;
      }
      
      throw new DetailLoginException("login.form.error.username.locked", errorKeyArgs, String.format("User are being locked,usernsme:%s.", username));
    }

    // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
    UserPrincipal principal = new UserPrincipal();
    principal.setName(username);

    principals.add(principal);

    authenticated = true;
    return true;
  }

  /**
   * @description 获取用户锁定服务对象
   * @return AbstractUserLockService
   */
  private AbstractUserLockService getUserLockStateService() {
    return this.applicationContext.getBean(this.userLockServiceBeanId, AbstractUserLockService.class);
  }

}

