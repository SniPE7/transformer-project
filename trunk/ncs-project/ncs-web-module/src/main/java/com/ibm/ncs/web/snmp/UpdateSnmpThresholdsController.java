package com.ibm.ncs.web.snmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.SnmpThresholdsDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dto.SnmpThresholds;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.util.Log4jInit;

public class UpdateSnmpThresholdsController implements Controller{
	String pageView;
	String Message = "";
	SnmpThresholdsDao SnmpThresholdsDao;
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



	public void setMessage(String message) {
		Message = message;
	}





	public SnmpThresholdsDao getSnmpThresholdsDao() {
		return SnmpThresholdsDao;
	}



	public void setSnmpThresholdsDao(SnmpThresholdsDao snmpThresholdsDao) {
		SnmpThresholdsDao = snmpThresholdsDao;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try{
		String performance = request.getParameter("performance");
		String 	btime	 	= request.getParameter("btime");
		String 	etime	 		= request.getParameter("etime");
		String 	threshold	 	= request.getParameter("threshold");
		String 	comparetype	 		= request.getParameter("comparetype");
		String 	filterflag1	 		= request.getParameter("filterflag1");
		String 	filterflag2	= request.getParameter("filterflag2");
		String 	severity1		 	= request.getParameter("severity1");
		String 	severity2	 	= request.getParameter("severity2");
		
		
		List<SnmpThresholds> snmpthresholds = new ArrayList<SnmpThresholds>();
		try{
		if(performance != null){
		    snmpthresholds = SnmpThresholdsDao.findWherePerformanceEquals(performance);
			System.out.println("snmpthresholds size is---"+snmpthresholds);
			if(snmpthresholds != null){
			   if(snmpthresholds.size() > 0){
				   SnmpThresholds snmpthreshold = new SnmpThresholds();
				   snmpthreshold = snmpthresholds.get(0);
				   
				   snmpthreshold.setBtime(btime);
				   snmpthreshold.setComparetype(comparetype);
				   snmpthreshold.setEtime(etime);
				   snmpthreshold.setFilterflag1(Long.parseLong(filterflag1));
				   snmpthreshold.setFilterflag2(Long.parseLong(filterflag2));
				   snmpthreshold.setPerformance(performance);
				   snmpthreshold.setSeverity1(severity1);
				   snmpthreshold.setSeverity2(severity2);
				   snmpthreshold.setThreshold(threshold);
				   
					
					SnmpThresholdsDao.update(snmpthreshold);
					
					 model.put("performance", snmpthreshold.getPerformance());
						model.put("threshold", snmpthreshold.getThreshold());
						model.put("btime", snmpthreshold.getBtime());
						model.put("etime", snmpthreshold.getEtime());
						
						model.put("filterflag1", snmpthreshold.getFilterflag1());
						model.put("filterflag2", snmpthreshold.getFilterflag2());
						
						model.put("severity1", snmpthreshold.getSeverity1());
						model.put("severity2", snmpthreshold.getSeverity2());
			   
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
			List<SnmpThresholds> tabList = null;
			tabList = SnmpThresholdsDao.findAll();
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
