package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.PolicySyslogDao;
import com.ibm.ncs.model.dto.PolicySyslog;
import com.ibm.ncs.model.dto.PolicySyslogPk;
import com.ibm.ncs.model.exceptions.PolicySyslogDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class PolicySyslogDaoImpl extends AbstractDAO implements ParameterizedRowMapper<PolicySyslog>, PolicySyslogDao
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
	 * @return PolicySyslogPk
	 */
	@Transactional
	public PolicySyslogPk insert(PolicySyslog dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getSpid(),dto.isMpidNull()?null:dto.getMpid(),dto.getMark(),dto.getManufacture(),dto.isEventtypeNull()?null:dto.getEventtype(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.isSeverity2Null()?null:dto.getSeverity2(),dto.isFilterflag1Null()?null:dto.getFilterflag1(),dto.isFilterflag2Null()?null:dto.getFilterflag2());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the POLICY_SYSLOG table.
	 */
	@Transactional
	public void update(PolicySyslogPk pk, PolicySyslog dto) throws PolicySyslogDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET SPID = ?, MPID = ?,  EVENTTYPE = ?, SEVERITY1 = ?, SEVERITY2 = ?, FILTERFLAG1 = ?, FILTERFLAG2 = ? WHERE SPID = ?",dto.getSpid(),dto.isMpidNull()?null:dto.getMpid(),dto.isEventtypeNull()?null:dto.getEventtype(),dto.isSeverity1Null()?null:dto.getSeverity1(),dto.isSeverity2Null()?null:dto.getSeverity2(),dto.isFilterflag1Null()?null:dto.getFilterflag1(),dto.isFilterflag2Null()?null:dto.getFilterflag2(),pk.getSpid());
	}

	/** 
	 * Deletes a single row in the POLICY_SYSLOG table.
	 */
	@Transactional
	public void delete(PolicySyslogPk pk) throws PolicySyslogDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE SPID = ?",pk.getSpid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return PolicySyslog
	 */
	public PolicySyslog mapRow(ResultSet rs, int row) throws SQLException
	{
		PolicySyslog dto = new PolicySyslog();
		dto.setSpid( rs.getLong( 1 ) );
		dto.setMpid( rs.getLong( 2 ) );
		if (rs.wasNull()) {
			dto.setMpidNull( true );
		}
		
		dto.setMark( rs.getString( 3 ) );
		dto.setManufacture( rs.getString( 4 ) );
		dto.setEventtype( rs.getLong( 5 ) );
		if (rs.wasNull()) {
			dto.setEventtypeNull( true );
		}
		
		dto.setSeverity1( rs.getLong( 6 ) );
		if (rs.wasNull()) {
			dto.setSeverity1Null( true );
		}
		
		dto.setSeverity2( rs.getLong( 7 ) );
		if (rs.wasNull()) {
			dto.setSeverity2Null( true );
		}
		
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
		return "POLICY_SYSLOG";
	}

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'SPID = :spid'.
	 */
	@Transactional
	public PolicySyslog findByPrimaryKey(long spid) throws PolicySyslogDaoException
	{
		try {
			List<PolicySyslog> list = jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE SPID = ?", this,spid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria ''.
	 */
	@Transactional
	public List<PolicySyslog> findAll() throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " ORDER BY SPID", this);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'SPID = :spid'.
	 */
	@Transactional
	public List<PolicySyslog> findWhereSpidEquals(long spid) throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE SPID = ? ORDER BY SPID", this,spid);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'MPID = :mpid'.
	 */
	@Transactional
	public List<PolicySyslog> findWhereMpidEquals(long mpid) throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE MPID = ? ORDER BY MPID", this,mpid);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'MARK = :mark'.
	 */
	@Transactional
	public List<PolicySyslog> findWhereMarkEquals(String mark) throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE MARK = ? ORDER BY MARK", this,mark);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'MANUFACTURE = :manufacture'.
	 */
	@Transactional
	public List<PolicySyslog> findWhereManufactureEquals(String manufacture) throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE MANUFACTURE = ? ORDER BY MANUFACTURE", this,manufacture);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'EVENTTYPE = :eventtype'.
	 */
	@Transactional
	public List<PolicySyslog> findWhereEventtypeEquals(long eventtype) throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE EVENTTYPE = ? ORDER BY EVENTTYPE", this,eventtype);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	@Transactional
	public List<PolicySyslog> findWhereSeverity1Equals(long severity1) throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE SEVERITY1 = ? ORDER BY SEVERITY1", this,severity1);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'SEVERITY2 = :severity2'.
	 */
	@Transactional
	public List<PolicySyslog> findWhereSeverity2Equals(long severity2) throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE SEVERITY2 = ? ORDER BY SEVERITY2", this,severity2);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'FILTERFLAG1 = :filterflag1'.
	 */
	@Transactional
	public List<PolicySyslog> findWhereFilterflag1Equals(long filterflag1) throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE FILTERFLAG1 = ? ORDER BY FILTERFLAG1", this,filterflag1);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'FILTERFLAG2 = :filterflag2'.
	 */
	@Transactional
	public List<PolicySyslog> findWhereFilterflag2Equals(long filterflag2) throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE FILTERFLAG2 = ? ORDER BY FILTERFLAG2", this,filterflag2);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}
	
	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'MARK = :mark'.
	 */
	@Transactional
	public List<PolicySyslog> findWhereMarkAndMpidEquals(String mark, long mpid) throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE MARK = ? and MPID = ? ORDER BY MARK", this,mark,mpid);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}
	
	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'MARK = :mark'.
	 */
	@Transactional
	public List<PolicySyslog> findWhereManufactureAndMpidEquals(String manufacture, long mpid) throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE MANUFACTURE = ? and MPID = ? ORDER BY MARK", this,manufacture,mpid);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}
	

	/** 
	 * Returns the rows from the POLICY_SYSLOG table that matches the specified primary-key value.
	 */
	public PolicySyslog findByPrimaryKey(PolicySyslogPk pk) throws PolicySyslogDaoException
	{
		return findByPrimaryKey( pk.getSpid() );
	}
	
	
	/** 
	 * Returns all rows from the POLICY_SYSLOG table that syslog belongs table T_devpol_map.
	 */
	@Transactional
	public List<PolicySyslog> findDeviceEvents() throws PolicySyslogDaoException
	{
		System.out.println("findDeviceEvents...");
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE  MPID in (select mpid from t_devpol_map)", this);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that  syslog belongs table T_linepol_map.
	 */
	@Transactional
	public List<PolicySyslog> findPortEvents() throws PolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("SELECT SPID, MPID, MARK, MANUFACTURE, EVENTTYPE, SEVERITY1, SEVERITY2, FILTERFLAG1, FILTERFLAG2 FROM " + getTableName() + " WHERE  MPID in (select mpid from t_linepol_map)", this);
		}
		catch (Exception e) {
			throw new PolicySyslogDaoException("Query failed", e);
		}
		
	}
	
}
