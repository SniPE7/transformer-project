/**
 * 
 */
package com.ibm.ncs.web.baseinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
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

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dao.SnmpEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TEventOidInitDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TEventtypeInfoInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dao.VEventTypeDao;
import com.ibm.ncs.model.dao.VMfCateDevtypeDao;
import com.ibm.ncs.model.dao.VOidGroupDao;
import com.ibm.ncs.model.dao.VPerformParamDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.SnmpEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TEventOidInit;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TOidgroupDetailsInit;
import com.ibm.ncs.model.dto.TOidgroupInfoInit;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.excel.ExcelTODB;
import com.ibm.ncs.util.excel.ExcelTableMapping;

/**
 * @author root
 *
 */
public class ImportBaseInfoController implements Controller {

	
	String pageView;
	String message;

	VMfCateDevtypeDao VMfCateDevtypeDao;
	VOidGroupDao VOidGroupDao;
	VEventTypeDao VEventTypeDao;
	VPerformParamDao VPerformParamDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	TCategoryMapInitDao TCategoryMapInitDao;
	DefMibGrpDao defMibGrpDao;
	TDeviceTypeInitDao TDeviceTypeInitDao;
	TEventtypeInfoInitDao TEventtypeInfoInitDao;
	TModuleInfoInitDao TModuleInfoInitDao;
	TOidgroupInfoInitDao TOidgroupInfoInitDao;
	TOidgroupDetailsInitDao TOidgroupDetailsInitDao;
	TEventTypeInitDao TEventTypeInitDao;
	TEventOidInitDao TEventOidInitDao;
	SyslogEventsProcessNsDao syslogEventsProcessNsDao;
	SyslogEventsProcessDao syslogEventsProcessDao;
	SnmpEventsProcessDao snmpEventsProcessDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	
	DataSource datasource;
	protected SimpleJdbcTemplate jdbcTemplate;
	


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		message = "";
		
