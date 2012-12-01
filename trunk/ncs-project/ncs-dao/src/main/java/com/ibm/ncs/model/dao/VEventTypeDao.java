package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VEventTypeDao;
import com.ibm.ncs.model.dto.VEventType;
import com.ibm.ncs.model.exceptions.VEventTypeDaoException;
import java.util.List;

public interface VEventTypeDao
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
	public void insert(VEventType dto);

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria ''.
	 */
	public List<VEventType> findAll() throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'MODID = :modid'.
	 */
	public List<VEventType> findWhereModidEquals(long modid) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'EVEID = :eveid'.
	 */
	public List<VEventType> findWhereEveidEquals(long eveid) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'ETID = :etid'.
	 */
	public List<VEventType> findWhereEtidEquals(long etid) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'ESTID = :estid'.
	 */
	public List<VEventType> findWhereEstidEquals(long estid) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'EVEOTHERNAME = :eveothername'.
	 */
	public List<VEventType> findWhereEveothernameEquals(String eveothername) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'ECODE = :ecode'.
	 */
	public List<VEventType> findWhereEcodeEquals(long ecode) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'GENERAL = :general'.
	 */
	public List<VEventType> findWhereGeneralEquals(long general) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'MAJOR = :major'.
	 */
	public List<VEventType> findWhereMajorEquals(String major) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'MINOR = :minor'.
	 */
	public List<VEventType> findWhereMinorEquals(String minor) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'OTHER = :other'.
	 */
	public List<VEventType> findWhereOtherEquals(String other) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<VEventType> findWhereDescriptionEquals(String description) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'USEFLAG = :useflag'.
	 */
	public List<VEventType> findWhereUseflagEquals(String useflag) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'T_MODULE_INFO_INIT_MODID = :tModuleInfoInitModid'.
	 */
	public List<VEventType> findWhereTModuleInfoInitModidEquals(long tModuleInfoInitModid) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'MNAME = :mname'.
	 */
	public List<VEventType> findWhereMnameEquals(String mname) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'MCODE = :mcode'.
	 */
	public List<VEventType> findWhereMcodeEquals(long mcode) throws VEventTypeDaoException;

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'T_MODULE_INFO_INIT_DESCRIPTION = :tModuleInfoInitDescription'.
	 */
	public List<VEventType> findWhereTModuleInfoInitDescriptionEquals(String tModuleInfoInitDescription) throws VEventTypeDaoException;

}
