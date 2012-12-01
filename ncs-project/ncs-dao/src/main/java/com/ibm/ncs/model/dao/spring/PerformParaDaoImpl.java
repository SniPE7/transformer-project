package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.PerformParaDao;
import com.ibm.ncs.model.dto.PerformPara;
import com.ibm.ncs.model.exceptions.PerformParaDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class PerformParaDaoImpl extends AbstractDAO implements ParameterizedRowMapper<PerformPara>, PerformParaDao
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
	 * @param dtid
	 * @param ecode
	 * @throws PerformParaDaoException
	 * @return List<PerformPara>
	 */
	public List<PerformPara> execute(long dtid, long ecode) throws PerformParaDaoException
	{
		try {
			return jdbcTemplate.query("select  b.MRID, 	b.DTID, 	b.MODID, 	b.EVEID, 	b.OIDGROUPNAME, 	b.OID, 	b.UNIT, 	b.DESCRIPTION, a.major from t_event_type_init a left join t_event_oid_init b  on a.eveid=b.eveid and  b.dtid=?  where  a.ecode=?", this,dtid,ecode);
		}
		catch (Exception e) {
			throw new PerformParaDaoException("Query failed", e);
		}
		
	}
	
	public List<PerformPara> findByDtidEcode(long dtid, long ecode) throws PerformParaDaoException
	{
		try {
			return jdbcTemplate.query("select  b.MRID, 	b.DTID, 	a.MODID, 	a.EVEID, 	b.OIDGROUPNAME, 	b.OID, 	b.UNIT, 	b.DESCRIPTION, a.major from t_event_type_init a left join t_event_oid_init b  on a.eveid=b.eveid and  b.dtid=?  where   a.ecode=?", this,dtid,ecode);
		}
		catch (Exception e) {
			throw new PerformParaDaoException("Query failed", e);
		}
		
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return PerformPara
	 */
	public PerformPara mapRow(ResultSet rs, int row) throws SQLException
	{
		PerformPara dto = new PerformPara();
		dto.setMrid( rs.getLong( 1 ) );
		dto.setDtid( rs.getLong( 2 ) );
		dto.setModid( rs.getLong( 3 ) );
		dto.setEveid( rs.getLong( 4 ) );
		dto.setOidgroupname( rs.getString( 5 ) );
		dto.setOid( rs.getString( 6 ) );
		dto.setUnit( rs.getString( 7 ) );
		dto.setDescription( rs.getString( 8 ) );
		dto.setMajor( rs.getString( 9 ) );
		return dto;
	}

}
