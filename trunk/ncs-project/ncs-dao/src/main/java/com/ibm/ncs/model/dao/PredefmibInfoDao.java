package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.PredefmibInfoDao;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.PredefmibInfoPk;
import com.ibm.ncs.model.exceptions.PredefmibInfoDaoException;
import java.util.List;

public interface PredefmibInfoDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return PredefmibInfoPk
	 */
	public PredefmibInfoPk insert(PredefmibInfo dto);

	/** 
	 * Updates a single row in the PREDEFMIB_INFO table.
	 */
	public void update(PredefmibInfoPk pk, PredefmibInfo dto) throws PredefmibInfoDaoException;

	/** 
	 * Deletes a single row in the PREDEFMIB_INFO table.
	 */
	public void delete(PredefmibInfoPk pk) throws PredefmibInfoDaoException;
	public void deleteByDevid(long devid) throws PredefmibInfoDaoException;
	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'PDMID = :pdmid'.
	 */
	public PredefmibInfo findByPrimaryKey(long pdmid) throws PredefmibInfoDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria ''.
	 */
	public List<PredefmibInfo> findAll() throws PredefmibInfoDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'ID = :id'.
	 */
	public List<PredefmibInfo> findByDefMibGrp(long mid) throws PredefmibInfoDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'PDMID = :pdmid'.
	 */
	public List<PredefmibInfo> findWherePdmidEquals(long pdmid) throws PredefmibInfoDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'MID = :mid'.
	 */
	public List<PredefmibInfo> findWhereMidEquals(long mid) throws PredefmibInfoDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'DEVID = :devid'.
	 */
	public List<PredefmibInfo> findWhereDevidEquals(long devid) throws PredefmibInfoDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'OIDINDEX = :oidindex'.
	 */
	public List<PredefmibInfo> findWhereOidindexEquals(String oidindex) throws PredefmibInfoDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_INFO table that match the criteria 'OIDNAME = :oidname'.
	 */
	public List<PredefmibInfo> findWhereOidnameEquals(String oidname) throws PredefmibInfoDaoException;

	/** 
	 * Returns the rows from the PREDEFMIB_INFO table that matches the specified primary-key value.
	 */
	public PredefmibInfo findByPrimaryKey(PredefmibInfoPk pk) throws PredefmibInfoDaoException;

	public void deleteByDevidAndNotInMid(long devid, String[] midArray) throws PredefmibInfoDaoException;
	
	public List<PredefmibInfo> findByDevidAndMidList(long devid, String[] midArray) throws PredefmibInfoDaoException;
	
	public PredefmibInfo findByDevidAndOidname(long devid,String oidname) throws PredefmibInfoDaoException;
	
	public void deleteByDevidAndNotInOidname(long devid, String[] oidnameArray) throws PredefmibInfoDaoException;
	
	public void deleteByDevidAndMidAndNotInOidname(long devid, long mid,String[] oidnameArray) throws PredefmibInfoDaoException;
	
	public List findByDevidJoingrp(long devid) throws PredefmibInfoDaoException;
	
	public PredefmibInfo findByDevidAndOidnameAndMid(long devid,long mid,String oidname) throws PredefmibInfoDaoException;
    
	public List<PredefmibInfo> findByDevidAndOidnameAndMid(long devid,long mid,String [] oidnameArray) throws PredefmibInfoDaoException;
	
	public void deleteByDevidAndMid(long devid,long mid) throws PredefmibInfoDaoException;
	
}
