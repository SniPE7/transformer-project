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

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dao.SnmpEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.SnmpEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceInfoPk;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
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

public class NewSnmpEventController implements Controller {

	//SyslogBean form;
	SnmpEventsProcessDao snmpEventsProcessDao;
	String pageView ;
	String message = "";
	TManufacturerInfoInitDao TManufacturerInfoInitDao;

	



	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}



	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}



	public SnmpEventsProcessDao getSnmpEventsProcessDao() {
		return snmpEventsProcessDao;
	}



	public void setSnmpEventsProcessDao(SnmpEventsProcessDao snmpEventsProcessDao) {
		this.snmpEventsProcessDao = snmpEventsProcessDao;
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
		String mark = request.getParameter("mark");
		String 	manufacture		= request.getParameter("manufacture");
		String 	resultlist	 		= request.getParameter("resultlist");
		String 	warnmessage	 		= request.getParameter("warnmessage");
		String 	summary	 	= request.getParameter("summary");
		
		
		try {
		

			List<TManufacturerInfoInit> manus = TManufacturerInfoInitDao.findAll();			
			model.put("manus", manus);	
			
		/*	List<SnmpEventsProcess> snmpevents = snmpEventsProcessDao.findWhereMarkEquals(mark);
			if(snmpevents != null){
				if(snmpevents.size()>0){
					message = "baseinfo.addsyslog.dul";
					model.put("message", message);
					model.put("manufacture", manufacture);
					model.put("mark", mark);
					model.put("resultlist", resultlist);
					model.put("summary", summary);
					model.put("warnmessage", warnmessage);
					
					
					return new ModelAndView("/secure/snmp/snmpevent.jsp","model",model);
				}
			}*/
			
			SnmpEventsProcess snmpevent = new SnmpEventsProcess();
			
			snmpevent.setMark(mark);
			snmpevent.setManufacture(manufacture);
			snmpevent.setResultlist(resultlist);
			snmpevent.setSummary(summary);
			snmpevent.setWarnmessage(warnmessage);
			
			try{
			snmpEventsProcessDao.insert(snmpevent);
			Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to snmpEventsProcessDao: " + snmpevent.toString());
			
			}catch(DataIntegrityViolationException e){
				message = "baseinfo.snmpevent.error";
				model.put("message", message);
				model.put("manufacture", manufacture);
				model.put("mark", mark);
				model.put("resultlist", resultlist);
				model.put("summary", summary);
				model.put("warnmessage", warnmessage);
				Log4jInit.ncsLog.error(this.getClass().getName() + " Inserted to snmpEventsProcessDao Failed: " + snmpevent.toString() + "\n" + e);
				
				return new ModelAndView("/secure/snmp/snmpevent.jsp","model",model);
				
			}	
		    catch (Exception e) {
			System.out.println("error-----------");
			message = "baseinfo.new.failed";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Inserted to snmpEventsProcessDao Failed: " + snmpevent.toString() + "\n" + e);
			model.put("message", message);
			e.printStackTrace();
			model.put("mark", mark);
			model.put("manufacture", manufacture);
			model.put("resultlist", resultlist);
			model.put("summary", summary);
			model.put("warnmessage", warnmessage);
			return new ModelAndView("/secure/snmp/snmpevent.jsp","model",model);
		}
		
		try{
			List<SnmpEventsProcess> tabList = null;
			tabList = snmpEventsProcessDao.findAll();
			model.put("tabList", tabList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			message = "baseinfo.list.error";		
			model.put("message", message);
			e.printStackTrace();
		}
		}catch(Exception e){
			e.printStackTrace();
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
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
