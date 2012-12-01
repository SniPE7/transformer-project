package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TCfgoidgroupTmpDao;
import com.ibm.ncs.model.dto.TCfgoidgroupTmp;
import com.ibm.ncs.model.exceptions.TCfgoidgroupTmpDaoException;
import java.util.List;

public interface TCfgoidgroupTmpDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(TCfgoidgroupTmp dto);

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_TMP table that match the criteria ''.
	 */
	public List<TCfgoidgroupTmp> findAll() throws TCfgoidgroupTmpDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_TMP table that match the criteria 'S_NO = :sNo'.
	 */
	public List<TCfgoidgroupTmp> findWhereSNoEquals(Long sNo) throws TCfgoidgroupTmpDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_TMP table that match the criteria 'OIDGROUPNAME = :oidgroupname'.
	 */
	public List<TCfgoidgroupTmp> findWhereOidgroupnameEquals(String oidgroupname) throws TCfgoidgroupTmpDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_TMP table that match the criteria 'OIDVALUE = :oidvalue'.
	 */
	public List<TCfgoidgroupTmp> findWhereOidvalueEquals(String oidvalue) throws TCfgoidgroupTmpDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_TMP table that match the criteria 'OIDNAME = :oidname'.
	 */
	public List<TCfgoidgroupTmp> findWhereOidnameEquals(String oidname) throws TCfgoidgroupTmpDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_TMP table that match the criteria 'OIDINDEX = :oidindex'.
	 */
	public List<TCfgoidgroupTmp> findWhereOidindexEquals(Long oidindex) throws TCfgoidgroupTmpDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_TMP table that match the criteria 'OIDUNIT = :oidunit'.
	 */
	public List<TCfgoidgroupTmp> findWhereOidunitEquals(String oidunit) throws TCfgoidgroupTmpDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_TMP table that match the criteria 'NMSIP = :nmsip'.
	 */
	public List<TCfgoidgroupTmp> findWhereNmsipEquals(String nmsip) throws TCfgoidgroupTmpDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_TMP table that match the criteria 'TARFILENAME = :tarfilename'.
	 */
	public List<TCfgoidgroupTmp> findWhereTarfilenameEquals(String tarfilename) throws TCfgoidgroupTmpDaoException;

}
