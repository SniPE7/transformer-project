package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VIcmpDevThresholdsDao;
import com.ibm.ncs.model.dto.VIcmpDevThresholds;
import com.ibm.ncs.model.exceptions.VIcmpDevThresholdsDaoException;
import java.util.List;

public interface VIcmpDevThresholdsDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VIcmpDevThresholds dto);

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria ''.
	 */
	public List<VIcmpDevThresholds> findAll() throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'DEVIP = :devip'.
	 */
	public List<VIcmpDevThresholds> findWhereDevipEquals(String devip) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'BTIME = :btime'.
	 */
	public List<VIcmpDevThresholds> findWhereBtimeEquals(String btime) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'ETIME = :etime'.
	 */
	public List<VIcmpDevThresholds> findWhereEtimeEquals(String etime) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'VALUE0 = :value0'.
	 */
	public List<VIcmpDevThresholds> findWhereValue0Equals(String value0) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'VALUE_1 = :value1'.
	 */
	public List<VIcmpDevThresholds> findWhereValue1Equals(String value1) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'VALUE_2 = :value2'.
	 */
	public List<VIcmpDevThresholds> findWhereValue2Equals(String value2) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'VAR0 = :var0'.
	 */
	public List<VIcmpDevThresholds> findWhereVar0Equals(String var0) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'VALUE_1_LOW = :value1Low'.
	 */
	public List<VIcmpDevThresholds> findWhereValue1LowEquals(String value1Low) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'VALUE_2_LOW = :value2Low'.
	 */
	public List<VIcmpDevThresholds> findWhereValue2LowEquals(String value2Low) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	public List<VIcmpDevThresholds> findWhereComparetypeEquals(String comparetype) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'V1L_SEVERITY_1 = :v1lSeverity1'.
	 */
	public List<VIcmpDevThresholds> findWhereV1lSeverity1Equals(long v1lSeverity1) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	public List<VIcmpDevThresholds> findWhereSeverity1Equals(long severity1) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	public List<VIcmpDevThresholds> findWhereSeverity2Equals(long severity2) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'V1L_SEVERITY_A = :v1lSeverityA'.
	 */
	public List<VIcmpDevThresholds> findWhereV1lSeverityAEquals(long v1lSeverityA) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'SEVERITY_A = :severityA'.
	 */
	public List<VIcmpDevThresholds> findWhereSeverityAEquals(long severityA) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'SEVERITY_B = :severityB'.
	 */
	public List<VIcmpDevThresholds> findWhereSeverityBEquals(long severityB) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'FILTER_A = :filterA'.
	 */
	public List<VIcmpDevThresholds> findWhereFilterAEquals(String filterA) throws VIcmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_DEV_THRESHOLDS table that match the criteria 'FILTER_B = :filterB'.
	 */
	public List<VIcmpDevThresholds> findWhereFilterBEquals(String filterB) throws VIcmpDevThresholdsDaoException;

}
