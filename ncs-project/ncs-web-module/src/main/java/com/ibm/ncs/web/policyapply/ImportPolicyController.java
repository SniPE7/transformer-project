/**
 * 
 */
package com.ibm.ncs.web.policyapply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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

import com.ibm.ncs.model.dao.PolicySyslogDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyDetailsDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dto.PolicySyslog;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyDetails;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.exceptions.TPolicyBaseDaoException;
import com.ibm.ncs.model.exceptions.TPolicyPeriodDaoException;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.excel.DBTOExcel;
import com.ibm.ncs.util.excel.ExcelTODB;
import com.ibm.ncs.util.excel.ExcelTableMapping;
import com.ibm.ncs.web.policyapply.bean.NonTimeFramePolicyDetails;

/**
 * @author root
 *
 */
public class ImportPolicyController implements Controller {

	TPolicyBaseDao TPolicyBaseDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	TPolicyDetailsDao TPolicyDetailsDao;
	TModuleInfoInitDao  TModuleInfoInitDao;
	TEventTypeInitDao  TEventTypeInitDao;
	GenPkNumber genPkNumber;
	PolicySyslogDao policySyslogDao;
	String pageView;
	String message;


	DataSource datasource;
	private int marke =0;
	
	protected SimpleJdbcTemplate jdbcTemplate;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		message = "";		
		try{
		final long MAX_SIZE = 1024 * 1024 * 1024;
		final String[] allowedExt = new String[] {"xls"};
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		DiskFileItemFactory dfif = new DiskFileItemFactory();
		dfif.setSizeThreshold(4096);
		dfif.setRepository(new File(request.getRealPath("/")));
		ServletFileUpload sfu = new ServletFileUpload(dfif);
		sfu.setSizeMax(MAX_SIZE);

		List fileList = null;
		try {
			fileList = sfu.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
			message = "policy.import.error";
			model.put("message", message);			
    		Log4jInit.ncsLog.error("InterruptedException in Export, message: " + e.getMessage());		
			return new ModelAndView(getPageView(),	"model", model);
		}

		if (fileList == null || fileList.size() == 0 || fileList.get(0).getClass().getName() == null) {
			message = "policy.import.noFile";	
			model.put("message", message);
			return new ModelAndView(getPageView(), "model" , model);
		}
		Iterator fileItr = fileList.iterator();

		while (fileItr.hasNext()) {
//			System.out.println("\t11111111\tin while");
			FileItem fileItem = null;
			String path = null;
			long size = 0;
			fileItem = (FileItem) fileItr.next();
			if (fileItem == null || fileItem.isFormField()) {
				continue;
			}
		
			path = fileItem.getName();
			size = fileItem.getSize();
			if ("".equals(path) || size == 0) {
				message = "policy.import.noFile";	
				model.put("message", message);
				return new ModelAndView(getPageView(), "model", model);
			}

			String t_name = path.substring(path.lastIndexOf("\\") + 1);
			String[] ver = t_name.split("\\.");
//			System.out.println(ver[1]);
			String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);
//			System.out.println("filename="+t_name+"; t_ext="+t_ext+"; ver[0]="+ver[0]+"; ver[1]="+ver[1]);
			int allowFlag = 0;
			int allowedExtCount = allowedExt.length;
			for (; allowFlag < allowedExtCount; allowFlag++) {
				if (allowedExt[allowFlag].equals(t_ext))
				break;
			}
			if (t_ext== null || allowFlag == allowedExtCount) {
				message = "policy.import.invalidFileType";	
				model.put("message", message);
				return new ModelAndView(getPageView(), "model", model);
			}

			String filename=ver[0]+".xls";		 
//			System.out.println("1111111^^^^^upload file name=" + filename);
			try {
				fileItem.write(new File(request.getRealPath("/") +"/uploadDir/"+ filename));
			  	String filePath = request.getRealPath("/")+ "/uploadDir/"+filename;
			  	
			  	//create a xls file for error message
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
				
				if(marke<1){ //ok
//					System.out.println("*************Return value from loadToDB is" + marke);
					if(marke == -2){	//no corresponding columns in excel ok
						message ="policy.import.missingColumn";
			        	model.put("message", message);					
			    		return new ModelAndView(getPageView(),	"model", model);
					}else if(marke == -3){//partially imported, and need to write error msg to another excel sheet
						message ="policy.import.partialSuccess";
			        	model.put("message", message);		
			        	
			        	SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmssSSS");
						String timestampStr = sdf.format(new Date());
						System.out.println("timestampstr is "+timestampStr);
						
			        	String errorDir = "/logs/xls";
						String errorXlsFile = "/importPolicyError_"+timestampStr+".xls";
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
						
			        	//write the error message to excel:
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
						message ="policy.import.noDataToImport";
			        	model.put("message", message);		
			    		return new ModelAndView(getPageView(),	"model", model);
					}
		        	message ="policy.import.failed";
		        	model.put("message", message);					
		    		return new ModelAndView(getPageView(),	"model", model);
				}else{//ok
				    message ="policy.import.success";
				    model.put("message", message);			
				    try {
						ExcelTODB.clearDir(request.getRealPath("/") +"/logs/xls");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return new ModelAndView(getPageView(),	"model", model);
				}
			} catch (Exception e) {
				e.printStackTrace();
	    		Log4jInit.ncsLog.error("InterruptedException in Export, message: " + e.getMessage());
			}
		}  
		}catch(Exception e){
			e.printStackTrace();
    		Log4jInit.ncsLog.error("InterruptedException in Export, message: " + e.getMessage());
		
		}
		message ="policy.import.failed";
    	model.put("message", message);									
		return new ModelAndView(getPageView(),	"model", model);
	}
	
	public  int  loadToDB(String filePath, HSSFWorkbook errorMsgWb) {		
		int mark=-1;
		boolean insertFlag = false;
	    boolean hasDataToImport = false;
		  InputStream inStr =null;
		  Workbook workBook =null;
		  Connection con =null;
		try {  	
			   inStr = new FileInputStream(filePath);
			   workBook = Workbook.getWorkbook(inStr);
			  
			  //get work sheet with the name 	  
		      Sheet[] sheetList = workBook.getSheets(); 
		       con = datasource.getConnection();
		      Map<String, ExcelTableMapping> mapping ;//= new HashMap<String, ExcelTableMapping>();
		      Map<String, Map> sheetmapping = new HashMap<String,Map>();
		      
		      if(sheetList != null){
		    	  for(int i=0; i<sheetList.length; i++){//for each row in excel
		    		  mapping = new  HashMap<String, ExcelTableMapping>();
		    		  Sheet sheet = sheetList[i];
		    		  String[] columnStrs = null;
		    		  if(sheet.getName().equalsIgnoreCase("NonTimeFrame_Policy_Details") 
		    				  || sheet.getName().equalsIgnoreCase("T_Policy_Period")
		    				  || sheet.getName().equalsIgnoreCase("Syslog_Policy_Details")){
		    			  hasDataToImport = true;
		    			  columnStrs = new String[sheet.getColumns()];
//			    		  System.out.println("Check point A:***************Read titles in excel file****************");
		    		      for(int c=0; c<sheet.getColumns(); c++){
		    		    	  columnStrs[c] = sheet.getCell(c, 0).getContents();
//		    		    	  System.out.println("  "+c+":"+columnStrs[c]);
		    		      }	
		    		  }else{
		    			  continue;
		    		  }
		    		  
		    		  //validate column mapping
		    		  int count=0;
	    		      if(sheet.getName().equalsIgnoreCase("NonTimeFrame_Policy_Details")){ //deal with non time frame policy "NonTimeFrame_Policy_Details"		
	    		    	  /**Using meta data*/
		    			  Statement descPstmt = con.createStatement();
		    			  ResultSet rs = descPstmt.executeQuery("select * from T_POLICY_DETAILS");
		    			  ResultSetMetaData rsmeta = rs.getMetaData();
		    			  //init mapping in while
		    			  int a = initTableMapping(rsmeta, columnStrs,mapping, "T_POLICY_DETAILS");
//		    			  System.out.println("End of Check point B1:***************Init Mapping for T_POLICY_DETAILS****Passed************");
		    			  rs.close();
		    			  descPstmt.close();
		    			  if(a<0){
		    				  return a;
		    			  }
		    			  
		    			  /**Using meta data*/
		    			  Statement descPstmt2 = con.createStatement();
		    			  ResultSet rs2 = descPstmt2.executeQuery("select * from T_POLICY_BASE");
		    			  ResultSetMetaData rsmeta2 = rs2.getMetaData();
		    			  //init mapping in while
		    			  a = initTableMapping(rsmeta2, columnStrs,mapping,"T_POLICY_BASE");
//		    			  System.out.println("End of Check point B2:***************Init Mapping for T_POLICY_BASE******passed**********");
			    		  rs2.close();
		    			  descPstmt2.close();
		    			  if(a<0){
		    				  return a;
		    			  }
		    			  
		    			  /**Using meta data*/
		    			  Statement descPstmt3 = con.createStatement();
		    			  ResultSet rs3 = descPstmt3.executeQuery("select * from T_MODULE_INFO_INIT");
		    			  ResultSetMetaData rsmeta3 = rs3.getMetaData();
		    			  //init mapping in while
		    			  a = initTableMapping(rsmeta3, columnStrs,mapping, "T_MODULE_INFO_INIT");
//		    			  System.out.println("End of Check point B3:***************Init Mapping for T_MODULE_INFO_INIT****passed************");
		    			  rs3.close();
		    			  descPstmt3.close();
		    			  if(a<0){
		    				  return a;
		    			  }
		    			  
		    			  /**Using meta data*/
		    			  Statement descPstmt4 = con.createStatement();
		    			  ResultSet rs4 = descPstmt4.executeQuery("select * from T_EVENT_TYPE_INIT");
		    			  ResultSetMetaData rsmeta4 = rs4.getMetaData();
		    			  //init mapping in while
		    			  a = initTableMapping(rsmeta4, columnStrs,mapping,"T_EVENT_TYPE_INIT");
//		    			  System.out.println("End of Check point B4:***************Init Mapping for T_EVENT_TYPE_INIT*passed***************");
		    			  rs4.close();
		    			  descPstmt4.close();
		    			  if(a<0){
		    				  return a;
		    			  }
	    		      }else if(sheet.getName().equalsIgnoreCase("T_Policy_Period")){//deal with time period policy
	    		    	  /**Using meta data*/
		    			  Statement descPstmt = con.createStatement();
		    			  ResultSet rs = descPstmt.executeQuery("select * from T_Policy_Period");
		    			  ResultSetMetaData rsmeta = rs.getMetaData();
		    			  //init mapping in while
		    			  for(int jj=1; jj<=rsmeta.getColumnCount(); jj++){
		  						ExcelTableMapping et = new ExcelTableMapping();
			  					String name = rsmeta.getColumnName(jj);
			  					if(name.equalsIgnoreCase("ETIME")  || name.equalsIgnoreCase("BTIME")){
			  						continue;
			  					}
			  					String type = rsmeta.getColumnTypeName(jj);
			  					String isNull = rsmeta.isNullable(jj)==0?"N":"Y";
			  					et.setName(name);
			  					et.setType(type);
			  					et.setNull(isNull.equalsIgnoreCase("Y")?true:false);
		    				  
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

//		    				  System.out.println("Count:" + count + "Column:"+ name+ " " + type + " "+ isNull + " index=" + et.getIndex());
		    				  mapping.put(et.getName().toUpperCase(), et);
		    				  count++;
		    			  }
		    			  rs.close();
		    			  descPstmt.close();
	    		      }else if(sheet.getName().equalsIgnoreCase("Syslog_Policy_Details")){
	    		    	  /**Using meta data*/
		    			  Statement descPstmt = con.createStatement();
		    			  ResultSet rs = descPstmt.executeQuery("select * from POLICY_SYSLOG");
		    			  ResultSetMetaData rsmeta = rs.getMetaData();
		    			  int a = initTableMapping(rsmeta, columnStrs,mapping, "POLICY_SYSLOG");
		    			  rs.close();
		    			  descPstmt.close();
		    			  if(a<0){
		    				  return a;
		    			  }
		    			  
		    			  /**Using meta data*/
		    			  Statement descPstmt2 = con.createStatement();
		    			  ResultSet rs2 = descPstmt2.executeQuery("select * from T_POLICY_BASE");
		    			  ResultSetMetaData rsmeta2 = rs2.getMetaData();
		    			  a = initTableMapping(rsmeta2, columnStrs,mapping,"T_POLICY_BASE");
//			    			  System.out.println("End of Check point B2:***************Init Mapping for T_POLICY_BASE******passed**********");
			    		  rs2.close();
		    			  descPstmt2.close();
		    			  if(a<0){
		    				  return a;
		    			  }		    
	    		      }
	 // System.out.println(mapping);  		      
	  					sheetmapping.put(sheet.getName().toUpperCase(), mapping);
	    		      //end of validate column mapping
		    	  }
	//System.out.println(sheetmapping);	    
	
		    	  for(int i=0; i<sheetList.length; i++){//for each row in excel
		    		  Sheet sheet = sheetList[i];
		    		  String[] columnStrs = null;
		    		  if(sheet.getName().equalsIgnoreCase("NonTimeFrame_Policy_Details") 
		    				  || sheet.getName().equalsIgnoreCase("T_Policy_Period")
		    				  || sheet.getName().equalsIgnoreCase("Syslog_Policy_Details")){
		    			  hasDataToImport = true;
		    			  columnStrs = new String[sheet.getColumns()];
//			    		  System.out.println("Check point A:***************Read titles in excel file****************");
		    		      for(int c=0; c<sheet.getColumns(); c++){
		    		    	  columnStrs[c] = sheet.getCell(c, 0).getContents();
//		    		    	  System.out.println("  "+c+":"+columnStrs[c]);
		    		      }	
		    		  }else{
		    			  continue;
		    		  }
		    		  mapping = sheetmapping.get(sheet.getName().toUpperCase());
	//	    		  System.out.println(mapping);
//	    		      System.out.println("End of Check point A:***************Read titles in excel file****Passed************");
	    		      int count=0;	    
	    		      //import non time frame policy
		    		  if(sheet.getName().equalsIgnoreCase("NonTimeFrame_Policy_Details")){ //deal with non time frame policy "NonTimeFrame_Policy_Details"		
		    			  
		    			  String sqls_details="insert into T_Policy_Details (Mpid, Modid, Eveid, Poll, Value_1, Severity_1, Filter_A, Value_2, Severity_2, Filter_B, Severity_A, Severity_B, Oidgroup, Ogflag, Value_1_Low, Value_2_Low, V1l_Severity_1, V1l_Severity_A, V2l_Severity_2, V2l_Severity_B)"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    			  String updateSqls_details = "UPDATE T_Policy_Details set Poll=?, Value_1=?, Severity_1=?, Filter_A=?, Value_2=?, Severity_2=?, Filter_B=?, Severity_A=?, Severity_B=?, Oidgroup=?, Ogflag=?, Value_1_Low=?, Value_2_Low=?, V1l_Severity_1=?, V1l_Severity_A=?, V2l_Severity_2=?, V2l_Severity_B=? Where Mpid=? and Modid=? and Eveid=?";
		    			  String[] sqlsCol_details= new String[]{"Mpid","Modid","Eveid","Poll","Value_1","Severity_1","Filter_A","Value_2","Severity_2","Filter_B","Severity_A","Severity_B","Oidgroup","Ogflag","Value_1_Low","Value_2_Low","V1l_Severity_1","V1l_Severity_A","V2l_Severity_2","V2l_Severity_B"};	
		    	 	      String[] updateSqlsCol_details= new String[]{"Poll","Value_1","Severity_1","Filter_A","Value_2","Severity_2","Filter_B","Severity_A","Severity_B","Oidgroup","Ogflag","Value_1_Low","Value_2_Low","V1l_Severity_1","V1l_Severity_A","V2l_Severity_2","V2l_Severity_B","Mpid","Modid","Eveid"};		
		    	 	      
		    	 	      String sqls_base="insert into T_Policy_Base (Mpid, Mpname, Category, Description)"+"values(?,?,?,?)";
		    			  String updateSqls_base = "UPDATE T_Policy_Base set Mpname=?, Category=?, Description=? Where Mpid=?";
		    			  String[] sqlsCol_base= new String[]{"Mpid","Mpname","Category","Description"};	
		    	 	      String[] updateSqlsCol_base= new String[]{"Mpname","Category","Description","Mpid"};	
		    	 	      
		    	 	      String sqls_module = "insert into T_module_info_init (modid,mname,mcode,description)"+"values(?,?,?,?)";
		    			  String updateSqls_module = "UPDATE T_module_info_init set mname=?, mcode=?, description=? where modid=?";
		    			  String[] sqlsCol_module= new String[]{"modid","mname","mcode","description"};	
		    	 	      String[] updateSqlsCol_module= new String[]{"mname","mcode","description","modid"};	
		    	 	      
		    	 	      String sqls_event="insert into t_event_type_init (modid,eveid,etid,estid,eveothername,ecode,general,major,minor,other,description,useflag)"+"values(?,?,?,?,?,?,?,?,?,?,?,?)";
		    			  String updateSqls_event = "UPDATE t_event_type_init set  etid=?, estid=?, eveothername=?, ecode=?, general=?, description=?, useflag=? Where eveid=? and modid=?";
		    			  String[] sqlsCol_event= new String[]{"modid","eveid","etid","estid","eveothername","ecode","general","major","minor","other","description","useflag"};	
		    	 	      String[] updateSqlsCol_event= new String[]{"etid","estid","eveothername","ecode","general","description","useflag","eveid","modid"};	
		    	 	      
		    	 	      PreparedStatement pstmt_details=con.prepareStatement(sqls_details);
		    	 	      PreparedStatement pstmtUpdate_details=con.prepareStatement(updateSqls_details);
		    	 	        
		    	 	      PreparedStatement pstmt_base=con.prepareStatement(sqls_base);
		    	 	      PreparedStatement pstmtUpdate_base=con.prepareStatement(updateSqls_base);
		    	 	      
		    	 	      PreparedStatement pstmt_module=con.prepareStatement(sqls_module);
		    	 	      PreparedStatement pstmtUpdate_module=con.prepareStatement(updateSqls_module);
		    	 	     
		    	 	      PreparedStatement pstmt_event=con.prepareStatement(sqls_event);
		    	 	      PreparedStatement pstmtUpdate_event=con.prepareStatement(updateSqls_event);
		    			  
		    	 	     for(int r=1; r<sheet.getRows(); r++){
		    	 	    	 try{
//		    	 	    	 System.out.println("Check Point C: Start Row :" + r + "----------------------------------------");
		    		    	  boolean parseExcelError = false;
		    		    	  boolean ifInsert_Details = false;
		    		    	  boolean ifUpdate_Base = false;
		    		    	  boolean ifUpdate_Module = false;
		    		    	  boolean ifUpdate_Event = false;
		    		    	  String mpid = sheet.getCell(mapping.get("MPID").getIndex(), r).getContents();
		    		    	  String modid = sheet.getCell(mapping.get("MODID").getIndex(), r).getContents();
		    		    	  String eveid = sheet.getCell(mapping.get("EVEID").getIndex(), r).getContents();
//		    		    	  System.out.println("\tPK for this row:mpid, modid, eveid=" + mpid+ " " + modid + " " + eveid );
		    		    	  //check whether to insert or update t_policy_base
		    		    	  List<TPolicyBase> tpbList = TPolicyBaseDao.findWhereMpnameEquals(sheet.getCell(mapping.get("MPNAME").getIndex(), r).getContents());
		    		    	  try{
		    		    		  if(tpbList != null && tpbList.size()>0 ){
		    		    			  if(tpbList.size()>1){
//		    		    				  System.out.print("\n\t\tCheck Point C12===major minor other bu wei yi on T_eventtypeinit");
				    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1,"MName shu ju ku zhong ji lu bu wei yi，无法解析", columnStrs);
				    		    		  mark = -3;
				    		    		  parseExcelError = true;
				    		    		  continue;
		    		    			  }
			    		    		  ifUpdate_Base = true;
			    		    		  mpid = String.valueOf(tpbList.get(0).getMpid());
//			    		    		  System.out.println("\t\tCheck Point C1==passed=mpname is already exist in T_Policy_Base, change the mpid if neccessary " + sheet.getCell(mapping.get("MPID").getIndex(), r).getContents() + " to " + mpid);
			    		    	  }else{
			    		    		  ifInsert_Details = true;
			    		    		  TPolicyBase tpb = TPolicyBaseDao.findByPrimaryKey(Long.parseLong(sheet.getCell(mapping.get("MPID").getIndex(), r).getContents()));
			    		    		  if(tpb !=null){
			    		    			  System.out.print("\n\t\tCheck Point C2==passed=mpname is not exist on T_POlicy_base, A new mpid is generated from :" + mpid);
			    		    			  mpid = String.valueOf(genPkNumber.getID());
			    		    			  System.out.print("TPolicyBaseDao to new id:" + mpid);
			    		    		  }
			    		    	  }
		    		    	  }catch(Exception e){
//		    		    		  System.out.print("\n\t\tCheck Point C3=passed==MPID 数据类型错误，无法解析 on T_POlicy_base");
		    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1,"MPID 数据类型错误，无法解析", columnStrs);
		    		    		  Log4jInit.ncsLog.error("MPID 数据类型错误，无法解析,"+ columnStrs +" message: " + e);
		    		    		  mark = -3;
		    		    		  parseExcelError = true;
		    		    		  continue;
		    		    	  }		    		    	
		    		    		
//		    		    	  System.out.println("\t\t====passed======Check Point C4: Start Policy Base: Action="+ new String(ifUpdate_Base==true?"Update":"Insert"));
		    		    	  int markTmp = insertUpdateNonTimeFramePolicy(ifUpdate_Base, sqlsCol_base, sheet, mapping, 
		    		    				r, mpid, "MPID", parseExcelError, errorMsgWb,
		    		    				columnStrs, pstmt_base, pstmtUpdate_base,updateSqlsCol_base, "t_policy_base","");
//		    		    	  System.out.println("\t\t====passed====== end of  Check Point C4: Start Policy Base: Action="+ new String(ifUpdate_Base==true?"Update":"Insert"));
		    		    	  if(markTmp == -3){
		    		    		  mark = -3;
		    		    		  continue;
		    		    	  }else if(markTmp == -1){
		    		    		  pstmt_base.close();
		    		    		  pstmtUpdate_base.close();
		    		    		  pstmt_details.close();
		    		    		  pstmtUpdate_details.close();
		    		    		  pstmt_module.close();
		    		    		  pstmtUpdate_module.close();
		    		    		  pstmt_event.close();
		    		    		  pstmtUpdate_event.close();
		    	//	    		  con.close();
		    		  	      	  return markTmp;
		    		    	  }
		    		    	  
//		    		    	  //check whether to insert or update t_module_info_init
//		    		    	  	    		    
		    		    	  List<TModuleInfoInit> tmiiList = TModuleInfoInitDao.findWhereMnameEquals(sheet.getCell(mapping.get("MNAME").getIndex(), r).getContents());
		    		    	  try{
		    		    		  if(tmiiList != null && tmiiList.size()>0 ){
			    		    		  ifUpdate_Module = true;
			    		    		  if(tmiiList.size()>1){
//		    		    				  System.out.print("\n\t\tCheck Point C12===major minor other bu wei yi on T_eventtypeinit");
				    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1,"MName shu ju ku zhong ji lu bu wei yi，无法解析", columnStrs);
				    		    		  mark = -3;
				    		    		  parseExcelError = true;
				    		    		  continue;
		    		    			  }
			    		    		  modid = String.valueOf(tmiiList.get(0).getModid());
//			    		    		  System.out.println("\t\tCheck Point C5=passed==mname is already exist in T_module_info_init, change the modid if neccessary " + sheet.getCell(mapping.get("MODID").getIndex(), r).getContents() + " to " + modid);
				    		     }else{
			    		    		  ifInsert_Details = true;
			    		    		  TModuleInfoInit tmii = TModuleInfoInitDao.findByPrimaryKey(Long.parseLong(sheet.getCell(mapping.get("MODID").getIndex(), r).getContents()));
			    		    		  if(tmii !=null){
			    		    			  System.out.print("\n\t\tCheck Point C6==passed=mname is not exist on T_module_info_init, A new mpid is generated from :" + modid);
		    		    			  
			    		    			  modid = String.valueOf(genPkNumber.getID());
			    		    			  System.out.print("TModuleInfoInitDao to new id:" + mpid);
			    		    		  }
			    		    	  }
		    		    	  }catch(Exception e){
//		    		    		  System.out.print("\n\t\tCheck Point C7=passed==MODID 数据类型错误，无法解析 on T_module_info_init");
		    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1,"MODID 数据类型错误，无法解析", columnStrs);
		    		    		  Log4jInit.ncsLog.error("MODID 数据类型错误，无法解析,"+ columnStrs +" message: " + e);
		    		    		  mark = -3;
		    		    		  parseExcelError = true;
		    		    		  continue;
		    		    	  }		    		    	
//		    		    	  System.out.println("\t\t====passed===Check Point C8 Start ModuleInfoInit: Action="+ new String(ifUpdate_Module==true?"Update":"Insert"));
		    		    		
		    		    	  markTmp = insertUpdateNonTimeFramePolicy(ifUpdate_Module, sqlsCol_module, sheet, mapping, 
		    		    				r, modid, "MODID", parseExcelError, errorMsgWb,
		    		    				columnStrs, pstmt_module, pstmtUpdate_module,updateSqlsCol_module,"T_module_info_init","");
//		    		    	  System.out.println("\t\t=====passed===== end of  Check Point C8: Start ModuleInfoInit: Action="+ new String(ifUpdate_Base==true?"Update":"Insert"));
		    		    	  if(markTmp == -3){
		    		    	  	  mark=-3;
		    		    		  continue;
		    		    	  }else if(markTmp == -1){
		    		    		  pstmt_base.close();
		    		    		  pstmtUpdate_base.close();
		    		    		  pstmt_details.close();
		    		    		  pstmtUpdate_details.close();
		    		    		  pstmt_module.close();
		    		    		  pstmtUpdate_module.close();
		    		    		  pstmt_event.close();
		    		    		  pstmtUpdate_event.close();
		    		//    		  con.close();
		    		  	      	  return markTmp;
		    		    	  }
//		    		    	  	
//		    		    	//check whether to insert or update t_event_info_init
//		    		    	  
		    		    	  List<TEventTypeInit> tetiList = TEventTypeInitDao.findWhereMajorMinorOtherEquals(sheet.getCell(mapping.get("MAJOR").getIndex(), r).getContents(),
		    		    			  sheet.getCell(mapping.get("MINOR").getIndex(), r).getContents(),sheet.getCell(mapping.get("OTHER").getIndex(), r).getContents());
		    		    	  try{
		    		    		  if(tetiList != null && tetiList.size()>0 ){
//		    		    			  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%tetiList.size()=%%%%%%%%%%%%%%" + tetiList.size());
		    		    			  if(tetiList.size()>1){
//		    		    				  System.out.print("\n\t\tCheck Point C12===major minor other bu wei yi on T_eventtypeinit");
				    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1,"major minor other zai shu ju ku zhong ji lu bu wei yi，无法解析", columnStrs);
				    		    		  mark = -3;
				    		    		  parseExcelError = true;
				    		    		  continue;
		    		    			  }
			    		    		  ifUpdate_Event = true;
			    		    		  eveid = String.valueOf(tetiList.get(0).getEveid());
			    		    		  
//			    		    		  System.out.println("\t\tCheck Point C9===MAJOR, minor, other is already exist in T_eventtypeinit, change the eveid if neccessary " + sheet.getCell(mapping.get("EVEID").getIndex(), r).getContents() + " to " + eveid);
						    	  }else{
			    		    		  ifInsert_Details = true; 
			    		    		  List<TEventTypeInit> teti = TEventTypeInitDao.findWhereEveidEquals(Long.parseLong(sheet.getCell(mapping.get("EVEID").getIndex(), r).getContents()));
			    		    		  if(teti !=null && teti.size()>0){
			    		    			 System.out.print("\n\t\tCheck Point C10===MAJOR,minor, other is not exist on T_eventtypeinit, A new eveid is generated from :" + eveid);
			    		    			  eveid = String.valueOf(genPkNumber.getID());
			    		    			  System.out.print("TEventTypeInitDao to new id:" + mpid);
			    		    		  }
			    		    	  }
		    		    	  }catch(Exception e){
//		    		    		  System.out.print("\n\t\tCheck Point C11===EVEID 数据类型错误，无法解析 on T_eventtypeinit");
		    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1,"EVEID 数据类型错误，无法解析", columnStrs);
		    		    		  Log4jInit.ncsLog.error("EVEID 数据类型错误，无法解析,"+ columnStrs +" message: " + e);
		    		    		  mark = -3;
		    		    		  parseExcelError = true;
		    		    		  continue;
		    		    	  }		    	
		    		    	  if(!ifUpdate_Module)
		    		    		  ifUpdate_Event = false;
		    		    		
//		    		    	  System.out.println("\n\t\t=======Check Point C12 Start t_event_type_init: Action="+ new String(ifUpdate_Event==true?"Update":"Insert"));
//		    		    	  System.out.println("\t\t\t===check point C12-1: T_module_info_init action=" + ifUpdate_Module + " eveid=" + eveid + " modid=" + modid);
		    		    	  markTmp = insertUpdateNonTimeFramePolicy(ifUpdate_Event, sqlsCol_event, sheet, mapping, 
		    		    				r, eveid, "EVEID", parseExcelError, errorMsgWb,
		    		    				columnStrs, pstmt_event, pstmtUpdate_event,updateSqlsCol_event, "t_event_type_init",modid);
//		    		    	  System.out.println("\t\t========== end of  Check Point C11: Start t_event_type_init: Action="+ new String(ifUpdate_Event==true?"Update":"Insert") + "\n");
		    		    	  if(markTmp == -3){
		    		    	  	  mark = -3;
		    		    		  continue;
		    		    	  }else if(markTmp == -1){
		    		    		  pstmt_base.close();
		    		    		  pstmtUpdate_base.close();
		    		    		  pstmt_details.close();
		    		    		  pstmtUpdate_details.close();
		    		    		  pstmt_module.close();
		    		    		  pstmtUpdate_module.close();
		    		    		  pstmt_event.close();
		    		    		  pstmtUpdate_event.close();
		    		 //   		  con.close();
		    		  	      	  return markTmp;
		    		    	  }
		    		    	  int descCount=0;
//		    		    	  System.out.println("========444Start policy details: Action="+ new String(ifInsert_Details==false?"Update":"Insert"));
		    		    	  
		    		    	  if(ifInsert_Details){ //insert into 
		    		    		  for(int aa=1; aa<=sqlsCol_details.length; aa++){
				    		    	  ExcelTableMapping eTmp = mapping.get(sqlsCol_details[aa-1].toUpperCase());
				    		    	  if(sqlsCol_details[aa-1].equalsIgnoreCase("description")){
				  		  				eTmp = mapping.get(sqlsCol_details[aa-1].toUpperCase()+String.valueOf(descCount));
				  		  				descCount++;
				  			    	  }
				    		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
				    		    	  if(sqlsCol_details[aa-1].toUpperCase().equals("MPID"))
				    		    		  cellContentTmp = mpid;
				    		    	  else if(sqlsCol_details[aa-1].toUpperCase().equals("EVEID"))
				    		    		  cellContentTmp = eveid;
				    		    	  else if(sqlsCol_details[aa-1].toUpperCase().equals("MODID"))
				    		    		  cellContentTmp = modid;
//				    		    	  System.out.println("aa=" + aa + "ColumnName=" + sqlsCol_details[aa-1]+"cell content=" + cellContentTmp);
				    		    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
				    		    		  mark  = -3;
				    		    		  parseExcelError = true;
				    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1 ,eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
				    		    		  continue;
				    		    	  }else{
//				    		    		  if(cellContentTmp == null || cellContentTmp.equals(""))
				    		    			  
					    		    	  try{
					    		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
					    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
					    		    				  pstmt_details.setNull(aa, java.sql.Types.NUMERIC);
    				    		    			  else
    				    		    				  pstmt_details.setLong(aa,Long.parseLong(cellContentTmp));
//					    		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
					    		    		  }else if(eTmp.getType().equalsIgnoreCase("DATE")){
					    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
					    		    				  pstmt_details.setNull(aa, java.sql.Types.DATE);
    				    		    			  else{
    				    		    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yy-MM-dd");
    				    		    				  pstmt_details.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
    				    		    			  }
//					    		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
					    		    		  }else if(eTmp.getType().equalsIgnoreCase("varchar2")){
					    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
					    		    				  pstmt_details.setNull(aa, java.sql.Types.VARCHAR);
    				    		    			  else
    				    		    				  pstmt_details.setString(aa,cellContentTmp);
//					    		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
					    		    		  }
					    		    	  }catch(Exception e){
					    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1,eTmp.getName()+" 数据类型错误，无法解析", columnStrs);
					    		    		  Log4jInit.ncsLog.error(" 数据类型错误，无法解析,"+ columnStrs +" message: " + e);
					    		    		  mark = -3;
					    		    		  parseExcelError = true;
//					    		    		  System.out.print("In insert catch:aa=" + aa + " type=" + eTmp.getType());
					    		    		  continue;
					    		    	  }
				    		    	  }
			    		    	  }
	
			    		    	  if(!parseExcelError){
//	    		    		    		 System.out.println("==Step 2: Execute insert to pstmtUpdate_details command");
	        		    				  try {
											pstmt_details.execute();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}	
			    		    	  }
		    		    	  }else{//update
//	    		    		    	  System.out.println("==Step 3: In update: Read row "+ r + "\tCol.length=" + updateSqlsCol.length+ "\tPrepare update sql:");
	    		    				  for(int aa=1; aa<=updateSqlsCol_details.length; aa++){
	    			    		    	  ExcelTableMapping eTmp = mapping.get(updateSqlsCol_details[aa-1].toUpperCase());
	    			    		    	  if(updateSqlsCol_details[aa-1].equalsIgnoreCase("description")){
	  				  		  				eTmp = mapping.get(updateSqlsCol_details[aa-1].toUpperCase()+String.valueOf(descCount));
	  				  		  				descCount++;
	  				  			    	  }
	    			    		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
	    			    		    	  if(updateSqlsCol_details[aa-1].toUpperCase().equals("MPID"))
					    		    		  cellContentTmp = mpid;
					    		    	  else if(updateSqlsCol_details[aa-1].toUpperCase().equals("EVEID"))
					    		    		  cellContentTmp = eveid;
					    		    	  else if(updateSqlsCol_details[aa-1].toUpperCase().equals("MODID"))
					    		    		  cellContentTmp = modid;
//	    			    		    	  System.out.println("aa=" + aa + "cell content=" + cellContentTmp);
	    			    		    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
	    			    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1,eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
	    			    		    		  mark = -3;
	    			    		    		  parseExcelError = true;
    				    		    		  continue;
	    			    		    	  }else{
	    				    		    	  try{
	    				    		    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
	    				    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
	    				    		    				  pstmtUpdate_details.setNull(aa, java.sql.Types.VARCHAR);
	    				    		    			  else
	    				    		    				  pstmtUpdate_details.setString(aa,cellContentTmp);
//	    				    		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
	    				    		    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
	    				    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
	    				    		    				  pstmtUpdate_details.setNull(aa, java.sql.Types.NUMERIC);
	    				    		    			  else
	    				    		    				  pstmtUpdate_details.setLong(aa,Long.parseLong(cellContentTmp));
//	    				    		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
	    				    		    	  	  }else if(eTmp.getType().equalsIgnoreCase("date")){
	    				    		    	  		if(cellContentTmp == null || cellContentTmp.equals("null"))
	    				    		    	  			pstmtUpdate_details.setNull(aa, java.sql.Types.DATE);
	    				    		    			  else
	    				    		    			  {
	    				    		    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    				    		    				  pstmtUpdate_details.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
	    				    		    			  }
//	    				    		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
	    				    		    		  }else{
//	    				    		    			  System.out.print("\taa=" + aa + " undefined data type:" +  eTmp.getType());
	    				    		    		  }
	    				    		    	  }catch(Exception ew){
	    				    		    		  mark = -3;
	    				    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1,eTmp.getName()+" 数据类型错误，无法解析", columnStrs);
	    				    		    		  Log4jInit.ncsLog.error(" 数据类型错误，无法解析,"+ columnStrs +" ew message: " + ew);
	//    				    		    		  ew.printStackTrace();
//	    				    		    		  System.out.print("In update catch:aa=" + aa + " type=" + eTmp.getType());
	    				    		    		  parseExcelError = true;
	    				    		    		  continue;
	    				    		    	  }
	    			    		    	  }
	    		    		    	  }
