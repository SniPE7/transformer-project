package com.ibm.ncs.web.syslog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.LinesEventsNotcareDao;
import com.ibm.ncs.model.dao.ModuleEventTypeDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class DeleteLinesNotCareController implements Controller {
	
	LinesEventsNotcareDao linesEventsNotcareDao ;
	
	 private String pageView;
	 private String message = "";
	 
	 
	

	

	public LinesEventsNotcareDao getLinesEventsNotcareDao() {
		return linesEventsNotcareDao;
	}

	public void setLinesEventsNotcareDao(LinesEventsNotcareDao linesEventsNotcareDao) {
		this.linesEventsNotcareDao = linesEventsNotcareDao;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}




	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		message = "";

	    LinesEventsNotcare line = new LinesEventsNotcare();
		String[] LinesEventsNotcares = request.getParameterValues("del");
	
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		if(LinesEventsNotcares!= null)
		for(int i =0 ; i<LinesEventsNotcares.length;i++){
			String LinesEventsNotcare = LinesEventsNotcares[i];
			//try{
				List<LinesEventsNotcare> lines = linesEventsNotcareDao.findWhereLinesnotcareEquals(LinesEventsNotcare);
			
				if(lines != null){
					if(lines.size()>0){
						LinesEventsNotcare  linestemp = lines.get(0);
						linesEventsNotcareDao.delete(linestemp);
					}
				}
				Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from syslogEventsProcessDao " + LinesEventsNotcare);
				
			/*}catch(Exception e){
//				String message = "delete.failed";
//				model.put("message", message);
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error Message:\n" + e.getMessage());
				e.printStackTrace();
			}*/
			
		}
		try{
			List<LinesEventsNotcare> tabList = null;
			tabList = linesEventsNotcareDao .findAll();
			model.put("tabList", tabList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			message = "baseinfo.list.error";		
			model.put("message", message);
			e.printStackTrace();
		}


		return new ModelAndView(getPageView(),"model",model);
	}





	





	





	public String getPageView() {
		return pageView;
	}





	public void setPageView(String pageView) {
		this.pageView = pageView;
	}





	
}
