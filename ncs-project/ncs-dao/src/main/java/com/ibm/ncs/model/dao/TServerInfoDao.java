package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TServerInfoDao;
import com.ibm.ncs.model.dto.TServerInfo;
import com.ibm.ncs.model.dto.TServerInfoPk;
import com.ibm.ncs.model.exceptions.TServerInfoDaoException;
import java.util.List;

public interface TServerInfoDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TServerInfoPk
	 */
	public TServerInfoPk insert(TServerInfo dto);

	/** 
	 * Updates a single row in the T_SERVER_INFO table.
	 */
	public void update(TServerInfoPk pk, TServerInfo dto) throws TServerInfoDaoException;

	/** 
	 * Deletes a single row in the T_SERVER_INFO table.
	 */
	public void delete(TServerInfoPk pk) throws TServerInfoDaoException;

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'NMSID = :nmsid'.
	 */
	public TServerInfo findByPrimaryKey(long nmsid) throws TServerInfoDaoException;

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria ''.
	 */
	public List<TServerInfo> findAll() throws TServerInfoDaoException;

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'NMSID = :nmsid'.
	 */
	public List<TServerInfo> findWhereNmsidEquals(long nmsid) throws TServerInfoDaoException;

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'NMSIP = :nmsip'.
	 */
	public List<TServerInfo> findWhereNmsipEquals(String nmsip) throws TServerInfoDaoException;

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'NMSNAME = :nmsname'.
	 */
	public List<TServerInfo> findWhereNmsnameEquals(String nmsname) throws TServerInfoDaoException;

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'USERNAME = :username'.
	 */
	public List<TServerInfo> findWhereUsernameEquals(String username) throws TServerInfoDaoException;

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'PASSWORD = :password'.
	 */
	public List<TServerInfo> findWherePasswordEquals(String password) throws TServerInfoDaoException;

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'OSTYPE = :ostype'.
	 */
	public List<TServerInfo> findWhereOstypeEquals(String ostype) throws TServerInfoDaoException;

	/** 
	 * Returns all rows from the T_SERVER_INFO table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TServerInfo> findWhereDescriptionEquals(String description) throws TServerInfoDaoException;

	/** 
	 * Returns the rows from the T_SERVER_INFO table that matches the specified primary-key value.
	 */
	public TServerInfo findByPrimaryKey(TServerInfoPk pk) throws TServerInfoDaoException;

	public List<TServerInfo> findByModuleName(String mname) throws TServerInfoDaoException;
}
