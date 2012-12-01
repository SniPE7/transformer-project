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
import com.ibm.ncs.model.dto.TCategoryMapInitPk;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TDeviceTypeInitPk;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInitPk;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class UpdateDeviceCategoryController implements Controller {

	private BaseInfoServices baseInfoService;
	private TCategoryMapInitDao TCategoryMapInitDao;
	private GenPkNumber genPkNumber;
	
	private String pageView;
	private String message;

	
	
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
		
		String idStr = request.getParameter("id");
		if(idStr==null) idStr="";
		Long id = Long.parseLong(idStr);
		TCategoryMapInit cmi = TCategoryMapInitDao.findByPrimaryKey(id);
		String name = request.getParameter("name");
		System.out.println("name from jsp is: "+name);
		Map<String, Object> mfs = new HashMap<String,Object>();
		try{
			
				List<TCategoryMapInit> cmis =  TCategoryMapInitDao.findAll();
				if(cmis != null && cmis.size()>0){
					for(int i=0;i<cmis.size();i++){
						String cm = cmis.get(i).getName();
						System.out.println("cm in db is "+cm);
						if(cm.equalsIgnoreCase(name)){
							System.out.println("dul----------");
							message = "baseinfo.adddevicecate.dul";
							mfs.put("message", message);
							mfs.put("id", id);
							mfs.put("name", name);
							
							return new ModelAndView("/secure/baseinfo/updateDeviceCategory.jsp","model",mfs);
						}
					}
				}
				
				TCategoryMapInitPk pk = new TCategoryMapInitPk(id);
				cmi.setName(name);
				
				try{
			    	TCategoryMapInitDao.update(pk, cmi);			
			     	Log4jInit.ncsLog.info(this.getClass().getName() + " updated to TOidgroupDetailsInitDao: pk=" + pk.toString() + " \tdto=" + cmi.toString());
				}catch(Exception e){
					message = "baseinfo.update.fail";
					mfs.put("message", message);
					mfs.put("id", id);
					mfs.put("name", name);
					
					
					return new ModelAndView("/secure/baseinfo/updateDeviceCategory.jsp","model",mfs);
				}
				
			
	
			List<TCategoryMapInit> tcmlist = TCategoryMapInitDao.findAll();	
			mfs.put("category", tcmlist);
			mfs.put("id", id);
			mfs.put("name", name);
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



	
	

	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}



	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}



	


}
