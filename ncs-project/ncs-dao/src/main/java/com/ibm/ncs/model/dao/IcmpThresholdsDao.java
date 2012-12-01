package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.IcmpThresholdsDao;
import com.ibm.ncs.model.dto.IcmpThresholds;
import com.ibm.ncs.model.dto.IcmpThresholdsPk;
import com.ibm.ncs.model.exceptions.IcmpThresholdsDaoException;
import java.util.List;

public interface IcmpThresholdsDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return IcmpThresholdsPk
	 */
	public IcmpThresholdsPk insert(IcmpThresholds dto);

	/** 
	 * Updates a single row in the ICMP_THRESHOLDS table.
	 */
	public void update(IcmpThresholdsPk pk, IcmpThresholds dto) throws IcmpThresholdsDaoException;

	/** 
	 * Deletes a single row in the ICMP_THRESHOLDS table.
	 */
	public void delete(IcmpThresholdsPk pk) throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria 'IPADDRESS = :ipaddress'.
	 */
	public IcmpThresholds findByPrimaryKey(String ipaddress) throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria ''.
	 */
	public List<IcmpThresholds> findAll() throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria 'IPADDRESS = :ipaddress'.
	 */
	public List<IcmpThresholds> findWhereIpaddressEquals(String ipaddress) throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria 'BTIME = :btime'.
	 */
	public List<IcmpThresholds> findWhereBtimeEquals(String btime) throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria 'ETIME = :etime'.
	 */
	public List<IcmpThresholds> findWhereEtimeEquals(String etime) throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria 'THRESHOLD = :threshold'.
	 */
	public List<IcmpThresholds> findWhereThresholdEquals(String threshold) throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria 'COMPARETYPE = :comparetype'.
	 */
	public List<IcmpThresholds> findWhereComparetypeEquals(String comparetype) throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	public List<IcmpThresholds> findWhereSeverity1Equals(String severity1) throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria 'SEVERITY2 = :severity2'.
	 */
	public List<IcmpThresholds> findWhereSeverity2Equals(String severity2) throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria 'FILTERFLAG1 = :filterflag1'.
	 */
	public List<IcmpThresholds> findWhereFilterflag1Equals(long filterflag1) throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria 'FILTERFLAG2 = :filterflag2'.
	 */
	public List<IcmpThresholds> findWhereFilterflag2Equals(long filterflag2) throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria 'VARLIST = :varlist'.
	 */
	public List<IcmpThresholds> findWhereVarlistEquals(String varlist) throws IcmpThresholdsDaoException;

	/** 
	 * Returns all rows from the ICMP_THRESHOLDS table that match the criteria 'SUMMARYCN = :summarycn'.
	 */
	public List<IcmpThresholds> findWhereSummarycnEquals(String summarycn) throws IcmpThresholdsDaoException;

	/** 
	 * Returns the rows from the ICMP_THRESHOLDS table that matches the specified primary-key value.
	 */
	public IcmpThresholds findByPrimaryKey(IcmpThresholdsPk pk) throws IcmpThresholdsDaoException;

	/**
	 * Method 'update'
	 */

	public void delete(IcmpThresholds dto);
	
	/**
	 * Method 'insert'
	 */

	public void update(IcmpThresholds dto);

	public int insertEffect();
	public int deleteAll();
}
