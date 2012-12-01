package com.ibm.ncs.util.excel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.*;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import au.com.bytecode.opencsv.CSVReader;

import com.ibm.ncs.model.dao.BkSnmpEventsProcessDao;
import com.ibm.ncs.model.dao.BkSyslogEventsProcessDao;
import com.ibm.ncs.model.dao.BkSyslogEventsProcessNsDao;
import com.ibm.ncs.model.dao.SnmpEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.model.exceptions.BkSnmpEventsProcessDaoException;
import com.ibm.ncs.model.exceptions.SequenceNMDaoException;
import com.ibm.ncs.model.exceptions.SnmpEventsProcessDaoException;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class Excel2DbAllSyslogController implements Controller {
	
String pageView;
DataSource datasource;
	private int marke =0;
	String Message = "";
	protected SimpleJdbcTemplate jdbcTemplate;
	SyslogEventsProcessDao syslogEventsProcessDao;
	SyslogEventsProcessNsDao syslogEventsProcessNsDao;
	BkSyslogEventsProcessDao bkSyslogEventsProcessDao;
	BkSyslogEventsProcessNsDao bkSyslogEventsProcessNsDao;
	
	
	private GenPkNumber genPkNumber;
	
	private String [] dbfilename= new String []{
			"SNMP_THRESHOLDS", //ok
			"SNMP_EVENTS_PROCESS",
			"ICMP_THRESHOLDS",
			"Events_Attention",
			"Lines_Events_Notcare",
			"SYSLOG_EVENTS_PROCESS",
			"SYSLOG_EVENTS_PROCESS_NS",
			"T_Policy_Details"
	};

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		

    	String mode = request.getParameter("checkmode");
		model.put("mode", mode);
		
		// 获得服务器端上传文件存放目录路径
		final long MAX_SIZE = 1024 * 1024 * 1024;// 设置上传文件最大值
		// 允许上传的文件格式的列表
		final String[] allowedExt = new String[] {"xlsx", "xls"};
		response.setContentType("text/html");
		// 设置字符编码为UTF-8 , 统一编码，处理出现乱码问题
		response.setCharacterEncoding("UTF-8");

		// 实例化一个硬盘文件工�?,用来配置上传组件ServletFileUpload
		DiskFileItemFactory dfif = new DiskFileItemFactory();

		dfif.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是 4K .多于的部分将临时存在硬盘

		dfif.setRepository(new File(request.getRealPath("/")));
		// 设置存放临时文件的目�?,web根目录下的目�?

		// 用以上工厂实例化上传组件
		ServletFileUpload sfu = new ServletFileUpload(dfif);
		// 设置�?大上传大�?
		sfu.setSizeMax(MAX_SIZE);

		PrintWriter out = response.getWriter();
		// 从request得到�?有上传域的列�? 
		List fileList = null;
		try {
		fileList = sfu.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
			Message = "excel.import.error";
			model.put("Message", Message);		
		}
		// 没有文件上传
		if (fileList == null || fileList.size() == 0) {
		Message = "excel.import.nullFile";
		model.put("Message", Message);
		Log4jInit.ncsLog.error("导入的文件为空，请重新选择");
		return new ModelAndView(getPageView(), "model" , model);
		}
		// 得到�?有上传的文件
		Iterator fileItr = fileList.iterator();
		// 循环处理�?有文�?
		while (fileItr.hasNext()) {
		FileItem fileItem = null;
		String path = null;
		long size = 0;
		// 得到当前文件
		fileItem = (FileItem) fileItr.next();
		// 忽略�?单form字段而不是上传域的文件域(<input type="text" />�?)
		if (fileItem == null || fileItem.isFormField()) {
		continue;
		}
		
		// 得到文件的完整路�?
		path = fileItem.getName();
		// 得到文件的大�?
		size = fileItem.getSize();
		if ("".equals(path) || size == 0) {
			Message ="excel.import.nullFile";
			model.put("Message", Message);

			Log4jInit.ncsLog.error("导入的文件为空，请重新选择");
			return new ModelAndView(getPageView(), "model", model);
		}

		// 得到去除路径的文件名
		String t_name = path.substring(path.lastIndexOf("\\") + 1);
		String[] ver = t_name.split("\\.");
		String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);
		int allowFlag = 0;
		int allowedExtCount = allowedExt.length;
		for (; allowFlag < allowedExtCount; allowFlag++) {
		if (allowedExt[allowFlag].equals(t_ext))
		break;
		}
		if (allowFlag == allowedExtCount) {
		
		Message = "excel.import.invalidFileType";
        model.put("Message", Message);
        Log4jInit.ncsLog.error("导入的文件为空，请选择*.xls类型的文件！");
		return new ModelAndView(getPageView(), "model", model);
		}
		String filename;
		if(ver[1].equals("xls")){
			filename=ver[0]+".xls";
		}else{
			filename=ver[0]+".xlsx";
		}
		
		
		 
		try {
		// 保存文件到目录下
	      fileItem.write(new File(request.getRealPath("/") +"/uploadDir/"+ filename));
		  String filePath = request.getRealPath("/")+ "/uploadDir/"+filename;
		  String tableName = ver[0];
		  HSSFWorkbook wbMsg = new HSSFWorkbook();
		  InputStream inStr = new FileInputStream(filePath);
	         
	         Workbook workBook = Workbook.getWorkbook(inStr);
	         // 这里有两种方法获取Sheet(术语：工作表)对象,一种为通过工作表的名字，另一种为通过工作表的下标，从0 开始
	         Sheet[] sheetlist = workBook.getSheets(); 
	         Sheet sheet = null;
	         String sheetName = "";
	         long time = System.currentTimeMillis();
	         boolean hasdatatoimport = false;
	         for(int i=0;i<sheetlist.length;i++){
	        	 sheet = sheetlist[i];
	        	 System.out.println("sheet name is :"+sheet.getName());
	        	
	        	 if(sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS") 
	    				  || sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS_NS")){
	    			  hasdatatoimport = true;
	    		  }else{
	    			  break;
	    		  }
	        	 
	         }
	         
	         System.out.println("hasdata? "+hasdatatoimport);
        	 if(hasdatatoimport == false){
          		Message ="excel.import.noDataToImport";
   	        	model.put("Message", Message);	
   				Log4jInit.ncsLog.error("EXCEL: " + filename + "没有可以导入的数据");
   	    		return new ModelAndView(getPageView(),	"model", model);
          	 }
	         
	       //backup data
	   			
   			 String backupresult = backup(time);
System.out.println("backupresult="+backupresult);
   			 if(backupresult.equalsIgnoreCase("fail")){
   				 Message = "excel.backup.error";
   				 model.put("Message", Message);
   				 return new ModelAndView(getPageView(),	"model", model);
   			 }
	         
	         
	         for(int i=0;i<sheetlist.length;i++){
	        	 sheet = sheetlist[i];
	        	 System.out.println("sheet name is :"+sheet.getName());
	        	 if(sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS") 
	    				  || sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS_NS")){
	   			  //delete
	   			 String deleteresult = deletedatabeforeload(sheet.getName());
	   			 if(deleteresult.equalsIgnoreCase("fail")){
	   				 Message = "excel.restore.deleteerror";
	   				 model.put("Message", Message);
	   				 return new ModelAndView(getPageView(),	"model", model);
	   			 }
	   			
	   			  marke =  loadToDB(filePath,wbMsg,sheet,time);
	           }
	        	
	         }
	     	fileItem.delete();//remove the uploaded file.
	     //    ExcelTODB.deletefromdir(request.getRealPath("/")+"uploadDir");
	     	try {
        		String delupload = request.getRealPath("/") +"/uploadDir";
				ExcelTODB.clearDir(delupload);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	     	SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String timestampStr = sdf.format(new Date());
			System.out.println("timestampstr is "+timestampStr);
			
	     	String errorDir = "/logs/xls";
			String errorXlsFile = "/importSyslogError_"+timestampStr+".xls";
			String errorLongName = request.getRealPath("/") +errorDir+errorXlsFile;
			String errorLinkStr = "<a href='"+request.getContextPath()+errorDir+errorXlsFile+"'>"+"错误信息："+"</a>";
			if(marke<1){
				if(marke == -2){	//no corresponding columns in excel ok
					Message ="excel.import.missingColumn";
					model.put("Message", Message);
					Log4jInit.ncsLog.error("EXCEL: " + filename + " 中的数据列与数据库中不符，请检查！");
	            	//String restoreresult = restoredata(time,sheet.getName());
					String restoreresult = restoredata(time);
					if(restoreresult.equalsIgnoreCase("fail")){
						System.out.println("restore failed--------");
					}
		    		return new ModelAndView(getPageView(),	"model", model);
				}else if(marke == -3){//partially imported, and need to write error msg to another excel sheet				
					Message ="excel.import.partialSuccess";
		        	model.put("Message", Message);	
		        	Log4jInit.ncsLog.error("EXCEL: " + filename + " 中部分数据导入失败，请查看错误日志/logs/importSyslogError_"+timestampStr+".xls");
		        	
		        	model.put("errorLinkStr", errorLinkStr);
		        	try {
		        		String delpath = request.getRealPath("/") +errorDir;
						ExcelTODB.clearDir(delpath);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	//write the error Message to excel:
		        	FileOutputStream outputStream = new FileOutputStream(errorLongName);
		        	try {
						wbMsg.write(outputStream);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
			        	outputStream.close();	
			        	outputStream = null;
					}
		        //	restoredata(time,sheet.getName());
		        //	restoredata(time);
		    		return new ModelAndView(getPageView(),	"model", model);
				}else if(marke == -4){
					Message ="excel.import.noDataToImport";
		        	model.put("Message", Message);	
					Log4jInit.ncsLog.error("EXCEL: " + filename + "没有可以导入的数据");
				//	restoredata(time,sheet.getName());
					restoredata(time);
		    		return new ModelAndView(getPageView(),	"model", model);
				}/*else if(marke == -6){
					 Message = "excel.backup.error";
	   				 model.put("Message", Message);
	   				 return new ModelAndView(getPageView(),	"model", model);
					
				}else if(marke == -7){
					 Message = "excel.restore.deleteerror";
	   				 model.put("Message", Message);
	   				 return new ModelAndView(getPageView(),	"model", model);
					
				}*/
				
				//Message ="excel.import.failed";
				Message ="excel.import.failed";
	        	model.put("Message", Message);		
				Log4jInit.ncsLog.error("EXCEL: " + filename + " 数据导入失败，请检查导入的文件");
				//restore when import fail
			//	restoredata(time,sheet.getName());
				restoredata(time);
	    		return new ModelAndView(getPageView(),	"model", model);
			}
			else{
				Message ="excel.import.success";
				model.put("Message", Message);
				 try {
						ExcelTODB.clearDir(request.getRealPath("/") +errorDir);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				Log4jInit.ncsLog.info("EXCEL: " + filename + " 导入成功");
				
			}
	         
	        /* System.out.println("hasdata? "+hasdatatoimport);
        	 if(hasdatatoimport == false){
          		Message ="excel.import.noDataToImport";
   	        	model.put("Message", Message);	
   				Log4jInit.ncsLog.error("EXCEL: " + filename + "没有可以导入的数据");
   	    		return new ModelAndView(getPageView(),	"model", model);
          	 }*/
   		//  System.out.println("sheetName is :"+sheetName);
	         
		} catch (Exception e) {
			e.printStackTrace();
			Log4jInit.ncsLog.error("EXCEL: " + filename + "导入失败 message:" + e.getMessage());
		}
		}  
		
		model.put("Message", Message);
	    
		return new ModelAndView(getPageView(), "model", model);
	}							

	


	private String restoredata(long time) throws Exception {
		String result = "success";
	//	System.out.println("sheetName in restore is :"+sheetName);
		

			List<SyslogEventsProcess> syslogs = syslogEventsProcessDao.findAll();
			List<SyslogEventsProcessNs> syslognss = syslogEventsProcessNsDao.findAll();
			System.out.println("syslog size is :"+syslogs.size()+" and syslog ns size is :"+syslognss.size());
			
			List<BkSyslogEventsProcess> bksyslogs = bkSyslogEventsProcessDao.findWhereBkTimeEquals(new Date(time));
			List<BkSyslogEventsProcessNs> bksyslognss = bkSyslogEventsProcessNsDao.findWhereBkTimeEquals(new Date(time));
			System.out.println("bksyslog size is :"+bksyslogs.size()+" and bksyslog ns size is :"+bksyslognss.size());
			
			if((syslogs!= null )&& (syslognss != null)){
			//	if(sheetName.equalsIgnoreCase("SYSLOG_EVENTS_PROCESS")){
				System.out.println("---------------");
				if(syslogs.size()>0 ){/*
				for(int i=0;i<syslogs.size();i++){
					SyslogEventsProcess syslog = syslogs.get(i);
					try{
						syslogEventsProcessDao.delete(syslog);
						System.out.println("delete syslog end-----");
						Log4jInit.ncsLog.info(this.getClass().getName()+"delete from snmpEventsProcessDao");
					}catch(Exception e){
						Log4jInit.ncsLog.error(this.getClass().getName()+"delete from snmpEventsProcessDao failed");
					    result = "fail";
					}
				}
				*/
					try{
					syslogEventsProcessDao.deleteAll();
					}catch(Exception e){
						result = "fail";
					}
					}
				
				if(bksyslogs.size()>0){/*
					for(int i=0;i<bksyslogs.size();i++){
						SyslogEventsProcess syslog = new SyslogEventsProcess();
						syslog.setMark(bksyslogs.get(i).getMark());
						syslog.setManufacture(bksyslogs.get(i).getManufacture());
						syslog.setBtimelist(bksyslogs.get(i).getBtimelist());
						syslog.setEtimelist(bksyslogs.get(i).getEtimelist());
						syslog.setFilterflag1(bksyslogs.get(i).getFilterflag1());
						syslog.setFilterflag2(bksyslogs.get(i).getFilterflag2());
						syslog.setSeverity1(bksyslogs.get(i).getSeverity1());
						syslog.setSeverity2(bksyslogs.get(i).getSeverity2());
						syslog.setPort(bksyslogs.get(i).getPort());
						syslog.setNotcareflag(bksyslogs.get(i).getNotcareflag());
						syslog.setType(bksyslogs.get(i).getType());
						syslog.setEventtype(bksyslogs.get(i).getEventtype());
						syslog.setSubeventtype(bksyslogs.get(i).getSubeventtype());
						syslog.setAlertgroup(bksyslogs.get(i).getAlertgroup());
						syslog.setAlertkey(bksyslogs.get(i).getAlertkey());
						syslog.setSummarycn(bksyslogs.get(i).getSummarycn());
						syslog.setProcesssuggest(bksyslogs.get(i).getProcesssuggest());
						syslog.setStatus(bksyslogs.get(i).getStatus());
						syslog.setAttentionflag(bksyslogs.get(i).getAttentionflag());
						syslog.setEvents(bksyslogs.get(i).getEvents());
						syslog.setOrigevent(bksyslogs.get(i).getOrigevent());
						syslog.setVarlist(bksyslogs.get(i).getVarlist());
						
						try{
							syslogEventsProcessDao.insert(syslog);
							Log4jInit.ncsLog.info(this.getClass().getName()+"Inserted to syslogEventsProcessDao :" +syslog.toString());
						//    message = "excel.restore.success";
						 //   model.put("Message", message);
						}catch(Exception e){
							Log4jInit.ncsLog.error(this.getClass().getName()+"Inserted to syslogEventsProcessDao Failed :\tdto=" + syslog.toString() + "\n" + e.getMessage());
							e.printStackTrace();
							result = "fail";
						}
						
					}
				*/
					try{
						syslogEventsProcessDao.batchInsertByBkTime(new Date(time));
						Log4jInit.ncsLog.info(this.getClass().getName()+"Inserted to syslogEventsProcessDao :" );
						
						}catch(Exception e){
							Log4jInit.ncsLog.error(this.getClass().getName()+"Inserted to syslogEventsProcessDao Failed :\tdto=" + "\n" + e.getMessage());
							e.printStackTrace();
							result = "fail";
						}
					}
			//}
				
			//	if(sheetName.equalsIgnoreCase("SYSLOG_EVENTS_PROCESS_NS")){
		//	System.out.println("restore for SYSLOG_EVENTS_PROCESS_NS...");	
				if(syslognss.size()>0){/*
				for(int i=0;i<syslognss.size();i++){
					SyslogEventsProcessNs syslogns = syslognss.get(i);
					try{
						System.out.println("delete in restore--------");
						syslogEventsProcessNsDao.delete(syslogns);
						System.out.println("delete syslogns end-----");
						Log4jInit.ncsLog.info(this.getClass().getName()+"delete from syslogEventsProcessNsDao");
					}catch(Exception e){
						Log4jInit.ncsLog.error(this.getClass().getName()+"delete from snmpEventsProcessNsDao failed");
					    result = "fail";
					}
				}
				*/
					try{
					syslogEventsProcessNsDao.deleteAll();
					}catch(Exception e){
						result = "fail";
					}
					}
				
				
				
				if(bksyslognss.size()>0){/*
					for(int i=0;i<bksyslognss.size();i++){

						SyslogEventsProcessNs syslogns = new SyslogEventsProcessNs();
						syslogns.setMark(bksyslognss.get(i).getMark());
						syslogns.setManufacture(bksyslognss.get(i).getManufacture());
						syslogns.setBtimelist(bksyslognss.get(i).getBtimelist());
						syslogns.setEtimelist(bksyslognss.get(i).getEtimelist());
						syslogns.setFilterflag1(bksyslognss.get(i).getFilterflag1());
						syslogns.setFilterflag2(bksyslognss.get(i).getFilterflag2());
						syslogns.setSeverity1(bksyslognss.get(i).getSeverity1());
						syslogns.setSeverity2(bksyslognss.get(i).getSeverity2());
						syslogns.setPort(bksyslognss.get(i).getPort());
						syslogns.setNotcareflag(bksyslognss.get(i).getNotcareflag());
						syslogns.setType(bksyslognss.get(i).getType());
						syslogns.setEventtype(bksyslognss.get(i).getEventtype());
						syslogns.setSubeventtype(bksyslognss.get(i).getSubeventtype());
						syslogns.setAlertgroup(bksyslognss.get(i).getAlertgroup());
						syslogns.setAlertkey(bksyslognss.get(i).getAlertkey());
						syslogns.setSummarycn(bksyslognss.get(i).getSummarycn());
						syslogns.setProcesssuggest(bksyslognss.get(i).getProcesssuggest());
						syslogns.setStatus(bksyslognss.get(i).getStatus());
						syslogns.setAttentionflag(bksyslognss.get(i).getAttentionflag());
						syslogns.setEvents(bksyslognss.get(i).getEvents());
						syslogns.setOrigevent(bksyslognss.get(i).getOrigevent());
						syslogns.setVarlist(bksyslognss.get(i).getVarlist());
						
						try{
							syslogEventsProcessNsDao.insert(syslogns);
							Log4jInit.ncsLog.info(this.getClass().getName()+"Inserted to syslogEventsProcessNsDao :" +syslogns.toString());
						    System.out.println("restore ns done");
							//    message = "excel.restore.success";
						//    model.put("Message", message);
						}catch(Exception e){
							Log4jInit.ncsLog.error(this.getClass().getName()+"Inserted to syslogEventsProcessNsDao Failed :\tdto=" + syslogns.toString() + "\n" + e.getMessage());
							e.printStackTrace();
							result = "fail";
						}
					
						
					}
				
			*/
					try{
						syslogEventsProcessNsDao.batchInsertByBkTime(new Date(time));
						Log4jInit.ncsLog.info(this.getClass().getName()+"Inserted to syslogEventsProcessNsDao :" );
					    System.out.println("restore ns done");
					
					}catch(Exception e){
						Log4jInit.ncsLog.error(this.getClass().getName()+"Inserted to syslogEventsProcessNsDao Failed :\tdto="  + "\n" + e.getMessage());
						e.printStackTrace();
						result = "fail";
					}
					}
			//	}
				}
			
			
		
//		System.out.println(" "+sheetName+" restore result is :"+result);
		return result;
		
	}




	private String deletedatabeforeload(String sheetName) throws Exception {
		String result = "success";
		
			List<SyslogEventsProcess> syslogs = syslogEventsProcessDao.findAll();
			List<SyslogEventsProcessNs> syslognss = syslogEventsProcessNsDao.findAll();
			System.out.println("syslog size is :"+syslogs.size()+" and syslog ns size is :"+syslognss.size());
			
			if((syslogs!= null )&& (syslognss != null)){
				if(sheetName.equalsIgnoreCase("SYSLOG_EVENTS_PROCESS")){
				System.out.println("---------------");
				if(syslogs.size()>0 ){/*
				for(int i=0;i<syslogs.size();i++){
					SyslogEventsProcess syslog = syslogs.get(i);
					try{
						syslogEventsProcessDao.delete(syslog);
						System.out.println("delete syslog end-----");
						Log4jInit.ncsLog.info(this.getClass().getName()+"delete from snmpEventsProcessDao");
						
					}catch(Exception e){
						Log4jInit.ncsLog.error(this.getClass().getName()+"delete from snmpEventsProcessDao failed");
					    result = "fail";
					}
				}
				*/
					syslogEventsProcessDao.deleteAll();
					System.out.println("after delete syslog size is :"+syslogEventsProcessDao.findAll().size());
					}
			}
				if(sheetName.equalsIgnoreCase("SYSLOG_EVENTS_PROCESS_NS")){
				if(syslognss.size()>0){/*
				for(int i=0;i<syslognss.size();i++){
					SyslogEventsProcessNs syslogns = syslognss.get(i);
					try{
						syslogEventsProcessNsDao.delete(syslogns);
						System.out.println("delete syslogns end-----");
						Log4jInit.ncsLog.info(this.getClass().getName()+"delete from syslogEventsProcessNsDao");
					}catch(Exception e){
						Log4jInit.ncsLog.error(this.getClass().getName()+"delete from snmpEventsProcessNsDao failed");
					    result = "fail";
					}
				}
				*/
					syslogEventsProcessNsDao.deleteAll();
					System.out.println("after delete syslog size is :"+syslogEventsProcessNsDao.findAll().size());
				}
			}
		}
			
	//	System.out.println(" "+sheetName+" delete result is :"+result);
		return result;
	}




	private String backup(long time) throws Exception {
		String result = "success";





		List<SyslogEventsProcess> syslogs = syslogEventsProcessDao.findAll();
		System.out.println("syslogs size is :"+syslogs.size());
		List<SyslogEventsProcessNs> syslognss = syslogEventsProcessNsDao.findAll();
		System.out.println("syslog ns size is :"+syslognss.size());
		
		/*if(syslogs.size()== 0){
			Message = "excel.backup.syslognodata";
			model.put("Message", Message);
			return new ModelAndView(getPageView(),"model",model);
		}*/
	//	if(sheetName.equalsIgnoreCase("SYSLOG_EVENTS_PROCESS")){
		if(syslogs != null){/*
			System.out.println("syslog time is :"+System.currentTimeMillis());
			for(int i=0;i<syslogs.size();i++){
			
			String mark = syslogs.get(i).getMark();
			String manufacture = syslogs.get(i).getManufacture();
			String varlist = syslogs.get(i).getVarlist();
			String btime = syslogs.get(i).getBtimelist();
			String etime = syslogs.get(i).getEtimelist();
			long filterflag1 = syslogs.get(i).getFilterflag1();
			long filterflag2 = syslogs.get(i).getFilterflag2();
			long severity1 = syslogs.get(i).getSeverity1();
			long severity2 = syslogs.get(i).getSeverity2();
			String port = syslogs.get(i).getPort();
			long notcareflag = syslogs.get(i).getNotcareflag();
			long type = syslogs.get(i).getType();
			long eventtype = syslogs.get(i).getEventtype();
			long subeventtype = syslogs.get(i).getSubeventtype();
			String alertgroup = syslogs.get(i).getAlertgroup();
			String alertkey = syslogs.get(i).getAlertkey();
			String summarycn = syslogs.get(i).getSummarycn();
			String processsugguest = syslogs.get(i).getProcesssuggest();
			String status = syslogs.get(i).getStatus();
			long attentionflag = syslogs.get(i).getAttentionflag();
			String events = syslogs.get(i).getEvents();
			String origevent = syslogs.get(i).getOrigevent();
			
			
			BkSyslogEventsProcess bkevent = new BkSyslogEventsProcess();
			long id = genPkNumber.getTaskID();
			bkevent.setBkId(id);
			
			
			bkevent.setBkTime(new Date(time));
			bkevent.setMark(mark);
			bkevent.setManufacture(manufacture);
			bkevent.setVarlist(varlist);
			bkevent.setBtimelist(btime);
			bkevent.setEtimelist(etime);
			bkevent.setFilterflag1(filterflag1);
			bkevent.setFilterflag2(filterflag2);
			bkevent.setSeverity1(severity1);
			bkevent.setSeverity2(severity2);
			bkevent.setPort(port);
			bkevent.setNotcareflag(notcareflag);
			bkevent.setType(type);
			bkevent.setEventtype(eventtype);
			bkevent.setSubeventtype(subeventtype);
			bkevent.setAlertgroup(alertgroup);
			bkevent.setAlertkey(alertkey);
			bkevent.setSummarycn(summarycn);
			bkevent.setProcesssuggest(processsugguest);
			bkevent.setStatus(status);
			bkevent.setAttentionflag(attentionflag);
			bkevent.setEvents(events);
			bkevent.setOrigevent(origevent);
			
			
			try{
				bkSyslogEventsProcessDao.insert(bkevent);
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessDao: " + bkevent.toString());
			    
			}catch(Exception e){
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessDao Failed : \tdto=" + bkevent.toString() + "\n" + e.getMessage());
			    result = "fail";
			}
			}
		*/
			if(syslogs.size()==0){

	        	BkSyslogEventsProcess bkevent = new BkSyslogEventsProcess();
				long id = genPkNumber.getTaskID();
				bkevent.setBkId(id);
				
				bkevent.setBkTime(new Date(time));
				bkevent.setMark(null);
				bkevent.setManufacture(null);
				bkevent.setVarlist(null);
				bkevent.setBtimelist(null);
				bkevent.setEtimelist(null);
				bkevent.setFilterflag1Null(true);
				bkevent.setFilterflag2Null(true);
				bkevent.setSeverity1Null(true);
				bkevent.setSeverity2Null(true);
				bkevent.setPort(null);
				bkevent.setNotcareflagNull(true);
				bkevent.setTypeNull(true);
				bkevent.setEventtypeNull(true);
				bkevent.setSubeventtypeNull(true);
				bkevent.setAlertgroup(null);
				bkevent.setAlertkey(null);
				bkevent.setSummarycn(null);
				bkevent.setProcesssuggest(null);
				bkevent.setStatus(null);
				bkevent.setAttentionflagNull(true);
				bkevent.setEvents(null);
				bkevent.setOrigevent(null);
				
				try{
					bkSyslogEventsProcessDao.insert(bkevent);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessDao: " + bkevent.toString());
				}catch(Exception e){
					result = "fail";
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessDao Failed : \tdto=" + bkevent.toString() + "\n" + e.getMessage());
				}
            	
			}else{
			try{
				System.out.println("1-------------");
			bkSyslogEventsProcessDao.batchInsertByBkTime(new Date(time));
			Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessDao: "+ new Date(time) );
			}catch(Exception e){
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessDao Failed : \t"  + new Date(time)+ "\n" + e);
			    result = "fail";
			}
			}
		}
	//}
//	if(sheetName.equalsIgnoreCase("SYSLOG_EVENTS_PROCESS_NS")){
		if(syslognss != null){/*

			for(int i=0;i<syslognss.size();i++){
			
			String mark = syslognss.get(i).getMark();
			String manufacture = syslognss.get(i).getManufacture();
			String varlist = syslognss.get(i).getVarlist();
			String btime = syslognss.get(i).getBtimelist();
			String etime = syslognss.get(i).getEtimelist();
			long filterflag1 = syslognss.get(i).getFilterflag1();
			long filterflag2 = syslognss.get(i).getFilterflag2();
			long severity1 = syslognss.get(i).getSeverity1();
			long severity2 = syslognss.get(i).getSeverity2();
			String port = syslognss.get(i).getPort();
			long notcareflag = syslognss.get(i).getNotcareflag();
			long type = syslognss.get(i).getType();
			long eventtype = syslognss.get(i).getEventtype();
			long subeventtype = syslognss.get(i).getSubeventtype();
			String alertgroup = syslognss.get(i).getAlertgroup();
			String alertkey = syslognss.get(i).getAlertkey();
			String summarycn = syslognss.get(i).getSummarycn();
			String processsugguest = syslognss.get(i).getProcesssuggest();
			String status = syslognss.get(i).getStatus();
			long attentionflag = syslognss.get(i).getAttentionflag();
			String events = syslognss.get(i).getEvents();
			String origevent = syslognss.get(i).getOrigevent();
			
			
			BkSyslogEventsProcessNs bkeventns = new BkSyslogEventsProcessNs();
			long id = genPkNumber.getTaskID();
			bkeventns.setBkId(id);
			
			
			bkeventns.setBkTime(new Date(time));
			bkeventns.setMark(mark);
			bkeventns.setManufacture(manufacture);
			bkeventns.setVarlist(varlist);
			bkeventns.setBtimelist(btime);
			bkeventns.setEtimelist(etime);
			
			bkeventns.setFilterflag1(filterflag1);
			bkeventns.setFilterflag2(filterflag2);
			bkeventns.setSeverity1(severity1);
			bkeventns.setSeverity2(severity2);
			bkeventns.setPort(port);
			bkeventns.setNotcareflag(notcareflag);
			bkeventns.setType(type);
			bkeventns.setEventtype(eventtype);
			bkeventns.setSubeventtype(subeventtype);
			bkeventns.setAlertgroup(alertgroup);
			bkeventns.setAlertkey(alertkey);
			bkeventns.setSummarycn(summarycn);
			bkeventns.setProcesssuggest(processsugguest);
			bkeventns.setStatus(status);
			bkeventns.setAttentionflag(attentionflag);
			bkeventns.setEvents(events);
			bkeventns.setOrigevent(origevent);
			
			try{
				bkSyslogEventsProcessNsDao.insert(bkeventns);
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessNsDao: " + bkeventns.toString());
			}catch(Exception e){
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessNsDao Failed : \tdto=" + bkeventns.toString() + "\n" + e.getMessage());
			    result = "fail";
			}
			}
		*/
			if(syslognss.size()==0){

			       BkSyslogEventsProcessNs bkeventns = new BkSyslogEventsProcessNs();
					long id = genPkNumber.getTaskID();
					bkeventns.setBkId(id);
					
					bkeventns.setBkTime(new Date(time));
					bkeventns.setMark(null);
					bkeventns.setManufacture(null);
					bkeventns.setVarlist(null);
					bkeventns.setBtimelist(null);
					bkeventns.setEtimelist(null);
					bkeventns.setFilterflag1Null(true);
					bkeventns.setFilterflag2Null(true);
					bkeventns.setSeverity1Null(true);
					bkeventns.setSeverity2Null(true);
					bkeventns.setPort(null);
					bkeventns.setNotcareflagNull(true);
					bkeventns.setTypeNull(true);
					bkeventns.setEventtypeNull(true);
					bkeventns.setSubeventtypeNull(true);
					bkeventns.setAlertgroup(null);
					bkeventns.setAlertkey(null);
					bkeventns.setSummarycn(null);
					bkeventns.setProcesssuggest(null);
					bkeventns.setStatus(null);
					bkeventns.setAttentionflagNull(true);
					bkeventns.setEvents(null);
					bkeventns.setOrigevent(null);
					
					try{
						bkSyslogEventsProcessNsDao.insert(bkeventns);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessNsDao: " + bkeventns.toString());
					}catch(Exception e){
						result = "fail";
						Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessNsDao Failed : \tdto=" + bkeventns.toString() + "\n" + e.getMessage());
					}
					
			       
		           
			
			}else{
			try{
				System.out.println("ns------------------");
				bkSyslogEventsProcessNsDao.batchInsertByBkTime(new Date(time));
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessNsDao: "+new Date(time));
			}catch(Exception e){
				Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to bkSyslogEventsProcessNsDao Failed :" + new Date(time)+"\n" + e.getMessage());
			    result = "fail";
			}
			}
		}
	//}
		
//	System.out.println(" "+sheetName+" backup result is :"+result ); 
		
		return result;
	 	
	}




	public  int  loadToDB(String filePath,HSSFWorkbook errorMsgWb,Sheet sheet,long time) {
		
		   int mark=-1;
	//	   boolean hasdatatoimport = false;
     try {
    	 String[] columnStrs = new String[sheet.getColumns()];
	      for(int c=0; c<sheet.getColumns(); c++){
	    	  columnStrs[c] = sheet.getCell(c, 0).getContents();
	      }
System.out.println("columnStrs=["+printStringArray(columnStrs)+"]");

	      Map<String, ExcelTableMapping> mapping = new HashMap<String, ExcelTableMapping>();
	      Connection con = datasource.getConnection();
	      if(sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS")){
          
          	  
  	 System.out.println("import to syslog----------");
			
  	Statement descPstmt = con.createStatement();
		ResultSet rs = descPstmt.executeQuery("select * from SYSLOG_EVENTS_PROCESS");
		ResultSetMetaData rsmeta = rs.getMetaData();
		
		for(int jj=1; jj<=rsmeta.getColumnCount(); jj++){
			ExcelTableMapping et = new ExcelTableMapping();
			String name = rsmeta.getColumnName(jj);
			String type = rsmeta.getColumnTypeName(jj);
			String isNull = rsmeta.isNullable(jj)==0?"N":"Y";
			  et.setName(name);
			  et.setType(type);
//			  et.setNull(isNull.equalsIgnoreCase("Y")?true:false);
			  if(name.equalsIgnoreCase("mark") || name.equalsIgnoreCase("BTIMELIST")
					  || name.equalsIgnoreCase("ETIMELIST")
					  || name.equalsIgnoreCase("FILTERFLAG1")
					  || name.equalsIgnoreCase("FILTERFLAG2")
					  || name.equalsIgnoreCase("SEVERITY1")
					  || name.equalsIgnoreCase("SEVERITY2")
					  || name.equalsIgnoreCase("NOTCAREFLAG")
					  || name.equalsIgnoreCase("TYPE")
					  || name.equalsIgnoreCase("EVENTTYPE")
					  || name.equalsIgnoreCase("SUBEVENTTYPE")
					  || name.equalsIgnoreCase("ALERTGROUP")
					  || name.equalsIgnoreCase("ATTENTIONFLAG")
					  || name.equalsIgnoreCase("EVENTS")
					  || name.equalsIgnoreCase("MANUFACTURE"))
				  et.setNull(false);
			  else
				  et.setNull(true);
			  
			  //find index in columnStrs
			  et.setIndex(-1);
			  for(int ii=0; ii<columnStrs.length; ii++){
				  if(columnStrs[ii].equalsIgnoreCase(name)){
					  et.setIndex(ii);
					  break;
				  }
			  }
			  if(et.getIndex() == -1){	//no corresponding column in excel. excel file error
				  return -2;
			  }

			  mapping.put(et.getName().toUpperCase(), et);
		  }
		  rs.close();
		  descPstmt.close();
		
 	     String sqls="insert into SYSLOG_EVENTS_PROCESS ( MARK , VARLIST , BTIMELIST , ETIMELIST , FILTERFLAG1 , FILTERFLAG2 , SEVERITY1 , SEVERITY2 , PORT , NOTCAREFLAG , TYPE , EVENTTYPE , SUBEVENTTYPE , ALERTGROUP , ALERTKEY , SUMMARYCN , PROCESSSUGGEST , STATUS, ATTENTIONFLAG , EVENTS , MANUFACTURE , ORIGEVENT )"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
 	     String[] sqlsCol= new String[]{"MARK","VARLIST","BTIMELIST","ETIMELIST","FILTERFLAG1","FILTERFLAG2","SEVERITY1","SEVERITY2","PORT","NOTCAREFLAG","TYPE","EVENTTYPE","SUBEVENTTYPE","ALERTGROUP","ALERTKEY","SUMMARYCN","PROCESSSUGGEST","STATUS","ATTENTIONFLAG","EVENTS","MANUFACTURE","ORIGEVENT"};	
 	     
 	     PreparedStatement pstmt=con.prepareStatement(sqls);
       
    	for (int m = 1; m < sheet.getRows(); m++) {
			  try{
  				 boolean parseExcelError = false;
  	   	//		 boolean ifUpdate = false;
  	   		  
  	   		//	 String MARK = sheet.getCell(mapping.get("MARK").getIndex(),m).getContents();
  	   		//	 String MANUFACTURE = sheet.getCell(mapping.get("MANUFACTURE").getIndex(), m).getContents();
  	   		   /*  SyslogEventsProcess syslog = syslogEventsProcessDao.findByPrimaryKey(MARK, MANUFACTURE);
  	   		     if(syslog != null ){
  	   				    		  ifUpdate = true;
  	   				    	  }*/
  	   		     
  	   		   	//   if(!ifUpdate){
  	   		   		   for(int aa=1; aa<=sqlsCol.length; aa++){
  	   		    	  ExcelTableMapping eTmp = mapping.get(sqlsCol[aa-1].toUpperCase());
  	   		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), m).getContents();
  	   		    	 
  	   		    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
  	   		    		  mark  = -3;
  	   		    		  parseExcelError = true;
  	   		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 字段不能为空！");
  	   		    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName() ,eTmp.getName() +" 字段不能为空！", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
  	   		    		  break;
  	   		    	  }else{
  		    		    	  try{
  		    		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
  		    		    			  if(eTmp.getName().equalsIgnoreCase("EVENTTYPE") // 1-8
  		    		    					  ||eTmp.getName().equalsIgnoreCase("Severity1") //1-7 or 100
  		    		    					  ||eTmp.getName().equalsIgnoreCase("Severity2") //1-7 or 100
  		    		    					  ||eTmp.getName().equalsIgnoreCase("SubEventType")// 1-10
  		    		    					  ||eTmp.getName().equalsIgnoreCase("FilterFlag1") //1-2
  		    		    					  ||eTmp.getName().equalsIgnoreCase("FilterFlag2")
  		    		    					  ||eTmp.getName().equalsIgnoreCase("ATTENTIONFLAG")
  		    		    					  ||eTmp.getName().equalsIgnoreCase("NOTCAREFLAG")
  		    		    					  ||eTmp.getName().equalsIgnoreCase("TYPE")){ //1-2
  		    		    				  if(cellContentTmp != null && cellContentTmp != ""){
  		    		    					  int min=0;
  		    		    					  int max=0;
  		    		    					  if(eTmp.getName().equalsIgnoreCase("FilterFlag1")|| eTmp.getName().equalsIgnoreCase("FilterFlag2")
  		    		    							  ||eTmp.getName().equalsIgnoreCase("NOTCAREFLAG") ||eTmp.getName().equalsIgnoreCase("ATTENTIONFLAG")){
  		    		    						  min=0;
  		    		    						  max=1;
  		    		    					  }else if(eTmp.getName().equalsIgnoreCase("EVENTTYPE")){
  		    		    						  min=1;
  		    		    						  max=8;
  		    		    					  }else if(eTmp.getName().equalsIgnoreCase("Severity1") ||eTmp.getName().equalsIgnoreCase("Severity2")){
  		    		    						  min=1;
  		    		    						  max=7;
  		    		    					  }else if(eTmp.getName().equalsIgnoreCase("SubEventType")){
  		    		    						  min=1;
  		    		    						  max=10;
  		    		    					  }else if(eTmp.getName().equalsIgnoreCase("TYPE")){
  		    		    						  min=0;
  		    		    						  max=2;
  		    		    					  }
  		    		    //					  System.out.println("cellContentTmp=" + cellContentTmp + "\tColumnName=" + eTmp.getName() + "\tmin=" + min + "\tmax=" + max);
  		    		    					  if((eTmp.getName().equalsIgnoreCase("Severity1") ||eTmp.getName().equalsIgnoreCase("Severity2"))){
		    		    							  if(Long.parseLong(cellContentTmp) < min || (Long.parseLong(cellContentTmp) > max && Long.parseLong(cellContentTmp) != 100)) {
		    		    								  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"取值范围为100/"+min+"-" + max, columnStrs);
		    		    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"取值范围为100/"+min+"-" + max);
		    		    		    		    		  mark = -3;
		    		    		    		    		  parseExcelError = true;
		    		    		    		    		  break;
		    		    							  }
		    		    						  }
  		    		    					  else{
  		    		    						  if(Long.parseLong(cellContentTmp) < min || Long.parseLong(cellContentTmp) > max) {
  		    		    							  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"取值范围为"+min+"-" + max, columnStrs);
		    		    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"取值范围为"+min+"-" + max);
		    		    		    		    		  mark = -3;
		    		    		    		    		  parseExcelError = true;
		    		    		    		    		  break;
  		    		    						  }
  		    		    					  }
  		    		    					  pstmt.setLong(aa,Long.parseLong(cellContentTmp));
  		    		    					  continue;
      		    		    			  }else{
  											pstmt.setNull(aa, java.sql.Types.NUMERIC);
  											continue;
      		    		    			  }    		    		    				  
  		    		    			  }
  		    		    			  
  		    		    			  if(cellContentTmp != null && cellContentTmp != ""){
  		    		    				  pstmt.setLong(aa,Long.parseLong(cellContentTmp));
  		    		    			  }else{
											pstmt.setNull(aa, java.sql.Types.NUMERIC);
  		    		    			  }
  		    		    		  }else if(eTmp.getType().equalsIgnoreCase("varchar2")){
  		    		    			  if(eTmp.getName().equalsIgnoreCase("BTIMELIST") || eTmp.getName().equalsIgnoreCase("ETIMELIST")){
  		    		    				  StringTokenizer timeStrs = new StringTokenizer(cellContentTmp, "|");
  		    		    				  if(timeStrs == null || timeStrs.countTokens() != 7){
  		    		    					  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM", columnStrs);
  		    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM");
  		    		    		    		  mark = -3;
  		    		    		    		  parseExcelError = true;
  		    		    		    		  break;
  		    		    				  }else{

  					    					  boolean flagBreak = false;
  					    					  for(int cel=0; cel<7; cel++){
  					    						  String temp = timeStrs.nextToken();
  					    						  StringTokenizer cellStrs = new StringTokenizer(temp, ":");
  				  		    				  if(cellStrs == null || cellStrs.countTokens() != 3){
  				  		    					  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
  				  		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
  				  		    		    		  mark = -3;
  				  		    		    		  parseExcelError = true;
  				  		    		    		  flagBreak = true;
  				  		    		    		  break;
  					    						  }else{
  					    							  try{
  					    								  String cellTmp1 = cellStrs.nextToken();
  					    								  String cellTmp2 = cellStrs.nextToken();
  					    								  String cellTmp3 = cellStrs.nextToken();
  						    							  if(Long.parseLong(cellTmp1) < 0 ||Long.parseLong(cellTmp1) > 24
  						    									  || Long.parseLong(cellTmp2) < 0 ||Long.parseLong(cellTmp2) > 60
  						    									  || Long.parseLong(cellTmp3) < 0 ||Long.parseLong(cellTmp3) > 60){
  						    								  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
  						    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
  						    		    		    		  mark = -3;
  						    		    		    		  parseExcelError = true;
  							  		    		    		  flagBreak = true;
  						    		    		    		  break;
  						    							  }
  					    							  }catch(Exception e){
  					    								  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
  					    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
  					    		    		    		  mark = -3;
  					    		    		    		  parseExcelError = true;
  						  		    		    		  flagBreak = true;
  					    		    		    		  break;
  					    							  }
  					    						  }
  					    					  }
  					    					  if(flagBreak)
  					    						  break;
  		    		    				  }
  		    		    				  
  		    		    			  }
  		    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
  		    		    				  pstmt.setNull(aa, java.sql.Types.VARCHAR);
			    		    			  else
			    		    				  pstmt.setString(aa,cellContentTmp);
  		    		    		  }
  		    		    	  }catch(Exception e){
  		    		    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"数据类型错误，无法解析", columnStrs);
  		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 数据类型错误，无法解析！");
  		    		    		  mark = -3;
  		    		    		  parseExcelError = true;
  		    		    		  break;
  		    		    	  }
  	   		    	  }
  			    	  }

  			    	  if(!parseExcelError){
  			    		try{
  			    		  pstmt.execute();	
						  	} catch (SQLException e) {
							  mark = -3;	
							  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " SQL错误:"+e.getMessage());
		System.out.println("columnStrs=["+printStringArray(columnStrs)+"]");							
							  appendErrorMessage(sheet.getRow(m),errorMsgWb, sheet.getName(), "SQL错误:"+e.getMessage(), columnStrs);
							  e.printStackTrace();
							  continue;
						  	}
  			    	  }
  	   	    	//	}
  	   		   	    	  	
  				  
  			  }catch(Exception ew){
	    		  mark = -3;
	    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),"信息错误，请检查！", columnStrs);
	    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " 错误:"+ew.getMessage());
	    		  continue;
	    	}	
  			  
  		  }
    	      pstmt.close();
		     if(mark != -3)
		     mark  =1;
   }else if(sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS_NS")){
  	 System.out.println("import to syslogns----------");
  	 //**Using meta data*//*
  	
   	Statement descPstmt = con.createStatement();
		ResultSet rs = descPstmt.executeQuery("select * from SYSLOG_EVENTS_PROCESS_NS");
		ResultSetMetaData rsmeta = rs.getMetaData();
		
		for(int jj=1; jj<=rsmeta.getColumnCount(); jj++){
			ExcelTableMapping et = new ExcelTableMapping();
			String name = rsmeta.getColumnName(jj);
			String type = rsmeta.getColumnTypeName(jj);
			String isNull = rsmeta.isNullable(jj)==0?"N":"Y";
			  et.setName(name);
			  et.setType(type);

//			  et.setNull(isNull.equalsIgnoreCase("Y")?true:false);
			  if(name.equalsIgnoreCase("mark") || name.equalsIgnoreCase("BTIMELIST")
					  || name.equalsIgnoreCase("ETIMELIST")
					  || name.equalsIgnoreCase("FILTERFLAG1")
					  || name.equalsIgnoreCase("FILTERFLAG2")
					  || name.equalsIgnoreCase("SEVERITY1")
					  || name.equalsIgnoreCase("SEVERITY2")
					  || name.equalsIgnoreCase("NOTCAREFLAG")
					  || name.equalsIgnoreCase("TYPE")
					  || name.equalsIgnoreCase("EVENTTYPE")
					  || name.equalsIgnoreCase("SUBEVENTTYPE")
					  || name.equalsIgnoreCase("ALERTGROUP")
					  || name.equalsIgnoreCase("ATTENTIONFLAG")
					  || name.equalsIgnoreCase("EVENTS")
					  || name.equalsIgnoreCase("MANUFACTURE"))
				  et.setNull(false);
			  else
				  et.setNull(true);
			  
			  //find index in columnStrs
			  et.setIndex(-1);
			  for(int ii=0; ii<columnStrs.length; ii++){
				  if(columnStrs[ii].equalsIgnoreCase(name)){
					  et.setIndex(ii);
					  break;
				  }
			  }
			  if(et.getIndex() == -1){	//no corresponding column in excel. excel file error
				  return -2;
			  }

			  mapping.put(et.getName().toUpperCase(), et);
		  }
		  rs.close();
		  descPstmt.close();
		  
	     String sqls="insert into SYSLOG_EVENTS_PROCESS_NS ( MARK , VARLIST , BTIMELIST , ETIMELIST , FILTERFLAG1 , FILTERFLAG2 , SEVERITY1 , SEVERITY2 , PORT , NOTCAREFLAG , TYPE , EVENTTYPE , SUBEVENTTYPE , ALERTGROUP , ALERTKEY , SUMMARYCN , PROCESSSUGGEST , STATUS, ATTENTIONFLAG , EVENTS , MANUFACTURE , ORIGEVENT )"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	     String[] sqlsCol= new String[]{"MARK","VARLIST","BTIMELIST","ETIMELIST","FILTERFLAG1","FILTERFLAG2","SEVERITY1","SEVERITY2","PORT","NOTCAREFLAG","TYPE","EVENTTYPE","SUBEVENTTYPE","ALERTGROUP","ALERTKEY","SUMMARYCN","PROCESSSUGGEST","STATUS","ATTENTIONFLAG","EVENTS","MANUFACTURE","ORIGEVENT"};	
	     
	     PreparedStatement pstmt=con.prepareStatement(sqls);
      
   	for (int m = 1; m < sheet.getRows(); m++) {
			  try{
 				 boolean parseExcelError = false;
 	   			 boolean ifUpdate = false;
 	   		  
 	   			 String MARK = sheet.getCell(mapping.get("MARK").getIndex(),m).getContents();
 	   			 String MANUFACTURE = sheet.getCell(mapping.get("MANUFACTURE").getIndex(), m).getContents();
 	   		     SyslogEventsProcessNs syslog = syslogEventsProcessNsDao.findByPrimaryKey(MARK, MANUFACTURE);
		   	   		if(syslog != null ){
				    		  ifUpdate = true;
				    	  }
		  
			//   if(!ifUpdate){
				   for(int aa=1; aa<=sqlsCol.length; aa++){
		 	  ExcelTableMapping eTmp = mapping.get(sqlsCol[aa-1].toUpperCase());
		 	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), m).getContents();
		 	 
		 	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
		 		  mark  = -3;
		 		  parseExcelError = true;
		 		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 字段不能为空！");
		 		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName() ,eTmp.getName() +" 字段不能为空！", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
		 		  break;
		 	  }else{
			    	  try{
			    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
			    			  if(eTmp.getName().equalsIgnoreCase("EVENTTYPE") // 1-8
			    					  ||eTmp.getName().equalsIgnoreCase("Severity1") //1-7 or 100
			    					  ||eTmp.getName().equalsIgnoreCase("Severity2") //1-7 or 100
			    					  ||eTmp.getName().equalsIgnoreCase("SubEventType")// 1-10
			    					  ||eTmp.getName().equalsIgnoreCase("FilterFlag1") //1-2
			    					  ||eTmp.getName().equalsIgnoreCase("FilterFlag2")
			    					  ||eTmp.getName().equalsIgnoreCase("ATTENTIONFLAG")
			    					  ||eTmp.getName().equalsIgnoreCase("NOTCAREFLAG")
			    					  ||eTmp.getName().equalsIgnoreCase("TYPE")){ //1-2
			    				  if(cellContentTmp != null && cellContentTmp != ""){
			    					  int min=0;
			    					  int max=0;
			    					  if(eTmp.getName().equalsIgnoreCase("FilterFlag1")|| eTmp.getName().equalsIgnoreCase("FilterFlag2")
			    							  ||eTmp.getName().equalsIgnoreCase("NOTCAREFLAG") ||eTmp.getName().equalsIgnoreCase("ATTENTIONFLAG")){
			    						  min=0;
			    						  max=1;
			    					  }else if(eTmp.getName().equalsIgnoreCase("EVENTTYPE")){
			    						  min=1;
			    						  max=8;
			    					  }else if(eTmp.getName().equalsIgnoreCase("Severity1") ||eTmp.getName().equalsIgnoreCase("Severity2")){
			    						  min=1;
			    						  max=7;
			    					  }else if(eTmp.getName().equalsIgnoreCase("SubEventType")){
			    						  min=1;
			    						  max=10;
			    					  }else if(eTmp.getName().equalsIgnoreCase("TYPE")){
			    						  min=0;
			    						  max=2;
			    					  }
			    		//			  System.out.println("cellContentTmp=" + cellContentTmp + "\tColumnName=" + eTmp.getName() + "\tmin=" + min + "\tmax=" + max);
			    					  if((eTmp.getName().equalsIgnoreCase("Severity1") ||eTmp.getName().equalsIgnoreCase("Severity2"))){
			  							  if(Long.parseLong(cellContentTmp) < min || (Long.parseLong(cellContentTmp) > max && Long.parseLong(cellContentTmp) != 100)) {
			  								  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"取值范围为100/"+min+"-" + max, columnStrs);
			  		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"取值范围为100/"+min+"-" + max);
			  		    		    		  mark = -3;
			  		    		    		  parseExcelError = true;
			  		    		    		  break;
			  							  }
			    					  }
			    					  else{
			    						  if(Long.parseLong(cellContentTmp) < min || Long.parseLong(cellContentTmp) > max) {
			    							  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"取值范围为"+min+"-" + max, columnStrs);
		  		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"取值范围为"+min+"-" + max);
		  		    		    		  mark = -3;
		  		    		    		  parseExcelError = true;
		  		    		    		  break;
			    						  }
			    					  }
			    					  pstmt.setLong(aa,Long.parseLong(cellContentTmp));
			    					  continue;
				    			  }else{
									pstmt.setNull(aa, java.sql.Types.NUMERIC);
									continue;
				    			  }    		    		    				  
			    			  }
			    			  
			    			  if(cellContentTmp != null && cellContentTmp != ""){
			    				  pstmt.setLong(aa,Long.parseLong(cellContentTmp));
			    			  }else{
								pstmt.setNull(aa, java.sql.Types.NUMERIC);
			    			  }
			    		  }else if(eTmp.getType().equalsIgnoreCase("varchar2")){
			    			  if(eTmp.getName().equalsIgnoreCase("BTIMELIST") || eTmp.getName().equalsIgnoreCase("ETIMELIST")){
			    				  StringTokenizer timeStrs = new StringTokenizer(cellContentTmp, "|");
			    				  if(timeStrs == null || timeStrs.countTokens() != 7){
			    					  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
			    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
			    		    		  mark = -3;
			    		    		  parseExcelError = true;
			    		    		  break;
			    				  }else{
			    					  boolean flagBreak = false;
			    					  for(int cel=0; cel<7; cel++){
			    						  String temp = timeStrs.nextToken();
			    						  StringTokenizer cellStrs = new StringTokenizer(temp, ":");
		  		    				  if(cellStrs == null || cellStrs.countTokens() != 3){
		  		    					  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
		  		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
		  		    		    		  mark = -3;
		  		    		    		  parseExcelError = true;
		  		    		    		  flagBreak = true;
		  		    		    		  break;
			    						  }else{
			    							  try{
			    								  String cellTmp1 = cellStrs.nextToken();
			    								  String cellTmp2 = cellStrs.nextToken();
			    								  String cellTmp3 = cellStrs.nextToken();
				    							  if(Long.parseLong(cellTmp1) < 0 ||Long.parseLong(cellTmp1) > 24
				    									  || Long.parseLong(cellTmp2) < 0 ||Long.parseLong(cellTmp2) > 60
				    									  || Long.parseLong(cellTmp3) < 0 ||Long.parseLong(cellTmp3) > 60){
				    								  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
				    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
				    		    		    		  mark = -3;
				    		    		    		  parseExcelError = true;
					  		    		    		  flagBreak = true;
				    		    		    		  break;
				    							  }
			    							  }catch(Exception e){
			    								  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
			    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
			    		    		    		  mark = -3;
			    		    		    		  parseExcelError = true;
				  		    		    		  flagBreak = true;
			    		    		    		  break;
			    							  }
			    						  }
			    					  }
			    					  if(flagBreak)
			    						  break;
			    				  }
			    				  
			    			  }
			    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
			    				  pstmt.setNull(aa, java.sql.Types.VARCHAR);
			    			  else
			    				  pstmt.setString(aa,cellContentTmp);
			    		  }
			    	  }catch(Exception e){
			    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"数据类型错误，无法解析", columnStrs);
			    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 数据类型错误，无法解析！");
			    		  mark = -3;
			    		  parseExcelError = true;
			    		  break;
			    	  }
		 	  }
			  }
		
			  if(!parseExcelError){
				try{
				  pstmt.execute();	
			  	} catch (SQLException e) {
				  mark = -3;	
				  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " SQL错误:"+e.getMessage());
				  appendErrorMessage(sheet.getRow(m),errorMsgWb, sheet.getName(), "SQL错误:"+e.getMessage(), columnStrs);
				  e.printStackTrace();
				  continue;
			  	}
			  }
	      
         // 通过输入流创建Workbook(术语：工作薄)对象     	
       /*  InputStream inStr = new FileInputStream(filePath);
         
         Workbook workBook = Workbook.getWorkbook(inStr);
         // 这里有两种方法获取Sheet(术语：工作表)对象,一种为通过工作表的名字，另一种为通过工作表的下标，从0 开始
         Sheet[] sheetlist = workBook.getSheets(); 
         
         Sheet sheet = null;
         String[] columnStrs = null;
         Map<String, ExcelTableMapping> mapping = new HashMap<String, ExcelTableMapping>();
         Connection con = datasource.getConnection();
         if(sheetlist != null){*/
        	 /*
         }
        	 for(int i=0; i<sheetlist.length; i++){//for each row in excel
        		 sheet = sheetlist[i];
	    		   columnStrs = null;
	    		  if(sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS") 
	    				  || sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS_NS")){
	    			  hasdatatoimport = true;
	    			  columnStrs = new String[sheet.getColumns()];
	    		      for(int c=0; c<sheet.getColumns(); c++){
	    		    	  columnStrs[c] = sheet.getCell(c, 0).getContents();
	    		      }	
	    		  }else{
	    			  continue;
	    		  }
              if(sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS")){
            	//backup data
  	   			
 	   			 String backupresult = backup(time,sheet.getName());
 	   			
 	   			 if(backupresult.equalsIgnoreCase("fail")){
 	   				 Message = "excel.backup.error";
 	   				 mark = -6;
 	   				 break;
 	   			 }
 	   			  //delete
 	   			 String deleteresult = deletedatabeforeload(sheet.getName());
 	   			 if(deleteresult.equalsIgnoreCase("fail")){
 	   				 Message = "excel.restore.deleteerror";
 	   				 mark=-7;
 	   				 break;
 	   			 }
            	  
    	 System.out.println("import to syslog----------");
    	*//**Using meta data*//*
			
    	Statement descPstmt = con.createStatement();
		ResultSet rs = descPstmt.executeQuery("select * from SYSLOG_EVENTS_PROCESS");
		ResultSetMetaData rsmeta = rs.getMetaData();
		
		for(int jj=1; jj<=rsmeta.getColumnCount(); jj++){
			ExcelTableMapping et = new ExcelTableMapping();
			String name = rsmeta.getColumnName(jj);
			String type = rsmeta.getColumnTypeName(jj);
			String isNull = rsmeta.isNullable(jj)==0?"N":"Y";
			  et.setName(name);
			  et.setType(type);
//			  et.setNull(isNull.equalsIgnoreCase("Y")?true:false);
			  if(name.equalsIgnoreCase("mark") || name.equalsIgnoreCase("BTIMELIST")
					  || name.equalsIgnoreCase("ETIMELIST")
					  || name.equalsIgnoreCase("FILTERFLAG1")
					  || name.equalsIgnoreCase("FILTERFLAG2")
					  || name.equalsIgnoreCase("SEVERITY1")
					  || name.equalsIgnoreCase("SEVERITY2")
					  || name.equalsIgnoreCase("NOTCAREFLAG")
					  || name.equalsIgnoreCase("TYPE")
					  || name.equalsIgnoreCase("EVENTTYPE")
					  || name.equalsIgnoreCase("SUBEVENTTYPE")
					  || name.equalsIgnoreCase("ALERTGROUP")
					  || name.equalsIgnoreCase("ATTENTIONFLAG")
					  || name.equalsIgnoreCase("EVENTS")
					  || name.equalsIgnoreCase("MANUFACTURE"))
				  et.setNull(false);
			  else
				  et.setNull(true);
			  
			  //find index in columnStrs
			  et.setIndex(-1);
			  for(int ii=0; ii<columnStrs.length; ii++){
				  if(columnStrs[ii].equalsIgnoreCase(name)){
					  et.setIndex(ii);
					  break;
				  }
			  }
			  if(et.getIndex() == -1){	//no corresponding column in excel. excel file error
				  return -2;
			  }

			  mapping.put(et.getName().toUpperCase(), et);
		  }
		  rs.close();
		  descPstmt.close();
		
   	     String sqls="insert into SYSLOG_EVENTS_PROCESS ( MARK , VARLIST , BTIMELIST , ETIMELIST , FILTERFLAG1 , FILTERFLAG2 , SEVERITY1 , SEVERITY2 , PORT , NOTCAREFLAG , TYPE , EVENTTYPE , SUBEVENTTYPE , ALERTGROUP , ALERTKEY , SUMMARYCN , PROCESSSUGGEST , STATUS, ATTENTIONFLAG , EVENTS , MANUFACTURE , ORIGEVENT )"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   	     String[] sqlsCol= new String[]{"MARK","VARLIST","BTIMELIST","ETIMELIST","FILTERFLAG1","FILTERFLAG2","SEVERITY1","SEVERITY2","PORT","NOTCAREFLAG","TYPE","EVENTTYPE","SUBEVENTTYPE","ALERTGROUP","ALERTKEY","SUMMARYCN","PROCESSSUGGEST","STATUS","ATTENTIONFLAG","EVENTS","MANUFACTURE","ORIGEVENT"};	
   	     
   	     PreparedStatement pstmt=con.prepareStatement(sqls);
         
      	for (int m = 1; m < sheet.getRows(); m++) {
 			  try{
    				 boolean parseExcelError = false;
    	   			 boolean ifUpdate = false;
    	   		  
    	   		//	 String MARK = sheet.getCell(mapping.get("MARK").getIndex(),m).getContents();
    	   		//	 String MANUFACTURE = sheet.getCell(mapping.get("MANUFACTURE").getIndex(), m).getContents();
    	   		     SyslogEventsProcess syslog = syslogEventsProcessDao.findByPrimaryKey(MARK, MANUFACTURE);
    	   		     if(syslog != null ){
    	   				    		  ifUpdate = true;
    	   				    	  }
    	   		     
    	   		   	//   if(!ifUpdate){
    	   		   		   for(int aa=1; aa<=sqlsCol.length; aa++){
    	   		    	  ExcelTableMapping eTmp = mapping.get(sqlsCol[aa-1].toUpperCase());
    	   		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), m).getContents();
    	   		    	 
    	   		    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
    	   		    		  mark  = -3;
    	   		    		  parseExcelError = true;
    	   		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 字段不能为空！");
    	   		    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName() ,eTmp.getName() +" 字段不能为空！", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
    	   		    		  break;
    	   		    	  }else{
    		    		    	  try{
    		    		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
    		    		    			  if(eTmp.getName().equalsIgnoreCase("EVENTTYPE") // 1-8
    		    		    					  ||eTmp.getName().equalsIgnoreCase("Severity1") //1-7 or 100
    		    		    					  ||eTmp.getName().equalsIgnoreCase("Severity2") //1-7 or 100
    		    		    					  ||eTmp.getName().equalsIgnoreCase("SubEventType")// 1-10
    		    		    					  ||eTmp.getName().equalsIgnoreCase("FilterFlag1") //1-2
    		    		    					  ||eTmp.getName().equalsIgnoreCase("FilterFlag2")
    		    		    					  ||eTmp.getName().equalsIgnoreCase("ATTENTIONFLAG")
    		    		    					  ||eTmp.getName().equalsIgnoreCase("NOTCAREFLAG")
    		    		    					  ||eTmp.getName().equalsIgnoreCase("TYPE")){ //1-2
    		    		    				  if(cellContentTmp != null && cellContentTmp != ""){
    		    		    					  int min=0;
    		    		    					  int max=0;
    		    		    					  if(eTmp.getName().equalsIgnoreCase("FilterFlag1")|| eTmp.getName().equalsIgnoreCase("FilterFlag2")
    		    		    							  ||eTmp.getName().equalsIgnoreCase("NOTCAREFLAG") ||eTmp.getName().equalsIgnoreCase("ATTENTIONFLAG")){
    		    		    						  min=0;
    		    		    						  max=1;
    		    		    					  }else if(eTmp.getName().equalsIgnoreCase("EVENTTYPE")){
    		    		    						  min=1;
    		    		    						  max=8;
    		    		    					  }else if(eTmp.getName().equalsIgnoreCase("Severity1") ||eTmp.getName().equalsIgnoreCase("Severity2")){
    		    		    						  min=1;
    		    		    						  max=7;
    		    		    					  }else if(eTmp.getName().equalsIgnoreCase("SubEventType")){
    		    		    						  min=1;
    		    		    						  max=10;
    		    		    					  }else if(eTmp.getName().equalsIgnoreCase("TYPE")){
    		    		    						  min=0;
    		    		    						  max=2;
    		    		    					  }
    		    		    					  System.out.println("cellContentTmp=" + cellContentTmp + "\tColumnName=" + eTmp.getName() + "\tmin=" + min + "\tmax=" + max);
    		    		    					  if((eTmp.getName().equalsIgnoreCase("Severity1") ||eTmp.getName().equalsIgnoreCase("Severity2"))){
		    		    							  if(Long.parseLong(cellContentTmp) < min || (Long.parseLong(cellContentTmp) > max && Long.parseLong(cellContentTmp) != 100)) {
		    		    								  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"取值范围为100/"+min+"-" + max, columnStrs);
		    		    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"取值范围为100/"+min+"-" + max);
		    		    		    		    		  mark = -3;
		    		    		    		    		  parseExcelError = true;
		    		    		    		    		  break;
		    		    							  }
		    		    						  }
    		    		    					  else{
    		    		    						  if(Long.parseLong(cellContentTmp) < min || Long.parseLong(cellContentTmp) > max) {
    		    		    							  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"取值范围为"+min+"-" + max, columnStrs);
		    		    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"取值范围为"+min+"-" + max);
		    		    		    		    		  mark = -3;
		    		    		    		    		  parseExcelError = true;
		    		    		    		    		  break;
    		    		    						  }
    		    		    					  }
    		    		    					  pstmt.setLong(aa,Long.parseLong(cellContentTmp));
    		    		    					  continue;
        		    		    			  }else{
    											pstmt.setNull(aa, java.sql.Types.NUMERIC);
    											continue;
        		    		    			  }    		    		    				  
    		    		    			  }
    		    		    			  
    		    		    			  if(cellContentTmp != null && cellContentTmp != ""){
    		    		    				  pstmt.setLong(aa,Long.parseLong(cellContentTmp));
    		    		    			  }else{
											pstmt.setNull(aa, java.sql.Types.NUMERIC);
    		    		    			  }
    		    		    		  }else if(eTmp.getType().equalsIgnoreCase("varchar2")){
    		    		    			  if(eTmp.getName().equalsIgnoreCase("BTIMELIST") || eTmp.getName().equalsIgnoreCase("ETIMELIST")){
    		    		    				  StringTokenizer timeStrs = new StringTokenizer(cellContentTmp, "|");
    		    		    				  if(timeStrs == null || timeStrs.countTokens() != 7){
    		    		    					  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM", columnStrs);
    		    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM");
    		    		    		    		  mark = -3;
    		    		    		    		  parseExcelError = true;
    		    		    		    		  break;
    		    		    				  }else{

    					    					  boolean flagBreak = false;
    					    					  for(int cel=0; cel<7; cel++){
    					    						  String temp = timeStrs.nextToken();
    					    						  StringTokenizer cellStrs = new StringTokenizer(temp, ":");
    				  		    				  if(cellStrs == null || cellStrs.countTokens() != 3){
    				  		    					  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
    				  		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
    				  		    		    		  mark = -3;
    				  		    		    		  parseExcelError = true;
    				  		    		    		  flagBreak = true;
    				  		    		    		  break;
    					    						  }else{
    					    							  try{
    					    								  String cellTmp1 = cellStrs.nextToken();
    					    								  String cellTmp2 = cellStrs.nextToken();
    					    								  String cellTmp3 = cellStrs.nextToken();
    						    							  if(Long.parseLong(cellTmp1) < 0 ||Long.parseLong(cellTmp1) > 24
    						    									  || Long.parseLong(cellTmp2) < 0 ||Long.parseLong(cellTmp2) > 60
    						    									  || Long.parseLong(cellTmp3) < 0 ||Long.parseLong(cellTmp3) > 60){
    						    								  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
    						    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
    						    		    		    		  mark = -3;
    						    		    		    		  parseExcelError = true;
    							  		    		    		  flagBreak = true;
    						    		    		    		  break;
    						    							  }
    					    							  }catch(Exception e){
    					    								  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
    					    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
    					    		    		    		  mark = -3;
    					    		    		    		  parseExcelError = true;
    						  		    		    		  flagBreak = true;
    					    		    		    		  break;
    					    							  }
    					    						  }
    					    					  }
    					    					  if(flagBreak)
    					    						  break;
    		    		    				  }
    		    		    				  
    		    		    			  }
    		    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
    		    		    				  pstmt.setNull(aa, java.sql.Types.VARCHAR);
			    		    			  else
			    		    				  pstmt.setString(aa,cellContentTmp);
    		    		    		  }
    		    		    	  }catch(Exception e){
    		    		    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"数据类型错误，无法解析", columnStrs);
    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 数据类型错误，无法解析！");
    		    		    		  mark = -3;
    		    		    		  parseExcelError = true;
    		    		    		  break;
    		    		    	  }
    	   		    	  }
    			    	  }

    			    	  if(!parseExcelError){
    			    		try{
    			    		  pstmt.execute();	
						  	} catch (SQLException e) {
							  mark = -3;	
							  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " SQL错误:"+e.getMessage());
							  appendErrorMessage(sheet.getRow(m),errorMsgWb, sheet.getName(), "SQL错误:"+e.getMessage(), columnStrs);
							  e.printStackTrace();
							  continue;
						  	}
    			    	  }
    	   	    	//	}
    	   		   	    	  	
    				  
    			  }catch(Exception ew){
 	    		  mark = -3;
 	    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),"信息错误，请检查！", columnStrs);
	    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " 错误:"+ew.getMessage());
 	    		  continue;
 	    	}	
    			  
    		  }
      	      pstmt.close();
		     if(mark != -3)
		     mark  =1;
     }else if(sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS_NS")){
    	 System.out.println("import to syslogns----------");
    	//backup data
  			
			 String backupresult = backup(time,sheet.getName());
			
			 if(backupresult.equalsIgnoreCase("fail")){
				 Message = "excel.backup.error";
				 mark = -6;
				 break;
			 }
			  //delete
			 String deleteresult = deletedatabeforeload(sheet.getName());
			 if(deleteresult.equalsIgnoreCase("fail")){
				 Message = "excel.restore.deleteerror";
				 mark=-7;
				 break;
			 }
    	 *//**Using meta data*//*
    	
     	Statement descPstmt = con.createStatement();
 		ResultSet rs = descPstmt.executeQuery("select * from SYSLOG_EVENTS_PROCESS_NS");
 		ResultSetMetaData rsmeta = rs.getMetaData();
 		
 		for(int jj=1; jj<=rsmeta.getColumnCount(); jj++){
 			ExcelTableMapping et = new ExcelTableMapping();
 			String name = rsmeta.getColumnName(jj);
 			String type = rsmeta.getColumnTypeName(jj);
 			String isNull = rsmeta.isNullable(jj)==0?"N":"Y";
			  et.setName(name);
			  et.setType(type);

//			  et.setNull(isNull.equalsIgnoreCase("Y")?true:false);
			  if(name.equalsIgnoreCase("mark") || name.equalsIgnoreCase("BTIMELIST")
					  || name.equalsIgnoreCase("ETIMELIST")
					  || name.equalsIgnoreCase("FILTERFLAG1")
					  || name.equalsIgnoreCase("FILTERFLAG2")
					  || name.equalsIgnoreCase("SEVERITY1")
					  || name.equalsIgnoreCase("SEVERITY2")
					  || name.equalsIgnoreCase("NOTCAREFLAG")
					  || name.equalsIgnoreCase("TYPE")
					  || name.equalsIgnoreCase("EVENTTYPE")
					  || name.equalsIgnoreCase("SUBEVENTTYPE")
					  || name.equalsIgnoreCase("ALERTGROUP")
					  || name.equalsIgnoreCase("ATTENTIONFLAG")
					  || name.equalsIgnoreCase("EVENTS")
					  || name.equalsIgnoreCase("MANUFACTURE"))
				  et.setNull(false);
			  else
				  et.setNull(true);
			  
			  //find index in columnStrs
			  et.setIndex(-1);
			  for(int ii=0; ii<columnStrs.length; ii++){
				  if(columnStrs[ii].equalsIgnoreCase(name)){
					  et.setIndex(ii);
					  break;
				  }
			  }
			  if(et.getIndex() == -1){	//no corresponding column in excel. excel file error
				  return -2;
			  }

			  mapping.put(et.getName().toUpperCase(), et);
		  }
		  rs.close();
		  descPstmt.close();
		  
  	     String sqls="insert into SYSLOG_EVENTS_PROCESS_NS ( MARK , VARLIST , BTIMELIST , ETIMELIST , FILTERFLAG1 , FILTERFLAG2 , SEVERITY1 , SEVERITY2 , PORT , NOTCAREFLAG , TYPE , EVENTTYPE , SUBEVENTTYPE , ALERTGROUP , ALERTKEY , SUMMARYCN , PROCESSSUGGEST , STATUS, ATTENTIONFLAG , EVENTS , MANUFACTURE , ORIGEVENT )"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  	     String[] sqlsCol= new String[]{"MARK","VARLIST","BTIMELIST","ETIMELIST","FILTERFLAG1","FILTERFLAG2","SEVERITY1","SEVERITY2","PORT","NOTCAREFLAG","TYPE","EVENTTYPE","SUBEVENTTYPE","ALERTGROUP","ALERTKEY","SUMMARYCN","PROCESSSUGGEST","STATUS","ATTENTIONFLAG","EVENTS","MANUFACTURE","ORIGEVENT"};	
  	     
  	     PreparedStatement pstmt=con.prepareStatement(sqls);
        
     	for (int m = 1; m < sheet.getRows(); m++) {
			  try{
   				 boolean parseExcelError = false;
   	   			 boolean ifUpdate = false;
   	   		  
   	   			 String MARK = sheet.getCell(mapping.get("MARK").getIndex(),m).getContents();
   	   			 String MANUFACTURE = sheet.getCell(mapping.get("MANUFACTURE").getIndex(), m).getContents();
   	   		     SyslogEventsProcessNs syslog = syslogEventsProcessNsDao.findByPrimaryKey(MARK, MANUFACTURE);
		   	   		if(syslog != null ){
				    		  ifUpdate = true;
				    	  }
		  
			//   if(!ifUpdate){
				   for(int aa=1; aa<=sqlsCol.length; aa++){
		 	  ExcelTableMapping eTmp = mapping.get(sqlsCol[aa-1].toUpperCase());
		 	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), m).getContents();
		 	 
		 	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
		 		  mark  = -3;
		 		  parseExcelError = true;
		 		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 字段不能为空！");
		 		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName() ,eTmp.getName() +" 字段不能为空！", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
		 		  break;
		 	  }else{
			    	  try{
			    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
			    			  if(eTmp.getName().equalsIgnoreCase("EVENTTYPE") // 1-8
			    					  ||eTmp.getName().equalsIgnoreCase("Severity1") //1-7 or 100
			    					  ||eTmp.getName().equalsIgnoreCase("Severity2") //1-7 or 100
			    					  ||eTmp.getName().equalsIgnoreCase("SubEventType")// 1-10
			    					  ||eTmp.getName().equalsIgnoreCase("FilterFlag1") //1-2
			    					  ||eTmp.getName().equalsIgnoreCase("FilterFlag2")
			    					  ||eTmp.getName().equalsIgnoreCase("ATTENTIONFLAG")
			    					  ||eTmp.getName().equalsIgnoreCase("NOTCAREFLAG")
			    					  ||eTmp.getName().equalsIgnoreCase("TYPE")){ //1-2
			    				  if(cellContentTmp != null && cellContentTmp != ""){
			    					  int min=0;
			    					  int max=0;
			    					  if(eTmp.getName().equalsIgnoreCase("FilterFlag1")|| eTmp.getName().equalsIgnoreCase("FilterFlag2")
			    							  ||eTmp.getName().equalsIgnoreCase("NOTCAREFLAG") ||eTmp.getName().equalsIgnoreCase("ATTENTIONFLAG")){
			    						  min=0;
			    						  max=1;
			    					  }else if(eTmp.getName().equalsIgnoreCase("EVENTTYPE")){
			    						  min=1;
			    						  max=8;
			    					  }else if(eTmp.getName().equalsIgnoreCase("Severity1") ||eTmp.getName().equalsIgnoreCase("Severity2")){
			    						  min=1;
			    						  max=7;
			    					  }else if(eTmp.getName().equalsIgnoreCase("SubEventType")){
			    						  min=1;
			    						  max=10;
			    					  }else if(eTmp.getName().equalsIgnoreCase("TYPE")){
			    						  min=0;
			    						  max=2;
			    					  }
			    					  System.out.println("cellContentTmp=" + cellContentTmp + "\tColumnName=" + eTmp.getName() + "\tmin=" + min + "\tmax=" + max);
			    					  if((eTmp.getName().equalsIgnoreCase("Severity1") ||eTmp.getName().equalsIgnoreCase("Severity2"))){
			  							  if(Long.parseLong(cellContentTmp) < min || (Long.parseLong(cellContentTmp) > max && Long.parseLong(cellContentTmp) != 100)) {
			  								  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"取值范围为100/"+min+"-" + max, columnStrs);
			  		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"取值范围为100/"+min+"-" + max);
			  		    		    		  mark = -3;
			  		    		    		  parseExcelError = true;
			  		    		    		  break;
			  							  }
			    					  }
			    					  else{
			    						  if(Long.parseLong(cellContentTmp) < min || Long.parseLong(cellContentTmp) > max) {
			    							  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"取值范围为"+min+"-" + max, columnStrs);
		  		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"取值范围为"+min+"-" + max);
		  		    		    		  mark = -3;
		  		    		    		  parseExcelError = true;
		  		    		    		  break;
			    						  }
			    					  }
			    					  pstmt.setLong(aa,Long.parseLong(cellContentTmp));
			    					  continue;
				    			  }else{
									pstmt.setNull(aa, java.sql.Types.NUMERIC);
									continue;
				    			  }    		    		    				  
			    			  }
			    			  
			    			  if(cellContentTmp != null && cellContentTmp != ""){
			    				  pstmt.setLong(aa,Long.parseLong(cellContentTmp));
			    			  }else{
								pstmt.setNull(aa, java.sql.Types.NUMERIC);
			    			  }
			    		  }else if(eTmp.getType().equalsIgnoreCase("varchar2")){
			    			  if(eTmp.getName().equalsIgnoreCase("BTIMELIST") || eTmp.getName().equalsIgnoreCase("ETIMELIST")){
			    				  StringTokenizer timeStrs = new StringTokenizer(cellContentTmp, "|");
			    				  if(timeStrs == null || timeStrs.countTokens() != 7){
			    					  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
			    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
			    		    		  mark = -3;
			    		    		  parseExcelError = true;
			    		    		  break;
			    				  }else{
			    					  boolean flagBreak = false;
			    					  for(int cel=0; cel<7; cel++){
			    						  String temp = timeStrs.nextToken();
			    						  StringTokenizer cellStrs = new StringTokenizer(temp, ":");
		  		    				  if(cellStrs == null || cellStrs.countTokens() != 3){
		  		    					  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
		  		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
		  		    		    		  mark = -3;
		  		    		    		  parseExcelError = true;
		  		    		    		  flagBreak = true;
		  		    		    		  break;
			    						  }else{
			    							  try{
			    								  String cellTmp1 = cellStrs.nextToken();
			    								  String cellTmp2 = cellStrs.nextToken();
			    								  String cellTmp3 = cellStrs.nextToken();
				    							  if(Long.parseLong(cellTmp1) < 0 ||Long.parseLong(cellTmp1) > 24
				    									  || Long.parseLong(cellTmp2) < 0 ||Long.parseLong(cellTmp2) > 60
				    									  || Long.parseLong(cellTmp3) < 0 ||Long.parseLong(cellTmp3) > 60){
				    								  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
				    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
				    		    		    		  mark = -3;
				    		    		    		  parseExcelError = true;
					  		    		    		  flagBreak = true;
				    		    		    		  break;
				    							  }
			    							  }catch(Exception e){
			    								  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
			    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
			    		    		    		  mark = -3;
			    		    		    		  parseExcelError = true;
				  		    		    		  flagBreak = true;
			    		    		    		  break;
			    							  }
			    						  }
			    					  }
			    					  if(flagBreak)
			    						  break;
			    				  }
			    				  
			    			  }
			    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
			    				  pstmt.setNull(aa, java.sql.Types.VARCHAR);
			    			  else
			    				  pstmt.setString(aa,cellContentTmp);
			    		  }
			    	  }catch(Exception e){
			    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"数据类型错误，无法解析", columnStrs);
			    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 数据类型错误，无法解析！");
			    		  mark = -3;
			    		  parseExcelError = true;
			    		  break;
			    	  }
		 	  }
			  }
		
			  if(!parseExcelError){
				try{
				  pstmt.execute();	
			  	} catch (SQLException e) {
				  mark = -3;	
				  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " SQL错误:"+e.getMessage());
				  appendErrorMessage(sheet.getRow(m),errorMsgWb, sheet.getName(), "SQL错误:"+e.getMessage(), columnStrs);
				  e.printStackTrace();
				  continue;
			  	}
			  }
				//}
			    	  	
		
		}catch(Exception ew){
		mark = -3;
		appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),"信息错误，请检查！", columnStrs);
		Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " 错误:"+ew.getMessage());
		continue;
		}	
		
		}
		pstmt.close();
		if(mark != -3)
		mark  =1;
    }
             }
         */
        	 /*}
        	 else
            	 return -2;
            if(con!=null)
 		    con.close();
     }catch (Exception e) {
         	if(mark!= -3) mark=-1;
         	e.printStackTrace();
  		  Log4jInit.ncsLog.error("导入失败：" +  " 错误:"+e.getMessage());
         }
     if(hasdatatoimport)
			return mark;
		else
			return -4;*/
     }catch(Exception ew){
		  mark = -3;
		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),"信息错误，请检查！", columnStrs);
		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " 错误:"+ew.getMessage());
		  continue;
	}	
		  
	  }
      pstmt.close();
     if(mark != -3)
     mark  =1;
     
   }
     }catch(Exception e){
    	 if(mark!= -3) mark=-1;
       	e.printStackTrace();
    	 Log4jInit.ncsLog.error("导入失败：" +  " 错误:"+e.getMessage());
     }
     return mark;
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









	public DataSource getDatasource() {
		return datasource;
	}









	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
		jdbcTemplate = new SimpleJdbcTemplate(datasource);
	}
	
	/**
	 * Init mapping only for table :T_module_info_init T_policy_base T_event_type_init T_policy_details
	 * @param rs
	 * @param columnStrs
	 * @param mapping
	 * @param sheet.getName()
	 * @return
	 */
	private void appendErrorMessage(Cell[] row, HSSFWorkbook errorMsgWb,String sheetName, String errorMsg, String[] colTitles){
		try {
			//set title style
			HSSFCellStyle titleStyle = errorMsgWb.createCellStyle();
			titleStyle.setFillBackgroundColor((short)13);
			if(sheetName.equalsIgnoreCase("SNMP_EVENTS_PROCESS")){
				
				HSSFSheet tfSheet = errorMsgWb.getSheet("ErrorMsg_SNMP_EVE_PRO");
				if(tfSheet == null){
				    tfSheet = errorMsgWb.createSheet("ErrorMsg_SNMP_EVE_PRO");//create a sheet
				    HSSFRow titleRow = tfSheet.createRow(0);//create title row
					createCell(titleRow, 0, "Error Message", titleStyle);
					for(int c=0; c<colTitles.length; c++){
						String temp = null;
						try{ 
							temp = colTitles[c];
						}catch(Exception e){
							System.out.println("colTitles[c]="+c+", "+colTitles[c]+" "+e.getMessage());
							break;
						}
						if(temp == null || temp.equals(""))
							continue;
						createCell(titleRow, c+1, temp, titleStyle);
					}
				}
				
				HSSFRow errorRow = tfSheet.createRow(tfSheet.getLastRowNum()+1);//create a new row
				createCell(errorRow, 0,errorMsg, titleStyle);
				for(int c=0; c<colTitles.length; c++){
					String temp = null;
					try{ 
						temp = row[c].getContents();
					}catch(Exception e){
						System.out.println("colTitles[c]="+c+", "+colTitles[c]+" "+e.getMessage());
						break;
					}
					if(temp == null || temp.equals(""))
						continue;
					createCell(errorRow, c+1,temp , titleStyle);
				}
			}else if(sheetName.equalsIgnoreCase("SYSLOG_EVENTS_PROCESS")){
				
				HSSFSheet tfSheet = errorMsgWb.getSheet("ErrorMsg_SYS_EVE_PRO");
				if(tfSheet == null){
				    tfSheet = errorMsgWb.createSheet("ErrorMsg_SYS_EVE_PRO");//create a sheet
					HSSFRow titleRow = tfSheet.createRow(0);//create title row
					createCell(titleRow, 0, "Error Message", titleStyle);
					for(int c=0; c<colTitles.length; c++){
						String temp = null;
						try {
							temp = colTitles[c];
						} catch (Exception e) {
							System.out.println("colTitles[c]="+c+", "+colTitles[c]+" "+e.getMessage());
							break;
						}
						if(temp == null || temp.equals(""))
							continue;
						createCell(titleRow, c+1, temp, titleStyle);
					}
				}
				
				HSSFRow errorRow = tfSheet.createRow(tfSheet.getLastRowNum()+1);//create a new row
				createCell(errorRow, 0,errorMsg, titleStyle);
				for(int c=0; c<colTitles.length; c++){
					String temp=null;
					try {
						temp = row[c].getContents();
					} catch (Exception e) {
						System.out.println("row[c]="+c+", "+row[c]+" "+e.getMessage());
						break;
					}
					if(temp == null || temp.equals(""))
						continue;
					createCell(errorRow, c+1,temp , titleStyle);
				}
			}else if(sheetName.equalsIgnoreCase("SYSLOG_EVENTS_PROCESS_NS")){
				
				HSSFSheet tfSheet = errorMsgWb.getSheet("ErrorMsg_SYS_EVE_PRO_NS");
				if(tfSheet == null){
				    tfSheet = errorMsgWb.createSheet("ErrorMsg_SYS_EVE_PRO_NS");//create a sheet
					HSSFRow titleRow = tfSheet.createRow(0);//create title row
					createCell(titleRow, 0, "Error Message", titleStyle);
					for(int c=0; c<colTitles.length; c++){
						String temp = null;
						try{ 
							temp = colTitles[c];
						}catch(Exception e){
							System.out.println("colTitles[c]="+c+", "+colTitles[c]+" "+e.getMessage());
							break;
						}
						if(temp == null || temp.equals(""))
							continue;
						createCell(titleRow, c+1, temp, titleStyle);
					}
				}
				
				HSSFRow errorRow = tfSheet.createRow(tfSheet.getLastRowNum()+1);//create a new row
				createCell(errorRow, 0,errorMsg, titleStyle);
				for(int c=0; c<colTitles.length; c++){
					String temp = null;
					try{ 
						temp = row[c].getContents();
					}catch(Exception e){
						System.out.println("colTitles[c]="+c+", "+colTitles[c]+" "+e.getMessage());
						break;
					}
					if(temp == null || temp.equals(""))
						continue;
					createCell(errorRow, c+1,temp , titleStyle);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}
	
	private static HSSFCell createCell(HSSFRow row, int index, String value, HSSFCellStyle style)
	{
		HSSFCell cell = row.createCell((short)index);
		cell.setCellValue(value);
		cell.setCellStyle(style);
		return cell;
	}




	public int getMarke() {
		return marke;
	}




	public void setMarke(int marke) {
		this.marke = marke;
	}




	public String getMessage() {
		return Message;
	}




	public void setMessage(String message) {
		Message = message;
	}




	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}




	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}




	




	public String[] getDbfilename() {
		return dbfilename;
	}




	public void setDbfilename(String[] dbfilename) {
		this.dbfilename = dbfilename;
	}




	public SyslogEventsProcessDao getSyslogEventsProcessDao() {
		return syslogEventsProcessDao;
	}




	public void setSyslogEventsProcessDao(
			SyslogEventsProcessDao syslogEventsProcessDao) {
		this.syslogEventsProcessDao = syslogEventsProcessDao;
	}




	public SyslogEventsProcessNsDao getSyslogEventsProcessNsDao() {
		return syslogEventsProcessNsDao;
	}




	public void setSyslogEventsProcessNsDao(
			SyslogEventsProcessNsDao syslogEventsProcessNsDao) {
		this.syslogEventsProcessNsDao = syslogEventsProcessNsDao;
	}




	




	public BkSyslogEventsProcessDao getBkSyslogEventsProcessDao() {
		return bkSyslogEventsProcessDao;
	}




	public void setBkSyslogEventsProcessDao(
			BkSyslogEventsProcessDao bkSyslogEventsProcessDao) {
		this.bkSyslogEventsProcessDao = bkSyslogEventsProcessDao;
	}




	public BkSyslogEventsProcessNsDao getBkSyslogEventsProcessNsDao() {
		return bkSyslogEventsProcessNsDao;
	}




	public void setBkSyslogEventsProcessNsDao(
			BkSyslogEventsProcessNsDao bkSyslogEventsProcessNsDao) {
		this.bkSyslogEventsProcessNsDao = bkSyslogEventsProcessNsDao;
	}




	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}




	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}
	
	private String printStringArray(String [] stringarray){
		StringBuffer sbf =  new StringBuffer();
		for (String sss: stringarray){
			sbf.append(", ").append(sss);
		}
		sbf.replace(0, 1, "");
		return sbf.toString();
	}

}
