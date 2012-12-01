package com.ibm.ncs.web.syslog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.ibm.ncs.model.dao.DeviceTypeTreeDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TListIpDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DeviceTypeTree;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceInfoPk;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TListIp;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.dto.TPortInfoPk;
import com.ibm.ncs.model.exceptions.SequenceNMDaoException;
import com.ibm.ncs.model.exceptions.TDeviceInfoDaoException;
import com.ibm.ncs.model.exceptions.TDeviceTypeInitDaoException;
import com.ibm.ncs.model.exceptions.TListIpDaoException;
import com.ibm.ncs.service.BaseInfoServices;
import com.ibm.ncs.service.ResourceManServices;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.IPAddr;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;
import com.ibm.ncs.web.resourceman.bean.DeviceInfoFormBean;
import com.ibm.ncs.web.syslog.bean.SyslogBean;

public class NewSyslogNSController implements Controller {

	//SyslogBean form;
	SyslogEventsProcessNsDao syslogEventsProcessNsDao;
	String pageView ;
	String message = "";
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}



	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}

	



	public SyslogEventsProcessNsDao getSyslogEventsProcessNsDao() {
		return syslogEventsProcessNsDao;
	}



	public void setSyslogEventsProcessNsDao(
			SyslogEventsProcessNsDao syslogEventsProcessNsDao) {
		this.syslogEventsProcessNsDao = syslogEventsProcessNsDao;
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
		PagedListHolder	pagedList = (PagedListHolder)session.getAttribute("pagelistns");
		
		pagedList.setPageSize(200);
		session.removeAttribute("pagelistns");
		
		Map<String, Object> model = new HashMap<String, Object>();
		List<SyslogEventsProcessNs> tabList = null;

		try {

			String 	varlist	 		= request.getParameter("varlist");
			String[] 	btimelist1	 	= request.getParameterValues("btimelist");
			String btimelist = "";
	        String[] 	etimelist1		= request.getParameterValues("etimelist");
	        String etimelist =  "";
			String 	filterflag1	 		= request.getParameter("filterflag1");
			String 	filterflag2	= request.getParameter("filterflag2");
			String 	severity1		 	= request.getParameter("severity1");
			String 	severity2	 	= request.getParameter("severity2");
			String 	port	 		= request.getParameter("port");
			String 	notcareflag	 	= request.getParameter("notcareflag");
			String 	type	 	= request.getParameter("type");
			String 	eventtype	 	= request.getParameter("eventtype");
			String 	subeventtype	 	= request.getParameter("subeventtype");
			String 	alertgroup	 	= request.getParameter("alertgroup");
			String 	alertkey	 	= request.getParameter("alertkey");
			String 	summarycn	 	= request.getParameter("summarycn");
			String 	processsuggest	= request.getParameter("processsuggest");
			String 	status	= request.getParameter("status");
			String 	attentionflag	= request.getParameter("attentionflag");
			String 	events	 	= request.getParameter("events");
			String 	origevent	 		= request.getParameter("origevent");
			String 	manufacture		= request.getParameter("manufacture");
			String mark = request.getParameter("mark");

			//validate time
			try{

				btimelist = secondsToString(stringToTime(btimelist1[0]))+"|" +secondsToString(stringToTime(btimelist1[1]))+"|"+secondsToString(stringToTime(btimelist1[2]))+"|"+secondsToString(stringToTime(btimelist1[3]))+"|"+secondsToString(stringToTime(btimelist1[4]))+"|"
		        +secondsToString(stringToTime(btimelist1[5]))+"|"+secondsToString(stringToTime(btimelist1[6]));
//		        System.out.println("btimelist is----"+btimelist);
		        
		        etimelist =  secondsToString(stringToTime(etimelist1[0]))+"|" +secondsToString(stringToTime(etimelist1[1]))+"|"+secondsToString(stringToTime(etimelist1[2]))+"|"+secondsToString(stringToTime(etimelist1[3]))+"|"+secondsToString(stringToTime(etimelist1[4]))+"|"
		        +secondsToString(stringToTime(etimelist1[5]))+"|"+secondsToString(stringToTime(etimelist1[6]));
//		        System.out.println("etimelist is---"+etimelist);
			}catch(Exception e){	
		        btimelist = "00:00:00|00:00:00|00:00:00|00:00:00|00:00:00|00:00:00|00:00:00";
		        etimelist = "23:59:59|23:59:59|23:59:59|23:59:59|23:59:59|23:59:59|23:59:59";				
				message = "baseinfo.addsyslog.timeError";
				model.put("message", message);
				model.put("message", message);
				model.put("alertgroup", alertgroup);
				model.put("alertkey", alertkey);
				model.put("attentionflag", attentionflag);
				model.put("btimelist", btimelist);
				model.put("etimelist", etimelist);
				model.put("events", events);
				model.put("eventtype", eventtype);
				model.put("filterflag1", filterflag1);
				model.put("filterflag2", filterflag2);
				model.put("manufacture", manufacture);
				model.put("mark", mark);
				model.put("notcareflag", notcareflag);
				model.put("origevent", origevent);
				model.put("port", port);
				model.put("processsuggest", processsuggest);
				model.put("severity1", severity1);
				model.put("severity2", severity2);
				model.put("status", status);
				model.put("subeventtype", subeventtype);
				model.put("summarycn", summarycn);
				model.put("type", type);
				model.put("varlist", varlist);
				List<TManufacturerInfoInit> manus = null;
				manus = TManufacturerInfoInitDao.findAll();
				model.put("manus", manus);
				model.put("method", "new");
				session.setAttribute("pagelistns", pagedList);
				return new ModelAndView("/secure/syslog/syslogns.jsp","model",model);
			}	
			
			SyslogEventsProcessNs syslogs = syslogEventsProcessNsDao.findByPrimaryKey(mark, manufacture);
			System.out.println("syslogns is :" +syslogs);
			if(syslogs != null){
					message = "baseinfo.addsyslog.dul";
					model.put("message", message);
					model.put("message", message);
					model.put("alertgroup", alertgroup);
					model.put("alertkey", alertkey);
					model.put("attentionflag", attentionflag);
					model.put("btimelist", btimelist);
					model.put("etimelist", etimelist);
					model.put("events", events);
					model.put("eventtype", eventtype);
					model.put("filterflag1", filterflag1);
					model.put("filterflag2", filterflag2);
					model.put("manufacture", manufacture);
					model.put("mark", mark);
					model.put("notcareflag", notcareflag);
					model.put("origevent", origevent);
					model.put("port", port);
					model.put("processsuggest", processsuggest);
					model.put("severity1", severity1);
					model.put("severity2", severity2);
					model.put("status", status);
					model.put("subeventtype", subeventtype);
					model.put("summarycn", summarycn);
					model.put("type", type);
					model.put("varlist", varlist);
					List<TManufacturerInfoInit> manus = null;
					manus = TManufacturerInfoInitDao.findAll();
					SortList sortmanu = new SortList();
					sortmanu.Sort(manus, "getMrname", null);
					model.put("manus", manus);
					model.put("method", "new");
					session.setAttribute("pagelistns", pagedList);
					return new ModelAndView("/secure/syslog/syslogns.jsp","model",model);
				}
			System.out.println("varlist--"+varlist+"--btimelist--"+btimelist+"--etimelist--"+etimelist+"--filterflag1--"+filterflag1
					+"--filterflag2--"+filterflag2+"--severity1--"+severity1+"--severity2--"+severity2+"--port--"+port+"--notcareflag--"
					+"--type--"+type+"--eventtype--"+eventtype+"--subeventtype--"+subeventtype+"--alertgroup--"+alertgroup+"--alertkey--"+
					alertkey+"--summarycn--"+summarycn+"--processsuggest--"+processsuggest+"--status--"+status+"--attentionflag--"+
					attentionflag+"--events--"+events+"--origevent--"+origevent+"--manufacture--"+manufacture);
			
			    SyslogEventsProcessNs syslog = new SyslogEventsProcessNs();
				syslog.setAlertgroup(alertgroup);
				syslog.setAlertkey(alertkey);
				syslog.setAttentionflag(Long.parseLong(attentionflag));
				syslog.setBtimelist(btimelist);
				syslog.setEtimelist(etimelist);
				syslog.setEvents(events);
				syslog.setEventtype(Long.parseLong(eventtype));
				syslog.setFilterflag1(Long.parseLong(filterflag1));
				
				syslog.setFilterflag2(Long.parseLong(filterflag2));
				syslog.setManufacture(manufacture);
				syslog.setMark(mark);
				syslog.setNotcareflag(Long.parseLong(notcareflag));
				syslog.setOrigevent(origevent);
				syslog.setPort(port);
				syslog.setProcesssuggest(processsuggest);
				syslog.setSeverity1(Long.parseLong(severity1));
				syslog.setSeverity2(Long.parseLong(severity2));
				syslog.setStatus(status);
				syslog.setSubeventtype(Long.parseLong(subeventtype));
				syslog.setSummarycn(summarycn);
				syslog.setType(Long.parseLong(type));
				syslog.setVarlist(varlist);
				try{
				syslogEventsProcessNsDao.insert(syslog);
				tabList = syslogEventsProcessNsDao.findAll();
				pagedList.setSource(tabList);
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to syslogEventsProcessNsDao: " + syslog.toString());
				}catch (Exception e) {
					System.out.println("new error---------");
					message = "syslog.new.error";
					Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
					model.put("message", message);

					model.put("alertgroup", alertgroup);
					model.put("alertkey", alertkey);
					model.put("attentionflag", attentionflag);
					model.put("btimelist", btimelist);
					model.put("etimelist", etimelist);
					model.put("events", events);
					model.put("eventtype", eventtype);
					model.put("filterflag1", filterflag1);
					model.put("filterflag2", filterflag2);
					model.put("manufacture", manufacture);
					model.put("mark", mark);
					model.put("notcareflag", notcareflag);
					model.put("origevent", origevent);
					model.put("port", port);
					model.put("processsuggest", processsuggest);
					model.put("severity1", severity1);
					model.put("severity2", severity2);
					model.put("status", status);
					model.put("subeventtype", subeventtype);
					model.put("summarycn", summarycn);
					model.put("type", type);
					model.put("varlist", varlist);
					model.put("method", "new");
					List<TManufacturerInfoInit> manus = null;
					manus = TManufacturerInfoInitDao.findAll();
					SortList sortmanu = new SortList();
					sortmanu.Sort(manus, "getMrname", null);
					model.put("manus", manus);
					e.printStackTrace();
					session.setAttribute("pagelistns", pagedList);
					return new ModelAndView("/secure/syslog/syslogns.jsp","model",model);
				}
				
			
		}catch (Exception e) {
			message = "baseinfo.new.failed";
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			model.put("message", message);
			e.printStackTrace();
		}
		
		try{
			/*List<SyslogEventsProcessNs> tabList = null;
			tabList = syslogEventsProcessNsDao.findAll();
			model.put("tabList", tabList);*/
			
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
			session.setAttribute("pagelistns", pagedList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			message = "baseinfo.list.error";	
			
			model.put("message", message);
			e.printStackTrace();
		}
		return new ModelAndView(getPageView(),"model",model);
	}

	

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public String getPageView() {
		return pageView;
	}

public static long stringToTime(String s) throws Exception{
	StringTokenizer st = new StringTokenizer(s, ":");
	String hoursStr = null;
	String minutesStr = null;
	String secondsStr = null;
	try {
		hoursStr = st.nextToken();
		minutesStr = st.nextToken();
		secondsStr = st.nextToken();
	} catch (Exception e) {
	}
	
	hoursStr=(hoursStr==null||hoursStr.equals(""))?"0":hoursStr;
	if(Long.parseLong(hoursStr) < 0 || Long.parseLong(hoursStr) >23)
		throw new Exception();
	minutesStr=(minutesStr==null||minutesStr.equals(""))?"0":minutesStr;
	if(Long.parseLong(minutesStr) < 0 || Long.parseLong(minutesStr) >60)
		throw new Exception();
	secondsStr=(secondsStr==null||secondsStr.equals(""))?"0":secondsStr;
	if(Long.parseLong(secondsStr) < 0 || Long.parseLong(secondsStr) >60)
		throw new Exception();
	
	long hours   = Long.parseLong(hoursStr)*3600;
	long minutes = Long.parseLong(minutesStr)*60;
	long seconds = Long.parseLong(secondsStr);
	
	return hours+minutes+seconds;
	
	
}

//public  String secondsToString(long time){
//	//int seconds = (int) (time % 60) ;
//	int minutes = (int) ((time / 60) % 60);
//	int hours 	= (int) ((time /3600) % 24);
//	
//	//String secondsStr = (seconds <10 ?"0":"")+seconds;
//	String minutesStr = (minutes <10 ?"0":"")+minutes;
//	String hoursStr   = (hours <10   ?"0":"")+hours;
////	System.out.println(hours+" "+minutes+" "+seconds);
//	return new String(hoursStr+":"+minutesStr);
//	
//	
//}

public static String secondsToString(long time){
	int seconds = (int) (time % 60) ;
	int minutes = (int) ((time / 60) % 60);
	int hours 	= (int) ((time /3600) % 24);
	
	String secondsStr = (seconds <10 ?"0":"")+seconds;
	String minutesStr = (minutes <10 ?"0":"")+minutes;
	String hoursStr   = (hours <10   ?"0":"")+hours;
//	System.out.println(hours+" "+minutes+" "+seconds);
	return new String(hoursStr+":"+minutesStr+":"+secondsStr);
	
	
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
