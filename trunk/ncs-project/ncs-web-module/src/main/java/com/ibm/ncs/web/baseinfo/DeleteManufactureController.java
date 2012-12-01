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
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class DeleteManufactureController implements Controller  {
	
	/*TEventTypeInitDao TEventTypeInitDao;
	TModuleInfoInitDao TModuleInfoInitDao;*/
	private TCategoryMapInitManager tCategoryMapInitManager;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	private TDeviceTypeInitDao TDeviceTypeInitDao;
	GenPkNumber GenPkNumber;
	 private String pageView;
	 private String message="";
	
	 

	public TDeviceTypeInitDao getTDeviceTypeInitDao() {
		return TDeviceTypeInitDao;
	}


	public void setTDeviceTypeInitDao(TDeviceTypeInitDao deviceTypeInitDao) {
		TDeviceTypeInitDao = deviceTypeInitDao;
	}


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		message = "";
		Map<String, Object> mfs = new HashMap<String,Object>();
		try{
			Long mrid = Long.parseLong(request.getParameter("mrid"));
			TManufacturerInfoInitPk pk = new TManufacturerInfoInitPk(mrid);
			
			
			List<TDeviceTypeInit> devicetree = TDeviceTypeInitDao.findWhereMridEquals(mrid);
			if(devicetree != null && devicetree.size()>0){
				message = "<script language='javascript'>alert('该厂商含有设备, 目前无法删除 ');</script>";
			//	System.out.println("message is---"+message);
				mfs.put("message", message);
			}else{
			TManufacturerInfoInitDao.delete(pk);
			Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from TManufacturerInfoInitDao " + pk.toString());
			}
			List<TManufacturerInfoInit> _result = this.tCategoryMapInitManager.getManufacturers();
			mfs.put("manufacturers", _result);
			mfs.put("refresh", "true");
		}catch(Exception e){
//			String message = "";
//			model.put("message", message);
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error Message:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("/secure/baseinfo/manufacturers.jsp", "model", mfs);

	}


private int parseCommand(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}


//
//	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,  Object command, BindException errors) throws ServletException{
//		//TEventTypeInit form = (TEventTypeInit) command; 
//		((TEventTypeInit)command).setGeneral(-1);
//		((TEventTypeInit)command).setEcode(9); //hardcoded
//		TEventTypeInitDao.insert((TEventTypeInit)command);
//		System.out.println("NewPreDefMibSnmpParamGroupsController... onSubmit...="+command);		
//		return new ModelAndView(new RedirectView(getSuccessView()));
//	}
//
//
//	public Object formBackinObject(HttpServletRequest request)throws ServletException {
//		TEventTypeInit eventtypeform = new TEventTypeInit();
//		eventtypeform.setDescription(request.getParameter("description"));
//		System.out.println("formbakingObject...description...");
//		return eventtypeform;
//	}


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


	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}


	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}


	public TCategoryMapInitManager getTCategoryMapInitManager() {
		return tCategoryMapInitManager;
	}


	public void setTCategoryMapInitManager(
			TCategoryMapInitManager categoryMapInitManager) {
		tCategoryMapInitManager = categoryMapInitManager;
	}


	


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	
	


	










}
