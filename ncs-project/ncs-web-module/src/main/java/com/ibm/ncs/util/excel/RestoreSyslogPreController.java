package com.ibm.ncs.util.excel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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


public class RestoreSyslogPreController implements Controller {
	 String pageView;
	 BkSyslogEventsProcessDao bkSyslogEventsProcessDao;
	 BkSyslogEventsProcessNsDao bkSyslogEventsProcessNsDao;
	 String message="";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		/*String mode = request.getParameter("checkmode");
		System.out.println("mode is :"+mode);
		model.put("mode", mode);*/
		    try{
				List<BkSyslogEventsProcess> bksyslog = bkSyslogEventsProcessDao.listAllVersions();
				List<BkSyslogEventsProcessNs> bksyslogns = bkSyslogEventsProcessNsDao.listAllVersions();
				System.out.println("bksyslog size is :"+bksyslog.size()+" and bksyslogns size is :"+bksyslogns.size());
				if(bksyslog != null && bksyslogns != null){
					if(bksyslog.size()==0 && bksyslogns.size()==0){
						message = "excel.bk.nodata";
						model.put("Message", message);
						return new ModelAndView("/secure/excel/uploadsyslog.jsp","model",model);
					}else{
				   
//				    		if(bksyslog.size()>bksyslogns.size()){
//				    			SortList<BkSyslogEventsProcess> sortsyslog = new SortList<BkSyslogEventsProcess>();
//							    sortsyslog.Sort(bksyslog, "getBkTime", "desc");
//							    model.put("bksnmp", bksyslog);
//				    		}else{
//				    			SortList<BkSyslogEventsProcessNs> sortsyslogns = new SortList<BkSyslogEventsProcessNs>();
//							    sortsyslogns.Sort(bksyslogns, "getBkTime", "desc");
//							    model.put("bksnmp", bksyslogns);
//				    		}
							
							Map<Date, VersionInfo> vermap = new HashMap<Date, VersionInfo>();
							List<BkSyslogEventsProcess> bk = new ArrayList<BkSyslogEventsProcess>();
							for (BkSyslogEventsProcess dto: bksyslog){
								bk = bkSyslogEventsProcessDao.findWhereBkTimeEquals(dto.getBkTime());
								VersionInfo vif = new VersionInfo(dto.getBkTime(), "(syslog="+dto.getBkId()+")", "( )");								
								vermap.put(dto.getBkTime(), vif);
								if(vermap.get(dto.getBkTime()).getSyslog().equals("(syslog=1)")&& (bk != null && bk.size()==1)&&(bk.get(0).getMark()==null)){
									vermap.get(dto.getBkTime()).setSyslog("(syslog=0)");
								}
								
							}
							List<BkSyslogEventsProcessNs> bkns = new ArrayList<BkSyslogEventsProcessNs>();
							for (BkSyslogEventsProcessNs dto : bksyslogns){
								bkns = bkSyslogEventsProcessNsDao.findWhereBkTimeEquals(dto.getBkTime());
								
								VersionInfo vif = vermap.get(dto.getBkTime());
								if (null == vif){	
									vif = new VersionInfo(dto.getBkTime(), "( )", "(syslogNS="+dto.getBkId()+")");
								}else{
									vif.syslogns= "(syslogNS="+dto.getBkId()+")";
								}
								vermap.put(dto.getBkTime(), vif);	
								if(vermap.get(dto.getBkTime()).getSyslogns().equals("(syslogNS=1)") && (bkns != null && bkns.size()==1)&&(bkns.get(0).getMark()==null)){
									vermap.get(dto.getBkTime()).setSyslogns("(syslogNS=0)");
								}
							}
							List<VersionInfo> dispver = new ArrayList<VersionInfo>();
							dispver.addAll(vermap.values());
							SortList<VersionInfo> srt = new SortList<VersionInfo>();
							srt.Sort(dispver,"getVer", "desc" );
							model.put("dispver", dispver);
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

	

	public BkSyslogEventsProcessDao getBkSyslogEventsProcessDao() {
		return bkSyslogEventsProcessDao;
	}

	public void setBkSyslogEventsProcessDao(
			BkSyslogEventsProcessDao bkSyslogEventsProcessDao) {
		this.bkSyslogEventsProcessDao = bkSyslogEventsProcessDao;
	}

	public BkSyslogEventsProcessNsDao getBkSyslogEventsProcessNsDao() {
		return bkSyslogEventsProcessNsDao;
	}

	public void setBkSyslogEventsProcessNsDao(
			BkSyslogEventsProcessNsDao bkSyslogEventsProcessNsDao) {
		this.bkSyslogEventsProcessNsDao = bkSyslogEventsProcessNsDao;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
		
	
}
