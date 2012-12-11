package com.ibm.ncs.model.dao;

import java.util.List;

import com.ibm.ncs.model.dto.PolicyTemplateScope;
import com.ibm.ncs.model.exceptions.TPolicyPublishInfoDaoException;
import com.ibm.ncs.model.exceptions.TPolicyTemplateScopeDaoException;

public interface TPolicyTemplateScopeDao {
	
	/**
	 * @param policyTemplate
	 * @throws TPolicyPublishInfoDaoException
	 */
	void insert(PolicyTemplateScope policyTemplateScope) throws TPolicyTemplateScopeDaoException;
	
	public void delete(PolicyTemplateScope policyTemplateScope) throws TPolicyTemplateScopeDaoException;

	public List<PolicyTemplateScope> findByPtvd(String ptvid) throws TPolicyTemplateScopeDaoException;
	
	public void update(PolicyTemplateScope dto) throws TPolicyTemplateScopeDaoException;
	
	public PolicyTemplateScope findByIDs(long ptvid, long dtid, long mrid) throws TPolicyTemplateScopeDaoException;

	void deleteAllByPtvid(long ptvid) throws TPolicyTemplateScopeDaoException;

}
