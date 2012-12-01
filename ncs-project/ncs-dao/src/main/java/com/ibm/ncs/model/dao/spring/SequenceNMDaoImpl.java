package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.SequenceNMDao;
import com.ibm.ncs.model.dto.SequenceNM;
import com.ibm.ncs.model.exceptions.SequenceNMDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class SequenceNMDaoImpl extends AbstractDAO implements ParameterizedRowMapper<SequenceNM>, SequenceNMDao
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
	 * Method 'execute'
	 * 
	 * @throws SequenceNMDaoException
	 * @return SequenceNM
	 */
	public List<SequenceNM> execute() throws SequenceNMDaoException
	{
		try {
			return  jdbcTemplate.query("SELECT NM_SEQ.NEXTVAL FROM DUAL", this);
		}
		catch (Exception e) {
			throw new SequenceNMDaoException("Query failed", e);
		}
		
	}
	
	public List<SequenceNM> usedMax() throws SequenceNMDaoException
	{
		String sql = "select max(max) nextval from " +
				"( select max(mid) max from def_mib_grp union " +
				"  select max(spid) max from policy_syslog union " +
				"  select max(pdmid) max from predefmib_info union " +
				"  select max(id) max from t_category_map_init union " +
				"  select max(devid) max from t_device_info union " +
				"  select max(dtid) max from t_device_type_init union " +
				"  select max(eveid) max from t_event_type_init union " +
				"  select max(gid) max from t_grp_net union " +
				"  select max(mrid) max from t_manufacturer_info_init union " +
				"  select max(modid) max from t_module_info_init union " +
				"  select max(opid) max from t_oidgroup_details_init union " +
				"  select max(opid) max from t_oidgroup_info_init union " +
				"  select max(MPID) max from t_policy_base union " +
				"  select max(ppid) max from t_policy_period union " +
				"  select max(ptid) max from t_port_info union " +
				"  select max(nmsid) max from t_server_info union " +
				"  select max(usid) max from t_user " +
				")";
			
		try {
			return  jdbcTemplate.query(sql, this);
		}
		catch (Exception e) {
			throw new SequenceNMDaoException("Query failed", e);
		}
	}
		
		

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return SequenceNM
	 */
	public SequenceNM mapRow(ResultSet rs, int row) throws SQLException
	{
		SequenceNM dto = new SequenceNM();
		dto.setGenID( new Long( rs.getLong(1) ) );
		return dto;
	}

}
