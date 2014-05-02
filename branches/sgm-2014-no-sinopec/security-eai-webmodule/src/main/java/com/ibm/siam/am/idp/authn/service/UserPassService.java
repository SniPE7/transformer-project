package com.ibm.siam.am.idp.authn.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import com.ibm.itim.ws.exceptions.WSInvalidLoginException;
import com.ibm.itim.ws.exceptions.WSLoginServiceException;
import com.ibm.siam.am.idp.entity.LdapUserEntity;


/**
 * 用户口令服务接口
 * 
 * @author zhangxianwen
 * @since 2012-6-20 上午9:35:40
 */

public interface UserPassService {

  /**
   * 用户密码最后修改时间
   * 
   * @param ldapUserEntity
   *          用户实体
   * @return Date 用户密码最后修改时间
   * @throws Exception
   */
  public Date getPassLastChanged(LdapUserEntity ldapUserEntity) throws Exception;

  /**
   * 获取用户密码重置状态
   * 
   * @param ldapUserEntity
   *          用户实体
   * @return boolean 密码重置状态；true：重置，false：未重置
   * @throws Exception
   */
  public boolean getPassResetState(LdapUserEntity ldapUserEntity) throws Exception;
  
  /**
   * 获取所有用户密码找回提示问题
   * 
   * @param ldapUserEntity
   *          用户实体
   * @return 密码找回问题信息
   * @throws Exception
   */
  public String[] getPassRecoveryQuestion(LdapUserEntity ldapUserEntity) throws Exception;
  
  /**
   * 获取账号信息
   * 
   * @param username
   *          用户名
   * @return 账号信息
   * @throws Exception
   */
  public LdapUserEntity getLdapUserEntityByUsername(String username) throws Exception;

  /**
   * 设置用户密码提示问题及答案
   * 
   * @param username
   *          用户账号
   * @param question
   *          提示问题
   * @param answer
   *          问题答案
   * @return 设置成功返回true，否则返回false
   */
  public boolean setPassRecoveryQuestion(String username, String question, String answer);
  
  /**
   * 获取所有用户密码找回提示问题
   * 
   * @return String[]密码找回提示问题数组
   * @throws Exception
   */
  public String[] getAllPassQuestion() throws Exception;

  /**
   * 修改用户密码
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
