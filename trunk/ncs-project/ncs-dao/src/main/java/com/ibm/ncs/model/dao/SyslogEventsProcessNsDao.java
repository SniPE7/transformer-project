package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.model.dto.SyslogEventsProcessNsPk;
import com.ibm.ncs.model.exceptions.SnmpEventsProcessDaoException;
import com.ibm.ncs.model.exceptions.SyslogEventsProcessNsDaoException;

import java.util.Date;
import java.util.List;

public interface SyslogEventsProcessNsDao
{
	public String getTableName();
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return SyslogEventsProcessNsPk
	 */
	public SyslogEventsProcessNsPk insert(SyslogEventsProcessNs dto);

	/** 
	 * Updates a single row in the SYSLOG_EVENTS_PROCESS_NS table.
	 */
	public void update(SyslogEventsProcessNsPk pk, SyslogEventsProcessNs dto) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Deletes a single row in the SYSLOG_EVENTS_PROCESS_NS table.
	 */
	public void delete(SyslogEventsProcessNsPk pk) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'MARK = :mark AND MANUFACTURE = :manufacture'.
	 */
	public SyslogEventsProcessNs findByPrimaryKey(String mark, String manufacture) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria ''.
	 */
	public List<SyslogEventsProcessNs> findAll() throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'MARK = :mark'.
	 */
	public List<SyslogEventsProcessNs> findWhereMarkEquals(String mark) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'VARLIST = :varlist'.
	 */
	public List<SyslogEventsProcessNs> findWhereVarlistEquals(String varlist) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'BTIMELIST = :btimelist'.
	 */
	public List<SyslogEventsProcessNs> findWhereBtimelistEquals(String btimelist) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ETIMELIST = :etimelist'.
	 */
	public List<SyslogEventsProcessNs> findWhereEtimelistEquals(String etimelist) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'FILTERFLAG1 = :filterflag1'.
	 */
	public List<SyslogEventsProcessNs> findWhereFilterflag1Equals(long filterflag1) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'FILTERFLAG2 = :filterflag2'.
	 */
	public List<SyslogEventsProcessNs> findWhereFilterflag2Equals(long filterflag2) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	public List<SyslogEventsProcessNs> findWhereSeverity1Equals(long severity1) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'SEVERITY2 = :severity2'.
	 */
	public List<SyslogEventsProcessNs> findWhereSeverity2Equals(long severity2) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'PORT = :port'.
	 */
	public List<SyslogEventsProcessNs> findWherePortEquals(String port) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'NOTCAREFLAG = :notcareflag'.
	 */
	public List<SyslogEventsProcessNs> findWhereNotcareflagEquals(long notcareflag) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'TYPE = :type'.
	 */
	public List<SyslogEventsProcessNs> findWhereTypeEquals(long type) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'EVENTTYPE = :eventtype'.
	 */
	public List<SyslogEventsProcessNs> findWhereEventtypeEquals(long eventtype) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'SUBEVENTTYPE = :subeventtype'.
	 */
	public List<SyslogEventsProcessNs> findWhereSubeventtypeEquals(long subeventtype) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ALERTGROUP = :alertgroup'.
	 */
	public List<SyslogEventsProcessNs> findWhereAlertgroupEquals(String alertgroup) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ALERTKEY = :alertkey'.
	 */
	public List<SyslogEventsProcessNs> findWhereAlertkeyEquals(String alertkey) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'SUMMARYCN = :summarycn'.
	 */
	public List<SyslogEventsProcessNs> findWhereSummarycnEquals(String summarycn) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	public List<SyslogEventsProcessNs> findWhereProcesssuggestEquals(String processsuggest) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'STATUS = :status'.
	 */
	public List<SyslogEventsProcessNs> findWhereStatusEquals(String status) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ATTENTIONFLAG = :attentionflag'.
	 */
	public List<SyslogEventsProcessNs> findWhereAttentionflagEquals(long attentionflag) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'EVENTS = :events'.
	 */
	public List<SyslogEventsProcessNs> findWhereEventsEquals(String events) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'MANUFACTURE = :manufacture'.
	 */
	public List<SyslogEventsProcessNs> findWhereManufactureEquals(String manufacture) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ORIGEVENT = :origevent'.
	 */
	public List<SyslogEventsProcessNs> findWhereOrigeventEquals(String origevent) throws SyslogEventsProcessNsDaoException;

	/** 
	 * Returns the rows from the SYSLOG_EVENTS_PROCESS_NS table that matches the specified primary-key value.
	 */
	public SyslogEventsProcessNs findByPrimaryKey(SyslogEventsProcessNsPk pk) throws SyslogEventsProcessNsDaoException;


	/**
	 * Method 'update'
	 * 
	 * @param dto
	 */
	
	public void update(SyslogEventsProcessNs dto);

	/**
	 * Method 'delete'
	 * 
	 * @param dto
	 */
	
	public int delete(SyslogEventsProcessNs dto);
	
	public int resetAllAttentionFlags();
	
	public int resetAllNotCareFlags();
	
	public int deleteAll() throws SyslogEventsProcessNsDaoException;

	/**
	 * Batch Insert from BK_SYSLOG_EVENTS_PROCESS_NS original data rows, for RESTORE usage.
	 */
	public void batchInsertByBkTime(Date bkTime) throws SyslogEventsProcessNsDaoException;
	
}
