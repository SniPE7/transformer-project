package com.ibm.ncs.model.dao;

import java.io.Writer;
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
	 * ���ص�ǰ�����Ѿ����õĲ��Լ�
	 * @return
	 * @throws TPolicyPublishInfoDaoException
	 */
	public PolicyPublishInfo getAppliedVersion() throws TPolicyPublishInfoDaoException;

	/**
	 * @param policyPublishInfo
	 * @throws TPolicyPublishInfoDaoException
	 */
	void insert(PolicyPublishInfo policyPublishInfo) throws TPolicyPublishInfoDaoException;
	
	public void update(long ppiid, PolicyPublishInfo dto) throws TPolicyPublishInfoDaoException;
	
	/**
	 * ��������
	 * @throws TPolicyPublishInfoDaoException
	 */
	public void release(long toPpiid, PolicyPublishInfo toDto) throws TPolicyPublishInfoDaoException;
	
	/**
	 * �����ز������ݴ�һ���汾����������İ汾
	 * @throws TPolicyPublishInfoDaoException
	 */
	public void upgrade(Writer writer) throws TPolicyPublishInfoDaoException;
	
	/**
	 * �����ز���Ǩ�Ƶ������汾����������ͬ������������mpname��鱾�ز����Ƿ����ڲ���ģ�弯���������������򲻻ῼ��mpname�Ƿ���ȵ�����
	 * @throws TPolicyPublishInfoDaoException
	 */
	public void migrate(Writer writer) throws TPolicyPublishInfoDaoException;

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
