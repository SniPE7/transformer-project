/**
 * 
 */
package com.ibm.tivoli.cmcc.spi;



/**
 * @author zhaodonglu
 *
 */
public interface PersonDAO {

  /**
   * Get User detail information.
   * @param msisdn
   * @return
   */
  public PersonDTO getPersonByMsisdn(String msisdn);
  
  /**
   * Verify service code , and update network password
   * @param msisdn
   * @param password
   * @return
   */
  public boolean updatePassword(String msisdn, String serviceCode, String networkPassword);
  
  /**
   * Verify User password
   * @param msisdn
   * @param passwordType    1：互联网密码, 2：服务密码
   * @param password
   * @return
   * @throws Exception 
   */
  public boolean verifyPassword(String msisdn, String passwordType, char[] password) throws Exception;
  
}
