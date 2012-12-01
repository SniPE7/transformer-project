package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ejs.cm.portability.DuplicateKeyException;
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

public class SavePreDefMibOidGroupDetailsController implements Controller {
	
	TOidgroupInfoInitDao TOidgroupInfoInitDao ;
	TOidgroupDetailsInitDao  TOidgroupDetailsInitDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	ModuleEventTypeDao ModuleEventTypeDao; 
	GenPkNumber GenPkNumber;
	
	 private String pageView;
	 private String message;
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		//refill next page
		Map<String, Object> model =  new HashMap<String, Object>();
		message = "";
		
		try {
			
			String opidStr =request.getParameter("opid");
			String oidvalue = 	request.getParameter("oidvalue");
			String oidname =	request.getParameter("oidname");
			String oidunit =	request.getParameter("oidunit");
			String oidindexStr =	request.getParameter("oidindex");
			String checkbps =	request.getParameter("checkbps");
			String flag =	request.getParameter("flag");
			long opid = Long.parseLong(opidStr);
			//long oidindex = Long.parseLong(oidindexStr);
			//System.out.println("\t\tinSavePreDefMibOidGroup:\t" + opidStr);
			TOidgroupDetailsInit dto1 = new TOidgroupDetailsInit();
			dto1.setOpid(opid);
			dto1.setOidvalue(oidvalue);
			dto1.setOidname(oidname);
			dto1.setOidunit(oidunit);
			dto1.setFlag((flag==null||flag.equals(""))?"0":"1");
			if (oidindexStr!=null && !"".equals(oidindexStr)){
				long oidindex = Long.parseLong(oidindexStr);
			dto1.setOidindex(oidindex);
			}
			
			
			
			TOidgroupDetailsInit detail = null;
			try{
				detail = TOidgroupDetailsInitDao.findByPrimaryKey(opid, oidname);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(detail == null){
					//System.out.println("new invoked----------");
					List<TOidgroupDetailsInit> oids = TOidgroupDetailsInitDao.findWhereOpidEquals(opid);
					if(oids != null && oids.size()>0){
					  for(int i=0;i<oids.size();i++){
						if(Long.parseLong(oidindexStr) == oids.get(i).getOidindex()){
							message = "baseinfo.oidgroup.dulindex";
							model.put("message", message);
							
							model.put("oidvalue", oidvalue);
							model.put("oidname", oidname);
							model.put("oidunit", oidunit);
							model.put("oidindex", oidindexStr);
							List<TManufacturerInfoInit> mflist = TManufacturerInfoInitDao.findAll();
							SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
							sortmanu.Sort(mflist, "getMrname", null);
							// device performance params list DEVICE
					//		long general = -1;
							long ecode = 9;
							List<ModuleEventType> perfparam = ModuleEventTypeDao.findModuleEventTypeByGeneralEcode( ecode);
							SortList<ModuleEventType> sortevent = new SortList<ModuleEventType>();
							sortevent.Sort(perfparam, "getMajor", null);
							
							if (null==model) 
							model = new HashMap();
							model.put("mflist", mflist);
							model.put("perfparam", perfparam);
							
							
							
				//		}
				//		model.put("dto1", dto1);
						List<TOidgroupDetailsInit> details =   TOidgroupDetailsInitDao.findWhereOpidEquals(opid);
						TOidgroupInfoInit 	oidgroup  = TOidgroupInfoInitDao.findByPrimaryKey(opid);
						model.put("oidgroup", oidgroup);
						model.put("details", details);
						model.put("opid", opidStr);
						
				
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
						} catch (Exception ee) {
							Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ee.getMessage());
							ee.printStackTrace();
						}			
				
						model.put("pmppara", pmppara);
						model.put("pmpmanu", pmpmanu);
						model.put("pmptail", pmptail);	
						
						request.getSession().removeAttribute("devoidmodle");
						request.getSession().setAttribute("devoidmodle", model);
							return new ModelAndView(getPageView(), "model", model);
						}
					  }
					}
					try{
					TOidgroupDetailsInitDao.insert(dto1);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TOidgroupDetailsInitDao: " + dto1.toString());	
					}catch(Exception e){
						//System.out.println("new exception----------");
						message = "baseinfo.new.error";
						model.put("message", message);
						//System.out.println("message is------"+model.get("message"));
						model.put("oidvalue", oidvalue);
						model.put("oidname", oidname);
						model.put("oidunit", oidunit);
						model.put("oidindex", oidindexStr);
						
						Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to tOidgroupInfoInitDao Failed: " + dto1.toString());	
						List<TManufacturerInfoInit> mflist = TManufacturerInfoInitDao.findAll();
						SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
						sortmanu.Sort(mflist, "getMrname", null);
						// device performance params list DEVICE
			//			long general = -1;
						long ecode = 9;
						List<ModuleEventType> perfparam = ModuleEventTypeDao.findModuleEventTypeByGeneralEcode( ecode);
						SortList<ModuleEventType> sortevent = new SortList<ModuleEventType>();
						sortevent.Sort(perfparam, "getMajor", null);
						
						if (null==model)
						model = new HashMap();
						model.put("mflist", mflist);
						model.put("perfparam", perfparam);
						
			//		}
			//		model.put("dto1", dto1);
					List<TOidgroupDetailsInit> details =   TOidgroupDetailsInitDao.findWhereOpidEquals(opid);
					TOidgroupInfoInit 	oidgroup  = TOidgroupInfoInitDao.findByPrimaryKey(opid);
					model.put("oidgroup", oidgroup);
					model.put("details", details);
					model.put("opid", opidStr);
					
			
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
					} catch (Exception ee) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ee.getMessage());
						ee.printStackTrace();
					}			
			
					model.put("pmppara", pmppara);
					model.put("pmpmanu", pmpmanu);
					model.put("pmptail", pmptail);	
					
					request.getSession().removeAttribute("devoidmodle");
					request.getSession().setAttribute("devoidmodle", model);
					
					return new ModelAndView(getPageView(),"model",model);
					}
				}else{
//					//System.out.println("@@==else: output DTO in update======@@:" + dto1);
					//dupe.printStackTrace();
					//System.out.println("update invoked------------");
					List<TOidgroupDetailsInit> oids = TOidgroupDetailsInitDao.findWhereOpidEquals(opid);
					if(oids != null && oids.size()>0){
					  for(int i=0;i<oids.size();i++){
						  if(!oids.get(i).getOidname().equalsIgnoreCase(oidname)){
						if(Long.parseLong(oidindexStr) == oids.get(i).getOidindex()){
							message = "baseinfo.oidgroup.dulindex";
							model.put("message", message);
							
							model.put("oidvalue", oidvalue);
							model.put("oidname", oidname);
							model.put("oidunit", oidunit);
							model.put("oidindex", oidindexStr);
							List<TManufacturerInfoInit> mflist = TManufacturerInfoInitDao.findAll();
							SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
							sortmanu.Sort(mflist, "getMrname", null);
							// device performance params list DEVICE
					//		long general = -1;
							long ecode = 9;
							List<ModuleEventType> perfparam = ModuleEventTypeDao.findModuleEventTypeByGeneralEcode( ecode);
							SortList<ModuleEventType> sortevent = new SortList<ModuleEventType>();
							sortevent.Sort(perfparam, "getMajor", null);
							
							if (null==model) 
							model = new HashMap();
							model.put("mflist", mflist);
							model.put("perfparam", perfparam);
							
							
							
				//		}
				//		model.put("dto1", dto1);
						List<TOidgroupDetailsInit> details =   TOidgroupDetailsInitDao.findWhereOpidEquals(opid);
						TOidgroupInfoInit 	oidgroup  = TOidgroupInfoInitDao.findByPrimaryKey(opid);
						model.put("oidgroup", oidgroup);
						model.put("details", details);
						model.put("opid", opidStr);
						
				
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
						} catch (Exception ee) {
							Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ee.getMessage());
							ee.printStackTrace();
						}			
				
						model.put("pmppara", pmppara);
						model.put("pmpmanu", pmpmanu);
						model.put("pmptail", pmptail);	
						
						request.getSession().removeAttribute("devoidmodle");
						request.getSession().setAttribute("devoidmodle", model);
							return new ModelAndView(getPageView(), "model", model);
						}
						  }
					  }
					}
					TOidgroupDetailsInitPk pk = dto1.createPk();
					try{
					TOidgroupDetailsInitDao.update(pk,dto1);
					Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TOidgroupDetailsInitDao: pk=" + pk.toString() + " \tdto=" + dto1.toString());
					}catch(Exception e){
						//System.out.println("update exception---------");
						message = "baseinfo.update.fail";
						model.put("message", message);
						model.put("oidvalue", oidvalue);
						model.put("oidname", oidname);
						model.put("oidunit", oidunit);
						model.put("oidindex", oidindexStr);
						Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TOidgroupDetailsInitDao Failed: pk=" + pk.toString() + " \tdto=" + dto1.toString());
						List<TManufacturerInfoInit> mflist = TManufacturerInfoInitDao.findAll();
						SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
						sortmanu.Sort(mflist, "getMrname", null);
						// device performance params list DEVICE
			//			long general = -1;
						long ecode = 9;
						List<ModuleEventType> perfparam = ModuleEventTypeDao.findModuleEventTypeByGeneralEcode( ecode);
						SortList<ModuleEventType> sortevent = new SortList<ModuleEventType>();
						sortevent.Sort(perfparam, "getMajor", null);
						
						if (null==model) 
						model = new HashMap();
						model.put("mflist", mflist);
						model.put("perfparam", perfparam);
						
						
						
			//		}
			//		model.put("dto1", dto1);
					List<TOidgroupDetailsInit> details =   TOidgroupDetailsInitDao.findWhereOpidEquals(opid);
					TOidgroupInfoInit 	oidgroup  = TOidgroupInfoInitDao.findByPrimaryKey(opid);
					model.put("oidgroup", oidgroup);
					model.put("details", details);
					model.put("opid", opidStr);
					
			
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
					} catch (Exception ee) {
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ee.getMessage());
						ee.printStackTrace();
					}			
			
					model.put("pmppara", pmppara);
					model.put("pmpmanu", pmpmanu);
					model.put("pmptail", pmptail);	
					
					request.getSession().removeAttribute("devoidmodle");
					request.getSession().setAttribute("devoidmodle", model);
					
					return new ModelAndView(getPageView(),"model",model);
					}
				}	
			
				//refill next page
		//		if (null==model){
					//manufacturer list mrname list only;
					List<TManufacturerInfoInit> mflist = TManufacturerInfoInitDao.findAll();
					SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
					sortmanu.Sort(mflist, "getMrname", null);
					// device performance params list DEVICE
		//			long general = -1;
					long ecode = 9;
					List<ModuleEventType> perfparam = ModuleEventTypeDao.findModuleEventTypeByGeneralEcode( ecode);
					SortList<ModuleEventType> sortevent = new SortList<ModuleEventType>();
					sortevent.Sort(perfparam, "getMajor", null);
					
					if (null==model) model = new HashMap();
					model.put("mflist", mflist);
					model.put("perfparam", perfparam);
		//		}
		//		model.put("dto1", dto1);
				List<TOidgroupDetailsInit> details =   TOidgroupDetailsInitDao.findWhereOpidEquals(opid);
				TOidgroupInfoInit 	oidgroup  = TOidgroupInfoInitDao.findByPrimaryKey(opid);
				model.put("oidgroup", oidgroup);
				model.put("details", details);
				model.put("opid", opidStr);
				
		
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
					Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
					e.printStackTrace();
				}			
		
				model.put("pmppara", pmppara);
				model.put("pmpmanu", pmpmanu);
				model.put("pmptail", pmptail);	
				
				request.getSession().removeAttribute("devoidmodle");
				request.getSession().setAttribute("devoidmodle", model);
				
				
			} catch (Exception e) {
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				e.printStackTrace();
			}
			return new ModelAndView(getPageView(), "model", model);
	}






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






	public TOidgroupInfoInitDao getTOidgroupInfoInitDao() {
		return TOidgroupInfoInitDao;
	}






	public void setTOidgroupInfoInitDao(TOidgroupInfoInitDao oidgroupInfoInitDao) {
		TOidgroupInfoInitDao = oidgroupInfoInitDao;
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
