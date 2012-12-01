package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.DspEventsFromPolicySyslogDao;
import com.ibm.ncs.model.dto.DspEventsFromPolicySyslog;
import com.ibm.ncs.model.exceptions.DspEventsFromPolicySyslogDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class DspEventsFromPolicySyslogDaoImpl extends AbstractDAO implements ParameterizedRowMapper<DspEventsFromPolicySyslog>, DspEventsFromPolicySyslogDao
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
	 * @param manufacture
	 * @param mpid
	 * @throws DspEventsFromPolicySyslogDaoException
	 * @return List<DspEventsFromPolicySyslog>
	 */
	public List<DspEventsFromPolicySyslog> execute(java.lang.String manufacture, long mpid) throws DspEventsFromPolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("select m.events, a.*  from POLICY_SYSLOG a,          (select * from syslog_events_process union select * from syslog_events_process_ns) m where a.mark=m.mark and a.manufacture=m.manufacture  and a.manufacture=?  and a.mpid = ?", this,manufacture,mpid);
		}
		catch (Exception e) {
			throw new DspEventsFromPolicySyslogDaoException("Query failed", e);
		}
		
	}

	/**
	 * Method 'findDspEventsByManufactureAndMpid'
	 * 
	 * @param manufacture
	 * @param mpid
	 * @throws DspEventsFromPolicySyslogDaoException
	 * @return List<DspEventsFromPolicySyslog>
	 */
	public List<DspEventsFromPolicySyslog> findDspEventsByManufactureAndMpid(java.lang.String manufacture, long mpid) throws DspEventsFromPolicySyslogDaoException
	{
		try {
			return jdbcTemplate.query("select m.events, a.*  from POLICY_SYSLOG a, " +
					"(select * from syslog_events_process union select * from syslog_events_process_ns) m " +
					"where a.mark=m.mark and a.manufacture=m.manufacture  and a.manufacture=?  and a.mpid = ?", this,manufacture,mpid);
		}
		catch (Exception e) {
			throw new DspEventsFromPolicySyslogDaoException("Query failed", e);
		}
		
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return DspEventsFromPolicySyslog
	 */
	public DspEventsFromPolicySyslog mapRow(ResultSet rs, int row) throws SQLException
	{
		DspEventsFromPolicySyslog dto = new DspEventsFromPolicySyslog();
		dto.setEvents( rs.getString( 1 ) );
		dto.setSpid( rs.getLong( 2 ) );
		dto.setMpid( rs.getLong( 3 ) );
		if (rs.wasNull()) {
			dto.setMpidNull( true );
		}
		dto.setMark( rs.getString( 4 ) );
		dto.setManufacture( rs.getString( 5 ) );
		dto.setEventtype( rs.getLong( 6 ) );
		if (rs.wasNull()) {
			dto.setEventtypeNull( true );
		}
		dto.setSeverity1( rs.getLong( 7 ) );
		if (rs.wasNull()) {
			dto.setSeverity1Null( true );
		}
		dto.setSeverity2( rs.getLong( 8 ) );
		if (rs.wasNull()) {
			dto.setSeverity2Null( true );
		}
		dto.setFilterflag1( rs.getLong( 9 ) );
		if (rs.wasNull()) {
			dto.setFilterflag1Null( true );
		}
		dto.setFilterflag2( rs.getLong( 10 ) );
		if (rs.wasNull()) {
			dto.setFilterflag2Null( true );
		}
		return dto;
	}

}
