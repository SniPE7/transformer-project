package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceInfoPk;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import java.util.List;

public interface TDeviceInfoDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TDeviceInfoPk
	 */
	public TDeviceInfoPk insert(TDeviceInfo dto);

	/** 
	 * Updates a single row in the T_DEVICE_INFO table.
	 */
	public void update(TDeviceInfoPk pk, TDeviceInfo dto) throws TDeviceInfoDaoException;

	/** 
	 * Deletes a single row in the T_DEVICE_INFO table.
	 */
	public void delete(TDeviceInfoPk pk) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'DEVID = :devid'.
	 */
	public TDeviceInfo findByPrimaryKey(long devid) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria ''.
	 */
	public List<TDeviceInfo> findAll() throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'DEVID = :devid'.
	 */
	public List<TDeviceInfo> findWhereDevidEquals(long devid) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'DEVIP = :devip'.
	 */
	public List<TDeviceInfo> findWhereDevipEquals(String devip) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'IPDECODE = :ipdecode'.
	 */
	public List<TDeviceInfo> findWhereIpdecodeEquals(long ipdecode) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'SYSNAME = :sysname'.
	 */
	public List<TDeviceInfo> findWhereSysnameEquals(String sysname) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'SYSNAMEALIAS = :sysnamealias'.
	 */
	public List<TDeviceInfo> findWhereSysnamealiasEquals(String sysnamealias) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'RSNO = :rsno'.
	 */
	public List<TDeviceInfo> findWhereRsnoEquals(String rsno) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'SRID = :srid'.
	 */
	public List<TDeviceInfo> findWhereSridEquals(long srid) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'ADMIN = :admin'.
	 */
	public List<TDeviceInfo> findWhereAdminEquals(String admin) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'PHONE = :phone'.
	 */
	public List<TDeviceInfo> findWherePhoneEquals(String phone) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'MRID = :mrid'.
	 */
	public List<TDeviceInfo> findWhereMridEquals(long mrid) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'DTID = :dtid'.
	 */
	public List<TDeviceInfo> findWhereDtidEquals(long dtid) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'SERIALID = :serialid'.
	 */
	public List<TDeviceInfo> findWhereSerialidEquals(String serialid) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'SWVERSION = :swversion'.
	 */
	public List<TDeviceInfo> findWhereSwversionEquals(String swversion) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'RAMSIZE = :ramsize'.
	 */
	public List<TDeviceInfo> findWhereRamsizeEquals(long ramsize) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'RAMUNIT = :ramunit'.
	 */
	public List<TDeviceInfo> findWhereRamunitEquals(String ramunit) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'NVRAMSIZE = :nvramsize'.
	 */
	public List<TDeviceInfo> findWhereNvramsizeEquals(long nvramsize) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'NVRAMUNIT = :nvramunit'.
	 */
	public List<TDeviceInfo> findWhereNvramunitEquals(String nvramunit) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'FLASHSIZE = :flashsize'.
	 */
	public List<TDeviceInfo> findWhereFlashsizeEquals(long flashsize) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'FLASHUNIT = :flashunit'.
	 */
	public List<TDeviceInfo> findWhereFlashunitEquals(String flashunit) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'FLASHFILENAME = :flashfilename'.
	 */
	public List<TDeviceInfo> findWhereFlashfilenameEquals(String flashfilename) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'FLASHFILESIZE = :flashfilesize'.
	 */
	public List<TDeviceInfo> findWhereFlashfilesizeEquals(String flashfilesize) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'RCOMMUNITY = :rcommunity'.
	 */
	public List<TDeviceInfo> findWhereRcommunityEquals(String rcommunity) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'WCOMMUNITY = :wcommunity'.
	 */
	public List<TDeviceInfo> findWhereWcommunityEquals(String wcommunity) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'DESCRIPTION = :description'.
	 */
	public List<TDeviceInfo> findWhereDescriptionEquals(String description) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'DOMAINID = :domainid'.
	 */
	public List<TDeviceInfo> findWhereDomainidEquals(long domainid) throws TDeviceInfoDaoException;

	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'SNMPVERSION = :snmpversion'.
	 */
	public List<TDeviceInfo> findWhereSnmpversionEquals(String snmpversion) throws TDeviceInfoDaoException;

	/** 
	 * Returns the rows from the T_DEVICE_INFO table that matches the specified primary-key value.
	 */
	public TDeviceInfo findByPrimaryKey(TDeviceInfoPk pk) throws TDeviceInfoDaoException;
	
	/** 
	 * Returns all rows from the T_DEVICE_INFO table that match the criteria 'IPDECODE  between a list of :ipdecode_min and :ipscope_max' .
	 * 
	 */
	public List<TDeviceInfo> findWhereIpdecodeBetweenScopes(long[] min, long [] max) throws TDeviceInfoDaoException;


	public List<TDeviceInfo> findDevInExcel(String [] devips) throws TDeviceInfoDaoException;
}
