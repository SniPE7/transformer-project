package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TGrpOrgDao;
import com.ibm.ncs.model.dto.TGrpOrg;
import com.ibm.ncs.model.dto.TGrpOrgPk;
import com.ibm.ncs.model.exceptions.TGrpOrgDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TGrpOrgDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TGrpOrg>, TGrpOrgDao
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
	 * @return TGrpOrgPk
	 */
	@Transactional
	public TGrpOrgPk insert(TGrpOrg dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( GID, GNAME, ORGABBR, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG, ORGSPELL ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )",dto.getGid(),dto.getGname(),dto.getOrgabbr(),dto.getSupid(),dto.getLevels(),dto.getDescription(),dto.getUnmallocedipsetflag(),dto.getOrgspell());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_GRP_ORG table.
	 */
	@Transactional
	public void update(TGrpOrgPk pk, TGrpOrg dto) throws TGrpOrgDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET GID = ?, GNAME = ?, ORGABBR = ?, SUPID = ?, LEVELS = ?, DESCRIPTION = ?, UNMALLOCEDIPSETFLAG = ?, ORGSPELL = ? WHERE GID = ?",dto.getGid(),dto.getGname(),dto.getOrgabbr(),dto.getSupid(),dto.getLevels(),dto.getDescription(),dto.getUnmallocedipsetflag(),dto.getOrgspell(),pk.getGid());
	}

	/** 
	 * Deletes a single row in the T_GRP_ORG table.
	 */
	@Transactional
	public void delete(TGrpOrgPk pk) throws TGrpOrgDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE GID = ?",pk.getGid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TGrpOrg
	 */
	public TGrpOrg mapRow(ResultSet rs, int row) throws SQLException
	{
		TGrpOrg dto = new TGrpOrg();
		dto.setGid( rs.getLong( 1 ) );
		dto.setGname( rs.getString( 2 ) );
		dto.setOrgabbr( rs.getString( 3 ) );
		dto.setSupid( rs.getLong( 4 ) );
		dto.setLevels( rs.getLong( 5 ) );
		dto.setDescription( rs.getString( 6 ) );
		dto.setUnmallocedipsetflag( rs.getString( 7 ) );
		dto.setOrgspell( rs.getString( 8 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_GRP_ORG";
	}

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'GID = :gid'.
	 */
	@Transactional
	public TGrpOrg findByPrimaryKey(long gid) throws TGrpOrgDaoException
	{
		try {
			List<TGrpOrg> list = jdbcTemplate.query("SELECT GID, GNAME, ORGABBR, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG, ORGSPELL FROM " + getTableName() + " WHERE GID = ?", this,gid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TGrpOrgDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria ''.
	 */
	@Transactional
	public List<TGrpOrg> findAll() throws TGrpOrgDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, ORGABBR, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG, ORGSPELL FROM " + getTableName() + " ORDER BY GID", this);
		}
		catch (Exception e) {
			throw new TGrpOrgDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'GID = :gid'.
	 */
	@Transactional
	public List<TGrpOrg> findWhereGidEquals(long gid) throws TGrpOrgDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, ORGABBR, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG, ORGSPELL FROM " + getTableName() + " WHERE GID = ? ORDER BY GID", this,gid);
		}
		catch (Exception e) {
			throw new TGrpOrgDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'GNAME = :gname'.
	 */
	@Transactional
	public List<TGrpOrg> findWhereGnameEquals(String gname) throws TGrpOrgDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, ORGABBR, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG, ORGSPELL FROM " + getTableName() + " WHERE GNAME = ? ORDER BY GNAME", this,gname);
		}
		catch (Exception e) {
			throw new TGrpOrgDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'ORGABBR = :orgabbr'.
	 */
	@Transactional
	public List<TGrpOrg> findWhereOrgabbrEquals(String orgabbr) throws TGrpOrgDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, ORGABBR, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG, ORGSPELL FROM " + getTableName() + " WHERE ORGABBR = ? ORDER BY ORGABBR", this,orgabbr);
		}
		catch (Exception e) {
			throw new TGrpOrgDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'SUPID = :supid'.
	 */
	@Transactional
	public List<TGrpOrg> findWhereSupidEquals(long supid) throws TGrpOrgDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, ORGABBR, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG, ORGSPELL FROM " + getTableName() + " WHERE SUPID = ? ORDER BY SUPID", this,supid);
		}
		catch (Exception e) {
			throw new TGrpOrgDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'LEVELS = :levels'.
	 */
	@Transactional
	public List<TGrpOrg> findWhereLevelsEquals(long levels) throws TGrpOrgDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, ORGABBR, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG, ORGSPELL FROM " + getTableName() + " WHERE LEVELS = ? ORDER BY LEVELS", this,levels);
		}
		catch (Exception e) {
			throw new TGrpOrgDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TGrpOrg> findWhereDescriptionEquals(String description) throws TGrpOrgDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, ORGABBR, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG, ORGSPELL FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TGrpOrgDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'UNMALLOCEDIPSETFLAG = :unmallocedipsetflag'.
	 */
	@Transactional
	public List<TGrpOrg> findWhereUnmallocedipsetflagEquals(String unmallocedipsetflag) throws TGrpOrgDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, ORGABBR, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG, ORGSPELL FROM " + getTableName() + " WHERE UNMALLOCEDIPSETFLAG = ? ORDER BY UNMALLOCEDIPSETFLAG", this,unmallocedipsetflag);
		}
		catch (Exception e) {
			throw new TGrpOrgDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'ORGSPELL = :orgspell'.
	 */
	@Transactional
	public List<TGrpOrg> findWhereOrgspellEquals(String orgspell) throws TGrpOrgDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, GNAME, ORGABBR, SUPID, LEVELS, DESCRIPTION, UNMALLOCEDIPSETFLAG, ORGSPELL FROM " + getTableName() + " WHERE ORGSPELL = ? ORDER BY ORGSPELL", this,orgspell);
		}
		catch (Exception e) {
			throw new TGrpOrgDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_GRP_ORG table that matches the specified primary-key value.
	 */
	public TGrpOrg findByPrimaryKey(TGrpOrgPk pk) throws TGrpOrgDaoException
	{
		return findByPrimaryKey( pk.getGid() );
	}

}
