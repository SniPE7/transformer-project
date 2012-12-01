package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TDevpolMapDao;
import com.ibm.ncs.model.dto.TDevpolMap;
import com.ibm.ncs.model.dto.TDevpolMapPk;
import com.ibm.ncs.model.exceptions.TDevpolMapDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TDevpolMapDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TDevpolMap>, TDevpolMapDao
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
	 * @return TDevpolMapPk
	 */
	@Transactional
	public TDevpolMapPk insert(TDevpolMap dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( DEVID, MPID, PPID ) VALUES ( ?, ?, ? )",dto.getDevid(),dto.isMpidNull()?null:dto.getMpid(),dto.isPpidNull()?null:dto.getPpid());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_DEVPOL_MAP table.
	 */
	@Transactional
	public void update(TDevpolMapPk pk, TDevpolMap dto) throws TDevpolMapDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET DEVID = ?, MPID = ?, PPID = ? WHERE DEVID = ?",dto.getDevid(),dto.getMpid(),dto.getPpid(),pk.getDevid());
	}

	/** 
	 * Deletes a single row in the T_DEVPOL_MAP table.
	 */
	@Transactional
	public void delete(TDevpolMapPk pk) throws TDevpolMapDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE DEVID = ?",pk.getDevid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TDevpolMap
	 */
	public TDevpolMap mapRow(ResultSet rs, int row) throws SQLException
	{
		TDevpolMap dto = new TDevpolMap();
		dto.setDevid( rs.getLong( 1 ) );
		dto.setMpid( rs.getLong( 2 ) );
		if (rs.wasNull()) {
			dto.setMpidNull( true );
		}
		
		dto.setPpid( rs.getLong( 3 ) );
		if (rs.wasNull()) {
			dto.setPpidNull( true );
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
		return "T_DEVPOL_MAP";
	}

	/** 
	 * Returns all rows from the T_DEVPOL_MAP table that match the criteria 'DEVID = :devid'.
	 */
	@Transactional
	public TDevpolMap findByPrimaryKey(long devid) throws TDevpolMapDaoException
	{
		try {
			List<TDevpolMap> list = jdbcTemplate.query("SELECT DEVID, MPID, PPID FROM " + getTableName() + " WHERE DEVID = ?", this,devid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TDevpolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVPOL_MAP table that match the criteria ''.
	 */
	@Transactional
	public List<TDevpolMap> findAll() throws TDevpolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, MPID, PPID FROM " + getTableName() + " ORDER BY DEVID", this);
		}
		catch (Exception e) {
			throw new TDevpolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVPOL_MAP table that match the criteria 'DEVID = :devid'.
	 */
	@Transactional
	public List<TDevpolMap> findWhereDevidEquals(long devid) throws TDevpolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, MPID, PPID FROM " + getTableName() + " WHERE DEVID = ? ORDER BY DEVID", this,devid);
		}
		catch (Exception e) {
			throw new TDevpolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVPOL_MAP table that match the criteria 'MPID = :mpid'.
	 */
	@Transactional
	public List<TDevpolMap> findWhereMpidEquals(long mpid) throws TDevpolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, MPID, PPID FROM " + getTableName() + " WHERE MPID = ? ORDER BY MPID", this,mpid);
		}
		catch (Exception e) {
			throw new TDevpolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVPOL_MAP table that match the criteria 'PPID = :ppid'.
	 */
	@Transactional
	public List<TDevpolMap> findWherePpidEquals(long ppid) throws TDevpolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, MPID, PPID FROM " + getTableName() + " WHERE PPID = ? ORDER BY PPID", this,ppid);
		}
		catch (Exception e) {
			throw new TDevpolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_DEVPOL_MAP table that matches the specified primary-key value.
	 */
	public TDevpolMap findByPrimaryKey(TDevpolMapPk pk) throws TDevpolMapDaoException
	{
		return findByPrimaryKey( pk.getDevid() );
	}

	/** 
	 * Deletes  rows in the T_DEVPOL_MAP table BUT not exist in the table T_DEVICE_INFO.
	 */
	@Transactional
	public void removeNoUseData() throws TDevpolMapDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE DEVID not in (select devid from T_DEVICE_INFO)");
	}
	
}
