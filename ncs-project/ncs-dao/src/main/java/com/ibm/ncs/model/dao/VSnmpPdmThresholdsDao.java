package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VSnmpPdmThresholdsDao;
import com.ibm.ncs.model.dto.VSnmpPdmThresholds;
import com.ibm.ncs.model.exceptions.VSnmpPdmThresholdsDaoException;
import java.util.List;

public interface VSnmpPdmThresholdsDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VSnmpPdmThresholds dto);

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria ''.
	 */
	public List<VSnmpPdmThresholds> findAll() throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'DEVIP = :devip'.
	 */
	public List<VSnmpPdmThresholds> findWhereDevipEquals(String devip) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'OIDNAME = :oidname'.
	 */
	public List<VSnmpPdmThresholds> findWhereOidnameEquals(String oidname) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'MAJOR = :major'.
	 */
	public List<VSnmpPdmThresholds> findWhereMajorEquals(String major) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'VALUE_1 = :value1'.
	 */
	public List<VSnmpPdmThresholds> findWhereValue1Equals(String value1) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'VALUE_1_LOW = :value1Low'.
	 */
	public List<VSnmpPdmThresholds> findWhereValue1LowEquals(String value1Low) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'VALUE_2 = :value2'.
	 */
	public List<VSnmpPdmThresholds> findWhereValue2Equals(String value2) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'VALUE_2_LOW = :value2Low'.
	 */
	public List<VSnmpPdmThresholds> findWhereValue2LowEquals(String value2Low) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	public List<VSnmpPdmThresholds> findWhereComparetypeEquals(String comparetype) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	public List<VSnmpPdmThresholds> findWhereSeverity1Equals(long severity1) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'V1L_SEVERITY_1 = :v1lSeverity1'.
	 */
	public List<VSnmpPdmThresholds> findWhereV1lSeverity1Equals(long v1lSeverity1) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	public List<VSnmpPdmThresholds> findWhereSeverity2Equals(long severity2) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'V2L_SEVERITY_2 = :v2lSeverity2'.
	 */
	public List<VSnmpPdmThresholds> findWhereV2lSeverity2Equals(long v2lSeverity2) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'FILTER_A = :filterA'.
	 */
	public List<VSnmpPdmThresholds> findWhereFilterAEquals(String filterA) throws VSnmpPdmThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PDM_THRESHOLDS table that match the criteria 'FILTER_B = :filterB'.
	 */
	public List<VSnmpPdmThresholds> findWhereFilterBEquals(String filterB) throws VSnmpPdmThresholdsDaoException;

}
