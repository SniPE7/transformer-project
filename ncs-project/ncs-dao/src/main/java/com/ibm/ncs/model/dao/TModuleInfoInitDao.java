package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TModuleInfoInitPk;
import com.ibm.ncs.model.exceptions.TModuleInfoInitDaoException;
import java.util.List;

public interface TModuleInfoInitDao
{
	public String getTableName();
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TModuleInfoInitPk
	 */
	public TModuleInfoInitPk insert(TModuleInfoInit dto);

	/** 
	 * Updates a single row in the T_MODULE_INFO_INIT table.
	 */
	public void update(TModuleInfoInitPk pk, TModuleInfoInit dto) throws TModuleInfoInitDaoException;

	/** 
	 * Deletes a single row in the T_MODULE_INFO_INIT table.
	 */
	public void delete(TModuleInfoInitPk pk) throws TModuleInfoInitDaoException;

	/** 
	 * Returns all rows from the T_MODULE_INFO_INIT table that match the criteria 'MODID = :modid'.
	 */
	public TModuleInfoInit findByPrimaryKey(long modid) throws TModuleInfoInitDaoException;

	/** 
	 * Returns all rows from the T_MODULE_INFO_INIT table that match the criteria ''.
	 */
	public List<TModuleInfoInit> findAll() throws TModuleInfoInitDaoException;

	/** 
	 * Returns all rows from the T_MODULE_INFO_INIT table that match the criteria 'MODID = :modid'.
	 */
	public List<TModuleInfoInit> findWhereModidEquals(long modid) throws TModuleInfoInitDaoException;

	/** 
	 * Returns all rows from the T_MODULE_INFO_INIT table that match the criteria 'MNAME = :mname'.
	 */
	public List<TModuleInfoInit> findWhereMnameEquals(String mname) throws TModuleInfoInitDaoException;

	/** 
	 * Returns all rows from the T_MODULE_INFO_INIT table that match the criteria 'MCODE = :mcode'.
	 */
	public List<TModuleInfoInit> findWhereMcodeEquals(long mcode) throws TModuleInfoInitDaoException;

	/** 
	 * Returns all rows from the T_MODULE_INFO_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TModuleInfoInit> findWhereDescriptionEquals(String description) throws TModuleInfoInitDaoException;

	/** 
	 * Returns the rows from the T_MODULE_INFO_INIT table that matches the specified primary-key value.
	 */
	public TModuleInfoInit findByPrimaryKey(TModuleInfoInitPk pk) throws TModuleInfoInitDaoException;

}
