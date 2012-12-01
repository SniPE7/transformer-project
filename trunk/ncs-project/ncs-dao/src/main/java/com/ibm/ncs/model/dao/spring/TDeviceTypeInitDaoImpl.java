package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TDeviceTypeInitPk;
import com.ibm.ncs.model.exceptions.TDeviceTypeInitDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TDeviceTypeInitDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TDeviceTypeInit>, TDeviceTypeInitDao
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
	 * @return TDeviceTypeInitPk
	 */
	@Transactional
	public TDeviceTypeInitPk insert(TDeviceTypeInit dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )",dto.getMrid(),dto.getDtid(),dto.isCategoryNull()?null:dto.getCategory(),dto.getSubcategory(),dto.getModel(),dto.getObjectid(),dto.getLogo(),dto.getDescription());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_DEVICE_TYPE_INIT table.
	 */
	@Transactional
	public void update(TDeviceTypeInitPk pk, TDeviceTypeInit dto) throws TDeviceTypeInitDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET MRID = ?, DTID = ?, CATEGORY = ?, SUBCATEGORY = ?, MODEL = ?, OBJECTID = ?, LOGO = ?, DESCRIPTION = ? WHERE DTID = ?",dto.getMrid(),dto.getDtid(),dto.getCategory(),dto.getSubcategory(),dto.getModel(),dto.getObjectid(),dto.getLogo(),dto.getDescription(),pk.getDtid());
	}

	/** 
	 * Deletes a single row in the T_DEVICE_TYPE_INIT table.
	 */
	@Transactional
	public void delete(TDeviceTypeInitPk pk) throws TDeviceTypeInitDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE DTID = ?",pk.getDtid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TDeviceTypeInit
	 */
	public TDeviceTypeInit mapRow(ResultSet rs, int row) throws SQLException
	{
		TDeviceTypeInit dto = new TDeviceTypeInit();
		dto.setMrid( rs.getLong( 1 ) );
		dto.setDtid( rs.getLong( 2 ) );
		dto.setCategory( rs.getLong( 3 ) );
		if (rs.wasNull()) {
			dto.setCategoryNull( true );
		}
		
		dto.setSubcategory( rs.getString( 4 ) );
		dto.setModel( rs.getString( 5 ) );
		dto.setObjectid( rs.getString( 6 ) );
		dto.setLogo( rs.getString( 7 ) );
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
		return "T_DEVICE_TYPE_INIT";
	}

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'DTID = :dtid'.
	 */
	@Transactional
	public TDeviceTypeInit findByPrimaryKey(long dtid) throws TDeviceTypeInitDaoException
	{
		try {
			List<TDeviceTypeInit> list = jdbcTemplate.query("SELECT MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE DTID = ?", this,dtid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TDeviceTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria ''.
	 */
	@Transactional
	public List<TDeviceTypeInit> findAll() throws TDeviceTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " ORDER BY DTID", this);
		}
		catch (Exception e) {
			throw new TDeviceTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'MRID = :mrid'.
	 */
	@Transactional
	public List<TDeviceTypeInit> findWhereMridEquals(long mrid) throws TDeviceTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE MRID = ? ORDER BY MRID", this,mrid);
		}
		catch (Exception e) {
			throw new TDeviceTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'DTID = :dtid'.
	 */
	@Transactional
	public List<TDeviceTypeInit> findWhereDtidEquals(long dtid) throws TDeviceTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE DTID = ? ORDER BY DTID", this,dtid);
		}
		catch (Exception e) {
			throw new TDeviceTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'CATEGORY = :category'.
	 */
	@Transactional
	public List<TDeviceTypeInit> findWhereCategoryEquals(long category) throws TDeviceTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE CATEGORY = ? ORDER BY CATEGORY", this,category);
		}
		catch (Exception e) {
			throw new TDeviceTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'SUBCATEGORY = :subcategory'.
	 */
	@Transactional
	public List<TDeviceTypeInit> findWhereSubcategoryEquals(String subcategory) throws TDeviceTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE SUBCATEGORY = ? ORDER BY SUBCATEGORY", this,subcategory);
		}
		catch (Exception e) {
			throw new TDeviceTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'MODEL = :model'.
	 */
	@Transactional
	public List<TDeviceTypeInit> findWhereModelEquals(String model) throws TDeviceTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE MODEL = ? ORDER BY MODEL", this,model);
		}
		catch (Exception e) {
			throw new TDeviceTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'OBJECTID = :objectid'.
	 */
	@Transactional
	public List<TDeviceTypeInit> findWhereObjectidEquals(String objectid) throws TDeviceTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE OBJECTID = ? ORDER BY OBJECTID", this,objectid);
		}
		catch (Exception e) {
			throw new TDeviceTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'LOGO = :logo'.
	 */
	@Transactional
	public List<TDeviceTypeInit> findWhereLogoEquals(String logo) throws TDeviceTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE LOGO = ? ORDER BY LOGO", this,logo);
		}
		catch (Exception e) {
			throw new TDeviceTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_TYPE_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TDeviceTypeInit> findWhereDescriptionEquals(String description) throws TDeviceTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TDeviceTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_DEVICE_TYPE_INIT table that matches the specified primary-key value.
	 */
	public TDeviceTypeInit findByPrimaryKey(TDeviceTypeInitPk pk) throws TDeviceTypeInitDaoException
	{
		return findByPrimaryKey( pk.getDtid() );
	}

	public TDeviceTypeInit findByMul(long mrid, long category, String subcate,
			String model) throws TDeviceTypeInitDaoException {
		try {
			/*String sql = "SELECT MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE MRID = "+mrid+" AND CATEGORY = "+category+" AND SUBCATEGORY = "+"'"+subcate+"'"+" AND MODEL ="+"'"+model+"'";
			System.out.println("sql is : "+sql);*/
			List<TDeviceTypeInit> list = jdbcTemplate.query("SELECT MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE MRID = ? AND CATEGORY = ? AND SUBCATEGORY = ? AND MODEL = ?", this,mrid,category,subcate,model);
			System.out.println("list size is : "+list.size());
		    return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TDeviceTypeInitDaoException("Query failed", e);
		}
	}

}
