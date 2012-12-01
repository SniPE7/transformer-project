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
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceInfoPk;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.exceptions.PredefmibInfoDaoException;
import com.ibm.ncs.model.exceptions.SequenceNMDaoException;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.exceptions.TDeviceTypeInitDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.model.exceptions.TManufacturerInfoInitDaoException;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.service.ResourceManServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;
import com.ibm.ncs.util.test.Device;
import com.ibm.ncs.util.test.Flashfile;
import com.ibm.ncs.util.test.Interface;
import com.ibm.ncs.web.resourceman.bean.DeviceInfoFormBean;

public class EditDeviceInfoController  implements Controller {

//	TGrpNetDao TGrpNetDao;
	TDeviceInfoDao TDeviceInfoDao;
//	TListIpDao TListIpDao;
	DeviceTypeTreeDao DeviceTypeTreeDao;
//	ResourceManServices ResourceManServices;
	DeviceInfoFormBean form;
	String pageView;
	TManufacturerInfoInitDao  TManufacturerInfoInitDao;
	TDeviceTypeInitDao TDeviceTypeInitDao;
	TPortInfoDao TPortInfoDao;
	TCategoryMapInitDao TCategoryMapInitDao;
	DefMibGrpDao DefMibGrpDao;
	PredefmibInfoDao PredefmibInfoDao;
	
