package com.ibm.ncs.model.dao;

import java.util.List;

import com.ibm.ncs.model.dto.TTakeEffectHistory;
import com.ibm.ncs.model.dto.TTakeEffectHistoryPk;
import com.ibm.ncs.model.exceptions.TTakeEffectHistoryDaoException;

public interface TTakeEffectHistoryDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TServerInfoPk
	 */
	public TTakeEffectHistoryPk insert(TTakeEffectHistory dto) throws TTakeEffectHistoryDaoException;

	/** 
	 * Updates a single row in the T_SERVER_NODE table.
	 */
	public void update(TTakeEffectHistoryPk pk, TTakeEffectHistory dto) throws TTakeEffectHistoryDaoException;

	/** 
	 * Deletes a single row in the T_SERVER_NODE table.
	 */
	public void delete(TTakeEffectHistoryPk pk) throws TTakeEffectHistoryDaoException;

	/** 
	 * Returns all rows from the T_SERVER_NODE table that match the criteria 'SERVER_ID = :serverId'.
	 */
	public TTakeEffectHistory findByTeid(long teid) throws TTakeEffectHistoryDaoException;

	/** 
	 * Returns all rows from the T_SERVER_NODE table that match the criteria ''.
	 */
	public List<TTakeEffectHistory> findAll() throws TTakeEffectHistoryDaoException;

}
