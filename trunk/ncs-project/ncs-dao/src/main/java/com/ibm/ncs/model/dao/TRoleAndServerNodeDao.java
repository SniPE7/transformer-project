package com.ibm.ncs.model.dao;

import java.util.List;

import com.ibm.ncs.model.dto.TRoleAndServerNode;
import com.ibm.ncs.model.exceptions.TUserDaoException;

public interface TRoleAndServerNodeDao {
	/**
	 * Returns all rows from the T_USER table that match the criteria 'STATUS =
	 * :status'.
	 */
	public List<TRoleAndServerNode> findByUsername(String username) throws TUserDaoException;

}
