package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.SnmpEventsProcessDao;
import com.ibm.ncs.model.dto.SnmpEventsProcess;
import com.ibm.ncs.model.dto.SnmpEventsProcessPk;
import com.ibm.ncs.model.exceptions.BkSnmpEventsProcessDaoException;
import com.ibm.ncs.model.exceptions.SnmpEventsProcessDaoException;

import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class SnmpEventsProcessDaoImpl extends AbstractDAO implements ParameterizedRowMapper<SnmpEventsProcess>, SnmpEventsProcessDao
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
	 * @return SnmpEventsProcessPk
	 */
	@Transactional
	public SnmpEventsProcessPk insert(SnmpEventsProcess dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY ) VALUES ( ?, ?, ?, ?, ? )",dto.getMark(),dto.getManufacture(),dto.getResultlist(),dto.getWarnmessage(),dto.getSummary());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the SNMP_EVENTS_PROCESS table.
	 */
	@Transactional
	public void update(SnmpEventsProcessPk pk, SnmpEventsProcess dto) throws SnmpEventsProcessDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET MARK = ?, MANUFACTURE = ?, RESULTLIST = ?, WARNMESSAGE = ?, SUMMARY = ? WHERE MARK = ?",dto.getMark(),dto.getManufacture(),dto.getResultlist(),dto.getWarnmessage(),dto.getSummary(),pk.getMark());
	}

	/** 
	 * Deletes a single row in the SNMP_EVENTS_PROCESS table.
	 */
	@Transactional
	public void delete(SnmpEventsProcessPk pk) throws SnmpEventsProcessDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE MARK = ?",pk.getMark());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return SnmpEventsProcess
	 */
	public SnmpEventsProcess mapRow(ResultSet rs, int row) throws SQLException
	{
		SnmpEventsProcess dto = new SnmpEventsProcess();
		dto.setMark( rs.getString( 1 ) );
		dto.setManufacture( rs.getString( 2 ) );
		dto.setResultlist( rs.getString( 3 ) );
		dto.setWarnmessage( rs.getString( 4 ) );
		dto.setSummary( rs.getString( 5 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "SNMP_EVENTS_PROCESS";
	}

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria 'MARK = :mark'.
	 */
	@Transactional
	public SnmpEventsProcess findByPrimaryKey(String mark) throws SnmpEventsProcessDaoException
	{
		try {
			List<SnmpEventsProcess> list = jdbcTemplate.query("SELECT MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE MARK = ?", this,mark);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new SnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria ''.
	 */
	@Transactional
	public List<SnmpEventsProcess> findAll() throws SnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " ORDER BY MARK", this);
		}
		catch (Exception e) {
			throw new SnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria 'MARK = :mark'.
	 */
	@Transactional
	public List<SnmpEventsProcess> findWhereMarkEquals(String mark) throws SnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE MARK = ? ORDER BY MARK", this,mark);
		}
		catch (Exception e) {
			throw new SnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria 'MANUFACTURE = :manufacture'.
	 */
	@Transactional
	public List<SnmpEventsProcess> findWhereManufactureEquals(String manufacture) throws SnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE MANUFACTURE = ? ORDER BY MANUFACTURE", this,manufacture);
		}
		catch (Exception e) {
			throw new SnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria 'RESULTLIST = :resultlist'.
	 */
	@Transactional
	public List<SnmpEventsProcess> findWhereResultlistEquals(String resultlist) throws SnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE RESULTLIST = ? ORDER BY RESULTLIST", this,resultlist);
		}
		catch (Exception e) {
			throw new SnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria 'WARNMESSAGE = :warnmessage'.
	 */
	@Transactional
	public List<SnmpEventsProcess> findWhereWarnmessageEquals(String warnmessage) throws SnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE WARNMESSAGE = ? ORDER BY WARNMESSAGE", this,warnmessage);
		}
		catch (Exception e) {
			throw new SnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_EVENTS_PROCESS table that match the criteria 'SUMMARY = :summary'.
	 */
	@Transactional
	public List<SnmpEventsProcess> findWhereSummaryEquals(String summary) throws SnmpEventsProcessDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY FROM " + getTableName() + " WHERE SUMMARY = ? ORDER BY SUMMARY", this,summary);
		}
		catch (Exception e) {
			throw new SnmpEventsProcessDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the SNMP_EVENTS_PROCESS table that matches the specified primary-key value.
	 */
	public SnmpEventsProcess findByPrimaryKey(SnmpEventsProcessPk pk) throws SnmpEventsProcessDaoException
	{
		return findByPrimaryKey( pk.getMark() );
	}
	
	/**
	 * Method 'update'
	 * 
	 * @param dto
	 */
	@Transactional
	public void update(SnmpEventsProcess dto) 
	{
		jdbcTemplate.update("Update " + getTableName() + " set   MANUFACTURE=?, RESULTLIST=?, SUMMARY=?, WARNMESSAGE=? " +
				" where mark=? "
				,dto.getManufacture(),dto.getResultlist(),dto.getSummary(),dto.getWarnmessage(),dto.getMark());
	}

	/**
	 * Method 'delete'
	 * 
	 * @param dto
	 */
	@Transactional
	public void delete(SnmpEventsProcess dto) 
	{
		jdbcTemplate.update("Delete from " + getTableName() + " where mark=?  "	,dto.getMark());
	}

	/**
	 * Method 'deleteAll'
	 * 
	 */
	@Transactional
	public void deleteAll() throws SnmpEventsProcessDaoException
	{
		jdbcTemplate.update("Delete from " + getTableName() );
	}
	
	/**
	 * Batch Insert from BK_SNMP_EVENTS_PROCESS original data rows. for RESTORE usage.
	 */
	public void batchInsertByBkTime(Date bkTime) throws SnmpEventsProcessDaoException
	{
		try {
			 jdbcTemplate.update("INSERT INTO " + getTableName() + 
					 " ( MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY ) "+
					 " select MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, SUMMARY  "+
					 " FROM DUAL, BK_SNMP_EVENTS_PROCESS where bk_time= ? ", bkTime);
		}
		catch (Exception e) {
			throw new SnmpEventsProcessDaoException("batchInsert failed", e);
		}
		
	}

}
