package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.DspEventsFromPolicySyslogDao;
import com.ibm.ncs.model.dto.DspEventsFromPolicySyslog;
import com.ibm.ncs.model.exceptions.DspEventsFromPolicySyslogDaoException;
import java.util.List;

public interface DspEventsFromPolicySyslogDao
{
	/**
	 * Method 'execute'
	 * 
	 * @param manufacture
	 * @param mpid
	 * @throws DspEventsFromPolicySyslogDaoException
	 * @return List<DspEventsFromPolicySyslog>
	 */
	public List<DspEventsFromPolicySyslog> execute(java.lang.String manufacture, long mpid) throws DspEventsFromPolicySyslogDaoException;

	public List<DspEventsFromPolicySyslog> findDspEventsByManufactureAndMpid(java.lang.String manufacture, long mpid) throws DspEventsFromPolicySyslogDaoException;

}
