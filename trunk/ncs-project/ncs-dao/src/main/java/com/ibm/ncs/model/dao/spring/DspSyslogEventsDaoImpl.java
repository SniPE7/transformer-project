package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.DspSyslogEventsDao;
import com.ibm.ncs.model.dto.DspSyslogEvents;
import com.ibm.ncs.model.exceptions.DspSyslogEventsDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class DspSyslogEventsDaoImpl extends AbstractDAO implements ParameterizedRowMapper<DspSyslogEvents>, DspSyslogEventsDao
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
	 * @param mark
	 * @param manufacture
	 * @throws DspSyslogEventsDaoException
	 * @return List<DspSyslogEvents>
	 */
	public List<DspSyslogEvents> execute(java.lang.String mark, java.lang.String manufacture) throws DspSyslogEventsDaoException
	{
		try {
			return jdbcTemplate.query("select * from syslog_events_process union select * from syslog_events_process_ns where mark=? and manufacture=?", this,mark,manufacture);
		}
		catch (Exception e) {
			throw new DspSyslogEventsDaoException("Query failed", e);
		}
		
	}

	/**
	 * Method 'listSyslogEventsByMarkAndManufacture'
	 * 
	 * @param mark
	 * @param manufacture
	 * @throws DspSyslogEventsDaoException
	 * @return List<DspSyslogEvents>
	 */
	public List<DspSyslogEvents> listSyslogEventsByMarkAndManufacture(java.lang.String mark, java.lang.String manufacture) throws DspSyslogEventsDaoException
	{
		try {
			return jdbcTemplate.query("select * from syslog_events_process union select * from syslog_events_process_ns where mark=? and manufacture=?", this,mark,manufacture);
		}
		catch (Exception e) {
			throw new DspSyslogEventsDaoException("Query failed", e);
		}
		
	}
	
//	/**
//	 * Method 'listSyslogEvents'
//	 * 
//	 * @param mark
//	 * @param manufacture
//	 * @throws DspSyslogEventsDaoException
//	 * @return List<DspSyslogEvents>
//	 */
//	public List<DspSyslogEvents> listSyslogEvents() throws DspSyslogEventsDaoException
//	{
//		try {
//			//return jdbcTemplate.query("select * from syslog_events_process union select * from syslog_events_process_ns", this);
//			return jdbcTemplate.query("select * from "+
//					" ( select * from syslog_events_process union select * from syslog_events_process_ns )" +
//					" where trim(mark)||trim(manufacture) not in (select trim(mark)||trim(manufacture) from policy_syslog )"
//					, this);
//		}
//		catch (Exception e) {
//			throw new DspSyslogEventsDaoException("Query failed", e);
//		}
//		
//	}
	
	/**
	 * Method 'listSyslogEvents(mpid)' 
	 * 
	 * @param mark
	 * @param manufacture
	 * @throws DspSyslogEventsDaoException
	 * @return List<DspSyslogEvents>
	 */
	public List<DspSyslogEvents> listSyslogEventsOnMpid(long mpid) throws DspSyslogEventsDaoException
	{
		try {
			//return jdbcTemplate.query("select * from syslog_events_process union select * from syslog_events_process_ns", this);
			return jdbcTemplate.query("select * from "+
					" ( select * from syslog_events_process union select * from syslog_events_process_ns )" +
					" where trim(mark)||trim(manufacture) not in (select trim(mark)||trim(manufacture) from policy_syslog where mpid=?)"
					, this,mpid);
		}
		catch (Exception e) {
			throw new DspSyslogEventsDaoException("Query failed", e);
		}
		
	}
	
	/**
	 * Method 'listSyslogEvents'
	 * 
	 * @param mark
	 * @param manufacture
	 * @throws DspSyslogEventsDaoException
	 * @return List<DspSyslogEvents>
	 */
	public List<DspSyslogEvents> listOccupiedSyslogEvents() throws DspSyslogEventsDaoException
	{
		try {
			//return jdbcTemplate.query("select * from syslog_events_process union select * from syslog_events_process_ns", this);
			return jdbcTemplate.query("select * from "+
					" ( select * from syslog_events_process union select * from syslog_events_process_ns )"
					+
					" where trim(mark)||trim(manufacture)  in (select trim(mark)||trim(manufacture) from policy_syslog)"
					, this);
		}
		catch (Exception e) {
			throw new DspSyslogEventsDaoException("Query failed", e);
		}
		
	}
	
	/**
	 * Method 'listSyslogEventsByManufacture'
	 * 
	 * @param mark
	 * @param manufacture
	 * @throws DspSyslogEventsDaoException
	 * @return List<DspSyslogEvents>
	 */
	public List<DspSyslogEvents> listSyslogEventsByManufacture(java.lang.String manufacture) throws DspSyslogEventsDaoException
	{
		try {
			return jdbcTemplate.query("select * from syslog_events_process union select * from syslog_events_process_ns where mark=? and manufacture=?", this,manufacture);
		}
		catch (Exception e) {
			throw new DspSyslogEventsDaoException("Query failed", e);
		}
		
	}
	
	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return DspSyslogEvents
	 */
	public DspSyslogEvents mapRow(ResultSet rs, int row) throws SQLException
	{
		DspSyslogEvents dto = new DspSyslogEvents();
		dto.setMark( rs.getString( 1 ) );
		dto.setVarlist( rs.getString( 2 ) );
		dto.setBtimelist( rs.getString( 3 ) );
		dto.setEtimelist( rs.getString( 4 ) );
		dto.setFilterflag1( rs.getLong( 5 ) );
		dto.setFilterflag2( rs.getLong( 6 ) );
		dto.setSeverity1( rs.getLong( 7 ) );
		dto.setSeverity2( rs.getLong( 8 ) );
		dto.setPort( rs.getString( 9 ) );
		dto.setNotcareflag( rs.getLong( 10 ) );
		dto.setType( rs.getLong( 11 ) );
		dto.setEventtype( rs.getLong( 12 ) );
		dto.setSubeventtype( rs.getLong( 13 ) );
		dto.setAlertgroup( rs.getString( 14 ) );
		dto.setAlertkey( rs.getString( 15 ) );
		dto.setSummarycn( rs.getString( 16 ) );
		dto.setProcesssuggest( rs.getString( 17 ) );
		dto.setStatus( rs.getString( 18 ) );
		dto.setAttentionflag( rs.getLong( 19 ) );
		dto.setEvents( rs.getString( 20 ) );
		dto.setManufacture( rs.getString( 21 ) );
		dto.setOrigevent( rs.getString( 22 ) );
		return dto;
	}

}
