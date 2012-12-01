package com.ibm.ncs.web.syslog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

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

public class DeleteSyslogController implements Controller {
	
	SyslogEventsProcessDao syslogEventsProcessDao;
	
	 private String pageView;
	 private String message = "";
	 
	 
	

	public SyslogEventsProcessDao getSyslogEventsProcessDao() {
		return syslogEventsProcessDao;
	}


	public void setSyslogEventsProcessDao(
			SyslogEventsProcessDao syslogEventsProcessDao) {
		this.syslogEventsProcessDao = syslogEventsProcessDao;
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

		ServletRequestDataBinder binder = new ServletRequestDataBinder(this);
		binder.bind(request);
		
		HttpSession session = request.getSession();
		PagedListHolder	pagedList = (PagedListHolder)session.getAttribute("pagelist");
		
		pagedList.setPageSize(200);
		session.removeAttribute("pagelist");
		
		System.out.println("after remove session content is************"+session.getAttribute("pagelist"));
//	    SyslogEventsProcess syslog = new SyslogEventsProcess();
//		String[] marks = request.getParameterValues("del");
	    String mark = request.getParameter("mark");
//		System.out.println("mark is ---"+mark);
		String manufacture = request.getParameter("manufacture");
//		System.out.println("manu is "+manufacture);
		
		Map<String,Object> model = new HashMap<String,Object>();
		List<SyslogEventsProcess> tabList = null;
		
		if(mark!= null){
	//	for(int i =0 ; i<marks.length;i++){
	//		String mark = marks[i];
			try{
			    
				/*List<SyslogEventsProcess> syslogs = syslogEventsProcessDao.findWhereMarkEquals(mark);
				System.out.println("syslogs is-----"+syslogs);*/
				//if(syslogs != null){
				//	if(syslogs.size()>0){
				//		SyslogEventsProcess syslogtemp = syslogs.get(0);
			            SyslogEventsProcess syslogtemp = syslogEventsProcessDao.findByPrimaryKey(mark, manufacture);
				//		System.out.println("syslogtemp is----"+syslogtemp);
						syslogEventsProcessDao.delete(syslogtemp);
						tabList = syslogEventsProcessDao.findAll();
						pagedList.setSource(tabList);
					//	pagedList.getSource().remove(syslogtemp);
			//		}
			//	}
				Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from syslogEventsProcessDao " + mark);
	//	}	
			}catch(Exception e){
//				String message = "delete.failed";
//				model.put("message", message);
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error Message:\n" + e.getMessage());
				e.printStackTrace();
			}
			
		}
		try{
			/*List<SyslogEventsProcess> tabList = null;
			tabList = syslogEventsProcessDao.findAll();*/

			//pagedList.setPageSize(200);
			
			 checkPageNavigation(request,pagedList);
			 String startandend = computeStartandEndPage(request,pagedList);
		
			 
			 String[] compute = startandend.split(",");
			 String startpageStr = compute[0];
			 String endpageStr = compute[1];
		//	 System.out.println("compute[0] is---"+startpageStr+" and compute[1] is "+endpageStr);
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
			session.setAttribute("pagelist", pagedList);
		//	model.put("tabList", tabList);
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

private static void checkPageNavigation(HttpServletRequest request,PagedListHolder pagedlist) {
		
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


	
}
