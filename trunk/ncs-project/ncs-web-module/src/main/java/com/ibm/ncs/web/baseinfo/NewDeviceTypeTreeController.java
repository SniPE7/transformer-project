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
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

public class NewDeviceTypeTreeController implements Controller {
	
	private BaseInfoServices baseInfoService;
	private TCategoryMapInitDao TCategoryMapInitDao;
	private TManufacturerInfoInitDao TManufacturerInfoInitDao;
	private TDeviceTypeInitDao TDeviceTypeInitDao;
	private GenPkNumber genPkNumber;
	
	private String pageView;
	private String errorView;
	
	private String message="";
	
	
	
	

	
	
	public String getErrorView() {
		return errorView;
	}



	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public void setBaseInfoService(BaseInfoServices baseInfoService) {
		this.baseInfoService = baseInfoService;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		message = "";
		//href="<%=request.getContextPath() %>/secure/baseinfo/devicetypelist.wss?mf=${theManufacturer.key}&cate=${theCategory.key}&subcate=${theSubCategory.key}" 
		String mf = request.getParameter("mf");
		String cate = request.getParameter("cate");
		String subcate = request.getParameter("subcate");
		
		
		System.out.println("mf is :"+mf+"cate is "+cate+" subcate from jsp is"+subcate);
		Long mrid = null;

		Map<String, Object> mfs = new HashMap<String,Object>();
		
		
		List<DeviceTypeTree> _result = null;
		List<TManufacturerInfoInit> mflist = null;
		List<TCategoryMapInit> catelist = null; 
		
			if(request.getParameter("manufacture")!=null)
			mrid = Long.parseLong(request.getParameter("manufacture"));
			Long categoryId = null;
			if(request.getParameter("category")!=null)
			categoryId = Long.parseLong(request.getParameter("category"));
			
			try{
			if(mrid!=null){
				mf = (TManufacturerInfoInitDao.findByPrimaryKey(mrid)).getMrname();
			}
			if(categoryId!=null){
				cate = (TCategoryMapInitDao.findByPrimaryKey(categoryId)).getName();
			}
			
	
		
			
			String devMfCateSubcate =(String)request.getParameter("devMfCateSubcate");//composed string from filter-select-[->] button
			//System.out.println("form get action string as: parameter="+devMfCateSubcate );
			
			if (null!=devMfCateSubcate && !"".equals(devMfCateSubcate)){
				//System.out.println(devMfCateSubcate);
				try {
				
					List<String> params = new ArrayList<String>();
					StringTokenizer st = new StringTokenizer(devMfCateSubcate,"||");
					while(st.hasMoreTokens()){
						String s = st.nextToken();
						params.add(s);
					}
					//System.out.println("splited as "+params);
				
					mf 		= params.get(0);
					cate	= params.get(1);
					subcate	= params.get(2);
				} catch (Exception e) {
					Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
					e.printStackTrace();
				}
			}
			
			
			
			mflist = TManufacturerInfoInitDao.findAll();
			SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
			sortmanu.Sort(mflist, "getMrname", null);
			
			catelist = TCategoryMapInitDao.findAll();
			SortList<TCategoryMapInit> sortcate = new SortList<TCategoryMapInit>();
			sortcate.Sort(catelist, "getName", null);
			
			mfs.put("mflist", mflist);
			mfs.put("catelist", catelist);
			mfs.put("mf", mf);
			mfs.put("cate", cate);
			mfs.put("subcate", subcate);

		}
		catch (Exception e){
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
}
