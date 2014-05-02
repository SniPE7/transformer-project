package com.ibm.siam.am.idp.authn.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import com.ibm.itim.ws.exceptions.WSInvalidLoginException;
import com.ibm.itim.ws.exceptions.WSLoginServiceException;
import com.ibm.siam.am.idp.entity.LdapUserEntity;


/**
 * �û��������ӿ�
 * 
 * @author zhangxianwen
 * @since 2012-6-20 ����9:35:40
 */

public interface UserPassService {

  /**
   * �û���������޸�ʱ��
   * 
   * @param ldapUserEntity
   *          �û�ʵ��
   * @return Date �û���������޸�ʱ��
   * @throws Exception
   */
  public Date getPassLastChanged(LdapUserEntity ldapUserEntity) throws Exception;

  /**
   * ��ȡ�û���������״̬
   * 
   * @param ldapUserEntity
   *          �û�ʵ��
   * @return boolean ��������״̬��true�����ã�false��δ����
   * @throws Exception
   */
  public boolean getPassResetState(LdapUserEntity ldapUserEntity) throws Exception;
  
  /**
   * ��ȡ�����û������һ���ʾ����
   * 
   * @param ldapUserEntity
   *          �û�ʵ��
   * @return �����һ�������Ϣ
   * @throws Exception
   */
  public String[] getPassRecoveryQuestion(LdapUserEntity ldapUserEntity) throws Exception;
  
  /**
   * ��ȡ�˺���Ϣ
   * 
   * @param username
   *          �û���
   * @return �˺���Ϣ
   * @throws Exception
   */
  public LdapUserEntity getLdapUserEntityByUsername(String username) throws Exception;

  /**
   * �����û�������ʾ���⼰��
   * 
   * @param username
   *          �û��˺�
   * @param question
   *          ��ʾ����
   * @param answer
   *          �����
   * @return ���óɹ�����true�����򷵻�false
   */
  public boolean setPassRecoveryQuestion(String username, String question, String answer);
  
  /**
   * ��ȡ�����û������һ���ʾ����
   * 
   * @return String[]�����һ���ʾ��������
   * @throws Exception
   */
  public String[] getAllPassQuestion() throws Exception;

  /**
   * �޸��û�����
   * 
   * @param username
   * @param newPassword
   * @throws ServiceException
   * @throws MalformedURLException
   * @throws RemoteException
   * @throws WSLoginServiceException
   * @throws WSInvalidLoginException
   * @throws javax.xml.rpc.ServiceException 
   */
  public void updatePassword(String username, String newPassword) throws MalformedURLException, WSInvalidLoginException, WSLoginServiceException, RemoteException, ServiceException;
}
