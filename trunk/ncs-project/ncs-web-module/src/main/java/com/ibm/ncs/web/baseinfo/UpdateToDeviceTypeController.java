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
import com.ibm.ncs.util.SortList;

public class UpdateToDeviceTypeController implements Controller {

	private TDeviceTypeInitDao TDeviceTypeInitDao;
	private GenPkNumber genPkNumber;
	private TCategoryMapInitDao TCategoryMapInitDao;
	private TManufacturerInfoInitDao TManufacturerInfoInitDao;
	private String pageView;
	//private String message = "";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		String dtidStr = request.getParameter("dtid");
		if(dtidStr == null) dtidStr = "";
		Long dtid = Long.parseLong(dtidStr);
		
		String mf = request.getParameter("mf");
		String cate = request.getParameter("cate");
		String subcate = request.getParameter("subcate");
		
		System.out.println("mf from hidden is---"+mf+"cate from hidden is--"+cate+"subcate from hidden is---"+subcate);
		
		TManufacturerInfoInit manu = null;
		List<TManufacturerInfoInit> mflist = null;
		List<TCategoryMapInit> catelist = null; 
		Map<String, Object> mfs = new HashMap<String,Object>();
		try{
			if(request.getParameter("mrid")!= null){
			Long mrid = Long.parseLong(request.getParameter("mrid"));
			manu = TManufacturerInfoInitDao.findByPrimaryKey(mrid);
			}
			
			TDeviceTypeInit dto = null;
			if(dtid != 0){
			 dto = TDeviceTypeInitDao.findByPrimaryKey(dtid);
			}/*else{
				message = "The Manufacture has no device!";
			}*/
			
			mflist = TManufacturerInfoInitDao.findAll();
			SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
			sortmanu.Sort(mflist, "getMrname", null);
			
			catelist = TCategoryMapInitDao.findAll();
			SortList<TCategoryMapInit> sortcate = new SortList<TCategoryMapInit>();
			sortcate.Sort(catelist, "getName", null);
			
			mfs.put("dtid", dtid);
			mfs.put("mflist", mflist);
			mfs.put("catelist", catelist);
			mfs.put("manu", manu);
			
			mfs.put("subCategory",dto.getSubcategory());
			mfs.put("model", dto.getModel());
			mfs.put("category", dto.getCategory());
			mfs.put("mrid", dto.getMrid());
			mfs.put("objectid", dto.getObjectid());
			mfs.put("description", dto.getDescription());	
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		mfs.put("mf", mf);
		mfs.put("cate", cate);
		mfs.put("subcate", subcate);
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




}
