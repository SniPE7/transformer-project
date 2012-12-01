package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.VSyslogDevLineNotcareDao;
import com.ibm.ncs.model.dto.VSyslogDevLineNotcare;
import com.ibm.ncs.model.exceptions.VSyslogDevLineNotcareDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class VSyslogDevLineNotcareDaoImpl extends AbstractDAO implements ParameterizedRowMapper<VSyslogDevLineNotcare>, VSyslogDevLineNotcareDao
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
	public void insert(VSyslogDevLineNotcare dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( DEVIP ) VALUES ( ? )",dto.getDevip());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return VSyslogDevLineNotcare
	 */
	public VSyslogDevLineNotcare mapRow(ResultSet rs, int row) throws SQLException
	{
		VSyslogDevLineNotcare dto = new VSyslogDevLineNotcare();
		dto.setDevip( rs.getString( 1 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "V_SYSLOG_DEV_LINE_NOTCARE";
	}

	/** 
	 * Returns all rows from the V_SYSLOG_DEV_LINE_NOTCARE table that match the criteria ''.
	 */
	@Transactional
	public List<VSyslogDevLineNotcare> findAll() throws VSyslogDevLineNotcareDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new VSyslogDevLineNotcareDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_DEV_LINE_NOTCARE table that match the criteria 'DEVIP = :devip'.
	 */
	@Transactional
	public List<VSyslogDevLineNotcare> findWhereDevipEquals(String devip) throws VSyslogDevLineNotcareDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP FROM " + getTableName() + " WHERE DEVIP = ? ORDER BY DEVIP", this,devip);
		}
		catch (Exception e) {
			throw new VSyslogDevLineNotcareDaoException("Query failed", e);
		}
		
	}

}
