package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.PolicySyslogDao;
import com.ibm.ncs.model.dto.PolicySyslog;
import com.ibm.ncs.model.dto.PolicySyslogPk;
import com.ibm.ncs.model.exceptions.PolicySyslogDaoException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface PolicySyslogDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return PolicySyslogPk
	 */
	public PolicySyslogPk insert(PolicySyslog dto);

	/** 
	 * Updates a single row in the POLICY_SYSLOG table.
	 */
	public void update(PolicySyslogPk pk, PolicySyslog dto) throws PolicySyslogDaoException;

	/** 
	 * Deletes a single row in the POLICY_SYSLOG table.
	 */
	public void delete(PolicySyslogPk pk) throws PolicySyslogDaoException;

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'SPID = :spid'.
	 */
	public PolicySyslog findByPrimaryKey(long spid) throws PolicySyslogDaoException;

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria ''.
	 */
	public List<PolicySyslog> findAll() throws PolicySyslogDaoException;

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'SPID = :spid'.
	 */
	public List<PolicySyslog> findWhereSpidEquals(long spid) throws PolicySyslogDaoException;

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'MPID = :mpid'.
	 */
	public List<PolicySyslog> findWhereMpidEquals(long mpid) throws PolicySyslogDaoException;

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'MARK = :mark'.
	 */
	public List<PolicySyslog> findWhereMarkEquals(String mark) throws PolicySyslogDaoException;

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'MANUFACTURE = :manufacture'.
	 */
	public List<PolicySyslog> findWhereManufactureEquals(String manufacture) throws PolicySyslogDaoException;

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'EVENTTYPE = :eventtype'.
	 */
	public List<PolicySyslog> findWhereEventtypeEquals(long eventtype) throws PolicySyslogDaoException;

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'SEVERITY1 = :severity1'.
	 */
	public List<PolicySyslog> findWhereSeverity1Equals(long severity1) throws PolicySyslogDaoException;

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'SEVERITY2 = :severity2'.
	 */
	public List<PolicySyslog> findWhereSeverity2Equals(long severity2) throws PolicySyslogDaoException;

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'FILTERFLAG1 = :filterflag1'.
	 */
	public List<PolicySyslog> findWhereFilterflag1Equals(long filterflag1) throws PolicySyslogDaoException;

	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'FILTERFLAG2 = :filterflag2'.
	 */
	public List<PolicySyslog> findWhereFilterflag2Equals(long filterflag2) throws PolicySyslogDaoException;
	
	public List<PolicySyslog> findWhereMarkAndMpidEquals(String mark, long mpid) throws PolicySyslogDaoException;
	
	/** 
	 * Returns all rows from the POLICY_SYSLOG table that match the criteria 'MARK = :mark'.
	 */
	public List<PolicySyslog> findWhereManufactureAndMpidEquals(String manufacture, long mpid) throws PolicySyslogDaoException;
	/** 
	 * Returns the rows from the POLICY_SYSLOG table that matches the specified primary-key value.
	 */
	public PolicySyslog findByPrimaryKey(PolicySyslogPk pk) throws PolicySyslogDaoException;

	/**
	 * Returns all rows from the POLICY_SYSLOG table that syslog belongs table T_devpol_map.
	 * @return
	 * @throws PolicySyslogDaoException
	 */
	public List<PolicySyslog> findDeviceEvents() throws PolicySyslogDaoException;
	
	/**
	 * Returns all rows from the POLICY_SYSLOG table that  syslog belongs table T_linepol_map.
	 * @return
	 * @throws PolicySyslogDaoException
	 */
	public List<PolicySyslog> findPortEvents() throws PolicySyslogDaoException;
	
}
