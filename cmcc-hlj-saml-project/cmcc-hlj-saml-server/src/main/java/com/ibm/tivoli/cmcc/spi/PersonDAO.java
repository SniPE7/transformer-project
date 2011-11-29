/**
 * 
 */
package com.ibm.tivoli.cmcc.spi;

import java.util.List;

import com.ibm.tivoli.cmcc.dao.PersonDTO;

/**
 * @author zhaodonglu
 *
 */
public interface PersonDAO {

  public List<PersonDTO> searchPerson(String filter);
  
  /**
   * Update password
   * @param msisdn
   * @param password
   * @return
   */
  public boolean updatePassword(String msisdn, String password);
  
  /**
   * 检查用户的口令
   * @param msisdn
   * @param passwordType    1：互联网密码, 2：服务密码
   * @param password
   * @return
   * @throws Exception 
   */
  public boolean checkMobileUserPassword(String msisdn, String passwordType, char[] password) throws Exception;
  
}
