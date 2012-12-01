package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TPpPortDao;
import com.ibm.ncs.model.dto.TPpPort;
import com.ibm.ncs.model.dto.TPpPortPk;
import com.ibm.ncs.model.exceptions.TPpPortDaoException;
import java.util.List;

public interface TPpPortDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TPpPortPk
	 */
	public TPpPortPk insert(TPpPort dto);

	/** 
	 * Updates a single row in the T_PP_PORT table.
	 */
	public void update(TPpPortPk pk, TPpPort dto) throws TPpPortDaoException;

	/** 
	 * Deletes a single row in the T_PP_PORT table.
	 */
	public void delete(TPpPortPk pk) throws TPpPortDaoException;

	/** 
	 * Returns all rows from the T_PP_PORT table that match the criteria 'DEVIP = :devip AND IFDESCR = :ifdescr'.
	 */
	public TPpPort findByPrimaryKey(String devip, String ifdescr) throws TPpPortDaoException;

	/** 
	 * Returns all rows from the T_PP_PORT table that match the criteria ''.
	 */
	public List<TPpPort> findAll() throws TPpPortDaoException;

	/** 
	 * Returns all rows from the T_PP_PORT table that match the criteria 'DEVIP = :devip'.
	 */
	public List<TPpPort> findWhereDevipEquals(String devip) throws TPpPortDaoException;

	/** 
	 * Returns all rows from the T_PP_PORT table that match the criteria 'IFDESCR = :ifdescr'.
	 */
	public List<TPpPort> findWhereIfdescrEquals(String ifdescr) throws TPpPortDaoException;

	/** 
	 * Returns all rows from the T_PP_PORT table that match the criteria 'BTIME = :btime'.
	 */
	public List<TPpPort> findWhereBtimeEquals(String btime) throws TPpPortDaoException;

	/** 
	 * Returns all rows from the T_PP_PORT table that match the criteria 'ETIME = :etime'.
	 */
	public List<TPpPort> findWhereEtimeEquals(String etime) throws TPpPortDaoException;

	/** 
	 * Returns the rows from the T_PP_PORT table that matches the specified primary-key value.
	 */
	public TPpPort findByPrimaryKey(TPpPortPk pk) throws TPpPortDaoException;
	
	/** 
	 * Deletes all rows in the T_PP_DEV table.
	 */
	public int deleteAll() ;	

	/**
	 * Method 'insertEffect'
	 * 
	 * @return rows affected
	 */
	public int insertEffect() ;

}
