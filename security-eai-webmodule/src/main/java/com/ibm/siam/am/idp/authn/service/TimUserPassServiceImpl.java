package com.ibm.siam.am.idp.authn.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.HardcodedFilter;

/**
 * TIM 用户口令服务实现类
 * 
 * @author zhangxianwen
 * @since 2012-6-21 上午9:47:27
 */

public abstract class TimUserPassServiceImpl extends AbstractUserPassService {
  private static final String DATE_FORMAT = "yyyyMMddHHmmZ";
  private Log log = LogFactory.getLog("TimUserPassServiceImpl");

  public TimUserPassServiceImpl() {
    super();
    this.setUserNameLdapAttribute("eruid");
    this.setUserPassLastChangedLdapAttribute("erpswdlastchanged");
    this.setUserPassResetStateLdapAttribute("erchangepswdrequired");
    this.setUserPassRecoveryQuestionLdapAttribute("erlostpasswordanswer");
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.authn.service.AbstractUserPassService#convertPwdChangeTime(java.lang.String)
   */
  protected Date convertPwdChangeTime(String dateTimeStr) {
    // Date sample: 201208170328Z
    SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
    try {
      String s = dateTimeStr;
      if (s.endsWith("Z")) {
        s = s.substring(0, s.length() - 1);
        s += "-0000";
      }
      return df.parse(s);
    } catch (ParseException e) {
      log.warn(String.format("Failure to parse date string: [%s], pattern: [%s]", dateTimeStr, DATE_FORMAT), e);
      return null;
    }
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.authn.service.AbstractUserPassService#convertPwdResetFalg(java.lang.String)
   */
  protected boolean convertPwdResetFalg(String value) {
    return Boolean.parseBoolean((value==null)?"true":value);
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.am.idp.authn.service.AbstractUserPassService#getUserSearchFilterByUsername(java.lang.String)
   */
  protected Filter getUserSearchFilterByUsername(String username) {
    AndFilter andFilter = new AndFilter();
    andFilter.and(new EqualsFilter(this.getUserNameLdapAttribute(), username));
    andFilter.and(new HardcodedFilter("(objectclass=erSystemUser)"));
    return andFilter;
  }

  /** {@inheritDoc} */
  public abstract boolean setPassRecoveryQuestion(String username, String question, String answer);
}