		Map<String, Object> model = new HashMap<String, Object>();
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
			message = "baseinfo.import.error";
			model.put("message", message);			
    		Log4jInit.ncsLog.error("InterruptedException in Export, message: " + e.getMessage());		
			return new ModelAndView(getPageView(),	"model", model);
		}

		if (fileList == null || fileList.size() == 0 || fileList.get(0).getClass().getName() == null) {
			message = "baseinfo.import.noFile";	
			model.put("message", message);
			return new ModelAndView(getPageView(), "model" , model);
		}
		Iterator fileItr = fileList.iterator();

		while (fileItr.hasNext()) {
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
				message = "baseinfo.import.noFile";	
				model.put("message", message);
				return new ModelAndView(getPageView(), "model", model);
			}

			String t_name = path.substring(path.lastIndexOf("\\") + 1);
			String[] ver = t_name.split("\\.");
			String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);
			int allowFlag = 0;
			int allowedExtCount = allowedExt.length;
			for (; allowFlag < allowedExtCount; allowFlag++) {
				if (allowedExt[allowFlag].equals(t_ext))
				break;
			}
			if (t_ext== null || allowFlag == allowedExtCount) {
				message = "baseinfo.import.invalidFileType";	
				model.put("message", message);
				return new ModelAndView(getPageView(), "model", model);
			}

			String filename=ver[0]+".xls";		 
			try {
				fileItem.write(new File(request.getRealPath("/") +"/uploadDir/"+ filename));
			  	String filePath = request.getRealPath("/")+ "/uploadDir/"+filename;
			  	
			  	//create a xls file for error message
			  	HSSFWorkbook wbMsg = new HSSFWorkbook();
				int marke = loadToDB(filePath,wbMsg);
				
				fileItem.delete();//remove the uploaded file.
				try {
	        		String delupload = request.getRealPath("/") +"/uploadDir";
					ExcelTODB.clearDir(delupload);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(marke<1){ //ok
					if(marke == -2){	//no corresponding columns in excel ok
						message ="baseinfo.import.missingColumn";
			        	model.put("message", message);					
			    		return new ModelAndView(getPageView(),	"model", model);
					}else if(marke == -3){//partially imported, and need to write error msg to another excel sheet
						message ="baseinfo.import.partialSuccess";
			        	model.put("message", message);		
			        	
			        	SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmssSSS");
						String timestampStr = sdf.format(new Date());
						System.out.println("timestampstr is "+timestampStr);
						
			        	String errorDir = "/logs/xls";
						String errorXlsFile = "/importBaseInfoError_"+timestampStr+".xls";
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
						message ="baseinfo.import.noDataToImport";
			        	model.put("message", message);		
			    		return new ModelAndView(getPageView(),	"model", model);
					}
		        	message ="baseinfo.import.failed";
		        	model.put("message", message);					
		    		return new ModelAndView(getPageView(),	"model", model);
				}else{//ok
				    message ="baseinfo.import.success";
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
		message ="baseinfo.import.failed";
    	model.put("message", message);									
		return new ModelAndView(getPageView(),	"model", model);
		
	}


	private int loadToDB(String filePath, HSSFWorkbook errorMsgWb) {
		
	    boolean hasDataToImport = false;
		int mark = -1;
		try{
			InputStream inStr = new FileInputStream(filePath);
			Workbook workBook = Workbook.getWorkbook(inStr);
			
			//get work sheet with the name 	  
		    Sheet[] sheetList = workBook.getSheets(); 
		    Connection con = datasource.getConnection();
		    Map<String, ExcelTableMapping> mapping_mf = new HashMap<String, ExcelTableMapping>();
		    Map<String, ExcelTableMapping> mapping_cate = new HashMap<String, ExcelTableMapping>();
		    Map<String, ExcelTableMapping> mapping_def = new HashMap<String, ExcelTableMapping>();
		    Map<String, ExcelTableMapping> mapping_mod = new HashMap<String, ExcelTableMapping>();
		    Map<String, ExcelTableMapping> mapping_oid = new HashMap<String, ExcelTableMapping>();
		    Map<String, ExcelTableMapping> mapping_snmp = new HashMap<String, ExcelTableMapping>();
		    Map<String, ExcelTableMapping> mapping_syslog= new HashMap<String, ExcelTableMapping>();
		    Map<String, ExcelTableMapping> mapping_syslogns = new HashMap<String, ExcelTableMapping>();
		    Map<String, ExcelTableMapping> mapping_VMfCateDevCate = new HashMap<String, ExcelTableMapping>(); //T_Category_Map_Init
		    Map<String, ExcelTableMapping> mapping_VMfCateDevMf = new HashMap<String, ExcelTableMapping>();//T_Manufacturer_Info_Init
		    Map<String, ExcelTableMapping> mapping_VMfCateDevDev = new HashMap<String, ExcelTableMapping>();//T_DEVICE_TYPE_INIT
		    Map<String, ExcelTableMapping> mapping_VEveEve = new HashMap<String, ExcelTableMapping>();//T_EVENT_TYPE_INIT
		    Map<String, ExcelTableMapping> mapping_VEveMod = new HashMap<String, ExcelTableMapping>();//T_MODULE_INFO_INIT
		    Map<String, ExcelTableMapping> mapping_VOidOidInfo = new HashMap<String, ExcelTableMapping>();//T_OIDGROUP_INFO_INIT
		    Map<String, ExcelTableMapping> mapping_VOidOidDetails = new HashMap<String, ExcelTableMapping>();//T_OIDGROUP_DETAILS_INIT
		    Map<String, ExcelTableMapping> mapping_VPerfEveoid = new HashMap<String, ExcelTableMapping>();//T_EVENT_OID_INIT
		    Map<String, ExcelTableMapping> mapping_VPerfOidInfo = new HashMap<String, ExcelTableMapping>();//T_OIDGROUP_INFO_INIT
		    Map<String, ExcelTableMapping> mapping_VPerfEve = new HashMap<String, ExcelTableMapping>();//T_EVENT_TYPE_INIT
		    Map<String, ExcelTableMapping> mapping_VPerfDev = new HashMap<String, ExcelTableMapping>();//T_DEVICE_TYPE_INIT
		    Map<String, ExcelTableMapping> mapping_period = new HashMap<String, ExcelTableMapping>();//T_POLICY_PERIOD
		    if(sheetList != null){
		    	for(int i=0; i<sheetList.length; i++){//for each row in excel
		    	  Sheet sheet = sheetList[i];
	    		  String[] columnStrs = null;
	    		  if(sheet.getName().equalsIgnoreCase(TManufacturerInfoInitDao.getTableName()) 
	    				  || sheet.getName().equalsIgnoreCase(TCategoryMapInitDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(defMibGrpDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(VMfCateDevtypeDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(VEventTypeDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(VOidGroupDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(VPerformParamDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(TModuleInfoInitDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(TOidgroupInfoInitDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(snmpEventsProcessDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(syslogEventsProcessDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(syslogEventsProcessNsDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(TPolicyPeriodDao.getTableName())
	    				  ){
	    			  hasDataToImport = true;
	    			  columnStrs = new String[sheet.getColumns()];
	    		      for(int c=0; c<sheet.getColumns(); c++){
	    		    	  columnStrs[c] = sheet.getCell(c, 0).getContents();
//	    		    	  System.out.println("sheet.getName():  " + sheet.getName() + " excel column index:"+c+":"+columnStrs[c]);
	    		      }	
	    		  }else{
	    			  continue;
	    		  }
		    		  
	    		  //validate column mapping
	    		  int count=0;
    		      if(sheet.getName().equalsIgnoreCase(TManufacturerInfoInitDao.getTableName())){ //deal with non time frame policy "NonTimeFrame_Policy_Details"		
    		    	  /**Using meta data*/
	    			  Statement descPstmt = con.createStatement();
	    			  ResultSet rs = descPstmt.executeQuery("select * from "+TManufacturerInfoInitDao.getTableName());
	    			  ResultSetMetaData rsmeta = rs.getMetaData();
	    			  //init mapping in while
	    			  int a = initTableMapping(rsmeta, columnStrs,mapping_mf, "T_Manufacturer_Info_Init");
	    			  rs.close();
	    			  descPstmt.close();
	    			  if(a<0){
	    				  return a;
	    			  }
    		      }else if(sheet.getName().equalsIgnoreCase(TCategoryMapInitDao.getTableName())){ //deal with non time frame policy "NonTimeFrame_Policy_Details"		
    		    	  /**Using meta data*/
	    			  Statement descPstmt = con.createStatement();
	    			  ResultSet rs = descPstmt.executeQuery("select * from "+TCategoryMapInitDao.getTableName());
	    			  ResultSetMetaData rsmeta = rs.getMetaData();
	    			  //init mapping in while
	    			  int a = initTableMapping(rsmeta, columnStrs,mapping_cate, "T_CATEGORY_MAP_INIT");
	    			  rs.close();
	    			  descPstmt.close();
	    			  if(a<0){
	    				  return a;
	    			  }
    		      }else if(sheet.getName().equalsIgnoreCase(defMibGrpDao.getTableName())){ //deal with non time frame policy "NonTimeFrame_Policy_Details"		
    		    	  /**Using meta data*/
	    			  Statement descPstmt = con.createStatement();
	    			  ResultSet rs = descPstmt.executeQuery("select * from "+defMibGrpDao.getTableName());
	    			  ResultSetMetaData rsmeta = rs.getMetaData();
	    			  //init mapping in while
	    			  int a = initTableMapping(rsmeta, columnStrs,mapping_def, "DEF_MIB_GRP");
	    			  rs.close();
	    			  descPstmt.close();
	    			  if(a<0){
	    				  return a;
	    			  }
    		      }else if(sheet.getName().equalsIgnoreCase(TModuleInfoInitDao.getTableName())){ //deal with non time frame policy "NonTimeFrame_Policy_Details"		
    		    	  /**Using meta data*/
	    			  Statement descPstmt = con.createStatement();
	    			  ResultSet rs = descPstmt.executeQuery("select * from "+TModuleInfoInitDao.getTableName());
	    			  ResultSetMetaData rsmeta = rs.getMetaData();
	    			  //init mapping in while
	    			  int a = initTableMapping(rsmeta, columnStrs,mapping_mod, "T_MODULE_INFO_INIT");
	    			  rs.close();
	    			  descPstmt.close();
	    			  if(a<0){
	    				  return a;
	    			  }
    		      }else if(sheet.getName().equalsIgnoreCase(TOidgroupInfoInitDao.getTableName())){ //deal with non time frame policy "NonTimeFrame_Policy_Details"		
    		    	  /**Using meta data*/
	    			  Statement descPstmt = con.createStatement();
	    			  ResultSet rs = descPstmt.executeQuery("select * from "+TOidgroupInfoInitDao.getTableName());
	    			  ResultSetMetaData rsmeta = rs.getMetaData();
	    			  //init mapping in while
	    			  int a = initTableMapping(rsmeta, columnStrs,mapping_oid, "T_OIDGROUP_INFO_INIT");
	    			  rs.close();
	    			  descPstmt.close();
	    			  if(a<0){
	    				  return a;
	    			  }
    		      }else if(sheet.getName().equalsIgnoreCase(syslogEventsProcessNsDao.getTableName())){ //deal with non time frame policy "NonTimeFrame_Policy_Details"		
    		    	  /**Using meta data*/
	    			  Statement descPstmt = con.createStatement();
	    			  ResultSet rs = descPstmt.executeQuery("select * from "+syslogEventsProcessNsDao.getTableName());
	    			  ResultSetMetaData rsmeta = rs.getMetaData();
	    			  //init mapping in while
	    			  int a = initTableMapping(rsmeta, columnStrs,mapping_syslogns, "SYSLOG_EVENTS_PROCESS_NS");
	    			  rs.close();
	    			  descPstmt.close();
	    			  if(a<0){
	    				  return a;
	    			  }
    		      }else if(sheet.getName().equalsIgnoreCase(syslogEventsProcessDao.getTableName())){ //deal with non time frame policy "NonTimeFrame_Policy_Details"		
    		    	  /**Using meta data*/
	    			  Statement descPstmt = con.createStatement();
	    			  ResultSet rs = descPstmt.executeQuery("select * from "+syslogEventsProcessDao.getTableName());
	    			  ResultSetMetaData rsmeta = rs.getMetaData();
	    			  //init mapping in while
	    			  int a = initTableMappingSyslog(rsmeta, columnStrs,mapping_syslog, "SYSLOG_EVENTS_PROCESS");
    		    	  System.out.println("\n\n*************************Testing Meta Data End************************\n\n");
	    			  rs.close();
	    			  descPstmt.close();
	    			  if(a<0){
	    				  return a;
	    			  }

    		      }else if(sheet.getName().equalsIgnoreCase(snmpEventsProcessDao.getTableName())){ //deal with non time frame policy "NonTimeFrame_Policy_Details"		
    		    	  /**Using meta data*/
	    			  Statement descPstmt = con.createStatement();
	    			  ResultSet rs = descPstmt.executeQuery("select * from "+snmpEventsProcessDao.getTableName());
	    			  ResultSetMetaData rsmeta = rs.getMetaData();
	    			  //init mapping in while
	    			  int a = initTableMapping(rsmeta, columnStrs,mapping_snmp, "SNMP_EVENTS_PROCESS");
	    			  rs.close();
	    			  descPstmt.close();
	    			  if(a<0){
	    				  return a;
	    			  }
    		      }else if(sheet.getName().equalsIgnoreCase(VMfCateDevtypeDao.getTableName())){ //deal with V_MF_CATE_DEVTYPE    		    	  
	    			  Statement descPstmt_cate=con.createStatement();
	    			  ResultSet rs_cate = descPstmt_cate.executeQuery("select * from "+TCategoryMapInitDao.getTableName());
	    			  ResultSetMetaData rsmeta_cate = rs_cate.getMetaData();
	    			  //init mapping in while
	    			  int a = initViewMapping(rsmeta_cate,columnStrs,mapping_VMfCateDevCate, VMfCateDevtypeDao.getTableName(), TCategoryMapInitDao.getTableName());
	    			  rs_cate.close();
	    			  descPstmt_cate.close();
	    			  if(a<0){
	    				  return a;
	    			  }
	    			  
	    			  Statement descPstmt_mf=con.createStatement();
	    			  ResultSet rs_mf = descPstmt_mf.executeQuery("select * from "+TManufacturerInfoInitDao.getTableName());
	    			  ResultSetMetaData rsmeta_mf = rs_mf.getMetaData();
	    			  //init mapping in while
	    			  a = initViewMapping(rsmeta_mf,columnStrs,mapping_VMfCateDevMf, VMfCateDevtypeDao.getTableName(), TManufacturerInfoInitDao.getTableName());
	    			  rs_mf.close();
	    			  descPstmt_mf.close();
	    			  if(a<0){
	    				  return a;
	    			  }
	    			  
	    			  Statement descPstmt_dev=con.createStatement();
	    			  ResultSet rs_dev = descPstmt_dev.executeQuery("select * from "+TDeviceTypeInitDao.getTableName());
	    			  ResultSetMetaData rsmeta_dev = rs_dev.getMetaData();
	    			  //init mapping in while
	    			  a = initViewMapping(rsmeta_dev,columnStrs,mapping_VMfCateDevDev, VMfCateDevtypeDao.getTableName(), TDeviceTypeInitDao.getTableName());
	    			  rs_dev.close();
	    			  descPstmt_dev.close();
	    			  if(a<0){
	    				  return a;
	    			  }
    		      }else if(sheet.getName().equalsIgnoreCase(VEventTypeDao.getTableName())){ //deal with VEventType	   
    		    	  Statement descPstmt_eve=con.createStatement();
	    			  ResultSet rs_eve = descPstmt_eve.executeQuery("select * from "+TEventTypeInitDao.getTableName());
	    			  ResultSetMetaData rsmeta_eve = rs_eve.getMetaData();
	    			  //init mapping in while
	    			  int a = initViewMapping(rsmeta_eve,columnStrs,mapping_VEveEve, VEventTypeDao.getTableName(), TEventTypeInitDao.getTableName());
	    			  rs_eve.close();
	    			  descPstmt_eve.close();
	    			  if(a<0){
	    				  return a;
	    			  }
	    			  
	    			  Statement descPstmt_mod=con.createStatement();
	    			  ResultSet rs_mod = descPstmt_mod.executeQuery("select * from "+TModuleInfoInitDao.getTableName());
	    			  ResultSetMetaData rsmeta_mod = rs_mod.getMetaData();
	    			  //init mapping in while
	    			  a = initViewMapping(rsmeta_mod,columnStrs,mapping_VEveMod, VEventTypeDao.getTableName(), TModuleInfoInitDao.getTableName());
	    			  rs_mod.close();
	    			  descPstmt_mod.close();
	    			  if(a<0){
	    				  return a;
	    			  }	    			  
    		      }else if(sheet.getName().equalsIgnoreCase(VOidGroupDao.getTableName())){ //deal with VOidGroupDao	     
    		    	  Statement descPstmt_oidInfo=con.createStatement();
	    			  ResultSet rs_oidInfo = descPstmt_oidInfo.executeQuery("select * from "+TOidgroupInfoInitDao.getTableName());
	    			  ResultSetMetaData rsmeta_oidInfo = rs_oidInfo.getMetaData();
	    			  //init mapping in while
	    			  int a = initViewMapping(rsmeta_oidInfo,columnStrs,mapping_VOidOidInfo, VOidGroupDao.getTableName(), TOidgroupInfoInitDao.getTableName());
	    			  rs_oidInfo.close();
	    			  descPstmt_oidInfo.close();
	    			  if(a<0){
	    				  return a;
	    			  }
	    			  
	    			  Statement descPstmt_oidDetails=con.createStatement();
	    			  ResultSet rs_oidDetails = descPstmt_oidDetails.executeQuery("select * from "+TOidgroupDetailsInitDao.getTableName());
	    			  ResultSetMetaData rsmeta_oidDetails = rs_oidDetails.getMetaData();
	    			  //init mapping in while
	    			  a = initViewMapping(rsmeta_oidDetails,columnStrs,mapping_VOidOidDetails, VOidGroupDao.getTableName(), TOidgroupDetailsInitDao.getTableName());
	    			  rs_oidDetails.close();
	    			  descPstmt_oidDetails.close();
	    			  if(a<0){
	    				  return a;
	    			  }	    			  
    		      }else if(sheet.getName().equalsIgnoreCase(VPerformParamDao.getTableName())){ //deal with VPerformParamDao	     
    		    	  Statement descPstmt_oidInfo=con.createStatement();
	    			  ResultSet rs_oidInfo = descPstmt_oidInfo.executeQuery("select * from "+TEventTypeInitDao.getTableName());
	    			  ResultSetMetaData rsmeta_oidInfo = rs_oidInfo.getMetaData();
	    			  //init mapping in while
	    			  int a = initViewMapping(rsmeta_oidInfo,columnStrs,mapping_VPerfEve, VPerformParamDao.getTableName(), TEventTypeInitDao.getTableName());
	    			  rs_oidInfo.close();
	    			  descPstmt_oidInfo.close();
	    			  if(a<0){
	    				  return a;
	    			  }
	    			  
	    			  Statement descPstmt_oidDetails=con.createStatement();
	    			  ResultSet rs_oidDetails = descPstmt_oidDetails.executeQuery("select * from "+TOidgroupInfoInitDao.getTableName());
	    			  ResultSetMetaData rsmeta_oidDetails = rs_oidDetails.getMetaData();
	    			  //init mapping in while
	    			  a = initViewMapping(rsmeta_oidDetails,columnStrs,mapping_VPerfOidInfo, VPerformParamDao.getTableName(), TOidgroupInfoInitDao.getTableName());
	    			  rs_oidDetails.close();
	    			  descPstmt_oidDetails.close();
	    			  if(a<0){
	    				  return a;
	    			  }	    
	    			  
	    			  Statement descPstmt_eveoid=con.createStatement();
	    			  ResultSet rs_eveoid = descPstmt_eveoid.executeQuery("select * from "+TEventOidInitDao.getTableName());
	    			  ResultSetMetaData rsmeta_eveoid = rs_eveoid.getMetaData();
	    			  //init mapping in while
	    			  a = initViewMapping(rsmeta_eveoid,columnStrs,mapping_VPerfEveoid, VPerformParamDao.getTableName(), TEventOidInitDao.getTableName());
	    			  rs_eveoid.close();
	    			  descPstmt_eveoid.close();
	    			  if(a<0){
	    				  return a;
	    			  }	   
	    			  
	    			  Statement descPstmt_dev=con.createStatement();
	    			  ResultSet rs_dev = descPstmt_dev.executeQuery("select * from "+TDeviceTypeInitDao.getTableName());
	    			  ResultSetMetaData rsmeta_dev = rs_dev.getMetaData();
	    			  //init mapping in while
	    			  a = initViewMapping(rsmeta_dev,columnStrs,mapping_VPerfDev, VPerformParamDao.getTableName(), TDeviceTypeInitDao.getTableName());
	    			  rs_dev.close();
	    			  descPstmt_dev.close();
	    			  if(a<0){
	    				  return a;
	    			  }	   
    		      }else if(sheet.getName().equalsIgnoreCase(TPolicyPeriodDao.getTableName())){ //deal with t_policy_period	
    		    	  /**Using meta data*/
	    			  Statement descPstmt = con.createStatement();
	    			  ResultSet rs = descPstmt.executeQuery("select * from "+TPolicyPeriodDao.getTableName());
	    			  ResultSetMetaData rsmeta = rs.getMetaData();
	    			  //init mapping in while
	    			  int a = initTableMappingPeriod(rsmeta, columnStrs,mapping_period, "T_POLICY_PERIOD");
	    			  rs.close();
	    			  descPstmt.close();
	    			  if(a<0){
	    				  return a;
	    			  }
    		      }
    		      //end of validate column mapping
//    		      System.out.println("\t\n\t**************end of validating column mapping" + sheet.getName());    		      
		    	}

  		      for(int i=0; i<sheetList.length; i++){//for each row in excel
	    		  Sheet sheet = sheetList[i];
	    		  String[] columnStrs = null;
	    		  if(sheet.getName().equalsIgnoreCase(TManufacturerInfoInitDao.getTableName()) 
	    				  || sheet.getName().equalsIgnoreCase(TCategoryMapInitDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(defMibGrpDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(VMfCateDevtypeDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(VEventTypeDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(VOidGroupDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(VPerformParamDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(TModuleInfoInitDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(TOidgroupInfoInitDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(snmpEventsProcessDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(syslogEventsProcessDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(syslogEventsProcessNsDao.getTableName())
	    				  || sheet.getName().equalsIgnoreCase(TPolicyPeriodDao.getTableName())
	    			){
	    			  hasDataToImport = true;
	    			  columnStrs = new String[sheet.getColumns()];
	    			  for(int c=0; c<sheet.getColumns(); c++){
	    		    	  columnStrs[c] = sheet.getCell(c, 0).getContents();
	    		      }	
	    		  }else{
	    			  continue;
	    		  }
	    		      
	    		  //import excel to db tables
	    		  int markTemp = -1;
	    		  if(sheet.getName().equalsIgnoreCase(TManufacturerInfoInitDao.getTableName())){	 
	    			  markTemp = importTManufacturerInfoInit(sheet, errorMsgWb, columnStrs, mapping_mf, con);
	    			 if(markTemp == -1)
	    				 return markTemp;
	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;
	    		  }else if(sheet.getName().equalsIgnoreCase(TCategoryMapInitDao.getTableName())){
	    			  markTemp = importTCategoryMapInit(sheet, errorMsgWb, columnStrs, mapping_cate, con);
//		    			 System.out.println("return value from importTCategoryMapInit" + mark);
	    			  if(markTemp == -1)
		    				 return markTemp;

	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;
	    	 	  }else if(sheet.getName().equalsIgnoreCase(defMibGrpDao.getTableName())){
	    	 		 markTemp = importdefMibGrp(sheet, errorMsgWb, columnStrs, mapping_def, con);
//	    			 System.out.println("return value from importdefMibGrp" + mark);
	    	 		if(markTemp == -1)
	    				 return markTemp;
	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;
		    	 }else if(sheet.getName().equalsIgnoreCase(TModuleInfoInitDao.getTableName())){
	    	 		 markTemp = importTModuleInfoInit(sheet, errorMsgWb, columnStrs, mapping_mod, con);
//	    			 System.out.println("return value from importdefMibGrp" + mark);
	    	 		if(markTemp == -1)
	    				 return markTemp;
	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;
		    	 }else if(sheet.getName().equalsIgnoreCase(TOidgroupInfoInitDao.getTableName())){
	    	 		 markTemp = importTOIDGroupInfo(sheet, errorMsgWb, columnStrs, mapping_oid, con);
//	    			 System.out.println("return value from importdefMibGrp" + mark);
	    	 		if(markTemp == -1)
	    				 return markTemp;
	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;
		    	 }else if(sheet.getName().equalsIgnoreCase(snmpEventsProcessDao.getTableName())){
	    	 		 markTemp = importSNMPEventsProcess(sheet, errorMsgWb, columnStrs, mapping_snmp, con);
//	    			 System.out.println("return value from importdefMibGrp" + mark);
	    	 		if(markTemp == -1)
	    				 return markTemp;
	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;
		    	 }else if(sheet.getName().equalsIgnoreCase(syslogEventsProcessDao.getTableName())){
	    	 		 markTemp = importSyslogEveProcess(sheet, errorMsgWb, columnStrs, mapping_syslog, con,syslogEventsProcessDao.getTableName());
//	    			 System.out.println("return value from importdefMibGrp" + mark);
	    	 		if(markTemp == -1)
	    				 return markTemp;
	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;
		    	 }else if(sheet.getName().equalsIgnoreCase(syslogEventsProcessNsDao.getTableName())){
	    	 		 markTemp = importSyslogEveProcess(sheet, errorMsgWb, columnStrs, mapping_syslogns, con, syslogEventsProcessNsDao.getTableName());
//	    			 System.out.println("return value from importdefMibGrp" + mark);
	    	 		if(markTemp == -1)
	    				 return markTemp;
	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;
		    	 }else if(sheet.getName().equalsIgnoreCase(VMfCateDevtypeDao.getTableName())){
		    		 markTemp = importVMfCateDevtype(sheet, errorMsgWb, columnStrs, mapping_VMfCateDevCate, mapping_VMfCateDevMf, mapping_VMfCateDevDev, con);
//	    	 		 System.out.println("return value from importVMfCateDevtype" + mark);
	    	 		 if(markTemp == -1)
	    				 return markTemp;
	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;
		    	 }else if(sheet.getName().equalsIgnoreCase(VEventTypeDao.getTableName())){
		    		 markTemp = importVEventType(sheet, errorMsgWb, columnStrs, mapping_VEveEve, mapping_VEveMod, con);
//	    	 		 System.out.println("return value from VEventTypeDao" + mark);
	    	 		if(markTemp == -1)
	    				 return markTemp;
	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;
		    	 }else if(sheet.getName().equalsIgnoreCase(VOidGroupDao.getTableName())){

		    		 markTemp = importVOidGroup(sheet, errorMsgWb, columnStrs, mapping_VOidOidInfo, mapping_VOidOidDetails, con);
//	    	 		 System.out.println("return value from VOidGroupDao" + mark);
	    	 		if(markTemp == -1)
	    				 return markTemp;
	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;
		    	 }else if(sheet.getName().equalsIgnoreCase(VPerformParamDao.getTableName())){
		    		 markTemp = importVPerformParam(sheet, errorMsgWb, columnStrs, mapping_VPerfEveoid, 
		 		    		mapping_VPerfOidInfo,mapping_VPerfEve,mapping_VPerfDev, con);
//	    	 		 System.out.println("return value from VOidGroupDao" + mark);
	    	 		if(markTemp == -1)
	    				 return markTemp;
	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;
		    	 }else if(sheet.getName().equalsIgnoreCase(TPolicyPeriodDao.getTableName())){
	    	 		 markTemp = importPeriodDefault(sheet, errorMsgWb, columnStrs, mapping_period, con,TPolicyPeriodDao.getTableName());
//	    			 System.out.println("return value from importdefMibGrp" + mark);
	    	 		if(markTemp == -1)
	    				 return markTemp;
	    			 if(markTemp == -3)
	    				 mark = markTemp;
	    			 else if(mark != -3)
	    				 mark = 1;		    	 
		    	 }//end of if
	    		 //end of import excel to db tables
  		      }//END OF for each row in excel
		    }else
				  return -2; //sheet list is null
			if(con!=null)
			   con.close();
		}catch (Exception e) {
	      	if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入失败:" + e.getMessage());
	    }
		
		if(hasDataToImport)
			return mark;
		else
			return -4;		
	}
	
	/**
	 * 
	 * @param sheet
	 * @param errorMsgWb
	 * @param columnStrs
	 * @param mapping_period
	 * @param con
	 * @param tableName
	 * @return
	 */
	private int importPeriodDefault(Sheet sheet, HSSFWorkbook errorMsgWb,
			String[] columnStrs, Map<String, ExcelTableMapping> mapping,
			Connection con, String tableName) {

		int mark=1;
		try{
			String sqls_del="delete from "+ TPolicyPeriodDao.getTableName()+" where defaultflag=1";
			 PreparedStatement pstmt_del=con.prepareStatement(sqls_del);
			 String sqls_insert="insert into "+ TPolicyPeriodDao.getTableName() + " ( PPID, PPNAME, START_TIME, END_TIME, DESCRIPTION, WORKDAY, ENABLED, DEFAULTFLAG, S1, S2, S3, S4, S5, S6, S7, E1, E2, E3, E4, E5, E6, E7 ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			 PreparedStatement pstmt_insert=con.prepareStatement(sqls_insert);	     
			 String sqls_update="Update "+ TPolicyPeriodDao.getTableName() + " SET  PPNAME = ?, START_TIME = ?, END_TIME = ?, DESCRIPTION = ?, WORKDAY = ?, ENABLED = ?, DEFAULTFLAG = ?, S1 = ?, S2 = ?, S3 = ?, S4 = ?, S5 = ?, S6 = ?, S7 = ?, E1 = ?, E2 = ?, E3 = ?, E4 = ?, E5 = ?, E6 = ?, E7 = ? where PPID=?";
			 PreparedStatement pstmt_update=con.prepareStatement(sqls_update);
			 String[] sqlsCol_insert= new String[]{"PPID","PPNAME","START_TIME","END_TIME","DESCRIPTION","WORKDAY","ENABLED","DEFAULTFLAG","S1","S2","S3","S4","S5","S6","S7","E1","E2","E3","E4","E5","E6","E7"};	
		     String[] sqlsCol_update= new String[]{"PPNAME","START_TIME","END_TIME","DESCRIPTION","WORKDAY","ENABLED","DEFAULTFLAG","S1","S2","S3","S4","S5","S6","S7","E1","E2","E3","E4","E5","E6","E7","PPID"};	
	 	      
//	 	     System.out.println("before delete:" + defMibGrpDao.findAll().size());
	 	     pstmt_del.execute();
//	 	     System.out.println("after delete:" + defMibGrpDao.findAll().size());
	 	    
	 	     long ppid = 0;
	 	     for(int r=1; r<sheet.getRows(); r++){
	 	    	boolean parseExcelError = false;
	 	    	boolean ifUpdate = false;
	 	    	try{
	 	    		 ppid = Long.parseLong(sheet.getCell(mapping.get("PPID").getIndex(), r).getContents());
//		 	    		System.out.println("Import OIDGroup Info init, opid=" + opid + "\tindex=" +mapping.get("OPID").getIndex()) ;
	 	    	}catch(Exception ew){
	 	    		 ew.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage(TPolicyPeriodDao.getTableName(),sheet.getRow(r), errorMsgWb, "PPID 数据类型错误，无法解析", columnStrs);
		    		  continue;
		    	}
	 	    	//check whether to insert or update t_policy_base
	 	    	TPolicyPeriod tmfList = TPolicyPeriodDao.findByPrimaryKey(ppid);
		    	  
		    		  if(tmfList != null){//update
		    			  ifUpdate = true;
		    		  }
		    	  
		    	  int markTmp = insertUpdateTable(TPolicyPeriodDao.getTableName(),ifUpdate,sqlsCol_insert, sheet, mapping, 
		    				r, parseExcelError, errorMsgWb,
		    				columnStrs, pstmt_insert, pstmt_update,sqlsCol_update);
		    	  if(markTmp == -3){
		    		  mark = -3;
		    		  continue;
		    	  }else if(markTmp == -1){
		    		  pstmt_insert.close();
		    		  pstmt_update.close();
		    		  pstmt_del.close();
		    		  con.close();
		  	      	  return markTmp;
		    	  }
//		    	  System.out.println("in for i=" + r+ " delete:" + defMibGrpDao.findAll().size());
	 	     }//end of for row
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheet.getName() + "失败:" + e.getMessage());
		}
	     return mark;
	
	}


	private int importSyslogEveProcess(Sheet sheet, HSSFWorkbook errorMsgWb,
			String[] columnStrs, Map<String, ExcelTableMapping> mapping, 
			Connection con,String tabName) {
		int mark=1;
		try{
	         String sqls_del="";
			 PreparedStatement pstmt_del=null;
			 String sqls_insert="";
			 PreparedStatement pstmt=null;
			 String sqls_update="";
			 PreparedStatement pstmtUpdate=null;
			 String[] sqlsCol= new String[]{"MARK","VARLIST","BTIMELIST","ETIMELIST","FILTERFLAG1","FILTERFLAG2","SEVERITY1","SEVERITY2","PORT","NOTCAREFLAG","TYPE","EVENTTYPE","SUBEVENTTYPE","ALERTGROUP","ALERTKEY","SUMMARYCN","PROCESSSUGGEST","STATUS","ATTENTIONFLAG","EVENTS","MANUFACTURE","ORIGEVENT"};	
		     String[] updateSqlsCol= new String[]{"VARLIST","BTIMELIST","ETIMELIST","FILTERFLAG1","FILTERFLAG2","SEVERITY1","SEVERITY2","PORT","NOTCAREFLAG","TYPE","EVENTTYPE","SUBEVENTTYPE","ALERTGROUP","ALERTKEY","SUMMARYCN","PROCESSSUGGEST","STATUS","ATTENTIONFLAG","EVENTS","ORIGEVENT","MARK","MANUFACTURE"};
	   	     
		     if(tabName.equalsIgnoreCase(syslogEventsProcessDao.getTableName())){
		    	 sqls_del="delete from "+ syslogEventsProcessDao.getTableName();
				 pstmt_del=con.prepareStatement(sqls_del);
				 sqls_insert="insert into "+ syslogEventsProcessDao.getTableName() + " ( MARK , VARLIST , BTIMELIST , ETIMELIST , FILTERFLAG1 , FILTERFLAG2 , SEVERITY1 , SEVERITY2 , PORT , NOTCAREFLAG , TYPE , EVENTTYPE , SUBEVENTTYPE , ALERTGROUP , ALERTKEY , SUMMARYCN , PROCESSSUGGEST , STATUS, ATTENTIONFLAG , EVENTS , MANUFACTURE , ORIGEVENT )"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				 pstmt=con.prepareStatement(sqls_insert);	     
				 sqls_update="Update "+ syslogEventsProcessDao.getTableName() + " set VARLIST=?,BTIMELIST=?, ETIMELIST=?, FILTERFLAG1=?, FILTERFLAG2=?, SEVERITY1=?, SEVERITY2=?, PORT=?, NOTCAREFLAG=?, TYPE=?, EVENTTYPE=?, SUBEVENTTYPE=?, ALERTGROUP=?, ALERTKEY=?, SUMMARYCN=?, PROCESSSUGGEST=?, STATUS=?, ATTENTIONFLAG=?, EVENTS=?,ORIGEVENT=? where Mark=? and MANUFACTURE=?";
				 pstmtUpdate=con.prepareStatement(sqls_update);
 
		     }else if(tabName.equalsIgnoreCase(syslogEventsProcessNsDao.getTableName())){
		    	 sqls_del="delete from "+ syslogEventsProcessNsDao.getTableName();
				 pstmt_del=con.prepareStatement(sqls_del);
				 sqls_insert="insert into "+ syslogEventsProcessNsDao.getTableName() + " ( MARK , VARLIST , BTIMELIST , ETIMELIST , FILTERFLAG1 , FILTERFLAG2 , SEVERITY1 , SEVERITY2 , PORT , NOTCAREFLAG , TYPE , EVENTTYPE , SUBEVENTTYPE , ALERTGROUP , ALERTKEY , SUMMARYCN , PROCESSSUGGEST , STATUS, ATTENTIONFLAG , EVENTS , MANUFACTURE , ORIGEVENT )"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				 pstmt=con.prepareStatement(sqls_insert);	     
				 sqls_update="Update "+ syslogEventsProcessNsDao.getTableName() + " set VARLIST=?,BTIMELIST=?, ETIMELIST=?, FILTERFLAG1=?, FILTERFLAG2=?, SEVERITY1=?, SEVERITY2=?, PORT=?, NOTCAREFLAG=?, TYPE=?, EVENTTYPE=?, SUBEVENTTYPE=?, ALERTGROUP=?, ALERTKEY=?, SUMMARYCN=?, PROCESSSUGGEST=?, STATUS=?, ATTENTIONFLAG=?, EVENTS=?,ORIGEVENT=? where Mark=? and MANUFACTURE=?";
				 pstmtUpdate=con.prepareStatement(sqls_update);

		     }else
		    	 return 1;
//	 	     System.out.println("before delete:" + defMibGrpDao.findAll().size());
	 	     pstmt_del.execute();
//	 	     System.out.println("after delete:" + defMibGrpDao.findAll().size());
	 	    
	 	    for (int m = 1; m < sheet.getRows(); m++) {
	 			  try{
	    				 boolean parseExcelError = false;
	    	   			 boolean ifUpdate = false;
	    	   		  
	    	   			 String MARK = sheet.getCell(mapping.get("MARK").getIndex(),m).getContents();
	    	   			 String MANUFACTURE = sheet.getCell(mapping.get("MANUFACTURE").getIndex(), m).getContents();
//	    	   			 System.out.println("33333In import SyslogProcess Mark=" + MARK + "\tManufacturer=" + MANUFACTURE) ;
	    	   			 if(tabName.equalsIgnoreCase(syslogEventsProcessDao.getTableName())){
	    	   				 SyslogEventsProcess syslog = syslogEventsProcessDao.findByPrimaryKey(MARK, MANUFACTURE);
	    	   				if(syslog != null ){
	   				    		  ifUpdate = true;
	   				    	  }
	    	   				
	    	   			 }else if(tabName.equalsIgnoreCase(syslogEventsProcessNsDao.getTableName())){
	    	   				SyslogEventsProcessNs syslog = syslogEventsProcessNsDao.findByPrimaryKey(MARK, MANUFACTURE);
	    	   				if(syslog != null ){
	   				    		  ifUpdate = true;
	   				    	  }
	    	   			 }
	    	   		     
	    	   			if(!ifUpdate){
	    					   for(int aa=1; aa<=sqlsCol.length; aa++){
	    			 	  ExcelTableMapping eTmp = mapping.get(sqlsCol[aa-1].toUpperCase());
	    			 	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), m).getContents();
	    			 	 
	    			 	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
	    			 		  mark  = -3;
	    			 		  parseExcelError = true;
	    			 		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +" 字段不能为空！");
	    			 		  appendErrorMessage(sheet.getName() ,sheet.getRow(m), errorMsgWb, eTmp.getName() +" 字段不能为空！", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
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
//	    				    					  System.out.println("cellContentTmp=" + cellContentTmp + "\tColumnName=" + eTmp.getName() + "\tmin=" + min + "\tmax=" + max);
	    				    					  if((eTmp.getName().equalsIgnoreCase("Severity1") ||eTmp.getName().equalsIgnoreCase("Severity2"))){
	    			  							  if(Long.parseLong(cellContentTmp) < min || (Long.parseLong(cellContentTmp) > max && Long.parseLong(cellContentTmp) != 100)) {
	    			  								  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+"取值范围为100/"+min+"-" + max, columnStrs);
	    			  		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"取值范围为100/"+min+"-" + max);
	    			  		    		    		  mark = -3;
	    			  		    		    		  parseExcelError = true;
	    			  		    		    		  break;
	    			  							  }
	    			  						  }
	    				    					  else{
	    				    						  if(Long.parseLong(cellContentTmp) < min || Long.parseLong(cellContentTmp) > max) {
	    				    							  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+"取值范围为"+min+"-" + max, columnStrs);
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
	    				    					  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM", columnStrs);
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
	    			  		    					  appendErrorMessage( sheet.getName(),sheet.getRow(m), errorMsgWb,eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
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
	    					    								  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
	    					    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS");
	    					    		    		    		  mark = -3;
	    					    		    		    		  parseExcelError = true;
	    						  		    		    		  flagBreak = true;
	    					    		    		    		  break;
	    					    							  }
	    				    							  }catch(Exception e){
	    				    								  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS|HH:MM:SS", columnStrs);
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
	    				    		  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+"数据类型错误，无法解析", columnStrs);
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
	    					  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb,  "SQL错误:"+e.getMessage(), columnStrs);
	    					  e.printStackTrace();
	    					  continue;
	    				  	}
	    				  }
	    					}else{//update
	    				  for(int aa=1; aa<=updateSqlsCol.length; aa++){
	    			   	  ExcelTableMapping eTmp = mapping.get(updateSqlsCol[aa-1].toUpperCase());
	    			   	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), m).getContents();
	    			   	 
	    			   	  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
	    			   		  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName() +"字段不能为空！", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
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
	    				    					  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM", columnStrs);
	    				    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM");
	    				    		    		  mark = -3;
	    				    		    		  parseExcelError = true;
	    				    		    		  break;
	    				    				  }else{
	    				    					  for(int cel=0; cel<7; cel++){
	    				    						  String temp = timeStrs.nextToken();
	    				    						  StringTokenizer cellStrs = new StringTokenizer(temp, ":");
	    			   		    				  if(cellStrs == null || cellStrs.countTokens() != 2){
	    			   		    					  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM", columnStrs);
	    			   		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM");
	    			   		    		    		  mark = -3;
	    			   		    		    		  parseExcelError = true;
	    			   		    		    		  break;
	    				    						  }else{
	    				    							  try{
	    				    								  String cellTmp1 = cellStrs.nextToken();
	    				    								  String cellTmp2 = cellStrs.nextToken();
	    					    							  if(Long.parseLong(cellTmp1) < 0 ||Long.parseLong(cellTmp1) > 24
	    					    									  || Long.parseLong(cellTmp2) < 0 ||Long.parseLong(cellTmp2) > 60){
	    					    								  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM", columnStrs);
	    					    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM");
	    					    		    		    		  mark = -3;
	    					    		    		    		  parseExcelError = true;
	    					    		    		    		  break;
	    					    							  }
	    				    							  }catch(Exception e){
	    				    								  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+" 填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM", columnStrs);
	    				    		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"填写时必须填写7个时间值，格式为HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM|HH:MM");
	    				    		    		    		  mark = -3;
	    				    		    		    		  parseExcelError = true;
	    				    		    		    		  break;
	    				    							  }
	    				    						  }
	    				    					  }
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
	    				    					  
//	    				    					  System.out.println("cellContentTmp=" + cellContentTmp + "\tColumnName=" + eTmp.getName() + "\tmin=" + min + "\tmax=" + max);
	    			  					  
	    				    					  if((eTmp.getName().equalsIgnoreCase("Severity1") ||eTmp.getName().equalsIgnoreCase("Severity2"))){
	    			  							  if(Long.parseLong(cellContentTmp) < min || (Long.parseLong(cellContentTmp) > max && Long.parseLong(cellContentTmp) != 100)) {
	    			  								  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+"取值范围为100/"+min+"-" + max, columnStrs);
	    			  		    		    		  Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" + eTmp.getName() +"取值范围为100/"+min+"-" + max);
	    			  		    		    		  mark = -3;
	    			  		    		    		  parseExcelError = true;
	    			  		    		    		  break;
	    			  							  }
	    			  						  }
	    				    					  else{
	    				    						  if(Long.parseLong(cellContentTmp) < min || Long.parseLong(cellContentTmp) > max) {
	    				    							  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+"取值范围为"+min+"-" + max, columnStrs);
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
	    				    		  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, eTmp.getName()+"数据类型错误，无法解析", columnStrs);
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
	    					  appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb,  "SQL错误:"+e.getMessage(), columnStrs);
	    					  e.printStackTrace();
	    					  continue;
	    					}  
	    				  }
	    			
	    					}
	    				    	  	
	    			
	    			}catch(Exception ew){
	    			mark = -3;
	    			appendErrorMessage(sheet.getName(),sheet.getRow(m), errorMsgWb, "信息错误，请检查！", columnStrs);
	    			Log4jInit.ncsLog.error(sheet.getName() + "导入失败：" +  " 错误:"+ew.getMessage());
	    			continue;
	    			}	
	    			
	    			}//end of for row
	 	    
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheet.getName() + "失败:" + e.getMessage());
		}
	     return mark;
	}
	
	private int importSNMPEventsProcess(Sheet sheet, HSSFWorkbook errorMsgWb,
			String[] columnStrs, Map<String, ExcelTableMapping> mapping,
			Connection con) {
		int mark=1;
		try{
			String sqls_del="delete from "+ snmpEventsProcessDao.getTableName();
			 PreparedStatement pstmt_del=con.prepareStatement(sqls_del);
			 String sqls_insert="insert into "+ snmpEventsProcessDao.getTableName() + " (MARK,MANUFACTURE,RESULTLIST,WARNMESSAGE,SUMMARY)"+" values(?,?,?,?,?)";
			 PreparedStatement pstmt_insert=con.prepareStatement(sqls_insert);	     
			 String sqls_update="Update "+ snmpEventsProcessDao.getTableName() + " set MANUFACTURE=?,RESULTLIST=?,WARNMESSAGE=?,SUMMARY=? where MARK=?";
			 PreparedStatement pstmt_update=con.prepareStatement(sqls_update);
			 String[] sqlsCol_insert= new String[]{"MARK", "MANUFACTURE", "RESULTLIST", "WARNMESSAGE","SUMMARY"};	
		     String[] sqlsCol_update= new String[]{"MANUFACTURE", "RESULTLIST", "WARNMESSAGE","SUMMARY","MARK"};	
		     
//	 	     System.out.println("before delete:" + defMibGrpDao.findAll().size());
	 	     pstmt_del.execute();
//	 	     System.out.println("after delete:" + defMibGrpDao.findAll().size());
	 	    
	 	     String markStr = "";
	 	     for(int r=1; r<sheet.getRows(); r++){
	 	    	boolean parseExcelError = false;
	 	    	boolean ifUpdate = false;
	 	    	try{
	 	    		 markStr = sheet.getCell(mapping.get("MARK").getIndex(), r).getContents();
	 	    	}catch(Exception ew){
	 	    		 ew.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage(snmpEventsProcessDao.getTableName(),sheet.getRow(r), errorMsgWb, "MARK 数据类型错误，无法解析", columnStrs);
		    		  continue;
		    	}
	 	    	//check whether to insert or update t_policy_base
	 	    	SnmpEventsProcess tmfList = snmpEventsProcessDao.findByPrimaryKey(markStr);
		    	if(tmfList != null){//update
		    		 ifUpdate = true;
		    	}
		    	  
		    	  int markTmp = insertUpdateTable(snmpEventsProcessDao.getTableName(),ifUpdate,sqlsCol_insert, sheet, mapping, 
		    				r, parseExcelError, errorMsgWb,
		    				columnStrs, pstmt_insert, pstmt_update,sqlsCol_update);
		    	  if(markTmp == -3){
		    		  mark = -3;
		    		  continue;
		    	  }else if(markTmp == -1){
		    		  pstmt_insert.close();
		    		  pstmt_update.close();
		    		  pstmt_del.close();
		    		  con.close();
		  	      	  return markTmp;
		    	  }
	 	     }//end of for row
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheet.getName() + "失败:" + e.getMessage());
		}
	     return mark;
	}


	private int importTOIDGroupInfo(Sheet sheet, HSSFWorkbook errorMsgWb,
			String[] columnStrs, Map<String, ExcelTableMapping> mapping,
			Connection con) {
		int mark=1;
		try{
			String sqls_del="delete from "+ TOidgroupInfoInitDao.getTableName();
			 PreparedStatement pstmt_del=con.prepareStatement(sqls_del);
			 String sqls_insert="insert into "+ TOidgroupInfoInitDao.getTableName() + " (OPID,OIDGROUPNAME,OTYPE,DESCRIPTION)"+" values(?,?,?,?)";
			 PreparedStatement pstmt_insert=con.prepareStatement(sqls_insert);	     
			 String sqls_update="Update "+ TOidgroupInfoInitDao.getTableName() + " set OIDGROUPNAME=?,OTYPE=?,DESCRIPTION=? where OPID=?";
			 PreparedStatement pstmt_update=con.prepareStatement(sqls_update);
			 String[] sqlsCol_insert= new String[]{"OPID","OIDGROUPNAME","OTYPE","DESCRIPTION"};	
		     String[] sqlsCol_update= new String[]{"OIDGROUPNAME","OTYPE","DESCRIPTION","OPID"};	
	 	      
//	 	     System.out.println("before delete:" + defMibGrpDao.findAll().size());
	 	     pstmt_del.execute();
//	 	     System.out.println("after delete:" + defMibGrpDao.findAll().size());
	 	    
	 	     long opid = 0;
	 	     for(int r=1; r<sheet.getRows(); r++){
	 	    	boolean parseExcelError = false;
	 	    	boolean ifUpdate = false;
	 	    	try{
	 	    		 opid = Long.parseLong(sheet.getCell(mapping.get("OPID").getIndex(), r).getContents());
//		 	    		System.out.println("Import OIDGroup Info init, opid=" + opid + "\tindex=" +mapping.get("OPID").getIndex()) ;
	 	    	}catch(Exception ew){
	 	    		 ew.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage(TOidgroupInfoInitDao.getTableName(),sheet.getRow(r), errorMsgWb, "OPID 数据类型错误，无法解析", columnStrs);
		    		  continue;
		    	}
	 	    	//check whether to insert or update t_policy_base
	 	    	TOidgroupInfoInit tmfList = TOidgroupInfoInitDao.findByPrimaryKey(opid);
		    	  
		    		  if(tmfList != null){//update
		    			  ifUpdate = true;
		    		  }
		    	  
		    	  int markTmp = insertUpdateTable(TOidgroupInfoInitDao.getTableName(),ifUpdate,sqlsCol_insert, sheet, mapping, 
		    				r, parseExcelError, errorMsgWb,
		    				columnStrs, pstmt_insert, pstmt_update,sqlsCol_update);
		    	  if(markTmp == -3){
		    		  mark = -3;
		    		  continue;
		    	  }else if(markTmp == -1){
		    		  pstmt_insert.close();
		    		  pstmt_update.close();
		    		  pstmt_del.close();
		    		  con.close();
		  	      	  return markTmp;
		    	  }
//		    	  System.out.println("in for i=" + r+ " delete:" + defMibGrpDao.findAll().size());
	 	     }//end of for row
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheet.getName() + "失败:" + e.getMessage());
		}
	     return mark;
	}


	private int importTModuleInfoInit(Sheet sheet, HSSFWorkbook errorMsgWb,
			String[] columnStrs, Map<String, ExcelTableMapping> mapping,
			Connection con) {
		int mark=1;
		try{
			String sqls_del="delete from "+ TModuleInfoInitDao.getTableName();
			 PreparedStatement pstmt_del=con.prepareStatement(sqls_del);
	 	     
			 String sqls_insert="insert into "+ TModuleInfoInitDao.getTableName() + " (MODID, MCode, MName, DESCRIPTION)"+" values(?,?,?,?)";
			 PreparedStatement pstmt_insert=con.prepareStatement(sqls_insert);
			 
			 String sqls_update="Update "+ TModuleInfoInitDao.getTableName() + " set MCode=?, MName=?, DESCRIPTION=? where MODID=?";
			 PreparedStatement pstmt_update=con.prepareStatement(sqls_update);
			 String[] sqlsCol_update= new String[]{"MCode", "MName", "DESCRIPTION","MODID"};	
	 	     String[] sqlsCol_insert= new String[]{"MODID","MCode", "MName", "DESCRIPTION"};		
	 	      
//	 	     System.out.println("before delete:" + defMibGrpDao.findAll().size());
	 	     pstmt_del.execute();
//	 	     System.out.println("after delete:" + defMibGrpDao.findAll().size());
	 	    
	 	     long mid = 0;
	 	     for(int r=1; r<sheet.getRows(); r++){
	 	    	boolean parseExcelError = false;
	 	    	boolean ifUpdate = false;
	 	    	try{
	 	    		 mid = Long.parseLong(sheet.getCell(mapping.get("MODID").getIndex(), r).getContents());
	 	    	}catch(Exception ew){
	 	    		 ew.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage(TModuleInfoInitDao.getTableName(),sheet.getRow(r), errorMsgWb, "MODID 数据类型错误，无法解析", columnStrs);
		    		  continue;
		    	}
	 	    	//check whether to insert or update t_policy_base
	 	    	TModuleInfoInit tmfList = TModuleInfoInitDao.findByPrimaryKey(mid);
		    	  if(tmfList != null){//update
		    			  ifUpdate = true;
		    		  }
		    	  
		    	  int markTmp = insertUpdateTable(TModuleInfoInitDao.getTableName(),ifUpdate,sqlsCol_insert, sheet, mapping, 
		    				r, parseExcelError, errorMsgWb,
		    				columnStrs, pstmt_insert, pstmt_update,sqlsCol_update);
		    	  if(markTmp == -3){
		    		  mark = -3;
		    		  continue;
		    	  }else if(markTmp == -1){
		    		  pstmt_insert.close();
		    		  pstmt_update.close();
		    		  pstmt_del.close();
		    		  con.close();
		  	      	  return markTmp;
		    	  }
//		    	  System.out.println("in for i=" + r+ " delete:" + defMibGrpDao.findAll().size());
	 	     }//end of for row
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheet.getName() + "失败:" + e.getMessage());
		}
	     return mark;
	}


	/**
	 * 		    		 
	 * @param sheet
	 * @param errorMsgWb
	 * @param columnStrs
	 * @param mapping_VPerfEveoid T_EVENT_OID_INIT
	 * @param mapping_VPerfOidInfo: T_OIDGROUP_INFO_INIT
	 * @param mapping_VPerfEve T_EVENT_TYPE_INIT
	 * @param mapping_VPerfDev T_DEVICE_TYPE_INIT
	 * @param con
	 * @return
	 */
	private int importVPerformParam(Sheet sheet, HSSFWorkbook errorMsgWb,
			String[] columnStrs,
			Map<String, ExcelTableMapping> mapping_VPerfEveoid,
			Map<String, ExcelTableMapping> mapping_VPerfOidInfo,
			Map<String, ExcelTableMapping> mapping_VPerfEve,
			Map<String, ExcelTableMapping> mapping_VPerfDev, Connection con) {
		
		int mark=1;
		try{			
		 //T_EVENT_OID_INIT
		 String sqls_delEveoid = "delete from " + TEventOidInitDao.getTableName();
		 PreparedStatement pstmt_delEveoid=con.prepareStatement(sqls_delEveoid);	 
	     String sqls_insertEveoid="insert into "+ TEventOidInitDao.getTableName() + " (EVEID,MRID,DTID,OIDGROUPNAME,MODID,OID,UNIT,DESCRIPTION)"+" values(?,?,?,?,?,?,?,?)";
		 PreparedStatement pstmt_insertEveoid=con.prepareStatement(sqls_insertEveoid);	     
		 String sqls_updateEveoid="Update "+ TEventOidInitDao.getTableName() + " set MRID=?,MODID=?,OID=?,UNIT=?,DESCRIPTION=? where EVEID=? and DTID=? and OIDGROUPNAME=?";
		 PreparedStatement pstmt_updateEveoid=con.prepareStatement(sqls_updateEveoid);
		 String[] sqlsCol_insertEveoid= new String[]{"EVEID","MRID","DTID","OIDGROUPNAME","MODID","OID","UNIT","DESCRIPTION"};	
	     String[] sqlsCol_updateEveoid= new String[]{"MRID","MODID","OID","UNIT","DESCRIPTION","EVEID","DTID","OIDGROUPNAME"};
//	     System.out.println("before delete from " +  TEventOidInitDao.getTableName() + " :" +  TEventOidInitDao.findAll().size());
	     pstmt_delEveoid.execute();
//	     System.out.println("after delete from " +  TEventOidInitDao.getTableName() + " :" +  TEventOidInitDao.findAll().size());

	     //T_OIDGROUP_INFO_INIT
		 String sqls_insertOidInfo="insert into "+ TOidgroupInfoInitDao.getTableName() + " (OPID,OIDGROUPNAME,OTYPE,DESCRIPTION)"+" values(?,?,?,?)";
		 PreparedStatement pstmt_insertOidInfo=con.prepareStatement(sqls_insertOidInfo);	     
		 String sqls_updateOidInfo="Update "+ TOidgroupInfoInitDao.getTableName() + " set OIDGROUPNAME=?,OTYPE=?,DESCRIPTION=? where OPID=?";
		 PreparedStatement pstmt_updateOidInfo=con.prepareStatement(sqls_updateOidInfo);
		 String[] sqlsCol_insertOidInfo= new String[]{"OPID","OIDGROUPNAME","OTYPE","DESCRIPTION"};	
	     String[] sqlsCol_updateOidInfo= new String[]{"OIDGROUPNAME","OTYPE","DESCRIPTION","OPID"};		
	     
	     //T_EVENT_TYPE_INIT
		 String sqls_insertEve="insert into "+ TEventTypeInitDao.getTableName() + " (MODID,EVEID,ETID,ESTID,EVEOTHERNAME,ECODE,GENERAL,MAJOR,MINOR,OTHER,DESCRIPTION,USEFLAG)"+" values(?,?,?,?,?,?,?,?,?,?,?,?)";
		 PreparedStatement pstmt_insertEve=con.prepareStatement(sqls_insertEve);	     
		 String sqls_updateEve="Update "+ TEventTypeInitDao.getTableName() + " set ETID=?,ESTID=?,EVEOTHERNAME=?,ECODE=?,GENERAL=?,MAJOR=?,MINOR=?,OTHER=?,DESCRIPTION=?,USEFLAG=? where EVEID=? and MODID=?";
		 PreparedStatement pstmt_updateEve=con.prepareStatement(sqls_updateEve);
		 String[] sqlsCol_insertEve= new String[]{"MODID","EVEID","ETID","ESTID","EVEOTHERNAME","ECODE","GENERAL","MAJOR","MINOR","OTHER","DESCRIPTION","USEFLAG"};	
	     String[] sqlsCol_updateEve= new String[]{"ETID","ESTID","EVEOTHERNAME","ECODE","GENERAL","MAJOR","MINOR","OTHER","DESCRIPTION","USEFLAG","EVEID","MODID"};		
		 
	     //T_DEVICE_TYPE_INIT	 
	     String sqls_insertDev="insert into "+ TDeviceTypeInitDao.getTableName() + " (MRID,DTID,CATEGORY,SUBCATEGORY,MODEL,OBJECTID,LOGO,DESCRIPTION)"+" values(?,?,?,?,?,?,?,?)";
		 PreparedStatement pstmt_insertDev=con.prepareStatement(sqls_insertDev);	     
		 String sqls_updateDev="Update "+ TDeviceTypeInitDao.getTableName() + " set MRID=?, CATEGORY=?, SUBCATEGORY=?,MODEL=?,OBJECTID=?,LOGO=?,DESCRIPTION=?  where DTID=?";
		 PreparedStatement pstmt_updateDev=con.prepareStatement(sqls_updateDev);
		 String[] sqlsCol_updateDev= new String[]{"MRID","CATEGORY","SUBCATEGORY","MODEL","OBJECTID","LOGO","DESCRIPTION","DTID"};	
	     String[] sqlsCol_insertDev= new String[]{"MRID","DTID","CATEGORY","SUBCATEGORY","MODEL","OBJECTID","LOGO","DESCRIPTION"};
	     
	     long eveid_eveOid = 0;
	     long dtid_eveOid = 0;	     
	     String oidGrpName_eveOid = "";
	     long opid_info = 0;
	     long eveid_eve = 0;
	     long modid_eve = 0, dtid_dev=0;
	     for(int r=1; r<sheet.getRows(); r++){
	    	boolean ifUpdate_Info = false;
	    	boolean ifUpdate_dev = false;
	    	boolean ifUpdate_EveOid = false;
	    	boolean ifUpdate_eve = false;
	    	try{
	    		eveid_eveOid = Long.parseLong(sheet.getCell(mapping_VPerfEveoid.get("EVEID").getIndex(), r).getContents());
	    		dtid_eveOid = Long.parseLong(sheet.getCell(mapping_VPerfEveoid.get("DTID").getIndex(), r).getContents());
	    		oidGrpName_eveOid = sheet.getCell(mapping_VPerfEveoid.get("OIDGROUPNAME").getIndex(), r).getContents();
	    		
	    		opid_info = Long.parseLong(sheet.getCell(mapping_VPerfOidInfo.get("OPID").getIndex(), r).getContents());
	    		
	    		eveid_eve = Long.parseLong(sheet.getCell(mapping_VPerfEve.get("EVEID").getIndex(), r).getContents());
	    		modid_eve = Long.parseLong(sheet.getCell(mapping_VPerfEve.get("MODID").getIndex(), r).getContents());

	    		dtid_dev = Long.parseLong(sheet.getCell(mapping_VPerfDev.get("DTID").getIndex(), r).getContents());
	    	}catch(Exception ew){
	    		 ew.printStackTrace();
	    		 mark = -3;
	    		 appendErrorMessage(VPerformParamDao.getTableName(),sheet.getRow(r), errorMsgWb, "EVEID/DTID/OPID/MODID/OIDGROUPNAME/数据类型错误，无法解析", columnStrs);
	    		 continue;
	    	}
	    	
	    	//check whether to insert or update table
	    	List<TEventOidInit> teveOid = TEventOidInitDao.findWhereFKsEquals(dtid_eveOid, eveid_eveOid, oidGrpName_eveOid);
	    	if(teveOid != null && teveOid.size()>0 ){//update
  			  if(teveOid.size()>1){
  				  appendErrorMessage(VPerformParamDao.getTableName(),sheet.getRow(r), errorMsgWb, "DTID,EVEID,OIDGROUPNAME在数据库T_EVENT_OID_INIT中记录不唯一，请核查数据库一致性", columnStrs);
  				  mark = -3;
  				  continue;
  			  }
  			  ifUpdate_EveOid = true;
  		  	}
	    	TDeviceTypeInit tdev = TDeviceTypeInitDao.findByPrimaryKey(dtid_dev);
	    	if(tdev !=null)
	    		ifUpdate_dev = true;
	    	TOidgroupInfoInit toidInfo = TOidgroupInfoInitDao.findByPrimaryKey(opid_info);
	    	if(toidInfo != null){//update
	    		ifUpdate_Info = true;
	    	}
	    	TEventTypeInit teve = TEventTypeInitDao.findByPrimaryKey(modid_eve, eveid_eve);
	    	if(teve != null){//update
	    		ifUpdate_eve = true;
	    	}
	    	
	    	int markTmpDev = prepareInsertUpdateView(VPerformParamDao.getTableName(),ifUpdate_dev,sqlsCol_insertDev, sheet, mapping_VPerfDev, 
	    			r, errorMsgWb,
	    			columnStrs, pstmt_insertDev, pstmt_updateDev,sqlsCol_updateDev);
	    	if(markTmpDev == -3){
	    		mark = -3;
	    		continue;
	    	}else if(markTmpDev == -1){
	    		pstmt_updateDev.close();
	    		pstmt_insertDev.close();
	    		pstmt_delEveoid.close();
		    	con.close();
		  	    return markTmpDev;
	    	}
	    	
	    	int markTmpEve = prepareInsertUpdateView(VPerformParamDao.getTableName(),ifUpdate_eve,sqlsCol_insertEve, sheet, mapping_VPerfEve, 
	    			r, errorMsgWb,
	    			columnStrs, pstmt_insertEve, pstmt_updateEve,sqlsCol_updateEve);
	    	if(markTmpEve == -3){
	    		mark = -3;
	    		continue;
	    	}else if(markTmpEve == -1){
	    		pstmt_updateEve.close();
	    		pstmt_insertEve.close();
	    		pstmt_delEveoid.close();
		    	con.close();
		  	    return markTmpEve;
	    	}
	    	
	    	int markTmpOidInfo = prepareInsertUpdateView(VPerformParamDao.getTableName(),ifUpdate_Info,sqlsCol_insertOidInfo, 
	    			sheet, mapping_VPerfOidInfo, 
	    			r, errorMsgWb,
	    			columnStrs, pstmt_insertOidInfo, pstmt_updateOidInfo,sqlsCol_updateOidInfo);
	    	if(markTmpOidInfo == -3){
	    		mark = -3;
	    		continue;
	    	}else if(markTmpOidInfo == -1){
	    		pstmt_updateOidInfo.close();
	    		pstmt_insertOidInfo.close();
	    		pstmt_delEveoid.close();
		    	con.close();
		  	    return markTmpOidInfo;
	    	}

	    	int markTmpEveOid = prepareInsertUpdateView(VPerformParamDao.getTableName(),ifUpdate_EveOid,sqlsCol_insertEveoid, sheet, mapping_VPerfEveoid, 
	    			r, errorMsgWb,
	    			columnStrs, pstmt_insertEveoid, pstmt_updateEveoid,sqlsCol_updateEveoid);
	    	if(markTmpEveOid == -3){
	    		mark = -3;
	    		continue;
	    	}else if(markTmpEveOid == -1){
	    		pstmt_updateEveoid.close();
	    		pstmt_insertEveoid.close();
	    		pstmt_delEveoid.close();
		    	con.close();
		  	    return markTmpEveOid;
	    	}
	    	
	    	//execute sql for this row
    		try{
//    			System.out.println("\tbefore execute SQL********************");
	    		if(ifUpdate_Info)
	    			pstmt_updateOidInfo.execute();
	    		else
	    			pstmt_insertOidInfo.execute();
	    		if(ifUpdate_dev)
	    			pstmt_updateDev.execute();
	    		else
	    			pstmt_insertDev.execute();
	    		if(ifUpdate_eve)
	    			pstmt_updateEve.execute();
	    		else
	    			pstmt_insertEve.execute();
	    		if(ifUpdate_EveOid)
	    			pstmt_updateEveoid.execute();
	    		else
	    			pstmt_insertEveoid.execute();
//    			System.out.println("\tAfter execute SQL********************");    		
    		}catch (Exception e) {
				mark = -3;
				appendErrorMessage(VPerformParamDao.getTableName(),sheet.getRow(r), errorMsgWb, "SQL错误: 提示： (Major, Minor, Other),OIDGroupName在数据库中必须唯一\n详细信息:"+ e.getMessage(), columnStrs);
				e.printStackTrace();
	    		continue;
    		}
//	    	System.out.println("in for row=" + r+ " TOidgroupDetailsInitDao:" + TOidgroupDetailsInitDao.findAll().size());
//	    	System.out.println("in for row=" + r+ " TOidgroupInfoInitDao:" + TOidgroupInfoInitDao.findAll().size());
	     }//end of for row

		pstmt_updateDev.close();
		pstmt_insertDev.close();
		pstmt_updateEve.close();
		pstmt_insertEve.close();
		pstmt_insertEveoid.close();
		pstmt_updateEveoid.close();
		pstmt_insertOidInfo.close();
		pstmt_updateOidInfo.close();
		pstmt_delEveoid.close();	
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheet.getName() + "失败:" + e.getMessage());
		}

	     return mark;
	}


	/**
	 * 
	 * @param sheet
	 * @param errorMsgWb
	 * @param columnStrs
	 * @param mapping_VOidOidInfo T_OIDGROUP_INFO_INIT
	 * @param mapping_VOidOidDetails T_OIDGROUP_DETAILS_INIT
	 * @param con
	 * @return
	 */
	private int importVOidGroup(Sheet sheet, HSSFWorkbook errorMsgWb,
			String[] columnStrs,
			Map<String, ExcelTableMapping> mapping_VOidOidInfo,
			Map<String, ExcelTableMapping> mapping_VOidOidDetails,
			Connection con) {
		int mark=1;
		try{
		 String sqls_delOidDetails = "delete from " + TOidgroupDetailsInitDao.getTableName();
		 PreparedStatement pstmt_delOidDetails=con.prepareStatement(sqls_delOidDetails);	 
	     String sqls_insertOidDetails="insert into "+ TOidgroupDetailsInitDao.getTableName() + " (OPID,OIDVALUE,OIDNAME,OIDUNIT,FLAG,OIDINDEX)"+" values(?,?,?,?,?,?)";
		 PreparedStatement pstmt_insertOidDetails=con.prepareStatement(sqls_insertOidDetails);	     
		 String sqls_updateOidDetails="Update "+ TOidgroupDetailsInitDao.getTableName() + " set OIDVALUE=?,OIDUNIT=?,FLAG=?,OIDINDEX=? where OPID=? and OIDNAME=?";
		 PreparedStatement pstmt_updateOidDetails=con.prepareStatement(sqls_updateOidDetails);
		 String[] sqlsCol_insertOidDetails= new String[]{"OPID","OIDVALUE","OIDNAME","OIDUNIT","FLAG","OIDINDEX"};	
	     String[] sqlsCol_updateOidDetails= new String[]{"OIDVALUE","OIDUNIT","FLAG","OIDINDEX","OPID","OIDNAME"};
//	     System.out.println("before delete from " +  TOidgroupInfoInitDao.getTableName() + " :" +  TOidgroupInfoInitDao.findAll().size());
	     pstmt_delOidDetails.execute();
//	     System.out.println("after delete from " +  TOidgroupInfoInitDao.getTableName() + " :" +  TOidgroupInfoInitDao.findAll().size());

		 String sqls_insertOidInfo="insert into "+ TOidgroupInfoInitDao.getTableName() + " (OPID,OIDGROUPNAME,OTYPE,DESCRIPTION)"+" values(?,?,?,?)";
		 PreparedStatement pstmt_insertOidInfo=con.prepareStatement(sqls_insertOidInfo);	     
		 String sqls_updateOidInfo="Update "+ TOidgroupInfoInitDao.getTableName() + " set OIDGROUPNAME=?,OTYPE=?,DESCRIPTION=? where OPID=?";
		 PreparedStatement pstmt_updateOidInfo=con.prepareStatement(sqls_updateOidInfo);
		 String[] sqlsCol_insertOidInfo= new String[]{"OPID","OIDGROUPNAME","OTYPE","DESCRIPTION"};	
	     String[] sqlsCol_updateOidInfo= new String[]{"OIDGROUPNAME","OTYPE","DESCRIPTION","OPID"};		
	     
	     long opid_info = 0;
	     long opid_details = 0;
	     String oidName_details = "";
	     for(int r=1; r<sheet.getRows(); r++){
	    	boolean ifUpdate_Info = false;
	    	boolean ifUpdate_Details = false;
	    	try{
	    		opid_info = Long.parseLong(sheet.getCell(mapping_VOidOidInfo.get("OPID").getIndex(), r).getContents());
	    		opid_details = Long.parseLong(sheet.getCell(mapping_VOidOidDetails.get("OPID").getIndex(), r).getContents());
	    		oidName_details = sheet.getCell(mapping_VOidOidDetails.get("OIDNAME").getIndex(), r).getContents();

//	    	     System.out.println("row=" + r + "\t opid_info=" + opid_info + "\topid_details=" + opid_details + "\t oidName_details" + oidName_details);	    	     
	    	}catch(Exception ew){
	    		 ew.printStackTrace();
	    		 mark = -3;
	    		 appendErrorMessage(VOidGroupDao.getTableName(),sheet.getRow(r), errorMsgWb, "OPID/OIDGROUPNAME数据类型错误，无法解析", columnStrs);
	    		 continue;
	    	}
	    	
	    	//check whether to insert or update table
	    	TOidgroupInfoInit toidInfo = TOidgroupInfoInitDao.findByPrimaryKey(opid_info);
	    	if(toidInfo != null){//update
	    		ifUpdate_Info = true;
	    	}
	    	TOidgroupDetailsInit tdetails = TOidgroupDetailsInitDao.findByPrimaryKey(opid_details, oidName_details);
	    	if(tdetails != null){//update
	    		ifUpdate_Details = true;
	    	}
	    	
	    	int markTmpMod = prepareInsertUpdateView(VOidGroupDao.getTableName(),ifUpdate_Info,sqlsCol_insertOidInfo, sheet, mapping_VOidOidInfo, 
	    			r, errorMsgWb,
	    			columnStrs, pstmt_insertOidInfo, pstmt_updateOidInfo,sqlsCol_updateOidInfo);
	    	if(markTmpMod == -3){
	    		mark = -3;
	    		continue;
	    	}else if(markTmpMod == -1){
	    		pstmt_updateOidInfo.close();
	    		pstmt_insertOidInfo.close();
	    		pstmt_delOidDetails.close();
		    	con.close();
		  	    return markTmpMod;
	    	}
	    	
	    	int markTmpEve = prepareInsertUpdateView(VOidGroupDao.getTableName(),ifUpdate_Details,sqlsCol_insertOidDetails, 
	    			sheet, mapping_VOidOidDetails, r, errorMsgWb, columnStrs, pstmt_insertOidDetails, pstmt_updateOidDetails,sqlsCol_updateOidDetails);
	    	if(markTmpEve== -3){
	    		mark = -3;
	    		continue;
	    	}else if(markTmpEve == -1){
	    		pstmt_updateOidDetails.close();
	    		pstmt_insertOidDetails.close();
	    		pstmt_delOidDetails.close();
		    	con.close();
		  	    return markTmpEve;
	    	}
	    	
	    	//execute sql for this row
    		try{
//    			System.out.println("\tbefore execute SQL********************");
	    		if(ifUpdate_Info)
	    			pstmt_updateOidInfo.execute();
	    		else
	    			pstmt_insertOidInfo.execute();
	    		if(ifUpdate_Details)
	    			pstmt_updateOidDetails.execute();
	    		else
	    			pstmt_insertOidDetails.execute();
//    			System.out.println("\tAfter execute SQL********************");    		
    		}catch (Exception e) {
				mark = -3;
				appendErrorMessage(VOidGroupDao.getTableName(),sheet.getRow(r), errorMsgWb, "SQL错误: 提示： OIDGroupName/OIDUnit在数据库中必须唯一\n详细信息:"+ e.getMessage(), columnStrs);
				e.printStackTrace();
	    		continue;
    		}
//	    	System.out.println("in for row=" + r+ " TOidgroupDetailsInitDao:" + TOidgroupDetailsInitDao.findAll().size());
//	    	System.out.println("in for row=" + r+ " TOidgroupInfoInitDao:" + TOidgroupInfoInitDao.findAll().size());
	     }//end of for row

		pstmt_updateOidDetails.close();
		pstmt_insertOidInfo.close();
		pstmt_updateOidDetails.close();
		pstmt_insertOidInfo.close();
		pstmt_delOidDetails.close();	
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheet.getName() + "失败:" + e.getMessage());
		}

	     return mark;
	}


	/**
	 * 
	 * @param sheet
	 * @param errorMsgWb
	 * @param columnStrs
	 * @param mapping_VEveEve //T_EVENT_TYPE_INIT
	 * @param mapping_VEveMod //T_MODULE_INFO_INIT
	 * @param con
	 * @return
	 */
	private int importVEventType(Sheet sheet, HSSFWorkbook errorMsgWb,
			String[] columnStrs,
			Map<String, ExcelTableMapping> mapping_VEveEve,
			Map<String, ExcelTableMapping> mapping_VEveMod, Connection con) {
		int mark=1;
		try{
	     String sqls_insertMod="insert into "+ TModuleInfoInitDao.getTableName() + " (MODID,MNAME,MCODE,DESCRIPTION)"+" values(?,?,?,?)";
		 PreparedStatement pstmt_insertMod=con.prepareStatement(sqls_insertMod);	     
		 String sqls_updateMod="Update "+ TModuleInfoInitDao.getTableName() + " set MNAME=?, MCODE=?, DESCRIPTION=? where MODID=?";
		 PreparedStatement pstmt_updateMod=con.prepareStatement(sqls_updateMod);
		 String[] sqlsCol_updateMod= new String[]{"MNAME","MCODE", "DESCRIPTION","MODID"};	
	     String[] sqlsCol_insertMod= new String[]{"MODID","MNAME","MCODE", "DESCRIPTION"};

		 String sqls_delEve = "delete from " + TEventTypeInitDao.getTableName();
		 PreparedStatement pstmt_delEve=con.prepareStatement(sqls_delEve);	 
		 String sqls_insertEve="insert into "+ TEventTypeInitDao.getTableName() + " (MODID,EVEID,ETID,ESTID,EVEOTHERNAME,ECODE,GENERAL,MAJOR,MINOR,OTHER,DESCRIPTION,USEFLAG)"+" values(?,?,?,?,?,?,?,?,?,?,?,?)";
		 PreparedStatement pstmt_insertEve=con.prepareStatement(sqls_insertEve);	     
		 String sqls_updateEve="Update "+ TEventTypeInitDao.getTableName() + " set ETID=?,ESTID=?,EVEOTHERNAME=?,ECODE=?,GENERAL=?,MAJOR=?,MINOR=?,OTHER=?,DESCRIPTION=?,USEFLAG=? where EVEID=? and MODID=?";
		 PreparedStatement pstmt_updateEve=con.prepareStatement(sqls_updateEve);
		 String[] sqlsCol_insertEve= new String[]{"MODID","EVEID","ETID","ESTID","EVEOTHERNAME","ECODE","GENERAL","MAJOR","MINOR","OTHER","DESCRIPTION","USEFLAG"};	
	     String[] sqlsCol_updateEve= new String[]{"ETID","ESTID","EVEOTHERNAME","ECODE","GENERAL","MAJOR","MINOR","OTHER","DESCRIPTION","USEFLAG","EVEID","MODID"};		
//	     System.out.println("before delete from " +  TEventTypeInitDao.getTableName() + " :" +  TEventTypeInitDao.findAll().size());
	     pstmt_delEve.execute();
//	     System.out.println("after delete from " +  TEventTypeInitDao.getTableName() + " :" +  TEventTypeInitDao.findAll().size());
	     
	     long modid_mod = 0;
	     long eveid_eve = 0;
	     long modid_eve = 0;
	     for(int r=1; r<sheet.getRows(); r++){
	    	boolean ifUpdate_mod = false;
	    	boolean ifUpdate_eve = false;
	    	try{
	    		eveid_eve = Long.parseLong(sheet.getCell(mapping_VEveEve.get("EVEID").getIndex(), r).getContents());
	    		modid_mod = Long.parseLong(sheet.getCell(mapping_VEveMod.get("MODID").getIndex(), r).getContents());
	    		modid_eve = Long.parseLong(sheet.getCell(mapping_VEveEve.get("MODID").getIndex(), r).getContents());

//	    	     System.out.println("row=" + r + "\tmodid_mod=" + modid_mod + "\teveid_eve=" + eveid_eve + "\t modid_eve" + modid_eve);	    	     
	    	}catch(Exception ew){
	    		 ew.printStackTrace();
	    		 mark = -3;
	    		 appendErrorMessage(VEventTypeDao.getTableName(),sheet.getRow(r), errorMsgWb, "EVEID/MODID数据类型错误，无法解析", columnStrs);
	    		 continue;
	    	}
	    	
	    	//check whether to insert or update table
	    	TModuleInfoInit tmod = TModuleInfoInitDao.findByPrimaryKey(modid_mod);
	    	if(tmod != null){//update
	    		ifUpdate_mod = true;
	    	}
	    	TEventTypeInit teve = TEventTypeInitDao.findByPrimaryKey(modid_eve, eveid_eve);
	    	if(teve != null){//update
	    		ifUpdate_eve = true;
	    	}
	    	
	    	int markTmpMod = prepareInsertUpdateView(VEventTypeDao.getTableName(),ifUpdate_mod,sqlsCol_insertMod, sheet, mapping_VEveMod, 
	    			r, errorMsgWb,
	    			columnStrs, pstmt_insertMod, pstmt_updateMod,sqlsCol_updateMod);
	    	if(markTmpMod == -3){
	    		mark = -3;
	    		continue;
	    	}else if(markTmpMod == -1){
	    		pstmt_updateMod.close();
	    		pstmt_insertMod.close();
	    		pstmt_delEve.close();
		    	con.close();
		  	    return markTmpMod;
	    	}
	    	
	    	int markTmpEve = prepareInsertUpdateView(VEventTypeDao.getTableName(),ifUpdate_eve,sqlsCol_insertEve, 
	    			sheet, mapping_VEveEve, r, errorMsgWb, columnStrs, pstmt_insertEve, pstmt_updateEve,sqlsCol_updateEve);
	    	if(markTmpEve== -3){
	    		mark = -3;
	    		continue;
	    	}else if(markTmpEve == -1){
	    		pstmt_updateEve.close();
	    		pstmt_insertEve.close();
	    		pstmt_delEve.close();
		    	con.close();
		  	    return markTmpEve;
	    	}
	    	
	    	//execute sql for this row
    		try{
//    			System.out.println("\tbefore execute SQL********************");
	    		if(ifUpdate_mod)
	    			pstmt_updateMod.execute();
	    		else
	    			pstmt_insertMod.execute();
	    		if(ifUpdate_eve)
	    			pstmt_updateEve.execute();
	    		else
	    			pstmt_insertEve.execute();
//    			System.out.println("\tAfter execute SQL********************");    		
    		}catch (Exception e) {
				mark = -3;
				appendErrorMessage(VEventTypeDao.getTableName(),sheet.getRow(r), errorMsgWb, "SQL错误: 提示： Major, Minor, Other及MName, Mcode在数据库中必须唯一\n详细信息:"+ e.getMessage(), columnStrs);
				e.printStackTrace();
	    		continue;
    		}
//	    	System.out.println("in for row=" + r+ " TModuleInfoInitDao:" + TModuleInfoInitDao.findAll().size());
//	    	System.out.println("in for row=" + r+ " TEventTypeInitDao:" + TEventTypeInitDao.findAll().size());
	     }//end of for row

		pstmt_updateMod.close();
		pstmt_insertMod.close();
		pstmt_updateEve.close();
		pstmt_insertEve.close();
		pstmt_delEve.close();	
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheet.getName() + "失败:" + e.getMessage());
		}

	     return mark;
	}


	/**
	 * 
	 * @param sheet
	 * @param errorMsgWb
	 * @param columnStrs
	 * @param mapping_cate
	 * @param mapping_mf
	 * @param mapping_dev
	 * @param con
	 * @return
	 */
	private int importVMfCateDevtype(Sheet sheet, HSSFWorkbook errorMsgWb,
			String[] columnStrs, Map<String, ExcelTableMapping> mapping_cate,
			Map<String, ExcelTableMapping> mapping_mf,
			Map<String, ExcelTableMapping> mapping_dev, Connection con) {
		int mark=1;
		try{
		 String sqls_insertCate="insert into "+ TCategoryMapInitDao.getTableName() + " (ID, Name, Flag)"+" values(?,?,?)";
		 PreparedStatement pstmt_insertCate=con.prepareStatement(sqls_insertCate);	     
		 String sqls_updateCate="Update "+ TCategoryMapInitDao.getTableName() + " set Name=?, Flag=? where ID=?";
		 PreparedStatement pstmt_updateCate=con.prepareStatement(sqls_updateCate);
		 String[] sqlsCol_updateCate= new String[]{"Name", "Flag", "ID"};	
	     String[] sqlsCol_insertCate= new String[]{"ID","Name","Flag"};		
	     
	     String sqls_insertMf="insert into "+ TManufacturerInfoInitDao.getTableName() + " (MRID, MRName, ObjectID, Description)"+" values(?,?,?,?)";
		 PreparedStatement pstmt_insertMf=con.prepareStatement(sqls_insertMf);	     
		 String sqls_updateMf="Update "+ TManufacturerInfoInitDao.getTableName() + " set MRName=?, ObjectID=?, Description=? where MRID=?";
		 PreparedStatement pstmt_updateMf=con.prepareStatement(sqls_updateMf);
		 String[] sqlsCol_updateMf= new String[]{"MRName","ObjectID", "Description", "MRID"};	
	     String[] sqlsCol_insertMf= new String[]{"MRID","MRName","ObjectID", "Description"};
	   
	     String sqls_delDev = "delete from " + TDeviceTypeInitDao.getTableName();
	     PreparedStatement pstmt_delDev=con.prepareStatement(sqls_delDev);	 
	     String sqls_insertDev="insert into "+ TDeviceTypeInitDao.getTableName() + " (MRID,DTID,CATEGORY,SUBCATEGORY,MODEL,OBJECTID,LOGO,DESCRIPTION)"+" values(?,?,?,?,?,?,?,?)";
		 PreparedStatement pstmt_insertDev=con.prepareStatement(sqls_insertDev);	     
		 String sqls_updateDev="Update "+ TDeviceTypeInitDao.getTableName() + " set MRID=?, CATEGORY=?, SUBCATEGORY=?,MODEL=?,OBJECTID=?,LOGO=?,DESCRIPTION=?  where DTID=?";
		 PreparedStatement pstmt_updateDev=con.prepareStatement(sqls_updateDev);
		 String[] sqlsCol_updateDev= new String[]{"MRID","CATEGORY","SUBCATEGORY","MODEL","OBJECTID","LOGO","DESCRIPTION","DTID"};	
	     String[] sqlsCol_insertDev= new String[]{"MRID","DTID","CATEGORY","SUBCATEGORY","MODEL","OBJECTID","LOGO","DESCRIPTION"};
//	     System.out.println("before delete from " +  TDeviceTypeInitDao.getTableName() + " :" +  TDeviceTypeInitDao.findAll().size());
	     pstmt_delDev.execute();
//	     System.out.println("after delete from " +  TDeviceTypeInitDao.getTableName() + " :" +  TDeviceTypeInitDao.findAll().size());
	     
	     long id_cate = 0;
	     long mrid_mf = 0;
	     long dtid_dev = 0;
	     for(int r=1; r<sheet.getRows(); r++){
	    	boolean ifUpdate_cate = false;
	    	boolean ifUpdate_mf = false;
	    	boolean ifUpdate_dev = false;
	    	try{
	    		 id_cate = Long.parseLong(sheet.getCell(mapping_cate.get("ID").getIndex(), r).getContents());
	    		 mrid_mf = Long.parseLong(sheet.getCell(mapping_mf.get("MRID").getIndex(), r).getContents());
	    		 dtid_dev = Long.parseLong(sheet.getCell(mapping_dev.get("DTID").getIndex(), r).getContents());

//	    	     System.out.println("row=" + r + "\tidcate=" + id_cate + "\tmrid_mf=" + mrid_mf + "\tdtid_dev" + dtid_dev);	    	     
	    	}catch(Exception ew){
	    		 ew.printStackTrace();
	    		 mark = -3;
	    		 appendErrorMessage(VMfCateDevtypeDao.getTableName(),sheet.getRow(r), errorMsgWb, "ID/MRID/DTID数据类型错误，无法解析", columnStrs);
	    		 continue;
	    	}
	    	
	    	//check whether to insert or update table
	    	TCategoryMapInit tcateList = TCategoryMapInitDao.findByPrimaryKey(id_cate);
	    	if(tcateList != null){//update
	    		ifUpdate_cate = true;
	    	}
	    	TManufacturerInfoInit tmfList = TManufacturerInfoInitDao.findByPrimaryKey(mrid_mf);
	    	if(tmfList != null){//update
	    		ifUpdate_mf = true;
	    	}
	    	TDeviceTypeInit tdev = TDeviceTypeInitDao.findByPrimaryKey(dtid_dev);
	    	if(tdev !=null)
	    		ifUpdate_dev = true;
	    	
	    	int markTmpCate = prepareInsertUpdateView(VMfCateDevtypeDao.getTableName(),ifUpdate_cate,sqlsCol_insertCate, sheet, mapping_cate, 
	    			r, errorMsgWb,
	    			columnStrs, pstmt_insertCate, pstmt_updateCate,sqlsCol_updateCate);
	    	if(markTmpCate == -3){
	    		mark = -3;
	    		continue;
	    	}else if(markTmpCate == -1){
	    		pstmt_updateCate.close();
	    		pstmt_insertCate.close();
	    		pstmt_delDev.close();
		    	con.close();
		  	    return markTmpCate;
	    	}
	    	
	    	int markTmpMf = prepareInsertUpdateView(VMfCateDevtypeDao.getTableName(),ifUpdate_mf,sqlsCol_insertMf, 
	    			sheet, mapping_mf, r, errorMsgWb, columnStrs, pstmt_insertMf, pstmt_updateMf,sqlsCol_updateMf);
	    	if(markTmpMf== -3){
	    		mark = -3;
	    		continue;
	    	}else if(markTmpMf == -1){
	    		pstmt_updateMf.close();
	    		pstmt_insertMf.close();
	    		pstmt_delDev.close();
		    	con.close();
		  	    return markTmpMf;
	    	}
	    	
	    	int markTmpDev = prepareInsertUpdateView(VMfCateDevtypeDao.getTableName(),ifUpdate_dev,sqlsCol_insertDev, 
	    			sheet, mapping_dev, r, errorMsgWb, columnStrs, pstmt_insertDev, pstmt_updateDev,sqlsCol_updateDev);
	    	if(markTmpDev== -3){
	    		mark = -3;
	    		continue;
	    	}else if(markTmpDev == -1){
	    		pstmt_updateDev.close();
	    		pstmt_insertDev.close();
	    		pstmt_delDev.close();
		    	con.close();
		  	    return markTmpDev;
	    	}
	    	
	    	//execute sql for this row
    		try{
//    			System.out.println("\tbefore execute SQL********************");
	    		if(ifUpdate_cate)
	    			pstmt_updateCate.execute();
	    		else
	    			pstmt_insertCate.execute();
	    		if(ifUpdate_mf)
	    			pstmt_updateMf.execute();
	    		else
	    			pstmt_insertMf.execute();
	    		if(ifUpdate_dev)
	    			pstmt_updateDev.execute();
	    		else
	    			pstmt_insertDev.execute();

//    			System.out.println("\tAfter execute SQL********************");    		
    		}catch (Exception e) {
				mark = -3;
				appendErrorMessage(VMfCateDevtypeDao.getTableName(),sheet.getRow(r), errorMsgWb, "SQL错误: 提示： ObjectID及MRName在数据库中必须唯一\n详细信息:"+ e.getMessage(), columnStrs);
				e.printStackTrace();
	    		continue;
    		}
//	    	System.out.println("in for row=" + r+ " TCategoryMapInitDao:" + TCategoryMapInitDao.findAll().size());
//	    	System.out.println("in for row=" + r+ " TManufacturerInfoInitDao:" + TManufacturerInfoInitDao.findAll().size());
//	    	System.out.println("in for row=" + r+ " TDeviceTypeInitDao:" + TDeviceTypeInitDao.findAll().size());
	     }//end of for row

		pstmt_updateCate.close();
		pstmt_insertCate.close();
		pstmt_updateDev.close();
		pstmt_insertDev.close();
		pstmt_updateMf.close();
		pstmt_insertMf.close();
		pstmt_delDev.close();	
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheet.getName() + "失败:" + e.getMessage());
		}

	     return mark;
	}



	/**
	 * Import from excel to def_mib_grp table
	 * @param sheet
	 * @param errorMsgWb
	 * @param columnStrs
	 * @param mapping
	 * @param con
	 * @return
	 */
	private int importdefMibGrp(Sheet sheet, HSSFWorkbook errorMsgWb,
			String[] columnStrs, Map<String, ExcelTableMapping> mapping,
			Connection con) {
		int mark=1;
		try{
			String sqls_del="delete from "+ defMibGrpDao.getTableName();
			 PreparedStatement pstmt_del=con.prepareStatement(sqls_del);
	 	     
			 String sqls_insert="insert into "+ defMibGrpDao.getTableName() + " (MID, Name, INDEXOID, INDEXVAR, DESCROID, DESCRVAR)"+" values(?,?,?,?,?,?)";
			 PreparedStatement pstmt_insert=con.prepareStatement(sqls_insert);
	 	     
			 String sqls_update="Update "+ defMibGrpDao.getTableName() + " set NAME=?, INDEXOID=?, INDEXVAR=?, DESCROID=?, DESCRVAR=? where MID=?";
			 PreparedStatement pstmt_update=con.prepareStatement(sqls_update);
			 String[] sqlsCol_update= new String[]{"NAME", "INDEXOID", "INDEXVAR", "DESCROID","DESCRVAR","MID"};	
	 	     String[] sqlsCol_insert= new String[]{"MID","NAME","INDEXOID","INDEXVAR", "DESCROID","DESCRVAR"};		
	 	      
//	 	     System.out.println("before delete:" + defMibGrpDao.findAll().size());
	 	     pstmt_del.execute();
//	 	     System.out.println("after delete:" + defMibGrpDao.findAll().size());
	 	    
	 	     long mid = 0;
	 	     for(int r=1; r<sheet.getRows(); r++){
	 	    	boolean parseExcelError = false;
	 	    	boolean ifUpdate = false;
	 	    	try{
	 	    		 mid = Long.parseLong(sheet.getCell(mapping.get("MID").getIndex(), r).getContents());
	 	    	}catch(Exception ew){
	 	    		 ew.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage(defMibGrpDao.getTableName(),sheet.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrs);
		    		  continue;
		    	}
	 	    	//check whether to insert or update t_policy_base
		    	  DefMibGrp tmfList = defMibGrpDao.findByPrimaryKey(mid);
		    	  try{
		    		  if(tmfList != null){//update
		    			  ifUpdate = true;
		    		  }
		    	  }catch(Exception e){
		    		  appendErrorMessage(defMibGrpDao.getTableName(),sheet.getRow(r), errorMsgWb, "MRID 数据类型错误，无法解析", columnStrs);
		    		  mark = -3;
		    		  parseExcelError = true;
		    		  e.printStackTrace();
		    		  continue;
		    	  }
		    	  
		    	  int markTmp = insertUpdateTable(defMibGrpDao.getTableName(),ifUpdate,sqlsCol_insert, sheet, mapping, 
		    				r, parseExcelError, errorMsgWb,
		    				columnStrs, pstmt_insert, pstmt_update,sqlsCol_update);
		    	  if(markTmp == -3){
		    		  mark = -3;
		    		  continue;
		    	  }else if(markTmp == -1){
		    		  pstmt_insert.close();
		    		  pstmt_update.close();
		    		  pstmt_del.close();
		    		  con.close();
		  	      	  return markTmp;
		    	  }
//		    	  System.out.println("in for i=" + r+ " delete:" + defMibGrpDao.findAll().size());
	 	     }//end of for row
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheet.getName() + "失败:" + e.getMessage());
		}
	     return mark;
	}


	/**
	 * Import from excel to table:T_Category_Map_Init
	 * @param sheet
	 * @param errorMsgWb
	 * @param columnStrs
	 * @param mapping
	 * @param con
	 * @return
	 */
	private int importTCategoryMapInit(Sheet sheet, HSSFWorkbook errorMsgWb,
			String[] columnStrs, Map<String, ExcelTableMapping> mapping,
			Connection con) {
		int mark=1;
		try{
			String sqls_del="delete from "+ TCategoryMapInitDao.getTableName();
		 PreparedStatement pstmt_del=con.prepareStatement(sqls_del);
	     
		 String sqls_insert="insert into "+ TCategoryMapInitDao.getTableName() + " (ID, Name, Flag)"+" values(?,?,?)";
		 PreparedStatement pstmt_insert=con.prepareStatement(sqls_insert);
	     
		 String sqls_update="Update "+ TCategoryMapInitDao.getTableName() + " set Name=?, Flag=? where ID=?";
		 PreparedStatement pstmt_update=con.prepareStatement(sqls_update);
		 String[] sqlsCol_update= new String[]{"Name", "Flag", "ID"};	
	     String[] sqlsCol_insert= new String[]{"ID","Name","Flag"};		
	      
//	     System.out.println("before delete:" + TCategoryMapInitDao.findAll().size());
	     pstmt_del.execute();
//	     System.out.println("after delete:" + TCategoryMapInitDao.findAll().size());
	    
	     long id = 0;
	     for(int r=1; r<sheet.getRows(); r++){
	    	boolean parseExcelError = false;
	    	boolean ifUpdate = false;
	    	try{
	    		 id = Long.parseLong(sheet.getCell(mapping.get("ID").getIndex(), r).getContents());
	    	}catch(Exception ew){
	    		 ew.printStackTrace();
	    		  mark = -3;
	    		  appendErrorMessage(TCategoryMapInitDao.getTableName(),sheet.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrs);
	    		  continue;
	    	}
	    	//check whether to insert or update t_policy_base
	    	  TCategoryMapInit tmfList = TCategoryMapInitDao.findByPrimaryKey(id);
	    	  try{
	    		  if(tmfList != null){//update
	    			  ifUpdate = true;
	    		  }
	    	  }catch(Exception e){
	    		  appendErrorMessage(TCategoryMapInitDao.getTableName(),sheet.getRow(r), errorMsgWb, "MRID 数据类型错误，无法解析", columnStrs);
	    		  mark = -3;
	    		  parseExcelError = true;
	    		  e.printStackTrace();
	    		  continue;
	    	  }
	    	  
	    	  int markTmp = insertUpdateTable(TCategoryMapInitDao.getTableName(),ifUpdate,sqlsCol_insert, sheet, mapping, 
	    				r, parseExcelError, errorMsgWb,
	    				columnStrs, pstmt_insert, pstmt_update,sqlsCol_update);
	    	  if(markTmp == -3){
	    		  mark = -3;
	    		  continue;
	    	  }else if(markTmp == -1){
	    		  pstmt_insert.close();
	    		  pstmt_update.close();
	    		  pstmt_del.close();
	    		  con.close();
	  	      	  return markTmp;
	    	  }
//	    	  System.out.println("in for row=" + r+ " delete:" + TCategoryMapInitDao.findAll().size());
	     }//end of for row
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheet.getName() + "失败:" + e.getMessage());
		}
	     return mark;
	}


	/**
	 * Import from excel to table:T_Manufacturer_Info_Init
	 * @param sheet
	 * @param errorMsgWb
	 * @param columnStrs
	 * @param mapping
	 * @param con
	 * @return
	 */
	private int importTManufacturerInfoInit(Sheet sheet,
			HSSFWorkbook errorMsgWb, String[] columnStrs, Map<String, ExcelTableMapping> mapping, Connection con) {
		int mark = 1;
		try{
		String sqls_del="delete from "+ TManufacturerInfoInitDao.getTableName();
		 PreparedStatement pstmt_del=con.prepareStatement(sqls_del);
	     
		 String sqls_insert="insert into "+ TManufacturerInfoInitDao.getTableName() + " (MRID, MRName, ObjectID, Description)"+" values(?,?,?,?)";
		 PreparedStatement pstmt_insert=con.prepareStatement(sqls_insert);
	     
		 String sqls_update="Update "+ TManufacturerInfoInitDao.getTableName() + " set MRName=?, ObjectID=?, Description=? where MRID=?";
		 PreparedStatement pstmt_update=con.prepareStatement(sqls_update);
		 String[] sqlsCol_update= new String[]{"MRName","ObjectID", "Description", "MRID"};	
	     String[] sqlsCol_insert= new String[]{"MRID","MRName","ObjectID", "Description"};		
	      
	     pstmt_del.execute();
	    
	     long mrid = 0;
	     for(int r=1; r<sheet.getRows(); r++){
	    	boolean parseExcelError = false;
	    	boolean ifUpdate = false;
	    	try{
	    		 mrid = Long.parseLong(sheet.getCell(mapping.get("MRID").getIndex(), r).getContents());
	    	}catch(Exception ew){
	    		 ew.printStackTrace();
	    		  mark = -3;
	    		  appendErrorMessage(TManufacturerInfoInitDao.getTableName(),sheet.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrs);
	    		  continue;
	    	}
	    	//check whether to insert or update t_policy_base
	    	  List<TManufacturerInfoInit> tmfList = TManufacturerInfoInitDao.findWhereMridEquals(mrid);
	    	  try{
	    		  if(tmfList != null && tmfList.size()>0 ){//update
	    			  if(tmfList.size()>1){
   		    		  appendErrorMessage(TManufacturerInfoInitDao.getTableName(),sheet.getRow(r), errorMsgWb, "MRID在数据库中记录不唯一，请核查数据库一致性", columnStrs);
   		    		  mark = -3;
   		    		  parseExcelError = true;
   		    		  continue;
	    			  }
		    		  ifUpdate = true;
	    		  }
	    	  }catch(Exception e){
	    		  appendErrorMessage(TManufacturerInfoInitDao.getTableName(),sheet.getRow(r), errorMsgWb, "MRID 数据类型错误，无法解析", columnStrs);
	    		  mark = -3;
	    		  parseExcelError = true;
	    		  e.printStackTrace();
	    		  continue;
	    	  }
	    	  
	    	  int markTmp = insertUpdateTable(TManufacturerInfoInitDao.getTableName(),ifUpdate,sqlsCol_insert, sheet, mapping, 
	    				r, parseExcelError, errorMsgWb,
	    				columnStrs, pstmt_insert, pstmt_update,sqlsCol_update);
	    	  if(markTmp == -3){
	    		  mark = -3;
	    		  continue;
	    	  }else if(markTmp == -1){
	    		  pstmt_insert.close();
	    		  pstmt_update.close();
	    		  pstmt_del.close();
	    		  con.close();
	  	      	  return markTmp;
	    	  }
//	    	  System.out.println("in for r= " + r + "  delete:" + TManufacturerInfoInitDao.findAll().size());
	 	     
	     }//end of for row
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();

			Log4jInit.ncsLog.error("导入 " + sheet.getName() + "失败:" + e.getMessage());
		}
		return mark;
	}



	/**
	 * 
	 * @param ifUpdate
	 * @param sqlsCol_insert
	 * @param sheet
	 * @param mapping
	 * @param r
	 * @param parseExcelError
	 * @param errorMsgWb
	 * @param columnStrs
	 * @param pstmt_insert
	 * @param pstmt_update
	 * @param sqlsCol_update
	 * @return
	 */
	private int insertUpdateTable(String tabName,boolean ifUpdate,
			String[] sqlsCol_insert, Sheet sheet,
			Map<String, ExcelTableMapping> mapping, int r,
			boolean parseExcelError, HSSFWorkbook errorMsgWb,
			String[] columnStrs, PreparedStatement pstmt_insert,
			PreparedStatement pstmt_update, String[] sqlsCol_update) {
			
			int mark = 1;
			if(!ifUpdate){

				for(int aa=1; aa<=sqlsCol_insert.length; aa++){
					ExcelTableMapping eTmp = mapping.get(sqlsCol_insert[aa-1].toUpperCase());
					String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
//					System.out.println("iterator=" + aa + " ColumnName=" + columnStrs[aa-1]+" cell content=" + cellContentTmp+"");
		    	  	if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
				    	mark  = -3;
				    	parseExcelError = true;
				    	appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
				    	break;
		    	  	}else{
			    	
	  		    	  try{
	  		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
	  		    			if(cellContentTmp == null || cellContentTmp.equals("null"))
	  		    				pstmt_insert.setNull(aa, java.sql.Types.NUMERIC);
			    			  else
			    				  pstmt_insert.setLong(aa,Long.parseLong(cellContentTmp));
//	  		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
	  		    		  }else if(eTmp.getType().equalsIgnoreCase("DATE")){
	  		    			if(cellContentTmp == null || cellContentTmp.equals("null"))
	  		    				pstmt_insert.setNull(aa, java.sql.Types.DATE);
			    			  else{
			    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yy-MM-dd");
			    				  pstmt_insert.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
			    			  }
//	  		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
	  		    		  }else if(eTmp.getType().equalsIgnoreCase("varchar2")){
	  		    			if(cellContentTmp == null || cellContentTmp.equals("null"))
	  		    				pstmt_insert.setNull(aa, java.sql.Types.VARCHAR);
			    			  else
			    				  pstmt_insert.setString(aa,cellContentTmp);
//	  		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
	  		    		  }
	  		    	  }catch(Exception e){
	  		    		  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName()+"数据类型错误，无法解析", columnStrs);
	  		    		  mark = -3;
	  		    		  parseExcelError = true;
	  		    		  break;
	  		    	  }
			    	  }
		    	  }

		    	  if(!parseExcelError){
	  				  try {
	  					pstmt_insert.execute();
					} catch (Exception e) {
						mark = -3;
						appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+e.getMessage(), columnStrs);
						e.printStackTrace();
					}	
		    	  }
	  	  }else{//update
					  for(int aa=1; aa<=sqlsCol_update.length; aa++){
	  		    	   ExcelTableMapping eTmp = mapping.get(sqlsCol_update[aa-1].toUpperCase());
		  		    	
	  		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
	  		    	  
//	  		    	System.out.println("iterator=" + aa + " ColumnName=" + sqlsCol_update[aa-1]+" cell content=" + cellContentTmp+"\n");
	  		    	if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
	  		    		  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
	  			    	  mark = -3;
	  		    		  parseExcelError = true;
	 		    		  break;
	  		    	  }else{
		    		    	  try{
		    		    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
		    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
		    		    				  pstmt_update.setNull(aa, java.sql.Types.VARCHAR);
		    		    			  else	    			    				  
		    		    				  pstmt_update.setString(aa,cellContentTmp);
//		    		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
		    		    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
		    		    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
		    		    				  pstmt_update.setNull(aa, java.sql.Types.NUMERIC);
		    			    			  else
		    			    				  pstmt_update.setLong(aa,Long.parseLong(cellContentTmp));
//		    		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
		    		    	  	  }else if(eTmp.getType().equalsIgnoreCase("date")){
		    		    	  		if(cellContentTmp == null || cellContentTmp.equals("null"))
		    		    	  			pstmt_update.setNull(aa, java.sql.Types.DATE);
		    		    			  else{
		    		    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    		    				  pstmt_update.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
		    		    			  }
//		    		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
		    		    		  }
		    		    	  }catch(Exception ew){
		    		    		  mark = -3;
		    		    		  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName()+" 数据类型错误，无法解析", columnStrs);
	    				    		    		  ew.printStackTrace();
//		    		    		  System.out.print("In insert catch:aa=" + aa + " type=" + eTmp.getType() +  " "+eTmp.getName()+" 数据类型错误，无法解析");
		      		    		  parseExcelError = true;
		    		    		  break;
		    		    	  }
	  		    	  }
			    	  }
					  if(!parseExcelError){
//						  System.out.println("==Step 4: Execute update command");
						  try {
							pstmt_update.execute();
						  } catch (SQLException e) {
							  mark = -3;						  
							  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+e.getMessage(), columnStrs);
							  e.printStackTrace();
						  }
					  }
	  	  }
			return mark;
		
	}




	private int prepareInsertUpdateView(String tabName,
			boolean ifUpdate, String[] sqlsCol_insert, Sheet sheet,
			Map<String, ExcelTableMapping> mapping, int r,
			HSSFWorkbook errorMsgWb,
			String[] columnStrs, PreparedStatement pstmt_insert,
			PreparedStatement pstmt_update, String[] sqlsCol_update) {

		int mark = 1;
		try{
			if(!ifUpdate){
//				System.out.println("prepare update:");
				for(int aa=1; aa<=sqlsCol_insert.length; aa++){
					ExcelTableMapping eTmp = mapping.get(sqlsCol_insert[aa-1].toUpperCase());
					String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
//					System.out.print("\tcellContent=" + cellContentTmp+"(" + eTmp.getType()+":" + eTmp.getIndex()+")");
					if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
				    	mark  = -3;
				    	appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	

						break;
					}else{
						try{
							if(eTmp.getType().equalsIgnoreCase("NUMBER")){
								if(cellContentTmp == null || cellContentTmp.equals("null"))
									pstmt_insert.setNull(aa, java.sql.Types.NUMERIC);
								else
									pstmt_insert.setLong(aa,Long.parseLong(cellContentTmp));
							}else if(eTmp.getType().equalsIgnoreCase("DATE")){
								if(cellContentTmp == null || cellContentTmp.equals("null"))
									pstmt_insert.setNull(aa, java.sql.Types.DATE);
								else{
									java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yy-MM-dd");
									pstmt_insert.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
								}
	  		    		  	}else if(eTmp.getType().equalsIgnoreCase("varchar2")){
	  		    		  		if(cellContentTmp == null || cellContentTmp.equals("null"))
	  		    		  			pstmt_insert.setNull(aa, java.sql.Types.VARCHAR);
	  		    		  		else
	  		    		  			pstmt_insert.setString(aa,cellContentTmp);
	  		    		  	}
		  		    	  }catch(Exception e){
		  		    		  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName()+"数据类型错误，无法解析", columnStrs);
		  		    		  mark = -3;
		  		    		  break;
		  		    	  }
			    	  }
		    	  }
//				System.out.println("end of prepare");
			}else{//update
	  		  for(int aa=1; aa<=sqlsCol_update.length; aa++){
	  			  ExcelTableMapping eTmp = mapping.get(sqlsCol_update[aa-1].toUpperCase());
	  			  String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
	  			  if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
	  				  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
	  				  mark = -3;
	  				  break;
	  			  }else{
	  				  try{
			    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
			    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
			    				  pstmt_update.setNull(aa, java.sql.Types.VARCHAR);
			    			  else	    			    				  
			    				  pstmt_update.setString(aa,cellContentTmp);
			    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
			    			  if(cellContentTmp == null || cellContentTmp.equals("null"))
			    				  pstmt_update.setNull(aa, java.sql.Types.NUMERIC);
			    			  else
			    				  pstmt_update.setLong(aa,Long.parseLong(cellContentTmp));
			    	  	  }else if(eTmp.getType().equalsIgnoreCase("date")){
			    	  		  if(cellContentTmp == null || cellContentTmp.equals("null"))
			    	  			  pstmt_update.setNull(aa, java.sql.Types.DATE);
			    	  		  else{
			    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			    				  pstmt_update.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
			    			  }
			    		  }
		    		  }catch(Exception ew){
		    			  mark = -3;
		    			  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName()+" 数据类型错误，无法解析", columnStrs);
		    			  ew.printStackTrace();
		    			  break;
		    		  }
	  		    }
	  		}
	  	  }
		}catch(Exception e){
			e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + tabName + "失败:" + e.getMessage());
			return -1;
		}
		
		return mark;
	}
	
	
	/**
	 * Init mapping only for table :
	 * @param rs
	 * @param columnStrs
	 * @param mapping
	 * @param tableName
	 * @return
	 */
	private int initTableMapping(ResultSetMetaData rs, String[] columnStrs, Map<String, ExcelTableMapping> mapping, String tableName){
		int count = 0;
		 try {
			 for(int i=1; i<=rs.getColumnCount(); i++){
					ExcelTableMapping et = new ExcelTableMapping();
					String name = rs.getColumnName(i);
					String type = rs.getColumnTypeName(i);
					String isNull = rs.isNullable(i)==0?"N":"Y";
				  et.setName(name);
				  et.setType(type);
				  et.setNull(isNull.equalsIgnoreCase("Y")?true:false);
				  if(tableName.equalsIgnoreCase("SNMP_EVENTS_PROCESS"))
					  et.setNull(false);
//				  System.out.println("tableName is:" + tableName + "\tFrom SQL: name=" + name + " \ttype=" + type + "\tisnull=" + isNull);
				  //find index in columnStrs
				  et.setIndex(-1);
				  for(int ii=0; ii<columnStrs.length; ii++){
					  if(columnStrs[ii].equalsIgnoreCase(name)){
						  et.setIndex(ii);
						  break;
					  }
				  }
				  if(et.getIndex() == -1){	//no corresponding column in excel. excel file error

					  Log4jInit.ncsLog.error("导入 " + tableName + "失败:" + et.getName() + "在Excel中无对应列");
					  return -2;
				  }
				  mapping.put(et.getName().toUpperCase(), et);
//				  System.out.println("@@@@@@@@@@mapping table Columnname=" + et.getName() + "\t isNullable:" + et.isNull() + "\tIndex in Excel=" + et.getIndex());
				  
				  count++;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + tableName + "失败:" + e.getMessage());
			return -1;
		}
		  return 1;
	}

	
	/**
	 * Init mapping only for table :
	 * @param rs
	 * @param columnStrs
	 * @param mapping
	 * @param tableName
	 * @return
	 */
	private int initTableMappingPeriod(ResultSetMetaData rsmeta, String[] columnStrs, Map<String, ExcelTableMapping> mapping, String tableName){
		int count = 0;
		 try {
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

//				  System.out.println("Count:" + count + "Column:"+ name+ " " + type + " "+ isNull + " index=" + et.getIndex());
				  mapping.put(et.getName().toUpperCase(), et);
				  count++;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + tableName + "失败:" + e.getMessage());
			return -1;
		}
		  return 1;
	}

	private int initTableMappingSyslog(ResultSetMetaData rs, String[] columnStrs, Map<String, ExcelTableMapping> mapping, String tableName){
		try{
			for(int i=1; i<=rs.getColumnCount(); i++){
				ExcelTableMapping et = new ExcelTableMapping();
				String name = rs.getColumnName(i);
				String type = rs.getColumnTypeName(i);
				String isNull = rs.isNullable(i)==0?"N":"Y";
				  et.setName(name);
				  et.setType(type);

//				  et.setNull(isNull.equalsIgnoreCase("Y")?true:false);
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

//				  System.out.println("in InitMappingSyslog: Column Name" + et.getName() + "\tIndex:" + et.getIndex()) ;
				  if(et.getIndex() == -1){	//no corresponding column in excel. excel file error

					  Log4jInit.ncsLog.error("导入 " + tableName + "失败:" + et.getName() + "在Excel中无对应列");
					  return -2;
				  }

				  mapping.put(et.getName().toUpperCase(), et);
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + tableName + "失败:" + e.getMessage());
			return -1;
		}
		  return 1;
	}
	
	
	/**
	 * Init view
	 * @param rs
	 * @param columnStrs
	 * @param mapping
	 * @param viewName
	 * @return
	 */
	private int initViewMapping(ResultSetMetaData rs, String[] columnStrs,
			Map<String, ExcelTableMapping> mapping, String viewName, String tableName) {
		int count = 0;
		
		try {
			for(int i=1; i<=rs.getColumnCount(); i++){
				ExcelTableMapping et = new ExcelTableMapping();
				String name = rs.getColumnName(i);
				String type = rs.getColumnTypeName(i);
				String isNull = rs.isNullable(i)==0?"N":"Y";
				et.setName(name);
				et.setType(type);
				et.setNull(isNull.equalsIgnoreCase("Y")?true:false);
//				System.out.println("view is:" + viewName + "\ttable name:" + tableName + "\tFrom SQL: name=" + name + " \ttype=" + type + "\tisnull=" + isNull);
				//find index in columnStrs
				et.setIndex(-1);
				for(int ii=0; ii<columnStrs.length; ii++){
					if(viewName.equalsIgnoreCase(VMfCateDevtypeDao.getTableName()) 
							&& tableName.equalsIgnoreCase(TManufacturerInfoInitDao.getTableName())){
						if((columnStrs[ii].equalsIgnoreCase("MF_OBJECTID") && name.equalsIgnoreCase("OBJECTID"))
								||(columnStrs[ii].equalsIgnoreCase("MF_DESCRIPTION") && name.equalsIgnoreCase("DESCRIPTION"))){
							et.setIndex(ii);
							break;
						}
						if(!name.equalsIgnoreCase("OBJECTID") &&  !name.equalsIgnoreCase("DESCRIPTION") && columnStrs[ii].equalsIgnoreCase(name)){
							et.setIndex(ii);
							break;
						}
					}else if(viewName.equalsIgnoreCase(VMfCateDevtypeDao.getTableName()) 
							&& tableName.equalsIgnoreCase(TCategoryMapInitDao.getTableName())){
						if(columnStrs[ii].equalsIgnoreCase(name)){
							et.setIndex(ii);
							break;
						}
					}else if(viewName.equalsIgnoreCase(VMfCateDevtypeDao.getTableName()) 
							&& tableName.equalsIgnoreCase(TDeviceTypeInitDao.getTableName())){
						if((columnStrs[ii].equalsIgnoreCase("devicetype_MRID") && name.equalsIgnoreCase("MRID"))){
							et.setIndex(ii);
							break;
						}
						if(!name.equalsIgnoreCase("MRID") && columnStrs[ii].equalsIgnoreCase(name)){
							et.setIndex(ii);
							break;
						}
					}else if(viewName.equalsIgnoreCase(VEventTypeDao.getTableName()) 
							&& tableName.equalsIgnoreCase(TEventTypeInitDao.getTableName())){
						if(columnStrs[ii].equalsIgnoreCase(name)){
							et.setIndex(ii);
							break;
						}
					}else if(viewName.equalsIgnoreCase(VEventTypeDao.getTableName()) 
							&& tableName.equalsIgnoreCase(TModuleInfoInitDao.getTableName())){
						if((columnStrs[ii].equalsIgnoreCase("T_MODULE_INFO_INIT_MODID") && name.equalsIgnoreCase("MODID"))
								||(columnStrs[ii].equalsIgnoreCase("T_MODULE_INFO_INIT_DESCRIPTION") && name.equalsIgnoreCase("DESCRIPTION"))){
							et.setIndex(ii);
							break;
						}
						if(!name.equalsIgnoreCase("MODID") && !name.equalsIgnoreCase("DESCRIPTION") && columnStrs[ii].equalsIgnoreCase(name)){
							et.setIndex(ii);
							break;
						}
					}else if(viewName.equalsIgnoreCase(VOidGroupDao.getTableName()) 
							&& tableName.equalsIgnoreCase(TOidgroupInfoInitDao.getTableName())){
						if((columnStrs[ii].equalsIgnoreCase("T_OIDGROUP_INFO_INIT_OPID") && name.equalsIgnoreCase("OPID"))){
							et.setIndex(ii);
							break;
						}
						if(!name.equalsIgnoreCase("OPID") && columnStrs[ii].equalsIgnoreCase(name)){
							et.setIndex(ii);
							break;
						}
					}else if(viewName.equalsIgnoreCase(VOidGroupDao.getTableName()) 
							&& tableName.equalsIgnoreCase(TOidgroupDetailsInitDao.getTableName())){
						if((columnStrs[ii].equalsIgnoreCase("T_OIDGROUP_DETAILS_INIT_OPID") && name.equalsIgnoreCase("OPID"))){
							et.setIndex(ii);
							break;
						}
						if(!name.equalsIgnoreCase("OPID") && columnStrs[ii].equalsIgnoreCase(name)){
							et.setIndex(ii);
							break;
						}
					}else if(viewName.equalsIgnoreCase(VPerformParamDao.getTableName()) && tableName.equalsIgnoreCase(TEventTypeInitDao.getTableName())){
						if((columnStrs[ii].equalsIgnoreCase("eventtype_MODID") && name.equalsIgnoreCase("MODID"))
								||(columnStrs[ii].equalsIgnoreCase("eventtype_EVEID") && name.equalsIgnoreCase("EVEID"))
								|| (columnStrs[ii].equalsIgnoreCase("eventtype_DESCRIPTION") && name.equalsIgnoreCase("DESCRIPTION"))){
							et.setIndex(ii);
							break;
						}
						if(!name.equalsIgnoreCase("MODID") && !name.equalsIgnoreCase("EVEID") 
								&& !name.equalsIgnoreCase("DESCRIPTION") && columnStrs[ii].equalsIgnoreCase(name)){
							et.setIndex(ii);
							break;
						}
					}else if(viewName.equalsIgnoreCase(VPerformParamDao.getTableName()) 
							&& tableName.equalsIgnoreCase(TOidgroupInfoInitDao.getTableName())){
						if((columnStrs[ii].equalsIgnoreCase("oidgroup_OPID") && name.equalsIgnoreCase("OPID"))
								||(columnStrs[ii].equalsIgnoreCase("oidgroup_OIDGROUPNAME") && name.equalsIgnoreCase("OIDGROUPNAME"))
								|| (columnStrs[ii].equalsIgnoreCase("oidgroup_DESCRIPTION") && name.equalsIgnoreCase("DESCRIPTION"))){
							et.setIndex(ii);
							break;
						}
						if(!name.equalsIgnoreCase("OPID") && !name.equalsIgnoreCase("OIDGROUPNAME") 
								&& !name.equalsIgnoreCase("DESCRIPTION") && columnStrs[ii].equalsIgnoreCase(name)){
							et.setIndex(ii);
							break;
						}
					}else if(viewName.equalsIgnoreCase(VPerformParamDao.getTableName()) 
							&& tableName.equalsIgnoreCase(TEventOidInitDao.getTableName())){
						if(columnStrs[ii].equalsIgnoreCase(name)){
							et.setIndex(ii);
							break;
						}
					}else if(viewName.equalsIgnoreCase(VPerformParamDao.getTableName()) 
							&& tableName.equalsIgnoreCase(TDeviceTypeInitDao.getTableName())){
						if((columnStrs[ii].equalsIgnoreCase("devtype_MRID") && name.equalsIgnoreCase("MRID"))
								||(columnStrs[ii].equalsIgnoreCase("devtype_DTID") && name.equalsIgnoreCase("DTID"))
								|| (columnStrs[ii].equalsIgnoreCase("devtype_DESCRIPTION") && name.equalsIgnoreCase("DESCRIPTION"))){
							et.setIndex(ii);
							break;
						}
						if(!name.equalsIgnoreCase("MRID") && !name.equalsIgnoreCase("DTID") 
								&& !name.equalsIgnoreCase("DESCRIPTION") && columnStrs[ii].equalsIgnoreCase(name)){
							et.setIndex(ii);
							break;
						}
					}
				}

				if(et.getIndex() == -1){	//no corresponding column in excel. excel file error
//					System.out.println("In return -2, no column:" + "column in db:" + name);

					Log4jInit.ncsLog.error("导入 " + tableName + "失败:" + et.getName() + "在Excel中无对应列");
					return -2;
				}
//				System.out.println("View Name:" + viewName + "\ttable name:" + tableName + " column:" + et.getName() + " index in excel:" + et.getIndex());
				mapping.put(et.getName().toUpperCase(), et);
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + tableName + "失败:" + e.getMessage());
			return -1;
		}
		return 1;
	}
	/**
	 * 
	 * @param row
	 * @param errorMsgWb
	 * @param errorMsg
	 * @param colTitles
	 */
	private void appendErrorMessage(String tabName,Cell[] row, HSSFWorkbook errorMsgWb,String errorMsg, String[] colTitles) {
		//set title style
		HSSFCellStyle titleStyle = errorMsgWb.createCellStyle();
//		System.out.println("in appendErrorMessage table name=" + tabName + "\trow" + row);
		try{
			HSSFSheet tfSheet = errorMsgWb.getSheet("ErrorMsg_"+tabName);
			if(tfSheet == null){
				tfSheet = errorMsgWb.createSheet("ErrorMsg_" + tabName);//create a sheet
//				System.out.println("in appendErrorMessage create sheet:" + "ErrorMessage_" + tabName);
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
			Log4jInit.ncsLog.error("导入 " + tabName + "失败:" + errorMsg);
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
//				System.out.print("Error message =" + temp );
			}
		}catch(Exception e){
			e.printStackTrace();

			Log4jInit.ncsLog.error("导入 " + tabName + "失败:" + e.getMessage());
		}
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
	
	private int initTableMappingSyslogUsingMeta(ResultSetMetaData rs, String[] columnStrs, Map<String, ExcelTableMapping> mapping, String tableName){
		try{
			for(int i=1; i<=rs.getColumnCount(); i++){
				  ExcelTableMapping et = new ExcelTableMapping();
				  String name = rs.getColumnName(i);
				  String type = rs.getColumnTypeName(i);
				  String isNull = rs.isNullable(i)==0?"N":"Y";
				  et.setName(name);
				  et.setType(type);
//				  System.out.println("%%%%%%%i=" + i + "ColumnName:" + name + "\tType=" + type + "\t nullable:" + isNull);
//				  et.setNull(isNull.equalsIgnoreCase("Y")?true:false);
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

//				  System.out.println("in InitMappingSyslog: Column Name" + et.getName() + "\tIndex:" + et.getIndex()) ;
				  if(et.getIndex() == -1){	//no corresponding column in excel. excel file error

					  Log4jInit.ncsLog.error("导入 " + tableName + "失败:" + et.getName() + "在Excel中无对应列");
					  return -2;
				  }

				  mapping.put(et.getName().toUpperCase(), et);
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + tableName + "失败:" + e.getMessage());
			return -1;
		}
		  return 1;
	}
	
	public VMfCateDevtypeDao getVMfCateDevtypeDao() {
		return VMfCateDevtypeDao;
	}


	public void setVMfCateDevtypeDao(VMfCateDevtypeDao mfCateDevtypeDao) {
		VMfCateDevtypeDao = mfCateDevtypeDao;
	}


	public VOidGroupDao getVOidGroupDao() {
		return VOidGroupDao;
	}


	public void setVOidGroupDao(VOidGroupDao oidGroupDao) {
		VOidGroupDao = oidGroupDao;
	}


	public VEventTypeDao getVEventTypeDao() {
		return VEventTypeDao;
	}


	public void setVEventTypeDao(VEventTypeDao eventTypeDao) {
		VEventTypeDao = eventTypeDao;
	}


	public VPerformParamDao getVPerformParamDao() {
		return VPerformParamDao;
	}


	public void setVPerformParamDao(VPerformParamDao performParamDao) {
		VPerformParamDao = performParamDao;
	}


	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}


	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}


	public TCategoryMapInitDao getTCategoryMapInitDao() {
		return TCategoryMapInitDao;
	}


	public void setTCategoryMapInitDao(TCategoryMapInitDao categoryMapInitDao) {
		TCategoryMapInitDao = categoryMapInitDao;
	}


	public DefMibGrpDao getDefMibGrpDao() {
		return defMibGrpDao;
	}


	public void setDefMibGrpDao(DefMibGrpDao defMibGrpDao) {
		this.defMibGrpDao = defMibGrpDao;
	}


	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
		jdbcTemplate = new SimpleJdbcTemplate(datasource);
	}


	public TDeviceTypeInitDao getTDeviceTypeInitDao() {
		return TDeviceTypeInitDao;
	}


	public void setTDeviceTypeInitDao(TDeviceTypeInitDao deviceTypeInitDao) {
		TDeviceTypeInitDao = deviceTypeInitDao;
	}

	public String getPageView() {
		return pageView;
	}


	public void setPageView(String pageView) {
		this.pageView = pageView;
	}



	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public TEventtypeInfoInitDao getTEventtypeInfoInitDao() {
		return TEventtypeInfoInitDao;
	}


	public void setTEventtypeInfoInitDao(TEventtypeInfoInitDao eventtypeInfoInitDao) {
		TEventtypeInfoInitDao = eventtypeInfoInitDao;
	}


	public TModuleInfoInitDao getTModuleInfoInitDao() {
		return TModuleInfoInitDao;
	}


	public void setTModuleInfoInitDao(TModuleInfoInitDao moduleInfoInitDao) {
		TModuleInfoInitDao = moduleInfoInitDao;
	}


	public TOidgroupInfoInitDao getTOidgroupInfoInitDao() {
		return TOidgroupInfoInitDao;
	}


	public void setTOidgroupInfoInitDao(TOidgroupInfoInitDao oidgroupInfoInitDao) {
		TOidgroupInfoInitDao = oidgroupInfoInitDao;
	}


	public TOidgroupDetailsInitDao getTOidgroupDetailsInitDao() {
		return TOidgroupDetailsInitDao;
	}


	public void setTOidgroupDetailsInitDao(
			TOidgroupDetailsInitDao oidgroupDetailsInitDao) {
		TOidgroupDetailsInitDao = oidgroupDetailsInitDao;
	}


	public TEventTypeInitDao getTEventTypeInitDao() {
		return TEventTypeInitDao;
	}


	public void setTEventTypeInitDao(TEventTypeInitDao eventTypeInitDao) {
		TEventTypeInitDao = eventTypeInitDao;
	}


	public TEventOidInitDao getTEventOidInitDao() {
		return TEventOidInitDao;
	}


	public void setTEventOidInitDao(TEventOidInitDao eventOidInitDao) {
		TEventOidInitDao = eventOidInitDao;
	}


	public SyslogEventsProcessNsDao getSyslogEventsProcessNsDao() {
		return syslogEventsProcessNsDao;
	}


	public void setSyslogEventsProcessNsDao(
			SyslogEventsProcessNsDao syslogEventsProcessNsDao) {
		this.syslogEventsProcessNsDao = syslogEventsProcessNsDao;
	}


	public SyslogEventsProcessDao getSyslogEventsProcessDao() {
		return syslogEventsProcessDao;
	}


	public void setSyslogEventsProcessDao(
			SyslogEventsProcessDao syslogEventsProcessDao) {
		this.syslogEventsProcessDao = syslogEventsProcessDao;
	}


	public SnmpEventsProcessDao getSnmpEventsProcessDao() {
		return snmpEventsProcessDao;
	}


	public void setSnmpEventsProcessDao(SnmpEventsProcessDao snmpEventsProcessDao) {
		this.snmpEventsProcessDao = snmpEventsProcessDao;
	}


	public TPolicyPeriodDao getTPolicyPeriodDao() {
		return TPolicyPeriodDao;
	}


	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}
	
	
	
}
