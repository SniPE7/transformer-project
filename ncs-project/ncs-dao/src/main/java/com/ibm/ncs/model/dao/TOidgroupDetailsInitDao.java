package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dto.TOidgroupDetailsInit;
import com.ibm.ncs.model.dto.TOidgroupDetailsInitPk;
import com.ibm.ncs.model.exceptions.TOidgroupDetailsInitDaoException;
import java.util.List;

public interface TOidgroupDetailsInitDao
{
	public String getTableName();
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TOidgroupDetailsInitPk
	 */
	public TOidgroupDetailsInitPk insert(TOidgroupDetailsInit dto);

	/** 
	 * Updates a single row in the T_OIDGROUP_DETAILS_INIT table.
	 */
	public void update(TOidgroupDetailsInitPk pk, TOidgroupDetailsInit dto) throws TOidgroupDetailsInitDaoException;

	/** 
	 * Deletes a single row in the T_OIDGROUP_DETAILS_INIT table.
	 */
	public void delete(TOidgroupDetailsInitPk pk) throws TOidgroupDetailsInitDaoException;
	
	public void delete(long opid) throws TOidgroupDetailsInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'OPID = :opid AND OIDNAME = :oidname'.
	 */
	public TOidgroupDetailsInit findByPrimaryKey(long opid, String oidname) throws TOidgroupDetailsInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria ''.
	 */
	public List<TOidgroupDetailsInit> findAll() throws TOidgroupDetailsInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'OPID = :opid'.
	 */
	public List<TOidgroupDetailsInit> findWhereOpidEquals(long opid) throws TOidgroupDetailsInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'OIDVALUE = :oidvalue'.
	 */
	public List<TOidgroupDetailsInit> findWhereOidvalueEquals(String oidvalue) throws TOidgroupDetailsInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'OIDNAME = :oidname'.
	 */
	public List<TOidgroupDetailsInit> findWhereOidnameEquals(String oidname) throws TOidgroupDetailsInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'OIDUNIT = :oidunit'.
	 */
	public List<TOidgroupDetailsInit> findWhereOidunitEquals(String oidunit) throws TOidgroupDetailsInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'FLAG = :flag'.
	 */
	public List<TOidgroupDetailsInit> findWhereFlagEquals(String flag) throws TOidgroupDetailsInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_DETAILS_INIT table that match the criteria 'OIDINDEX = :oidindex'.
	 */
	public List<TOidgroupDetailsInit> findWhereOidindexEquals(long oidindex) throws TOidgroupDetailsInitDaoException;

	/** 
	 * Returns the rows from the T_OIDGROUP_DETAILS_INIT table that matches the specified primary-key value.
	 */
	public TOidgroupDetailsInit findByPrimaryKey(TOidgroupDetailsInitPk pk) throws TOidgroupDetailsInitDaoException;

}
