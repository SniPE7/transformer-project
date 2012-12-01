package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.LinesEventsNotcareDao;
import com.ibm.ncs.model.dto.LinesEventsNotcare;
import com.ibm.ncs.model.dto.LinesEventsNotcarePk;
import com.ibm.ncs.model.exceptions.LinesEventsNotcareDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class LinesEventsNotcareDaoImpl extends AbstractDAO implements ParameterizedRowMapper<LinesEventsNotcare>, LinesEventsNotcareDao
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
	 * @return LinesEventsNotcarePk
	 */
	@Transactional
	public LinesEventsNotcarePk insert(LinesEventsNotcare dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( LINESNOTCARE ) VALUES ( ? )",dto.getLinesnotcare());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the LINES_EVENTS_NOTCARE table.
	 */
	@Transactional
	public void update(LinesEventsNotcarePk pk, LinesEventsNotcare dto) throws LinesEventsNotcareDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET LINESNOTCARE = ? WHERE LINESNOTCARE = ?",dto.getLinesnotcare(),pk.getLinesnotcare());
	}

	/** 
	 * Deletes a single row in the LINES_EVENTS_NOTCARE table.
	 */
	@Transactional
	public void delete(LinesEventsNotcarePk pk) throws LinesEventsNotcareDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE LINESNOTCARE = ?",pk.getLinesnotcare());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return LinesEventsNotcare
	 */
	public LinesEventsNotcare mapRow(ResultSet rs, int row) throws SQLException
	{
		LinesEventsNotcare dto = new LinesEventsNotcare();
		dto.setLinesnotcare( rs.getString( 1 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "LINES_EVENTS_NOTCARE";
	}

	/** 
	 * Returns all rows from the LINES_EVENTS_NOTCARE table that match the criteria 'LINESNOTCARE = :linesnotcare'.
	 */
	@Transactional
	public LinesEventsNotcare findByPrimaryKey(String linesnotcare) throws LinesEventsNotcareDaoException
	{
		try {
			List<LinesEventsNotcare> list = jdbcTemplate.query("SELECT LINESNOTCARE FROM " + getTableName() + " WHERE LINESNOTCARE = ?", this,linesnotcare);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new LinesEventsNotcareDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the LINES_EVENTS_NOTCARE table that match the criteria ''.
	 */
	@Transactional
	public List<LinesEventsNotcare> findAll() throws LinesEventsNotcareDaoException
	{
		try {
			return jdbcTemplate.query("SELECT LINESNOTCARE FROM " + getTableName() + " ORDER BY LINESNOTCARE", this);
		}
		catch (Exception e) {
			throw new LinesEventsNotcareDaoException("Query failed", e);
		}
		
	}

	/**
	 * Method 'update'
	 * 
	 * @param dto
	 */
	@Transactional
	public void update(LinesEventsNotcare dto)
	{
		
	jdbcTemplate.update("Update"+getTableName()+"set LINESNOTCARE = ?", dto.getLinesnotcare());
	}

	/**
	 * Method 'delete'
	 * 
	 * @param dto
	 */
	@Transactional
	public void delete(LinesEventsNotcare dto)
	{
		jdbcTemplate.update("Delete from " + getTableName() + " where linesnotcare=?  "	,dto.getLinesnotcare());
	}

	/** 
	 * Returns all rows from the LINES_EVENTS_NOTCARE table that match the criteria 'LINESNOTCARE = :linesnotcare'.
	 */
	@Transactional
	public List<LinesEventsNotcare> findWhereLinesnotcareEquals(String linesnotcare) throws LinesEventsNotcareDaoException
	{
		try {
			return jdbcTemplate.query("SELECT LINESNOTCARE FROM " + getTableName() + " WHERE LINESNOTCARE = ? ORDER BY LINESNOTCARE", this,linesnotcare);
		}
		catch (Exception e) {
			throw new LinesEventsNotcareDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the LINES_EVENTS_NOTCARE table that matches the specified primary-key value.
	 */
	public LinesEventsNotcare findByPrimaryKey(LinesEventsNotcarePk pk) throws LinesEventsNotcareDaoException
	{
		return findByPrimaryKey( pk.getLinesnotcare() );
	}
	/** 
	 * Deletes  all row in the LINES_EVENTS_NOTCARE table.
	 */
	@Transactional
	public int deleteAll() throws LinesEventsNotcareDaoException
	{
		int ret = -1;
		ret = jdbcTemplate.update("DELETE FROM " + getTableName() );
		return ret;
	}
	/**
	 * Method 'insertAll'
	 * 
	 * @param dto
	 * @return LinesEventsNotcarePk
	 */
	@Transactional
	public int insertEffect()
	{
		int ret = -1;
//		String sqldev = "select devip||'|'||ifdescr  linesnotcare  from v_syslog_port_line_notcare";
		String sqlport = "select distinct devip||'|'||ifdescr  linesnotcare  from v_syslog_port_line_notcare";
//		String sqlpdm ="select devip||'|'||oidname  linesnotcare  from v_syslog_pdm_line_notcare";
//		try {
//			ret = jdbcTemplate.update("INSERT INTO " + getTableName() + " ( "+ sqldev +" )");
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//		}
		try {
			ret += jdbcTemplate.update("INSERT INTO " + getTableName() + " ( "+ sqlport +" )");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
//		try {
//			ret += jdbcTemplate.update("INSERT INTO " + getTableName() + " ( "+ sqlpdm +" )");
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//		}
		return ret;
	}
}
