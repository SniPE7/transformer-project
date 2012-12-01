package com.ibm.ncs.web.snmp;

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
import com.ibm.ncs.model.dao.SnmpEventsProcessDao;
import com.ibm.ncs.model.dao.SnmpThresholdsDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.SnmpEventsProcess;
import com.ibm.ncs.model.dto.SnmpThresholds;
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

public class NewSnmpThresholdController implements Controller {

	//SyslogBean form;
	SnmpThresholdsDao snmpThresholdsDao;
	String pageView ;
	String message = "";
	



	public SnmpThresholdsDao getSnmpThresholdsDao() {
		return snmpThresholdsDao;
	}



	public void setSnmpThresholdsDao(SnmpThresholdsDao snmpThresholdsDao) {
		this.snmpThresholdsDao = snmpThresholdsDao;
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
		String performance = request.getParameter("performance");
		String threshold = request.getParameter("threshold");
		String comparetype = request.getParameter("comparetype");
		String 	btime	 	= request.getParameter("btime");
		String 	etime	 		= request.getParameter("etime");
		String 	filterflag1	 		= request.getParameter("filterflag1");
		String 	filterflag2	= request.getParameter("filterflag2");
		String 	severity1		 	= request.getParameter("severity1");
		String 	severity2	 	= request.getParameter("severity2");
		
		
		try {
		
			
			List<SnmpThresholds> snmpthresholds = snmpThresholdsDao.findWherePerformanceEquals(performance);
			if(snmpthresholds != null){
				if(snmpthresholds.size()>0){
					message = "baseinfo.addsyslog.dul";
					model.put("message", message);
					
					model.put("performance", performance);
					model.put("btime", btime);
					model.put("etime", etime);
					model.put("filterflag1", filterflag1);
					model.put("filterflag2", filterflag2);
					model.put("severity1", severity1);
					model.put("severity2", severity2);
					model.put("threshold", threshold);
					model.put("comparetype", comparetype);
				
					
					
					return new ModelAndView("/secure/snmp/snmpthreshold.jsp","model",model);
				}
			}
			
			SnmpThresholds snmpthreshold = new SnmpThresholds();
			snmpthreshold.setBtime(btime);
			snmpthreshold.setComparetype(comparetype);
			snmpthreshold.setEtime(etime);
			snmpthreshold.setFilterflag1(Long.parseLong(filterflag1));
			snmpthreshold.setFilterflag2(Long.parseLong(filterflag2));
			snmpthreshold.setPerformance(performance);
			snmpthreshold.setSeverity1(severity1);
			snmpthreshold.setSeverity2(severity2);
			snmpthreshold.setThreshold(threshold);
			
		
			
			
				
			snmpThresholdsDao.insert(snmpthreshold);
				
			
			
		}catch (Exception e) {
			System.out.println("error-----------");
			message = "baseinfo.new.error";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			model.put("message", message);
			e.printStackTrace();
			model.put("performance", performance);
			model.put("btime", btime);
			model.put("etime", etime);
			model.put("filterflag1", filterflag1);
			model.put("filterflag2", filterflag2);
			model.put("severity1", severity1);
			model.put("severity2", severity2);
			model.put("threshold", threshold);
			model.put("comparetype", comparetype);
			return new ModelAndView("/secure/snmp/snmpthreshold.jsp","model",model);
		}
		
		try{
			List<SnmpThresholds> tabList = null;
			tabList = snmpThresholdsDao.findAll();
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
