package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.BkSnmpEventsProcessDao;
import com.ibm.ncs.model.dto.BkSnmpEventsProcess;
import com.ibm.ncs.model.dto.BkSnmpEventsProcessPk;
import com.ibm.ncs.model.exceptions.BkSnmpEventsProcessDaoException;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface BkSnmpEventsProcessDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return BkSnmpEventsProcessPk
	 */
	public BkSnmpEventsProcessPk insert(BkSnmpEventsProcess dto);

	/** 
	 * Updates a single row in the BK_SNMP_EVENTS_PROCESS table.
	 */
	public void update(BkSnmpEventsProcessPk pk, BkSnmpEventsProcess dto) throws BkSnmpEventsProcessDaoException;

	/** 
	 * Deletes a single row in the BK_SNMP_EVENTS_PROCESS table.
	 */
	public void delete(BkSnmpEventsProcessPk pk) throws BkSnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'BK_ID = :bkId'.
	 */
	public BkSnmpEventsProcess findByPrimaryKey(long bkId) throws BkSnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria ''.
	 */
	public List<BkSnmpEventsProcess> findAll() throws BkSnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'BK_ID = :bkId'.
	 */
	public List<BkSnmpEventsProcess> findWhereBkIdEquals(long bkId) throws BkSnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'BK_TIME = :bkTime'.
	 */
	public List<BkSnmpEventsProcess> findWhereBkTimeEquals(Date bkTime) throws BkSnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'MARK = :mark'.
	 */
	public List<BkSnmpEventsProcess> findWhereMarkEquals(String mark) throws BkSnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'MANUFACTURE = :manufacture'.
	 */
	public List<BkSnmpEventsProcess> findWhereManufactureEquals(String manufacture) throws BkSnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'RESULTLIST = :resultlist'.
	 */
	public List<BkSnmpEventsProcess> findWhereResultlistEquals(String resultlist) throws BkSnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'WARNMESSAGE = :warnmessage'.
	 */
	public List<BkSnmpEventsProcess> findWhereWarnmessageEquals(String warnmessage) throws BkSnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'SUMMARY = :summary'.
	 */
	public List<BkSnmpEventsProcess> findWhereSummaryEquals(String summary) throws BkSnmpEventsProcessDaoException;

	/** 
	 * Returns the rows from the BK_SNMP_EVENTS_PROCESS table that matches the specified primary-key value.
	 */
	public BkSnmpEventsProcess findByPrimaryKey(BkSnmpEventsProcessPk pk) throws BkSnmpEventsProcessDaoException;

	

	public List<BkSnmpEventsProcess> listByVersion(Date bkTime) throws BkSnmpEventsProcessDaoException;
	
	public List<BkSnmpEventsProcess> listAllVersions() throws BkSnmpEventsProcessDaoException;
	
	public void deleteAll() throws BkSnmpEventsProcessDaoException;
	
	public void deleteByBkTime(Date bkTime) throws BkSnmpEventsProcessDaoException;
	
	public void batchInsertByBkTime(Date bkTime) throws BkSnmpEventsProcessDaoException;
	/** 
	 * delete  rows from the BK_SNMP_EVENTS_PROCESS table except for latest three versions (BK_TIME=version).
	 */
	public void clean4Last3ver() throws BkSnmpEventsProcessDaoException;
}
