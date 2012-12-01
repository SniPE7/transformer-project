package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VSnmpPortThresholdsDao;
import com.ibm.ncs.model.dto.VSnmpPortThresholds;
import com.ibm.ncs.model.exceptions.VSnmpPortThresholdsDaoException;
import java.util.List;

public interface VSnmpPortThresholdsDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VSnmpPortThresholds dto);

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria ''.
	 */
	public List<VSnmpPortThresholds> findAll() throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'DEVIP = :devip'.
	 */
	public List<VSnmpPortThresholds> findWhereDevipEquals(String devip) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'IFDESCR = :ifdescr'.
	 */
	public List<VSnmpPortThresholds> findWhereIfdescrEquals(String ifdescr) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'MAJOR = :major'.
	 */
	public List<VSnmpPortThresholds> findWhereMajorEquals(String major) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'VALUE_1 = :value1'.
	 */
	public List<VSnmpPortThresholds> findWhereValue1Equals(String value1) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'VALUE_1_LOW = :value1Low'.
	 */
	public List<VSnmpPortThresholds> findWhereValue1LowEquals(String value1Low) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'VALUE_2 = :value2'.
	 */
	public List<VSnmpPortThresholds> findWhereValue2Equals(String value2) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'VALUE_2_LOW = :value2Low'.
	 */
	public List<VSnmpPortThresholds> findWhereValue2LowEquals(String value2Low) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	public List<VSnmpPortThresholds> findWhereComparetypeEquals(String comparetype) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	public List<VSnmpPortThresholds> findWhereSeverity1Equals(long severity1) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'V1L_SEVERITY_1 = :v1lSeverity1'.
	 */
	public List<VSnmpPortThresholds> findWhereV1lSeverity1Equals(long v1lSeverity1) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	public List<VSnmpPortThresholds> findWhereSeverity2Equals(long severity2) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'V2L_SEVERITY_2 = :v2lSeverity2'.
	 */
	public List<VSnmpPortThresholds> findWhereV2lSeverity2Equals(long v2lSeverity2) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'FILTER_A = :filterA'.
	 */
	public List<VSnmpPortThresholds> findWhereFilterAEquals(String filterA) throws VSnmpPortThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_PORT_THRESHOLDS table that match the criteria 'FILTER_B = :filterB'.
	 */
	public List<VSnmpPortThresholds> findWhereFilterBEquals(String filterB) throws VSnmpPortThresholdsDaoException;

}
