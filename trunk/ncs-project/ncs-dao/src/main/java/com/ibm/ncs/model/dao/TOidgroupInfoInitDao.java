package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.TOidgroupInfoInit;
import com.ibm.ncs.model.dto.TOidgroupInfoInitPk;
import com.ibm.ncs.model.exceptions.TOidgroupInfoInitDaoException;
import java.util.List;

public interface TOidgroupInfoInitDao
{
	public String getTableName();
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TOidgroupInfoInitPk
	 */
	public TOidgroupInfoInitPk insert(TOidgroupInfoInit dto);

	/** 
	 * Updates a single row in the T_OIDGROUP_INFO_INIT table.
	 */
	public void update(TOidgroupInfoInitPk pk, TOidgroupInfoInit dto) throws TOidgroupInfoInitDaoException;

	/** 
	 * Deletes a single row in the T_OIDGROUP_INFO_INIT table.
	 */
	public void delete(TOidgroupInfoInitPk pk) throws TOidgroupInfoInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_INFO_INIT table that match the criteria 'OPID = :opid'.
	 */
	public TOidgroupInfoInit findByPrimaryKey(long opid) throws TOidgroupInfoInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_INFO_INIT table that match the criteria ''.
	 */
	public List<TOidgroupInfoInit> findAll() throws TOidgroupInfoInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_INFO_INIT table that match the criteria 'OPID = :opid'.
	 */
	public List<TOidgroupInfoInit> findWhereOpidEquals(long opid) throws TOidgroupInfoInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_INFO_INIT table that match the criteria 'OIDGROUPNAME = :oidgroupname'.
	 */
	public List<TOidgroupInfoInit> findWhereOidgroupnameEquals(String oidgroupname) throws TOidgroupInfoInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_INFO_INIT table that match the criteria 'OTYPE = :otype'.
	 */
	public List<TOidgroupInfoInit> findWhereOtypeEquals(long otype) throws TOidgroupInfoInitDaoException;

	/** 
	 * Returns all rows from the T_OIDGROUP_INFO_INIT table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TOidgroupInfoInit> findWhereDescriptionEquals(String description) throws TOidgroupInfoInitDaoException;

	/** 
	 * Returns the rows from the T_OIDGROUP_INFO_INIT table that matches the specified primary-key value.
	 */
	public TOidgroupInfoInit findByPrimaryKey(TOidgroupInfoInitPk pk) throws TOidgroupInfoInitDaoException;
	
	public List<TOidgroupInfoInit> listOccupied() throws TOidgroupInfoInitDaoException;
	
	public List<TOidgroupInfoInit> listOccupied(String major) throws TOidgroupInfoInitDaoException;

}
