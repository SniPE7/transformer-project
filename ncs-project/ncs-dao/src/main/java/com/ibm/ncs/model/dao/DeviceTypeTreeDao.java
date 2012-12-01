package com.ibm.ncs.model.dao;

import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.exceptions.DeviceTypeTreeDaoException;
import java.util.List;

public interface DeviceTypeTreeDao
{
	/**
	 * Method 'execute'
	 * 
	 * @throws DeviceTypeTreeDaoException
	 * @return List<DeviceTypeTree>
	 */
	//public List<DeviceTypeTree> execute() throws DeviceTypeTreeDaoException;
	public List<DeviceTypeTree> execute(String mrName, String cateName, String subCategory) throws DeviceTypeTreeDaoException;
	public List<DeviceTypeTree> listDeviceTypeTree() throws DeviceTypeTreeDaoException;
	public List<DeviceTypeTree> listDeviceTypeTreeWithModelOid() throws DeviceTypeTreeDaoException;
	public List<DeviceTypeTree> findDeviceTypeByManufacture(String mrName) throws DeviceTypeTreeDaoException;
	public List<DeviceTypeTree> findDeviceTypeByWhere(String mrName, String cateName) throws DeviceTypeTreeDaoException;
	public List<DeviceTypeTree> findDeviceTypeByWhere(String mrName, String cateCate, String subCategory) throws DeviceTypeTreeDaoException;
	
	/**
	 * Method 'findDeviceTypeByObjectID'
	 * 
	 * @throws DeviceTypeTreeDaoException
	 * @return List<DeviceTypeTree>
	 */
	public List<DeviceTypeTree> findDeviceTypeByObjectID(String objectid) throws DeviceTypeTreeDaoException;

	public List<DeviceTypeTree> findDeviceTypeByDeviceID(long devid) throws DeviceTypeTreeDaoException;
	
	public List<DeviceTypeTree>  searchByMulti(String mrName,String cateName,String subcate,String model,String objectid,String descr) throws DeviceTypeTreeDaoException;

	public DeviceTypeTree findByDtid(long dtid) throws DeviceTypeTreeDaoException;
}