//	    		    				  System.out.println("\n==Step 4: Execute update command\n");
	    		    				  if(!parseExcelError){
//	    		    		    		  System.out.println("==Step 2: Execute update to pstmtUpdate_details command");
	    		    					  try {
											pstmtUpdate_details.execute();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
	    		    				  }
    		    			  }	
//		    		    	  System.out.println("End of Row :" + r + "----------------------------------------");

		    		    	 }catch(Exception ew){
		    		    		  mark = -3;
		    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1," 数据类型错误，无法解析+??", columnStrs);
		    		    		  Log4jInit.ncsLog.error("数据类型错误，无法解析+??,"+ columnStrs +" ew message: " + ew);
		    		    		  ew.printStackTrace();
		    		    		  continue;
		    		    	}	
			    	  }//end of for row
		    	 	  pstmt_base.close();
  		    		  pstmtUpdate_base.close();
  		    		  pstmt_details.close();
  		    		  pstmtUpdate_details.close();
  		    		  pstmt_module.close();
  		    		  pstmtUpdate_module.close();
  		    		  pstmt_event.close();
  		    		  pstmtUpdate_event.close();
		    		  if(mark != -3)
		    			  mark  =1;
		    			  
			    	  }else if(sheet.getName().equalsIgnoreCase("T_Policy_Period")){//deal with time period policy
	
		    		      String sqls="insert into T_Policy_Period (Ppid, PpName, Start_Time, End_Time, Description, Workday, Enabled, DefaultFlag, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7)"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    	 	      String updateSqls = "UPDATE T_Policy_Period set PpName=?, Start_Time=?, End_Time=?, Description=?, Workday=?, Enabled=?, DefaultFlag=?, S1=?, S2=?, S3=?, S4=?, S5=?, S6=?, S7=?, E1=?, E2=?, E3=?, E4=?, E5=?, E6=?, E7=? Where Ppid=?";
		    	 	      String[] sqlsCol= new String[]{"Ppid","PpName","Start_Time","End_Time","Description","Workday","Enabled","DefaultFlag","S1","S2","S3","S4","S5","S6","S7","E1","E2","E3","E4","E5","E6","E7"};	
		    	 	      String[] updateSqlsCol= new String[]{"PpName","Start_Time","End_Time","Description","Workday","Enabled","DefaultFlag","S1","S2","S3","S4","S5","S6","S7","E1","E2","E3","E4","E5","E6","E7","Ppid"};	
		    	 	      
		    	 	      PreparedStatement pstmt=con.prepareStatement(sqls);
		    	 	      PreparedStatement pstmtUpdate=con.prepareStatement(updateSqls);
		    	 	        
		    		      for(int r=1; r<sheet.getRows(); r++){
		    		    	  try{
		    		    	  boolean parseExcelError = false;
		    		    	  boolean ifUpdate = false;
		    		    	  String ppid = sheet.getCell(mapping.get("PPID").getIndex(), r).getContents();
		    		    	  //check whether to insert or update this record
		    		    	  List<TPolicyPeriod> tpList = TPolicyPeriodDao.findWherePpnameEquals(sheet.getCell(mapping.get("PPNAME").getIndex(), r).getContents());

		    		    	  try{
		    		    		  if(tpList != null && tpList.size()>0 ){
			    		    		  ifUpdate = true;
			    		    		  ppid = String.valueOf(tpList.get(0).getPpid());
//			    		    		  System.out.println("changed ppid from " + sheet.getCell(mapping.get("PPID").getIndex(), r).getContents() + " to " + ppid);
			    		    	  }else{
			    		    		  TPolicyPeriod tp = TPolicyPeriodDao.findByPrimaryKey(Long.parseLong(sheet.getCell(mapping.get("PPID").getIndex(), r).getContents()));
			    		    		  if(tp !=null){
			    		    			  System.out.print("\nA new ppid is generated from :" + ppid);
			    		    			  ppid = String.valueOf(genPkNumber.getID());
			    		    			  System.out.print("TPolicyPeriodDao to new id:" + ppid);
			    		    		  }
			    		    	  }
		    		    	  }catch(Exception e){
		    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 2,"PPID 数据类型错误，无法解析", columnStrs);
		    		    		  Log4jInit.ncsLog.error("type=2; PPID 数据类型错误，无法解析,"+ columnStrs +" message: " + e);
		    		    		  mark = -3;
		    		    		  parseExcelError = true;
		    		    		  continue;
		    		    	  }		    		    	
		    		    		  
		    		    	  if(!ifUpdate){
//		    		    		  System.out.println("==Step 3: In INSERT: Read row "+ r + "\tCol.length=" + updateSqlsCol.length+ "\tPrepare INSERT sql:");
    		    				  for(int aa=1; aa<=sqlsCol.length; aa++){
				    		    	  ExcelTableMapping eTmp = mapping.get(sqlsCol[aa-1].toUpperCase());
				    		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
				    		    	  if(sqlsCol[aa-1].toUpperCase().equals("PPID"))
				    		    		  cellContentTmp = ppid;
	//			    		    	  System.out.println("aa=" + aa + "cell content=" + cellContentTmp);
				    		    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
				    		    		  mark  = -3;
				    		    		  parseExcelError = true;
				    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 2 ,eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
				    		    		  continue;
				    		    	  }else{
					    		    	  try{
					    		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
					    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
					    		    				  pstmt.setNull(aa, java.sql.Types.NUMERIC);
    				    		    			  else
    				    		    				  pstmt.setLong(aa,Long.parseLong(cellContentTmp));
//					    		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
					    		    		  }else if(eTmp.getType().equalsIgnoreCase("DATE")){
					    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
					    		    				  pstmt.setNull(aa, java.sql.Types.DATE);
    				    		    			  else{
    				    		    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yy-MM-dd");
    				    		    				  pstmt.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
    				    		    			  }
//					    		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
					    		    		  }else if(eTmp.getType().equalsIgnoreCase("varchar2")){
					    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
					    		    				  pstmt.setNull(aa, java.sql.Types.VARCHAR);
    				    		    			  else
    				    		    				  pstmt.setString(aa,cellContentTmp);
//					    		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
					    		    		  }
					    		    	  }catch(Exception e){
					    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 2,eTmp.getName()+" 数据类型错误，无法解析", columnStrs);
					    		    		  Log4jInit.ncsLog.error("type=2; 数据类型错误，无法解析,"+ columnStrs +" message: " + e);
					    		    		  mark = -3;
					    		    		  parseExcelError = true;
//					    		    		  System.out.print("In insert catch:aa=" + aa + " type=" + eTmp.getType());
					    		    		  continue;
					    		    	  }
				    		    	  }
			    		    	  }
	
			    		    	  if(!parseExcelError){
//	    		    		    		  System.out.println("\n==Step 2: Execute insert command\n");
	        		    				  try {
											pstmt.execute();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}	
			    		    	  }
		    		    	  }else{//update
//	    		    		    	  System.out.println("==Step 3: In update: Read row "+ r + "\tCol.length=" + updateSqlsCol.length+ "\tPrepare update sql:");
	    		    				  for(int aa=1; aa<=updateSqlsCol.length; aa++){
	    			    		    	  ExcelTableMapping eTmp = mapping.get(updateSqlsCol[aa-1].toUpperCase());
	    			    		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
					    		    	  if(updateSqlsCol[aa-1].toUpperCase().equals("PPID"))
					    		    		  cellContentTmp = ppid;
//	    			    		    	  System.out.println("aa=" + aa + "cell content=" + cellContentTmp);
	    			    		    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
	    			    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 2,eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
	    			    		    		  mark = -3;
	    			    		    		  parseExcelError = true;
    				    		    		  continue;
	    			    		    	  }else{
	    				    		    	  try{
	    				    		    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
	    				    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
	    				    		    				  pstmtUpdate.setNull(aa, java.sql.Types.VARCHAR);
	    				    		    			  else
	    				    		    				  pstmtUpdate.setString(aa,cellContentTmp);
//	    				    		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
	    				    		    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
	    				    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
	    				    		    				  pstmtUpdate.setNull(aa, java.sql.Types.NUMERIC);
	    				    		    			  else
	    				    		    				  pstmtUpdate.setLong(aa,Long.parseLong(cellContentTmp));
//	    				    		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
	    				    		    	  	  }else if(eTmp.getType().equalsIgnoreCase("date")){
	    				    		    	  		if(cellContentTmp == null || cellContentTmp.equals("null"))
	    				    		    	  			pstmtUpdate.setNull(aa, java.sql.Types.DATE);
	    				    		    			  else{
	    				    		    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    				    		    				  pstmtUpdate.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
	    				    		    			  }
//	    				    		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
	    				    		    		  }else{
//	    				    		    			  System.out.print("\taa=" + aa + " undefined data type:" +  eTmp.getType());
	    				    		    		  }
	    				    		    	  }catch(Exception ew){
	    				    		    		  mark = -3;
	    				    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 2,eTmp.getName()+" 数据类型错误，无法解析"+cellContentTmp, columnStrs);
	    				    		    		  Log4jInit.ncsLog.error("type=2; 数据类型错误，无法解析,"+ columnStrs +" ew message: " + ew);
	//    				    		    		  ew.printStackTrace();
//	    				    		    		  System.out.print("In update catch:aa=" + aa + " type=" + eTmp.getType());
	    				    		    		  parseExcelError = true;
	    				    		    		  continue;
	    				    		    	  }
	    			    		    	  }
	    		    		    	  }
