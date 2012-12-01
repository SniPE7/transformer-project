package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.BkSyslogEventsProcessDao;
import com.ibm.ncs.model.dto.BkSyslogEventsProcess;
import com.ibm.ncs.model.dto.BkSyslogEventsProcessPk;
import com.ibm.ncs.model.exceptions.BkSyslogEventsProcessDaoException;
import java.util.Date;
import java.util.List;

public interface BkSyslogEventsProcessDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return BkSyslogEventsProcessPk
	 */
	public BkSyslogEventsProcessPk insert(BkSyslogEventsProcess dto);

	/** 
	 * Updates a single row in the BK_SYSLOG_EVENTS_PROCESS table.
	 */
	public void update(BkSyslogEventsProcessPk pk, BkSyslogEventsProcess dto) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Deletes a single row in the BK_SYSLOG_EVENTS_PROCESS table.
	 */
	public void delete(BkSyslogEventsProcessPk pk) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'BK_ID = :bkId'.
	 */
	public BkSyslogEventsProcess findByPrimaryKey(long bkId) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria ''.
	 */
	public List<BkSyslogEventsProcess> findAll() throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'BK_ID = :bkId'.
	 */
	public List<BkSyslogEventsProcess> findWhereBkIdEquals(long bkId) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'BK_TIME = :bkTime'.
	 */
	public List<BkSyslogEventsProcess> findWhereBkTimeEquals(Date bkTime) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'MARK = :mark'.
	 */
	public List<BkSyslogEventsProcess> findWhereMarkEquals(String mark) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'VARLIST = :varlist'.
	 */
	public List<BkSyslogEventsProcess> findWhereVarlistEquals(String varlist) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'BTIMELIST = :btimelist'.
	 */
	public List<BkSyslogEventsProcess> findWhereBtimelistEquals(String btimelist) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'ETIMELIST = :etimelist'.
	 */
	public List<BkSyslogEventsProcess> findWhereEtimelistEquals(String etimelist) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'FILTERFLAG1 = :filterflag1'.
	 */
	public List<BkSyslogEventsProcess> findWhereFilterflag1Equals(long filterflag1) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'FILTERFLAG2 = :filterflag2'.
	 */
	public List<BkSyslogEventsProcess> findWhereFilterflag2Equals(long filterflag2) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	public List<BkSyslogEventsProcess> findWhereSeverity1Equals(long severity1) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'SEVERITY2 = :severity2'.
	 */
	public List<BkSyslogEventsProcess> findWhereSeverity2Equals(long severity2) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'PORT = :port'.
	 */
	public List<BkSyslogEventsProcess> findWherePortEquals(String port) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'NOTCAREFLAG = :notcareflag'.
	 */
	public List<BkSyslogEventsProcess> findWhereNotcareflagEquals(long notcareflag) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'TYPE = :type'.
	 */
	public List<BkSyslogEventsProcess> findWhereTypeEquals(long type) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'EVENTTYPE = :eventtype'.
	 */
	public List<BkSyslogEventsProcess> findWhereEventtypeEquals(long eventtype) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'SUBEVENTTYPE = :subeventtype'.
	 */
	public List<BkSyslogEventsProcess> findWhereSubeventtypeEquals(long subeventtype) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'ALERTGROUP = :alertgroup'.
	 */
	public List<BkSyslogEventsProcess> findWhereAlertgroupEquals(String alertgroup) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'ALERTKEY = :alertkey'.
	 */
	public List<BkSyslogEventsProcess> findWhereAlertkeyEquals(String alertkey) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'SUMMARYCN = :summarycn'.
	 */
	public List<BkSyslogEventsProcess> findWhereSummarycnEquals(String summarycn) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	public List<BkSyslogEventsProcess> findWhereProcesssuggestEquals(String processsuggest) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'STATUS = :status'.
	 */
	public List<BkSyslogEventsProcess> findWhereStatusEquals(String status) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'ATTENTIONFLAG = :attentionflag'.
	 */
	public List<BkSyslogEventsProcess> findWhereAttentionflagEquals(long attentionflag) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'EVENTS = :events'.
	 */
	public List<BkSyslogEventsProcess> findWhereEventsEquals(String events) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'MANUFACTURE = :manufacture'.
	 */
	public List<BkSyslogEventsProcess> findWhereManufactureEquals(String manufacture) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS table that match the criteria 'ORIGEVENT = :origevent'.
	 */
	public List<BkSyslogEventsProcess> findWhereOrigeventEquals(String origevent) throws BkSyslogEventsProcessDaoException;

	/** 
	 * Returns the rows from the BK_SYSLOG_EVENTS_PROCESS table that matches the specified primary-key value.
	 */
	public BkSyslogEventsProcess findByPrimaryKey(BkSyslogEventsProcessPk pk) throws BkSyslogEventsProcessDaoException;

	public List<BkSyslogEventsProcess> listAllVersions() throws BkSyslogEventsProcessDaoException;
	
	public void deleteAll() throws BkSyslogEventsProcessDaoException;
	
	public void deleteByBkTime(Date bkTime) throws BkSyslogEventsProcessDaoException;
	
	public void batchInsertByBkTime(Date bkTime) throws BkSyslogEventsProcessDaoException;
    
	
	/** 
	 * delete  rows from the BK_SNMP_EVENTS_PROCESS table except for latest three versions (BK_TIME=version).
	 */
	public void clean4Last3ver() throws BkSyslogEventsProcessDaoException;
}
