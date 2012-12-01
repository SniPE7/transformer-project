package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.SnmpThresholdsDao;
import com.ibm.ncs.model.dto.SnmpThresholds;
import com.ibm.ncs.model.dto.SnmpThresholdsPk;
import com.ibm.ncs.model.exceptions.SnmpThresholdsDaoException;
import java.util.List;

public interface SnmpThresholdsDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return SnmpThresholdsPk
	 */
	public SnmpThresholdsPk insert(SnmpThresholds dto);

	/** 
	 * Updates a single row in the SNMP_THRESHOLDS table.
	 */
	public void update(SnmpThresholdsPk pk, SnmpThresholds dto) throws SnmpThresholdsDaoException;

	/** 
	 * Deletes a single row in the SNMP_THRESHOLDS table.
	 */
	public void delete(SnmpThresholdsPk pk) throws SnmpThresholdsDaoException;

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'PERFORMANCE = :performance'.
	 */
	public SnmpThresholds findByPrimaryKey(String performance) throws SnmpThresholdsDaoException;

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria ''.
	 */
	public List<SnmpThresholds> findAll() throws SnmpThresholdsDaoException;

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'PERFORMANCE = :performance'.
	 */
	public List<SnmpThresholds> findWherePerformanceEquals(String performance) throws SnmpThresholdsDaoException;

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'BTIME = :btime'.
	 */
	public List<SnmpThresholds> findWhereBtimeEquals(String btime) throws SnmpThresholdsDaoException;

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'ETIME = :etime'.
	 */
	public List<SnmpThresholds> findWhereEtimeEquals(String etime) throws SnmpThresholdsDaoException;

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'THRESHOLD = :threshold'.
	 */
	public List<SnmpThresholds> findWhereThresholdEquals(String threshold) throws SnmpThresholdsDaoException;

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	public List<SnmpThresholds> findWhereComparetypeEquals(String comparetype) throws SnmpThresholdsDaoException;

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	public List<SnmpThresholds> findWhereSeverity1Equals(String severity1) throws SnmpThresholdsDaoException;

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'SEVERITY2 = :severity2'.
	 */
	public List<SnmpThresholds> findWhereSeverity2Equals(String severity2) throws SnmpThresholdsDaoException;

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'FILTERFLAG1 = :filterflag1'.
	 */
	public List<SnmpThresholds> findWhereFilterflag1Equals(long filterflag1) throws SnmpThresholdsDaoException;

	/** 
	 * Returns all rows from the SNMP_THRESHOLDS table that match the criteria 'FILTERFLAG2 = :filterflag2'.
	 */
	public List<SnmpThresholds> findWhereFilterflag2Equals(long filterflag2) throws SnmpThresholdsDaoException;

	/** 
	 * Returns the rows from the SNMP_THRESHOLDS table that matches the specified primary-key value.
	 */
	public SnmpThresholds findByPrimaryKey(SnmpThresholdsPk pk) throws SnmpThresholdsDaoException;
	/**
	 * Method 'update'
	 */
	
	public void delete(SnmpThresholds dto);
	
	/**
	 * Method 'insert'
	 */
	
	public void update(SnmpThresholds dto);
	public int insertEffect();
	public int deleteAll();
}
