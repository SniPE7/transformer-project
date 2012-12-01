package com.ibm.ncs.web.baseinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
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
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class UpdateToManufaturesController implements Controller {

	private TDeviceTypeInitDao TDeviceTypeInitDao;
	private GenPkNumber genPkNumber;
	private TCategoryMapInitDao TCategoryMapInitDao;
	private TManufacturerInfoInitDao TManufacturerInfoInitDao;
	private BaseInfoServices baseInfoService;
	private String pageView;
	private String message = "";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		message = "";
		Map<String, Object> mfs = new HashMap<String,Object>();
		try{
			Long mrid = Long.parseLong(request.getParameter("mrid"));
			TManufacturerInfoInit dto = TManufacturerInfoInitDao.findByPrimaryKey(mrid);
			/*List<TManufacturerInfoInit> mflist = null;
			mflist = TManufacturerInfoInitDao.findAll();*/
			
			//check if there are devices under the manufacture
			String mf = null;
			if(mrid != null){
				 mf = (TManufacturerInfoInitDao.findByPrimaryKey(mrid)).getMrname();
			}
			
			
				/*List<TDeviceTypeInit> devicetree = TDeviceTypeInitDao.findWhereMridEquals(mrid);
				System.out.println("device list size is-----"+devicetree.size());
			
			if(devicetree.size()>0){
				mfs.put("messageFlag", "yes");
			}else{
				mfs.put("messageFlag", "no");
			}*/
			/*if(baseInfoService.findDeviceTypeByManufacture(mf) == null){
				mfs.put("mrid", mrid);
				mfs.put("mflist", mflist);
				mfs.put("mrname",dto.getMrname());
				mfs.put("objectid", dto.getObjectid());
				mfs.put("description", dto.getDescription());
			}else{
				List<DeviceTypeTree> devicetree = baseInfoService.findDeviceTypeByManufacture(mf);
				System.out.println("device list size is-----"+devicetree.size());
			
			if(devicetree.size()>0){
				mfs.put("messageFlag", "yes");
			}else{
				mfs.put("messageFlag", "no");
			}*/
			mfs.put("mrid", mrid);
			//mfs.put("mflist", mflist);
			mfs.put("mrname",dto.getMrname());
			mfs.put("objectid", dto.getObjectid());
			mfs.put("description", dto.getDescription());
			//}
			
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

	public BaseInfoServices getBaseInfoService() {
		return baseInfoService;
	}


	public void setBaseInfoService(BaseInfoServices baseInfoService) {
		this.baseInfoService = baseInfoService;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}


	

}