//	    		    				  System.out.println("\n==Step 4: Execute update command\n");
	    		    				  if(!parseExcelError){
	    		    					  try {
											pstmtUpdate.execute();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
	    		    				  }
    		    			  }	
		    		    	  }catch(Exception ew){
		    		    		  mark = -3;
		    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 2," 数据类型错误，无法解析+?", columnStrs);
		    		    		  Log4jInit.ncsLog.error("type=2; 数据类型错误，无法解析,"+ columnStrs +" ew message: " + ew);
		    		    		  continue;
		    		    	}	
			    	  }//end of for row
		    		  pstmt.close();
		    		  pstmtUpdate.close();
		    		  if(mark != -3)
		    			  mark  =1;
		    		//end of t_policy_period  
		    	  }else if(sheet.getName().equalsIgnoreCase("Syslog_Policy_Details")){			  
		    			  
		    			  String sqls_details="insert into Policy_Syslog (Spid, Mpid, Mark, Manufacture, Eventtype, Severity1, Severity2, Filterflag1, Filterflag2)"+" values(?,?,?,?,?,?,?,?,?)";
		    			  String updateSqls_details = "UPDATE Policy_Syslog set Mpid=?, Mark=?, Manufacture=?, Eventtype=?, Severity1=?, Severity2=?, Filterflag1=?, Filterflag2=? Where Spid=?";
		    			  String[] sqlsCol_details= new String[]{"Spid","Mpid","Mark","Manufacture","Eventtype","Severity1","Severity2","Filterflag1","Filterflag2"};	
		    	 	      String[] updateSqlsCol_details= new String[]{"Mpid","Mark","Manufacture","Eventtype","Severity1","Severity2","Filterflag1","Filterflag2", "Spid"};		
		    	 	      
		    	 	      String sqls_base="insert into T_Policy_Base (Mpid, Mpname, Category, Description)"+"values(?,?,?,?)";
		    			  String updateSqls_base = "UPDATE T_Policy_Base set Mpname=?, Category=?, Description=? Where Mpid=?";
		    			  String[] sqlsCol_base= new String[]{"Mpid","Mpname","Category","Description"};	
		    	 	      String[] updateSqlsCol_base= new String[]{"Mpname","Category","Description","Mpid"};	
		    	 	      
		    	 	      PreparedStatement pstmt_details=con.prepareStatement(sqls_details);
		    	 	      PreparedStatement pstmtUpdate_details=con.prepareStatement(updateSqls_details);
		    	 	        
		    	 	      PreparedStatement pstmt_base=con.prepareStatement(sqls_base);
		    	 	      PreparedStatement pstmtUpdate_base=con.prepareStatement(updateSqls_base);
		    	 	      
		    	 	     for(int r=1; r<sheet.getRows(); r++){
		    	 	    	 try{
//		    	 	    	 System.out.println("Check Point C: Start Row :" + r + "----------------------------------------");
		    		    	  boolean parseExcelError = false;
		    		    	  boolean ifInsert_Details = false;
		    		    	  boolean ifUpdate_Base = false;
		    		    	  String mpid = "";
		    		    	  String spid = "";
		    		    	  String markID = "";
		    		    	  try{
		    		    		  mpid = sheet.getCell(mapping.get("MPID").getIndex(), r).getContents();
		    		    		  spid = sheet.getCell(mapping.get("SPID").getIndex(), r).getContents();
		    		    		  markID = sheet.getCell(mapping.get("MARK").getIndex(), r).getContents();
		    		    		  
//		    		    		  System.out.println("\tPK for this row:mpid, SPID, mark=" + mpid+ " " + spid );
		    		    	  }catch(Exception ew){
		    		    		  mark = -3;
		    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 3," mpid, spid, mark 数据类型错误，无法解析", columnStrs);
		    		    		  Log4jInit.ncsLog.error("type=3, mpid,spid, mark 数据类型错误，无法解析,"+ columnStrs +" message: " + ew);
		    		    		  continue;
		    		    	}	
		    		    	  //check whether to insert or update t_policy_base
		    		    	  List<TPolicyBase> tpbList = TPolicyBaseDao.findWhereMpnameEquals(sheet.getCell(mapping.get("MPNAME").getIndex(), r).getContents());
		    		    	  try{
		    		    		  if(tpbList != null && tpbList.size()>0 ){
		    		    			  if(tpbList.size()>1){
//		    		    				  System.out.print("\n\t\tCheck Point C12===major minor other bu wei yi on T_eventtypeinit");
				    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 3,"MName shu ju ku zhong ji lu bu wei yi，无法解析", columnStrs);
				    		    		  mark = -3;
				    		    		  parseExcelError = true;
				    		    		  continue;
		    		    			  }
			    		    		  ifUpdate_Base = true;
			    		    		  mpid = String.valueOf(tpbList.get(0).getMpid());
//			    		    		  System.out.println("\t\tCheck Point C1==passed=mpname is already exist in T_Policy_Base, change the mpid if neccessary " + sheet.getCell(mapping.get("MPID").getIndex(), r).getContents() + " to " + mpid);
			    		    	  }else{
			    		    		  ifInsert_Details = true;
			    		    		  TPolicyBase tpb = TPolicyBaseDao.findByPrimaryKey(Long.parseLong(sheet.getCell(mapping.get("MPID").getIndex(), r).getContents()));
			    		    		  if(tpb !=null){
			    		    			  System.out.print("\n\t\tCheck Point C2==passed=mpname is not exist on T_POlicy_base, A new mpid is generated from :" + mpid);
			    		    			  mpid = String.valueOf(genPkNumber.getID());
			    		    			  System.out.print("TPolicyBaseDao to new id:" + mpid);
			    		    		  }
			    		    		  PolicySyslog psTmp = policySyslogDao.findByPrimaryKey(Long.parseLong(spid));
			    		    		  if(psTmp != null)
			    		    			  spid = String.valueOf(genPkNumber.getID());			    		    		  
			    		    	  }
		    		    		  if(!ifInsert_Details){
		    		    			  List<PolicySyslog> psTmp = policySyslogDao.findWhereMarkAndMpidEquals(markID, Long.parseLong(mpid));
			    		    		  if(psTmp == null){
			    		    			  spid = String.valueOf(genPkNumber.getID());
			    		    			  ifInsert_Details = true;
			    		    		  }else{
			    		    			  String manu = sheet.getCell(mapping.get("MANUFACTURE").getIndex(), r).getContents();
			    		    			  boolean flagTmp = false;
			    		    			  for(int g=0; g<psTmp.size(); g++){
			    		    				  if(psTmp.get(g).getManufacture().equalsIgnoreCase(manu)){
			    		    					  flagTmp = true;
			    		    					  break;
					    		    		  }
			    		    			  }
			    		    			  if(!flagTmp){
			    		    				  spid = String.valueOf(genPkNumber.getID());
				    		    			  ifInsert_Details = true;
			    		    			  }
			    		    		  }
		    		    		  }
		    		    	  }catch(Exception e){
//		    		    		  System.out.print("\n\t\tCheck Point C3=passed==MPID 数据类型错误，无法解析 on T_POlicy_base");
		    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 3,"MPID 数据类型错误，无法解析", columnStrs);
		    		    		  Log4jInit.ncsLog.error("type=3; MPID 数据类型错误，无法解析,"+ columnStrs +" message: " + e);
		    		    		  mark = -3;
		    		    		  parseExcelError = true;
		    		    		  e.printStackTrace();
		    		    		  continue;
		    		    	  }		    		    	
		    		    		
