package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.LinesEventsNotcareDao;
import com.ibm.ncs.model.dto.LinesEventsNotcare;
import com.ibm.ncs.model.dto.LinesEventsNotcarePk;
import com.ibm.ncs.model.exceptions.LinesEventsNotcareDaoException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface LinesEventsNotcareDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return LinesEventsNotcarePk
	 */
	public LinesEventsNotcarePk insert(LinesEventsNotcare dto);

	/** 
	 * Updates a single row in the LINES_EVENTS_NOTCARE table.
	 */
	public void update(LinesEventsNotcarePk pk, LinesEventsNotcare dto) throws LinesEventsNotcareDaoException;

	/** 
	 * Deletes a single row in the LINES_EVENTS_NOTCARE table.
	 */
	public void delete(LinesEventsNotcarePk pk) throws LinesEventsNotcareDaoException;

	/** 
	 * Returns all rows from the LINES_EVENTS_NOTCARE table that match the criteria 'LINESNOTCARE = :linesnotcare'.
	 */
	public LinesEventsNotcare findByPrimaryKey(String linesnotcare) throws LinesEventsNotcareDaoException;

	/** 
	 * Returns all rows from the LINES_EVENTS_NOTCARE table that match the criteria ''.
	 */
	public List<LinesEventsNotcare> findAll() throws LinesEventsNotcareDaoException;

	/** 
	 * Returns all rows from the LINES_EVENTS_NOTCARE table that match the criteria 'LINESNOTCARE = :linesnotcare'.
	 */
	public List<LinesEventsNotcare> findWhereLinesnotcareEquals(String linesnotcare) throws LinesEventsNotcareDaoException;

	/** 
	 * Returns the rows from the LINES_EVENTS_NOTCARE table that matches the specified primary-key value.
	 */
	public LinesEventsNotcare findByPrimaryKey(LinesEventsNotcarePk pk) throws LinesEventsNotcareDaoException;

	/**
	 * Method 'update'
	 */
	@Transactional
	public void delete(LinesEventsNotcare dto);
	
	/**
	 * Method 'insert'
	 */
	@Transactional
	public void update(LinesEventsNotcare dto);
	
	public int deleteAll() throws LinesEventsNotcareDaoException;
	public int insertEffect();
}
