package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.VSyslogPdmEventsAttentionDao;
import com.ibm.ncs.model.dto.VSyslogPdmEventsAttention;
import com.ibm.ncs.model.exceptions.VSyslogPdmEventsAttentionDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class VSyslogPdmEventsAttentionDaoImpl extends AbstractDAO implements ParameterizedRowMapper<VSyslogPdmEventsAttention>, VSyslogPdmEventsAttentionDao
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
	 */
	@Transactional
	public void insert(VSyslogPdmEventsAttention dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( DEVIP, MARK, SEVERITY1, PROCESSSUGGEST ) VALUES ( ?, ?, ?, ? )",dto.getDevip(),dto.getMark(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.getProcesssuggest());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return VSyslogPdmEventsAttention
	 */
	public VSyslogPdmEventsAttention mapRow(ResultSet rs, int row) throws SQLException
	{
		VSyslogPdmEventsAttention dto = new VSyslogPdmEventsAttention();
		dto.setDevip( rs.getString( 1 ) );
		dto.setMark( rs.getString( 2 ) );
		dto.setSeverity1( rs.getLong( 3 ) );
		if (rs.wasNull()) {
			dto.setSeverity1Null( true );
		}
		
		dto.setProcesssuggest( rs.getString( 4 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "V_SYSLOG_PDM_EVENTS_ATTENTION";
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_EVENTS_ATTENTION table that match the criteria ''.
	 */
	@Transactional
	public List<VSyslogPdmEventsAttention> findAll() throws VSyslogPdmEventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MARK, SEVERITY1, PROCESSSUGGEST FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new VSyslogPdmEventsAttentionDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_EVENTS_ATTENTION table that match the criteria 'DEVIP = :devip'.
	 */
	@Transactional
	public List<VSyslogPdmEventsAttention> findWhereDevipEquals(String devip) throws VSyslogPdmEventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MARK, SEVERITY1, PROCESSSUGGEST FROM " + getTableName() + " WHERE DEVIP = ? ORDER BY DEVIP", this,devip);
		}
		catch (Exception e) {
			throw new VSyslogPdmEventsAttentionDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_EVENTS_ATTENTION table that match the criteria 'MARK = :mark'.
	 */
	@Transactional
	public List<VSyslogPdmEventsAttention> findWhereMarkEquals(String mark) throws VSyslogPdmEventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MARK, SEVERITY1, PROCESSSUGGEST FROM " + getTableName() + " WHERE MARK = ? ORDER BY MARK", this,mark);
		}
		catch (Exception e) {
			throw new VSyslogPdmEventsAttentionDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_EVENTS_ATTENTION table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	@Transactional
	public List<VSyslogPdmEventsAttention> findWhereSeverity1Equals(long severity1) throws VSyslogPdmEventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MARK, SEVERITY1, PROCESSSUGGEST FROM " + getTableName() + " WHERE SEVERITY1 = ? ORDER BY SEVERITY1", this,severity1);
		}
		catch (Exception e) {
			throw new VSyslogPdmEventsAttentionDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_EVENTS_ATTENTION table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	@Transactional
	public List<VSyslogPdmEventsAttention> findWhereProcesssuggestEquals(String processsuggest) throws VSyslogPdmEventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MARK, SEVERITY1, PROCESSSUGGEST FROM " + getTableName() + " WHERE PROCESSSUGGEST = ? ORDER BY PROCESSSUGGEST", this,processsuggest);
		}
		catch (Exception e) {
			throw new VSyslogPdmEventsAttentionDaoException("Query failed", e);
		}
		
	}

}
