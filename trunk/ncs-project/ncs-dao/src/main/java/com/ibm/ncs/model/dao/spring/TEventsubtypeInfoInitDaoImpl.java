package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TEventsubtypeInfoInitDao;
import com.ibm.ncs.model.dto.TEventsubtypeInfoInit;
import com.ibm.ncs.model.dto.TEventsubtypeInfoInitPk;
import com.ibm.ncs.model.exceptions.TEventsubtypeInfoInitDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TEventsubtypeInfoInitDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TEventsubtypeInfoInit>, TEventsubtypeInfoInitDao
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
	 * @return TEventsubtypeInfoInitPk
	 */
	@Transactional
	public TEventsubtypeInfoInitPk insert(TEventsubtypeInfoInit dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( ESTID, EVESUBTYPE, DESCRIPTION, ESTCODE ) VALUES ( ?, ?, ?, ? )",dto.getEstid(),dto.getEvesubtype(),dto.getDescription(),dto.getEstcode());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_EVENTSUBTYPE_INFO_INIT table.
	 */
	@Transactional
	public void update(TEventsubtypeInfoInitPk pk, TEventsubtypeInfoInit dto) throws TEventsubtypeInfoInitDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET ESTID = ?, EVESUBTYPE = ?, DESCRIPTION = ?, ESTCODE = ? WHERE ESTID = ?",dto.getEstid(),dto.getEvesubtype(),dto.getDescription(),dto.getEstcode(),pk.getEstid());
	}

	/** 
	 * Deletes a single row in the T_EVENTSUBTYPE_INFO_INIT table.
	 */
	@Transactional
	public void delete(TEventsubtypeInfoInitPk pk) throws TEventsubtypeInfoInitDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE ESTID = ?",pk.getEstid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TEventsubtypeInfoInit
	 */
	public TEventsubtypeInfoInit mapRow(ResultSet rs, int row) throws SQLException
	{
		TEventsubtypeInfoInit dto = new TEventsubtypeInfoInit();
		dto.setEstid( rs.getLong( 1 ) );
		dto.setEvesubtype( rs.getString( 2 ) );
		dto.setDescription( rs.getString( 3 ) );
		dto.setEstcode( rs.getString( 4 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_EVENTSUBTYPE_INFO_INIT";
	}

	/** 
	 * Returns all rows from the T_EVENTSUBTYPE_INFO_INIT table that match the criteria 'ESTID = :estid'.
	 */
	@Transactional
	public TEventsubtypeInfoInit findByPrimaryKey(long estid) throws TEventsubtypeInfoInitDaoException
	{
		try {
			List<TEventsubtypeInfoInit> list = jdbcTemplate.query("SELECT ESTID, EVESUBTYPE, DESCRIPTION, ESTCODE FROM " + getTableName() + " WHERE ESTID = ?", this,estid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TEventsubtypeInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENTSUBTYPE_INFO_INIT table that match the criteria ''.
	 */
	@Transactional
	public List<TEventsubtypeInfoInit> findAll() throws TEventsubtypeInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ESTID, EVESUBTYPE, DESCRIPTION, ESTCODE FROM " + getTableName() + " ORDER BY ESTID", this);
		}
		catch (Exception e) {
			throw new TEventsubtypeInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENTSUBTYPE_INFO_INIT table that match the criteria 'ESTID = :estid'.
	 */
	@Transactional
	public List<TEventsubtypeInfoInit> findWhereEstidEquals(long estid) throws TEventsubtypeInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ESTID, EVESUBTYPE, DESCRIPTION, ESTCODE FROM " + getTableName() + " WHERE ESTID = ? ORDER BY ESTID", this,estid);
		}
		catch (Exception e) {
			throw new TEventsubtypeInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENTSUBTYPE_INFO_INIT table that match the criteria 'EVESUBTYPE = :evesubtype'.
	 */
	@Transactional
	public List<TEventsubtypeInfoInit> findWhereEvesubtypeEquals(String evesubtype) throws TEventsubtypeInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ESTID, EVESUBTYPE, DESCRIPTION, ESTCODE FROM " + getTableName() + " WHERE EVESUBTYPE = ? ORDER BY EVESUBTYPE", this,evesubtype);
		}
		catch (Exception e) {
			throw new TEventsubtypeInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENTSUBTYPE_INFO_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TEventsubtypeInfoInit> findWhereDescriptionEquals(String description) throws TEventsubtypeInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ESTID, EVESUBTYPE, DESCRIPTION, ESTCODE FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TEventsubtypeInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_EVENTSUBTYPE_INFO_INIT table that match the criteria 'ESTCODE = :estcode'.
	 */
	@Transactional
	public List<TEventsubtypeInfoInit> findWhereEstcodeEquals(String estcode) throws TEventsubtypeInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT ESTID, EVESUBTYPE, DESCRIPTION, ESTCODE FROM " + getTableName() + " WHERE ESTCODE = ? ORDER BY ESTCODE", this,estcode);
		}
		catch (Exception e) {
			throw new TEventsubtypeInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_EVENTSUBTYPE_INFO_INIT table that matches the specified primary-key value.
	 */
	public TEventsubtypeInfoInit findByPrimaryKey(TEventsubtypeInfoInitPk pk) throws TEventsubtypeInfoInitDaoException
	{
		return findByPrimaryKey( pk.getEstid() );
	}

}
