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
import com.ibm.ncs.model.dao.TEventOidInitDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TLinepolMapDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyDetailsDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.Log4jInit;

public class LinePortOidGroupsController implements Controller {
	
	TOidgroupInfoInitDao tOidgroupInfoInitDao ;
	 private String pageView;
	 TEventOidInitDao TEventOidInitDao;
	 TEventTypeInitDao TEventTypeInitDao;
	 TPolicyDetailsDao TPolicyDetailsDao;
	 TLinepolMapDao TLinepolMapDao;
	 TDeviceInfoDao TDeviceInfoDao;
	 TPortInfoDao TPortInfoDao;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		
		long otype =2;
		List<TOidgroupInfoInit> oidglist = null;
        Map<String,Object> model = new HashMap<String,Object>();
     /*   long dtidfrombegin = 0;
        long dtidfromend = 0;
        
        List<TLinepolMap> linepolmap = null;*/
        String oidgroupnames = "";
		String message = "";
		try{
			oidglist = tOidgroupInfoInitDao.findWhereOtypeEquals(otype);
			/*linepolmap = TLinepolMapDao.findAll();
			if(oidglist != null && oidglist.size()>0)
			for(int i=0;i<oidglist.size();i++){
				TOidgroupInfoInit groupinfo = oidglist.get(i);
				String oidgroupname = groupinfo.getOidgroupname();
				List<TEventOidInit> eventoids = TEventOidInitDao.findWhereOidgroupnameEquals(oidgroupname);
				if(eventoids != null && eventoids.size()>0)
				System.out.println("eventoid list size is---"+eventoids.size());
				for(int j=0;j<eventoids.size();j++){
					TEventOidInit event = eventoids.get(j);
					System.out.println("event oidgroupname is----"+event.getOidgroupname());
				    dtidfrombegin = event.getDtid();
					if(linepolmap != null && linepolmap.size()>0)
					for(int m=0;m<linepolmap.size();m++){
						TLinepolMap linepol = linepolmap.get(m);
						long ptid = linepol.getPtid();
						TPortInfo port = TPortInfoDao.findByPrimaryKey(ptid);
						long devid = port.getDevid();
						TDeviceInfo device = TDeviceInfoDao.findByPrimaryKey(devid);
						dtidfromend = device.getDtid();
						
						if(dtidfrombegin == dtidfromend){
							oidgroupnames += oidgroupname+";";
							model.put("oidgroupnames", oidgroupnames);
							System.out.println("oidgroupnames are ---"+oidgroupnames);
							System.out.println("oidgroupnames are ************"+oidgroupnames);
							System.out.println("grouopname from map is-----"+model.get("oidgroupnames"));
						}
						
						
					}
			
				}
				
			}
			*/
			/* List<TOidgroupInfoInit> occupied = tOidgroupInfoInitDao.listOccupied();
				for (TOidgroupInfoInit dto : occupied){
					oidgroupnames +=dto.getOidgroupname()+";";
				}*/
				
		}catch(DaoException e){
			e.printStackTrace();
			message = "global.db.error";
			model.put("message", message);
			return new ModelAndView("/dberror.jsp","model",model);
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
       
		
		
		model.put("oidglist",oidglist);
		model.put("oidgroupnames", oidgroupnames);
		return new ModelAndView(getPageView(), "model",model);
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
