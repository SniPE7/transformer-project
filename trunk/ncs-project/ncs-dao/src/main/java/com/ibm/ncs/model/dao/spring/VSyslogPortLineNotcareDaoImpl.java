package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.VSyslogPortLineNotcareDao;
import com.ibm.ncs.model.dto.VSyslogPortLineNotcare;
import com.ibm.ncs.model.exceptions.VSyslogPortLineNotcareDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class VSyslogPortLineNotcareDaoImpl extends AbstractDAO implements ParameterizedRowMapper<VSyslogPortLineNotcare>, VSyslogPortLineNotcareDao
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
	public void insert(VSyslogPortLineNotcare dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( DEVIP, IFDESCR ) VALUES ( ?, ? )",dto.getDevip(),dto.getIfdescr());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return VSyslogPortLineNotcare
	 */
	public VSyslogPortLineNotcare mapRow(ResultSet rs, int row) throws SQLException
	{
		VSyslogPortLineNotcare dto = new VSyslogPortLineNotcare();
		dto.setDevip( rs.getString( 1 ) );
		dto.setIfdescr( rs.getString( 2 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "V_SYSLOG_PORT_LINE_NOTCARE";
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_LINE_NOTCARE table that match the criteria ''.
	 */
	@Transactional
	public List<VSyslogPortLineNotcare> findAll() throws VSyslogPortLineNotcareDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, IFDESCR FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new VSyslogPortLineNotcareDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_LINE_NOTCARE table that match the criteria 'DEVIP = :devip'.
	 */
	@Transactional
	public List<VSyslogPortLineNotcare> findWhereDevipEquals(String devip) throws VSyslogPortLineNotcareDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, IFDESCR FROM " + getTableName() + " WHERE DEVIP = ? ORDER BY DEVIP", this,devip);
		}
		catch (Exception e) {
			throw new VSyslogPortLineNotcareDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_LINE_NOTCARE table that match the criteria 'IFDESCR = :ifdescr'.
	 */
	@Transactional
	public List<VSyslogPortLineNotcare> findWhereIfdescrEquals(String ifdescr) throws VSyslogPortLineNotcareDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, IFDESCR FROM " + getTableName() + " WHERE IFDESCR = ? ORDER BY IFDESCR", this,ifdescr);
		}
		catch (Exception e) {
			throw new VSyslogPortLineNotcareDaoException("Query failed", e);
		}
		
	}

}
