package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VSyslogPdmEventsAttentionDao;
import com.ibm.ncs.model.dto.VSyslogPdmEventsAttention;
import com.ibm.ncs.model.exceptions.VSyslogPdmEventsAttentionDaoException;
import java.util.List;

public interface VSyslogPdmEventsAttentionDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VSyslogPdmEventsAttention dto);

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_EVENTS_ATTENTION table that match the criteria ''.
	 */
	public List<VSyslogPdmEventsAttention> findAll() throws VSyslogPdmEventsAttentionDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_EVENTS_ATTENTION table that match the criteria 'DEVIP = :devip'.
	 */
	public List<VSyslogPdmEventsAttention> findWhereDevipEquals(String devip) throws VSyslogPdmEventsAttentionDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_EVENTS_ATTENTION table that match the criteria 'MARK = :mark'.
	 */
	public List<VSyslogPdmEventsAttention> findWhereMarkEquals(String mark) throws VSyslogPdmEventsAttentionDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_EVENTS_ATTENTION table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	public List<VSyslogPdmEventsAttention> findWhereSeverity1Equals(long severity1) throws VSyslogPdmEventsAttentionDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_EVENTS_ATTENTION table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	public List<VSyslogPdmEventsAttention> findWhereProcesssuggestEquals(String processsuggest) throws VSyslogPdmEventsAttentionDaoException;

}
