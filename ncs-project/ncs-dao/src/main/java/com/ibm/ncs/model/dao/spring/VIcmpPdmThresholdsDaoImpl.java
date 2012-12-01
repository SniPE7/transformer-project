package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.VIcmpPdmThresholdsDao;
import com.ibm.ncs.model.dto.VIcmpPdmThresholds;
import com.ibm.ncs.model.exceptions.VIcmpPdmThresholdsDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class VIcmpPdmThresholdsDaoImpl extends AbstractDAO implements ParameterizedRowMapper<VIcmpPdmThresholds>, VIcmpPdmThresholdsDao
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
	public void insert(VIcmpPdmThresholds dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getDevip(),dto.getOidname(),dto.getBtime(),dto.getEtime(),dto.getValue0(),dto.getValue1(),dto.getValue2(),dto.getVar0(),dto.getValue1Low(),dto.getValue2Low(),dto.getComparetype(),dto.isV1lSeverity1Null()?null:dto.getV1lSeverity1(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.isSeverity2Null()?null:dto.getSeverity2(),dto.isV1lSeverityANull()?null:dto.getV1lSeverityA(),dto.isSeverityANull()?null:dto.getSeverityA(),dto.isSeverityBNull()?null:dto.getSeverityB(),dto.getFilterA(),dto.getFilterB());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return VIcmpPdmThresholds
	 */
	public VIcmpPdmThresholds mapRow(ResultSet rs, int row) throws SQLException
	{
		VIcmpPdmThresholds dto = new VIcmpPdmThresholds();
		dto.setDevip( rs.getString( 1 ) );
		dto.setOidname( rs.getString( 2 ) );
		dto.setBtime( rs.getString( 3 ) );
		dto.setEtime( rs.getString( 4 ) );
		dto.setValue0( rs.getString( 5 ) );
		dto.setValue1( rs.getString( 6 ) );
		dto.setValue2( rs.getString( 7 ) );
		dto.setVar0( rs.getString( 8 ) );
		dto.setValue1Low( rs.getString( 9 ) );
		dto.setValue2Low( rs.getString( 10 ) );
		dto.setComparetype( rs.getString( 11 ) );
		dto.setV1lSeverity1( rs.getLong( 12 ) );
		if (rs.wasNull()) {
			dto.setV1lSeverity1Null( true );
		}
		
		dto.setSeverity1( rs.getLong( 13 ) );
		if (rs.wasNull()) {
			dto.setSeverity1Null( true );
		}
		
		dto.setSeverity2( rs.getLong( 14 ) );
		if (rs.wasNull()) {
			dto.setSeverity2Null( true );
		}
		
		dto.setV1lSeverityA( rs.getLong( 15 ) );
		if (rs.wasNull()) {
			dto.setV1lSeverityANull( true );
		}
		
		dto.setSeverityA( rs.getLong( 16 ) );
		if (rs.wasNull()) {
			dto.setSeverityANull( true );
		}
		
		dto.setSeverityB( rs.getLong( 17 ) );
		if (rs.wasNull()) {
			dto.setSeverityBNull( true );
		}
		
		dto.setFilterA( rs.getString( 18 ) );
		dto.setFilterB( rs.getString( 19 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "V_ICMP_PDM_THRESHOLDS";
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria ''.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findAll() throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'DEVIP = :devip'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereDevipEquals(String devip) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE DEVIP = ? ORDER BY DEVIP", this,devip);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'OIDNAME = :oidname'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereOidnameEquals(String oidname) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE OIDNAME = ? ORDER BY OIDNAME", this,oidname);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'BTIME = :btime'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereBtimeEquals(String btime) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE BTIME = ? ORDER BY BTIME", this,btime);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'ETIME = :etime'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereEtimeEquals(String etime) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE ETIME = ? ORDER BY ETIME", this,etime);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'VALUE0 = :value0'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereValue0Equals(String value0) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE VALUE0 = ? ORDER BY VALUE0", this,value0);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'VALUE_1 = :value1'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereValue1Equals(String value1) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE VALUE_1 = ? ORDER BY VALUE_1", this,value1);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'VALUE_2 = :value2'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereValue2Equals(String value2) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE VALUE_2 = ? ORDER BY VALUE_2", this,value2);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'VAR0 = :var0'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereVar0Equals(String var0) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE VAR0 = ? ORDER BY VAR0", this,var0);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'VALUE_1_LOW = :value1Low'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereValue1LowEquals(String value1Low) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE VALUE_1_LOW = ? ORDER BY VALUE_1_LOW", this,value1Low);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'VALUE_2_LOW = :value2Low'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereValue2LowEquals(String value2Low) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE VALUE_2_LOW = ? ORDER BY VALUE_2_LOW", this,value2Low);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereComparetypeEquals(String comparetype) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE COMPARETYPE = ? ORDER BY COMPARETYPE", this,comparetype);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'V1L_SEVERITY_1 = :v1lSeverity1'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereV1lSeverity1Equals(long v1lSeverity1) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE V1L_SEVERITY_1 = ? ORDER BY V1L_SEVERITY_1", this,v1lSeverity1);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereSeverity1Equals(long severity1) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE SEVERITY_1 = ? ORDER BY SEVERITY_1", this,severity1);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereSeverity2Equals(long severity2) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE SEVERITY_2 = ? ORDER BY SEVERITY_2", this,severity2);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'V1L_SEVERITY_A = :v1lSeverityA'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereV1lSeverityAEquals(long v1lSeverityA) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE V1L_SEVERITY_A = ? ORDER BY V1L_SEVERITY_A", this,v1lSeverityA);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'SEVERITY_A = :severityA'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereSeverityAEquals(long severityA) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE SEVERITY_A = ? ORDER BY SEVERITY_A", this,severityA);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'SEVERITY_B = :severityB'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereSeverityBEquals(long severityB) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE SEVERITY_B = ? ORDER BY SEVERITY_B", this,severityB);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'FILTER_A = :filterA'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereFilterAEquals(String filterA) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE FILTER_A = ? ORDER BY FILTER_A", this,filterA);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'FILTER_B = :filterB'.
	 */
	@Transactional
	public List<VIcmpPdmThresholds> findWhereFilterBEquals(String filterB) throws VIcmpPdmThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, OIDNAME, BTIME, ETIME, VALUE0, VALUE_1, VALUE_2, VAR0, VALUE_1_LOW, VALUE_2_LOW, COMPARETYPE, V1L_SEVERITY_1, SEVERITY_1, SEVERITY_2, V1L_SEVERITY_A, SEVERITY_A, SEVERITY_B, FILTER_A, FILTER_B FROM " + getTableName() + " WHERE FILTER_B = ? ORDER BY FILTER_B", this,filterB);
		}
		catch (Exception e) {
			throw new VIcmpPdmThresholdsDaoException("Query failed", e);
		}
		
	}

}
