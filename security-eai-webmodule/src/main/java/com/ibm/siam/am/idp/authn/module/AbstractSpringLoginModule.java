package com.ibm.siam.am.idp.authn.module;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 用户登录身份验证（实现Spring容器）
 * 
 * @author zhangxianwen
 * @since 2012-6-19 下午4:40:53
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
   * 由于ShareState的存活期仅仅在一个LoginContext存活周期内，如果需要更大范围共享一些状态，则需要使用此调用来设置相关参数.
   * @param key
   * @param value
   */
  protected void setSessionLevelState(Object key, Object value) {
    Map map = getAllSessionLevelState(this.sharedState);
    map.put(key, value);
  }
  
  /**
   * 由于ShareState的存活期仅仅在一个LoginContext存活周期内，如果需要更大范围共享一些状态，则需要使用此调用来提取相关参数.
   * @param key
   * @param value
   */
  protected Object getSessionLevelState(Object key) {
    Map map = getAllSessionLevelState(this.sharedState);
    return map.get(key);
  }
  
  /**
   * 获取所有Session级别共享的数据
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
   * 保存所有的SessionState到LoginModule的ShareState
   * @param loginModuleShareState
   * @param sessionShareState
   */
  public static void setAllSessionLevelState(Map loginModuleShareState, Map sessionShareState) {
    loginModuleShareState.put(SESSION_LEVEL_STATE, sessionShareState);
  }
}
