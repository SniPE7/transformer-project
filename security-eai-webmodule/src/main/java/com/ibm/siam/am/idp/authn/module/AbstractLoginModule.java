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
 * @description �û���¼�����֤
 * @author zhangxianwen
 * @version 1.0
 * @Copyright (c) 2012
 * @create 2012-03-12 16��31
 */

public abstract class AbstractLoginModule implements LoginModule {

  /** Constant for login name stored in shared state. */
  public static final String LOGIN_NAME = "javax.security.auth.login.name";
  
  /** Constant for entryDn stored in shared state. */
  public static final String LOGIN_DN = "edu.vt.middleware.ldap.jaas.login.entryDn";
  
  /** Constant for login password stored in shared state. */
  public static final String LOGIN_PASSWORD = "javax.security.auth.login.password";
  
  public static final String LOGIN_USER_ACCOUNT_PRINCIPAL = "login.user.account.principal";
  
  /** ��¼��֤ԴKEY */
  public static String LOGIN_AUTH_SOURCE_KEY = "LOGIN_AUTH_SOURCE_KEY";
  
  /** ��¼��֤״̬��KEY */
  public static String LOGIN_AUTH_CODE_KEY = "LOGIN_AUTH_CODE_KEY";
  
  /** ��¼��֤�ɹ� */
  public static int LOGIN_AUTH_SUCCESS = 1000;
  
  /** ��¼�û������� */
  public static int LOGIN_USER_NOT_EXISTS = 1001;
  
  /** ��¼��֤ʧ�ܣ�������� */
  public static int LOGIN_AUTH_PASS_ERROR = 1002;
  
  protected static final String PERSON_STATUS = "PersonStatus";
  
  /* Ҫ������֤�� Subject */
  protected Subject subject;
  /* �����������û�ͨ�ŵ� CallbackHandler */
  protected CallbackHandler callbackHandler;
  /* �����������õ� LoginModule �����״̬ */
  protected Map sharedState;
  /* �ڵ�¼ Configuration ��Ϊ���ض��� LoginModule ָ����ѡ�� */
  protected Map<String, ?> options;

  /* Ҫ��֤�����ʵ����� */
  protected Set<Principal> principals;

  /* ����֤��ʶ */
  protected boolean authenticated = false;
  /* ���ύ��ʶ */
  protected boolean committed = false;

  /* ��־������� */
  protected Log log = LogFactory.getLog(this.getClass());

  /**
   * @description ��ʼ���� LoginModule
   * @param subject
   *          Ҫ������֤�� Subject
   * @param callbackHandler
   *          �����������û�ͨ�ŵ� CallbackHandler�����磬��ʾҪ���û��������룩
   * @param sharedState
   *          �����������õ� LoginModule �����״̬
   * @param options
   *          �ڵ�¼ Configuration ��Ϊ���ض��� LoginModule ָ����ѡ��
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
   * @description ��ֹ��֤���̵ķ���
   * @return boolean ����˷����ɹ����򷵻� true�����Ӧ�ú��Դ� LoginModule���򷵻�
   *         false�������ֹʧ�����׳��쳣��Ϣ
   * @throws LoginException
   *           �����ֹʧ��
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
   * @description ע�� Subject �ķ���
   * @return ����˷����ɹ����򷵻� true�����Ӧ�ú��Դ� LoginModule���򷵻� false�����ע��ʧ�����׳��쳣��Ϣ
   * @throws LoginException
   *           ���ע��ʧ��
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
