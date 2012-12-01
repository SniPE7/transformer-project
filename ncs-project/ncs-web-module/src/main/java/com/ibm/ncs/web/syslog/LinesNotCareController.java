package com.ibm.ncs.web.syslog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.LinesEventsNotcareDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.util.Log4jInit;

public class LinesNotCareController implements Controller {
	
	String pageView;
	String Message = "";
	String message1 = "";
	LinesEventsNotcareDao linesEventsNotcareDao ;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			List<LinesEventsNotcare> tabList = null;
			tabList = linesEventsNotcareDao .findAll();
			model.put("tabList", tabList);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			Message = "baseinfo.list.error";		
			model.put("message", Message);
			e.printStackTrace();
		}
	
		return new ModelAndView(getPageView(), "model", model);
	}							









	public LinesEventsNotcareDao getLinesEventsNotcareDao() {
		return linesEventsNotcareDao;
	}









	public void setLinesEventsNotcareDao(LinesEventsNotcareDao linesEventsNotcareDao) {
		this.linesEventsNotcareDao = linesEventsNotcareDao;
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
	

	public String getMessage1() {
		return message1;
	}



	public void setMessage1(String message1) {
		this.message1 = message1;
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
}
