package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TDevpolMapDao;
import com.ibm.ncs.model.dto.TDevpolMap;
import com.ibm.ncs.model.dto.TDevpolMapPk;
import com.ibm.ncs.model.exceptions.TDevpolMapDaoException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface TDevpolMapDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TDevpolMapPk
	 */
	public TDevpolMapPk insert(TDevpolMap dto);

	/** 
	 * Updates a single row in the T_DEVPOL_MAP table.
	 */
	public void update(TDevpolMapPk pk, TDevpolMap dto) throws TDevpolMapDaoException;

	/** 
	 * Deletes a single row in the T_DEVPOL_MAP table.
	 */
	public void delete(TDevpolMapPk pk) throws TDevpolMapDaoException;

	/** 
	 * Returns all rows from the T_DEVPOL_MAP table that match the criteria 'DEVID = :devid'.
	 */
	public TDevpolMap findByPrimaryKey(long devid) throws TDevpolMapDaoException;

	/** 
	 * Returns all rows from the T_DEVPOL_MAP table that match the criteria ''.
	 */
	public List<TDevpolMap> findAll() throws TDevpolMapDaoException;

	/** 
	 * Returns all rows from the T_DEVPOL_MAP table that match the criteria 'DEVID = :devid'.
	 */
	public List<TDevpolMap> findWhereDevidEquals(long devid) throws TDevpolMapDaoException;

	/** 
	 * Returns all rows from the T_DEVPOL_MAP table that match the criteria 'MPID = :mpid'.
	 */
	public List<TDevpolMap> findWhereMpidEquals(long mpid) throws TDevpolMapDaoException;

	/** 
	 * Returns all rows from the T_DEVPOL_MAP table that match the criteria 'PPID = :ppid'.
	 */
	public List<TDevpolMap> findWherePpidEquals(long ppid) throws TDevpolMapDaoException;

	/** 
	 * Returns the rows from the T_DEVPOL_MAP table that matches the specified primary-key value.
	 */
	public TDevpolMap findByPrimaryKey(TDevpolMapPk pk) throws TDevpolMapDaoException;

	/** 
	 * Deletes  rows in the T_DEVPOL_MAP table BUT not exist in the table T_DEVICE_INFO.
	 */
	@Transactional
	public void removeNoUseData() throws TDevpolMapDaoException;
}
