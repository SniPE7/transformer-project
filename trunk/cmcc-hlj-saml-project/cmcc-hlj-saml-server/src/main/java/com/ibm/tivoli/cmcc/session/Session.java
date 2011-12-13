/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibm.tivoli.cmcc.spi.PersonDTO;
import com.ibm.tivoli.cmcc.util.Helper;

/**
 * @author zhaodonglu
 *
 */
public class Session implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -1171871599242867847L;
  
  /**
   * SAML Session ID
   */
  private String artifactID = Helper.generatorID();;
  
  /**
   * SAML Session Domain
   */
  private String artifactDomain = null;
  
  /**
   * Session create time
   */
  private Date createTime = new Date();
  
  /**
   * Session Last access time 
   */
  private Date lastAccessTime = new Date();
  /**
   * User detail info
   */
  private PersonDTO personDTO = null;

  /**
   * Login id
   */
  private String uid = null;
  
  /**
   * 表示用户是否已经在总部登录后转入二级节点.
   * true -- 本地先登录
   * false -- 表示总部先登录
   */
  private boolean oringinal = true;
  
  /**
   * HttpSession ID
   */
  private String httpSessionId = null;
  
  private Map<String, Serializable> attributes = new HashMap<String, Serializable>();

  /**
   * 
   */
  public Session() {
    super();
  }

  /**
   * @param artifactID
   * @param httpSessionId
   * @param uid
   * @param personDTO
   */
  public Session(String artifactID, String artifactDomain, String httpSessionId, String uid, PersonDTO personDTO) {
    super();
    this.artifactID = artifactID;
    this.httpSessionId = httpSessionId;
    this.uid = uid;
    this.personDTO = personDTO;
  }

  /**
   * @param artifactID
   * @param httpSessionId
   * @param uid
   * @param personDTO
   * @param oringinal
   */
  public Session(String artifactID, String artifactDomain, String httpSessionId, String uid, PersonDTO personDTO, boolean oringinal) {
    super();
    this.artifactID = artifactID;
    this.httpSessionId = httpSessionId;
    this.uid = uid;
    this.personDTO = personDTO;
    this.oringinal = oringinal;
  }

  /**
   * @return the artifactID
   */
  public String getArtifactID() {
    return artifactID;
  }

  /**
   * @return the artifactDomain
   */
  public String getArtifactDomain() {
    return artifactDomain;
  }

  /**
   * @return the createTime
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * @return the lastAccessTime
   */
  public Date getLastAccessTime() {
    return lastAccessTime;
  }

  /**
   * @return the personDTO
   */
  public PersonDTO getPersonDTO() {
    return personDTO;
  }

  /**
   * @return the uid
   */
  public String getUid() {
    return uid;
  }

  /**
   * @return the oringinal
   */
  public boolean isOringinal() {
    return oringinal;
  }

  /**
   * @return the httpSessionId
   */
  public String getHttpSessionId() {
    return httpSessionId;
  }

  /**
   * @return the attributes
   */
  public Set<String> getAttributeNames() {
    return attributes.keySet();
  }

  /**
   * @param attributes the attributes to set
   */
  public void setAttribute(String name, Serializable value) {
    this.attributes.put(name, value);
  }
  
  /**
   * @param name
   * @return
   */
  public Serializable getAttribute(String name) {
    return this.attributes.get(name);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("Session [artifactID=%s, httpSessionId=%s, uid=%s, createTime=%s, lastAccessTime=%s, oringinal=%s, personDTO=%s]", artifactID,
        httpSessionId, uid, createTime, lastAccessTime, oringinal, personDTO);
  }


}
