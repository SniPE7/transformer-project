package com.ibm.ncs.util.excel;


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
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
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class BackUpController implements Controller {
	
    String pageView;
	String Message = "";
	SnmpEventsProcessDao snmpEventsProcessDao;
	BkSnmpEventsProcessDao bkSnmpEventsProcessDao;
	
	
	private GenPkNumber genPkNumber;
	


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("backup controller invoked----");
		
		
		String mode = request.getParameter("checkmode");
		System.out.println("mode is :"+mode);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("mode", mode);
		try{
		 long time = System.currentTimeMillis();
		
		if(mode != null && mode.equalsIgnoreCase("snmp")){
			List<SnmpEventsProcess> snmps = snmpEventsProcessDao.findAll();
			
//		 java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			if(snmps != null){
            if(snmps.size()==0){
            	Message = "excel.backup.nodata";
            	model.put("Message", Message);
            	return new ModelAndView(getPageView(),"model",model);
            }
			if(snmps.size()>0){/*
				for(int i=0;i<snmps.size();i++){
				
				String mark = snmps.get(i).getMark();
				String manufacture = snmps.get(i).getManufacture();
				String resultlist = snmps.get(i).getResultlist();
				String warnmessage = snmps.get(i).getWarnmessage();
				String summary = snmps.get(i).getSummary();
				
				BkSnmpEventsProcess bkevent = new BkSnmpEventsProcess();
				long id = genPkNumber.getTaskID();
				bkevent.setBkId(id);
				
				
				bkevent.setBkTime(new Date(time));
				bkevent.setMark(mark);
				bkevent.setManufacture(manufacture);
				bkevent.setResultlist(resultlist);
				bkevent.setWarnmessage(warnmessage);
				bkevent.setSummary(summary);
				
				try{
					bkSnmpEventsProcessDao.insert(bkevent);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSnmpEventsProcessDao: " + bkevent.toString());
					Message = "excel.backup.success";
			        model.put("Message", Message);
				}catch(Exception e){
					Message = "excel.backup.error";
					model.put("Message", Message);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSnmpEventsProcessDao Failed : \tdto=" + bkevent.toString() + "\n" + e.getMessage());
				}
				
				
				}
				
			*/
				try{
					bkSnmpEventsProcessDao.batchInsertByBkTime(new Date(time));
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSnmpEventsProcessDao: " + new Date(time));
					Message = "excel.backup.success";
			        model.put("Message", Message);
				}catch(Exception e){
					Message = "excel.backup.error";
					model.put("Message", Message);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSnmpEventsProcessDao Failed : \tdto=" + new Date(time) + "\n" + e);
				}
			}
		}
			
		}
		}catch(DaoException e){
			e.printStackTrace();
			Message = "global.db.error";
			model.put("message", Message);
			return new ModelAndView("/dberror.jsp","model",model);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ModelAndView(getPageView(),"model",model);
		
	}			
	
	public void backupsnmp(){
		
	}

	public static void display(TCategoryMapInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getName() );
		buf.append( ", " );
		buf.append( dto.getFlag() );
		System.out.println( buf.toString() );
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

	public SnmpEventsProcessDao getSnmpEventsProcessDao() {
		return snmpEventsProcessDao;
	}

	public void setSnmpEventsProcessDao(SnmpEventsProcessDao snmpEventsProcessDao) {
		this.snmpEventsProcessDao = snmpEventsProcessDao;
	}


	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}




	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}

	public BkSnmpEventsProcessDao getBkSnmpEventsProcessDao() {
		return bkSnmpEventsProcessDao;
	}

	public void setBkSnmpEventsProcessDao(
			BkSnmpEventsProcessDao bkSnmpEventsProcessDao) {
		this.bkSnmpEventsProcessDao = bkSnmpEventsProcessDao;
	}

	
	
	

}
