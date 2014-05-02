package com.ibm.siam.am.idp.authn.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.HardcodedFilter;

import com.ibm.itim.ws.exceptions.WSInvalidLoginException;
import com.ibm.itim.ws.exceptions.WSLoginServiceException;
import com.ibm.itim.ws.model.WSSession;
import com.ibm.itim.ws.services.WSItimService;
import com.ibm.itim.ws.services.facade.ITIMWebServiceFactory;
import com.ibm.siam.am.idp.ldap.DirectoryEntityContextMapper;
import com.ibm.siam.am.idp.entity.LdapUserEntity;


public abstract class AbstractUserPassService implements UserPassService {

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(TimUserPassServiceImpl.class);
  protected LdapTemplate ldapTemplate;
  private String ldapUserBaseDN;
  /** userName对应ldap属性名称 */
  private String userNameLdapAttribute;
  /** 用户密码最后修改时间对应Ldap属性名称 */
  private String userPassLastChangedLdapAttribute;

  /** 用户密码重置状态对应Ldap属性名称 */
  private String userPassResetStateLdapAttribute;

  /** 用户密码密码找回问题对应Ldap属性名称 */
  protected String userPassRecoveryQuestionLdapAttribute;
  /** ITIM API */
  private String timSoapEndpoint;

  /** ITIM manager account */
  private String itimManager;

  /** ITIM manager account password */
  private String itimManagerPwd;

  /** ITIM update user's password notify by mail */
  private boolean notifyByMail;

  /** 用户拥有者DNLdap属性名 */
  private String userOwnerLdapAttribute;
  
  /** 用户密码所有提示问题列表过滤条件 */
  private String passQuestionListFilter = "(&(objectclass=erchallenges)(cn=challenges))";
  
  /** 用户密码所有提示问题Ldap属性 */
  private String passQuestionListLdapAttribute = "erlostpasswordquestion";

  public AbstractUserPassService() {
    super();
  }

  /**
   * @return the ldapTemplate
   */
  public LdapTemplate getLdapTemplate() {
    return ldapTemplate;
  }

