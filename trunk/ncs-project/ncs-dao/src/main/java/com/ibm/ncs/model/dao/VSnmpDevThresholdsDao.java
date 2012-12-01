package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VSnmpDevThresholdsDao;
import com.ibm.ncs.model.dto.VSnmpDevThresholds;
import com.ibm.ncs.model.exceptions.VSnmpDevThresholdsDaoException;
import java.util.List;

public interface VSnmpDevThresholdsDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VSnmpDevThresholds dto);

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria ''.
	 */
	public List<VSnmpDevThresholds> findAll() throws VSnmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'DEVIP = :devip'.
	 */
	public List<VSnmpDevThresholds> findWhereDevipEquals(String devip) throws VSnmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'MAJOR = :major'.
	 */
	public List<VSnmpDevThresholds> findWhereMajorEquals(String major) throws VSnmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'VALUE_1 = :value1'.
	 */
	public List<VSnmpDevThresholds> findWhereValue1Equals(String value1) throws VSnmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'VALUE_2 = :value2'.
	 */
	public List<VSnmpDevThresholds> findWhereValue2Equals(String value2) throws VSnmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	public List<VSnmpDevThresholds> findWhereComparetypeEquals(String comparetype) throws VSnmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'SEVERITY_1 = :severity1'.
	 */
	public List<VSnmpDevThresholds> findWhereSeverity1Equals(long severity1) throws VSnmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'SEVERITY_2 = :severity2'.
	 */
	public List<VSnmpDevThresholds> findWhereSeverity2Equals(long severity2) throws VSnmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'FILTER_A = :filterA'.
	 */
	public List<VSnmpDevThresholds> findWhereFilterAEquals(String filterA) throws VSnmpDevThresholdsDaoException;

	/** 
	 * Returns all rows from the V_SNMP_DEV_THRESHOLDS table that match the criteria 'FILTER_B = :filterB'.
	 */
	public List<VSnmpDevThresholds> findWhereFilterBEquals(String filterB) throws VSnmpDevThresholdsDaoException;

}
