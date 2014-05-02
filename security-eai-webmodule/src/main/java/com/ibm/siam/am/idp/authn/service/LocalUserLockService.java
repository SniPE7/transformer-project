package com.ibm.siam.am.idp.authn.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * �����û�����/����״̬����ʵ����
 * @author zhangxianwen
 * @since 2012-6-18 ����4:54:06
 */

public class LocalUserLockService extends AbstractUserLockService {

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(LocalUserLockService.class);
  
  /** {@inheritDoc} */
  @Override
  public void unLock(UserLockState userLockState) {
    String username = userLockState.getValueAsString(userNameAttr);
    log.debug(String.format("unLock username is %s", username));
    if(userLockMap.containsKey(username)){
      userLockMap.remove(username);
    }
  }

  /** {@inheritDoc} */
  @Override
  public UserLockState getUser(String username) {
    log.debug(String.format("getUser by username is %s", username));
    return userLockMap.get(username);
  }

  /** {@inheritDoc} */
  @Override
  protected void saveUserLockState(UserLockState userLockState) {
    String username = userLockState.getValueAsString(userNameAttr);
    log.debug(String.format("saveUserLockState; username:%s", username));
    //�����û�
    int failedNumber = Integer.parseInt(userLockState.getValueAsString(failedNumberAttr));
    if(failedNumber == maxFailedNumber){
      userLockState.setValue(lockStateAttr, true);
      SimpleDateFormat df = new SimpleDateFormat(unlockTimeFormat);
      Date date = new Date();
      date.setTime(date.getTime() + unLockTimeInterval);
      userLockState.setValue(unlockTimeAttr, df.format(date));
    }
    userLockMap.put(username, userLockState);
  }

}
