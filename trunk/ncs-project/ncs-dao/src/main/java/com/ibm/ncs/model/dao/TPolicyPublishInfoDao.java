package com.ibm.ncs.model.dao;

import java.util.List;

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
	 * ���ش��ڱ�д״̬�Ĳ��Լ�
	 * @return
	 * @throws TPolicyPublishInfoDaoException
	 */
	List<PolicyPublishInfo> getAllHistoryAndReleasedVersion() throws TPolicyPublishInfoDaoException;

	/**
	 * ���ط����Ĳ��Լ�
	 * @return
	 * @throws TPolicyPublishInfoDaoException
	 */
	public PolicyPublishInfo getReleasedVersion() throws TPolicyPublishInfoDaoException;

	/**
	 * ����������ʷ�汾�Ĳ��Լ�
	 * @return
	 * @throws TPolicyPublishInfoDaoException
	 */
	public List<PolicyPublishInfo> getHistoryVersions() throws TPolicyPublishInfoDaoException;
	/**
	 * @param policyPublishInfo
	 * @throws TPolicyPublishInfoDaoException
	 */
	void insert(PolicyPublishInfo policyPublishInfo) throws TPolicyPublishInfoDaoException;
	
	public void update(long ppiid, PolicyPublishInfo dto) throws TPolicyBaseDaoException;
	
	public void delete(long ppiid) throws TPolicyBaseDaoException;

	public PolicyPublishInfo findById(String parameter) throws TPolicyPublishInfoDaoException;
	
	/**
	 * �������е�PolicyPublishInfo
	 * @param srcPpiid
	 * @param destPpiid
	 * @throws TPolicyPublishInfoDaoException
	 */
	public void copyAllPolicyTemplateVer(long srcPpiid, long destPpiid) throws TPolicyPublishInfoDaoException;


}
