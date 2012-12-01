package com.ibm.ncs.web.resourceman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dao.PredefmibInfoDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.exceptions.DeviceTypeTreeDaoException;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.AppException;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SnmpUtil;
import com.ibm.ncs.util.ectest.Detector;
import com.ibm.ncs.util.test.Device;
import com.ibm.ncs.util.test.Flashfile;
import com.ibm.ncs.util.test.Interface;
import com.ibm.ncs.web.resourceman.bean.DeviceInfoFormBean;

public class SnmpPollController implements Controller {

	DeviceInfoFormBean form;
	BaseInfoServices  	BaseInfoService;
	DeviceTypeTreeDao  DeviceTypeTreeDao;
	DefMibGrpDao DefMibGrpDao;
	PredefmibInfoDao PredefmibInfoDao;
	String addView;
	String editView;
//	String message="";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

	//parse request and get snmp values, forward to newdeviceinfo page

		String message = "";
//		Map<String, Object> model = new HashMap<String, Object>();
		Map<String,Object> model = (Map<String, Object>)request.getSession().getAttribute("DevInfoMap");
		boolean newmap = false;
		if (model==null){
			model = new HashMap<String, Object>();		
			newmap= true;
		}
		form = (DeviceInfoFormBean)model.get("form");
		form = (form == null)?new DeviceInfoFormBean():form;
	//	System.out.println("ChooseDeviceTypecontroller session obj newmap="+newmap+"; model="+model);	
		try{
			String gid = request.getParameter("gid");
			model = parseInput(request, model);
	//		model = coveredWithSnmp(model);
	//
	//		form = parseInput(request, form);
			
	//		DeviceInfoFormBean form = (DeviceInfoFormBean)request.getSession(true).getAttribute("devicetypeform");
	//		System.out.println("snmppoll.wss: devicetypeform="+form.getDevip());
			
			form = coveredWithSnmp(form);
			String devidStr = form.getDevid();
			long devid;
			try {
				devid = Long.parseLong(devidStr.trim());
			} catch (Exception e) {
				devid=-1l;
			}
			 List<DefMibGrp> alldefmibgrp = DefMibGrpDao.findAll();
			 //List<PredefmibInfo> listpdmInfo = PredefmibInfoDao.findWhereDevidEquals(devid);
			 String [] predefmibindex = request.getParameterValues("predefmibindex");
			 Map<Long,String[]> pdmid2nameflag = conposePdmDsp(alldefmibgrp, predefmibindex);
			model.put("portinfo", form.getPortinfolist());
			model.put("flashfilel", form.getFlashfile());
			model.put("pdmInfoByMidMap", form.getPdmInfoByMidMap());	
			model.put("alldefmibgrp", alldefmibgrp);		
			model.put("pdmid2nameflag", pdmid2nameflag);
			model.put("form", form);
			model.put("gid", gid);
		}catch(RuntimeException re){
			message="resourceman.poll.fail";
				//"<script language='javascript'>Alert('POLL Failure! please check input or try again later!');</script>";
			model.put("message", message);
			Log4jInit.ncsLog.error(this.getClass().getName() + " Poll failure occured:\n" + re);
		}catch(AppException ae){
			message="resourceman.poll.fail";
				//"<script language='javascript'>Alert('POLL Failure! please check input or try again later!');</script>";
			model.put("message", message);
			Log4jInit.ncsLog.error(this.getClass().getName() + " Poll failure occured:\n" + ae);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e);
			e.printStackTrace();
		}
		String action = request.getParameter("formAction");
		System.out.println("formAction"+action);
		if(null!=action&&"add".equals(action)){
			return new ModelAndView(getAddView(), "model",model);
		}else {
			return new ModelAndView(getEditView(), "model",model);
		}
	}
	

	public DefMibGrpDao getDefMibGrpDao() {
		return DefMibGrpDao;
	}


	public void setDefMibGrpDao(DefMibGrpDao defMibGrpDao) {
		DefMibGrpDao = defMibGrpDao;
	}


	public PredefmibInfoDao getPredefmibInfoDao() {
		return PredefmibInfoDao;
	}


	public void setPredefmibInfoDao(PredefmibInfoDao predefmibInfoDao) {
		PredefmibInfoDao = predefmibInfoDao;
	}


	private Map<String, Object> parseInput(HttpServletRequest request, Map<String, Object> map){
		
		String devid 			= request.getParameter("devid");
		String 	devname	 		= request.getParameter("devname");
		String 	devalias	 	= request.getParameter("devalias");
		String 	devip	 		= request.getParameter("devip");
				devip			= (devip!=null)?devip.trim():null;
		String 	resnum	 		= request.getParameter("resnum");
		String domainid			= request.getParameter("domainid");
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
		String[] predefmibindex = request.getParameterValues("predefmibindex");
		
		//DeviceInfoFormBean form = (DeviceInfoFormBean)request.getSession(true).getAttribute("devicetypeform");
		if ( form==null){
			form = new DeviceInfoFormBean();
		}
			form.setDevid(devid);
			form.setDevname(devname);
			form.setDevalias(devalias);
			form.setDevip(devip);
			form.setResnum(resnum);
			form.setDomainid(domainid);
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
			form.setPredefmibindex(predefmibindex);
			
			//request.getSession().setAttribute("devicetypeform", form);
			
			
			
	
		//System.out.println("snmppoll.wss: devicetypeform="+form.getDevip());
		
		map.put("devid", devid);
		map.put("devname", devname);
		map.put("devalias", devalias);
		map.put("devip", devip);
		map.put("resnum", resnum);
		map.put("domainid", domainid);
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
		map.put("predefmibindex", predefmibindex);

		return map;
	}

	private DeviceInfoFormBean convertMap2Form(Map map){
		
		return null;
		
		
	}
	/**
	 *  polling and prefill	 
	 */
	private DeviceInfoFormBean coveredWithSnmp(DeviceInfoFormBean form) throws AppException, RuntimeException{
		String devip 		= form.getDevip();
		String rcommunity	= form.getRcommunity();
		String snmpversion = form.getSnmpversion();
		int snmpVer =(null==snmpversion ||"".equals(snmpversion))?0:Integer.parseInt(snmpversion);
		
		long snmpTimeout = 5000l;
		
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
		
		String 	uptime 		= SnmpUtil.snmpGet(devip, rcommunity, oids[0], snmpVer-1, snmpTimeout);
		System.out.println("uptime="+uptime);
		System.out.println("devip="+devip+" rcommunity="+rcommunity+"oids[0]="+ oids[0]+"snmpver="+ snmpVer+"snmptimeout="+ snmpTimeout);
		if(	uptime==null || "".equals(uptime)){ throw new RuntimeException("SNMP Timeout."); };
		
		System.out.println("uptime="+uptime);
		
		String devicetype 	= SnmpUtil.snmpGet(devip, rcommunity, oids[1], snmpVer-1, snmpTimeout);
		String sysname 		= SnmpUtil.snmpGet(devip, rcommunity, oids[2], snmpVer-1, snmpTimeout);
		String desc			= SnmpUtil.snmpGet(devip, rcommunity, oids[3], snmpVer-1, snmpTimeout);
		String ramsize 		= SnmpUtil.snmpGet(devip, rcommunity, oids[4], snmpVer-1, snmpTimeout);		
		String nvramsize 	= SnmpUtil.snmpGet(devip, rcommunity, oids[5], snmpVer-1, snmpTimeout);
		
		String swver 		= SnmpUtil.snmpGet(devip, rcommunity, oids[6], snmpVer-1, snmpTimeout);
		String serial	 	= SnmpUtil.snmpGet(devip, rcommunity, oids[7], snmpVer-1, snmpTimeout);
		String ifnum	 	= SnmpUtil.snmpGet(devip, rcommunity, oids[8], snmpVer-1, snmpTimeout);	
		
//		System.out.println("devicetype="+devicetype);
//		System.out.println("sysname="+sysname);
//		System.out.println("desc="+desc);
//		System.out.println("ramsize="+ramsize);
//		System.out.println("nvramsize="+nvramsize);
//		System.out.println("swver="+swver);
//		System.out.println("serial="+serial);
//		System.out.println("ifnum="+ifnum);
		
		Detector dtr =  new Detector();
        
        Device dev = new Device();
        dev.setIp(devip);
        dev.setRCommunity(rcommunity);
        dev.setSnmpversion(snmpVer);
        ArrayList lst = new ArrayList();        
		Device newdev = null;
		try {newdev = dtr.fillDeviceBySNMP(dev, lst);} catch (Exception e1) {e1.printStackTrace();}		
		Interface[] ifs=null;
		TPortInfo tmp = null;
		List<TPortInfo> portinfo = new ArrayList<TPortInfo>();
		try {
			ifs = newdev.getInterfaces();
			for (int i=0; i<ifs.length;i++){
				tmp = new TPortInfo();
				tmp.setPtid(-1);
				tmp.setIfindex(	ifs[i].getIndex());
				tmp.setIfdescr( ifs[i].getName()); 
				tmp.setIfip(ifs[i].getIp());
				tmp.setIfmac(ifs[i].getMac());
				portinfo.add(tmp);			
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Flashfile[] flashfile=null;
		try {
			flashfile = newdev.getFlashfiles();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		List<PredefmibInfo> pdminfolst = new ArrayList();
		Map<Long, Object> pdmInfoByMidMap = new TreeMap<Long, Object>();
		String[] predefmibindex = form.getPredefmibindex();
		String devidstr = form.getDevid();
		try {
			if( predefmibindex!=null){
			long devid;
			try {
				devid = Long.parseLong(devidstr);
			} catch (Exception e) {
				devid=-1l;
			}
			for (int i = 0; i < predefmibindex.length; i++) {
				long mid=-1l;
				String midstr ="";
				String name="";
				String indexoid="";
				String descroid="";
				try {
					String[] sss = predefmibindex[i].split("\\|");
					midstr 		= sss[0];
					name		= sss[1];
					indexoid	= sss[2];
					descroid	= sss[3];
				mid=Long.parseLong(midstr);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					List tmp1 = SnmpUtil.snmpTable(devip, rcommunity, indexoid,	snmpVer - 1, snmpTimeout);
					List tmp2 = SnmpUtil.snmpTable(devip, rcommunity, descroid,	snmpVer - 1, snmpTimeout);
					List tmp3 = new ArrayList();
					for (int k = 0; k < tmp1.size(); k++) {
						String s1 = (String) tmp1.get(k);
						String s2 = (String) tmp2.get(k);
						PredefmibInfo dto = new PredefmibInfo();
						dto.setPdmid(-1l);
						dto.setDevid(devid);
						dto.setMid(mid);
						dto.setOidindex(s1);
						dto.setOidname(s2);
						tmp3.add(dto);
					}
					pdmInfoByMidMap.put(mid, tmp3);
System.out.println("tmp1="+tmp1);
System.out.println("tmp2="+tmp2);
System.out.println("tmp3="+tmp3);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
System.out.println("pdmInfoByMidMap="+pdmInfoByMidMap);
		form.setObjectid(devicetype);
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
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e);
			
			throw new RuntimeException("Exception on Searching DeviceType from polled objectid. " + e);
		}
		System.out.println(devicetypetree);
		int num = devicetypetree.size();
		if ( num <= 0 ) {			
			String message="Cannot find Objectid from DB.";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + message);
			//throw new RuntimeException("Exception on searching devicetype, NO objectid found in DB ; oid="+devicetype);
		}else 	if ( num > 1) {
			String message="Unbiguous Objectid found in DB.";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + message);
			//throw new RuntimeException("Exception on searching devicetype, Unbiguous objectid found in DB, record number="+num+ "; oid="+devicetype);
		}
		
		//form.setManufacture(  devicetypetree.get(0).getMrid()+"");
		try {			form.setManufacture(devicetypetree.get(0).getMrName() );		} catch (Exception e) {	form.setManufacture(null);	}
		try {			form.setDevtype(	devicetypetree.get(0).getCateName() );		} catch (Exception e) {	form.setDevtype(null);	}
		try {			form.setDevsubtype(	devicetypetree.get(0).getSubCategory() );		} catch (Exception e) {	form.setDevsubtype(null);	}
		try {			form.setModel(		devicetypetree.get(0).getModel());		} catch (Exception e) {	form.setModel(null);	}
		//try {			form.setObjectid(	devicetype );		} catch (Exception e) {		}
		try {			form.setDtid	(	devicetypetree.get(0).getDtid() );		} catch (Exception e) {	form.setDtid(-1);	}
		form.setDescription(desc);
		//System.out.println("dtid="+devicetypetree.get(0).getDtid()+"   ..."+form.getDtid());
		form.setSnmpversion(snmpversion);
		form.setDevname(sysname);
		try {			form.setMrid(devicetypetree.get(0).getMrid()+"");		} catch (Exception e) {	form.setMrid(null);	}
		
		
		form.setDevSoftwareVer(newdev.getSoftwareversion());
		form.setDevSerialNum(newdev.getSerial());
		form.setRamsize(newdev.getRamsize()+"");
		form.setNvramsize(newdev.getNvramsize()+"");
		form.setFlashSize(newdev.getFlashsize()+"");
		
		form.setDevice(newdev);
		form.setPortinfolist(portinfo);
		form.setFlashfile(flashfile);
		
		form.setPdmInfoByMidMap(pdmInfoByMidMap);
		
		return form;
				
	}
	private Map<Long, String[]> conposePdmDsp(List<DefMibGrp> alldefmibgrp, String[] predefmibindex) {
		Map<Long, String[]> mapflag =  new TreeMap<Long, String[]>();
		for (DefMibGrp dto:alldefmibgrp){
			mapflag.put(dto.getMid(), new String[]{dto.getName(),"0",dto.getIndexvar(),dto.getDescrvar()});
		}
		if(predefmibindex!=null){
		for(int i=0;i<predefmibindex.length;i++){
			StringTokenizer stk = new StringTokenizer(predefmibindex[i], "|");
			String s1 = stk.nextToken();//mid
//			String s2 = stk.nextToken();
//			String s3 = stk.nextToken();
//			String s4 = stk.nextToken();
			long midused = Long.parseLong(s1);
			String [] tmp = mapflag.get(midused);
			tmp[1] = "1";
			mapflag.put(midused, tmp);
		}
	}
		System.out.println("mapflag="+mapflag);
	return mapflag;
}
	
	private Map<Long, Object> getPdmInfoMap(List<PredefmibInfo> listpdmInfo) {
		Map<Long, Object> map = new TreeMap<Long, Object>();

		try {
			 for (PredefmibInfo dto: listpdmInfo){
				 long mid = dto.getMid();
				 List<PredefmibInfo> templst = null;
				 if(map.containsKey(mid)){
					 templst = (List)map.get(mid);
				 }else {
					 templst = new ArrayList<PredefmibInfo>();
				 }
				 templst.add(dto);
				 map.put(mid, templst);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("map="+map);
	return map;
}

	public DeviceInfoFormBean getForm() {
		return form;
	}

	public void setForm(DeviceInfoFormBean form) {
		this.form = form;
	}


	public BaseInfoServices getBaseInfoService() {
		return BaseInfoService;
	}


	public void setBaseInfoService(BaseInfoServices baseInfoService) {
		BaseInfoService = baseInfoService;
	}





	public String getAddView() {
		return addView;
	}


	public void setAddView(String addView) {
		this.addView = addView;
	}


	public String getEditView() {
		return editView;
	}


	public void setEditView(String editView) {
		this.editView = editView;
	}


	public DeviceTypeTreeDao getDeviceTypeTreeDao() {
		return DeviceTypeTreeDao;
	}


	public void setDeviceTypeTreeDao(DeviceTypeTreeDao deviceTypeTreeDao) {
		DeviceTypeTreeDao = deviceTypeTreeDao;
	}



}
