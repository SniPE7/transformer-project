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
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceInfoPk;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;
import com.ibm.ncs.util.test.Flashfile;
import com.ibm.ncs.web.resourceman.bean.DeviceInfoFormBean;

public class ChooseDeviceTypeController implements Controller {

	BaseInfoServices  BaseInfoService;
	DefMibGrpDao DefMibGrpDao;
	
	public void setBaseInfoService(BaseInfoServices baseInfoServices) {
		BaseInfoService = baseInfoServices;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {


		Map<String,Object> mod = (Map<String, Object>)request.getSession().getAttribute("DevInfoMap");
		boolean newmap = false;
		if (mod==null){
			mod = new HashMap<String, Object>();		
			newmap= true;
		}
		DeviceInfoFormBean form = (DeviceInfoFormBean)mod.get("form");
		form = (form == null)?new DeviceInfoFormBean():form;
		System.out.println("ChooseDeviceTypecontroller session obj newmap="+newmap+"; mod="+mod);		
		try{ 
			List<DeviceTypeTree>  devtree =  BaseInfoService.listDeviceTypeTreeWithModelOid();
			SortList<DeviceTypeTree> sortdevtype = new SortList<DeviceTypeTree>();
			//sortdevtype.Sort(devtree, "getMrName", null);
			sortdevtype.sortmulti(devtree, "getMrName", "getCateName", "getSubCategory");
			mod.put("devicetype", devtree);

			//keep existing parameters from previous page
			String action			= request.getParameter("formAction");
			mod.put("formAction", action);
			String devid			= request.getParameter("devid");
			mod.put("devid", devid);
			String 	devname	 		= request.getParameter("devname");
			mod.put("devname", devname);
			String 	devalias	 	= request.getParameter("devalias");
			mod.put("devalias", devalias);
			String 	devip	 		= request.getParameter("devip");
					devip			=(devip!=null)?devip.trim():null;
			mod.put("devip", devip);
			String 	resnum	 		= request.getParameter("resnum");
			mod.put("resnum", resnum);
			String 	providerphone	= request.getParameter("providerphone");
			mod.put("providerphone", providerphone);
			String 	devtype		 	= request.getParameter("devtype");
			mod.put("devtype", devtype);
			String 	devsubtype	 	= request.getParameter("devsubtype");
			mod.put("devsubtype", devsubtype);
			String 	model	 		= request.getParameter("model");
			mod.put("model", model);
			String 	manufacture	 	= request.getParameter("manufacture");
			mod.put("manufacture", manufacture);
			String 	objectid	 	= request.getParameter("objectid");
			mod.put("objectid", objectid);
			String 	snmpversion	 	= request.getParameter("snmpversion");
			mod.put("snmpversion", snmpversion);
			String 	rcommunity	 	= request.getParameter("rcommunity");
			mod.put("rcommunity", rcommunity);
			String 	adminName	 	= request.getParameter("adminName");
			mod.put("adminName", adminName);
			String 	adminPhone	 	= request.getParameter("adminPhone");
			mod.put("adminPhone", adminPhone);
			String 	devpolicy	 	= request.getParameter("devpolicy");
			mod.put("devpolicy", devpolicy);
			String 	timeframPolicy	= request.getParameter("timeframPolicy");
			mod.put("timeframPolicy", timeframPolicy);
			String 	devSoftwareVer	= request.getParameter("devSoftwareVer");
			mod.put("devSoftwareVer", devSoftwareVer);
			String 	devSerialNum	= request.getParameter("devSerialNum");
			mod.put("devSerialNum",devSerialNum);
			String 	devgroup	 	= request.getParameter("devgroup");
			mod.put("devgroup", devgroup);
			String 	ramsize	 		= request.getParameter("ramsize");
			mod.put("ramsize", ramsize);
			String 	nvramsize		= request.getParameter("nvramsize");
			mod.put("nvramsize", nvramsize);
			String 	flashSize	 	= request.getParameter("flashSize");
			mod.put("flashSize", flashSize);
			String 	description	 	= request.getParameter("description");
			mod.put("description", description);
			String 	domainid	 	= request.getParameter("domainid");
			mod.put("domainid", domainid);
			
			String[] predefmibindex = request.getParameterValues("predefmibindex");
			mod.put("predefmibindex", predefmibindex);
 			
			String dtid = request.getParameter("dtid");
			mod.put("dtid", dtid);

			List<TPortInfo> portinfo = parsePortInfo(request, devid);
			mod.put("portinfo", portinfo);
			
			Flashfile[] flashfile = parseFlashFile(request);
			//mod.put("flashfile", flashfile);
	        
			Map<Long, Object> pdmInfoByMidMap = parsePdmInfo(request,devid);
			mod.put("pdmInfoByMidMap", pdmInfoByMidMap);
			
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
			form.setFlashSize(flashSize);
	//		form.setFlashfile_name(flashfile_name)
			form.setPredefmibindex(predefmibindex);

			if(dtid != null&&!dtid.equals(""))
				form.setDtid(Long.parseLong(dtid));
			form.setDescription(description);
			form.setPortinfolist(portinfo);
			form.setFlashfile(flashfile);
			form.setPdmInfoByMidMap(pdmInfoByMidMap);

			
			List<DefMibGrp> alldefmibgrp = DefMibGrpDao.findAll();
			Map<Long,String[]> pdmid2nameflag = conposePdmDsp(alldefmibgrp, predefmibindex);
			mod.put("alldefmibgrp", alldefmibgrp);		
			mod.put("pdmid2nameflag", pdmid2nameflag);
			
			mod.put("form", form);
			
			String gid = request.getParameter("gid");
			mod.put("gid", gid);
			String supid = request.getParameter("supid");
			mod.put("supid", supid);
			String level = request.getParameter("level");
			mod.put("level", level);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e);
			e.printStackTrace();
		}
//		request.getSession(true).removeAttribute("DevInfoMap");
//		request.getSession(true).setAttribute("DevInfoMap", mod);
//		System.out.println("In ChooseDeviceTypeController:\t gid=" + gid + "\t supid=" + supid + "\t level=" + level);
		return new ModelAndView("/secure/resourceman/chooseDevicetype.jsp", "model", mod);
	}
	

