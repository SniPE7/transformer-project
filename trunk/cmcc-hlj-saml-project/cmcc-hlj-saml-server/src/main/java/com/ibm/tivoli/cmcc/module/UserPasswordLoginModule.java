/**
 * 
 */
package com.ibm.tivoli.cmcc.module;


import javax.security.auth.spi.LoginModule;

import com.ibm.tivoli.cmcc.spi.PersonDAO;
import com.ibm.tivoli.cmcc.spi.PersonDTO;



/**
 * @author zhaodonglu
 * 
 */
public class UserPasswordLoginModule extends AbstractMobileUserLoginModule implements LoginModule {

  protected PersonDAO personDAO = null;
  /**
   * 
   */
  public UserPasswordLoginModule() {
    super();
  }

  /**
   * @return the personDAO
   */
  public PersonDAO getPersonDAO() {
    return personDAO;
  }

  /**
   * @param personDAO the personDAO to set
   */
  public void setPersonDAO(PersonDAO personDAO) {
    this.personDAO = personDAO;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.module.AbstractMobileUserLoginModule#authenticate(java.lang.String, java.lang.String, char[])
   */
  protected PersonDTO authenticate(String username, String passwordType, char[] password) throws Exception {
    PersonDTO person = null;
    // TODO 合并两次调用为一次
    boolean correct = personDAO.verifyPassword(username, passwordType, password);
    if (correct) {
       person = personDAO.getPersonByMsisdn(username);
    }
    return person;
  }

}
