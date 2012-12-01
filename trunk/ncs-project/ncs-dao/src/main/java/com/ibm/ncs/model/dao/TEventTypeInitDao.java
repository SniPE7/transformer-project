package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TEventTypeInitPk;
import com.ibm.ncs.model.exceptions.TEventTypeInitDaoException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface TEventTypeInitDao
{
	public String getTableName();
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TEventTypeInitPk
	 */
	public TEventTypeInitPk insert(TEventTypeInit dto);

	/** 
	 * Updates a single row in the T_EVENT_TYPE_INIT table.
	 */
	public void update(TEventTypeInitPk pk, TEventTypeInit dto) throws TEventTypeInitDaoException;

	/** 
	 * Deletes a single row in the T_EVENT_TYPE_INIT table.
	 */
	public void delete(TEventTypeInitPk pk) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'MODID = :modid AND EVEID = :eveid'.
	 */
	public TEventTypeInit findByPrimaryKey(long modid, long eveid) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria ''.
	 */
	public List<TEventTypeInit> findAll() throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'ETID = :etid'.
	 */
	public List<TEventTypeInit> findWhereEtidEquals(long etid) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'MODID = :modid'.
	 */
	public List<TEventTypeInit> findWhereModidEquals(long modid) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'EVEID = :eveid'.
	 */
	public List<TEventTypeInit> findWhereEveidEquals(long eveid) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'ESTID = :estid'.
	 */
	public List<TEventTypeInit> findWhereEstidEquals(long estid) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'EVEOTHERNAME = :eveothername'.
	 */
	public List<TEventTypeInit> findWhereEveothernameEquals(String eveothername) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'ECODE = :ecode'.
	 */
	public List<TEventTypeInit> findWhereEcodeEquals(long ecode) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'GENERAL = :general'.
	 */
	public List<TEventTypeInit> findWhereGeneralEquals(long general) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'MAJOR = :major'.
	 */
	public List<TEventTypeInit> findWhereMajorEquals(String major) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'MINOR = :minor'.
	 */
	public List<TEventTypeInit> findWhereMinorEquals(String minor) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'OTHER = :other'.
	 */
	public List<TEventTypeInit> findWhereOtherEquals(String other) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TEventTypeInit> findWhereDescriptionEquals(String description) throws TEventTypeInitDaoException;

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'USEFLAG = :useflag'.
	 */
	public List<TEventTypeInit> findWhereUseflagEquals(String useflag) throws TEventTypeInitDaoException;

	/** 
	 * Returns the rows from the T_EVENT_TYPE_INIT table that matches the specified primary-key value.
	 */
	public TEventTypeInit findByPrimaryKey(TEventTypeInitPk pk) throws TEventTypeInitDaoException;

	
	//device filter 
	//port filter
	//pre def mib index filter
	public List<TEventTypeInit> findByGeneralEcodeModid( long ecode, long modid) throws TEventTypeInitDaoException;
	
	public List<TEventTypeInit> findWhereMajorMinorOtherEquals(String major, String minor, String other) throws TEventTypeInitDaoException;
	/** 
	 * Returns rows from the T_EVENT_TYPE_INIT table that match the criteria 'GENERAL = :general'.
	 * filter for device =1, port=6, predef mib index=9, by ecode =1,6, 9
	 * filter for snmp by  modid=####
	 */
	@Transactional
	public List<TEventTypeInit> listForDeviceSyslog(long mpid  ) throws TEventTypeInitDaoException;

	/** 
	 * Returns rows from the T_EVENT_TYPE_INIT table that match the criteria 'GENERAL = :general'.
	 * filter for device =1, port=6, predef mib index=9, by ecode =1,6, 9
	 * filter for snmp by  modid=####
	 */
	@Transactional
	public List<TEventTypeInit> listForPortSyslog(long mpid  ) throws TEventTypeInitDaoException;

	public List<TEventTypeInit> listForDeviceSnmp(long mpid  ) throws TEventTypeInitDaoException;

	/** 
	 * Returns rows from the T_EVENT_TYPE_INIT table that match the criteria 'GENERAL = :general'.
	 * filter for device =1, port=6, predef mib index=9, by ecode =1,6, 9
	 * filter for snmp by   modid=####
	 */
	@Transactional
	public List<TEventTypeInit> listForPortSnmp(long mpid ) throws TEventTypeInitDaoException;
	public List<TEventTypeInit> listForPreDefMibSnmp(long mpid ) throws TEventTypeInitDaoException;
	
	
	public List<TEventTypeInit> listForDeviceIcmp( long mpid ) throws TEventTypeInitDaoException;

	/** 
	 * Returns rows from the T_EVENT_TYPE_INIT table that match the criteria 'GENERAL = :general'.
	 * filter for device =1, port=6, predef mib index=9, by ecode =1,6, 9
	 * filter for snmp by  modid=####
	 */
	@Transactional
	public List<TEventTypeInit> listForPortIcmp(long mpid  ) throws TEventTypeInitDaoException;
	
	public List listMajor() throws TEventTypeInitDaoException;

}
