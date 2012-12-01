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

public class UpdateToDeviceCategoryController implements Controller {

	private GenPkNumber genPkNumber;
	private TCategoryMapInitDao TCategoryMapInitDao;
	TDeviceTypeInitDao TDeviceTypeInitDao;
	private String pageView;
	 private String message = "";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		message = "";
		//List<TCategoryMapInit> tcmlist = null;
		Map<String, Object> mfs = new HashMap<String,Object>();
		String idStr = request.getParameter("id");
		/*String mf = request.getParameter("mf");
		String cate = request.getParameter("cate");
		String subcate = request.getParameter("subcate");*/
		
		//System.out.println("mf from jsp is--"+mf+"cate from jsp is--"+cate+"subcate from jsp is---"+subcate);
		try{
			if(idStr == null) idStr = "";
			Long id = Long.parseLong(idStr);
			TCategoryMapInit dto = TCategoryMapInitDao.findByPrimaryKey(id);
			
		//	tcmlist = TCategoryMapInitDao.findAll();
			//System.out.println("device category list is"+tcmList);
			
			
		/*	if(TDeviceTypeInitDao.findWhereCategoryEquals(id)!= null){
			List<TDeviceTypeInit> devices = TDeviceTypeInitDao.findWhereCategoryEquals(id);
			System.out.println("devices size is"+devices.size());
			if(devices.size()>0){
				message = "yes";
				mfs.put("messageFlag", message);
				
			}
			}*/
			mfs.put("id", id);
			mfs.put("name",dto.getName());
		//	mfs.put("tcmlist", tcmlist);
		
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		/*mfs.put("mf", mf);
		mfs.put("cate", cate);
		mfs.put("subcate", subcate);*/
		
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
