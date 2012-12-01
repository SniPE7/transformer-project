package com.ibm.ncs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;

import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.exceptions.DeviceTypeTreeDaoException;
import com.ibm.ncs.model.exceptions.TCategoryMapInitDaoException;
import com.ibm.ncs.model.exceptions.TDeviceTypeInitDaoException;
import com.ibm.ncs.model.exceptions.TManufacturerInfoInitDaoException;
import com.ibm.ncs.model.facade.baseInfoFacade;


public class BaseInfoServices  {

	private TDeviceTypeInitDao tDeviceTypeInitDao;
	
	private TCategoryMapInitDao tCategoryMapInfoDao;
	
	private TManufacturerInfoInitDao tManufacturerInfoInitDao;
	
	private DeviceTypeTreeDao deviceTypeTreeDao;
	
	
	public List<DeviceTypeTree> listDeviceTypeTreeWithModelOid() throws DeviceTypeTreeDaoException{
		return deviceTypeTreeDao.listDeviceTypeTreeWithModelOid(); 
		//.execute();
	}//total


	public List<DeviceTypeTree> findDeviceTypeTree() throws DeviceTypeTreeDaoException{
		return deviceTypeTreeDao.listDeviceTypeTree(); 
		//.execute();
	}//total

	public List<DeviceTypeTree> findDeviceTypeByManufacture(String mrname) throws DeviceTypeTreeDaoException{
		return deviceTypeTreeDao.findDeviceTypeByManufacture(mrname);
	}

	public List<DeviceTypeTree> findDeviceTypeByWhere(String mrname, String category) throws DeviceTypeTreeDaoException{
		return deviceTypeTreeDao.findDeviceTypeByWhere(mrname,category);
	}
	
	public List<DeviceTypeTree> findDeviceTypeByWhere(String mrname, String category, String subcategory) throws DeviceTypeTreeDaoException{
		return deviceTypeTreeDao.findDeviceTypeByWhere(mrname, category, subcategory);
	}


	public List<TManufacturerInfoInit> findAllManufactures() throws TManufacturerInfoInitDaoException {
		return tManufacturerInfoInitDao.findAll();
	}
	
	public List<DeviceTypeTree> findDeviceTypeByObjectID(String objectid) throws DeviceTypeTreeDaoException {
		return deviceTypeTreeDao.findDeviceTypeByObjectID(objectid);
	}
	

	public List<TCategoryMapInit> findAllCategory() throws TCategoryMapInitDaoException{
		return tCategoryMapInfoDao.findAll();
	}
	

	
	

	public void setTDeviceTypeInitDao(TDeviceTypeInitDao tDeviceTypeInitDao) {
		tDeviceTypeInitDao = tDeviceTypeInitDao;
	}

	public void setTCategoryMapInitDao(TCategoryMapInitDao tCategoryMapInitDao) {
		tCategoryMapInitDao = tCategoryMapInitDao;
	}

	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao TManufacturerInfoInitDao) {
		tManufacturerInfoInitDao = TManufacturerInfoInitDao;
	}

	public void setDeviceTypeTreeDao(DeviceTypeTreeDao deviceTypeTreeDao) {
		this.deviceTypeTreeDao = deviceTypeTreeDao;
	}
	
	

	
	

}
