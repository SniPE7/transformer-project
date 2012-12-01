package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.BkSyslogEventsProcessNsDao;
import com.ibm.ncs.model.dto.BkSyslogEventsProcess;
import com.ibm.ncs.model.dto.BkSyslogEventsProcessNs;
import com.ibm.ncs.model.dto.BkSyslogEventsProcessNsPk;
import com.ibm.ncs.model.exceptions.BkSnmpEventsProcessDaoException;
import com.ibm.ncs.model.exceptions.BkSyslogEventsProcessDaoException;
import com.ibm.ncs.model.exceptions.BkSyslogEventsProcessNsDaoException;
import java.util.Date;
import java.util.List;

public interface BkSyslogEventsProcessNsDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return BkSyslogEventsProcessNsPk
	 */
	public BkSyslogEventsProcessNsPk insert(BkSyslogEventsProcessNs dto);

	/** 
	 * Updates a single row in the BK_SYSLOG_EVENTS_PROCESS_NS table.
	 */
	public void update(BkSyslogEventsProcessNsPk pk, BkSyslogEventsProcessNs dto) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Deletes a single row in the BK_SYSLOG_EVENTS_PROCESS_NS table.
	 */
	public void delete(BkSyslogEventsProcessNsPk pk) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'BK_ID = :bkId'.
	 */
	public BkSyslogEventsProcessNs findByPrimaryKey(long bkId) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria ''.
	 */
	public List<BkSyslogEventsProcessNs> findAll() throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'BK_ID = :bkId'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereBkIdEquals(long bkId) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'BK_TIME = :bkTime'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereBkTimeEquals(Date bkTime) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'MARK = :mark'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereMarkEquals(String mark) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'VARLIST = :varlist'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereVarlistEquals(String varlist) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'BTIMELIST = :btimelist'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereBtimelistEquals(String btimelist) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ETIMELIST = :etimelist'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereEtimelistEquals(String etimelist) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'FILTERFLAG1 = :filterflag1'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereFilterflag1Equals(long filterflag1) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'FILTERFLAG2 = :filterflag2'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereFilterflag2Equals(long filterflag2) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereSeverity1Equals(long severity1) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'SEVERITY2 = :severity2'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereSeverity2Equals(long severity2) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'PORT = :port'.
	 */
	public List<BkSyslogEventsProcessNs> findWherePortEquals(String port) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'NOTCAREFLAG = :notcareflag'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereNotcareflagEquals(long notcareflag) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'TYPE = :type'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereTypeEquals(long type) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'EVENTTYPE = :eventtype'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereEventtypeEquals(long eventtype) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'SUBEVENTTYPE = :subeventtype'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereSubeventtypeEquals(long subeventtype) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ALERTGROUP = :alertgroup'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereAlertgroupEquals(String alertgroup) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ALERTKEY = :alertkey'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereAlertkeyEquals(String alertkey) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'SUMMARYCN = :summarycn'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereSummarycnEquals(String summarycn) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereProcesssuggestEquals(String processsuggest) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'STATUS = :status'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereStatusEquals(String status) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ATTENTIONFLAG = :attentionflag'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereAttentionflagEquals(long attentionflag) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'EVENTS = :events'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereEventsEquals(String events) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'MANUFACTURE = :manufacture'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereManufactureEquals(String manufacture) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ORIGEVENT = :origevent'.
	 */
	public List<BkSyslogEventsProcessNs> findWhereOrigeventEquals(String origevent) throws BkSyslogEventsProcessNsDaoException;

	/** 
	 * Returns the rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that matches the specified primary-key value.
	 */
	public BkSyslogEventsProcessNs findByPrimaryKey(BkSyslogEventsProcessNsPk pk) throws BkSyslogEventsProcessNsDaoException;

	public List<BkSyslogEventsProcessNs> listAllVersions() throws BkSyslogEventsProcessNsDaoException;
	
	public void deleteAll() throws BkSyslogEventsProcessNsDaoException;
	
	public void deleteByBkTime(Date bkTime) throws BkSyslogEventsProcessNsDaoException;
	
	public void batchInsertByBkTime(Date bkTime) throws BkSyslogEventsProcessNsDaoException;
    
	/** 
	 * delete  rows from the BK_SNMP_EVENTS_PROCESS table except for latest three versions (BK_TIME=version).
	 */
	public void clean4Last3ver() throws BkSyslogEventsProcessNsDaoException;
}
