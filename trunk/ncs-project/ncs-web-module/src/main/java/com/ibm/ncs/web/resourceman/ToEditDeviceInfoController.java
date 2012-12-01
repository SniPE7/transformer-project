package com.ibm.ncs.web.resourceman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.test.Flashfile;
import com.ibm.ncs.web.resourceman.bean.DeviceInfoFormBean;

public class ToEditDeviceInfoController implements Controller {

	TDeviceTypeInitDao TDeviceTypeInitDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	DefMibGrpDao  DefMibGrpDao;
	String pageView;
	

	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}


	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}


	public String getPageView() {
		return pageView;
	}


	public void setPageView(String pageView) {
		this.pageView = pageView;
	}


	public TDeviceTypeInitDao getTDeviceTypeInitDao() {
		return TDeviceTypeInitDao;
	}


	public void setTDeviceTypeInitDao(TDeviceTypeInitDao deviceTypeInitDao) {
		TDeviceTypeInitDao = deviceTypeInitDao;
	}


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		//Map mod = new HashMap();
		Map<String,Object> mod = (Map<String, Object>)request.getSession().getAttribute("DevInfoMap");
		boolean newmap = false;
		if (mod==null){
			mod = new HashMap<String, Object>();		
			newmap= true;
		}
		System.out.println("session obj newmap="+newmap+"; mod="+mod);		
		try{
			String 	manufacture	 	= request.getParameter("manufacture");
			mod.put("manufacture", manufacture);
			String dtid = request.getParameter("dtid");
			mod.put("dtid", dtid);
			String 	devtype		 	= request.getParameter("devtype");
			mod.put("devtype", devtype);
			String 	devsubtype	 	= request.getParameter("devsubtype");
			mod.put("devsubtype", devsubtype);
			String 	model	 		= request.getParameter("model");
			mod.put("model", model);
			String 	objectid	 	= request.getParameter("objectid");
			mod.put("objectid", objectid);
			DeviceInfoFormBean form = (DeviceInfoFormBean) mod.get("form");
			form = (form == null)?new DeviceInfoFormBean():form;			
			form.setDtid(Long.parseLong(dtid));
			form.setMrid(request.getParameter("mrid"));
			form.setDevtype(devtype);
			form.setDevsubtype(devsubtype);
			form.setModel(model);
			form.setManufacture(manufacture);
			form.setObjectid(objectid);
			String action = request.getParameter("formAction");

			if("edit".equals(action)){
				return new ModelAndView("/secure/resourceman/editdeviceInfo.jsp","model",mod);
			}else if("add".equals(action)){
				return new ModelAndView("/secure/resourceman/adddeviceInfo.jsp","model",mod);
			}
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView(getPageView(),"model",mod);
	}



	public DefMibGrpDao getDefMibGrpDao() {
		return DefMibGrpDao;
	}


	public void setDefMibGrpDao(DefMibGrpDao defMibGrpDao) {
		DefMibGrpDao = defMibGrpDao;
	}
}
