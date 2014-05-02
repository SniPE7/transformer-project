package com.ibm.siam.am.idp.authn.service;

import java.util.List;

import com.ibm.siam.am.idp.entity.LdapUserEntity;

/**
 * LDAP用户服务接口
 * @author zhangxianwen
 * @since 2012-6-14 下午5:08:04
 */

public interface UserService {
  
  /**
   * 根据uid查询LDAP中用户信息
   * @param uid 用户uid值
   * @return List<UserTamLdapEntity> 查询结果;无对应记录时List.size = 0;
   */
  public List<LdapUserEntity> searchByUid(String uid);
  
  /**
   * 根据条件查询LDAP中用户信息
   * @param filter 查询条件
   * @return List<UserTamLdapEntity> 查询结果;无对应记录时List.size = 0;
   */
  public List<LdapUserEntity> searchByFilter(String filter);
  
  /**
   * 认证指定dn的用户口令是否正确
   * @param userDn 用户dn
   * @param password 用户口令
   * @return 正确true，否则false
   */
  public boolean authenticateByUserDnPassword(String userDn, String password);
}
