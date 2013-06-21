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

import org.apache.commons.lang.StringUtils;

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

public class UpdateUserLockStateLoginModule extends AbstractSpringLoginModule {

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

    // 检查之前是否已经认证成功
    String loginUsername = (String) this.sharedState.get(LOGIN_NAME);
    if (!StringUtils.isEmpty(loginUsername)) {
      // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
      UserPrincipal principal = new UserPrincipal();
      principal.setName(loginUsername);
      principals.add(principal);
      authenticated = true;
      
      //清理失败状态
      AbstractUserLockService userLockStateService = getUserLockStateService();
      try {
        userLockStateService.cleanState(loginUsername);
      } catch (Exception e) {
          log.error(e.getMessage(), e);
      }
      
      return true;
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

    loginUsername = nameCallback.getName();

    Integer maxFailedNumber = 0;
    Integer failedNumber = 0;
    AbstractUserLockService userLockStateService = getUserLockStateService();
    try {
      failedNumber = userLockStateService.getFailedNumber(loginUsername);
      if(failedNumber!=null){
        failedNumber += 1;
      }else{
        failedNumber = 1;
      }
    } catch (Exception e1) {
      log.error(String.format("Get user failed number exception; usernsme:%s.", loginUsername), e1);
    }
    
    try {
      
      userLockStateService.loginFailed(loginUsername);
    } catch (Exception e1) {
      log.error(String.format("User login failure record exception; usernsme:%s.", loginUsername), e1);
    }

    maxFailedNumber = userLockStateService.getMaxFailedNumber();
    if (failedNumber == maxFailedNumber) {
      //提示解锁时间
      Date unlockDate = userLockStateService.getUnlockTime(loginUsername);
      String errorKeyArgs = maxFailedNumber.toString();
      
      if(isHintUnlockTime && unlockDate != null){
        long unlockTime = unlockDate.getTime() - (new Date().getTime());
        int unlockM =  Long.valueOf(unlockTime / 60000).intValue();
        if ((unlockTime % 60000) >= 30000 || unlockM == 0) {
          unlockM++;
        }
        
        errorKeyArgs = maxFailedNumber + "," + unlockM;
      }
      
      throw new DetailLoginException("login.form.error.username.locked", errorKeyArgs, String.format("Username is locked, username:%s", loginUsername));
    } else {
      throw new DetailLoginException("login.form.error.userpass.invalidNumber", failedNumber.toString() + "," + maxFailedNumber.toString(), String.format("Authenticate failed.The current failure %s time, common %s chance.",
          failedNumber.toString(), maxFailedNumber.toString()));
    }
  }

  /**
   * @description 提交验证过程的方法
   * @return boolean 如果此方法成功，则返回 true；如果应该忽略此 LoginModule，则返回
   *         false；如果提交失败则抛出异常信息
   * @throws LoginException
   *           如果提交失败
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
   * @description 获取用户锁定服务对象
   * @return AbstractUserLockService
   */
  private AbstractUserLockService getUserLockStateService() {
    return this.applicationContext.getBean(this.userLockServiceBeanId, AbstractUserLockService.class);
  }

}

