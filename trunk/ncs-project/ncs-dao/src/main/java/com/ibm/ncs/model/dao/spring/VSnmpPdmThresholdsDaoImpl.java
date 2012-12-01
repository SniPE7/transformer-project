package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.VSnmpPdmThresholdsDao;
import com.ibm.ncs.model.dto.VSnmpPdmThresholds;
import com.ibm.ncs.model.exceptions.VSnmpPdmThresholdsDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class VSnmpPdmThresholdsDaoImpl extends AbstractDAO implements ParameterizedRowMapper<VSnmpPdmThresholds>, VSnmpPdmThresholdsDao
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
	public void insert(VSnmpPdmThresholds dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getDevip(),dto.getOidname(),dto.getMajor(),dto.getValue1(),dto.getValue1Low(),dto.getValue2(),dto.getValue2Low(),dto.getComparetype(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.isV1lSeverity1Null()?null:dto.getV1lSeverity1(),dto.isSeverity2Null()?null:dto.getSeverity2(),dto.isV2lSeverity2Null()?null:dto.getV2lSeverity2(),dto.getFilterA(),dto.getFilterB());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return VSnmpPdmThresholds
	 */
	public VSnmpPdmThresholds mapRow(ResultSet rs, int row) throws SQLException
	{
		VSnmpPdmThresholds dto = new VSnmpPdmThresholds();
		dto.setDevip( rs.getString( 1 ) );
		dto.setOidname( rs.getString( 2 ) );
		dto.setMajor( rs.getString( 3 ) );
		dto.setValue1( rs.getString( 4 ) );
		dto.setValue1Low( rs.getString( 5 ) );
		dto.setValue2( rs.getString( 6 ) );
		dto.setValue2Low( rs.getString( 7 ) );
		dto.setComparetype( rs.getString( 8 ) );
		dto.setSeverity1( rs.getLong( 9 ) );
		if (rs.wasNull()) {
			dto.setSeverity1Null( true );
		}
		
		dto.setV1lSeverity1( rs.getLong( 10 ) );
		if (rs.wasNull()) {
			dto.setV1lSeverity1Null( true );
		}
		
		dto.setSeverity2( rs.getLong( 11 ) );
		if (rs.wasNull()) {
			dto.setSeverity2Null( true );
		}
		
		dto.setV2lSeverity2( rs.getLong( 12 ) );
		if (rs.wasNull()) {
			dto.setV2lSeverity2Null( true );
		}
		
		dto.setFilterA( rs.getString( 13 ) );
		dto.setFilterB( rs.getString( 14 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "V_SNMP_PDM_THRESHOLDS";
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria ''.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findAll() throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'DEVIP = :devip'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereDevipEquals(String devip) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE DEVIP = ? ORDER BY DEVIP", this,devip);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'OIDNAME = :oidname'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereOidnameEquals(String oidname) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE OIDNAME = ? ORDER BY OIDNAME", this,oidname);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'MAJOR = :major'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereMajorEquals(String major) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE MAJOR = ? ORDER BY MAJOR", this,major);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'VALUE_1 = :value1'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereValue1Equals(String value1) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE VALUE_1 = ? ORDER BY VALUE_1", this,value1);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'VALUE_1_LOW = :value1Low'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereValue1LowEquals(String value1Low) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE VALUE_1_LOW = ? ORDER BY VALUE_1_LOW", this,value1Low);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'VALUE_2 = :value2'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereValue2Equals(String value2) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE VALUE_2 = ? ORDER BY VALUE_2", this,value2);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'VALUE_2_LOW = :value2Low'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereValue2LowEquals(String value2Low) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE VALUE_2_LOW = ? ORDER BY VALUE_2_LOW", this,value2Low);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereComparetypeEquals(String comparetype) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE COMPARETYPE = ? ORDER BY COMPARETYPE", this,comparetype);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereSeverity1Equals(long severity1) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE SEVERITY_1 = ? ORDER BY SEVERITY_1", this,severity1);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'V1L_SEVERITY_1 = :v1lSeverity1'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereV1lSeverity1Equals(long v1lSeverity1) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE V1L_SEVERITY_1 = ? ORDER BY V1L_SEVERITY_1", this,v1lSeverity1);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereSeverity2Equals(long severity2) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE SEVERITY_2 = ? ORDER BY SEVERITY_2", this,severity2);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'V2L_SEVERITY_2 = :v2lSeverity2'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereV2lSeverity2Equals(long v2lSeverity2) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE V2L_SEVERITY_2 = ? ORDER BY V2L_SEVERITY_2", this,v2lSeverity2);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'FILTER_A = :filterA'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereFilterAEquals(String filterA) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE FILTER_A = ? ORDER BY FILTER_A", this,filterA);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'FILTER_B = :filterB'.
	 */
	@Transactional
	public List<VSnmpPdmThresholds> findWhereFilterBEquals(String filterB) throws VSnmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, MAJOR, VALUE_1, VALUE_1_LOW, VALUE_2, VALUE_2_LOW, COMPARETYPE, SEVERITY_1, V1L_SEVERITY_1, SEVERITY_2, V2L_SEVERITY_2, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE FILTER_B = ? ORDER BY FILTER_B", this,filterB);
		}
		catch (Exception e) {
			throw new VSnmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

}
