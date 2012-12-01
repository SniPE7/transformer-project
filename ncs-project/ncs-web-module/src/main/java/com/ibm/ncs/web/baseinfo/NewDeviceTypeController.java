package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

/**
 * @author root
 *
 */
public class NewDeviceTypeController implements Controller {
	
	private TCategoryMapInitDao TCategoryMapInitDao;
	private GenPkNumber genPkNumber;
	private String pageView;
	private String name;
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



	


  
	

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		Map devices = new HashMap();
		message = "";
		try{
		//	TDeviceTypeInit cmi = new TDeviceTypeInit();
			TCategoryMapInit cmi = new TCategoryMapInit();
		//	System.out.println("name is---"+arg0.getParameter("name"));
			List<TCategoryMapInit> cmis = TCategoryMapInitDao.findAll();
			if(cmis != null && cmis.size()>0){
				for(int i =0;i<cmis.size();i++){
					if(cmis.get(i).getName().equalsIgnoreCase(arg0.getParameter("name"))){
						System.out.println("dul-------------");
						message = "baseinfo.adddevicecate.dul";
						devices.put("message", message);
						devices.put("name", arg0.getParameter("name"));
						return new ModelAndView(getErrorView(),"model",devices);
					}
				}
			}
			/*List<TCategoryMapInit> cmis = TCategoryMapInitDao.findWhereNameEquals(arg0.getParameter("name"));
			if(cmis.size()>0){
				for(int i=0;i<cmis.size();i++){
					if(cmis.get(i).getName().equalsIgnoreCase(arg0.getParameter("name"))){
						message = "baseinfo.adddevicecate.dul";
						devices.put("message", message);
						return new ModelAndView(getErrorView(),"model",devices);
					}
				}
			}*/
			cmi.setId(genPkNumber.getID());
			cmi.setName(arg0.getParameter("name"));
			cmi.setFlag("0");
			try{
			TCategoryMapInitDao.insert(cmi);
			Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TCategoryMapInitDao: " + cmi.toString());
			}catch(Exception ee){
				message = "baseinfo.new.error";
				devices.put("message", message);
				devices.put("name", arg0.getParameter("name"));
				return new ModelAndView("/secure/baseinfo/newDeviceType.jsp", "model", devices);
				
			}
			devices.put("name", arg0.getParameter("name"));
			devices.put("refresh","true");
			
		
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
			return new ModelAndView(getErrorView(),"model",devices);
		}
		
		return new ModelAndView(getPageView(), "model", devices);
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





	

}
