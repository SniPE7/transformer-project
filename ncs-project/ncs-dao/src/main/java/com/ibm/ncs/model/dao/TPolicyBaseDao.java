package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyBasePk;
import com.ibm.ncs.model.exceptions.TPolicyBaseDaoException;
import java.util.List;

public interface TPolicyBaseDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TPolicyBasePk
	 */
	public TPolicyBasePk insert(TPolicyBase dto);

	/** 
	 * Updates a single row in the T_POLICY_BASE table.
	 */
	public void update(TPolicyBasePk pk, TPolicyBase dto) throws TPolicyBaseDaoException;

	/** 
	 * Deletes a single row in the T_POLICY_BASE table.
	 */
	public void delete(TPolicyBasePk pk) throws TPolicyBaseDaoException;

	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria 'MPID = :mpid'.
	 */
	public TPolicyBase findByPrimaryKey(long mpid) throws TPolicyBaseDaoException;

	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria ''.
	 */
	public List<TPolicyBase> findAll() throws TPolicyBaseDaoException;
	
	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria ''.
	 */
	public List<TPolicyBase> findAllOrderBy(String orderbyCol) throws TPolicyBaseDaoException;
	
	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria 'MPID = :mpid'.
	 */
	public List<TPolicyBase> findWhereMpidEquals(long mpid) throws TPolicyBaseDaoException;

	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria 'MPNAME = :mpname'.
	 */
	public List<TPolicyBase> findWhereMpnameEquals(String mpname) throws TPolicyBaseDaoException;

	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria 'CATEGORY = :category'.
	 */
	public List<TPolicyBase> findWhereCategoryEquals(long category) throws TPolicyBaseDaoException;

	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TPolicyBase> findWhereDescriptionEquals(String description) throws TPolicyBaseDaoException;

	/** 
	 * Returns the rows from the T_POLICY_BASE table that matches the specified primary-key value.
	 */
	public TPolicyBase findByPrimaryKey(TPolicyBasePk pk) throws TPolicyBaseDaoException;
	
	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName();

}
