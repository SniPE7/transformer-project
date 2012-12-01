package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.ModuleEventTypeDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

public class SaveNewLineportOidController implements Controller {
	
	TOidgroupInfoInitDao tOidgroupInfoInitDao ;
	TOidgroupDetailsInitDao  TOidgroupDetailsInitDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	ModuleEventTypeDao ModuleEventTypeDao; 
	GenPkNumber GenPkNumber;
	
	 private String pageView;
	 private String message;
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		Map<String, Object> model =  new HashMap<String, Object>();
		message = "";
		
		//String opid
		String pmppara = request.getParameter("performanceparaselect");
		String pmpmanu = request.getParameter("manufselect");
		String pmptail = request.getParameter("pmptail");
		  String oidgroupname = pmppara +"_"+pmpmanu;
			
			if(pmptail!=null && !pmptail.equals(""))
				oidgroupname += "_"+pmptail ;
		
		TOidgroupInfoInit dto = new TOidgroupInfoInit();
		
		long otype = 2;  //  ...Line port
		long opid = GenPkNumber.getID();
		try {
			dto.setOpid(opid);
			dto.setOidgroupname(oidgroupname);
			dto.setOtype(otype);
			//dto.setDescription(description);
			try{
			TOidgroupInfoInitPk pk =  tOidgroupInfoInitDao.insert(dto);
			Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to tOidgroupInfoInitDao: " + dto.toString());	
			}catch (DataIntegrityViolationException de){
				message = "baseinfo.oid.error";
				model.put("messageg",message);
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to tOidgroupInfoInitDao Failed: " + dto.toString()+de);
			}
			catch(Exception e){
				message = "baseinfo.new.error";
				
				model.put("messageg", message);
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to tOidgroupInfoInitDao Failed: " + dto.toString());	
				
				
				//manufacturer list mrname list only;
				List<TManufacturerInfoInit> mflist = TManufacturerInfoInitDao.findAll();
				SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
				sortmanu.Sort(mflist, "getMrname", null);
				// device performance params list DEVICE
	//			long general = -1;
				long ecode =6;
				List<ModuleEventType> perfparam = ModuleEventTypeDao.findModuleEventTypeByGeneralEcode( ecode);
				SortList<ModuleEventType> sortevent = new SortList<ModuleEventType>();
				sortevent.Sort(perfparam, "getMajor", null);
				
				if (null==model)
				model = new HashMap();
				model.put("mflist", mflist);
				model.put("perfparam", perfparam);
				
				model.put("dto", dto);
				model.put("pmppara", pmppara);
				model.put("pmpmanu", pmpmanu);
				model.put("pmptail", pmptail);
				model.put("opid", opid);
				
				request.getSession().removeAttribute("devoidmodle");
				request.getSession().setAttribute("devoidmodle", model);
				
				return new ModelAndView(getPageView(),"model",model);
				
			}
		
			
			//manufacturer list mrname list only;
			List<TManufacturerInfoInit> mflist = TManufacturerInfoInitDao.findAll();
			SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
			sortmanu.Sort(mflist, "getMrname", null);
			// device performance params list DEVICE
	//		long general = -1;
			long ecode =6;
			List<ModuleEventType> perfparam = ModuleEventTypeDao.findModuleEventTypeByGeneralEcode( ecode);
			SortList<ModuleEventType> sortevent = new SortList<ModuleEventType>();
			sortevent.Sort(perfparam, "getMajor", null);
			
			if (null==model)
			model = new HashMap();
			model.put("mflist", mflist);
			model.put("perfparam", perfparam);
			
			model.put("dto", dto);
			model.put("pmppara", pmppara);
			model.put("pmpmanu", pmpmanu);
			model.put("pmptail", pmptail);
			model.put("opid", opid);
			
			request.getSession().removeAttribute("devoidmodle");
			request.getSession().setAttribute("devoidmodle", model);
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView(getPageView(), "model", model);
	}





	public void setTOidgroupInfoInitDao(TOidgroupInfoInitDao oidgroupInfoInitDao) {
		tOidgroupInfoInitDao = oidgroupInfoInitDao;
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





	public TOidgroupInfoInitDao getTOidgroupInfoInitDao() {
		return tOidgroupInfoInitDao;
	}





	public TOidgroupDetailsInitDao getTOidgroupDetailsInitDao() {
		return TOidgroupDetailsInitDao;
	}





	public void setTOidgroupDetailsInitDao(
			TOidgroupDetailsInitDao oidgroupDetailsInitDao) {
		TOidgroupDetailsInitDao = oidgroupDetailsInitDao;
	}





	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}





	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}





	public ModuleEventTypeDao getModuleEventTypeDao() {
		return ModuleEventTypeDao;
	}





	public void setModuleEventTypeDao(ModuleEventTypeDao moduleEventTypeDao) {
		ModuleEventTypeDao = moduleEventTypeDao;
	}





	public GenPkNumber getGenPkNumber() {
		return GenPkNumber;
	}





	public void setGenPkNumber(GenPkNumber genPkNumber) {
		GenPkNumber = genPkNumber;
	}





	public String getMessage() {
		return message;
	}





	public void setMessage(String message) {
		this.message = message;
	}
	
}
