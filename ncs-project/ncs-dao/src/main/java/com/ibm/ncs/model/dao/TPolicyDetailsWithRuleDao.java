package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dto.TPolicyDetailsWithRule;
import com.ibm.ncs.model.dto.TPolicyDetailsWithRulePk;
import com.ibm.ncs.model.exceptions.TPolicyDetailsWithRuleDaoException;

public interface TPolicyDetailsWithRuleDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TPolicyDetailsPk
	 */
	public TPolicyDetailsWithRulePk insert(TPolicyDetailsWithRule dto);

	/** 
	 * Updates a single row in the T_POLICY_DETAILS table.
	 */
	public void update(TPolicyDetailsWithRulePk pk, TPolicyDetailsWithRule dto) throws TPolicyDetailsWithRuleDaoException;

	/** 
	 * Deletes a single row in the T_POLICY_DETAILS table.
	 */
	public void delete(TPolicyDetailsWithRulePk pk) throws TPolicyDetailsWithRuleDaoException;
	
	public void delete(long ptvid) throws TPolicyDetailsWithRuleDaoException;
	
	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName();

	public TPolicyDetailsWithRule findByEveidAndModid(long eveid, long modid) throws TPolicyDetailsWithRuleDaoException;
}
