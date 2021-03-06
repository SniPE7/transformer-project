package com.ibm.ncs.web.syslog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.util.Log4jInit;

public class SyslogEventProcessNSController implements Controller {
	
	String pageView;
	String Message = "";
	String message1 = "";
	SyslogEventsProcessNsDao syslogEventsProcessNsDao;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		PagedListHolder pagedList = null;
		List<SyslogEventsProcessNs> tabList = null;
		ServletRequestDataBinder binder = new ServletRequestDataBinder(this);
		binder.bind(request);
		
		
		
		HttpSession session = request.getSession();
		try{/*
			List<SyslogEventsProcessNs> tabList = null;
			//tabList = syslogEventsProcessNsDao.findAll();
			
			model.put("tabList", tabList);*/
			System.out.println("session is :"+session.getAttribute("pagelistns"));
		//	if(session.getAttribute("pagelistns") == null){
			    
				tabList = syslogEventsProcessNsDao.findAll();
				System.out.println("syslog ns size is :"+tabList.size());

				 pagedList = new PagedListHolder(tabList);
				 pagedList.setPageSize(200);
				
				/*}else{
					pagedList = (PagedListHolder)session.getAttribute("pagelistns");
					
					pagedList.setPageSize(200);
					session.removeAttribute("pagelistns");
				}*/
				 checkPageNavigation(request,pagedList);
				 String startandend = computeStartandEndPage(request,pagedList);
				 String[] compute = startandend.split(",");
				 String startpageStr = compute[0];
				 String endpageStr = compute[1];
				 
				 int startpage = Integer.parseInt(startpageStr);
				 int endpage = Integer.parseInt(endpageStr);
				 model.put("startpage", startpage);
				 model.put("endpage", endpage);
				 
				 String startandendrecords = computeRecords(request,pagedList);
				 String[] computeRecords = startandendrecords.split(",");
				 String startRecord = computeRecords[0];
				 String endRecord = computeRecords[1];
				 model.put("startRecord", startRecord);
				 model.put("endRecord", endRecord);
				 
				int total = pagedList.getNrOfElements();
				
				model.put("pagedtabList", pagedList);
				model.put("total", total);
				session.setAttribute("pagelistns", pagedList);
				//model.put("tabList", tabList);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			Message = "baseinfo.list.error";		
			model.put("message", Message);
			e.printStackTrace();
		}
		
		return new ModelAndView(getPageView(), "model", model);
	}							



       private void checkPageNavigation(HttpServletRequest request,PagedListHolder pagedlist) {
   		
   		String cmd = request.getParameter("cmd");
  		String page = request.getParameter("page");
   		
   		if(cmd != null){
   			if(cmd.equalsIgnoreCase("firstPage")){
   				pagedlist.setPage(0);
   			}
   			if(cmd.equalsIgnoreCase("previousPage")){
   				pagedlist.previousPage();
   			}
   			if(cmd.equalsIgnoreCase("nextPage")){
   				pagedlist.nextPage();
   			}
   			if(cmd.equalsIgnoreCase("lastPage")){
   				pagedlist.setPage(pagedlist.getPageCount()-1);
   			}
   			
   		}
   		if(page != null){
   			pagedlist.setPage(Integer.parseInt(page));
   		}
   		
   		
   	}
       
       private String computeStartandEndPage(HttpServletRequest request,PagedListHolder pagedlist){
   		int startpage = 1;
   		int SHOWPAGE = 6;
   		int endpage = 5;
   		int page = pagedlist.getPage();
   		//if(pageStr != null){
   			if(pagedlist.getPageCount() < SHOWPAGE){
   				endpage = pagedlist.getPageCount();
   			}else{
   				if(page < SHOWPAGE - 1){
   					endpage = SHOWPAGE;
   				}else{
   					int i = (int)Math.ceil(page + 1) / SHOWPAGE;
   					System.out.println("i is---"+ i);
   					if(pagedlist.getPageCount() < ((i+1)* SHOWPAGE)){
   						endpage = pagedlist.getPageCount();
   					}else{
   					endpage = SHOWPAGE + i * SHOWPAGE;
   					}
   				}
   				
   			}
   			
   		//}
   		System.out.println("startpage is--"+startpage+" endpage is---"+endpage);
   		return startpage+","+endpage+",";
   		
   		
   		
   		
   	}

       private static String computeRecords(HttpServletRequest request,PagedListHolder pagedlist){
   		
   		int page = pagedlist.getPage();
   		System.out.println("page in pagedlist is "+page);
   		int startRecord = 1;
   		int endRecord = 1;
   		
   			
   		if(page == 0){
				startRecord = 1;
				if(pagedlist.getNrOfElements()<pagedlist.getPageSize()){
					endRecord = pagedlist.getNrOfElements();
				}else{
				endRecord = pagedlist.getPageSize();
				}
   			}else{
   				startRecord = page * pagedlist.getPageSize() + 1;
   		    	if((page+1)*pagedlist.getPageSize()>pagedlist.getNrOfElements()){
   				   endRecord = pagedlist.getNrOfElements();
   		    	}else{
   		    		endRecord = startRecord + pagedlist.getPageSize()-1;
   		    	}
   			}
   		
   		return startRecord+","+endRecord+",";
   		
   	}


	public SyslogEventsProcessNsDao getSyslogEventsProcessNsDao() {
		return syslogEventsProcessNsDao;
	}







	public void setSyslogEventsProcessNsDao(
			SyslogEventsProcessNsDao syslogEventsProcessNsDao) {
		this.syslogEventsProcessNsDao = syslogEventsProcessNsDao;
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
