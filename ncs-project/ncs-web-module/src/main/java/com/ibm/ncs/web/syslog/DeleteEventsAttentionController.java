package com.ibm.ncs.web.syslog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.EventsAttentionDao;
import com.ibm.ncs.model.dao.ModuleEventTypeDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class DeleteEventsAttentionController implements Controller {
	
	 EventsAttentionDao eventsAttentionDao;
	
	 private String pageView;
	 private String message = "";
	 
	

	public EventsAttentionDao getEventsAttentionDao() {
		return eventsAttentionDao;
	}

	public void setEventsAttentionDao(EventsAttentionDao eventsAttentionDao) {
		this.eventsAttentionDao = eventsAttentionDao;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}




	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		

		message = "";
	    SyslogEventsProcess syslog = new SyslogEventsProcess();
		String[] marks = request.getParameterValues("del");
		System.out.println("marks are ---"+marks);
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		if(marks!= null)
		for(int i =0 ; i<marks.length;i++){
			String eventsAttention = marks[i];
			//try{
				List<EventsAttention> syslogs = eventsAttentionDao.findWhereEventsattentionEquals(eventsAttention);
				System.out.println("syslogs is-----"+syslogs);
				if(syslogs != null){
					if(syslogs.size()>0){
						EventsAttention syslogtemp = syslogs.get(0);
						System.out.println("syslogtemp is----"+syslogtemp);
						eventsAttentionDao.delete(syslogtemp);
					}
				}
				Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from syslogEventsProcessDao " + eventsAttention);
		}
		try{
			List<EventsAttention> tabList = null;
			tabList = eventsAttentionDao.findAll();
			model.put("tabList", tabList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			message = "baseinfo.list.error";		
			model.put("message", message);
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





	
}