//		    		    	  System.out.println("\t\t====passed======Check Point C4: Start Policy Base: Action="+ new String(ifUpdate_Base==true?"Update":"Insert"));
		    		    	  int markTmp = insertUpdateSyslogPolicy(ifUpdate_Base, sqlsCol_base, sheet, mapping, 
		    		    				r, mpid, "MPID", parseExcelError, errorMsgWb,
		    		    				columnStrs, pstmt_base, pstmtUpdate_base,updateSqlsCol_base);
//		    		    	  System.out.println("\t\t====passed====== end of  Check Point C4: Start Policy Base: Action="+ new String(ifUpdate_Base==true?"Update":"Insert"));
		    		    	  if(markTmp == -3){
		    		    		  mark = -3;
		    		    		  continue;
		    		    	  }else if(markTmp == -1){
		    		    		  pstmt_base.close();
		    		    		  pstmtUpdate_base.close();
		    		    		  pstmt_details.close();
		    		    		  pstmtUpdate_details.close();
		    		    	//	  con.close();
		    		  	      	  return markTmp;
		    		    	  }
		    		    	  
//		    		    	  System.out.println("========444Start policy details: Action="+ new String(ifInsert_Details==false?"Update":"Insert"));
		    		    	  
		    		    	  if(ifInsert_Details){ //insert into 
		    		    		  for(int aa=1; aa<=sqlsCol_details.length; aa++){
				    		    	  ExcelTableMapping eTmp = mapping.get(sqlsCol_details[aa-1].toUpperCase());
//				    		    	  System.out.println("eTmp: Name=" + eTmp.getName() + "Type=" + eTmp.getType() + "Index=" + eTmp.getIndex());
				    		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();

//				    		    	  System.out.println("sqlsCol_details[aa-1]=" + sqlsCol_details[aa-1] + " spid=" + spid + " mpid=" +mpid);
				    		    	  if(sqlsCol_details[aa-1].toUpperCase().equals("SPID"))
				    		    		  cellContentTmp = spid;
				    		    	  if(sqlsCol_details[aa-1].toUpperCase().equals("MPID"))
				    		    		  cellContentTmp = mpid;
//				    		    	  System.out.println("aa=" + aa + "ColumnName=" + sqlsCol_details[aa-1]+"cell content=" + cellContentTmp);
				    		    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
				    		    		  mark  = -3;
				    		    		  parseExcelError = true;
				    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 3 ,eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
				    		    		  continue;
				    		    	  }else{
//				    		    		  if(eTmp.isNull() && cellContentTmp.equalsIgnoreCase("null"))
//				    		    			  continue;
					    		    	  try{
					    		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
					    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
					    		    				  pstmt_details.setNull(aa, java.sql.Types.NUMERIC);
    				    		    			  else
    				    		    				  pstmt_details.setLong(aa,Long.parseLong(cellContentTmp));
//					    		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
					    		    		  }else if(eTmp.getType().equalsIgnoreCase("DATE")){
					    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
					    		    				  pstmt_details.setNull(aa, java.sql.Types.DATE);
    				    		    			  else{
    				    		    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yy-MM-dd");
    				    		    				  pstmt_details.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
    				    		    			  }
//					    		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
					    		    		  }else if(eTmp.getType().equalsIgnoreCase("varchar2")){
					    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
					    		    				  pstmt_details.setNull(aa, java.sql.Types.VARCHAR);
    				    		    			  else
    				    		    				  pstmt_details.setString(aa,cellContentTmp);
//					    		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
					    		    		  }
					    		    	  }catch(Exception e){
					    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 3,eTmp.getName()+" 数据类型错误，无法解析", columnStrs);
					    		    		  Log4jInit.ncsLog.error("type=3; 数据类型错误，无法解析,"+ columnStrs +"message: " + e);
					    		    		  mark = -3;
					    		    		  parseExcelError = true;
					    		    		  e.printStackTrace();
//					    		    		  System.out.print("In insert catch:aa=" + aa + " type=" + eTmp.getType());
					    		    		  continue;
					    		    	  }
				    		    	  }
			    		    	  }
	
			    		    	  if(!parseExcelError){
//	    		    		    		 System.out.println("==Step 2: Execute insert to pstmtUpdate_details command");
	        		    				  try {
											pstmt_details.execute();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}	
			    		    	  }
		    		    	  }else{//update
//    		    		    	  System.out.println("==Step 3: In update: Read row "+ r + "\tCol.length=" + updateSqlsCol_details.length+ "\tPrepare update sql:");
    		    				  for(int aa=1; aa<=updateSqlsCol_details.length; aa++){
    			    		    	  ExcelTableMapping eTmp = mapping.get(updateSqlsCol_details[aa-1].toUpperCase());
//	    			    		      System.out.println("^^^^^^^^eTmp: Name=" + eTmp.getName() + "Type=" + eTmp.getType() + "Index=" + eTmp.getIndex());
				    		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
    			    		    	  if(updateSqlsCol_details[aa-1].toUpperCase().equals("SPID"))
				    		    		  cellContentTmp = spid;
    			    		    	  if(updateSqlsCol_details[aa-1].toUpperCase().equals("MPID"))
					    		    	  cellContentTmp = mpid;
//	    			    		    	  System.out.println("aa=" + aa + "cell content=" + cellContentTmp);
    			    		    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
    			    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 3,eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
    			    		    		  mark = -3;
    			    		    		  parseExcelError = true;
				    		    		  continue;
    			    		    	  }else{
    				    		    	  try{
    				    		    		  
    				    		    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
    				    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
        				    		    			  pstmtUpdate_details.setNull(aa, java.sql.Types.VARCHAR);
    				    		    			  else
    				    		    				  pstmtUpdate_details.setString(aa,cellContentTmp);    				    		    	
//	    				    		    		  System.out.print(aa+" varchar " + cellContentTmp + "\t");
    				    		    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
    				    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
        				    		    			  pstmtUpdate_details.setNull(aa, java.sql.Types.NUMERIC);
    				    		    			  else
    				    		    				  pstmtUpdate_details.setLong(aa,Long.parseLong(cellContentTmp));
//	    				    		    		  System.out.print(aa+" number " + cellContentTmp + "\t");
    				    		    	  	  }else if(eTmp.getType().equalsIgnoreCase("date")){
    				    		    	  		if(cellContentTmp == null || cellContentTmp.equals("null"))
      				    		    			  pstmtUpdate_details.setNull(aa, java.sql.Types.DATE);
  				    		    			  else{
  				    		    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    				    		    			  pstmtUpdate_details.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
  				    		    			  }
//	    				    		    		  System.out.print(aa+" Date " + cellContentTmp + "\t");
    				    		    		  }else{
//	    				    		    	      System.out.print("\taa=" + aa + " undefined data type:" +  eTmp.getType());
    				    		    		  }
    				    		    	  }catch(Exception ew){
    				    		    		  mark = -3;
    				    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 3,eTmp.getName()+" 数据类型错误，无法解析"+cellContentTmp, columnStrs);
    				    		    		  Log4jInit.ncsLog.error("type=3; 数据类型错误，无法解析,"+ columnStrs +" message: " + ew);
    				    		    		  ew.printStackTrace();
//	    				    		    		  System.out.print("In update catch:aa=" + aa + " type=" + eTmp.getType());
    				    		    		  parseExcelError = true;
    				    		    		  continue;
    				    		    	  }
    			    		    	  }
    		    		    	  }
