package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TGrpNetPk;
import com.ibm.ncs.model.exceptions.TGrpNetDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TGrpNetDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TGrpNet>, TGrpNetDao
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
	 * @return TGrpNetPk
	 */
	@Transactional
	public TGrpNetPk insert(TGrpNet dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( GID, GNAME, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG ) VALUES ( ?, ?, ?, ?, ?, ? )",dto.getGid(),dto.getGname(),dto.getSupid(),dto.getLevels(),dto.getDescription(),dto.getUnmallocedipsetflag());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_GRP_NET table.
	 */
	@Transactional
	public void update(TGrpNetPk pk, TGrpNet dto) throws TGrpNetDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET GID = ?, GNAME = ?, SUPID = ?, LEVELS = ?, DESCRIPTION = ?, UNMALLOCEDIPSETFLAG = ? WHERE GID = ?",dto.getGid(),dto.getGname(),dto.getSupid(),dto.getLevels(),dto.getDescription(),dto.getUnmallocedipsetflag(),pk.getGid());
	}

	/** 
	 * Deletes a single row in the T_GRP_NET table.
	 */
	@Transactional
	public void delete(TGrpNetPk pk) throws TGrpNetDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE GID = ?",pk.getGid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TGrpNet
	 */
	public TGrpNet mapRow(ResultSet rs, int row) throws SQLException
	{
		TGrpNet dto = new TGrpNet();
		dto.setGid( rs.getLong( 1 ) );
		dto.setGname( rs.getString( 2 ) );
		dto.setSupid( rs.getLong( 3 ) );
		dto.setLevels( rs.getLong( 4 ) );
		dto.setDescription( rs.getString( 5 ) );
		dto.setUnmallocedipsetflag( rs.getString( 6 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_GRP_NET";
	}

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'GID = :gid'.
	 */
	@Transactional
	public TGrpNet findByPrimaryKey(long gid) throws TGrpNetDaoException
	{
		try {
			List<TGrpNet> list = jdbcTemplate.query("SELECT GID, GNAME, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG FROM " + getTableName() + " WHERE GID = ?", this,gid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TGrpNetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria ''.
	 */
	@Transactional
	public List<TGrpNet> findAll() throws TGrpNetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG FROM " + getTableName() + " ORDER BY GID", this);
		}
		catch (Exception e) {
			throw new TGrpNetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'GID = :gid'.
	 */
	@Transactional
	public List<TGrpNet> findWhereGidEquals(long gid) throws TGrpNetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG FROM " + getTableName() + " WHERE GID = ? ORDER BY GID", this,gid);
		}
		catch (Exception e) {
			throw new TGrpNetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'GNAME = :gname'.
	 */
	@Transactional
	public List<TGrpNet> findWhereGnameEquals(String gname) throws TGrpNetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG FROM " + getTableName() + " WHERE GNAME = ? ORDER BY GNAME", this,gname);
		}
		catch (Exception e) {
			throw new TGrpNetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'SUPID = :supid'.
	 */
	@Transactional
	public List<TGrpNet> findWhereSupidEquals(long supid) throws TGrpNetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG FROM " + getTableName() + " WHERE SUPID = ? ORDER BY SUPID", this,supid);
		}
		catch (Exception e) {
			throw new TGrpNetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'LEVELS = :levels'.
	 */
	@Transactional
	public List<TGrpNet> findWhereLevelsEquals(long levels) throws TGrpNetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG FROM " + getTableName() + " WHERE LEVELS = ? ORDER BY LEVELS", this,levels);
		}
		catch (Exception e) {
			throw new TGrpNetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TGrpNet> findWhereDescriptionEquals(String description) throws TGrpNetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TGrpNetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'UNMALLOCEDIPSETFLAG = :unmallocedipsetflag'.
	 */
	@Transactional
	public List<TGrpNet> findWhereUnmallocedipsetflagEquals(String unmallocedipsetflag) throws TGrpNetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG FROM " + getTableName() + " WHERE UNMALLOCEDIPSETFLAG = ? ORDER BY UNMALLOCEDIPSETFLAG", this,unmallocedipsetflag);
		}
		catch (Exception e) {
			throw new TGrpNetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_GRP_NET table that matches the specified primary-key value.
	 */
	public TGrpNet findByPrimaryKey(TGrpNetPk pk) throws TGrpNetDaoException
	{
		return findByPrimaryKey( pk.getGid() );
	}

}
