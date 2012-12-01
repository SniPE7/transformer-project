package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TLineInfoDao;
import com.ibm.ncs.model.dto.TLineInfo;
import com.ibm.ncs.model.dto.TLineInfoPk;
import com.ibm.ncs.model.exceptions.TLineInfoDaoException;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TLineInfoDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TLineInfo>, TLineInfoDao
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
	 * @return TLineInfoPk
	 */
	@Transactional
	public TLineInfoPk insert(TLineInfo dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( LEID, LINENAME, LENO, CATEGORY, BANDWIDTH, BANDWIDTHUNIT, APPLYTIME, OPENTIME, DESCRIPTION ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getLeid(),dto.getLinename(),dto.getLeno(),dto.isCategoryNull()?null:dto.getCategory(),dto.isBandwidthNull()?null:dto.getBandwidth(),dto.getBandwidthunit(),dto.getApplytime(),dto.getOpentime(),dto.getDescription());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_LINE_INFO table.
	 */
	@Transactional
	public void update(TLineInfoPk pk, TLineInfo dto) throws TLineInfoDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET LEID = ?, LINENAME = ?, LENO = ?, CATEGORY = ?, BANDWIDTH = ?, BANDWIDTHUNIT = ?, APPLYTIME = ?, OPENTIME = ?, DESCRIPTION = ? WHERE LEID = ?",dto.getLeid(),dto.getLinename(),dto.getLeno(),dto.getCategory(),dto.getBandwidth(),dto.getBandwidthunit(),dto.getApplytime(),dto.getOpentime(),dto.getDescription(),pk.getLeid());
	}

	/** 
	 * Deletes a single row in the T_LINE_INFO table.
	 */
	@Transactional
	public void delete(TLineInfoPk pk) throws TLineInfoDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE LEID = ?",pk.getLeid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TLineInfo
	 */
	public TLineInfo mapRow(ResultSet rs, int row) throws SQLException
	{
		TLineInfo dto = new TLineInfo();
		dto.setLeid( rs.getLong( 1 ) );
		dto.setLinename( rs.getString( 2 ) );
		dto.setLeno( rs.getString( 3 ) );
		dto.setCategory( rs.getLong( 4 ) );
		if (rs.wasNull()) {
			dto.setCategoryNull( true );
		}
		
		dto.setBandwidth( rs.getLong( 5 ) );
		if (rs.wasNull()) {
			dto.setBandwidthNull( true );
		}
		
		dto.setBandwidthunit( rs.getString( 6 ) );
		dto.setApplytime( rs.getTimestamp(7 ) );
		dto.setOpentime( rs.getTimestamp(8 ) );
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
		return "T_LINE_INFO";
	}

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'LEID = :leid'.
	 */
	@Transactional
	public TLineInfo findByPrimaryKey(long leid) throws TLineInfoDaoException
	{
		try {
			List<TLineInfo> list = jdbcTemplate.query("SELECT LEID, LINENAME, LENO, CATEGORY, BANDWIDTH, BANDWIDTHUNIT, APPLYTIME, OPENTIME, DESCRIPTION FROM " + getTableName() + " WHERE LEID = ?", this,leid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TLineInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria ''.
	 */
	@Transactional
	public List<TLineInfo> findAll() throws TLineInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT LEID, LINENAME, LENO, CATEGORY, BANDWIDTH, BANDWIDTHUNIT, APPLYTIME, OPENTIME, DESCRIPTION FROM " + getTableName() + " ORDER BY LEID", this);
		}
		catch (Exception e) {
			throw new TLineInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'LEID = :leid'.
	 */
	@Transactional
	public List<TLineInfo> findWhereLeidEquals(long leid) throws TLineInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT LEID, LINENAME, LENO, CATEGORY, BANDWIDTH, BANDWIDTHUNIT, APPLYTIME, OPENTIME, DESCRIPTION FROM " + getTableName() + " WHERE LEID = ? ORDER BY LEID", this,leid);
		}
		catch (Exception e) {
			throw new TLineInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'LINENAME = :linename'.
	 */
	@Transactional
	public List<TLineInfo> findWhereLinenameEquals(String linename) throws TLineInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT LEID, LINENAME, LENO, CATEGORY, BANDWIDTH, BANDWIDTHUNIT, APPLYTIME, OPENTIME, DESCRIPTION FROM " + getTableName() + " WHERE LINENAME = ? ORDER BY LINENAME", this,linename);
		}
		catch (Exception e) {
			throw new TLineInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'LENO = :leno'.
	 */
	@Transactional
	public List<TLineInfo> findWhereLenoEquals(String leno) throws TLineInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT LEID, LINENAME, LENO, CATEGORY, BANDWIDTH, BANDWIDTHUNIT, APPLYTIME, OPENTIME, DESCRIPTION FROM " + getTableName() + " WHERE LENO = ? ORDER BY LENO", this,leno);
		}
		catch (Exception e) {
			throw new TLineInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'CATEGORY = :category'.
	 */
	@Transactional
	public List<TLineInfo> findWhereCategoryEquals(long category) throws TLineInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT LEID, LINENAME, LENO, CATEGORY, BANDWIDTH, BANDWIDTHUNIT, APPLYTIME, OPENTIME, DESCRIPTION FROM " + getTableName() + " WHERE CATEGORY = ? ORDER BY CATEGORY", this,category);
		}
		catch (Exception e) {
			throw new TLineInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'BANDWIDTH = :bandwidth'.
	 */
	@Transactional
	public List<TLineInfo> findWhereBandwidthEquals(long bandwidth) throws TLineInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT LEID, LINENAME, LENO, CATEGORY, BANDWIDTH, BANDWIDTHUNIT, APPLYTIME, OPENTIME, DESCRIPTION FROM " + getTableName() + " WHERE BANDWIDTH = ? ORDER BY BANDWIDTH", this,bandwidth);
		}
		catch (Exception e) {
			throw new TLineInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'BANDWIDTHUNIT = :bandwidthunit'.
	 */
	@Transactional
	public List<TLineInfo> findWhereBandwidthunitEquals(String bandwidthunit) throws TLineInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT LEID, LINENAME, LENO, CATEGORY, BANDWIDTH, BANDWIDTHUNIT, APPLYTIME, OPENTIME, DESCRIPTION FROM " + getTableName() + " WHERE BANDWIDTHUNIT = ? ORDER BY BANDWIDTHUNIT", this,bandwidthunit);
		}
		catch (Exception e) {
			throw new TLineInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'APPLYTIME = :applytime'.
	 */
	@Transactional
	public List<TLineInfo> findWhereApplytimeEquals(Date applytime) throws TLineInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT LEID, LINENAME, LENO, CATEGORY, BANDWIDTH, BANDWIDTHUNIT, APPLYTIME, OPENTIME, DESCRIPTION FROM " + getTableName() + " WHERE APPLYTIME = ? ORDER BY APPLYTIME", this,applytime);
		}
		catch (Exception e) {
			throw new TLineInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'OPENTIME = :opentime'.
	 */
	@Transactional
	public List<TLineInfo> findWhereOpentimeEquals(Date opentime) throws TLineInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT LEID, LINENAME, LENO, CATEGORY, BANDWIDTH, BANDWIDTHUNIT, APPLYTIME, OPENTIME, DESCRIPTION FROM " + getTableName() + " WHERE OPENTIME = ? ORDER BY OPENTIME", this,opentime);
		}
		catch (Exception e) {
			throw new TLineInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TLineInfo> findWhereDescriptionEquals(String description) throws TLineInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT LEID, LINENAME, LENO, CATEGORY, BANDWIDTH, BANDWIDTHUNIT, APPLYTIME, OPENTIME, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TLineInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_LINE_INFO table that matches the specified primary-key value.
	 */
	public TLineInfo findByPrimaryKey(TLineInfoPk pk) throws TLineInfoDaoException
	{
		return findByPrimaryKey( pk.getLeid() );
	}

}
