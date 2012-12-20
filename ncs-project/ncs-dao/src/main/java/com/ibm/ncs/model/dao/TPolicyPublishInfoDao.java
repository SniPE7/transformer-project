package com.ibm.ncs.model.dao;

import java.util.List;

import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.model.exceptions.TPolicyBaseDaoException;
import com.ibm.ncs.model.exceptions.TPolicyPublishInfoDaoException;

public interface TPolicyPublishInfoDao {
	
	/**
	 * @return
	 * @throws TPolicyPublishInfoDaoException
	 */
	public PolicyPublishInfo getDraftVersion() throws TPolicyPublishInfoDaoException;

	/**
	 * @return
	 * @throws TPolicyPublishInfoDaoException
	 */
	String getNewVersion4Draft() throws TPolicyPublishInfoDaoException;

	/**
	 * 返回处于编写状态的策略集
	 * @return
	 * @throws TPolicyPublishInfoDaoException
	 */
	List<PolicyPublishInfo> getAllHistoryAndReleasedVersion() throws TPolicyPublishInfoDaoException;

	/**
	 * 返回发布的策略集
	 * @return
	 * @throws TPolicyPublishInfoDaoException
	 */
	public PolicyPublishInfo getReleasedVersion() throws TPolicyPublishInfoDaoException;

	/**
	 * 返回所有历史版本的策略集
	 * @return
	 * @throws TPolicyPublishInfoDaoException
	 */
	public List<PolicyPublishInfo> getHistoryVersions() throws TPolicyPublishInfoDaoException;
	/**
	 * @param policyPublishInfo
	 * @throws TPolicyPublishInfoDaoException
	 */
	void insert(PolicyPublishInfo policyPublishInfo) throws TPolicyPublishInfoDaoException;
	
	public void update(long ppiid, PolicyPublishInfo dto) throws TPolicyPublishInfoDaoException;
	
	public void release(long toPpiid, PolicyPublishInfo toDto) throws TPolicyPublishInfoDaoException;
	
	public void delete(long ppiid) throws TPolicyBaseDaoException;

	public PolicyPublishInfo findById(String parameter) throws TPolicyPublishInfoDaoException;
	
	/**
	 * 复制所有的PolicyPublishInfo
	 * @param srcPpiid
	 * @param destPpiid
	 * @throws TPolicyPublishInfoDaoException
	 */
	public void copyAllPolicyTemplateVer(long srcPpiid, long destPpiid) throws TPolicyPublishInfoDaoException;


}
