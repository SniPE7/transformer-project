package com.ibm.ncs.web.syslog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.LinesEventsNotcareDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dto.LinesEventsNotcare;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.util.Log4jInit;

public class UpdateToLinesNotCareController implements Controller{
	String pageView;
	String Message = "";
	LinesEventsNotcareDao linesEventsNotcareDao ;
	String method = "update";
	
	

	public String getMethod() {
		return method;
	}



	public void setMethod(String method) {
		this.method = method;
	}



	public String getPageView() {
		return pageView;
	}



	public void setPageView(String pageView) {
		this.pageView = pageView;
	}



	public String getMessage() {
		return Message;
	}



	public void setMessage(String message) {
		Message = message;
	}




	public LinesEventsNotcareDao getLinesEventsNotcareDao() {
		return linesEventsNotcareDao;
	}



	public void setLinesEventsNotcareDao(LinesEventsNotcareDao linesEventsNotcareDao) {
		this.linesEventsNotcareDao = linesEventsNotcareDao;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Message = "";
		try{
		String linesnotcare = request.getParameter("linesnotcare");
		List<LinesEventsNotcare> lines = new ArrayList<LinesEventsNotcare>();
		
		if(linesnotcare != null){
			lines = linesEventsNotcareDao.findWhereLinesnotcareEquals(linesnotcare);
		
			if(lines != null){
			   if(lines.size() > 0){
				   LinesEventsNotcare line = new LinesEventsNotcare();
				   line = lines.get(0);
				   
				   model.put("linesnotcare", line.getLinesnotcare());
				
				   
				   
			   }
			}
			
		}
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
			
		}
		model.put("method", method);
		return new ModelAndView(getPageView(),"model",model);
	}

}
