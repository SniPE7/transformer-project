/**
 * 
 */
package com.ibm.tivoli.cmcc.ldap;

import java.util.List;

/**
 * @author Zhao Dong Lu
 *
 */
public interface PersonDAO {

  public List<PersonDTO> searchPerson(String filter);
  
  //public List getContactDetails(String commonName, String lastName);
  
  //public void insertContact(PersonDTO contactDTO);
  
  public boolean updatePassword(String filter, String password);
  
  /**
   * 检查用户的口令
   * @param msisdn
   * @param passwordType    1：互联网密码, 2：服务密码
   * @param password
   * @return
   * @throws Exception 
   */
  public boolean checkMobileUserPassword(String msisdn, String passwordType, char[] password) throws Exception;
  
  /**
   * Find person by samlID and update samlID attribute
   * @param base
   * @param filter
   * @param uniqueIdentifier
   * @return
   */
  public boolean updateUniqueIdentifier(String filter, String uniqueIdentifier);
  
  /**
   * Find person by samlID and delete samlID attribute
   * @param base
   * @param filter
   * @param uniqueIdentifier
   * @return
   */
  public boolean deleteUniqueIdentifier(String filter, String uniqueIdentifier);
  
  /**
   * Find person by msisdn and create samlID attribute
   * @param base
   * @param msisdn
   * @return
   * @throws Exception
   */
  public String insertUniqueIdentifier(String msisdn, String uniqueIdentifier) throws Exception;
  
  //public void deleteContact(PersonDTO contactDTO);
}

