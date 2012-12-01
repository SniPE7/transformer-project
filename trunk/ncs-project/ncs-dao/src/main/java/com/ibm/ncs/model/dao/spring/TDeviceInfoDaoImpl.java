package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceInfoPk;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.exceptions.SyslogEventsProcessDaoException;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TDeviceInfoDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TDeviceInfo>, TDeviceInfoDao
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
	 * @return TDeviceInfoPk
	 */
	@Transactional
	public TDeviceInfoPk insert(TDeviceInfo dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getDevid(),dto.getDevip(),dto.getIpdecode(),dto.getSysname(),dto.getSysnamealias(),dto.getRsno(),dto.isSridNull()?null:dto.getSrid(),dto.getAdmin(),dto.getPhone(),dto.isMridNull()?null:dto.getMrid(),dto.isDtidNull()?null:dto.getDtid(),dto.getSerialid(),dto.getSwversion(),dto.isRamsizeNull()?null:dto.getRamsize(),dto.getRamunit(),dto.isNvramsizeNull()?null:dto.getNvramsize(),dto.getNvramunit(),dto.isFlashsizeNull()?null:dto.getFlashsize(),dto.getFlashunit(),dto.getFlashfilename(),dto.getFlashfilesize(),dto.getRcommunity(),dto.getWcommunity(),dto.getDescription(),dto.isDomainidNull()?null:dto.getDomainid(),dto.getSnmpversion());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_DEVICE_INFO table.
	 */
	@Transactional
	public void update(TDeviceInfoPk pk, TDeviceInfo dto) throws TDeviceInfoDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET DEVID = ?, DEVIP = ?, IPDECODE = ?, SYSNAME = ?, SYSNAMEALIAS = ?, RSNO = ?, SRID = ?, ADMIN = ?, PHONE = ?, MRID = ?, DTID = ?, SERIALID = ?, SWVERSION = ?, RAMSIZE = ?, RAMUNIT = ?, NVRAMSIZE = ?, NVRAMUNIT = ?, FLASHSIZE = ?, FLASHUNIT = ?, FLASHFILENAME = ?, FLASHFILESIZE = ?, RCOMMUNITY = ?, WCOMMUNITY = ?, DESCRIPTION = ?, DOMAINID = ?, SNMPVERSION = ? WHERE DEVID = ?",dto.getDevid(),dto.getDevip(),dto.getIpdecode(),dto.getSysname(),dto.getSysnamealias(),dto.getRsno(),dto.getSrid(),dto.getAdmin(),dto.getPhone(),dto.getMrid(),dto.getDtid(),dto.getSerialid(),dto.getSwversion(),dto.getRamsize(),dto.getRamunit(),dto.getNvramsize(),dto.getNvramunit(),dto.getFlashsize(),dto.getFlashunit(),dto.getFlashfilename(),dto.getFlashfilesize(),dto.getRcommunity(),dto.getWcommunity(),dto.getDescription(),dto.getDomainid(),dto.getSnmpversion(),pk.getDevid());
	}

	/** 
	 * Deletes a single row in the T_DEVICE_INFO table.
	 */
	@Transactional
	public void delete(TDeviceInfoPk pk) throws TDeviceInfoDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE DEVID = ?",pk.getDevid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TDeviceInfo
	 */
	public TDeviceInfo mapRow(ResultSet rs, int row) throws SQLException
	{
		TDeviceInfo dto = new TDeviceInfo();
		dto.setDevid( rs.getLong( 1 ) );
		dto.setDevip( rs.getString( 2 ) );
		dto.setIpdecode( rs.getLong( 3 ) );
		dto.setSysname( rs.getString( 4 ) );
		dto.setSysnamealias( rs.getString( 5 ) );
		dto.setRsno( rs.getString( 6 ) );
		dto.setSrid( rs.getLong( 7 ) );
		if (rs.wasNull()) {
			dto.setSridNull( true );
		}
		
		dto.setAdmin( rs.getString( 8 ) );
		dto.setPhone( rs.getString( 9 ) );
		dto.setMrid( rs.getLong( 10 ) );
		if (rs.wasNull()) {
			dto.setMridNull( true );
		}
		
		dto.setDtid( rs.getLong( 11 ) );
		if (rs.wasNull()) {
			dto.setDtidNull( true );
		}
		
		dto.setSerialid( rs.getString( 12 ) );
		dto.setSwversion( rs.getString( 13 ) );
		dto.setRamsize( rs.getLong( 14 ) );
		if (rs.wasNull()) {
			dto.setRamsizeNull( true );
		}
		
		dto.setRamunit( rs.getString( 15 ) );
		dto.setNvramsize( rs.getLong( 16 ) );
		if (rs.wasNull()) {
			dto.setNvramsizeNull( true );
		}
		
		dto.setNvramunit( rs.getString( 17 ) );
		dto.setFlashsize( rs.getLong( 18 ) );
		if (rs.wasNull()) {
			dto.setFlashsizeNull( true );
		}
		
		dto.setFlashunit( rs.getString( 19 ) );
		dto.setFlashfilename( rs.getString( 20 ) );
		dto.setFlashfilesize( rs.getString( 21 ) );
		dto.setRcommunity( rs.getString( 22 ) );
		dto.setWcommunity( rs.getString( 23 ) );
		dto.setDescription( rs.getString( 24 ) );
		dto.setDomainid( rs.getLong( 25 ) );
		if (rs.wasNull()) {
			dto.setDomainidNull( true );
		}
		
		dto.setSnmpversion( rs.getString( 26 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_DEVICE_INFO";
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'DEVID = :devid'.
	 */
	@Transactional
	public TDeviceInfo findByPrimaryKey(long devid) throws TDeviceInfoDaoException
	{
		try {
			List<TDeviceInfo> list = jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE DEVID = ?", this,devid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria ''.
	 */
	@Transactional
	public List<TDeviceInfo> findAll() throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " ORDER BY DEVID", this);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'DEVID = :devid'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereDevidEquals(long devid) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE DEVID = ? ORDER BY DEVID", this,devid);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'DEVIP = :devip'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereDevipEquals(String devip) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE DEVIP = ? ORDER BY DEVIP", this,devip);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'IPDECODE = :ipdecode'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereIpdecodeEquals(long ipdecode) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE IPDECODE = ? ORDER BY IPDECODE", this,ipdecode);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'SYSNAME = :sysname'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereSysnameEquals(String sysname) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE SYSNAME = ? ORDER BY SYSNAME", this,sysname);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'SYSNAMEALIAS = :sysnamealias'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereSysnamealiasEquals(String sysnamealias) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE SYSNAMEALIAS = ? ORDER BY SYSNAMEALIAS", this,sysnamealias);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'RSNO = :rsno'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereRsnoEquals(String rsno) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE RSNO = ? ORDER BY RSNO", this,rsno);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'SRID = :srid'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereSridEquals(long srid) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE SRID = ? ORDER BY SRID", this,srid);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'ADMIN = :admin'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereAdminEquals(String admin) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE ADMIN = ? ORDER BY ADMIN", this,admin);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'PHONE = :phone'.
	 */
	@Transactional
	public List<TDeviceInfo> findWherePhoneEquals(String phone) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE PHONE = ? ORDER BY PHONE", this,phone);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'MRID = :mrid'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereMridEquals(long mrid) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE MRID = ? ORDER BY MRID", this,mrid);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'DTID = :dtid'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereDtidEquals(long dtid) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE DTID = ? ORDER BY DTID", this,dtid);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'SERIALID = :serialid'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereSerialidEquals(String serialid) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE SERIALID = ? ORDER BY SERIALID", this,serialid);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'SWVERSION = :swversion'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereSwversionEquals(String swversion) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE SWVERSION = ? ORDER BY SWVERSION", this,swversion);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'RAMSIZE = :ramsize'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereRamsizeEquals(long ramsize) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE RAMSIZE = ? ORDER BY RAMSIZE", this,ramsize);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'RAMUNIT = :ramunit'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereRamunitEquals(String ramunit) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE RAMUNIT = ? ORDER BY RAMUNIT", this,ramunit);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'NVRAMSIZE = :nvramsize'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereNvramsizeEquals(long nvramsize) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE NVRAMSIZE = ? ORDER BY NVRAMSIZE", this,nvramsize);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'NVRAMUNIT = :nvramunit'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereNvramunitEquals(String nvramunit) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE NVRAMUNIT = ? ORDER BY NVRAMUNIT", this,nvramunit);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'FLASHSIZE = :flashsize'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereFlashsizeEquals(long flashsize) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE FLASHSIZE = ? ORDER BY FLASHSIZE", this,flashsize);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'FLASHUNIT = :flashunit'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereFlashunitEquals(String flashunit) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE FLASHUNIT = ? ORDER BY FLASHUNIT", this,flashunit);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'FLASHFILENAME = :flashfilename'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereFlashfilenameEquals(String flashfilename) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE FLASHFILENAME = ? ORDER BY FLASHFILENAME", this,flashfilename);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'FLASHFILESIZE = :flashfilesize'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereFlashfilesizeEquals(String flashfilesize) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE FLASHFILESIZE = ? ORDER BY FLASHFILESIZE", this,flashfilesize);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'RCOMMUNITY = :rcommunity'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereRcommunityEquals(String rcommunity) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE RCOMMUNITY = ? ORDER BY RCOMMUNITY", this,rcommunity);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'WCOMMUNITY = :wcommunity'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereWcommunityEquals(String wcommunity) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE WCOMMUNITY = ? ORDER BY WCOMMUNITY", this,wcommunity);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereDescriptionEquals(String description) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'DOMAINID = :domainid'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereDomainidEquals(long domainid) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE DOMAINID = ? ORDER BY DOMAINID", this,domainid);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'SNMPVERSION = :snmpversion'.
	 */
	@Transactional
	public List<TDeviceInfo> findWhereSnmpversionEquals(String snmpversion) throws TDeviceInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + " WHERE SNMPVERSION = ? ORDER BY SNMPVERSION", this,snmpversion);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_DEVICE_INFO table that matches the specified primary-key value.
	 */
	public TDeviceInfo findByPrimaryKey(TDeviceInfoPk pk) throws TDeviceInfoDaoException
	{
		return findByPrimaryKey( pk.getDevid() );
	}


	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'IPDECODE  between a list of :ipdecode_min and :ipscope_max' .
	 * 
	 */
	@Transactional
	public List<TDeviceInfo> findWhereIpdecodeBetweenScopes(long[] min, long [] max) throws TDeviceInfoDaoException
	{
		try {
			if(min.length != max.length) {
				throw new TDeviceInfoDaoException("the min max pair not the same length for the between !");
			}
			else if (min.length==0 ||max.length==0){
				//throw new TDeviceInfoDaoException("Zero length of the min max pair for the between !");
				return new ArrayList<TDeviceInfo>();
			}
			StringBuffer whereString = new StringBuffer(" WHERE ");
			for (int i=0; i<min.length;i++){				
					whereString.append(" IPDECODE between "+min[i]+" and "+ max[i]+" ");
					if (i<min.length-1){
						whereString.append(" or ");
					}				
				
			}
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + 
					whereString.toString()
					//" WHERE IPDECODE = ? ORDER BY IPDECODE"
					, this);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}



	public List<TDeviceInfo> findDevInExcel(String[] devips)
			throws TDeviceInfoDaoException {
		try {
			if (devips.length==0){
				//throw new TDeviceInfoDaoException("Zero length of the min max pair for the between !");
				return new ArrayList<TDeviceInfo>();
			}
			System.out.println("devips length is : "+devips.length);
			StringBuffer whereString = new StringBuffer(" WHERE devip in( ");
			for (int i=0; i<devips.length-1;i++){				
					whereString.append("'"+devips[i]+"' , ");
			}
			       whereString.append("'"+devips[devips.length - 1]+"'"+")");
			       System.out.println("whereString is : "+whereString.toString() );
			return jdbcTemplate.query("SELECT DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION FROM " + getTableName() + 
					whereString.toString()
					//" WHERE IPDECODE = ? ORDER BY IPDECODE"
					, this);
		}
		catch (Exception e) {
			throw new TDeviceInfoDaoException("Query failed", e);
		}
		
	}
	
}
