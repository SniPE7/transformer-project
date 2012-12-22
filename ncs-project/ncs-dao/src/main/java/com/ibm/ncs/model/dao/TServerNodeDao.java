package com.ibm.ncs.model.dao;

import java.util.List;

import com.ibm.ncs.model.dto.TServerNode;
import com.ibm.ncs.model.dto.TServerNodePk;
import com.ibm.ncs.model.exceptions.TServerNodeDaoException;

public interface TServerNodeDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TServerInfoPk
	 */
	public TServerNodePk insert(TServerNode dto) throws TServerNodeDaoException;

	/** 
	 * Updates a single row in the T_SERVER_NODE table.
	 */
	public void update(TServerNodePk pk, TServerNode dto) throws TServerNodeDaoException;

	/** 
	 * Deletes a single row in the T_SERVER_NODE table.
	 */
	public void delete(TServerNodePk pk) throws TServerNodeDaoException;

	/** 
	 * Returns all rows from the T_SERVER_NODE table that match the criteria 'SERVER_ID = :serverId'.
	 */
	public TServerNode findByServerId(long serverId) throws TServerNodeDaoException;

	/** 
	 * Returns all rows from the T_SERVER_NODE table that match the criteria 'SERVER_ID = :serverId'.
	 */
	public TServerNode findByServerCode(String serverCode) throws TServerNodeDaoException;

	/** 
	 * Returns all rows from the T_SERVER_NODE table that match the criteria ''.
	 */
	public List<TServerNode> findAll() throws TServerNodeDaoException;

}
