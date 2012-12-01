package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TLineInfoDao;
import com.ibm.ncs.model.dto.TLineInfo;
import com.ibm.ncs.model.dto.TLineInfoPk;
import com.ibm.ncs.model.exceptions.TLineInfoDaoException;
import java.util.Date;
import java.util.List;

public interface TLineInfoDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TLineInfoPk
	 */
	public TLineInfoPk insert(TLineInfo dto);

	/** 
	 * Updates a single row in the T_LINE_INFO table.
	 */
	public void update(TLineInfoPk pk, TLineInfo dto) throws TLineInfoDaoException;

	/** 
	 * Deletes a single row in the T_LINE_INFO table.
	 */
	public void delete(TLineInfoPk pk) throws TLineInfoDaoException;

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'LEID = :leid'.
	 */
	public TLineInfo findByPrimaryKey(long leid) throws TLineInfoDaoException;

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria ''.
	 */
	public List<TLineInfo> findAll() throws TLineInfoDaoException;

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'LEID = :leid'.
	 */
	public List<TLineInfo> findWhereLeidEquals(long leid) throws TLineInfoDaoException;

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'LINENAME = :linename'.
	 */
	public List<TLineInfo> findWhereLinenameEquals(String linename) throws TLineInfoDaoException;

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'LENO = :leno'.
	 */
	public List<TLineInfo> findWhereLenoEquals(String leno) throws TLineInfoDaoException;

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'CATEGORY = :category'.
	 */
	public List<TLineInfo> findWhereCategoryEquals(long category) throws TLineInfoDaoException;

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'BANDWIDTH = :bandwidth'.
	 */
	public List<TLineInfo> findWhereBandwidthEquals(long bandwidth) throws TLineInfoDaoException;

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'BANDWIDTHUNIT = :bandwidthunit'.
	 */
	public List<TLineInfo> findWhereBandwidthunitEquals(String bandwidthunit) throws TLineInfoDaoException;

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'APPLYTIME = :applytime'.
	 */
	public List<TLineInfo> findWhereApplytimeEquals(Date applytime) throws TLineInfoDaoException;

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'OPENTIME = :opentime'.
	 */
	public List<TLineInfo> findWhereOpentimeEquals(Date opentime) throws TLineInfoDaoException;

	/** 
	 * Returns all rows from the T_LINE_INFO table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TLineInfo> findWhereDescriptionEquals(String description) throws TLineInfoDaoException;

	/** 
	 * Returns the rows from the T_LINE_INFO table that matches the specified primary-key value.
	 */
	public TLineInfo findByPrimaryKey(TLineInfoPk pk) throws TLineInfoDaoException;

}
