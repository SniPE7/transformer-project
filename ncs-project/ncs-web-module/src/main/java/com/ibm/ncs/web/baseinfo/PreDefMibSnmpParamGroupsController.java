package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.ModuleEventTypeDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyDetailsDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.Log4jInit;

public class PreDefMibSnmpParamGroupsController implements Controller {
	

	ModuleEventTypeDao ModuleEventTypeDao;
	 private String pageView;
	 TPolicyDetailsDao tPolicyDetailsDao;
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		String message = "";
		Map<String, Object> model = new HashMap<String,Object>();
	//	long general = -1;
		long ecode = 9;
		List<ModuleEventType> eventtype = null;
		try{
			eventtype = ModuleEventTypeDao.findModuleEventTypeByGeneralEcode(ecode);
			String eveides = "";
			if(eventtype != null && eventtype.size()>0){
				for(int i=0;i<eventtype.size();i++){
					
					long eveid = eventtype.get(i).getEveid();
					
					
					List<TPolicyDetails> policyes = tPolicyDetailsDao.findWhereEveidEquals(eveid);
					System.out.println("eveid is--"+eveid+"policyes size is--"+policyes.size());
					if(policyes.size()>0){
						eveides += eveid+";";
						model.put("eveides", eveides);
					}
					
				}
			}
		}catch(DaoException e){
			e.printStackTrace();
			message = "global.db.error";
			model.put("message", message);
			return new ModelAndView("/dberror.jsp","model",model);
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		model.put("eventtype",eventtype);
		return new ModelAndView(getPageView(), "model",model);
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







	public ModuleEventTypeDao getModuleEventTypeDao() {
		return ModuleEventTypeDao;
	}







	public void setModuleEventTypeDao(ModuleEventTypeDao moduleEventTypeDao) {
		ModuleEventTypeDao = moduleEventTypeDao;
	}







	public TPolicyDetailsDao getTPolicyDetailsDao() {
		return tPolicyDetailsDao;
	}







	public void setTPolicyDetailsDao(TPolicyDetailsDao policyDetailsDao) {
		tPolicyDetailsDao = policyDetailsDao;
	}
	
	





}
