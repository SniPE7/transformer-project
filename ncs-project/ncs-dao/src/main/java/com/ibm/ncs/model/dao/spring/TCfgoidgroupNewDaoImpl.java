package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TCfgoidgroupNewDao;
import com.ibm.ncs.model.dto.TCfgoidgroupNew;
import com.ibm.ncs.model.exceptions.TCfgoidgroupNewDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TCfgoidgroupNewDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TCfgoidgroupNew>, TCfgoidgroupNewDao
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
	 */
	@Transactional
	public void insert(TCfgoidgroupNew dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( S_NO, OIDGROUPNAME, OIDVALUE, OIDNAME, OIDINDEX, OIDUNIT, NMSIP, TARFILENAME ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )",dto.getSNo(),dto.getOidgroupname(),dto.getOidvalue(),dto.getOidname(),dto.getOidindex(),dto.getOidunit(),dto.getNmsip(),dto.getTarfilename());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TCfgoidgroupNew
	 */
	public TCfgoidgroupNew mapRow(ResultSet rs, int row) throws SQLException
	{
		TCfgoidgroupNew dto = new TCfgoidgroupNew();
		dto.setSNo( new Long( rs.getLong(1) ) );
		if (rs.wasNull()) {
			dto.setSNo( null );
		}
		
		dto.setOidgroupname( rs.getString( 2 ) );
		dto.setOidvalue( rs.getString( 3 ) );
		dto.setOidname( rs.getString( 4 ) );
		dto.setOidindex( new Long( rs.getLong(5) ) );
		if (rs.wasNull()) {
			dto.setOidindex( null );
		}
		
		dto.setOidunit( rs.getString( 6 ) );
		dto.setNmsip( rs.getString( 7 ) );
		dto.setTarfilename( rs.getString( 8 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "T_CFGOIDGROUP_NEW";
	}

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria ''.
	 */
	@Transactional
	public List<TCfgoidgroupNew> findAll() throws TCfgoidgroupNewDaoException
	{
		try {
			return jdbcTemplate.query("SELECT S_NO, OIDGROUPNAME, OIDVALUE, OIDNAME, OIDINDEX, OIDUNIT, NMSIP, TARFILENAME FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new TCfgoidgroupNewDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'S_NO = :sNo'.
	 */
	@Transactional
	public List<TCfgoidgroupNew> findWhereSNoEquals(Long sNo) throws TCfgoidgroupNewDaoException
	{
		try {
			return jdbcTemplate.query("SELECT S_NO, OIDGROUPNAME, OIDVALUE, OIDNAME, OIDINDEX, OIDUNIT, NMSIP, TARFILENAME FROM " + getTableName() + " WHERE S_NO = ? ORDER BY S_NO", this,sNo);
		}
		catch (Exception e) {
			throw new TCfgoidgroupNewDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'OIDGROUPNAME = :oidgroupname'.
	 */
	@Transactional
	public List<TCfgoidgroupNew> findWhereOidgroupnameEquals(String oidgroupname) throws TCfgoidgroupNewDaoException
	{
		try {
			return jdbcTemplate.query("SELECT S_NO, OIDGROUPNAME, OIDVALUE, OIDNAME, OIDINDEX, OIDUNIT, NMSIP, TARFILENAME FROM " + getTableName() + " WHERE OIDGROUPNAME = ? ORDER BY OIDGROUPNAME", this,oidgroupname);
		}
		catch (Exception e) {
			throw new TCfgoidgroupNewDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'OIDVALUE = :oidvalue'.
	 */
	@Transactional
	public List<TCfgoidgroupNew> findWhereOidvalueEquals(String oidvalue) throws TCfgoidgroupNewDaoException
	{
		try {
			return jdbcTemplate.query("SELECT S_NO, OIDGROUPNAME, OIDVALUE, OIDNAME, OIDINDEX, OIDUNIT, NMSIP, TARFILENAME FROM " + getTableName() + " WHERE OIDVALUE = ? ORDER BY OIDVALUE", this,oidvalue);
		}
		catch (Exception e) {
			throw new TCfgoidgroupNewDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'OIDNAME = :oidname'.
	 */
	@Transactional
	public List<TCfgoidgroupNew> findWhereOidnameEquals(String oidname) throws TCfgoidgroupNewDaoException
	{
		try {
			return jdbcTemplate.query("SELECT S_NO, OIDGROUPNAME, OIDVALUE, OIDNAME, OIDINDEX, OIDUNIT, NMSIP, TARFILENAME FROM " + getTableName() + " WHERE OIDNAME = ? ORDER BY OIDNAME", this,oidname);
		}
		catch (Exception e) {
			throw new TCfgoidgroupNewDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'OIDINDEX = :oidindex'.
	 */
	@Transactional
	public List<TCfgoidgroupNew> findWhereOidindexEquals(Long oidindex) throws TCfgoidgroupNewDaoException
	{
		try {
			return jdbcTemplate.query("SELECT S_NO, OIDGROUPNAME, OIDVALUE, OIDNAME, OIDINDEX, OIDUNIT, NMSIP, TARFILENAME FROM " + getTableName() + " WHERE OIDINDEX = ? ORDER BY OIDINDEX", this,oidindex);
		}
		catch (Exception e) {
			throw new TCfgoidgroupNewDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'OIDUNIT = :oidunit'.
	 */
	@Transactional
	public List<TCfgoidgroupNew> findWhereOidunitEquals(String oidunit) throws TCfgoidgroupNewDaoException
	{
		try {
			return jdbcTemplate.query("SELECT S_NO, OIDGROUPNAME, OIDVALUE, OIDNAME, OIDINDEX, OIDUNIT, NMSIP, TARFILENAME FROM " + getTableName() + " WHERE OIDUNIT = ? ORDER BY OIDUNIT", this,oidunit);
		}
		catch (Exception e) {
			throw new TCfgoidgroupNewDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'NMSIP = :nmsip'.
	 */
	@Transactional
	public List<TCfgoidgroupNew> findWhereNmsipEquals(String nmsip) throws TCfgoidgroupNewDaoException
	{
		try {
			return jdbcTemplate.query("SELECT S_NO, OIDGROUPNAME, OIDVALUE, OIDNAME, OIDINDEX, OIDUNIT, NMSIP, TARFILENAME FROM " + getTableName() + " WHERE NMSIP = ? ORDER BY NMSIP", this,nmsip);
		}
		catch (Exception e) {
			throw new TCfgoidgroupNewDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'TARFILENAME = :tarfilename'.
	 */
	@Transactional
	public List<TCfgoidgroupNew> findWhereTarfilenameEquals(String tarfilename) throws TCfgoidgroupNewDaoException
	{
		try {
			return jdbcTemplate.query("SELECT S_NO, OIDGROUPNAME, OIDVALUE, OIDNAME, OIDINDEX, OIDUNIT, NMSIP, TARFILENAME FROM " + getTableName() + " WHERE TARFILENAME = ? ORDER BY TARFILENAME", this,tarfilename);
		}
		catch (Exception e) {
			throw new TCfgoidgroupNewDaoException("Query failed", e);
		}
		
	}

}
