package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInitPk;
import com.ibm.ncs.model.exceptions.TManufacturerInfoInitDaoException;
import java.util.List;

public interface TManufacturerInfoInitDao
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
	 * @return TManufacturerInfoInitPk
	 */
	public TManufacturerInfoInitPk insert(TManufacturerInfoInit dto);

	/** 
	 * Updates a single row in the T_MANUFACTURER_INFO_INIT table.
	 */
	public void update(TManufacturerInfoInitPk pk, TManufacturerInfoInit dto) throws TManufacturerInfoInitDaoException;

	/** 
	 * Deletes a single row in the T_MANUFACTURER_INFO_INIT table.
	 */
	public void delete(TManufacturerInfoInitPk pk) throws TManufacturerInfoInitDaoException;

	/** 
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the criteria 'MRID = :mrid'.
	 */
	public TManufacturerInfoInit findByPrimaryKey(long mrid) throws TManufacturerInfoInitDaoException;

	/** 
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the criteria ''.
	 */
	public List<TManufacturerInfoInit> findAll() throws TManufacturerInfoInitDaoException;

	/** 
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the criteria 'MRID = :mrid'.
	 */
	public List<TManufacturerInfoInit> findWhereMridEquals(long mrid) throws TManufacturerInfoInitDaoException;

	/** 
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the criteria 'MRNAME = :mrname'.
	 */
	public List<TManufacturerInfoInit> findWhereMrnameEquals(String mrname) throws TManufacturerInfoInitDaoException;

	/** 
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the criteria 'OBJECTID = :objectid'.
	 */
	public List<TManufacturerInfoInit> findWhereObjectidEquals(String objectid) throws TManufacturerInfoInitDaoException;

	/** 
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TManufacturerInfoInit> findWhereDescriptionEquals(String description) throws TManufacturerInfoInitDaoException;

	/** 
	 * Returns the rows from the T_MANUFACTURER_INFO_INIT table that matches the specified primary-key value.
	 */
	public TManufacturerInfoInit findByPrimaryKey(TManufacturerInfoInitPk pk) throws TManufacturerInfoInitDaoException;

}
