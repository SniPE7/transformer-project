package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TModgrpMapDao;
import com.ibm.ncs.model.dto.TModgrpMap;
import com.ibm.ncs.model.dto.TModgrpMapPk;
import com.ibm.ncs.model.exceptions.TModgrpMapDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TModgrpMapDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TModgrpMap>, TModgrpMapDao
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
	 * @return TModgrpMapPk
	 */
	@Transactional
	public TModgrpMapPk insert(TModgrpMap dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( GID, NMSID, MODID ) VALUES ( ?, ?, ? )",dto.getGid(),dto.getNmsid(),dto.getModid());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_MODGRP_MAP table.
	 */
	@Transactional
	public void update(TModgrpMapPk pk, TModgrpMap dto) throws TModgrpMapDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET GID = ?, NMSID = ?, MODID = ? WHERE GID = ? AND NMSID = ? AND MODID = ?",dto.getGid(),dto.getNmsid(),dto.getModid(),pk.getGid(),pk.getNmsid(),pk.getModid());
	}

	/** 
	 * Deletes a single row in the T_MODGRP_MAP table.
	 */
	@Transactional
	public void delete(TModgrpMapPk pk) throws TModgrpMapDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE GID = ? AND NMSID = ? AND MODID = ?",pk.getGid(),pk.getNmsid(),pk.getModid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TModgrpMap
	 */
	public TModgrpMap mapRow(ResultSet rs, int row) throws SQLException
	{
		TModgrpMap dto = new TModgrpMap();
		dto.setGid( rs.getLong( 1 ) );
		dto.setNmsid( rs.getLong( 2 ) );
		dto.setModid( rs.getLong( 3 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_MODGRP_MAP";
	}

	/** 
	 * Returns all rows from the T_MODGRP_MAP table that match the criteria 'GID = :gid AND NMSID = :nmsid AND MODID = :modid'.
	 */
	@Transactional
	public TModgrpMap findByPrimaryKey(long gid, long nmsid, long modid) throws TModgrpMapDaoException
	{
		try {
			List<TModgrpMap> list = jdbcTemplate.query("SELECT GID, NMSID, MODID FROM " + getTableName() + " WHERE GID = ? AND NMSID = ? AND MODID = ?", this,gid,nmsid,modid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TModgrpMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MODGRP_MAP table that match the criteria ''.
	 */
	@Transactional
	public List<TModgrpMap> findAll() throws TModgrpMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, NMSID, MODID FROM " + getTableName() + " ORDER BY GID, NMSID, MODID", this);
		}
		catch (Exception e) {
			throw new TModgrpMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MODGRP_MAP table that match the criteria 'GID = :gid'.
	 */
	@Transactional
	public List<TModgrpMap> findWhereGidEquals(long gid) throws TModgrpMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, NMSID, MODID FROM " + getTableName() + " WHERE GID = ? ORDER BY GID", this,gid);
		}
		catch (Exception e) {
			throw new TModgrpMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MODGRP_MAP table that match the criteria 'NMSID = :nmsid'.
	 */
	@Transactional
	public List<TModgrpMap> findWhereNmsidEquals(long nmsid) throws TModgrpMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, NMSID, MODID FROM " + getTableName() + " WHERE NMSID = ? ORDER BY NMSID", this,nmsid);
		}
		catch (Exception e) {
			throw new TModgrpMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_MODGRP_MAP table that match the criteria 'MODID = :modid'.
	 */
	@Transactional
	public List<TModgrpMap> findWhereModidEquals(long modid) throws TModgrpMapDaoException
	{
		try {
			return jdbcTemplate.query("SELECT GID, NMSID, MODID FROM " + getTableName() + " WHERE MODID = ? ORDER BY MODID", this,modid);
		}
		catch (Exception e) {
			throw new TModgrpMapDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_MODGRP_MAP table that matches the specified primary-key value.
	 */
	public TModgrpMap findByPrimaryKey(TModgrpMapPk pk) throws TModgrpMapDaoException
	{
		return findByPrimaryKey( pk.getGid(), pk.getNmsid(), pk.getModid() );
	}

}
