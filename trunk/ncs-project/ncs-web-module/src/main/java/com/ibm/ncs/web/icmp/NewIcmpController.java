package com.ibm.ncs.web.icmp;

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
import com.ibm.ncs.model.dao.IcmpThresholdsDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.IcmpThresholds;
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

public class NewIcmpController implements Controller {

	//SyslogBean form;
	IcmpThresholdsDao icmpThresholdsDao;
	String pageView ;
	String message = "";
	

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

		Map<String, Object> model = new HashMap<String, Object>();
		String ipaddress = request.getParameter("ipaddress");
		String 	btime	 	= request.getParameter("btime");
		String 	etime	 		= request.getParameter("etime");
		String threshold = request.getParameter("threshold");
		String comparetype = request.getParameter("comparetype");
		String 	filterflag1	 		= request.getParameter("filterflag1");
		String 	filterflag2	= request.getParameter("filterflag2");
		String 	severity1		 	= request.getParameter("severity1");
		String 	severity2	 	= request.getParameter("severity2");
		String varlist = request.getParameter("varlist");
		String 	summarycn	 	= request.getParameter("summarycn");
		
		try {
			
			
			List<IcmpThresholds> icmps = icmpThresholdsDao.findWhereIpaddressEquals(ipaddress);
			if(icmps != null){
				if(icmps.size()>0){
					message = "baseinfo.addsyslog.dul";
					model.put("message", message);
					model.put("ipaddress", ipaddress);
					model.put("btime", btime);
					model.put("etime", etime);
					model.put("threshold", threshold);
					model.put("filterflag1", filterflag1);
					model.put("filterflag2", filterflag2);
					model.put("severity1", severity1);
					model.put("severity2", severity2);
					model.put("summarycn", summarycn);
					model.put("varlist", varlist);
					model.put("comparetype", comparetype);
					
					return new ModelAndView("/secure/icmp/icmpthreshold.jsp","model",model);
				}
			}
			
			
			
			IcmpThresholds icmp = new IcmpThresholds();
			
				icmp.setIpaddress(ipaddress);
				icmp.setBtime(btime);
				icmp.setEtime(etime);
				icmp.setThreshold(threshold);
				icmp.setSeverity1(severity1);
				icmp.setSeverity2(severity2);
				icmp.setFilterflag1(Long.parseLong(filterflag1));
				icmp.setFilterflag2(Long.parseLong(filterflag2));
				icmp.setComparetype(comparetype);
				icmp.setSummarycn(summarycn);
				icmp.setVarlist(varlist);
				
				icmpThresholdsDao.insert(icmp);
				
			
			
		}catch (Exception e) {
			System.out.println("error-----------");
			message = "baseinfo.new.error";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			model.put("message", message);
			e.printStackTrace();
			
			model.put("ipaddress", ipaddress);
			model.put("btime", btime);
			model.put("etime", etime);
			model.put("threshold", threshold);
			model.put("filterflag1", filterflag1);
			model.put("filterflag2", filterflag2);
			model.put("severity1", severity1);
			model.put("severity2", severity2);
			model.put("summarycn", summarycn);
			model.put("varlist", varlist);
			model.put("comparetype", comparetype);
			return new ModelAndView("/secure/icmp/icmpthreshold.jsp","model",model);
		}
		
		try{
			List<IcmpThresholds> tabList = null;
			tabList = icmpThresholdsDao.findAll();
			model.put("tabList", tabList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			message = "listicmp.error";		
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
