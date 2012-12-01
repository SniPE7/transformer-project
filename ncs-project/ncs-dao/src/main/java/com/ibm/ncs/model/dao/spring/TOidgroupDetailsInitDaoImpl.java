package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dto.TOidgroupDetailsInit;
import com.ibm.ncs.model.dto.TOidgroupDetailsInitPk;
import com.ibm.ncs.model.exceptions.TOidgroupDetailsInitDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TOidgroupDetailsInitDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TOidgroupDetailsInit>, TOidgroupDetailsInitDao
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
	 * @return TOidgroupDetailsInitPk
	 */
	@Transactional
	public TOidgroupDetailsInitPk insert(TOidgroupDetailsInit dto)
	{
		//System.out.println(dto);
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX ) VALUES ( ?, ?, ?, ?, ?, ? )",dto.getOpid(),dto.getOidvalue(),dto.getOidname(),dto.getOidunit(),dto.getFlag(),dto.isOidindexNull()?null:dto.getOidindex());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_OIDGROUP_DETAILS_INIT table.
	 */
	@Transactional
	public void update(TOidgroupDetailsInitPk pk, TOidgroupDetailsInit dto) throws TOidgroupDetailsInitDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET OPID = ?, OIDVALUE = ?, OIDNAME = ?, OIDUNIT = ?, FLAG = ?, OIDINDEX = ? WHERE OPID = ? AND OIDNAME = ?",dto.getOpid(),dto.getOidvalue(),dto.getOidname(),dto.getOidunit(),dto.getFlag(),dto.getOidindex(),pk.getOpid(),pk.getOidname());
	}

	/** 
	 * Deletes a single row in the T_OIDGROUP_DETAILS_INIT table.
	 */
	@Transactional
	public void delete(TOidgroupDetailsInitPk pk) throws TOidgroupDetailsInitDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE OPID = ? AND OIDNAME = ?",pk.getOpid(),pk.getOidname());
	}

	public void delete(long opid) throws TOidgroupDetailsInitDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE OPID = ? ",opid);
	}
	
	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TOidgroupDetailsInit
	 */
	public TOidgroupDetailsInit mapRow(ResultSet rs, int row) throws SQLException
	{
		TOidgroupDetailsInit dto = new TOidgroupDetailsInit();
		dto.setOpid( rs.getLong( 1 ) );
		dto.setOidvalue( rs.getString( 2 ) );
		dto.setOidname( rs.getString( 3 ) );
		dto.setOidunit( rs.getString( 4 ) );
		dto.setFlag( rs.getString( 5 ) );
		dto.setOidindex( rs.getLong( 6 ) );
		if (rs.wasNull()) {
			dto.setOidindexNull( true );
		}
		
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_OIDGROUP_DETAILS_INIT";
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'OPID = :opid AND OIDNAME = :oidname'.
	 */
	@Transactional
	public TOidgroupDetailsInit findByPrimaryKey(long opid, String oidname) throws TOidgroupDetailsInitDaoException
	{
		try {
			List<TOidgroupDetailsInit> list = jdbcTemplate.query("SELECT OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE OPID = ? AND OIDNAME = ?", this,opid,oidname);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TOidgroupDetailsInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria ''.
	 */
	@Transactional
	public List<TOidgroupDetailsInit> findAll() throws TOidgroupDetailsInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " ORDER BY OPID, OIDNAME", this);
		}
		catch (Exception e) {
			throw new TOidgroupDetailsInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'OPID = :opid'.
	 */
	@Transactional
	public List<TOidgroupDetailsInit> findWhereOpidEquals(long opid) throws TOidgroupDetailsInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE OPID = ? ORDER BY OPID", this,opid);
		}
		catch (Exception e) {
			throw new TOidgroupDetailsInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'OIDVALUE = :oidvalue'.
	 */
	@Transactional
	public List<TOidgroupDetailsInit> findWhereOidvalueEquals(String oidvalue) throws TOidgroupDetailsInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE OIDVALUE = ? ORDER BY OIDVALUE", this,oidvalue);
		}
		catch (Exception e) {
			throw new TOidgroupDetailsInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'OIDNAME = :oidname'.
	 */
	@Transactional
	public List<TOidgroupDetailsInit> findWhereOidnameEquals(String oidname) throws TOidgroupDetailsInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE OIDNAME = ? ORDER BY OIDNAME", this,oidname);
		}
		catch (Exception e) {
			throw new TOidgroupDetailsInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'OIDUNIT = :oidunit'.
	 */
	@Transactional
	public List<TOidgroupDetailsInit> findWhereOidunitEquals(String oidunit) throws TOidgroupDetailsInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE OIDUNIT = ? ORDER BY OIDUNIT", this,oidunit);
		}
		catch (Exception e) {
			throw new TOidgroupDetailsInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'FLAG = :flag'.
	 */
	@Transactional
	public List<TOidgroupDetailsInit> findWhereFlagEquals(String flag) throws TOidgroupDetailsInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE FLAG = ? ORDER BY FLAG", this,flag);
		}
		catch (Exception e) {
			throw new TOidgroupDetailsInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'OIDINDEX = :oidindex'.
	 */
	@Transactional
	public List<TOidgroupDetailsInit> findWhereOidindexEquals(long oidindex) throws TOidgroupDetailsInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE OIDINDEX = ? ORDER BY OIDINDEX", this,oidindex);
		}
		catch (Exception e) {
			throw new TOidgroupDetailsInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_OIDGROUP_DETAILS_INIT table that matches the specified primary-key value.
	 */
	public TOidgroupDetailsInit findByPrimaryKey(TOidgroupDetailsInitPk pk) throws TOidgroupDetailsInitDaoException
	{
		return findByPrimaryKey( pk.getOpid(), pk.getOidname() );
	}

}
