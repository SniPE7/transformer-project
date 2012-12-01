package com.ibm.ncs.util.excel;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import au.com.bytecode.opencsv.CSVReader;

import com.ibm.ncs.model.dto.DefMibGrp;
import com.ibm.ncs.model.dto.PredefmibInfo;
import com.ibm.ncs.model.dto.SnmpEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TDeviceInfo;
import com.ibm.ncs.model.dto.TDeviceTypeInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TOidgroupInfoInit;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.dto.TPortInfo;
import com.ibm.ncs.model.dto.VEventType;
import com.ibm.ncs.model.dto.VMfCateDevtype;
import com.ibm.ncs.model.dto.VOidGroup;
import com.ibm.ncs.model.dto.VPerformParam;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.web.policyapply.bean.NonTimeFramePolicyDetails;
import com.ibm.ncs.web.policyapply.bean.SyslogPolicyDetails;


public class DBTOExcel {
	
	
	public static HSSFWorkbook exportPolicyDetails(String pDetailsTabName, List<NonTimeFramePolicyDetails> pDetailsList, String periodTabName, 
			List<TPolicyPeriod> peroidList, List<NonTimeFramePolicyDetails> messageList, 
			List<SyslogPolicyDetails> syslogPolicyDetailsList, String syslogPolicyDetailsName)
	{
		//create a work book
		HSSFWorkbook wb = new HSSFWorkbook();
		try {

			HSSFSheet sheet; 
			if(pDetailsList!= null && pDetailsList.size()>0){
				sheet = wb.createSheet(pDetailsTabName);//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
	
	    		int index=0;
	    		createCell(row, index++, "Mpid", titleStyle);	
	    		createCell(row, index++, "Modid", titleStyle);	
	    		createCell(row, index++, "Eveid", titleStyle);	
	    		createCell(row, index++, "Poll", titleStyle);	
	    		createCell(row, index++, "Value_1", titleStyle);	
	    		createCell(row, index++, "Severity_1", titleStyle);	
	    		createCell(row, index++, "Filter_A", titleStyle);	
	    		createCell(row, index++, "Value_2", titleStyle);	
	    		createCell(row, index++, "Severity_2", titleStyle);	
	    		createCell(row, index++, "Filter_B", titleStyle);	
	    		createCell(row, index++, "Severity_A", titleStyle);	
	    		createCell(row, index++, "Severity_B", titleStyle);	
	    		createCell(row, index++, "Oidgroup", titleStyle);	
	    		createCell(row, index++, "Ogflag", titleStyle);	
	    		createCell(row, index++, "Value_1_Low", titleStyle);	
	    		createCell(row, index++, "Value_2_Low", titleStyle);	
	    		createCell(row, index++, "V1l_Severity_1", titleStyle);	
	    		createCell(row, index++, "V1l_Severity_A", titleStyle);	
	    		createCell(row, index++, "V2l_Severity_2", titleStyle);	
	    		createCell(row, index++, "V2l_Severity_B", titleStyle);	    		
	    		createCell(row, index++, "CompareType", titleStyle);
	    		createCell(row, index++, "MPName", titleStyle);			//start T_Policy_Base
	    		createCell(row, index++, "Category", titleStyle);		
	    		createCell(row, index++, "Description", titleStyle);		
	    		createCell(row, index++, "MName", titleStyle);			//start T_Module_Info_Init
	    		createCell(row, index++, "MCode", titleStyle);		
	    		createCell(row, index++, "Description", titleStyle);		
	    		createCell(row, index++, "ETID", titleStyle);				//start T_Event_Type_Init
	    		createCell(row, index++, "ESTID", titleStyle);		
	    		createCell(row, index++, "EVEOthername", titleStyle);		
	    		createCell(row, index++, "ECode", titleStyle);		
	    		createCell(row, index++, "General", titleStyle);		
	    		createCell(row, index++, "Major", titleStyle);		
	    		createCell(row, index++, "Minor", titleStyle);		
	    		createCell(row, index++, "Other", titleStyle);		
	    		createCell(row, index++, "Description", titleStyle);		
	    		createCell(row, index++, "Useflag", titleStyle);
	    		
	    		if( pDetailsList != null){
					//set row style
	//	    		HSSFCellStyle style = wb.createCellStyle();
	    			for(int i=0; i<pDetailsList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							NonTimeFramePolicyDetails p = pDetailsList.get(i);
//	    				System.out.println("\t\ti=" + i + "\tpolicy details=" + p);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getPolicyDetails().getMpid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().getModid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().getEveid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().isPollNull()?null:p.getPolicyDetails().getPoll()), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicyDetails().getValue1()==null?null:p.getPolicyDetails().getValue1())), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().isSeverity1Null()?null:p.getPolicyDetails().getSeverity1()), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicyDetails().getFilterA()==null?null:p.getPolicyDetails().getFilterA())), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicyDetails().getValue2()==null?null:p.getPolicyDetails().getValue2())), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().isSeverity2Null()?null:p.getPolicyDetails().getSeverity2()), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicyDetails().getFilterB()==null?null:p.getPolicyDetails().getFilterB())), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().isSeverityANull()?null:p.getPolicyDetails().getSeverityA()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().isSeverityBNull()?null:p.getPolicyDetails().getSeverityB()), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicyDetails().getOidgroup()==null?null:p.getPolicyDetails().getOidgroup())), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicyDetails().getOgflag()==null?null:p.getPolicyDetails().getOgflag())), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicyDetails().getValue1Low()==null?null:p.getPolicyDetails().getValue1Low())), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicyDetails().getValue2Low()==null?null:p.getPolicyDetails().getValue2Low())), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().isV1lSeverity1Null()?null:p.getPolicyDetails().getV1lSeverity1()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().isV1lSeverityANull()?null:p.getPolicyDetails().getV1lSeverityA()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().isV2lSeverity2Null()?null:p.getPolicyDetails().getV2lSeverity2()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().isV2lSeverityBNull()?null:p.getPolicyDetails().getV2lSeverityB()), titleStyle);			    		
							createCell(row, index++, String.valueOf((p.getPolicyDetails().getComparetype()==null?null:p.getPolicyDetails().getComparetype())), titleStyle);	

							createCell(row, index++, String.valueOf((p.getPolicyBase().getMpname()==null?null:p.getPolicyBase().getMpname())), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyBase().getCategory()), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicyBase().getDescription()==null?null:p.getPolicyBase().getDescription())), titleStyle);	
							

							createCell(row, index++, String.valueOf((p.getModuleInfoInit().getMname()==null?null:p.getModuleInfoInit().getMname())), titleStyle);	
							createCell(row, index++, String.valueOf(p.getModuleInfoInit().getMcode()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getModuleInfoInit().getDescription()), titleStyle);	
							

							createCell(row, index++, String.valueOf(p.getEventTypeInit().getEtid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEventTypeInit().getEstid()), titleStyle);	
							createCell(row, index++, String.valueOf((p.getEventTypeInit().getEveothername()==null?null:p.getEventTypeInit().getEveothername())), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEventTypeInit().getEcode()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEventTypeInit().getGeneral()), titleStyle);	
							createCell(row, index++, String.valueOf((p.getEventTypeInit().getMajor()==null?null:p.getEventTypeInit().getMajor())), titleStyle);	
							createCell(row, index++, String.valueOf((p.getEventTypeInit().getMinor()==null?null:p.getEventTypeInit().getMinor())), titleStyle);	
							createCell(row, index++, String.valueOf((p.getEventTypeInit().getOther()==null?null:p.getEventTypeInit().getOther())), titleStyle);	
							createCell(row, index++, String.valueOf((p.getEventTypeInit().getDescription()==null?null:p.getEventTypeInit().getDescription())), titleStyle);	
							createCell(row, index++, String.valueOf((p.getEventTypeInit().getUseflag()==null?null:p.getEventTypeInit().getUseflag())), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <pDetailsList>, message: " + e.getMessage());
							e.printStackTrace();
						}	
	    			}
	    		}
			}

			if(peroidList!= null && peroidList.size()>0){
				sheet = wb.createSheet(periodTabName);//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
	
	    		int index=0;
	    		createCell(row, index++, "Ppid", titleStyle);	
	    		createCell(row, index++, "PpName", titleStyle);	
	    		createCell(row, index++, "Start_Time", titleStyle);	
	    		createCell(row, index++, "End_Time", titleStyle);	
	    		createCell(row, index++, "Description", titleStyle);	
	    		createCell(row, index++, "Workday", titleStyle);	
	    		createCell(row, index++, "Enabled", titleStyle);	
	    		createCell(row, index++, "DefaultFlag", titleStyle);	
	    		createCell(row, index++, "S1", titleStyle);	
	    		createCell(row, index++, "S2", titleStyle);	
	    		createCell(row, index++, "S3", titleStyle);	
	    		createCell(row, index++, "S4", titleStyle);	
	    		createCell(row, index++, "S5", titleStyle);	
	    		createCell(row, index++, "S6", titleStyle);	
	    		createCell(row, index++, "S7", titleStyle);	
	    		createCell(row, index++, "E1", titleStyle);	
	    		createCell(row, index++, "E2", titleStyle);	
	    		createCell(row, index++, "E3", titleStyle);	
	    		createCell(row, index++, "E4", titleStyle);	
	    		createCell(row, index++, "E5", titleStyle);	
	    		createCell(row, index++, "E6", titleStyle);	
	    		createCell(row, index++, "E7", titleStyle);		
	    		
	    		if( peroidList != null){
					//set row style
	//	    		HSSFCellStyle style = wb.createCellStyle();
	    			for(int i=0; i<peroidList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							TPolicyPeriod p = peroidList.get(i);
//	    				System.out.println("\t\ti=" + i + "\tpolicy details=" + p);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getPpid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPpname()), titleStyle);	
							System.out.println("start time=" + p.getStartTime());
							 java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							Timestamp timestamp = new Timestamp(f.parse(p.getStartTime().toString()).getTime());
							System.out.println("after parse. start time=" + timestamp);
							createCell(row, index++, String.valueOf(timestamp), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEndTime()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getDescription()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getWorkday()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEnabled()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getDefaultflag()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS1()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS2()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS3()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS4()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS5()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS6()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS7()), titleStyle);
							createCell(row, index++, String.valueOf(p.getE1()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getE2()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getE3()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getE4()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getE5()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getE6()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getE7()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <peroidList>, message: " + e.getMessage());
							e.printStackTrace();
						}				    		
	    			}
	    		}
			}
			
			if(syslogPolicyDetailsList!= null && syslogPolicyDetailsList.size()>0){
				sheet = wb.createSheet(syslogPolicyDetailsName);//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
	
	    		int index=0;
	    		createCell(row, index++, "SpID", titleStyle);	
	    		createCell(row, index++, "MpID", titleStyle);	
	    		createCell(row, index++, "Mark", titleStyle);	
	    		createCell(row, index++, "Manufacture", titleStyle);	
	    		createCell(row, index++, "EventType", titleStyle);	
	    		createCell(row, index++, "Severity1", titleStyle);	
	    		createCell(row, index++, "Severity2", titleStyle);	
	    		createCell(row, index++, "FilterFlag1", titleStyle);
	    		createCell(row, index++, "FilterFlag2", titleStyle);	
	    		createCell(row, index++, "MPName", titleStyle);			//start T_Policy_Base
	    		createCell(row, index++, "Category", titleStyle);		
	    		createCell(row, index++, "Description", titleStyle);	
	    		
	    		if( syslogPolicyDetailsList != null){
	    			for(int i=0; i<syslogPolicyDetailsList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							SyslogPolicyDetails p = syslogPolicyDetailsList.get(i);
//	    				System.out.println("\t\ti=" + i + "\tpolicy details=" + p);
							createCell(row, index++, String.valueOf(p.getPolicySyslog().getSpid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicySyslog().getMpid()), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicySyslog().getMark()==null?null:p.getPolicySyslog().getMark())), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicySyslog().getManufacture()==null?null:p.getPolicySyslog().getManufacture())), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicySyslog().getEventtype() == 0?"8":p.getPolicySyslog().getEventtype())), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicySyslog().isSeverity1Null()?null:p.getPolicySyslog().getSeverity1()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicySyslog().isSeverity2Null()?null:p.getPolicySyslog().getSeverity2()), titleStyle);		
							createCell(row, index++, String.valueOf(p.getPolicySyslog().getFilterflag1()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicySyslog().getFilterflag2()), titleStyle);	

							createCell(row, index++, String.valueOf((p.getPolicyBase().getMpname()==null?null:p.getPolicyBase().getMpname())), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyBase().getCategory()), titleStyle);	
							createCell(row, index++, String.valueOf((p.getPolicyBase().getDescription()==null?null:p.getPolicyBase().getDescription())), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in syslogPolicyDetailsList, message: " + e.getMessage());
							e.printStackTrace();
						}		
	    			}
	    		}
			}
			
			//to handle error messasge
			if(messageList!= null && messageList.size()>0){
				sheet = wb.createSheet("Error_Message");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
	    		//set title color
	    		titleStyle.setFillForegroundColor((short)44);
	    		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    		//set title font
	    		HSSFFont hsFont = wb.createFont();
	    		hsFont.setBoldweight((short) 700);
	    		titleStyle.setFont(hsFont);
	
	    		int index=0;
	    		createCell(row, index++, "Mpid", titleStyle);	
	    		createCell(row, index++, "Modid", titleStyle);	
	    		createCell(row, index++, "Eveid", titleStyle);	
	    		createCell(row, index++, "MPName", titleStyle);			//start T_Policy_Base			
	    		createCell(row, index++, "MName", titleStyle);		
	    		createCell(row, index++, "Error Message", titleStyle);
	    		
	    		if( messageList != null){
					//set row style
	    			for(int i=0; i<messageList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							NonTimeFramePolicyDetails p = messageList.get(i);
//	    				System.out.println("\t\ti=" + i + "\tpolicy details=" + p);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getPolicyDetails().getMpid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().getModid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPolicyDetails().getEveid()), titleStyle);	
							
							createCell(row, index++, String.valueOf(p.getPolicyBase().getMpname()), titleStyle);	
							
							createCell(row, index++, String.valueOf(p.getModuleInfoInit().getMname()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getMessage()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in NonTimeFramePolicyDetails.messageList, message: " + e.getMessage());
							e.printStackTrace();
						}
	    			}
	    		}
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    		Log4jInit.ncsLog.error("InterruptedException in Export, message: " + e.getMessage());
    	}
    	return wb;
    	
	}
	
	public static List<String> exportPolicyDetailsToCSV(String csvFilePath)
	{
//		System.out.println("\t\tin DB to Excel. java********");
		
		//create a work book
		List<String> wb = null;
		try {

			CSVReader csvWriter = null;
			File tempFile = null;
			FileReader fWriter = null;
				tempFile = new File(csvFilePath);
				fWriter = new FileReader(tempFile);
				csvWriter = new CSVReader(fWriter);
				
				wb = csvWriter.readAll();
			}catch(IOException ioex){
				ioex.printStackTrace();
			}
    	return wb;
    	
	}
	
	
	/**
	 * export base info to excel file
	 * @param exportList
	 * @param msgList
	 * @return
	 */
	public static HSSFWorkbook exportBaseInfo(Map<String, Object> exportList, Map<String, Map<String, Object>> msgList)
	{
		//create a work book
		HSSFWorkbook wb = new HSSFWorkbook();
		try {
			HSSFSheet sheet; 

			if(msgList!=null){
				
	    		int rowCount = 0,index=0;
				if(msgList.containsKey("V_EVENT_TYPE")){
					sheet = wb.getSheet("Error_Message");
					if(sheet == null)						
						sheet = wb.createSheet("Error_Message");//create a sheet
					
		    		HSSFRow row = sheet.createRow(rowCount);//create title row
		    		//set title style
		    		HSSFCellStyle titleStyle = wb.createCellStyle();
		    		
					Map<String,Object> tabName =  new HashMap<String, Object>();
					tabName = msgList.get("V_EVENT_TYPE");	
					index=0;
    				row = sheet.createRow(rowCount);
    				
    				createCell(row, index++, "ErrorMessage", titleStyle);	
    	    		createCell(row, index++, "ETID", titleStyle);	
    	    		createCell(row, index++, "MODID", titleStyle);	
    	    		createCell(row, index++, "EVEID", titleStyle);	
    	    		createCell(row, index++, "Major", titleStyle);	
    	    		createCell(row, index++, "Minor", titleStyle);	
    	    		createCell(row, index++, "Other", titleStyle);	
    	    		rowCount++;
					if(tabName.containsKey("MODID")){
						java.sql.ResultSet rs= (ResultSet) tabName.get("MODID");
						
						while(rs.next()){						
							try {
								row = sheet.createRow(rowCount);
								index=0;
								createCell(row, index++, String.valueOf(tabName.get("MODID_Message")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getInt("ETID")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getInt("MODID")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getInt("EVEID")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Major")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Minor")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Other")), titleStyle);	
								rowCount++;
							} catch (Exception e) {
								Log4jInit.ncsLog.error("Exception in Export row in MODID_Message, message: " + e.getMessage());
								e.printStackTrace();
							}
						}	
						rs.close();
					}
				}
				if(msgList.containsKey("V_OID_GROUP")){
					rowCount++;
					sheet = wb.getSheet("Error_Message");
					if(sheet == null)						
						sheet = wb.createSheet("Error_Message");//create a sheet
					
		    		HSSFRow row = sheet.createRow(rowCount);//create title row
		    		//set title style
		    		HSSFCellStyle titleStyle = wb.createCellStyle();
		    		
					System.out.println("V_OID_GROUP is not null");
					Map<String,Object> tabName =  new HashMap<String, Object>();
					tabName = msgList.get("V_OID_GROUP");	
					index=0;
    				row = sheet.createRow(rowCount);
    				
    				createCell(row, index++, "ErrorMessage", titleStyle);	
    	    		createCell(row, index++, "OPID", titleStyle);	
    	    		createCell(row, index++, "OIDValue", titleStyle);	
    	    		createCell(row, index++, "OIDName", titleStyle);	
    	    		createCell(row, index++, "OIDUnit", titleStyle);	
    	    		createCell(row, index++, "Flag", titleStyle);	
    	    		createCell(row, index++, "OIDIndex", titleStyle);	
    	    		rowCount++;
					if(tabName.containsKey("OPID")){
						java.sql.ResultSet rs= (ResultSet) tabName.get("OPID");
						
						while(rs.next()){							
							try {
								row = sheet.createRow(rowCount);
								index=0;
								createCell(row, index++, String.valueOf(tabName.get("OPID_Message")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getInt("OPID")), titleStyle);
								createCell(row, index++, String.valueOf(rs.getString("OIDValue")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("OIDName")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("OIDUnit")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Flag")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("OIDIndex")), titleStyle);	
								rowCount++;
							} catch (Exception e) {
								Log4jInit.ncsLog.error("Exception in Export row in OPID_Message, message: " + e.getMessage());
								e.printStackTrace();
							}
						}	
						rs.close();
					}
				}
				
				if(msgList.containsKey("V_MF_CATE_DEVTYPE")){
					sheet = wb.getSheet("Error_Message");
					if(sheet == null)						
						sheet = wb.createSheet("Error_Message");//create a sheet
					
		    		HSSFRow row = sheet.createRow(0);//create title row
		    		//set title style
		    		HSSFCellStyle titleStyle = wb.createCellStyle();
		    		
					System.out.println("V_MF_CATE_DEVTYPE is not null");
					Map<String,Object> tabName =  new HashMap<String, Object>();
					tabName = msgList.get("V_MF_CATE_DEVTYPE");	
					index=0;
    				row = sheet.createRow(rowCount);
    				
    				createCell(row, index++, "ErrorMessage", titleStyle);	
    	    		createCell(row, index++, "MRID", titleStyle);	
    	    		createCell(row, index++, "DTID", titleStyle);	
    	    		createCell(row, index++, "Category", titleStyle);	
    	    		createCell(row, index++, "SubCategory", titleStyle);	
    	    		createCell(row, index++, "Model", titleStyle);		
    	    		createCell(row, index++, "ObjectID", titleStyle);	
    	    		createCell(row, index++, "Logo", titleStyle);	
    	    		createCell(row, index++, "Description", titleStyle);
    	    		rowCount++;
					if(tabName.containsKey("ID")){
						java.sql.ResultSet rs= (ResultSet) tabName.get("ID");
						while(rs.next()){						
							try {
								row = sheet.createRow(rowCount);
								index=0;
								createCell(row, index++, String.valueOf(tabName.get("ID_Message")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getInt("MRID")), titleStyle);
								createCell(row, index++, String.valueOf(rs.getInt("DTID")), titleStyle);
								createCell(row, index++, String.valueOf(rs.getInt("Category")), titleStyle);
								createCell(row, index++, String.valueOf(rs.getString("SubCategory")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Model")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("ObjectID")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Logo")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Description")), titleStyle);	
								rowCount++;
							} catch (Exception e) {
								Log4jInit.ncsLog.error("Exception in Export row in ID_Message, message: " + e.getMessage());
								e.printStackTrace();
							}
						}
						rs.close();
					}

					if(tabName.containsKey("MRID")){
						java.sql.ResultSet rs= (ResultSet) tabName.get("MRID");
						
						while(rs.next()){							
							try {
								row = sheet.createRow(rowCount);
								index=0;
								createCell(row, index++, String.valueOf(tabName.get("MRID_Message")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getInt("MRID")), titleStyle);
								createCell(row, index++, String.valueOf(rs.getInt("DTID")), titleStyle);
								createCell(row, index++, String.valueOf(rs.getInt("Category")), titleStyle);
								createCell(row, index++, String.valueOf(rs.getString("SubCategory")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Model")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("ObjectID")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Logo")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Description")), titleStyle);	
								rowCount++;
							} catch (Exception e) {
								Log4jInit.ncsLog.error("Exception in Export row in MRID_Message, message: " + e.getMessage());
								e.printStackTrace();
							}
						}
						rs.close();
					}
				}

				if(msgList.containsKey("V_PERFORM_PARAM")){
					rowCount++;
					sheet = wb.getSheet("Error_Message");
					if(sheet == null)						
						sheet = wb.createSheet("Error_Message");//create a sheet
					
		    		HSSFRow row = sheet.createRow(rowCount);//create title row
		    		//set title style
		    		HSSFCellStyle titleStyle = wb.createCellStyle();
		    		
					System.out.println("V_PERFORM_PARAM is not null");
					Map<String,Object> tabName =  new HashMap<String, Object>();
					tabName = msgList.get("V_PERFORM_PARAM");	
					index=0;
    				row = sheet.createRow(rowCount);
    				
    				createCell(row, index++, "ErrorMessage", titleStyle);	
    	    		createCell(row, index++, "MRID", titleStyle);	
    	    		createCell(row, index++, "DTID", titleStyle);	
    	    		createCell(row, index++, "MODID", titleStyle);	
    	    		createCell(row, index++, "EVEID", titleStyle);	
    	    		createCell(row, index++, "OIDGroupName", titleStyle);	
    	    		createCell(row, index++, "OID", titleStyle);		
    	    		createCell(row, index++, "Unit", titleStyle);		
    	    		createCell(row, index++, "Description", titleStyle);	
    	    		rowCount++;
					if(tabName.containsKey("EVEID")){
						java.sql.ResultSet rs= (ResultSet) tabName.get("EVEID");
						
						while(rs.next()){						
							try {
								row = sheet.createRow(rowCount);
								index=0;
								createCell(row, index++, String.valueOf(tabName.get("EVEID_Message")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getInt("MRID")), titleStyle); 	
								createCell(row, index++, String.valueOf(rs.getInt("DTID")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getInt("MODID")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getInt("EVEID")), titleStyle);
								createCell(row, index++, String.valueOf(rs.getString("OIDGroupName")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("OID")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Unit")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Description")), titleStyle);	
								rowCount++;
							} catch (Exception e) {
								Log4jInit.ncsLog.error("Exception in Export row in EVEID_Message, message: " + e.getMessage());
								e.printStackTrace();
							}
						}	
						rs.close();
					}
					if(tabName.containsKey("DTID")){
						java.sql.ResultSet rs= (ResultSet) tabName.get("DTID");
						
						while(rs.next()){						
							try {
									row = sheet.createRow(rowCount);
									index=0;
									createCell(row, index++, String.valueOf(tabName.get("DTID_Message")), titleStyle);	
									createCell(row, index++, String.valueOf(rs.getInt("MRID")), titleStyle); 	
									createCell(row, index++, String.valueOf(rs.getInt("DTID")), titleStyle);	
									createCell(row, index++, String.valueOf(rs.getInt("MODID")), titleStyle);	
									createCell(row, index++, String.valueOf(rs.getInt("EVEID")), titleStyle);
									createCell(row, index++, String.valueOf(rs.getString("OIDGroupName")), titleStyle);	
									createCell(row, index++, String.valueOf(rs.getString("OID")), titleStyle);	
									createCell(row, index++, String.valueOf(rs.getString("Unit")), titleStyle);	
									createCell(row, index++, String.valueOf(rs.getString("Description")), titleStyle);	
									rowCount++;
							} catch (Exception e) {
								Log4jInit.ncsLog.error("Exception in Export row in DTID_Message, message: " + e.getMessage());
								e.printStackTrace();
							}
						}	
						rs.close();
					}
					if(tabName.containsKey("OIDGROUPNAME")){
						java.sql.ResultSet rs= (ResultSet) tabName.get("OIDGROUPNAME");
						while(rs.next()){					
							try {
								row = sheet.createRow(rowCount);
								index=0;
								createCell(row, index++, String.valueOf(tabName.get("OIDGROUPNAME_Message")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getInt("MRID")), titleStyle); 	
								createCell(row, index++, String.valueOf(rs.getInt("DTID")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getInt("MODID")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getInt("EVEID")), titleStyle);
								createCell(row, index++, String.valueOf(rs.getString("OIDGroupName")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("OID")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Unit")), titleStyle);	
								createCell(row, index++, String.valueOf(rs.getString("Description")), titleStyle);	
								rowCount++;
							} catch (Exception e) {
								Log4jInit.ncsLog.error("Exception in Export row in OIDGROUPNAME_Message, message: " + e.getMessage());
								e.printStackTrace();
							}
						}	
						rs.close();
					}
				}
			}

			if(exportList.containsKey( new String("T_Manufacturer_Info_Init").toUpperCase())){
				sheet = wb.createSheet("T_Manufacturer_Info_Init");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();

				List<TManufacturerInfoInit> TManufacturerInfoInitList = new ArrayList();
				
				TManufacturerInfoInitList = (List<TManufacturerInfoInit>) exportList.get(new String("T_Manufacturer_Info_Init").toUpperCase());
	    		int index=0;
	    		//from T_Manufacturer_Info_Init
	    		createCell(row, index++, "MRId", titleStyle);	
	    		createCell(row, index++, "MRName", titleStyle);	
	    		createCell(row, index++, "ObjectID", titleStyle);	
	    		createCell(row, index++, "Description", titleStyle);
	    		if( TManufacturerInfoInitList != null){
	    			for(int i=0; i<TManufacturerInfoInitList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							TManufacturerInfoInit p = TManufacturerInfoInitList.get(i);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getMrid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getMrname()),titleStyle);	
							createCell(row, index++, String.valueOf(p.getObjectid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getDescription()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <TManufacturerInfoInitList>, message: " + e.getMessage());
							e.printStackTrace();
						}
			    	}
	    		}
			}
			
			if(exportList.containsKey( new String("T_Category_Map_Init").toUpperCase())){
				sheet = wb.createSheet("T_Category_Map_Init");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<TCategoryMapInit> TCategoryMapInitList = new ArrayList();
				TCategoryMapInitList = (List<TCategoryMapInit>) exportList.get(new String("T_Category_Map_Init").toUpperCase());
	    		int index=0;
	    		//from T_Manufacturer_Info_Init
	    		createCell(row, index++, "ID", titleStyle);	
	    		createCell(row, index++, "Name", titleStyle);	
	    		createCell(row, index++, "Flag", titleStyle);	
	    		if( TCategoryMapInitList != null){
	    			for(int i=0; i<TCategoryMapInitList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							TCategoryMapInit p = TCategoryMapInitList.get(i);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getId()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getName()),titleStyle);	
							createCell(row, index++, String.valueOf(p.getFlag()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <TCategoryMapInitList>, message: " + e.getMessage());
							e.printStackTrace();
						}	
			    	}
	    		}
			}
			
			if(exportList.containsKey( new String("Def_Mib_Grp").toUpperCase())){
				sheet = wb.createSheet("Def_Mib_Grp");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<DefMibGrp> DefMibGrpList = new ArrayList();
				DefMibGrpList = (List<DefMibGrp>) exportList.get(new String("Def_Mib_Grp").toUpperCase());
	    		int index=0;
	    		//from T_Manufacturer_Info_Init
	    		createCell(row, index++, "MID", titleStyle);	
	    		createCell(row, index++, "Name", titleStyle);	
	    		createCell(row, index++, "IndexOID", titleStyle);
	    		createCell(row, index++, "IndexVar", titleStyle);
	    		createCell(row, index++, "DescrOID", titleStyle);
	    		createCell(row, index++, "DescrVar", titleStyle);	
	    		if( DefMibGrpList != null){
	    			for(int i=0; i<DefMibGrpList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							DefMibGrp p = DefMibGrpList.get(i);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getMid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getName()),titleStyle);	
							createCell(row, index++, String.valueOf(p.getIndexoid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getIndexvar()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getDescroid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getDescrvar()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <DefMibGrpList>, message: " + e.getMessage());
							e.printStackTrace();
						}	
			    	}
	    		}
			}
			
			if(exportList.containsKey( new String("T_MODULE_INFO_INIT").toUpperCase())){
				sheet = wb.createSheet("T_MODULE_INFO_INIT");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<TModuleInfoInit> TModuleInfoInitList = new ArrayList();
				TModuleInfoInitList = (List<TModuleInfoInit>) exportList.get(new String("T_MODULE_INFO_INIT").toUpperCase());
	    		int index=0;
	    		//from T_Manufacturer_Info_Init
	    		createCell(row, index++, "MODID", titleStyle);	
	    		createCell(row, index++, "MCode", titleStyle);	
	    		createCell(row, index++, "MName", titleStyle);
	    		createCell(row, index++, "DESCRIPTION", titleStyle);	
	    		if( TModuleInfoInitList != null){
	    			for(int i=0; i<TModuleInfoInitList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							TModuleInfoInit p = TModuleInfoInitList.get(i);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getModid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getMcode()),titleStyle);	
							createCell(row, index++, String.valueOf(p.getMname()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getDescription()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <TModuleInfoInitList>, message: " + e.getMessage());
							e.printStackTrace();
						}	
			    	}
	    		}
			}
			
			if(exportList.containsKey( new String("T_OIDGROUP_INFO_INIT").toUpperCase())){
				sheet = wb.createSheet("T_OIDGROUP_INFO_INIT");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<TOidgroupInfoInit> TOidgroupInfoInitList = new ArrayList();
				TOidgroupInfoInitList = (List<TOidgroupInfoInit>) exportList.get(new String("T_OIDGROUP_INFO_INIT").toUpperCase());
	    		int index=0;
	    		//from T_Manufacturer_Info_Init
	    		createCell(row, index++, "OPID", titleStyle);	
	    		createCell(row, index++, "OIDGROUPNAME", titleStyle);	
	    		createCell(row, index++, "OTYPE", titleStyle);
	    		createCell(row, index++, "DESCRIPTION", titleStyle);
	    		if( TOidgroupInfoInitList != null){
	    			for(int i=0; i<TOidgroupInfoInitList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							TOidgroupInfoInit p = TOidgroupInfoInitList.get(i);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getOpid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getOidgroupname()),titleStyle);	
							createCell(row, index++, String.valueOf(p.getOtype()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getDescription()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <TOidgroupInfoInitList>, message: " + e.getMessage());
							e.printStackTrace();
						}		
			    	}
	    		}
			}
			
			if(exportList.containsKey("V_MF_CATE_DEVTYPE")){
				sheet = wb.createSheet("V_MF_CATE_DEVTYPE");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<VMfCateDevtype> VMfCateDevtypeList = new ArrayList();
				VMfCateDevtypeList = (List<VMfCateDevtype>) exportList.get("V_MF_CATE_DEVTYPE");
	    		int index=0;
	    		//from t_category_map_init
	    		createCell(row, index++, "ID", titleStyle);	
	    		createCell(row, index++, "Name", titleStyle);	
	    		createCell(row, index++, "Flag", titleStyle);	
	    		
	    		//from t_manufacture_info_init
	    		createCell(row, index++, "MRID", titleStyle);	
	    		createCell(row, index++, "MRName", titleStyle);	
	    		createCell(row, index++, "MF_ObjectID", titleStyle);	
	    		createCell(row, index++, "MF_Description", titleStyle);	
	    		
	    		//from t_device_type_init
	    		createCell(row, index++, "devicetype_MRID", titleStyle);	
	    		createCell(row, index++, "DTID", titleStyle);	
	    		createCell(row, index++, "Category", titleStyle);	
	    		createCell(row, index++, "SubCategory", titleStyle);	
	    		createCell(row, index++, "Model", titleStyle);	
	    		createCell(row, index++, "ObjectID", titleStyle);	
	    		createCell(row, index++, "Logo", titleStyle);	
	    		createCell(row, index++, "Description", titleStyle);	
	    		
	    		if( VMfCateDevtypeList != null){
	    			for(int i=0; i<VMfCateDevtypeList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							VMfCateDevtype p = VMfCateDevtypeList.get(i);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getId()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getName()),titleStyle);	
							createCell(row, index++, String.valueOf(p.getFlag()), titleStyle);	
							
							createCell(row, index++, String.valueOf(p.getMrid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getMrname()), titleStyle);
							createCell(row, index++, String.valueOf(p.getMfObjectid()), titleStyle);
							createCell(row, index++, String.valueOf(p.getMfDescription()), titleStyle);

							createCell(row, index++, String.valueOf(p.getDevicetypeMrid()), titleStyle);
							createCell(row, index++, String.valueOf(p.getDtid()), titleStyle);
							createCell(row, index++, String.valueOf(p.isCategoryNull()?null:p.getCategory()), titleStyle);
							createCell(row, index++, String.valueOf(p.getSubcategory()), titleStyle);
							createCell(row, index++, String.valueOf(p.getModel()), titleStyle);
							createCell(row, index++, String.valueOf(p.getObjectid()), titleStyle);
							createCell(row, index++, String.valueOf(p.getLogo()), titleStyle);
							createCell(row, index++, String.valueOf(p.getDescription()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <VMfCateDevtypeList>, message: " + e.getMessage());
							e.printStackTrace();
						}
			    	}
	    		}
			}
			
			if(exportList.containsKey("V_EVENT_TYPE")){
				sheet = wb.createSheet("V_EVENT_TYPE");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<VEventType> VEventTypeList = new ArrayList();
				VEventTypeList = (List<VEventType>) exportList.get("V_EVENT_TYPE");
	    		int index=0;
	    		//from t_event_type_init
	    		createCell(row, index++, "MODID", titleStyle);	
	    		createCell(row, index++, "EVEID", titleStyle);	
	    		createCell(row, index++, "ETID", titleStyle);	
	    		createCell(row, index++, "ESTID", titleStyle);	
	    		createCell(row, index++, "EveOtherName", titleStyle);	
	    		createCell(row, index++, "Ecode", titleStyle);	
	    		createCell(row, index++, "General", titleStyle);	
	    		createCell(row, index++, "Major", titleStyle);	
	    		createCell(row, index++, "Minor", titleStyle);	
	    		createCell(row, index++, "Other", titleStyle);	
	    		createCell(row, index++, "Description", titleStyle);	
	    		createCell(row, index++, "UseFlag", titleStyle);	
	    		
	    		//from t_module_info_init
	    		createCell(row, index++, "T_Module_Info_Init_ModID", titleStyle);	
	    		createCell(row, index++, "MName", titleStyle);	
	    		createCell(row, index++, "MCode", titleStyle);	
	    		createCell(row, index++, "T_Module_Info_Init_Description", titleStyle);		
	    		
	    		if( VEventTypeList != null){
	    			for(int i=0; i<VEventTypeList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							VEventType p = VEventTypeList.get(i);
							//create title row
							createCell(row, index++, String.valueOf(p.getModid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEveid()),titleStyle);	
							createCell(row, index++, String.valueOf(p.isEtidNull()?null:p.getEtid()), titleStyle);				    		
							createCell(row, index++, String.valueOf(p.isEstidNull()?null:p.getEstid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEveothername()), titleStyle);
							createCell(row, index++, String.valueOf(p.getEcode()), titleStyle);
							createCell(row, index++, String.valueOf(p.isGeneralNull()?null:p.getGeneral()), titleStyle);
							createCell(row, index++, String.valueOf(p.getMajor()), titleStyle);
							createCell(row, index++, String.valueOf(p.getMinor()), titleStyle);
							createCell(row, index++, String.valueOf(p.getOther()), titleStyle);
							createCell(row, index++, String.valueOf(p.getDescription()), titleStyle);
							createCell(row, index++, String.valueOf(p.getUseflag()), titleStyle);
							
							createCell(row, index++, String.valueOf(p.getTModuleInfoInitModid()), titleStyle);
							createCell(row, index++, String.valueOf(p.getMname()), titleStyle);
							createCell(row, index++, String.valueOf(p.getMcode()), titleStyle);
							createCell(row, index++, String.valueOf(p.getTModuleInfoInitDescription()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <VEventTypeList>, message: " + e.getMessage());
							e.printStackTrace();
						}
			    	}
	    		}
			}
			
			if(exportList.containsKey("V_OID_GROUP")){
				sheet = wb.createSheet("V_OID_GROUP");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<VOidGroup> VOidGroupList = new ArrayList();
				VOidGroupList = (List<VOidGroup>) exportList.get("V_OID_GROUP");
	    		int index=0;
	    		//from t_oidgroup_info_init
	    		createCell(row, index++, "T_OIDGroup_Info_Init_Opid", titleStyle);	
	    		createCell(row, index++, "OIDGroupName", titleStyle);	
	    		createCell(row, index++, "OType", titleStyle);	
	    		createCell(row, index++, "Description", titleStyle);
	    		
	    		//from t_oidgroup_details_init
	    		createCell(row, index++, "T_OIDGroup_Details_Init_Opid", titleStyle);	
	    		createCell(row, index++, "OIDValue", titleStyle);	
	    		createCell(row, index++, "OIDName", titleStyle);	
	    		createCell(row, index++, "OIDUnit", titleStyle);	
	    		createCell(row, index++, "Flag", titleStyle);	
	    		createCell(row, index++, "OIDIndex", titleStyle);		
	    		
	    		if( VOidGroupList != null){
	    			for(int i=0; i<VOidGroupList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							VOidGroup p = VOidGroupList.get(i);
							//create title row
							createCell(row, index++, String.valueOf(p.getTOidgroupInfoInitOpid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getOidgroupname()),titleStyle);	
							createCell(row, index++, String.valueOf(p.getOtype()), titleStyle);				    		
							createCell(row, index++, String.valueOf(p.getDescription()), titleStyle);	
							
							createCell(row, index++, String.valueOf(p.getTOidgroupDetailsInitOpid()), titleStyle);
							createCell(row, index++, String.valueOf(p.getOidvalue()), titleStyle);
							createCell(row, index++, String.valueOf(p.getOidname()), titleStyle);
							createCell(row, index++, String.valueOf(p.getOidunit()), titleStyle);
							createCell(row, index++, String.valueOf(p.getFlag()), titleStyle);
							createCell(row, index++, String.valueOf(p.isOidindexNull()?null:p.getOidindex()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <VOidGroupList>, message: " + e.getMessage());
							e.printStackTrace();
						}
			    	}
	    		}
			}
			
			if(exportList.containsKey("V_PERFORM_PARAM")){
				sheet = wb.createSheet("V_PERFORM_PARAM");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<VPerformParam> VPerformParamList = new ArrayList();
				VPerformParamList = (List<VPerformParam>) exportList.get("V_PERFORM_PARAM");
	    		int index=0;
	    		//from t_event_type_init
	    		createCell(row, index++, "EventType_Modid", titleStyle);	
	    		createCell(row, index++, "EventType_Eveid", titleStyle);	
	    		createCell(row, index++, "Etid", titleStyle);	
	    		createCell(row, index++, "Estid", titleStyle);
	    		createCell(row, index++, "EveOtherName", titleStyle);	
	    		createCell(row, index++, "Ecode", titleStyle);	
	    		createCell(row, index++, "General", titleStyle);	
	    		createCell(row, index++, "Major", titleStyle);	
	    		createCell(row, index++, "Minor", titleStyle);	
	    		createCell(row, index++, "Other", titleStyle);	
	    		createCell(row, index++, "eventtype_DESCRIPTION", titleStyle);	
	    		createCell(row, index++, "UseFlag", titleStyle);	
	    		
	    		//from t_oidgroup_info_init
	    		createCell(row, index++, "OIDGroup_Opid", titleStyle);	
	    		createCell(row, index++, "OIDGroup_OIDGroupName", titleStyle);	
	    		createCell(row, index++, "OType", titleStyle);	
	    		createCell(row, index++, "OIDGroup_Description", titleStyle);	
	    		
	    		//from t_event_oid_init
	    		createCell(row, index++, "EveId", titleStyle);	
	    		createCell(row, index++, "Mrid", titleStyle);	
	    		createCell(row, index++, "Dtid", titleStyle);	
	    		createCell(row, index++, "OIDGroupName", titleStyle);	
	    		createCell(row, index++, "Modid", titleStyle);	
	    		createCell(row, index++, "OID", titleStyle);	
	    		createCell(row, index++, "Unit", titleStyle);	
	    		createCell(row, index++, "Description", titleStyle);	
	    		
	    		//from t_device_type_init
	    		createCell(row, index++, "DevType_Mrid", titleStyle);	
	    		createCell(row, index++, "DevType_Dtid", titleStyle);	
	    		createCell(row, index++, "Category", titleStyle);	
	    		createCell(row, index++, "SubCategory", titleStyle);	
	    		createCell(row, index++, "Model", titleStyle);	
	    		createCell(row, index++, "ObjectID", titleStyle);	
	    		createCell(row, index++, "Logo", titleStyle);	
	    		createCell(row, index++, "DevType_Description", titleStyle);
	    		
	    		if( VPerformParamList != null){
	    			for(int i=0; i<VPerformParamList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							VPerformParam p = VPerformParamList.get(i);
							//create title row
							createCell(row, index++, String.valueOf(p.getEventtypeModid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEventtypeEveid()), titleStyle);
							createCell(row, index++, String.valueOf(p.isEtidNull()?null:p.getEtid()), titleStyle);
							createCell(row, index++, String.valueOf(p.isEstidNull()?null:p.getEstid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEveothername()), titleStyle);
							createCell(row, index++, String.valueOf(p.getEcode()), titleStyle);
							createCell(row, index++, String.valueOf(p.isGeneralNull()?null:p.getGeneral()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getMajor()), titleStyle);
							createCell(row, index++, String.valueOf(p.getMinor()), titleStyle);
							createCell(row, index++, String.valueOf(p.getOther()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEventtypeDescription()), titleStyle);
							createCell(row, index++, String.valueOf(p.getUseflag()), titleStyle);
							
							createCell(row, index++, String.valueOf(p.getOidgroupOpid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getOidgroupOidgroupname()), titleStyle);
							createCell(row, index++, String.valueOf(p.getOtype()), titleStyle);
							createCell(row, index++, String.valueOf(p.getOidgroupDescription()), titleStyle);	
							
							createCell(row, index++, String.valueOf(p.getEveid()), titleStyle);
							createCell(row, index++, String.valueOf(p.getMrid()), titleStyle);
							createCell(row, index++, String.valueOf(p.getDtid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getOidgroupname()), titleStyle);
							createCell(row, index++, String.valueOf(p.getModid()), titleStyle);
							createCell(row, index++, String.valueOf(p.getOid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getUnit()), titleStyle);
							createCell(row, index++, String.valueOf(p.getDescription()), titleStyle);
							
							createCell(row, index++, String.valueOf(p.getDevtypeMrid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getDevtypeDtid()), titleStyle);
							createCell(row, index++, String.valueOf(p.isCategoryNull()?null:p.getCategory()), titleStyle);
							createCell(row, index++, String.valueOf(p.getSubcategory()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getModel()), titleStyle);
							createCell(row, index++, String.valueOf(p.getObjectid()), titleStyle);
							createCell(row, index++, String.valueOf(p.getLogo()), titleStyle);
							createCell(row, index++, String.valueOf(p.getDevtypeDescription()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <VPerformParamList>, message: " + e.getMessage());
							e.printStackTrace();
						}
			    	}
	    		}
			}
			

			if(exportList.containsKey( new String("SYSLOG_EVENTS_PROCESS").toUpperCase())){
				sheet = wb.createSheet("SYSLOG_EVENTS_PROCESS");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<SyslogEventsProcess> SyslogEventsProcessList = new ArrayList();
				SyslogEventsProcessList = (List<SyslogEventsProcess>) exportList.get(new String("SYSLOG_EVENTS_PROCESS").toUpperCase());
	    		int index=0;
	    		//from T_Manufacturer_Info_Init
	    		createCell(row, index++, "MARK", titleStyle);	
	    		createCell(row, index++, "VARLIST", titleStyle);	
	    		createCell(row, index++, "BTIMELIST", titleStyle);
	    		createCell(row, index++, "ETIMELIST", titleStyle);	
	    		createCell(row, index++, "FILTERFLAG1", titleStyle);	
	    		createCell(row, index++, "FILTERFLAG2", titleStyle);
	    		createCell(row, index++, "SEVERITY1", titleStyle);	
	    		createCell(row, index++, "SEVERITY2", titleStyle);	
	    		createCell(row, index++, "PORT", titleStyle);
	    		createCell(row, index++, "NOTCAREFLAG", titleStyle);	
	    		createCell(row, index++, "TYPE", titleStyle);
	    		createCell(row, index++, "EVENTTYPE", titleStyle);	
	    		createCell(row, index++, "SUBEVENTTYPE", titleStyle);	
	    		createCell(row, index++, "ALERTGROUP", titleStyle);
	    		createCell(row, index++, "ALERTKEY", titleStyle);	
	    		createCell(row, index++, "SUMMARYCN", titleStyle);	
	    		createCell(row, index++, "PROCESSSUGGEST", titleStyle);	
	    		createCell(row, index++, "STATUS", titleStyle);	
	    		createCell(row, index++, "ATTENTIONFLAG", titleStyle);	
	    		createCell(row, index++, "EVENTS", titleStyle);	
	    		createCell(row, index++, "MANUFACTURE", titleStyle);	
	    		createCell(row, index++, "ORIGEVENT", titleStyle);	
	    		 
	    		if( SyslogEventsProcessList != null){
	    			for(int i=0; i<SyslogEventsProcessList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							SyslogEventsProcess p = SyslogEventsProcessList.get(i);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getMark()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getVarlist()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getBtimelist()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEtimelist()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isFilterflag1Null()?null:p.getFilterflag1()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isFilterflag2Null()?null:p.getFilterflag2()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isSeverity1Null()?null:p.getSeverity1()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isSeverity2Null()?null:p.getSeverity2()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPort()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isNotcareflagNull()?null:p.getNotcareflag()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isTypeNull()?null:p.getType()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isEventtypeNull()?null:p.getEventtype()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isSubeventtypeNull()?null:p.getSubeventtype()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getAlertgroup()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getAlertkey()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getSummarycn()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getProcesssuggest()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getStatus()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isAttentionflagNull()?null:p.getAttentionflag()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEvents()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getManufacture()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getOrigevent()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <SyslogEventsProcessList>, message: " + e.getMessage());
							e.printStackTrace();
						}	
			    	}
	    		}
			}
			if(exportList.containsKey( new String("SYSLOG_EVENTS_PROCESS_NS").toUpperCase())){
				sheet = wb.createSheet("SYSLOG_EVENTS_PROCESS_NS");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<SyslogEventsProcessNs> SyslogEventsProcessNsList = new ArrayList();
				SyslogEventsProcessNsList = (List<SyslogEventsProcessNs>) exportList.get(new String("SYSLOG_EVENTS_PROCESS_NS").toUpperCase());
	    		int index=0;
	    		//from T_Manufacturer_Info_Init
	    		createCell(row, index++, "MARK", titleStyle);	
	    		createCell(row, index++, "VARLIST", titleStyle);	
	    		createCell(row, index++, "BTIMELIST", titleStyle);
	    		createCell(row, index++, "ETIMELIST", titleStyle);	
	    		createCell(row, index++, "FILTERFLAG1", titleStyle);	
	    		createCell(row, index++, "FILTERFLAG2", titleStyle);
	    		createCell(row, index++, "SEVERITY1", titleStyle);	
	    		createCell(row, index++, "SEVERITY2", titleStyle);	
	    		createCell(row, index++, "PORT", titleStyle);
	    		createCell(row, index++, "NOTCAREFLAG", titleStyle);	
	    		createCell(row, index++, "TYPE", titleStyle);
	    		createCell(row, index++, "EVENTTYPE", titleStyle);	
	    		createCell(row, index++, "SUBEVENTTYPE", titleStyle);	
	    		createCell(row, index++, "ALERTGROUP", titleStyle);
	    		createCell(row, index++, "ALERTKEY", titleStyle);	
	    		createCell(row, index++, "SUMMARYCN", titleStyle);	
	    		createCell(row, index++, "PROCESSSUGGEST", titleStyle);	
	    		createCell(row, index++, "STATUS", titleStyle);	
	    		createCell(row, index++, "ATTENTIONFLAG", titleStyle);	
	    		createCell(row, index++, "EVENTS", titleStyle);	
	    		createCell(row, index++, "MANUFACTURE", titleStyle);	
	    		createCell(row, index++, "ORIGEVENT", titleStyle);	
	    		 
	    		if( SyslogEventsProcessNsList != null){
	    			for(int i=0; i<SyslogEventsProcessNsList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							SyslogEventsProcessNs p = SyslogEventsProcessNsList.get(i);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getMark()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getVarlist()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getBtimelist()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEtimelist()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isFilterflag1Null()?null:p.getFilterflag1()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isFilterflag2Null()?null:p.getFilterflag2()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isSeverity1Null()?null:p.getSeverity1()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isSeverity2Null()?null:p.getSeverity2()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPort()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isNotcareflagNull()?null:p.getNotcareflag()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isTypeNull()?null:p.getType()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isEventtypeNull()?null:p.getEventtype()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isSubeventtypeNull()?null:p.getSubeventtype()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getAlertgroup()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getAlertkey()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getSummarycn()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getProcesssuggest()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getStatus()), titleStyle);	
							createCell(row, index++, String.valueOf(p.isAttentionflagNull()?null:p.getAttentionflag()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEvents()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getManufacture()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getOrigevent()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <SyslogEventsProcessNsList>, message: " + e.getMessage());
							e.printStackTrace();
						}	
			    	}
	    		}
			}
			if(exportList.containsKey( new String("SNMP_EVENTS_PROCESS").toUpperCase())){
				sheet = wb.createSheet("SNMP_EVENTS_PROCESS");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<SnmpEventsProcess> SnmpEventsProcessList = new ArrayList();
				SnmpEventsProcessList = (List<SnmpEventsProcess>) exportList.get(new String("SNMP_EVENTS_PROCESS").toUpperCase());
	    		int index=0;
	    		//from T_Manufacturer_Info_Init
	    		createCell(row, index++, "MARK", titleStyle);	
	    		createCell(row, index++, "MANUFACTURE", titleStyle);	
	    		createCell(row, index++, "RESULTLIST", titleStyle);	
	    		createCell(row, index++, "WARNMESSAGE", titleStyle);	
	    		createCell(row, index++, "SUMMARY", titleStyle);	

	    		if( SnmpEventsProcessList != null){
	    			for(int i=0; i<SnmpEventsProcessList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							SnmpEventsProcess p = SnmpEventsProcessList.get(i);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getMark()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getManufacture()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getResultlist()), titleStyle);
							createCell(row, index++, String.valueOf(p.getWarnmessage()), titleStyle);
							createCell(row, index++, String.valueOf(p.getSummary()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <SnmpEventsProcessList>, message: " + e.getMessage());
							e.printStackTrace();
						}	
			    	}
	    		}
			}
			if(exportList.containsKey(new String("t_policy_period").toUpperCase())){

				sheet = wb.createSheet("T_POLICY_PERIOD");//create a sheet
	    		HSSFRow row = sheet.createRow(0);//create title row
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<TPolicyPeriod> peroidList = new ArrayList();
				peroidList = (List<TPolicyPeriod>) exportList.get(new String("t_policy_period").toUpperCase());
	    		int index=0;
	
	    		createCell(row, index++, "Ppid", titleStyle);	
	    		createCell(row, index++, "PpName", titleStyle);	
	    		createCell(row, index++, "Start_Time", titleStyle);	
	    		createCell(row, index++, "End_Time", titleStyle);	
	    		createCell(row, index++, "Description", titleStyle);	
	    		createCell(row, index++, "Workday", titleStyle);	
	    		createCell(row, index++, "Enabled", titleStyle);	
	    		createCell(row, index++, "DefaultFlag", titleStyle);	
	    		createCell(row, index++, "S1", titleStyle);	
	    		createCell(row, index++, "S2", titleStyle);	
	    		createCell(row, index++, "S3", titleStyle);	
	    		createCell(row, index++, "S4", titleStyle);	
	    		createCell(row, index++, "S5", titleStyle);	
	    		createCell(row, index++, "S6", titleStyle);	
	    		createCell(row, index++, "S7", titleStyle);	
	    		createCell(row, index++, "E1", titleStyle);	
	    		createCell(row, index++, "E2", titleStyle);	
	    		createCell(row, index++, "E3", titleStyle);	
	    		createCell(row, index++, "E4", titleStyle);	
	    		createCell(row, index++, "E5", titleStyle);	
	    		createCell(row, index++, "E6", titleStyle);	
	    		createCell(row, index++, "E7", titleStyle);		
	    		
	    		if( peroidList != null){
					//set row style
	//	    		HSSFCellStyle style = wb.createCellStyle();
	    			for(int i=0; i<peroidList.size(); i++){
	    				try {
							index=0;
							row = sheet.createRow(i+1);
							TPolicyPeriod p = peroidList.get(i);
//	    				System.out.println("\t\ti=" + i + "\tpolicy details=" + p);
							//create title row, make sure export file only include columns which users can see in ticketList.jsp
							createCell(row, index++, String.valueOf(p.getPpid()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getPpname()), titleStyle);	
							System.out.println("start time=" + p.getStartTime());
							 java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							Timestamp timestamp = new Timestamp(f.parse(p.getStartTime().toString()).getTime());
							System.out.println("after parse. start time=" + timestamp);
							createCell(row, index++, String.valueOf(timestamp), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEndTime()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getDescription()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getWorkday()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getEnabled()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getDefaultflag()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS1()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS2()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS3()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS4()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS5()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS6()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getS7()), titleStyle);
							createCell(row, index++, String.valueOf(p.getE1()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getE2()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getE3()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getE4()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getE5()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getE6()), titleStyle);	
							createCell(row, index++, String.valueOf(p.getE7()), titleStyle);
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <peroidList>, message: " + e.getMessage());
							e.printStackTrace();
						}				    		
	    			}
	    		}
			

	    		
			
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    		Log4jInit.ncsLog.error("InterruptedException in Export, message: " + e.getMessage());
    	}
    	return wb;
    	
	}
	
	public static HSSFWorkbook exportDevInfo(String gname,Map<String,Object> exportList,Map<Long,Object> manufmap,Map<Long,Object> dtmap,Map<Long,Object> catemap,Map<Long,TDeviceInfo> devicefromport,Map<Long,TDeviceInfo> devicefrommib,Map<Long,List<TPortInfo>> portmap,Map<Long,List<PredefmibInfo>> mibmap,Map<Long,Object> mibgrpmap){
		//create a work book
		HSSFWorkbook wb = new HSSFWorkbook();
		
		
		try{
			HSSFSheet sheet = null;
			
			//device baseinfo
			if(exportList.containsKey("T_DEVICE_INFO")){

				sheet = wb.createSheet("T_DEVICE_INFO");//create a sheet
	    		HSSFRow rowCH = sheet.createRow(0);//create chinese title row
	    		HSSFRow rowEN = sheet.createRow(1);
	    		//set title style
	    		HSSFCellStyle titleStyle = wb.createCellStyle();
				List<TDeviceInfo> deviceInfo = new ArrayList();
				deviceInfo = (List<TDeviceInfo>) exportList.get("T_DEVICE_INFO");
	    		int index=0;
	    		int indexano=0;
	    		
	    		createCell(rowCH,index++,"设备归属地",titleStyle);
	    		createCell(rowCH,index++,"设备ID",titleStyle);
	    		createCell(rowCH,index++,"资源编码",titleStyle);
	    		createCell(rowCH,index++,"设备名称",titleStyle);
	    		createCell(rowCH,index++,"设备别名",titleStyle);
	    		createCell(rowCH,index++,"IP",titleStyle);
	    		createCell(rowCH,index++,"IPDECODE",titleStyle);
	    		createCell(rowCH,index++,"设备类型",titleStyle);
	    		createCell(rowCH,index++,"子类型",titleStyle);
	    		createCell(rowCH,index++,"型号",titleStyle);
	    		createCell(rowCH,index++,"OBJECTID",titleStyle);
	    		createCell(rowCH,index++,"负责人",titleStyle);
	    		createCell(rowCH,index++,"负责人电话",titleStyle);
	    		createCell(rowCH,index++,"制造厂商",titleStyle);
	    		createCell(rowCH,index++,"SNMP版本",titleStyle);
	    		createCell(rowCH,index++,"用途",titleStyle);
	    		createCell(rowCH,index++,"SRID",titleStyle);
	    		createCell(rowCH,index++,"设备序列号",titleStyle);
	    		createCell(rowCH,index++,"设备软件版本",titleStyle);
	    		createCell(rowCH,index++,"RAM 大小",titleStyle);
	    		createCell(rowCH,index++,"RAM 单位");
	    		createCell(rowCH,index++,"NVRAM 大小",titleStyle);
	    		createCell(rowCH,index++,"NVRAM 单位",titleStyle);
	    		createCell(rowCH,index++,"FLASH 大小",titleStyle);
	    		createCell(rowCH,index++,"FLASH 单位",titleStyle);
	    		createCell(rowCH,index++,"FLASHFILE 名称",titleStyle);
	    		createCell(rowCH,index++,"FLASHFILE 大小",titleStyle);
	    		createCell(rowCH,index++,"共同体名",titleStyle);
	    		createCell(rowCH,index++,"共同体名",titleStyle);
	    		createCell(rowCH,index++,"描述",titleStyle);
	    		
	    		createCell(rowEN, indexano++, "GNAME", titleStyle);
	    		createCell(rowEN, indexano++, "DEVID", titleStyle);	
	    		createCell(rowEN, indexano++, "RSNO", titleStyle);	
	    		createCell(rowEN, indexano++, "SYSNAME", titleStyle);
	    		createCell(rowEN, indexano++, "SYSNAMEALIAS", titleStyle);	
	    		createCell(rowEN, indexano++, "DEVIP", titleStyle);	
	    		createCell(rowEN, indexano++,"IPDECODE",titleStyle);
	    		createCell(rowEN, indexano++, "CATEGORY", titleStyle);	
	    		createCell(rowEN, indexano++, "SUBCATEGORY", titleStyle);	
	    		createCell(rowEN, indexano++, "MODEL", titleStyle);	
	    		createCell(rowEN, indexano++, "OBJECTID", titleStyle);	
	    		createCell(rowEN, indexano++, "ADMIN", titleStyle);	
	    		createCell(rowEN, indexano++, "PHONE", titleStyle);	
	    		createCell(rowEN, indexano++, "MANUFACTURE", titleStyle);	
	    		createCell(rowEN, indexano++, "SNMPVERSION", titleStyle);
	    		createCell(rowEN, indexano++, "DOMAINID", titleStyle);	
	    		
	    		createCell(rowEN, indexano++, "SRID", titleStyle);
	    		createCell(rowEN, indexano++, "SERIALID", titleStyle);
	    		createCell(rowEN, indexano++, "SWVERSION", titleStyle);
	    		createCell(rowEN, indexano++, "RAMSIZE", titleStyle);
	    		createCell(rowEN, indexano++, "RAMUNIT", titleStyle);
	    		createCell(rowEN, indexano++, "NVRAMSIZE", titleStyle);
	    		createCell(rowEN, indexano++, "NVRAMUNIT", titleStyle);
	    		createCell(rowEN, indexano++, "FLASHSIZE", titleStyle);
	    		createCell(rowEN, indexano++, "FLASHUNIT", titleStyle);
	    		createCell(rowEN, indexano++, "FLASHFILENAME", titleStyle);
	    		createCell(rowEN, indexano++, "FLASHFILESIZE", titleStyle);
	    		createCell(rowEN, indexano++, "RCOMMUNITY", titleStyle);
	    		createCell(rowEN, indexano++, "WCOMMUNITY", titleStyle);
	    		createCell(rowEN, indexano++, "DESCRIPTION", titleStyle);
	    		
	    		
	    		if( deviceInfo != null){
	    			for(int i=1; i<=deviceInfo.size(); i++){
	    				try {
							index=0;
							rowEN = sheet.createRow(i+1);
							TDeviceInfo device = deviceInfo.get(i-1);
							System.out.println("device is :"+device);
							createCell(rowEN, index++, String.valueOf(gname), titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getDevid()), titleStyle);	
							createCell(rowEN, index++, String.valueOf(device.getRsno()),titleStyle);	
							createCell(rowEN, index++, String.valueOf(device.getSysname()), titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getSysnamealias()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getDevip()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getIpdecode()),titleStyle);
							//category,subcategory,model
							try{
							if(dtmap != null){
								
								TDeviceTypeInit devicetype = (TDeviceTypeInit)dtmap.get(device.getDtid());
								System.out.println("devicetype is null ?"+(devicetype == null)+"and devicetype is :"+devicetype);
								System.out.println("devicetype's category id  is :"+devicetype.getCategory());
								if(devicetype != null){
							      if(catemap != null){
							    	
								     createCell(rowEN, index++, String.valueOf(catemap.get(devicetype.getCategory())),titleStyle);
							      }else{
							    	  createCell(rowEN, index++, "",titleStyle);
							      }
							      createCell(rowEN, index++, String.valueOf(((TDeviceTypeInit)dtmap.get(device.getDtid())).getSubcategory()),titleStyle);
							      createCell(rowEN, index++, String.valueOf(((TDeviceTypeInit)dtmap.get(device.getDtid())).getModel()),titleStyle);
							      createCell(rowEN, index++, String.valueOf(((TDeviceTypeInit)dtmap.get(device.getDtid())).getObjectid()),titleStyle);
							}else{
								createCell(rowEN, index++, "",titleStyle);
								createCell(rowEN, index++, "",titleStyle);
								createCell(rowEN, index++, "",titleStyle);
								createCell(rowEN, index++, "",titleStyle);
							}
							}else{
								createCell(rowEN, index++, "",titleStyle);
								createCell(rowEN, index++, "",titleStyle);
								createCell(rowEN, index++, "",titleStyle);
								createCell(rowEN, index++, "",titleStyle);
							}}catch(Exception e){
								createCell(rowEN, index++, "",titleStyle);
								createCell(rowEN, index++, "",titleStyle);
								createCell(rowEN, index++, "",titleStyle);
								createCell(rowEN, index++, "",titleStyle);
							}
							createCell(rowEN, index++, String.valueOf(device.getAdmin()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getPhone()),titleStyle);
							//manufacture
							
								System.out.println("manufamap is :"+manufmap+" and device mrid is "+device.getMrid());
								//System.out.println("devid is "+device.getDevid()+"manu name is :"+((TManufacturerInfoInit)(manufmap.get(device.getMrid()))).getMrname());
							if(manufmap != null){
								try{
								String mrname = ((TManufacturerInfoInit)(manufmap.get(device.getMrid()))).getMrname();
								createCell(rowEN, index++, String.valueOf(mrname), titleStyle);
							}catch(Exception e){
								System.out.println("catch invoked----");
								createCell(rowEN, index++, "",titleStyle);
							}}else{
								createCell(rowEN, index++, "",titleStyle);
							}
							
							
							createCell(rowEN, index++, String.valueOf(device.getSnmpversion()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getDomainid()),titleStyle);
							createCell(rowEN, index++,String.valueOf(device.getSrid()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getSerialid()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getSwversion()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getRamsize()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getRamunit()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getNvramsize()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getNvramunit()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getFlashsize()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getFlashunit()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getFlashfilename()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getFlashfilesize()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getRcommunity()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getWcommunity()),titleStyle);
							createCell(rowEN, index++, String.valueOf(device.getDescription()),titleStyle);
							
						} catch (Exception e) {
							Log4jInit.ncsLog.error("Exception in Export row in <TDeviceInfo>, message: " + e.getMessage());
							e.printStackTrace();
						}	
			    	}
	    		}
	    		
	    		if(exportList.containsKey("T_PORT_INFO")){

					sheet = wb.createSheet("T_PORT_INFO");//create a sheet
		    		HSSFRow rowPortCH = sheet.createRow(0);//create chinese title row
		    		HSSFRow rowPortEN = sheet.createRow(1);
		    		//set title style
					
					Map<Long,List<TPortInfo>> portInfo = new HashMap<Long,List<TPortInfo>>();
					portInfo = (Map<Long,List<TPortInfo>>)exportList.get("T_PORT_INFO");
					List<TPortInfo> ports = new ArrayList<TPortInfo>();
		    		int indexPort=0;
		    		int indexPortano=0;
		    		createCell(rowPortCH,indexPort++,"设备归属地",titleStyle);
		    		createCell(rowPortCH,indexPort++,"设备ID",titleStyle);
		    		createCell(rowPortCH,indexPort++,"设备IP(*)",titleStyle);
		    		createCell(rowPortCH,indexPort++,"设备名称",titleStyle);
		    		createCell(rowPortCH,indexPort++,"端口ID",titleStyle);
		    		createCell(rowPortCH,indexPort++,"端口名称",titleStyle);
		    		createCell(rowPortCH,indexPort++,"端口IP",titleStyle);
		    		createCell(rowPortCH,indexPort++,"端口IPDECODE",titleStyle);
		    		createCell(rowPortCH,indexPort++,"端口Mac 地址",titleStyle);
		    		createCell(rowPortCH,indexPort++,"IFOPERSTATUS",titleStyle);
		    		createCell(rowPortCH,indexPort++,"端口索引",titleStyle);
		    		createCell(rowPortCH,indexPort++,"描述",titleStyle);
		    		
		    		createCell(rowPortEN, indexPortano++, "GNAME", titleStyle);
		    		createCell(rowPortEN, indexPortano++, "DEVID", titleStyle);	
		    		createCell(rowPortEN, indexPortano++, "DEVIP", titleStyle);	
		    		createCell(rowPortEN, indexPortano++, "SYSNAME", titleStyle);
		    		createCell(rowPortEN, indexPortano++, "PTID", titleStyle);
		    		createCell(rowPortEN, indexPortano++, "IFDESCR", titleStyle);
		    		createCell(rowPortEN, indexPortano++, "IFIP", titleStyle);	
		    		createCell(rowPortEN, indexPortano++, "IPDECODE_IFIP", titleStyle);	
		    		createCell(rowPortEN, indexPortano++, "IFMAC", titleStyle);	
		    		createCell(rowPortEN, indexPortano++, "IFOPERSTATUS", titleStyle);	
		    		createCell(rowPortEN, indexPortano++, "IFINDEX", titleStyle);
		    		createCell(rowPortEN, indexPortano++, "DESCRIPTION", titleStyle);
		    		
		    		if( portInfo != null){
						int rowCount = 2;
		    				try {
								rowPortEN = sheet.createRow(rowCount);
								Set<Long> devids = portInfo.keySet();
								Iterator<Long> iterator = devids.iterator();
								while(iterator.hasNext()){
									long devid = iterator.next();
									ports = portInfo.get(devid);
									
									if(ports != null && ports.size()>0){
										
										for(TPortInfo port : ports){
										int indexin = 0;
										rowPortEN = sheet.createRow(rowCount);
										createCell(rowPortEN, indexin++, String.valueOf(gname),titleStyle);
										createCell(rowPortEN, indexin++, String.valueOf(devid),titleStyle);
										createCell(rowPortEN, indexin++, String.valueOf(((TDeviceInfo)devicefromport.get(devid)).getDevip()), titleStyle);
										createCell(rowPortEN, indexin++, String.valueOf(((TDeviceInfo)devicefromport.get(devid)).getSysname()), titleStyle);	
										createCell(rowPortEN, indexin++, String.valueOf(port.getPtid()),titleStyle);
										createCell(rowPortEN, indexin++, String.valueOf(port.getIfdescr()),titleStyle);
										createCell(rowPortEN, indexin++, String.valueOf(port.getIfip()), titleStyle);
										createCell(rowPortEN, indexin++, String.valueOf(port.getIpdecodeIfip()), titleStyle);
										createCell(rowPortEN, indexin++, String.valueOf(port.getIfmac()),titleStyle);
										createCell(rowPortEN, indexin++, String.valueOf(port.getIfoperstatus()),titleStyle);
										createCell(rowPortEN, indexin++, String.valueOf(port.getIfindex()),titleStyle);
										createCell(rowPortEN, indexin++, String.valueOf(port.getDescription()),titleStyle);
										rowCount ++;
										}
									}
									
								}
							} catch (Exception e) {
								Log4jInit.ncsLog.error("Exception in Export row in <TPortInfo>, message: " + e.getMessage());
								e.printStackTrace();
							}
							
		    		}
				
				
					
				
	    		}
	    		
	    		if(exportList.containsKey("PREDEFMIB_INFO")){


					sheet = wb.createSheet("PREDEFMIB_INFO");//create a sheet
		    		HSSFRow rowMibCH = sheet.createRow(0);//create chinese title row
		    		HSSFRow rowMibEN = sheet.createRow(1);
		    		//set title style
					
					Map<Long,List> mibInfo = new HashMap<Long,List>();
					mibInfo = (Map<Long,List>)exportList.get("PREDEFMIB_INFO");
					List<PredefmibInfo> mibs = new ArrayList<PredefmibInfo>();
		    		int indexMib=0;
		    		int indexMibano=0;
		    		
		    		createCell(rowMibCH,indexMib++,"设备归属地",titleStyle);
		    		createCell(rowMibCH,indexMib++,"设备ID",titleStyle);
		    		createCell(rowMibCH,indexMib++,"设备IP(*)",titleStyle);
		    		createCell(rowMibCH,indexMib++,"设备名称",titleStyle);
		    		createCell(rowMibCH,indexMib++,"私有MIB INDEX ID",titleStyle);
		    		createCell(rowMibCH,indexMib++,"私有Index 等效名",titleStyle);
		    		createCell(rowMibCH,indexMib++,"私有Descr 等效名",titleStyle);
		    		createCell(rowMibCH,indexMib++,"私有MIB GROUP ID",titleStyle);
		    		createCell(rowMibCH,indexMib++,"私有MIB GROUP 名称",titleStyle);
		    		createCell(rowMibCH,indexMib++,"私有Index OiD",titleStyle);
		    		createCell(rowMibCH,indexMib++,"私有Index 等效名",titleStyle);
		    		createCell(rowMibCH,indexMib++,"私有Descr OiD",titleStyle);
		    		createCell(rowMibCH,indexMib++,"私有Descr 等效名",titleStyle);
		    	
		    		
		    		createCell(rowMibEN, indexMibano++, "GNAME", titleStyle);
		    		createCell(rowMibEN, indexMibano++, "DEVID", titleStyle);	
		    		createCell(rowMibEN, indexMibano++, "DEVIP", titleStyle);	
		    		createCell(rowMibEN, indexMibano++, "SYSNAME", titleStyle);
		    		createCell(rowMibEN, indexMibano++, "PDMID", titleStyle);
		    		createCell(rowMibEN, indexMibano++, "OIDINDEX", titleStyle);
		    		createCell(rowMibEN, indexMibano++, "OIDNAME", titleStyle);	
		    		createCell(rowMibEN, indexMibano++, "MID", titleStyle);
		    		createCell(rowMibEN, indexMibano++, "NAME", titleStyle);
		    		createCell(rowMibEN, indexMibano++, "INDEXOID", titleStyle);
		    		createCell(rowMibEN, indexMibano++, "INDEXVAR", titleStyle);
		    		createCell(rowMibEN, indexMibano++, "DESCROID", titleStyle);
		    		createCell(rowMibEN, indexMibano++, "DESCRVAR", titleStyle);
		    	
		    		
		    		if( mibInfo != null){
		    			int rowCount = 2;
		    				try {
								rowMibEN = sheet.createRow(rowCount);
								Set<Long> devids = mibInfo.keySet();
								Iterator<Long> iterator = devids.iterator();
								while(iterator.hasNext()){
									long devid = iterator.next();
									mibs = mibInfo.get(devid);
									if(mibs != null && mibs.size()>0){
										for(PredefmibInfo mib : mibs){
											Long mid = mib.getMid();
											index=0;
											rowMibEN = sheet.createRow(rowCount);
											if((DefMibGrp)(mibgrpmap.get(mid))!= null){
											createCell(rowMibEN, index++, String.valueOf(gname),titleStyle);
											createCell(rowMibEN, index++, String.valueOf(devid),titleStyle);
											createCell(rowMibEN, index++, String.valueOf(((TDeviceInfo)devicefrommib.get(devid)).getDevip()), titleStyle);
											createCell(rowMibEN, index++, String.valueOf(((TDeviceInfo)devicefrommib.get(devid)).getSysname()), titleStyle);
											createCell(rowMibEN, index++, String.valueOf(mib.getPdmid()),titleStyle);
											createCell(rowMibEN, index++, String.valueOf(mib.getOidindex()),titleStyle);
											createCell(rowMibEN, index++, String.valueOf(mib.getOidname()),titleStyle);
											createCell(rowMibEN, index++, String.valueOf(mid),titleStyle);
											createCell(rowMibEN, index++, String.valueOf(((DefMibGrp)(mibgrpmap.get(mid))).getName()),titleStyle);
											createCell(rowMibEN, index++, String.valueOf(((DefMibGrp)(mibgrpmap.get(mid))).getIndexoid()),titleStyle);
											createCell(rowMibEN, index++, String.valueOf(((DefMibGrp)(mibgrpmap.get(mid))).getIndexvar()),titleStyle);
											createCell(rowMibEN, index++, String.valueOf(((DefMibGrp)(mibgrpmap.get(mid))).getDescroid()),titleStyle);
											createCell(rowMibEN, index++, String.valueOf(((DefMibGrp)(mibgrpmap.get(mid))).getDescrvar()),titleStyle);
											rowCount ++;
											}
										}
									}
								}
								
							} catch (Exception e) {
								Log4jInit.ncsLog.error("Exception in Export row in <PredefmibINFO>, message: " + e.getMessage());
								e.printStackTrace();
							}	
		    		}
				
				
					
				
	    		
	    			
	    		}
			
			}
			
			
			
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return wb;
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
}
