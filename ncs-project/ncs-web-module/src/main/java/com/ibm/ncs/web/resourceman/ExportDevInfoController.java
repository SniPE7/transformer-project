package com.ibm.ncs.web.resourceman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dao.PredefmibInfoDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.service.PolicyAppServices;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.excel.DBTOExcel;

public class ExportDevInfoController implements Controller {
	String message = "";
	PolicyAppServices PolicyAppServices;
	TDeviceInfoDao TDeviceInfoDao;
	TPortInfoDao TPortInfoDao;
	PredefmibInfoDao predefmibInfoDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	TCategoryMapInitDao TCategoryMapInitDao; 
	TDeviceTypeInitDao TDeviceTypeInitDao;
	TGrpNetDao TGrpNetDao;
	DefMibGrpDao defMibGrpDao;
	String pageView;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		String gidStr = request.getParameter("gid");
		 if (null==gidStr||"".equals(gidStr.trim())){return null;}
		    long gid = Long.parseLong(gidStr.trim());
		String [] sel = request.getParameterValues("sel");
		try{
		if(sel != null){
			
			Map<String, Object> exportList = new HashMap<String, Object>();
			 String gname = TGrpNetDao.findByPrimaryKey(gid).getGname();
			List<TDeviceInfo> deviceInfos = new ArrayList<TDeviceInfo>();
			for(int i=0; i<sel.length; i++){
				String devidStr = sel[i];
				TDeviceInfo deviceInfo = TDeviceInfoDao.findByPrimaryKey(Long.parseLong(devidStr));
				deviceInfos.add(deviceInfo);
			}
			
			 List<TPortInfo> portInfo = null;
			 List<PredefmibInfo> premibInfo = null;
			 List<TManufacturerInfoInit> manufactures = TManufacturerInfoInitDao.findAll();
				Map<Long,Object> manufmap = new HashMap<Long, Object>();
				for(TManufacturerInfoInit mf: manufactures){
					manufmap.put(mf.getMrid(), mf);
				}
				List<TDeviceTypeInit> devicetype = TDeviceTypeInitDao.findAll();
				Map<Long,Object> dtmap = new HashMap<Long, Object>();
				for(TDeviceTypeInit dt :  devicetype ){
					dtmap.put(dt.getDtid(), dt);
				}
				Map<Long,Object> catemap = new HashMap<Long,Object>();
				List<TCategoryMapInit> catelist =TCategoryMapInitDao.findAll();
				for(TCategoryMapInit catedto: catelist){
					catemap.put(catedto.getId(), catedto.getName());
				}
				
				if(deviceInfos != null && deviceInfos.size()>0){
					ServletOutputStream outputStream = response.getOutputStream();
					response.reset();
				//	response.setHeader("Content-disposition", "attachement;filename=\""+gname+"Export.xls\"");
					response.setContentType("application/vnd.ms-excel");				
					response.addHeader("content-type", "application/x-msdownload");
					response.setHeader("Content-disposition", "attachement;filename=\"DevInfoExport.xls\"");
					
					exportList.put("T_DEVICE_INFO", deviceInfos);
					
					Map<Long,List<TPortInfo>>  portmap = new HashMap<Long,List<TPortInfo>>();
			     	Map<Long,List<PredefmibInfo>> mibmap = new HashMap<Long,List<PredefmibInfo>>();
			     	Map<Long,Object> mibgrpmap = new HashMap<Long,Object>();
			     	Map<Long,TDeviceInfo> devicefromport = new HashMap<Long,TDeviceInfo>();
			     	Map<Long,TDeviceInfo> devicefrommib = new HashMap<Long,TDeviceInfo>();
		    	   for(TDeviceInfo device : deviceInfos){
		    		   long devid = device.getDevid();
		    		   portInfo = TPortInfoDao.findWhereDevidEquals(devid);
		    		  
		    		   if(portInfo != null && portInfo.size()>0){
		    			   portmap.put(devid, portInfo);
		    			   devicefromport.put(devid, device);
		    		   }
		    		   
		    		   premibInfo = predefmibInfoDao.findWhereDevidEquals(devid);
		    		   if(premibInfo != null && premibInfo.size()>0){
		    			   mibmap.put(devid, premibInfo);
		    			   devicefrommib.put(devid, device);
		    		   }
		    		   
		    	   }
		    	 
		    	   if(portmap != null && portmap.size()>0){
		    	   exportList.put("T_PORT_INFO", portmap);
		    	   }
		    	   
		    	 
		    	   if(mibmap != null && mibmap.size()>0){
		    	   exportList.put("PREDEFMIB_INFO", mibmap);
		    	   }
		    	   List<DefMibGrp> mibgrp = defMibGrpDao.findAllWhereMidEqualsMibInfoMid();
		    	   if(mibgrp != null && mibgrp.size()>0){
		    		   for(DefMibGrp mib : mibgrp){
		    			   mibgrpmap.put(mib.getMid(), mib);
		    		   }
		    	   }
		    	   
		    	   System.out.println("mibgrpmap is "+mibgrpmap);
		    	/*  System.out.println("manumap is :"+manufmap+" and catemap is :"+catemap+" and dtmap is :"+dtmap+" and devicefrommib is :"+devicefrommib
		    			  +" and devicefromport is :"+devicefromport+" and portmap is :"+portmap+" and mibmap is :"+mibmap+" and exportList is :"+exportList.size());
		        */     
		             try {
		            	    
							DBTOExcel.exportDevInfo(gname,exportList,manufmap,dtmap,catemap,devicefromport,devicefrommib,portmap,mibmap,mibgrpmap).write(outputStream);
							outputStream.flush();
							outputStream.close();
							Log4jInit.ncsLog.info("Exported Device Info");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}else{
					message = "baseinfo.export.noData";
					model.put("message", message);				
					return new ModelAndView(getPageView(),	"model", model);
				}
			
			
		}else{
			message = "resourceman.export.noSelection";
			model.put("message", message);
			List<TDeviceInfo> deviceinfo = PolicyAppServices.getDeviceInfoListOfTheGidNode(gid);
			
			List<TManufacturerInfoInit> manufactures = TManufacturerInfoInitDao.findAll();
			Map<Long,Object> manufmap = new HashMap<Long, Object>();
			for(TManufacturerInfoInit mf: manufactures){
				manufmap.put(mf.getMrid(), mf);
			}
			List<TDeviceTypeInit> devicetype = TDeviceTypeInitDao.findAll();
			Map<Long,Object> dtmap = new HashMap<Long, Object>();
			for(TDeviceTypeInit dt :  devicetype ){
				dtmap.put(dt.getDtid(), dt);
			}
			Map<Long,Object> catemap = new HashMap<Long,Object>();
			List<TCategoryMapInit> catelist =TCategoryMapInitDao.findAll();
			for(TCategoryMapInit catedto: catelist){
				catemap.put(catedto.getId(), catedto.getName());
			}
			model.put("catemap", catemap);

			model.put("deviceinfo", deviceinfo);
			Map seobj = (Map)request.getSession().getAttribute("grpnetNames");
			model.put("grpnetNames", seobj);
			model.put("manufmap",manufmap);
			model.put("dtmap", dtmap);
			model.put("gid", gid);
			return new ModelAndView(getPageView(),	"model", model);
		}
		}catch(Exception e){
			e.printStackTrace();
            Log4jInit.ncsLog.error("InterruptedException in Export DeviceInfo, message: " + e.getMessage());
			
			message = "resourceman.export.failed";
			model.put("message", message);				
			return new ModelAndView(getPageView(),	"model", model);
		}
    	
	
		message = "resourceman.export.success";
		model.put("message", message);		
		
		List<TDeviceInfo> deviceinfo = PolicyAppServices.getDeviceInfoListOfTheGidNode(gid);
		
		List<TManufacturerInfoInit> manufactures = TManufacturerInfoInitDao.findAll();
		Map<Long,Object> manufmap = new HashMap<Long, Object>();
		for(TManufacturerInfoInit mf: manufactures){
			manufmap.put(mf.getMrid(), mf);
		}
		List<TDeviceTypeInit> devicetype = TDeviceTypeInitDao.findAll();
		Map<Long,Object> dtmap = new HashMap<Long, Object>();
		for(TDeviceTypeInit dt :  devicetype ){
//			String devtype = "";
			dtmap.put(dt.getDtid(), dt);
//			 List<TCategoryMapInit> tCatDto =TCategoryMapInitDao.findWhereIdEquals(dt.getCategory());
//			 if(tCatDto != null && tCatDto.size()>0)
//				 devtype = tCatDto.get(0).getName();
//			dtmap.put(dt.getCategory(), devtype);
		}
		Map<Long,Object> catemap = new HashMap<Long,Object>();
		List<TCategoryMapInit> catelist =TCategoryMapInitDao.findAll();
		for(TCategoryMapInit catedto: catelist){
			catemap.put(catedto.getId(), catedto.getName());
		}
		model.put("catemap", catemap);

		model.put("deviceinfo", deviceinfo);
//		model.put("listipbygid", listipbygid);
//		model.put("ipdecodescopes", new long [][] {min, max} );
		Map seobj = (Map)request.getSession().getAttribute("grpnetNames");
		model.put("grpnetNames", seobj);
		model.put("manufmap",manufmap);
		model.put("dtmap", dtmap);
		model.put("gid", gid);
		return new ModelAndView(getPageView(),	"model", model);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PolicyAppServices getPolicyAppServices() {
		return PolicyAppServices;
	}

	public void setPolicyAppServices(PolicyAppServices policyAppServices) {
		PolicyAppServices = policyAppServices;
	}


	public TDeviceInfoDao getTDeviceInfoDao() {
		return TDeviceInfoDao;
	}

	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}

