package group.tivoli.security.eai.loginmodule.tam.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.ModificationItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import com.sinopec.siam.am.idp.authn.service.AbstractUserLockService;
import com.sinopec.siam.am.idp.authn.service.UserLockState;
import com.sinopec.siam.am.idp.ldap.DirectoryEntity2ModifyAttributeConverter;
import com.sinopec.siam.am.idp.ldap.DirectoryEntityContextMapper;
import com.sinopec.siam.am.idp.ldap.LdapConverter;
import com.sinopec.siam.am.idp.ldap.ModifyAttribute;

/**
 * LDAP 用户锁定/解锁服务
 * 
 * @author zhangxianwen
 * @since 2012-6-19 上午10:11:41
 */

public class LdapUserLockService extends AbstractUserLockService {

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(LdapUserLockService.class);
  
  private LdapTemplate ldapTemplate;

  private String ldapUserBaseDN;
  
  /** 是否开启缓存 */
  private boolean isCache = false;
  
  /** 是否开启时间验证解锁 */
  private boolean hasHintUnlockTime = false;
  
  //特权账号
  private String privilegeAccounts = "";
  private Map<String, String> mapPrivilegeAccounts = new HashMap<String, String>();
  
  public void init() {

    initPrivilegeAccounts(privilegeAccounts);
  }
  
  private void initPrivilegeAccounts(String strAccounts) {
    if (strAccounts != null && !"".equals(strAccounts)) {
      strAccounts = strAccounts.trim().toLowerCase();
      String[] accounts = strAccounts.split(",");
      for (String account : accounts) {
        if (account != null && !"".equals(account.trim())) {
          mapPrivilegeAccounts.put(account, account);
        }
      }
    }
  }
  
  public String getPrivilegeAccounts() {
    return privilegeAccounts;
  }

  public void setPrivilegeAccounts(String privilegeAccounts) {
    this.privilegeAccounts = privilegeAccounts;
  }

  public boolean getIsCache() {
    return isCache;
  }

  public void setIsCache(boolean isCache) {
    this.isCache = isCache;
  }
  
  public boolean isHasHintUnlockTime() {
    return hasHintUnlockTime;
  }

  public void setHasHintUnlockTime(boolean hasHintUnlockTime) {
    this.hasHintUnlockTime = hasHintUnlockTime;
  }

  /** {@inheritDoc} */
  @Override
  public void unLock(UserLockState userLockState) {
    String username = userLockState.getValueAsString(userNameAttr);
    if(log.isDebugEnabled()){
      log.debug(String.format("unLock username is %s", username));
    }
    userLockState.setValue(lockStateAttr, true);
    userLockState.setValue(failedNumberAttr, 0);
    userLockState.setValue(unlockTimeAttr, null);
    updateUserLock(userLockState);
  }
  
  public void cleanState(String userName) throws Exception{
    
    UserLockState userLockState = getUser(userName);
    if(userLockState == null){
      return;
    }
    
    if(log.isDebugEnabled()){
      log.debug(String.format("cleanState username is %s", userName));
    }
    userLockState.setValue(failedNumberAttr, 0);
    userLockState.setValue(unlockTimeAttr, null);
    updateUserLock(userLockState);
  }

