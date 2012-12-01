package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.VMfCateDevtypeDao;
import com.ibm.ncs.model.dto.VMfCateDevtype;
import com.ibm.ncs.model.exceptions.VMfCateDevtypeDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class VMfCateDevtypeDaoImpl extends AbstractDAO implements ParameterizedRowMapper<VMfCateDevtype>, VMfCateDevtypeDao
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
	public void insert(VMfCateDevtype dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getName(),dto.getFlag(),dto.getMrid(),dto.getMrname(),dto.getMfObjectid(),dto.getMfDescription(),dto.getDevicetypeMrid(),dto.getDtid(),dto.isCategoryNull()?null:dto.getCategory(),dto.getSubcategory(),dto.getModel(),dto.getObjectid(),dto.getLogo(),dto.getDescription());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return VMfCateDevtype
	 */
	public VMfCateDevtype mapRow(ResultSet rs, int row) throws SQLException
	{
		VMfCateDevtype dto = new VMfCateDevtype();
		dto.setId( rs.getLong( 1 ) );
		dto.setName( rs.getString( 2 ) );
		dto.setFlag( rs.getString( 3 ) );
		dto.setMrid( rs.getLong( 4 ) );
		dto.setMrname( rs.getString( 5 ) );
		dto.setMfObjectid( rs.getString( 6 ) );
		dto.setMfDescription( rs.getString( 7 ) );
		dto.setDevicetypeMrid( rs.getLong( 8 ) );
		dto.setDtid( rs.getLong( 9 ) );
		dto.setCategory( rs.getLong( 10 ) );
		if (rs.wasNull()) {
			dto.setCategoryNull( true );
		}
		
		dto.setSubcategory( rs.getString( 11 ) );
		dto.setModel( rs.getString( 12 ) );
		dto.setObjectid( rs.getString( 13 ) );
		dto.setLogo( rs.getString( 14 ) );
		dto.setDescription( rs.getString( 15 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "V_MF_CATE_DEVTYPE";
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria ''.
	 */
	@Transactional
	public List<VMfCateDevtype> findAll() throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'ID = :id'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereIdEquals(long id) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE ID = ? ORDER BY ID", this,id);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'NAME = :name'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereNameEquals(String name) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE NAME = ? ORDER BY NAME", this,name);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'FLAG = :flag'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereFlagEquals(String flag) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE FLAG = ? ORDER BY FLAG", this,flag);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'MRID = :mrid'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereMridEquals(long mrid) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE MRID = ? ORDER BY MRID", this,mrid);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'MRNAME = :mrname'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereMrnameEquals(String mrname) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE MRNAME = ? ORDER BY MRNAME", this,mrname);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'MF_OBJECTID = :mfObjectid'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereMfObjectidEquals(String mfObjectid) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE MF_OBJECTID = ? ORDER BY MF_OBJECTID", this,mfObjectid);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'MF_DESCRIPTION = :mfDescription'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereMfDescriptionEquals(String mfDescription) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE MF_DESCRIPTION = ? ORDER BY MF_DESCRIPTION", this,mfDescription);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'DEVICETYPE_MRID = :devicetypeMrid'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereDevicetypeMridEquals(long devicetypeMrid) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE DEVICETYPE_MRID = ? ORDER BY DEVICETYPE_MRID", this,devicetypeMrid);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'DTID = :dtid'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereDtidEquals(long dtid) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE DTID = ? ORDER BY DTID", this,dtid);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'CATEGORY = :category'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereCategoryEquals(long category) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE CATEGORY = ? ORDER BY CATEGORY", this,category);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'SUBCATEGORY = :subcategory'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereSubcategoryEquals(String subcategory) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE SUBCATEGORY = ? ORDER BY SUBCATEGORY", this,subcategory);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'MODEL = :model'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereModelEquals(String model) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE MODEL = ? ORDER BY MODEL", this,model);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'OBJECTID = :objectid'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereObjectidEquals(String objectid) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE OBJECTID = ? ORDER BY OBJECTID", this,objectid);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'LOGO = :logo'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereLogoEquals(String logo) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE LOGO = ? ORDER BY LOGO", this,logo);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_MF_CATE_DEVTYPE table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<VMfCateDevtype> findWhereDescriptionEquals(String description) throws VMfCateDevtypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG, MRID, MRNAME, MF_OBJECTID, MF_DESCRIPTION, DEVICETYPE_MRID, DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new VMfCateDevtypeDaoException("Query failed", e);
		}
		
	}

}
