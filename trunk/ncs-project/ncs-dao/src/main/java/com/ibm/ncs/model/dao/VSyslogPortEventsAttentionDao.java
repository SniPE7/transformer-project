package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VSyslogPortEventsAttentionDao;
import com.ibm.ncs.model.dto.VSyslogPortEventsAttention;
import com.ibm.ncs.model.exceptions.VSyslogPortEventsAttentionDaoException;
import java.util.List;

public interface VSyslogPortEventsAttentionDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VSyslogPortEventsAttention dto);

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_EVENTS_ATTENTION table that match the criteria ''.
	 */
	public List<VSyslogPortEventsAttention> findAll() throws VSyslogPortEventsAttentionDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_EVENTS_ATTENTION table that match the criteria 'DEVIP = :devip'.
	 */
	public List<VSyslogPortEventsAttention> findWhereDevipEquals(String devip) throws VSyslogPortEventsAttentionDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_EVENTS_ATTENTION table that match the criteria 'MARK = :mark'.
	 */
	public List<VSyslogPortEventsAttention> findWhereMarkEquals(String mark) throws VSyslogPortEventsAttentionDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_EVENTS_ATTENTION table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	public List<VSyslogPortEventsAttention> findWhereSeverity1Equals(long severity1) throws VSyslogPortEventsAttentionDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_EVENTS_ATTENTION table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	public List<VSyslogPortEventsAttention> findWhereProcesssuggestEquals(String processsuggest) throws VSyslogPortEventsAttentionDaoException;

}
