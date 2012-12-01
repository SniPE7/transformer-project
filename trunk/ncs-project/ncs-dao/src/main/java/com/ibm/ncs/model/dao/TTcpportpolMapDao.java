package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TTcpportpolMapDao;
import com.ibm.ncs.model.dto.TTcpportpolMap;
import com.ibm.ncs.model.dto.TTcpportpolMapPk;
import com.ibm.ncs.model.exceptions.TTcpportpolMapDaoException;
import java.util.List;

public interface TTcpportpolMapDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TTcpportpolMapPk
	 */
	public TTcpportpolMapPk insert(TTcpportpolMap dto);

	/** 
	 * Updates a single row in the T_TCPPORTPOL_MAP table.
	 */
	public void update(TTcpportpolMapPk pk, TTcpportpolMap dto) throws TTcpportpolMapDaoException;

	/** 
	 * Deletes a single row in the T_TCPPORTPOL_MAP table.
	 */
	public void delete(TTcpportpolMapPk pk) throws TTcpportpolMapDaoException;

	/** 
	 * Returns all rows from the T_TCPPORTPOL_MAP table that match the criteria 'HID = :hid'.
	 */
	public TTcpportpolMap findByPrimaryKey(long hid) throws TTcpportpolMapDaoException;

	/** 
	 * Returns all rows from the T_TCPPORTPOL_MAP table that match the criteria ''.
	 */
	public List<TTcpportpolMap> findAll() throws TTcpportpolMapDaoException;

	/** 
	 * Returns all rows from the T_TCPPORTPOL_MAP table that match the criteria 'HID = :hid'.
	 */
	public List<TTcpportpolMap> findWhereHidEquals(long hid) throws TTcpportpolMapDaoException;

	/** 
	 * Returns all rows from the T_TCPPORTPOL_MAP table that match the criteria 'MPID = :mpid'.
	 */
	public List<TTcpportpolMap> findWhereMpidEquals(long mpid) throws TTcpportpolMapDaoException;

	/** 
	 * Returns all rows from the T_TCPPORTPOL_MAP table that match the criteria 'PPID = :ppid'.
	 */
	public List<TTcpportpolMap> findWherePpidEquals(long ppid) throws TTcpportpolMapDaoException;

	/** 
	 * Returns the rows from the T_TCPPORTPOL_MAP table that matches the specified primary-key value.
	 */
	public TTcpportpolMap findByPrimaryKey(TTcpportpolMapPk pk) throws TTcpportpolMapDaoException;

}
