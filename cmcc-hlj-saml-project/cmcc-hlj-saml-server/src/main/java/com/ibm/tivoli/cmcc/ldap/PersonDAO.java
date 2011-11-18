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

  public List<PersonDTO> searchPerson(String base, String filter);
  
  //public List getContactDetails(String commonName, String lastName);
  
  //public void insertContact(PersonDTO contactDTO);
  
  public boolean updatePassword(String base, String filter, String password);
  
  /**
   * Find person by samlID and update samlID attribute
   * @param base
   * @param filter
   * @param uniqueIdentifier
   * @return
   */
  public boolean updateUniqueIdentifier(String base, String filter, String uniqueIdentifier);
  
  /**
   * Find person by samlID and delete samlID attribute
   * @param base
   * @param filter
   * @param uniqueIdentifier
   * @return
   */
  public boolean deleteUniqueIdentifier(String base, String filter, String uniqueIdentifier);
  
  /**
   * Find person by msisdn and create samlID attribute
   * @param base
   * @param msisdn
   * @return
   * @throws Exception
   */
  public String insertUniqueIdentifier(String base, String msisdn, String uniqueIdentifier) throws Exception;
  
  //public void deleteContact(PersonDTO contactDTO);
}

