package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.DspSyslogEventsDao;
import com.ibm.ncs.model.dto.DspSyslogEvents;
import com.ibm.ncs.model.exceptions.DspSyslogEventsDaoException;
import java.util.List;

public interface DspSyslogEventsDao
{
	/**
	 * Method 'execute'
	 * 
	 * @param mark
	 * @param manufacture
	 * @throws DspSyslogEventsDaoException
	 * @return List<DspSyslogEvents>
	 */
	public List<DspSyslogEvents> execute(java.lang.String mark, java.lang.String manufacture) throws DspSyslogEventsDaoException;

	public List<DspSyslogEvents> listSyslogEventsByMarkAndManufacture(java.lang.String mark, java.lang.String manufacture) throws DspSyslogEventsDaoException;

	public List<DspSyslogEvents> listSyslogEventsByManufacture(java.lang.String manufacture) throws DspSyslogEventsDaoException;
	
//	public List<DspSyslogEvents> listSyslogEvents() throws DspSyslogEventsDaoException;
	
	public List<DspSyslogEvents> listSyslogEventsOnMpid(long mpid) throws DspSyslogEventsDaoException;
	
	public List<DspSyslogEvents> listOccupiedSyslogEvents() throws DspSyslogEventsDaoException;
}
