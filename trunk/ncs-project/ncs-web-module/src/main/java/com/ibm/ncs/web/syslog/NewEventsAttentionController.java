package com.ibm.ncs.web.syslog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dao.EventsAttentionDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.EventsAttention;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceInfoPk;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.dto.TPortInfoPk;
import com.ibm.ncs.model.exceptions.SequenceNMDaoException;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.exceptions.TDeviceTypeInitDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.service.ResourceManServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.IPAddr;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.web.resourceman.bean.DeviceInfoFormBean;
import com.ibm.ncs.web.syslog.bean.SyslogBean;

public class NewEventsAttentionController implements Controller {

	//SyslogBean form;
	EventsAttentionDao eventsAttentionDao;
	String pageView ;
	String message = "";
	
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
		Map<String, Object> model = new HashMap<String, Object>();
		String eventsAttention = request.getParameter("eventsAttention");
		String 	severity	 		= request.getParameter("severity");
		String 	processSuggest	 	= request.getParameter("processSuggest");
		System.out.println("the eventsAttention from jsp is---"+eventsAttention+"severity from jsp is---"+severity
				+"processSuggest from jsp is---"+processSuggest);
		try {
			
			List<EventsAttention> evnts = eventsAttentionDao.findWhereEventsattentionEquals(eventsAttention);
			//System.out.println("syslogs is null-----------?"+syslogs.isEmpty());
			if(evnts != null){
				if(evnts.size()>0){
					message = "baseinfo.addsyslog.dul";
					model.put("message", message);
					model.put("eventsAttention", eventsAttention);
					model.put("severity", severity);
					model.put("processSuggest", processSuggest);
					return new ModelAndView("/secure/syslog/eventsAttention.jsp","model",model);
				}
			}
			
			EventsAttention eventAttention = new EventsAttention();
			
			eventAttention.setEventsattention(eventsAttention);
			eventAttention.setProcesssuggest(processSuggest);
			eventAttention.setSeverity(Long.parseLong(severity));
			
			eventsAttentionDao.insert(eventAttention);	
			
			System.out.println("eventAttention is------"+eventAttention);
			model.put("eventsAttention", eventAttention.getEventsattention());
			model.put("severity", eventAttention.getSeverity());
			model.put("processSuggest", eventAttention.getProcesssuggest());
			
		}catch (Exception e) {
			System.out.println("error-----------");
			message = "baseinfo.new.error";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			model.put("message", message);
			e.printStackTrace();
			model.put("eventsAttention", eventsAttention);
			model.put("severity", severity);
			model.put("processSuggest", processSuggest);
			return new ModelAndView("/secure/syslog/eventsAttention.jsp","model",model);
		}
		
		try{
			List<EventsAttention> tabList = null;
			tabList = eventsAttentionDao.findAll();
			System.out.println("the size of tablist is"+tabList);
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

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public String getPageView() {
		return pageView;
	}



}
