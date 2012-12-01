package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.BkSyslogEventsProcessNsDao;
import com.ibm.ncs.model.dto.BkSyslogEventsProcessNs;
import com.ibm.ncs.model.dto.BkSyslogEventsProcessNsPk;
import com.ibm.ncs.model.exceptions.BkSnmpEventsProcessDaoException;
import com.ibm.ncs.model.exceptions.BkSyslogEventsProcessDaoException;
import com.ibm.ncs.model.exceptions.BkSyslogEventsProcessNsDaoException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class BkSyslogEventsProcessNsDaoImpl extends AbstractDAO implements ParameterizedRowMapper<BkSyslogEventsProcessNs>, BkSyslogEventsProcessNsDao
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
	 * @return BkSyslogEventsProcessNsPk
	 */
	@Transactional
	public BkSyslogEventsProcessNsPk insert(BkSyslogEventsProcessNs dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getBkId(),dto.getBkTime(),dto.getMark(),dto.getVarlist(),dto.getBtimelist(),dto.getEtimelist(),dto.isFilterflag1Null()?null:dto.getFilterflag1(),dto.isFilterflag2Null()?null:dto.getFilterflag2(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.isSeverity2Null()?null:dto.getSeverity2(),dto.getPort(),dto.isNotcareflagNull()?null:dto.getNotcareflag(),dto.isTypeNull()?null:dto.getType(),dto.isEventtypeNull()?null:dto.getEventtype(),dto.isSubeventtypeNull()?null:dto.getSubeventtype(),dto.getAlertgroup(),dto.getAlertkey(),dto.getSummarycn(),dto.getProcesssuggest(),dto.getStatus(),dto.isAttentionflagNull()?null:dto.getAttentionflag(),dto.getEvents(),dto.getManufacture(),dto.getOrigevent());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the BK_SYSLOG_EVENTS_PROCESS_NS table.
	 */
	@Transactional
	public void update(BkSyslogEventsProcessNsPk pk, BkSyslogEventsProcessNs dto) throws BkSyslogEventsProcessNsDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET BK_ID = ?, BK_TIME = ?, MARK = ?, VARLIST = ?, BTIMELIST = ?, ETIMELIST = ?, FILTERFLAG1 = ?, FILTERFLAG2 = ?, SEVERITY1 = ?, SEVERITY2 = ?, PORT = ?, NOTCAREFLAG = ?, TYPE = ?, EVENTTYPE = ?, SUBEVENTTYPE = ?, ALERTGROUP = ?, ALERTKEY = ?, SUMMARYCN = ?, PROCESSSUGGEST = ?, STATUS = ?, ATTENTIONFLAG = ?, EVENTS = ?, MANUFACTURE = ?, ORIGEVENT = ? WHERE BK_ID = ?",dto.getBkId(),dto.getBkTime(),dto.getMark(),dto.getVarlist(),dto.getBtimelist(),dto.getEtimelist(),dto.getFilterflag1(),dto.getFilterflag2(),dto.getSeverity1(),dto.getSeverity2(),dto.getPort(),dto.getNotcareflag(),dto.getType(),dto.getEventtype(),dto.getSubeventtype(),dto.getAlertgroup(),dto.getAlertkey(),dto.getSummarycn(),dto.getProcesssuggest(),dto.getStatus(),dto.getAttentionflag(),dto.getEvents(),dto.getManufacture(),dto.getOrigevent(),pk.getBkId());
	}

	/** 
	 * Deletes a single row in the BK_SYSLOG_EVENTS_PROCESS_NS table.
	 */
	@Transactional
	public void delete(BkSyslogEventsProcessNsPk pk) throws BkSyslogEventsProcessNsDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE BK_ID = ?",pk.getBkId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return BkSyslogEventsProcessNs
	 */
	public BkSyslogEventsProcessNs mapRow(ResultSet rs, int row) throws SQLException
	{
		BkSyslogEventsProcessNs dto = new BkSyslogEventsProcessNs();
		dto.setBkId( rs.getLong( 1 ) );
		dto.setBkTime( rs.getTimestamp(2 ) );
		dto.setMark( rs.getString( 3 ) );
		dto.setVarlist( rs.getString( 4 ) );
		dto.setBtimelist( rs.getString( 5 ) );
		dto.setEtimelist( rs.getString( 6 ) );
		dto.setFilterflag1( rs.getLong( 7 ) );
		if (rs.wasNull()) {
			dto.setFilterflag1Null( true );
		}
		
		dto.setFilterflag2( rs.getLong( 8 ) );
		if (rs.wasNull()) {
			dto.setFilterflag2Null( true );
		}
		
		dto.setSeverity1( rs.getLong( 9 ) );
		if (rs.wasNull()) {
			dto.setSeverity1Null( true );
		}
		
		dto.setSeverity2( rs.getLong( 10 ) );
		if (rs.wasNull()) {
			dto.setSeverity2Null( true );
		}
		
		dto.setPort( rs.getString( 11 ) );
		dto.setNotcareflag( rs.getLong( 12 ) );
		if (rs.wasNull()) {
			dto.setNotcareflagNull( true );
		}
		
		dto.setType( rs.getLong( 13 ) );
		if (rs.wasNull()) {
			dto.setTypeNull( true );
		}
		
		dto.setEventtype( rs.getLong( 14 ) );
		if (rs.wasNull()) {
			dto.setEventtypeNull( true );
		}
		
		dto.setSubeventtype( rs.getLong( 15 ) );
		if (rs.wasNull()) {
			dto.setSubeventtypeNull( true );
		}
		
		dto.setAlertgroup( rs.getString( 16 ) );
		dto.setAlertkey( rs.getString( 17 ) );
		dto.setSummarycn( rs.getString( 18 ) );
		dto.setProcesssuggest( rs.getString( 19 ) );
		dto.setStatus( rs.getString( 20 ) );
		dto.setAttentionflag( rs.getLong( 21 ) );
		if (rs.wasNull()) {
			dto.setAttentionflagNull( true );
		}
		
		dto.setEvents( rs.getString( 22 ) );
		dto.setManufacture( rs.getString( 23 ) );
		dto.setOrigevent( rs.getString( 24 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "BK_SYSLOG_EVENTS_PROCESS_NS";
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'BK_ID = :bkId'.
	 */
	@Transactional
	public BkSyslogEventsProcessNs findByPrimaryKey(long bkId) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			List<BkSyslogEventsProcessNs> list = jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE BK_ID = ?", this,bkId);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria ''.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findAll() throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " ORDER BY BK_ID", this);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'BK_ID = :bkId'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereBkIdEquals(long bkId) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE BK_ID = ? ORDER BY BK_ID", this,bkId);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'BK_TIME = :bkTime'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereBkTimeEquals(Date bkTime) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE BK_TIME = ? ORDER BY BK_TIME", this,bkTime);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'MARK = :mark'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereMarkEquals(String mark) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE MARK = ? ORDER BY MARK", this,mark);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'VARLIST = :varlist'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereVarlistEquals(String varlist) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE VARLIST = ? ORDER BY VARLIST", this,varlist);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'BTIMELIST = :btimelist'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereBtimelistEquals(String btimelist) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE BTIMELIST = ? ORDER BY BTIMELIST", this,btimelist);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ETIMELIST = :etimelist'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereEtimelistEquals(String etimelist) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE ETIMELIST = ? ORDER BY ETIMELIST", this,etimelist);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'FILTERFLAG1 = :filterflag1'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereFilterflag1Equals(long filterflag1) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE FILTERFLAG1 = ? ORDER BY FILTERFLAG1", this,filterflag1);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'FILTERFLAG2 = :filterflag2'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereFilterflag2Equals(long filterflag2) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE FILTERFLAG2 = ? ORDER BY FILTERFLAG2", this,filterflag2);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereSeverity1Equals(long severity1) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE SEVERITY1 = ? ORDER BY SEVERITY1", this,severity1);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'SEVERITY2 = :severity2'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereSeverity2Equals(long severity2) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE SEVERITY2 = ? ORDER BY SEVERITY2", this,severity2);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'PORT = :port'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWherePortEquals(String port) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE PORT = ? ORDER BY PORT", this,port);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'NOTCAREFLAG = :notcareflag'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereNotcareflagEquals(long notcareflag) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE NOTCAREFLAG = ? ORDER BY NOTCAREFLAG", this,notcareflag);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'TYPE = :type'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereTypeEquals(long type) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE TYPE = ? ORDER BY TYPE", this,type);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'EVENTTYPE = :eventtype'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereEventtypeEquals(long eventtype) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE EVENTTYPE = ? ORDER BY EVENTTYPE", this,eventtype);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'SUBEVENTTYPE = :subeventtype'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereSubeventtypeEquals(long subeventtype) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE SUBEVENTTYPE = ? ORDER BY SUBEVENTTYPE", this,subeventtype);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ALERTGROUP = :alertgroup'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereAlertgroupEquals(String alertgroup) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE ALERTGROUP = ? ORDER BY ALERTGROUP", this,alertgroup);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ALERTKEY = :alertkey'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereAlertkeyEquals(String alertkey) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE ALERTKEY = ? ORDER BY ALERTKEY", this,alertkey);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'SUMMARYCN = :summarycn'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereSummarycnEquals(String summarycn) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE SUMMARYCN = ? ORDER BY SUMMARYCN", this,summarycn);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereProcesssuggestEquals(String processsuggest) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE PROCESSSUGGEST = ? ORDER BY PROCESSSUGGEST", this,processsuggest);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'STATUS = :status'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereStatusEquals(String status) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE STATUS = ? ORDER BY STATUS", this,status);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ATTENTIONFLAG = :attentionflag'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereAttentionflagEquals(long attentionflag) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE ATTENTIONFLAG = ? ORDER BY ATTENTIONFLAG", this,attentionflag);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'EVENTS = :events'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereEventsEquals(String events) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE EVENTS = ? ORDER BY EVENTS", this,events);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'MANUFACTURE = :manufacture'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereManufactureEquals(String manufacture) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE MANUFACTURE = ? ORDER BY MANUFACTURE", this,manufacture);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that match the criteria 'ORIGEVENT = :origevent'.
	 */
	@Transactional
	public List<BkSyslogEventsProcessNs> findWhereOrigeventEquals(String origevent) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT FROM " + getTableName() + " WHERE ORIGEVENT = ? ORDER BY ORIGEVENT", this,origevent);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the BK_SYSLOG_EVENTS_PROCESS_NS table that matches the specified primary-key value.
	 */
	public BkSyslogEventsProcessNs findByPrimaryKey(BkSyslogEventsProcessNsPk pk) throws BkSyslogEventsProcessNsDaoException
	{
		return findByPrimaryKey( pk.getBkId() );
	}

	/**
	 * Returns the list of backup versions.
	 * Special columns that represent the records count and for each version...
	 * bk_id   -->  count
	 * bk_time -->  version string
	 */
	public List<BkSyslogEventsProcessNs> listAllVersions()
			throws BkSyslogEventsProcessNsDaoException {
		try {
//			String sql = "SELECT distinct 0, BK_TIME, null, null, null, null, null,null, null, null, null, null,null, null, null, null, null," +
//					"null, null, null, null, null,null, null FROM " 
//					 + getTableName() + "  ORDER BY BK_TIME desc";
			String sql = "SELECT count(*), BK_TIME, null, null, null, null, null,null, null, null, null, null,null, null, null, null, null," +
			"null, null, null, null, null,null, null FROM " 
			 + getTableName() + " group by bk_time  ORDER BY BK_TIME desc";
			return   jdbcTemplate.query(sql, this);
			
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}
	/** 
	 * Deletes all rows in the BK_SNMP_EVENTS_PROCESS table.
	 */
	@Transactional
	public void deleteAll() throws BkSyslogEventsProcessNsDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() );
	}

	/** 
	 * delete all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'BK_TIME = :bkTime'.
	 */
	@Transactional
	public void deleteByBkTime(Date bkTime) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String xxx = sf.format(bkTime);
			// jdbcTemplate.update("DELETE FROM " + getTableName() +  " WHERE BK_TIME = ?", this, bkTime);
			 jdbcTemplate.update("DELETE FROM " + getTableName() +  " WHERE BK_TIME = "+"to_timestamp('"+ xxx +"', 'YYYY-MM-DD HH24:MI:SS.FF3')");
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}
	
	/**
	 * Batch Insert from SYSLOG_EVENTS_PROCESS_NS original data rows, for BACKUP usage.
	 */
	public void batchInsertByBkTime(Date bkTime) throws BkSyslogEventsProcessNsDaoException
	{
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String xxx = sf.format(bkTime);
			String sql ="INSERT INTO " + getTableName() + 
			 " ( BK_ID, BK_TIME, MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT ) "+
			 " select TASK_SEQ.nextval,  " +
			 " to_timestamp('"+ xxx +"', 'YYYY-MM-DD HH24:MI:SS.FF3'), " +
			 "        MARK, VARLIST, BTIMELIST, ETIMELIST, FILTERFLAG1, FILTERFLAG2, SEVERITY1, SEVERITY2, PORT, NOTCAREFLAG, TYPE, EVENTTYPE, SUBEVENTTYPE, ALERTGROUP, ALERTKEY, SUMMARYCN, PROCESSSUGGEST, STATUS, ATTENTIONFLAG, EVENTS, MANUFACTURE, ORIGEVENT  "+
			 " FROM DUAL, SYSLOG_EVENTS_PROCESS_NS ";
			System.out.println(sql);
			 jdbcTemplate.update(sql);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("batchInsert failed", e);
		}
		
	}

	/** 
	 * delete  rows from the BK_SNMP_EVENTS_PROCESS table except for latest three versions (BK_TIME=version).
	 */
	@Transactional
	public void clean4Last3ver() throws BkSyslogEventsProcessNsDaoException
	{
		try {
			
			String wherestring = " select bk_time from (select distinct bk_time from "+ getTableName() +" order by bk_time desc) where rownum <=3 ";
			String sql = "DELETE FROM " + getTableName() +" where bk_time not in ( " +wherestring +" )";
			System.out.println(sql);
			 jdbcTemplate.update(sql);
		}
		catch (Exception e) {
			throw new BkSyslogEventsProcessNsDaoException("Query failed", e);
		}
		
	}
}
