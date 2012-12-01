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

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dao.PredefmibInfoDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.PredefmibInfoPk;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceInfoPk;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.dto.TPortInfoPk;
import com.ibm.ncs.model.exceptions.DefMibGrpDaoException;
import com.ibm.ncs.model.exceptions.SequenceNMDaoException;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.exceptions.TDeviceTypeInitDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.service.ResourceManServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.IPAddr;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.test.Flashfile;
import com.ibm.ncs.web.resourceman.bean.DeviceInfoFormBean;

public class NewDeviceInfoController implements Controller {

	DeviceInfoFormBean form;
//	BaseInfoServices  	BaseInfoService;
//	DeviceTypeTreeDao  DeviceTypeTreeDao;	
	GenPkNumber genPkNumber;
	TPortInfoDao TPortInfoDao;
	String pageView ;
	String fallback;
//	String message = "";
	
	TDeviceInfoDao TDeviceInfoDao;
	PredefmibInfoDao PredefmibInfoDao;
	VerifyIPAddressRange VerifyIPAddressRange;
	DefMibGrpDao DefMibGrpDao;

	public TDeviceInfoDao getTDeviceInfoDao() {
		return TDeviceInfoDao;
	}



	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}



	public DeviceInfoFormBean getForm() {
		return form;
	}



	public void setForm(DeviceInfoFormBean form) {
		this.form = form;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		String gid = request.getParameter("gid");

		String message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println("\t\t*&***Gid in NewDeviceInfoController is:" + gid);
		try {
			model = parseInput(request, model);
			
			model.put("form", form);
			//verify ipaddress within the parent
			boolean allowed = verifyRange(request);
			if (allowed ==false) {
				String message2 = "IP地址不在管理IP地址段范围内";
				 message  = "error.dev.info.iprange.false";
				model.put("message2", message2);
				model.put("message", message);
	//	System.out.println(model);
				return new ModelAndView("/secure/resourceman/adddeviceInfo.jsp", "model", model );
			}
			String action = request.getParameter("formAction");
			if (action!=null && action.equals("addnew")){
				//DAO insert
				System.out.println("insert action ");
				//TODO : convert  DeviceInfoFormBean into  TDeviceInfo; and then insert into TDeviceInfo 
				TDeviceInfo deviceInfoDto = new TDeviceInfo();
				
				long devid = genPkNumber.getID();
				deviceInfoDto.setDevid(devid);
				deviceInfoDto.setSysname((String) model.get("devname"));
				deviceInfoDto.setSysnamealias((String) model.get("devalias"));
				String ipaddr =(String) model.get("devip");
				deviceInfoDto.setDevip(ipaddr);
				long ipdecode = IPAddr.encode(ipaddr);
				deviceInfoDto.setIpdecode(ipdecode);
				deviceInfoDto.setRsno((String) model.get("resnum"));
//				deviceInfoDto.set((String) model.get("providerphone"));
				deviceInfoDto.setDomainid(Long.parseLong((String)model.get("domainid")));
				//get device type id:
				String dtid =  request.getParameter("dtid");
				if(dtid == null || dtid.equals(""))
					dtid = "-1";
				deviceInfoDto.setDtid(Long.parseLong(dtid));
				//deviceInfoDto.setSysname((String) model.get("devsubtype"));
				
				//deviceInfoDto.set((String) model.get("model"));
				
				//get manufacture ID:
				//deviceInfoDto.setSysname((String) model.get("manufacture"));
				
//				deviceInfoDto.set((String) model.get("objectid"));
				
				deviceInfoDto.setSnmpversion((String) model.get("snmpversion"));
				deviceInfoDto.setRcommunity((String) model.get("rcommunity"));
				deviceInfoDto.setAdmin((String) model.get("adminName"));
				deviceInfoDto.setPhone((String) model.get("adminPhone"));
//				deviceInfoDto.set((String) model.get("devpolicy"));
//				deviceInfoDto.set((String) model.get("timeframPolicy"));
				deviceInfoDto.setSwversion((String) model.get("devSoftwareVer"));
				deviceInfoDto.setSerialid((String) model.get("devSerialNum"));
//				deviceInfoDto.set((String) model.get("devgroup"));
				
				String ramsizeTmp = (String) model.get("ramsize");
				String nvramsize = (String) model.get("nvramsize");
				String flashSize = (String) model.get("flashSize");
				if(ramsizeTmp == null || ramsizeTmp.equals(""))
					ramsizeTmp = "-1";
				if(nvramsize == null || nvramsize.equals(""))
					nvramsize = "-1";
				if(flashSize == null || flashSize.equals(""))
					flashSize = "-1";
				deviceInfoDto.setRamsize(Long.parseLong(ramsizeTmp.trim()));
				deviceInfoDto.setNvramsize(Long.parseLong(nvramsize.trim()));				
				deviceInfoDto.setFlashsize(Long.parseLong(flashSize.trim()));
				try {	deviceInfoDto.setMrid(Long.parseLong(request.getParameter("mrid")));		} catch (Exception e) {		}
				deviceInfoDto.setDescription((String) model.get("description"));
				
				try {
					//flashfile to db
					String[] flashfilename = request.getParameterValues("flashfile_name");
					String[] flashfilesize = request.getParameterValues("flashfile_size");
					String ffname2db = convertffname(flashfilename);
					String ffsize2db = convertffsize(flashfilesize);
					if(ffname2db != null && ffname2db.length()>=4000){
						ffname2db = ffname2db.substring(0,3999);
						message += "The length of flashFileName  is over 4000, cannot write to db over 4000.";
						Log4jInit.ncsLog.info(this.getClass().getName() + "The length of flashFileName  is over 4000, cannot write to db over 4000.");
					}
					if(ffsize2db != null && ffsize2db.length()>=1500 ){
						ffsize2db = ffsize2db.substring(0, 1499);
						message += "The Length of flashFileSize is over 1500...cannot write to db over 1500.";
						Log4jInit.ncsLog.info(this.getClass().getName() + "The Length of flashFileSize is over 1500...cannot write to db over 1500.");
					}
					deviceInfoDto.setFlashfilename(ffname2db);
					deviceInfoDto.setFlashfilesize(ffsize2db);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println("\t\t*****dto in NewDeviceInfoController is:\n\t\t\t" + deviceInfoDto);
				TDeviceInfoPk pkTemp;
				try {
					pkTemp = TDeviceInfoDao.insert(deviceInfoDto);
				} catch (DataIntegrityViolationException  e) {					
					e.printStackTrace();
					throw new RuntimeException("TDeviceInfo Insert Error! DataIntegrityViolation.");
				}catch (Exception e){
					throw new RuntimeException("TDeviceInfo Insert Error!");
				}
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TDeviceInfoDao: " + deviceInfoDto.toString());
				
				if(pkTemp != null)
					System.out.print("\t\n\tTDeviceInfoPk is:" + pkTemp);
                
				
				/*for(int i=0;i<5;i++){
					Long ptid = genPkNumber.getID();
				
				TPortInfo portinfo = new TPortInfo();
				portinfo.setPtid(ptid);
				portinfo.setDevid(devid);
				portinfo.setIfdescr(request.getParameter("interface_name"));
				portinfo.setIfindex(Long.parseLong(request.getParameter("interface_index")));
				portinfo.setIfip(request.getParameter("interface_ip"));
				portinfo.setIfmac(request.getParameter("interface_mac"));
				
				//TPortInfoDao.insert(portinfo);
				
				System.out.println("the portinfo inserted are-----------"+portinfo);
				}*/
				//TPortInfo portinfo = new TPortInfo();
				String[] ptidStr = request.getParameterValues("ptid");
				String[] interface_name = (String[])request.getParameterValues("interface_name");
				String[] interface_index = (String[]) request.getParameterValues("interface_index");
				String[] interface_ip = (String[])request.getParameterValues("interface_ip");
				String[] interface_mac = (String[])request.getParameterValues("interface_mac");
				
				if(ptidStr != null){
					 
						for(int i=0;i<ptidStr.length;i++){ 
							Long ptid = genPkNumber.getID();
					
						TPortInfo portinfo = new TPortInfo(); 
						portinfo.setPtid(ptid); 
						portinfo.setDevid(devid); 
						portinfo.setIfdescr(interface_name[i]); 
						portinfo.setIfindex(Long.parseLong(interface_index[i])); 
						portinfo.setIfip(interface_ip[i]); 
						portinfo.setIfmac(interface_mac[i]); 
						
						try {
							TPortInfoDao.insert( portinfo);
						} catch (Exception e) {							
							e.printStackTrace();
							message="TPortinfo Insert Error!";
							Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to portinfo= " + portinfo);
							throw new RuntimeException("TportInfo insert error.");
						}
						System.out.println("after insert the port info is-----"+portinfo);
						}
					}
				
				String[] pdmidstr = request.getParameterValues("pdmid");
				String[] midstr   = request.getParameterValues("mid");
				String[] pdmindex = request.getParameterValues("pdmindex");
				String[] pdmdescr  = request.getParameterValues("pdmdescr");
				try {
					if(pdmidstr != null){
						// if there are any pdminfo info changes, replace all with new list (polling result list );
						boolean replaceall = false;
						for(int x=0;x<pdmidstr.length;x++){ 
							if (pdmidstr[x]==null||pdmidstr[x].equals("")||pdmidstr[x].equals("-1")){ 								
								replaceall = true; 
								break; 
							} 
						} 
						if (replaceall){ 
							PredefmibInfoDao.deleteByDevid(devid); 
							
							for(int i=0;i<pdmidstr.length;i++){ 
								Long pdmid = genPkNumber.getID();
						
								PredefmibInfo pdminfodto =  new PredefmibInfo(); 
								pdminfodto.setPdmid(pdmid); 
								pdminfodto.setDevid(devid); 
								pdminfodto.setMid(Long.parseLong(midstr[i])); 
								pdminfodto.setOidindex(pdmindex[i]); 
								pdminfodto.setOidname(pdmdescr[i]);  
							
								try {
									PredefmibInfoDao.insert( pdminfodto);
								} catch (Exception e) {
									e.printStackTrace();
									message="PredefmibInfoDao update Error! pdminfodto="+pdminfodto;
									throw new RuntimeException(message);
								}
							System.out.println("after insert the pdminfo info is-----"+pdminfodto);
							}
						}else{
						//otherwise update row by row.
						for(int i=0;i<pdmidstr.length;i++){
							Long pdmid = Long.parseLong(pdmidstr[i]);
							System.out.println("pdmid is ="+pdmid);
						
						//List<PredefmibInfo> pdminfos = PredefmibInfoDao.findWhereDevidEquals(devid);
						
						PredefmibInfoPk pk = new PredefmibInfoPk(pdmid);
						PredefmibInfo pdminfodto = PredefmibInfoDao.findByPrimaryKey(pdmid);
						pdminfodto.setDevid(devid);
						pdminfodto.setMid(Long.parseLong(midstr[i]));
						pdminfodto.setOidindex(pdmindex[i]);
						pdminfodto.setOidname(pdmdescr[i]);
						
						try {
							PredefmibInfoDao.update(pk, pdminfodto);
						} catch (Exception e) {
							e.printStackTrace();
							message="PredefmibInfoDao Insert Error!";
							throw new RuntimeException("PreDefMibInfo Insert Error!");
						}
						Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to PredefmibInfoDao: pk=" +pk.toString() + "\tdto= " +pdminfodto.toString());
						
						System.out.println("after updating the pdminfodto info is-----"+pdminfodto);
						}
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					Log4jInit.ncsLog.info(this.getClass().getName() + "Error on write to db for pdminfodto " );
				}
				
			}
			
			//return new ModelAndView(getPageView()+"?gid="+gid	);
		} catch(NumberFormatException ne){
			message = "input.parseNumError";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ne.getMessage());
			model.put("message", message);
			ne.printStackTrace();
		}catch(RuntimeException re){
			message = "resourceman.deviceinfo.insert.error";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" +message+" ;"+ re.getMessage());
			model.put("message", message);
			re.printStackTrace();	
		}catch (Exception e) {
			message = "policy.new.error";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			model.put("message", message);
			e.printStackTrace();
		}
		model.put("message", message);
		if (message!=null&&!message.equals("")){
			return new ModelAndView(getFallback()+"?gid="+gid,"model",model);
		}else {
			return new ModelAndView(getPageView()+"?gid="+gid);
		}
	}

	
	private String convertffsize(String[] flashfilesize) {
		if(flashfilesize==null){return null;}
		StringBuffer ret = new StringBuffer("<m_FlashFile Size ");
		for (int i=0; i<flashfilesize.length;i++){
			ret.append("Attr"+i+"=\""+flashfilesize[i]+"\" ");
		}
		ret.append(" />");
		return ret.toString();
	}

	private String convertffname(String[] flashfilename) {
		if(flashfilename==null){return null;}
		StringBuffer ret = new StringBuffer("<m_FlashFile Name ");
		for (int i=0; i<flashfilename.length;i++){
			ret.append("Attr"+i+"=\""+flashfilename[i]+"\" ");
		}
		ret.append(" />");
		return ret.toString();	
	}
	private Map<String, Object> parseInput(HttpServletRequest request, Map<String, Object> map){
		
		String gidstr 			= request.getParameter("gid");
		String supid			= request.getParameter("supid");
		String devid 			= request.getParameter("devid");
		String 	devname	 		= request.getParameter("devname");
		String 	devalias	 	= request.getParameter("devalias");
		String 	devip	 		= request.getParameter("devip");
				devip			= (devip!=null)?devip.trim():null; 
		String 	resnum	 		= request.getParameter("resnum");
		String  domainid        = request.getParameter("domainid");
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

		
		String 	mrid	 	= request.getParameter("mrid");
		String 	dtid	 	= request.getParameter("dtid");
		List<TPortInfo> portinfo = parsePortInfo(request, devid);
		map.put("portinfo", portinfo);
		
		Flashfile[] flashfile = parseFlashFile(request);
		//map.put("flashfile", flashfile);
        
		Map<Long, Object> pdmInfoByMidMap = parsePdmInfo(request,devid);
		map.put("pdmInfoByMidMap", pdmInfoByMidMap);
		
		//DeviceInfoFormBean form = (DeviceInfoFormBean)request.getSession(true).getAttribute("devicetypeform");
		if ( form==null){
			form = new DeviceInfoFormBean();
		}
			try {
				form.setGid(gidstr);
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
//			form.setFlashfile_name(flashfile_name)
				form.setDescription(description);

				form.setMrid(mrid);
				if(dtid != null&& !dtid.equals(""))
				form.setDtid(Long.parseLong(dtid));
				form.setPortinfolist(portinfo);
				form.setFlashfile(flashfile);
				form.setPdmInfoByMidMap(pdmInfoByMidMap);
				request.getSession().setAttribute("devicetypeform", form);
			} catch (NumberFormatException e) {
				String message = "input.parseNumError";
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				map.put("message", message);
				e.printStackTrace();
			}
			
			
			
	
		System.out.println("snmppoll.wss: devicetypeform="+form.getDevip());
		
		map.put("gid", gidstr);
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

		map.put("mrid", mrid);
		map.put("dtid", dtid);
		try {
			List<DefMibGrp> alldefmibgrp = DefMibGrpDao.findAll();
			Map<Long,String[]> pdmid2nameflag = conposePdmDsp(alldefmibgrp, predefmibindex);
			map.put("alldefmibgrp", alldefmibgrp);		
			map.put("pdmid2nameflag", pdmid2nameflag);
		} catch (DefMibGrpDaoException e) {
			e.printStackTrace();
		}
		
		return map;
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
	
	private boolean verifyRange(HttpServletRequest request) {
		boolean allowed = false;
		try {
		String gidstr = request.getParameter("gid");
//		String supgidstr = request.getParameter("supid");
		String ipstr = request.getParameter("devip");
		String mask = "255.255.255.255";
		long gid = Long.parseLong(gidstr);
//		if(supgidstr==null||supgidstr.equals("")){return allowed = true;}
//		long supgid = Long.parseLong(supgidstr);
		allowed = VerifyIPAddressRange.verifyRange(gid, new String[]{ ipstr}, new String[] {mask});
		System.out.println("saving device info verify... allowed="+allowed);
		}
		catch(Exception e){
			e.printStackTrace();
			//return allowed = false;
		}
		return allowed;
	}

	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}

	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}



	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public String getPageView() {
		return pageView;
	}



	public TPortInfoDao getTPortInfoDao() {
		return TPortInfoDao;
	}



	public void setTPortInfoDao(TPortInfoDao portInfoDao) {
		TPortInfoDao = portInfoDao;
	}



//	public String getMessage() {
//		return message;
//	}
//
//
//
//	public void setMessage(String message) {
//		this.message = message;
//	}



	public PredefmibInfoDao getPredefmibInfoDao() {
		return PredefmibInfoDao;
	}



	public void setPredefmibInfoDao(PredefmibInfoDao predefmibInfoDao) {
		PredefmibInfoDao = predefmibInfoDao;
	}



	public String getFallback() {
		return fallback;
	}



	public void setFallback(String fallback) {
		this.fallback = fallback;
	}



	public VerifyIPAddressRange getVerifyIPAddressRange() {
		return VerifyIPAddressRange;
	}



	public void setVerifyIPAddressRange(VerifyIPAddressRange verifyIPAddressRange) {
		VerifyIPAddressRange = verifyIPAddressRange;
	}



	public DefMibGrpDao getDefMibGrpDao() {
		return DefMibGrpDao;
	}



	public void setDefMibGrpDao(DefMibGrpDao defMibGrpDao) {
		DefMibGrpDao = defMibGrpDao;
	}



}
