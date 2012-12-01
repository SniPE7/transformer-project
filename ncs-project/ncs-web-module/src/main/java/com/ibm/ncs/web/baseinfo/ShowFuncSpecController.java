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

import com.ibm.ncs.model.dao.PerformParaDao;
import com.ibm.ncs.model.dao.TEventOidInitDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.Log4jInit;

public class ShowFuncSpecController implements Controller {
	
	private PerformParaDao  PerformParaDao;
	
	private String pageView;

	



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> perparams = new HashMap<String,Object>();
		try{
			String dtId = request.getParameter("dtid");
			if(dtId == null) dtId = "";
			long dtid = Long.parseLong(dtId);
			
		     List<PerformPara> params = PerformParaDao.findByDtidEcode(dtid, 1);
	
			perparams.put("params", params);
			perparams.put("dtid", dtid);
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}	
		return new ModelAndView(getPageView(), "model", perparams);
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



	public PerformParaDao getPerformParaDao() {
		return PerformParaDao;
	}



	public void setPerformParaDao(PerformParaDao performParaDao) {
		PerformParaDao = performParaDao;
	}




	


}
