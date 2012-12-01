package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VIcmpPortThresholdsDao;
import com.ibm.ncs.model.dto.VIcmpPortThresholds;
import com.ibm.ncs.model.exceptions.VIcmpPortThresholdsDaoException;
import java.util.List;

public interface VIcmpPortThresholdsDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VIcmpPortThresholds dto);

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria ''.
	 */
	public List<VIcmpPortThresholds> findAll() throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'DEVIP = :devip'.
	 */
	public List<VIcmpPortThresholds> findWhereDevipEquals(String devip) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'IFIP = :ifip'.
	 */
	public List<VIcmpPortThresholds> findWhereIfipEquals(String ifip) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'EXPIP = :expip'.
	 */
	public List<VIcmpPortThresholds> findWhereExpipEquals(String expip) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'BTIME = :btime'.
	 */
	public List<VIcmpPortThresholds> findWhereBtimeEquals(String btime) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'ETIME = :etime'.
	 */
	public List<VIcmpPortThresholds> findWhereEtimeEquals(String etime) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'VALUE0 = :value0'.
	 */
	public List<VIcmpPortThresholds> findWhereValue0Equals(String value0) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'VALUE_1 = :value1'.
	 */
	public List<VIcmpPortThresholds> findWhereValue1Equals(String value1) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'VALUE_2 = :value2'.
	 */
	public List<VIcmpPortThresholds> findWhereValue2Equals(String value2) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'VAR0 = :var0'.
	 */
	public List<VIcmpPortThresholds> findWhereVar0Equals(String var0) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'VALUE_1_LOW = :value1Low'.
	 */
	public List<VIcmpPortThresholds> findWhereValue1LowEquals(String value1Low) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'VALUE_2_LOW = :value2Low'.
	 */
	public List<VIcmpPortThresholds> findWhereValue2LowEquals(String value2Low) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	public List<VIcmpPortThresholds> findWhereComparetypeEquals(String comparetype) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'V1L_SEVERITY_1 = :v1lSeverity1'.
	 */
	public List<VIcmpPortThresholds> findWhereV1lSeverity1Equals(long v1lSeverity1) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	public List<VIcmpPortThresholds> findWhereSeverity1Equals(long severity1) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	public List<VIcmpPortThresholds> findWhereSeverity2Equals(long severity2) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'V1L_SEVERITY_A = :v1lSeverityA'.
	 */
	public List<VIcmpPortThresholds> findWhereV1lSeverityAEquals(long v1lSeverityA) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'SEVERITY_A = :severityA'.
	 */
	public List<VIcmpPortThresholds> findWhereSeverityAEquals(long severityA) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'SEVERITY_B = :severityB'.
	 */
	public List<VIcmpPortThresholds> findWhereSeverityBEquals(long severityB) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'FILTER_A = :filterA'.
	 */
	public List<VIcmpPortThresholds> findWhereFilterAEquals(String filterA) throws VIcmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PORT_THRESHOLDS table that match the criteria 'FILTER_B = :filterB'.
	 */
	public List<VIcmpPortThresholds> findWhereFilterBEquals(String filterB) throws VIcmpPortThresholdsDaoException;

}
