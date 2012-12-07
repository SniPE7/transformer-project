package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessPk;
import com.ibm.ncs.model.exceptions.SnmpEventsProcessDaoException;
import com.ibm.ncs.model.exceptions.SyslogEventsProcessDaoException;

import java.util.Date;
import java.util.List;

public interface SyslogEventsProcessDao
{
	public String getTableName();
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return SyslogEventsProcessPk
	 */
	public SyslogEventsProcessPk insert(SyslogEventsProcess dto);

	/** 
	 * Updates a single row in the SYSLOG_EVENTS_PROCESS table.
	 */
	public void update(SyslogEventsProcessPk pk, SyslogEventsProcess dto) throws SyslogEventsProcessDaoException;

	/** 
	 * Deletes a single row in the SYSLOG_EVENTS_PROCESS table.
	 */
	public void delete(SyslogEventsProcessPk pk) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'MARK = :mark AND MANUFACTURE = :manufacture'.
	 */
	public SyslogEventsProcess findByPrimaryKey(String mark, String manufacture) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria ''.
	 */
	public List<SyslogEventsProcess> findAll() throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'MARK = :mark'.
	 */
	public List<SyslogEventsProcess> findWhereMarkEquals(String mark) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'VARLIST = :varlist'.
	 */
	public List<SyslogEventsProcess> findWhereVarlistEquals(String varlist) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'BTIMELIST = :btimelist'.
	 */
	public List<SyslogEventsProcess> findWhereBtimelistEquals(String btimelist) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'ETIMELIST = :etimelist'.
	 */
	public List<SyslogEventsProcess> findWhereEtimelistEquals(String etimelist) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'FILTERFLAG1 = :filterflag1'.
	 */
	public List<SyslogEventsProcess> findWhereFilterflag1Equals(long filterflag1) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'FILTERFLAG2 = :filterflag2'.
	 */
	public List<SyslogEventsProcess> findWhereFilterflag2Equals(long filterflag2) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	public List<SyslogEventsProcess> findWhereSeverity1Equals(long severity1) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'SEVERITY2 = :severity2'.
	 */
	public List<SyslogEventsProcess> findWhereSeverity2Equals(long severity2) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'PORT = :port'.
	 */
	public List<SyslogEventsProcess> findWherePortEquals(String port) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'NOTCAREFLAG = :notcareflag'.
	 */
	public List<SyslogEventsProcess> findWhereNotcareflagEquals(long notcareflag) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'TYPE = :type'.
	 */
	public List<SyslogEventsProcess> findWhereTypeEquals(long type) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'EVENTTYPE = :eventtype'.
	 */
	public List<SyslogEventsProcess> findWhereEventtypeEquals(long eventtype) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'SUBEVENTTYPE = :subeventtype'.
	 */
	public List<SyslogEventsProcess> findWhereSubeventtypeEquals(long subeventtype) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'ALERTGROUP = :alertgroup'.
	 */
	public List<SyslogEventsProcess> findWhereAlertgroupEquals(String alertgroup) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'ALERTKEY = :alertkey'.
	 */
	public List<SyslogEventsProcess> findWhereAlertkeyEquals(String alertkey) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'SUMMARYCN = :summarycn'.
	 */
	public List<SyslogEventsProcess> findWhereSummarycnEquals(String summarycn) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	public List<SyslogEventsProcess> findWhereProcesssuggestEquals(String processsuggest) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'STATUS = :status'.
	 */
	public List<SyslogEventsProcess> findWhereStatusEquals(String status) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'ATTENTIONFLAG = :attentionflag'.
	 */
	public List<SyslogEventsProcess> findWhereAttentionflagEquals(long attentionflag) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'EVENTS = :events'.
	 */
	public List<SyslogEventsProcess> findWhereEventsEquals(String events) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'MANUFACTURE = :manufacture'.
	 */
	public List<SyslogEventsProcess> findWhereManufactureEquals(String manufacture) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'ORIGEVENT = :origevent'.
	 */
	public List<SyslogEventsProcess> findWhereOrigeventEquals(String origevent) throws SyslogEventsProcessDaoException;

	/** 
	 * Returns the rows from the SYSLOG_EVENTS_PROCESS table that matches the specified primary-key value.
	 */
	public SyslogEventsProcess findByPrimaryKey(SyslogEventsProcessPk pk) throws SyslogEventsProcessDaoException;

	/**
	 * Method 'update'
	 */
	
	public void delete(SyslogEventsProcess dto);
	
	/**
	 * Method 'insert'
	 */
	
	public void update(SyslogEventsProcess dto);
	
	/**
	 * Update SYSLOG_EVENTS_PROCESS set ATTENTIONFLAG=0 where ATTENTIONFLAG=1
	 * @return
	 */
	public int resetAllAttentionFlags();
	
	/**
	 * Update SYSLOG_EVENTS_PROCESS set NOTCAREFLAG=0 where NOTCAREFLAG=1
	 * @return
	 */
	public int resetAllNotCareFlags();
	
	public int deleteAll() throws SyslogEventsProcessDaoException;
	
	/**
	 * Batch Insert from SYSLOG_EVENTS_PROCESS original data rows, for RESTORE usage.
	 */
	public void batchInsertByBkTime(Date bkTime) throws SyslogEventsProcessDaoException;
}
