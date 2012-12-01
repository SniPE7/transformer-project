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
import javax.servlet.http.HttpSession;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

public class SearchDeviceTypeTreeController implements Controller {
	
	
	private DeviceTypeTreeDao deviceTypeTreeDao;
	
	private String pageView;
	private String message="";
	
	

	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}




	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		message = "";
		//href="<%=request.getContextPath() %>/secure/baseinfo/devicetypelist.wss?mf=${theManufacturer.key}&cate=${theCategory.key}&subcate=${theSubCategory.key}" 
		String manu = request.getParameter("manu").trim();
		String category = request.getParameter("category").trim();
		String subcate = request.getParameter("subcate").trim();
		String model = request.getParameter("model").trim();
		String objectid = request.getParameter("objectid").trim();
		String description = request.getParameter("description").trim();
		
		
	//	System.out.println("mf is :"+manu+"cate is "+category+"subcate is "+subcate+" model is "+model+" objectid is "+objectid+" descr is "+description);
		
		Map<String, Object> devicelist = new HashMap<String,Object>();
		List<DeviceTypeTree> _result = null;
		HttpSession session = request.getSession();
		
			if((manu == null||!manu.equals("") )&& (category == null||!category.equals("") )&& (subcate == null||!subcate.equals("") )&& (model == null||!model.equals("") )&& (objectid == null||!objectid.equals("") )&& (description == null||!description.equals(""))){
				message = "baseinfo.search.nocondition";
				devicelist.put("message", message);
				
				devicelist.put("mrName", manu);
				devicelist.put("cateName", category);
				devicelist.put("subcate", subcate);
				devicelist.put("model", model);
				devicelist.put("objectid", objectid);
				devicelist.put("description", description);
				return new ModelAndView("/secure/baseinfo/searchDevicetypelist.jsp","model",devicelist);
			}
    	try{ 
			_result = deviceTypeTreeDao.searchByMulti(manu, category, subcate, model, objectid, description);
			
			if(_result.size() == 0){
				message = "baseinfo.search.nodata";
				devicelist.put("message", message);
				
			}
			devicelist.put("devicelist", _result);
			

		}
		catch (Exception e){
			message = "baseinfo.search.fail";
			devicelist.put("message", message);
			
			devicelist.put("manu", manu);
			devicelist.put("category", category);
			devicelist.put("subcate", subcate);
			devicelist.put("model", model);
			devicelist.put("objectid", objectid);
			devicelist.put("description", description);
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
			return new ModelAndView("/secure/baseinfo/searchDevicetypelist.jsp","model",devicelist);
		} 
		session.setAttribute("searchresult", _result);
		return new ModelAndView(getPageView(), "model", devicelist);
	}




	public String getPageView() {
		return pageView;
	}



	public void setPageView(String pageView) {
		this.pageView = pageView;
	}



	public DeviceTypeTreeDao getDeviceTypeTreeDao() {
		return deviceTypeTreeDao;
	}



	public void setDeviceTypeTreeDao(DeviceTypeTreeDao deviceTypeTreeDao) {
		this.deviceTypeTreeDao = deviceTypeTreeDao;
	}

	

}
