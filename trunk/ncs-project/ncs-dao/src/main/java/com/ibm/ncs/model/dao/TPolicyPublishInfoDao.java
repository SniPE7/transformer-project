package com.ibm.ncs.model.dao;

import java.util.List;

import com.ibm.ncs.model.dao.spring.TPolicyPublishInfoDaoException;
import com.ibm.ncs.model.exceptions.TPolicyBaseDaoException;

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
	 * @return
	 * @throws TPolicyPublishInfoDaoException
	 */
	List<PolicyPublishInfo> getAllHistoryAndReleasedVersion() throws TPolicyPublishInfoDaoException;

	/**
	 * @param policyPublishInfo
	 * @throws TPolicyPublishInfoDaoException
	 */
	void insert(PolicyPublishInfo policyPublishInfo) throws TPolicyPublishInfoDaoException;
	
	public void update(long ppiid, PolicyPublishInfo dto) throws TPolicyBaseDaoException;
	
	public void delete(long ppiid) throws TPolicyBaseDaoException;

	public PolicyPublishInfo findById(String parameter) throws TPolicyPublishInfoDaoException;

}
