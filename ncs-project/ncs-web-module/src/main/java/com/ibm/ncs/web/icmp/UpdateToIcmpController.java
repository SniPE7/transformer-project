package com.ibm.ncs.web.icmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.IcmpThresholdsDao;
import com.ibm.ncs.model.dao.SnmpThresholdsDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dto.IcmpThresholds;
import com.ibm.ncs.model.dto.SnmpThresholds;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.util.Log4jInit;

public class UpdateToIcmpController implements Controller{
	String pageView;
	String Message = "";
	IcmpThresholdsDao icmpThresholdsDao;
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





	public IcmpThresholdsDao getIcmpThresholdsDao() {
		return icmpThresholdsDao;
	}



	public void setIcmpThresholdsDao(IcmpThresholdsDao icmpThresholdsDao) {
		this.icmpThresholdsDao = icmpThresholdsDao;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try{
		String ipaddress = request.getParameter("ipaddress");
		List<IcmpThresholds> icmps = new ArrayList<IcmpThresholds>();
		
		if(ipaddress != null){
			icmps = icmpThresholdsDao.findWhereIpaddressEquals(ipaddress);
			if(icmps != null){
			   if(icmps.size() > 0){
				   IcmpThresholds icmp = new IcmpThresholds();
				   icmp = icmps.get(0);
				   System.out.println("the object is to updated is---------"+icmp);
				   
				    model.put("ipaddress", icmp.getIpaddress());
					model.put("btime", icmp.getBtime());
					model.put("etime", icmp.getEtime());
					model.put("comparetype", icmp.getComparetype());
					model.put("threshold", icmp.getThreshold());
					model.put("filterflag1", icmp.getFilterflag1());
					model.put("filterflag2", icmp.getFilterflag2());
					model.put("severity1", icmp.getSeverity1());
					model.put("severity2", icmp.getSeverity2());
					model.put("varlist", icmp.getVarlist());
					model.put("summarycn", icmp.getSummarycn());
					
				   
				   
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
