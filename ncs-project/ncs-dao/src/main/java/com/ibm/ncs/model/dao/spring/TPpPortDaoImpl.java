package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TPpPortDao;
import com.ibm.ncs.model.dto.TPpPort;
import com.ibm.ncs.model.dto.TPpPortPk;
import com.ibm.ncs.model.exceptions.TPpDevDaoException;
import com.ibm.ncs.model.exceptions.TPpPortDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TPpPortDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TPpPort>, TPpPortDao
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
	 * @return TPpPortPk
	 */
	@Transactional
	public TPpPortPk insert(TPpPort dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( DEVIP, IFDESCR, BTIME, ETIME ) VALUES ( ?, ?, ?, ? )",dto.getDevip(),dto.getIfdescr(),dto.getBtime(),dto.getEtime());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_PP_PORT table.
	 */
	@Transactional
	public void update(TPpPortPk pk, TPpPort dto) throws TPpPortDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET DEVIP = ?, IFDESCR = ?, BTIME = ?, ETIME = ? WHERE DEVIP = ? AND IFDESCR = ?",dto.getDevip(),dto.getIfdescr(),dto.getBtime(),dto.getEtime(),pk.getDevip(),pk.getIfdescr());
	}

	/** 
	 * Deletes a single row in the T_PP_PORT table.
	 */
	@Transactional
	public void delete(TPpPortPk pk) throws TPpPortDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE DEVIP = ? AND IFDESCR = ?",pk.getDevip(),pk.getIfdescr());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TPpPort
	 */
	public TPpPort mapRow(ResultSet rs, int row) throws SQLException
	{
		TPpPort dto = new TPpPort();
		dto.setDevip( rs.getString( 1 ) );
		dto.setIfdescr( rs.getString( 2 ) );
		dto.setBtime( rs.getString( 3 ) );
		dto.setEtime( rs.getString( 4 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_PP_PORT";
	}

	/** 
	 * Returns all rows from the T_PP_PORT table that match the criteria 'DEVIP = :devip AND IFDESCR = :ifdescr'.
	 */
	@Transactional
	public TPpPort findByPrimaryKey(String devip, String ifdescr) throws TPpPortDaoException
	{
		try {
			List<TPpPort> list = jdbcTemplate.query("SELECT DEVIP, IFDESCR, BTIME, ETIME FROM " + getTableName() + " WHERE DEVIP = ? AND IFDESCR = ?", this,devip,ifdescr);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TPpPortDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PP_PORT table that match the criteria ''.
	 */
	@Transactional
	public List<TPpPort> findAll() throws TPpPortDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, IFDESCR, BTIME, ETIME FROM " + getTableName() + " ORDER BY DEVIP, IFDESCR", this);
		}
		catch (Exception e) {
			throw new TPpPortDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PP_PORT table that match the criteria 'DEVIP = :devip'.
	 */
	@Transactional
	public List<TPpPort> findWhereDevipEquals(String devip) throws TPpPortDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, IFDESCR, BTIME, ETIME FROM " + getTableName() + " WHERE DEVIP = ? ORDER BY DEVIP", this,devip);
		}
		catch (Exception e) {
			throw new TPpPortDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PP_PORT table that match the criteria 'IFDESCR = :ifdescr'.
	 */
	@Transactional
	public List<TPpPort> findWhereIfdescrEquals(String ifdescr) throws TPpPortDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, IFDESCR, BTIME, ETIME FROM " + getTableName() + " WHERE IFDESCR = ? ORDER BY IFDESCR", this,ifdescr);
		}
		catch (Exception e) {
			throw new TPpPortDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PP_PORT table that match the criteria 'BTIME = :btime'.
	 */
	@Transactional
	public List<TPpPort> findWhereBtimeEquals(String btime) throws TPpPortDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, IFDESCR, BTIME, ETIME FROM " + getTableName() + " WHERE BTIME = ? ORDER BY BTIME", this,btime);
		}
		catch (Exception e) {
			throw new TPpPortDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_PP_PORT table that match the criteria 'ETIME = :etime'.
	 */
	@Transactional
	public List<TPpPort> findWhereEtimeEquals(String etime) throws TPpPortDaoException
	{
		try {
			return jdbcTemplate.query("SELECT DEVIP, IFDESCR, BTIME, ETIME FROM " + getTableName() + " WHERE ETIME = ? ORDER BY ETIME", this,etime);
		}
		catch (Exception e) {
			throw new TPpPortDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_PP_PORT table that matches the specified primary-key value.
	 */
	public TPpPort findByPrimaryKey(TPpPortPk pk) throws TPpPortDaoException
	{
		return findByPrimaryKey( pk.getDevip(), pk.getIfdescr() );
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
	 * @param dto
	 * @return rows affected
	 */
	@Transactional
	public int insertEffect() 
	{
		int ret = -1; //success flag; 
		String sqldev  = "select distinct devip, ifdescr, btime, etime from v_pp_port ";
		try {
			ret  = jdbcTemplate.update("INSERT INTO " + getTableName() +"( "+ sqldev +" )");
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 
		return ret;
	}
}
