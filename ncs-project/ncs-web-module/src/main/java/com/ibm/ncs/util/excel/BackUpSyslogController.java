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

public class BackUpSyslogController implements Controller {
	
    String pageView;
	String Message = "";
	String messageforns = "";
	SyslogEventsProcessDao syslogEventsProcessDao;
	SyslogEventsProcessNsDao syslogEventsProcessNsDao;
	BkSyslogEventsProcessDao bkSyslogEventsProcessDao;
	BkSyslogEventsProcessNsDao bkSyslogEventsProcessNsDao;
	
	private GenPkNumber genPkNumber;
	


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("backup controller invoked----");
		
		/*String mode = request.getParameter("checkmode");
		System.out.println("mode is :"+mode);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("mode", mode);*/
		Map<String,Object> model = new HashMap<String,Object>();
		try{
		 long time = System.currentTimeMillis();
		

			List<SyslogEventsProcess> syslogs = syslogEventsProcessDao.findAll();
			
			List<SyslogEventsProcessNs> syslognss = syslogEventsProcessNsDao.findAll();
			System.out.println("syslog ns size is :"+syslognss.size());
			//		 java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			if(syslogs != null && syslognss != null){
				if(syslogs.size()==0 && syslognss.size()==0){
					Message = "excel.backup.nodata";
					model.put("Message", Message);
					return new ModelAndView(getPageView(),"model",model);
				}else{
			        if(syslogs.size()== 0){
			         	Message = "excel.backup.syslognodata";
			        	model.put("Message", Message);
			        	BkSyslogEventsProcess bkevent = new BkSyslogEventsProcess();
						long id = genPkNumber.getTaskID();
						bkevent.setBkId(id);
						
						bkevent.setBkTime(new Date(time));
						bkevent.setMark(null);
						bkevent.setManufacture(null);
						bkevent.setVarlist(null);
						bkevent.setBtimelist(null);
						bkevent.setEtimelist(null);
						bkevent.setFilterflag1Null(true);
						bkevent.setFilterflag2Null(true);
						bkevent.setSeverity1Null(true);
						bkevent.setSeverity2Null(true);
						bkevent.setPort(null);
						bkevent.setNotcareflagNull(true);
						bkevent.setTypeNull(true);
						bkevent.setEventtypeNull(true);
						bkevent.setSubeventtypeNull(true);
						bkevent.setAlertgroup(null);
						bkevent.setAlertkey(null);
						bkevent.setSummarycn(null);
						bkevent.setProcesssuggest(null);
						bkevent.setStatus(null);
						bkevent.setAttentionflagNull(true);
						bkevent.setEvents(null);
						bkevent.setOrigevent(null);
						
						try{
							bkSyslogEventsProcessDao.insert(bkevent);
							Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessDao: " + bkevent.toString());
						    
						}catch(Exception e){
							Message = "excel.insert.error";
							model.put("Message", Message);
							Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessDao Failed : \tdto=" + bkevent.toString() + "\n" + e.getMessage());
							return new ModelAndView(getPageView(),"model",model);
						}
		            	}
		        	if(syslognss.size() == 0){
				       messageforns = "excel.backup.syslognsnodata";
				       model.put("messageforns", messageforns);
				       
				       BkSyslogEventsProcessNs bkeventns = new BkSyslogEventsProcessNs();
						long id = genPkNumber.getTaskID();
						bkeventns.setBkId(id);
						
						bkeventns.setBkTime(new Date(time));
						bkeventns.setMark(null);
						bkeventns.setManufacture(null);
						bkeventns.setVarlist(null);
						bkeventns.setBtimelist(null);
						bkeventns.setEtimelist(null);
						bkeventns.setFilterflag1Null(true);
						bkeventns.setFilterflag2Null(true);
						bkeventns.setSeverity1Null(true);
						bkeventns.setSeverity2Null(true);
						bkeventns.setPort(null);
						bkeventns.setNotcareflagNull(true);
						bkeventns.setTypeNull(true);
						bkeventns.setEventtypeNull(true);
						bkeventns.setSubeventtypeNull(true);
						bkeventns.setAlertgroup(null);
						bkeventns.setAlertkey(null);
						bkeventns.setSummarycn(null);
						bkeventns.setProcesssuggest(null);
						bkeventns.setStatus(null);
						bkeventns.setAttentionflagNull(true);
						bkeventns.setEvents(null);
						bkeventns.setOrigevent(null);
						
						try{
							bkSyslogEventsProcessNsDao.insert(bkeventns);
							Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessNsDao: " + bkeventns.toString());
						}catch(Exception e){
							messageforns = "excel.nsinsert.error";
							 model.put("messageforns", messageforns);
							Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessNsDao Failed : \tdto=" + bkeventns.toString() + "\n" + e.getMessage());
						    return new ModelAndView(getPageView(),"model",model);
						}
						
				       
			           }
				}

			if(syslogs.size()>0 ){
				/*
				for(int i=0;i<syslogs.size();i++){
				
				String mark = syslogs.get(i).getMark();
				String manufacture = syslogs.get(i).getManufacture();
				String varlist = syslogs.get(i).getVarlist();
				String btime = syslogs.get(i).getBtimelist();
				String etime = syslogs.get(i).getEtimelist();
				long filterflag1 = syslogs.get(i).getFilterflag1();
				System.out.println("flag1 is :"+filterflag1);
				long filterflag2 = syslogs.get(i).getFilterflag2();
				long severity1 = syslogs.get(i).getSeverity1();
				long severity2 = syslogs.get(i).getSeverity2();
				String port = syslogs.get(i).getPort();
				long notcareflag = syslogs.get(i).getNotcareflag();
				long type = syslogs.get(i).getType();
				long eventtype = syslogs.get(i).getEventtype();
				long subeventtype = syslogs.get(i).getSubeventtype();
				String alertgroup = syslogs.get(i).getAlertgroup();
				String alertkey = syslogs.get(i).getAlertkey();
				String summarycn = syslogs.get(i).getSummarycn();
				String processsugguest = syslogs.get(i).getProcesssuggest();
				String status = syslogs.get(i).getStatus();
				long attentionflag = syslogs.get(i).getAttentionflag();
				String events = syslogs.get(i).getEvents();
				String origevent = syslogs.get(i).getOrigevent();
				
				
				BkSyslogEventsProcess bkevent = new BkSyslogEventsProcess();
				long id = genPkNumber.getTaskID();
				bkevent.setBkId(id);
				
				bkevent.setBkTime(new Date(time));
				bkevent.setMark(mark);
				bkevent.setManufacture(manufacture);
				bkevent.setVarlist(varlist);
				bkevent.setBtimelist(btime);
				bkevent.setEtimelist(etime);
				bkevent.setFilterflag1(filterflag1);
				bkevent.setFilterflag2(filterflag2);
				bkevent.setSeverity1(severity1);
				bkevent.setSeverity2(severity2);
				bkevent.setPort(port);
				bkevent.setNotcareflag(notcareflag);
				bkevent.setType(type);
				bkevent.setEventtype(eventtype);
				bkevent.setSubeventtype(subeventtype);
				bkevent.setAlertgroup(alertgroup);
				bkevent.setAlertkey(alertkey);
				bkevent.setSummarycn(summarycn);
				bkevent.setProcesssuggest(processsugguest);
				bkevent.setStatus(status);
				bkevent.setAttentionflag(attentionflag);
				bkevent.setEvents(events);
				bkevent.setOrigevent(origevent);
				
				
				try{
					bkSyslogEventsProcessDao.insert(bkevent);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessDao: " + bkevent.toString());
				    
				}catch(Exception e){
					Message = "excel.insert.error";
					model.put("Message", Message);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessDao Failed : \tdto=" + bkevent.toString() + "\n" + e.getMessage());
					return new ModelAndView(getPageView(),"model",model);
				}
			
				}
				*/
				
				try{
					bkSyslogEventsProcessDao.batchInsertByBkTime(new Date(time));
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessDao: " + time);
				    
				}catch(Exception e){
					Message = "excel.insert.error";
					model.put("Message", Message);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessDao Failed : \tdto=" +time + "\n" + e);
					return new ModelAndView(getPageView(),"model",model);
				}

				  Message = "excel.insert.success";
		          model.put("Message", Message);
				
			}
			
			if(syslognss.size()>0){

/*
				for(int i=0;i<syslognss.size();i++){
				
				String mark = syslognss.get(i).getMark();
				String manufacture = syslognss.get(i).getManufacture();
				String varlist = syslognss.get(i).getVarlist();
				String btime = syslognss.get(i).getBtimelist();
				String etime = syslognss.get(i).getEtimelist();
				long filterflag1 = syslognss.get(i).getFilterflag1();
				long filterflag2 = syslognss.get(i).getFilterflag2();
				long severity1 = syslognss.get(i).getSeverity1();
				long severity2 = syslognss.get(i).getSeverity2();
				String port = syslognss.get(i).getPort();
				long notcareflag = syslognss.get(i).getNotcareflag();
				long type = syslognss.get(i).getType();
				long eventtype = syslognss.get(i).getEventtype();
				long subeventtype = syslognss.get(i).getSubeventtype();
				String alertgroup = syslognss.get(i).getAlertgroup();
				String alertkey = syslognss.get(i).getAlertkey();
				String summarycn = syslognss.get(i).getSummarycn();
				String processsugguest = syslognss.get(i).getProcesssuggest();
				String status = syslognss.get(i).getStatus();
				long attentionflag = syslognss.get(i).getAttentionflag();
				String events = syslognss.get(i).getEvents();
				String origevent = syslognss.get(i).getOrigevent();
				
				
				BkSyslogEventsProcessNs bkeventns = new BkSyslogEventsProcessNs();
				long id = genPkNumber.getTaskID();
				bkeventns.setBkId(id);
				
				bkeventns.setBkTime(new Date(time));
				bkeventns.setMark(mark);
				bkeventns.setManufacture(manufacture);
				bkeventns.setVarlist(varlist);
				bkeventns.setBtimelist(btime);
				bkeventns.setEtimelist(etime);
				
				bkeventns.setFilterflag1(filterflag1);
				bkeventns.setFilterflag2(filterflag2);
				bkeventns.setSeverity1(severity1);
				bkeventns.setSeverity2(severity2);
				bkeventns.setPort(port);
				bkeventns.setNotcareflag(notcareflag);
				bkeventns.setType(type);
				bkeventns.setEventtype(eventtype);
				bkeventns.setSubeventtype(subeventtype);
				bkeventns.setAlertgroup(alertgroup);
				bkeventns.setAlertkey(alertkey);
				bkeventns.setSummarycn(summarycn);
				bkeventns.setProcesssuggest(processsugguest);
				bkeventns.setStatus(status);
				bkeventns.setAttentionflag(attentionflag);
				bkeventns.setEvents(events);
				bkeventns.setOrigevent(origevent);
				
				try{
					bkSyslogEventsProcessNsDao.insert(bkeventns);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessNsDao: " + bkeventns.toString());
				}catch(Exception e){
					messageforns = "excel.nsinsert.error";
					 model.put("messageforns", messageforns);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessNsDao Failed : \tdto=" + bkeventns.toString() + "\n" + e.getMessage());
				    return new ModelAndView(getPageView(),"model",model);
				}
				}
				*/
				try{
					bkSyslogEventsProcessNsDao.batchInsertByBkTime(new Date(time));
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessNsDao: " + new Date(time));
				}catch(Exception e){
					messageforns = "excel.nsinsert.error";
					 model.put("messageforns", messageforns);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessNsDao Failed : \tdto=" + new Date(time) + "\n" + e);
				    return new ModelAndView(getPageView(),"model",model);
				}
				 messageforns = "excel.nsinsert.success";
		          model.put("messageforns", messageforns);
				 
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


	public SyslogEventsProcessDao getSyslogEventsProcessDao() {
		return syslogEventsProcessDao;
	}

	public void setSyslogEventsProcessDao(
			SyslogEventsProcessDao syslogEventsProcessDao) {
		this.syslogEventsProcessDao = syslogEventsProcessDao;
	}




	public SyslogEventsProcessNsDao getSyslogEventsProcessNsDao() {
		return syslogEventsProcessNsDao;
	}




	public void setSyslogEventsProcessNsDao(
			SyslogEventsProcessNsDao syslogEventsProcessNsDao) {
		this.syslogEventsProcessNsDao = syslogEventsProcessNsDao;
	}




	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}




	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}

	public BkSyslogEventsProcessDao getBkSyslogEventsProcessDao() {
		return bkSyslogEventsProcessDao;
	}

	public void setBkSyslogEventsProcessDao(
			BkSyslogEventsProcessDao bkSyslogEventsProcessDao) {
		this.bkSyslogEventsProcessDao = bkSyslogEventsProcessDao;
	}

	public BkSyslogEventsProcessNsDao getBkSyslogEventsProcessNsDao() {
		return bkSyslogEventsProcessNsDao;
	}

	public void setBkSyslogEventsProcessNsDao(
			BkSyslogEventsProcessNsDao bkSyslogEventsProcessNsDao) {
		this.bkSyslogEventsProcessNsDao = bkSyslogEventsProcessNsDao;
	}

	public String getMessageforns() {
		return messageforns;
	}

	public void setMessageforns(String messageforns) {
		this.messageforns = messageforns;
	}
	
	
	
	
	
	

}
