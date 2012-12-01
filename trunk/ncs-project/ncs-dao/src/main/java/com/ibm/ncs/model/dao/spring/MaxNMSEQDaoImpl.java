package com.ibm.ncs.model.dao.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ibm.ncs.model.dao.MaxNMSEQDao;
import com.ibm.ncs.model.dto.MaxNMSEQ;
import com.ibm.ncs.model.exceptions.MaxNMSEQDaoException;

public class MaxNMSEQDaoImpl extends AbstractDAO implements ParameterizedRowMapper<MaxNMSEQ>, MaxNMSEQDao
{
	 private static Logger ncsLog = Logger.getLogger(MaxNMSEQDaoImpl.class);

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

//	/**
//	 * Method 'execute'
//	 * 
//	 * @throws MaxNMSEQDaoException
//	 * @return MaxNMSEQ
//	 */
//	public MaxNMSEQ execute() throws MaxNMSEQDaoException
//	{
//		try {
//			return jdbcTemplate.query("select last_number from user_sequences where sequence_name ='NM_SEQ';", this);
//		}
//		catch (Exception e) {
//			throw new MaxNMSEQDaoException("Query failed", e);
//		}
//		
//	}
	
	/**
	 * Method 'getMaxNMSeq'
	 * 
	 * @throws MaxNMSEQDaoException
	 * @return long
	 */
	public long getMaxNMSeq() throws MaxNMSEQDaoException
	{
		try {
			List<MaxNMSEQ> l = jdbcTemplate.query("select last_number from user_sequences where sequence_name ='NM_SEQ';", this);
			MaxNMSEQ mtmp=((MaxNMSEQ)l.get(0));
			long last = mtmp.getMax();
			return last;
//			List<MaxNMSEQ> l = 
//			jdbcTemplate.query("select last_number from user_sequences where sequence_name ='NM_SEQ';", this);
			
//			return ((MaxNMSEQ)l.get(0)).getMax() ;
		}
		catch (Exception e) {
			throw new MaxNMSEQDaoException("Query failed", e);
		}
		
	}

	

	/**
	 * Method 'setNMSeqNumber' Alter the sequence number to new value.
	 * 
	 * @throws MaxNMSEQDaoException
	 * @return long
	 */
	public long setNMSeqNumber(long newNumber) throws MaxNMSEQDaoException{
		try{
		
		List<MaxNMSEQ> l = jdbcTemplate.query("select last_number from user_sequences where sequence_name ='NM_SEQ'", this);
		MaxNMSEQ mtmp=((MaxNMSEQ)l.get(0));
		long last = mtmp.getMax();
		long offset =  newNumber - last;
//		System.out.println("NMSeqNumberDao... setNMSeqNumber... newNumber="+newNumber+"; last="+last+";  offset="+offset);
		try {
			jdbcTemplate.update("Alter SEQUENCE NM_SEQ INCREMENT BY "+offset+" ");
		} catch (Exception e) {
			ncsLog.info(this.getClass().getName() + "Exception on ...1...Alter SEQUENCE NM_SEQ INCREMENT BY "+offset+" ");
		}
		try {
			jdbcTemplate.query("select NM_SEQ.nextval from dual", this);
		} catch (Exception e) {
			ncsLog.info(this.getClass().getName() + "Exception on ...2...select NM_SEQ.nextval from dual");
		}
		try {
			jdbcTemplate.update("Alter SEQUENCE NM_SEQ INCREMENT BY 1");
		} catch (Exception e) {
			ncsLog.info(this.getClass().getName() + "Exception on ...3...Alter SEQUENCE NM_SEQ INCREMENT BY 1");
		}
		try {
			jdbcTemplate.query("select NM_SEQ.nextval from dual", this);
		} catch (Exception e) {
			ncsLog.info(this.getClass().getName() + "Exception on ...4...select NM_SEQ.nextval from dual");
		}
		ncsLog.debug(this.getClass().getName() + "sequence change action ...last_number ="+last +", offset="+offset);
		return newNumber;
	}
	catch (Exception e) {
		throw new MaxNMSEQDaoException("Query failed", e);
	}
	}
	
	
	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return MaxNMSEQ
	 */
	public MaxNMSEQ mapRow(ResultSet rs, int row) throws SQLException
	{
		MaxNMSEQ dto = new MaxNMSEQ();
		dto.setMax( rs.getLong( 1 ) );
		return dto;
	}

}