	private Map<Long, Object> parsePdmInfo(HttpServletRequest request, String devidstr) {
		Map<Long, Object> map = new TreeMap<Long, Object>();
		String[] pdmidstr = request.getParameterValues("pdmid");
		String[] midstr   = request.getParameterValues("mid");
		String[] pdmindex = request.getParameterValues("pdmindex");
		String[] pdmdescr  = request.getParameterValues("pdmdescr");
		if(pdmidstr==null) {return null; };
		
		long devidLong = -1;
		try {			devidLong = Long.parseLong(devidstr);		} catch (NumberFormatException e1) {		}
		try{			
			for (int i=0; i<pdmidstr.length;i++){
				Long midlong = new Long(midstr[i]);
				PredefmibInfo pdm = new PredefmibInfo();
				pdm.setDevid(devidLong);
				pdm.setMid(midlong);
				pdm.setOidindex(pdmindex[i]);
				pdm.setOidname(pdmdescr[i]);
				pdm.setPdmid(Long.parseLong(pdmidstr[i]));
				List<PredefmibInfo> tmp = null;
				try {
					tmp = (List<PredefmibInfo>) map.get(midlong);
				} catch (Exception e) {
				}
				if(tmp==null) {tmp=new ArrayList<PredefmibInfo>();}
				tmp.add(pdm);
				map.put(midlong, tmp);
				
			}
		}catch (Exception e){
		}
		return map;
	}


	private Flashfile[] parseFlashFile(HttpServletRequest request) {
		Flashfile[] flashfile = null;
		String flashfile_name[] = request.getParameterValues("flashfile_name");
		String flashfile_size[] = request.getParameterValues("flashfile_size");
		if(flashfile_name!=null&& flashfile_size!=null){
			flashfile = new Flashfile[flashfile_name.length];
			for(int i = 0; i < flashfile_name.length; i++)
		    {
		        Flashfile tempff = new Flashfile(flashfile_name[i], Long.parseLong(flashfile_size[i]));
		        flashfile[i] = tempff;
		    }
		}
		return flashfile;
	}


	private List<TPortInfo> parsePortInfo(HttpServletRequest request, String devid) {
		String[] ptidStr = request.getParameterValues("ptid");
		String[] interface_name = (String[])request.getParameterValues("interface_name");
		String[] interface_index = (String[]) request.getParameterValues("interface_index");
		String[] interface_ip = (String[])request.getParameterValues("interface_ip");
		String[] interface_mac = (String[])request.getParameterValues("interface_mac");
		List<TPortInfo> portinfo = new ArrayList<TPortInfo>();
		long devidLong = -1;
		try {			devidLong = Long.parseLong(devid);		} catch (NumberFormatException e1) {		}
		try {
			
			if(ptidStr ==null){ return null; };
			for(int i=0; i<ptidStr.length;i++){
				TPortInfo tmp = new TPortInfo();
				tmp.setDevid(devidLong);
				tmp.setPtid((ptidStr==null || ptidStr.equals(""))?0:Long.parseLong(ptidStr[i]));
				tmp.setIfdescr(interface_name[i]);
				tmp.setIfindex(Long.parseLong(interface_index[i]));
				tmp.setIfip(interface_ip[i]);
				tmp.setIfmac(interface_mac[i]);
				portinfo.add(tmp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return portinfo;
	}
	
	
	private Map<Long, String[]> conposePdmDsp(List<DefMibGrp> alldefmibgrp, String[] predefmibindex) {
		Map<Long, String[]> mapflag =  new TreeMap<Long, String[]>();
		for (DefMibGrp dto:alldefmibgrp){
			mapflag.put(dto.getMid(), new String[]{dto.getName(),"0", dto.getIndexvar(), dto.getDescrvar()});
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

	public BaseInfoServices getBaseInfoService() {
		return BaseInfoService;
	}

	public DefMibGrpDao getDefMibGrpDao() {
		return DefMibGrpDao;
	}

	public void setDefMibGrpDao(DefMibGrpDao defMibGrpDao) {
		DefMibGrpDao = defMibGrpDao;
	}



}
