package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.VSyslogPortLineNotcareDao;
import com.ibm.ncs.model.dto.VSyslogPortLineNotcare;
import com.ibm.ncs.model.exceptions.VSyslogPortLineNotcareDaoException;
import java.util.List;

public interface VSyslogPortLineNotcareDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(VSyslogPortLineNotcare dto);

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_LINE_NOTCARE table that match the criteria ''.
	 */
	public List<VSyslogPortLineNotcare> findAll() throws VSyslogPortLineNotcareDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_LINE_NOTCARE table that match the criteria 'DEVIP = :devip'.
	 */
	public List<VSyslogPortLineNotcare> findWhereDevipEquals(String devip) throws VSyslogPortLineNotcareDaoException;

	/** 
	 * Returns all rows from the V_SYSLOG_PORT_LINE_NOTCARE table that match the criteria 'IFDESCR = :ifdescr'.
	 */
	public List<VSyslogPortLineNotcare> findWhereIfdescrEquals(String ifdescr) throws VSyslogPortLineNotcareDaoException;

}
