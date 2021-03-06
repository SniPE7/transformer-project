package com.ibm.ncs.util.excel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.BkSnmpEventsProcessDao;
import com.ibm.ncs.model.dao.BkSyslogEventsProcessDao;
import com.ibm.ncs.model.dao.BkSyslogEventsProcessNsDao;
import com.ibm.ncs.model.dao.SnmpEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dto.BkSnmpEventsProcess;
import com.ibm.ncs.model.dto.BkSyslogEventsProcess;
import com.ibm.ncs.model.dto.BkSyslogEventsProcessNs;
import com.ibm.ncs.model.dto.SnmpEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

public class DeleteVersionController implements Controller {
	String pageView;
	BkSnmpEventsProcessDao bkSnmpEventsProcessDao;
	
	
	String message = "";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("delete version invoked----");
		String mode = request.getParameter("checkmode");
		System.out.println("mode is :"+mode);
		System.out.println("mode from jsp is :"+mode);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("mode", mode);
		
		String versionStr = request.getParameter("versionsel");
		System.out.println("the version to be deleted is :"+versionStr);
		
		
		
		
		if(versionStr != null){
			Timestamp timestamp = Timestamp.valueOf(versionStr);
			System.out.println("timestamp is :"+timestamp);
			List<BkSnmpEventsProcess> snmp = bkSnmpEventsProcessDao.findWhereBkTimeEquals(timestamp);
			
			System.out.println("snmp size is :"+snmp.size());
			if(snmp.size()>0 ){
				try{
					bkSnmpEventsProcessDao.deleteByBkTime(timestamp);
					message = "excel.deletever.success";
					model.put("Message", message);
				Log4jInit.ncsLog.info("delete version from snmp");
				}catch(Exception e){
					message = "excel.deletever.error";
					model.put("Message", message);
					e.printStackTrace();
					Log4jInit.ncsLog.error("delete version from snmp failed");
				}
				}
			
			
		}
		
		List<BkSnmpEventsProcess> bksnmp = bkSnmpEventsProcessDao.listAllVersions();
		System.out.println("bksnmp size is :"+bksnmp);
		if(bksnmp != null ){
			if(bksnmp.size()==0){
				message = "excel.bk.nodata";
				model.put("Message", message);
				return new ModelAndView("/secure/excel/upload.jsp","model",model);
			}
			if(bksnmp.size()>0){
//			SortList<BkSnmpEventsProcess> sortmanu = new SortList<BkSnmpEventsProcess>();
//			sortmanu.Sort(bksnmp, "getBkTime", "desc");
//			model.put("bksnmp", bksnmp);
				
				List<VersionInfo> dispver = new ArrayList<VersionInfo>();
				for (BkSnmpEventsProcess dto: bksnmp){
					VersionInfo vif = new VersionInfo(dto.getBkTime(), "(snmp="+dto.getBkId()+")", "");								
					dispver.add(vif);					
				}
				SortList<VersionInfo> srt = new SortList<VersionInfo>();
				srt.Sort(dispver,"getVer", "desc" );
				model.put("dispver", dispver);
			}
			
		}else{
			message = "excel.bk.nodata";
			model.put("Message", message);
			return new ModelAndView("/secure/excel/upload.jsp","model",model);
		}
		return new ModelAndView(getPageView(),"model",model);
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

	public BkSnmpEventsProcessDao getBkSnmpEventsProcessDao() {
		return bkSnmpEventsProcessDao;
	}

	public void setBkSnmpEventsProcessDao(
			BkSnmpEventsProcessDao bkSnmpEventsProcessDao) {
		this.bkSnmpEventsProcessDao = bkSnmpEventsProcessDao;
	}

	
	
	
	

}
