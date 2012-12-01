package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TPortlineMapDao;
import com.ibm.ncs.model.dto.TPortlineMap;
import com.ibm.ncs.model.dto.TPortlineMapPk;
import com.ibm.ncs.model.exceptions.TPortlineMapDaoException;
import java.util.List;

public interface TPortlineMapDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TPortlineMapPk
	 */
	public TPortlineMapPk insert(TPortlineMap dto);

	/** 
	 * Updates a single row in the T_PORTLINE_MAP table.
	 */
	public void update(TPortlineMapPk pk, TPortlineMap dto) throws TPortlineMapDaoException;

	/** 
	 * Deletes a single row in the T_PORTLINE_MAP table.
	 */
	public void delete(TPortlineMapPk pk) throws TPortlineMapDaoException;

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'PTID = :ptid'.
	 */
	public TPortlineMap findByPrimaryKey(long ptid) throws TPortlineMapDaoException;

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria ''.
	 */
	public List<TPortlineMap> findAll() throws TPortlineMapDaoException;

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'PTID = :ptid'.
	 */
	public List<TPortlineMap> findWherePtidEquals(long ptid) throws TPortlineMapDaoException;

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'LEID = :leid'.
	 */
	public List<TPortlineMap> findWhereLeidEquals(long leid) throws TPortlineMapDaoException;

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'SRID = :srid'.
	 */
	public List<TPortlineMap> findWhereSridEquals(long srid) throws TPortlineMapDaoException;

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'ADMIN = :admin'.
	 */
	public List<TPortlineMap> findWhereAdminEquals(String admin) throws TPortlineMapDaoException;

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'PHONE = :phone'.
	 */
	public List<TPortlineMap> findWherePhoneEquals(String phone) throws TPortlineMapDaoException;

	/** 
	 * Returns all rows from the T_PORTLINE_MAP table that match the criteria 'SIDE = :side'.
	 */
	public List<TPortlineMap> findWhereSideEquals(String side) throws TPortlineMapDaoException;

	/** 
	 * Returns the rows from the T_PORTLINE_MAP table that matches the specified primary-key value.
	 */
	public TPortlineMap findByPrimaryKey(TPortlineMapPk pk) throws TPortlineMapDaoException;

}
