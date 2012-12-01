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
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class DeleteDevOidGroupDetailsController implements Controller {
	
	TOidgroupInfoInitDao tOidgroupInfoInitDao ;
	TOidgroupDetailsInitDao  TOidgroupDetailsInitDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	ModuleEventTypeDao ModuleEventTypeDao; 
	GenPkNumber GenPkNumber;
	
	 private String pageView;
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		//refill next page
		Map<String, Object> model =  (Map<String,Object>)request.getSession().getAttribute("devoidmodle");
		try {
			model.remove("message");
		} catch (Exception e1) {
		}
		
		try {
			String opidStr =request.getParameter("opid");
			String oidvalue = 	request.getParameter("oidvalue");
			String oidname =	request.getParameter("oidname");
			String oidunit =	request.getParameter("oidunit");
			String oidindexStr =	request.getParameter("oidindex");
			String checkbps =	request.getParameter("checkbps");
			String flag =	request.getParameter("flag");
			
			long opid = 0;
			if(opidStr!= null)
				opid = Long.parseLong(opidStr);
		
			TOidgroupDetailsInit dto1 = new TOidgroupDetailsInit();
			dto1.setOpid(opid);
			dto1.setOidvalue(oidvalue);
			dto1.setOidname(oidname);
			dto1.setOidunit(oidunit);
			dto1.setFlag((flag==null||"".equals(flag))?"0":flag);
			if(oidindexStr!=null||!"".equals(oidindexStr)){
			long oidindex = Long.parseLong((oidindexStr));
			
			
			dto1.setOidindex(oidindex);
			}
			TOidgroupDetailsInitPk pk = new TOidgroupDetailsInitPk(opid,oidname);
			TOidgroupDetailsInitDao.delete(pk);
			Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from TOidgroupDetailsInitDao " + pk.toString());
			
//			if (null==model){
			//manufacturer list mrname list only;
			List<TManufacturerInfoInit> mflist = TManufacturerInfoInitDao.findAll();
			// device performance params list DEVICE
			//long general = -1;
			long ecode = 1;
			List<ModuleEventType> perfparam = ModuleEventTypeDao.findModuleEventTypeByGeneralEcode( ecode);
			if (null==model) model = new HashMap();
			model.put("mflist", mflist);
			model.put("perfparam", perfparam);
//			}
	//		model.put("dto1", dto1);
			List<TOidgroupDetailsInit> oidgroupdetail =   TOidgroupDetailsInitDao.findWhereOpidEquals(opid);
			model.put("opid", opidStr);
			model.put("oidgroupdetail", oidgroupdetail);
			model.put("details", oidgroupdetail);
			TOidgroupInfoInit 			oidgroup  		= tOidgroupInfoInitDao.findByPrimaryKey(opid);
			String oidgroupname = oidgroup.getOidgroupname();
			StringTokenizer tk = new StringTokenizer(oidgroupname ,"_");
			String pmppara="",pmpmanu="",pmptail="";
			try {
				//need to fixed: if there's an empty item in db, e.g. oidgroupname='_manuf_nameaaa', then, there's only two tokens, and will throw exception for the following code:
				if(tk.hasMoreTokens()){
					pmppara = tk.nextToken();
					pmpmanu = tk.nextToken();
					pmptail = tk.nextToken();
				}
			} catch (Exception e) {
//				String message = "";
//				model.put("message", message);
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error Message:\n" + e.getMessage());
				e.printStackTrace();
			}			
	
			model.put("pmppara", pmppara);
			model.put("pmpmanu", pmpmanu);
			model.put("pmptail", pmptail);	
			request.getSession().removeAttribute("devoidmodle");
			request.getSession().setAttribute("devoidmodle", model);
		} catch (Exception e) {
			String message = "failed";
			model.put("message", message);
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error Message:\n" + e.getMessage());
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
}
