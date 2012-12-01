package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VMfCateDevtypeDao;
import com.ibm.ncs.model.dto.VMfCateDevtype;
import com.ibm.ncs.model.exceptions.VMfCateDevtypeDaoException;
import java.util.List;

public interface VMfCateDevtypeDao
{
	/** 
	 * get table name;
	 */
	public String getTableName();
	
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VMfCateDevtype dto);

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria ''.
	 */
	public List<VMfCateDevtype> findAll() throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'ID = :id'.
	 */
	public List<VMfCateDevtype> findWhereIdEquals(long id) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'NAME = :name'.
	 */
	public List<VMfCateDevtype> findWhereNameEquals(String name) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'FLAG = :flag'.
	 */
	public List<VMfCateDevtype> findWhereFlagEquals(String flag) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'MRID = :mrid'.
	 */
	public List<VMfCateDevtype> findWhereMridEquals(long mrid) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'MRNAME = :mrname'.
	 */
	public List<VMfCateDevtype> findWhereMrnameEquals(String mrname) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'MF_OBJECTID = :mfObjectid'.
	 */
	public List<VMfCateDevtype> findWhereMfObjectidEquals(String mfObjectid) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'MF_DESCRIPTION = :mfDescription'.
	 */
	public List<VMfCateDevtype> findWhereMfDescriptionEquals(String mfDescription) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'DEVICETYPE_MRID = :devicetypeMrid'.
	 */
	public List<VMfCateDevtype> findWhereDevicetypeMridEquals(long devicetypeMrid) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'DTID = :dtid'.
	 */
	public List<VMfCateDevtype> findWhereDtidEquals(long dtid) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'CATEGORY = :category'.
	 */
	public List<VMfCateDevtype> findWhereCategoryEquals(long category) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'SUBCATEGORY = :subcategory'.
	 */
	public List<VMfCateDevtype> findWhereSubcategoryEquals(String subcategory) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'MODEL = :model'.
	 */
	public List<VMfCateDevtype> findWhereModelEquals(String model) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'OBJECTID = :objectid'.
	 */
	public List<VMfCateDevtype> findWhereObjectidEquals(String objectid) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'LOGO = :logo'.
	 */
	public List<VMfCateDevtype> findWhereLogoEquals(String logo) throws VMfCateDevtypeDaoException;

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<VMfCateDevtype> findWhereDescriptionEquals(String description) throws VMfCateDevtypeDaoException;

}
