package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TListIpDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TListIp>, TListIpDao
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
	public void insert(TListIp dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( GID, CATEGORY, IP, MASK, IPDECODE_MIN, IPDECODE_MAX, DESCRIPTION ) VALUES ( ?, ?, ?, ?, ?, ?, ? )",dto.getGid(),dto.getCategory(),dto.getIp(),dto.getMask(),dto.getIpdecodeMin(),dto.getIpdecodeMax(),dto.getDescription());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TListIp
	 */
	public TListIp mapRow(ResultSet rs, int row) throws SQLException
	{
		TListIp dto = new TListIp();
		dto.setGid( rs.getLong( 1 ) );
		dto.setCategory( rs.getLong( 2 ) );
		dto.setIp( rs.getString( 3 ) );
		dto.setMask( rs.getString( 4 ) );
		dto.setIpdecodeMin( rs.getLong( 5 ) );
		dto.setIpdecodeMax( rs.getLong( 6 ) );
		dto.setDescription( rs.getString( 7 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_LIST_IP";
	}

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria ''.
	 */
	@Transactional
	public List<TListIp> findAll() throws TListIpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, CATEGORY, IP, MASK, IPDECODE_MIN, IPDECODE_MAX, DESCRIPTION FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new TListIpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'GID = :gid'.
	 */
	@Transactional
	public List<TListIp> findWhereGidEquals(long gid) throws TListIpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, CATEGORY, IP, MASK, IPDECODE_MIN, IPDECODE_MAX, DESCRIPTION FROM " + getTableName() + " WHERE GID = ? ORDER BY GID", this,gid);
		}
		catch (Exception e) {
			throw new TListIpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'CATEGORY = :category'.
	 */
	@Transactional
	public List<TListIp> findWhereCategoryEquals(long category) throws TListIpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, CATEGORY, IP, MASK, IPDECODE_MIN, IPDECODE_MAX, DESCRIPTION FROM " + getTableName() + " WHERE CATEGORY = ? ORDER BY CATEGORY", this,category);
		}
		catch (Exception e) {
			throw new TListIpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'IP = :ip'.
	 */
	@Transactional
	public List<TListIp> findWhereIpEquals(String ip) throws TListIpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, CATEGORY, IP, MASK, IPDECODE_MIN, IPDECODE_MAX, DESCRIPTION FROM " + getTableName() + " WHERE IP = ? ORDER BY IP", this,ip);
		}
		catch (Exception e) {
			throw new TListIpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'MASK = :mask'.
	 */
	@Transactional
	public List<TListIp> findWhereMaskEquals(String mask) throws TListIpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, CATEGORY, IP, MASK, IPDECODE_MIN, IPDECODE_MAX, DESCRIPTION FROM " + getTableName() + " WHERE MASK = ? ORDER BY MASK", this,mask);
		}
		catch (Exception e) {
			throw new TListIpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'IPDECODE_MIN = :ipdecodeMin'.
	 */
	@Transactional
	public List<TListIp> findWhereIpdecodeMinEquals(long ipdecodeMin) throws TListIpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, CATEGORY, IP, MASK, IPDECODE_MIN, IPDECODE_MAX, DESCRIPTION FROM " + getTableName() + " WHERE IPDECODE_MIN = ? ORDER BY IPDECODE_MIN", this,ipdecodeMin);
		}
		catch (Exception e) {
			throw new TListIpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'IPDECODE_MAX = :ipdecodeMax'.
	 */
	@Transactional
	public List<TListIp> findWhereIpdecodeMaxEquals(long ipdecodeMax) throws TListIpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, CATEGORY, IP, MASK, IPDECODE_MIN, IPDECODE_MAX, DESCRIPTION FROM " + getTableName() + " WHERE IPDECODE_MAX = ? ORDER BY IPDECODE_MAX", this,ipdecodeMax);
		}
		catch (Exception e) {
			throw new TListIpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TListIp> findWhereDescriptionEquals(String description) throws TListIpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, CATEGORY, IP, MASK, IPDECODE_MIN, IPDECODE_MAX, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TListIpDaoException("Query failed", e);
		}
		
	}
	
	public void deleteByGid(long gid) throws TListIpDaoException {
		try {
			String sql="delete from "+getTableName()+" where gid=?";
			 jdbcTemplate.update(sql, gid);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete(TListIp data) throws TListIpDaoException {
//		String sql = "delete from t_list_ip where gid="+data.getGid()+
//					" and category="+ data.getCategory()+ 
//					" ipdecode_min="+ data.getIpdecodeMin()+ 
//					" ipdecode_max="+data.getIpdecodeMax();
		String sql = "delete from  "+getTableName()+" where gid=?"+
														" and category=?"+ 
														" and ipdecode_min=?"+
														" and ipdecode_max=?";
		jdbcTemplate.update(sql, this,data.getGid(),data.getCategory(),data.getIpdecodeMin(),data.getIpdecodeMax());
	}
	
	
//	public void addingIpList(List<TListIp> newiplist){
//		String sql = " insert into T_LIST_IP (GID, CATEGORY, IP, MASK, IPDECODE_MIN, IPDECODE_MAX, DESCRIPTION)  ";
//					
//		// values
//		for(TListIp dto: newiplist){
//			
//			StringBuffer values = new StringBuffer();
//			
//			values.append(" values ( ");
//			values.append(dto.getGid()).append(", ");
//			values.append(dto.getCategory()).append(", ");
//			values.append(dto.getIp()).append(", ");
//			values.append(dto.getMask()).append(", ");
//			values.append(dto.getIpdecodeMin()).append(", ");
//			values.append(dto.getIpdecodeMax()).append(", ");
//			values.append(dto.getDescription());
//			values.append(" )");
//			
//			jdbcTemplate.update(sql+values.toString(), this);
//		}
//		
//	}

}
