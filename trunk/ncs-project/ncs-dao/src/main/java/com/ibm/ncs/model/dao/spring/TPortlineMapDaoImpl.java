package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TPortlineMapDao;
import com.ibm.ncs.model.dto.TPortlineMap;
import com.ibm.ncs.model.dto.TPortlineMapPk;
import com.ibm.ncs.model.exceptions.TPortlineMapDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TPortlineMapDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TPortlineMap>, TPortlineMapDao
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
	 * @return TPortlineMapPk
	 */
	@Transactional
	public TPortlineMapPk insert(TPortlineMap dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( PTID, LEID, SRID, ADMIN, PHONE, SIDE ) VALUES ( ?, ?, ?, ?, ?, ? )",dto.getPtid(),dto.isLeidNull()?null:dto.getLeid(),dto.isSridNull()?null:dto.getSrid(),dto.getAdmin(),dto.getPhone(),dto.getSide());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_PORTLINE_MAP table.
	 */
	@Transactional
	public void update(TPortlineMapPk pk, TPortlineMap dto) throws TPortlineMapDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET PTID = ?, LEID = ?, SRID = ?, ADMIN = ?, PHONE = ?, SIDE = ? WHERE PTID = ?",dto.getPtid(),dto.getLeid(),dto.getSrid(),dto.getAdmin(),dto.getPhone(),dto.getSide(),pk.getPtid());
	}

	/** 
	 * Deletes a single row in the T_PORTLINE_MAP table.
	 */
	@Transactional
	public void delete(TPortlineMapPk pk) throws TPortlineMapDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PTID = ?",pk.getPtid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TPortlineMap
	 */
	public TPortlineMap mapRow(ResultSet rs, int row) throws SQLException
	{
		TPortlineMap dto = new TPortlineMap();
		dto.setPtid( rs.getLong( 1 ) );
		dto.setLeid( rs.getLong( 2 ) );
		if (rs.wasNull()) {
			dto.setLeidNull( true );
		}
		
		dto.setSrid( rs.getLong( 3 ) );
		if (rs.wasNull()) {
			dto.setSridNull( true );
		}
		
		dto.setAdmin( rs.getString( 4 ) );
		dto.setPhone( rs.getString( 5 ) );
		dto.setSide( rs.getString( 6 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_PORTLINE_MAP";
	}

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'PTID = :ptid'.
	 */
	@Transactional
	public TPortlineMap findByPrimaryKey(long ptid) throws TPortlineMapDaoException
	{
		try {
			List<TPortlineMap> list = jdbcTemplate.query("SELECT PTID, LEID, SRID, ADMIN, PHONE, SIDE FROM " + getTableName() + " WHERE PTID = ?", this,ptid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TPortlineMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria ''.
	 */
	@Transactional
	public List<TPortlineMap> findAll() throws TPortlineMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, LEID, SRID, ADMIN, PHONE, SIDE FROM " + getTableName() + " ORDER BY PTID", this);
		}
		catch (Exception e) {
			throw new TPortlineMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'PTID = :ptid'.
	 */
	@Transactional
	public List<TPortlineMap> findWherePtidEquals(long ptid) throws TPortlineMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, LEID, SRID, ADMIN, PHONE, SIDE FROM " + getTableName() + " WHERE PTID = ? ORDER BY PTID", this,ptid);
		}
		catch (Exception e) {
			throw new TPortlineMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'LEID = :leid'.
	 */
	@Transactional
	public List<TPortlineMap> findWhereLeidEquals(long leid) throws TPortlineMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, LEID, SRID, ADMIN, PHONE, SIDE FROM " + getTableName() + " WHERE LEID = ? ORDER BY LEID", this,leid);
		}
		catch (Exception e) {
			throw new TPortlineMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'SRID = :srid'.
	 */
	@Transactional
	public List<TPortlineMap> findWhereSridEquals(long srid) throws TPortlineMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, LEID, SRID, ADMIN, PHONE, SIDE FROM " + getTableName() + " WHERE SRID = ? ORDER BY SRID", this,srid);
		}
		catch (Exception e) {
			throw new TPortlineMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'ADMIN = :admin'.
	 */
	@Transactional
	public List<TPortlineMap> findWhereAdminEquals(String admin) throws TPortlineMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, LEID, SRID, ADMIN, PHONE, SIDE FROM " + getTableName() + " WHERE ADMIN = ? ORDER BY ADMIN", this,admin);
		}
		catch (Exception e) {
			throw new TPortlineMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'PHONE = :phone'.
	 */
	@Transactional
	public List<TPortlineMap> findWherePhoneEquals(String phone) throws TPortlineMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, LEID, SRID, ADMIN, PHONE, SIDE FROM " + getTableName() + " WHERE PHONE = ? ORDER BY PHONE", this,phone);
		}
		catch (Exception e) {
			throw new TPortlineMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'SIDE = :side'.
	 */
	@Transactional
	public List<TPortlineMap> findWhereSideEquals(String side) throws TPortlineMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, LEID, SRID, ADMIN, PHONE, SIDE FROM " + getTableName() + " WHERE SIDE = ? ORDER BY SIDE", this,side);
		}
		catch (Exception e) {
			throw new TPortlineMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_PORTLINE_MAP table that matches the specified primary-key value.
	 */
	public TPortlineMap findByPrimaryKey(TPortlineMapPk pk) throws TPortlineMapDaoException
	{
		return findByPrimaryKey( pk.getPtid() );
	}

}
