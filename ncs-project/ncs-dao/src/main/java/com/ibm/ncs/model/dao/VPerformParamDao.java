package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VPerformParamDao;
import com.ibm.ncs.model.dto.VPerformParam;
import com.ibm.ncs.model.exceptions.VPerformParamDaoException;
import java.util.List;

public interface VPerformParamDao
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
	public void insert(VPerformParam dto);

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria ''.
	 */
	public List<VPerformParam> findAll() throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'EVENTTYPE_MODID = :eventtypeModid'.
	 */
	public List<VPerformParam> findWhereEventtypeModidEquals(long eventtypeModid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'EVENTTYPE_EVEID = :eventtypeEveid'.
	 */
	public List<VPerformParam> findWhereEventtypeEveidEquals(long eventtypeEveid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'ETID = :etid'.
	 */
	public List<VPerformParam> findWhereEtidEquals(long etid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'ESTID = :estid'.
	 */
	public List<VPerformParam> findWhereEstidEquals(long estid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'EVEOTHERNAME = :eveothername'.
	 */
	public List<VPerformParam> findWhereEveothernameEquals(String eveothername) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'ECODE = :ecode'.
	 */
	public List<VPerformParam> findWhereEcodeEquals(long ecode) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'GENERAL = :general'.
	 */
	public List<VPerformParam> findWhereGeneralEquals(long general) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'MAJOR = :major'.
	 */
	public List<VPerformParam> findWhereMajorEquals(String major) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'MINOR = :minor'.
	 */
	public List<VPerformParam> findWhereMinorEquals(String minor) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OTHER = :other'.
	 */
	public List<VPerformParam> findWhereOtherEquals(String other) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'EVENTTYPE_DESCRIPTION = :eventtypeDescription'.
	 */
	public List<VPerformParam> findWhereEventtypeDescriptionEquals(String eventtypeDescription) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'USEFLAG = :useflag'.
	 */
	public List<VPerformParam> findWhereUseflagEquals(String useflag) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OIDGROUP_OPID = :oidgroupOpid'.
	 */
	public List<VPerformParam> findWhereOidgroupOpidEquals(long oidgroupOpid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OIDGROUP_OIDGROUPNAME = :oidgroupOidgroupname'.
	 */
	public List<VPerformParam> findWhereOidgroupOidgroupnameEquals(String oidgroupOidgroupname) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OTYPE = :otype'.
	 */
	public List<VPerformParam> findWhereOtypeEquals(long otype) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OIDGROUP_DESCRIPTION = :oidgroupDescription'.
	 */
	public List<VPerformParam> findWhereOidgroupDescriptionEquals(String oidgroupDescription) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'EVEID = :eveid'.
	 */
	public List<VPerformParam> findWhereEveidEquals(long eveid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'MRID = :mrid'.
	 */
	public List<VPerformParam> findWhereMridEquals(long mrid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'DTID = :dtid'.
	 */
	public List<VPerformParam> findWhereDtidEquals(long dtid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OIDGROUPNAME = :oidgroupname'.
	 */
	public List<VPerformParam> findWhereOidgroupnameEquals(String oidgroupname) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'MODID = :modid'.
	 */
	public List<VPerformParam> findWhereModidEquals(long modid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OID = :oid'.
	 */
	public List<VPerformParam> findWhereOidEquals(String oid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'UNIT = :unit'.
	 */
	public List<VPerformParam> findWhereUnitEquals(String unit) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<VPerformParam> findWhereDescriptionEquals(String description) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'DEVTYPE_MRID = :devtypeMrid'.
	 */
	public List<VPerformParam> findWhereDevtypeMridEquals(long devtypeMrid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'DEVTYPE_DTID = :devtypeDtid'.
	 */
	public List<VPerformParam> findWhereDevtypeDtidEquals(long devtypeDtid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'CATEGORY = :category'.
	 */
	public List<VPerformParam> findWhereCategoryEquals(long category) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'SUBCATEGORY = :subcategory'.
	 */
	public List<VPerformParam> findWhereSubcategoryEquals(String subcategory) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'MODEL = :model'.
	 */
	public List<VPerformParam> findWhereModelEquals(String model) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OBJECTID = :objectid'.
	 */
	public List<VPerformParam> findWhereObjectidEquals(String objectid) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'LOGO = :logo'.
	 */
	public List<VPerformParam> findWhereLogoEquals(String logo) throws VPerformParamDaoException;

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'DEVTYPE_DESCRIPTION = :devtypeDescription'.
	 */
	public List<VPerformParam> findWhereDevtypeDescriptionEquals(String devtypeDescription) throws VPerformParamDaoException;

}
