package com.ibm.siam.am.idp.authn.module;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * �û���¼�����֤��ʵ��Spring������
 * 
 * @author zhangxianwen
 * @since 2012-6-19 ����4:40:53
 */

public abstract class AbstractSpringLoginModule extends AbstractLoginModule implements ApplicationContextAware {
  
  /** Request attribute to which user's principal password should be bound. */
  public static final String PRINCIPAL_PASSWORD_KEY = "principal_password";

  /** Request attribute to which user's principal DN should be bound. */
  public static final String PRINCIPAL_DN_KEY = "principal_dn";

  /** Request attribute to which user's principal name should be bound. */
  public static final String PRINCIPAL_NAME_KEY = "principal_name";

  public static final String SESSION_LEVEL_STATE = "_SESSION_LEVEL_STATE_";
  
  /**
   * Spring Bean Factory
   */
  protected ApplicationContext applicationContext = null;

  /** {@inheritDoc} */
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
  
  /**
   * ����ShareState�Ĵ���ڽ�����һ��LoginContext��������ڣ������Ҫ����Χ����һЩ״̬������Ҫʹ�ô˵�����������ز���.
   * @param key
   * @param value
   */
  protected void setSessionLevelState(Object key, Object value) {
    Map map = getAllSessionLevelState(this.sharedState);
    map.put(key, value);
  }
  
  /**
   * ����ShareState�Ĵ���ڽ�����һ��LoginContext��������ڣ������Ҫ����Χ����һЩ״̬������Ҫʹ�ô˵�������ȡ��ز���.
   * @param key
   * @param value
   */
  protected Object getSessionLevelState(Object key) {
    Map map = getAllSessionLevelState(this.sharedState);
    return map.get(key);
  }
  
  /**
   * ��ȡ����Session�����������
   * @param shareState
   * @return
   */
  public static Map<?, ?> getAllSessionLevelState(Map shareState) {
    Map<?, ?> map = (Map<Object, Object>)shareState.get(SESSION_LEVEL_STATE);
    if (map == null) {
       map = new HashMap<Object, Object>();
       shareState.put(SESSION_LEVEL_STATE, map);
    }
    return (Map<?, ?>) shareState.get(SESSION_LEVEL_STATE);
  }
  
  /**
   * �������е�SessionState��LoginModule��ShareState
   * @param loginModuleShareState
   * @param sessionShareState
   */
  public static void setAllSessionLevelState(Map loginModuleShareState, Map sessionShareState) {
    loginModuleShareState.put(SESSION_LEVEL_STATE, sessionShareState);
  }
}
