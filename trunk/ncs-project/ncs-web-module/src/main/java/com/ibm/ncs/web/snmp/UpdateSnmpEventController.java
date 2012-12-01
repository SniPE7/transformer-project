package com.ibm.ncs.web.snmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.SnmpEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.SnmpEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.util.Log4jInit;

public class UpdateSnmpEventController implements Controller{
	String pageView;
	String Message = "";
	SnmpEventsProcessDao snmpEventsProcessDao;
	String method = "update";
	TManufacturerInfoInitDao TManufacturerInfoInitDao;

	



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

	
	public SnmpEventsProcessDao getSnmpEventsProcessDao() {
		return snmpEventsProcessDao;
	}



	public void setSnmpEventsProcessDao(SnmpEventsProcessDao snmpEventsProcessDao) {
		this.snmpEventsProcessDao = snmpEventsProcessDao;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Message = "";
		try{
			String mark = request.getParameter("mark");
			System.out.println("mark is------"+mark);
			String 	resultlist	 		= request.getParameter("resultlist");
			String 	summary	 	= request.getParameter("summary");
			String 	warnmessage	 		= request.getParameter("warnmessage");
			String 	manufacture		= request.getParameter("manufacture");
			
			
			SnmpEventsProcess snmpevents = new SnmpEventsProcess();
			try{
				List<TManufacturerInfoInit> manus = TManufacturerInfoInitDao.findAll();			
				model.put("manus", manus);
				if(mark != null){
					snmpevents = snmpEventsProcessDao.findByPrimaryKey(mark);
					if(snmpevents != null){
						   SnmpEventsProcess snmpevent = new SnmpEventsProcess();
						    snmpevent.setManufacture(manufacture);
							snmpevent.setResultlist(resultlist);
							snmpevent.setSummary(summary);
							snmpevent.setWarnmessage(warnmessage);
							snmpevent.setMark(mark);
							
							
							snmpEventsProcessDao.update(snmpevent);
							Log4jInit.ncsLog.info(this.getClass().getName() + " updated to SnmpEventsProcessDao:dto=" + snmpevent.toString());
							
							
							model.put("mark", mark);
							model.put("manufacture", manufacture);
							model.put("resultlist", snmpevent.getResultlist());
							model.put("summary", snmpevent.getSummary());
							model.put("warnmessage", snmpevent.getWarnmessage());
								
					   
					   }
					}
			}catch(Exception e){
				System.out.println("update failed-------------");
			  	Message = "baseinfo.update.fail";
			  	Log4jInit.ncsLog.error(this.getClass().getName() + " updated to SnmpEventsProcessDao Failed"
						+ "\n" + e.getMessage());
				model.put("message", Message);
				e.printStackTrace();
				model.put("mark", mark);
				model.put("manufacture", manufacture);
				model.put("resultlist", resultlist);
				model.put("summary", summary);
				model.put("warnmessage", warnmessage);
				model.put("method", method);
				return new ModelAndView("/secure/snmp/snmpevent.jsp","model",model);
			}
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		
		try{
			List<SnmpEventsProcess> tabList = null;
			tabList = snmpEventsProcessDao.findAll();
			model.put("tabList", tabList);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			Message = "baseinfo.list.error";		
			model.put("message", Message);
			e.printStackTrace();
		}
		
		model.put("method", method);
		return new ModelAndView(getPageView(),"model",model);
	}

}
