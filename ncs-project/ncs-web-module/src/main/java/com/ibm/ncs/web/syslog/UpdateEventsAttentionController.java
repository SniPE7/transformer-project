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

public class UpdateEventsAttentionController implements Controller{
	String pageView;
	String Message = "";
	EventsAttentionDao eventsAttentionDao;
	String method = "update";
	
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

	public EventsAttentionDao getEventsAttentionDao() {
		return eventsAttentionDao;
	}



	public void setEventsAttentionDao(EventsAttentionDao eventsAttentionDao) {
		this.eventsAttentionDao = eventsAttentionDao;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Message = "";
		try{
		String eventsAttention = request.getParameter("eventsAttention");
		String 	severity	 		= request.getParameter("severity");
		String processSuggest = request.getParameter("processSuggest");
		
		System.out.println("eventsAttention from jsp is--"+eventsAttention+"severity form jsp is--"+severity+
				"process from jsp is---"+processSuggest);
		List<EventsAttention> events = new ArrayList<EventsAttention>();
		try{
		if(eventsAttention != null){
			events = eventsAttentionDao.findWhereEventsattentionEquals(eventsAttention);
			if(events != null){
			   if(events.size() > 0){
				   EventsAttention event = new EventsAttention();
				   event = events.get(0);
				   System.out.println("event is---"+event);
				   
				   event.setEventsattention(eventsAttention);
				   event.setProcesssuggest(processSuggest);
				   event.setSeverity(Long.parseLong(severity));
				   System.out.println("after setting the event is---"+event);
				   
				   eventsAttentionDao.update(event);
				model.put("eventsAttention", event.getEventsattention());
				model.put("processSuggest", event.getProcesssuggest());
				model.put("severity", event.getSeverity());
			   }
			}
			
		}}catch(Exception e){
			   Message = "baseinfo.update.fail";
			   model.put("message", Message);
			   e.printStackTrace();
		   }
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
			
		}
		
		try{
			List<EventsAttention> tabList = null;
			tabList = eventsAttentionDao.findAll();
			System.out.println("after setting the tablist is---"+tabList);
			model.put("tabList", tabList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			Message = "baseinfo.list.error";		
			model.put("message", Message);
			e.printStackTrace();
		}
		
		
		model.put("method", method);
		return new ModelAndView(getPageView(),"model",model);
	}

}
