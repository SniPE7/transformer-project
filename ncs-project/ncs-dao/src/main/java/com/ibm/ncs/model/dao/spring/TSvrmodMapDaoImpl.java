package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TSvrmodMapDao;
import com.ibm.ncs.model.dto.TSvrmodMap;
import com.ibm.ncs.model.dto.TSvrmodMapPk;
import com.ibm.ncs.model.exceptions.TSvrmodMapDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TSvrmodMapDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TSvrmodMap>, TSvrmodMapDao
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
	 * @return TSvrmodMapPk
	 */
	@Transactional
	public TSvrmodMapPk insert(TSvrmodMap dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( NMSID, MODID, PATH, DESCRIPTION ) VALUES ( ?, ?, ?, ? )",dto.getNmsid(),dto.getModid(),dto.getPath(),dto.getDescription());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_SVRMOD_MAP table.
	 */
	@Transactional
	public void update(TSvrmodMapPk pk, TSvrmodMap dto) throws TSvrmodMapDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET NMSID = ?, MODID = ?, PATH = ?, DESCRIPTION = ? WHERE NMSID = ? AND MODID = ?",dto.getNmsid(),dto.getModid(),dto.getPath(),dto.getDescription(),pk.getNmsid(),pk.getModid());
	}

	/** 
	 * Deletes a single row in the T_SVRMOD_MAP table.
	 */
	@Transactional
	public void delete(TSvrmodMapPk pk) throws TSvrmodMapDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE NMSID = ? AND MODID = ?",pk.getNmsid(),pk.getModid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TSvrmodMap
	 */
	public TSvrmodMap mapRow(ResultSet rs, int row) throws SQLException
	{
		TSvrmodMap dto = new TSvrmodMap();
		dto.setNmsid( rs.getLong( 1 ) );
		dto.setModid( rs.getLong( 2 ) );
		dto.setPath( rs.getString( 3 ) );
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
		return "T_SVRMOD_MAP";
	}

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table that match the criteria 'NMSID = :nmsid AND MODID = :modid'.
	 */
	@Transactional
	public TSvrmodMap findByPrimaryKey(long nmsid, long modid) throws TSvrmodMapDaoException
	{
		try {
			List<TSvrmodMap> list = jdbcTemplate.query("SELECT NMSID, MODID, PATH, DESCRIPTION FROM " + getTableName() + " WHERE NMSID = ? AND MODID = ?", this,nmsid,modid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TSvrmodMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table that match the criteria ''.
	 */
	@Transactional
	public List<TSvrmodMap> findAll() throws TSvrmodMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, MODID, PATH, DESCRIPTION FROM " + getTableName() + " ORDER BY NMSID, MODID", this);
		}
		catch (Exception e) {
			throw new TSvrmodMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table that match the criteria 'NMSID = :nmsid'.
	 */
	@Transactional
	public List<TSvrmodMap> findWhereNmsidEquals(long nmsid) throws TSvrmodMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, MODID, PATH, DESCRIPTION FROM " + getTableName() + " WHERE NMSID = ? ORDER BY NMSID", this,nmsid);
		}
		catch (Exception e) {
			throw new TSvrmodMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table that match the criteria 'MODID = :modid'.
	 */
	@Transactional
	public List<TSvrmodMap> findWhereModidEquals(long modid) throws TSvrmodMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, MODID, PATH, DESCRIPTION FROM " + getTableName() + " WHERE MODID = ? ORDER BY MODID", this,modid);
		}
		catch (Exception e) {
			throw new TSvrmodMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table that match the criteria 'PATH = :path'.
	 */
	@Transactional
	public List<TSvrmodMap> findWherePathEquals(String path) throws TSvrmodMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, MODID, PATH, DESCRIPTION FROM " + getTableName() + " WHERE PATH = ? ORDER BY PATH", this,path);
		}
		catch (Exception e) {
			throw new TSvrmodMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TSvrmodMap> findWhereDescriptionEquals(String description) throws TSvrmodMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, MODID, PATH, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TSvrmodMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_SVRMOD_MAP table that matches the specified primary-key value.
	 */
	public TSvrmodMap findByPrimaryKey(TSvrmodMapPk pk) throws TSvrmodMapDaoException
	{
		return findByPrimaryKey( pk.getNmsid(), pk.getModid() );
	}

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table join table T_MODULE_INFO_INIT that match the criteria 'MNAME = :mname'.
	 */
	@Transactional
	public List<TSvrmodMap> findByModuleName(String mname) throws TSvrmodMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT NMSID, MODID, PATH, DESCRIPTION FROM " + getTableName() + " a where modid in (select modid from t_module_info_init where mname = ?)", this,mname);
		}
		catch (Exception e) {
			throw new TSvrmodMapDaoException("Query failed", e);
		}
		
	}
}
