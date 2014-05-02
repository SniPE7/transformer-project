package com.ibm.siam.am.idp.authn.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户锁定/解锁服务
 * @author zhangxianwen
 * @since 2012-6-18 下午4:06:09
 */

public abstract class AbstractUserLockService {
  
  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(AbstractUserLockService.class);
  
  /** 用户锁定信息本地缓存 */
  protected Map<String, UserLockState> userLockMap = new HashMap<String, UserLockState>();
  
  /** 最大失败次数 */
  protected int maxFailedNumber;
  
  /** 解锁时间时隔 */
  protected long unLockTimeInterval;
  
  /** userName对应ldap属性名称 */
  protected String userNameAttr;

  /** unlockTime对应ldap属性名称 */
  protected String unlockTimeAttr;
  
  /** 解锁时间格式 */
  protected String unlockTimeFormat;
  
  /** 登录失败次数failedNumber对应ldap属性名称 */
  protected String failedNumberAttr;

  /** lockState对应ldap属性名称 */
  protected String lockStateAttr;

  /**
   * 用户解锁
   * @param userLockState 用户锁定信息实例对象
   */
  public abstract void unLock(UserLockState userLockState);
  
  /**
   * 获取用户锁定信息
   * @param username 用户名
   * @return UserLockState 用户锁定信息实例对象，如果对象不存在刚返回null
   * @throws Exception 
   */
  public abstract UserLockState getUser(String username) throws Exception;
  
  /**
   * 保存用户锁定信息
   * @param userLockState 用户锁定信息实例对象
   */
  protected abstract void saveUserLockState(UserLockState userLockState);
  
  /**
   * 用户登录失败
   * @param username 用户名
   * @throws Exception 
   */
  public void loginFailed(String username) throws Exception{
    log.debug(String.format("loginFailed by username is %s", username));
    
    UserLockState userLockState = getUser(username);
    if(userLockState == null){
      userLockState = new UserLockState();
      userLockState.setValue(userNameAttr, username);
    }
    String failedNumber = userLockState.getValueAsString(failedNumberAttr);
    
    if(failedNumber == null){
      userLockState.setValue(failedNumberAttr, 1);
    }else{
      userLockState.setValue(failedNumberAttr, Integer.parseInt(failedNumber) + 1);
    }
    saveUserLockState(userLockState);
  }
  
  /**
   * 用户是否锁定
   * @param username 用户名
   * @return 锁定：true，未锁定：false
   * @throws Exception 
   */
  public boolean isLocked(String username) throws Exception{
    log.debug(String.format("isLocked by username is %s", username));
    UserLockState userLockState = getUser(username);
    if(userLockState == null){
      return false;
    }
    String lockState = userLockState.getValueAsString(lockStateAttr);
    if(lockState == null || !Boolean.parseBoolean(lockState)){
      return false;
    }
    //用户解锁
    SimpleDateFormat df = new SimpleDateFormat(unlockTimeFormat);
    String unLockTime = userLockState.getValueAsString(unlockTimeAttr);
    String nowTime = df.format(new Date());
    if(unLockTime.compareTo(nowTime) > 0){
      return true;
    }
    unLock(userLockState);
    return false;
  }
  
  /**
   * 根据用户名获取用户解锁时间
   * @param username 用户名
   * @return Date 解决时间
   */
  public Date getUnlockTime(String username){
    log.debug(String.format("getUnlockTime by username is %s", username));
    UserLockState userLockState;
    try {
      userLockState = getUser(username);
    } catch (Exception e) {
      log.warn(String.format("getUser by name: [%s]", username), e);
      return null;
    }
    if(userLockState == null){
      return null;
    }
    String unLockTime = userLockState.getValueAsString(unlockTimeAttr);
    if(unLockTime == null){
      return null;
    }
    //用户解锁
    SimpleDateFormat df = new SimpleDateFormat(unlockTimeFormat);
    try {
      return df.parse(unLockTime);
    } catch (ParseException e) {
      log.warn(String.format("Failure to parse date string: [%s], pattern: [%s]", unLockTime, unlockTimeFormat), e);
      return null;
    }
  }
  
  /**
   * 获取用户登录失败次数
   * @param username 用户名
   * @return int 登录失败次数
   * @throws Exception
   */
  public int getFailedNumber(String username) throws Exception{
    log.debug(String.format("getFailedNumber by username is %s", username));
    UserLockState userLockState = getUser(username);
    if(userLockState == null){
      return 0;
    }
    String failedNumber = userLockState.getValueAsString(failedNumberAttr);
    if(failedNumber == null){
      return 0;
    }
    return Integer.parseInt(failedNumber);
  }
  
  /**
   * 解锁所有在解锁期的用户
   */
  public void unLockAll(){
    Object[] keyArr = userLockMap.keySet().toArray();
    for(Object key : keyArr){
      UserLockState userLockState = userLockMap.get(key);
      String lockState = userLockState.getValueAsString(lockStateAttr);
      if(lockState == null || !Boolean.parseBoolean(lockState)){
        continue;
      }
      SimpleDateFormat df = new SimpleDateFormat(unlockTimeFormat);
      String unLockTime = userLockState.getValueAsString(unlockTimeAttr);
      String nowTime = df.format(new Date());
      if(unLockTime.compareTo(nowTime) > 0){
        continue;
      }
      unLock(userLockState);
    }
  }
  
  public int getMaxFailedNumber() {
    return maxFailedNumber;
  }

  public void setMaxFailedNumber(int maxFailedNumber) {
    this.maxFailedNumber = maxFailedNumber;
  }

  public long getUnLockTimeInterval() {
    return unLockTimeInterval;
  }

  public void setUnLockTimeInterval(long unLockTimeInterval) {
    this.unLockTimeInterval = unLockTimeInterval;
  }

  /**
   * @return the userNameAttr
   */
  public String getUserNameAttr() {
    return userNameAttr;
  }

  /**
   * @param userNameAttr the userNameAttr to set
   */
  public void setUserNameAttr(String userNameAttr) {
    this.userNameAttr = userNameAttr;
  }

  /**
   * @return the unlockTimeAttr
   */
  public String getUnlockTimeAttr() {
    return unlockTimeAttr;
  }

  /**
   * @param unlockTimeAttr the unlockTimeAttr to set
   */
  public void setUnlockTimeAttr(String unlockTimeAttr) {
    this.unlockTimeAttr = unlockTimeAttr;
  }

  /**
   * @return the unlockTimeFormat
   */
  public String getUnlockTimeFormat() {
    return unlockTimeFormat;
  }

  /**
   * @param unlockTimeFormat the unlockTimeFormat to set
   */
  public void setUnlockTimeFormat(String unlockTimeFormat) {
    this.unlockTimeFormat = unlockTimeFormat;
  }

  /**
   * @return the failedNumberAttr
   */
  public String getFailedNumberAttr() {
    return failedNumberAttr;
  }

  /**
   * @param failedNumberAttr the failedNumberAttr to set
   */
  public void setFailedNumberAttr(String failedNumberAttr) {
    this.failedNumberAttr = failedNumberAttr;
  }

  /**
   * @return the lockStateAttr
   */
  public String getLockStateAttr() {
    return lockStateAttr;
  }

  /**
   * @param lockStateAttr the lockStateAttr to set
   */
  public void setLockStateAttr(String lockStateAttr) {
    this.lockStateAttr = lockStateAttr;
  }

}
