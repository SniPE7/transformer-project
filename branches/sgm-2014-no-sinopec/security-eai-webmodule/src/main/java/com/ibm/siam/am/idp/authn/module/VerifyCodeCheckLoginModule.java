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

import com.ibm.siam.am.idp.authn.principal.UserPrincipal;
import com.ibm.siam.am.idp.authn.provider.FormOperationCallback;
import com.ibm.siam.am.idp.authn.provider.KaptachaCallback;

/**
 * 验证码校验LoginModule
 * <p>
 * 对用户输入的验证码和session中的验证码进行比较，如果不一致则登录失败
 * </p>
 * 
 * @author zhoutengfei
 * @since 2012-8-18 上午11:53:08
 */

public class VerifyCodeCheckLoginModule extends AbstractSpringLoginModule {

  /** 登录操作的标识 */
  private String loginOptFlag = "login";

  /** 万能验证码 */
  private String powerfulVerifyCode = "null";

  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    if (options.get("powerfulVerifyCode") != null) {
      this.powerfulVerifyCode = (String) options.get("powerfulVerifyCode");
    }

    if (options.get("loginOptFlag") != null) {
      this.loginOptFlag = (String) options.get("loginOptFlag");
    }
  }

  /** {@inheritDoc} */
  /* (non-Javadoc)
   * @see javax.security.auth.spi.LoginModule#login()
   */
  public boolean login() throws LoginException {
    if (callbackHandler == null) {
      log.error("No CallbackHandler");
      return false;
    }

    NameCallback nameCallback = new NameCallback("User Name: ");
    KaptachaCallback kaptachaCallback = new KaptachaCallback();
    FormOperationCallback formOperationCallback = new FormOperationCallback(this.loginOptFlag);

    Callback[] callbacks = new Callback[] { nameCallback, kaptachaCallback, formOperationCallback };
    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return false;
    } catch (UnsupportedCallbackException e) {
      log.error(e.getMessage(), e);
      return false;
    }

    String checkcode = kaptachaCallback.getCodeFromInput();
    String checkCodeCache = kaptachaCallback.getKaptachaCode();
    log.debug(String.format("verifyCheckCode checkCode:%s, checkCodeCache:%s.", checkcode, checkCodeCache));
    String opt = formOperationCallback.getOperation();

    if (opt != null && !loginOptFlag.equals(opt) && checkcode == null) {
      checkcode = checkCodeCache;
    }

    if (StringUtils.isEmpty(checkcode)) {
      throw new DetailLoginException("login.form.error.checkCodeIsNull", String.format("Could not get checkcode from user request, missing check code"));
    }
    if (checkcode.equalsIgnoreCase(checkCodeCache) || checkcode.equals(powerfulVerifyCode)) {
      // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
      UserPrincipal principal = new UserPrincipal();
      principal.setName(nameCallback.getName());

      principals.add(principal);
      authenticated = true;
      return true;
    } else {
      if (checkCodeCache == null || "".equals(checkCodeCache)) {
        throw new DetailLoginException("login.form.error.checkCodeCacheIsNull", String.format("Could not get pre-generated-checkcode from user session, missing pre-generated-checkcode."));
      } else {
        throw new DetailLoginException("login.form.error.checkCodeFailed", String.format("Checkcode not match, pre-gen-code: [%s], user-code: [%s], powerful-code: [%s]", checkCodeCache, checkcode, powerfulVerifyCode));
      }
    }

  }

}
