package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TEventOidInitDao;
import com.ibm.ncs.model.dto.TEventOidInit;
import com.ibm.ncs.model.dto.TPolicyDetailsPk;
import com.ibm.ncs.model.exceptions.TEventOidInitDaoException;
import java.util.List;

public interface TEventOidInitDao
{
	public String getTableName();
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(TEventOidInit dto);

	public void update(TEventOidInit dto);
	
	public void delete(long eveid, long dtid) throws TEventOidInitDaoException;
	
	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria ''.
	 */
	public List<TEventOidInit> findAll() throws TEventOidInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'MRID = :mrid'.
	 */
	public List<TEventOidInit> findWhereMridEquals(long mrid) throws TEventOidInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'DTID = :dtid'.
	 */
	public List<TEventOidInit> findWhereDtidEquals(long dtid) throws TEventOidInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'MODID = :modid'.
	 */
	public List<TEventOidInit> findWhereModidEquals(long modid) throws TEventOidInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'EVEID = :eveid'.
	 */
	public List<TEventOidInit> findWhereEveidEquals(long eveid) throws TEventOidInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'OIDGROUPNAME = :oidgroupname'.
	 */
	public List<TEventOidInit> findWhereOidgroupnameEquals(String oidgroupname) throws TEventOidInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'OID = :oid'.
	 */
	public List<TEventOidInit> findWhereOidEquals(String oid) throws TEventOidInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'UNIT = :unit'.
	 */
	public List<TEventOidInit> findWhereUnitEquals(String unit) throws TEventOidInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TEventOidInit> findWhereDescriptionEquals(String description) throws TEventOidInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'DTID = :dtid and EVEID =: eveid'.
	 */
	public List<TEventOidInit> findWhereDtidNEveidEquals(long dtid, long eveid) throws TEventOidInitDaoException;
	
	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'DTID = :dtid and EVEID =: eveid' and OIDGROUPNAME =: oidGrpName
	 */
	public List<TEventOidInit> findWhereFKsEquals(long dtid, long eveid, String oidGrpName) throws TEventOidInitDaoException; 
}
