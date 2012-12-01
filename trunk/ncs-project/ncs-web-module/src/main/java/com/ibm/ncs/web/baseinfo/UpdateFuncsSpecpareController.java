package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.PerformParaDao;
import com.ibm.ncs.model.dao.TEventOidInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.PerformPara;
import com.ibm.ncs.model.dto.TEventOidInit;
import com.ibm.ncs.model.dto.TOidgroupInfoInit;
import com.ibm.ncs.util.Log4jInit;

public class UpdateFuncsSpecpareController implements Controller {
	
	private String pageView;
	private	TEventOidInitDao TEventOidInitDao;
	private TOidgroupInfoInitDao TOidgroupInfoInitDao;
	private PerformParaDao  PerformParaDao;
	private String message;
	
	
	
	public PerformParaDao getPerformParaDao() {
		return PerformParaDao;
	}






	public void setPerformParaDao(PerformParaDao performParaDao) {
		PerformParaDao = performParaDao;
	}






	public String getMessage() {
		return message;
	}






	public void setMessage(String message) {
		this.message = message;
	}






	public TOidgroupInfoInitDao getTOidgroupInfoInitDao() {
		return TOidgroupInfoInitDao;
	}






	public void setTOidgroupInfoInitDao(TOidgroupInfoInitDao oidgroupInfoInitDao) {
		TOidgroupInfoInitDao = oidgroupInfoInitDao;
	}






	public String getPageView() {
		return pageView;
	}



	


	public TEventOidInitDao getTEventOidInitDao() {
		return TEventOidInitDao;
	}






	public void setTEventOidInitDao(TEventOidInitDao eventOidInitDao) {
		TEventOidInitDao = eventOidInitDao;
	}






	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
        message = "";
		Map<String, Object> performs = new HashMap<String,Object>();
		try{
			String oidgroupname = request.getParameter("oidgroupname");
			String unit = request.getParameter("unit");
			String description = request.getParameter("description");
			Long eveid = Long.parseLong(request.getParameter("eveid"));
			TEventOidInit eoi = new TEventOidInit();	
			String major = request.getParameter("major");
			eoi.setEveid(eveid);
			eoi.setDtid(Long.parseLong(request.getParameter("dtid")));
			eoi.setModid(Long.parseLong(request.getParameter("modid")));
			eoi.setMrid(Long.parseLong(request.getParameter("mrid")));
			eoi.setOid(request.getParameter("oid"));
			eoi.setOidgroupname(oidgroupname);
			eoi.setUnit(unit);
			eoi.setDescription(description);
			List<TEventOidInit> tev = null;
			tev = TEventOidInitDao.findWhereDtidNEveidEquals(Long.parseLong(request.getParameter("dtid")),eveid);
			if(tev.size() > 0){
				if(oidgroupname!=null && !oidgroupname.equalsIgnoreCase("")){
					//if oidgroupname is not null, then update
					try{
					TEventOidInitDao.update(eoi); 	
					Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TEventOidInitDao: \tdto=" + eoi.toString());	
					}catch(Exception e){
						message = "baseinfo.update.failed";
						performs.put("message", message);
						performs.put("major", major);
						Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TEventOidInitDao Failed: \tdto=" + eoi.toString());	
						List<TOidgroupInfoInit> toi = null;
						try{
							
							toi = TOidgroupInfoInitDao.findAll();
						    performs.put("toi", toi);
					
						}catch (Exception ee) {
							Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
							ee.printStackTrace();
						}
						return new ModelAndView("/secure/baseinfo/newParam.jsp","model",performs);
					}
				}else{	//if oidgroupname is null, then delete this record;
//					System.out.println("tev.size=" + tev.size());
					TEventOidInitDao.delete(eveid, Long.parseLong(request.getParameter("dtid"))); 	
					Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TEventOidInitDao:id=" + eveid + " and" + Long.parseLong(request.getParameter("dtid")));
					
				}
			}else{
//				System.out.println("In Insert***************");
				try{
				TEventOidInitDao.insert(eoi); 
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TEventOidInitDao: " + eoi.toString());	
				}catch(Exception e){
					message = "baseinfo.new.failed";
					performs.put("major", major);
					performs.put("message", message);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TEventOidInitDao Failed: \tdto=" + eoi.toString());	
					List<TOidgroupInfoInit> toi = null;
					try{
						
						toi = TOidgroupInfoInitDao.findAll();
					    performs.put("toi", toi);
				
					}catch (Exception ee) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
						ee.printStackTrace();
					}
					return new ModelAndView("/secure/baseinfo/newParam.jsp","model",performs);
					
				}
			}

			List<TOidgroupInfoInit> toi = null;
			//List<TEventTypeInit> tei = TEventTypeInitDao.findWhereEveidEquals(eveid);
			//List<TEventTypeInit> tei = TEventTypeInitDao.findAll();
			toi = TOidgroupInfoInitDao.findAll();
			 performs.put("toi", toi);
			 performs.put("major", major);
	//		 System.out.println("In UpdateFuncsSpecpare&&&&&&&&&&&&getDescription=" + toi.get(0).getDescription() +"getOpid"+toi.get(0).getOpid()+"getOtype="+toi.get(0).getOtype());
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		
		return new ModelAndView(getPageView(),"model",performs);
	}

}
