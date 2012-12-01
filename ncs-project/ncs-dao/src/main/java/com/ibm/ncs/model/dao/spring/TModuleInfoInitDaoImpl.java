package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TModuleInfoInitPk;
import com.ibm.ncs.model.exceptions.TModuleInfoInitDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TModuleInfoInitDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TModuleInfoInit>, TModuleInfoInitDao
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
	 * @return TModuleInfoInitPk
	 */
	@Transactional
	public TModuleInfoInitPk insert(TModuleInfoInit dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( MODID, MNAME, MCODE, DESCRIPTION ) VALUES ( ?, ?, ?, ? )",dto.getModid(),dto.getMname(),dto.getMcode(),dto.getDescription());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_MODULE_INFO_INIT table.
	 */
	@Transactional
	public void update(TModuleInfoInitPk pk, TModuleInfoInit dto) throws TModuleInfoInitDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET MODID = ?, MNAME = ?, MCODE = ?, DESCRIPTION = ? WHERE MODID = ?",dto.getModid(),dto.getMname(),dto.getMcode(),dto.getDescription(),pk.getModid());
	}

	/** 
	 * Deletes a single row in the T_MODULE_INFO_INIT table.
	 */
	@Transactional
	public void delete(TModuleInfoInitPk pk) throws TModuleInfoInitDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE MODID = ?",pk.getModid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TModuleInfoInit
	 */
	public TModuleInfoInit mapRow(ResultSet rs, int row) throws SQLException
	{
		TModuleInfoInit dto = new TModuleInfoInit();
		dto.setModid( rs.getLong( 1 ) );
		dto.setMname( rs.getString( 2 ) );
		dto.setMcode( rs.getLong( 3 ) );
		dto.setDescription( rs.getString( 4 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_MODULE_INFO_INIT";
	}

	/** 
	 * Returns all rows from the T_MODULE_INFO_INIT table that match the criteria 'MODID = :modid'.
	 */
	@Transactional
	public TModuleInfoInit findByPrimaryKey(long modid) throws TModuleInfoInitDaoException
	{
		try {
			List<TModuleInfoInit> list = jdbcTemplate.query("SELECT MODID, MNAME, MCODE, DESCRIPTION FROM " + getTableName() + " WHERE MODID = ?", this,modid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TModuleInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MODULE_INFO_INIT table that match the criteria ''.
	 */
	@Transactional
	public List<TModuleInfoInit> findAll() throws TModuleInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, MNAME, MCODE, DESCRIPTION FROM " + getTableName() + " ORDER BY MODID", this);
		}
		catch (Exception e) {
			throw new TModuleInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MODULE_INFO_INIT table that match the criteria 'MODID = :modid'.
	 */
	@Transactional
	public List<TModuleInfoInit> findWhereModidEquals(long modid) throws TModuleInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, MNAME, MCODE, DESCRIPTION FROM " + getTableName() + " WHERE MODID = ? ORDER BY MODID", this,modid);
		}
		catch (Exception e) {
			throw new TModuleInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MODULE_INFO_INIT table that match the criteria 'MNAME = :mname'.
	 */
	@Transactional
	public List<TModuleInfoInit> findWhereMnameEquals(String mname) throws TModuleInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, MNAME, MCODE, DESCRIPTION FROM " + getTableName() + " WHERE MNAME = ? ORDER BY MNAME", this,mname);
		}
		catch (Exception e) {
			throw new TModuleInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MODULE_INFO_INIT table that match the criteria 'MCODE = :mcode'.
	 */
	@Transactional
	public List<TModuleInfoInit> findWhereMcodeEquals(long mcode) throws TModuleInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, MNAME, MCODE, DESCRIPTION FROM " + getTableName() + " WHERE MCODE = ? ORDER BY MCODE", this,mcode);
		}
		catch (Exception e) {
			throw new TModuleInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MODULE_INFO_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TModuleInfoInit> findWhereDescriptionEquals(String description) throws TModuleInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MODID, MNAME, MCODE, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TModuleInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_MODULE_INFO_INIT table that matches the specified primary-key value.
	 */
	public TModuleInfoInit findByPrimaryKey(TModuleInfoInitPk pk) throws TModuleInfoInitDaoException
	{
		return findByPrimaryKey( pk.getModid() );
	}

}
