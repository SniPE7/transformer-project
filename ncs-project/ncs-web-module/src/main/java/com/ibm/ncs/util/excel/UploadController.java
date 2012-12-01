package com.ibm.ncs.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.export.IcmpPolicyExporter;
import com.ibm.ncs.export.IcmpPolicyExporterImpl;
import com.ibm.ncs.export.SnmpPolicyExporter;
import com.ibm.ncs.export.SnmpPolicyExporterImpl;
import com.ibm.ncs.export.SrcTypeExporter;
import com.ibm.ncs.export.SrcTypeExporterImpl;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.Log4jInit;

public class UploadController implements Controller {
	
	String pageView;
	String Message = "";


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try{
			String mode = request.getParameter("mode");
			System.out.println("mode in controller=" + mode);
			model.put("mode", mode);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			Message = "savePolicyDetailCtl.error";		
			model.put("message", Message);
			e.printStackTrace();
		}
		
		return new ModelAndView(getPageView(), "model", model);
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
