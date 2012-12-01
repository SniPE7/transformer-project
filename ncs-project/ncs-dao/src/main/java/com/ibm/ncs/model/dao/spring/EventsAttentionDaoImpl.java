package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.EventsAttentionDao;
import com.ibm.ncs.model.dto.EventsAttention;
import com.ibm.ncs.model.dto.EventsAttentionPk;
import com.ibm.ncs.model.exceptions.EventsAttentionDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class EventsAttentionDaoImpl extends AbstractDAO implements ParameterizedRowMapper<EventsAttention>, EventsAttentionDao
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
	 * @return EventsAttentionPk
	 */
	@Transactional
	public EventsAttentionPk insert(EventsAttention dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( EVENTSATTENTION, SEVERITY, PROCESSSUGGEST ) VALUES ( ?, ?, ? )",dto.getEventsattention(),dto.isSeverityNull()?null:dto.getSeverity(),dto.getProcesssuggest());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the EVENTS_ATTENTION table.
	 */
	@Transactional
	public void update(EventsAttentionPk pk, EventsAttention dto) throws EventsAttentionDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET EVENTSATTENTION = ?, SEVERITY = ?, PROCESSSUGGEST = ? WHERE EVENTSATTENTION = ?",dto.getEventsattention(),dto.getSeverity(),dto.getProcesssuggest(),pk.getEventsattention());
	}

	/** 
	 * Deletes a single row in the EVENTS_ATTENTION table.
	 */
	@Transactional
	public void delete(EventsAttentionPk pk) throws EventsAttentionDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE EVENTSATTENTION = ?",pk.getEventsattention());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return EventsAttention
	 */
	public EventsAttention mapRow(ResultSet rs, int row) throws SQLException
	{
		EventsAttention dto = new EventsAttention();
		dto.setEventsattention( rs.getString( 1 ) );
		dto.setSeverity( rs.getLong( 2 ) );
		if (rs.wasNull()) {
			dto.setSeverityNull( true );
		}
		
		dto.setProcesssuggest( rs.getString( 3 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "EVENTS_ATTENTION";
	}

	/** 
	 * Returns all rows from the EVENTS_ATTENTION table that match the criteria 'EVENTSATTENTION = :eventsattention'.
	 */
	@Transactional
	public EventsAttention findByPrimaryKey(String eventsattention) throws EventsAttentionDaoException
	{
		try {
			List<EventsAttention> list = jdbcTemplate.query("SELECT EVENTSATTENTION, SEVERITY, PROCESSSUGGEST FROM " + getTableName() + " WHERE EVENTSATTENTION = ?", this,eventsattention);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new EventsAttentionDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the EVENTS_ATTENTION table that match the criteria ''.
	 */
	@Transactional
	public List<EventsAttention> findAll() throws EventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTSATTENTION, SEVERITY, PROCESSSUGGEST FROM " + getTableName() + " ORDER BY EVENTSATTENTION", this);
		}
		catch (Exception e) {
			throw new EventsAttentionDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the EVENTS_ATTENTION table that match the criteria 'EVENTSATTENTION = :eventsattention'.
	 */
	@Transactional
	public List<EventsAttention> findWhereEventsattentionEquals(String eventsattention) throws EventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTSATTENTION, SEVERITY, PROCESSSUGGEST FROM " + getTableName() + " WHERE EVENTSATTENTION = ? ORDER BY EVENTSATTENTION", this,eventsattention);
		}
		catch (Exception e) {
			throw new EventsAttentionDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the EVENTS_ATTENTION table that match the criteria 'SEVERITY = :severity'.
	 */
	@Transactional
	public List<EventsAttention> findWhereSeverityEquals(long severity) throws EventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTSATTENTION, SEVERITY, PROCESSSUGGEST FROM " + getTableName() + " WHERE SEVERITY = ? ORDER BY SEVERITY", this,severity);
		}
		catch (Exception e) {
			throw new EventsAttentionDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the EVENTS_ATTENTION table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	@Transactional
	public List<EventsAttention> findWhereProcesssuggestEquals(String processsuggest) throws EventsAttentionDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTSATTENTION, SEVERITY, PROCESSSUGGEST FROM " + getTableName() + " WHERE PROCESSSUGGEST = ? ORDER BY PROCESSSUGGEST", this,processsuggest);
		}
		catch (Exception e) {
			throw new EventsAttentionDaoException("Query failed", e);
		}
		
	}

	public void delete(EventsAttention dto) {
		jdbcTemplate.update("Delete from " + getTableName() + " where eventsAttention=?  ",dto.getEventsattention());
		
	}

	public void update(EventsAttention dto) {
		jdbcTemplate.update("update "+getTableName()+" set Severity=?,ProcessSuggest=? where eventsAttention=?", dto.getSeverity(),dto.getProcesssuggest(),dto.getEventsattention());
		
	}

	/** 
	 * Returns the rows from the EVENTS_ATTENTION table that matches the specified primary-key value.
	 */
	public EventsAttention findByPrimaryKey(EventsAttentionPk pk) throws EventsAttentionDaoException
	{
		return findByPrimaryKey( pk.getEventsattention() );
	}

	public int deleteAll() {
		int ret = -1; //success flag;
		ret = jdbcTemplate.update("Delete from " + getTableName() );
		return ret;
		
	}
	
	/**
	 * Method 'insertEffect'
	 * 
	 * @param dto
	 * @return EventsAttentionPk
	 */
	@Transactional
	public int insertEffect()
	{
		int ret = -1; //success flag;
		String sqldev = "select distinct devip||'|'||events  eventsattention, severity1 severity, processsuggest processsuggest from v_syslog_dev_events_attention";
		String sqlport = "select distinct devip||'|'||ifdescr||'|'||events  eventsattention, severity1 severity, processsuggest processsuggest from v_syslog_port_events_attention";
/*
		String sqlpdm = "select devip||'|'||oidname||'|'||mark  eventsattention, severity1 severity, processsuggest processsuggest from v_syslog_pdm_events_attention";
		*/
		try {
			ret = jdbcTemplate.update("INSERT INTO " + getTableName() + " ( "+ sqldev +" )");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		try {
			ret += jdbcTemplate.update("INSERT INTO " + getTableName() + " ( "+ sqlport +" )");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
/*		try {
			ret += jdbcTemplate.update("INSERT INTO " + getTableName() + " ( "+ sqlpdm +" )");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}*/
		return ret;
	}
	
}
