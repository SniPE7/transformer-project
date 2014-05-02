package com.ibm.siam.am.idp.authn.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * �û�����/��������
 * @author zhangxianwen
 * @since 2012-6-18 ����4:06:09
 */

public abstract class AbstractUserLockService {
  
  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(AbstractUserLockService.class);
  
  /** �û�������Ϣ���ػ��� */
  protected Map<String, UserLockState> userLockMap = new HashMap<String, UserLockState>();
  
  /** ���ʧ�ܴ��� */
  protected int maxFailedNumber;
  
  /** ����ʱ��ʱ�� */
  protected long unLockTimeInterval;
  
  /** userName��Ӧldap�������� */
  protected String userNameAttr;

  /** unlockTime��Ӧldap�������� */
  protected String unlockTimeAttr;
  
  /** ����ʱ���ʽ */
  protected String unlockTimeFormat;
  
  /** ��¼ʧ�ܴ���failedNumber��Ӧldap�������� */
  protected String failedNumberAttr;

  /** lockState��Ӧldap�������� */
  protected String lockStateAttr;

  /**
   * �û�����
   * @param userLockState �û�������Ϣʵ������
   */
  public abstract void unLock(UserLockState userLockState);
  
  /**
   * ��ȡ�û�������Ϣ
   * @param username �û���
   * @return UserLockState �û�������Ϣʵ������������󲻴��ڸշ���null
   * @throws Exception 
   */
  public abstract UserLockState getUser(String username) throws Exception;
  
  /**
   * �����û�������Ϣ
   * @param userLockState �û�������Ϣʵ������
   */
  protected abstract void saveUserLockState(UserLockState userLockState);
  
  /**
   * �û���¼ʧ��
   * @param username �û���
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
   * �û��Ƿ�����
   * @param username �û���
   * @return ������true��δ������false
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
    //�û�����
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
   * �����û�����ȡ�û�����ʱ��
   * @param username �û���
   * @return Date ���ʱ��
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
    //�û�����
    SimpleDateFormat df = new SimpleDateFormat(unlockTimeFormat);
    try {
      return df.parse(unLockTime);
    } catch (ParseException e) {
      log.warn(String.format("Failure to parse date string: [%s], pattern: [%s]", unLockTime, unlockTimeFormat), e);
      return null;
    }
  }
  
  /**
   * ��ȡ�û���¼ʧ�ܴ���
   * @param username �û���
   * @return int ��¼ʧ�ܴ���
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
   * ���������ڽ����ڵ��û�
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
