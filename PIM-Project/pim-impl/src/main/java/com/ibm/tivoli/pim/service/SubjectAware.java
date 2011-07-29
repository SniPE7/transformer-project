/**
 * 
 */
package com.ibm.tivoli.pim.service;

import javax.security.auth.Subject;

/**
 * @author Administrator
 *
 */
public interface SubjectAware {
  
  public void setSubject(Subject subject);

}
