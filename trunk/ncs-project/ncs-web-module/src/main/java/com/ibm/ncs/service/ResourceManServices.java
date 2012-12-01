package com.ibm.ncs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;

import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.exceptions.DeviceTypeTreeDaoException;
import com.ibm.ncs.model.exceptions.TCategoryMapInitDaoException;
import com.ibm.ncs.model.exceptions.TDeviceTypeInitDaoException;
import com.ibm.ncs.model.exceptions.TGrpNetDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.model.exceptions.TManufacturerInfoInitDaoException;
import com.ibm.ncs.model.facade.baseInfoFacade;


public class ResourceManServices  {

	private TGrpNetDao TGrpNetDao;
	
	private TListIpDao TListIpDao;
	
	private DeviceTypeTreeDao deviceTypeTreeDao;
	
	

	public List<TGrpNet> findAl() throws  TGrpNetDaoException{
		return TGrpNetDao.findAll();
	}//total


	public List<TListIp> findWhereGidEquals(long gid) throws TListIpDaoException{
		return TListIpDao.findWhereGidEquals(gid);
	}
	
	public List<TListIp> findWhereIpEquals(String ip) throws TListIpDaoException{
		return TListIpDao.findWhereIpEquals(ip);
	}

	

	
	


	public void setTGrpNetDao(TGrpNetDao grpNetDao) {
		TGrpNetDao = grpNetDao;
	}

	public void setTListIpDao(TListIpDao listIpDao) {
		TListIpDao = listIpDao;
	}

	public void setDeviceTypeTreeDao(DeviceTypeTreeDao deviceTypeTreeDao) {
		this.deviceTypeTreeDao = deviceTypeTreeDao;
	}
	
	
	
	
	

}
