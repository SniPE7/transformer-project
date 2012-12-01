package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TPolicyDefaultDao;
import com.ibm.ncs.model.dto.TPolicyDefault;
import com.ibm.ncs.model.dto.TPolicyDefaultPk;
import com.ibm.ncs.model.exceptions.TPolicyDefaultDaoException;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TPolicyDefaultDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TPolicyDefault>, TPolicyDefaultDao
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
	 * @return TPolicyDefaultPk
	 */
	@Transactional
	public TPolicyDefaultPk insert(TPolicyDefault dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getModid(),dto.getEveid(),dto.getStartTime(),dto.getEndTime(),dto.getValue1(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.getFilterA(),dto.getValue2(),dto.isSeverity2Null()?null:dto.getSeverity2(),dto.getFilterB(),dto.isSeverityANull()?null:dto.getSeverityA(),dto.isSeverityBNull()?null:dto.getSeverityB(),dto.getOidgroup(),dto.getOgflag(),dto.isS1Null()?null:dto.getS1(),dto.isS2Null()?null:dto.getS2(),dto.isS3Null()?null:dto.getS3(),dto.isS4Null()?null:dto.getS4(),dto.isS5Null()?null:dto.getS5(),dto.isS6Null()?null:dto.getS6(),dto.isS7Null()?null:dto.getS7(),dto.isE1Null()?null:dto.getE1(),dto.isE2Null()?null:dto.getE2(),dto.isE3Null()?null:dto.getE3(),dto.isE4Null()?null:dto.getE4(),dto.isE5Null()?null:dto.getE5(),dto.isE6Null()?null:dto.getE6(),dto.isE7Null()?null:dto.getE7());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_POLICY_DEFAULT table.
	 */
	@Transactional
	public void update(TPolicyDefaultPk pk, TPolicyDefault dto) throws TPolicyDefaultDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET MODID = ?, EVEID = ?, START_TIME = ?, END_TIME = ?, VALUE_1 = ?, SEVERITY_1 = ?, FILTER_A = ?, VALUE_2 = ?, SEVERITY_2 = ?, FILTER_B = ?, SEVERITY_A = ?, SEVERITY_B = ?, OIDGROUP = ?, OGFLAG = ?, S1 = ?, S2 = ?, S3 = ?, S4 = ?, S5 = ?, S6 = ?, S7 = ?, E1 = ?, E2 = ?, E3 = ?, E4 = ?, E5 = ?, E6 = ?, E7 = ? WHERE MODID = ? AND EVEID = ?",dto.getModid(),dto.getEveid(),dto.getStartTime(),dto.getEndTime(),dto.getValue1(),dto.getSeverity1(),dto.getFilterA(),dto.getValue2(),dto.getSeverity2(),dto.getFilterB(),dto.getSeverityA(),dto.getSeverityB(),dto.getOidgroup(),dto.getOgflag(),dto.getS1(),dto.getS2(),dto.getS3(),dto.getS4(),dto.getS5(),dto.getS6(),dto.getS7(),dto.getE1(),dto.getE2(),dto.getE3(),dto.getE4(),dto.getE5(),dto.getE6(),dto.getE7(),pk.getModid(),pk.getEveid());
	}

	/** 
	 * Deletes a single row in the T_POLICY_DEFAULT table.
	 */
	@Transactional
	public void delete(TPolicyDefaultPk pk) throws TPolicyDefaultDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE MODID = ? AND EVEID = ?",pk.getModid(),pk.getEveid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TPolicyDefault
	 */
	public TPolicyDefault mapRow(ResultSet rs, int row) throws SQLException
	{
		TPolicyDefault dto = new TPolicyDefault();
		dto.setModid( rs.getLong( 1 ) );
		dto.setEveid( rs.getLong( 2 ) );
		dto.setStartTime( rs.getTimestamp(3 ) );
		dto.setEndTime( rs.getTimestamp(4 ) );
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
		dto.setS1( rs.getLong( 15 ) );
		if (rs.wasNull()) {
			dto.setS1Null( true );
		}
		
		dto.setS2( rs.getLong( 16 ) );
		if (rs.wasNull()) {
			dto.setS2Null( true );
		}
		
		dto.setS3( rs.getLong( 17 ) );
		if (rs.wasNull()) {
			dto.setS3Null( true );
		}
		
		dto.setS4( rs.getLong( 18 ) );
		if (rs.wasNull()) {
			dto.setS4Null( true );
		}
		
		dto.setS5( rs.getLong( 19 ) );
		if (rs.wasNull()) {
			dto.setS5Null( true );
		}
		
		dto.setS6( rs.getLong( 20 ) );
		if (rs.wasNull()) {
			dto.setS6Null( true );
		}
		
		dto.setS7( rs.getLong( 21 ) );
		if (rs.wasNull()) {
			dto.setS7Null( true );
		}
		
		dto.setE1( rs.getLong( 22 ) );
		if (rs.wasNull()) {
			dto.setE1Null( true );
		}
		
		dto.setE2( rs.getLong( 23 ) );
		if (rs.wasNull()) {
			dto.setE2Null( true );
		}
		
		dto.setE3( rs.getLong( 24 ) );
		if (rs.wasNull()) {
			dto.setE3Null( true );
		}
		
		dto.setE4( rs.getLong( 25 ) );
		if (rs.wasNull()) {
			dto.setE4Null( true );
		}
		
		dto.setE5( rs.getLong( 26 ) );
		if (rs.wasNull()) {
			dto.setE5Null( true );
		}
		
		dto.setE6( rs.getLong( 27 ) );
		if (rs.wasNull()) {
			dto.setE6Null( true );
		}
		
		dto.setE7( rs.getLong( 28 ) );
		if (rs.wasNull()) {
			dto.setE7Null( true );
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
		return "T_POLICY_DEFAULT";
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'MODID = :modid AND EVEID = :eveid'.
	 */
	@Transactional
	public TPolicyDefault findByPrimaryKey(long modid, long eveid) throws TPolicyDefaultDaoException
	{
		try {
			List<TPolicyDefault> list = jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE MODID = ? AND EVEID = ?", this,modid,eveid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria ''.
	 */
	@Transactional
	public List<TPolicyDefault> findAll() throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " ORDER BY MODID, EVEID", this);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'MODID = :modid'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereModidEquals(long modid) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE MODID = ? ORDER BY MODID", this,modid);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'EVEID = :eveid'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereEveidEquals(long eveid) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE EVEID = ? ORDER BY EVEID", this,eveid);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'START_TIME = :startTime'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereStartTimeEquals(Date startTime) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE START_TIME = ? ORDER BY START_TIME", this,startTime);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'END_TIME = :endTime'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereEndTimeEquals(Date endTime) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE END_TIME = ? ORDER BY END_TIME", this,endTime);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'VALUE_1 = :value1'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereValue1Equals(String value1) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE VALUE_1 = ? ORDER BY VALUE_1", this,value1);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereSeverity1Equals(long severity1) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE SEVERITY_1 = ? ORDER BY SEVERITY_1", this,severity1);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'FILTER_A = :filterA'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereFilterAEquals(String filterA) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE FILTER_A = ? ORDER BY FILTER_A", this,filterA);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'VALUE_2 = :value2'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereValue2Equals(String value2) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE VALUE_2 = ? ORDER BY VALUE_2", this,value2);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereSeverity2Equals(long severity2) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE SEVERITY_2 = ? ORDER BY SEVERITY_2", this,severity2);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'FILTER_B = :filterB'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereFilterBEquals(String filterB) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE FILTER_B = ? ORDER BY FILTER_B", this,filterB);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'SEVERITY_A = :severityA'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereSeverityAEquals(long severityA) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE SEVERITY_A = ? ORDER BY SEVERITY_A", this,severityA);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'SEVERITY_B = :severityB'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereSeverityBEquals(long severityB) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE SEVERITY_B = ? ORDER BY SEVERITY_B", this,severityB);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'OIDGROUP = :oidgroup'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereOidgroupEquals(String oidgroup) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE OIDGROUP = ? ORDER BY OIDGROUP", this,oidgroup);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'OGFLAG = :ogflag'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereOgflagEquals(String ogflag) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE OGFLAG = ? ORDER BY OGFLAG", this,ogflag);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S1 = :s1'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereS1Equals(long s1) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE S1 = ? ORDER BY S1", this,s1);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S2 = :s2'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereS2Equals(long s2) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE S2 = ? ORDER BY S2", this,s2);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S3 = :s3'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereS3Equals(long s3) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE S3 = ? ORDER BY S3", this,s3);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S4 = :s4'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereS4Equals(long s4) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE S4 = ? ORDER BY S4", this,s4);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S5 = :s5'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereS5Equals(long s5) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE S5 = ? ORDER BY S5", this,s5);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S6 = :s6'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereS6Equals(long s6) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE S6 = ? ORDER BY S6", this,s6);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S7 = :s7'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereS7Equals(long s7) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE S7 = ? ORDER BY S7", this,s7);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E1 = :e1'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereE1Equals(long e1) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE E1 = ? ORDER BY E1", this,e1);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E2 = :e2'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereE2Equals(long e2) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE E2 = ? ORDER BY E2", this,e2);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E3 = :e3'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereE3Equals(long e3) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE E3 = ? ORDER BY E3", this,e3);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E4 = :e4'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereE4Equals(long e4) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE E4 = ? ORDER BY E4", this,e4);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E5 = :e5'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereE5Equals(long e5) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE E5 = ? ORDER BY E5", this,e5);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E6 = :e6'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereE6Equals(long e6) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE E6 = ? ORDER BY E6", this,e6);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E7 = :e7'.
	 */
	@Transactional
	public List<TPolicyDefault> findWhereE7Equals(long e7) throws TPolicyDefaultDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, START_TIME, END_TIME, VALUE_1, SEVERITY_1, FILTER_A, VALUE_2, SEVERITY_2, FILTER_B, SEVERITY_A, SEVERITY_B, OIDGROUP, OGFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 FROM " + getTableName() + " WHERE E7 = ? ORDER BY E7", this,e7);
		}
		catch (Exception e) {
			throw new TPolicyDefaultDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_POLICY_DEFAULT table that matches the specified primary-key value.
	 */
	public TPolicyDefault findByPrimaryKey(TPolicyDefaultPk pk) throws TPolicyDefaultDaoException
	{
		return findByPrimaryKey( pk.getModid(), pk.getEveid() );
	}

}
