package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VIcmpPdmThresholdsDao;
import com.ibm.ncs.model.dto.VIcmpPdmThresholds;
import com.ibm.ncs.model.exceptions.VIcmpPdmThresholdsDaoException;
import java.util.List;

public interface VIcmpPdmThresholdsDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VIcmpPdmThresholds dto);

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria ''.
	 */
	public List<VIcmpPdmThresholds> findAll() throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'DEVIP = :devip'.
	 */
	public List<VIcmpPdmThresholds> findWhereDevipEquals(String devip) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'OIDNAME = :oidname'.
	 */
	public List<VIcmpPdmThresholds> findWhereOidnameEquals(String oidname) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'BTIME = :btime'.
	 */
	public List<VIcmpPdmThresholds> findWhereBtimeEquals(String btime) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'ETIME = :etime'.
	 */
	public List<VIcmpPdmThresholds> findWhereEtimeEquals(String etime) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'VALUE0 = :value0'.
	 */
	public List<VIcmpPdmThresholds> findWhereValue0Equals(String value0) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'VALUE_1 = :value1'.
	 */
	public List<VIcmpPdmThresholds> findWhereValue1Equals(String value1) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'VALUE_2 = :value2'.
	 */
	public List<VIcmpPdmThresholds> findWhereValue2Equals(String value2) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'VAR0 = :var0'.
	 */
	public List<VIcmpPdmThresholds> findWhereVar0Equals(String var0) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'VALUE_1_LOW = :value1Low'.
	 */
	public List<VIcmpPdmThresholds> findWhereValue1LowEquals(String value1Low) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'VALUE_2_LOW = :value2Low'.
	 */
	public List<VIcmpPdmThresholds> findWhereValue2LowEquals(String value2Low) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	public List<VIcmpPdmThresholds> findWhereComparetypeEquals(String comparetype) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'V1L_SEVERITY_1 = :v1lSeverity1'.
	 */
	public List<VIcmpPdmThresholds> findWhereV1lSeverity1Equals(long v1lSeverity1) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	public List<VIcmpPdmThresholds> findWhereSeverity1Equals(long severity1) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	public List<VIcmpPdmThresholds> findWhereSeverity2Equals(long severity2) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'V1L_SEVERITY_A = :v1lSeverityA'.
	 */
	public List<VIcmpPdmThresholds> findWhereV1lSeverityAEquals(long v1lSeverityA) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'SEVERITY_A = :severityA'.
	 */
	public List<VIcmpPdmThresholds> findWhereSeverityAEquals(long severityA) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'SEVERITY_B = :severityB'.
	 */
	public List<VIcmpPdmThresholds> findWhereSeverityBEquals(long severityB) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'FILTER_A = :filterA'.
	 */
	public List<VIcmpPdmThresholds> findWhereFilterAEquals(String filterA) throws VIcmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_ICMP_PDM_THRESHOLDS table that match the criteria 'FILTER_B = :filterB'.
	 */
	public List<VIcmpPdmThresholds> findWhereFilterBEquals(String filterB) throws VIcmpPdmThresholdsDaoException;

}
