package com.ibm.ncs.util.excel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.BkSnmpEventsProcessDao;
import com.ibm.ncs.model.dao.BkSyslogEventsProcessDao;
import com.ibm.ncs.model.dao.BkSyslogEventsProcessNsDao;
import com.ibm.ncs.model.dto.BkSnmpEventsProcess;
import com.ibm.ncs.model.dto.BkSyslogEventsProcess;
import com.ibm.ncs.model.dto.BkSyslogEventsProcessNs;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.util.SortList;


public class RestorePreController implements Controller {
	 String pageView;
	 BkSnmpEventsProcessDao bkSnmpEventsProcessDao;
	 String message="";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		try{
		String mode = request.getParameter("checkmode");
		System.out.println("mode is :"+mode);
		model.put("mode", mode);
		
		if(mode != null){
			if(mode.equalsIgnoreCase("snmp")){
				List<BkSnmpEventsProcess> bksnmp = bkSnmpEventsProcessDao.listAllVersions();
				System.out.println("bksnmp size is :"+bksnmp);
				List bktimelist = null;
				if(bksnmp != null && bksnmp.size()>0){
//					SortList<BkSnmpEventsProcess> sortmanu = new SortList<BkSnmpEventsProcess>();
//					sortmanu.Sort(bksnmp, "getBkTime", "desc");
//					model.put("bksnmp", bksnmp);
					/*for(int i=0;i<bksnmp.size();i++){
						Date bktime = bksnmp.get(i).getBkTime();
						
						if(bktimelist == null){
						    bktimelist.add(bktime);
						}else{
							if(bktimelist.contains(bktime)== false){
								bktimelist.add(bktime);
							}
						}
					}
					model.put("bktimelist", bktimelist);*/
					

					List<VersionInfo> dispver = new ArrayList<VersionInfo>();
					for (BkSnmpEventsProcess dto: bksnmp){
						VersionInfo vif = new VersionInfo(dto.getBkTime(), "(snmp="+dto.getBkId()+")", "");								
						dispver.add(vif);
						
					}
					SortList<VersionInfo> srt = new SortList<VersionInfo>();
					srt.Sort(dispver,"getVer", "desc" );
					model.put("dispver", dispver);
					
				}else{
					message = "excel.bk.nodata";
					model.put("Message", message);
					return new ModelAndView("/secure/excel/upload.jsp","model",model);
				}
				
			}
		}
		}catch(DaoException e){
			e.printStackTrace();
			message = "global.db.error";
			model.put("message", message);
			return new ModelAndView("/dberror.jsp","model",model);
		}
		
		
		return new ModelAndView(getPageView(),"model",model);
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public BkSnmpEventsProcessDao getBkSnmpEventsProcessDao() {
		return bkSnmpEventsProcessDao;
	}

	public void setBkSnmpEventsProcessDao(
			BkSnmpEventsProcessDao bkSnmpEventsProcessDao) {
		this.bkSnmpEventsProcessDao = bkSnmpEventsProcessDao;
	}

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
