package group.tivoli.security.eai.loginmodule.tam.service;

import java.util.List;

import javax.naming.directory.ModificationItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinopec.siam.am.idp.authn.service.UserLockState;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;

/**
 * TIM USER LDAP 锁操作服务
 * 
 * @author xuhong
 * @since 2012-12-7 上午10:31:21
 */

public class TimUserLockService extends LdapUserLockService {

  private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

  private final static String PEOPLE_STATE = "erpersonstatus";
  private final static String ACCOUNT_STATE = "eraccountstatus";

  private final static String LOCK_VALUE = "1";
  private final static String UNLOCK_VALUE = "0";

  private final static String OWNER_ATTRIBUTE_NAME = "owner";

  private TimUserLdapUtil timUserLdapUtil;
  
  public TimUserLdapUtil getTimUserLdapUtil() {
    return timUserLdapUtil;
  }

  public void setTimUserLdapUtil(TimUserLdapUtil timUserLdapUtil) {
    this.timUserLdapUtil = timUserLdapUtil;
  }

  private void changePeopleStatus(LdapUserEntity entity, String status) {
    timUserLdapUtil.modifyEntity(entity,
        new ModificationItem[] { timUserLdapUtil.getOneModificationItem(PEOPLE_STATE, status) });
  }

  private void changeAccountStatus(LdapUserEntity entity, String status) {
    timUserLdapUtil.modifyEntity(entity,
        new ModificationItem[] { timUserLdapUtil.getOneModificationItem(ACCOUNT_STATE, status) });
  }
  
  public void init(){
    super.init();
    
    //from tim ldap get maxfailnumber
    String maxCount = getTimConfigMaxFailCount();
    if(maxCount!=null && !"".equals(maxCount.trim())){
      this.maxFailedNumber = Integer.parseInt(maxCount);
    }
  }

  private void changeAllStatus(String uid, String status) {
    // get people
    LdapUserEntity people = timUserLdapUtil.getPeople(uid);
    
    if(people==null){
      log.error(String.format("not find the people [%s] in tim ldap.", uid));
      throw new RuntimeException(String.format("not find the people [%s] in tim ldap.", uid));
    }

    // unlock people
    changePeopleStatus(people, status);

    // get owner
    String owner = people.getDn();

/*    // get systemuser
    LdapUserEntity systemUser = timUserLdapUtil.getSystemUserByOwner(owner);

    // unlock systemuser
    changeAccountStatus(systemUser, status);*/

    // get accounts
    List<LdapUserEntity> accounts = timUserLdapUtil.getAccountsByOwner(owner);

    // unlock accounts
    if (null != accounts) {
      for (LdapUserEntity account : accounts) {
        changeAccountStatus(account, status);
      }
    }
  }

  public void timUnlockAll(String uid) {
    changeAllStatus(uid, UNLOCK_VALUE);
  }

  public void timLockAll(String uid) {
    changeAllStatus(uid, LOCK_VALUE);
  }
  
  /** {@inheritDoc} */
  @Override
  protected void saveUserLockState(UserLockState userLockState) {
    int failedNumber = Integer.parseInt(userLockState.getValueAsString(failedNumberAttr));

    super.saveUserLockState(userLockState);
    
    String username = userLockState.getValueAsString(userNameAttr);
    if(log.isDebugEnabled()){
      log.debug(String.format("tim ldap status saveUserLockState; username:%s, userDn:%s,lockState:%s,unLockTime:%s",
        username, userLockState.getDn(), userLockState.getValueAsString(lockStateAttr), userLockState.getValueAsString(unlockTimeAttr)));
    }
    //锁定用户
    if(failedNumber >= maxFailedNumber && !isPrivilegeAccount(username)){
      timLockAll(username);
    }
    
  }
  
  public String getTimConfigMaxFailCount(){
    try {
      LdapUserEntity config = timUserLdapUtil.getTimConfigureEntity();
      if (config != null) {
        return config.getValueAsString("erlogoncount");
      }
    } catch (Exception e) {
      log.error("获取TIM　LDAP密码失败次数最大配置值失败：", e);
    }
    return "";
  }
  
  public String getTimConfigPassExpiration(){
    try {
      LdapUserEntity config = timUserLdapUtil.getTimConfigureEntity();
      if (config != null) {
        return config.getValueAsString("erpswdexpirationperiod");
      }
    } catch (Exception e) {
      log.error("获取TIM　LDAP密码失败次数最大配置值失败：", e);
    }
    return "";
  }

}
