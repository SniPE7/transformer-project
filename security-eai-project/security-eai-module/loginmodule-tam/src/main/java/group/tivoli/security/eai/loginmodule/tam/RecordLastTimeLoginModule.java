package group.tivoli.security.eai.loginmodule.tam;

import group.tivoli.security.eai.loginmodule.tam.service.TimUserLdapUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.apache.commons.lang.StringUtils;

import com.sinopec.siam.am.idp.authn.module.AbstractSpringLoginModule;
import com.sinopec.siam.am.idp.authn.principal.UserPrincipal;
import com.sinopec.siam.am.idp.entity.LdapUserEntity;

/**
 * 更新用户最新一次登录时间LoginModule
 * <p>
 * 更新用户最新一次登录时间
 * </p>
 * 
 * @author xuhong
 * @since 2013-1-6 上午11:29:08
 */

public class RecordLastTimeLoginModule extends AbstractSpringLoginModule {

  /** 用户锁定服务Bean ID */
  private String timUserLdapServiceBeanId = null;
  
  private String timeZone = "GMT";
  
  private final static String TIM_LAST_ACCESS_DATE_TAG = "erlastaccessdate";

  /** {@inheritDoc} */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
      Map<String, ?> options) {
    super.initialize(subject, callbackHandler, sharedState, options);

    this.timUserLdapServiceBeanId = (String) options.get("timUserLdapServiceBeanId");
    if (this.timUserLdapServiceBeanId == null || "".equals(this.timUserLdapServiceBeanId)) {
      throw new RuntimeException("timUserLdapServiceBeanId is null");
    }
    
    String zone = (String) options.get("timeZone");
    if (null != zone && !"".equals(zone)) {
      this.timeZone = zone;
    }
  }

  /** {@inheritDoc} */
  public boolean login() throws LoginException {
    if (callbackHandler == null) {
      log.error("No CallbackHandler");
      return false;
    }

    // 检查之前是否已经认证成功
    String loginUsername = (String) this.sharedState.get(LOGIN_NAME);
    if (!StringUtils.isEmpty(loginUsername)) {
      // 用户校验，通过LADP查询捆绑人员uid信息，存储Subject
      UserPrincipal principal = new UserPrincipal();
      principal.setName(loginUsername);
      principals.add(principal);
      authenticated = true;
      
      //认证通过后，记录最新一次登录时间
      TimUserLdapUtil timUserLdapService = getTimUserLdapService();
      try {
        
        LdapUserEntity systemUser = timUserLdapService.getSystemUser(loginUsername);
        
        if(null == systemUser){
          log.error(String.format("the user account can't find in tim; usernsme:%s.", loginUsername));
          return true;
        }
        
        
        timUserLdapService.modifyEntity(systemUser.getDn(), TIM_LAST_ACCESS_DATE_TAG, getNowDate(this.timeZone));
      } catch (Exception e) {
          log.error(e.getMessage(), e);
      }
      
      return true;
    }
    
    //log.warn(String.format("the user account can't find in tim; usernsme:%s.", loginUsername));
    
    return true;
  }
  
  private static String getNowDate(String zone){
    //201212290740Z
    DateFormat df = new SimpleDateFormat("yyyyMMddHHmm", Locale.ENGLISH); 
    df.setTimeZone(TimeZone.getTimeZone(zone));
    
    Date  now = new Date();
    
    return df.format(now) + "Z";
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
   * @description 获取用户锁定服务对象
   * @return AbstractUserLockService
   */
  private TimUserLdapUtil getTimUserLdapService() {
    return this.applicationContext.getBean(this.timUserLdapServiceBeanId, TimUserLdapUtil.class);
  }

}

