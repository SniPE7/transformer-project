package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.SnmpEventsProcessDao;
import com.ibm.ncs.model.dto.SnmpEventsProcess;
import com.ibm.ncs.model.dto.SnmpEventsProcessPk;
import com.ibm.ncs.model.exceptions.BkSnmpEventsProcessDaoException;
import com.ibm.ncs.model.exceptions.SnmpEventsProcessDaoException;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface SnmpEventsProcessDao
{
	public String getTableName();
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return SnmpEventsProcessPk
	 */
	public SnmpEventsProcessPk insert(SnmpEventsProcess dto);

	/** 
	 * Updates a single row in the SNMP_EVENTS_PROCESS table.
	 */
	public void update(SnmpEventsProcessPk pk, SnmpEventsProcess dto) throws SnmpEventsProcessDaoException;

	/** 
	 * Deletes a single row in the SNMP_EVENTS_PROCESS table.
	 */
	public void delete(SnmpEventsProcessPk pk) throws SnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria 'MARK = :mark'.
	 */
	public SnmpEventsProcess findByPrimaryKey(String mark) throws SnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria ''.
	 */
	public List<SnmpEventsProcess> findAll() throws SnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria 'MARK = :mark'.
	 */
	public List<SnmpEventsProcess> findWhereMarkEquals(String mark) throws SnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria 'MANUFACTURE = :manufacture'.
	 */
	public List<SnmpEventsProcess> findWhereManufactureEquals(String manufacture) throws SnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria 'RESULTLIST = :resultlist'.
	 */
	public List<SnmpEventsProcess> findWhereResultlistEquals(String resultlist) throws SnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria 'WARNMESSAGE = :warnmessage'.
	 */
	public List<SnmpEventsProcess> findWhereWarnmessageEquals(String warnmessage) throws SnmpEventsProcessDaoException;

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria 'SUMMARY = :summary'.
	 */
	public List<SnmpEventsProcess> findWhereSummaryEquals(String summary) throws SnmpEventsProcessDaoException;

	/** 
	 * Returns the rows from the SNMP_EVENTS_PROCESS table that matches the specified primary-key value.
	 */
	public SnmpEventsProcess findByPrimaryKey(SnmpEventsProcessPk pk) throws SnmpEventsProcessDaoException;

	/**
	 * Method 'update'
	 */
	@Transactional
	public void delete(SnmpEventsProcess dto);
	
	/**
	 * Method 'insert'
	 */
	@Transactional
	public void update(SnmpEventsProcess dto);
	
	public void deleteAll() throws SnmpEventsProcessDaoException;
	
	/**
	 * Batch Insert from BK_SNMP_EVENTS_PROCESS original data rows. for RESTORE usage.
	 */
	public void batchInsertByBkTime(Date bkTime) throws SnmpEventsProcessDaoException;
}
