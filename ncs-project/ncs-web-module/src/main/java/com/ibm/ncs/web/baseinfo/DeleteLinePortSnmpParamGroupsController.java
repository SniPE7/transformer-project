package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.ibm.ncs.model.dao.ModuleEventTypeDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyDetailsDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class DeleteLinePortSnmpParamGroupsController extends SimpleFormController implements Controller {
	
	
	 private String pageView;
	 TEventTypeInitDao TEventTypeInitDao;
	 TModuleInfoInitDao TModuleInfoInitDao;
	 ModuleEventTypeDao ModuleEventTypeDao;
	 TPolicyDetailsDao tPolicyDetailsDao;
	 GenPkNumber GenPkNumber;
	 String message = "";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		//System.out.println("delete function invoked---");
		
		

		message = "";
		TEventTypeInit eventtypeform = new TEventTypeInit();
	//	String[] eveidstr = request.getParameterValues("eveid");
		//System.out.println("eveidstr is---"+eveidstr);
		
		Map<String,Object> eveidmap = new HashMap<String,Object>();
		String eveidstr = request.getParameter("eveid");
		String modidstr = request.getParameter("modid");
		String eveidcannotbedeleted = request.getParameter("eveidcannotbedeleted");
		//System.out.println("eveidcannotbedeleted list is---"+eveidcannotbedeleted);
		Map<String,Object> model = new HashMap<String,Object>();
		String evescannotbedeleted = "";
		
		
		boolean returntojsp = false;
		
		long eveid1 ;
		long modid ;
		String eveidselected = "";
		if(eveidstr != null){
	//	for(int i =0 ; i<eveidstr.length;i++){
			//System.out.println("i= "+i);
			
			try{
		/*		StringTokenizer st = new StringTokenizer(eveidstr[i],"|");
				String s1 = st.nextToken();
				String s2 = st.nextToken();*/
				eveid1 = Long.parseLong(eveidstr);
				modid = Long.parseLong(modidstr);
				//System.out.println("s1 are ---"+s1);
				String[] eveidesfromjsp = eveidcannotbedeleted.split(";");
				eveidselected += eveidstr+" ,";
				//System.out.println("eveidselected is--"+eveidselected);
				
				
				if(eveidesfromjsp != null)
				for(int e=0;e<eveidesfromjsp.length;e++){
					//System.out.println("e= "+e);
					//System.out.println("eveid are ---"+eveidesfromjsp[e]);
					if(eveidstr.equalsIgnoreCase(eveidesfromjsp[e])){
						//System.out.println("equal-----------");
						/*if(!evescannotbedeleted.contains(s1))
						evescannotbedeleted += " " + s1 +" ";*/
						//System.out.println("evescannotbedeleted is-------"+evescannotbedeleted);
					    returntojsp = true;
					    
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
	//	}
		
		if(returntojsp == true){
			message = "<script language='javascript'>alert('该线路/端口性能指标定制了策略，不能删除！');</script>";
			//message = "devicesnmpparam.delete.forbidden";
			//System.out.println("message is---"+message);
			model.put("message", message);
			eveidmap.put("eve", eveidstr);
			model.put("eveid", eveidmap);
			model.put("selected", eveidselected);
			
			//System.out.println("eveidselected is--"+model.get("selected"));
			
			
			//long general = -1;
			long ecode = 6;
			List<ModuleEventType> eventtype = null;
			try{
				eventtype = ModuleEventTypeDao.findModuleEventTypeByGeneralEcode(ecode);
				//System.out.println("eventtype size is---"+eventtype.size());
				String eveides = "";
				if(eventtype != null && eventtype.size()>0){
					for(int m=0;m<eventtype.size();m++){
						
						long eveid = eventtype.get(m).getEveid();
						String eveidStr = eveid +"";
						model.put("eveidStr", eveidStr);
						//System.out.println("eveid to return to jsp is ---"+model.get("eveidStr"));
						List<TPolicyDetails> policyes = tPolicyDetailsDao.findWhereEveidEquals(eveid);
						
						if(policyes.size()>0){
							eveides += eveid+";";
							model.put("eveides", eveides);
						}
						
					}
				}
			}catch(Exception e){
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				e.printStackTrace();
			}
			model.put("eventtype",eventtype);
			
			return new ModelAndView(getPageView(),"model",model);
			
		}
		
	
			
          //      for(int i =0 ; i<eveidstr.length;i++){
				
				List<ModuleEventType> eventtype = null;
				try{
					/*StringTokenizer st = new StringTokenizer(eveidstr[i],"|");
					String s1 = st.nextToken();
					String s2 = st.nextToken();*/
					eveid1 = Long.parseLong(eveidstr);
					modid = Long.parseLong(modidstr);
					//System.out.println("s1 are ---"+s1);
					if(returntojsp == false){
						//System.out.println("delete in fact---");
						TEventTypeInitPk pk = new TEventTypeInitPk(modid, eveid1);
						TEventTypeInitDao.delete(pk);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from TEventTypeInitDao " + pk.toString());
				}
					}catch(Exception e){
//					String message = "delete.failed";
//					model.put("message", message);
					Log4jInit.ncsLog.error(this.getClass().getName() + " Error Message:\n" + e.getMessage());
					e.printStackTrace();
				}
			
			
			
	//	}
		
		}
		
		
		//long general = -1;
		long ecode = 6;
		List<ModuleEventType> eventtype = null;
		try{
			eventtype = ModuleEventTypeDao.findModuleEventTypeByGeneralEcode(ecode);
			//System.out.println("eventtype size is---"+eventtype.size());
			String eveides = "";
			if(eventtype != null && eventtype.size()>0){
				for(int m=0;m<eventtype.size();m++){
					
					long eveid = eventtype.get(m).getEveid();
					String eveidStr = eveid+"";
					model.put("eveidStr", eveidStr);
					//System.out.println("eveid to return to jsp is ---"+model.get("eveidStr"));
					
					
					List<TPolicyDetails> policyes = tPolicyDetailsDao.findWhereEveidEquals(eveid);
					
					if(policyes.size()>0){
						eveides += eveid+";";
						model.put("eveides", eveides);
					}
					
				}
			}
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		model.put("eventtype",eventtype);
		

		//model.put("message", message);
		return new ModelAndView(getPageView(),"model",model);
	}


	
//	public ModelAndView onSubmit(Object command){
//		((TEventTypeInit)command).setGeneral(-1);
//		((TEventTypeInit)command).setEcode(6); //hardcoded
//		TEventTypeInitDao.insert((TEventTypeInit)command);
//		return new ModelAndView(new RedirectView(getSuccessView()));
//		
//	}





	public static void display(TCategoryMapInit dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getName() );
		buf.append( ", " );
		buf.append( dto.getFlag() );
		//System.out.println( buf.toString() );
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



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}





}
