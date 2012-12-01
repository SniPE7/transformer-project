package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.VEventTypeDao;
import com.ibm.ncs.model.dto.VEventType;
import com.ibm.ncs.model.exceptions.VEventTypeDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class VEventTypeDaoImpl extends AbstractDAO implements ParameterizedRowMapper<VEventType>, VEventTypeDao
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
	public void insert(VEventType dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getModid(),dto.getEveid(),dto.isEtidNull()?null:dto.getEtid(),dto.isEstidNull()?null:dto.getEstid(),dto.getEveothername(),dto.getEcode(),dto.isGeneralNull()?null:dto.getGeneral(),dto.getMajor(),dto.getMinor(),dto.getOther(),dto.getDescription(),dto.getUseflag(),dto.getTModuleInfoInitModid(),dto.getMname(),dto.getMcode(),dto.getTModuleInfoInitDescription());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return VEventType
	 */
	public VEventType mapRow(ResultSet rs, int row) throws SQLException
	{
		VEventType dto = new VEventType();
		dto.setModid( rs.getLong( 1 ) );
		dto.setEveid( rs.getLong( 2 ) );
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
		dto.setDescription( rs.getString( 11 ) );
		dto.setUseflag( rs.getString( 12 ) );
		dto.setTModuleInfoInitModid( rs.getLong( 13 ) );
		dto.setMname( rs.getString( 14 ) );
		dto.setMcode( rs.getLong( 15 ) );
		dto.setTModuleInfoInitDescription( rs.getString( 16 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "V_EVENT_TYPE";
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria ''.
	 */
	@Transactional
	public List<VEventType> findAll() throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'MODID = :modid'.
	 */
	@Transactional
	public List<VEventType> findWhereModidEquals(long modid) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE MODID = ? ORDER BY MODID", this,modid);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'EVEID = :eveid'.
	 */
	@Transactional
	public List<VEventType> findWhereEveidEquals(long eveid) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE EVEID = ? ORDER BY EVEID", this,eveid);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'ETID = :etid'.
	 */
	@Transactional
	public List<VEventType> findWhereEtidEquals(long etid) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE ETID = ? ORDER BY ETID", this,etid);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'ESTID = :estid'.
	 */
	@Transactional
	public List<VEventType> findWhereEstidEquals(long estid) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE ESTID = ? ORDER BY ESTID", this,estid);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'EVEOTHERNAME = :eveothername'.
	 */
	@Transactional
	public List<VEventType> findWhereEveothernameEquals(String eveothername) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE EVEOTHERNAME = ? ORDER BY EVEOTHERNAME", this,eveothername);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'ECODE = :ecode'.
	 */
	@Transactional
	public List<VEventType> findWhereEcodeEquals(long ecode) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE ECODE = ? ORDER BY ECODE", this,ecode);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'GENERAL = :general'.
	 */
	@Transactional
	public List<VEventType> findWhereGeneralEquals(long general) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE GENERAL = ? ORDER BY GENERAL", this,general);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'MAJOR = :major'.
	 */
	@Transactional
	public List<VEventType> findWhereMajorEquals(String major) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE MAJOR = ? ORDER BY MAJOR", this,major);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'MINOR = :minor'.
	 */
	@Transactional
	public List<VEventType> findWhereMinorEquals(String minor) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE MINOR = ? ORDER BY MINOR", this,minor);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'OTHER = :other'.
	 */
	@Transactional
	public List<VEventType> findWhereOtherEquals(String other) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE OTHER = ? ORDER BY OTHER", this,other);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<VEventType> findWhereDescriptionEquals(String description) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'USEFLAG = :useflag'.
	 */
	@Transactional
	public List<VEventType> findWhereUseflagEquals(String useflag) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE USEFLAG = ? ORDER BY USEFLAG", this,useflag);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'T_MODULE_INFO_INIT_MODID = :tModuleInfoInitModid'.
	 */
	@Transactional
	public List<VEventType> findWhereTModuleInfoInitModidEquals(long tModuleInfoInitModid) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE T_MODULE_INFO_INIT_MODID = ? ORDER BY T_MODULE_INFO_INIT_MODID", this,tModuleInfoInitModid);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'MNAME = :mname'.
	 */
	@Transactional
	public List<VEventType> findWhereMnameEquals(String mname) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE MNAME = ? ORDER BY MNAME", this,mname);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'MCODE = :mcode'.
	 */
	@Transactional
	public List<VEventType> findWhereMcodeEquals(long mcode) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE MCODE = ? ORDER BY MCODE", this,mcode);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_EVENT_TYPE table that match the criteria 'T_MODULE_INFO_INIT_DESCRIPTION = :tModuleInfoInitDescription'.
	 */
	@Transactional
	public List<VEventType> findWhereTModuleInfoInitDescriptionEquals(String tModuleInfoInitDescription) throws VEventTypeDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, EVEID, ETID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG, T_MODULE_INFO_INIT_MODID, MNAME, MCODE, T_MODULE_INFO_INIT_DESCRIPTION FROM " + getTableName() + " WHERE T_MODULE_INFO_INIT_DESCRIPTION = ? ORDER BY T_MODULE_INFO_INIT_DESCRIPTION", this,tModuleInfoInitDescription);
		}
		catch (Exception e) {
			throw new VEventTypeDaoException("Query failed", e);
		}
		
	}

}
