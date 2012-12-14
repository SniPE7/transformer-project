package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TEventTypeInitPk;
import com.ibm.ncs.model.exceptions.TEventTypeInitDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TEventTypeInitDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TEventTypeInit>, TEventTypeInitDao
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
	 * @return TEventTypeInitPk
	 */
	@Transactional
	public TEventTypeInitPk insert(TEventTypeInit dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.isEtidNull()?null:dto.getEtid(),dto.getModid(),dto.getEveid(),dto.isEstidNull()?null:dto.getEstid(),dto.getEveothername(),dto.getEcode(),dto.isGeneralNull()?null:dto.getGeneral(),dto.getMajor(),dto.getMinor(),dto.getOther(),dto.getDescription(),dto.getUseflag());
//		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.isEtidNull()?null:dto.getEtid(),dto.getModid(),dto.getEveid(),dto.isEstidNull()?null:dto.getEstid(),dto.getEveothername(),dto.getEcode(),dto.isGeneralNull()?null:dto.getGeneral(),dto.getMajor(),dto.getMinor(),dto.getOther(),dto.getDescription(),dto.getUseflag());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_EVENT_TYPE_INIT table.
	 */
	@Transactional
	public void update(TEventTypeInitPk pk, TEventTypeInit dto) throws TEventTypeInitDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET ETID = ?, MODID = ?, EVEID = ?, ESTID = ?, EVEOTHERNAME = ?, ECODE = ?, GENERAL = ?, MAJOR = ?, MINOR = ?, OTHER = ?, DESCRIPTION = ?, USEFLAG = ? WHERE MODID = ? AND EVEID = ?",dto.getEtid(),dto.getModid(),dto.getEveid(),dto.getEstid(),dto.getEveothername(),dto.getEcode(),dto.getGeneral(),dto.getMajor(),dto.getMinor(),dto.getOther(),dto.getDescription(),dto.getUseflag(),pk.getModid(),pk.getEveid());
	}

	/** 
	 * Deletes a single row in the T_EVENT_TYPE_INIT table.
	 */
	@Transactional
	public void delete(TEventTypeInitPk pk) throws TEventTypeInitDaoException
	{
		//System.out.println("DELETE FROM " + getTableName() + " WHERE MODID = ? AND EVEID = ?"+pk);
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE MODID = ? AND EVEID = ?",pk.getModid(),pk.getEveid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TEventTypeInit
	 */
	public TEventTypeInit mapRow(ResultSet rs, int row) throws SQLException
	{
		TEventTypeInit dto = new TEventTypeInit();
		dto.setEtid( rs.getLong( 1 ) );
		if (rs.wasNull()) {
			dto.setEtidNull( true );
		}
		
		dto.setModid( rs.getLong( 2 ) );
		dto.setEveid( rs.getLong( 3 ) );
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
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_EVENT_TYPE_INIT";
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'MODID = :modid AND EVEID = :eveid'.
	 */
	@Transactional
	public TEventTypeInit findByPrimaryKey(long modid, long eveid) throws TEventTypeInitDaoException
	{
		try {
			List<TEventTypeInit> list = jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE MODID = ? AND EVEID = ?", this,modid,eveid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria ''.
	 */
	@Transactional
	public List<TEventTypeInit> findAll() throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " ORDER BY MODID, EVEID", this);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'ETID = :etid'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereEtidEquals(long etid) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE ETID = ? ORDER BY ETID", this,etid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'MODID = :modid'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereModidEquals(long modid) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE MODID = ? ORDER BY MODID", this,modid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'EVEID = :eveid'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereEveidEquals(long eveid) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE EVEID = ? ORDER BY EVEID", this,eveid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'ESTID = :estid'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereEstidEquals(long estid) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE ESTID = ? ORDER BY ESTID", this,estid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'EVEOTHERNAME = :eveothername'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereEveothernameEquals(String eveothername) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE EVEOTHERNAME = ? ORDER BY EVEOTHERNAME", this,eveothername);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'ECODE = :ecode'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereEcodeEquals(long ecode) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE ECODE = ? ORDER BY ECODE", this,ecode);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'GENERAL = :general'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereGeneralEquals(long general) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE GENERAL = ? ORDER BY GENERAL", this,general);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'MAJOR = :major'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereMajorEquals(String major) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE MAJOR = ? ORDER BY MAJOR", this,major);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'MINOR = :minor'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereMinorEquals(String minor) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE MINOR = ? ORDER BY MINOR", this,minor);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'OTHER = :other'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereOtherEquals(String other) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE OTHER = ? ORDER BY OTHER", this,other);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereDescriptionEquals(String description) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'USEFLAG = :useflag'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereUseflagEquals(String useflag) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE USEFLAG = ? ORDER BY USEFLAG", this,useflag);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_EVENT_TYPE_INIT table that matches the specified primary-key value.
	 */
	public TEventTypeInit findByPrimaryKey(TEventTypeInitPk pk) throws TEventTypeInitDaoException
	{
		return findByPrimaryKey( pk.getModid(), pk.getEveid() );
	}
	
	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'GENERAL = :general'.
	 * filter for device , port, predef mib index, by ecode =1,6, 9
	 * filter for snmp by  modid=####
	 */
	@Transactional
	public List<TEventTypeInit> findByGeneralEcodeModid( long ecode, long modid) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE  ecode=? and modid=? ORDER BY GENERAL, ecode, modid", this, ecode,modid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}
	
	/** 
	 * Returns all rows from the T_EVENT_TYPE_INIT table that match the criteria 'MAJOR = :major MINOR = :minor OTHER= :other'.
	 */
	@Transactional
	public List<TEventTypeInit> findWhereMajorMinorOtherEquals(String major, String minor, String other) throws TEventTypeInitDaoException
	{
		try {
			if((major.equalsIgnoreCase("null")|| major==null) || (minor.equalsIgnoreCase("null")|| minor==null)  || (other.equalsIgnoreCase("null")|| other==null) ){
				if((major.equalsIgnoreCase("null")|| major==null) && !((minor.equalsIgnoreCase("null")|| minor==null)  || (other.equalsIgnoreCase("null")|| other==null)))
					return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE MAJOR is NULL and MINOR=? and OTHER=? ORDER BY ETID", this, minor, other);
				else if((minor.equalsIgnoreCase("null")|| minor==null) && !((major.equalsIgnoreCase("null")|| major==null)  || (other.equalsIgnoreCase("null")|| other==null)))
					return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE MAJOR=? and MINOR is NULL and OTHER=? ORDER BY ETID", this,major, other);
				else if((other.equalsIgnoreCase("null")|| other==null) && !((minor.equalsIgnoreCase("null")|| minor==null)  || (major.equalsIgnoreCase("null")|| major==null)))
					return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE MAJOR=?  and MINOR=? and OTHER is NULL ORDER BY ETID", this,major, minor);
			
				if((major.equalsIgnoreCase("null")|| major==null) && (minor.equalsIgnoreCase("null")|| minor==null) && !(other.equalsIgnoreCase("null")|| other==null))
					return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE MAJOR is NULL and MINOR is NULL and OTHER=? ORDER BY ETID", this,other);
				else if((major.equalsIgnoreCase("null")|| major==null) && (other.equalsIgnoreCase("null")|| other==null) && !(minor.equalsIgnoreCase("null")|| minor==null))
					return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE MAJOR is NULL and MINOR=? and OTHER is NULL ORDER BY ETID", this,minor);
				else if((other.equalsIgnoreCase("null")|| other==null) && (minor.equalsIgnoreCase("null")|| minor==null) && !(major.equalsIgnoreCase("null")|| major==null))
					return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE MAJOR=?  and MINOR is NULL and OTHER is NULL ORDER BY ETID", this,major);
			
				if((major.equalsIgnoreCase("null")|| major==null) && (minor.equalsIgnoreCase("null")|| minor==null) && (other.equalsIgnoreCase("null")|| other==null))
				return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE MAJOR is NULL and MINOR is NULL and OTHER is NULL ORDER BY ETID", this);
				
			}
			return jdbcTemplate.query("SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " WHERE MAJOR = ? and MINOR=? and OTHER=? ORDER BY ETID", this,major, minor, other);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}
	
	/** 
	 * Returns rows from the T_EVENT_TYPE_INIT table that match the criteria 'GENERAL = :general'.
	 * filter for device =1, port=6, predef mib index=9, by ecode =1,6, 9
	 * filter for snmp by  modid=####
	 */
	@Transactional
	public List<TEventTypeInit> listForDeviceSyslog(long mpid ) throws TEventTypeInitDaoException
	{
		try {
//			String sql = "select * from ("+
//				"SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " a "+
//			" WHERE general <>-1 and ecode=1  and modid in (select modid from t_module_info_init where lower(mname)=lower('syslog'))  " +
//			") where eveid not in (select eveid from t_policy_details where mpid="+mpid+")"+
//						" ORDER BY modid , eveid,estid";
			//System.out.println(sql);
			return jdbcTemplate.query("select * from ("+
					"SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + " a "+
					" WHERE  ecode=1  and modid in (select modid from t_module_info_init where lower(mname)=lower('syslog'))  " +
					") where eveid not in (select eveid from t_policy_details where mpid=?)"+
					" ORDER BY modid , eveid,estid", this,mpid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}	

	/** 
	 * Returns rows from the T_EVENT_TYPE_INIT table that match the criteria 'GENERAL = :general'.
	 * filter for device =1, port=6, predef mib index=9, by ecode =1,6, 9
	 * filter for snmp by  modid=####
	 */
	@Transactional
	public List<TEventTypeInit> listForPortSyslog(long mpid ) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("select * from ( "+
					" SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + 
					" WHERE  ecode=6  and modid in (select modid from t_module_info_init where lower(mname)=lower('syslog')) " +
					" ) where eveid not in (select eveid from t_policy_details where mpid=?)"+
					" ORDER BY modid , eveid,estid"
					, this,mpid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}	
	
	public List<TEventTypeInit> listForDeviceSnmp(long mpid ) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("select * from ("+
					"SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + 
					" WHERE  ecode=1  and modid in (select modid from t_module_info_init where lower(mname)=lower('snmp')) " +
					") where eveid not in (select eveid from t_policy_details where mpid=?)"+
					" ORDER BY modid , eveid,estid", this,mpid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}	

	/** 
	 * Returns rows from the T_EVENT_TYPE_INIT table that match the criteria 'GENERAL = :general'.
	 * filter for device =1, port=6, predef mib index=9, by ecode =1,6, 9
	 * filter for snmp by  modid=####
	 */
	@Transactional
	public List<TEventTypeInit> listForPortSnmp(long mpid ) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("select * from ("+
					"SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + 
					" WHERE   ecode=6  and modid in (select modid from t_module_info_init where lower(mname)=lower('snmp')) " +
					") where eveid not in (select eveid from t_policy_details where mpid=?)"+
					" ORDER BY modid , eveid,estid", this, mpid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}
	/** 
	 * Returns rows from the T_EVENT_TYPE_INIT table that match the criteria 'GENERAL = :general'.
	 * filter for device =1, port=6, predef mib index=9, by ecode =1,6, 9
	 * filter for snmp by  modid=####
	 */
	@Transactional
	public List<TEventTypeInit> listForPreDefMibSnmp(long mpid ) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("select * from ("+
					"SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + 
					" WHERE  ecode=9  and modid in (select modid from t_module_info_init where lower(mname)=lower('snmp')) " +
					") where eveid not in (select eveid from t_policy_details where mpid=?)"+
					" ORDER BY modid , eveid,estid", this, mpid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}
	

	public List<TEventTypeInit> listForDeviceIcmp(long mpid ) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("select * from ("+
					"SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + 
					" WHERE  modid in (select modid from t_module_info_init where lower(mname)=lower('icmp')) " +
					") where eveid not in (select eveid from t_policy_details where mpid=?)"+
					" ORDER BY modid , eveid,estid", this,mpid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}	

	/** 
	 * Returns rows from the T_EVENT_TYPE_INIT table that match the criteria 'GENERAL = :general'.
	 * filter for device =1, port=6, predef mib index=9, by ecode =1,6, 9
	 * filter for snmp by  modid=####
	 */
	@Transactional
	public List<TEventTypeInit> listForPortIcmp(long mpid ) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query(" select * from ("+
					"SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + 
					" WHERE  modid in (select modid from t_module_info_init where lower(mname)=lower('icmp')) " +
					") where eveid not in (select eveid from t_policy_details where mpid=?)"+
					" ORDER BY modid , eveid,estid", this,mpid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	public List<TEventTypeInit> listMajor() throws TEventTypeInitDaoException {
		try {
			String sql = " SELECT * FROM T_EVENT_TYPE_INIT e,T_POLICY_DETAILS p WHERE e.EVEID=p.EVEID";
			System.out.println("listmajor sql is "+sql);
			return jdbcTemplate.query(sql, this);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
	}
	
	
	public List<TEventTypeInit> listForDeviceSnmpRule(long ptvid ) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("select * from ("+
					"SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + 
					" WHERE  ecode=1  and modid in (select modid from t_module_info_init where lower(mname)=lower('snmp')) " +
					") where eveid not in (select eveid from T_POLICY_EVENT_RULE where ptvid=?)"+
					" ORDER BY modid , eveid,estid", this, ptvid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}	

	public List<TEventTypeInit> listForPortSnmpRule(long ptvid ) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("select * from ("+
					"SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + 
					" WHERE   ecode=6  and modid in (select modid from t_module_info_init where lower(mname)=lower('snmp')) " +
					") where eveid not in (select eveid from T_POLICY_EVENT_RULE where ptvid=?)"+
					" ORDER BY modid , eveid,estid", this, ptvid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}

	public List<TEventTypeInit> listForPreDefMibSnmpRule(long ptvid ) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("select * from ("+
					"SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + 
					" WHERE  ecode=9  and modid in (select modid from t_module_info_init where lower(mname)=lower('snmp')) " +
					") where eveid not in (select eveid from T_POLICY_EVENT_RULE where ptvid=?)"+
					" ORDER BY modid , eveid,estid", this, ptvid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}
	

	public List<TEventTypeInit> listForDeviceIcmpRule(long ptvid) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query("select * from ("+
					"SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + 
					" WHERE  modid in (select modid from t_module_info_init where lower(mname)=lower('icmp')) " +
					") where eveid not in (select eveid from T_POLICY_EVENT_RULE where ptvid=?)"+
					" ORDER BY modid , eveid,estid", this,ptvid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}	

	public List<TEventTypeInit> listForPortIcmpRule(long ptvid ) throws TEventTypeInitDaoException
	{
		try {
			return jdbcTemplate.query(" select * from ("+
					"SELECT ETID, MODID, EVEID, ESTID, EVEOTHERNAME, ECODE, GENERAL, MAJOR, MINOR, OTHER, DESCRIPTION, USEFLAG FROM " + getTableName() + 
					" WHERE  modid in (select modid from t_module_info_init where lower(mname)=lower('icmp')) " +
					") where eveid not in (select eveid from T_POLICY_EVENT_RULE where ptvid=?)"+
					" ORDER BY modid , eveid,estid", this, ptvid);
		}
		catch (Exception e) {
			throw new TEventTypeInitDaoException("Query failed", e);
		}
		
	}
}
