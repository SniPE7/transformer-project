package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.VSnmpDevThresholdsDao;
import com.ibm.ncs.model.dto.VSnmpDevThresholds;
import com.ibm.ncs.model.exceptions.VSnmpDevThresholdsDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class VSnmpDevThresholdsDaoImpl extends AbstractDAO implements ParameterizedRowMapper<VSnmpDevThresholds>, VSnmpDevThresholdsDao
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
	public void insert(VSnmpDevThresholds dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( DEVIP, MAJOR, VALUE_1, VALUE_2, COMPARETYPE, SEVERITY_1, SEVERITY_2, FILTER_A, FILTER_B ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getDevip(),dto.getMajor(),dto.getValue1(),dto.getValue2(),dto.getComparetype(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.isSeverity2Null()?null:dto.getSeverity2(),dto.getFilterA(),dto.getFilterB());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return VSnmpDevThresholds
	 */
	public VSnmpDevThresholds mapRow(ResultSet rs, int row) throws SQLException
	{
		VSnmpDevThresholds dto = new VSnmpDevThresholds();
		dto.setDevip( rs.getString( 1 ) );
		dto.setMajor( rs.getString( 2 ) );
		dto.setValue1( rs.getString( 3 ) );
		dto.setValue2( rs.getString( 4 ) );
		dto.setComparetype( rs.getString( 5 ) );
		dto.setSeverity1( rs.getLong( 6 ) );
		if (rs.wasNull()) {
			dto.setSeverity1Null( true );
		}
		
		dto.setSeverity2( rs.getLong( 7 ) );
		if (rs.wasNull()) {
			dto.setSeverity2Null( true );
		}
		
		dto.setFilterA( rs.getString( 8 ) );
		dto.setFilterB( rs.getString( 9 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "V_SNMP_DEV_THRESHOLDS";
	}

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria ''.
	 */
	@Transactional
	public List<VSnmpDevThresholds> findAll() throws VSnmpDevThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MAJOR, VALUE_1, VALUE_2, COMPARETYPE, SEVERITY_1, SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new VSnmpDevThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'DEVIP = :devip'.
	 */
	@Transactional
	public List<VSnmpDevThresholds> findWhereDevipEquals(String devip) throws VSnmpDevThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MAJOR, VALUE_1, VALUE_2, COMPARETYPE, SEVERITY_1, SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE DEVIP = ? ORDER BY DEVIP", this,devip);
		}
		catch (Exception e) {
			throw new VSnmpDevThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'MAJOR = :major'.
	 */
	@Transactional
	public List<VSnmpDevThresholds> findWhereMajorEquals(String major) throws VSnmpDevThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MAJOR, VALUE_1, VALUE_2, COMPARETYPE, SEVERITY_1, SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE MAJOR = ? ORDER BY MAJOR", this,major);
		}
		catch (Exception e) {
			throw new VSnmpDevThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'VALUE_1 = :value1'.
	 */
	@Transactional
	public List<VSnmpDevThresholds> findWhereValue1Equals(String value1) throws VSnmpDevThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MAJOR, VALUE_1, VALUE_2, COMPARETYPE, SEVERITY_1, SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE VALUE_1 = ? ORDER BY VALUE_1", this,value1);
		}
		catch (Exception e) {
			throw new VSnmpDevThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'VALUE_2 = :value2'.
	 */
	@Transactional
	public List<VSnmpDevThresholds> findWhereValue2Equals(String value2) throws VSnmpDevThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MAJOR, VALUE_1, VALUE_2, COMPARETYPE, SEVERITY_1, SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE VALUE_2 = ? ORDER BY VALUE_2", this,value2);
		}
		catch (Exception e) {
			throw new VSnmpDevThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	@Transactional
	public List<VSnmpDevThresholds> findWhereComparetypeEquals(String comparetype) throws VSnmpDevThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MAJOR, VALUE_1, VALUE_2, COMPARETYPE, SEVERITY_1, SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE COMPARETYPE = ? ORDER BY COMPARETYPE", this,comparetype);
		}
		catch (Exception e) {
			throw new VSnmpDevThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	@Transactional
	public List<VSnmpDevThresholds> findWhereSeverity1Equals(long severity1) throws VSnmpDevThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MAJOR, VALUE_1, VALUE_2, COMPARETYPE, SEVERITY_1, SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE SEVERITY_1 = ? ORDER BY SEVERITY_1", this,severity1);
		}
		catch (Exception e) {
			throw new VSnmpDevThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	@Transactional
	public List<VSnmpDevThresholds> findWhereSeverity2Equals(long severity2) throws VSnmpDevThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MAJOR, VALUE_1, VALUE_2, COMPARETYPE, SEVERITY_1, SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE SEVERITY_2 = ? ORDER BY SEVERITY_2", this,severity2);
		}
		catch (Exception e) {
			throw new VSnmpDevThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'FILTER_A = :filterA'.
	 */
	@Transactional
	public List<VSnmpDevThresholds> findWhereFilterAEquals(String filterA) throws VSnmpDevThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MAJOR, VALUE_1, VALUE_2, COMPARETYPE, SEVERITY_1, SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE FILTER_A = ? ORDER BY FILTER_A", this,filterA);
		}
		catch (Exception e) {
			throw new VSnmpDevThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'FILTER_B = :filterB'.
	 */
	@Transactional
	public List<VSnmpDevThresholds> findWhereFilterBEquals(String filterB) throws VSnmpDevThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, MAJOR, VALUE_1, VALUE_2, COMPARETYPE, SEVERITY_1, SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE FILTER_B = ? ORDER BY FILTER_B", this,filterB);
		}
		catch (Exception e) {
			throw new VSnmpDevThresholdsDaoException("Query failed", e);
		}
		
	}

}
