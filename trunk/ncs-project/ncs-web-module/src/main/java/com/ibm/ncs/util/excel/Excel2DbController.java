package com.ibm.ncs.util.excel;

import java.io.BufferedReader;
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

import com.ibm.ncs.model.dao.SnmpEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.util.Log4jInit;

public class Excel2DbController implements Controller {
	
String pageView;
DataSource datasource;
	private int marke =0;
	String Message = "";
	protected SimpleJdbcTemplate jdbcTemplate;
	SnmpEventsProcessDao snmpEventsProcessDao;
	SyslogEventsProcessDao syslogEventsProcessDao;
	SyslogEventsProcessNsDao syslogEventsProcessNsDao;
	
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
		if(mode.equalsIgnoreCase("snmp")){
			setPageView("/secure/excel/upload.jsp");
		}
		if(mode.equalsIgnoreCase("syslog")){
			setPageView("/secure/excel/uploadsyslog.jsp");
		}
		
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
		 
		  marke = loadToDB(filePath,wbMsg);
		 
		fileItem.delete();//remove the uploaded file.
		try {
    		String delupload = request.getRealPath("/") +"/uploadDir";
			ExcelTODB.clearDir(delupload);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(marke<1){
			if(marke == -2){	//no corresponding columns in excel ok
				Message ="excel.import.missingColumn";
				model.put("Message", Message);
				Log4jInit.ncsLog.error("EXCEL: " + filename + " 中的数据列与数据库中不符，请检查！");
	    		return new ModelAndView(getPageView(),	"model", model);
			}else if(marke == -3){//partially imported, and need to write error msg to another excel sheet				
				Message ="excel.import.partialSuccess";
	        	model.put("Message", Message);	
	        	Log4jInit.ncsLog.error("EXCEL: " + filename + " 中部分数据导入失败，请查看错误日志/logs/importExcelError.xls");
	        	
	        	SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String timestampStr = sdf.format(new Date());
				System.out.println("timestampstr is "+timestampStr);
				
				
	        	String errorDir = "/logs/xls";
				String errorXlsFile = "/importDevInfoError_"+timestampStr+".xls";
				String errorLongName = request.getRealPath("/") +errorDir+errorXlsFile;
				String errorLinkStr = "<a href='"+request.getContextPath()+errorDir+errorXlsFile+"'>"+"错误信息："+"</a>";
				
				model.put("errorLinkStr", errorLinkStr);
				
				//-- clear the error directory
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
	    		return new ModelAndView(getPageView(),	"model", model);
			}else if(marke == -4){
				Message ="excel.import.noDataToImport";
	        	model.put("Message", Message);	
				Log4jInit.ncsLog.error("EXCEL: " + filename + "没有可以导入的数据");
	    		return new ModelAndView(getPageView(),	"model", model);
			}
			
			//Message ="excel.import.failed";
			Message ="excel.import.failed";
        	model.put("Message", Message);		
			Log4jInit.ncsLog.error("EXCEL: " + filename + " 数据导入失败，请检查导入的文件");
    		return new ModelAndView(getPageView(),	"model", model);
		}
		else{
			Message ="excel.import.success";
			 try {
						ExcelTODB.clearDir(request.getRealPath("/") +"/logs/xls");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			Log4jInit.ncsLog.info("EXCEL: " + filename + " 导入成功");
		}
		} catch (Exception e) {
			e.printStackTrace();
			Log4jInit.ncsLog.error("EXCEL: " + filename + "导入失败 message:" + e.getMessage());
		}
		}  
		
		model.put("Message", Message);
	    
