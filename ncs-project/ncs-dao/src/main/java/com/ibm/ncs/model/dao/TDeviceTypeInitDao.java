package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TDeviceTypeInitPk;
import com.ibm.ncs.model.exceptions.TDeviceTypeInitDaoException;
import java.util.List;

public interface TDeviceTypeInitDao
{
	public String getTableName();
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TDeviceTypeInitPk
	 */
	public TDeviceTypeInitPk insert(TDeviceTypeInit dto);

	/** 
	 * Updates a single row in the T_DEVICE_TYPE_INIT table.
	 */
	public void update(TDeviceTypeInitPk pk, TDeviceTypeInit dto) throws TDeviceTypeInitDaoException;

	/** 
	 * Deletes a single row in the T_DEVICE_TYPE_INIT table.
	 */
	public void delete(TDeviceTypeInitPk pk) throws TDeviceTypeInitDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'DTID = :dtid'.
	 */
	public TDeviceTypeInit findByPrimaryKey(long dtid) throws TDeviceTypeInitDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria ''.
	 */
	public List<TDeviceTypeInit> findAll() throws TDeviceTypeInitDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'MRID = :mrid'.
	 */
	public List<TDeviceTypeInit> findWhereMridEquals(long mrid) throws TDeviceTypeInitDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'DTID = :dtid'.
	 */
	public List<TDeviceTypeInit> findWhereDtidEquals(long dtid) throws TDeviceTypeInitDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'CATEGORY = :category'.
	 */
	public List<TDeviceTypeInit> findWhereCategoryEquals(long category) throws TDeviceTypeInitDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'SUBCATEGORY = :subcategory'.
	 */
	public List<TDeviceTypeInit> findWhereSubcategoryEquals(String subcategory) throws TDeviceTypeInitDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'MODEL = :model'.
	 */
	public List<TDeviceTypeInit> findWhereModelEquals(String model) throws TDeviceTypeInitDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'OBJECTID = :objectid'.
	 */
	public List<TDeviceTypeInit> findWhereObjectidEquals(String objectid) throws TDeviceTypeInitDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'LOGO = :logo'.
	 */
	public List<TDeviceTypeInit> findWhereLogoEquals(String logo) throws TDeviceTypeInitDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TDeviceTypeInit> findWhereDescriptionEquals(String description) throws TDeviceTypeInitDaoException;

	/** 
	 * Returns the rows from the T_DEVICE_TYPE_INIT table that matches the specified primary-key value.
	 */
	public TDeviceTypeInit findByPrimaryKey(TDeviceTypeInitPk pk) throws TDeviceTypeInitDaoException;
	
	public TDeviceTypeInit findByMul(long mrid,long category,String subcate,String objid) throws TDeviceTypeInitDaoException;

}
