package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TUserDao;
import com.ibm.ncs.model.dto.TUser;
import com.ibm.ncs.model.dto.TUserPk;
import com.ibm.ncs.model.exceptions.TUserDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TUserDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TUser>, TUserDao
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
	 * @return TUserPk
	 */
	@Transactional
	public TUserPk insert(TUser dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( USID, UNAME, PASSWORD, STATUS, DESCRIPTION ) VALUES ( ?, ?, ?, ?, ? )",dto.getUsid(),dto.getUname(),dto.getPassword(),dto.getStatus(),dto.getDescription());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_USER table.
	 */
	@Transactional
	public void update(TUserPk pk, TUser dto) throws TUserDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET USID = ?, UNAME = ?, PASSWORD = ?, STATUS = ?, DESCRIPTION = ? WHERE USID = ?",dto.getUsid(),dto.getUname(),dto.getPassword(),dto.getStatus(),dto.getDescription(),pk.getUsid());
	}

	/** 
	 * Deletes a single row in the T_USER table.
	 */
	@Transactional
	public void delete(TUserPk pk) throws TUserDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE USID = ?",pk.getUsid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TUser
	 */
	public TUser mapRow(ResultSet rs, int row) throws SQLException
	{
		TUser dto = new TUser();
		dto.setUsid( rs.getLong( 1 ) );
		dto.setUname( rs.getString( 2 ) );
		dto.setPassword( rs.getString( 3 ) );
		dto.setStatus( rs.getString( 4 ) );
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
		return "T_USER";
	}

	/** 
	 * Returns all rows from the T_USER table that match the criteria 'USID = :usid'.
	 */
	@Transactional
	public TUser findByPrimaryKey(long usid) throws TUserDaoException
	{
		try {
			List<TUser> list = jdbcTemplate.query("SELECT USID, UNAME, PASSWORD, STATUS, DESCRIPTION FROM " + getTableName() + " WHERE USID = ?", this,usid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TUserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_USER table that match the criteria ''.
	 */
	@Transactional
	public List<TUser> findAll() throws TUserDaoException
	{
		try {
			return jdbcTemplate.query("SELECT USID, UNAME, PASSWORD, STATUS, DESCRIPTION FROM " + getTableName() + " ORDER BY USID", this);
		}
		catch (Exception e) {
			throw new TUserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_USER table that match the criteria 'USID = :usid'.
	 */
	@Transactional
	public List<TUser> findWhereUsidEquals(long usid) throws TUserDaoException
	{
		try {
			return jdbcTemplate.query("SELECT USID, UNAME, PASSWORD, STATUS, DESCRIPTION FROM " + getTableName() + " WHERE USID = ? ORDER BY USID", this,usid);
		}
		catch (Exception e) {
			throw new TUserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_USER table that match the criteria 'UNAME = :uname'.
	 */
	@Transactional
	public List<TUser> findWhereUnameEquals(String uname) throws TUserDaoException
	{
		try {
			return jdbcTemplate.query("SELECT USID, UNAME, PASSWORD, STATUS, DESCRIPTION FROM " + getTableName() + " WHERE UNAME = ? ORDER BY UNAME", this,uname);
		}
		catch (Exception e) {
			throw new TUserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_USER table that match the criteria 'PASSWORD = :password'.
	 */
	@Transactional
	public List<TUser> findWherePasswordEquals(String password) throws TUserDaoException
	{
		try {
			return jdbcTemplate.query("SELECT USID, UNAME, PASSWORD, STATUS, DESCRIPTION FROM " + getTableName() + " WHERE PASSWORD = ? ORDER BY PASSWORD", this,password);
		}
		catch (Exception e) {
			throw new TUserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_USER table that match the criteria 'STATUS = :status'.
	 */
	@Transactional
	public List<TUser> findWhereStatusEquals(String status) throws TUserDaoException
	{
		try {
			return jdbcTemplate.query("SELECT USID, UNAME, PASSWORD, STATUS, DESCRIPTION FROM " + getTableName() + " WHERE STATUS = ? ORDER BY STATUS", this,status);
		}
		catch (Exception e) {
			throw new TUserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_USER table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TUser> findWhereDescriptionEquals(String description) throws TUserDaoException
	{
		try {
			return jdbcTemplate.query("SELECT USID, UNAME, PASSWORD, STATUS, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TUserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_USER table that matches the specified primary-key value.
	 */
	public TUser findByPrimaryKey(TUserPk pk) throws TUserDaoException
	{
		return findByPrimaryKey( pk.getUsid() );
	}

}
