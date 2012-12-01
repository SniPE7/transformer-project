package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDevpolMapDao;
import com.ibm.ncs.model.dao.TEventOidInitDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyDetailsDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.Log4jInit;

public class DeviceOidGroupsController implements Controller {
	
	TOidgroupInfoInitDao tOidgroupInfoInitDao ;
	 private String pageView;
	 TEventOidInitDao TEventOidInitDao;
	 TEventTypeInitDao TEventTypeInitDao;
	 TPolicyDetailsDao TPolicyDetailsDao;
	 TDeviceInfoDao TDeviceInfoDao;
	 TDevpolMapDao TDevpolMapDao;
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		String message = "";
		List<TOidgroupInfoInit> oidglist = null;
		String oidgroupnames = "";
		/*long dtidfromend = 0;
		long dtidfrombegin = 0;*/
		long otype =1;
		Map<String,Object> model = new HashMap<String,Object>();
		
		try{
			oidglist = tOidgroupInfoInitDao.findWhereOtypeEquals(otype);
//			if(oidglist != null && oidglist.size()>0)
//			for(int i=0;i<oidglist.size();i++){
//				TOidgroupInfoInit groupinfo = oidglist.get(i);
//				try{
//					String oidgroupname = groupinfo.getOidgroupname();
//					List<TDevpolMap> devpolmap = TDevpolMapDao.findAll();
//					if(devpolmap != null && devpolmap.size()>0){
//					
//					List<TEventOidInit> eventoids = TEventOidInitDao.findWhereOidgroupnameEquals(oidgroupname);
//					if(eventoids != null && eventoids.size()>0)
//					System.out.println("eventoid list size is---"+eventoids.size());
					
//					for(int j=0;j<eventoids.size();j++){
//						TEventOidInit event = eventoids.get(j);
//						System.out.println("event oidgroupname is----"+event.getOidgroupname());
//						dtidfrombegin = event.getDtid();
//						System.out.println("dtidfrombegin is---"+dtidfrombegin);
//						for(int m =0;m<devpolmap.size();m++){
//							long devid = devpolmap.get(m).getDevid();
//							TDeviceInfo deviceinfo = TDeviceInfoDao.findByPrimaryKey(devid);
//							dtidfromend = deviceinfo.getDtid();
//							System.out.println("dtidfromend is----"+dtidfromend);
//							System.out.println("the two equal ?---"+ (dtidfromend == dtidfrombegin));
//							if(dtidfromend == dtidfrombegin){
//								oidgroupnames += oidgroupname+";";
//								//model.put("oidgroupnames", oidgroupnames);
//								System.out.println("oidgroupnames are ---"+oidgroupnames);
//							}
//							}
//						
//						
//					}
					
					
					
//					}

					
					
				
//			}catch(Exception e){
//				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
//				e.printStackTrace();
//			}
//				
//				
//			}
			

			/*List<TOidgroupInfoInit> occupied = tOidgroupInfoInitDao.listOccupied();
			for (TOidgroupInfoInit dto : occupied){
				oidgroupnames +=dto.getOidgroupname()+";";
			}*/
			
			
		}catch(DaoException e){
			System.out.println("inner----------");
			e.printStackTrace();
			message = "global.db.error";
			model.put("message", message);
			return new ModelAndView("/dberror.jsp","model",model);
		}catch(Exception e){
			System.out.println("outer---------");
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		model.put("oidgroupnames", oidgroupnames);
		model.put("oidglist",oidglist);
		return new ModelAndView(getPageView(), "model",model);
	}




	public String checkDelete(TOidgroupInfoInit groupinfo){
		String oidgroupnames = "";
		long dtidfromend = 0;
		long dtidfrombegin = 0;
		try{
				String oidgroupname = groupinfo.getOidgroupname();
				List<TDevpolMap> devpolmap = TDevpolMapDao.findAll();
				if(devpolmap != null && devpolmap.size()>0){
					for(int m =0;m<devpolmap.size();m++){
					long devid = devpolmap.get(m).getDevid();
					TDeviceInfo deviceinfo = TDeviceInfoDao.findByPrimaryKey(devid);
					dtidfromend = deviceinfo.getDtid();
					System.out.println("dtidfromend is----"+dtidfromend);
					}
				}
				List<TEventOidInit> eventoids = TEventOidInitDao.findWhereOidgroupnameEquals(oidgroupname);
				if(eventoids != null && eventoids.size()>0)
				System.out.println("eventoid list size is---"+eventoids.size());
				
				for(int j=0;j<eventoids.size();j++){
					TEventOidInit event = eventoids.get(j);
					System.out.println("event oidgroupname is----"+event.getOidgroupname());
					dtidfrombegin = event.getDtid();
					System.out.println("dtidfrombegin is---"+dtidfrombegin);
				}
				
					if(dtidfromend == dtidfrombegin){
						oidgroupnames += oidgroupname+"; ";
						//model.put("oidgroupnames", oidgroupnames);
						System.out.println("oidgroupnames are ---"+oidgroupnames);
					}
					
			
				
				
			
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return oidgroupnames;
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





	public TPolicyDetailsDao getTPolicyDetailsDao() {
		return TPolicyDetailsDao;
	}





	public void setTPolicyDetailsDao(TPolicyDetailsDao policyDetailsDao) {
		TPolicyDetailsDao = policyDetailsDao;
	}




	public TDeviceInfoDao getTDeviceInfoDao() {
		return TDeviceInfoDao;
	}




	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}




	public TDevpolMapDao getTDevpolMapDao() {
		return TDevpolMapDao;
	}




	public void setTDevpolMapDao(TDevpolMapDao devpolMapDao) {
		TDevpolMapDao = devpolMapDao;
	}
	
	
	
}
