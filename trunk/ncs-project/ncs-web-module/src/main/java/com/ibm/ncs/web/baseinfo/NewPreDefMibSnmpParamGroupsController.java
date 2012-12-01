package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.ibm.ncs.model.dao.ModuleEventTypeDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class NewPreDefMibSnmpParamGroupsController implements Controller  {
	
	TEventTypeInitDao TEventTypeInitDao;
	TModuleInfoInitDao TModuleInfoInitDao;
	GenPkNumber GenPkNumber;
	 private String pageView;
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try{
			TEventTypeInit eventtypeform = new TEventTypeInit();
			String eveidstr = request.getParameter("eveid");
			String modidstr = request.getParameter("modid");
			String generalstr = request.getParameter("general");
			String ecodestr  =request.getParameter("ecode");
			String major = request.getParameter("major");
			String description = request.getParameter("description");
			
			//SNMP mcode=2 modid-->
			List<TModuleInfoInit> modinfo =  TModuleInfoInitDao.findWhereMcodeEquals(2);
			TModuleInfoInit module = modinfo.get(0);
			long modid 	= module.getModid();  	//SNMP 
			long general = (generalstr!=null&&!generalstr.equals(""))?Long.parseLong(generalstr):-1; //general = -1 ;; //snmp
	//		long ecode	= 9 ; 		//preDefMib
			long ecode = Long.parseLong(ecodestr);
			long eveid ;
			
			eveid = GenPkNumber.getID();
			eventtypeform.setEveid(eveid);
			eventtypeform.setModid(modid);
			eventtypeform.setGeneral(general);
			eventtypeform.setEcode(ecode);
			eventtypeform.setMajor(major);
			eventtypeform.setDescription(description);
			try{
				TEventTypeInitDao.insert(eventtypeform);
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TEventTypeInitDao: " + eventtypeform.toString());
			}catch (DataIntegrityViolationException de){
				String message = "baseinfo.newsnmp.dul";
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TEventTypeInitDao Failed : \tdto=" + eventtypeform.toString() + "\n" + de);
				model.put("message", message);
				return new ModelAndView("/secure/baseinfo/newPreDefMibSnmpParam.jsp", "model", model);
			}catch(Exception ee){
				String message = "baseinfo.new.error";
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TEventTypeInitDao Failed : \tdto=" + eventtypeform.toString() + "\n" + ee.getMessage());
				model.put("message", message);
				model.put("major", major);
				model.put("description", description);
				return new ModelAndView("/secure/baseinfo/newPreDefMibSnmpParam.jsp", "model", model);
			}
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		
		return new ModelAndView(getPageView());
	}


private int parseCommand(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}


//
//	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,  Object command, BindException errors) throws ServletException{
//		//TEventTypeInit form = (TEventTypeInit) command; 
//		((TEventTypeInit)command).setGeneral(-1);
//		((TEventTypeInit)command).setEcode(9); //hardcoded
//		TEventTypeInitDao.insert((TEventTypeInit)command);
//		System.out.println("NewPreDefMibSnmpParamGroupsController... onSubmit...="+command);		
//		return new ModelAndView(new RedirectView(getSuccessView()));
//	}
//
//
//	public Object formBackinObject(HttpServletRequest request)throws ServletException {
//		TEventTypeInit eventtypeform = new TEventTypeInit();
//		eventtypeform.setDescription(request.getParameter("description"));
//		System.out.println("formbakingObject...description...");
//		return eventtypeform;
//	}


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



	public TEventTypeInitDao getTEventTypeInitDao() {
		return TEventTypeInitDao;
	}



	public void setTEventTypeInitDao(TEventTypeInitDao eventTypeInitDao) {
		TEventTypeInitDao = eventTypeInitDao;
	}


	public GenPkNumber getGenPkNumber() {
		return GenPkNumber;
	}


	public void setGenPkNumber(GenPkNumber genPkNumber) {
		GenPkNumber = genPkNumber;
	}


	public TModuleInfoInitDao getTModuleInfoInitDao() {
		return TModuleInfoInitDao;
	}


	public void setTModuleInfoInitDao(TModuleInfoInitDao moduleInfoInitDao) {
		TModuleInfoInitDao = moduleInfoInitDao;
	}











}
