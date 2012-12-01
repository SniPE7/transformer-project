package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VOidGroupDao;
import com.ibm.ncs.model.dto.VOidGroup;
import com.ibm.ncs.model.exceptions.VOidGroupDaoException;
import java.util.List;

public interface VOidGroupDao
{
	/** 
	 * get table name;
	 */
	public String getTableName();
	
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VOidGroup dto);

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria ''.
	 */
	public List<VOidGroup> findAll() throws VOidGroupDaoException;

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'T_OIDGROUP_INFO_INIT_OPID = :tOidgroupInfoInitOpid'.
	 */
	public List<VOidGroup> findWhereTOidgroupInfoInitOpidEquals(long tOidgroupInfoInitOpid) throws VOidGroupDaoException;

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'OIDGROUPNAME = :oidgroupname'.
	 */
	public List<VOidGroup> findWhereOidgroupnameEquals(String oidgroupname) throws VOidGroupDaoException;

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'OTYPE = :otype'.
	 */
	public List<VOidGroup> findWhereOtypeEquals(long otype) throws VOidGroupDaoException;

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<VOidGroup> findWhereDescriptionEquals(String description) throws VOidGroupDaoException;

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'T_OIDGROUP_DETAILS_INIT_OPID = :tOidgroupDetailsInitOpid'.
	 */
	public List<VOidGroup> findWhereTOidgroupDetailsInitOpidEquals(long tOidgroupDetailsInitOpid) throws VOidGroupDaoException;

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'OIDVALUE = :oidvalue'.
	 */
	public List<VOidGroup> findWhereOidvalueEquals(String oidvalue) throws VOidGroupDaoException;

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'OIDNAME = :oidname'.
	 */
	public List<VOidGroup> findWhereOidnameEquals(String oidname) throws VOidGroupDaoException;

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'OIDUNIT = :oidunit'.
	 */
	public List<VOidGroup> findWhereOidunitEquals(String oidunit) throws VOidGroupDaoException;

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'FLAG = :flag'.
	 */
	public List<VOidGroup> findWhereFlagEquals(String flag) throws VOidGroupDaoException;

	/** 
	 * Returns all rows from the V_OID_GROUP table that match the criteria 'OIDINDEX = :oidindex'.
	 */
	public List<VOidGroup> findWhereOidindexEquals(long oidindex) throws VOidGroupDaoException;

}
