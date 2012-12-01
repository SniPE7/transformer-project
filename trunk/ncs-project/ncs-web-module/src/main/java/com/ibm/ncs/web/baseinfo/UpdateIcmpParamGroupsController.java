package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class UpdateIcmpParamGroupsController implements Controller {
	
	TEventTypeInitDao TEventTypeInitDao;
	 private String pageView;

		TModuleInfoInitDao TModuleInfoInitDao;
		GenPkNumber GenPkNumber;


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			TEventTypeInit eventtypeform = new TEventTypeInit();
			String eveidstr = request.getParameter("eveid");
			String modidstr = request.getParameter("modid");
			String generalstr = request.getParameter("general");
			String major = request.getParameter("major");
			String description = request.getParameter("description");
			
			//SNMP mcode=2 modid-->
//			List<TModuleInfoInit> modinfo =  TModuleInfoInitDao.findWhereMcodeEquals(2);
//			TModuleInfoInit module = modinfo.get(0);
//			long modid 	= module.getModid();  	//SNMP 
			long general = (generalstr!=null&&!generalstr.equals(""))?Long.parseLong(generalstr):-1; //general = -1 ; ; //snmp
			long ecode	= 7 ; 		//preDefMib
			
			long eveid ;
			if (null!=eveidstr&& !"".equals("eveidstr")){
				eveid = Long.parseLong(eveidstr);
			}else{
				Log4jInit.ncsLog.error(this.getClass().getName() + " eveid is null");
				throw new RuntimeException();
			}
			long modid;
			if(null!=modidstr&& !"".equals(modidstr)){
				modid = Long.parseLong(modidstr);
			}else{

				Log4jInit.ncsLog.error(this.getClass().getName() + " modid is null");
				throw new RuntimeException();
			}		

			eventtypeform.setEveid(eveid);
			eventtypeform.setModid(modid);
			eventtypeform.setGeneral(general);
			eventtypeform.setEcode(ecode);
			eventtypeform.setMajor(major);
			eventtypeform.setDescription(description);
			
			TEventTypeInitPk pk = new TEventTypeInitPk(modid, eveid);

			try{
				TEventTypeInitDao.update(pk, eventtypeform);
				Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TOidgroupDetailsInitDao: pk=" + pk.toString() + " \tdto=" + eventtypeform.toString());
			}catch(Exception ee){
				String message = "baseinfo.update.failed";
				Log4jInit.ncsLog.error(this.getClass().getName() + " updated to TOidgroupDetailsInitDao Failed: pk=" + pk.toString() + " \tdto=" + eventtypeform.toString()
						+ "\n" + ee.getMessage());
				model.put("message", message);

				return new ModelAndView("/secure/baseinfo/updateIcmpParam.jsp", "model", model);
			}
			
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}	

		return new ModelAndView(getPageView(), "model", model);
	}


//	public ModelAndView onSubmit(Object command){
//		
//		((TEventTypeInit)command).setGeneral(-1);
//		((TEventTypeInit)command).setEcode(1); //hardcoded
//		TEventTypeInitDao.insert((TEventTypeInit)command);
//		return new ModelAndView(new RedirectView(getSuccessView()));
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


	public TModuleInfoInitDao getTModuleInfoInitDao() {
		return TModuleInfoInitDao;
	}


	public void setTModuleInfoInitDao(TModuleInfoInitDao moduleInfoInitDao) {
		TModuleInfoInitDao = moduleInfoInitDao;
	}


	public GenPkNumber getGenPkNumber() {
		return GenPkNumber;
	}


	public void setGenPkNumber(GenPkNumber genPkNumber) {
		GenPkNumber = genPkNumber;
	}





}
