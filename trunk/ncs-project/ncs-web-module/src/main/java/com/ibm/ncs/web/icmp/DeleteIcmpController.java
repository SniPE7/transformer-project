package com.ibm.ncs.web.icmp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.IcmpThresholdsDao;
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

public class DeleteIcmpController implements Controller {
	
	IcmpThresholdsDao icmpThresholdsDao;
	
	 private String pageView;
	 private String message = "";
	 
	

	public IcmpThresholdsDao getIcmpThresholdsDao() {
		return icmpThresholdsDao;
	}

	public void setIcmpThresholdsDao(IcmpThresholdsDao icmpThresholdsDao) {
		this.icmpThresholdsDao = icmpThresholdsDao;
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
		IcmpThresholds icmp = new IcmpThresholds();
		String[] ipaddresses = request.getParameterValues("del");
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		if(ipaddresses!= null)
		for(int i =0 ; i<ipaddresses.length;i++){
			String ipaddress = ipaddresses[i];
			//try{
				List<IcmpThresholds> icmps = icmpThresholdsDao.findWhereIpaddressEquals(ipaddress);
				System.out.println("icmp is-----"+icmp);
				if(icmps != null){
					if(icmps.size()>0){
						IcmpThresholds icmptemp = icmps.get(0);
						icmpThresholdsDao.delete(icmptemp);
					}
				}
				Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from SnmpEventsProcessDao " + ipaddress);
			
			
		}
		try{
			List<IcmpThresholds> tabList = null;
			tabList = icmpThresholdsDao.findAll();
			model.put("tabList", tabList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			message = "baseinfo.delete.error";		
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
