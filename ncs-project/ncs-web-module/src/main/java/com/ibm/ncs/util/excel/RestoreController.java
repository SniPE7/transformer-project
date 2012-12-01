package com.ibm.ncs.util.excel;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.BkSnmpEventsProcessDao;
import com.ibm.ncs.model.dao.BkSyslogEventsProcessDao;
import com.ibm.ncs.model.dao.BkSyslogEventsProcessNsDao;
import com.ibm.ncs.model.dao.SnmpEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dto.BkSnmpEventsProcess;
import com.ibm.ncs.model.dto.BkSyslogEventsProcess;
import com.ibm.ncs.model.dto.BkSyslogEventsProcessNs;
import com.ibm.ncs.model.dto.SnmpEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.util.Log4jInit;

public class RestoreController implements Controller {
	String pageView;
	BkSnmpEventsProcessDao bkSnmpEventsProcessDao;
	SnmpEventsProcessDao snmpEventsProcessDao;
	
	
	
	String message = "";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("restore controller invoked----");
		Map<String,Object> model = new HashMap<String,Object>();
		String mode = request.getParameter("checkmode");
		System.out.println("mode in restore is :"+mode);
		model.put("mode", mode);
		String versionStr = request.getParameter("version");
		System.out.println("versionStr is :"+versionStr);
		 
		Timestamp timestamp = Timestamp.valueOf(versionStr);
		
	
		
		if(mode != null){
			if(mode.equalsIgnoreCase("snmp")){
				List<SnmpEventsProcess> snmps = snmpEventsProcessDao.findAll();
				System.out.println("snmps size is :"+snmps);
				if(snmps != null && snmps.size()>0){
					for(int i=0;i<snmps.size();i++){
						SnmpEventsProcess dto = snmps.get(i);
						try{
							System.out.println("delete begin-----");
						snmpEventsProcessDao.delete(dto);
						System.out.println("delete end-----");
						Log4jInit.ncsLog.info(this.getClass().getName()+"delete from snmpEventsProcessDao");
						}catch(Exception e){
							System.out.println("delete error-----");
							message = "excel.restore.deleteerror";
							model.put("Message", message);
							Log4jInit.ncsLog.error(this.getClass().getName()+"delete from snmpEventsProcessDao failed");
						    return new ModelAndView(getPageView(),"model",model);
						}
						
					}
					
				}
				
				List<BkSnmpEventsProcess> bksnmp = bkSnmpEventsProcessDao.findWhereBkTimeEquals(timestamp);
				System.out.println("bksnmp size is :"+bksnmp);
				if(bksnmp != null && bksnmp.size()>0){/*
					for(int i=0;i<bksnmp.size();i++){
						SnmpEventsProcess snmp = new SnmpEventsProcess();
						snmp.setMark(bksnmp.get(i).getMark());
						snmp.setManufacture(bksnmp.get(i).getManufacture());
						snmp.setResultlist(bksnmp.get(i).getResultlist());
						snmp.setWarnmessage(bksnmp.get(i).getWarnmessage());
						snmp.setSummary(bksnmp.get(i).getSummary());
						
						try{
							snmpEventsProcessDao.insert(snmp);
							Log4jInit.ncsLog.info(this.getClass().getName()+"Inserted to SnmpEventsProcessDao :" +snmp.toString());
						    message = "excel.restore.success";
						    model.put("Message", message);
						}catch(Exception e){
							message = "excel.restore.error";
							Log4jInit.ncsLog.error(this.getClass().getName()+"Inserted to SnmpEventsProcessDao Failed :\tdto=" + snmp.toString() + "\n" + e.getMessage());
							e.printStackTrace();
							model.put("Message", message);
							
						}
					}
				*/
					try{
						snmpEventsProcessDao.batchInsertByBkTime(timestamp);
						Log4jInit.ncsLog.info(this.getClass().getName()+"Inserted to SnmpEventsProcessDao :" +timestamp);
					    message = "excel.restore.success";
					    model.put("Message", message);
					}catch(Exception e){
						message = "excel.restore.error";
						Log4jInit.ncsLog.error(this.getClass().getName()+"Inserted to SnmpEventsProcessDao Failed :\tdto=" + timestamp + "\n" + e.getMessage());
						e.printStackTrace();
						model.put("Message", message);
						
					}
				}
				
			}
				
			}
		
		
		return new ModelAndView(getPageView(),"model",model);
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	

	public BkSnmpEventsProcessDao getBkSnmpEventsProcessDao() {
		return bkSnmpEventsProcessDao;
	}

	public void setBkSnmpEventsProcessDao(
			BkSnmpEventsProcessDao bkSnmpEventsProcessDao) {
		this.bkSnmpEventsProcessDao = bkSnmpEventsProcessDao;
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

	

}