//	    		    				  System.out.println("\n==Step 4: Execute update command\n");
    		    				  if(!parseExcelError){
//    		    		    		  System.out.println("==Step 2: Execute update to pstmtUpdate_details command");
    		    					  try {
										pstmtUpdate_details.execute();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
    		    				  }
    		    			  }	
//		    		    	  System.out.println("End of Row :" + r + "----------------------------------------");

		    		    	 }catch(Exception ew){
		    		    		  mark = -3;
		    		    		  ew.printStackTrace();
		    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 3," 数据类型错误，无法解析", columnStrs);
		    		    		  Log4jInit.ncsLog.error("type=3;  数据类型错误，无法解析,"+ columnStrs +" message: " + ew);
		    		    		  continue;
		    		    	}	
			    	  }//end of for row
		    	 	  pstmt_base.close();
  		    		  pstmtUpdate_base.close();
  		    		  pstmt_details.close();
  		    		  pstmtUpdate_details.close();
		    		  if(mark != -3)
		    			  mark  =1;
		    	  }//end of syslog policy import
		    }
		  }else
			  return -2; //sheet list is null
		  
//		  if(con!=null)
//		    con.close();
		}catch (Exception e) {
//	      	System.out.println("异常内容如下?");
	      	if(mark!= -3) mark=-1;
	      	e.printStackTrace();
	    }finally{
	    	try {
				if(con!=null)
				    con.close();
			} catch (SQLException e) {
			}
	    }
		if(hasDataToImport)
			return mark;
		else
			return -4;
	}
	
	/**
	 * 
	 * @param row
	 * @param errorMsgWb
	 * @param type 
	 * 			type=1 Non time frame policy message
	 * 			type = 2 Policy Period message
	 * 			type = 3 Syslog Pollicy Details
	 * @param errorMsg
	 * @param colTitles
	 */
	private void appendErrorMessage(Cell[] row, HSSFWorkbook errorMsgWb,int type, String errorMsg, String[] colTitles) {
		//set title style
		try{
		HSSFCellStyle titleStyle = errorMsgWb.createCellStyle();
		
		if(type == 2){
			HSSFSheet tfSheet = errorMsgWb.getSheet("TimeFrame_Policy_Export_ErrorMessage");
			if(tfSheet == null){
//				System.out.println("In create a new sheet");
				tfSheet = errorMsgWb.createSheet("TimeFrame_Policy_Export_ErrorMessage");//create a sheet
				HSSFRow titleRow = tfSheet.createRow(0);//create title row
				createCell(titleRow, 0, "Error Message", titleStyle);
				for(int c=0; c<colTitles.length; c++){
					//String temp = colTitles[c];
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
//				System.out.println("in read columns :" + row[c].getContents());
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
		}else if(type == 1){
			HSSFSheet tfSheet = errorMsgWb.getSheet("Non_TimeFrame_Policy_Export_ErrorMessage");
			if(tfSheet == null){
				tfSheet = errorMsgWb.createSheet("Non_TimeFrame_Policy_Export_ErrorMessage");//create a sheet
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
			
//			System.out.println("*****************before creating a new row. the total row number in error message excel is:" + tfSheet.getLastRowNum()) ;
			HSSFRow errorRow = tfSheet.createRow(tfSheet.getLastRowNum()+1);//create a new row

			createCell(errorRow,0,errorMsg, titleStyle);
			for(int c=0; c<colTitles.length; c++){
//				System.out.println("in read columns :" + row[c].getContents());
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
		}else if(type == 3){
			HSSFSheet tfSheet = errorMsgWb.getSheet("Syslog_Policy_Details_Export_ErrorMessage");
			if(tfSheet == null){
				tfSheet = errorMsgWb.createSheet("Syslog_Policy_Details_Export_ErrorMessage");//create a sheet
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
			
//			System.out.println("*****************before creating a new row. the total row number in error message excel is:" + tfSheet.getLastRowNum()) ;
			HSSFRow errorRow = tfSheet.createRow(tfSheet.getLastRowNum()+1);//create a new row

			createCell(errorRow,0,errorMsg, titleStyle);
			for(int c=0; c<colTitles.length; c++){
//				System.out.println("in read columns :" + row[c].getContents());
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
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private int insertUpdateSyslogPolicy(boolean ifUpdate, String[] sqlsCol, Sheet sheet, Map<String, ExcelTableMapping> mapping, 
			int r, String id, String idStr, boolean parseExcelError, HSSFWorkbook errorMsgWb,
			String[] columnStrs, PreparedStatement pstmt, PreparedStatement pstmtUpdate, String[] updateSqlsCol)
	{
		
		int mark = -1;
		if(!ifUpdate){
//  		  	System.out.println("\t\t\t==Step 1: In INSERT: Read row "+ r + "\tCol.length=" + sqlsCol.length+ "\tPrepare INSERT sql:");
			
			for(int aa=1; aa<=sqlsCol.length; aa++){
				ExcelTableMapping eTmp = mapping.get(sqlsCol[aa-1].toUpperCase());
		    String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
		    if(sqlsCol[aa-1].toUpperCase().equals(idStr))
		      cellContentTmp = id;
//			  System.out.println("iterator=" + aa + " ColumnName=" + sqlsCol[aa-1]+" cell content=" + cellContentTmp+"");
	    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
		    	mark  = -3;
		    	parseExcelError = true;
//		    	System.out.println("iterator=" + aa + " In error:"+ eTmp.getName() +" 字段不能为空");
		    	appendErrorMessage(sheet.getRow(r), errorMsgWb, 3 ,eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
		    	continue;
		      }else{
		    	
  		    	  try{
  		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
  		    			if(cellContentTmp == null || cellContentTmp.equals("null"))
  		    				pstmt.setNull(aa, java.sql.Types.NUMERIC);
		    			  else
		    				  pstmt.setLong(aa,Long.parseLong(cellContentTmp));
//  		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
  		    		  }else if(eTmp.getType().equalsIgnoreCase("DATE")){
  		    			if(cellContentTmp == null || cellContentTmp.equals("null"))
  		    				pstmt.setNull(aa, java.sql.Types.DATE);
		    			  else{
		    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yy-MM-dd");
		    				  pstmt.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
		    			  }
//  		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
  		    		  }else if(eTmp.getType().equalsIgnoreCase("varchar2")){
  		    			if(cellContentTmp == null || cellContentTmp.equals("null"))
  		    				pstmt.setNull(aa, java.sql.Types.VARCHAR);
		    			  else
		    				  pstmt.setString(aa,cellContentTmp);
//  		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
  		    		  }
  		    	  }catch(Exception e){
  		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 3,eTmp.getName()+" 数据类型错误，无法解析", columnStrs);
  		    		Log4jInit.ncsLog.error("type=3; 数据类型错误，无法解析,"+ columnStrs +" message: " + e);
  		    		  mark = -3;
  		    		  parseExcelError = true;
//  		    		  System.out.print("In insert catch:aa=" + aa + " type=" + eTmp.getType() +  " "+eTmp.getName()+" 数据类型错误，无法解析");
  		    		  continue;
  		    	  }
		    	  }
	    	  }

	    	  if(!parseExcelError){
//		    		  System.out.println("==Execute insert to command");
  				  try {
					pstmt.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
	    	  }
  	  }else{//update
//		    	  System.out.println("==Step 3: In update: Read row "+ r + "\tCol.length=" + updateSqlsCol.length+ "\tPrepare update sql:");
				  for(int aa=1; aa<=updateSqlsCol.length; aa++){
  		    	    	ExcelTableMapping eTmp = mapping.get(updateSqlsCol[aa-1].toUpperCase());
	  		    	
  		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
  		    	  
  		    	  if(updateSqlsCol[aa-1].toUpperCase().equals(idStr.toUpperCase()))
  		    		  cellContentTmp = id;
//  		    	System.out.println("iterator=" + aa + " ColumnName=" + updateSqlsCol[aa-1]+" cell content=" + cellContentTmp+"\n");
  		    	if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
  		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 3,eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
//  		    		  System.out.println("iterator=" + aa + " In error:"+ eTmp.getName() +" 字段不能为空\n");
  			    	  mark = -3;
  		    		  parseExcelError = true;
 		    		  continue;
  		    	  }else{
	    		    	  try{
	    		    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
	    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
	    		    				  pstmtUpdate.setNull(aa, java.sql.Types.VARCHAR);
	    		    			  else	    			    				  
	    		    				  pstmtUpdate.setString(aa,cellContentTmp);
//	    		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
	    		    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
	    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
	    		    				  pstmtUpdate.setNull(aa, java.sql.Types.NUMERIC);
	    			    			  else
	    			    				  pstmtUpdate.setLong(aa,Long.parseLong(cellContentTmp));
//	    		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
	    		    	  	  }else if(eTmp.getType().equalsIgnoreCase("date")){
	    		    	  		if(cellContentTmp == null || cellContentTmp.equals("null"))
	    		    	  			pstmtUpdate.setNull(aa, java.sql.Types.DATE);
	    		    			  else{
	    		    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    		    				  pstmtUpdate.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
	    		    			  }
//	    		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
	    		    		  }else{
//	    		    			  System.out.print("\taa=" + aa + " undefined data type:" +  eTmp.getType());
	    		    		  }
	    		    	  }catch(Exception ew){
	    		    		  mark = -3;
	    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 3,eTmp.getName()+" 数据类型错误，无法解析"+cellContentTmp, columnStrs);
	    		    		  Log4jInit.ncsLog.error("type=3;  数据类型错误，无法解析,"+ columnStrs +" message: " + ew);
    				    		    		  ew.printStackTrace();
//	    		    		  System.out.print("In insert catch:aa=" + aa + " type=" + eTmp.getType() +  " "+eTmp.getName()+" 数据类型错误，无法解析");
	      		    		  parseExcelError = true;
	    		    		  continue;
	    		    	  }
  		    	  }
		    	  }
				  if(!parseExcelError){
//					  System.out.println("==Step 4: Execute update command");
					  try {
						pstmtUpdate.execute();
					  } catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					  }
				  }
  	  }
		return 1;
	}
	
	
	
	private int insertUpdateNonTimeFramePolicy(boolean ifUpdate, String[] sqlsCol, Sheet sheet, Map<String, ExcelTableMapping> mapping, 
			int r, String id, String idStr, boolean parseExcelError, HSSFWorkbook errorMsgWb,
			String[] columnStrs, PreparedStatement pstmt, PreparedStatement pstmtUpdate, String[] updateSqlsCol, String tableName, String modid)
	{
		int mark = -1;
		int descCount=0;
		if(!ifUpdate){
//  		  	System.out.println("\t\t\t==Step 1: In INSERT: Read row "+ r + "\tCol.length=" + sqlsCol.length+ "\tPrepare INSERT sql:");
			
			for(int aa=1; aa<=sqlsCol.length; aa++){
				ExcelTableMapping eTmp = mapping.get(sqlsCol[aa-1].toUpperCase());
	  			if(sqlsCol[aa-1].equalsIgnoreCase("description")){
	  				if(tableName.equalsIgnoreCase("T_policy_base"))
	  					eTmp = mapping.get(sqlsCol[aa-1].toUpperCase());
	  				else if(tableName.equalsIgnoreCase("T_module_info_init"))
	  					eTmp = mapping.get(sqlsCol[aa-1].toUpperCase()+"1");
	  				else if(tableName.equalsIgnoreCase("t_event_type_init"))
	  					eTmp = mapping.get(sqlsCol[aa-1].toUpperCase()+"2");
		    	}
  			
		    String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
		    if(sqlsCol[aa-1].toUpperCase().equals(idStr))
		      cellContentTmp = id;
		    if(tableName.equalsIgnoreCase("t_event_type_init")){
		    	if(sqlsCol[aa-1].toUpperCase().equals("MODID"))
	    		  cellContentTmp = modid;
		    }
//			  System.out.println("iterator=" + aa + " ColumnName=" + sqlsCol[aa-1]+" cell content=" + cellContentTmp+"");
	    	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
		    	mark  = -3;
		    	parseExcelError = true;
//		    	System.out.println("iterator=" + aa + " In error:"+ eTmp.getName() +" 字段不能为空");
		    	appendErrorMessage(sheet.getRow(r), errorMsgWb, 1 ,eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
		    	continue;
		      }else{
		    	
//		    	  if(cellContentTmp.equalsIgnoreCase("null")){
////		    		  System.out.println("iterator=" + aa + " content is null");
//		    		  cellContentTmp="";
//		    	  }
  		    	  try{
  		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
  		    			if(cellContentTmp == null || cellContentTmp.equals("null"))
  		    				pstmt.setNull(aa, java.sql.Types.NUMERIC);
		    			  else	    			    				  
		    				  pstmt.setLong(aa,Long.parseLong(cellContentTmp));
//  		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
  		    		  }else if(eTmp.getType().equalsIgnoreCase("DATE")){
  		    			if(cellContentTmp == null || cellContentTmp.equals("null"))
  		    				pstmt.setNull(aa, java.sql.Types.DATE);
		    			  else{	    			    				  
		    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yy-MM-dd");
  		    			  pstmt.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
		    			  }
//  		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
  		    		  }else if(eTmp.getType().equalsIgnoreCase("varchar2")){
  		    			if(cellContentTmp == null || cellContentTmp.equals("null"))
  		    				pstmt.setNull(aa, java.sql.Types.VARCHAR);
		    			  else	    			    				  
		    				  pstmt.setString(aa,cellContentTmp);
//  		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
  		    		  }
  		    	  }catch(Exception e){
  		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1,eTmp.getName()+" 数据类型错误，无法解析", columnStrs);
  		    		Log4jInit.ncsLog.error(" 数据类型错误，无法解析,"+ columnStrs +"message: " + e);
  		    		  mark = -3;
  		    		  parseExcelError = true;
//  		    		  System.out.print("In insert catch:aa=" + aa + " type=" + eTmp.getType() +  " "+eTmp.getName()+" 数据类型错误，无法解析");
  		    		  continue;
  		    	  }
		    	  }
	    	  }

	    	  if(!parseExcelError){
//		    		  System.out.println("==Execute insert to command");
  				  try {
					pstmt.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
	    	  }
  	  }else{//update
//		    	  System.out.println("==Step 3: In update: Read row "+ r + "\tCol.length=" + updateSqlsCol.length+ "\tPrepare update sql:");
				  for(int aa=1; aa<=updateSqlsCol.length; aa++){
  		    	    	ExcelTableMapping eTmp = mapping.get(updateSqlsCol[aa-1].toUpperCase());
	  		    		if(updateSqlsCol[aa-1].equalsIgnoreCase("description")){
	  		  				if(tableName.equalsIgnoreCase("T_policy_base"))
	  		  					eTmp = mapping.get(updateSqlsCol[aa-1].toUpperCase());
	  		  				else if(tableName.equalsIgnoreCase("T_module_info_init"))
	  		  					eTmp = mapping.get(updateSqlsCol[aa-1].toUpperCase()+"1");
	  		  				else if(tableName.equalsIgnoreCase("t_event_type_init"))
	  		  					eTmp = mapping.get(updateSqlsCol[aa-1].toUpperCase()+"2");
	  			    	}
		    	
	  		  //  System.out.println(eTmp.getName()+","+eTmp.getIndex());
  		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
  		    	  
  		    	  if(updateSqlsCol[aa-1].toUpperCase().equals(idStr.toUpperCase()))
  		    		  cellContentTmp = id;
  		    	  if(tableName.equalsIgnoreCase("t_event_type_init")){
  			    	if(updateSqlsCol[aa-1].toUpperCase().equals("MODID"))
  		    		  cellContentTmp = modid;
  		    	  }
//  		    	System.out.println("iterator=" + aa + " ColumnName=" + updateSqlsCol[aa-1]+" cell content=" + cellContentTmp+"tablename="+tableName+"\n");
  		    	if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
  		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1,eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
//  		    		  System.out.println("iterator=" + aa + " In error:"+ eTmp.getName() +" 字段不能为空\n");
  			    	  mark = -3;
  		    		  parseExcelError = true;
 		    		  continue;
  		    	  }else{
	    		    	  try{
	    		    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
	    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
	    		    				  pstmtUpdate.setNull(aa, java.sql.Types.VARCHAR);
	    		    			  else	    			    				  
	    		    				  pstmtUpdate.setString(aa,cellContentTmp);
//	    		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
	    		    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
	    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
	    		    				  pstmtUpdate.setNull(aa, java.sql.Types.NUMERIC);
	    			    			  else	    			    				  
	    			    				  pstmtUpdate.setLong(aa,Long.parseLong(cellContentTmp));
//	    		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
	    		    	  	  }else if(eTmp.getType().equalsIgnoreCase("date")){
	    		    	  		if(cellContentTmp == null || cellContentTmp.equals("null"))
	    		    	  			pstmtUpdate.setNull(aa, java.sql.Types.DATE);
	    		    	  		else{			    				  
	    		    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    		    			  pstmtUpdate.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
	    		    	  		}
//	    		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
	    		    		  }else{
//	    		    			  System.out.print("\taa=" + aa + " undefined data type:" +  eTmp.getType());
	    		    		  }
	    		    	  }catch(Exception ew){
	    		    		  mark = -3;
	    		    		  appendErrorMessage(sheet.getRow(r), errorMsgWb, 1,eTmp.getName()+" 数据类型错误，无法解析", columnStrs);
	    		    		  Log4jInit.ncsLog.error(" 数据类型错误，无法解析,"+ columnStrs +"message: " + ew);
    				    		    		  ew.printStackTrace();
//	    		    		  System.out.print("In insert catch:aa=" + aa + " type=" + eTmp.getType() +  " "+eTmp.getName()+" 数据类型错误，无法解析");
	      		    		  parseExcelError = true;
	    		    		  continue;
	    		    	  }
  		    	  }
		    	  }
				  if(!parseExcelError){
//					  System.out.println("==Step 4: Execute update command");
					  try {
						pstmtUpdate.execute();
					  } catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					  }
				  }
  	  }
		return 1;
	}
	
	/**
	 * Init mapping only for table :T_module_info_init T_policy_base T_event_type_init T_policy_details
	 * @param rs
	 * @param columnStrs
	 * @param mapping
	 * @param tableName
	 * @return
	 */
	private int initTableMapping(ResultSetMetaData rs, String[] columnStrs, Map<String, ExcelTableMapping> mapping, String tableName){
		int count = 0;
		 try {
			int desc = 0;
			for(int i=1; i<=rs.getColumnCount(); i++){
				ExcelTableMapping et = new ExcelTableMapping();
				String name = rs.getColumnName(i);
				
				  if(!tableName.equalsIgnoreCase("T_policy_details") && !tableName.equalsIgnoreCase("POLICY_SYSLOG") ){
					  if(name.equalsIgnoreCase("mpid") || name.equalsIgnoreCase("eveid") || name.equalsIgnoreCase("modid"))
						  continue;
				  }
					String type = rs.getColumnTypeName(i);
					String isNull = rs.isNullable(i)==0?"N":"Y";
				  et.setName(name);
				  et.setType(type);
				  et.setNull(isNull.equalsIgnoreCase("Y")?true:false);
//				  System.out.println("tableName is:" + tableName + "\tFrom SQL: name=" + name + " \ttype=" + type + "\tisnull=" + isNull);
				  //find index in columnStrs
				  et.setIndex(-1);
				  for(int ii=0; ii<columnStrs.length; ii++){
					  if(columnStrs[ii].equalsIgnoreCase(name)){
						  if(name.equalsIgnoreCase("description") && !tableName.equalsIgnoreCase("T_policy_base")){
							  if(tableName.equalsIgnoreCase("T_policy_base")){
								  if(desc==0){
									  et.setIndex(ii);
									  break;
								  }									  
							  }else if(tableName.equalsIgnoreCase("T_module_info_init")){
								  if(desc==1){
									  et.setIndex(ii);
									  break;
								  }									  
							  }else if(tableName.equalsIgnoreCase("T_event_type_init")){
								  if(desc==2){
									  et.setIndex(ii);
									  break;
								  }									  
							  }
							  desc++;
						  }else{
							  et.setIndex(ii);
							  break;
						  }
					  }
				  }
				  if(et.getIndex() == -1){	//no corresponding column in excel. excel file error
//					  System.out.println("\t^^^^^^^^^no corresponding column in excel. excel file error ^column name in db:" + et.getName());
					  return -2;
				  }

				  if(name.equalsIgnoreCase("description") && !tableName.equalsIgnoreCase("T_policy_base")){
					  int desc_a=0;
					  if(tableName.equalsIgnoreCase("T_policy_base"))
						  desc_a = 0;
					  else if(tableName.equalsIgnoreCase("T_module_info_init"))
						  desc_a = 1;
					  else if(tableName.equalsIgnoreCase("T_event_type_init"))
						  desc_a = 2;
					  
//					  System.out.println("\tInfo to put to mapping" + "Key:"+ et.getName().toUpperCase()+String.valueOf(desc_a)+ " Type:" + type + " IsNull:"+ isNull + " index in excel=" + et.getIndex());
					  mapping.put(et.getName().toUpperCase()+String.valueOf(desc_a), et);
				  }else{
					  
//					  System.out.println("\tInfo to put to mapping" + "Key:"+ et.getName().toUpperCase()+ " Type:" + type + " IsNull:"+ isNull + " index in excel=" + et.getIndex());
				  	  mapping.put(et.getName().toUpperCase(), et);
				  }
				  count++;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		  return 1;
	}
	
	
	private static HSSFCell createCell(HSSFRow row, int index, String value)
	{
		HSSFCell cell = row.createCell((short)index);
		cell.setCellValue(value);
		return cell;
	}
	private static HSSFCell createCell(HSSFRow row, int index, String value, HSSFCellStyle style)
	{
		HSSFCell cell = row.createCell((short)index);
		cell.setCellValue(value);
		cell.setCellStyle(style);
		return cell;
	}
	
	
	public TModuleInfoInitDao getTModuleInfoInitDao() {
		return TModuleInfoInitDao;
	}


	public void setTModuleInfoInitDao(TModuleInfoInitDao moduleInfoInitDao) {
		TModuleInfoInitDao = moduleInfoInitDao;
	}


	public TEventTypeInitDao getTEventTypeInitDao() {
		return TEventTypeInitDao;
	}


	public void setTEventTypeInitDao(TEventTypeInitDao eventTypeInitDao) {
		TEventTypeInitDao = eventTypeInitDao;
	}


	public String getPageView() {
		return pageView;
	}


	public void setPageView(String pageView) {
		this.pageView = pageView;
	}


	public TPolicyPeriodDao getTPolicyPeriodDao() {
		return TPolicyPeriodDao;
	}


	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}


	public TPolicyBaseDao getTPolicyBaseDao() {
		return TPolicyBaseDao;
	}


	public void setTPolicyBaseDao(TPolicyBaseDao policyBaseDao) {
		TPolicyBaseDao = policyBaseDao;
	}


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public TPolicyDetailsDao getTPolicyDetailsDao() {
		return TPolicyDetailsDao;
	}


	public void setTPolicyDetailsDao(TPolicyDetailsDao policyDetailsDao) {
		TPolicyDetailsDao = policyDetailsDao;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
		jdbcTemplate = new SimpleJdbcTemplate(datasource);
	}

	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}

	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}

	public PolicySyslogDao getPolicySyslogDao() {
		return policySyslogDao;
	}

	public void setPolicySyslogDao(PolicySyslogDao policySyslogDao) {
		this.policySyslogDao = policySyslogDao;
	}
	
}
