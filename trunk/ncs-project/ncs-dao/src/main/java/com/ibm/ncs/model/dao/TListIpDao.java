package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import java.util.List;

public interface TListIpDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(TListIp dto);

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria ''.
	 */
	public List<TListIp> findAll() throws TListIpDaoException;

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'GID = :gid'.
	 */
	public List<TListIp> findWhereGidEquals(long gid) throws TListIpDaoException;

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'CATEGORY = :category'.
	 */
	public List<TListIp> findWhereCategoryEquals(long category) throws TListIpDaoException;

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'IP = :ip'.
	 */
	public List<TListIp> findWhereIpEquals(String ip) throws TListIpDaoException;

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'MASK = :mask'.
	 */
	public List<TListIp> findWhereMaskEquals(String mask) throws TListIpDaoException;

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'IPDECODE_MIN = :ipdecodeMin'.
	 */
	public List<TListIp> findWhereIpdecodeMinEquals(long ipdecodeMin) throws TListIpDaoException;

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'IPDECODE_MAX = :ipdecodeMax'.
	 */
	public List<TListIp> findWhereIpdecodeMaxEquals(long ipdecodeMax) throws TListIpDaoException;

	/** 
	 * Returns all rows from the T_LIST_IP table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TListIp> findWhereDescriptionEquals(String description) throws TListIpDaoException;

	
	public void deleteByGid(long gid) throws TListIpDaoException ;

	
	public void delete(TListIp data) throws TListIpDaoException ;
	
}