		return new ModelAndView(getPageView(), "model", model);
	}							


	public  int  loadToDB(String filePath,HSSFWorkbook errorMsgWb) {
		
		   int mark=-1;
		   boolean hasdatatoimport = false;
     try {
         // 通过输入流创建Workbook(术语：工作薄)对象     	
         InputStream inStr = new FileInputStream(filePath);
         
         Workbook workBook = Workbook.getWorkbook(inStr);
         // 这里有两种方法获取Sheet(术语：工作表)对象,一种为通过工作表的名字，另一种为通过工作表的下标，从0 开始
         Sheet[] sheetlist = workBook.getSheets(); 
         
         Sheet sheet = null;
         String[] columnStrs = null;
         Map<String, ExcelTableMapping> mapping = new HashMap<String, ExcelTableMapping>();
         Connection con = datasource.getConnection();
         if(sheetlist != null){
        	 for(int i=0; i<sheetlist.length; i++){//for each row in excel
        		 sheet = sheetlist[i];
	    		   columnStrs = null;
	    		  if(sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS") 
	    				  || sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS_NS")
	    				  || sheet.getName().equalsIgnoreCase("SNMP_EVENTS_PROCESS")){
	    			  hasdatatoimport = true;
	    			  columnStrs = new String[sheet.getColumns()];
	    		      for(int c=0; c<sheet.getColumns(); c++){
	    		    	  columnStrs[c] = sheet.getCell(c, 0).getContents();
	    		      }	
	    		  }else{
	    			  continue;
	    		  }
             if(sheet.getName().equalsIgnoreCase("SNMP_EVENTS_PROCESS")){
            	 /**Using meta data*/
	   			 Statement descPstmt = con.createStatement();
	   			 ResultSet rs = descPstmt.executeQuery("select * from SNMP_EVENTS_PROCESS");
	   			 ResultSetMetaData rsmeta = rs.getMetaData();
   			  
	   			 for(int jj=1; jj<=rsmeta.getColumnCount(); jj++){
					ExcelTableMapping et = new ExcelTableMapping();
					String name = rsmeta.getColumnName(jj);
					String type = rsmeta.getColumnTypeName(jj);
					String isNull = rsmeta.isNullable(jj)==0?"N":"Y";
					et.setName(name);
					et.setType(type);
//				  	et.setNull(isNull.equalsIgnoreCase("Y")?true:false);
					et.setNull(false);// all column in SNMP_EVENTS_PROCESS is required
					//find index in columnStrs
					et.setIndex(-1);
					for(int ii=0; ii<columnStrs.length; ii++){
					  if(columnStrs[ii].equalsIgnoreCase(name)){
						  et.setIndex(ii);
						  break;
					  }
					}
					if(et.getIndex() == -1){	//no corresponding column in excel. excel file error

					  Log4jInit.ncsLog.error("导入 SNMP_EVENTS_PROCESS 失败:" + et.getName() + "在Excel中无对应列");
					  return -2;
					}

					mapping.put(et.getName().toUpperCase(), et);
			  }
			  rs.close();
			  descPstmt.close();
        	 
        	 
        	 //ok
        	  String updateSqls="Update SNMP_EVENTS_PROCESS set MANUFACTURE=?, RESULTLIST=?, WARNMESSAGE=?, Summary=? where Mark=?";
              String sqls = "insert into SNMP_EVENTS_PROCESS (MARK, MANUFACTURE, RESULTLIST, WARNMESSAGE, Summary)"+"values (?,?,?,?,?)";
              String[] sqlsCol= new String[]{"MARK","MANUFACTURE","RESULTLIST","WARNMESSAGE","Summary"};	
	 	      String[] updateSqlsCol= new String[]{"MANUFACTURE","RESULTLIST","WARNMESSAGE","Summary","MARK"};	
              
              PreparedStatement pstmt=con.prepareStatement(sqls);
   	          PreparedStatement pstmtUpdate=con.prepareStatement(updateSqls);
	   		  for (int m = 1; m < sheet.getRows(); m++) {
	   			  try{
	   				 boolean parseExcelError = false;
	   	   			 boolean ifUpdate = false;
	   	   		  
	   	   			 String MARK = sheet.getCell(mapping.get("MARK").getIndex(),m).getContents();
	   	   		     List<SnmpEventsProcess> snmplist = snmpEventsProcessDao.findWhereMarkEquals(MARK);
	   	   		     if(snmplist != null && snmplist.size()>0){
	   	   		    	 ifUpdate = true;
	   	   		     }
	   	   		     if(!ifUpdate){
	   	   		    	 for(int aa=1; aa<=sqlsCol.length; aa++){
	   	   		    	  ExcelTableMapping eTmp = mapping.get(sqlsCol[aa-1].toUpperCase());
	   	   		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), m).getContents();
	   	   		    	 
	   	   		    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
	   	   		    		  mark  = -3;
	   	   		    		  parseExcelError = true;
	   	   		    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName() ,eTmp.getName() +" 字段不能为空！", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
	   	   		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 字段不能为空！");
	   	   		    		  break;
	   	   		    	  }else{
	   		    		    	  try{
	   		    		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
	   		    		    			  if(cellContentTmp != null && cellContentTmp != "")
	   		    		    				  pstmt.setLong(aa,Long.parseLong(cellContentTmp));
	   		    		    			  else
	   		    		    				  pstmt.setNull(aa, java.sql.Types.NUMERIC);
	   		    		    		  }else if(eTmp.getType().equalsIgnoreCase("varchar2")){
	   		    		    			if(cellContentTmp == null || cellContentTmp.equals("null"))
	   		    		    				pstmt.setNull(aa, java.sql.Types.VARCHAR);
	   		    		    			else
	   		    		    				pstmt.setString(aa,cellContentTmp);
	   		    		    		  }
	   		    		    	  }catch(Exception e){
	   		    		    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"数据类型错误，无法解析", columnStrs);
	   		    		    		  mark = -3;
	   		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 数据类型错误，无法解析！");
	   		    		    		  parseExcelError = true;
	   		    		    		  break;
	   		    		    	  }
	   	   		    	  }
	   			    	  }
	
	   			    	  if(!parseExcelError){
	   			    		try {
	   			    		 pstmt.execute();	
							  } catch (SQLException e) {
								  mark = -3;						  
								  appendErrorMessage(sheet.getRow(m),errorMsgWb, sheet.getName(), "SQL错误:"+e.getMessage(), columnStrs);
								  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " SQL错误:"+e.getMessage());
								  e.printStackTrace();
							  }
	   			    	  }
	   	   	    		}else{//update
		    				  for(int aa=1; aa<=updateSqlsCol.length; aa++){
			    		    	  ExcelTableMapping eTmp = mapping.get(updateSqlsCol[aa-1].toUpperCase());
			    		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), m).getContents();
			    		    	 
			    		    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
			    		    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName() +"字段不能为空！", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
			    		    		  mark = -3;
			    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 字段不能为空！");
			    		    		  parseExcelError = true;
			    		    		  break;
			    		    	  }else{
				    		    	  try{
				    		    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
				    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
				    		    				  pstmtUpdate.setNull(aa, java.sql.Types.VARCHAR);
				    		    			  else
				    		    				  pstmtUpdate.setString(aa,cellContentTmp);
				    		    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
				    		    			  if(cellContentTmp != null && cellContentTmp != "")
				    		    				  pstmtUpdate.setLong(aa,Long.parseLong(cellContentTmp));
				    		    			  else
				    		    				  pstmtUpdate.setNull(aa, java.sql.Types.NUMERIC);
				    		    	  	  }
				    		    	  }catch(Exception ew){
				    		    		  mark = -3;
				    		    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"数据类型错误，无法解析", columnStrs);
				    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 数据类型错误，无法解析！");
				    		    		  ew.printStackTrace();
				    		    		  parseExcelError = true;
				    		    		  break;
				    		    	  }
			    		    	  }
		    		    	  }
		    				  if(!parseExcelError){
		    					  try {
		    						  pstmtUpdate.execute();
		  						  } catch (SQLException e) {
		  							  mark = -3;			
		  							  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " SQL错误:"+e.getMessage());
		  							  appendErrorMessage(sheet.getRow(m),errorMsgWb, sheet.getName(), "SQL错误:"+e.getMessage(), columnStrs);
		  							  e.printStackTrace();
		  						  }
		    					 
		    				  }
	  			  
	   	   	    		}
	   			  }catch(Exception ew){
		    		  mark = -3;
		    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),"信息错误，请检查！", columnStrs);
		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " 错误:"+ew.getMessage());
		    		  continue;
		    	}	
	   			  
	   		  }
   	    	  	
	   		  if(pstmt !=null)
	   			  pstmt.close();
	   		  if(pstmtUpdate !=null)pstmtUpdate.close();
   		     if(mark != -3)
 			  mark  =1;
     }else if(sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS")){
    	/**Using meta data*/
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
		
    	 String updateSqls = "UPDATE SYSLOG_EVENTS_PROCESS set VARLIST=?,BTIMELIST=?, ETIMELIST=?, FILTERFLAG1=?, FILTERFLAG2=?, SEVERITY1=?, SEVERITY2=?, PORT=?, NOTCAREFLAG=?, TYPE=?, EVENTTYPE=?, SUBEVENTTYPE=?, ALERTGROUP=?, ALERTKEY=?, SUMMARYCN=?, PROCESSSUGGEST=?, STATUS=?, ATTENTIONFLAG=?, EVENTS=?,ORIGEVENT=? where Mark=? and MANUFACTURE=?";
   	     String sqls="insert into SYSLOG_EVENTS_PROCESS ( MARK , VARLIST , BTIMELIST , ETIMELIST , FILTERFLAG1 , FILTERFLAG2 , SEVERITY1 , SEVERITY2 , PORT , NOTCAREFLAG , TYPE , EVENTTYPE , SUBEVENTTYPE , ALERTGROUP , ALERTKEY , SUMMARYCN , PROCESSSUGGEST , STATUS, ATTENTIONFLAG , EVENTS , MANUFACTURE , ORIGEVENT )"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   	     String[] sqlsCol= new String[]{"MARK","VARLIST","BTIMELIST","ETIMELIST","FILTERFLAG1","FILTERFLAG2","SEVERITY1","SEVERITY2","PORT","NOTCAREFLAG","TYPE","EVENTTYPE","SUBEVENTTYPE","ALERTGROUP","ALERTKEY","SUMMARYCN","PROCESSSUGGEST","STATUS","ATTENTIONFLAG","EVENTS","MANUFACTURE","ORIGEVENT"};	
	     String[] updateSqlsCol= new String[]{"VARLIST","BTIMELIST","ETIMELIST","FILTERFLAG1","FILTERFLAG2","SEVERITY1","SEVERITY2","PORT","NOTCAREFLAG","TYPE","EVENTTYPE","SUBEVENTTYPE","ALERTGROUP","ALERTKEY","SUMMARYCN","PROCESSSUGGEST","STATUS","ATTENTIONFLAG","EVENTS","ORIGEVENT","MARK","MANUFACTURE"};
   	     
   	     PreparedStatement pstmt=con.prepareStatement(sqls);
         PreparedStatement pstmtUpdate=con.prepareStatement(updateSqls);
         
      	for (int m = 1; m < sheet.getRows(); m++) {
 			  try{
    				 boolean parseExcelError = false;
    	   			 boolean ifUpdate = false;
    	   		  
    	   			 String MARK = sheet.getCell(mapping.get("MARK").getIndex(),m).getContents();
    	   			 String MANUFACTURE = sheet.getCell(mapping.get("MANUFACTURE").getIndex(), m).getContents();
    	   		     SyslogEventsProcess syslog = syslogEventsProcessDao.findByPrimaryKey(MARK, MANUFACTURE);
    	   		     if(syslog != null ){
    	   				    		  ifUpdate = true;
    	   				    	  }
    	   		     
    	   		   	   if(!ifUpdate){
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
    	   	    		}else{//update
 	    				  for(int aa=1; aa<=updateSqlsCol.length; aa++){
 		    		    	  ExcelTableMapping eTmp = mapping.get(updateSqlsCol[aa-1].toUpperCase());
 		    		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), m).getContents();
 		    		    	 
 		    		    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
 		    		    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName() +"字段不能为空！", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
 		    		    		 Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 字段不能为空！");
 		    		    		 mark = -3;
 		    		    		  parseExcelError = true;
 		    		    		 break;
 		    		    	  }else{
 			    		    	  try{
 			    		    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
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
 			    		    				 pstmtUpdate.setNull(aa, java.sql.Types.VARCHAR);
			    		    			 else
			    		    				 pstmtUpdate.setString(aa,cellContentTmp);
 			    		    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
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
   		    		    					  pstmtUpdate.setLong(aa,Long.parseLong(cellContentTmp));
   		    		    					  continue;
       		    		    			  }else{
       		    		    				pstmtUpdate.setNull(aa, java.sql.Types.NUMERIC);
       		    		    				continue;
       		    		    			  }    		    		    				  
   		    		    			  }
 			    		    			  if(cellContentTmp != null && cellContentTmp != ""){
 			    		    			  pstmtUpdate.setLong(aa,Long.parseLong(cellContentTmp));
 			    		    			  }else{
 			    		    				 pstmtUpdate.setNull(aa, java.sql.Types.NUMERIC);
 			    		    			  }
 			    		    	  	  }
 			    		    	  }catch(Exception ew){
 			    		    		  mark = -3;
 			    		    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"数据类型错误，无法解析", columnStrs);
 			    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 数据类型错误，无法解析！");
 			    		    		  parseExcelError = true;
 			    		    		  break;
 			    		    	  }
 		    		    	  }
 	    		    	  }
 	    				  if(!parseExcelError){
 	    					try{ 
 	    						pstmtUpdate.execute();
 	    					} catch (SQLException e) {
							  mark = -3;				
							  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " SQL错误:"+e.getMessage());
							  appendErrorMessage(sheet.getRow(m),errorMsgWb, sheet.getName(), "SQL错误:"+e.getMessage(), columnStrs);
							  e.printStackTrace();
							  continue;
 	    					}  
 	    				  }
   			  
    	   	    		}
    	   		   	    	  	
    				  
    			  }catch(Exception ew){
 	    		  mark = -3;
 	    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),"信息错误，请检查！", columnStrs);
	    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " 错误:"+ew.getMessage());
 	    		  continue;
 	    	}	
    			  
    		  }
      	      pstmt.close();
		      pstmtUpdate.close();
		     if(mark != -3)
		     mark  =1;
     }else if(sheet.getName().equalsIgnoreCase("SYSLOG_EVENTS_PROCESS_NS")){
    	 /**Using meta data*/
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
		  
   	     String updateSqls = "UPDATE SYSLOG_EVENTS_PROCESS_NS set VARLIST=?,BTIMELIST=?, ETIMELIST=?, FILTERFLAG1=?, FILTERFLAG2=?, SEVERITY1=?, SEVERITY2=?, PORT=?, NOTCAREFLAG=?, TYPE=?, EVENTTYPE=?, SUBEVENTTYPE=?, ALERTGROUP=?, ALERTKEY=?, SUMMARYCN=?, PROCESSSUGGEST=?, STATUS=?, ATTENTIONFLAG=?, EVENTS=?,ORIGEVENT=? where Mark=? and MANUFACTURE=?";
  	     String sqls="insert into SYSLOG_EVENTS_PROCESS_NS ( MARK , VARLIST , BTIMELIST , ETIMELIST , FILTERFLAG1 , FILTERFLAG2 , SEVERITY1 , SEVERITY2 , PORT , NOTCAREFLAG , TYPE , EVENTTYPE , SUBEVENTTYPE , ALERTGROUP , ALERTKEY , SUMMARYCN , PROCESSSUGGEST , STATUS, ATTENTIONFLAG , EVENTS , MANUFACTURE , ORIGEVENT )"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  	     String[] sqlsCol= new String[]{"MARK","VARLIST","BTIMELIST","ETIMELIST","FILTERFLAG1","FILTERFLAG2","SEVERITY1","SEVERITY2","PORT","NOTCAREFLAG","TYPE","EVENTTYPE","SUBEVENTTYPE","ALERTGROUP","ALERTKEY","SUMMARYCN","PROCESSSUGGEST","STATUS","ATTENTIONFLAG","EVENTS","MANUFACTURE","ORIGEVENT"};	
	     String[] updateSqlsCol= new String[]{"VARLIST","BTIMELIST","ETIMELIST","FILTERFLAG1","FILTERFLAG2","SEVERITY1","SEVERITY2","PORT","NOTCAREFLAG","TYPE","EVENTTYPE","SUBEVENTTYPE","ALERTGROUP","ALERTKEY","SUMMARYCN","PROCESSSUGGEST","STATUS","ATTENTIONFLAG","EVENTS","ORIGEVENT","MARK","MANUFACTURE"};
  	     
  	     PreparedStatement pstmt=con.prepareStatement(sqls);
         PreparedStatement pstmtUpdate=con.prepareStatement(updateSqls);
        
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
		  
			   if(!ifUpdate){
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
				}else{//update
			  for(int aa=1; aa<=updateSqlsCol.length; aa++){
		   	  ExcelTableMapping eTmp = mapping.get(updateSqlsCol[aa-1].toUpperCase());
		   	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), m).getContents();
		   	 
		   	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
		   		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName() +"字段不能为空！", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
		   		 Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 字段不能为空！");
		   		 mark = -3;
		   		  parseExcelError = true;
		   		 break;
		   	  }else{
			    	  try{
			    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
			    			 if(eTmp.getName().equalsIgnoreCase("BTIMELIST") || eTmp.getName().equalsIgnoreCase("ETIMELIST")){
			    				  StringTokenizer timeStrs = new StringTokenizer(cellContentTmp, "|");
			    				  if(timeStrs == null || timeStrs.countTokens() != 7){
			    					  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
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
			    				 pstmtUpdate.setNull(aa, java.sql.Types.VARCHAR);
			    			 else
			    				 pstmtUpdate.setString(aa,cellContentTmp);
			    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
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
			    					  pstmtUpdate.setLong(aa,Long.parseLong(cellContentTmp));
			    					  continue;
				    			  }else{
				    				pstmtUpdate.setNull(aa, java.sql.Types.NUMERIC);
				    				continue;
				    			  }    		    		    				  
			    			  }
			    			  if(cellContentTmp != null && cellContentTmp != ""){
			    			  pstmtUpdate.setLong(aa,Long.parseLong(cellContentTmp));
			    			  }else{
			    				 pstmtUpdate.setNull(aa, java.sql.Types.NUMERIC);
			    			  }
			    	  	  }
			    	  }catch(Exception ew){
			    		  mark = -3;
			    		  appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),eTmp.getName()+"数据类型错误，无法解析", columnStrs);
			    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 数据类型错误，无法解析！");
			    		  parseExcelError = true;
			    		  break;
			    	  }
		   	  }
			  }
			  if(!parseExcelError){
				try{ 
					pstmtUpdate.execute();
				} catch (SQLException e) {
				  mark = -3;				
				  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " SQL错误:"+e.getMessage());
				  appendErrorMessage(sheet.getRow(m),errorMsgWb, sheet.getName(), "SQL错误:"+e.getMessage(), columnStrs);
				  e.printStackTrace();
				  continue;
				}  
			  }
		
				}
			    	  	
		
		}catch(Exception ew){
		mark = -3;
		appendErrorMessage(sheet.getRow(m), errorMsgWb, sheet.getName(),"信息错误，请检查！", columnStrs);
		Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " 错误:"+ew.getMessage());
		continue;
		}	
		
		}
		pstmt.close();
		pstmtUpdate.close();
		if(mark != -3)
		mark  =1;
    }
             }
         }
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
			return -4;
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
						String temp = null;;
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
							temp = row[c].getContents();
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




	public SnmpEventsProcessDao getSnmpEventsProcessDao() {
		return snmpEventsProcessDao;
	}




	public void setSnmpEventsProcessDao(SnmpEventsProcessDao snmpEventsProcessDao) {
		this.snmpEventsProcessDao = snmpEventsProcessDao;
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

}
