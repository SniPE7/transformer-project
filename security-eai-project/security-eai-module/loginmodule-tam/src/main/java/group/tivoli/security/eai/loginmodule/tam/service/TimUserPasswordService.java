package group.tivoli.security.eai.loginmodule.tam.service;

import javax.naming.directory.ModificationItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinopec.siam.am.idp.entity.LdapUserEntity;

/**
 * TIM USER LDAP 锁操作服务
 * 
 * @author xuhong
 * @since 2012-12-7 上午10:31:21
 */

public class TimUserPasswordService {

  private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
  
  private final String SYSTEMUSER_ERCHANGEPSWDREQUIRED = "erchangepswdrequired";

  private TimUserLdapUtil timUserLdapUtil;
  
  public TimUserLdapUtil getTimUserLdapUtil() {
    return timUserLdapUtil;
  }

  public void setTimUserLdapUtil(TimUserLdapUtil timUserLdapUtil) {
    this.timUserLdapUtil = timUserLdapUtil;
  }

  private void changeSystemUserPwdRequired(LdapUserEntity entity, String status) {
    timUserLdapUtil.modifyEntity(entity,
        new ModificationItem[] { timUserLdapUtil.getOneModificationItem(SYSTEMUSER_ERCHANGEPSWDREQUIRED, status) });
  }
  
  public void openSystemUserPwdRequired(String uid){
    // get systemUser
    LdapUserEntity systemUser = timUserLdapUtil.getSystemUser(uid);
    
    if(systemUser==null){
      log.error(String.format("not find the systemUser [%s] in tim ldap.", uid));
      throw new RuntimeException(String.format("not find the systemUser [%s] in tim ldap.", uid));
    }
    
    changeSystemUserPwdRequired(systemUser, "true");
  }
  
  public void closeSystemUserPwdRequired(String uid){
    // get systemUser
    LdapUserEntity systemUser = timUserLdapUtil.getSystemUser(uid);
    
    if(systemUser==null){
      log.error(String.format("not find the systemUser [%s] in tim ldap.", uid));
      throw new RuntimeException(String.format("not find the systemUser [%s] in tim ldap.", uid));
    }
    
    changeSystemUserPwdRequired(systemUser, "false");
  }

  public boolean getSystemUserPwdRequired(String uid) {
    // get systemUser
    LdapUserEntity systemUser = timUserLdapUtil.getSystemUser(uid);
    
    if(systemUser==null){
      log.error(String.format("not find the systemUser [%s] in tim ldap.", uid));
      throw new RuntimeException(String.format("not find the systemUser [%s] in tim ldap.", uid));
    }
    
    String retBool = systemUser.getValueAsString(SYSTEMUSER_ERCHANGEPSWDREQUIRED);
    
    if(retBool!=null){
      return Boolean.parseBoolean(retBool);
    }
    
    return false;
  }
}
