package com.ibm.siam.am.idp.authn.service;

import java.util.List;

import com.ibm.siam.am.idp.entity.LdapUserEntity;

/**
 * LDAP�û�����ӿ�
 * @author zhangxianwen
 * @since 2012-6-14 ����5:08:04
 */

public interface UserService {
  
  /**
   * ����uid��ѯLDAP���û���Ϣ
   * @param uid �û�uidֵ
   * @return List<UserTamLdapEntity> ��ѯ���;�޶�Ӧ��¼ʱList.size = 0;
   */
  public List<LdapUserEntity> searchByUid(String uid);
  
  /**
   * ����������ѯLDAP���û���Ϣ
   * @param filter ��ѯ����
   * @return List<UserTamLdapEntity> ��ѯ���;�޶�Ӧ��¼ʱList.size = 0;
   */
  public List<LdapUserEntity> searchByFilter(String filter);
  
  /**
   * ��ָ֤��dn���û������Ƿ���ȷ
   * @param userDn �û�dn
   * @param password �û�����
   * @return ��ȷtrue������false
   */
  public boolean authenticateByUserDnPassword(String userDn, String password);
}
