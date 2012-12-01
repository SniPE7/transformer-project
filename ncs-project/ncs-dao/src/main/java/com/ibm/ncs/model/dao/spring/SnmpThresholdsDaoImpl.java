package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.SnmpThresholdsDao;
import com.ibm.ncs.model.dto.SnmpThresholds;
import com.ibm.ncs.model.dto.SnmpThresholdsPk;
import com.ibm.ncs.model.exceptions.SnmpThresholdsDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class SnmpThresholdsDaoImpl extends AbstractDAO implements ParameterizedRowMapper<SnmpThresholds>, SnmpThresholdsDao
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
	 * @return SnmpThresholdsPk
	 */
	@Transactional
	public SnmpThresholdsPk insert(SnmpThresholds dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getPerformance(),dto.getBtime(),dto.getEtime(),dto.getThreshold(),dto.getComparetype(),dto.getSeverity1(),dto.getSeverity2(),dto.isFilterflag1Null()?null:dto.getFilterflag1(),dto.isFilterflag2Null()?null:dto.getFilterflag2());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the SNMP_THRESHOLDS table.
	 */
	@Transactional
	public void update(SnmpThresholdsPk pk, SnmpThresholds dto) throws SnmpThresholdsDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET PERFORMANCE = ?, BTIME = ?, ETIME = ?, THRESHOLD = ?, COMPARETYPE = ?, SEVERITY1 = ?, SEVERITY2 = ?, FILTERFLAG1 = ?, FILTERFLAG2 = ? WHERE PERFORMANCE = ?",dto.getPerformance(),dto.getBtime(),dto.getEtime(),dto.getThreshold(),dto.getComparetype(),dto.getSeverity1(),dto.getSeverity2(),dto.getFilterflag1(),dto.getFilterflag2(),pk.getPerformance());
	}

	/** 
	 * Deletes a single row in the SNMP_THRESHOLDS table.
	 */
	@Transactional
	public void delete(SnmpThresholdsPk pk) throws SnmpThresholdsDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PERFORMANCE = ?",pk.getPerformance());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return SnmpThresholds
	 */
	public SnmpThresholds mapRow(ResultSet rs, int row) throws SQLException
	{
		SnmpThresholds dto = new SnmpThresholds();
		dto.setPerformance( rs.getString( 1 ) );
		dto.setBtime( rs.getString( 2 ) );
		dto.setEtime( rs.getString( 3 ) );
		dto.setThreshold( rs.getString( 4 ) );
		dto.setComparetype( rs.getString( 5 ) );
		dto.setSeverity1( rs.getString( 6 ) );
		dto.setSeverity2( rs.getString( 7 ) );
		dto.setFilterflag1( rs.getLong( 8 ) );
		if (rs.wasNull()) {
			dto.setFilterflag1Null( true );
		}
		
		dto.setFilterflag2( rs.getLong( 9 ) );
		if (rs.wasNull()) {
			dto.setFilterflag2Null( true );
		}
		
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "SNMP_THRESHOLDS";
	}

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'PERFORMANCE = :performance'.
	 */
	@Transactional
	public SnmpThresholds findByPrimaryKey(String performance) throws SnmpThresholdsDaoException
	{
		try {
			List<SnmpThresholds> list = jdbcTemplate.query("SELECT PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE PERFORMANCE = ?", this,performance);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new SnmpThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria ''.
	 */
	@Transactional
	public List<SnmpThresholds> findAll() throws SnmpThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " ORDER BY PERFORMANCE", this);
		}
		catch (Exception e) {
			throw new SnmpThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'PERFORMANCE = :performance'.
	 */
	@Transactional
	public List<SnmpThresholds> findWherePerformanceEquals(String performance) throws SnmpThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE PERFORMANCE = ? ORDER BY PERFORMANCE", this,performance);
		}
		catch (Exception e) {
			throw new SnmpThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'BTIME = :btime'.
	 */
	@Transactional
	public List<SnmpThresholds> findWhereBtimeEquals(String btime) throws SnmpThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE BTIME = ? ORDER BY BTIME", this,btime);
		}
		catch (Exception e) {
			throw new SnmpThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'ETIME = :etime'.
	 */
	@Transactional
	public List<SnmpThresholds> findWhereEtimeEquals(String etime) throws SnmpThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE ETIME = ? ORDER BY ETIME", this,etime);
		}
		catch (Exception e) {
			throw new SnmpThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'THRESHOLD = :threshold'.
	 */
	@Transactional
	public List<SnmpThresholds> findWhereThresholdEquals(String threshold) throws SnmpThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE THRESHOLD = ? ORDER BY THRESHOLD", this,threshold);
		}
		catch (Exception e) {
			throw new SnmpThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	@Transactional
	public List<SnmpThresholds> findWhereComparetypeEquals(String comparetype) throws SnmpThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE COMPARETYPE = ? ORDER BY COMPARETYPE", this,comparetype);
		}
		catch (Exception e) {
			throw new SnmpThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	@Transactional
	public List<SnmpThresholds> findWhereSeverity1Equals(String severity1) throws SnmpThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE SEVERITY1 = ? ORDER BY SEVERITY1", this,severity1);
		}
		catch (Exception e) {
			throw new SnmpThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'SEVERITY2 = :severity2'.
	 */
	@Transactional
	public List<SnmpThresholds> findWhereSeverity2Equals(String severity2) throws SnmpThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE SEVERITY2 = ? ORDER BY SEVERITY2", this,severity2);
		}
		catch (Exception e) {
			throw new SnmpThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'FILTERFLAG1 = :filterflag1'.
	 */
	@Transactional
	public List<SnmpThresholds> findWhereFilterflag1Equals(long filterflag1) throws SnmpThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE FILTERFLAG1 = ? ORDER BY FILTERFLAG1", this,filterflag1);
		}
		catch (Exception e) {
			throw new SnmpThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'FILTERFLAG2 = :filterflag2'.
	 */
	@Transactional
	public List<SnmpThresholds> findWhereFilterflag2Equals(long filterflag2) throws SnmpThresholdsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PERFORMANCE, BTIME, ETIME, THRESHOLD, COMPARETYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE FILTERFLAG2 = ? ORDER BY FILTERFLAG2", this,filterflag2);
		}
		catch (Exception e) {
			throw new SnmpThresholdsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the SNMP_THRESHOLDS table that matches the specified primary-key value.
	 */
	public SnmpThresholds findByPrimaryKey(SnmpThresholdsPk pk) throws SnmpThresholdsDaoException
	{
		return findByPrimaryKey( pk.getPerformance() );
	}
	/**
	 * Method 'update'
	 * 
	 * @param dto
	 */

	public void update(SnmpThresholds dto)
	{
		jdbcTemplate.update("Update " + getTableName() + " set   BTIME=?, ETIME=?, THRESHOLD=?, COMPARETYPE=?, SEVERITY1=?, SEVERITY2=?,  FILTERFLAG1=?,FILTERFLAG2=?  " +
				" where performance=? "
				,dto.getBtime(),dto.getEtime(),dto.getThreshold(),dto.getComparetype(),dto.getSeverity1(),dto.getSeverity2(),dto.getFilterflag1(),dto.getFilterflag2(),dto.getPerformance());
	}

	/**
	 * Method 'delete'
	 * 
	 * @param dto
	 */

	public void delete(SnmpThresholds dto)
	{
		jdbcTemplate.update("Delete from " + getTableName() + " where performance=?  "	,dto.getPerformance());
	}
	
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return SnmpThresholdsPk
	 */
	@Transactional
	public int insertEffect()
	{
		int ret = -1; //success flag; 
		String sqldev  = "select devip||'|'||major performance, btime, etime, " +
				" value_1||'|'||(case when value_2 is not null then value_2 else 'null' end) threshold, " +
				" 'value '||comparetype||' threshold' comparetype, " +
				"  severity_1||'|'||(case when length(trim(''||severity_2 ))<>0  then (''||severity_2) else 'null' end) severity1, " +
				"  severity_A||'|'||(case when length(trim(''||severity_B ))<>0  then (''||severity_B) else 'null' end) severity2, " +
				" filter_A filterflag1,filter_B filterflag2 " +
				" from v_snmp_dev_thresholds "+
				" where value_1 is not null ";
		String sqlport = "select devip||'|'||IFDESCR||'|'||major performance, btime, etime, " +
				" value_1||'|'||" +
				"(case when value_1_low is not null then value_1_low else 'null' end)||'|'||" +
				"(case when value_2     is not null then value_2     else 'null' end)||'|'||" +
				"(case when value_2_low is not null then value_2_low else 'null' end)  threshold, " +
				" 'value '||comparetype||' threshold' comparetype, " +
			
				"  severity_1||'|'||" +
				"(case when length(trim(''||v1l_severity_1 ))<>0  then (''||v1l_severity_1) else 'null' end)||'|'|| " +
				"(case when length(trim(''||severity_2 ))<>0  then (''||severity_2) else 'null' end)||'|'|| " +
				"(case when length(trim(''||v2l_severity_2 ))<>0  then (''||v2l_severity_2) else 'null' end) " +
				" severity1, " +
				
				"  severity_A||'|'||" +
				"(case when length(trim(''||v1l_severity_A ))<>0  then (''||v1l_severity_A) else 'null' end)||'|'|| " +
				"(case when length(trim(''||severity_B ))<>0  then (''||severity_B) else 'null' end)||'|'|| " +
				"(case when length(trim(''||v2l_severity_B ))<>0  then (''||v2l_severity_B) else 'null' end) " +
				" severity2, " +
				
				" filter_A filterflag1,filter_B filterflag2 " +
				" from v_snmp_PORT_thresholds"+
				" where value_1 is not null ";
		String sqlpdm  = "select devip||'|'||oidname||'|'||major performance, btime, etime, " +
				" value_1||'|'||" +
		//		"(case when value_1_low is not null then value_1_low else 'null' end)||'|'||" +
		//		"(case when value_2     is not null then value_2     else 'null' end)||'|'||" +
		//		"(case when value_2_low is not null then value_2_low else 'null' end)  threshold, " +
				"(case when value_2     is not null then value_2     else 'null' end)  threshold, " +
				" 'value '||comparetype||' threshold' comparetype, " +
			
				"  severity_1||'|'||" +
		//		"(case when length(trim(''||v1l_severity_1 ))<>0  then (''||v1l_severity_1) else 'null' end)||'|'|| " +
		//		"(case when length(trim(''||severity_2 ))<>0  then (''||severity_2) else 'null' end)||'|'|| " +
		//		"(case when length(trim(''||v2l_severity_2 ))<>0  then (''||v2l_severity_2) else 'null' end) " +
				"(case when length(trim(''||severity_2 ))<>0  then (''||severity_2) else 'null' end) " +
				" severity1, " +
				
				"  severity_A||'|'||" +
		//		"(case when length(trim(''||v1l_severity_A ))<>0  then (''||v1l_severity_A) else 'null' end)||'|'|| " +
		//		"(case when length(trim(''||severity_B ))<>0  then (''||severity_B) else 'null' end)||'|'|| " +
		//		"(case when length(trim(''||v2l_severity_B ))<>0  then (''||v2l_severity_B) else 'null' end) " +
				"(case when length(trim(''||severity_B ))<>0  then (''||severity_B) else 'null' end) " +
				" severity2, " +
				" filter_A filterflag1,filter_B filterflag2 " +
				" from v_snmp_pdm_thresholds"+
				" where value_1 is not null ";
		try {
			ret  = jdbcTemplate.update("INSERT INTO " + getTableName() +"("+ sqldev +")");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		try {
			ret += jdbcTemplate.update("INSERT INTO " + getTableName() +"("+ sqlport +")");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		try {
			ret += jdbcTemplate.update("INSERT INTO " + getTableName() +"("+ sqlpdm +")");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}  
		return ret;
	}
	
	public int  deleteAll(){
		int ret = -1;
		ret = jdbcTemplate.update("DELETE FROM " + getTableName() );
		return ret;
	}

}
