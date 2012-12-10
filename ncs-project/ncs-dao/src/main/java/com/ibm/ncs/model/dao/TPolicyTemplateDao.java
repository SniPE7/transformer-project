package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dto.PolicyTemplate;
import com.ibm.ncs.model.exceptions.TPolicyPublishInfoDaoException;
import com.ibm.ncs.model.exceptions.TPolicyTemplateDaoException;

public interface TPolicyTemplateDao {
	
	/**
	 * @param policyTemplate
	 * @throws TPolicyPublishInfoDaoException
	 */
	void insert(PolicyTemplate policyTemplate) throws TPolicyTemplateDaoException;
	
	public void update(long ppiid, PolicyTemplate dto) throws TPolicyTemplateDaoException;
	
	public void delete(long ptid) throws TPolicyTemplateDaoException;

	public PolicyTemplate findById(String ptid) throws TPolicyTemplateDaoException;

	public PolicyTemplate findByMpname(String mpname) throws TPolicyTemplateDaoException;

}
