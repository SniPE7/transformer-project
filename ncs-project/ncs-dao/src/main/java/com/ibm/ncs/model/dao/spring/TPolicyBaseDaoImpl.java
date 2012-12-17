package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyBasePk;
import com.ibm.ncs.model.exceptions.TPolicyBaseDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TPolicyBaseDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TPolicyBase>, TPolicyBaseDao
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
	 * @return TPolicyBasePk
	 */
	@Transactional
	public TPolicyBasePk insert(TPolicyBase dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( MPID, PTVID, MPNAME, CATEGORY, DESCRIPTION ) VALUES ( ?, ?, ?, ?, ? )",dto.getMpid(),(dto.getPtvid()==0)?null:dto.getPtvid(), dto.getMpname(),dto.getCategory(),dto.getDescription());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_POLICY_BASE table.
	 */
	@Transactional
	public void update(TPolicyBasePk pk, TPolicyBase dto) throws TPolicyBaseDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET MPID = ?, PTVID = ?, MPNAME = ?, CATEGORY = ?, DESCRIPTION = ? WHERE MPID = ?",dto.getMpid(),dto.getPtvid(), dto.getMpname(),dto.getCategory(),dto.getDescription(),pk.getMpid());
	}

	/** 
	 * Deletes a single row in the T_POLICY_BASE table.
	 */
	@Transactional
	public void delete(TPolicyBasePk pk) throws TPolicyBaseDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE MPID = ?",pk.getMpid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TPolicyBase
	 */
	public TPolicyBase mapRow(ResultSet rs, int row) throws SQLException
	{
		TPolicyBase dto = new TPolicyBase();
		dto.setMpid( rs.getLong( 1 ) );
		dto.setPtvid(rs.getLong(2));
		dto.setMpname( rs.getString( 3 ) );
		dto.setCategory( rs.getLong( 4 ) );
		dto.setDescription( rs.getString( 5 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_POLICY_BASE";
	}

	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria 'MPID = :mpid'.
	 */
	@Transactional
	public TPolicyBase findByPrimaryKey(long mpid) throws TPolicyBaseDaoException
	{
		try {
			List<TPolicyBase> list = jdbcTemplate.query("SELECT MPID, PTVID, MPNAME, CATEGORY, DESCRIPTION FROM " + getTableName() + " WHERE MPID = ?", this,mpid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TPolicyBaseDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria ''.
	 */
	@Transactional
	public List<TPolicyBase> findAll() throws TPolicyBaseDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, PTVID, MPNAME, CATEGORY, DESCRIPTION FROM " + getTableName() + " ORDER BY MPID", this);
		}
		catch (Exception e) {
			throw new TPolicyBaseDaoException("Query failed", e);
		}
		
	}
	
	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria ''.
	 */
	@Transactional
	public List<TPolicyBase> findAllOrderBy(String orderbyCol) throws TPolicyBaseDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, PTVID, MPNAME, CATEGORY, DESCRIPTION FROM " + getTableName() + " ORDER BY ?", this,orderbyCol);
		}
		catch (Exception e) {
			throw new TPolicyBaseDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria 'MPID = :mpid'.
	 */
	@Transactional
	public List<TPolicyBase> findWhereMpidEquals(long mpid) throws TPolicyBaseDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, PTVID, MPNAME, CATEGORY, DESCRIPTION FROM " + getTableName() + " WHERE MPID = ? ORDER BY MPID", this,mpid);
		}
		catch (Exception e) {
			throw new TPolicyBaseDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria 'MPNAME = :mpname'.
	 */
	@Transactional
	public List<TPolicyBase> findWhereMpnameEquals(String mpname) throws TPolicyBaseDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, PTVID, MPNAME, CATEGORY, DESCRIPTION FROM " + getTableName() + " WHERE MPNAME = ? ORDER BY MPNAME", this,mpname);
		}
		catch (Exception e) {
			throw new TPolicyBaseDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria 'CATEGORY = :category'.
	 */
	@Transactional
	public List<TPolicyBase> findWhereCategoryEquals(long category) throws TPolicyBaseDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, PTVID, MPNAME, CATEGORY, DESCRIPTION FROM " + getTableName() + " WHERE CATEGORY = ? ORDER BY CATEGORY", this,category);
		}
		catch (Exception e) {
			throw new TPolicyBaseDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_POLICY_BASE table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TPolicyBase> findWhereDescriptionEquals(String description) throws TPolicyBaseDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MPID, PTVID, MPNAME, CATEGORY, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TPolicyBaseDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_POLICY_BASE table that matches the specified primary-key value.
	 */
	public TPolicyBase findByPrimaryKey(TPolicyBasePk pk) throws TPolicyBaseDaoException
	{
		return findByPrimaryKey( pk.getMpid() );
	}

}
