package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VSyslogDevEventsAttentionDao;
import com.ibm.ncs.model.dto.VSyslogDevEventsAttention;
import com.ibm.ncs.model.exceptions.VSyslogDevEventsAttentionDaoException;
import java.util.List;

public interface VSyslogDevEventsAttentionDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VSyslogDevEventsAttention dto);

	/** 
	 * Returns all rows from the V_SYSLOG_DEV_EVENTS_ATTENTION table that match the criteria ''.
	 */
	public List<VSyslogDevEventsAttention> findAll() throws VSyslogDevEventsAttentionDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_DEV_EVENTS_ATTENTION table that match the criteria 'DEVIP = :devip'.
	 */
	public List<VSyslogDevEventsAttention> findWhereDevipEquals(String devip) throws VSyslogDevEventsAttentionDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_DEV_EVENTS_ATTENTION table that match the criteria 'MARK = :mark'.
	 */
	public List<VSyslogDevEventsAttention> findWhereMarkEquals(String mark) throws VSyslogDevEventsAttentionDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_DEV_EVENTS_ATTENTION table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	public List<VSyslogDevEventsAttention> findWhereSeverity1Equals(long severity1) throws VSyslogDevEventsAttentionDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_DEV_EVENTS_ATTENTION table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	public List<VSyslogDevEventsAttention> findWhereProcesssuggestEquals(String processsuggest) throws VSyslogDevEventsAttentionDaoException;

}
