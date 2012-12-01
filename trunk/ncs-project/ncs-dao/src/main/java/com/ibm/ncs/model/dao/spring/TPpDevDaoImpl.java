package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TPpDevDao;
import com.ibm.ncs.model.dto.TPpDev;
import com.ibm.ncs.model.dto.TPpDevPk;
import com.ibm.ncs.model.exceptions.TPpDevDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TPpDevDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TPpDev>, TPpDevDao
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
	 * @return TPpDevPk
	 */
	@Transactional
	public TPpDevPk insert(TPpDev dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( DEVIP, BTIME, ETIME ) VALUES ( ?, ?, ? )",dto.getDevip(),dto.getBtime(),dto.getEtime());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_PP_DEV table.
	 */
	@Transactional
	public void update(TPpDevPk pk, TPpDev dto) throws TPpDevDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET DEVIP = ?, BTIME = ?, ETIME = ? WHERE DEVIP = ?",dto.getDevip(),dto.getBtime(),dto.getEtime(),pk.getDevip());
	}

	/** 
	 * Deletes a single row in the T_PP_DEV table.
	 */
	@Transactional
	public void delete(TPpDevPk pk) throws TPpDevDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE DEVIP = ?",pk.getDevip());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TPpDev
	 */
	public TPpDev mapRow(ResultSet rs, int row) throws SQLException
	{
		TPpDev dto = new TPpDev();
		dto.setDevip( rs.getString( 1 ) );
		dto.setBtime( rs.getString( 2 ) );
		dto.setEtime( rs.getString( 3 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_PP_DEV";
	}

	/** 
	 * Returns all rows from the T_PP_DEV table that match the criteria 'DEVIP = :devip'.
	 */
	@Transactional
	public TPpDev findByPrimaryKey(String devip) throws TPpDevDaoException
	{
		try {
			List<TPpDev> list = jdbcTemplate.query("SELECT DEVIP, BTIME, ETIME FROM " + getTableName() + " WHERE DEVIP = ?", this,devip);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TPpDevDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PP_DEV table that match the criteria ''.
	 */
	@Transactional
	public List<TPpDev> findAll() throws TPpDevDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, BTIME, ETIME FROM " + getTableName() + " ORDER BY DEVIP", this);
		}
		catch (Exception e) {
			throw new TPpDevDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PP_DEV table that match the criteria 'DEVIP = :devip'.
	 */
	@Transactional
	public List<TPpDev> findWhereDevipEquals(String devip) throws TPpDevDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, BTIME, ETIME FROM " + getTableName() + " WHERE DEVIP = ? ORDER BY DEVIP", this,devip);
		}
		catch (Exception e) {
			throw new TPpDevDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PP_DEV table that match the criteria 'BTIME = :btime'.
	 */
	@Transactional
	public List<TPpDev> findWhereBtimeEquals(String btime) throws TPpDevDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, BTIME, ETIME FROM " + getTableName() + " WHERE BTIME = ? ORDER BY BTIME", this,btime);
		}
		catch (Exception e) {
			throw new TPpDevDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PP_DEV table that match the criteria 'ETIME = :etime'.
	 */
	@Transactional
	public List<TPpDev> findWhereEtimeEquals(String etime) throws TPpDevDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, BTIME, ETIME FROM " + getTableName() + " WHERE ETIME = ? ORDER BY ETIME", this,etime);
		}
		catch (Exception e) {
			throw new TPpDevDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_PP_DEV table that matches the specified primary-key value.
	 */
	public TPpDev findByPrimaryKey(TPpDevPk pk) throws TPpDevDaoException
	{
		return findByPrimaryKey( pk.getDevip() );
	}

	/** 
	 * Deletes all rows in the T_PP_DEV table.
	 */
	@Transactional
	public int deleteAll() 
	{
		int ret = -1;
		ret = jdbcTemplate.update("DELETE FROM " + getTableName()  );
		return ret;
	}
	

	/**
	 * Method 'insertEffect'
	 * 
	 * @return rows affected
	 */
	@Transactional
	public int insertEffect() 
	{
		int ret = -1; //success flag; 
		String sqldev  = "select distinct devip, btime, etime from v_pp_dev ";
		try {
			ret  = jdbcTemplate.update("INSERT INTO " + getTableName() +"( "+ sqldev +" )");
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 
		return ret;
	}
}
