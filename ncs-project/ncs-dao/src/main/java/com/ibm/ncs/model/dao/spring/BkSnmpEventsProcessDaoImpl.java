package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.BkSnmpEventsProcessDao;
import com.ibm.ncs.model.dto.BkSnmpEventsProcess;
import com.ibm.ncs.model.dto.BkSnmpEventsProcessPk;
import com.ibm.ncs.model.exceptions.BkSnmpEventsProcessDaoException;
import com.ibm.ncs.model.exceptions.BkSyslogEventsProcessDaoException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class BkSnmpEventsProcessDaoImpl extends AbstractDAO implements ParameterizedRowMapper<BkSnmpEventsProcess>, BkSnmpEventsProcessDao
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
	 * @return BkSnmpEventsProcessPk
	 */
	@Transactional
	public BkSnmpEventsProcessPk insert(BkSnmpEventsProcess dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( BK_ID, BK_TIME, MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY ) VALUES ( ?, ?, ?, ?, ?, ?, ? )",dto.getBkId(),dto.getBkTime(),dto.getMark(),dto.getManufacture(),dto.getResultlist(),dto.getWarnmessage(),dto.getSummary());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the BK_SNMP_EVENTS_PROCESS table.
	 */
	@Transactional
	public void update(BkSnmpEventsProcessPk pk, BkSnmpEventsProcess dto) throws BkSnmpEventsProcessDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET BK_ID = ?, BK_TIME = ?, MARK = ?, MANUFACTURE = ?, RESULTLIST = ?, WARNMESSAGE = ?, SUMMARY = ? WHERE BK_ID = ?",dto.getBkId(),dto.getBkTime(),dto.getMark(),dto.getManufacture(),dto.getResultlist(),dto.getWarnmessage(),dto.getSummary(),pk.getBkId());
	}

	/** 
	 * Deletes a single row in the BK_SNMP_EVENTS_PROCESS table.
	 */
	@Transactional
	public void delete(BkSnmpEventsProcessPk pk) throws BkSnmpEventsProcessDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE BK_ID = ?",pk.getBkId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return BkSnmpEventsProcess
	 */
	public BkSnmpEventsProcess mapRow(ResultSet rs, int row) throws SQLException
	{
		BkSnmpEventsProcess dto = new BkSnmpEventsProcess();
		dto.setBkId( rs.getLong( 1 ) );
		dto.setBkTime( rs.getTimestamp(2 ) );
		dto.setMark( rs.getString( 3 ) );
		dto.setManufacture( rs.getString( 4 ) );
		dto.setResultlist( rs.getString( 5 ) );
		dto.setWarnmessage( rs.getString( 6 ) );
		dto.setSummary( rs.getString( 7 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "BK_SNMP_EVENTS_PROCESS";
	}

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'BK_ID = :bkId'.
	 */
	@Transactional
	public BkSnmpEventsProcess findByPrimaryKey(long bkId) throws BkSnmpEventsProcessDaoException
	{
		try {
			List<BkSnmpEventsProcess> list = jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE BK_ID = ?", this,bkId);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria ''.
	 */
	@Transactional
	public List<BkSnmpEventsProcess> findAll() throws BkSnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " ORDER BY BK_ID", this);
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'BK_ID = :bkId'.
	 */
	@Transactional
	public List<BkSnmpEventsProcess> findWhereBkIdEquals(long bkId) throws BkSnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE BK_ID = ? ORDER BY BK_ID", this,bkId);
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'BK_TIME = :bkTime'.
	 */
	@Transactional
	public List<BkSnmpEventsProcess> findWhereBkTimeEquals(Date bkTime) throws BkSnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE BK_TIME = ? ORDER BY BK_TIME", this,bkTime);
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'MARK = :mark'.
	 */
	@Transactional
	public List<BkSnmpEventsProcess> findWhereMarkEquals(String mark) throws BkSnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE MARK = ? ORDER BY MARK", this,mark);
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'MANUFACTURE = :manufacture'.
	 */
	@Transactional
	public List<BkSnmpEventsProcess> findWhereManufactureEquals(String manufacture) throws BkSnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE MANUFACTURE = ? ORDER BY MANUFACTURE", this,manufacture);
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'RESULTLIST = :resultlist'.
	 */
	@Transactional
	public List<BkSnmpEventsProcess> findWhereResultlistEquals(String resultlist) throws BkSnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE RESULTLIST = ? ORDER BY RESULTLIST", this,resultlist);
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'WARNMESSAGE = :warnmessage'.
	 */
	@Transactional
	public List<BkSnmpEventsProcess> findWhereWarnmessageEquals(String warnmessage) throws BkSnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE WARNMESSAGE = ? ORDER BY WARNMESSAGE", this,warnmessage);
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'SUMMARY = :summary'.
	 */
	@Transactional
	public List<BkSnmpEventsProcess> findWhereSummaryEquals(String summary) throws BkSnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE SUMMARY = ? ORDER BY SUMMARY", this,summary);
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the BK_SNMP_EVENTS_PROCESS table that matches the specified primary-key value.
	 */
	public BkSnmpEventsProcess findByPrimaryKey(BkSnmpEventsProcessPk pk) throws BkSnmpEventsProcessDaoException
	{
		return findByPrimaryKey( pk.getBkId() );
	}
	
	/** 
	 * Returns all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'BK_TIME = :bkTime'.
	 */
	@Transactional
	public List<BkSnmpEventsProcess> listByVersion(Date bkTime) throws BkSnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT BK_ID, BK_TIME, MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " 
					+ getTableName() + " WHERE BK_TIME = ? ORDER BY BK_TIME", this,bkTime);		
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}
	
	/**
	 * Returns the list of backup versions.
	 * Special columns that represent the records count and for each version...
	 * bk_id   -->  count
	 * bk_time -->  version string
	 */
	public List<BkSnmpEventsProcess> listAllVersions() throws BkSnmpEventsProcessDaoException
	{
		try {
//			String sql = "SELECT distinct 0, BK_TIME, null, null, null, null, null FROM " 
//					 + getTableName() + "  ORDER BY BK_TIME desc";
			String sql = "SELECT count(*), BK_TIME, null, null, null, null, null FROM " 
				 + getTableName() + " group by bk_time  ORDER BY BK_TIME desc";			
			return   jdbcTemplate.query(sql, this);
			
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}
	
	/** 
	 * Deletes all rows in the BK_SNMP_EVENTS_PROCESS table.
	 */
	@Transactional
	public void deleteAll() throws BkSnmpEventsProcessDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() );
	}

	/** 
	 * delete all rows from the BK_SNMP_EVENTS_PROCESS table that match the criteria 'BK_TIME = :bkTime'.
	 */
	@Transactional
	public void deleteByBkTime(Date bkTime) throws BkSnmpEventsProcessDaoException
	{
		try {
			 jdbcTemplate.update("DELETE FROM " + getTableName() +  " WHERE BK_TIME = ?", bkTime);
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}
	/**
	 * Batch Insert from SNMP_EVENTS_PROCESS original data rows. for BACKUP usage.
	 */
	public void batchInsertByBkTime(Date bkTime) throws BkSnmpEventsProcessDaoException
	{
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String xxx = sf.format(bkTime);			
			 jdbcTemplate.update("INSERT INTO " + getTableName() + 
					 " ( BK_ID, BK_TIME, MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY ) "+
					 " select TASK_SEQ.nextval,  " +
					 " to_timestamp('"+ xxx +"', 'YYYY-MM-DD HH24:MI:SS.FF3'), " +
					 "         MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY  "+
					 " FROM DUAL, SNMP_EVENTS_PROCESS ");
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("batchInsert failed", e);
		}
		
	}
	
	/** 
	 * delete  rows from the BK_SNMP_EVENTS_PROCESS table except for latest three versions (BK_TIME=version).
	 */
	@Transactional
	public void clean4Last3ver() throws BkSnmpEventsProcessDaoException
	{
		try {
			
			String wherestring = " select bk_time from (select distinct bk_time from "+ getTableName() +" order by bk_time desc) where rownum <=3 ";
			String sql = "DELETE FROM " + getTableName() +" where bk_time not in ( " +wherestring +" )";
			System.out.println(sql);
			 jdbcTemplate.update(sql);
		}
		catch (Exception e) {
			throw new BkSnmpEventsProcessDaoException("Query failed", e);
		}
		
	}
	
}
