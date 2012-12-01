package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.Log4jInit;

public class OidGroupsController implements Controller {
	
	TOidgroupInfoInitDao tOidgroupInfoInitDao ;
	 private String pageView;
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		String strOidtype = request.getParameter("otype");
		Map<String, Object> oidgroups = new HashMap<String,Object>();
		try{
			if(strOidtype == null || "".equals(strOidtype)){
				Log4jInit.ncsLog.error(this.getClass().getName() + " OIDType is null");
				
				return new ModelAndView("/secure/baseinfo/oidgroups.jsp", "message" ,"Your session is expired, please do it start over.");
			}
			long otype =  Long.valueOf(strOidtype);
			
			List<TOidgroupInfoInit> oidglist = tOidgroupInfoInitDao.findWhereOtypeEquals(otype);
			oidgroups.put("oidgroups", oidglist);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("/secure/baseinfo/oidgroups.jsp", "model", oidgroups);
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
}
