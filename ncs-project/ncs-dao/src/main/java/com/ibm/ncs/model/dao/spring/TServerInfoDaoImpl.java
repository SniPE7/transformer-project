package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TServerInfoDao;
import com.ibm.ncs.model.dto.TServerInfo;
import com.ibm.ncs.model.dto.TServerInfoPk;
import com.ibm.ncs.model.exceptions.TServerInfoDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TServerInfoDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TServerInfo>, TServerInfoDao
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
	 * @return TServerInfoPk
	 */
	@Transactional
	public TServerInfoPk insert(TServerInfo dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION ) VALUES ( ?, ?, ?, ?, ?, ?, ? )",dto.getNmsid(),dto.getNmsip(),dto.getNmsname(),dto.getUsername(),dto.getPassword(),dto.getOstype(),dto.getDescription());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_SERVER_INFO table.
	 */
	@Transactional
	public void update(TServerInfoPk pk, TServerInfo dto) throws TServerInfoDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET NMSID = ?, NMSIP = ?, NMSNAME = ?, USERNAME = ?, PASSWORD = ?, OSTYPE = ?, DESCRIPTION = ? WHERE NMSID = ?",dto.getNmsid(),dto.getNmsip(),dto.getNmsname(),dto.getUsername(),dto.getPassword(),dto.getOstype(),dto.getDescription(),pk.getNmsid());
	}

	/** 
	 * Deletes a single row in the T_SERVER_INFO table.
	 */
	@Transactional
	public void delete(TServerInfoPk pk) throws TServerInfoDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE NMSID = ?",pk.getNmsid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TServerInfo
	 */
	public TServerInfo mapRow(ResultSet rs, int row) throws SQLException
	{
		TServerInfo dto = new TServerInfo();
		dto.setNmsid( rs.getLong( 1 ) );
		dto.setNmsip( rs.getString( 2 ) );
		dto.setNmsname( rs.getString( 3 ) );
		dto.setUsername( rs.getString( 4 ) );
		dto.setPassword( rs.getString( 5 ) );
		dto.setOstype( rs.getString( 6 ) );
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
		return "T_SERVER_INFO";
	}

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'NMSID = :nmsid'.
	 */
	@Transactional
	public TServerInfo findByPrimaryKey(long nmsid) throws TServerInfoDaoException
	{
		try {
			List<TServerInfo> list = jdbcTemplate.query("SELECT NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION FROM " + getTableName() + " WHERE NMSID = ?", this,nmsid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TServerInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria ''.
	 */
	@Transactional
	public List<TServerInfo> findAll() throws TServerInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION FROM " + getTableName() + " ORDER BY NMSID", this);
		}
		catch (Exception e) {
			throw new TServerInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'NMSID = :nmsid'.
	 */
	@Transactional
	public List<TServerInfo> findWhereNmsidEquals(long nmsid) throws TServerInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION FROM " + getTableName() + " WHERE NMSID = ? ORDER BY NMSID", this,nmsid);
		}
		catch (Exception e) {
			throw new TServerInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'NMSIP = :nmsip'.
	 */
	@Transactional
	public List<TServerInfo> findWhereNmsipEquals(String nmsip) throws TServerInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION FROM " + getTableName() + " WHERE NMSIP = ? ORDER BY NMSIP", this,nmsip);
		}
		catch (Exception e) {
			throw new TServerInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'NMSNAME = :nmsname'.
	 */
	@Transactional
	public List<TServerInfo> findWhereNmsnameEquals(String nmsname) throws TServerInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION FROM " + getTableName() + " WHERE NMSNAME = ? ORDER BY NMSNAME", this,nmsname);
		}
		catch (Exception e) {
			throw new TServerInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'USERNAME = :username'.
	 */
	@Transactional
	public List<TServerInfo> findWhereUsernameEquals(String username) throws TServerInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION FROM " + getTableName() + " WHERE USERNAME = ? ORDER BY USERNAME", this,username);
		}
		catch (Exception e) {
			throw new TServerInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'PASSWORD = :password'.
	 */
	@Transactional
	public List<TServerInfo> findWherePasswordEquals(String password) throws TServerInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION FROM " + getTableName() + " WHERE PASSWORD = ? ORDER BY PASSWORD", this,password);
		}
		catch (Exception e) {
			throw new TServerInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'OSTYPE = :ostype'.
	 */
	@Transactional
	public List<TServerInfo> findWhereOstypeEquals(String ostype) throws TServerInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION FROM " + getTableName() + " WHERE OSTYPE = ? ORDER BY OSTYPE", this,ostype);
		}
		catch (Exception e) {
			throw new TServerInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TServerInfo> findWhereDescriptionEquals(String description) throws TServerInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TServerInfoDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_SERVER_INFO table that matches the specified primary-key value.
	 */
	public TServerInfo findByPrimaryKey(TServerInfoPk pk) throws TServerInfoDaoException
	{
		return findByPrimaryKey( pk.getNmsid() );
	}
	
	/** 
	 * Returns all rows from the T_SERVER_INFO and join T_SVRMOD_MAP T_MODULE_INFO_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TServerInfo> findByModuleName(String mname) throws TServerInfoDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, NMSIP, NMSNAME, USERNAME, PASSWORD, OSTYPE, DESCRIPTION FROM " + getTableName() + " WHERE NMSID in(select nmsid from t_svrmod_map b join t_module_info_init c on b.modid= c.modid and c.mname= ?) ", this,mname);
		}
		catch (Exception e) {
			throw new TServerInfoDaoException("Query failed", e);
		}
		
	}

}
