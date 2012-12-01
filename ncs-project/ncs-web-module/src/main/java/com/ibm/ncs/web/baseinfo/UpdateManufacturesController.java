package com.ibm.ncs.web.baseinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TDeviceTypeInitPk;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInitPk;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class UpdateManufacturesController implements Controller {

	private BaseInfoServices baseInfoService;
	private TCategoryMapInitDao TCategoryMapInitDao;
	private TManufacturerInfoInitDao TManufacturerInfoInitDao;
	private TDeviceTypeInitDao TDeviceTypeInitDao;
	private GenPkNumber genPkNumber;
	
	private String pageView;
	private String message="";

	
	
	public void setBaseInfoService(BaseInfoServices baseInfoService) {
		this.baseInfoService = baseInfoService;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		

		message = "";
		String mf = null;

		Map<String, Object> mfs = new HashMap<String,Object>();
		List<TManufacturerInfoInit> mflist = null;
		String mridStr = request.getParameter("mrid");
		/*String check = request.getParameter("check");
		
		mfs.put("messageFlag", check);*/
		try{
			if(mridStr == null) mridStr = "";
			Long mrid = Long.parseLong(mridStr);
			String mrname = request.getParameter("mrname");
			String objectid = request.getParameter("objectid");
			String description = request.getParameter("description");
			
			if(mrid!=null){
				mf = (TManufacturerInfoInitDao.findByPrimaryKey(mrid)).getMrname();
			}

			
			if(mf!=null){
				
				TManufacturerInfoInit mfi = new TManufacturerInfoInit();
				mfi = TManufacturerInfoInitDao.findByPrimaryKey(mrid);
			
				TManufacturerInfoInitPk pk = new TManufacturerInfoInitPk(mrid);
				mfi.setMrname(mrname);
				mfi.setDescription(description);
			    mfi.setObjectid(objectid);
			    try{
			    	TManufacturerInfoInitDao.update(pk, mfi);
			    	
			    	Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TManufacturerInfoInitDao: pk=" + pk.toString() + " \tdto=" + mfi.toString());
			    }catch(DataIntegrityViolationException ee){
			    	ee.printStackTrace();
			    	Log4jInit.ncsLog.error(this.getClass().getName() + "  updated to TManufacturerInfoInitDao Failed:pk=" + pk.toString() + " \tdto=" + mfi.toString() + "\n" + ee.getMessage());
					message = "baseinfo.updatemanu.dul";
					mfs.put("message", message);
					mfs.put("mrid", mrid);
					//mfs.put("mflist", mflist);
					mfs.put("mrname",mfi.getMrname());
					mfs.put("objectid", mfi.getObjectid());
					mfs.put("description", mfi.getDescription());
					return new ModelAndView("/secure/baseinfo/updateManufactures.jsp", "model", mfs);
				}
			    catch(Exception ee){
			    	ee.printStackTrace();
			    	Log4jInit.ncsLog.error(this.getClass().getName() + "  updated to TManufacturerInfoInitDao Failed:pk=" + pk.toString() + " \tdto=" + mfi.toString() + "\n" + ee.getMessage());
					message = "baseinfo.update.fail";
					mfs.put("message", message);
					mfs.put("mrid", mrid);
					//mfs.put("mflist", mflist);
					mfs.put("mrname",mfi.getMrname());
					mfs.put("objectid", mfi.getObjectid());
					mfs.put("description", mfi.getDescription());
					return new ModelAndView("/secure/baseinfo/updateManufactures.jsp", "model", mfs);
			    }
				
			   
				
				
			
			}

			mflist = TManufacturerInfoInitDao.findAll();
			mfs.put("manufacturers", mflist);
			mfs.put("mrid", mrid);
			mfs.put("mrname", mrname);
			mfs.put("objectid", objectid);
			mfs.put("description", description);
			mfs.put("refresh", "true");
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView(getPageView(), "model", mfs);
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



	public BaseInfoServices getBaseInfoService() {
		return baseInfoService;
	}



	public TCategoryMapInitDao getTCategoryMapInitDao() {
		return TCategoryMapInitDao;
	}



	public void setTCategoryMapInitDao(TCategoryMapInitDao categoryMapInitDao) {
		TCategoryMapInitDao = categoryMapInitDao;
	}



	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}



	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}



	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}



	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
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
