package com.ibm.ncs.web.snmp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.ModuleEventTypeDao;
import com.ibm.ncs.model.dao.SnmpEventsProcessDao;
import com.ibm.ncs.model.dao.SnmpThresholdsDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class DeleteThresholdsController implements Controller {
	
	SnmpThresholdsDao SnmpThresholdsDao;
	
	 private String pageView;
	 private String message = "";
	 
	

	public SnmpThresholdsDao getSnmpThresholdsDao() {
		return SnmpThresholdsDao;
	}

	public void setSnmpThresholdsDao(SnmpThresholdsDao snmpThresholdsDao) {
		SnmpThresholdsDao = snmpThresholdsDao;
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
		SnmpThresholds snmpthreshold = new SnmpThresholds();
		String[] performances = request.getParameterValues("del");
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		if(performances!= null)
		for(int i =0 ; i<performances.length;i++){
			String performance = performances[i];
			//try{
				List<SnmpThresholds> snmpevents = SnmpThresholdsDao.findWherePerformanceEquals(performance);
				System.out.println("snmpevents is-----"+snmpevents);
				if(snmpevents != null){
					if(snmpevents.size()>0){
						SnmpThresholds snmptemp = snmpevents.get(0);
						SnmpThresholdsDao.delete(snmptemp);
					}
				}
				Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from SnmpEventsProcessDao " + performance);
			
			
		}
		try{
			List<SnmpThresholds> tabList = null;
			tabList = SnmpThresholdsDao.findAll();
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
