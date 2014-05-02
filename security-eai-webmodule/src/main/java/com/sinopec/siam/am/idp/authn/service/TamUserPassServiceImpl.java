package com.sinopec.siam.am.idp.authn.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.directory.ModificationItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.HardcodedFilter;

import com.sinopec.siam.am.idp.entity.LdapUserEntity;
import com.sinopec.siam.am.idp.ldap.DirectoryEntity2ModifyAttributeConverter;
import com.sinopec.siam.am.idp.ldap.LdapConverter;
import com.sinopec.siam.am.idp.ldap.ModifyAttribute;
import com.sinopec.siam.utils.ITIMChallengeAndResponseUtil;

/**
 * TAM 用户口令服务实现类
 * 
 * @author zhangxianwen
 * @since 2012-6-21 上午9:47:27
 */

public class TamUserPassServiceImpl extends AbstractUserPassService {
  private static final String DATE_FORMAT = "yyyyMMddHHmmssZ";
  private Log log = LogFactory.getLog("TamUserPassServiceImpl");

  public TamUserPassServiceImpl() {
    super();
    this.setUserNameLdapAttribute("principalName");
    this.setUserPassLastChangedLdapAttribute("secPwdLastChanged");
    this.setUserPassResetStateLdapAttribute("secPwdValid");
    this.setUserPassRecoveryQuestionLdapAttribute("erlostpasswordanswer");
  }

  /** {@inheritDoc} */
  public boolean setPassRecoveryQuestion(String username, String question, String answer) {
    if(log.isDebugEnabled()){
      log.debug(String.format("Set User Pass Recovery Question, username[%s]; question:%s, answer:%s", username,
          question, answer));
    }
    List<LdapUserEntity> result = getUserByUsername(username);
    if (result.size() == 0) {
      log.error(String.format("Username not exists, username[%s]; question:%s, answer:%s", username, question, answer));
      return false;
    } else if (result.size() > 1) {
      log.error(String.format("Find more than one user by username, username[%s]; question:%s, answer:%s", username,
          question, answer));
      return false;
    }
    LdapUserEntity userEntity = result.get(0);
    String quesAns = ITIMChallengeAndResponseUtil.generateChallengeAndResponse(question, answer);
    userEntity.setValue(this.userPassRecoveryQuestionLdapAttribute, quesAns);
    ModifyAttribute[] attributes = DirectoryEntity2ModifyAttributeConverter.convert(userEntity);
    ModificationItem[] mods = LdapConverter.convertModifyAttribute2ModificationItem(attributes);
    ldapTemplate.modifyAttributes(userEntity.getDn(), mods);

    return true;
  }
  
  /* (non-Javadoc)
   * @see com.sinopec.siam.am.idp.authn.service.AbstractUserPassService#convertPwdChangeTime(java.lang.String)
   */
  protected Date convertPwdChangeTime(String dateTimeStr) {
    
    SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
    try {
      String s = dateTimeStr;
      if (s.endsWith(".0Z")) {
        s = s.substring(0, s.length() - 3);
        s += "-0000";
      } else if (s.endsWith("Z")) {
        // Date sample: 201208170328Z
        s = s.substring(0, s.length() - 1);
        s += "-0000";
      } else {
      	s += "-0000";
      }
      return df.parse(s);
    } catch (ParseException e) {
      log.warn(String.format("Failure to parse date string: [%s], pattern: [%s]", dateTimeStr, DATE_FORMAT), e);
      return null;
    }
  }

  /* (non-Javadoc)
   * @see com.sinopec.siam.am.idp.authn.service.AbstractUserPassService#convertPwdResetFalg(java.lang.String)
   */
  protected boolean convertPwdResetFalg(String value) {
    return !Boolean.parseBoolean((value==null)?"true":value);
  }

  /* (non-Javadoc)
   * @see com.sinopec.siam.am.idp.authn.service.AbstractUserPassService#getUserSearchFilterByUsername(java.lang.String)
   */
  protected Filter getUserSearchFilterByUsername(String username) {
    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter(this.getUserNameLdapAttribute(), username));
    andFilter.and(new HardcodedFilter("(objectclass=secUser)"));
    return andFilter;
  }
}
