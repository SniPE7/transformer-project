package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TEventtypeInfoInitDao;
import com.ibm.ncs.model.dto.TEventtypeInfoInit;
import com.ibm.ncs.model.dto.TEventtypeInfoInitPk;
import com.ibm.ncs.model.exceptions.TEventtypeInfoInitDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TEventtypeInfoInitDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TEventtypeInfoInit>, TEventtypeInfoInitDao
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
	 * @return TEventtypeInfoInitPk
	 */
	@Transactional
	public TEventtypeInfoInitPk insert(TEventtypeInfoInit dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( ETID, EVETYPE, DESCRIPTION, ETCODE ) VALUES ( ?, ?, ?, ? )",dto.getEtid(),dto.getEvetype(),dto.getDescription(),dto.getEtcode());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_EVENTTYPE_INFO_INIT table.
	 */
	@Transactional
	public void update(TEventtypeInfoInitPk pk, TEventtypeInfoInit dto) throws TEventtypeInfoInitDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET ETID = ?, EVETYPE = ?, DESCRIPTION = ?, ETCODE = ? WHERE ETID = ?",dto.getEtid(),dto.getEvetype(),dto.getDescription(),dto.getEtcode(),pk.getEtid());
	}

	/** 
	 * Deletes a single row in the T_EVENTTYPE_INFO_INIT table.
	 */
	@Transactional
	public void delete(TEventtypeInfoInitPk pk) throws TEventtypeInfoInitDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE ETID = ?",pk.getEtid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TEventtypeInfoInit
	 */
	public TEventtypeInfoInit mapRow(ResultSet rs, int row) throws SQLException
	{
		TEventtypeInfoInit dto = new TEventtypeInfoInit();
		dto.setEtid( rs.getLong( 1 ) );
		dto.setEvetype( rs.getString( 2 ) );
		dto.setDescription( rs.getString( 3 ) );
		dto.setEtcode( rs.getString( 4 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_EVENTTYPE_INFO_INIT";
	}

	/** 
	 * Returns all rows from the T_EVENTTYPE_INFO_INIT table that match the criteria 'ETID = :etid'.
	 */
	@Transactional
	public TEventtypeInfoInit findByPrimaryKey(long etid) throws TEventtypeInfoInitDaoException
	{
		try {
			List<TEventtypeInfoInit> list = jdbcTemplate.query("SELECT ETID, EVETYPE, DESCRIPTION, ETCODE FROM " + getTableName() + " WHERE ETID = ?", this,etid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TEventtypeInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENTTYPE_INFO_INIT table that match the criteria ''.
	 */
	@Transactional
	public List<TEventtypeInfoInit> findAll() throws TEventtypeInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, EVETYPE, DESCRIPTION, ETCODE FROM " + getTableName() + " ORDER BY ETID", this);
		}
		catch (Exception e) {
			throw new TEventtypeInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENTTYPE_INFO_INIT table that match the criteria 'ETID = :etid'.
	 */
	@Transactional
	public List<TEventtypeInfoInit> findWhereEtidEquals(long etid) throws TEventtypeInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, EVETYPE, DESCRIPTION, ETCODE FROM " + getTableName() + " WHERE ETID = ? ORDER BY ETID", this,etid);
		}
		catch (Exception e) {
			throw new TEventtypeInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENTTYPE_INFO_INIT table that match the criteria 'EVETYPE = :evetype'.
	 */
	@Transactional
	public List<TEventtypeInfoInit> findWhereEvetypeEquals(String evetype) throws TEventtypeInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, EVETYPE, DESCRIPTION, ETCODE FROM " + getTableName() + " WHERE EVETYPE = ? ORDER BY EVETYPE", this,evetype);
		}
		catch (Exception e) {
			throw new TEventtypeInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENTTYPE_INFO_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TEventtypeInfoInit> findWhereDescriptionEquals(String description) throws TEventtypeInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, EVETYPE, DESCRIPTION, ETCODE FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TEventtypeInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENTTYPE_INFO_INIT table that match the criteria 'ETCODE = :etcode'.
	 */
	@Transactional
	public List<TEventtypeInfoInit> findWhereEtcodeEquals(String etcode) throws TEventtypeInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ETID, EVETYPE, DESCRIPTION, ETCODE FROM " + getTableName() + " WHERE ETCODE = ? ORDER BY ETCODE", this,etcode);
		}
		catch (Exception e) {
			throw new TEventtypeInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_EVENTTYPE_INFO_INIT table that matches the specified primary-key value.
	 */
	public TEventtypeInfoInit findByPrimaryKey(TEventtypeInfoInitPk pk) throws TEventtypeInfoInitDaoException
	{
		return findByPrimaryKey( pk.getEtid() );
	}

}
