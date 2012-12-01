package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TGrpOrgDao;
import com.ibm.ncs.model.dto.TGrpOrg;
import com.ibm.ncs.model.dto.TGrpOrgPk;
import com.ibm.ncs.model.exceptions.TGrpOrgDaoException;
import java.util.List;

public interface TGrpOrgDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TGrpOrgPk
	 */
	public TGrpOrgPk insert(TGrpOrg dto);

	/** 
	 * Updates a single row in the T_GRP_ORG table.
	 */
	public void update(TGrpOrgPk pk, TGrpOrg dto) throws TGrpOrgDaoException;

	/** 
	 * Deletes a single row in the T_GRP_ORG table.
	 */
	public void delete(TGrpOrgPk pk) throws TGrpOrgDaoException;

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'GID = :gid'.
	 */
	public TGrpOrg findByPrimaryKey(long gid) throws TGrpOrgDaoException;

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria ''.
	 */
	public List<TGrpOrg> findAll() throws TGrpOrgDaoException;

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'GID = :gid'.
	 */
	public List<TGrpOrg> findWhereGidEquals(long gid) throws TGrpOrgDaoException;

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'GNAME = :gname'.
	 */
	public List<TGrpOrg> findWhereGnameEquals(String gname) throws TGrpOrgDaoException;

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'ORGABBR = :orgabbr'.
	 */
	public List<TGrpOrg> findWhereOrgabbrEquals(String orgabbr) throws TGrpOrgDaoException;

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'SUPID = :supid'.
	 */
	public List<TGrpOrg> findWhereSupidEquals(long supid) throws TGrpOrgDaoException;

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'LEVELS = :levels'.
	 */
	public List<TGrpOrg> findWhereLevelsEquals(long levels) throws TGrpOrgDaoException;

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TGrpOrg> findWhereDescriptionEquals(String description) throws TGrpOrgDaoException;

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'UNMALLOCEDIPSETFLAG = :unmallocedipsetflag'.
	 */
	public List<TGrpOrg> findWhereUnmallocedipsetflagEquals(String unmallocedipsetflag) throws TGrpOrgDaoException;

	/** 
	 * Returns all rows from the T_GRP_ORG table that match the criteria 'ORGSPELL = :orgspell'.
	 */
	public List<TGrpOrg> findWhereOrgspellEquals(String orgspell) throws TGrpOrgDaoException;

	/** 
	 * Returns the rows from the T_GRP_ORG table that matches the specified primary-key value.
	 */
	public TGrpOrg findByPrimaryKey(TGrpOrgPk pk) throws TGrpOrgDaoException;

}
