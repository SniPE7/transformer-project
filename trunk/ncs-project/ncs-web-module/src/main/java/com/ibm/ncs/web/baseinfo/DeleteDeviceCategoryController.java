package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.ibm.ncs.model.dao.ModuleEventTypeDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class DeleteDeviceCategoryController implements Controller  {
	
	
	private TCategoryMapInitManager tCategoryMapInitManager;
	private TCategoryMapInitDao TCategoryMapInitDao;
	private TDeviceTypeInitDao TDeviceTypeInitDao;
	GenPkNumber GenPkNumber;
	
	
	 private String pageView;
	 private String message = "";
	
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		message = "";
		Map<String, Object> mfs = new HashMap<String,Object>();
		try{
			Long id = Long.parseLong(request.getParameter("id"));
			
			List<TDeviceTypeInit> devicetype = TDeviceTypeInitDao.findWhereCategoryEquals(id);
			if(devicetype != null && devicetype.size()>0){
				message = "<script language='javascript'>alert('该设备类型含有设备, 目前无法删除 ');</script>";
				mfs.put("message", message);
			}
			else{
			TCategoryMapInitPk pk = new TCategoryMapInitPk(id);
			TCategoryMapInitDao.delete(pk);
			Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from TCategoryMapInit " + pk.toString());
			}
			
			List<TCategoryMapInit> _result = this.tCategoryMapInitManager.getCategory();
			mfs.put("category", _result);
		}catch(Exception e){
			String message = "delete.failed";
			mfs.put("message", message);
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error Message:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("/secure/baseinfo/categorys.jsp", "model", mfs);

	}


private int parseCommand(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 0;
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
		return GenPkNumber;
	}


	public void setGenPkNumber(GenPkNumber genPkNumber) {
		GenPkNumber = genPkNumber;
	}


	

	public TCategoryMapInitManager getTCategoryMapInitManager() {
		return tCategoryMapInitManager;
	}


	public void setTCategoryMapInitManager(
			TCategoryMapInitManager categoryMapInitManager) {
		tCategoryMapInitManager = categoryMapInitManager;
	}


	public TCategoryMapInitDao getTCategoryMapInitDao() {
		return TCategoryMapInitDao;
	}


	public void setTCategoryMapInitDao(TCategoryMapInitDao categoryMapInitDao) {
		TCategoryMapInitDao = categoryMapInitDao;
	}


	public TDeviceTypeInitDao getTDeviceTypeInitDao() {
		return TDeviceTypeInitDao;
	}


	public void setTDeviceTypeInitDao(TDeviceTypeInitDao deviceTypeInitDao) {
		TDeviceTypeInitDao = deviceTypeInitDao;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	

	
	
	
	
	
	


	










}