  public UserLockState getUser(String username) throws Exception {
    if(log.isDebugEnabled()){
      log.debug(String.format("getUser by username is %s", username));
    }
    
    if(isCache && userLockMap.containsKey(username)){
      return userLockMap.get(username);
    }
    
    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter(userNameAttr, username));
    List<UserLockState> result = ldapTemplate.search(ldapUserBaseDN, andFilter.encode(),
        new DirectoryEntityContextMapper(UserLockState.class));
    if (result.size() == 0) {
      log.info(String.format("Username not exists, username: %s.", username));
      throw new Exception(String.format("Username not exists, username: %s.", username));
    }
    if (result.size() > 1) {
      log.info(String.format("Find more than one user by username,return get(0),usernsme:%s.", username));
    }
    UserLockState userLockState = result.get(0);
    log.info(String.format("getUser by username is %s;dn:%s", username, userLockState.getDn()));
    if (isCache) {
      userLockMap.put(username, userLockState);
    }
    return userLockState;
  }

  /** {@inheritDoc} */
  @Override
  protected void saveUserLockState(UserLockState userLockState) {
    String username = userLockState.getValueAsString(userNameAttr);
    if(log.isDebugEnabled()){
      log.debug(String.format("saveUserLockState; username:%s, userDn:%s,lockState:%s,unLockTime:%s",
        username, userLockState.getDn(), userLockState.getValueAsString(lockStateAttr), userLockState.getValueAsString(unlockTimeAttr)));
    }
    //锁定用户
    int failedNumber = Integer.parseInt(userLockState.getValueAsString(failedNumberAttr));
    if(failedNumber >= maxFailedNumber && !isPrivilegeAccount(username)){
      userLockState.setValue(lockStateAttr, false);
      
      SimpleDateFormat df = new SimpleDateFormat(unlockTimeFormat);
      Date date = new Date();
      date.setTime(date.getTime() + unLockTimeInterval);
      userLockState.setValue("secPwdLastFailed", df.format(date));
      
      userLockState.setValue(failedNumberAttr, 0);
      //userLockState.setValue(unlockTimeAttr, null);     
      
      updateUserLock(userLockState);
    }else{
      //userLockState.setValue(lockStateAttr, true);
      SimpleDateFormat df = new SimpleDateFormat(unlockTimeFormat);
      Date date = new Date();
      date.setTime(date.getTime() + unLockTimeInterval);
      userLockState.setValue("secPwdLastFailed", df.format(date));
      userLockState.setValue(failedNumberAttr, failedNumber);
      
      updateUserLock(userLockState);

    }
    
    if (isCache) {
      userLockMap.put(username, userLockState);
    }
  }
  
  protected boolean isPrivilegeAccount(String uid) {
    if (uid != null && !"".equals(uid)) {
      return mapPrivilegeAccounts.containsKey(uid.toLowerCase());
    }
    return false;
  }
  
  /**
   * 用户是否锁定
   * @param username 用户名
   * @return 锁定：true，未锁定：false
   * @throws Exception 
   */
  /** {@inheritDoc} */
  @Override
  public boolean isLocked(String username) throws Exception{
    log.debug(String.format("isLocked by username is %s", username));
    UserLockState userLockState = getUser(username);
    if(userLockState == null){
      return false;
    }
    String lockState = userLockState.getValueAsString(lockStateAttr);
    if(lockState != null && !Boolean.parseBoolean(lockState)){
      return true;
    }
    //用户解锁
    SimpleDateFormat df = new SimpleDateFormat(unlockTimeFormat);
    String unLockTime = userLockState.getValueAsString(unlockTimeAttr);
    String nowTime = df.format(new Date());
    if(unLockTime!=null && unLockTime.compareTo(nowTime) > 0){
      return true;
    }
    
    if (hasHintUnlockTime) {
      unLock(userLockState);
    }
    
    return false;
  }

  /**
   * 更新用户锁定信息到LDAP
   * @param userLockState 用户信息
   */
  private void updateUserLock(UserLockState userLockState){
    ModifyAttribute[] attributes = DirectoryEntity2ModifyAttributeConverter.convert(userLockState);
    ModificationItem[] mods = LdapConverter.convertModifyAttribute2ModificationItem(attributes);
    ldapTemplate.modifyAttributes(userLockState.getDn(), mods);
  }

  public LdapTemplate getLdapTemplate() {
    return ldapTemplate;
  }

  public void setLdapTemplate(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  public String getLdapUserBaseDN() {
    return ldapUserBaseDN;
  }

  public void setLdapUserBaseDN(String ldapUserBaseDN) {
    this.ldapUserBaseDN = ldapUserBaseDN;
  }

}

