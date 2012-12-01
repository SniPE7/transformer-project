package com.ibm.ncs.web.syslog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.EventsAttentionDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dto.EventsAttention;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.util.Log4jInit;

public class UpdateToEventsAttentionController implements Controller{
	String pageView;
	String Message = "";
	EventsAttentionDao eventsAttentionDao;
	String method = "update";
	
	

	public EventsAttentionDao getEventsAttentionDao() {
		return eventsAttentionDao;
	}



	public void setEventsAttentionDao(EventsAttentionDao eventsAttentionDao) {
		this.eventsAttentionDao = eventsAttentionDao;
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


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try{
		String eventsAttention = request.getParameter("eventsAttention");
		//System.out.println("mark from jsp is---"+mark);
		List<EventsAttention> syslogs = new ArrayList<EventsAttention>();
		
		if(eventsAttention != null){
			syslogs = eventsAttentionDao.findWhereEventsattentionEquals(eventsAttention);
			System.out.println("syslogs size is---"+syslogs);
			if(syslogs != null){
			   if(syslogs.size() > 0){
				   EventsAttention syslog = new EventsAttention();
				   syslog = syslogs.get(0);
				   model.put("eventsAttention", syslog.getEventsattention());
				   model.put("severity", syslog.getSeverity());
				   model.put("processSuggest", syslog.getProcesssuggest());
				      
			   }
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
