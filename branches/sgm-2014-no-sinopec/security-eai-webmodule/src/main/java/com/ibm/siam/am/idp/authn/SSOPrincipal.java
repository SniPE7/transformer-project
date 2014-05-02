package com.ibm.siam.am.idp.authn;

import java.util.List;

/**
 * ��¼�û�Princial
 * @author zhangxianwen
 * @since 2012-7-16 ����4:59:15
 */

public interface SSOPrincipal {

  /** Session�Ự�д洢��¼�û���ϢKEY */
  public final static String NAME_OF_SESSION_ATTR = "_SSO_USER_PRINCIPAL_";
  
  /** Session�Ự�д洢��¼�û������֤����KEY */
  public final static String LAST_REQUIRED_AUTHEN_LEVEL_OF_SESSION_ATTR = "_SSO_USER_LAST_AUTHEN_LEVEL_";

  /**
   * ��ȡͳһID
   * @return String
   */
  public abstract String getUid();

  /**
   * ��ȡ�û�����
   * @return String
   */
  public abstract String getCn();

  /**
   * ��ȡ���һ����֤����
   * <p>�������֤�������򷵻�""</p>
   * @return String
   */
  public String getLastAuthenMethod();
  
  /**
   * �Ƿ����ָ����֤����
   * <p>����Ѿ�ͨ��ָ������֤������֤������true</p>
   * @param authenMethod
   * @return
   */
  public boolean containsAuthenMethod(String authenMethod);
  
  /**
   * @return
   */
  public String getMaxSucceedAuthenLevel();
  
  /**
   * @param successAuthenLevel
   */
  public void addSuccessAuthenLevel(String successAuthenLevel);
  
  /**
   * ��ȡ�����������б�
   * <p>�����������Ϊ��,��list.size=0</p>
   * @return List<String>
   */
  public abstract List<String> getAttributeNames();
  
  /**
   * ������������ȡ����ֵ
   * <p>����ֵ����String����, �������ͽ�����yyyy-MM-dd HH:mm:ss:s.SSSZ��ʽ, ���磺2012-09-14 16:56:02:2.078+0800</p>
   * @param attrName ��������
   * @return String
   */
  public abstract String getSingleValue(String attrName);
  
  /**
   * ������������ȡ����ֵ
   * <p>�������ֵΪ��,��list.size=0</p>
   * <p>����ֵ����String����, �������ͽ�����yyyy-MM-dd HH:mm:ss:s.SSSZ��ʽ, ���磺2012-09-14 16:56:02:2.078+0800</p>
   * @param attrName ��������
   * @return List<String>
   */
  public abstract List<String> getValueAsList(String attrName);

  /**
   * @param authenMethod
   */
  public abstract void addSuccessAuthenMethod(String authenMethod);

}
