package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.EventsAttentionDao;
import com.ibm.ncs.model.dto.EventsAttention;
import com.ibm.ncs.model.dto.EventsAttentionPk;
import com.ibm.ncs.model.exceptions.EventsAttentionDaoException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface EventsAttentionDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return EventsAttentionPk
	 */
	public EventsAttentionPk insert(EventsAttention dto);

	/** 
	 * Updates a single row in the EVENTS_ATTENTION table.
	 */
	public void update(EventsAttentionPk pk, EventsAttention dto) throws EventsAttentionDaoException;

	/** 
	 * Deletes a single row in the EVENTS_ATTENTION table.
	 */
	public void delete(EventsAttentionPk pk) throws EventsAttentionDaoException;

	/** 
	 * Returns all rows from the EVENTS_ATTENTION table that match the criteria 'EVENTSATTENTION = :eventsattention'.
	 */
	public EventsAttention findByPrimaryKey(String eventsattention) throws EventsAttentionDaoException;

	/** 
	 * Returns all rows from the EVENTS_ATTENTION table that match the criteria ''.
	 */
	public List<EventsAttention> findAll() throws EventsAttentionDaoException;

	/** 
	 * Returns all rows from the EVENTS_ATTENTION table that match the criteria 'EVENTSATTENTION = :eventsattention'.
	 */
	public List<EventsAttention> findWhereEventsattentionEquals(String eventsattention) throws EventsAttentionDaoException;

	/** 
	 * Returns all rows from the EVENTS_ATTENTION table that match the criteria 'SEVERITY = :severity'.
	 */
	public List<EventsAttention> findWhereSeverityEquals(long severity) throws EventsAttentionDaoException;

	/** 
	 * Returns all rows from the EVENTS_ATTENTION table that match the criteria 'PROCESSSUGGEST = :processsuggest'.
	 */
	public List<EventsAttention> findWhereProcesssuggestEquals(String processsuggest) throws EventsAttentionDaoException;

	/** 
	 * Returns the rows from the EVENTS_ATTENTION table that matches the specified primary-key value.
	 */
	public EventsAttention findByPrimaryKey(EventsAttentionPk pk) throws EventsAttentionDaoException;

	/**
	 * Method 'update'
	 */
	@Transactional
	public void delete(EventsAttention dto);
	
	/**
	 * Method 'insert'
	 */
	@Transactional
	public void update(EventsAttention dto);
	public int deleteAll();
	public int insertEffect();
}
