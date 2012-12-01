package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.dto.TPortInfoPk;
import com.ibm.ncs.model.exceptions.TPortInfoDaoException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface TPortInfoDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TPortInfoPk
	 */
	public TPortInfoPk insert(TPortInfo dto);

	/** 
	 * Updates a single row in the T_PORT_INFO table.
	 */
	public void update(TPortInfoPk pk, TPortInfo dto) throws TPortInfoDaoException;

	/** 
	 * Deletes a single row in the T_PORT_INFO table.
	 */
	public void delete(TPortInfoPk pk) throws TPortInfoDaoException;
	
	/** 
	 * Deletes a multi rows in the T_PORT_INFO table by devid.
	 */
	public void delete(long devid) throws TPortInfoDaoException;
	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'PTID = :ptid'.
	 */
	public TPortInfo findByPrimaryKey(long ptid) throws TPortInfoDaoException;

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria ''.
	 */
	public List<TPortInfo> findAll() throws TPortInfoDaoException;

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'PTID = :ptid'.
	 */
	public List<TPortInfo> findWherePtidEquals(long ptid) throws TPortInfoDaoException;

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'DEVID = :devid'.
	 */
	public List<TPortInfo> findWhereDevidEquals(long devid) throws TPortInfoDaoException;

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'IFINDEX = :ifindex'.
	 */
	public List<TPortInfo> findWhereIfindexEquals(long ifindex) throws TPortInfoDaoException;

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'IFIP = :ifip'.
	 */
	public List<TPortInfo> findWhereIfipEquals(String ifip) throws TPortInfoDaoException;

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'IPDECODE_IFIP = :ipdecodeIfip'.
	 */
	public List<TPortInfo> findWhereIpdecodeIfipEquals(long ipdecodeIfip) throws TPortInfoDaoException;

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'IFMAC = :ifmac'.
	 */
	public List<TPortInfo> findWhereIfmacEquals(String ifmac) throws TPortInfoDaoException;

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'IFOPERSTATUS = :ifoperstatus'.
	 */
	public List<TPortInfo> findWhereIfoperstatusEquals(String ifoperstatus) throws TPortInfoDaoException;

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'IFDESCR = :ifdescr'.
	 */
	public List<TPortInfo> findWhereIfdescrEquals(String ifdescr) throws TPortInfoDaoException;

	/** 
	 * Returns all rows from the T_PORT_INFO table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TPortInfo> findWhereDescriptionEquals(String description) throws TPortInfoDaoException;

	/** 
	 * Returns the rows from the T_PORT_INFO table that matches the specified primary-key value.
	 */
	public TPortInfo findByPrimaryKey(TPortInfoPk pk) throws TPortInfoDaoException;

	/** 
	 * Deletes a multi rows in the T_PORT_INFO table by devid and not in ifdescr list.
	 */
	@Transactional
	public void deleteByNotInIfdescrList(long devid, String [] ifdescrArray) throws TPortInfoDaoException;
	
	/** 
	 * Returns all rows from the T_PORT_INFO table that match the devid and in the list 'IFDESCR = :ifdescr'.
	 */
	@Transactional
	public List<TPortInfo> findByDevidAndIfdescrList(long devid, String[] ifdescrArray) throws TPortInfoDaoException;
	
	public TPortInfo findByDevidAndIFdesc(long devid,String ifdescr) throws TPortInfoDaoException;
	
}
