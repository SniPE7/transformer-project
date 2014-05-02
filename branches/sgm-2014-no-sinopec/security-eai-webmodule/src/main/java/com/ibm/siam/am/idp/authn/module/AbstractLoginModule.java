package com.ibm.siam.am.idp.authn.module;

import java.security.Principal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @description 用户登录身份验证
 * @author zhangxianwen
 * @version 1.0
 * @Copyright (c) 2012
 * @create 2012-03-12 16：31
 */

public abstract class AbstractLoginModule implements LoginModule {

  /** Constant for login name stored in shared state. */
  public static final String LOGIN_NAME = "javax.security.auth.login.name";
  
  /** Constant for entryDn stored in shared state. */
  public static final String LOGIN_DN = "edu.vt.middleware.ldap.jaas.login.entryDn";
  
  /** Constant for login password stored in shared state. */
  public static final String LOGIN_PASSWORD = "javax.security.auth.login.password";
  
  public static final String LOGIN_USER_ACCOUNT_PRINCIPAL = "login.user.account.principal";
  
  /** 登录认证源KEY */
  public static String LOGIN_AUTH_SOURCE_KEY = "LOGIN_AUTH_SOURCE_KEY";
  
  /** 登录认证状态码KEY */
  public static String LOGIN_AUTH_CODE_KEY = "LOGIN_AUTH_CODE_KEY";
  
  /** 登录认证成功 */
  public static int LOGIN_AUTH_SUCCESS = 1000;
  
  /** 登录用户不存在 */
  public static int LOGIN_USER_NOT_EXISTS = 1001;
  
  /** 登录认证失败（密码错误） */
  public static int LOGIN_AUTH_PASS_ERROR = 1002;
  
  protected static final String PERSON_STATUS = "PersonStatus";
  
  /* 要进行验证的 Subject */
  protected Subject subject;
  /* 用来与最终用户通信的 CallbackHandler */
  protected CallbackHandler callbackHandler;
  /* 与其他已配置的 LoginModule 共享的状态 */
  protected Map sharedState;
  /* 在登录 Configuration 中为此特定的 LoginModule 指定的选项 */
  protected Map<String, ?> options;

  /* 要验证对象的实体对象集 */
  protected Set<Principal> principals;

  /* 已认证标识 */
  protected boolean authenticated = false;
  /* 已提交标识 */
  protected boolean committed = false;

  /* 日志输出对象 */
  protected Log log = LogFactory.getLog(this.getClass());

  /**
   * @description 初始化此 LoginModule
   * @param subject
   *          要进行验证的 Subject
   * @param callbackHandler
   *          用来与最终用户通信的 CallbackHandler（例如，提示要求用户名和密码）
   * @param sharedState
   *          与其他已配置的 LoginModule 共享的状态
   * @param options
   *          在登录 Configuration 中为此特定的 LoginModule 指定的选项
   */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    this.subject = subject;
    this.callbackHandler = callbackHandler;
    this.sharedState = sharedState;
    this.options = options;
    this.principals = new HashSet<Principal>();
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
   * @description 中止验证过程的方法
   * @return boolean 如果此方法成功，则返回 true；如果应该忽略此 LoginModule，则返回
   *         false；如果中止失败则抛出异常信息
   * @throws LoginException
   *           如果中止失败
   */
  public boolean abort() throws LoginException {
    if (!authenticated) {
      return false;
    } else if (authenticated && !committed) {
      authenticated = false;
      clearState();
    } else {
      logout();
    }

    return true;
  }

  /**
   * @description 注销 Subject 的方法
   * @return 如果此方法成功，则返回 true；如果应该忽略此 LoginModule，则返回 false；如果注销失败则抛出异常信息
   * @throws LoginException
   *           如果注销失败
   */
  public boolean logout() throws LoginException {
    if (subject.isReadOnly()) {
      clearState();
      throw new LoginException("Subject is read-only.");
    }

    final Iterator<Principal> prinIter = this.subject.getPrincipals().iterator();
    while (prinIter.hasNext()) {
      this.subject.getPrincipals().remove(prinIter.next());
    }

    final Iterator<Object> credIter = this.subject.getPrivateCredentials().iterator();
    while (credIter.hasNext()) {
      this.subject.getPrivateCredentials().remove(credIter.next());
    }

    clearState();
    authenticated = false;
    committed = false;
    return true;
  }

  /**
   * Removes any stateful principals, credentials, or roles stored by login.
   * Also removes shared state name, dn, and password if clearPass is set.
   */
  protected void clearState() {
    principals.clear();
  }
}
