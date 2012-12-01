package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TLinepolMapDao;
import com.ibm.ncs.model.dto.TLinepolMap;
import com.ibm.ncs.model.dto.TLinepolMapPk;
import com.ibm.ncs.model.exceptions.TLinepolMapDaoException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface TLinepolMapDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TLinepolMapPk
	 */
	public TLinepolMapPk insert(TLinepolMap dto);

	/** 
	 * Updates a single row in the T_LINEPOL_MAP table.
	 */
	public void update(TLinepolMapPk pk, TLinepolMap dto) throws TLinepolMapDaoException;

	/** 
	 * Deletes a single row in the T_LINEPOL_MAP table.
	 */
	public void delete(TLinepolMapPk pk) throws TLinepolMapDaoException;

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria 'PTID = :ptid'.
	 */
	public TLinepolMap findByPrimaryKey(long ptid) throws TLinepolMapDaoException;

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria ''.
	 */
	public List<TLinepolMap> findAll() throws TLinepolMapDaoException;

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria 'PTID = :ptid'.
	 */
	public List<TLinepolMap> findWherePtidEquals(long ptid) throws TLinepolMapDaoException;

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria 'PPID = :ppid'.
	 */
	public List<TLinepolMap> findWherePpidEquals(long ppid) throws TLinepolMapDaoException;

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria 'MCODE = :mcode'.
	 */
	public List<TLinepolMap> findWhereMcodeEquals(long mcode) throws TLinepolMapDaoException;

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria 'FLAG = :flag'.
	 */
	public List<TLinepolMap> findWhereFlagEquals(Integer flag) throws TLinepolMapDaoException;

	/** 
	 * Returns all rows from the T_LINEPOL_MAP table that match the criteria 'MPID = :mpid'.
	 */
	public List<TLinepolMap> findWhereMpidEquals(long mpid) throws TLinepolMapDaoException;

	/** 
	 * Returns the rows from the T_LINEPOL_MAP table that matches the specified primary-key value.
	 */
	public TLinepolMap findByPrimaryKey(TLinepolMapPk pk) throws TLinepolMapDaoException;

	/** 
	 * Deletes  rows in the T_LINEPOL_MAP table BUT not exist in the table T_PORT_INFO.
	 */
	@Transactional
	public void removeNoUseData() throws TLinepolMapDaoException;
}
