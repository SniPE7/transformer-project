package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.VOidGroupDao;
import com.ibm.ncs.model.dto.VOidGroup;
import com.ibm.ncs.model.exceptions.VOidGroupDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class VOidGroupDaoImpl extends AbstractDAO implements ParameterizedRowMapper<VOidGroup>, VOidGroupDao
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
	public void insert(VOidGroup dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( T_OIDGROUP_INFO_INIT_OPID, OIDGROUPNAME, OTYPE, DESCRIPTION, T_OIDGROUP_DETAILS_INIT_OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getTOidgroupInfoInitOpid(),dto.getOidgroupname(),dto.getOtype(),dto.getDescription(),dto.getTOidgroupDetailsInitOpid(),dto.getOidvalue(),dto.getOidname(),dto.getOidunit(),dto.getFlag(),dto.isOidindexNull()?null:dto.getOidindex());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return VOidGroup
	 */
	public VOidGroup mapRow(ResultSet rs, int row) throws SQLException
	{
		VOidGroup dto = new VOidGroup();
		dto.setTOidgroupInfoInitOpid( rs.getLong( 1 ) );
		dto.setOidgroupname( rs.getString( 2 ) );
		dto.setOtype( rs.getLong( 3 ) );
		dto.setDescription( rs.getString( 4 ) );
		dto.setTOidgroupDetailsInitOpid( rs.getLong( 5 ) );
		dto.setOidvalue( rs.getString( 6 ) );
		dto.setOidname( rs.getString( 7 ) );
		dto.setOidunit( rs.getString( 8 ) );
		dto.setFlag( rs.getString( 9 ) );
		dto.setOidindex( rs.getLong( 10 ) );
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
		return "V_OID_GROUP";
	}

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria ''.
	 */
	@Transactional
	public List<VOidGroup> findAll() throws VOidGroupDaoException
	{
		try {
			return jdbcTemplate.query("SELECT T_OIDGROUP_INFO_INIT_OPID, OIDGROUPNAME, OTYPE, DESCRIPTION, T_OIDGROUP_DETAILS_INIT_OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new VOidGroupDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'T_OIDGROUP_INFO_INIT_OPID = :tOidgroupInfoInitOpid'.
	 */
	@Transactional
	public List<VOidGroup> findWhereTOidgroupInfoInitOpidEquals(long tOidgroupInfoInitOpid) throws VOidGroupDaoException
	{
		try {
			return jdbcTemplate.query("SELECT T_OIDGROUP_INFO_INIT_OPID, OIDGROUPNAME, OTYPE, DESCRIPTION, T_OIDGROUP_DETAILS_INIT_OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE T_OIDGROUP_INFO_INIT_OPID = ? ORDER BY T_OIDGROUP_INFO_INIT_OPID", this,tOidgroupInfoInitOpid);
		}
		catch (Exception e) {
			throw new VOidGroupDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'OIDGROUPNAME = :oidgroupname'.
	 */
	@Transactional
	public List<VOidGroup> findWhereOidgroupnameEquals(String oidgroupname) throws VOidGroupDaoException
	{
		try {
			return jdbcTemplate.query("SELECT T_OIDGROUP_INFO_INIT_OPID, OIDGROUPNAME, OTYPE, DESCRIPTION, T_OIDGROUP_DETAILS_INIT_OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE OIDGROUPNAME = ? ORDER BY OIDGROUPNAME", this,oidgroupname);
		}
		catch (Exception e) {
			throw new VOidGroupDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'OTYPE = :otype'.
	 */
	@Transactional
	public List<VOidGroup> findWhereOtypeEquals(long otype) throws VOidGroupDaoException
	{
		try {
			return jdbcTemplate.query("SELECT T_OIDGROUP_INFO_INIT_OPID, OIDGROUPNAME, OTYPE, DESCRIPTION, T_OIDGROUP_DETAILS_INIT_OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE OTYPE = ? ORDER BY OTYPE", this,otype);
		}
		catch (Exception e) {
			throw new VOidGroupDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<VOidGroup> findWhereDescriptionEquals(String description) throws VOidGroupDaoException
	{
		try {
			return jdbcTemplate.query("SELECT T_OIDGROUP_INFO_INIT_OPID, OIDGROUPNAME, OTYPE, DESCRIPTION, T_OIDGROUP_DETAILS_INIT_OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new VOidGroupDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'T_OIDGROUP_DETAILS_INIT_OPID = :tOidgroupDetailsInitOpid'.
	 */
	@Transactional
	public List<VOidGroup> findWhereTOidgroupDetailsInitOpidEquals(long tOidgroupDetailsInitOpid) throws VOidGroupDaoException
	{
		try {
			return jdbcTemplate.query("SELECT T_OIDGROUP_INFO_INIT_OPID, OIDGROUPNAME, OTYPE, DESCRIPTION, T_OIDGROUP_DETAILS_INIT_OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE T_OIDGROUP_DETAILS_INIT_OPID = ? ORDER BY T_OIDGROUP_DETAILS_INIT_OPID", this,tOidgroupDetailsInitOpid);
		}
		catch (Exception e) {
			throw new VOidGroupDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'OIDVALUE = :oidvalue'.
	 */
	@Transactional
	public List<VOidGroup> findWhereOidvalueEquals(String oidvalue) throws VOidGroupDaoException
	{
		try {
			return jdbcTemplate.query("SELECT T_OIDGROUP_INFO_INIT_OPID, OIDGROUPNAME, OTYPE, DESCRIPTION, T_OIDGROUP_DETAILS_INIT_OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE OIDVALUE = ? ORDER BY OIDVALUE", this,oidvalue);
		}
		catch (Exception e) {
			throw new VOidGroupDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'OIDNAME = :oidname'.
	 */
	@Transactional
	public List<VOidGroup> findWhereOidnameEquals(String oidname) throws VOidGroupDaoException
	{
		try {
			return jdbcTemplate.query("SELECT T_OIDGROUP_INFO_INIT_OPID, OIDGROUPNAME, OTYPE, DESCRIPTION, T_OIDGROUP_DETAILS_INIT_OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE OIDNAME = ? ORDER BY OIDNAME", this,oidname);
		}
		catch (Exception e) {
			throw new VOidGroupDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'OIDUNIT = :oidunit'.
	 */
	@Transactional
	public List<VOidGroup> findWhereOidunitEquals(String oidunit) throws VOidGroupDaoException
	{
		try {
			return jdbcTemplate.query("SELECT T_OIDGROUP_INFO_INIT_OPID, OIDGROUPNAME, OTYPE, DESCRIPTION, T_OIDGROUP_DETAILS_INIT_OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE OIDUNIT = ? ORDER BY OIDUNIT", this,oidunit);
		}
		catch (Exception e) {
			throw new VOidGroupDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'FLAG = :flag'.
	 */
	@Transactional
	public List<VOidGroup> findWhereFlagEquals(String flag) throws VOidGroupDaoException
	{
		try {
			return jdbcTemplate.query("SELECT T_OIDGROUP_INFO_INIT_OPID, OIDGROUPNAME, OTYPE, DESCRIPTION, T_OIDGROUP_DETAILS_INIT_OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE FLAG = ? ORDER BY FLAG", this,flag);
		}
		catch (Exception e) {
			throw new VOidGroupDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'OIDINDEX = :oidindex'.
	 */
	@Transactional
	public List<VOidGroup> findWhereOidindexEquals(long oidindex) throws VOidGroupDaoException
	{
		try {
			return jdbcTemplate.query("SELECT T_OIDGROUP_INFO_INIT_OPID, OIDGROUPNAME, OTYPE, DESCRIPTION, T_OIDGROUP_DETAILS_INIT_OPID, OIDVALUE, OIDNAME, OIDUNIT, FLAG, OIDINDEX FROM " + getTableName() + " WHERE OIDINDEX = ? ORDER BY OIDINDEX", this,oidindex);
		}
		catch (Exception e) {
			throw new VOidGroupDaoException("Query failed", e);
		}
		
	}

}
