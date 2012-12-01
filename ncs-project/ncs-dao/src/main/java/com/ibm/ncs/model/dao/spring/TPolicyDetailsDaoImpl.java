package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TPolicyDetailsDao;
import com.ibm.ncs.model.dto.TPolicyDetails;
import com.ibm.ncs.model.dto.TPolicyDetailsPk;
import com.ibm.ncs.model.exceptions.TPolicyDetailsDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TPolicyDetailsDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TPolicyDetails>, TPolicyDetailsDao
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
	 * @return TPolicyDetailsPk
	 */
	@Transactional
	public TPolicyDetailsPk insert(TPolicyDetails dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getMpid(),dto.getModid(),dto.getEveid(),dto.isPollNull()?null:dto.getPoll(),dto.getValue1(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.getFilterA(),dto.getValue2(),dto.isSeverity2Null()?null:dto.getSeverity2(),dto.getFilterB(),dto.isSeverityANull()?null:dto.getSeverityA(),dto.isSeverityBNull()?null:dto.getSeverityB(),dto.getOidgroup(),dto.getOgflag(),dto.getValue1Low(),dto.getValue2Low(),dto.isV1lSeverity1Null()?null:dto.getV1lSeverity1(),dto.isV1lSeverityANull()?null:dto.getV1lSeverityA(),dto.isV2lSeverity2Null()?null:dto.getV2lSeverity2(),dto.isV2lSeverityBNull()?null:dto.getV2lSeverityB(),dto.getComparetype());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_POLICY_DETAILS table.
	 */
	@Transactional
	public void update(TPolicyDetailsPk pk, TPolicyDetails dto) throws TPolicyDetailsDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET MPID = ?, MODID = ?, EVEID = ?, POLL = ?, VALUE_1 = ?, SEVERITY_1 = ?, FILTER_A = ?, VALUE_2 = ?, SEVERITY_2 = ?, FILTER_B = ?, SEVERITY_A = ?, SEVERITY_B = ?, OIDGROUP = ?, OGFLAG = ?, VALUE_1_LOW = ?, VALUE_2_LOW = ?, V1L_SEVERITY_1 = ?, V1L_SEVERITY_A = ?, V2L_SEVERITY_2 = ?, V2L_SEVERITY_B = ?, COMPARETYPE = ? WHERE MPID = ? AND MODID = ? AND EVEID = ?",dto.getMpid(),dto.getModid(),dto.getEveid(),dto.isPollNull()?null:dto.getPoll(),dto.getValue1(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.getFilterA(),dto.getValue2(),dto.isSeverity2Null()?null:dto.getSeverity2(),dto.getFilterB(),dto.isSeverityANull()?null:dto.getSeverityA(),dto.isSeverityBNull()?null:dto.getSeverityB(),dto.getOidgroup(),dto.getOgflag(),dto.getValue1Low(),dto.getValue2Low(),dto.isV1lSeverity1Null()?null:dto.getV1lSeverity1(),dto.isV1lSeverityANull()?null:dto.getV1lSeverityA(),dto.isV2lSeverity2Null()?null:dto.getV2lSeverity2(),dto.isV2lSeverityBNull()?null:dto.getV2lSeverityB(),dto.getComparetype(),pk.getMpid(),pk.getModid(),pk.getEveid());
	}
	/** 
	 * Deletes a multi rows in the T_POLICY_DETAILS table.
	 */
	@Transactional
	public void delete(long mpid) throws TPolicyDetailsDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE MPID = ? ",mpid);
	}
	/** 
	 * Deletes a single row in the T_POLICY_DETAILS table.
	 */
	@Transactional
	public void delete(TPolicyDetailsPk pk) throws TPolicyDetailsDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE MPID = ? AND MODID = ? AND EVEID = ?",pk.getMpid(),pk.getModid(),pk.getEveid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TPolicyDetails
	 */
	public TPolicyDetails mapRow(ResultSet rs, int row) throws SQLException
	{
		TPolicyDetails dto = new TPolicyDetails();
		dto.setMpid( rs.getLong( 1 ) );
		dto.setModid( rs.getLong( 2 ) );
		dto.setEveid( rs.getLong( 3 ) );
		dto.setPoll( rs.getLong( 4 ) );
		if (rs.wasNull()) {
			dto.setPollNull( true );
		}
		
		dto.setValue1( rs.getString( 5 ) );
		dto.setSeverity1( rs.getLong( 6 ) );
		if (rs.wasNull()) {
			dto.setSeverity1Null( true );
		}
		
		dto.setFilterA( rs.getString( 7 ) );
		dto.setValue2( rs.getString( 8 ) );
		dto.setSeverity2( rs.getLong( 9 ) );
		if (rs.wasNull()) {
			dto.setSeverity2Null( true );
		}
		
		dto.setFilterB( rs.getString( 10 ) );
		dto.setSeverityA( rs.getLong( 11 ) );
		if (rs.wasNull()) {
			dto.setSeverityANull( true );
		}
		
		dto.setSeverityB( rs.getLong( 12 ) );
		if (rs.wasNull()) {
			dto.setSeverityBNull( true );
		}
		
		dto.setOidgroup( rs.getString( 13 ) );
		dto.setOgflag( rs.getString( 14 ) );
		dto.setValue1Low( rs.getString( 15 ) );
		dto.setValue2Low( rs.getString( 16 ) );
		dto.setV1lSeverity1( rs.getLong( 17 ) );
		if (rs.wasNull()) {
			dto.setV1lSeverity1Null( true );
		}
		
		dto.setV1lSeverityA( rs.getLong( 18 ) );
		if (rs.wasNull()) {
			dto.setV1lSeverityANull( true );
		}
		
		dto.setV2lSeverity2( rs.getLong( 19 ) );
		if (rs.wasNull()) {
			dto.setV2lSeverity2Null( true );
		}
		
		dto.setV2lSeverityB( rs.getLong( 20 ) );
		if (rs.wasNull()) {
			dto.setV2lSeverityBNull( true );
		}
		
		dto.setComparetype( rs.getString( 21 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_POLICY_DETAILS";
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'MPID = :mpid AND MODID = :modid AND EVEID = :eveid'.
	 */
	@Transactional
	public TPolicyDetails findByPrimaryKey(long mpid, long modid, long eveid) throws TPolicyDetailsDaoException
	{
		try {
			List<TPolicyDetails> list = jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE MPID = ? AND MODID = ? AND EVEID = ?", this,mpid,modid,eveid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria ''.
	 */
	@Transactional
	public List<TPolicyDetails> findAll() throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " ORDER BY MPID, MODID, EVEID", this);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'MPID = :mpid'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereMpidEquals(long mpid) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE MPID = ? ORDER BY MPID", this,mpid);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'MODID = :modid'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereModidEquals(long modid) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE MODID = ? ORDER BY MODID", this,modid);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'EVEID = :eveid'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereEveidEquals(long eveid) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE EVEID = ? ORDER BY EVEID", this,eveid);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'POLL = :poll'.
	 */
	@Transactional
	public List<TPolicyDetails> findWherePollEquals(long poll) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE POLL = ? ORDER BY POLL", this,poll);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'VALUE_1 = :value1'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereValue1Equals(String value1) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE VALUE_1 = ? ORDER BY VALUE_1", this,value1);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereSeverity1Equals(long severity1) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE SEVERITY_1 = ? ORDER BY SEVERITY_1", this,severity1);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'FILTER_A = :filterA'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereFilterAEquals(String filterA) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE FILTER_A = ? ORDER BY FILTER_A", this,filterA);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'VALUE_2 = :value2'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereValue2Equals(String value2) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE VALUE_2 = ? ORDER BY VALUE_2", this,value2);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereSeverity2Equals(long severity2) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE SEVERITY_2 = ? ORDER BY SEVERITY_2", this,severity2);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'FILTER_B = :filterB'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereFilterBEquals(String filterB) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE FILTER_B = ? ORDER BY FILTER_B", this,filterB);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'SEVERITY_A = :severityA'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereSeverityAEquals(long severityA) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE SEVERITY_A = ? ORDER BY SEVERITY_A", this,severityA);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'SEVERITY_B = :severityB'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereSeverityBEquals(long severityB) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE SEVERITY_B = ? ORDER BY SEVERITY_B", this,severityB);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'OIDGROUP = :oidgroup'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereOidgroupEquals(String oidgroup) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE OIDGROUP = ? ORDER BY OIDGROUP", this,oidgroup);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'OGFLAG = :ogflag'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereOgflagEquals(String ogflag) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE OGFLAG = ? ORDER BY OGFLAG", this,ogflag);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'VALUE_1_LOW = :value1Low'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereValue1LowEquals(String value1Low) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE VALUE_1_LOW = ? ORDER BY VALUE_1_LOW", this,value1Low);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'VALUE_2_LOW = :value2Low'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereValue2LowEquals(String value2Low) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE VALUE_2_LOW = ? ORDER BY VALUE_2_LOW", this,value2Low);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'V1L_SEVERITY_1 = :v1lSeverity1'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereV1lSeverity1Equals(long v1lSeverity1) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE V1L_SEVERITY_1 = ? ORDER BY V1L_SEVERITY_1", this,v1lSeverity1);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'V1L_SEVERITY_A = :v1lSeverityA'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereV1lSeverityAEquals(long v1lSeverityA) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE V1L_SEVERITY_A = ? ORDER BY V1L_SEVERITY_A", this,v1lSeverityA);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'V2L_SEVERITY_2 = :v2lSeverity2'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereV2lSeverity2Equals(long v2lSeverity2) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE V2L_SEVERITY_2 = ? ORDER BY V2L_SEVERITY_2", this,v2lSeverity2);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'V2L_SEVERITY_B = :v2lSeverityB'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereV2lSeverityBEquals(long v2lSeverityB) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE V2L_SEVERITY_B = ? ORDER BY V2L_SEVERITY_B", this,v2lSeverityB);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	@Transactional
	public List<TPolicyDetails> findWhereComparetypeEquals(String comparetype) throws TPolicyDetailsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, MODID, EVEID, POLL, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, VALUE_1_LOW, VALUE_2_LOW, V1L_SEVERITY_1, V1L_SEVERITY_A, V2L_SEVERITY_2, V2L_SEVERITY_B, COMPARETYPE FROM " + getTableName() + " WHERE COMPARETYPE = ? ORDER BY COMPARETYPE", this,comparetype);
		}
		catch (Exception e) {
			throw new TPolicyDetailsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_POLICY_DETAILS table that matches the specified primary-key value.
	 */
	public TPolicyDetails findByPrimaryKey(TPolicyDetailsPk pk) throws TPolicyDetailsDaoException
	{
		return findByPrimaryKey( pk.getMpid(), pk.getModid(), pk.getEveid() );
	}

}
