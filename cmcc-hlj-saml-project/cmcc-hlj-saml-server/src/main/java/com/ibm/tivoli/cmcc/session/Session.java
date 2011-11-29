/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.util.Date;

import com.ibm.tivoli.cmcc.spi.PersonDTO;

/**
 * @author zhaodonglu
 *
 */
public class Session {
  
  private Date createTime = new Date();
  private Date lastAccessTime = new Date();
  private PersonDTO personDTO = null;

  /**
   * 
   */
  public Session() {
    super();
  }

  /**
   * @param createTime
   * @param lastAccessTime
   */
  public Session(Date createTime, Date lastAccessTime) {
    super();
    this.createTime = createTime;
    this.lastAccessTime = lastAccessTime;
  }

  /**
   * @param personDTO
   */
  public Session(PersonDTO personDTO) {
    super();
    this.personDTO = personDTO;
  }

  /**
   * @return the createTime
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * @param createTime the createTime to set
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * @return the lastAccessTime
   */
  public Date getLastAccessTime() {
    return lastAccessTime;
  }

  /**
   * @param lastAccessTime the lastAccessTime to set
   */
  public void setLastAccessTime(Date lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }

  /**
   * @return the personDTO
   */
  public PersonDTO getPersonDTO() {
    return personDTO;
  }

  /**
   * @param personDTO the personDTO to set
   */
  public void setPersonDTO(PersonDTO personDTO) {
    this.personDTO = personDTO;
  }

}
