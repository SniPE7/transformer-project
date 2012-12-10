package com.ibm.ncs.model.dao;

import java.util.List;

import com.ibm.ncs.model.dto.PolicyTemplateVer;
import com.ibm.ncs.model.exceptions.TPolicyTemplateVerDaoException;

public interface TPolicyTemplateVerDao {
	
	/**
	 * @param  dto
	 * @throws TPolicyTemplateVerDaoException
	 */
	void insert(PolicyTemplateVer  dto) throws TPolicyTemplateVerDaoException;
	
	public void update(long ppiid, PolicyTemplateVer dto) throws TPolicyTemplateVerDaoException;
	
	public void delete(long ptid) throws TPolicyTemplateVerDaoException;

	public PolicyTemplateVer findById(String ptid) throws TPolicyTemplateVerDaoException;
	
	/**
	 * �������з�������ppiid���¼�
	 * @param ppiid
	 * @return
	 * @throws TPolicyTemplateVerDaoException
	 */
	public List<PolicyTemplateVer> findByPublishInfoId(String ppiid) throws TPolicyTemplateVerDaoException;

}
