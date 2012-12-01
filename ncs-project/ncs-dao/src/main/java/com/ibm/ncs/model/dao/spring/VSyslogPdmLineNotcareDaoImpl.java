package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.VSyslogPdmLineNotcareDao;
import com.ibm.ncs.model.dto.VSyslogPdmLineNotcare;
import com.ibm.ncs.model.exceptions.VSyslogPdmLineNotcareDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class VSyslogPdmLineNotcareDaoImpl extends AbstractDAO implements ParameterizedRowMapper<VSyslogPdmLineNotcare>, VSyslogPdmLineNotcareDao
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
	public void insert(VSyslogPdmLineNotcare dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( DEVIP, OIDNAME ) VALUES ( ?, ? )",dto.getDevip(),dto.getOidname());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return VSyslogPdmLineNotcare
	 */
	public VSyslogPdmLineNotcare mapRow(ResultSet rs, int row) throws SQLException
	{
		VSyslogPdmLineNotcare dto = new VSyslogPdmLineNotcare();
		dto.setDevip( rs.getString( 1 ) );
		dto.setOidname( rs.getString( 2 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "V_SYSLOG_PDM_LINE_NOTCARE";
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_LINE_NOTCARE table that match the criteria ''.
	 */
	@Transactional
	public List<VSyslogPdmLineNotcare> findAll() throws VSyslogPdmLineNotcareDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new VSyslogPdmLineNotcareDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_LINE_NOTCARE table that match the criteria 'DEVIP = :devip'.
	 */
	@Transactional
	public List<VSyslogPdmLineNotcare> findWhereDevipEquals(String devip) throws VSyslogPdmLineNotcareDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME FROM " + getTableName() + " WHERE DEVIP = ? ORDER BY DEVIP", this,devip);
		}
		catch (Exception e) {
			throw new VSyslogPdmLineNotcareDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_LINE_NOTCARE table that match the criteria 'OIDNAME = :oidname'.
	 */
	@Transactional
	public List<VSyslogPdmLineNotcare> findWhereOidnameEquals(String oidname) throws VSyslogPdmLineNotcareDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME FROM " + getTableName() + " WHERE OIDNAME = ? ORDER BY OIDNAME", this,oidname);
		}
		catch (Exception e) {
			throw new VSyslogPdmLineNotcareDaoException("Query failed", e);
		}
		
	}

}
