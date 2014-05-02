package com.ibm.siam.am.idp.entity;

import com.ibm.siam.am.idp.ldap.entity.DirectoryEntity;

/**
 * LDAP �û�ʵ����
 * @author zhangxianwen
 * @since 2012-6-20 ����3:27:59
 */

public class LdapUserEntity extends DirectoryEntity {

private static final long serialVersionUID = 854917615126748756L;
  
  public void setUid(String uid){
    setValue("uid", uid);
  }
  
  public String getUid(){
    return getValueAsString("uid");
  }
  
}
