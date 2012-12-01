package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TUserDao;
import com.ibm.ncs.model.dto.TUser;
import com.ibm.ncs.model.dto.TUserPk;
import com.ibm.ncs.model.exceptions.TUserDaoException;
import java.util.List;

public interface TUserDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TUserPk
	 */
	public TUserPk insert(TUser dto);

	/** 
	 * Updates a single row in the T_USER table.
	 */
	public void update(TUserPk pk, TUser dto) throws TUserDaoException;

	/** 
	 * Deletes a single row in the T_USER table.
	 */
	public void delete(TUserPk pk) throws TUserDaoException;

	/** 
	 * Returns all rows from the T_USER table that match the criteria 'USID = :usid'.
	 */
	public TUser findByPrimaryKey(long usid) throws TUserDaoException;

	/** 
	 * Returns all rows from the T_USER table that match the criteria ''.
	 */
	public List<TUser> findAll() throws TUserDaoException;

	/** 
	 * Returns all rows from the T_USER table that match the criteria 'USID = :usid'.
	 */
	public List<TUser> findWhereUsidEquals(long usid) throws TUserDaoException;

	/** 
	 * Returns all rows from the T_USER table that match the criteria 'UNAME = :uname'.
	 */
	public List<TUser> findWhereUnameEquals(String uname) throws TUserDaoException;

	/** 
	 * Returns all rows from the T_USER table that match the criteria 'PASSWORD = :password'.
	 */
	public List<TUser> findWherePasswordEquals(String password) throws TUserDaoException;

	/** 
	 * Returns all rows from the T_USER table that match the criteria 'STATUS = :status'.
	 */
	public List<TUser> findWhereStatusEquals(String status) throws TUserDaoException;

	/** 
	 * Returns all rows from the T_USER table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TUser> findWhereDescriptionEquals(String description) throws TUserDaoException;

	/** 
	 * Returns the rows from the T_USER table that matches the specified primary-key value.
	 */
	public TUser findByPrimaryKey(TUserPk pk) throws TUserDaoException;

}
