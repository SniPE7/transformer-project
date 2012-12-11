package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInitPk;
import com.ibm.ncs.model.exceptions.TManufacturerInfoInitDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TManufacturerInfoInitDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TManufacturerInfoInit>, TManufacturerInfoInitDao
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
	 * @return TManufacturerInfoInitPk
	 */
	@Transactional
	public TManufacturerInfoInitPk insert(TManufacturerInfoInit dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( MRID, MRNAME, OBJECTID, DESCRIPTION ) VALUES ( ?, ?, ?, ? )",dto.getMrid(),dto.getMrname(),dto.getObjectid(),dto.getDescription());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_MANUFACTURER_INFO_INIT table.
	 */
	@Transactional
	public void update(TManufacturerInfoInitPk pk, TManufacturerInfoInit dto) throws TManufacturerInfoInitDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET MRID = ?, MRNAME = ?, OBJECTID = ?, DESCRIPTION = ? WHERE MRID = ?",dto.getMrid(),dto.getMrname(),dto.getObjectid(),dto.getDescription(),pk.getMrid());
	}

	/** 
	 * Deletes a single row in the T_MANUFACTURER_INFO_INIT table.
	 */
	@Transactional
	public void delete(TManufacturerInfoInitPk pk) throws TManufacturerInfoInitDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE MRID = ?",pk.getMrid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TManufacturerInfoInit
	 */
	public TManufacturerInfoInit mapRow(ResultSet rs, int row) throws SQLException
	{
		TManufacturerInfoInit dto = new TManufacturerInfoInit();
		dto.setMrid( rs.getLong( 1 ) );
		dto.setMrname( rs.getString( 2 ) );
		dto.setObjectid( rs.getString( 3 ) );
		dto.setDescription( rs.getString( 4 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_MANUFACTURER_INFO_INIT";
	}

	/** 
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the criteria 'MRID = :mrid'.
	 */
	@Transactional
	public TManufacturerInfoInit findByPrimaryKey(long mrid) throws TManufacturerInfoInitDaoException
	{
		try {
			List<TManufacturerInfoInit> list = jdbcTemplate.query("SELECT MRID, MRNAME, OBJECTID, DESCRIPTION FROM " + getTableName() + " WHERE MRID = ?", this,mrid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TManufacturerInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the criteria ''.
	 */
	@Transactional
	public List<TManufacturerInfoInit> findAll() throws TManufacturerInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, MRNAME, OBJECTID, DESCRIPTION FROM " + getTableName() + " ORDER BY UPPER(MRNAME)", this);
		}
		catch (Exception e) {
			throw new TManufacturerInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the criteria 'MRID = :mrid'.
	 */
	@Transactional
	public List<TManufacturerInfoInit> findWhereMridEquals(long mrid) throws TManufacturerInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, MRNAME, OBJECTID, DESCRIPTION FROM " + getTableName() + " WHERE MRID = ? ORDER BY MRID", this,mrid);
		}
		catch (Exception e) {
			throw new TManufacturerInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the criteria 'MRNAME = :mrname'.
	 */
	@Transactional
	public List<TManufacturerInfoInit> findWhereMrnameEquals(String mrname) throws TManufacturerInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, MRNAME, OBJECTID, DESCRIPTION FROM " + getTableName() + " WHERE MRNAME = ? ORDER BY MRNAME", this,mrname);
		}
		catch (Exception e) {
			throw new TManufacturerInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the criteria 'OBJECTID = :objectid'.
	 */
	@Transactional
	public List<TManufacturerInfoInit> findWhereObjectidEquals(String objectid) throws TManufacturerInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, MRNAME, OBJECTID, DESCRIPTION FROM " + getTableName() + " WHERE OBJECTID = ? ORDER BY OBJECTID", this,objectid);
		}
		catch (Exception e) {
			throw new TManufacturerInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MANUFACTURER_INFO_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TManufacturerInfoInit> findWhereDescriptionEquals(String description) throws TManufacturerInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, MRNAME, OBJECTID, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TManufacturerInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_MANUFACTURER_INFO_INIT table that matches the specified primary-key value.
	 */
	public TManufacturerInfoInit findByPrimaryKey(TManufacturerInfoInitPk pk) throws TManufacturerInfoInitDaoException
	{
		return findByPrimaryKey( pk.getMrid() );
	}

}
