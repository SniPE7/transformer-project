package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TPolicyDefaultDao;
import com.ibm.ncs.model.dto.TPolicyDefault;
import com.ibm.ncs.model.dto.TPolicyDefaultPk;
import com.ibm.ncs.model.exceptions.TPolicyDefaultDaoException;
import java.util.Date;
import java.util.List;

public interface TPolicyDefaultDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TPolicyDefaultPk
	 */
	public TPolicyDefaultPk insert(TPolicyDefault dto);

	/** 
	 * Updates a single row in the T_POLICY_DEFAULT table.
	 */
	public void update(TPolicyDefaultPk pk, TPolicyDefault dto) throws TPolicyDefaultDaoException;

	/** 
	 * Deletes a single row in the T_POLICY_DEFAULT table.
	 */
	public void delete(TPolicyDefaultPk pk) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'MODID = :modid AND EVEID = :eveid'.
	 */
	public TPolicyDefault findByPrimaryKey(long modid, long eveid) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria ''.
	 */
	public List<TPolicyDefault> findAll() throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'MODID = :modid'.
	 */
	public List<TPolicyDefault> findWhereModidEquals(long modid) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'EVEID = :eveid'.
	 */
	public List<TPolicyDefault> findWhereEveidEquals(long eveid) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'START_TIME = :startTime'.
	 */
	public List<TPolicyDefault> findWhereStartTimeEquals(Date startTime) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'END_TIME = :endTime'.
	 */
	public List<TPolicyDefault> findWhereEndTimeEquals(Date endTime) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'VALUE_1 = :value1'.
	 */
	public List<TPolicyDefault> findWhereValue1Equals(String value1) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	public List<TPolicyDefault> findWhereSeverity1Equals(long severity1) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'FILTER_A = :filterA'.
	 */
	public List<TPolicyDefault> findWhereFilterAEquals(String filterA) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'VALUE_2 = :value2'.
	 */
	public List<TPolicyDefault> findWhereValue2Equals(String value2) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	public List<TPolicyDefault> findWhereSeverity2Equals(long severity2) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'FILTER_B = :filterB'.
	 */
	public List<TPolicyDefault> findWhereFilterBEquals(String filterB) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'SEVERITY_A = :severityA'.
	 */
	public List<TPolicyDefault> findWhereSeverityAEquals(long severityA) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'SEVERITY_B = :severityB'.
	 */
	public List<TPolicyDefault> findWhereSeverityBEquals(long severityB) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'OIDGROUP = :oidgroup'.
	 */
	public List<TPolicyDefault> findWhereOidgroupEquals(String oidgroup) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'OGFLAG = :ogflag'.
	 */
	public List<TPolicyDefault> findWhereOgflagEquals(String ogflag) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S1 = :s1'.
	 */
	public List<TPolicyDefault> findWhereS1Equals(long s1) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S2 = :s2'.
	 */
	public List<TPolicyDefault> findWhereS2Equals(long s2) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S3 = :s3'.
	 */
	public List<TPolicyDefault> findWhereS3Equals(long s3) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S4 = :s4'.
	 */
	public List<TPolicyDefault> findWhereS4Equals(long s4) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S5 = :s5'.
	 */
	public List<TPolicyDefault> findWhereS5Equals(long s5) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S6 = :s6'.
	 */
	public List<TPolicyDefault> findWhereS6Equals(long s6) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'S7 = :s7'.
	 */
	public List<TPolicyDefault> findWhereS7Equals(long s7) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E1 = :e1'.
	 */
	public List<TPolicyDefault> findWhereE1Equals(long e1) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E2 = :e2'.
	 */
	public List<TPolicyDefault> findWhereE2Equals(long e2) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E3 = :e3'.
	 */
	public List<TPolicyDefault> findWhereE3Equals(long e3) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E4 = :e4'.
	 */
	public List<TPolicyDefault> findWhereE4Equals(long e4) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E5 = :e5'.
	 */
	public List<TPolicyDefault> findWhereE5Equals(long e5) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E6 = :e6'.
	 */
	public List<TPolicyDefault> findWhereE6Equals(long e6) throws TPolicyDefaultDaoException;

	/** 
	 * Returns all rows from the T_POLICY_DEFAULT table that match the criteria 'E7 = :e7'.
	 */
	public List<TPolicyDefault> findWhereE7Equals(long e7) throws TPolicyDefaultDaoException;

	/** 
	 * Returns the rows from the T_POLICY_DEFAULT table that matches the specified primary-key value.
	 */
	public TPolicyDefault findByPrimaryKey(TPolicyDefaultPk pk) throws TPolicyDefaultDaoException;

}
