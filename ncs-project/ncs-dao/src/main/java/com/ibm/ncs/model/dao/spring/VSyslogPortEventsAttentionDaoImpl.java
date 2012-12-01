package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.VSyslogPortEventsAttentionDao;
import com.ibm.ncs.model.dto.VSyslogPortEventsAttention;
import com.ibm.ncs.model.exceptions.VSyslogPortEventsAttentionDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class VSyslogPortEventsAttentionDaoImpl extends AbstractDAO implements ParameterizedRowMapper<VSyslogPortEventsAttention>, VSyslogPortEventsAttentionDao
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
	public void insert(VSyslogPortEventsAttention dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( DEVIP, MARK, SEVERITY1, PROCESSSUGGEST ) VALUES ( ?, ?, ?, ? )",dto.getDevip(),dto.getMark(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.getProcesssuggest());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return VSyslogPortEventsAttention
	 */
	public VSyslogPortEventsAttention mapRow(ResultSet rs, int row) throws SQLException
	{
		VSyslogPortEventsAttention dto = new VSyslogPortEventsAttention();
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
		return "V_SYSLOG_PORT_EVENTS_ATTENTION";
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_EVENTS_ATTENTION table that match the criteria ''.
	 */
	@Transactional
	public List<VSyslogPortEventsAttention> findAll() throws VSyslogPortEventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MARK, SEVERITY1, PROCESSSUGGEST FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new VSyslogPortEventsAttentionDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_EVENTS_ATTENTION table that match the criteria 'DEVIP = :devip'.
	 */
	@Transactional
	public List<VSyslogPortEventsAttention> findWhereDevipEquals(String devip) throws VSyslogPortEventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MARK, SEVERITY1, PROCESSSUGGEST FROM " + getTableName() + " WHERE DEVIP = ? ORDER BY DEVIP", this,devip);
		}
		catch (Exception e) {
			throw new VSyslogPortEventsAttentionDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_EVENTS_ATTENTION table that match the criteria 'MARK = :mark'.
	 */
	@Transactional
	public List<VSyslogPortEventsAttention> findWhereMarkEquals(String mark) throws VSyslogPortEventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MARK, SEVERITY1, PROCESSSUGGEST FROM " + getTableName() + " WHERE MARK = ? ORDER BY MARK", this,mark);
		}
		catch (Exception e) {
			throw new VSyslogPortEventsAttentionDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_EVENTS_ATTENTION table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	@Transactional
	public List<VSyslogPortEventsAttention> findWhereSeverity1Equals(long severity1) throws VSyslogPortEventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MARK, SEVERITY1, PROCESSSUGGEST FROM " + getTableName() + " WHERE SEVERITY1 = ? ORDER BY SEVERITY1", this,severity1);
		}
		catch (Exception e) {
			throw new VSyslogPortEventsAttentionDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_EVENTS_ATTENTION table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	@Transactional
	public List<VSyslogPortEventsAttention> findWhereProcesssuggestEquals(String processsuggest) throws VSyslogPortEventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MARK, SEVERITY1, PROCESSSUGGEST FROM " + getTableName() + " WHERE PROCESSSUGGEST = ? ORDER BY PROCESSSUGGEST", this,processsuggest);
		}
		catch (Exception e) {
			throw new VSyslogPortEventsAttentionDaoException("Query failed", e);
		}
		
	}

}
