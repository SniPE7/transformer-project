package com.ibm.ncs.web.baseinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.ModuleEventTypeDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TEventOidInitDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TLinepolMapDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TEventOidInit;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TLinepolMap;
import com.ibm.ncs.model.dto.TOidgroupInfoInit;
import com.ibm.ncs.model.dto.TOidgroupInfoInitPk;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class DeleteLineportOidController implements Controller {
	
	TOidgroupInfoInitDao tOidgroupInfoInitDao ;
	TOidgroupDetailsInitDao  TOidgroupDetailsInitDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	ModuleEventTypeDao ModuleEventTypeDao; 
	GenPkNumber GenPkNumber;
	String message = "";
	TEventOidInitDao TEventOidInitDao;
    TEventTypeInitDao TEventTypeInitDao;
    
    TLinepolMapDao TLinepolMapDao;
    TDeviceInfoDao TDeviceInfoDao;
    TPortInfoDao TPortInfoDao;
    
	
	 private String pageView;
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		

		message = "";
		TOidgroupInfoInit dto = new TOidgroupInfoInit();
	
		boolean returntojsp = false;
		Map<String,Object> model = new HashMap<String,Object>();
	    List<TLinepolMap> linepolmap = null;
		
	    String opidStr = request.getParameter("opid");
	    long opid = Long.parseLong(opidStr);
	    
	 
        
		if(opidStr!= null){
	
			try{

				String oidGroupname = tOidgroupInfoInitDao.findByPrimaryKey(opid).getOidgroupname();
				System.out.println("oidgroupname from jsp is---------"+oidGroupname);
				
				List<TEventTypeInit> eventlist = TEventTypeInitDao.listMajor();
				List majorlist = new ArrayList();
				if(eventlist.size()>0){
					for(TEventTypeInit event : eventlist){
						String major = event.getMajor();
						System.out.println("contains is "+(oidGroupname.contains(major)));
						if(oidGroupname.contains(major)){
							returntojsp = true;
						}
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
	//	}
			System.out.println("flag is---"+returntojsp);
		if(returntojsp == true){
			System.out.println("can not be deleted invoked----");
			message = "<script language='javascript'>alert('该线路/端口应用了策略,不能删除！ ');</script>";
		
			System.out.println("message is---"+message);
			model.put("message", message);
			
			
			long otype =2;
			List<TOidgroupInfoInit> oidglist = null;
	       
	       
			
			try{
				
				oidglist = tOidgroupInfoInitDao.findWhereOtypeEquals(otype);
				
			}catch(Exception e){
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
				e.printStackTrace();
			}
			model.put("oidglist",oidglist);
			return new ModelAndView(getPageView(),"model",model);
			
		}
		
			
			try{
				TOidgroupInfoInitPk pk = new TOidgroupInfoInitPk(opid);
				tOidgroupInfoInitDao.delete(pk);
				Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from tOidgroupInfoInitDao " + pk.toString());
				
				TOidgroupDetailsInitDao.delete(opid);
				Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from TOidgroupDetailsInitDao " + opid);
				
			}catch(Exception e){
//				String message = "delete.failed";
//				model.put("message", message);
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error Message:\n" + e.getMessage());
				e.printStackTrace();
			}
			
//		}
		}
		long otype =2;
		List<TOidgroupInfoInit> oidglist = null;
		
		try{
			
			oidglist = tOidgroupInfoInitDao.findWhereOtypeEquals(otype);
			
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		model.put("oidglist",oidglist);
		return new ModelAndView(getPageView(),"model",model);
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





	public TEventOidInitDao getTEventOidInitDao() {
		return TEventOidInitDao;
	}





	public void setTEventOidInitDao(TEventOidInitDao eventOidInitDao) {
		TEventOidInitDao = eventOidInitDao;
	}





	public TEventTypeInitDao getTEventTypeInitDao() {
		return TEventTypeInitDao;
	}





	public void setTEventTypeInitDao(TEventTypeInitDao eventTypeInitDao) {
		TEventTypeInitDao = eventTypeInitDao;
	}





	public TLinepolMapDao getTLinepolMapDao() {
		return TLinepolMapDao;
	}





	public void setTLinepolMapDao(TLinepolMapDao linepolMapDao) {
		TLinepolMapDao = linepolMapDao;
	}





	public TDeviceInfoDao getTDeviceInfoDao() {
		return TDeviceInfoDao;
	}





	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}





	public TPortInfoDao getTPortInfoDao() {
		return TPortInfoDao;
	}





	public void setTPortInfoDao(TPortInfoDao portInfoDao) {
		TPortInfoDao = portInfoDao;
	}
	
	
	
}
