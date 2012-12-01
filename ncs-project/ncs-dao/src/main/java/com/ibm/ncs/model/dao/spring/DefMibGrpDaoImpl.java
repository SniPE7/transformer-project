package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.DefMibGrpPk;
import com.ibm.ncs.model.exceptions.DefMibGrpDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class DefMibGrpDaoImpl extends AbstractDAO implements ParameterizedRowMapper<DefMibGrp>, DefMibGrpDao
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
	 * @return DefMibGrpPk
	 */
	@Transactional
	public DefMibGrpPk insert(DefMibGrp dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( MID, NAME, INDEXOID, INDEXVAR, DESCROID, DESCRVAR ) VALUES ( ?, ?, ?, ?, ?, ? )",dto.getMid(),dto.getName(),dto.getIndexoid(),dto.getIndexvar(),dto.getDescroid(),dto.getDescrvar());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the DEF_MIB_GRP table.
	 */
	@Transactional
	public void update(DefMibGrpPk pk, DefMibGrp dto) throws DefMibGrpDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET MID = ?, NAME = ?, INDEXOID = ?, INDEXVAR = ?, DESCROID = ?, DESCRVAR = ? WHERE MID = ?",dto.getMid(),dto.getName(),dto.getIndexoid(),dto.getIndexvar(),dto.getDescroid(),dto.getDescrvar(),pk.getMid());
	}
	/** 
	 * Updates a single row in the DEF_MIB_GRP table.
	 */
	@Transactional
	public void update(DefMibGrp dto) throws DefMibGrpDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET MID = ?, NAME = ?, INDEXOID = ?, INDEXVAR = ?, DESCROID = ?, DESCRVAR = ? WHERE MID = ?",dto.getMid(),dto.getName(),dto.getIndexoid(),dto.getIndexvar(),dto.getDescroid(),dto.getDescrvar(),dto.getMid());
	}
	/** 
	 * Deletes a single row in the DEF_MIB_GRP table.
	 */
	@Transactional
	public void delete(DefMibGrpPk pk) throws DefMibGrpDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE MID = ?",pk.getMid());
	}
	/** 
	 * Deletes a single row in the DEF_MIB_GRP table.
	 */
	@Transactional
	public void delete(long mid) throws DefMibGrpDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE MID = ?",mid);
	}
	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return DefMibGrp
	 */
	public DefMibGrp mapRow(ResultSet rs, int row) throws SQLException
	{
		DefMibGrp dto = new DefMibGrp();
		dto.setMid( rs.getLong( 1 ) );
		dto.setName( rs.getString( 2 ) );
		dto.setIndexoid( rs.getString( 3 ) );
		dto.setIndexvar( rs.getString( 4 ) );
		dto.setDescroid( rs.getString( 5 ) );
		dto.setDescrvar( rs.getString( 6 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "DEF_MIB_GRP";
	}

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'MID = :mid'.
	 */
	@Transactional
	public DefMibGrp findByPrimaryKey(long mid) throws DefMibGrpDaoException
	{
		try {
			List<DefMibGrp> list = jdbcTemplate.query("SELECT MID, NAME, INDEXOID, INDEXVAR, DESCROID, DESCRVAR FROM " + getTableName() + " WHERE MID = ?", this,mid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new DefMibGrpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria ''.
	 */
	@Transactional
	public List<DefMibGrp> findAll() throws DefMibGrpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MID, NAME, INDEXOID, INDEXVAR, DESCROID, DESCRVAR FROM " + getTableName() + " ORDER BY MID", this);
		}
		catch (Exception e) {
			throw new DefMibGrpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'MID = :mid'.
	 */
	@Transactional
	public List<DefMibGrp> findWhereMidEquals(long mid) throws DefMibGrpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MID, NAME, INDEXOID, INDEXVAR, DESCROID, DESCRVAR FROM " + getTableName() + " WHERE MID = ? ORDER BY MID", this,mid);
		}
		catch (Exception e) {
			throw new DefMibGrpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'NAME = :name'.
	 */
	@Transactional
	public List<DefMibGrp> findWhereNameEquals(String name) throws DefMibGrpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MID, NAME, INDEXOID, INDEXVAR, DESCROID, DESCRVAR FROM " + getTableName() + " WHERE NAME = ? ORDER BY NAME", this,name);
		}
		catch (Exception e) {
			throw new DefMibGrpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'INDEXOID = :indexoid'.
	 */
	@Transactional
	public List<DefMibGrp> findWhereIndexoidEquals(String indexoid) throws DefMibGrpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MID, NAME, INDEXOID, INDEXVAR, DESCROID, DESCRVAR FROM " + getTableName() + " WHERE INDEXOID = ? ORDER BY INDEXOID", this,indexoid);
		}
		catch (Exception e) {
			throw new DefMibGrpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'INDEXVAR = :indexvar'.
	 */
	@Transactional
	public List<DefMibGrp> findWhereIndexvarEquals(String indexvar) throws DefMibGrpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MID, NAME, INDEXOID, INDEXVAR, DESCROID, DESCRVAR FROM " + getTableName() + " WHERE INDEXVAR = ? ORDER BY INDEXVAR", this,indexvar);
		}
		catch (Exception e) {
			throw new DefMibGrpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'DESCROID = :descroid'.
	 */
	@Transactional
	public List<DefMibGrp> findWhereDescroidEquals(String descroid) throws DefMibGrpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MID, NAME, INDEXOID, INDEXVAR, DESCROID, DESCRVAR FROM " + getTableName() + " WHERE DESCROID = ? ORDER BY DESCROID", this,descroid);
		}
		catch (Exception e) {
			throw new DefMibGrpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'DESCRVAR = :descrvar'.
	 */
	@Transactional
	public List<DefMibGrp> findWhereDescrvarEquals(String descrvar) throws DefMibGrpDaoException
	{
		try {
			return jdbcTemplate.query("SELECT MID, NAME, INDEXOID, INDEXVAR, DESCROID, DESCRVAR FROM " + getTableName() + " WHERE DESCRVAR = ? ORDER BY DESCRVAR", this,descrvar);
		}
		catch (Exception e) {
			throw new DefMibGrpDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the DEF_MIB_GRP table that matches the specified primary-key value.
	 */
	public DefMibGrp findByPrimaryKey(DefMibGrpPk pk) throws DefMibGrpDaoException
	{
		return findByPrimaryKey( pk.getMid() );
	}

	public List<DefMibGrp> findAllWhereMidEqualsMibInfoMid()
			throws DefMibGrpDaoException {
		try {
			return jdbcTemplate.query("SELECT a.MID, NAME, INDEXOID, INDEXVAR, DESCROID, DESCRVAR FROM DEF_MIB_GRP a,PREDEFMIB_INFO b WHERE a.MID = b.MID", this);
		}
		catch (Exception e) {
			throw new DefMibGrpDaoException("Query failed", e);
		}
	}

}
