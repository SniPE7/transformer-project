package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TEventsubtypeInfoInitDao;
import com.ibm.ncs.model.dto.TEventsubtypeInfoInit;
import com.ibm.ncs.model.dto.TEventsubtypeInfoInitPk;
import com.ibm.ncs.model.exceptions.TEventsubtypeInfoInitDaoException;
import java.util.List;

public interface TEventsubtypeInfoInitDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TEventsubtypeInfoInitPk
	 */
	public TEventsubtypeInfoInitPk insert(TEventsubtypeInfoInit dto);

	/** 
	 * Updates a single row in the T_EVENTSUBTYPE_INFO_INIT table.
	 */
	public void update(TEventsubtypeInfoInitPk pk, TEventsubtypeInfoInit dto) throws TEventsubtypeInfoInitDaoException;

	/** 
	 * Deletes a single row in the T_EVENTSUBTYPE_INFO_INIT table.
	 */
	public void delete(TEventsubtypeInfoInitPk pk) throws TEventsubtypeInfoInitDaoException;

	/** 
	 * Returns all rows from the T_EVENTSUBTYPE_INFO_INIT table that match the criteria 'ESTID = :estid'.
	 */
	public TEventsubtypeInfoInit findByPrimaryKey(long estid) throws TEventsubtypeInfoInitDaoException;

	/** 
	 * Returns all rows from the T_EVENTSUBTYPE_INFO_INIT table that match the criteria ''.
	 */
	public List<TEventsubtypeInfoInit> findAll() throws TEventsubtypeInfoInitDaoException;

	/** 
	 * Returns all rows from the T_EVENTSUBTYPE_INFO_INIT table that match the criteria 'ESTID = :estid'.
	 */
	public List<TEventsubtypeInfoInit> findWhereEstidEquals(long estid) throws TEventsubtypeInfoInitDaoException;

	/** 
	 * Returns all rows from the T_EVENTSUBTYPE_INFO_INIT table that match the criteria 'EVESUBTYPE = :evesubtype'.
	 */
	public List<TEventsubtypeInfoInit> findWhereEvesubtypeEquals(String evesubtype) throws TEventsubtypeInfoInitDaoException;

	/** 
	 * Returns all rows from the T_EVENTSUBTYPE_INFO_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TEventsubtypeInfoInit> findWhereDescriptionEquals(String description) throws TEventsubtypeInfoInitDaoException;

	/** 
	 * Returns all rows from the T_EVENTSUBTYPE_INFO_INIT table that match the criteria 'ESTCODE = :estcode'.
	 */
	public List<TEventsubtypeInfoInit> findWhereEstcodeEquals(String estcode) throws TEventsubtypeInfoInitDaoException;

	/** 
	 * Returns the rows from the T_EVENTSUBTYPE_INFO_INIT table that matches the specified primary-key value.
	 */
	public TEventsubtypeInfoInit findByPrimaryKey(TEventsubtypeInfoInitPk pk) throws TEventsubtypeInfoInitDaoException;

}
