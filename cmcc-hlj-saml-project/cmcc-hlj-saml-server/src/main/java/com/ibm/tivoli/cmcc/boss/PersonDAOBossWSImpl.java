/**
 * 
 */
package com.ibm.tivoli.cmcc.boss;

import java.util.Map;

import com.ibm.lbs.mcc.hl.fsso.boss.BossService;
import com.ibm.tivoli.cmcc.spi.PersonDAO;
import com.ibm.tivoli.cmcc.spi.PersonDTO;

/**
 * @author zhaodonglu
 * 
 */
public class PersonDAOBossWSImpl implements PersonDAO {

  private BossService bossService = null;

  /**
   * 
   */
  public PersonDAOBossWSImpl() {
    super();
  }

  /**
   * @return the bossService
   */
  public BossService getBossService() {
    return bossService;
  }

  /**
   * @param bossService
   *          the bossService to set
   */
  public void setBossService(BossService bossService) {
    this.bossService = bossService;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.spi.PersonDAO#getPersonByMsisdn(java.lang.String)
   */
  public PersonDTO getPersonByMsisdn(String msisdn) {
    Map<String, String> result = this.bossService.query(msisdn);
    if (result == null) {
      return null;
    }
    String retCode = result.get("RetCode");
    if (!"000000".equals(retCode)) {
       return null;
    }
    PersonDTO person = new PersonDTO();
    person.setBrand(result.get("2"));
    person.setCommonName(result.get("9"));
    //person.setCurrentPoint(result.get(""));
    //person.setFetionStatus(result.get(""));
    person.setLastName(result.get("9"));
    //person.setMail139Status(result.get(""));
    person.setMsisdn(msisdn);
    //person.setNickname(result.get(""));
    //person.setProvince(result.get(""));
    person.setStatus(result.get("5"));
    //person.setUserLevel(result.get(""));
    return person;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.spi.PersonDAO#updatePassword(java.lang.String, java.lang.String, java.lang.String)
   */
  public boolean updatePassword(String msisdn, String serviceCode, String networkPassword) {
    throw new RuntimeException("Unsupport method");
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.spi.PersonDAO#verifyPassword(java.lang.String, java.lang.String, char[])
   */
  public boolean verifyPassword(String msisdn, String passwordType, char[] password) throws Exception {
    Map<String, String> result = this.bossService.auth(msisdn, passwordType, new String(password));
    if (result == null) {
      return false;
    }
    String retCode = result.get("RetCode");
    if ("000000".equals(retCode)) {
       return true;
    }
    return false;
  }

}
