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
import org.springframework.web.servlet.view.RedirectView;

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dao.PredefmibInfoDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.PredefmibInfoPk;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceInfoPk;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.dto.TPortInfoPk;
import com.ibm.ncs.model.exceptions.DefMibGrpDaoException;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.IPAddr;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.test.Flashfile;
import com.ibm.ncs.web.resourceman.bean.DeviceInfoFormBean;

public class SavingDeviceInfoController implements Controller {

	DeviceInfoFormBean form;
	TGrpNetDao TGrpNetDao;
	TDeviceInfoDao TDeviceInfoDao;
	TListIpDao TListIpDao;
	TPortInfoDao TPortInfoDao;
	private GenPkNumber genPkNumber;
	String pageView;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	PredefmibInfoDao PredefmibInfoDao;
	VerifyIPAddressRange VerifyIPAddressRange ;
	DefMibGrpDao DefMibGrpDao;
	
	public PredefmibInfoDao getPredefmibInfoDao() {
		return PredefmibInfoDao;
	}

	public void setPredefmibInfoDao(PredefmibInfoDao predefmibInfoDao) {
		PredefmibInfoDao = predefmibInfoDao;
	}

	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}

	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}

	public TPortInfoDao getTPortInfoDao() {
		return TPortInfoDao;
	}

	public void setTPortInfoDao(TPortInfoDao portInfoDao) {
		TPortInfoDao = portInfoDao;
	}

	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}

	public void setTListIpDao(TListIpDao listIpDao) {
		TListIpDao = listIpDao;
	}

	public void setTGrpNetDao(TGrpNetDao grpNetDao) {
		TGrpNetDao = grpNetDao;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model = parseInput(request, model);
		model = parsePortinfo(request, model);
		model = parsePDMinfo(request, model);
		String gidstr = request.getParameter("gid");
		String supgidstr = request.getParameter("supgid");
		String ipstr = request.getParameter("devip");
		String message = "";
		try {
			
			model.put("form", form);

			//verify ipaddress within the parent
			boolean allowed = verifyRange(request);
			if (allowed ==false) {
				String message2 = "IP地址不在管理IP地址段范围内";
				message  = "error.dev.info.iprange.false";
				model.put("message2", message2);
				model.put("message", message);
	System.out.println(model);
				return new ModelAndView("/secure/resourceman/editdeviceInfo.jsp", "model", model );
			}
			
			String action = request.getParameter("formAction");

			if ("save".equals(action)){
				
				System.out.println("action...save...gid="+gidstr+" " );
				//
				TDeviceInfo deviceInfoDto = new TDeviceInfo();
				String devidstr = (String)model.get("devid");
				System.out.println("action...save... devid="+devidstr );
				long devid = Long.parseLong(devidstr);
				deviceInfoDto.setDevid(devid);
				try{	long mrid = Long.parseLong(request.getParameter("mrid"));		
				deviceInfoDto.setMrid(mrid);} catch (Exception e) {		}
				
				//TPortInfo portinfo = new TPortInfo();
				String[] ptidStr = request.getParameterValues("ptid");
				String[] interface_name = (String[])request.getParameterValues("interface_name");
				String[] interface_index = (String[]) request.getParameterValues("interface_index");
				String[] interface_ip = (String[])request.getParameterValues("interface_ip");
				String[] interface_mac = (String[])request.getParameterValues("interface_mac");
				System.out.println("ptid=|"+ptidStr+"|");
				try {
					if(ptidStr != null){

						// replace, insert , delete /*new method to set port info*/
						//1. delete 
						try {
							TPortInfoDao.deleteByNotInIfdescrList(devid, interface_name);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log4jInit.ncsLog.debug(this.getClass().getName() + " TPortInfoDao.deleteByNotInIfdescrList(devid, interface_name)\n"+devid );
						} 
						//2. update
						List<TPortInfo>  ports2update = null;
						try {
							ports2update = TPortInfoDao.findByDevidAndIfdescrList(devid, interface_name);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log4jInit.ncsLog.info(this.getClass().getName() + "TPortInfoDao.findByDevidAndIfdescrList(devid, interface_name)\n"+devid);
						} 
						//prepare for insert list/uninsert list;
						List<String> uninsert = new ArrayList();
						for(TPortInfo dto: ports2update){
							Log4jInit.ncsLog.debug(this.getClass().getName() + "before update the port info as-----"+dto);
							for(int i=0;i<ptidStr.length;i++){
								if(interface_name[i].equalsIgnoreCase(dto.getIfdescr())){
									try {
										dto.setIfindex(Long.parseLong(interface_index[i]));
										//dto.setIfdescr(interface_name[i]);
										dto.setIfip(interface_ip[i]);
										dto.setIfmac(interface_mac[i]);
										TPortInfoPk pk = dto.createPk();
										TPortInfoDao.update(pk, dto);
									} catch (Exception e) {
										e.printStackTrace();
										message = "Error on write db to port info."+dto;
										Log4jInit.ncsLog.error(this.getClass().getName() + "error on update the port info..."+dto);
										throw new RuntimeException(message);
									}
									Log4jInit.ncsLog.debug(this.getClass().getName() + "after update the port info as-----"+dto);
									uninsert.add(interface_name[i]);
								}
							}  
						 
						} 
						//3. insert into
						for(int i=0;i<ptidStr.length;i++){ 
							Log4jInit.ncsLog.debug(this.getClass().getName() + "before insert the ifdescr ="+interface_name[i]);
							if (uninsert.contains(interface_name[i]) ==true){ 
								Log4jInit.ncsLog.debug(this.getClass().getName() + "ignored insert; the ifdescr ="+interface_name[i]);
								continue;
							}
							TPortInfo portinfo = null;
							try {
								Long ptid = genPkNumber.getID(); 
								portinfo = new TPortInfo(); 
								portinfo.setPtid(ptid); 
								portinfo.setDevid(devid); 
								portinfo.setIfdescr(interface_name[i]); 
								portinfo.setIfindex(Long.parseLong(interface_index[i])); 
								portinfo.setIfip(interface_ip[i]); 
								portinfo.setIfmac(interface_mac[i]); 
								 
								TPortInfoDao.insert( portinfo);
							} catch (Exception e) {
								message = "Error on write db to port info."+portinfo;
								Log4jInit.ncsLog.error(this.getClass().getName() + "error on insert the port info..."+portinfo);
								e.printStackTrace();
								throw new RuntimeException(message);
							}  
							Log4jInit.ncsLog.debug(this.getClass().getName() + "after insert the port info as-----"+portinfo);
						}  
						 
						} else{
							try {
								TPortInfoDao.delete(devid);
							} catch (Exception e) {
								message = "Error on delete portinfo from db. devid="+devid;
								Log4jInit.ncsLog.error(this.getClass().getName() + "error on delete the port info...devid="+devid);
								e.printStackTrace();
								throw new RuntimeException(message);
							} 
						}
//					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				
			
				
				deviceInfoDto.setSysname((String) model.get("devname"));
				deviceInfoDto.setSysnamealias((String) model.get("devalias"));
				String ipaddr =(String) model.get("devip");
				deviceInfoDto.setDevip(ipaddr);
				long ipdecode = IPAddr.encode(ipaddr);
				deviceInfoDto.setIpdecode(ipdecode);
				deviceInfoDto.setRsno((String) model.get("resnum"));
//				deviceInfoDto.set((String) model.get("providerphone"));
				deviceInfoDto.setDomainid(Long.parseLong((String) model.get("domainid")));
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
				if(ramsizeTmp == null || ramsizeTmp.trim().equals(""))
					ramsizeTmp = "-1";
				if(nvramsize == null || nvramsize.trim().equals(""))
					nvramsize = "-1";
				if(flashSize == null || flashSize.trim().equals(""))
					flashSize = "-1";
				deviceInfoDto.setRamsize(Long.parseLong(ramsizeTmp));
				deviceInfoDto.setNvramsize(Long.parseLong(nvramsize));				
				deviceInfoDto.setFlashsize(Long.parseLong(flashSize));
				
				deviceInfoDto.setDescription((String) model.get("description"));

				try {
					//flashfile to db
					String[] flashfilename = request.getParameterValues("flashfile_name");
					String[] flashfilesize = request.getParameterValues("flashfile_size");
					String ffname2db = convertffname(flashfilename);
					String ffsize2db = convertffsize(flashfilesize);
					if(ffname2db!=null && ffname2db.length()>=4000){
						ffname2db = ffname2db.substring(0,3999);
						message += "The length of flashFileName  is over 4000, cannot write to db over 4000.";
					}
					if(ffsize2db!= null && ffsize2db.length()>=1500 ){
						ffsize2db = ffsize2db.substring(0, 1499);
						message += "The Length of flashFileSize is over 1500...cannot write to db over 1500.";
					}
					deviceInfoDto.setFlashfilename(ffname2db);
					deviceInfoDto.setFlashfilesize(ffsize2db);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String[] pdmidstr = request.getParameterValues("pdmid");
				String[] midstr   = request.getParameterValues("mid");
				String[] pdmindex = request.getParameterValues("pdmindex");
				String[] pdmdescr  = request.getParameterValues("pdmdescr");
				
				try {
					if(pdmidstr != null){

						//1.delete
						try {
							PredefmibInfoDao.deleteByDevidAndNotInMid(devid, midstr);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log4jInit.ncsLog.error(this.getClass().getName() + "PredefmibInfoDao.deleteByDevidAndNotInMid(devid, midstr)"+devid);
						}
						//2.update
						List<PredefmibInfo> pdm2update = null;
						try {
							pdm2update =  PredefmibInfoDao.findByDevidAndMidList(devid, midstr);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log4jInit.ncsLog.error(this.getClass().getName() + "PredefmibInfoDao.findByDevidAndMidList(devid, midstr)"+devid);
						}
						//prepare for insert list/uninsert list;
						List<String> uninsertpdm = new ArrayList();
						if(pdm2update!=null && pdm2update.size()>0){
						for(PredefmibInfo pdminfodto:pdm2update){
							Log4jInit.ncsLog.info(this.getClass().getName() + "before update ...pdminfodto="+pdminfodto);
							System.out.println("before update ...pdminfodto="+pdminfodto);
							for(int i=0;i<pdmidstr.length;i++){
								if(midstr[i].equals(pdminfodto.getMid()+"")&&(pdmindex[i].equals(pdminfodto.getOidindex()))&&(pdmdescr[i].equals(pdminfodto.getOidname()))){
									try {
										pdminfodto.setOidindex(pdmindex[i]);
										pdminfodto.setOidname(pdmdescr[i]);
										PredefmibInfoPk pk = pdminfodto.createPk();
										PredefmibInfoDao.update(pk, pdminfodto);
									} catch (Exception e) {
										message = "Error on write to db for pdminfodto="+pdminfodto;
										e.printStackTrace();
										Log4jInit.ncsLog.error(this.getClass().getName() + "error on update ...pdminfodto="+pdminfodto);
										System.out.println("error on update ...pdminfodto="+pdminfodto);
										throw new RuntimeException(message);
									}
									uninsertpdm.add(midstr[i]);
								}
								Log4jInit.ncsLog.info(this.getClass().getName() + "after update ...pdminfodto="+pdminfodto);
								System.out.println("after update ...pdminfodto="+pdminfodto);
							}
						}
						}
						//3.insert
						for(int i=0;i<pdmidstr.length;i++){
							Log4jInit.ncsLog.info(this.getClass().getName() + "before insert ...pdminfodto="+pdmidstr[i]);
							//System.out.println("before insert ...pdminfodto="+pdmidstr[i]);
							if(uninsertpdm.contains(midstr[i])){
								Log4jInit.ncsLog.info(this.getClass().getName() + "ignored insert ...pdminfodto="+pdmidstr[i]);
								//System.out.println("ignored insert ...pdminfodto="+pdmidstr[i]);
								continue;
							}
								PredefmibInfo pdminfodto = new PredefmibInfo();
								try {
									long pdmid = genPkNumber.getID();
									pdminfodto.setPdmid(pdmid);
									pdminfodto.setDevid(devid);
									pdminfodto.setMid(Long.parseLong(midstr[i]));
									pdminfodto.setOidindex(pdmindex[i]);
									pdminfodto.setOidname(pdmdescr[i]);
									PredefmibInfoPk pk = pdminfodto.createPk();
									PredefmibInfoDao.insert( pdminfodto);
								} catch (Exception e) {
									message = "Error on write to db for pdminfodto="+pdminfodto;
									e.printStackTrace();
									Log4jInit.ncsLog.error(this.getClass().getName() + "error on insert ...pdminfodto="+pdminfodto);
									//System.out.println("error on insert ...pdminfodto="+pdminfodto);
									throw new RuntimeException(message);
								}
								Log4jInit.ncsLog.info(this.getClass().getName() + "after insert ...pdminfodto="+pdminfodto);
								//System.out.println("after insert ...pdminfodto="+pdminfodto);
							}
						}else{//pdmidstr ==null 
							PredefmibInfoDao.deleteByDevid(devid); 
						}
						
//					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				try{
				System.out.println("\t\t*****dto in SavingDeviceInfoController is:\n\t\t\t" + deviceInfoDto);
				TDeviceInfoPk pk = deviceInfoDto.createPk();
				TDeviceInfoDao.update(pk, deviceInfoDto);
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TDeviceInfoDao: pk=" +pk.toString() + "\tdto= " +deviceInfoDto.toString());
				}catch(Exception e){
					e.printStackTrace();
					message = "TDeviceInfo update Error!";
					throw new RuntimeException(message);
					
				}
			}
			
			
		}catch(RuntimeException e){
			message = "resourceman.deviceinfo.insert.error";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" +message+" ;"+ e.getMessage());
			model.put("message", message);
			e.printStackTrace();	
			return new ModelAndView("/secure/resourceman/editdeviceInfo.jsp", "model", model );
			
		}
		catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
			model.put("message", message);
			return new ModelAndView("/secure/resourceman/editdeviceInfo.jsp", "model", model );
		}
		return new ModelAndView((getPageView()+"?gid="+gidstr) );
	//	return new ModelAndView((getPageView()+"?gid="+gidstr) );
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
		allowed = VerifyIPAddressRange.verifyRange(gid,  new String[]{ ipstr}, new String[] {mask});
		System.out.println("saving device info verify... allowed="+allowed);
		}
		catch(Exception e){
			e.printStackTrace();
			//return allowed = false;
		}
		return allowed;
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

	public DeviceInfoFormBean getForm() {
		return form;
	}

	public void setForm(DeviceInfoFormBean form) {
		this.form = form;
	}

	

	private Map<String, Object> parseInput(HttpServletRequest request, Map<String, Object> map){
		
		String gid 				= request.getParameter("gid");
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
				form.setGid(gid);
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
//			form.setFlashfile_name(flashfile_name)
				form.setDescription(description);
				
				form.setMrid(mrid);
				form.setDtid(Long.parseLong(dtid));
				form.setPortinfolist(portinfo);
				form.setFlashfile(flashfile);
				form.setPdmInfoByMidMap(pdmInfoByMidMap);
			} catch (NumberFormatException e) {
				String message = "input.parseNumError";
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				map.put("message", message);
				e.printStackTrace();
			}
			
			request.getSession().setAttribute("devicetypeform", form);
			
			
			
	
		//System.out.println("snmppoll.wss: devicetypeform="+form.getDevip());
		System.out.print("devid="+devid);
		map.put("gid", gid);
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
	
	private Map<String, Object> parsePortinfo(HttpServletRequest request, Map<String, Object> map){
		List<TPortInfo> lst = new ArrayList<TPortInfo>();
		//TPortInfo portinfo = new TPortInfo();
		String[] ptidStr = request.getParameterValues("ptid");
		String[] interface_name = (String[])request.getParameterValues("interface_name");
		String[] interface_index = (String[]) request.getParameterValues("interface_index");
		String[] interface_ip = (String[])request.getParameterValues("interface_ip");
		String[] interface_mac = (String[])request.getParameterValues("interface_mac");
		if (ptidStr!=null){
		for(int i=0;i<ptidStr.length;i++){ 
			TPortInfo portinfo = null;
			try {
				Long ptid = Long.parseLong(ptidStr[i]);
				portinfo = new TPortInfo(); 
				portinfo.setPtid(ptid); 
	//			portinfo.setDevid(devid); 
				portinfo.setIfdescr(interface_name[i]); 
				portinfo.setIfindex(Long.parseLong(interface_index[i])); 
				portinfo.setIfip(interface_ip[i]); 
				portinfo.setIfmac(interface_mac[i]); 
				 
				lst.add(portinfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

		}}
		form.setPortinfolist(lst);
		map.put("portinfo", lst);
		return map;
	}
	
	private Map<String, Object> parsePDMinfo(HttpServletRequest request, Map<String, Object> model) {
		List<PredefmibInfo> pdminfolst = new ArrayList();
		Map<Long, Object> pdmInfoByMidMap = new HashMap<Long, Object>();
		try {
			String[] pdmidstr = request.getParameterValues("pdmid");
			String[] midstr   = request.getParameterValues("mid");
			String[] pdmindex = request.getParameterValues("pdmindex");
			String[] pdmdescr  = request.getParameterValues("pdmdescr");
			if (pdmidstr!=null){
			for(int i=0;i<pdmidstr.length;i++){ 
				Long pdmid = Long.parseLong(pdmidstr[i]);

				PredefmibInfo pdminfodto =  new PredefmibInfo(); 
				pdminfodto.setPdmid(pdmid); 
//				pdminfodto.setDevid(devid); 
				pdminfodto.setMid(Long.parseLong(midstr[i])); 
				pdminfodto.setOidindex(pdmindex[i]); 
				pdminfodto.setOidname(pdmdescr[i]);  
			
				pdminfolst.add(pdminfodto);
			
			}
			pdmInfoByMidMap = getPdmInfoMap(pdminfolst);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		form.setPdmInfoByMidMap(pdmInfoByMidMap);
		model.put("pdmInfoByMidMap", pdmInfoByMidMap);
		return model;
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
	
	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}

	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}

	public TGrpNetDao getTGrpNetDao() {
		return TGrpNetDao;
	}

	public TDeviceInfoDao getTDeviceInfoDao() {
		return TDeviceInfoDao;
	}

	public TListIpDao getTListIpDao() {
		return TListIpDao;
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
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
