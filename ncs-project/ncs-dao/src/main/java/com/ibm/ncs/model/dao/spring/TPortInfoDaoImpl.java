package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.dto.TPortInfoPk;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.exceptions.TPortInfoDaoException;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TPortInfoDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TPortInfo>, TPortInfoDao
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
	 * @return TPortInfoPk
	 */
	@Transactional
	public TPortInfoPk insert(TPortInfo dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getPtid(),dto.getDevid(),dto.isIfindexNull()?null:dto.getIfindex(),dto.getIfip(),dto.isIpdecodeIfipNull()?null:dto.getIpdecodeIfip(),dto.getIfmac(),dto.getIfoperstatus(),dto.getIfdescr(),dto.getDescription());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_PORT_INFO table.
	 */
	@Transactional
	public void update(TPortInfoPk pk, TPortInfo dto) throws TPortInfoDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET PTID = ?, DEVID = ?, IFINDEX = ?, IFIP = ?, IPDECODE_IFIP = ?, IFMAC = ?, IFOPERSTATUS = ?, IFDESCR = ?, DESCRIPTION = ? WHERE PTID = ?",dto.getPtid(),dto.getDevid(),dto.getIfindex(),dto.getIfip(),dto.getIpdecodeIfip(),dto.getIfmac(),dto.getIfoperstatus(),dto.getIfdescr(),dto.getDescription(),pk.getPtid());
	}

	/** 
	 * Deletes a single row in the T_PORT_INFO table.
	 */
	@Transactional
	public void delete(TPortInfoPk pk) throws TPortInfoDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE PTID = ?",pk.getPtid());
	}

	/** 
	 * Deletes a multi rows in the T_PORT_INFO table by devid.
	 */
	@Transactional
	public void delete(long devid) throws TPortInfoDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE DEVID = ?",devid);
	}
	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TPortInfo
	 */
	public TPortInfo mapRow(ResultSet rs, int row) throws SQLException
	{
		TPortInfo dto = new TPortInfo();
		dto.setPtid( rs.getLong( 1 ) );
		dto.setDevid( rs.getLong( 2 ) );
		dto.setIfindex( rs.getLong( 3 ) );
		if (rs.wasNull()) {
			dto.setIfindexNull( true );
		}
		
		dto.setIfip( rs.getString( 4 ) );
		dto.setIpdecodeIfip( rs.getLong( 5 ) );
		if (rs.wasNull()) {
			dto.setIpdecodeIfipNull( true );
		}
		
		dto.setIfmac( rs.getString( 6 ) );
		dto.setIfoperstatus( rs.getString( 7 ) );
		dto.setIfdescr( rs.getString( 8 ) );
		dto.setDescription( rs.getString( 9 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_PORT_INFO";
	}

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'PTID = :ptid'.
	 */
	@Transactional
	public TPortInfo findByPrimaryKey(long ptid) throws TPortInfoDaoException
	{
		try {
			List<TPortInfo> list = jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE PTID = ?", this,ptid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TPortInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria ''.
	 */
	@Transactional
	public List<TPortInfo> findAll() throws TPortInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " ORDER BY PTID", this);
		}
		catch (Exception e) {
			throw new TPortInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'PTID = :ptid'.
	 */
	@Transactional
	public List<TPortInfo> findWherePtidEquals(long ptid) throws TPortInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE PTID = ? ORDER BY PTID", this,ptid);
		}
		catch (Exception e) {
			throw new TPortInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'DEVID = :devid'.
	 */
	@Transactional
	public List<TPortInfo> findWhereDevidEquals(long devid) throws TPortInfoDaoException
	{
		try {
//			String sql="SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE DEVID = ? ORDER BY DEVID";
//			System.out.println("sql="+sql);
			return jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE DEVID = ? ORDER BY DEVID", this,devid);
		}
		catch (Exception e) {
			throw new TPortInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'IFINDEX = :ifindex'.
	 */
	@Transactional
	public List<TPortInfo> findWhereIfindexEquals(long ifindex) throws TPortInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE IFINDEX = ? ORDER BY IFINDEX", this,ifindex);
		}
		catch (Exception e) {
			throw new TPortInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'IFIP = :ifip'.
	 */
	@Transactional
	public List<TPortInfo> findWhereIfipEquals(String ifip) throws TPortInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE IFIP = ? ORDER BY IFIP", this,ifip);
		}
		catch (Exception e) {
			throw new TPortInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'IPDECODE_IFIP = :ipdecodeIfip'.
	 */
	@Transactional
	public List<TPortInfo> findWhereIpdecodeIfipEquals(long ipdecodeIfip) throws TPortInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE IPDECODE_IFIP = ? ORDER BY IPDECODE_IFIP", this,ipdecodeIfip);
		}
		catch (Exception e) {
			throw new TPortInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'IFMAC = :ifmac'.
	 */
	@Transactional
	public List<TPortInfo> findWhereIfmacEquals(String ifmac) throws TPortInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE IFMAC = ? ORDER BY IFMAC", this,ifmac);
		}
		catch (Exception e) {
			throw new TPortInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'IFOPERSTATUS = :ifoperstatus'.
	 */
	@Transactional
	public List<TPortInfo> findWhereIfoperstatusEquals(String ifoperstatus) throws TPortInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE IFOPERSTATUS = ? ORDER BY IFOPERSTATUS", this,ifoperstatus);
		}
		catch (Exception e) {
			throw new TPortInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'IFDESCR = :ifdescr'.
	 */
	@Transactional
	public List<TPortInfo> findWhereIfdescrEquals(String ifdescr) throws TPortInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE IFDESCR = ? ORDER BY IFDESCR", this,ifdescr);
		}
		catch (Exception e) {
			throw new TPortInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TPortInfo> findWhereDescriptionEquals(String description) throws TPortInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TPortInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_PORT_INFO table that matches the specified primary-key value.
	 */
	public TPortInfo findByPrimaryKey(TPortInfoPk pk) throws TPortInfoDaoException
	{
		return findByPrimaryKey( pk.getPtid() );
	}
	
	/** 
	 * Deletes a multi rows in the T_PORT_INFO table by devid and not in ifdescr list.
	 */
	@Transactional
	public void deleteByNotInIfdescrList(long devid, String [] ifdescrArray) throws TPortInfoDaoException
	{
		if (ifdescrArray==null) return;
//		StringBuffer wherestring = new StringBuffer();
//		for(int i=0;i<ifdescrArray.length;i++){
//			if(i==0){wherestring.append("'"+ifdescrArray[i]+"'");}
//			else{wherestring.append(", '"+ifdescrArray[i]+"'");}
//		}
//		System.out.println("T_PORT_INFO .deleteByNotInIfdescrList... whereString=\n"+wherestring);
//		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE DEVID = ? and ifdescr not in ("+wherestring.toString()+")",devid);
		int intmp = ifdescrArray.length;		
		int loop = intmp / 900 ; 
		List<TPortInfo> ret = new ArrayList<TPortInfo>();
		for (int x=0; x <= loop; x++){
			
			StringBuffer wherestring = new StringBuffer();
			for(int i=x*900; (i<ifdescrArray.length)&&(i<900+x*900) ;i++){
//				if(i==0){wherestring.append("'"+ifdescrArray[i]+"'");}
//				else {wherestring.append(", '"+ifdescrArray[i]+"'");}
				wherestring.append(", '"+ifdescrArray[i]+"'");
			}	
			wherestring.replace(0, 1, "");
//			System.out.println("T_PORT_INFO .findByDevidAndIfdescrList... whereString=\n"+wherestring);
			try {
				jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE DEVID = ? and ifdescr not in ("+wherestring.toString()+")",devid);
				
			}
			catch (Exception e) {
				throw new TPortInfoDaoException("Query failed", e);
			}
			
		}
		
	
	}
	
	/** 
	 * Returns all rows from the T_PORT_INFO table that match the devid and in the list 'IFDESCR = :ifdescr'.
	 */
	@Transactional
	public List<TPortInfo> findByDevidAndIfdescrList(long devid, String[] ifdescrArray) throws TPortInfoDaoException
	{
		if (ifdescrArray==null) return null;
//		StringBuffer wherestring = new StringBuffer();
//		for(int i=0;i<ifdescrArray.length;i++){
//			if(i==0){wherestring.append("'"+ifdescrArray[i]+"'");}
//			else {wherestring.append(", '"+ifdescrArray[i]+"'");}
//		}
		int intmp = ifdescrArray.length;		
		int loop = intmp / 900 ; 
//		System.out.println("intmp="+intmp+"; loop="+loop);
		List<TPortInfo> ret = new ArrayList<TPortInfo>();
		for (int x=0; x <= loop; x++){
			
			StringBuffer wherestring = new StringBuffer();
			for(int i=x*900; (i<ifdescrArray.length)&&(i<900+x*900) ;i++){
//				if(i==0){wherestring.append("'"+ifdescrArray[i]+"'");}
//				else {wherestring.append(", '"+ifdescrArray[i]+"'");}
				wherestring.append(", '"+ifdescrArray[i]+"'");
//				System.out.println("loop i="+i+"; x="+x);
			}	
			wherestring.replace(0, 1, " ");
//			System.out.println("T_PORT_INFO .findByDevidAndIfdescrList... whereString=\n"+wherestring);
			String sqlloop = "SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE devid=? and IFDESCR in ("+wherestring.toString()+")";
//			System.out.println("x="+x+"; sqlloop="+sqlloop);
			try {
				List<TPortInfo> ret0 =  jdbcTemplate.query(sqlloop, this,devid);
				ret.addAll(ret0);
			}
			catch (Exception e) {
				throw new TPortInfoDaoException("Query failed", e);
			}
			
		}
		return ret;
//		//--
//		if(intmp <700){
//			StringBuffer wherestring = new StringBuffer();
//			for(int i=0;i<ifdescrArray.length;i++){
//				if(i==0){wherestring.append("'"+ifdescrArray[i]+"'");}
//				else {wherestring.append(", '"+ifdescrArray[i]+"'");}
//			}	
//			//System.out.println("T_PORT_INFO .findByDevidAndIfdescrList... whereString=\n"+wherestring);
//			try {
//				return jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE devid=? and IFDESCR in ("+wherestring.toString()+")", this,devid);
//			}
//			catch (Exception e) {
//				throw new TPortInfoDaoException("Query failed", e);
//			}
//		}
//		//---
//		else if (intmp >=700 && intmp <1400){
//			StringBuffer wherestring1 = new StringBuffer();
//			for(int i=0;(i<ifdescrArray.length) || (i<700);i++){
//				if(i==0){wherestring1.append("'"+ifdescrArray[i]+"'");}
//				else {wherestring1.append(", '"+ifdescrArray[i]+"'");}
//			}
//			StringBuffer wherestring2 = new StringBuffer();
//			for(int i=700;i<ifdescrArray.length;i++){
//				if(i==0){wherestring2.append("'"+ifdescrArray[i]+"'");}
//				else {wherestring2.append(", '"+ifdescrArray[i]+"'");}
//			}
//			//System.out.println("T_PORT_INFO .findByDevidAndIfdescrList... whereString=\n"+wherestring);
//			List<TPortInfo> lst = null;
//			List<TPortInfo> lst2= null;
//			try {
//				 lst  =  jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE devid=? and IFDESCR in ("+wherestring1.toString()+")", this,devid);
//				 lst2 =  jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE devid=? and IFDESCR in ("+wherestring2.toString()+")", this,devid);
//			}
//			catch (Exception e) {
//				throw new TPortInfoDaoException("Query failed", e);
//			}
//			lst.addAll(lst2);
//			
//			return lst  ;
//		}
//		//--
//		else if (intmp >=1400){
//			StringBuffer wherestring1 = new StringBuffer();
//			for(int i=0;(i<ifdescrArray.length) || (i<700);i++){
//				if(i==0){wherestring1.append("'"+ifdescrArray[i]+"'");}
//				else {wherestring1.append(", '"+ifdescrArray[i]+"'");}
//			}
//			StringBuffer wherestring2 = new StringBuffer();
//			for(int i=700;(i<ifdescrArray.length) || (i<1400);i++){
//				if(i==0){wherestring2.append("'"+ifdescrArray[i]+"'");}
//				else {wherestring2.append(", '"+ifdescrArray[i]+"'");}
//			}
//			StringBuffer wherestring3 = new StringBuffer();
//			for(int i=1400;(i<ifdescrArray.length) ;i++){
//				if(i==0){wherestring3.append("'"+ifdescrArray[i]+"'");}
//				else {wherestring3.append(", '"+ifdescrArray[i]+"'");}
//			}
//			List lst = null; List lst2 = null; List lst3 = null;
//			try {
//				 lst =  jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE devid=? and IFDESCR in ("+wherestring1.toString()+")", this,devid);
//				 lst2 = jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE devid=? and IFDESCR in ("+wherestring2.toString()+")", this,devid);
//				 lst3 = jdbcTemplate.query("SELECT PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION FROM " + getTableName() + " WHERE devid=? and IFDESCR in ("+wherestring3.toString()+")", this,devid);
//				 lst.addAll(lst2);
//				 lst.addAll(lst3);
//				 return lst;
//			}
//			catch (Exception e) {
//				throw new TPortInfoDaoException("Query failed", e);
//			}
//		}
//		return null;
		
		
	}

	public TPortInfo findByDevidAndIFdesc(long devid,String ifdescr) throws TPortInfoDaoException {
		try {
			List<TPortInfo> list = jdbcTemplate.query("SELECT * FROM " + getTableName() + " WHERE DEVID = ? AND IFDESCR = ?", this,devid,ifdescr);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TPortInfoDaoException("Query failed", e);
		}
	}

	

}
