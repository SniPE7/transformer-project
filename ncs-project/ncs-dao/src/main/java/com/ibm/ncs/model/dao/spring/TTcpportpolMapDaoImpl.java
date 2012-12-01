package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TTcpportpolMapDao;
import com.ibm.ncs.model.dto.TTcpportpolMap;
import com.ibm.ncs.model.dto.TTcpportpolMapPk;
import com.ibm.ncs.model.exceptions.TTcpportpolMapDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TTcpportpolMapDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TTcpportpolMap>, TTcpportpolMapDao
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
	 * @return TTcpportpolMapPk
	 */
	@Transactional
	public TTcpportpolMapPk insert(TTcpportpolMap dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( HID, MPID, PPID ) VALUES ( ?, ?, ? )",dto.getHid(),dto.isMpidNull()?null:dto.getMpid(),dto.isPpidNull()?null:dto.getPpid());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_TCPPORTPOL_MAP table.
	 */
	@Transactional
	public void update(TTcpportpolMapPk pk, TTcpportpolMap dto) throws TTcpportpolMapDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET HID = ?, MPID = ?, PPID = ? WHERE HID = ?",dto.getHid(),dto.getMpid(),dto.getPpid(),pk.getHid());
	}

	/** 
	 * Deletes a single row in the T_TCPPORTPOL_MAP table.
	 */
	@Transactional
	public void delete(TTcpportpolMapPk pk) throws TTcpportpolMapDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE HID = ?",pk.getHid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TTcpportpolMap
	 */
	public TTcpportpolMap mapRow(ResultSet rs, int row) throws SQLException
	{
		TTcpportpolMap dto = new TTcpportpolMap();
		dto.setHid( rs.getLong( 1 ) );
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
		return "T_TCPPORTPOL_MAP";
	}

	/** 
	 * Returns all rows from the T_TCPPORTPOL_MAP table that match the criteria 'HID = :hid'.
	 */
	@Transactional
	public TTcpportpolMap findByPrimaryKey(long hid) throws TTcpportpolMapDaoException
	{
		try {
			List<TTcpportpolMap> list = jdbcTemplate.query("SELECT HID, MPID, PPID FROM " + getTableName() + " WHERE HID = ?", this,hid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TTcpportpolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_TCPPORTPOL_MAP table that match the criteria ''.
	 */
	@Transactional
	public List<TTcpportpolMap> findAll() throws TTcpportpolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT HID, MPID, PPID FROM " + getTableName() + " ORDER BY HID", this);
		}
		catch (Exception e) {
			throw new TTcpportpolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_TCPPORTPOL_MAP table that match the criteria 'HID = :hid'.
	 */
	@Transactional
	public List<TTcpportpolMap> findWhereHidEquals(long hid) throws TTcpportpolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT HID, MPID, PPID FROM " + getTableName() + " WHERE HID = ? ORDER BY HID", this,hid);
		}
		catch (Exception e) {
			throw new TTcpportpolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_TCPPORTPOL_MAP table that match the criteria 'MPID = :mpid'.
	 */
	@Transactional
	public List<TTcpportpolMap> findWhereMpidEquals(long mpid) throws TTcpportpolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT HID, MPID, PPID FROM " + getTableName() + " WHERE MPID = ? ORDER BY MPID", this,mpid);
		}
		catch (Exception e) {
			throw new TTcpportpolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_TCPPORTPOL_MAP table that match the criteria 'PPID = :ppid'.
	 */
	@Transactional
	public List<TTcpportpolMap> findWherePpidEquals(long ppid) throws TTcpportpolMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT HID, MPID, PPID FROM " + getTableName() + " WHERE PPID = ? ORDER BY PPID", this,ppid);
		}
		catch (Exception e) {
			throw new TTcpportpolMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_TCPPORTPOL_MAP table that matches the specified primary-key value.
	 */
	public TTcpportpolMap findByPrimaryKey(TTcpportpolMapPk pk) throws TTcpportpolMapDaoException
	{
		return findByPrimaryKey( pk.getHid() );
	}

}
