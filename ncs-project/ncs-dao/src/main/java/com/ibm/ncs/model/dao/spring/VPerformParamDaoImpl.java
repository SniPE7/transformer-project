package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.VPerformParamDao;
import com.ibm.ncs.model.dto.VPerformParam;
import com.ibm.ncs.model.exceptions.VPerformParamDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class VPerformParamDaoImpl extends AbstractDAO implements ParameterizedRowMapper<VPerformParam>, VPerformParamDao
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
	public void insert(VPerformParam dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getEventtypeModid(),dto.getEventtypeEveid(),dto.isEtidNull()?null:dto.getEtid(),dto.isEstidNull()?null:dto.getEstid(),dto.getEveothername(),dto.getEcode(),dto.isGeneralNull()?null:dto.getGeneral(),dto.getMajor(),dto.getMinor(),dto.getOther(),dto.getEventtypeDescription(),dto.getUseflag(),dto.getOidgroupOpid(),dto.getOidgroupOidgroupname(),dto.getOtype(),dto.getOidgroupDescription(),dto.getEveid(),dto.getMrid(),dto.getDtid(),dto.getOidgroupname(),dto.getModid(),dto.getOid(),dto.getUnit(),dto.getDescription(),dto.getDevtypeMrid(),dto.getDevtypeDtid(),dto.isCategoryNull()?null:dto.getCategory(),dto.getSubcategory(),dto.getModel(),dto.getObjectid(),dto.getLogo(),dto.getDevtypeDescription());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return VPerformParam
	 */
	public VPerformParam mapRow(ResultSet rs, int row) throws SQLException
	{
		VPerformParam dto = new VPerformParam();
		dto.setEventtypeModid( rs.getLong( 1 ) );
		dto.setEventtypeEveid( rs.getLong( 2 ) );
		dto.setEtid( rs.getLong( 3 ) );
		if (rs.wasNull()) {
			dto.setEtidNull( true );
		}
		
		dto.setEstid( rs.getLong( 4 ) );
		if (rs.wasNull()) {
			dto.setEstidNull( true );
		}
		
		dto.setEveothername( rs.getString( 5 ) );
		dto.setEcode( rs.getLong( 6 ) );
		dto.setGeneral( rs.getLong( 7 ) );
		if (rs.wasNull()) {
			dto.setGeneralNull( true );
		}
		
		dto.setMajor( rs.getString( 8 ) );
		dto.setMinor( rs.getString( 9 ) );
		dto.setOther( rs.getString( 10 ) );
		dto.setEventtypeDescription( rs.getString( 11 ) );
		dto.setUseflag( rs.getString( 12 ) );
		dto.setOidgroupOpid( rs.getLong( 13 ) );
		dto.setOidgroupOidgroupname( rs.getString( 14 ) );
		dto.setOtype( rs.getLong( 15 ) );
		dto.setOidgroupDescription( rs.getString( 16 ) );
		dto.setEveid( rs.getLong( 17 ) );
		dto.setMrid( rs.getLong( 18 ) );
		dto.setDtid( rs.getLong( 19 ) );
		dto.setOidgroupname( rs.getString( 20 ) );
		dto.setModid( rs.getLong( 21 ) );
		dto.setOid( rs.getString( 22 ) );
		dto.setUnit( rs.getString( 23 ) );
		dto.setDescription( rs.getString( 24 ) );
		dto.setDevtypeMrid( rs.getLong( 25 ) );
		dto.setDevtypeDtid( rs.getLong( 26 ) );
		dto.setCategory( rs.getLong( 27 ) );
		if (rs.wasNull()) {
			dto.setCategoryNull( true );
		}
		
		dto.setSubcategory( rs.getString( 28 ) );
		dto.setModel( rs.getString( 29 ) );
		dto.setObjectid( rs.getString( 30 ) );
		dto.setLogo( rs.getString( 31 ) );
		dto.setDevtypeDescription( rs.getString( 32 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "V_PERFORM_PARAM";
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria ''.
	 */
	@Transactional
	public List<VPerformParam> findAll() throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'EVENTTYPE_MODID = :eventtypeModid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereEventtypeModidEquals(long eventtypeModid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE EVENTTYPE_MODID = ? ORDER BY EVENTTYPE_MODID", this,eventtypeModid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'EVENTTYPE_EVEID = :eventtypeEveid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereEventtypeEveidEquals(long eventtypeEveid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE EVENTTYPE_EVEID = ? ORDER BY EVENTTYPE_EVEID", this,eventtypeEveid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'ETID = :etid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereEtidEquals(long etid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE ETID = ? ORDER BY ETID", this,etid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'ESTID = :estid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereEstidEquals(long estid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE ESTID = ? ORDER BY ESTID", this,estid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'EVEOTHERNAME = :eveothername'.
	 */
	@Transactional
	public List<VPerformParam> findWhereEveothernameEquals(String eveothername) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE EVEOTHERNAME = ? ORDER BY EVEOTHERNAME", this,eveothername);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'ECODE = :ecode'.
	 */
	@Transactional
	public List<VPerformParam> findWhereEcodeEquals(long ecode) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE ECODE = ? ORDER BY ECODE", this,ecode);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'GENERAL = :general'.
	 */
	@Transactional
	public List<VPerformParam> findWhereGeneralEquals(long general) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE GENERAL = ? ORDER BY GENERAL", this,general);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'MAJOR = :major'.
	 */
	@Transactional
	public List<VPerformParam> findWhereMajorEquals(String major) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE MAJOR = ? ORDER BY MAJOR", this,major);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'MINOR = :minor'.
	 */
	@Transactional
	public List<VPerformParam> findWhereMinorEquals(String minor) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE MINOR = ? ORDER BY MINOR", this,minor);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OTHER = :other'.
	 */
	@Transactional
	public List<VPerformParam> findWhereOtherEquals(String other) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE OTHER = ? ORDER BY OTHER", this,other);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'EVENTTYPE_DESCRIPTION = :eventtypeDescription'.
	 */
	@Transactional
	public List<VPerformParam> findWhereEventtypeDescriptionEquals(String eventtypeDescription) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE EVENTTYPE_DESCRIPTION = ? ORDER BY EVENTTYPE_DESCRIPTION", this,eventtypeDescription);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'USEFLAG = :useflag'.
	 */
	@Transactional
	public List<VPerformParam> findWhereUseflagEquals(String useflag) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE USEFLAG = ? ORDER BY USEFLAG", this,useflag);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OIDGROUP_OPID = :oidgroupOpid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereOidgroupOpidEquals(long oidgroupOpid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE OIDGROUP_OPID = ? ORDER BY OIDGROUP_OPID", this,oidgroupOpid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OIDGROUP_OIDGROUPNAME = :oidgroupOidgroupname'.
	 */
	@Transactional
	public List<VPerformParam> findWhereOidgroupOidgroupnameEquals(String oidgroupOidgroupname) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE OIDGROUP_OIDGROUPNAME = ? ORDER BY OIDGROUP_OIDGROUPNAME", this,oidgroupOidgroupname);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OTYPE = :otype'.
	 */
	@Transactional
	public List<VPerformParam> findWhereOtypeEquals(long otype) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE OTYPE = ? ORDER BY OTYPE", this,otype);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OIDGROUP_DESCRIPTION = :oidgroupDescription'.
	 */
	@Transactional
	public List<VPerformParam> findWhereOidgroupDescriptionEquals(String oidgroupDescription) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE OIDGROUP_DESCRIPTION = ? ORDER BY OIDGROUP_DESCRIPTION", this,oidgroupDescription);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'EVEID = :eveid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereEveidEquals(long eveid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE EVEID = ? ORDER BY EVEID", this,eveid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'MRID = :mrid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereMridEquals(long mrid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE MRID = ? ORDER BY MRID", this,mrid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'DTID = :dtid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereDtidEquals(long dtid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE DTID = ? ORDER BY DTID", this,dtid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OIDGROUPNAME = :oidgroupname'.
	 */
	@Transactional
	public List<VPerformParam> findWhereOidgroupnameEquals(String oidgroupname) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE OIDGROUPNAME = ? ORDER BY OIDGROUPNAME", this,oidgroupname);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'MODID = :modid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereModidEquals(long modid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE MODID = ? ORDER BY MODID", this,modid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OID = :oid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereOidEquals(String oid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE OID = ? ORDER BY OID", this,oid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'UNIT = :unit'.
	 */
	@Transactional
	public List<VPerformParam> findWhereUnitEquals(String unit) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE UNIT = ? ORDER BY UNIT", this,unit);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<VPerformParam> findWhereDescriptionEquals(String description) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'DEVTYPE_MRID = :devtypeMrid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereDevtypeMridEquals(long devtypeMrid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE DEVTYPE_MRID = ? ORDER BY DEVTYPE_MRID", this,devtypeMrid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'DEVTYPE_DTID = :devtypeDtid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereDevtypeDtidEquals(long devtypeDtid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE DEVTYPE_DTID = ? ORDER BY DEVTYPE_DTID", this,devtypeDtid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'CATEGORY = :category'.
	 */
	@Transactional
	public List<VPerformParam> findWhereCategoryEquals(long category) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE CATEGORY = ? ORDER BY CATEGORY", this,category);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'SUBCATEGORY = :subcategory'.
	 */
	@Transactional
	public List<VPerformParam> findWhereSubcategoryEquals(String subcategory) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE SUBCATEGORY = ? ORDER BY SUBCATEGORY", this,subcategory);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'MODEL = :model'.
	 */
	@Transactional
	public List<VPerformParam> findWhereModelEquals(String model) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE MODEL = ? ORDER BY MODEL", this,model);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'OBJECTID = :objectid'.
	 */
	@Transactional
	public List<VPerformParam> findWhereObjectidEquals(String objectid) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE OBJECTID = ? ORDER BY OBJECTID", this,objectid);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'LOGO = :logo'.
	 */
	@Transactional
	public List<VPerformParam> findWhereLogoEquals(String logo) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE LOGO = ? ORDER BY LOGO", this,logo);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_PERFORM_PARAM table that match the criteria 'DEVTYPE_DESCRIPTION = :devtypeDescription'.
	 */
	@Transactional
	public List<VPerformParam> findWhereDevtypeDescriptionEquals(String devtypeDescription) throws VPerformParamDaoException
	{
		try {
			return jdbcTemplate.query("SELECT EVENTTYPE_MODID, EVENTTYPE_EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, EVENTTYPE_DESCRIPTION, USEFLAG, OIDGROUP_OPID, OIDGROUP_OIDGROUPNAME, OTYPE, OIDGROUP_DESCRIPTION, EVEID, MRID, DTID, OIDGROUPNAME, MODID, OID, UNIT, DESCRIPTION, DEVTYPE_MRID, DEVTYPE_DTID, CATEGORY, SUBCATEGORY, MODEL, OBJECTID, LOGO, DEVTYPE_DESCRIPTION FROM " + getTableName() + " WHERE DEVTYPE_DESCRIPTION = ? ORDER BY DEVTYPE_DESCRIPTION", this,devtypeDescription);
		}
		catch (Exception e) {
			throw new VPerformParamDaoException("Query failed", e);
		}
		
	}

}
