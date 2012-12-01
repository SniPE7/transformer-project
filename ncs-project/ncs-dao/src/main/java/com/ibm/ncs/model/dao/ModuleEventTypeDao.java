package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.ModuleEventTypeDao;
import com.ibm.ncs.model.dto.ModuleEventType;
import com.ibm.ncs.model.exceptions.ModuleEventTypeDaoException;
import java.util.List;

public interface ModuleEventTypeDao
{
	/**
	 * Method 'execute'
	 * 
	 * @param eveid
	 * @param general
	 * @param ecode
	 * @throws ModuleEventTypeDaoException
	 * @return List<ModuleEventType>
	 */
	public List<ModuleEventType> execute(long eveid, long general, long ecode) throws ModuleEventTypeDaoException;
	/**
	 * Method 'findModuleEventTypeByEveidGeneralEcode'
	 * 
	 * @param eveid = event id
	 * @param general : general = -1 (private) ; general =0 (public)
	 * @param ecode : ecode = 1 (device); ecode= 6(line/port);ecode=7 ( icmp )ecode=9 (pre def mib)
	 * @throws ModuleEventTypeDaoException
	 * @return List<ModuleEventType>
	 */
	public List<ModuleEventType> findModuleEventTypeByEveidGeneralEcode(long eveid,  long ecode) throws ModuleEventTypeDaoException;
	/**
	 * Method 'findModuleEventTypeByEveidGeneralEcode'
	 * 
	 * 
	 * @param general : general = -1 (private) ; general =0 (public)
	 * @param ecode : ecode = 1 (device); ecode= 6(line/port;ecode=7 ( icmp );ecode=9 (pre def mib)
	 * @throws ModuleEventTypeDaoException
	 * @return List<ModuleEventType>
	 */
	public List<ModuleEventType> findModuleEventTypeByGeneralEcode(  long ecode) throws ModuleEventTypeDaoException;

	public List<ModuleEventType> findModuleEventTypeByEcode(  long ecode) throws ModuleEventTypeDaoException;
}
