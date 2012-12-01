package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.PredefmibInfoDao;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.PredefmibInfoPk;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.exceptions.PredefmibInfoDaoException;
import com.ibm.ncs.model.exceptions.TPortInfoDaoException;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class PredefmibInfoDaoImpl extends AbstractDAO implements ParameterizedRowMapper<PredefmibInfo>, PredefmibInfoDao
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
	 * @return PredefmibInfoPk
	 */
	@Transactional
	public PredefmibInfoPk insert(PredefmibInfo dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( PDMID, MID, DEVID, OIDINDEX, OIDNAME ) VALUES ( ?, ?, ?, ?, ? )",dto.getPdmid(),dto.isMidNull()?null:dto.getMid(),dto.isDevidNull()?null:dto.getDevid(),dto.getOidindex(),dto.getOidname());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the PREDEFMIB_INFO table.
	 */
	@Transactional
	public void update(PredefmibInfoPk pk, PredefmibInfo dto) throws PredefmibInfoDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET PDMID = ?, MID = ?, DEVID = ?, OIDINDEX = ?, OIDNAME = ? WHERE PDMID = ?",dto.getPdmid(),dto.getMid(),dto.getDevid(),dto.getOidindex(),dto.getOidname(),pk.getPdmid());
	}

	/** 
	 * Deletes a single row in the PREDEFMIB_INFO table.
	 */
	@Transactional
	public void delete(PredefmibInfoPk pk) throws PredefmibInfoDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PDMID = ?",pk.getPdmid());
	}
	/**
	 * Delete multi rows for selected devid
	 * @param devid
	 * @throws PredefmibInfoDaoException
	 */
	public void deleteByDevid(long devid) throws PredefmibInfoDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE DEVID = ?",devid);
	}
	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return PredefmibInfo
	 */
	public PredefmibInfo mapRow(ResultSet rs, int row) throws SQLException
	{
		PredefmibInfo dto = new PredefmibInfo();
		dto.setPdmid( rs.getLong( 1 ) );
		dto.setMid( rs.getLong( 2 ) );
		if (rs.wasNull()) {
			dto.setMidNull( true );
		}
		
		dto.setDevid( rs.getLong( 3 ) );
		if (rs.wasNull()) {
			dto.setDevidNull( true );
		}
		
		dto.setOidindex( rs.getString( 4 ) );
		dto.setOidname( rs.getString( 5 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "PREDEFMIB_INFO";
	}

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'PDMID = :pdmid'.
	 */
	@Transactional
	public PredefmibInfo findByPrimaryKey(long pdmid) throws PredefmibInfoDaoException
	{
		try {
			List<PredefmibInfo> list = jdbcTemplate.query("SELECT PDMID, MID, DEVID, OIDINDEX, OIDNAME FROM " + getTableName() + " WHERE PDMID = ?", this,pdmid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new PredefmibInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria ''.
	 */
	@Transactional
	public List<PredefmibInfo> findAll() throws PredefmibInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MID, DEVID, OIDINDEX, OIDNAME FROM " + getTableName() + " ORDER BY PDMID", this);
		}
		catch (Exception e) {
			throw new PredefmibInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'ID = :id'.
	 */
	@Transactional
	public List<PredefmibInfo> findByDefMibGrp(long mid) throws PredefmibInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MID, DEVID, OIDINDEX, OIDNAME FROM " + getTableName() + " WHERE MID = ?", this,mid);
		}
		catch (Exception e) {
			throw new PredefmibInfoDaoException("Query failed", e);
		}
		
	}
	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'PDMID = :pdmid'.
	 */
	@Transactional
	public List<PredefmibInfo> findWherePdmidEquals(long pdmid) throws PredefmibInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MID, DEVID, OIDINDEX, OIDNAME FROM " + getTableName() + " WHERE PDMID = ? ORDER BY PDMID", this,pdmid);
		}
		catch (Exception e) {
			throw new PredefmibInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'MID = :mid'.
	 */
	@Transactional
	public List<PredefmibInfo> findWhereMidEquals(long mid) throws PredefmibInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MID, DEVID, OIDINDEX, OIDNAME FROM " + getTableName() + " WHERE MID = ? ORDER BY MID", this,mid);
		}
		catch (Exception e) {
			throw new PredefmibInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'DEVID = :devid'.
	 */
	@Transactional
	public List<PredefmibInfo> findWhereDevidEquals(long devid) throws PredefmibInfoDaoException
	{
		try {
			//System.out.println("sql=SELECT PDMID, MID, DEVID, OIDINDEX, OIDNAME FROM " + getTableName() + " WHERE DEVID = "+devid+" ORDER BY DEVID,mid,pdmid");
			return jdbcTemplate.query("SELECT PDMID, MID, DEVID, OIDINDEX, OIDNAME FROM " + getTableName() + " WHERE DEVID = ? ORDER BY DEVID,mid,pdmid", this,devid);
		}
		catch (Exception e) {
			throw new PredefmibInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'OIDINDEX = :oidindex'.
	 */
	@Transactional
	public List<PredefmibInfo> findWhereOidindexEquals(String oidindex) throws PredefmibInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MID, DEVID, OIDINDEX, OIDNAME FROM " + getTableName() + " WHERE OIDINDEX = ? ORDER BY OIDINDEX", this,oidindex);
		}
		catch (Exception e) {
			throw new PredefmibInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'OIDNAME = :oidname'.
	 */
	@Transactional
	public List<PredefmibInfo> findWhereOidnameEquals(String oidname) throws PredefmibInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PDMID, MID, DEVID, OIDINDEX, OIDNAME FROM " + getTableName() + " WHERE OIDNAME = ? ORDER BY OIDNAME", this,oidname);
		}
		catch (Exception e) {
			throw new PredefmibInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the PREDEFMIB_INFO table that matches the specified primary-key value.
	 */
	public PredefmibInfo findByPrimaryKey(PredefmibInfoPk pk) throws PredefmibInfoDaoException
	{
		return findByPrimaryKey( pk.getPdmid() );
	}

	/** 
	 * Deletes rows from the PREDEFMIB_INFO table that match devid and not in list of MID.
	 */
	@Transactional
	public void deleteByDevidAndNotInMid(long devid, String[] midArray) throws PredefmibInfoDaoException
	{
		StringBuffer wherestring = new StringBuffer();
		for(int i=0;i<midArray.length;i++){
			if(i==0){wherestring.append(midArray[i]);}
			else{wherestring.append(", "+midArray[i]);}
		}
		System.out.println("PREDEFMIB_INFO.deleteByDevidAndNotInMid...midarray..wherestring="+wherestring);
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE devid=? and MID not in ("+wherestring.toString()+")",devid);
	}
	
	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match devid and in mid list.
	 */
	@Transactional
	public List<PredefmibInfo> findByDevidAndMidList(long devid, String[] midArray) throws PredefmibInfoDaoException
	{
		StringBuffer wherestring = new StringBuffer();
		for(int i=0;i<midArray.length;i++){
			if(i==0){wherestring.append(midArray[i]);}
			else{wherestring.append(", "+midArray[i]);}
		}
		System.out.println("PREDEFMIB_INFO.findByDevidAndMidList...midarray..wherestring="+wherestring);
		try {
			return jdbcTemplate.query("SELECT PDMID, MID, DEVID, OIDINDEX, OIDNAME FROM " + getTableName() + " WHERE DEVID = ? and MID  in ("+wherestring.toString()+")", this,devid);
		}
		catch (Exception e) {
			throw new PredefmibInfoDaoException("Query failed", e);
		}
		
	}

	public PredefmibInfo findByDevidAndOidname(long devid, String oidname)
			throws PredefmibInfoDaoException {
		try {
			List<PredefmibInfo> list = jdbcTemplate.query("SELECT * FROM " + getTableName() + " WHERE DEVID = ? AND OIDNAME = ?", this,devid,oidname);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new PredefmibInfoDaoException("Query failed", e);
		}
	}
	
	@Transactional
	public void deleteByDevidAndNotInOidname(long devid, String[] oidnameArray) throws PredefmibInfoDaoException
	{
		if (oidnameArray==null) return;
		int intmp = oidnameArray.length;		
		int loop = intmp / 900 ; 
		List<TPortInfo> ret = new ArrayList<TPortInfo>();
		for (int x=0; x <= loop; x++){
			
			StringBuffer wherestring = new StringBuffer();
			for(int i=x*900; (i<oidnameArray.length)&&(i<900+x*900) ;i++){
				wherestring.append(", '"+oidnameArray[i]+"'");
			}	
			wherestring.replace(0, 1, "");
			System.out.println("PREDEFMIB_INFO .findByDevidAndOidnameList... whereString=\n"+wherestring);
			try {
				jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE DEVID = ? and oidname not in ("+wherestring.toString()+")",devid);
				
			}
			catch (Exception e) {
				throw new PredefmibInfoDaoException("Query failed", e);
			}
			
		}
		
	
	}

	public List findByDevidJoingrp(long devid) throws PredefmibInfoDaoException {
		try {
			return jdbcTemplate.query("SELECT * FROM PREDEFMIB_INFO a,DEF_MIB_GRP b WHERE a.MID = b.MID AND a.DEVID = ?", this,devid);
		}
		catch (Exception e) {
			throw new PredefmibInfoDaoException("Query failed", e);
		}
	}
	
	public PredefmibInfo findByDevidAndOidnameAndMid(long devid,long mid,String oidname) throws PredefmibInfoDaoException{
		try {
			List<PredefmibInfo> list = jdbcTemplate.query("SELECT * FROM PREDEFMIB_INFO  WHERE DEVID=? AND MID = ? AND OIDNAME = ?", this,devid,mid,oidname);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new PredefmibInfoDaoException("Query failed", e);
		}
	}

	public void deleteByDevidAndMidAndNotInOidname(long devid, long mid,
			String[] oidnameArray) throws PredefmibInfoDaoException {
		if (oidnameArray==null) return;
		int intmp = oidnameArray.length;		
		int loop = intmp / 900 ; 
		List<TPortInfo> ret = new ArrayList<TPortInfo>();
		for (int x=0; x <= loop; x++){
			
			StringBuffer wherestring = new StringBuffer();
			for(int i=x*900; (i<oidnameArray.length)&&(i<900+x*900) ;i++){
				wherestring.append(", '"+oidnameArray[i]+"'");
			}	
			wherestring.replace(0, 1, "");
			System.out.println("PredefMibInfoDao .deleteByDevidAndMidAndNotInOidname... whereString=\n"+wherestring);
			String sql = "DELETE FROM " + getTableName() + " WHERE DEVID = ? and MID=? and oidname not in ("+wherestring.toString()+")";
			System.out.println("delete sqp is "+sql);
			try {
				jdbcTemplate.update(sql,devid, mid);
				
			}
			catch (Exception e) {
				throw new PredefmibInfoDaoException("Query failed", e);
			}
			
		}
		
	}

	public List<PredefmibInfo> findByDevidAndOidnameAndMid(long devid,
			long mid, String[] oidnameArray) throws PredefmibInfoDaoException {
				
		if (oidnameArray==null) return null;

		int intmp = oidnameArray.length;		
		int loop = intmp / 900 ; 
		System.out.println("intmp="+intmp+"; loop="+loop);
		List<PredefmibInfo> ret = new ArrayList<PredefmibInfo>();
		for (int x=0; x <= loop; x++){
			
			StringBuffer wherestring = new StringBuffer();
			for(int i=x*900; (i<oidnameArray.length)&&(i<900+x*900) ;i++){
				wherestring.append(", '"+oidnameArray[i]+"'");
				System.out.println("loop i="+i+"; x="+x);
			}	
			wherestring.replace(0, 1, " ");
			System.out.println("PREDEFMIB_INFO.findByDevidAndOidnameAndMid...oidnameArray..wherestring="+wherestring);
			String sqlloop = "SELECT PDMID, MID, DEVID, OIDINDEX, OIDNAME FROM " + getTableName() + " WHERE DEVID = ? AND MID=? and OIDNAME  in ("+wherestring.toString()+")";
			System.out.println("x="+x+"; sqlloop="+sqlloop);
			try {
				List<PredefmibInfo> ret0 =  jdbcTemplate.query(sqlloop, this,devid,mid);
				ret.addAll(ret0);
			}
			catch (Exception e) {
				throw new PredefmibInfoDaoException("Query failed", e);
			}
			
		}
		return ret;
	}

	public void deleteByDevidAndMid(long devid,long mid) throws PredefmibInfoDaoException {
		{
			jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE DEVID=? AND MID = ?",devid,mid);
		}		
	}
	
	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match devid and in mid list.
	 */
	
}