  /**
   * @param ldapTemplate
   *          the ldapTemplate to set
   */
  public void setLdapTemplate(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  /**
   * @return the ldapUserBaseDN
   */
  public String getLdapUserBaseDN() {
    return ldapUserBaseDN;
  }

  /**
   * @param ldapUserBaseDN
   *          the ldapUserBaseDN to set
   */
  public void setLdapUserBaseDN(String ldapUserBaseDN) {
    this.ldapUserBaseDN = ldapUserBaseDN;
  }

  /**
   * @return the userNameLdapAttribute
   */
  public String getUserNameLdapAttribute() {
    return userNameLdapAttribute;
  }

  /**
   * @param userNameLdapAttribute
   *          the userNameLdapAttribute to set
   */
  public void setUserNameLdapAttribute(String userNameLdapAttribute) {
    this.userNameLdapAttribute = userNameLdapAttribute;
  }

  /**
   * @return the userPassLastChangedLdapAttribute
   */
  public String getUserPassLastChangedLdapAttribute() {
    return userPassLastChangedLdapAttribute;
  }

  /**
   * @param userPassLastChangedLdapAttribute
   *          the userPassLastChangedLdapAttribute to set
   */
  public void setUserPassLastChangedLdapAttribute(String userPassLastChangedLdapAttribute) {
    this.userPassLastChangedLdapAttribute = userPassLastChangedLdapAttribute;
  }

  /**
   * @return the userPassResetStateLdapAttribute
   */
  public String getUserPassResetStateLdapAttribute() {
    return userPassResetStateLdapAttribute;
  }

  /**
   * @param userPassResetStateLdapAttribute
   *          the userPassResetStateLdapAttribute to set
   */
  public void setUserPassResetStateLdapAttribute(String userPassResetStateLdapAttribute) {
    this.userPassResetStateLdapAttribute = userPassResetStateLdapAttribute;
  }

  /**
   * @return the userPassRecoveryQuestionLdapAttribute
   */
  public String getUserPassRecoveryQuestionLdapAttribute() {
    return userPassRecoveryQuestionLdapAttribute;
  }

  /**
   * @param userPassRecoveryQuestionLdapAttribute
   *          the userPassRecoveryQuestionLdapAttribute to set
   */
  public void setUserPassRecoveryQuestionLdapAttribute(String userPassRecoveryQuestionLdapAttribute) {
    this.userPassRecoveryQuestionLdapAttribute = userPassRecoveryQuestionLdapAttribute;
  }

  /**
   * @return the timSoapEndpoint
   */
  public String getTimSoapEndpoint() {
    return timSoapEndpoint;
  }

  /**
   * @param timSoapEndpoint
   *          the timSoapEndpoint to set
   */
  public void setTimSoapEndpoint(String timSoapEndpoint) {
    this.timSoapEndpoint = timSoapEndpoint;
  }

  /**
   * @return the itimManager
   */
  public String getItimManager() {
    return itimManager;
  }

  /**
   * @param itimManager
   *          the itimManager to set
   */
  public void setItimManager(String itimManager) {
    this.itimManager = itimManager;
  }

  /**
   * @return the itimManagerPwd
   */
  public String getItimManagerPwd() {
    return itimManagerPwd;
  }

  /**
   * @param itimManagerPwd
   *          the itimManagerPwd to set
   */
  public void setItimManagerPwd(String itimManagerPwd) {
    this.itimManagerPwd = itimManagerPwd;
  }

  /**
   * @return the notifyByMail
   */
  public boolean isNotifyByMail() {
    return notifyByMail;
  }

  /**
   * @param notifyByMail
   *          the notifyByMail to set
   */
  public void setNotifyByMail(boolean notifyByMail) {
    this.notifyByMail = notifyByMail;
  }

  /**
   * @return the userOwnerLdapAttribute
   */
  public String getUserOwnerLdapAttribute() {
    return userOwnerLdapAttribute;
  }

  /**
   * @param userOwnerLdapAttribute
   *          the userOwnerLdapAttribute to set
   */
  public void setUserOwnerLdapAttribute(String userOwnerLdapAttribute) {
    this.userOwnerLdapAttribute = userOwnerLdapAttribute;
  }

  /**
   * @return the passQuestionListFilter
   */
  public String getPassQuestionListFilter() {
    return passQuestionListFilter;
  }

  /**
   * @param passQuestionListFilter the passQuestionListFilter to set
   */
  public void setPassQuestionListFilter(String passQuestionListFilter) {
    this.passQuestionListFilter = passQuestionListFilter;
  }

  /**
   * @return the passQuestionListLdapAttribute
   */
  public String getPassQuestionListLdapAttribute() {
    return passQuestionListLdapAttribute;
  }

  /**
   * @param passQuestionListLdapAttribute the passQuestionListLdapAttribute to set
   */
  public void setPassQuestionListLdapAttribute(String passQuestionListLdapAttribute) {
    this.passQuestionListLdapAttribute = passQuestionListLdapAttribute;
  }

  /** {@inheritDoc} */
  public Date getPassLastChanged(LdapUserEntity ldapUserEntity) throws Exception {
    if(ldapUserEntity == null){
      log.info(String.format(
          "User not exists; userNameLdapAttribute:%s, userPassRecoveryQuestionLdapAttribute:%s",
          userNameLdapAttribute, userPassLastChangedLdapAttribute));
      throw new Exception(String.format(
          "User not exists; userNameLdapAttribute:%s, userPassRecoveryQuestionLdapAttribute:%s",
          userNameLdapAttribute, userPassLastChangedLdapAttribute));
    }
    String lastChanged = ldapUserEntity.getValueAsString(userPassLastChangedLdapAttribute);
    return convertPwdChangeTime(lastChanged);
  }

  /** {@inheritDoc} */
  public boolean getPassResetState(LdapUserEntity ldapUserEntity) throws Exception {
    // True --- Password valid, not in reset state
    if(ldapUserEntity == null){
      log.info(String.format(
          "User not exists; userNameLdapAttribute:%s, userPassResetStateLdapAttribute:%s",
          userNameLdapAttribute, userPassResetStateLdapAttribute));
      throw new Exception(String.format(
          "User not exists; userNameLdapAttribute:%s, userPassResetStateLdapAttribute:%s",
          userNameLdapAttribute, userPassResetStateLdapAttribute));
    }
    String value = ldapUserEntity.getValueAsString(userPassResetStateLdapAttribute);
    return convertPwdResetFalg(value);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  public String[] getAllPassQuestion() throws ServiceException {
    if(log.isDebugEnabled()){
      log.debug("Get All Password Question ");
    }
    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter("cn", "challenges"));
    andFilter.and(new HardcodedFilter("(objectclass=erchallenges)"));
    
    List<LdapUserEntity> result = ldapTemplate.search(ldapUserBaseDN, passQuestionListFilter,
        new DirectoryEntityContextMapper(LdapUserEntity.class));
    if (result.size() == 0) {
      log.error(String.format("Username not exists, filter:%s", passQuestionListFilter));
      throw new ServiceException(String.format("Username not exists, filter:%s", passQuestionListFilter));
    } else if (result.size() > 1) {
      log.error(String.format("Find more than one user, filter:%s", passQuestionListFilter));
      throw new ServiceException(String.format("Find more than one user by filter, filter:%s", passQuestionListFilter));
    }
    LdapUserEntity userEntity = result.get(0);
    return userEntity.getValueAsStringArray(passQuestionListLdapAttribute);
  }

  /** {@inheritDoc} */
  public String[] getPassRecoveryQuestion(LdapUserEntity ldapUserEntity) throws Exception {
    if(ldapUserEntity == null){
      log.info(String.format(
          "User not exists; userNameLdapAttribute:%s, userPassRecoveryQuestionLdapAttribute:%s",
          userNameLdapAttribute, userPassRecoveryQuestionLdapAttribute));
      throw new Exception(String.format(
          "User not exists; userNameLdapAttribute:%s, userPassRecoveryQuestionLdapAttribute:%s",
          userNameLdapAttribute, userPassRecoveryQuestionLdapAttribute));
    }
    return ldapUserEntity.getValueAsStringArray(userPassRecoveryQuestionLdapAttribute);
  }
  
  /** {@inheritDoc} */
  public LdapUserEntity getLdapUserEntityByUsername(String username) throws Exception {
    if(log.isDebugEnabled()){
      log.debug(String
          .format(
              "Get LdapUserEntity by username[%s]; userNameLdapAttribute:%s, userPassRecoveryQuestionLdapAttribute:%s",
              username, userNameLdapAttribute, userPassRecoveryQuestionLdapAttribute));      
    }
    List<LdapUserEntity> result = getUserByUsername(username);
    if (result.size() == 0) {
      log.info(String.format(
          "Username not exists, username: %s; userNameLdapAttribute:%s, userPassRecoveryQuestionLdapAttribute:%s",
          username, userNameLdapAttribute, userPassRecoveryQuestionLdapAttribute));
      throw new Exception(String.format(
          "Username not exists, username: %s; userNameLdapAttribute:%s, userPassRecoveryQuestionLdapAttribute:%s",
          username, userNameLdapAttribute, userPassRecoveryQuestionLdapAttribute));
    } else if (result.size() > 1) {
      log.info(String
          .format(
              "Find more than one user by username,usernsme:%s; userNameLdapAttribute:%s, userPassRecoveryQuestionLdapAttribute:%s",
              username, userNameLdapAttribute, userPassRecoveryQuestionLdapAttribute));
      throw new Exception(
          String
              .format(
                  "Find more than one user by username,usernsme:%s; userNameLdapAttribute:%s, userPassRecoveryQuestionLdapAttribute:%s",
                  username, userNameLdapAttribute, userPassRecoveryQuestionLdapAttribute));
    }
    
    return result.get(0);
  }

  

  /**
   * {@inheritDoc}
   * 
   * @throws ServiceException
   * @throws MalformedURLException
   * @throws RemoteException
   * @throws WSLoginServiceException
   * @throws WSInvalidLoginException
   * @throws javax.xml.rpc.ServiceException 
   */
  public void updatePassword(String username, String newPassword) throws MalformedURLException,
  WSInvalidLoginException, WSLoginServiceException, RemoteException, ServiceException {
    if(log.isDebugEnabled()){
      log.debug("Update User Pass by username [{}]", username);
    }
    List<LdapUserEntity> result = getUserByUsername(username);
    if (result.size() == 0) {
      log.error("Username not exists, username [{}]", username);
      throw new ServiceException(String.format("Username not exists, username[%s]", username));
    } else if (result.size() > 1) {
      log.error("Find more than one user, username [{}]", username);
      throw new ServiceException(String.format("Find more than one user by username, username[%s]", username));
    }
    LdapUserEntity userEntity = result.get(0);
    // 修改TIM口令（API）
    ITIMWebServiceFactory webServiceFactory = new ITIMWebServiceFactory(timSoapEndpoint);
    WSItimService wsItimService = webServiceFactory.getWSItimService();
    Calendar scheduledTime = Calendar.getInstance();
    scheduledTime.setTime(new Date());
    WSSession wsSession = wsItimService.login(itimManager, itimManagerPwd);

    wsItimService.synchPasswords(wsSession, userEntity.getValueAsString(userOwnerLdapAttribute), newPassword,
        null, notifyByMail);

  }

  /**
   * 获取用户信息
   * 
   * @param username
   *          用户名
   * @return List<LdapUserEntity> 用户信息，如果用户不存在:list.size=0
   */
  @SuppressWarnings("unchecked")
  protected List<LdapUserEntity> getUserByUsername(String username) {
    if(log.isDebugEnabled()){
      log.debug(String.format("Get User by username:%s", username));
    }
    Filter filter = getUserSearchFilterByUsername(username);
    return ldapTemplate.search(ldapUserBaseDN, filter.encode(), new DirectoryEntityContextMapper(LdapUserEntity.class));
  }

  /**
   * 返回根据用户名查询用户的Filter
   * 
   * @param username
   * @return
   */
  protected abstract Filter getUserSearchFilterByUsername(String username);

  /**
   * 返回将日期字符串转换后的日期值
   * 
   * @return
   */
  protected abstract Date convertPwdChangeTime(String dateTimeStr);

  /**
   * @param value
   * @return
   */
  /**
   * 将参数转换为boolean型，并确保true代表帐号密码已经被重置.
   * 
   * @param value
   * @return
   */
  protected abstract boolean convertPwdResetFalg(String value);

}
