/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.io.Serializable;
import java.util.Date;

import com.ibm.tivoli.cmcc.server.utils.Helper;
import com.ibm.tivoli.cmcc.spi.PersonDTO;

/**
 * @author zhaodonglu
 *
 */
public class Session implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -1171871599242867847L;
  
  private String artifactID = Helper.generatorID();;
  private Date createTime = new Date();
  private Date lastAccessTime = new Date();
  private PersonDTO personDTO = null;
  private String uid = null;
  
  /**
   * 表示用户是否已经在总部登录后转入二级节点.
   * true -- 本地先登录
   * false -- 表示总部先登录
   */
  private boolean oringinal = true;

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
   * @param artifactID
   * @param personDTO
   */
  public Session(String artifactID, PersonDTO personDTO) {
    super();
    this.artifactID = artifactID;
    this.personDTO = personDTO;
  }

  /**
   * @param artifactID
   * @param uid
   * @param personDTO
   */
  public Session(String artifactID, String uid, PersonDTO personDTO) {
    super();
    this.artifactID = artifactID;
    this.uid = uid;
    this.personDTO = personDTO;
  }

  /**
   * @return the artifactID
   */
  public String getArtifactID() {
    return artifactID;
  }

  /**
   * @param artifactID the artifactID to set
   */
  public void setArtifactID(String artifactID) {
    this.artifactID = artifactID;
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

  /**
   * @return the uid
   */
  public String getUid() {
    return uid;
  }

  /**
   * @param uid the uid to set
   */
  public void setUid(String uid) {
    this.uid = uid;
  }

  public void touch() {
    this.lastAccessTime = new Date();    
  }

  /**
   * @return the oringinal
   */
  public boolean isOringinal() {
    return oringinal;
  }

  /**
   * @param oringinal the oringinal to set
   */
  public void setOringinal(boolean original) {
    this.oringinal = original;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("Session [artifactID=%s, createTime=%s, lastAccessTime=%s, personDTO=%s, uid=%s, oringinal=%s]", artifactID, createTime, lastAccessTime, personDTO,
        uid, this.oringinal);
  }

}
