package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TPpDevDao;
import com.ibm.ncs.model.dto.TPpDev;
import com.ibm.ncs.model.dto.TPpDevPk;
import com.ibm.ncs.model.exceptions.TPpDevDaoException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface TPpDevDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TPpDevPk
	 */
	public TPpDevPk insert(TPpDev dto);

	/** 
	 * Updates a single row in the T_PP_DEV table.
	 */
	public void update(TPpDevPk pk, TPpDev dto) throws TPpDevDaoException;

	/** 
	 * Deletes a single row in the T_PP_DEV table.
	 */
	public void delete(TPpDevPk pk) throws TPpDevDaoException;

	/** 
	 * Returns all rows from the T_PP_DEV table that match the criteria 'DEVIP = :devip'.
	 */
	public TPpDev findByPrimaryKey(String devip) throws TPpDevDaoException;

	/** 
	 * Returns all rows from the T_PP_DEV table that match the criteria ''.
	 */
	public List<TPpDev> findAll() throws TPpDevDaoException;

	/** 
	 * Returns all rows from the T_PP_DEV table that match the criteria 'DEVIP = :devip'.
	 */
	public List<TPpDev> findWhereDevipEquals(String devip) throws TPpDevDaoException;

	/** 
	 * Returns all rows from the T_PP_DEV table that match the criteria 'BTIME = :btime'.
	 */
	public List<TPpDev> findWhereBtimeEquals(String btime) throws TPpDevDaoException;

	/** 
	 * Returns all rows from the T_PP_DEV table that match the criteria 'ETIME = :etime'.
	 */
	public List<TPpDev> findWhereEtimeEquals(String etime) throws TPpDevDaoException;

	/** 
	 * Returns the rows from the T_PP_DEV table that matches the specified primary-key value.
	 */
	public TPpDev findByPrimaryKey(TPpDevPk pk) throws TPpDevDaoException;
	
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
