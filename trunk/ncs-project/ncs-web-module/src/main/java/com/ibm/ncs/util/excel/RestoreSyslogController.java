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

public class RestoreSyslogController implements Controller {
	String pageView;
	BkSyslogEventsProcessDao bkSyslogEventsProcessDao;
	BkSyslogEventsProcessNsDao bkSyslogEventsProcessNsDao;
	SyslogEventsProcessDao syslogEventsProcessDao;
	SyslogEventsProcessNsDao syslogEventsProcessNsDao;
	
	
	String message = "";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("restore controller invoked----");
		Map<String,Object> model = new HashMap<String,Object>();
		/*String mode = request.getParameter("checkmode");
		System.out.println("mode in restore is :"+mode);
		model.put("mode", mode);*/
		String versionStr = request.getParameter("version");
		System.out.println("versionStr is :"+versionStr);
		 
		Timestamp timestamp = Timestamp.valueOf(versionStr);
		
	
		
				List<SyslogEventsProcess> syslogs = syslogEventsProcessDao.findAll();
				List<SyslogEventsProcessNs> syslognss = syslogEventsProcessNsDao.findAll();
				System.out.println("syslog size is :"+syslogs.size()+" and syslog ns size is :"+syslognss.size());
				
				if((syslogs!= null )&& (syslognss != null)){
					System.out.println("---------------");
					if(syslogs.size()>0 ){/*
					for(int i=0;i<syslogs.size();i++){
						SyslogEventsProcess syslog = syslogs.get(i);
						try{
							syslogEventsProcessDao.delete(syslog);
							System.out.println("delete syslog end-----");
							Log4jInit.ncsLog.info(this.getClass().getName()+"delete from snmpEventsProcessDao");
						}catch(Exception e){
							message = "excel.restore.deleteerror";
							model.put("Message", message);
							Log4jInit.ncsLog.error(this.getClass().getName()+"delete from snmpEventsProcessDao failed");
						    return new ModelAndView(getPageView(),"model",model);
						}
					}
					*/
						try{
						syslogEventsProcessDao.deleteAll();
						Log4jInit.ncsLog.info(this.getClass().getName()+"delete from snmpEventsProcessDao");
						}catch(Exception e){
							message = "excel.restore.deleteerror";
							model.put("Message", message);
							Log4jInit.ncsLog.error(this.getClass().getName()+"delete from snmpEventsProcessDao failed");
						    return new ModelAndView(getPageView(),"model",model);
						}
					}
					
					if(syslognss.size()>0){/*
					for(int i=0;i<syslognss.size();i++){
						SyslogEventsProcessNs syslogns = syslognss.get(i);
						try{
							syslogEventsProcessNsDao.delete(syslogns);
							System.out.println("delete syslogns end-----");
							Log4jInit.ncsLog.info(this.getClass().getName()+"delete from syslogEventsProcessNsDao");
						}catch(Exception e){
							message = "excel.restore.deleteerror";
							model.put("Message", message);
							Log4jInit.ncsLog.error(this.getClass().getName()+"delete from snmpEventsProcessNsDao failed");
						    return new ModelAndView(getPageView(),"model",model);
						}
					}
					*/
						try{
							syslogEventsProcessNsDao.deleteAll();
							System.out.println("delete syslogns end-----");
							Log4jInit.ncsLog.info(this.getClass().getName()+"delete from syslogEventsProcessNsDao");
						}catch(Exception e){
							message = "excel.restore.deleteerror";
							model.put("Message", message);
							Log4jInit.ncsLog.error(this.getClass().getName()+"delete from snmpEventsProcessNsDao failed");
						    return new ModelAndView(getPageView(),"model",model);
						}
					}
					List<BkSyslogEventsProcess> bksyslogs = bkSyslogEventsProcessDao.findWhereBkTimeEquals(timestamp);
					List<BkSyslogEventsProcessNs> bksyslognss = bkSyslogEventsProcessNsDao.findWhereBkTimeEquals(timestamp);
					System.out.println("bksyslog size is :"+bksyslogs.size()+" and bksyslog ns size is :"+bksyslognss.size());
					if(bksyslogs.size()>0){
						if(bksyslogs.size() == 1 && bksyslogs.get(0).getMark() == null){}else{
							
						
						
						/*
						for(int i=0;i<bksyslogs.size();i++){
							SyslogEventsProcess syslog = new SyslogEventsProcess();
							syslog.setMark(bksyslogs.get(i).getMark());
							syslog.setManufacture(bksyslogs.get(i).getManufacture());
							syslog.setBtimelist(bksyslogs.get(i).getBtimelist());
							syslog.setEtimelist(bksyslogs.get(i).getEtimelist());
							syslog.setFilterflag1(bksyslogs.get(i).getFilterflag1());
							syslog.setFilterflag2(bksyslogs.get(i).getFilterflag2());
							syslog.setSeverity1(bksyslogs.get(i).getSeverity1());
							syslog.setSeverity2(bksyslogs.get(i).getSeverity2());
							syslog.setPort(bksyslogs.get(i).getPort());
							syslog.setNotcareflag(bksyslogs.get(i).getNotcareflag());
							syslog.setType(bksyslogs.get(i).getType());
							syslog.setEventtype(bksyslogs.get(i).getEventtype());
							syslog.setSubeventtype(bksyslogs.get(i).getSubeventtype());
							syslog.setAlertgroup(bksyslogs.get(i).getAlertgroup());
							syslog.setAlertkey(bksyslogs.get(i).getAlertkey());
							syslog.setSummarycn(bksyslogs.get(i).getSummarycn());
							syslog.setProcesssuggest(bksyslogs.get(i).getProcesssuggest());
							syslog.setStatus(bksyslogs.get(i).getStatus());
							syslog.setAttentionflag(bksyslogs.get(i).getAttentionflag());
							syslog.setEvents(bksyslogs.get(i).getEvents());
							syslog.setOrigevent(bksyslogs.get(i).getOrigevent());
							syslog.setVarlist(bksyslogs.get(i).getVarlist());
							
							try{
								syslogEventsProcessDao.insert(syslog);
								Log4jInit.ncsLog.info(this.getClass().getName()+"Inserted to syslogEventsProcessDao :" +syslog.toString());
							//    message = "excel.restore.success";
							 //   model.put("Message", message);
							}catch(Exception e){
								message = "excel.restore.error";
								Log4jInit.ncsLog.error(this.getClass().getName()+"Inserted to syslogEventsProcessDao Failed :\tdto=" + syslog.toString() + "\n" + e.getMessage());
								e.printStackTrace();
								model.put("Message", message);
								 return new ModelAndView(getPageView(),"model",model);
							}
							
						}
						*/
						
						try{
							syslogEventsProcessDao.batchInsertByBkTime(timestamp);
							Log4jInit.ncsLog.info(this.getClass().getName()+"Inserted to syslogEventsProcessDao :" +timestamp);
						//    message = "excel.restore.success";
						 //   model.put("Message", message);
						}catch(Exception e){
							message = "excel.restore.error";
							Log4jInit.ncsLog.error(this.getClass().getName()+"Inserted to syslogEventsProcessDao Failed :\tdto=" + timestamp + "\n" + e);
							e.printStackTrace();
							model.put("Message", message);
							 return new ModelAndView(getPageView(),"model",model);
						}
					}
					
					}
					if(bksyslognss.size()>0){
						if(bksyslognss.size() == 1 && bksyslognss.get(0).getMark()== null){}else{
							
						
						/*
						for(int i=0;i<bksyslognss.size();i++){

							SyslogEventsProcessNs syslogns = new SyslogEventsProcessNs();
							syslogns.setMark(bksyslognss.get(i).getMark());
							syslogns.setManufacture(bksyslognss.get(i).getManufacture());
							syslogns.setBtimelist(bksyslognss.get(i).getBtimelist());
							syslogns.setEtimelist(bksyslognss.get(i).getEtimelist());
							syslogns.setFilterflag1(bksyslognss.get(i).getFilterflag1());
							syslogns.setFilterflag2(bksyslognss.get(i).getFilterflag2());
							syslogns.setSeverity1(bksyslognss.get(i).getSeverity1());
							syslogns.setSeverity2(bksyslognss.get(i).getSeverity2());
							syslogns.setPort(bksyslognss.get(i).getPort());
							syslogns.setNotcareflag(bksyslognss.get(i).getNotcareflag());
							syslogns.setType(bksyslognss.get(i).getType());
							syslogns.setEventtype(bksyslognss.get(i).getEventtype());
							syslogns.setSubeventtype(bksyslognss.get(i).getSubeventtype());
							syslogns.setAlertgroup(bksyslognss.get(i).getAlertgroup());
							syslogns.setAlertkey(bksyslognss.get(i).getAlertkey());
							syslogns.setSummarycn(bksyslognss.get(i).getSummarycn());
							syslogns.setProcesssuggest(bksyslognss.get(i).getProcesssuggest());
							syslogns.setStatus(bksyslognss.get(i).getStatus());
							syslogns.setAttentionflag(bksyslognss.get(i).getAttentionflag());
							syslogns.setEvents(bksyslognss.get(i).getEvents());
							syslogns.setOrigevent(bksyslognss.get(i).getOrigevent());
							syslogns.setVarlist(bksyslognss.get(i).getVarlist());
							
							try{
								syslogEventsProcessNsDao.insert(syslogns);
								Log4jInit.ncsLog.info(this.getClass().getName()+"Inserted to syslogEventsProcessNsDao :" +syslogns.toString());
							//    message = "excel.restore.success";
							//    model.put("Message", message);
							}catch(Exception e){
								message = "excel.restore.error";
								Log4jInit.ncsLog.error(this.getClass().getName()+"Inserted to syslogEventsProcessNsDao Failed :\tdto=" + syslogns.toString() + "\n" + e.getMessage());
								e.printStackTrace();
								model.put("Message", message);
								 return new ModelAndView(getPageView(),"model",model);
							}
						
							
						}
						*/
						try{
							syslogEventsProcessNsDao.batchInsertByBkTime(timestamp);
							Log4jInit.ncsLog.info(this.getClass().getName()+"Inserted to syslogEventsProcessNsDao :" +timestamp);
						//    message = "excel.restore.success";
						//    model.put("Message", message);
						}catch(Exception e){
							message = "excel.restore.error";
							Log4jInit.ncsLog.error(this.getClass().getName()+"Inserted to syslogEventsProcessNsDao Failed :\tdto=" + timestamp + "\n" + e);
							e.printStackTrace();
							model.put("Message", message);
							 return new ModelAndView(getPageView(),"model",model);
						}
					}
					}
					System.out.println("restore success");
					message = "excel.restore.success";
				    model.put("Message", message);
						
					}
				
				
				
					return new ModelAndView(getPageView(),"model",model);
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
	
	

}
