package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.TEventOidInit;
import com.ibm.ncs.model.dto.TOidgroupInfoInit;
import com.ibm.ncs.model.dto.TOidgroupInfoInitPk;
import com.ibm.ncs.model.exceptions.TEventOidInitDaoException;
import com.ibm.ncs.model.exceptions.TOidgroupInfoInitDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TOidgroupInfoInitDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TOidgroupInfoInit>, TOidgroupInfoInitDao
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
	 * @return TOidgroupInfoInitPk
	 */
	@Transactional
	public TOidgroupInfoInitPk insert(TOidgroupInfoInit dto)
	{
		//System.out.println(dto);
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( OPID, OIDGROUPNAME, OTYPE, DESCRIPTION ) VALUES ( ?, ?, ?, ? )",dto.getOpid(),dto.getOidgroupname(),dto.getOtype(),dto.getDescription());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the T_OIDGROUP_INFO_INIT table.
	 */
	@Transactional
	public void update(TOidgroupInfoInitPk pk, TOidgroupInfoInit dto) throws TOidgroupInfoInitDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET OPID = ?, OIDGROUPNAME = ?, OTYPE = ?, DESCRIPTION = ? WHERE OPID = ?",dto.getOpid(),dto.getOidgroupname(),dto.getOtype(),dto.getDescription(),pk.getOpid());
	}

	/** 
	 * Deletes a single row in the T_OIDGROUP_INFO_INIT table.
	 */
	@Transactional
	public void delete(TOidgroupInfoInitPk pk) throws TOidgroupInfoInitDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE OPID = ?",pk.getOpid());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TOidgroupInfoInit
	 */
	public TOidgroupInfoInit mapRow(ResultSet rs, int row) throws SQLException
	{
		TOidgroupInfoInit dto = new TOidgroupInfoInit();
		dto.setOpid( rs.getLong( 1 ) );
		dto.setOidgroupname( rs.getString( 2 ) );
		dto.setOtype( rs.getLong( 3 ) );
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
		return "T_OIDGROUP_INFO_INIT";
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_INFO_INIT table that match the criteria 'OPID = :opid'.
	 */
	@Transactional
	public TOidgroupInfoInit findByPrimaryKey(long opid) throws TOidgroupInfoInitDaoException
	{
		try {
			List<TOidgroupInfoInit> list = jdbcTemplate.query("SELECT OPID, OIDGROUPNAME, OTYPE, DESCRIPTION FROM " + getTableName() + " WHERE OPID = ?", this,opid);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TOidgroupInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_INFO_INIT table that match the criteria ''.
	 */
	@Transactional
	public List<TOidgroupInfoInit> findAll() throws TOidgroupInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT OPID, OIDGROUPNAME, OTYPE, DESCRIPTION FROM " + getTableName() + " ORDER BY OPID", this);
		}
		catch (Exception e) {
			throw new TOidgroupInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_INFO_INIT table that match the criteria 'OPID = :opid'.
	 */
	@Transactional
	public List<TOidgroupInfoInit> findWhereOpidEquals(long opid) throws TOidgroupInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT OPID, OIDGROUPNAME, OTYPE, DESCRIPTION FROM " + getTableName() + " WHERE OPID = ? ORDER BY OPID", this,opid);
		}
		catch (Exception e) {
			throw new TOidgroupInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_INFO_INIT table that match the criteria 'OIDGROUPNAME = :oidgroupname'.
	 */
	@Transactional
	public List<TOidgroupInfoInit> findWhereOidgroupnameEquals(String oidgroupname) throws TOidgroupInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT OPID, OIDGROUPNAME, OTYPE, DESCRIPTION FROM " + getTableName() + " WHERE OIDGROUPNAME = ? ORDER BY OIDGROUPNAME", this,oidgroupname);
		}
		catch (Exception e) {
			throw new TOidgroupInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_INFO_INIT table that match the criteria 'OTYPE = :otype'.
	 */
	@Transactional
	public List<TOidgroupInfoInit> findWhereOtypeEquals(long otype) throws TOidgroupInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT OPID, OIDGROUPNAME, OTYPE, DESCRIPTION FROM " + getTableName() + " WHERE OTYPE = ? ORDER BY OTYPE", this,otype);
		}
		catch (Exception e) {
			throw new TOidgroupInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the T_OIDGROUP_INFO_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	@Transactional
	public List<TOidgroupInfoInit> findWhereDescriptionEquals(String description) throws TOidgroupInfoInitDaoException
	{
		try {
			return jdbcTemplate.query("SELECT OPID, OIDGROUPNAME, OTYPE, DESCRIPTION FROM " + getTableName() + " WHERE DESCRIPTION = ? ORDER BY DESCRIPTION", this,description);
		}
		catch (Exception e) {
			throw new TOidgroupInfoInitDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the T_OIDGROUP_INFO_INIT table that matches the specified primary-key value.
	 */
	public TOidgroupInfoInit findByPrimaryKey(TOidgroupInfoInitPk pk) throws TOidgroupInfoInitDaoException
	{
		return findByPrimaryKey( pk.getOpid() );
	}

	public List<TOidgroupInfoInit> listOccupied() throws TOidgroupInfoInitDaoException
	{
		try {
//			String sql = "select OPID, OIDGROUPNAME, OTYPE, DESCRIPTION  from t_oidgroup_info_init a where  oidgroupname in"+
//			     "  ("+
//			     "  select b.oidgroupname from t_event_oid_init b , t_device_type_init c ,t_device_info d"+
//			      " where b.dtid=c.dtid and c.dtid=d.dtid "+
//			     "  and d.devid in  "+
//			     "       ("+
//			     "       select devid from "+
//			     "       ("+
//			     "         select devid from t_device_info x where devid in (select devid from t_devpol_map) "+
//			     "         union"+
//			     "         select devid from t_port_info y where ptid in (select ptid from t_linepol_map)"+
//			     "         union"+
//			     "         select devid from predefmib_info z where pdmid in (select pdmid from predefmib_pol_map)"+
//			     "       )"+
//			     "      )"+
//			     "   )";
			/*String sql = "select OPID, OIDGROUPNAME, OTYPE, DESCRIPTION  from t_oidgroup_info_init a where  oidgroupname in"+
		     "  ("+
		     "  select b.oidgroupname from t_event_oid_init b , t_device_type_init c ,t_device_info d"+
		      " where b.dtid=c.dtid and c.dtid=d.dtid )";*/
			String sql = "select OPID, OIDGROUPNAME, OTYPE, DESCRIPTION  from t_oidgroup_info_init a where  oidgroupname in"+
		     "  ("+
		     "  select b.oidgroupname from t_event_oid_init b)";
			return jdbcTemplate.query(sql, this);
		}
		catch (Exception e) {
			throw new TOidgroupInfoInitDaoException("Query failed", e);
		}
		
	}

	
	public List<TOidgroupInfoInit> listOccupied(String major) throws TOidgroupInfoInitDaoException
	{
		try {
			
			String sql = "select *  from t_oidgroup_info_init  where  oidgroupname like '"+major+"%'";
			System.out.println("sql is "+sql);
			return jdbcTemplate.query(sql, this);
		}
		catch (Exception e) {
			throw new TOidgroupInfoInitDaoException("Query failed", e);
		}
		
	}
	
}
