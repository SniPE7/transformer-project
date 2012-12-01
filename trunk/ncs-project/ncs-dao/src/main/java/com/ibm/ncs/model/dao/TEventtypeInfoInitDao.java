package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TEventtypeInfoInitDao;
import com.ibm.ncs.model.dto.TEventtypeInfoInit;
import com.ibm.ncs.model.dto.TEventtypeInfoInitPk;
import com.ibm.ncs.model.exceptions.TEventtypeInfoInitDaoException;
import java.util.List;

public interface TEventtypeInfoInitDao
{
	public String getTableName();
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TEventtypeInfoInitPk
	 */
	public TEventtypeInfoInitPk insert(TEventtypeInfoInit dto);

	/** 
	 * Updates a single row in the T_EVENTTYPE_INFO_INIT table.
	 */
	public void update(TEventtypeInfoInitPk pk, TEventtypeInfoInit dto) throws TEventtypeInfoInitDaoException;

	/** 
	 * Deletes a single row in the T_EVENTTYPE_INFO_INIT table.
	 */
	public void delete(TEventtypeInfoInitPk pk) throws TEventtypeInfoInitDaoException;

	/** 
	 * Returns all rows from the T_EVENTTYPE_INFO_INIT table that match the criteria 'ETID = :etid'.
	 */
	public TEventtypeInfoInit findByPrimaryKey(long etid) throws TEventtypeInfoInitDaoException;

	/** 
	 * Returns all rows from the T_EVENTTYPE_INFO_INIT table that match the criteria ''.
	 */
	public List<TEventtypeInfoInit> findAll() throws TEventtypeInfoInitDaoException;

	/** 
	 * Returns all rows from the T_EVENTTYPE_INFO_INIT table that match the criteria 'ETID = :etid'.
	 */
	public List<TEventtypeInfoInit> findWhereEtidEquals(long etid) throws TEventtypeInfoInitDaoException;

	/** 
	 * Returns all rows from the T_EVENTTYPE_INFO_INIT table that match the criteria 'EVETYPE = :evetype'.
	 */
	public List<TEventtypeInfoInit> findWhereEvetypeEquals(String evetype) throws TEventtypeInfoInitDaoException;

	/** 
	 * Returns all rows from the T_EVENTTYPE_INFO_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TEventtypeInfoInit> findWhereDescriptionEquals(String description) throws TEventtypeInfoInitDaoException;

	/** 
	 * Returns all rows from the T_EVENTTYPE_INFO_INIT table that match the criteria 'ETCODE = :etcode'.
	 */
	public List<TEventtypeInfoInit> findWhereEtcodeEquals(String etcode) throws TEventtypeInfoInitDaoException;

	/** 
	 * Returns the rows from the T_EVENTTYPE_INFO_INIT table that matches the specified primary-key value.
	 */
	public TEventtypeInfoInit findByPrimaryKey(TEventtypeInfoInitPk pk) throws TEventtypeInfoInitDaoException;

}
