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
import com.ibm.ncs.model.dao.LinesEventsNotcareDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.LinesEventsNotcare;
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

public class NewLinesNotCareController implements Controller {

	//SyslogBean form;
	LinesEventsNotcareDao linesEventsNotcareDao ;
	String pageView ;
	String message = "";
	




	public LinesEventsNotcareDao getLinesEventsNotcareDao() {
		return linesEventsNotcareDao;
	}



	public void setLinesEventsNotcareDao(LinesEventsNotcareDao linesEventsNotcareDao) {
		this.linesEventsNotcareDao = linesEventsNotcareDao;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		

		Map<String, Object> model = new HashMap<String, Object>();
		String linesnotcare = request.getParameter("linesnotcare");

		message = "";
		try {
						
			List<LinesEventsNotcare> lines = linesEventsNotcareDao.findWhereLinesnotcareEquals(linesnotcare);
						if(lines != null){
				if(lines .size()>0){
					message = "baseinfo.addsyslog.dul";
					model.put("message", message);
					model.put("linesnotcare", linesnotcare);
										
					return new ModelAndView("/secure/syslog/linesnotcare.jsp","model",model);
				}
			}
			
			
			
						
						LinesEventsNotcare line = new LinesEventsNotcare();
			
				line.setLinesnotcare(request.getParameter("linesnotcare"));
								
				linesEventsNotcareDao.insert(line);
				
			
			
		}catch (Exception e) {
			System.out.println("error-----------");
			message = "baseinfo.new.error";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			model.put("message", message);
			e.printStackTrace();
			model.put("linesnotcare", linesnotcare);
				
			return new ModelAndView("/secure/syslog/linesnotcare.jsp","model",model);
		}
		
		try{
			List<LinesEventsNotcare> tabList = null;
			tabList = linesEventsNotcareDao.findAll();
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
