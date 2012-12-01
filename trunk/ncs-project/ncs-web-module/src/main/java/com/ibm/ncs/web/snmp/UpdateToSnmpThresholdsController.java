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

public class UpdateToSnmpThresholdsController implements Controller{
	String pageView;
	String Message = "";
	SnmpThresholdsDao snmpThresholdsDao;
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
		return snmpThresholdsDao;
	}



	public void setSnmpThresholdsDao(SnmpThresholdsDao snmpThresholdsDao) {
		this.snmpThresholdsDao = snmpThresholdsDao;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try{
		String performance = request.getParameter("performance");
		List<SnmpThresholds> snmpthresholds = new ArrayList<SnmpThresholds>();
		
		if(performance != null){
			snmpthresholds = snmpThresholdsDao.findWherePerformanceEquals(performance);
			if(snmpthresholds != null){
			   if(snmpthresholds.size() > 0){
				   SnmpThresholds snmpthreshold = new SnmpThresholds();
				   snmpthreshold = snmpthresholds.get(0);
				   System.out.println("the object is to updated is---------"+snmpthreshold);
				   
				   model.put("performance", snmpthreshold.getPerformance());
				
					model.put("btime", snmpthreshold.getBtime());
					model.put("etime", snmpthreshold.getEtime());
					model.put("threshold", snmpthreshold.getThreshold());
					model.put("comparetype", snmpthreshold.getComparetype());
					model.put("filterflag1", snmpthreshold.getFilterflag1());
					model.put("filterflag2", snmpthreshold.getFilterflag2());
					model.put("severity1", snmpthreshold.getSeverity1());
					model.put("severity2", snmpthreshold.getSeverity2());
					
				   
				   
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
