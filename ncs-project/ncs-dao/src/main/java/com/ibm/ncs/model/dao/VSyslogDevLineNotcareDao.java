package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VSyslogDevLineNotcareDao;
import com.ibm.ncs.model.dto.VSyslogDevLineNotcare;
import com.ibm.ncs.model.exceptions.VSyslogDevLineNotcareDaoException;
import java.util.List;

public interface VSyslogDevLineNotcareDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VSyslogDevLineNotcare dto);

	/** 
	 * Returns all rows from the V_SYSLOG_DEV_LINE_NOTCARE table that match the criteria ''.
	 */
	public List<VSyslogDevLineNotcare> findAll() throws VSyslogDevLineNotcareDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_DEV_LINE_NOTCARE table that match the criteria 'DEVIP = :devip'.
	 */
	public List<VSyslogDevLineNotcare> findWhereDevipEquals(String devip) throws VSyslogDevLineNotcareDaoException;

}
