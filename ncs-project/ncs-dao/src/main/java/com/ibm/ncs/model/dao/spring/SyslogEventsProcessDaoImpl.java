package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessPk;
import com.ibm.ncs.model.exceptions.BkSyslogEventsProcessNsDaoException;
import com.ibm.ncs.model.exceptions.SyslogEventsProcessDaoException;
import com.ibm.ncs.model.exceptions.SyslogEventsProcessNsDaoException;

import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class SyslogEventsProcessDaoImpl extends AbstractDAO implements ParameterizedRowMapper<SyslogEventsProcess>, SyslogEventsProcessDao
{
	protected SimpleJdbcTemplate jdbcTemplate;

	protected DataSource dataSource;

	/**
	 * Method 'setDataSource'
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return SyslogEventsProcessPk
	 */
	@Transactional
	public SyslogEventsProcessPk insert(SyslogEventsProcess dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getMark(),dto.getVarlist(),dto.getBtimelist(),dto.getEtimelist(),dto.isFilterflag1Null()?null:dto.getFilterflag1(),dto.isFilterflag2Null()?null:dto.getFilterflag2(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.isSeverity2Null()?null:dto.getSeverity2(),dto.getPort(),dto.isNotcareflagNull()?null:dto.getNotcareflag(),dto.isTypeNull()?null:dto.getType(),dto.isEventtypeNull()?null:dto.getEventtype(),dto.isSubeventtypeNull()?null:dto.getSubeventtype(),dto.getAlertgroup(),dto.getAlertkey(),dto.getSummarycn(),dto.getProcesssuggest(),dto.getStatus(),dto.isAttentionflagNull()?null:dto.getAttentionflag(),dto.getEvents(),dto.getManufacture(),dto.getOrigevent());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the SYSLOG_EVENTS_PROCESS table.
	 */
	@Transactional
	public void update(SyslogEventsProcessPk pk, SyslogEventsProcess dto) throws SyslogEventsProcessDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET  VARLIST = ?, BTIMELIST = ?, ETIMELIST = ?, FILTERFLAG1 = ?, FILTERFLAG2 = ?, SEVERITY1 = ?, SEVERITY2 = ?, PORT = ?, NOTCAREFLAG = ?, TYPE = ?, EVENTTYPE = ?, SUBEVENTTYPE = ?, ALERTGROUP = ?, ALERTKEY = ?, SUMMARYCN = ?, PROCESSSUGGEST = ?, STATUS = ?, ATTENTIONFLAG = ?, EVENTS = ?,  ORIGEVENT = ? WHERE MARK = ? AND MANUFACTURE = ?",dto.getVarlist(),dto.getBtimelist(),dto.getEtimelist(),dto.getFilterflag1(),dto.getFilterflag2(),dto.getSeverity1(),dto.getSeverity2(),dto.getPort(),dto.getNotcareflag(),dto.getType(),dto.getEventtype(),dto.getSubeventtype(),dto.getAlertgroup(),dto.getAlertkey(),dto.getSummarycn(),dto.getProcesssuggest(),dto.getStatus(),dto.getAttentionflag(),dto.getEvents(),dto.getOrigevent(),pk.getMark(),pk.getManufacture());
	}

	/** 
	 * Deletes a single row in the SYSLOG_EVENTS_PROCESS table.
	 */
	@Transactional
	public void delete(SyslogEventsProcessPk pk) throws SyslogEventsProcessDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE MARK = ? AND MANUFACTURE = ?",pk.getMark(),pk.getManufacture());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return SyslogEventsProcess
	 */
	public SyslogEventsProcess mapRow(ResultSet rs, int row) throws SQLException
	{
		SyslogEventsProcess dto = new SyslogEventsProcess();
		dto.setMark( rs.getString( 1 ) );
		dto.setVarlist( rs.getString( 2 ) );
		dto.setBtimelist( rs.getString( 3 ) );
		dto.setEtimelist( rs.getString( 4 ) );
		dto.setFilterflag1( rs.getLong( 5 ) );
		if (rs.wasNull()) {
			dto.setFilterflag1Null( true );
		}
		
		dto.setFilterflag2( rs.getLong( 6 ) );
		if (rs.wasNull()) {
			dto.setFilterflag2Null( true );
		}
		
		dto.setSeverity1( rs.getLong( 7 ) );
		if (rs.wasNull()) {
			dto.setSeverity1Null( true );
		}
		
		dto.setSeverity2( rs.getLong( 8 ) );
		if (rs.wasNull()) {
			dto.setSeverity2Null( true );
		}
		
		dto.setPort( rs.getString( 9 ) );
		dto.setNotcareflag( rs.getLong( 10 ) );
		if (rs.wasNull()) {
			dto.setNotcareflagNull( true );
		}
		
		dto.setType( rs.getLong( 11 ) );
		if (rs.wasNull()) {
			dto.setTypeNull( true );
		}
		
		dto.setEventtype( rs.getLong( 12 ) );
		if (rs.wasNull()) {
			dto.setEventtypeNull( true );
		}
		
		dto.setSubeventtype( rs.getLong( 13 ) );
		if (rs.wasNull()) {
			dto.setSubeventtypeNull( true );
		}
		
		dto.setAlertgroup( rs.getString( 14 ) );
		dto.setAlertkey( rs.getString( 15 ) );
		dto.setSummarycn( rs.getString( 16 ) );
		dto.setProcesssuggest( rs.getString( 17 ) );
		dto.setStatus( rs.getString( 18 ) );
		dto.setAttentionflag( rs.getLong( 19 ) );
		if (rs.wasNull()) {
			dto.setAttentionflagNull( true );
		}
		
		dto.setEvents( rs.getString( 20 ) );
		dto.setManufacture( rs.getString( 21 ) );
		dto.setOrigevent( rs.getString( 22 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "SYSLOG_EVENTS_PROCESS";
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'MARK = :mark AND MANUFACTURE = :manufacture'.
	 */
	@Transactional
	public SyslogEventsProcess findByPrimaryKey(String mark, String manufacture) throws SyslogEventsProcessDaoException
	{
		try {
			List<SyslogEventsProcess> list = jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE MARK = ? AND MANUFACTURE = ?", this,mark,manufacture);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria ''.
	 */
	@Transactional
	public List<SyslogEventsProcess> findAll() throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " ORDER BY MARK, MANUFACTURE", this);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'MARK = :mark'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereMarkEquals(String mark) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE MARK = ? ORDER BY MARK", this,mark);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'VARLIST = :varlist'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereVarlistEquals(String varlist) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE VARLIST = ? ORDER BY VARLIST", this,varlist);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'BTIMELIST = :btimelist'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereBtimelistEquals(String btimelist) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE BTIMELIST = ? ORDER BY BTIMELIST", this,btimelist);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'ETIMELIST = :etimelist'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereEtimelistEquals(String etimelist) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE ETIMELIST = ? ORDER BY ETIMELIST", this,etimelist);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'FILTERFLAG1 = :filterflag1'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereFilterflag1Equals(long filterflag1) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE FILTERFLAG1 = ? ORDER BY FILTERFLAG1", this,filterflag1);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'FILTERFLAG2 = :filterflag2'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereFilterflag2Equals(long filterflag2) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE FILTERFLAG2 = ? ORDER BY FILTERFLAG2", this,filterflag2);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereSeverity1Equals(long severity1) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE SEVERITY1 = ? ORDER BY SEVERITY1", this,severity1);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'SEVERITY2 = :severity2'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereSeverity2Equals(long severity2) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE SEVERITY2 = ? ORDER BY SEVERITY2", this,severity2);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'PORT = :port'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWherePortEquals(String port) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE PORT = ? ORDER BY PORT", this,port);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'NOTCAREFLAG = :notcareflag'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereNotcareflagEquals(long notcareflag) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE NOTCAREFLAG = ? ORDER BY NOTCAREFLAG", this,notcareflag);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'TYPE = :type'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereTypeEquals(long type) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE TYPE = ? ORDER BY TYPE", this,type);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'EVENTTYPE = :eventtype'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereEventtypeEquals(long eventtype) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE EVENTTYPE = ? ORDER BY EVENTTYPE", this,eventtype);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'SUBEVENTTYPE = :subeventtype'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereSubeventtypeEquals(long subeventtype) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE SUBEVENTTYPE = ? ORDER BY SUBEVENTTYPE", this,subeventtype);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'ALERTGROUP = :alertgroup'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereAlertgroupEquals(String alertgroup) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE ALERTGROUP = ? ORDER BY ALERTGROUP", this,alertgroup);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'ALERTKEY = :alertkey'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereAlertkeyEquals(String alertkey) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE ALERTKEY = ? ORDER BY ALERTKEY", this,alertkey);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'SUMMARYCN = :summarycn'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereSummarycnEquals(String summarycn) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE SUMMARYCN = ? ORDER BY SUMMARYCN", this,summarycn);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereProcesssuggestEquals(String processsuggest) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE PROCESSSUGGEST = ? ORDER BY PROCESSSUGGEST", this,processsuggest);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'STATUS = :status'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereStatusEquals(String status) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE STATUS = ? ORDER BY STATUS", this,status);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'ATTENTIONFLAG = :attentionflag'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereAttentionflagEquals(long attentionflag) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE ATTENTIONFLAG = ? ORDER BY ATTENTIONFLAG", this,attentionflag);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'EVENTS = :events'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereEventsEquals(String events) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE EVENTS = ? ORDER BY EVENTS", this,events);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'MANUFACTURE = :manufacture'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereManufactureEquals(String manufacture) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE MANUFACTURE = ? ORDER BY MANUFACTURE", this,manufacture);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SYSLOG_EVENTS_PROCESS table that match the criteria 'ORIGEVENT = :origevent'.
	 */
	@Transactional
	public List<SyslogEventsProcess> findWhereOrigeventEquals(String origevent) throws SyslogEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE ORIGEVENT = ? ORDER BY ORIGEVENT", this,origevent);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the SYSLOG_EVENTS_PROCESS table that matches the specified primary-key value.
	 */
	public SyslogEventsProcess findByPrimaryKey(SyslogEventsProcessPk pk) throws SyslogEventsProcessDaoException
	{
		return findByPrimaryKey( pk.getMark(), pk.getManufacture() );
	}

	/**
	 * Method 'update'
	 * 
	 * @param dto
	 */

	public void update(SyslogEventsProcess dto)
	{
		jdbcTemplate.update("Update " + getTableName() + " set   VARLIST=?, BTIMELIST=?, ETIMELIST=?, FILTERFLAG1=?, FILTERFLAG2=?, SEVERITY1=?, SEVERITY2=?, PORT=?, NOTCAREFLAG=?, TYPE=?, EVENTTYPE=?, SUBEVENTTYPE=?, ALERTGROUP=?, ALERTKEY=?, SUMMARYCN=?, PROCESSSUGGEST=?, STATUS=?, ATTENTIONFLAG=?, EVENTS=?, ORIGEVENT=?  " +
				" where mark=? and MANUFACTURE=? "
				,dto.getVarlist(),dto.getBtimelist(),dto.getEtimelist(),dto.isFilterflag1Null()?null:dto.getFilterflag1(),dto.isFilterflag2Null()?null:dto.getFilterflag2(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.isSeverity2Null()?null:dto.getSeverity2(),dto.getPort(),dto.isNotcareflagNull()?null:dto.getNotcareflag(),dto.isTypeNull()?null:dto.getType(),dto.isEventtypeNull()?null:dto.getEventtype(),dto.isSubeventtypeNull()?null:dto.getSubeventtype(),dto.getAlertgroup(),dto.getAlertkey(),dto.getSummarycn(),dto.getProcesssuggest(),dto.getStatus(),dto.isAttentionflagNull()?null:dto.getAttentionflag(),dto.getEvents(),dto.getOrigevent(),dto.getMark(),dto.getManufacture());
	}

	/**
	 * Method 'delete'
	 * 
	 * @param dto
	 */

	public void delete(SyslogEventsProcess dto)
	{
		jdbcTemplate.update("Delete from " + getTableName() + " where mark=? and  MANUFACTURE=? "	,dto.getMark(),dto.getManufacture());
	}
	
	public int resetAllAttentionFlags()
	{
		int ret = -1;
		try {
			ret = jdbcTemplate.update("Update " + getTableName() + " set ATTENTIONFLAG=0 where ATTENTIONFLAG=1 ");
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public int resetAllNotCareFlags()
	{
		int ret = -1;
		try {
			ret = jdbcTemplate.update("Update " + getTableName() + " set NOTCAREFLAG=0 where NOTCAREFLAG=1");
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * Method 'deleteAll'
	 * 
	 */
	@Transactional
	public int  deleteAll() throws SyslogEventsProcessDaoException
	{
		int ret =-1;
		ret = jdbcTemplate.update("Delete from " + getTableName() 	);
		return ret;
	}
	

	/**
	 * Batch Insert from BK_SYSLOG_EVENTS_PROCESS original data rows, for RESTORE usage.
	 */
	public void batchInsertByBkTime(Date bkTime) throws SyslogEventsProcessDaoException
	{
		try {
			 jdbcTemplate.update("INSERT INTO " + getTableName() + 
					 " (  MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT ) "+
					 " select  " +
					 "    MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT  "+
					 " FROM DUAL, BK_SYSLOG_EVENTS_PROCESS where bk_time=? ", bkTime);
		}
		catch (Exception e) {
			throw new SyslogEventsProcessDaoException("batchInsert failed", e);
		}
		
	}
	
}
