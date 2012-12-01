package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TSvrmodMapDao;
import com.ibm.ncs.model.dto.TSvrmodMap;
import com.ibm.ncs.model.dto.TSvrmodMapPk;
import com.ibm.ncs.model.exceptions.TSvrmodMapDaoException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface TSvrmodMapDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TSvrmodMapPk
	 */
	public TSvrmodMapPk insert(TSvrmodMap dto);

	/** 
	 * Updates a single row in the T_SVRMOD_MAP table.
	 */
	public void update(TSvrmodMapPk pk, TSvrmodMap dto) throws TSvrmodMapDaoException;

	/** 
	 * Deletes a single row in the T_SVRMOD_MAP table.
	 */
	public void delete(TSvrmodMapPk pk) throws TSvrmodMapDaoException;

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table that match the criteria 'NMSID = :nmsid AND MODID = :modid'.
	 */
	public TSvrmodMap findByPrimaryKey(long nmsid, long modid) throws TSvrmodMapDaoException;

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table that match the criteria ''.
	 */
	public List<TSvrmodMap> findAll() throws TSvrmodMapDaoException;

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table that match the criteria 'NMSID = :nmsid'.
	 */
	public List<TSvrmodMap> findWhereNmsidEquals(long nmsid) throws TSvrmodMapDaoException;

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table that match the criteria 'MODID = :modid'.
	 */
	public List<TSvrmodMap> findWhereModidEquals(long modid) throws TSvrmodMapDaoException;

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table that match the criteria 'PATH = :path'.
	 */
	public List<TSvrmodMap> findWherePathEquals(String path) throws TSvrmodMapDaoException;

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TSvrmodMap> findWhereDescriptionEquals(String description) throws TSvrmodMapDaoException;

	/** 
	 * Returns the rows from the T_SVRMOD_MAP table that matches the specified primary-key value.
	 */
	public TSvrmodMap findByPrimaryKey(TSvrmodMapPk pk) throws TSvrmodMapDaoException;

	/** 
	 * Returns all rows from the T_SVRMOD_MAP table join table T_MODULE_INFO_INIT that match the criteria 'MNAME = :mname'.
	 */
	@Transactional
	public List<TSvrmodMap> findByModuleName(String mname) throws TSvrmodMapDaoException;
}
