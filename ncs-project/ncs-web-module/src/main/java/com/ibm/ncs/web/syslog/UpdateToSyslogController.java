package com.ibm.ncs.web.syslog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

public class UpdateToSyslogController implements Controller{
	String pageView;
	String Message = "";
	SyslogEventsProcessDao syslogEventsProcessDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	String method = "update";
	



	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}



	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}



	public String getMethod() {
		return method;
	}



	public void setMethod(String method) {
		this.method = method;
	}



	public String getPageView() {
		return pageView;
	}



	public void setPageView(String pageView) {
		this.pageView = pageView;
	}



	public String getMessage() {
		return Message;
	}



	public void setMessage(String message) {
		Message = message;
	}



	public SyslogEventsProcessDao getSyslogEventsProcessDao() {
		return syslogEventsProcessDao;
	}



	public void setSyslogEventsProcessDao(
			SyslogEventsProcessDao syslogEventsProcessDao) {
		this.syslogEventsProcessDao = syslogEventsProcessDao;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try{
		String mark = request.getParameter("mark");
		String manuf = request.getParameter("manuf");
		SyslogEventsProcess syslog = new SyslogEventsProcess();
		List<TManufacturerInfoInit> manus = null;
		
		if(mark != null){
			syslog = syslogEventsProcessDao.findByPrimaryKey(mark, manuf);
			if(syslog != null){
			   model.put("alertgroup", syslog.getAlertgroup());
				model.put("alertkey", syslog.getAlertkey());
				model.put("attentionflag", syslog.getAttentionflag());
				model.put("btimelist", syslog.getBtimelist());
				model.put("etimelist", syslog.getEtimelist());
				model.put("events", syslog.getEvents());
				model.put("eventtype", syslog.getEventtype());
				model.put("filterflag1", syslog.getFilterflag1());
				model.put("filterflag2", syslog.getFilterflag2());
				model.put("manufacture", syslog.getManufacture());
				model.put("mark", mark);
				model.put("notcareflag", syslog.getNotcareflag());
				model.put("origevent", syslog.getOrigevent());
				model.put("port", syslog.getPort());
				model.put("processsuggest", syslog.getProcesssuggest());
				model.put("severity1", syslog.getSeverity1());
				model.put("severity2", syslog.getSeverity2());
				model.put("status", syslog.getStatus());
				model.put("subeventtype", syslog.getSubeventtype());
				model.put("summarycn", syslog.getSummarycn());
				model.put("type", syslog.getType());
				model.put("varlist", syslog.getVarlist());
				model.put("markOld", mark);
				model.put("manuf", manuf);
				manus = TManufacturerInfoInitDao.findAll();
				SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
				sortmanu.Sort(manus, "getMrname", null);
				model.put("manus", manus);				
		   }
		}
		
		
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();			
		}
		model.put("method", method);
		return new ModelAndView(getPageView(),"model",model);
	}

}
