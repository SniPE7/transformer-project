/**
 * 
 */
package com.ibm.siam.am.idp.authn;

import java.util.List;

/**
 * @author zhaodonglu
 *
 */
public interface AuthenLevelDirector {
  
  /**
   * ����һ��ƥ��AuthenLevel����֤����
   * <p>���û�ж�Ӧ��֤��������null �������Ӧ��֤�����б�Ϊ�շ���""�������֤�����Ӧ�����֤��������ȡ��һ��</p>
   * @param authenLevel ��֤����
   * @return
   */
  public String getMatachedAuthenMethod(String authenLevel);
  
  /**
   * ����һ��ƥ��AuthenLevel����֤�����б�
   * <p>���û�ж�Ӧ��֤�����б���null</p>
   * @param authenLevel ��֤����
   * @return
   */
  public List<String> getMatachedAuthenMethodList(String authenLevel);
  
  /**
   * ��ѯ��֤������Ӧ����֤����
   * @param authenMethod
   * @return
   */
  public String getAuthenLevel(String authenMethod);

}
