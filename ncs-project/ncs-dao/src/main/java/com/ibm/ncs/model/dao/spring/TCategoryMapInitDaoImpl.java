package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TCategoryMapInitPk;
import com.ibm.ncs.model.exceptions.TCategoryMapInitDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TCategoryMapInitDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TCategoryMapInit>, TCategoryMapInitDao
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
	 * @return TCategoryMapInitPk
	 */
	@Transactional
	public TCategoryMapInitPk insert(TCategoryMapInit dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( ID, NAME, FLAG ) VALUES ( ?, ?, ? )",dto.getId(),dto.getName(),dto.getFlag());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_CATEGORY_MAP_INIT table.
	 */
	@Transactional
	public void update(TCategoryMapInitPk pk, TCategoryMapInit dto) throws TCategoryMapInitDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET ID = ?, NAME = ?, FLAG = ? WHERE ID = ?",dto.getId(),dto.getName(),dto.getFlag(),pk.getId());
	}

	/** 
	 * Deletes a single row in the T_CATEGORY_MAP_INIT table.
	 */
	@Transactional
	public void delete(TCategoryMapInitPk pk) throws TCategoryMapInitDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE ID = ?",pk.getId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TCategoryMapInit
	 */
	public TCategoryMapInit mapRow(ResultSet rs, int row) throws SQLException
	{
		TCategoryMapInit dto = new TCategoryMapInit();
		dto.setId( rs.getLong( 1 ) );
		dto.setName( rs.getString( 2 ) );
		dto.setFlag( rs.getString( 3 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_CATEGORY_MAP_INIT";
	}

	/** 
	 * Returns all rows from the T_CATEGORY_MAP_INIT table that match the criteria 'ID = :id'.
	 */
	@Transactional
	public TCategoryMapInit findByPrimaryKey(long id) throws TCategoryMapInitDaoException
	{
		try {
			List<TCategoryMapInit> list = jdbcTemplate.query("SELECT ID, NAME, FLAG FROM " + getTableName() + " WHERE ID = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TCategoryMapInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_CATEGORY_MAP_INIT table that match the criteria ''.
	 */
	@Transactional
	public List<TCategoryMapInit> findAll() throws TCategoryMapInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG FROM " + getTableName() + " ORDER BY ID", this);
		}
		catch (Exception e) {
			throw new TCategoryMapInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_CATEGORY_MAP_INIT table that match the criteria 'ID = :id'.
	 */
	@Transactional
	public List<TCategoryMapInit> findWhereIdEquals(long id) throws TCategoryMapInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG FROM " + getTableName() + " WHERE ID = ? ORDER BY ID", this,id);
		}
		catch (Exception e) {
			throw new TCategoryMapInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_CATEGORY_MAP_INIT table that match the criteria 'NAME = :name'.
	 */
	@Transactional
	public List<TCategoryMapInit> findWhereNameEquals(String name) throws TCategoryMapInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG FROM " + getTableName() + " WHERE NAME = ? ORDER BY NAME", this,name);
		}
		catch (Exception e) {
			throw new TCategoryMapInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_CATEGORY_MAP_INIT table that match the criteria 'FLAG = :flag'.
	 */
	@Transactional
	public List<TCategoryMapInit> findWhereFlagEquals(String flag) throws TCategoryMapInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ID, NAME, FLAG FROM " + getTableName() + " WHERE FLAG = ? ORDER BY FLAG", this,flag);
		}
		catch (Exception e) {
			throw new TCategoryMapInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_CATEGORY_MAP_INIT table that matches the specified primary-key value.
	 */
	public TCategoryMapInit findByPrimaryKey(TCategoryMapInitPk pk) throws TCategoryMapInitDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
