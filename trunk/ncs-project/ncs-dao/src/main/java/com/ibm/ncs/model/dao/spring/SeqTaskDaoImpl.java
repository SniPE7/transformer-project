package com.ibm.ncs.model.dao.spring;

import com.ibm.ncs.model.dao.SeqTaskDao;
import com.ibm.ncs.model.dto.SeqTask;
import com.ibm.ncs.model.dto.SequenceNM;
import com.ibm.ncs.model.exceptions.SeqTaskDaoException;
import com.ibm.ncs.model.exceptions.SequenceNMDaoException;

import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class SeqTaskDaoImpl extends AbstractDAO implements ParameterizedRowMapper<SeqTask>, SeqTaskDao
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
	 * @throws SeqTaskDaoException
	 * @return SeqTask
	 */
	public List<SeqTask> execute() throws SeqTaskDaoException
	{
		try {
			return jdbcTemplate.query("SELECT TASK_SEQ.NEXTVAL FROM DUAL", this);
		}
		catch (Exception e) {
			throw new SeqTaskDaoException("Query failed", e);
		}
		
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return SeqTask
	 */
	public SeqTask mapRow(ResultSet rs, int row) throws SQLException
	{
		SeqTask dto = new SeqTask();
		dto.setGenTaskID( new Long( rs.getLong(1) ) );
		return dto;
	}
	
	public List<SeqTask> usedTaskMax() throws SeqTaskDaoException
	{
		String sql = "select max(max) nextval from " +
				"( select max(bk_id) max from bk_snmp_events_process union " +
				"  select max(bk_id) max from bk_syslog_events_process union " +
				"  select max(bk_id) max from bk_syslog_events_process_ns " +
				")";
			
		try {
			return  jdbcTemplate.query(sql, this);
		}
		catch (Exception e) {
			throw new SeqTaskDaoException("Query failed", e);
		}	
	}
	
	public long setSeqTaskNumber(long newNumber) throws SeqTaskDaoException{
	
		List<SeqTask> l = jdbcTemplate.query("select last_number from user_sequences where sequence_name='TASK_SEQ'", this); 
		SeqTask seqtmp = (SeqTask)l.get(0);
		long last = seqtmp.getGenTaskID();
		long offset = newNumber - last ;
//System.out.println("newNumber="+newNumber+"; last="+last+";  offset="+offset);
		jdbcTemplate.update("Alter SEQUENCE TASK_SEQ INCREMENT BY " +offset +" ");
		jdbcTemplate.query("select TASK_SEQ.nextval from dual", this);
		jdbcTemplate.update("Alter SEQUENCE TASK_SEQ INCREMENT BY 1");
		jdbcTemplate.query("select TASK_SEQ.nextval from dual", this);
		return newNumber;
		
	}
	
	public long getMaxTaskSeq() throws SeqTaskDaoException{
		List<SeqTask> l = jdbcTemplate.query("select last_number from user_sequences where sequence_name='TASK_SEQ'", this);
		SeqTask seqtmp = (SeqTask)l.get(0);
		long last = seqtmp.getGenTaskID();
		return last;
	}
}
