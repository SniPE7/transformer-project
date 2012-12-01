package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TEventOidInitDao;
import com.ibm.ncs.model.dto.TEventOidInit;
import com.ibm.ncs.model.dto.TPolicyDetailsPk;
import com.ibm.ncs.model.exceptions.TEventOidInitDaoException;
import com.ibm.ncs.model.exceptions.TPolicyDetailsDaoException;

import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TEventOidInitDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TEventOidInit>, TEventOidInitDao
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
	public void insert(TEventOidInit dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( MRID, DTID, MODID, EVEID, OIDGROUPNAME, OID, UNIT, DESCRIPTION ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )",dto.getMrid(),dto.getDtid(),dto.getModid(),dto.getEveid(),dto.getOidgroupname(),dto.getOid(),dto.getUnit(),dto.getDescription());
	}

	
	/**
	 * Method 'update'
	 * 
	 * @param dto
	 */
	@Transactional
	public void update(TEventOidInit dto)
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET MRID=?, MODID=?, OIDGROUPNAME=?, OID=?, UNIT=?, DESCRIPTION=?  where  EVEID=? and DTID=?",dto.getMrid(),dto.getModid(),dto.getOidgroupname(),dto.getOid(),dto.getUnit(),dto.getDescription(),dto.getEveid(),dto.getDtid());
	}
	
	/** 
	 * Deletes a single row in the T_EVENT_OID_INIT table.
	 */
	@Transactional
	public void delete(long eveid, long dtid) throws TEventOidInitDaoException
	{
		try {
			 jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE EVEID=? and DTID=? ",eveid, dtid);
		}
		catch (Exception e) {
			throw new TEventOidInitDaoException("Query failed", e);
		}
	}

	
	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TEventOidInit
	 */
	public TEventOidInit mapRow(ResultSet rs, int row) throws SQLException
	{
		TEventOidInit dto = new TEventOidInit();
		dto.setMrid( rs.getLong( 1 ) );
		dto.setDtid( rs.getLong( 2 ) );
		dto.setModid( rs.getLong( 3 ) );
		dto.setEveid( rs.getLong( 4 ) );
		dto.setOidgroupname( rs.getString( 5 ) );
		dto.setOid( rs.getString( 6 ) );
		dto.setUnit( rs.getString( 7 ) );
		dto.setDescription( rs.getString( 8 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_EVENT_OID_INIT";
	}

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria ''.
	 */
	@Transactional
	public List<TEventOidInit> findAll() throws TEventOidInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, MODID, EVEID, OIDGROUPNAME, OID, UNIT, DESCRIPTION FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new TEventOidInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'MRID = :mrid'.
	 */
	@Transactional
	public List<TEventOidInit> findWhereMridEquals(long mrid) throws TEventOidInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, MODID, EVEID, OIDGROUPNAME, OID, UNIT, DESCRIPTION FROM " + getTableName() + " WHERE MRID = ? ORDER BY MRID", this,mrid);
		}
		catch (Exception e) {
			throw new TEventOidInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'DTID = :dtid'.
	 */
	@Transactional
	public List<TEventOidInit> findWhereDtidEquals(long dtid) throws TEventOidInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, MODID, EVEID, OIDGROUPNAME, OID, UNIT, DESCRIPTION FROM " + getTableName() + " WHERE DTID = ? ORDER BY DTID", this,dtid);
		}
		catch (Exception e) {
			throw new TEventOidInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'MODID = :modid'.
	 */
	@Transactional
	public List<TEventOidInit> findWhereModidEquals(long modid) throws TEventOidInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, MODID, EVEID, OIDGROUPNAME, OID, UNIT, DESCRIPTION FROM " + getTableName() + " WHERE MODID = ? ORDER BY MODID", this,modid);
		}
		catch (Exception e) {
			throw new TEventOidInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'EVEID = :eveid'.
	 */
	@Transactional
	public List<TEventOidInit> findWhereEveidEquals(long eveid) throws TEventOidInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, MODID, EVEID, OIDGROUPNAME, OID, UNIT, DESCRIPTION FROM " + getTableName() + " WHERE EVEID = ? ORDER BY EVEID", this,eveid);
		}
		catch (Exception e) {
			throw new TEventOidInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'OIDGROUPNAME = :oidgroupname'.
	 */
	@Transactional
	public List<TEventOidInit> findWhereOidgroupnameEquals(String oidgroupname) throws TEventOidInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, MODID, EVEID, OIDGROUPNAME, OID, UNIT, DESCRIPTION FROM " + getTableName() + " WHERE OIDGROUPNAME = ? ORDER BY OIDGROUPNAME", this,oidgroupname);
		}
		catch (Exception e) {
			throw new TEventOidInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'OID = :oid'.
	 */
	@Transactional
	public List<TEventOidInit> findWhereOidEquals(String oid) throws TEventOidInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, MODID, EVEID, OIDGROUPNAME, OID, UNIT, DESCRIPTION FROM " + getTableName() + " WHERE OID = ? ORDER BY OID", this,oid);
		}
		catch (Exception e) {
			throw new TEventOidInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'UNIT = :unit'.
	 */
	@Transactional
	public List<TEventOidInit> findWhereUnitEquals(String unit) throws TEventOidInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, MODID, EVEID, OIDGROUPNAME, OID, UNIT, DESCRIPTION FROM " + getTableName() + " WHERE UNIT = ? ORDER BY UNIT", this,unit);
		}
		catch (Exception e) {
			throw new TEventOidInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TEventOidInit> findWhereDescriptionEquals(String description) throws TEventOidInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, MODID, EVEID, OIDGROUPNAME, OID, UNIT, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TEventOidInitDaoException("Query failed", e);
		}
		
	}
	
	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TEventOidInit> findWhereDtidNEveidEquals(long dtid, long eveid) throws TEventOidInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, MODID, EVEID, OIDGROUPNAME, OID, UNIT, DESCRIPTION FROM " + getTableName() + " WHERE dtid = ? and eveid = ?", this,dtid, eveid);
		}
		catch (Exception e) {
			throw new TEventOidInitDaoException("Query failed", e);
		}
		
	}
	
	/** 
	 * Returns all rows from the T_EVENT_OID_INIT table that match the criteria 'dtid, eveid, oidgroupname'.
	 */
	@Transactional
	public List<TEventOidInit> findWhereFKsEquals(long dtid, long eveid, String oidGrpName) throws TEventOidInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, MODID, EVEID, OIDGROUPNAME, OID, UNIT, DESCRIPTION FROM " + getTableName() + " WHERE dtid = ? and eveid = ? and OIDGROUPNAME = ?", this,dtid, eveid, oidGrpName);
		}
		catch (Exception e) {
			throw new TEventOidInitDaoException("Query failed", e);
		}
		
	}
	

}