	public TPortInfoDao getTPortInfoDao() {
		return TPortInfoDao;
	}

	public void setTPortInfoDao(TPortInfoDao portInfoDao) {
		TPortInfoDao = portInfoDao;
	}

	public PredefmibInfoDao getPredefmibInfoDao() {
		return predefmibInfoDao;
	}

	public void setPredefmibInfoDao(PredefmibInfoDao predefmibInfoDao) {
		this.predefmibInfoDao = predefmibInfoDao;
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

	public TCategoryMapInitDao getTCategoryMapInitDao() {
		return TCategoryMapInitDao;
	}

	public void setTCategoryMapInitDao(TCategoryMapInitDao categoryMapInitDao) {
		TCategoryMapInitDao = categoryMapInitDao;
	}

	public TDeviceTypeInitDao getTDeviceTypeInitDao() {
		return TDeviceTypeInitDao;
	}

	public void setTDeviceTypeInitDao(TDeviceTypeInitDao deviceTypeInitDao) {
		TDeviceTypeInitDao = deviceTypeInitDao;
	}

	public TGrpNetDao getTGrpNetDao() {
		return TGrpNetDao;
	}

	public void setTGrpNetDao(TGrpNetDao grpNetDao) {
		TGrpNetDao = grpNetDao;
	}

	public DefMibGrpDao getDefMibGrpDao() {
		return defMibGrpDao;
	}

	public void setDefMibGrpDao(DefMibGrpDao defMibGrpDao) {
		this.defMibGrpDao = defMibGrpDao;
	}

	
	
	
	
	

}