	public PredefmibInfoDao getPredefmibInfoDao() {
		return PredefmibInfoDao;
	}
	public void setPredefmibInfoDao(PredefmibInfoDao predefmibInfoDao) {
		PredefmibInfoDao = predefmibInfoDao;
	}
	public DefMibGrpDao getDefMibGrpDao() {
		return DefMibGrpDao;
	}
	public void setDefMibGrpDao(DefMibGrpDao defMibGrpDao) {
		DefMibGrpDao = defMibGrpDao;
	}
	public TCategoryMapInitDao getTCategoryMapInitDao() {
		return TCategoryMapInitDao;
	}
	public void setTCategoryMapInitDao(TCategoryMapInitDao categoryMapInitDao) {
		TCategoryMapInitDao = categoryMapInitDao;
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
	public DeviceInfoFormBean getForm() {
		return form;
	}



	public void setForm(DeviceInfoFormBean form) {
		this.form = form;
	}

/*
//	public ModelAndView onSubmit(Object command) {
//		
//		
//		return null;
//		
//	}
//	
//	public ModelAndView onSubmit(Object form){
//		
//		deviceinfoform = (DeviceInfoFormBean) form;
//		
//		
//		
//		try {
//
//			TDeviceInfo devinfoobj 		= new TDeviceInfo();
//			
//			devinfoobj.setDevip( deviceinfoform.getDevip());
//			devinfoobj.setSysname(deviceinfoform.getDevname());
//			devinfoobj.setSysnamealias(deviceinfoform.getDevalias());
//			devinfoobj.setRsno(deviceinfoform.getResnum());
//			//...
//			
//			//devinfoobj.setDevid(deviceinfoform.getDev)
//
//			//TDeviceInfoDao.update(devinfoobj);
//			
//			
//
//
//		}catch (Exception e){
//			throw new RuntimeException("Exception on update into Tdeviceinfo");
//		}
//		
//		
//		
//		return new ModelAndView(new RedirectView(getSuccessView()) );
//		
//	}
*/
	
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		String message ="";
		try{
			// New 
			// list all the device info items by devid ... should be generated later when insert
			// polling field
			// polling ports
			// polling flash files
			// commit and save
			String gid = request.getParameter("gid");		
			model = parseInput(request, model);
			// search ipdecode list for the certain node.
			long devid = 0l;
			String devidStr  = request.getParameter("devid");
			if (null==devidStr||"".equals(devidStr.trim())){ //think of a new device
				System.out.println(" devid==>null");
				//return new ModelAndView("/secure/resourceman/newdeviceInfo.jsp");
			}
			 devid = Long.parseLong(devidStr.trim());
			 
			 //System.out.println("EditDeviceOInfoController...devid="+devid);
			 TDeviceInfo deviceinfo = TDeviceInfoDao.findByPrimaryKey(devid);
			 List<TPortInfo> portinfo = TPortInfoDao.findWhereDevidEquals(devid);
			 SortList<TPortInfo> sortport = new SortList<TPortInfo>();
			 sortport.Sort(portinfo, "getIfindex", null);
			 List<DefMibGrp> alldefmibgrp = null;
			Map<Long, String[]> pdmid2nameflag = null;
			Map<Long, Object> pdmInfoByMidMap = null;
			try {
				alldefmibgrp = DefMibGrpDao.findAll();
				 List<PredefmibInfo> listpdmInfo = PredefmibInfoDao.findWhereDevidEquals(devid);
				 SortList<PredefmibInfo> sortpdm = new SortList<PredefmibInfo>();
				 sortpdm.Sort(listpdmInfo, "getOidindex", null);
				 pdmid2nameflag = conposePdmDsp(alldefmibgrp, listpdmInfo);
				 pdmInfoByMidMap = getPdmInfoMap(listpdmInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			 System.out.println("the port belong to this device is----------"+portinfo);
//	
//			 System.out.println("edit dev info... deviceinfo="+deviceinfo);
	//		 DeviceTypeTreeDao.findDeviceTypeByObjectID(objectid)
			 form = convert(deviceinfo);
		
	//		devicetypetree.setCategory(category);
			model.put("form", form);
			model.put("gid", gid);
			model.put("devid",devid);
			model.put("portinfo",portinfo );
			model.put("alldefmibgrp", alldefmibgrp);
			model.put("pdmInfoByMidMap", pdmInfoByMidMap);
			model.put("pdmid2nameflag", pdmid2nameflag);
//			System.out.println("gid="+gid+"devid="+devid+" "+model+pageView);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e);
			e.printStackTrace();
		}

		request.getSession().removeAttribute("DevInfoMap");
		request.getSession().setAttribute("DevInfoMap", model);

		
		return new ModelAndView(getPageView(), "model", model);
		
	}


	private Map<Long, String[]> conposePdmDsp(List<DefMibGrp> alldefmibgrp, List<PredefmibInfo> listpdmInfo) {
		Map<Long, String[]> mapflag =  new TreeMap<Long, String[]>();
		for (DefMibGrp dto:alldefmibgrp){
			mapflag.put(dto.getMid(), new String[]{dto.getName(),"0",dto.getIndexvar(),dto.getDescrvar()});
		}
		System.out.println(mapflag);
		for(PredefmibInfo sel:listpdmInfo){
			long midused = sel.getMid();
			try {
				String [] tmp = mapflag.get(midused);
				tmp[1] = "1";
				mapflag.put(midused, tmp);
			} catch (Exception e) {
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
	
	private DeviceInfoFormBean convert(TDeviceInfo deviceinfo) throws TManufacturerInfoInitDaoException, TDeviceTypeInitDaoException {
		DeviceInfoFormBean form = new DeviceInfoFormBean();
		long devtypeLong=-1;
		String devtype = "";
		String devsubtype="";
		String devmodel="";
		String devObjid="";
		String mfname="";
		long dtid = deviceinfo.getDtid();
		 long mrid = deviceinfo.getMrid(); 
		try {
			 TManufacturerInfoInit mf = TManufacturerInfoInitDao.findByPrimaryKey(mrid);
			 TDeviceTypeInit dt = TDeviceTypeInitDao.findByPrimaryKey(dtid);
			 System.out.println("devinfo convert...dtid/mrid"+dtid+"/"+mrid+" mf="+mf+" dt="+dt);
			 devtypeLong = dt.getCategory();
			 List<TCategoryMapInit> tCatDto =TCategoryMapInitDao.findWhereIdEquals(devtypeLong);
			 if(tCatDto != null && tCatDto.size()>0)
				 devtype = tCatDto.get(0).getName();
			 devsubtype = dt.getSubcategory();
			 devmodel = dt.getModel();
			 devObjid = dt.getObjectid();
			 mfname = mf.getMrname();
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e);
			//e.printStackTrace();
		}
		if ( form==null){
			form = new DeviceInfoFormBean();
			}
		form.setDevid(deviceinfo.getDevid()+"");
			form.setDevname(deviceinfo.getSysname());
			form.setDevalias(deviceinfo.getSysnamealias());
			form.setDevip(deviceinfo.getDevip());
			form.setResnum(deviceinfo.getRsno());
			form.setDomainid(deviceinfo.getDomainid()+"");
			form.setProviderphone(deviceinfo.getPhone());
			form.setDevtype(devtype);
			form.setDevsubtype(devsubtype);
			form.setModel(devmodel);
			form.setManufacture(mfname);
			form.setObjectid(devObjid);
			form.setSnmpversion(deviceinfo.getSnmpversion());
			form.setRcommunity(deviceinfo.getRcommunity());
			form.setAdminName(deviceinfo.getAdmin());
			form.setAdminPhone(deviceinfo.getPhone());
			form.setDevpolicy("");
			form.setTimeframPolicy("");
			form.setDevSoftwareVer(deviceinfo.getSwversion());
			form.setDevSerialNum(deviceinfo.getSerialid());
			form.setDevgroup(deviceinfo.getDomainid()+"");
			form.setRamsize(deviceinfo.getNvramsize()+"");
			form.setNvramsize(deviceinfo.getNvramsize()+"");
			form.setFlashfilename(deviceinfo.getFlashfilename());
			form.setFlashfilesize(deviceinfo.getFlashfilesize());
			form.setFlashSize(deviceinfo.getFlashsize()+"");
			form.setDescription(deviceinfo.getDescription());
			form.setMrid(String.valueOf(mrid));
			form.setDtid(deviceinfo.getDtid());
			
			try {
				Flashfile[] ff =  ffstr2ffobj(deviceinfo.getFlashfilename(),deviceinfo.getFlashfilesize());
				form.setFlashfile(ff);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return form;
	}
	private Flashfile[] ffstr2ffobj(String flashfilename, String flashfilesize) {
		if (flashfilename==null || flashfilesize==null){ return null;}
		System.out.println("flashfilename="+flashfilename+" / falshfilesize="+flashfilesize);
		 Flashfile flashfiles [] = null;
        String ns[];
        String ss[];
        ns = flashfilename.split(" Attr");
        ss = flashfilesize.split(" Attr");
        if(ns.length != ss.length || ns.length <= 1)        {   return null;}
        try
        {
        	
            ArrayList al = new ArrayList();
            for(int i = 0; i < ns.length - 1; i++){
                try
                {
                    Flashfile ff = new Flashfile(ns[i + 1].substring(ns[i + 1].indexOf("\"") + 1, ns[i + 1].lastIndexOf("\"")), Long.parseLong(ss[i + 1].substring(ss[i + 1].indexOf("\"") + 1, ss[i + 1].lastIndexOf("\""))));
                    al.add(ff);
                    System.out.println("ns="+ns[i]+": ss="+ss[i]+":flashfile covert : ff="+ff);
                }
                catch(NumberFormatException numberformatexception) { }
            }
                flashfiles = new Flashfile[al.size()];
            for(int i = 0; i < flashfiles.length; i++){
                flashfiles[i] = (Flashfile)al.get(i);}

System.out.println("list="+al+" | flashfiles="+flashfiles);
        }
        catch(Exception e)
        {
            flashfiles = new Flashfile[0];
            e.printStackTrace();
        }
        System.out.println(" flashfiles="+flashfiles);
        return flashfiles;
	}
	private Map<String, Object> parseInput(HttpServletRequest request, Map<String, Object> map){
		String devid			= request.getParameter("devid");
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
		//	form.setDevgroup(devgroup);
			form.setRamsize(nvramsize);
			form.setNvramsize(nvramsize);
			form.setFlashSize(flashSize);
//			form.setFlashfilename(flashfilename());
//			form.setFlashfilesize(flashfilesize());
			form.setDescription(description);
			if(dtid != null)
				form.setDtid(Long.parseLong(dtid));
			request.getSession().setAttribute("devicetypeform", form);
			
			form.setPortinfolist(portinfo);
			form.setFlashfile(flashfile);
			form.setPdmInfoByMidMap(pdmInfoByMidMap);
			
		
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
		map.put("dtid", dtid);
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
		try{
			devidLong = Long.parseLong(devidstr);			
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
	


	public TDeviceInfoDao getTDeviceInfoDao() {
		return TDeviceInfoDao;
	}

	public DeviceTypeTreeDao getDeviceTypeTreeDao() {
		return DeviceTypeTreeDao;
	}

	public void setDeviceTypeTreeDao(DeviceTypeTreeDao deviceTypeTreeDao) {
		DeviceTypeTreeDao = deviceTypeTreeDao;
	}


	 public  Device getDeviceByRequest(HttpServletRequest request)
	    {
	
	        String id = request.getParameter("dvid");
	        String name = request.getParameter("name");
	        String nameb = request.getParameter("nameb");
	        String ip = request.getParameter("ip");
	        String srno = request.getParameter("srno");
	        String serviceid = request.getParameter("service");
	        String devicetypeid = request.getParameter("devicetypeid").trim();
	        String snmpversion = request.getParameter("snmpversion");
	        String admin = request.getParameter("admin");
	        String phone = request.getParameter("phone");
	        String softwareversion = request.getParameter("softwareversion");
	        String serial = request.getParameter("serial");
	        String group = request.getParameter("group");
	        String ramsize = request.getParameter("ramsize");
	        String ramunit = request.getParameter("ramunit");
	        String nvramsize = request.getParameter("nvramsize");
	        String nvramunit = request.getParameter("nvramunit");
	        String flashsize = request.getParameter("flashsize");
	        String flashunit = request.getParameter("flashunit");
	        String desc = request.getParameter("desc");
	        String rcommunity = request.getParameter("rcommunity");
	        String interface_index[] = request.getParameterValues("interface_index");
	        String interface_name[] = request.getParameterValues("interface_name");
	        String interface_ip[] = request.getParameterValues("interface_ip");
	        String interface_mac[] = request.getParameterValues("interface_mac");
	        String flashfile_name[] = request.getParameterValues("flashfile_name");
	        String flashfile_size[] = request.getParameterValues("flashfile_size");
	        Interface is[] = new Interface[0];
	        if(interface_index != null && interface_index.length > 0)
	        {
	            is = new Interface[interface_index.length];
	            for(int i = 0; i < interface_index.length; i++)
	            {
	                Interface ii = new Interface();
	                ii.index = Integer.parseInt(interface_index[i]);
	                try
	                {
	                    ii.ip = interface_ip[i];
	                }
	                catch(ArrayIndexOutOfBoundsException e)
	                {
	                    ii.ip = " ";
	                }
	                try
	                {
	                    ii.mac = interface_mac[i];
	                }
	                catch(ArrayIndexOutOfBoundsException e)
	                {
	                    ii.mac = " ";
	                }
	                try
	                {
	                    ii.name = interface_name[i];
	                }
	                catch(ArrayIndexOutOfBoundsException e)
	                {
	                    ii.name = " ";
	                }
	                is[i] = ii;
	            }

	        }
	        Flashfile ff[] = new Flashfile[0];
	        if(flashfile_name != null)
	        {
	            ff = new Flashfile[flashfile_name.length];
	            for(int i = 0; i < flashfile_name.length; i++)
	            {
	                Flashfile tempff = new Flashfile(flashfile_name[i], Long.parseLong(flashfile_size[i]));
	                ff[i] = tempff;
	            }

	        }
	        Device d = new Device();
	        d.admin = admin;
	        d.desc = desc;
	        try
	        {
//	            d.devicegroup.id = Integer.parseInt(group);
	        }
	        catch(NumberFormatException e)
	        {
	            d.flashsize = -1L;
	        }
	        try
	        {
	            d.flashsize = (long)d.setreal(Float.parseFloat(flashsize), flashunit);
	        }
	        catch(NumberFormatException e)
	        {
	            d.flashsize = -1L;
	        }
	        d.flashunit = flashunit;
	        try
	        {
	            d.id = Integer.parseInt(id);
	        }
	        catch(NumberFormatException e)
	        {
	            d.id = -1L;
	        }
	        d.ip = ip;
	        d.name = name;
	        d.nameb = nameb;
	        try
	        {
	            d.nvramsize = (long)d.setreal(Float.parseFloat(nvramsize), nvramunit);
	        }
	        catch(NumberFormatException e)
	        {
	            d.nvramsize = -1L;
	        }
	        d.nvramunit = nvramunit;
	        d.phone = phone;
	        try
	        {
	            d.ramsize = (long)d.setreal(Float.parseFloat(ramsize), ramunit);
	        }
	        catch(NumberFormatException e)
	        {
	            d.ramsize = -1L;
	        }
	        d.ramunit = ramunit;
	        d.serial = serial;
	        try
	        {
	            d.snmpversion = Integer.parseInt(snmpversion);
	        }
	        catch(NumberFormatException e)
	        {
	            d.snmpversion = -1;
	        }
	        d.softwareversion = softwareversion;
	        d.srno = srno;
	        d.rCommunity = rcommunity;

	        d.devicetype.id = Integer.parseInt(devicetypeid);
	        d.flashfiles = ff;
	        d.interfaces = is;
	        return d;
	    }



	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}
	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}
	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}
	public TDeviceTypeInitDao getTDeviceTypeInitDao() {
		return TDeviceTypeInitDao;
	}
	public void setTDeviceTypeInitDao(TDeviceTypeInitDao deviceTypeInitDao) {
		TDeviceTypeInitDao = deviceTypeInitDao;
	}

}
