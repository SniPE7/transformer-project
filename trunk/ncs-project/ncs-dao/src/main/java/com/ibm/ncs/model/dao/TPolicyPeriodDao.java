package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.dto.TPolicyPeriodPk;
import com.ibm.ncs.model.exceptions.TPolicyPeriodDaoException;
import java.util.Date;
import java.util.List;

public interface TPolicyPeriodDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TPolicyPeriodPk
	 */
	public TPolicyPeriodPk insert(TPolicyPeriod dto);

	public String getTableName();
	
	/** 
	 * Updates a single row in the T_POLICY_PERIOD table.
	 */
	public void update(TPolicyPeriodPk pk, TPolicyPeriod dto) throws TPolicyPeriodDaoException;

	/** 
	 * Deletes a single row in the T_POLICY_PERIOD table.
	 */
	public void delete(TPolicyPeriodPk pk) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'PPID = :ppid'.
	 */
	public TPolicyPeriod findByPrimaryKey(long ppid) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria ''.
	 */
	public List<TPolicyPeriod> findAll() throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'PPID = :ppid'.
	 */
	public List<TPolicyPeriod> findWherePpidEquals(long ppid) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'PPNAME = :ppname'.
	 */
	public List<TPolicyPeriod> findWherePpnameEquals(String ppname) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'START_TIME = :startTime'.
	 */
	public List<TPolicyPeriod> findWhereStartTimeEquals(Date startTime) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'END_TIME = :endTime'.
	 */
	public List<TPolicyPeriod> findWhereEndTimeEquals(Date endTime) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TPolicyPeriod> findWhereDescriptionEquals(String description) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'WORKDAY = :workday'.
	 */
	public List<TPolicyPeriod> findWhereWorkdayEquals(String workday) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'ENABLED = :enabled'.
	 */
	public List<TPolicyPeriod> findWhereEnabledEquals(String enabled) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'DEFAULTFLAG = :defaultflag'.
	 */
	public List<TPolicyPeriod> findWhereDefaultflagEquals(String defaultflag) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S1 = :s1'.
	 */
	public List<TPolicyPeriod> findWhereS1Equals(long s1) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S2 = :s2'.
	 */
	public List<TPolicyPeriod> findWhereS2Equals(long s2) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S3 = :s3'.
	 */
	public List<TPolicyPeriod> findWhereS3Equals(long s3) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S4 = :s4'.
	 */
	public List<TPolicyPeriod> findWhereS4Equals(long s4) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S5 = :s5'.
	 */
	public List<TPolicyPeriod> findWhereS5Equals(long s5) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S6 = :s6'.
	 */
	public List<TPolicyPeriod> findWhereS6Equals(long s6) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'S7 = :s7'.
	 */
	public List<TPolicyPeriod> findWhereS7Equals(long s7) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E1 = :e1'.
	 */
	public List<TPolicyPeriod> findWhereE1Equals(long e1) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E2 = :e2'.
	 */
	public List<TPolicyPeriod> findWhereE2Equals(long e2) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E3 = :e3'.
	 */
	public List<TPolicyPeriod> findWhereE3Equals(long e3) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E4 = :e4'.
	 */
	public List<TPolicyPeriod> findWhereE4Equals(long e4) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E5 = :e5'.
	 */
	public List<TPolicyPeriod> findWhereE5Equals(long e5) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E6 = :e6'.
	 */
	public List<TPolicyPeriod> findWhereE6Equals(long e6) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'E7 = :e7'.
	 */
	public List<TPolicyPeriod> findWhereE7Equals(long e7) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'BTIME = :btime'.
	 */
	public List<TPolicyPeriod> findWhereBtimeEquals(String btime) throws TPolicyPeriodDaoException;

	/** 
	 * Returns all rows from the T_POLICY_PERIOD table that match the criteria 'ETIME = :etime'.
	 */
	public List<TPolicyPeriod> findWhereEtimeEquals(String etime) throws TPolicyPeriodDaoException;

	/** 
	 * Returns the rows from the T_POLICY_PERIOD table that matches the specified primary-key value.
	 */
	public TPolicyPeriod findByPrimaryKey(TPolicyPeriodPk pk) throws TPolicyPeriodDaoException;

}
