package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TGrpNetPk;
import com.ibm.ncs.model.exceptions.TGrpNetDaoException;
import java.util.List;

public interface TGrpNetDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TGrpNetPk
	 */
	public TGrpNetPk insert(TGrpNet dto);

	/** 
	 * Updates a single row in the T_GRP_NET table.
	 */
	public void update(TGrpNetPk pk, TGrpNet dto) throws TGrpNetDaoException;

	/** 
	 * Deletes a single row in the T_GRP_NET table.
	 */
	public void delete(TGrpNetPk pk) throws TGrpNetDaoException;

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'GID = :gid'.
	 */
	public TGrpNet findByPrimaryKey(long gid) throws TGrpNetDaoException;

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria ''.
	 */
	public List<TGrpNet> findAll() throws TGrpNetDaoException;

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'GID = :gid'.
	 */
	public List<TGrpNet> findWhereGidEquals(long gid) throws TGrpNetDaoException;

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'GNAME = :gname'.
	 */
	public List<TGrpNet> findWhereGnameEquals(String gname) throws TGrpNetDaoException;

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'SUPID = :supid'.
	 */
	public List<TGrpNet> findWhereSupidEquals(long supid) throws TGrpNetDaoException;

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'LEVELS = :levels'.
	 */
	public List<TGrpNet> findWhereLevelsEquals(long levels) throws TGrpNetDaoException;

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TGrpNet> findWhereDescriptionEquals(String description) throws TGrpNetDaoException;

	/** 
	 * Returns all rows from the T_GRP_NET table that match the criteria 'UNMALLOCEDIPSETFLAG = :unmallocedipsetflag'.
	 */
	public List<TGrpNet> findWhereUnmallocedipsetflagEquals(String unmallocedipsetflag) throws TGrpNetDaoException;

	/** 
	 * Returns the rows from the T_GRP_NET table that matches the specified primary-key value.
	 */
	public TGrpNet findByPrimaryKey(TGrpNetPk pk) throws TGrpNetDaoException;

}
