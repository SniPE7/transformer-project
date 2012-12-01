package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.DefMibGrpPk;
import com.ibm.ncs.model.exceptions.DefMibGrpDaoException;
import java.util.List;

public interface DefMibGrpDao
{
	/**
	 * 
	 * @return table Name
	 */
	public String getTableName();
	
	
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return DefMibGrpPk
	 */
	public DefMibGrpPk insert(DefMibGrp dto);

	/** 
	 * Updates a single row in the DEF_MIB_GRP table.
	 */
	public void update(DefMibGrpPk pk, DefMibGrp dto) throws DefMibGrpDaoException;
	public void update(DefMibGrp dto) throws DefMibGrpDaoException;
	
	/** 
	 * Deletes a single row in the DEF_MIB_GRP table.
	 */
	public void delete(DefMibGrpPk pk) throws DefMibGrpDaoException;
	public void delete(long mid) throws DefMibGrpDaoException;
	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'MID = :mid'.
	 */
	public DefMibGrp findByPrimaryKey(long mid) throws DefMibGrpDaoException;

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria ''.
	 */
	public List<DefMibGrp> findAll() throws DefMibGrpDaoException;

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'MID = :mid'.
	 */
	public List<DefMibGrp> findWhereMidEquals(long mid) throws DefMibGrpDaoException;

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'NAME = :name'.
	 */
	public List<DefMibGrp> findWhereNameEquals(String name) throws DefMibGrpDaoException;

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'INDEXOID = :indexoid'.
	 */
	public List<DefMibGrp> findWhereIndexoidEquals(String indexoid) throws DefMibGrpDaoException;

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'INDEXVAR = :indexvar'.
	 */
	public List<DefMibGrp> findWhereIndexvarEquals(String indexvar) throws DefMibGrpDaoException;

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'DESCROID = :descroid'.
	 */
	public List<DefMibGrp> findWhereDescroidEquals(String descroid) throws DefMibGrpDaoException;

	/** 
	 * Returns all rows from the DEF_MIB_GRP table that match the criteria 'DESCRVAR = :descrvar'.
	 */
	public List<DefMibGrp> findWhereDescrvarEquals(String descrvar) throws DefMibGrpDaoException;

	/** 
	 * Returns the rows from the DEF_MIB_GRP table that matches the specified primary-key value.
	 */
	public DefMibGrp findByPrimaryKey(DefMibGrpPk pk) throws DefMibGrpDaoException;

	public List<DefMibGrp> findAllWhereMidEqualsMibInfoMid() throws DefMibGrpDaoException;
}
