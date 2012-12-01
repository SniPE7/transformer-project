package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TPolicyDetailsDao;
import com.ibm.ncs.model.dto.TPolicyDetails;
import com.ibm.ncs.model.dto.TPolicyDetailsPk;
import com.ibm.ncs.model.exceptions.TPolicyDetailsDaoException;
import java.util.List;

public interface TPolicyDetailsDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TPolicyDetailsPk
	 */
	public TPolicyDetailsPk insert(TPolicyDetails dto);

	/** 
	 * Updates a single row in the T_POLICY_DETAILS table.
	 */
	public void update(TPolicyDetailsPk pk, TPolicyDetails dto) throws TPolicyDetailsDaoException;

	/** 
	 * Deletes a single row in the T_POLICY_DETAILS table.
	 */
	public void delete(TPolicyDetailsPk pk) throws TPolicyDetailsDaoException;
	public void delete(long mpid) throws TPolicyDetailsDaoException;
	
	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName();
	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'MPID = :mpid AND MODID = :modid AND EVEID = :eveid'.
	 */
	public TPolicyDetails findByPrimaryKey(long mpid, long modid, long eveid) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria ''.
	 */
	public List<TPolicyDetails> findAll() throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'MPID = :mpid'.
	 */
	public List<TPolicyDetails> findWhereMpidEquals(long mpid) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'MODID = :modid'.
	 */
	public List<TPolicyDetails> findWhereModidEquals(long modid) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'EVEID = :eveid'.
	 */
	public List<TPolicyDetails> findWhereEveidEquals(long eveid) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'POLL = :poll'.
	 */
	public List<TPolicyDetails> findWherePollEquals(long poll) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'VALUE_1 = :value1'.
	 */
	public List<TPolicyDetails> findWhereValue1Equals(String value1) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	public List<TPolicyDetails> findWhereSeverity1Equals(long severity1) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'FILTER_A = :filterA'.
	 */
	public List<TPolicyDetails> findWhereFilterAEquals(String filterA) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'VALUE_2 = :value2'.
	 */
	public List<TPolicyDetails> findWhereValue2Equals(String value2) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	public List<TPolicyDetails> findWhereSeverity2Equals(long severity2) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'FILTER_B = :filterB'.
	 */
	public List<TPolicyDetails> findWhereFilterBEquals(String filterB) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'SEVERITY_A = :severityA'.
	 */
	public List<TPolicyDetails> findWhereSeverityAEquals(long severityA) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'SEVERITY_B = :severityB'.
	 */
	public List<TPolicyDetails> findWhereSeverityBEquals(long severityB) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'OIDGROUP = :oidgroup'.
	 */
	public List<TPolicyDetails> findWhereOidgroupEquals(String oidgroup) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'OGFLAG = :ogflag'.
	 */
	public List<TPolicyDetails> findWhereOgflagEquals(String ogflag) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'VALUE_1_LOW = :value1Low'.
	 */
	public List<TPolicyDetails> findWhereValue1LowEquals(String value1Low) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'VALUE_2_LOW = :value2Low'.
	 */
	public List<TPolicyDetails> findWhereValue2LowEquals(String value2Low) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'V1L_SEVERITY_1 = :v1lSeverity1'.
	 */
	public List<TPolicyDetails> findWhereV1lSeverity1Equals(long v1lSeverity1) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'V1L_SEVERITY_A = :v1lSeverityA'.
	 */
	public List<TPolicyDetails> findWhereV1lSeverityAEquals(long v1lSeverityA) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'V2L_SEVERITY_2 = :v2lSeverity2'.
	 */
	public List<TPolicyDetails> findWhereV2lSeverity2Equals(long v2lSeverity2) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'V2L_SEVERITY_B = :v2lSeverityB'.
	 */
	public List<TPolicyDetails> findWhereV2lSeverityBEquals(long v2lSeverityB) throws TPolicyDetailsDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DETAILS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	public List<TPolicyDetails> findWhereComparetypeEquals(String comparetype) throws TPolicyDetailsDaoException;

	/** 
	 * Returns the rows from the T_POLICY_DETAILS table that matches the specified primary-key value.
	 */
	public TPolicyDetails findByPrimaryKey(TPolicyDetailsPk pk) throws TPolicyDetailsDaoException;

}
