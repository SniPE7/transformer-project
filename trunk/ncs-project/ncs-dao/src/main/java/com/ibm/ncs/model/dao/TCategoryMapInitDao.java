package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TCategoryMapInitPk;
import com.ibm.ncs.model.exceptions.TCategoryMapInitDaoException;
import java.util.List;

public interface TCategoryMapInitDao
{
	/**
	 * 
	 * @return table Name
	 */
	public String getTableName();
	
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TCategoryMapInitPk
	 */
	public TCategoryMapInitPk insert(TCategoryMapInit dto);

	/** 
	 * Updates a single row in the T_CATEGORY_MAP_INIT table.
	 */
	public void update(TCategoryMapInitPk pk, TCategoryMapInit dto) throws TCategoryMapInitDaoException;

	/** 
	 * Deletes a single row in the T_CATEGORY_MAP_INIT table.
	 */
	public void delete(TCategoryMapInitPk pk) throws TCategoryMapInitDaoException;

	/** 
	 * Returns all rows from the T_CATEGORY_MAP_INIT table that match the criteria 'ID = :id'.
	 */
	public TCategoryMapInit findByPrimaryKey(long id) throws TCategoryMapInitDaoException;

	/** 
	 * Returns all rows from the T_CATEGORY_MAP_INIT table that match the criteria ''.
	 */
	public List<TCategoryMapInit> findAll() throws TCategoryMapInitDaoException;

	/** 
	 * Returns all rows from the T_CATEGORY_MAP_INIT table that match the criteria 'ID = :id'.
	 */
	public List<TCategoryMapInit> findWhereIdEquals(long id) throws TCategoryMapInitDaoException;

	/** 
	 * Returns all rows from the T_CATEGORY_MAP_INIT table that match the criteria 'NAME = :name'.
	 */
	public List<TCategoryMapInit> findWhereNameEquals(String name) throws TCategoryMapInitDaoException;

	/** 
	 * Returns all rows from the T_CATEGORY_MAP_INIT table that match the criteria 'FLAG = :flag'.
	 */
	public List<TCategoryMapInit> findWhereFlagEquals(String flag) throws TCategoryMapInitDaoException;

	/** 
	 * Returns the rows from the T_CATEGORY_MAP_INIT table that matches the specified primary-key value.
	 */
	public TCategoryMapInit findByPrimaryKey(TCategoryMapInitPk pk) throws TCategoryMapInitDaoException;

}
