package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VSyslogPdmLineNotcareDao;
import com.ibm.ncs.model.dto.VSyslogPdmLineNotcare;
import com.ibm.ncs.model.exceptions.VSyslogPdmLineNotcareDaoException;
import java.util.List;

public interface VSyslogPdmLineNotcareDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VSyslogPdmLineNotcare dto);

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_LINE_NOTCARE table that match the criteria ''.
	 */
	public List<VSyslogPdmLineNotcare> findAll() throws VSyslogPdmLineNotcareDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_LINE_NOTCARE table that match the criteria 'DEVIP = :devip'.
	 */
	public List<VSyslogPdmLineNotcare> findWhereDevipEquals(String devip) throws VSyslogPdmLineNotcareDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_PDM_LINE_NOTCARE table that match the criteria 'OIDNAME = :oidname'.
	 */
	public List<VSyslogPdmLineNotcare> findWhereOidnameEquals(String oidname) throws VSyslogPdmLineNotcareDaoException;

}
