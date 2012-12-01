package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.ModuleEventTypeDao;
import com.ibm.ncs.model.dao.TEventOidInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

public class EditDevOidController implements Controller {
	
	TOidgroupInfoInitDao tOidgroupInfoInitDao ;
	TOidgroupDetailsInitDao  TOidgroupDetailsInitDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	ModuleEventTypeDao ModuleEventTypeDao; 
	GenPkNumber GenPkNumber;
	TEventOidInitDao TEventOidInitDao;
	
	 private String pageView;
	 
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		String opidStr = request.getParameter("opid");
		
		if (null==opidStr || "".equals(opidStr)){
			Log4jInit.ncsLog.error(this.getClass().getName() + " opid is null:\n");
			return new ModelAndView(getPageView()); //error 
		}
		
		long opid = Long.parseLong(opidStr);
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			TOidgroupInfoInit 			oidgroup  		= tOidgroupInfoInitDao.findByPrimaryKey(opid);
			String oidgroupname = oidgroup.getOidgroupname();
			
	//		System.out.println("oidname is "+oidgroupname);
			List<TEventOidInit> events = TEventOidInitDao.findWhereOidgroupnameEquals(oidgroupname);
			if(events != null && events.size()>0){
			      model.put("saveflag", "yes");
			}
			StringTokenizer tk = new StringTokenizer(oidgroupname ,"_");
			String pmppara="",pmpmanu="",pmptail="";
			try{
				if(tk.hasMoreTokens()){
					pmppara = tk.nextToken();
					pmpmanu = tk.nextToken();
					pmptail = tk.nextToken();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			List<TOidgroupDetailsInit> 	oidgroupdetail 	= TOidgroupDetailsInitDao.findWhereOpidEquals(opid);

			//manufacturer list mrname list only;
			List<TManufacturerInfoInit> mflist = TManufacturerInfoInitDao.findAll();
			SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
			sortmanu.Sort(mflist, "getMrname", null);
			// device performance params list DEVICE
			//long general = -1;
			long ecode =  1;
			List<ModuleEventType> perfparam = ModuleEventTypeDao.findModuleEventTypeByGeneralEcode( ecode);
			SortList<ModuleEventType> sortevent = new SortList<ModuleEventType>();
			sortevent.Sort(perfparam, "getMajor", null);
			
			model.put("mflist", mflist);
			model.put("perfparam", perfparam);
	
			model.put("oidgroup", oidgroup);
			model.put("details", oidgroupdetail);
			model.put("oidgroupdetail", oidgroupdetail);
			model.put("oidgroupname", oidgroupname);
			model.put("opid", opid);
			model.put("pmppara", pmppara);
			model.put("pmpmanu", pmpmanu);
			model.put("pmptail", pmptail);

		}catch (Exception e) {
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





	public TEventOidInitDao getTEventOidInitDao() {
		return TEventOidInitDao;
	}





	public void setTEventOidInitDao(TEventOidInitDao eventOidInitDao) {
		TEventOidInitDao = eventOidInitDao;
	}
	
}
