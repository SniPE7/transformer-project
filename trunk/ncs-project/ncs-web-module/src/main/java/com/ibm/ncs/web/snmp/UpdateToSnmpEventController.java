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
import com.ibm.ncs.util.SortList;

public class UpdateToSnmpEventController implements Controller{
	String pageView;
	String Message = "";
	SnmpEventsProcessDao snmpEventsProcessDao;
	String method = "update";
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	
	

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


	

	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}



	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try{
		String mark = request.getParameter("mark");
		//System.out.println("mark from jsp is---"+mark);
		List<SnmpEventsProcess> snmpevents = new ArrayList<SnmpEventsProcess>();
		List<TManufacturerInfoInit> manus = null;
		
		if(mark != null){
			snmpevents = snmpEventsProcessDao.findWhereMarkEquals(mark);
			if(snmpevents != null){
			   if(snmpevents.size() > 0){
				   SnmpEventsProcess snmpevent = new SnmpEventsProcess();
				   snmpevent = snmpevents.get(0);
				   
				  
					model.put("manufacture", snmpevent.getManufacture());
					model.put("mark", mark);
					model.put("resultlist", snmpevent.getResultlist());
					model.put("summary", snmpevent.getSummary());
					model.put("warnmessage", snmpevent.getWarnmessage());
					
			   }
			}
			
		}
		manus = TManufacturerInfoInitDao.findAll();
		SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
		sortmanu.Sort(manus, "getMrname", null);
		
		model.put("manus", manus);
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
			
		}
		model.put("method", method);
		
		return new ModelAndView(getPageView(),"model",model);
	}

}
