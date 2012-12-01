package com.ibm.ncs.web.resourceman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dao.PredefmibInfoDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.PredefmibInfoPk;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceInfoPk;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.dto.TPortInfoPk;
import com.ibm.ncs.model.exceptions.DeviceTypeTreeDaoException;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.AppException;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SnmpUtil;
import com.ibm.ncs.web.resourceman.bean.DeviceInfoFormBean;

public class DeleteDeviceInfoController implements Controller {

	DeviceInfoFormBean form;
	//BaseInfoServices  	BaseInfoService;
	DeviceTypeTreeDao  DeviceTypeTreeDao;
	String pageView;
	TDeviceInfoDao TDeviceInfoDao;
	TPortInfoDao TPortInfoDao;
	PredefmibInfoDao PredefmibInfoDao;
	
	

	public TPortInfoDao getTPortInfoDao() {
		return TPortInfoDao;
	}


	public void setTPortInfoDao(TPortInfoDao portInfoDao) {
		TPortInfoDao = portInfoDao;
	}


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {


		try {
			String devidStr =  request.getParameter("devid");
			long devid = Long.parseLong(devidStr);
			System.out.println("the devid from jsp is-------"+devid);
			//--port
			List<TPortInfo> ports = TPortInfoDao.findWhereDevidEquals(devid);
			System.out.println("the size of port is-------"+ports.size());
			for(int i=0;i<ports.size();i++){
				TPortInfo portinfo = ports.get(i);
				TPortInfoPk pk = new TPortInfoPk(portinfo.getPtid());
				TPortInfoDao.delete(pk);
				Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TPortInfoDao: " + pk.toString());
			}
			//--pre def mib
			List<PredefmibInfo> pdms = PredefmibInfoDao.findWhereDevidEquals(devid);
			System.out.println("the size of pdms is-------"+pdms.size());
			for(int i=0;i<pdms.size();i++){
				PredefmibInfo pdminfo = pdms.get(i);
				PredefmibInfoPk pk = new PredefmibInfoPk(pdminfo.getPdmid());
				PredefmibInfoDao.delete(pk);
				Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from PredefmibInfoDao: " + pk.toString());
			}
			//--
			TDeviceInfoPk pk = new TDeviceInfoPk(devid);
			System.out.println("the device pk i want to delete is------"+pk);
			TDeviceInfoDao.delete(pk);
			Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TDeviceInfoDao: " + pk.toString());
			
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		String gidstr = request.getParameter("gid");
		//System.out.println("delete...gid="+gidstr);
		return new ModelAndView(getPageView()+"?gid="+gidstr);
	}
	

	private Map<String, Object> parseInput(HttpServletRequest request, Map<String, Object> map){
		
		String 	devname	 		= request.getParameter("devname");
		String 	devalias	 	= request.getParameter("devalias");
		String 	devip	 		= request.getParameter("devip");
		String 	resnum	 		= request.getParameter("resnum");
		String 	providerphone	= request.getParameter("providerphone");
		String 	devtype		 	= request.getParameter("devtype");
		String 	devsubtype	 	= request.getParameter("devsubtype");
		String 	model	 		= request.getParameter("model");
		String 	manufacture	 	= request.getParameter("manufacture");
		String 	objectid	 	= request.getParameter("objectid");
		String 	snmpversion	 	= request.getParameter("snmpversion");
		String 	rcommunity	 	= request.getParameter("rcommunity");
		String 	adminName	 	= request.getParameter("adminName");
		String 	adminPhone	 	= request.getParameter("adminPhone");
		String 	devpolicy	 	= request.getParameter("devpolicy");
		String 	timeframPolicy	= request.getParameter("timeframPolicy");
		String 	devSoftwareVer	= request.getParameter("devSoftwareVer");
		String 	devSerialNum	= request.getParameter("devSerialNum");
		String 	devgroup	 	= request.getParameter("devgroup");
		String 	ramsize	 		= request.getParameter("ramsize");
		String 	nvramsize		= request.getParameter("nvramsize");
		String 	flashSize	 	= request.getParameter("flashSize");
		String 	description	 	= request.getParameter("description");
		
		DeviceInfoFormBean form = (DeviceInfoFormBean)request.getSession(true).getAttribute("devicetypeform");
		if ( form==null){
			form = new DeviceInfoFormBean();
			form.setDevname(devname);
			form.setDevalias(devalias);
			form.setDevip(devip);
			form.setResnum(resnum);
			form.setProviderphone(providerphone);
			form.setDevtype(devtype);
			form.setDevsubtype(devsubtype);
			form.setModel(model);
			form.setManufacture(manufacture);
			form.setObjectid(objectid);
			form.setSnmpversion(snmpversion);
			form.setRcommunity(rcommunity);
			form.setAdminName(adminName);
			form.setAdminPhone(adminPhone);
			form.setDevpolicy(devpolicy);
			form.setTimeframPolicy(timeframPolicy);
			form.setDevSoftwareVer(devSoftwareVer);
			form.setDevSerialNum(devSerialNum);
			form.setDevgroup(devgroup);
			form.setRamsize(nvramsize);
			form.setNvramsize(nvramsize);
//			form.setFlashfile_name(flashfile_name)
			form.setDescription(description);
			
			request.getSession().setAttribute("devicetypeform", form);
			
			
			
		}
		System.out.println("snmppoll.wss: devicetypeform="+form.getDevip());
		
		map.put("devname", devname);
		map.put("devalias", devalias);
		map.put("devip", devip);
		map.put("resnum", resnum);
		map.put("providerphone", providerphone);
		map.put("devtype", devtype);
		map.put("devsubtype", devsubtype);
		map.put("model", model);
		map.put("manufacture", manufacture);
		map.put("objectid", objectid);
		map.put("snmpversion", snmpversion);
		map.put("rcommunity", rcommunity);
		map.put("adminName", adminName);
		map.put("adminPhone", adminPhone);
		map.put("devpolicy", devpolicy);
		map.put("timeframPolicy", timeframPolicy);
		map.put("devSoftwareVer", devSoftwareVer);
		map.put("devSerialNum", devSerialNum);
		map.put("devgroup", devgroup);
		map.put("ramsize", ramsize);
		map.put("nvramsize", nvramsize);
		map.put("flashSize", flashSize);
		map.put("description", description);

		return map;
	}

	private DeviceInfoFormBean convertMap2Form(Map map){
		
		return null;
		
		
	}
	
	private DeviceInfoFormBean coveredWithSnmp(DeviceInfoFormBean form) throws AppException{
		String devip 		= form.getDevip();
		String rcommunity	= form.getRcommunity();
		String snmpversion = form.getSnmpversion();
		int snmpVer =(null==snmpversion ||"".equals(snmpversion))?0:Integer.parseInt(snmpversion);
		long snmpTimeout = 500l;
		
		String [] oids = new String [] {
										"1.3.6.1.2.1.1.3.0",				/*sysuptime, oid*/
										
										"1.3.6.1.2.1.1.2.0",				/*devicetype*/
										"1.3.6.1.2.1.1.5.0",				/*sysname*/
										"1.3.6.1.2.1.1.1.0",				/*desc*/
										"1.3.6.1.4.1.9.3.6.6.0",			/*ramsize*/
										
										"1.3.6.1.4.1.9.3.6.7.0",			/*nvramsize*/
										"1.3.6.1.2.1.47.1.1.1.1.9.1",		/*swver*/
										"1.3.6.1.4.1.9.3.6.3.0",			/*serial*/
										"1.3.6.1.2.1.2.1.0",				/*if num */
										
										"1.3.6.1.4.1.9.9.10.1.1.2.1.2", 	/*flashsize*/										
										"1.3.6.1.4.1.9.9.10.1.1.4.2.1.1.5", /*flashfile*/
										"1.3.6.1.4.1.9.9.10.1.1.4.2.1.1.2",	/*flashfile 2*/
										
										"1.3.6.1.2.1.2.2.1.1.0",			/*interface*/
										"1.3.6.1.2.1.2.2.1.2.0",			/*interface 2*/
										} ;
		
		String 	uptime 		= SnmpUtil.snmpGet(devip, rcommunity, oids[0], snmpVer, snmpTimeout);
		if(	uptime==null || "".equals(uptime)){ throw new RuntimeException("SNMP Timeout."); };
		
		System.out.println("uptime="+uptime);
		
		String devicetype 	= SnmpUtil.snmpGet(devip, rcommunity, oids[1], snmpVer, snmpTimeout);
		String sysname 		= SnmpUtil.snmpGet(devip, rcommunity, oids[2], snmpVer, snmpTimeout);
		String desc			= SnmpUtil.snmpGet(devip, rcommunity, oids[3], snmpVer, snmpTimeout);
		String ramsize 		= SnmpUtil.snmpGet(devip, rcommunity, oids[4], snmpVer, snmpTimeout);		
		String nvramsize 	= SnmpUtil.snmpGet(devip, rcommunity, oids[5], snmpVer, snmpTimeout);
		
		String swver 		= SnmpUtil.snmpGet(devip, rcommunity, oids[6], snmpVer, snmpTimeout);
		String serial	 	= SnmpUtil.snmpGet(devip, rcommunity, oids[7], snmpVer, snmpTimeout);
		String ifnum	 	= SnmpUtil.snmpGet(devip, rcommunity, oids[8], snmpVer, snmpTimeout);	
		
//		System.out.println("devicetype="+devicetype);
//		System.out.println("sysname="+sysname);
//		System.out.println("desc="+desc);
//		System.out.println("ramsize="+ramsize);
//		System.out.println("nvramsize="+nvramsize);
//		System.out.println("swver="+swver);
//		System.out.println("serial="+serial);
//		System.out.println("ifnum="+ifnum);
		
		
		
//		form.setObjectid(devicetype);
//		form.setModel(sysname);
//		form.setDescription(desc);
//		form.setRamsize(ramsize);
//		form.setNvramsize(nvramsize);
//		form.setDevSoftwareVer(swver);
		//form.set ?? serial ??
		//form.set ?? ifnum  ??
		
		//search device type from the object id
		List<DeviceTypeTree> devicetypetree = null;
		try {
			//devicetypetree = BaseInfoService.findDeviceTypeByObjectID(devicetype);
			devicetypetree = DeviceTypeTreeDao.findDeviceTypeByObjectID(devicetype);
		} catch (DeviceTypeTreeDaoException e) {
			throw new RuntimeException("Exception on Searching DeviceType from polled objectid. " + e);
		}
		System.out.println(devicetypetree);
		int num = devicetypetree.size();
		if ( num <= 0 ) {			
			//throw new RuntimeException("Exception on searching devicetype, NO objectid found in DB ; oid="+devicetype);
		}else 	if ( num > 1) {
			//throw new RuntimeException("Exception on searching devicetype, Unbiguous objectid found in DB, record number="+num+ "; oid="+devicetype);
		}
		//form.setManufacture(  devicetypetree.get(0).getMrid()+"");
		form.setManufacture(devicetypetree.get(0).getMrName() );
		form.setDevtype(	devicetypetree.get(0).getCateName() );
		form.setDevsubtype(	devicetypetree.get(0).getSubCategory() );
		form.setModel(		devicetypetree.get(0).getModel());
		form.setObjectid(	devicetypetree.get(0).getObjectid() );
		form.setDtid	(	devicetypetree.get(0).getDtid() );
		System.out.println("dtid="+devicetypetree.get(0).getDtid()+"   ..."+form.getDtid());
		form.setSnmpversion(snmpversion);
		form.setDevname(sysname);
		form.setMrid(devicetypetree.get(0).getMrid()+"");
		
		
		return form;
				
	}

	public DeviceInfoFormBean getForm() {
		return form;
	}

	public void setForm(DeviceInfoFormBean form) {
		this.form = form;
	}


	/*public BaseInfoServices getBaseInfoService() {
		return BaseInfoService;
	}


	public void setBaseInfoService(BaseInfoServices baseInfoService) {
		BaseInfoService = baseInfoService;
	}*/


	public String getPageView() {
		return pageView;
	}


	public void setPageView(String pageView) {
		this.pageView = pageView;
	}


	public DeviceTypeTreeDao getDeviceTypeTreeDao() {
		return DeviceTypeTreeDao;
	}


	public void setDeviceTypeTreeDao(DeviceTypeTreeDao deviceTypeTreeDao) {
		DeviceTypeTreeDao = deviceTypeTreeDao;
	}


	public TDeviceInfoDao getTDeviceInfoDao() {
		return TDeviceInfoDao;
	}


	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}


	public PredefmibInfoDao getPredefmibInfoDao() {
		return PredefmibInfoDao;
	}


	public void setPredefmibInfoDao(PredefmibInfoDao predefmibInfoDao) {
		PredefmibInfoDao = predefmibInfoDao;
	}



}
