package com.ibm.ncs.web.resourceman;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dao.PredefmibInfoDao;
import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TDeviceTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TPortInfoDao;
import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.exceptions.DefMibGrpDaoException;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.IPAddr;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.excel.ExcelTODB;
import com.ibm.ncs.util.excel.ExcelTableMapping;

public class ImportDevInfoController implements Controller{

	String pageView;
	String message;
	
	TDeviceInfoDao TDeviceInfoDao;
	TPortInfoDao TPortInfoDao;
	PredefmibInfoDao predefmibInfoDao;
	DefMibGrpDao defMibGrpDao;
	GenPkNumber genPkNumber;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	TCategoryMapInitDao TCategoryMapInitDao; 
	TDeviceTypeInitDao TDeviceTypeInitDao;
	
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
				System.out.println("marke is --"+marke);
				fileItem.delete();//remove the uploaded file.
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
				String errorXlsFile = "/importDevInfoError_"+timestampStr+".xls";
				String errorLongName = request.getRealPath("/") +errorDir+errorXlsFile;
				String errorLinkStr = "<a href='"+request.getContextPath()+errorDir+errorXlsFile+"'>"+"错误信息："+"</a>";
				if(marke<1){ //ok
					if(marke == -2){	//no corresponding columns in excel ok
						message ="resourceman.import.missingColumn";
			        	model.put("message", message);					
			    		return new ModelAndView("/secure/resourceman/importDevInfo.jsp",	"model", model);
					}else if(marke == -3){//partially imported, and need to write error msg to another excel sheet
						message ="resourceman.import.partialSuccess";
						model.put("message", message);	
						
						model.put("errorLinkStr", errorLinkStr);
			        	//write the error message to excel:
			            	//--1 timestamp
			     //   	String jj = "/logs/xls/importDevInfoError_201107181500.xls"; 
			        	
			        	//-- clear the error directory
			        	try {
			        		String delpath = request.getRealPath("/") +errorDir;
							ExcelTODB.clearDir(delpath);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        	//-- write to the error spread sheet 
					//	FileOutputStream outputStream = new FileOutputStream(request.getRealPath("/") +"/logs/importDevInfoError.xls");
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
			        	System.out.println("after close outputStream is null ? "+(outputStream == null));
			        	 
			    		return new ModelAndView("/secure/resourceman/importDevInfo.jsp",	"model", model);
					}else if(marke == -4){
						message ="resourceman.import.noDataToImport";
			        	model.put("message", message);		
			    		return new ModelAndView("/secure/resourceman/importDevInfo.jsp",	"model", model);
					}
		        	message ="resourceman.import.failed";
		        	model.put("message", message);
		        	model.put("errorLinkStr", errorLinkStr);
		    		return new ModelAndView("/secure/resourceman/importDevInfo.jsp",	"model", model);
				}else{//ok
				    message ="resourceman.import.success";
				    model.put("message", message);	
				     try {
						ExcelTODB.clearDir(request.getRealPath("/") +errorDir);
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
		
		return new ModelAndView("/secure/resourceman/importDevInfo.jsp", "model", model);
	}


	private int loadToDB(String filePath, HSSFWorkbook errorMsgWb) {
		boolean hasDataToImport = false;
		int mark=-1;
		try{
			InputStream inStr = new FileInputStream(filePath);
			Workbook workBook = Workbook.getWorkbook(inStr);
			Map<String, ExcelTableMapping> mapping_DevInfo = new HashMap<String, ExcelTableMapping>();//T_DEVICE_INFO
			Map<String, ExcelTableMapping> mapping_PortInfo = new HashMap<String, ExcelTableMapping>();//T_PORT_INFO
			Map<String, ExcelTableMapping> mapping_PredefmibInfo = new HashMap<String, ExcelTableMapping>();//PREDEFMIB_INFO
				
			//get work sheet with the name 	  
		    Sheet[] sheetList = workBook.getSheets(); 
		    Connection con = datasource.getConnection();
		    if(sheetList != null){
		    	Sheet sheetdev = null;
	    		Sheet sheetport = null;
	    		Sheet sheetmib = null;
		    	for(int i=0;i<sheetList.length;i++){
		    		Sheet sheet = sheetList[i];
		    		if(sheet.getName().equalsIgnoreCase("T_DEVICE_INFO")){
		    			hasDataToImport = true;
		    			sheetdev = sheetList[i];
		    		}
		    		if(sheet.getName().equalsIgnoreCase("T_PORT_INFO")){
		    			sheetport = sheetList[i];
		    		}
		    		if(sheet.getName().equalsIgnoreCase("PREDEFMIB_INFO")){
		    			sheetmib = sheetList[i];
		    		}
		    		
		    	}
		    	System.out.println("sheetdev is null ? "+(sheetdev == null)+" and sheetport is null ? "+(sheetport == null)+" and sheetmib is null ? "+(sheetmib == null));
		    	
		    	if(hasDataToImport == true){
		    		if(sheetdev != null){
		    			System.out.println("sheetdev is not null");
		    			System.out.println("sheedev column count is : "+sheetdev.getColumns());
		    			String[] columnStrsOfDev = new String[sheetdev.getColumns()];
		    			
		    			for(int c=0;c<columnStrsOfDev.length;c++){
		    				columnStrsOfDev[c] = sheetdev.getCell(c, 1).getContents();
		    			}
		    			
		    			
		    			 Statement descPstmt = con.createStatement();
		    			  ResultSet rs = descPstmt.executeQuery("select * from t_device_info");
		    			  ResultSetMetaData rsmeta = rs.getMetaData();
		    			  //init mapping in while
		    			  int a = initDevTableMapping(rsmeta, columnStrsOfDev,mapping_DevInfo, "T_DEVICE_INFO");
		    			  rs.close();
		    			  descPstmt.close();
		    			  if(a<0){
		    				  return a;
		    			  }
		    			  System.out.println("mapping dev result is : "+a);
		    			  
		    			  //importdev
		    			  int markTemp = -1;
		    			  markTemp = importDevInfo(sheetdev, errorMsgWb, columnStrsOfDev, mapping_DevInfo, con);
		    			  System.out.println("markTmp in dev is : "+markTemp);
		    			  if(markTemp == -1)
			    				 return markTemp;
			    			 if(markTemp == -3)
			    				 mark = markTemp;
			    			 else if(mark != -3)
			    				 mark = 1;
			    			 if(mark != 1){
			    				 return mark;
			    			 }
			    			 
		    			  List<TDeviceInfo> devlist = TDeviceInfoDao.findAll();
		    			  System.out.println("after insert dev the devlist size is :"+devlist.size());
		    			  Map<String,String> devmap = new HashMap<String,String>();
						  if(devlist != null && devlist.size()>0){
						      for(TDeviceInfo dev : devlist){
							     String devipindb = dev.getDevip();
							     long devidindb = dev.getDevid();
							     String devidStr = Long.toString(devidindb);
							     System.out.println("long devid is "+devidindb+" and string devid is "+devidStr);
							     devmap.put(devipindb, devidStr);
						  }
						      System.out.println("after insert devmap is : "+devmap);
						  }
		    			  
		    			if(sheetport != null){
		    				System.out.println("sheetport is not null");
		    				System.out.println("sheetport column count is : "+sheetport.getColumns());
			    			String[] columnStrsOfPort = new String[sheetport.getColumns()];
			    			
			    			for(int c=0;c<columnStrsOfPort.length;c++){
			    				columnStrsOfPort[c] = sheetport.getCell(c, 1).getContents();
			    			}
			    			
			    			  Statement descPstmtPort = con.createStatement();
			    			  ResultSet rsport = descPstmtPort.executeQuery("select * from t_port_info");
			    			  ResultSetMetaData rsmetaport = rsport.getMetaData();
			    			  
			    			  //init mapping in while
			    			  int aP = initTableMapping(rsmetaport, columnStrsOfPort,mapping_PortInfo, "T_PORT_INFO");
			    			  rsport.close();
			    			  descPstmtPort.close();
			    			  if(aP<0){
			    				  return aP;
			    			  }
			    			  System.out.println("mapping port result is : "+aP);
			    			  
			    			  markTemp = importPortInfo(sheetport, errorMsgWb, columnStrsOfPort, mapping_PortInfo, con,devmap);
			    			  System.out.println("markTmp in port is : "+markTemp);
			    			  if(markTemp == -1)
				    				 return markTemp;
				    			 if(markTemp == -3)
				    				 mark = markTemp;
				    			 else if(mark != -3)
				    				 mark = 1;
		    			}
		    			
		    			if(sheetmib != null){
		    				System.out.println("sheetmib is not null");

		    				System.out.println("sheetmib column count is : "+sheetmib.getColumns());
			    			String[] columnStrsOfMib = new String[sheetmib.getColumns()];
			    			
			    			for(int c=0;c<columnStrsOfMib.length;c++){
			    				columnStrsOfMib[c] = sheetmib.getCell(c, 1).getContents();
			    			}
			    			
			    			  Statement descPstmtMib = con.createStatement();
			    			  Statement descPstmtMibGrp = con.createStatement();
			    			  ResultSet rsmib = descPstmtMib.executeQuery("select * from predefmib_info");
			    			  ResultSet rsmibgrp = descPstmtMibGrp.executeQuery("select * from def_mib_grp");
			    			  ResultSetMetaData rsmetamib = rsmib.getMetaData();
			    			  ResultSetMetaData rsmetamibgrp = rsmibgrp.getMetaData();
			    			  
			    			  //init mapping in while
			    			  int aM = initTableMapping(rsmetamib, columnStrsOfMib,mapping_PredefmibInfo, "PREDEFMIB_INFO");
			    			  int aG = initTableMapping(rsmetamibgrp,columnStrsOfMib,mapping_PredefmibInfo,"PREDEFMIB_INFO");
			    			  rsmib.close();
			    			  rsmibgrp.close();
			    			  descPstmtMib.close();
			    			  descPstmtMibGrp.close();
			    			  if(aM<0){
			    				  return aM;
			    			  }
			    			  if(aG<0){
			    				  return aG;
			    			  }
			    			  System.out.println("mapping MIB result is : "+aM+"and mapping mibgrp result is "+aG);
			    			  
			    		      markTemp = importMibInfo(sheetmib, errorMsgWb, columnStrsOfMib, mapping_PredefmibInfo, con,devmap);
			    			  System.out.println("markTmp in mib is : "+markTemp);
			    			  if(markTemp == -1)
				    				 return markTemp;
				    			 if(markTemp == -3)
				    				 mark = markTemp;
				    			 else if(mark != -3)
				    				 mark = 1;
		    			
		    				
		    			}
		    		}
		    		
		    	}
		    }
		    
		    try{
		    	if(con != null){
		    		con.close();
		    		con = null;
		    	}
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		  
		   
		}catch(Exception e){
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
	 * Import from excel to table:T_Device_info
	 * @param sheetdev
	 * @param errorMsgWb
	 * @param columnStrsOfDev
	 * @param mapping_DevInfo
	 * @param con
	 * @return
	 */
	
	private int importDevInfo(Sheet sheetdev, HSSFWorkbook errorMsgWb,
			String[] columnStrsOfDev,
			Map<String, ExcelTableMapping> mapping_DevInfo, Connection con) {
		System.out.println("import dev invoked----");
		
		int mark = 1;
		try{
			String sqls_insert="insert into T_DEVICE_INFO (DEVID, DEVIP, IPDECODE, SYSNAME, SYSNAMEALIAS, RSNO, SRID, ADMIN, PHONE, MRID, DTID, SERIALID, SWVERSION, RAMSIZE, RAMUNIT, NVRAMSIZE, NVRAMUNIT, FLASHSIZE, FLASHUNIT, FLASHFILENAME, FLASHFILESIZE, RCOMMUNITY, WCOMMUNITY, DESCRIPTION, DOMAINID, SNMPVERSION)"+"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    PreparedStatement pstmt_insert=con.prepareStatement(sqls_insert);
				 
			String sqls_update="Update T_DEVICE_INFO set DEVIP=?, IPDECODE=?, SYSNAME=?, SYSNAMEALIAS=?, RSNO=?, SRID=?, ADMIN=?, PHONE=?, MRID=?, DTID=?, SERIALID=?, SWVERSION=?, RAMSIZE=?, RAMUNIT=?, NVRAMSIZE=?, NVRAMUNIT=?, FLASHSIZE=?, FLASHUNIT=?, FLASHFILENAME=?, FLASHFILESIZE=?, RCOMMUNITY=?, WCOMMUNITY=?, DESCRIPTION=?, DOMAINID=?, SNMPVERSION=? where DEVID=?";
			PreparedStatement pstmt_update=con.prepareStatement(sqls_update);
			String[] sqlsCol_update= new String[]{"DEVIP", "IPDECODE", "SYSNAME", "SYSNAMEALIAS", "RSNO", "SRID", "ADMIN", "PHONE", "MRID", "DTID", "SERIALID", "SWVERSION", "RAMSIZE", "RAMUNIT", "NVRAMSIZE", "NVRAMUNIT", "FLASHSIZE", "FLASHUNIT", "FLASHFILENAME", "FLASHFILESIZE", "RCOMMUNITY", "WCOMMUNITY", "DESCRIPTION", "DOMAINID", "SNMPVERSION","DEVID"};	
		 	String[] sqlsCol_insert= new String[]{"DEVID", "DEVIP", "IPDECODE", "SYSNAME", "SYSNAMEALIAS", "RSNO", "SRID", "ADMIN", "PHONE", "MRID", "DTID", "SERIALID", "SWVERSION", "RAMSIZE", "RAMUNIT", "NVRAMSIZE", "NVRAMUNIT", "FLASHSIZE", "FLASHUNIT", "FLASHFILENAME", "FLASHFILESIZE", "RCOMMUNITY", "WCOMMUNITY", "DESCRIPTION", "DOMAINID", "SNMPVERSION"};		
		 	   
		 	String devip = "";
			long devid = 0;
			String [] devips = new String[sheetdev.getRows()-2];
			Map<String,String> devmap = new HashMap<String,String>();
			System.out.println("devsheet row is "+sheetdev.getRows());
			 List duplist = new ArrayList();
			 Map<String,Integer> duprowmap = new HashMap<String,Integer>();
			 String sysname = "";
			 for(int r=2;r<sheetdev.getRows();r++){
				boolean parseExcelError = false;
				boolean duponce = false;
				boolean dupnameonce = false;
				try{
				  devip = sheetdev.getCell(mapping_DevInfo.get("DEVIP").getIndex(),r).getContents();
				  sysname = sheetdev.getCell(mapping_DevInfo.get("SYSNAME").getIndex(),r).getContents();
				}catch(Exception e){
					  e.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage("T_DEVICE_INFO",sheetdev.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfDev);
		    		  parseExcelError = true;
		    		  continue;
				}
				  if(devip != null /*&& (!deviplist.contains(devip))*/){
				     devips[r-2]=devip;
				   //  deviplist.add(devip);
				  }
				  if(!duprowmap.containsKey(devip)){
					  duprowmap.put(devip, r);
				  }else{
					int rr = duprowmap.get(devip);
					 mark = -3;
					 if(duponce == false && (!duplist.contains(devip))){
		    		  appendErrorMessage("T_DEVICE_INFO",sheetdev.getRow(rr), errorMsgWb, "DEVIP重复", columnStrsOfDev);
					  duponce = true;
					  duplist.add(devip);
					 }
		    		  appendErrorMessage("T_DEVICE_INFO",sheetdev.getRow(r), errorMsgWb, "DEVIP重复", columnStrsOfDev);
		    		  parseExcelError = true;
		    		  continue;
				  }
				  if(!duprowmap.containsKey(sysname)){
					  duprowmap.put(sysname, r);
				  }else{
					int rname = duprowmap.get(sysname);
					 mark = -3;
					 if(dupnameonce == false && (!duplist.contains(sysname))){
		    		  appendErrorMessage("T_DEVICE_INFO",sheetdev.getRow(rname), errorMsgWb, "SYSNAME重复", columnStrsOfDev);
					  duponce = true;
					  duplist.add(sysname);
					 }
		    		  appendErrorMessage("T_DEVICE_INFO",sheetdev.getRow(r), errorMsgWb, "SYSNAME重复", columnStrsOfDev);
		    		  parseExcelError = true;
		    		  continue;
				  }
				  
  			}
			  System.out.println("devips is : "+devips);
			 
			 
			  
			  List<TDeviceInfo> devlist = TDeviceInfoDao.findDevInExcel(devips);
			  System.out.println("devlist from devips is : "+devlist);
			  if(devlist != null && devlist.size()>0){
			      for(TDeviceInfo dev : devlist){
				     String devipindb = dev.getDevip();
				     long devidindb = dev.getDevid();
				     devmap.put(devipindb, devidindb+"");
			  }
			      System.out.println("devmap is : "+devmap);
			  }
			  
			 
			  for(int r=2;r<sheetdev.getRows();r++){
				  boolean parseExcelError = false;
			      boolean ifUpdate = false;
			    
			   
			      try{
				  devip = sheetdev.getCell(mapping_DevInfo.get("DEVIP").getIndex(),r).getContents();
			      }catch(Exception e){
					  e.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage("T_DEVICE_INFO",sheetdev.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfDev);
		    		  parseExcelError = true;
		    		  continue;
				}
				  if(devmap.containsKey(devip)){//update
					  System.out.println("update dev-----");
					  ifUpdate = true;
				  }
				  
				  int markTmp = insertUpdateDevTable("T_DEVICE_INFO",ifUpdate,sqlsCol_insert, sheetdev, mapping_DevInfo, 
		    				r, parseExcelError, errorMsgWb,
		    				columnStrsOfDev, pstmt_insert, pstmt_update,sqlsCol_update,devmap);
				  
		    	  if(markTmp == -3){
		    		  mark = -3;
		    		  continue;
		    	  }else if(markTmp == -1){
		    		  pstmt_insert.close();
		    		  pstmt_update.close();
		    		  con.close();
		  	      	  return markTmp;
		    	  }
				  
			  }
		 	
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheetdev.getName() + "失败:" + e.getMessage());
			
		}
		return mark;
	}

	
	
	/**
	 * Import from excel to table:T_Device_info
	 * @param sheetdev
	 * @param errorMsgWb
	 * @param columnStrsOfDev
	 * @param mapping_DevInfo
	 * @param con
	 * @return
	 */
	
	private int importPortInfo(Sheet sheetport, HSSFWorkbook errorMsgWb,
			String[] columnStrsOfPort,
			Map<String, ExcelTableMapping> mapping_PortInfo,Connection con,Map<String,String> devmapall) {
//		System.out.println("import port invoked----");
		int mark = 1;
		try{
			
			String sqls_insert="insert into T_PORT_INFO (PTID, DEVID, IFINDEX, IFIP, IPDECODE_IFIP, IFMAC, IFOPERSTATUS, IFDESCR, DESCRIPTION)"+"values(?,?,?,?,?,?,?,?,?)";
		    PreparedStatement pstmt_insert=con.prepareStatement(sqls_insert);
				 
			String sqls_update="UPDATE T_PORT_INFO set  IFINDEX=?, IFIP=?, IPDECODE_IFIP=?, IFMAC=?, IFOPERSTATUS=?, DESCRIPTION=? where DEVID=? AND IFDESCR=?";
			PreparedStatement pstmt_update=con.prepareStatement(sqls_update);
			String[] sqlsCol_insert= new String[]{"PTID", "DEVID", "IFINDEX", "IFIP", "IPDECODE_IFIP", "IFMAC", "IFOPERSTATUS", "IFDESCR", "DESCRIPTION"};		
			String[] sqlsCol_update= new String[]{"IFINDEX", "IFIP", "IPDECODE_IFIP", "IFMAC", "IFOPERSTATUS", "DESCRIPTION","DEVID","IFDESCR" };	
		 		   
		 	String devip = "";
			long devid = 0;
			String ifdescr = "";
			String [] ifdescrs = new String[sheetport.getRows()-2];
			Map<String,Long> portmap = new HashMap<String,Long>();
			boolean parseExcelError = false;
			Map<String, List> ipPortMap = new HashMap<String , List>();
			//get ifdescrArray
			int indexdevip = 0;
			for(int i=0;i<columnStrsOfPort.length;i++){
				if(columnStrsOfPort[i].equalsIgnoreCase("DEVIP")){
					indexdevip = i;
				}
			}
			for(int r=2;r<sheetport.getRows();r++){
								
				try{
				  String devipstr   =  sheetport.getCell(indexdevip,r).getContents();
				  List ttmp = ipPortMap.get(devipstr);
				  if(ttmp == null){
					  ttmp = new ArrayList<String>();
				  }
				  ifdescr = sheetport.getCell(mapping_PortInfo.get("IFDESCR").getIndex(), r).getContents();
				  ttmp.add(ifdescr);
				  ipPortMap.put(devipstr, ttmp);
	//			  System.out.println("ifdesc is : "+ifdescr);
				}catch(Exception e){
					  e.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage("T_PORT_INFO",sheetport.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfPort);
		    		  parseExcelError = true;
		    		  continue;
				}
  			}
			System.out.println("ipPortMap...size="+ipPortMap.size()+"ipPortMap=\n");
	//		System.out.println("ifdescrsArray is "+ifdescrs.toString());
			
	//		System.out.println("index of devip in port is : "+indexdevip);
			//delete the port where ifdescr not in ifdescrArray
//			for(int r=2;r<sheetport.getRows();r++){
//				try{
//				devip = sheetport.getCell(indexdevip,r).getContents();
//	//			System.out.println("devip from port sheet is : "+devip);
//				}catch(Exception e){
//					  e.printStackTrace();
//		    		  mark = -3;
//		    		  appendErrorMessage("T_PORT_INFO",sheetport.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfPort);
//		    		  parseExcelError = true;
//		    		  continue;
//				}
//				 if(devip != null)
//			     devid = devmap.get(devip);
//	//			 System.out.println("devid from devmap is : "+devid);
//	//			 System.out.println("delete port begin----");
//	//			 List<TPortInfo> ports = TPortInfoDao.findAll();
//	//			 System.out.println("ports size before delete is : "+ports.size());
//				 try{
//				 TPortInfoDao.deleteByNotInIfdescrList(devid, ifdescrs);
//				 Log4jInit.ncsLog.info(this.getClass().getName() + " TPortInfoDao.deleteByNotInIfdescrList(devid, interface_name)\n"+devid );
//				 }catch(Exception e){
//					 e.printStackTrace();
//		    		  mark = -3;
//		    		  appendErrorMessage("T_PORT_INFO",sheetport.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfPort);
//		    		  parseExcelError = true;
//		    		  Log4jInit.ncsLog.error(this.getClass().getName() + "error on delete port info...");
//		    		  continue;
//				 }
//	//			 System.out.println("delete port end-----");
//			}
			//delete ports by devid + ifdescr
			Map<String, List> ipPortDB = new HashMap<String, List>();
			Iterator ipit = ipPortMap.keySet().iterator();
			while(ipit.hasNext()){
				
					String deviptmp  = (String)ipit.next();
					String devidtmp = devmapall.get(deviptmp);
					List ifdescrslist = ipPortMap.get(deviptmp);
	//				System.out.println("ifdescrlist size is "+ifdescrslist.size());
					String[] ifdescrstmp = (String[])ifdescrslist.toArray(new String[0]);
	//				System.out.println("prama for delete : ddevid is "+devidtmp+" and ifdescrlist [] is "+ifdescrstmp.toString()); 
				try{
					long ddevid = Long.parseLong(devidtmp);
					TPortInfoDao.deleteByNotInIfdescrList(ddevid, ifdescrstmp);
					Log4jInit.ncsLog.info(this.getClass().getName() + " TPortInfoDao.deleteByNotInIfdescrList(devid, interface_name)\n"+devid );
				}catch(Exception e){
					 e.printStackTrace();
		    		  mark = -3;
		    //		  appendErrorMessage("T_PORT_INFO",sheetport.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfPort);
		    		  parseExcelError = true;
		    		  Log4jInit.ncsLog.error(this.getClass().getName() + "error on delete port info...");
		    		  continue;
				}
				try{
					long ddevid = Long.parseLong(devidtmp);
					List<TPortInfo> ifnameAtDB = TPortInfoDao.findByDevidAndIfdescrList(ddevid, ifdescrstmp);
	//				System.out.println("ifnameAtDB IS NULL ? "+(ifnameAtDB == null));
					for(TPortInfo dto :ifnameAtDB){
						List ttmp = ipPortDB.get(dto.getDevid()+"");
	//					System.out.println("ttmp is null ? "+(ttmp == null));
						if(ttmp == null){
							ttmp = new ArrayList();
						}
						ttmp.add(dto.getIfdescr());
						ipPortDB.put(dto.getDevid()+"",ttmp);
	//					System.out.println("data in ipPortDB IS "+ipPortDB);
					}
				}catch(Exception e){
					 e.printStackTrace();
		    		  mark = -3;
		    //		  appendErrorMessage("T_PORT_INFO",sheetport.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfPort);
		    		  parseExcelError = true;
		    		  Log4jInit.ncsLog.error(this.getClass().getName() + "error on retrieve port info...findByDevidAndIfdescrList(,)"+devidtmp+", \n"+ifdescrslist+")");
		    		  continue;
				}
			}
			System.out.println("data in ipPortDB IS "+ipPortDB);
			//portmap
//			List<TPortInfo> ports = TPortInfoDao.findAll();
//	//		System.out.println("ports size after delete is : "+ports.size());
//			if(ports != null && ports.size()>0){
//				for(TPortInfo port : ports){
//					long devidindb = port.getDevid();
//					String ifdescrindb = port.getIfdescr();
//					portmap.put(ifdescrindb,devidindb);
//				}
//			}
	//		  System.out.println("portmap is : "+portmap);
			
			  for(int r=2;r<sheetport.getRows();r++){
				  parseExcelError = false;
			      boolean ifUpdate = false;
			      
			      try{
			      devip = sheetport.getCell(indexdevip,r).getContents();
				  ifdescr = sheetport.getCell(mapping_PortInfo.get("IFDESCR").getIndex(),r).getContents();
			      }catch(Exception e){
					  e.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage("T_PORT_INFO",sheetport.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfPort);
		    		  parseExcelError = true;
		    		  continue;
			      }
			    //check if update or insert
//			      if(portmap != null && portmap.size()>0){
//						for(int i = 0;i<portmap.size();i++){
//							if(portmap.containsKey(ifdescr)){
//								
//								ifUpdate = true;
//							}
//						}
//					}
			      if(ipPortDB != null && ipPortDB.size()>0){
			    	  String devidtmp = devmapall.get(devip);
			    	  if(devidtmp != null){
			    		  List updtmp = ipPortDB.get(devidtmp);
	//		    		  System.out.println("updtmp is null ? "+(updtmp == null));
			    		  if(updtmp != null && updtmp.contains(ifdescr)){
			    			  ifUpdate = true;
			    		  }
			    	  }
			      }
			      
	//		      System.out.println("update port(devip = "+devip+" ) is : "+ifUpdate);
				  int markTmp = insertUpdateTable("T_PORT_INFO",ifUpdate,sqlsCol_insert, sheetport, mapping_PortInfo, 
		    				r, parseExcelError, errorMsgWb,
		    				columnStrsOfPort, pstmt_insert, pstmt_update,sqlsCol_update,devmapall,devip);
		    	  if(markTmp == -3){
		    		  mark = -3;
		    		  continue;
		    	  }else if(markTmp == -1){
		    		  pstmt_insert.close();
		    		  pstmt_update.close();
		    		  con.close();
		  	      	  return markTmp;
		    	  }
				  
			  }
		 	
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheetport.getName() + "失败:" + e.getMessage());
			
		}
		return mark;
	}
	

	/**
	 * Import from excel to table:Predefmib_info
	 * @param sheetdev
	 * @param errorMsgWb
	 * @param columnStrsOfMib
	 * @param mapping_PredefmibInfo
	 * @param con
	 * @return
	 */
	
	private int importMibInfo(Sheet sheetmib, HSSFWorkbook errorMsgWb,
			String[] columnStrsOfMib,
			Map<String, ExcelTableMapping> mapping_PredefmibInfo, Connection con,Map<String,String> devmap) {
		System.out.println("import mib invoked----");
		int mark = 1;
		try{
			
			String sqls_insert="insert into PREDEFMIB_INFO (PDMID, MID, DEVID, OIDINDEX, OIDNAME)"+"values(?,?,?,?,?)";
		    PreparedStatement pstmt_insert=con.prepareStatement(sqls_insert);
				 
			String sqls_update="UPDATE PREDEFMIB_INFO set  MID=?, OIDINDEX=? where  DEVID=? AND  OIDNAME=?";
			PreparedStatement pstmt_update=con.prepareStatement(sqls_update);
			String[] sqlsCol_update= new String[]{"MID","OIDINDEX","DEVID","OIDNAME" };	
		 	String[] sqlsCol_insert= new String[]{"PDMID", "MID", "DEVID", "OIDINDEX", "OIDNAME"};	
		 	
		 	String sqls_insertgrp="insert into DEF_MIB_GRP ( MID, NAME, INDEXOID, INDEXVAR, DESCROID, DESCRVAR )"+"values(?,?,?,?,?,?)";
		    PreparedStatement pstmt_insertgrp=con.prepareStatement(sqls_insertgrp);
				 
			String sqls_updategrp="UPDATE DEF_MIB_GRP set  INDEXOID=? ,INDEXVAR=?, DESCROID=?, DESCRVAR=? where  NAME=?";
			PreparedStatement pstmt_updategrp=con.prepareStatement(sqls_updategrp);
			String[] sqlsCol_updategrp= new String[]{ "INDEXOID", "INDEXVAR", "DESCROID", "DESCRVAR", "NAME" };	
		 	String[] sqlsCol_insertgrp= new String[]{"MID", "NAME", "INDEXOID", "INDEXVAR", "DESCROID", "DESCRVAR"};	
		 	   
		 	String devip = "";
		//	long devid = 0;
			String oidname = "";
			String name="";
		//	long mid = 0;
			String [] oidnames = new String[sheetmib.getRows()-2];
			Map<String,Long> mibmap = new HashMap<String,Long>();
			boolean parseExcelError = false;
			//Map<String, List> ipMibMap = new HashMap<String , List>();
			Map<String,Map<String,List>> ipMibMap = new HashMap<String,Map<String,List>>();
			Map<String,List> tmpMap = new HashMap<String,List>();
			int indexdevip = 0;
			 int indexname = 0;
			for(int i=0;i<columnStrsOfMib.length;i++){
				if(columnStrsOfMib[i].equalsIgnoreCase("DEVIP")){
					indexdevip = i;
				}
				if(columnStrsOfMib[i].equalsIgnoreCase("NAME")){
					indexname = i;
				}
			}
			Map<String,String> oidnametoname = new HashMap<String,String>();
			System.out.println("index of devip in port is : "+indexdevip);
			//get oidnameArray
			for(int r=2;r<sheetmib.getRows();r++){
				
				try{
				  String devipStr = sheetmib.getCell(indexdevip,r).getContents();
				  System.out.println("devip from mib sheet is : "+devip);
				  
				 // List ttmp = ipMibMap.get(devipStr);
				  tmpMap = ipMibMap.get(devipStr);
				 
				  if(tmpMap == null){
					  tmpMap = new HashMap<String,List>();
				  }
				  List tmpoidname = tmpMap.get("oidname");
				  if(tmpoidname == null){
					  tmpoidname = new ArrayList();
				  }
				  List tmpname = tmpMap.get("name");
				  if(tmpname == null){
					  tmpname = new ArrayList();
				  }
				  oidname = sheetmib.getCell(mapping_PredefmibInfo.get("OIDNAME").getIndex(), r).getContents();
				  name = sheetmib.getCell(indexname, r).getContents();
				  System.out.println("oidname is : "+oidname+" and name is "+name);
				  oidnametoname.put(oidname, name);
				  tmpoidname.add(oidname);
				  tmpMap.put("oidname", tmpoidname);
				  tmpname.add(name);
				  tmpMap.put("name", tmpname);
				  ipMibMap.put(devipStr, tmpMap);
				  System.out.println("map oidnametoname is "+oidnametoname+" and inner map is "+tmpMap+" and outer map is "+ipMibMap);
				}catch(Exception e){
					  e.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage("PREDEFMIB_INFO",sheetmib.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfMib);
		    		  parseExcelError = true;
		    		  continue;
				}
				 /* if(oidname != null){
					  oidnames[r-2]=oidname;
				  }*/
  			}
			
			System.out.println("ipMibMap...size="+ipMibMap.size()+"ipMibMap=\n"+ipMibMap);
		//	System.out.println("oidnames is "+oidnames.toString());
			
			//delete the mib where oidname not in oidnameArray
	/*		for(int r=2;r<sheetmib.getRows();r++){
				try{
				devip = sheetmib.getCell(indexdevip,r).getContents();
				System.out.println("devip from mib sheet is : "+devip);
				}catch(Exception e){
					  e.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage("PREDEFMIB_INFO",sheetmib.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfMib);
		    		  parseExcelError = true;
		    		  continue;
				}
				 if(devip != null)
			     devid = Long.parseLong(devmap.get(devip));
				 System.out.println("devid from devmap is : "+devid);
				 System.out.println("delete mib begin----");
				 try{
					 List<PredefmibInfo> mibs = predefmibInfoDao.findAll();
					 System.out.println("mibs size before delete is : "+mibs.size());
					 predefmibInfoDao.deleteByDevidAndNotInOidname(devid, oidnames);
				     Log4jInit.ncsLog.info(this.getClass().getName() + " predefmibInfoDao.deleteByDevidAndNotInOidname(devid, oidnames)\n"+devid );
				 }catch(Exception e){
					 e.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage("PREDEFMIB_INFO",sheetmib.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfMib);
		    		  parseExcelError = true;
		    		  Log4jInit.ncsLog.error(this.getClass().getName() + "error on delete MIB info...");
		    		  continue;
				 }
				 System.out.println("delete mib end-----");
			}
		*/	
			
			List<PredefmibInfo> allresult = predefmibInfoDao.findAll();
			Map<Long,List> idMidMap = new HashMap<Long,List>();
			for(PredefmibInfo mib : allresult){
				List mibs = idMidMap.get(mib.getDevid());
				if(mibs == null){
					mibs = new ArrayList();
				}
				long mid = mib.getMid();
				if(!mibs.contains(mid)){
				mibs.add(mid);
				}
				idMidMap.put(mib.getDevid(),mibs);
			}
			System.out.println("idMibMap is "+idMidMap);
			/*Iterator idmidit = idMidMap.keySet().iterator();
			while(idmidit.hasNext()){
				long devid = (Long)idmidit.next();
				List mids = idMidMap.get(devid);
				List namelist = ipMibMap.get(key)
			}*/
			//delete by devid+oidname
			Map<String, List> ipMibDB = new HashMap<String, List>();
			Iterator ipit = ipMibMap.keySet().iterator();
			Map<String,List> ipMidDB = new HashMap<String,List>();
			
			
			while(ipit.hasNext()){
				
					String deviptmp  = (String)ipit.next();
					String devidtmp = devmap.get(deviptmp);
					List oidnameslist = ipMibMap.get(deviptmp).get("oidname");
					List namelist = ipMibMap.get(deviptmp).get("name");
					String[] oidnamestmp = (String[])oidnameslist.toArray(new String[0]);
					
					List mid = idMidMap.get(Long.parseLong(devidtmp));
					
				for(int i=0;i<namelist.size();i++){
					//String nametmp = oidnametoname.get(oidname);
					String nametmp = (String)namelist.get(i);
					long midtmp = 0;
					List<DefMibGrp> grps = defMibGrpDao.findWhereNameEquals(nametmp);
					if(grps != null && grps.size()>0){
						midtmp = grps.get(0).getMid();
					}
                    if(mid != null){
						if(mid.contains(midtmp)){
					//		System.out.println("remove----------");
							mid.remove(midtmp);
						}
					 } 
               //     System.out.println("after remove midtmp the idMidMap is "+idMidMap);
					
				try{
					long ddevid = Long.parseLong(devidtmp);
					System.out.println("param for delete : devid is "+ddevid+" and mid is "+midtmp+" and oidname is "+oidnamestmp);
					
					if(midtmp != 0){
					predefmibInfoDao.deleteByDevidAndMidAndNotInOidname(ddevid, midtmp,oidnamestmp);
					Log4jInit.ncsLog.info(this.getClass().getName() + " predefmibInfoDao.deleteByDevidAndNotInMid(ddevid, oidnamestmp)\n"+ddevid );
					}
				}catch(Exception e){
					 e.printStackTrace();
		    		  mark = -3;
		    //		  appendErrorMessage("T_PORT_INFO",sheetport.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfPort);
		    		  parseExcelError = true;
		    		  Log4jInit.ncsLog.error(this.getClass().getName() + "error on delete mib info...");
		    		  continue;
				}
				
				}
				if(mid != null && mid.size()>0){
					System.out.println("delete by mid----------");
					  for(int k=0;k<mid.size();k++){
						  predefmibInfoDao.deleteByDevidAndMid(Long.parseLong(devidtmp), (Long)mid.get(k));
					  }
					}
				
				for(int ii=0;ii<namelist.size();ii++){
					String nametemp = (String)namelist.get(ii);
					long midtemp = 0;
					List<DefMibGrp> grpes = defMibGrpDao.findWhereNameEquals(nametemp);
					if(grpes != null && grpes.size()>0){
						midtemp = grpes.get(0).getMid();
					}
			//		System.out.println("midtemp for oidnameAtDB IS : "+midtmp);
					try{
						long ddevid = Long.parseLong(devidtmp);
						if(midtemp != 0){
						List<PredefmibInfo> oidnameAtDB = predefmibInfoDao.findByDevidAndOidnameAndMid(ddevid,midtemp,oidnamestmp);
						System.out.println("oidnameAtDB SIZE IS "+oidnameAtDB.size());
						List ttmp = new ArrayList();
						for(PredefmibInfo dto :oidnameAtDB){
							 ttmp = ipMibDB.get(dto.getDevid()+"");
							if(ttmp == null){
								ttmp = new ArrayList();
							}
							ttmp.add(dto.getOidname());
							
					    //	ipMibDB.put(dto.getDevid()+"",ttmp);
						}
						ipMibDB.put(ddevid+"",ttmp);
						System.out.println("ipMibDB IS "+ipMibDB);
						}
					}catch(Exception e){
						 e.printStackTrace();
			    		  mark = -3;
			    //		  appendErrorMessage("T_PORT_INFO",sheetport.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfPort);
			    		  parseExcelError = true;
			    		  Log4jInit.ncsLog.error(this.getClass().getName() + "error on retrieve mib info...findByDevidAndMidList(,)"+devidtmp+", \n"+oidnameslist+")");
			    		  continue;
					}
				}
				
			}
			//mibmap
			/*List<PredefmibInfo> mibs = predefmibInfoDao.findAll();
			System.out.println("mibs size after delete is : "+mibs.size());
			if(mibs != null && mibs.size()>0){
				for(PredefmibInfo mib : mibs){
					long devidindb = mib.getDevid();
					String oidnameindb = mib.getOidname();
					mibmap.put(oidnameindb,devidindb);
				}
			}
			  System.out.println("mibmap is : "+mibmap);
			
			  for(int r=2;r<sheetmib.getRows();r++){
				  parseExcelError = false;
			      boolean ifUpdate = false;
			      
			      try{
			      devip = sheetmib.getCell(indexdevip,r).getContents();
				  oidname = sheetmib.getCell(mapping_PredefmibInfo.get("OIDNAME").getIndex(),r).getContents();
			      }catch(Exception e){
					  e.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage("PREDEFMIB_INFO",sheetmib.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfMib);
		    		  parseExcelError = true;
		    		  continue;
				}
			    //check if update or insert
			      if(mibmap != null && mibmap.size()>0){
						for(int i = 0;i<mibmap.size();i++){
							if(mibmap.containsKey(oidname)){
								ifUpdate = true;
							}
						}
					}
			      System.out.println("update mib(devip = )"+devip+" is : "+ifUpdate);*/
			
				List namelist = new ArrayList();
				List<DefMibGrp> grps = defMibGrpDao.findAll();
				for(DefMibGrp grp : grps){
					namelist.add(grp.getName());
				}
			  for(int r=2;r<sheetmib.getRows();r++){
				  parseExcelError = false;
			      boolean ifUpdate = false;
			      boolean ifUpdateGrp = false;
			      
			      
			      try{
			      devip = sheetmib.getCell(indexdevip,r).getContents();
				  oidname = sheetmib.getCell(mapping_PredefmibInfo.get("OIDNAME").getIndex(),r).getContents();
				  name = sheetmib.getCell(indexname, r).getContents();
			      }catch(Exception e){
					  e.printStackTrace();
		    		  mark = -3;
		    		  appendErrorMessage("PREDEFMIB_INFO",sheetmib.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrsOfMib);
		    		  parseExcelError = true;
		    		  continue;
			      }
			 
			      //MIBGRP 
					
					if(namelist.contains(name)){
						ifUpdateGrp = true;
				    }
				
			      if(ipMibDB != null && ipMibDB.size()>0){
			    	  String devidtmp = devmap.get(devip);
			    	  if(devidtmp != null){
			    		  List updtmp = ipMibDB.get(devidtmp);
			    		  System.out.println("updtmp is null ? "+(updtmp == null));
			    		  if(updtmp != null && updtmp.contains(oidname)){
			    			  ifUpdate = true;
			    		  }
			    	  }
			      }
			      
			     
					System.out.println("update grp is "+ifUpdateGrp);
				  int markTmp = insertUpdateMibTable("PREDEFMIB_INFO",ifUpdate,ifUpdateGrp,sqlsCol_insert,sqlsCol_insertgrp, sheetmib, mapping_PredefmibInfo, 
		    				r, parseExcelError, errorMsgWb,
		    				columnStrsOfMib, pstmt_insert, pstmt_update,pstmt_insertgrp, pstmt_updategrp,sqlsCol_update,sqlsCol_updategrp,devmap,devip);
		    	  if(markTmp == -3){
		    		  mark = -3;
		    		  continue;
		    	  }else if(markTmp == -1){
		    		  pstmt_insert.close();
		    		  pstmt_update.close();
		    		  con.close();
		  	      	  return markTmp;
		    	  }
				  
			  }
		 	
		}catch(Exception e){
			if(mark!= -3) mark=-1;
	      	e.printStackTrace();
			Log4jInit.ncsLog.error("导入 " + sheetmib.getName() + "失败:" + e.getMessage());
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
	private int insertUpdateDevTable(String tabName,boolean ifUpdate,
			String[] sqlsCol_insert, Sheet sheet,
			Map<String, ExcelTableMapping> mapping, int r,
			boolean parseExcelError, HSSFWorkbook errorMsgWb,
			String[] columnStrs, PreparedStatement pstmt_insert,
			PreparedStatement pstmt_update, String[] sqlsCol_update,Map<String,String> devmap) {
	//		System.out.println("insertupdatetable DEV invoked----and update is "+ifUpdate);
			int mark = 1;
			int indexmrid=0;
			int indexdtid=0;
			int indexsubcate=0;
			int indexmodel=0;
			int indexobjid=0;
			int indexwcomm=0;
			try{
			for(int i=0;i<columnStrs.length;i++){
				if(columnStrs[i].equalsIgnoreCase("manufacture")){
					indexmrid = i;
					
				}
				if(columnStrs[i].equalsIgnoreCase("category")){
					indexdtid = i;
				}
				if(columnStrs[i].equalsIgnoreCase("subcategory")){
					indexsubcate = i;
				}
				if(columnStrs[i].equalsIgnoreCase("model")){
					indexmodel = i;
				}
				if(columnStrs[i].equalsIgnoreCase("objectid")){
					indexobjid = i;
				}
				if(columnStrs[i].equalsIgnoreCase("wcommunity")){
					indexwcomm = i;
				}
				
			}
	//		System.out.println("indexmrid is : "+indexmrid+" indexdtid is : "+indexdtid+" indexsubcate is : "+indexsubcate+" and indexmodel is : "+indexmodel+" indexobj is : "+indexobjid +" and indexwcomm is : "+indexwcomm);
			if(!ifUpdate){
				System.out.println("insert------------------and r is : "+r);

				for(int aa=1; aa<=sqlsCol_insert.length; aa++){
				//	System.out.println("************************************************");
					ExcelTableMapping eTmp = mapping.get(sqlsCol_insert[aa-1].toUpperCase());
				//	System.out.println("eTmp is null ?: "+(eTmp==null));
					
					if(eTmp==null){
				//		System.out.println("eTmp null invoked----------");
						String manufacture = sheet.getCell(indexmrid, r).getContents().trim();
						String category = sheet.getCell(indexdtid, r).getContents().trim();
						String subcategory = sheet.getCell(indexsubcate, r).getContents().trim();
						String model = sheet.getCell(indexmodel, r).getContents().trim();
		//				String wcommunity = sheet.getCell(indexwcomm, r).getContents();
						String objectid = sheet.getCell(indexobjid, r).getContents().trim();
		//				System.out.println("manufacture is : "+manufacture+" and category is : "+category+" and subcategory is : "+subcategory+" model is : "+model+"and objectid is : "+objectid);
					try{
		//				System.out.println("eTmp is null : column is "+sqlsCol_insert[aa-1].toUpperCase());
						if(sqlsCol_insert[aa-1].toUpperCase().equalsIgnoreCase("MRID")){

		//    					System.out.println("column mrid-----");
  		    				  if(manufacture != null){
  		    					List<TManufacturerInfoInit> manus = new ArrayList<TManufacturerInfoInit>();
  		    					  try{
  		    				         manus = TManufacturerInfoInitDao.findWhereMrnameEquals(manufacture);
  		    					  }catch(Exception e){
  		    						  appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb, "MANUFACTURE在数据库中记录不唯一，请核查数据库一致性", columnStrs);
		    	   		    		  mark = -3;
		    	   		    		  parseExcelError = true;
		    	   		    		  continue;
  		    					  }
  		    				if(manus != null && manus.size()>0){
  		    					if(manus.size()>1){
  		    						 appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb, "MANUFACTURE在数据库中记录不唯一，请核查数据库一致性", columnStrs);
  		    	   		    		  mark = -3;
  		    	   		    		  parseExcelError = true;
  		    	   		    		  continue;
  		    					}else{
  		    						TManufacturerInfoInit manu = manus.get(0);
  		    						if(manu != null){
  		    							long mrid = manu.getMrid();
  		    			//				System.out.println("mrid final is : "+mrid);
  		    							pstmt_insert.setLong(aa, mrid);
  		    						}
  		    					}
  		    					
  		    				}else{
  		    				pstmt_insert.setNull(aa, java.sql.Types.NUMERIC);
  		    			  }
  		    			  }
		    			  
						}
						
						if(sqlsCol_insert[aa-1].toUpperCase().equalsIgnoreCase("DTID")){
		    		//			System.out.println("column dtid----");
		    		//			System.out.println("object id is null or '' ?"+(objectid==null||objectid==""));
  		    					if(objectid == null||objectid.equals("")){
  		    		//				System.out.println("objectid is null ");
	  		    				  if(manufacture != null && !manufacture.equals("") && category != null && !category.equals("") && subcategory != null && !subcategory.equals("") && model != null && !model.equals("")){
	  		    	//				  System.out.println("not all is null-----------");
	  		    					long mrid=0;
	  		    					long cateid=0;
	  		    					  //get mrid
	  		    					List<TManufacturerInfoInit> manus = TManufacturerInfoInitDao.findWhereMrnameEquals(manufacture);
		  		    				if(manus != null && manus.size()>0){
		  		    					if(manus.size()>1){
		  		    						 appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb, "MANUFACTURE在数据库中记录不唯一，请核查数据库一致性", columnStrs);
		  		    	   		    		  mark = -3;
		  		    	   		    		  parseExcelError = true;
		  		    	   		    		  continue;
		  		    					}else{
		  		    						TManufacturerInfoInit manu = manus.get(0);
		  		    						if(manu != null){
		  		    							mrid = manu.getMrid();
		  		    						}
		  		    					}
		  		    					
		  		    				}else{
		  		    					appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb, "厂商"+manufacture+"在数据库中不存在", columnStrs);
	  		    	   		    		  mark = -3;
	  		    	   		    		  parseExcelError = true;
	  		    	   		    		  continue;
		  		    				}
		  		    				//get cateid
		  		    				List<TCategoryMapInit> cateObj = TCategoryMapInitDao.findWhereNameEquals(category);
		  		    //				System.out.println("cateObj is null ? "+(cateObj==null));
		  		    				if(cateObj != null && cateObj.size()>0){
		  		    					if(cateObj.size()>1){
		  		    						 appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb, "CATEGORY在数据库中记录不唯一，请核查数据库一致性", columnStrs);
		  		    	   		    		  mark = -3;
		  		    	   		    		  parseExcelError = true;
		  		    	   		    		  continue;
		  		    					}else{
		  		    						TCategoryMapInit cate = cateObj.get(0);
		  		    						if(cate != null){
		  		    							cateid = cate.getId();
		  		    						}
		  		    					}
		  		    					}else{
		  		    						appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb, "设备类型"+category+"在数据库中不存在", columnStrs);
		  		    	   		    		  mark = -3;
		  		    	   		    		  parseExcelError = true;
		  		    	   		    		  continue;
		  		    					}
		  		    			//	System.out.println("mrid is "+mrid+" cateid is "+cateid+" subcategory is "+subcategory+" model is "+model);
		  		    				TDeviceTypeInit devicetype = TDeviceTypeInitDao.findByMul(mrid, cateid, subcategory, model);
		  		    			//	System.out.println("devicetype is null?: "+(devicetype==null));
		  		    				if(devicetype != null){
		  		   // 					System.out.println("dtid from muti is "+devicetype.getDtid());
		  		    					pstmt_insert.setLong(aa, devicetype.getDtid());
		  		    				}else{
		  		    					  appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb, "CATEGORY/SUBCATEGORY/MODEL在数据库中记录不唯一，请核查数据库一致性", columnStrs);
	  		    	   		    		  mark = -3;
	  		    	   		    		  parseExcelError = true;
	  		    	   		    		  continue;
		  		    				}
		  		    				}else{
		  		    //					System.out.println("all is null-------");
		  		    					pstmt_insert.setNull(aa, java.sql.Types.NUMERIC);
		  		    				}
	  		    				
	  		    			  }else{//get dtid by objectid
	  		  //  				System.out.println("objectid is not null ");
	  		    				  List<TDeviceTypeInit> devtype = TDeviceTypeInitDao.findWhereObjectidEquals(objectid);
	  		    				  if(devtype != null && devtype.size()>0){
	  		    					  if(devtype.size()>1){
	  		    						appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb,"OBJECTID在数据库中记录不唯一，请核查数据库一致性", columnStrs);
	  		    	   		    		  mark = -3;
	  		    	   		    		  parseExcelError = true;
	  		    	   		    		  continue;
	  		    					  }else{
	  		    						  TDeviceTypeInit dev = devtype.get(0);
	  		    		//				  System.out.println("dtid final is : "+dev.getDtid());
	  		    						  pstmt_insert.setLong(aa, dev.getDtid());
	  		    					  }
	  		    				  }else{
	  		    					appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb,"OBJECTID为"+objectid+"的设备不存在。", columnStrs);
  		    	   		    		  mark = -3;
  		    	   		    		  parseExcelError = true;
  		    	   		    		  continue;
	  		    				  }
	  		    			  }
  		    					}
						
					}catch(Exception e){
						 appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrs);
	  		    		  mark = -3;
	  		    		  parseExcelError = true;
	  		    		  break;
					}
						}else{//eTmp is not null
					
					String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
					String ipStr = sheet.getCell(mapping.get("DEVIP").getIndex(),r).getContents();
			//		System.out.println("devip is :"+ipStr);
//					System.out.println("iterator=" + aa + " ColumnName=" + columnStrs[aa-1]+" cell content=" + cellContentTmp+"");
		    	  	System.out.println("COLUMN IS : "+eTmp.getName());
		    	  	
					if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
				    	mark  = -3;
				    	parseExcelError = true;
				    	appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
				    	break;
		    	  	}else{
			    	
	  		    	  try{
	  		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
	  		    			  if(!eTmp.getName().equalsIgnoreCase("DEVID")&&!eTmp.getName().equalsIgnoreCase("IPDECODE")){
	  		    	//			  System.out.println("normal column-------and cellContentTmp is null?: "+(cellContentTmp==null));
	  		    			   if(cellContentTmp == null || cellContentTmp.equals("null")||cellContentTmp == ""){
	  		    	//			   System.out.println("null--------");
	  		    				pstmt_insert.setNull(aa, java.sql.Types.NUMERIC);
	  		    				
	  		    				}
			    			   else{
			    		//		  System.out.println("not null");
			    		//		  System.out.println("cellContentTmp is : "+cellContentTmp);
			    				  pstmt_insert.setLong(aa,Long.parseLong(cellContentTmp));
			    				  
			    			   }
	  		    			  }else{
	  		    		//		  System.out.println("no normal column---------");
	  		    				if(eTmp.getName().equalsIgnoreCase("DEVID")){
	  		    					System.out.println("column devid--------");
	  		    					long devid = genPkNumber.getID();
	  		    					pstmt_insert.setLong(aa, devid);
	  		    					System.out.println("devid = "+devid);
	  		    				}
	  		    				
	  		    				
	  		    				
	  		    				if(eTmp.getName().equalsIgnoreCase("IPDECODE")){
	  		    		//			System.out.println("column ipdecode----------");
	  		    					 long ipdecode = IPAddr.ip2Long(ipStr);
	 		  		    			 pstmt_insert.setLong(aa,ipdecode);
	  		    				}
	  		    				
	  		    				
	  		    				
	  		    			  }
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
	  		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
	  		    		  }
	  		    	  }catch(Exception e){
	  		    		  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName()+"数据类型错误，无法解析", columnStrs);
	  		    		  mark = -3;
	  		    		  parseExcelError = true;
	  		    		  break;
	  		    	  }
			    	  }
		    	  }
				}

		    	  if(!parseExcelError){
	  				  try {
	  		//			  System.out.println("insert begin----------");
	  					  pstmt_insert.execute();
	  		//			  System.out.println("insert end-------");
					}catch(DataIntegrityViolationException dupe){
						mark = -3;
						appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+dupe.getMessage(), columnStrs);
						dupe.printStackTrace();
					}catch (Exception e) {
						mark = -3;
						appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+e.getMessage(), columnStrs);
						e.printStackTrace();
					}	
		    	  }
	  	  }else{//update
					  for(int aa=1; aa<=sqlsCol_update.length; aa++){
	  		    	   ExcelTableMapping eTmp = mapping.get(sqlsCol_update[aa-1].toUpperCase());
	  		 //   	   System.out.println("cols in updatecols now is "+sqlsCol_update[aa-1].toUpperCase());
	  		//    	   System.out.println("eTmp in update is null ?: "+(eTmp==null));
						
						if(eTmp==null){
			//				System.out.println("eTmp null invoked----------");
							String manufacture = sheet.getCell(indexmrid, r).getContents().trim();
							String category = sheet.getCell(indexdtid, r).getContents().trim();
							String subcategory = sheet.getCell(indexsubcate, r).getContents().trim();
							String model = sheet.getCell(indexmodel, r).getContents().trim();
			//				String wcommunity = sheet.getCell(indexwcomm, r).getContents();
							String objectid = sheet.getCell(indexobjid, r).getContents().trim();
				//			System.out.println("manufacture is : "+manufacture+" and category is : "+category+" and subcategory is : "+subcategory+" model is : "+model+"and objectid is : "+objectid);
						try{
					//		System.out.println("eTmp is null : column is "+sqlsCol_insert[aa].toUpperCase());
							if(sqlsCol_insert[aa].toUpperCase().equalsIgnoreCase("MRID")){

			    	//				System.out.println("column mrid-----");
	  		    				  if(manufacture != null){
	  		    					List<TManufacturerInfoInit> manus = new ArrayList<TManufacturerInfoInit>();
	  		    					  try{
	  		    				         manus = TManufacturerInfoInitDao.findWhereMrnameEquals(manufacture);
	  		    					  }catch(Exception e){
	  		    						  appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb, "MANUFACTURE在数据库中记录不唯一，请核查数据库一致性", columnStrs);
			    	   		    		  mark = -3;
			    	   		    		  parseExcelError = true;
			    	   		    		  continue;
	  		    					  }
	  		    				if(manus != null && manus.size()>0){
	  		    					if(manus.size()>1){
	  		    						 appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb, "MANUFACTURE在数据库中记录不唯一，请核查数据库一致性", columnStrs);
	  		    	   		    		  mark = -3;
	  		    	   		    		  parseExcelError = true;
	  		    	   		    		  continue;
	  		    					}else{
	  		    						TManufacturerInfoInit manu = manus.get(0);
	  		    						if(manu != null){
	  		    							long mrid = manu.getMrid();
	  		    		//					System.out.println("mrid final is : "+mrid);
	  		    							pstmt_update.setLong(aa, mrid);
	  		    		//					System.out.println("mrid = "+mrid);
	  		    						}
	  		    					}
	  		    					
	  		    				}else{
	  		    				pstmt_update.setNull(aa, java.sql.Types.NUMERIC);
	  		    	//			System.out.println("mrid = :null");
	  		    			  }
	  		    			  }
			    			  
							}
							
							if(sqlsCol_insert[aa].toUpperCase().equalsIgnoreCase("DTID")){
			    			//		System.out.println("column dtid----");
			    			//		System.out.println("objectid is null ? "+(objectid==""));
	  		    					if(objectid == null||objectid.equals("")){
		  		    				  if(manufacture==null||manufacture.equals("") ||category == null || category.equals("") || subcategory == null||subcategory.equals("") || model == null||model.equals("")){
		  		    		//			System.out.println("all is null-----------");
		  		    					pstmt_update.setNull(aa, java.sql.Types.NUMERIC);
		  		    		//			System.out.println("dtid = :null");
		  		    				  }else{
			  		    	//			    System.out.println("all is not null----------");
			  		    					long mrid=0;
			  		    					long cateid=0;
			  		    					  //get mrid
			  		    					List<TManufacturerInfoInit> manus = TManufacturerInfoInitDao.findWhereMrnameEquals(manufacture);
				  		    				if(manus != null && manus.size()>0){
				  		    					if(manus.size()>1){
				  		    						 appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb, "MANUFACTURE在数据库中记录不唯一，请核查数据库一致性", columnStrs);
				  		    	   		    		  mark = -3;
				  		    	   		    		  parseExcelError = true;
				  		    	   		    		  continue;
				  		    					}else{
				  		    						TManufacturerInfoInit manu = manus.get(0);
				  		    						if(manu != null){
				  		    							mrid = manu.getMrid();
				  		    						}
				  		    					}
				  		    					
				  		    				}
				  		    				//get cateid
				  		    				List<TCategoryMapInit> cateObj = TCategoryMapInitDao.findWhereNameEquals(category);
				  		    	//			System.out.println("cateObj is null ? "+(cateObj==null));
				  		    				if(cateObj != null && cateObj.size()>0){
				  		    					if(cateObj.size()>1){
				  		    						 appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb, "CATEGORY在数据库中记录不唯一，请核查数据库一致性", columnStrs);
				  		    	   		    		  mark = -3;
				  		    	   		    		  parseExcelError = true;
				  		    	   		    		  continue;
				  		    					}else{
				  		    						TCategoryMapInit cate = cateObj.get(0);
				  		    						if(cate != null){
				  		    							cateid = cate.getId();
				  		    						}
				  		    					}
				  		    					}
				  		    			//	System.out.println("mrid is "+mrid+" cateid is "+cateid+" subcategory is "+subcategory+" model is "+model);
				  		    				TDeviceTypeInit devicetype = TDeviceTypeInitDao.findByMul(mrid, cateid, subcategory, model);
				  		    		//		System.out.println("devicetype is null?: "+(devicetype==null));
				  		    				if(devicetype != null){
				  		    		//			System.out.println("dtid from muti is "+devicetype.getDtid());
				  		    					pstmt_update.setLong(aa, devicetype.getDtid());
				  		    		//			System.out.println("dtid = "+devicetype.getDtid());
				  		    				}else{
				  		    					  appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb, "CATEGORY/SUBCATEGORY/MODEL在数据库中记录不唯一，请核查数据库一致性", columnStrs);
			  		    	   		    		  mark = -3;
			  		    	   		    		  parseExcelError = true;
			  		    	   		    		  continue;
				  		    				}
				  		    			//	System.out.println("update : mrid = "+mrid+"cateid = "+cateid);
				  		    				
			  		    				}
		  		    				
		  		    			  }else{//get dtid by objectid
		  		    				  List<TDeviceTypeInit> devtype = TDeviceTypeInitDao.findWhereObjectidEquals(objectid);
		  		    				  if(devtype != null && devtype.size()>0){
		  		    					  if(devtype.size()>1){
		  		    						appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb,"OBJECTID在数据库中记录不唯一，请核查数据库一致性", columnStrs);
		  		    	   		    		  mark = -3;
		  		    	   		    		  parseExcelError = true;
		  		    	   		    		  continue;
		  		    					  }else{
		  		    						  TDeviceTypeInit dev = devtype.get(0);
		  		    		//				  System.out.println("dtid final is : "+dev.getDtid());
		  		    						  pstmt_update.setLong(aa, dev.getDtid());
		  		    	//					  System.out.println("dtid = "+dev.getDtid());
		  		    	//					System.out.println("update dtid from objid : cateid = "+dev.getDtid());
		  		    					  }
		  		    				  }else{
		  		    					appendErrorMessage("T_DEVICE_INFO",sheet.getRow(r), errorMsgWb,"OBJECTID在数据库中记录不唯一，请核查数据库一致性", columnStrs);
	  		    	   		    		  mark = -3;
	  		    	   		    		  parseExcelError = true;
	  		    	   		    		  continue;
		  		    				  }
		  		    			  }
	  		    					
	  		    					
	  		    					}
							
						}catch(Exception e){
							 appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "数据类型错误，无法解析", columnStrs);
		  		    		  mark = -3;
		  		    		  parseExcelError = true;
		  		    		  break;
						}
						
							}else{//eTmp is not null in update
								
	  		    	  String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
	  		    	  
					String ipStr = sheet.getCell(mapping.get("DEVIP").getIndex(),r).getContents();
		//			System.out.println("devip is :"+ipStr);
//					System.out.println("iterator=" + aa + " ColumnName=" + columnStrs[aa-1]+" cell content=" + cellContentTmp+"");
		    	  	System.out.println("COLUMN IS : "+eTmp.getName());
		    	  	
//	  		    	System.out.println("iterator=" + aa + " ColumnName=" + sqlsCol_update[aa-1]+" cell content=" + cellContentTmp+"\n");
	  		    	if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
	  		    		  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
	  			    	  mark = -3;
	  		    		  parseExcelError = true;
	 		    		  break;
	  		    	  }else{
		    		    	  try{
		    		    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
		    		    			  if(cellContentTmp == null || cellContentTmp.equals("null")){
		    		    				  pstmt_update.setNull(aa, java.sql.Types.VARCHAR);
		    		    	//		      System.out.println(eTmp.getName()+ " = :null");
		    		    			  }else{    			    				  
		    		    				  pstmt_update.setString(aa,cellContentTmp);
		    		    				  System.out.println(eTmp.getName()+" = "+cellContentTmp);
		    		    				  }
//		    		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
		    		    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
			  		    			  if(!eTmp.getName().equalsIgnoreCase("DEVID")&&!eTmp.getName().equalsIgnoreCase("IPDECODE")){
			  		    	//			  System.out.println("normal column-------and cellContentTmp is null?: "+(cellContentTmp==null));
			  		    			   if(cellContentTmp == null || cellContentTmp.equals("null")||cellContentTmp == ""){
			  		    	//			   System.out.println("null--------");
			  		    				pstmt_update.setNull(aa, java.sql.Types.NUMERIC);
			  		    	//			System.out.println(eTmp.getName()+" = null");
			  		    				}
					    			   else{
					    	//			  System.out.println("not null");
					    	//			  System.out.println("cellContentTmp is : "+cellContentTmp);
					    				  pstmt_update.setLong(aa,Long.parseLong(cellContentTmp));
					    	//			  System.out.println(eTmp.getName()+ " = "+cellContentTmp);
					    			   }
			  		    			  }else{
			  		    				  System.out.println("no normal column---------");
			  		    				if(eTmp.getName().equalsIgnoreCase("DEVID")){
			  		    	//				System.out.println("devid from map is : "+devmap.get(ipStr));
			  		    					pstmt_update.setLong(aa, Long.parseLong(devmap.get(ipStr)));
			  		    	//			    System.out.println("devid = "+devmap.get(ipStr));
			  		    				}
			  		    				
			  		    				
			  		    				
			  		    				if(eTmp.getName().equalsIgnoreCase("IPDECODE")){
			  		    	//				System.out.println("column ipdecode----------");
			  		    					 long ipdecode = IPAddr.ip2Long(ipStr);
			 		  		    			 pstmt_update.setLong(aa,ipdecode);
			 		  		 //   			 System.out.println("ipdecode = "+ipdecode);
			  		    				}
			  		    				
			  		    				
			  		    				
			  		    			  }
//			  		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
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
					  }
					  if(!parseExcelError){
//						  System.out.println("==Step 4: Execute update command");
						  try {
			//				  System.out.println("execute update begin-----");
							pstmt_update.execute();
			//				System.out.println("execute update end-------");
						  }catch(DataIntegrityViolationException dupe){
								mark = -3;
								appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+dupe.getMessage(), columnStrs);
								dupe.printStackTrace();
							}catch (SQLException e) {
							  mark = -3;						  
							  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+e.getMessage(), columnStrs);
							  e.printStackTrace();
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
			PreparedStatement pstmt_update, String[] sqlsCol_update,Map<String,String> devmap,String ipStr) {
			System.out.println("insertupdatetable PORT invoked----and update is "+ifUpdate);
			int mark = 1;
			
			
			if(!ifUpdate){
//				System.out.println("insert ------------------and r is : "+r);

				for(int aa=1; aa<=sqlsCol_insert.length; aa++){
				//	System.out.println("************************************************");
					ExcelTableMapping eTmp = mapping.get(sqlsCol_insert[aa-1].toUpperCase());
//					System.out.println("eTmp is null ?: "+(eTmp==null));
					
					String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
					
//					System.out.println("iterator=" + aa + " ColumnName=" + columnStrs[aa-1]+" cell content=" + cellContentTmp+"");
//		    	  	System.out.println("COLUMN IS : "+eTmp.getName()+" and null is "+eTmp.getType());
		    	  	
					if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
				    	mark  = -3;
				    	parseExcelError = true;
				    	appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
				    	break;
		    	  	}else{
			    	
	  		    	  try{
	  		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
	  		    			  if(!eTmp.getName().equalsIgnoreCase("PTID")&& !eTmp.getName().equalsIgnoreCase("DEVID")&&!eTmp.getName().equalsIgnoreCase("PDMID")){
//	  		    				System.out.println("normal column---------");
	  		    			   if(cellContentTmp == null || cellContentTmp.equals("null")||cellContentTmp == ""){
//	  		    				   System.out.println("null--------");
	  		    				pstmt_insert.setNull(aa, java.sql.Types.NUMERIC);
	  		    				
	  		    				}
			    			   else{
//			    				  System.out.println("not null");
//			    				  System.out.println("cellContentTmp is : "+cellContentTmp);
			    				  pstmt_insert.setLong(aa,Long.parseLong(cellContentTmp));
			    			   }
	  		    			  }else{
//	  		    				  System.out.println("no normal column---------");
	  		    				 
	  		    				if(eTmp.getName().equalsIgnoreCase("DEVID")){
//	  		    					System.out.println("column devid--------");
//	  		  					    System.out.println("devip is :"+ipStr);
	  		  					    String devidStr = devmap.get(ipStr);
	  		  					    long devid = Long.parseLong(devidStr);
//	  		  					    System.out.println("devid in port from ipStr is : "+devid);
	  		    					pstmt_insert.setLong(aa, devid);
//	  		    					System.out.println("devid = "+devid);
	  		    				}
	  		    				
	  		    				if(eTmp.getName().equalsIgnoreCase("PTID")){
//	  		    					System.out.println("column ptid----------");
	  		    					 long ptid = genPkNumber.getID();
	 		  		    			 pstmt_insert.setLong(aa,ptid);
//	 		  		    			 System.out.println("ptid = "+ptid);
	  		    				}
	  		    				
	  		    				if(eTmp.getName().equalsIgnoreCase("PDMID")){
//	  		    					System.out.println("column pdmid----------");
	  		    					 long pdmid = genPkNumber.getID();
	 		  		    			 pstmt_insert.setLong(aa,pdmid);
//	 		  		    			 System.out.println("pdmid = "+pdmid);
	  		    				}
	  		    				
	  		    			  }
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
	  		    			//  if(!eTmp.getName().equalsIgnoreCase("DEVIP")&&!eTmp.getName().equalsIgnoreCase("SYSNAME")){
	  		    				if(cellContentTmp == null || cellContentTmp.equals("null"))
		  		    				pstmt_insert.setNull(aa, java.sql.Types.VARCHAR);
				    			  else
				    				  pstmt_insert.setString(aa,cellContentTmp);
	  		    		//	  }
	  		    			
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
	  		//			  System.out.println("insert begin----------");
	  					  pstmt_insert.execute();
	  		//			  System.out.println("insert end-------");
					}catch(DataIntegrityViolationException dupe){
						mark = -3;
						appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+dupe.getMessage(), columnStrs);
						dupe.printStackTrace();
					}catch (Exception e) {
						mark = -3;
						appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+e.getMessage(), columnStrs);
						e.printStackTrace();
					}	
		    	  }
	  	  }else{//update
//	  		  System.out.println("update  -------------");
					  for(int aa=1; aa<=sqlsCol_update.length; aa++){
	  		    	   ExcelTableMapping eTmp = mapping.get(sqlsCol_update[aa-1].toUpperCase());
//	  		    	   System.out.println("cols in updatecols now is "+sqlsCol_update[aa-1].toUpperCase()+" and aa is "+aa);
//	  		    	   System.out.println("eTmp in update is null ?: "+(eTmp==null));
								
	  		    	   String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
	  		    	  
					
//					System.out.println("iterator=" + aa + " ColumnName=" + columnStrs[aa-1]+" cell content=" + cellContentTmp+"");
//		    	  	System.out.println("COLUMN IS : "+eTmp.getName());
		    	  	
//	  		    	System.out.println("iterator=" + aa + " ColumnName=" + sqlsCol_update[aa-1]+" cell content=" + cellContentTmp+"\n");
	  		    	if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
	  		    		  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
	  			    	  mark = -3;
	  		    		  parseExcelError = true;
	 		    		  break;
	  		    	  }else{
		    		    	  try{
		    		    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
		    		    			//  if(!eTmp.getName().equalsIgnoreCase("DEVIP")&&!eTmp.getName().equalsIgnoreCase("SYSNAME"))
		    		    			  if(cellContentTmp == null || cellContentTmp.equals("null")){
		    		    				  pstmt_update.setNull(aa, java.sql.Types.VARCHAR);
		    		    	//		      System.out.println(eTmp.getName()+ " = :null");
		    		    			  }else{    			    				  
		    		    				  pstmt_update.setString(aa,cellContentTmp);
		    		    	//			  System.out.println(eTmp.getName()+" = "+cellContentTmp);
		    		    				  }
//		    		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
		    		    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
			  		    			  if(!eTmp.getName().equalsIgnoreCase("DEVID")&&!eTmp.getName().equalsIgnoreCase("PTID")&&!eTmp.getName().equalsIgnoreCase("PDMID")){
			  		    //				  System.out.println("normal column-------and cellContentTmp is null?: "+(cellContentTmp==null));
			  		    			   if(cellContentTmp == null || cellContentTmp.equals("null")||cellContentTmp == ""){
			  		    //				   System.out.println("null--------");
			  		    				pstmt_update.setNull(aa, java.sql.Types.NUMERIC);
			  		    //				System.out.println(eTmp.getName()+" = null");
			  		    				}
					    			   else{
					    				//  System.out.println("not null");
					    			//	  System.out.println("cellContentTmp is : "+cellContentTmp);
					    				  pstmt_update.setLong(aa,Long.parseLong(cellContentTmp));
					    		//		  System.out.println(eTmp.getName()+ " = "+cellContentTmp);
					    			   }
			  		    			  }else{
			  		    	//			  System.out.println("no normal column---------");
			  		    				if(eTmp.getName().equalsIgnoreCase("DEVID")){
			  		    //					System.out.println("devid from map is : "+devmap.get(ipStr));
			  		    					pstmt_update.setLong(aa, Long.parseLong(devmap.get(ipStr)));
			  		    	//			    System.out.println("devid = "+devmap.get(ipStr));
			  		    				}
			  		    				
			  		    				if(eTmp.getName().equalsIgnoreCase("PTID")){
			  		    					
			  		    	//				System.out.println("column ptid----------");
			  		    					String ifdescr = sheet.getCell(mapping.get("IFDESCR").getIndex(),r).getContents();
			  		  		//			    System.out.println("ifdescr is :"+ifdescr);
		  		    		//			    System.out.println("DEVIP IS : "+ipStr+"devid from map is : "+devmap.get(ipStr));
		  		    					    long devid = Long.parseLong(devmap.get(ipStr));
			  		  					    TPortInfo port = TPortInfoDao.findByDevidAndIFdesc(devid, ifdescr);
			  		  		//			    System.out.println("port find by devid and ifdescr is null ? "+(port == null));
			  		  					    if(port != null){
			  		  					    pstmt_update.setLong(aa,port.getPtid());
			  		  		//			    System.out.println("ptid = "+port.getPtid());
			  		  					    }else{
			  		  					      appendErrorMessage("T_PORT_INFO",sheet.getRow(r), errorMsgWb,"PTID在数据库中记录不唯一，请核查数据库一致性", columnStrs);
		  		    	   		    		  mark = -3;
		  		    	   		    		  parseExcelError = true;
		  		    	   		    		  break;
			  		  					    }
			  		    				}
			  		    				
                                        if(eTmp.getName().equalsIgnoreCase("PDMID")){
			  		    					
			  		    	//				System.out.println("column pdmid----------");
			  		    					String oidname = sheet.getCell(mapping.get("OIDNAME").getIndex(),r).getContents();
			  		  					    System.out.println("oidname is :"+oidname);
		  		    		//			    System.out.println("devid from map is : "+devmap.get(ipStr));
		  		    					    long devid = Long.parseLong(devmap.get(ipStr));
			  		  					    PredefmibInfo mib = predefmibInfoDao.findByDevidAndOidname(devid, oidname);
			  		  		//			    System.out.println("mib is null ? "+(mib == null));
			  		  					    if(mib != null){
			  		  					    pstmt_update.setLong(aa,mib.getPdmid());
			  		  					    }
			  		  		//			    System.out.println("pdmid = "+mib.getPdmid());
			  		    				}
			  		    				
			  		    				
			  		    			  }
//			  		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
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
			//				  System.out.println("execute update begin-----");
							pstmt_update.execute();
			//				System.out.println("execute update end-------");
						  }catch(DataIntegrityViolationException dupe){
								mark = -3;
								appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+dupe.getMessage(), columnStrs);
								dupe.printStackTrace();
							}catch (SQLException e) {
							  mark = -3;						  
							  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+e.getMessage(), columnStrs);
							  e.printStackTrace();
						  }
					  }
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
	private int insertUpdateMibTable(String tabName,boolean ifUpdate,boolean ifUpdateGrp,
			String[] sqlsCol_insert,String[] sqlsCol_insertgrp, Sheet sheet,
			Map<String, ExcelTableMapping> mapping, int r,
			boolean parseExcelError, HSSFWorkbook errorMsgWb,
			String[] columnStrs, PreparedStatement pstmt_insert,
			PreparedStatement pstmt_update,PreparedStatement pstmt_insertgrp,
			PreparedStatement pstmt_updategrp,String[] sqlsCol_update,String[] sqlsCol_updategrp,Map<String,String> devmap,String ipStr) {
	//		System.out.println("insertupdatetable MIB invoked----and update is "+ifUpdate);
			int mark = 1;
			if(!ifUpdateGrp){
				for(int a = 1;a <= sqlsCol_insertgrp.length;a++){
					ExcelTableMapping eTmp = mapping.get(sqlsCol_insertgrp[a-1].toUpperCase());
		//			System.out.println("eTmp is null ?: "+(eTmp==null));
					
					String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
		//			System.out.println("COLUMN IS : "+eTmp.getName()+" and null is "+eTmp.getType());
					if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
				    	mark  = -3;
				    	parseExcelError = true;
				    	appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
				    	break;
		    	  	}else{

				    	
		  		    	  try{
		  		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
		  		    			  if(!eTmp.getName().equalsIgnoreCase("MID")){
		  		    //				System.out.println("normal column---------");
		  		    			   if(cellContentTmp == null || cellContentTmp.equals("null")||cellContentTmp == ""){
		  		    //				   System.out.println("null--------");
		  		    				pstmt_insertgrp.setNull(a, java.sql.Types.NUMERIC);
		  		    				
		  		    				}
				    			   else{
				    				  System.out.println("not null");
				   // 				  System.out.println("cellContentTmp is : "+cellContentTmp);
				    				  pstmt_insertgrp.setLong(a,Long.parseLong(cellContentTmp));
				    			   }
		  		    			  }else{
		  		    //				  System.out.println("no normal column---------");
		  		    				 
		  		    				if(eTmp.getName().equalsIgnoreCase("MID")){
		  		    //					System.out.println("column MID--------");
		  		    					long mid = genPkNumber.getID();
		  		    					pstmt_insertgrp.setLong(a, mid);
		  		    //					System.out.println("mid = "+mid);
		  		    				}
		  		    			  }
//		  		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
		  		    		  }else if(eTmp.getType().equalsIgnoreCase("DATE")){
		  		    			if(cellContentTmp == null || cellContentTmp.equals("null"))
		  		    				pstmt_insertgrp.setNull(a, java.sql.Types.DATE);
				    			  else{
				    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yy-MM-dd");
				    				  pstmt_insertgrp.setTimestamp(a,new Timestamp( f.parse(cellContentTmp).getTime()));
				    			  }
//		  		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
		  		    		  }else if(eTmp.getType().equalsIgnoreCase("varchar2")){
		  		    			//  if(!eTmp.getName().equalsIgnoreCase("DEVIP")&&!eTmp.getName().equalsIgnoreCase("SYSNAME")){
		  		    				if(cellContentTmp == null || cellContentTmp.equals("null") || cellContentTmp.equals(""))
			  		    				pstmt_insertgrp.setNull(a, java.sql.Types.VARCHAR);
					    			  else
					    				  pstmt_insertgrp.setString(a,cellContentTmp);
		  		    				
		  		    		//	  }
		  		    			
//		  		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
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
	  		//			  System.out.println("insert grp begin----------");
	  					  pstmt_insertgrp.execute();
	  		//			  System.out.println("insert grp end-------");
					}catch(DataIntegrityViolationException dupe){
						mark = -1;
						appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+dupe.getMessage(), columnStrs);
						dupe.printStackTrace();
					}catch (Exception e) {
						mark = -1;
						appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+e.getMessage(), columnStrs);
						e.printStackTrace();
					}	
		    	  }
			}else{//update
	//	  		  System.out.println("update  mib grp -------------");
				  for(int aa=1; aa<=sqlsCol_updategrp.length; aa++){
  		    	   ExcelTableMapping eTmp = mapping.get(sqlsCol_updategrp[aa-1].toUpperCase());
  	//	    	   System.out.println("cols in updatecols now is "+sqlsCol_updategrp[aa-1].toUpperCase()+" and aa is "+aa);
  	//	    	   System.out.println("eTmp in update is null ?: "+(eTmp==null));
							
  		    	   String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
  		    	  
				
//				System.out.println("iterator=" + aa + " ColumnName=" + columnStrs[aa-1]+" cell content=" + cellContentTmp+"");
	 //   	  	System.out.println("COLUMN IS : "+eTmp.getName());
	    	  	
//  		    	System.out.println("iterator=" + aa + " ColumnName=" + sqlsCol_update[aa-1]+" cell content=" + cellContentTmp+"\n");
  		    	if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
  		    		  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
  			    	  mark = -3;
  		    		  parseExcelError = true;
 		    		  break;
  		    	  }else{
	    		    	  try{
	    		    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
	    		    			//  if(!eTmp.getName().equalsIgnoreCase("DEVIP")&&!eTmp.getName().equalsIgnoreCase("SYSNAME"))
	    		    			  if(cellContentTmp == null || cellContentTmp.equals("null")||cellContentTmp.equals("")){
	    		    				  pstmt_updategrp.setNull(aa, java.sql.Types.VARCHAR);
	    		    	//		      System.out.println(eTmp.getName()+ " = :null");
	    		    			  }else{    			    				  
	    		    				  pstmt_updategrp.setString(aa,cellContentTmp);
	    		    	//			  System.out.println(eTmp.getName()+" = "+cellContentTmp);
	    		    				  }
//	    		    			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
	    		    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
		  		    			  if(!eTmp.getName().equalsIgnoreCase("MID")){
		  		    	//			  System.out.println("normal column-------and cellContentTmp is null?: "+(cellContentTmp==null));
		  		    			   if(cellContentTmp == null || cellContentTmp.equals("null")||cellContentTmp == ""){
		  		    	//			   System.out.println("null--------");
		  		    				pstmt_updategrp.setNull(aa, java.sql.Types.NUMERIC);
		  		    	//			System.out.println(eTmp.getName()+" = null");
		  		    				}
				    			   else{
				    				  System.out.println("not null");
				    	//			  System.out.println("cellContentTmp is : "+cellContentTmp);
				    				  pstmt_updategrp.setLong(aa,Long.parseLong(cellContentTmp));
				    	//			  System.out.println(eTmp.getName()+ " = "+cellContentTmp);
				    			   }
		  		    			  }else{
		  		    	//			  System.out.println("no normal column---------");
		  		    				
                                    if(eTmp.getName().equalsIgnoreCase("MID")){
		  		    					
		  		    	//				System.out.println("column mid----------");
		  		    				    ExcelTableMapping eTmpt = mapping.get("NAME");
	  		    		//			    System.out.println("eTmp is null ?: "+(eTmp==null));
	  		    					    String name = sheet.getCell(eTmpt.getIndex(), r).getContents();
		  		  					    List<DefMibGrp> grp = defMibGrpDao.findWhereNameEquals(name);
		  		  		//			    System.out.println("grp is null ? "+(grp == null));
		  		  					    long mid=0;
		  		  					    if(grp != null && grp.size()>0){
		  		  					    	mid = grp.get(0).getMid();
		  		  					    }
		  		  					    pstmt_updategrp.setLong(aa, mid);
		  		  		//			    System.out.println("mid = "+mid);
		  		    				}
		  		    				
		  		    				
		  		    			  }
//		  		    			  System.out.print(aa+" number " + cellContentTmp + "\t");
		  		    		  }else if(eTmp.getType().equalsIgnoreCase("date")){
	    		    	  		if(cellContentTmp == null || cellContentTmp.equals("null"))
	    		    	  			pstmt_updategrp.setNull(aa, java.sql.Types.DATE);
	    		    			  else{
	    		    				  java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    		    				  pstmt_updategrp.setTimestamp(aa,new Timestamp( f.parse(cellContentTmp).getTime()));
	    		    			  }
//	    		    			  System.out.print(aa+" Date " + cellContentTmp + "\t");
	    		    		  }
	    		    	  }catch(Exception ew){
	    		    		  mark = -3;
	    		    		  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName()+" 数据类型错误，无法解析", columnStrs);
    				    		    		  ew.printStackTrace();
//	    		    		  System.out.print("In insert catch:aa=" + aa + " type=" + eTmp.getType() +  " "+eTmp.getName()+" 数据类型错误，无法解析");
	      		    		  parseExcelError = true;
	    		    		  break;
	    		    	  }
  		    	  }
		    	  
				  }
				  if(!parseExcelError){
//					  System.out.println("==Step 4: Execute update command");
					  try {
			//			  System.out.println("execute update grp begin-----");
						pstmt_updategrp.execute();
		//				System.out.println("execute update grp end-------");
					  }catch(DataIntegrityViolationException dupe){
							mark = -1;
							appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+dupe.getMessage(), columnStrs);
							dupe.printStackTrace();
						}catch (SQLException e) {
						  mark = -1;						  
						  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+e.getMessage(), columnStrs);
						  e.printStackTrace();
					  }
				  }
  	  
				
			}
			
			/*try {
				List<DefMibGrp> grps = defMibGrpDao.findAll();
				for(DefMibGrp grp : grps){
	//				System.out.println("grp name is "+grp.getName()+" and grp mid is "+grp.getMid());
				}
			} catch (DefMibGrpDaoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			
			if(!ifUpdate){
		//		System.out.println("insert mib------------------and r is : "+r);

				for(int aa=1; aa<=sqlsCol_insert.length; aa++){
				//	System.out.println("************************************************");
					ExcelTableMapping eTmp = mapping.get(sqlsCol_insert[aa-1].toUpperCase());
			//		System.out.println("eTmp is null ?: "+(eTmp==null));
					String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
					
//					System.out.println("iterator=" + aa + " ColumnName=" + columnStrs[aa-1]+" cell content=" + cellContentTmp+"");
		  //  	  	System.out.println("COLUMN IS : "+eTmp.getName()+" and null is "+eTmp.getType());
		    	  	
					if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
				    	mark  = -3;
				    	parseExcelError = true;
				    	appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
				    	break;
		    	  	}else{
			    	
	  		    	  try{
	  		    		  if(eTmp.getType().equalsIgnoreCase("NUMBER")){
	  		    			  if(!eTmp.getName().equalsIgnoreCase("DEVID")&&!eTmp.getName().equalsIgnoreCase("PDMID")&&!eTmp.getName().equalsIgnoreCase("MID")){
	  		    		//		System.out.println("normal column---------");
	  		    			   if(cellContentTmp == null || cellContentTmp.equals("null")||cellContentTmp == ""){
	  		    		//		   System.out.println("null--------");
	  		    				pstmt_insert.setNull(aa, java.sql.Types.NUMERIC);
	  		    				
	  		    				}
			    			   else{
			    		//		  System.out.println("not null");
			    		//		  System.out.println("cellContentTmp is : "+cellContentTmp);
			    				  pstmt_insert.setLong(aa,Long.parseLong(cellContentTmp));
			    			   }
	  		    			  }else{
	  		    		//		  System.out.println("no normal column---------");
	  		    				 
	  		    				if(eTmp.getName().equalsIgnoreCase("DEVID")){
	  		    		//			System.out.println("column devid--------");
	  		  			//		    System.out.println("devip is :"+ipStr);
	  		  					    String devidStr = devmap.get(ipStr);
	  		  					    long devid = Long.parseLong(devidStr);
	  		  			//		    System.out.println("devid in port from ipStr is : "+devid);
	  		    					pstmt_insert.setLong(aa, devid);
	  		    		//			System.out.println("devid = "+devid);
	  		    				}
	  		    				
	  		    				if(eTmp.getName().equalsIgnoreCase("PDMID")){
	  		    		//			System.out.println("column pdmid----------");
	  		    					 long pdmid = genPkNumber.getID();
	 		  		    			 pstmt_insert.setLong(aa,pdmid);
	 		  		   // 			 System.out.println("pdmid = "+pdmid);
	  		    				}
	  		    				
	  		    				if(eTmp.getName().equalsIgnoreCase("MID")){
	  		    		//			System.out.println("column mid----------");
	  		    					    ExcelTableMapping eTmpt = mapping.get("NAME");
	  		    		//			    System.out.println("eTmp is null ?: "+(eTmp==null));
	  		    					    String name = sheet.getCell(eTmpt.getIndex(), r).getContents();
		  		  		//			    System.out.println("name is "+name);
		  		  					    List<DefMibGrp> grp = defMibGrpDao.findWhereNameEquals(name);
		  		  		//			    System.out.println("grp is null ? "+(grp == null)+" and grp size is "+grp.size()); 
		  		  					    long mid=0;
		  		  					    if(grp != null && grp.size()>0){
		  		  					    	mid = grp.get(0).getMid();
		  		  					    }
		  		  					    pstmt_insert.setLong(aa, mid);
		  		  			//		    System.out.println("mid = "+mid);
	  		    				}
	  		    				
	  		    			  }
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
	  		    			//  if(!eTmp.getName().equalsIgnoreCase("DEVIP")&&!eTmp.getName().equalsIgnoreCase("SYSNAME")){
	  		    				if(cellContentTmp == null || cellContentTmp.equals("null"))
		  		    				pstmt_insert.setNull(aa, java.sql.Types.VARCHAR);
				    			  else
				    				  pstmt_insert.setString(aa,cellContentTmp);
	  		    		//	  }
	  		    			
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
	  	//				  System.out.println("insert mib begin----------");
	  					  pstmt_insert.execute();
	  	//				  System.out.println("insert mib end-------");
					}catch(DataIntegrityViolationException dupe){
						mark = -3;
						appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+dupe.getMessage(), columnStrs);
						dupe.printStackTrace();
					}catch (Exception e) {
						mark = -3;
						appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+e.getMessage(), columnStrs);
						e.printStackTrace();
					}	
		    	  }
	  	  }else{//update
	  //		  System.out.println("update  mib-------------");
					  for(int aa=1; aa<=sqlsCol_update.length; aa++){
	  		    	   ExcelTableMapping eTmp = mapping.get(sqlsCol_update[aa-1].toUpperCase());
	  	//	    	   System.out.println("cols in updatecols now is "+sqlsCol_update[aa-1].toUpperCase()+" and aa is "+aa);
	  	//	    	   System.out.println("eTmp in update is null ?: "+(eTmp==null));
								
	  		    	   String cellContentTmp = sheet.getCell(eTmp.getIndex(), r).getContents();
	  		    	  
					
//					System.out.println("iterator=" + aa + " ColumnName=" + columnStrs[aa-1]+" cell content=" + cellContentTmp+"");
		//    	  	System.out.println("COLUMN IS : "+eTmp.getName());
		    	  	
//	  		    	System.out.println("iterator=" + aa + " ColumnName=" + sqlsCol_update[aa-1]+" cell content=" + cellContentTmp+"\n");
	  		    	if(!eTmp.isNull() && (cellContentTmp == null || cellContentTmp.equals(""))){
	  		    		  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, eTmp.getName() +" 字段不能为空", columnStrs); //not done. copy the row with errors to policyPeriodErrorMsg		    	
	  			    	  mark = -3;
	  		    		  parseExcelError = true;
	 		    		  break;
	  		    	  }else{
		    		    	  try{
		    		    		  if(eTmp.getType().equalsIgnoreCase("varchar2")){
		    		    			//  if(!eTmp.getName().equalsIgnoreCase("DEVIP")&&!eTmp.getName().equalsIgnoreCase("SYSNAME"))
		    		    			  if(cellContentTmp == null || cellContentTmp.equals("null")){
		    		    				  pstmt_update.setNull(aa, java.sql.Types.VARCHAR);
		    		   // 			      System.out.println(eTmp.getName()+ " = :null");
		    		    			  }else{    			    				  
		    		    				  pstmt_update.setString(aa,cellContentTmp);
		    		   // 				  System.out.println(eTmp.getName()+" = "+cellContentTmp);
		    		    				  }
//		    		   // 			  System.out.print(aa+" varchar " + cellContentTmp + "\t");
		    		    		  }else if(eTmp.getType().equalsIgnoreCase("number")){
			  		    			  if(!eTmp.getName().equalsIgnoreCase("DEVID")&&!eTmp.getName().equalsIgnoreCase("PDMID")&&!eTmp.getName().equalsIgnoreCase("MID")){
			  		   // 				  System.out.println("normal column-------and cellContentTmp is null?: "+(cellContentTmp==null));
			  		    			   if(cellContentTmp == null || cellContentTmp.equals("null")||cellContentTmp == ""){
			  		    //				   System.out.println("null--------");
			  		    				pstmt_update.setNull(aa, java.sql.Types.NUMERIC);
			  		    //				System.out.println(eTmp.getName()+" = null");
			  		    				}
					    			   else{
					    //				  System.out.println("not null");
					    //				  System.out.println("cellContentTmp is : "+cellContentTmp);
					    				  pstmt_update.setLong(aa,Long.parseLong(cellContentTmp));
					    //				  System.out.println(eTmp.getName()+ " = "+cellContentTmp);
					    			   }
			  		    			  }else{
			  		    //				  System.out.println("no normal column---------");
			  		    				if(eTmp.getName().equalsIgnoreCase("DEVID")){
			  		    //					System.out.println("devid from map is : "+devmap.get(ipStr));
			  		    					pstmt_update.setLong(aa, Long.parseLong(devmap.get(ipStr)));
			  		    //				    System.out.println("devid = "+devmap.get(ipStr));
			  		    				}
			  		    				
			  		    				
                                        if(eTmp.getName().equalsIgnoreCase("PDMID")){
			  		    					
			  		   // 					System.out.println("column pdmid----------");
			  		    					String oidname = sheet.getCell(mapping.get("OIDNAME").getIndex(),r).getContents();
			  		  					    System.out.println("oidname is :"+oidname);
		  		    	//				    System.out.println("devid from map is : "+devmap.get(ipStr));
		  		    					    long devid = Long.parseLong(devmap.get(ipStr));
		  		    					    ExcelTableMapping eTmpt = mapping.get("NAME");
		  		    	//				    System.out.println("eTmp is null ?: "+(eTmp==null));
		  		    					    String name = sheet.getCell(eTmpt.getIndex(), r).getContents();
			  		  	//				    System.out.println("name is "+name);
  		  		  		//			        System.out.println("name is "+name);
  		  		  					        List<DefMibGrp> grp = defMibGrpDao.findWhereNameEquals(name);
  		  		  		//			        System.out.println("grp is null ? "+(grp == null));
  		  		  					        long mid=grp.get(0).getMid();
			  		  					    PredefmibInfo mib = predefmibInfoDao.findByDevidAndOidnameAndMid(devid, mid, oidname);
			  		  	//				    System.out.println("mib is null ? "+(mib == null));
			  		  					    if(mib != null){
			  		  					    pstmt_update.setLong(aa,mib.getPdmid());
			  		  					    }
			  		  	//				    System.out.println("pdmid = "+mib.getPdmid());
			  		    				}
                                        
                                        if(eTmp.getName().equalsIgnoreCase("MID")){
        	  		    //					System.out.println("column mid----------");
        	  		    					ExcelTableMapping eTmpt = mapping.get("NAME");
    	  		    	//				    System.out.println("eTmp is null ?: "+(eTmp==null));
    	  		    					    String name = sheet.getCell(eTmpt.getIndex(), r).getContents();
    		  		  	//				    System.out.println("name is "+name);
        		  		  					    List<DefMibGrp> grp = defMibGrpDao.findWhereNameEquals(name);
        		  		//  					    System.out.println("grp is null ? "+(grp == null));
        		  		  					    long mid=0;
        		  		  					    if(grp != null && grp.size()>0){
        		  		  					    	mid = grp.get(0).getMid();
        		  		  					    }
        		  		  					    pstmt_update.setLong(aa, mid);
        		  		//  					    System.out.println("mid = "+mid);
        	  		    				}
			  		    				
			  		    				
			  		    			  }
//			  	//	    			  System.out.print(aa+" number " + cellContentTmp + "\t");
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
			//				  System.out.println("execute update mib begin-----");
							pstmt_update.execute();
			//				System.out.println("execute update mib end-------");
						  }catch(DataIntegrityViolationException dupe){
								mark = -3;
								appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+dupe.getMessage(), columnStrs);
								dupe.printStackTrace();
							}catch (SQLException e) {
							  mark = -3;						  
							  appendErrorMessage(tabName,sheet.getRow(r), errorMsgWb, "SQL错误:"+e.getMessage(), columnStrs);
							  e.printStackTrace();
						  }
					  }
	  	  }
			return mark;
		
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
			System.out.println("tfSheet is null ? "+(tfSheet == null));
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
		//				System.out.println("colTitles[c]="+c+", "+colTitles[c]+" "+e.getMessage());
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
					System.out.println("write exception-----------");
		//			System.out.println("colTitles[c]="+c+", "+colTitles[c]+" "+e.getMessage());
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
	


	/**
	 * Init view
	 * @param rs
	 * @param columnStrs
	 * @param mapping
	 * @param viewName
	 * @return
	 */
	private int initDevTableMapping(ResultSetMetaData rs, String[] columnStrs, Map<String, ExcelTableMapping> mapping, String tableName) {
	//	System.out.println("init dev table mapping---");
		int count = 0;
		 try {
			 for(int i=1; i<=rs.getColumnCount(); i++){
					ExcelTableMapping et = new ExcelTableMapping();
					String name = rs.getColumnName(i);
					String type = rs.getColumnTypeName(i);
				//	System.out.println("type is : "+type);
					String isNull = rs.isNullable(i)==0?"N":"Y";
					
		//			System.out.println("et name is : "+name+" and type is : "+type);
					if(!name.equalsIgnoreCase("mrid")&& !name.equalsIgnoreCase("dtid")/*&& !name.equalsIgnoreCase("wcommunity")*/){
		//				System.out.println("normal name-----");
					 et.setName(name);
				     et.setType(type);
				  if(/*name.equalsIgnoreCase("devip")||*/name.equalsIgnoreCase("sysname")||name.equalsIgnoreCase("domainid")){
					  et.setNull(false);
				  }else{
					  et.setNull(true);
				  }
				  
				  
				  et.setIndex(-1);
				  for(int ii=0; ii<columnStrs.length; ii++){
					  if(columnStrs[ii].equalsIgnoreCase(name)/*||columnStrs[ii].equalsIgnoreCase("MANUFACTURE")
						||columnStrs[ii].equalsIgnoreCase("CATEGORY")||columnStrs[ii].equalsIgnoreCase("SUBCATEGORY")
						||columnStrs[ii].equalsIgnoreCase("WCOMMUNITY")*/){
						  et.setIndex(ii);
						  break;
					  }
					  
				  }
				  if(et.getIndex() == -1){	//no corresponding column in excel. excel file error

					  Log4jInit.ncsLog.error("导入 " + tableName + "失败:" + et.getName() + "在Excel中无对应列");
					  return -2;
				  }
				mapping.put(et.getName().toUpperCase(), et);
			    count++;
				  
				}/*else{
					System.out.println("no normal name--------");
					if(name.equalsIgnoreCase("mrid")){
						et.setName(name);
						et.setType("VARCHAR2");
						et.setNull(true);
						et.setIndex(-1);
						for(int ii=0;ii<columnStrs.length;i++){
							if(columnStrs[ii].equalsIgnoreCase("manufacture")){
								et.setIndex(ii);
								break;
							}
							
						}
					}
					if(name.equalsIgnoreCase("dtid")){

						et.setName(name);
						et.setType(type);
						et.setNull(true);
						et.setIndex(-1);
						for(int ii=0;ii<columnStrs.length;i++){
							if(columnStrs[ii].equalsIgnoreCase("objectid")){
								et.setIndex(ii);
								break;
							}
							
						}
					
					}
				}*/
					
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
	private int initTableMapping(ResultSetMetaData rs, String[] columnStrs, Map<String, ExcelTableMapping> mapping, String tableName) {
	//	System.out.println("init dev table mapping---");
		int count = 0;
		 try {
			 for(int i=1; i<=rs.getColumnCount(); i++){
					ExcelTableMapping et = new ExcelTableMapping();
					String name = rs.getColumnName(i);
					String type = rs.getColumnTypeName(i);
				//	System.out.println("type is : "+type);
					String isNull = rs.isNullable(i)==0?"N":"Y";
					
		//			System.out.println("et name is : "+name+" and type is : "+type);
					 et.setName(name);
				     et.setType(type);
                  //   et.setNull(true);
				  if(name.equalsIgnoreCase("oidname")||name.equalsIgnoreCase("name")||name.equalsIgnoreCase("ifdescr")){
					  et.setNull(false);
				  }else{
					  et.setNull(true);
				  }
				  
				  
				  et.setIndex(-1);
				  for(int ii=0; ii<columnStrs.length; ii++){
					  System.out.println("colum");
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

	public TDeviceInfoDao getTDeviceInfoDao() {
		return TDeviceInfoDao;
	}

	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}

	public TPortInfoDao getTPortInfoDao() {
		return TPortInfoDao;
	}

	public void setTPortInfoDao(TPortInfoDao portInfoDao) {
		TPortInfoDao = portInfoDao;
	}

	public PredefmibInfoDao getPredefmibInfoDao() {
		return predefmibInfoDao;
	}

	public void setPredefmibInfoDao(PredefmibInfoDao predefmibInfoDao) {
		this.predefmibInfoDao = predefmibInfoDao;
	}

	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}

	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
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


	public TDeviceTypeInitDao getTDeviceTypeInitDao() {
		return TDeviceTypeInitDao;
	}


	public void setTDeviceTypeInitDao(TDeviceTypeInitDao deviceTypeInitDao) {
		TDeviceTypeInitDao = deviceTypeInitDao;
	}


	public DefMibGrpDao getDefMibGrpDao() {
		return defMibGrpDao;
	}


	public void setDefMibGrpDao(DefMibGrpDao defMibGrpDao) {
		this.defMibGrpDao = defMibGrpDao;
	}


	

	
	

	
	

	
}
