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

public class DeleteSyslogVersionMulController implements Controller {
	String pageView;
	BkSyslogEventsProcessDao bkSyslogEventsProcessDao;
	BkSyslogEventsProcessNsDao bkSyslogEventsProcessNsDao;
	SyslogEventsProcessDao syslogEventsProcessDao;
	SyslogEventsProcessNsDao syslogEventsProcessNsDao;
	
	
	String message = "";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("delete versionmul invoked----");
		Map<String,Object> model = new HashMap<String,Object>();
		
		try{
			List<BkSyslogEventsProcess> bksyslog = bkSyslogEventsProcessDao.listAllVersions();
			List<BkSyslogEventsProcessNs> bksyslogns = bkSyslogEventsProcessNsDao.listAllVersions();
			
			if(bksyslog.size()>3 || bksyslogns.size()>3){
				try{
		          bkSyslogEventsProcessDao.clean4Last3ver();
		          bkSyslogEventsProcessNsDao.clean4Last3ver();
		          Log4jInit.ncsLog.info("delete versionmulti from syslog and syslogns");
		          message = "excel.clear.success";
		          model.put("Message", message);
				}catch(Exception e){
					message = "excel.clear.error";
					model.put("Message", message);
					e.printStackTrace();
					Log4jInit.ncsLog.error("delete versionmulti from syslog and syslogns failed");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			List<BkSyslogEventsProcess> bksyslog = bkSyslogEventsProcessDao.listAllVersions();
			List<BkSyslogEventsProcessNs> bksyslogns = bkSyslogEventsProcessNsDao.listAllVersions();
			System.out.println("bksyslog size is :"+bksyslog.size()+" and bksyslogns size is :"+bksyslogns.size());
			if(bksyslog != null && bksyslogns != null){
				if(bksyslog.size()==0 && bksyslogns.size()==0){
					message = "excel.bk.nodata";
					model.put("Message", message);
					return new ModelAndView("/secure/excel/choosesyslogversion.jsp","model",model);
				}else{
			   
//			    		if(bksyslog.size()>bksyslogns.size()){
//			    			SortList<BkSyslogEventsProcess> sortsyslog = new SortList<BkSyslogEventsProcess>();
//						    sortsyslog.Sort(bksyslog, "getBkTime", "desc");
//						    model.put("bksnmp", bksyslog);
//			    		}else{
//			    			SortList<BkSyslogEventsProcessNs> sortsyslogns = new SortList<BkSyslogEventsProcessNs>();
//						    sortsyslogns.Sort(bksyslogns, "getBkTime", "desc");
//						    model.put("bksnmp", bksyslogns);
//			    		}
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
			
		}catch(Exception e){
			e.printStackTrace();
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

	public SyslogEventsProcessDao getSyslogEventsProcessDao() {
		return syslogEventsProcessDao;
	}

	public void setSyslogEventsProcessDao(
			SyslogEventsProcessDao syslogEventsProcessDao) {
		this.syslogEventsProcessDao = syslogEventsProcessDao;
	}

	public SyslogEventsProcessNsDao getSyslogEventsProcessNsDao() {
		return syslogEventsProcessNsDao;
	}

	public void setSyslogEventsProcessNsDao(
			SyslogEventsProcessNsDao syslogEventsProcessNsDao) {
		this.syslogEventsProcessNsDao = syslogEventsProcessNsDao;
	}
	
	

}
