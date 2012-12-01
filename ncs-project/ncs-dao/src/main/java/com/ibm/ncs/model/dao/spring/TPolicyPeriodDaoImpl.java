package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.dto.TPolicyPeriodPk;
import com.ibm.ncs.model.exceptions.TPolicyPeriodDaoException;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TPolicyPeriodDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TPolicyPeriod>, TPolicyPeriodDao
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
	 * @return TPolicyPeriodPk
	 */
	@Transactional
	public TPolicyPeriodPk insert(TPolicyPeriod dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getPpid(),dto.getPpname(),dto.getStartTime(),dto.getEndTime(),dto.getDescription(),dto.getWorkday(),dto.getEnabled(),dto.getDefaultflag(),dto.isS1Null()?null:dto.getS1(),dto.isS2Null()?null:dto.getS2(),dto.isS3Null()?null:dto.getS3(),dto.isS4Null()?null:dto.getS4(),dto.isS5Null()?null:dto.getS5(),dto.isS6Null()?null:dto.getS6(),dto.isS7Null()?null:dto.getS7(),dto.isE1Null()?null:dto.getE1(),dto.isE2Null()?null:dto.getE2(),dto.isE3Null()?null:dto.getE3(),dto.isE4Null()?null:dto.getE4(),dto.isE5Null()?null:dto.getE5(),dto.isE6Null()?null:dto.getE6(),dto.isE7Null()?null:dto.getE7(),dto.getBtime(),dto.getEtime());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_POLICY_PERIOD table.
	 */
	@Transactional
	public void update(TPolicyPeriodPk pk, TPolicyPeriod dto) throws TPolicyPeriodDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET  PPNAME = ?, START_TIME = ?, END_TIME = ?, DESCRIPTION = ?, WORKDAY = ?, ENABLED = ?, DEFAULTFLAG = ?, S1 = ?, S2 = ?, S3 = ?, S4 = ?, S5 = ?, S6 = ?, S7 = ?, E1 = ?, E2 = ?, E3 = ?, E4 = ?, E5 = ?, E6 = ?, E7 = ?, BTIME = ?, ETIME = ? WHERE PPID = ?",dto.getPpname(),dto.getStartTime(),dto.getEndTime(),dto.getDescription(),dto.getWorkday(),dto.getEnabled(),dto.getDefaultflag(),dto.getS1(),dto.getS2(),dto.getS3(),dto.getS4(),dto.getS5(),dto.getS6(),dto.getS7(),dto.getE1(),dto.getE2(),dto.getE3(),dto.getE4(),dto.getE5(),dto.getE6(),dto.getE7(),dto.getBtime(),dto.getEtime(),pk.getPpid());
	}

	/** 
	 * Deletes a single row in the T_POLICY_PERIOD table.
	 */
	@Transactional
	public void delete(TPolicyPeriodPk pk) throws TPolicyPeriodDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PPID = ?",pk.getPpid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TPolicyPeriod
	 */
	public TPolicyPeriod mapRow(ResultSet rs, int row) throws SQLException
	{
		TPolicyPeriod dto = new TPolicyPeriod();
		dto.setPpid( rs.getLong( 1 ) );
		dto.setPpname( rs.getString( 2 ) );
		dto.setStartTime( rs.getTimestamp(3 ) );
		dto.setEndTime( rs.getTimestamp(4 ) );
		dto.setDescription( rs.getString( 5 ) );
		dto.setWorkday( rs.getString( 6 ) );
		dto.setEnabled( rs.getString( 7 ) );
		dto.setDefaultflag( rs.getString( 8 ) );
		dto.setS1( rs.getLong( 9 ) );
		if (rs.wasNull()) {
			dto.setS1Null( true );
		}
		
		dto.setS2( rs.getLong( 10 ) );
		if (rs.wasNull()) {
			dto.setS2Null( true );
		}
		
		dto.setS3( rs.getLong( 11 ) );
		if (rs.wasNull()) {
			dto.setS3Null( true );
		}
		
		dto.setS4( rs.getLong( 12 ) );
		if (rs.wasNull()) {
			dto.setS4Null( true );
		}
		
		dto.setS5( rs.getLong( 13 ) );
		if (rs.wasNull()) {
			dto.setS5Null( true );
		}
		
		dto.setS6( rs.getLong( 14 ) );
		if (rs.wasNull()) {
			dto.setS6Null( true );
		}
		
		dto.setS7( rs.getLong( 15 ) );
		if (rs.wasNull()) {
			dto.setS7Null( true );
		}
		
		dto.setE1( rs.getLong( 16 ) );
		if (rs.wasNull()) {
			dto.setE1Null( true );
		}
		
		dto.setE2( rs.getLong( 17 ) );
		if (rs.wasNull()) {
			dto.setE2Null( true );
		}
		
		dto.setE3( rs.getLong( 18 ) );
		if (rs.wasNull()) {
			dto.setE3Null( true );
		}
		
		dto.setE4( rs.getLong( 19 ) );
		if (rs.wasNull()) {
			dto.setE4Null( true );
		}
		
		dto.setE5( rs.getLong( 20 ) );
		if (rs.wasNull()) {
			dto.setE5Null( true );
		}
		
		dto.setE6( rs.getLong( 21 ) );
		if (rs.wasNull()) {
			dto.setE6Null( true );
		}
		
		dto.setE7( rs.getLong( 22 ) );
		if (rs.wasNull()) {
			dto.setE7Null( true );
		}
		
		dto.setBtime( rs.getString( 23 ) );
		dto.setEtime( rs.getString( 24 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_POLICY_PERIOD";
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'PPID = :ppid'.
	 */
	@Transactional
	public TPolicyPeriod findByPrimaryKey(long ppid) throws TPolicyPeriodDaoException
	{
		try {
			List<TPolicyPeriod> list = jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE PPID = ?", this,ppid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria ''.
	 */
	@Transactional
	public List<TPolicyPeriod> findAll() throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " ORDER BY PPID", this);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'PPID = :ppid'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWherePpidEquals(long ppid) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE PPID = ? ORDER BY PPID", this,ppid);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'PPNAME = :ppname'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWherePpnameEquals(String ppname) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE PPNAME = ? ORDER BY PPNAME", this,ppname);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'START_TIME = :startTime'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereStartTimeEquals(Date startTime) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE START_TIME = ? ORDER BY START_TIME", this,startTime);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'END_TIME = :endTime'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereEndTimeEquals(Date endTime) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE END_TIME = ? ORDER BY END_TIME", this,endTime);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereDescriptionEquals(String description) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'WORKDAY = :workday'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereWorkdayEquals(String workday) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE WORKDAY = ? ORDER BY WORKDAY", this,workday);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'ENABLED = :enabled'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereEnabledEquals(String enabled) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE ENABLED = ? ORDER BY ENABLED", this,enabled);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'DEFAULTFLAG = :defaultflag'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereDefaultflagEquals(String defaultflag) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE DEFAULTFLAG = ? ORDER BY DEFAULTFLAG", this,defaultflag);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S1 = :s1'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereS1Equals(long s1) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE S1 = ? ORDER BY S1", this,s1);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S2 = :s2'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereS2Equals(long s2) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE S2 = ? ORDER BY S2", this,s2);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S3 = :s3'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereS3Equals(long s3) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE S3 = ? ORDER BY S3", this,s3);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S4 = :s4'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereS4Equals(long s4) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE S4 = ? ORDER BY S4", this,s4);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S5 = :s5'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereS5Equals(long s5) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE S5 = ? ORDER BY S5", this,s5);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S6 = :s6'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereS6Equals(long s6) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE S6 = ? ORDER BY S6", this,s6);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S7 = :s7'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereS7Equals(long s7) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE S7 = ? ORDER BY S7", this,s7);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E1 = :e1'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereE1Equals(long e1) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE E1 = ? ORDER BY E1", this,e1);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E2 = :e2'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereE2Equals(long e2) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE E2 = ? ORDER BY E2", this,e2);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E3 = :e3'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereE3Equals(long e3) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE E3 = ? ORDER BY E3", this,e3);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E4 = :e4'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereE4Equals(long e4) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE E4 = ? ORDER BY E4", this,e4);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E5 = :e5'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereE5Equals(long e5) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE E5 = ? ORDER BY E5", this,e5);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E6 = :e6'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereE6Equals(long e6) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE E6 = ? ORDER BY E6", this,e6);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E7 = :e7'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereE7Equals(long e7) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE E7 = ? ORDER BY E7", this,e7);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'BTIME = :btime'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereBtimeEquals(String btime) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE BTIME = ? ORDER BY BTIME", this,btime);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'ETIME = :etime'.
	 */
	@Transactional
	public List<TPolicyPeriod> findWhereEtimeEquals(String etime) throws TPolicyPeriodDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7, BTIME, ETIME FROM " + getTableName() + " WHERE ETIME = ? ORDER BY ETIME", this,etime);
		}
		catch (Exception e) {
			throw new TPolicyPeriodDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_POLICY_PERIOD table that matches the specified primary-key value.
	 */
	public TPolicyPeriod findByPrimaryKey(TPolicyPeriodPk pk) throws TPolicyPeriodDaoException
	{
		return findByPrimaryKey( pk.getPpid() );
	}

}
