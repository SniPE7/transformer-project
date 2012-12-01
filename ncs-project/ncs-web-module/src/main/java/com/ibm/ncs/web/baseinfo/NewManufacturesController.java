package com.ibm.ncs.web.baseinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

import org.springframework.web.servlet.mvc.Controller;

/**
 * @author root
 *
 */
public class NewManufacturesController implements Controller {
	
	private TManufacturerInfoInitDao TManufacturerInfoInitDao;
	private GenPkNumber genPkNumber;
	private String pageView;
	private String name;
	private String objID;
	private String description;
	private String message="";
	private String errorView;
	
	
	
	
	
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



	public String getPageView() {
		return pageView;
	}



	public void setPageView(String pageView) {
		this.pageView = pageView;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getObjID() {
		return objID;
	}



	public void setObjID(String objID) {
		this.objID = objID;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}


  
	

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		Map category = new HashMap();
		message = "";
		try{
			
			TManufacturerInfoInit mfi = new TManufacturerInfoInit();
			String name = arg0.getParameter("name");
			String objectid = arg0.getParameter("objID");
			String description = arg0.getParameter("description");
			/*List<TManufacturerInfoInit> mfis = new ArrayList<TManufacturerInfoInit>();
			try{
				mfis = TManufacturerInfoInitDao.findWhereMrnameEquals(name);
			}catch(Exception ee){
				message = "baseinfo.addmanu.error";
				category.put("message", message);
				category.put("name", name);
				category.put("objID", objectid);
				category.put("description", description);
				ee.printStackTrace();
				Log4jInit.ncsLog.error(this.getClass().getName() + " Inserted to TManufacturerInfoInitDao Failed: " + mfi.toString() + "\n" + ee.getMessage());
				return new ModelAndView(getErrorView(),"model",category);
			}
			if(mfis.size() > 0){
				for(int i=0;i<mfis.size();i++){
					if(mfis.get(i).getMrname().equalsIgnoreCase(arg0.getParameter("name"))){
						message = "baseinfo.addmanu.dul";
						category.put("message", message);						
						category.put("name", name);
						category.put("objID", objectid);
						category.put("description", description);
						return new ModelAndView(getErrorView(),"model",category);
					}
				}
			}*/
			mfi.setMrid(genPkNumber.getID());
			mfi.setObjectid(arg0.getParameter("objID"));
			mfi.setDescription(arg0.getParameter("description"));
			mfi.setMrname(arg0.getParameter("name"));
			try{
				TManufacturerInfoInitDao.insert(mfi);
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TManufacturerInfoInitDao: " + mfi.toString());
			}catch(DataIntegrityViolationException ee){
				message = "baseinfo.addmanu.dul";
				category.put("message", message);
				category.put("name", name);
				category.put("objID", objectid);
				category.put("description", description);
				Log4jInit.ncsLog.error(this.getClass().getName() + " Inserted to TManufacturerInfoInitDao Failed: " + mfi.toString() + "\n" + ee);
				return new ModelAndView(getErrorView(),"model",category);
			}
			catch(Exception ee){
				message = "baseinfo.new.error";
				category.put("message", message);
				category.put("name", name);
				category.put("objID", objectid);
				category.put("description", description);
				ee.printStackTrace();
				Log4jInit.ncsLog.error(this.getClass().getName() + " Inserted to TManufacturerInfoInitDao Failed: " + mfi.toString() + "\n" + ee);
				return new ModelAndView(getErrorView(),"model",category);
			}
			
			
			category.put("name", name);
			category.put("objID", objectid);
			category.put("description", description);
			category.put("refresh", "true");
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
			return new ModelAndView(getErrorView(),"model",category);
		}
		return new ModelAndView(getPageView()+"?refresh=true", "model", category);
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



	

}
