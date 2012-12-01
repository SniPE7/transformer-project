package com.ibm.ncs.model.dao.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.ncs.model.dao.TLinepolMapDao;
import com.ibm.ncs.model.dto.TLinepolMap;
import com.ibm.ncs.model.dto.TLinepolMapPk;
import com.ibm.ncs.model.exceptions.TLinepolMapDaoException;

public class TLinepolMapDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TLinepolMap>, TLinepolMapDao
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
	 * @return TLinepolMapPk
	 */
	@Transactional
	public TLinepolMapPk insert(TLinepolMap dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( PTID, PPID, MCODE, FLAG, MPID ) VALUES ( ?, ?, ?, ?, ? )",dto.getPtid(),dto.isPpidNull()?null:dto.getPpid(),dto.isMcodeNull()?null:dto.getMcode(),dto.getFlag(),dto.isMpidNull()?null:dto.getMpid());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_LINEPOL_MAP table.
	 */
	@Transactional
	public void update(TLinepolMapPk pk, TLinepolMap dto) throws TLinepolMapDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET PTID = ?, PPID = ?, MCODE = ?, FLAG = ?, MPID = ? WHERE PTID = ?",dto.getPtid(),dto.getPpid(),dto.getMcode(),dto.getFlag(),dto.getMpid(),pk.getPtid());
	}

	/** 
	 * Deletes a single row in the T_LINEPOL_MAP table.
	 */
	@Transactional
	public void delete(TLinepolMapPk pk) throws TLinepolMapDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PTID = ?",pk.getPtid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TLinepolMap
	 */
	public TLinepolMap mapRow(ResultSet rs, int row) throws SQLException
	{
		TLinepolMap dto = new TLinepolMap();
		dto.setPtid( rs.getLong( 1 ) );
		dto.setPpid( rs.getLong( 2 ) );
		if (rs.wasNull()) {
			dto.setPpidNull( true );
		}
		
		dto.setMcode( rs.getLong( 3 ) );
		if (rs.wasNull()) {
			dto.setMcodeNull( true );
		}
		
		dto.setFlag( new Integer( rs.getInt(4) ) );
		if (rs.wasNull()) {
			dto.setFlag( null );
		}
		
		dto.setMpid( rs.getLong( 5 ) );
		if (rs.wasNull()) {
			dto.setMpidNull( true );
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
		return "T_LINEPOL_MAP";
	}

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria 'PTID = :ptid'.
	 */
	@Transactional
	public TLinepolMap findByPrimaryKey(long ptid) throws TLinepolMapDaoException
	{
		try {
			List<TLinepolMap> list = jdbcTemplate.query("SELECT PTID, PPID, MCODE, FLAG, MPID FROM " + getTableName() + " WHERE PTID = ?", this,ptid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TLinepolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria ''.
	 */
	@Transactional
	public List<TLinepolMap> findAll() throws TLinepolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, PPID, MCODE, FLAG, MPID FROM " + getTableName() + " ORDER BY PTID", this);
		}
		catch (Exception e) {
			throw new TLinepolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria 'PTID = :ptid'.
	 */
	@Transactional
	public List<TLinepolMap> findWherePtidEquals(long ptid) throws TLinepolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, PPID, MCODE, FLAG, MPID FROM " + getTableName() + " WHERE PTID = ? ORDER BY PTID", this,ptid);
		}
		catch (Exception e) {
			throw new TLinepolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria 'PPID = :ppid'.
	 */
	@Transactional
	public List<TLinepolMap> findWherePpidEquals(long ppid) throws TLinepolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, PPID, MCODE, FLAG, MPID FROM " + getTableName() + " WHERE PPID = ? ORDER BY PPID", this,ppid);
		}
		catch (Exception e) {
			throw new TLinepolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria 'MCODE = :mcode'.
	 */
	@Transactional
	public List<TLinepolMap> findWhereMcodeEquals(long mcode) throws TLinepolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, PPID, MCODE, FLAG, MPID FROM " + getTableName() + " WHERE MCODE = ? ORDER BY MCODE", this,mcode);
		}
		catch (Exception e) {
			throw new TLinepolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria 'FLAG = :flag'.
	 */
	@Transactional
	public List<TLinepolMap> findWhereFlagEquals(Integer flag) throws TLinepolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, PPID, MCODE, FLAG, MPID FROM " + getTableName() + " WHERE FLAG = ? ORDER BY FLAG", this,flag);
		}
		catch (Exception e) {
			throw new TLinepolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria 'MPID = :mpid'.
	 */
	@Transactional
	public List<TLinepolMap> findWhereMpidEquals(long mpid) throws TLinepolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, PPID, MCODE, FLAG, MPID FROM " + getTableName() + " WHERE MPID = ? ORDER BY MPID", this,mpid);
		}
		catch (Exception e) {
			throw new TLinepolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_LINEPOL_MAP table that matches the specified primary-key value.
	 */
	public TLinepolMap findByPrimaryKey(TLinepolMapPk pk) throws TLinepolMapDaoException
	{
		return findByPrimaryKey( pk.getPtid() );
	}

	/** 
	 * Deletes  rows in the T_LINEPOL_MAP table BUT not exist in the table T_PORT_INFO.
	 */
	@Transactional
	public void removeNoUseData() throws TLinepolMapDaoException{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PTID not in (select ptid from T_PORT_INFO)");
	}
}
