package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TCfgoidgroupNewDao;
import com.ibm.ncs.model.dto.TCfgoidgroupNew;
import com.ibm.ncs.model.exceptions.TCfgoidgroupNewDaoException;
import java.util.List;

public interface TCfgoidgroupNewDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(TCfgoidgroupNew dto);

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria ''.
	 */
	public List<TCfgoidgroupNew> findAll() throws TCfgoidgroupNewDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'S_NO = :sNo'.
	 */
	public List<TCfgoidgroupNew> findWhereSNoEquals(Long sNo) throws TCfgoidgroupNewDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'OIDGROUPNAME = :oidgroupname'.
	 */
	public List<TCfgoidgroupNew> findWhereOidgroupnameEquals(String oidgroupname) throws TCfgoidgroupNewDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'OIDVALUE = :oidvalue'.
	 */
	public List<TCfgoidgroupNew> findWhereOidvalueEquals(String oidvalue) throws TCfgoidgroupNewDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'OIDNAME = :oidname'.
	 */
	public List<TCfgoidgroupNew> findWhereOidnameEquals(String oidname) throws TCfgoidgroupNewDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'OIDINDEX = :oidindex'.
	 */
	public List<TCfgoidgroupNew> findWhereOidindexEquals(Long oidindex) throws TCfgoidgroupNewDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'OIDUNIT = :oidunit'.
	 */
	public List<TCfgoidgroupNew> findWhereOidunitEquals(String oidunit) throws TCfgoidgroupNewDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'NMSIP = :nmsip'.
	 */
	public List<TCfgoidgroupNew> findWhereNmsipEquals(String nmsip) throws TCfgoidgroupNewDaoException;

	/** 
	 * Returns all rows from the T_CFGOIDGROUP_NEW table that match the criteria 'TARFILENAME = :tarfilename'.
	 */
	public List<TCfgoidgroupNew> findWhereTarfilenameEquals(String tarfilename) throws TCfgoidgroupNewDaoException;

}
