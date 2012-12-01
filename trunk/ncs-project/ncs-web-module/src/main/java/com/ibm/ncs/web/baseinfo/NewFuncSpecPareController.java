package com.ibm.ncs.web.baseinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TEventOidInitDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class NewFuncSpecPareController implements Controller {
	
	
	private GenPkNumber genPkNumber;
	
	private TEventTypeInitDao TEventTypeInitDao;
	private TOidgroupInfoInitDao TOidgroupInfoInitDao;
    private	TEventOidInitDao TEventOidInitDao;
	
	private String pageView;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		Map<String, Object> performs = new HashMap<String,Object>();
		try{
			Long eveid = Long.parseLong(request.getParameter("eveid"));
			String oidgroupname =request.getParameter("oidgroupname");
			
			List<TEventTypeInit> tei = null;
			tei = TEventTypeInitDao.findAll();

			List<TOidgroupInfoInit> toi = null;
			toi = TOidgroupInfoInitDao.findAll();
//			System.out.println("the oidgroup is ---"+toi);

			performs.put("tei", tei);
			performs.put("toi",toi);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		
	    return new ModelAndView(getPageView(), "model", performs);
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



	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}



	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}



	public TEventTypeInitDao getTEventTypeInitDao() {
		return TEventTypeInitDao;
	}



	public void setTEventTypeInitDao(TEventTypeInitDao eventTypeInitDao) {
		TEventTypeInitDao = eventTypeInitDao;
	}



	public TOidgroupInfoInitDao getTOidgroupInfoInitDao() {
		return TOidgroupInfoInitDao;
	}



	public void setTOidgroupInfoInitDao(TOidgroupInfoInitDao oidgroupInfoInitDao) {
		TOidgroupInfoInitDao = oidgroupInfoInitDao;
	}



	



	
}
