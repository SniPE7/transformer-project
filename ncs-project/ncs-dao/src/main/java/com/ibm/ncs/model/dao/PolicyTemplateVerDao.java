package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dto.PolicyTemplate;
import com.ibm.ncs.model.exceptions.TPolicyBaseDaoException;
import com.ibm.ncs.model.exceptions.TPolicyPublishInfoDaoException;

public interface PolicyTemplateVerDao {
	
	/**
	 * @param policyTemplate
	 * @throws TPolicyPublishInfoDaoException
	 */
	void insert(PolicyTemplate policyTemplate) throws TPolicyPublishInfoDaoException;
	
	public void update(long ppiid, PolicyPublishInfo dto) throws TPolicyBaseDaoException;
	
	public void delete(long ptid) throws TPolicyBaseDaoException;

	public PolicyTemplate findById(String ptid) throws TPolicyPublishInfoDaoException;

}
