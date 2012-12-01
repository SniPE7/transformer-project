package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.PredefmibPolMapDao;
import com.ibm.ncs.model.dto.PredefmibPolMap;
import com.ibm.ncs.model.dto.PredefmibPolMapPk;
import com.ibm.ncs.model.exceptions.PredefmibPolMapDaoException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface PredefmibPolMapDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return PredefmibPolMapPk
	 */
	public PredefmibPolMapPk insert(PredefmibPolMap dto);

	/** 
	 * Updates a single row in the PREDEFMIB_POL_MAP table.
	 */
	public void update(PredefmibPolMapPk pk, PredefmibPolMap dto) throws PredefmibPolMapDaoException;

	/** 
	 * Deletes a single row in the PREDEFMIB_POL_MAP table.
	 */
	public void delete(PredefmibPolMapPk pk) throws PredefmibPolMapDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'PDMID = :pdmid'.
	 */
	public PredefmibPolMap findByPrimaryKey(long pdmid) throws PredefmibPolMapDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria ''.
	 */
	public List<PredefmibPolMap> findAll() throws PredefmibPolMapDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'PDMID = :pdmid'.
	 */
	public List<PredefmibPolMap> findByPredefmibInfo(long pdmid) throws PredefmibPolMapDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'PDMID = :pdmid'.
	 */
	public List<PredefmibPolMap> findWherePdmidEquals(long pdmid) throws PredefmibPolMapDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'MPID = :mpid'.
	 */
	public List<PredefmibPolMap> findWhereMpidEquals(long mpid) throws PredefmibPolMapDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'PPID = :ppid'.
	 */
	public List<PredefmibPolMap> findWherePpidEquals(long ppid) throws PredefmibPolMapDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'MCODE = :mcode'.
	 */
	public List<PredefmibPolMap> findWhereMcodeEquals(long mcode) throws PredefmibPolMapDaoException;

	/** 
	 * Returns all rows from the PREDEFMIB_POL_MAP table that match the criteria 'FLAG = :flag'.
	 */
	public List<PredefmibPolMap> findWhereFlagEquals(int flag) throws PredefmibPolMapDaoException;

	/** 
	 * Returns the rows from the PREDEFMIB_POL_MAP table that matches the specified primary-key value.
	 */
	public PredefmibPolMap findByPrimaryKey(PredefmibPolMapPk pk) throws PredefmibPolMapDaoException;

	/** 
	 * Deletes  rows in the PREDEFMIB_POL_MAP table BUT not exist in the table PREDEFMIB_INFO.
	 */
	@Transactional
	public void removeNoUseData() throws PredefmibPolMapDaoException;
}
