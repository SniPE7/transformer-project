package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TModgrpMapDao;
import com.ibm.ncs.model.dto.TModgrpMap;
import com.ibm.ncs.model.dto.TModgrpMapPk;
import com.ibm.ncs.model.exceptions.TModgrpMapDaoException;
import java.util.List;

public interface TModgrpMapDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TModgrpMapPk
	 */
	public TModgrpMapPk insert(TModgrpMap dto);

	/** 
	 * Updates a single row in the T_MODGRP_MAP table.
	 */
	public void update(TModgrpMapPk pk, TModgrpMap dto) throws TModgrpMapDaoException;

	/** 
	 * Deletes a single row in the T_MODGRP_MAP table.
	 */
	public void delete(TModgrpMapPk pk) throws TModgrpMapDaoException;

	/** 
	 * Returns all rows from the T_MODGRP_MAP table that match the criteria 'GID = :gid AND NMSID = :nmsid AND MODID = :modid'.
	 */
	public TModgrpMap findByPrimaryKey(long gid, long nmsid, long modid) throws TModgrpMapDaoException;

	/** 
	 * Returns all rows from the T_MODGRP_MAP table that match the criteria ''.
	 */
	public List<TModgrpMap> findAll() throws TModgrpMapDaoException;

	/** 
	 * Returns all rows from the T_MODGRP_MAP table that match the criteria 'GID = :gid'.
	 */
	public List<TModgrpMap> findWhereGidEquals(long gid) throws TModgrpMapDaoException;

	/** 
	 * Returns all rows from the T_MODGRP_MAP table that match the criteria 'NMSID = :nmsid'.
	 */
	public List<TModgrpMap> findWhereNmsidEquals(long nmsid) throws TModgrpMapDaoException;

	/** 
	 * Returns all rows from the T_MODGRP_MAP table that match the criteria 'MODID = :modid'.
	 */
	public List<TModgrpMap> findWhereModidEquals(long modid) throws TModgrpMapDaoException;

	/** 
	 * Returns the rows from the T_MODGRP_MAP table that matches the specified primary-key value.
	 */
	public TModgrpMap findByPrimaryKey(TModgrpMapPk pk) throws TModgrpMapDaoException;

}
