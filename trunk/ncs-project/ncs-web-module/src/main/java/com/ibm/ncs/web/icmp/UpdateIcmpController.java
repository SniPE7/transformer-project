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

public class UpdateIcmpController implements Controller{
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
		String 	btime	 	= request.getParameter("btime");
		String 	etime	 		= request.getParameter("etime");
		String 	threshold	 	= request.getParameter("threshold");
		String 	comparetype	 		= request.getParameter("comparetype");
		String 	filterflag1	 		= request.getParameter("filterflag1");
		String 	filterflag2	= request.getParameter("filterflag2");
		String 	severity1		 	= request.getParameter("severity1");
		String 	severity2	 	= request.getParameter("severity2");
		String varlist = request.getParameter("varlist");
		String 	summarycn	 	= request.getParameter("summarycn");
		
		
		List<IcmpThresholds> icmps = new ArrayList<IcmpThresholds>();
		try{
		if(ipaddress != null){
		    icmps = icmpThresholdsDao.findWhereIpaddressEquals(ipaddress);
			System.out.println("icmps size is---"+icmps);
			if(icmps != null){
			   if(icmps.size() > 0){
				   IcmpThresholds icmp = new IcmpThresholds();
				   icmp = icmps.get(0);
				   
				   icmp.setComparetype(comparetype);
				   icmp.setBtime(btime);
				   icmp.setEtime(etime);
				   icmp.setFilterflag1(Long.parseLong(filterflag1));
				   icmp.setFilterflag2(Long.parseLong(filterflag2));
				   icmp.setSeverity1(severity1);
				   icmp.setSeverity2(severity2);
				   icmp.setThreshold(threshold);
				   icmp.setVarlist(varlist);
				   icmp.setSummarycn(summarycn);
				   
				   
				   icmpThresholdsDao.update(icmp);
					
					 model.put("ipaddress", icmp.getIpaddress());
						model.put("threshold", icmp.getThreshold());
						model.put("btime", icmp.getBtime());
						model.put("etime", icmp.getEtime());
						model.put("comparetype", icmp.getComparetype());
						
						model.put("filterflag1", icmp.getFilterflag1());
						model.put("filterflag2", icmp.getFilterflag2());
						
						model.put("severity1", icmp.getSeverity1());
						model.put("severity2", icmp.getSeverity2());
						model.put("varlist", icmp.getVarlist());
						model.put("summarycn", icmp.getSummarycn());
			   
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
			List<IcmpThresholds> tabList = null;
			tabList = icmpThresholdsDao.findAll();
			model.put("tabList", tabList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			Message = "baseinfo.update.fail";		
			model.put("message", Message);
			e.printStackTrace();
		}
		
		
		model.put("method", method);
		return new ModelAndView(getPageView(),"model",model);
	}

}
