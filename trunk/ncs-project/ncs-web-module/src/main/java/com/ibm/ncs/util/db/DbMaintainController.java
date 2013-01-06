package com.ibm.ncs.util.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.ibm.ncs.web.policytakeeffect.TakeEffectProcessImpl;

public class DbMaintainController implements Controller {
	
	Logger logger = Logger.getLogger(DbMaintainController.class);
	
String pageView;


DbBackup DbBackup;
DbRestore DbRestore;
Map model = new HashMap();
String message;	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//Map model = new HashMap();
		model.clear();
		message="";
	
		String action = request.getParameter("formAction");
		if((action!=null)&& (action.equals("backup"))){
		//
		//	backupDB(response);
		       File file = null;;
		        try
		        {
		        	file = DbBackup.backupDb();
		            if(file != null)
		            {
		                String fileName = "NccData.sql";
		                response.setContentType("application/text;charset=UTF-8");
		                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		                ServletOutputStream out = response.getOutputStream();
		                FileInputStream input = new FileInputStream(file);
		                int fileLength = (int)file.length();
		                int bufferLength = 0x100000;
		                int len = 0;
		                byte buffer[] = new byte[bufferLength];
		                boolean bFlush = false;
		                while((len = input.read(buffer, 0, bufferLength)) != -1) 
		                {
		                    bFlush = true;
		                    out.write(buffer, 0, len);
		                }
		                if(bFlush)
		                    out.flush();
		                out.close();
		                input.close();
		                file.delete();
		            }
		        }
		        catch(IOException ee)
		        {
		            ee.printStackTrace();
		        }

		        return null;
		}else if((action!=null) && (action.equals("restore"))){
			
			File file = getUploadedFile(request,response);
			
			restoreDB(file,response);
		}
		
		
		
		model.put("message", message);
		
		return new ModelAndView(getPageView(), "model", model);
	}







	private File getUploadedFile(HttpServletRequest request, HttpServletResponse response) {
	
		File saveTo =null;
		try{

			final long MAX_SIZE = 1024 * 1024 * 1024;
		
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
				return null;
			}

			if (fileList == null || fileList.size() == 0 || fileList.get(0).getClass().getName() == null) {
				message = "policy.import.noFile";	
				model.put("message", message);
				return null;
			}
			Iterator fileItr = fileList.iterator();

			while (fileItr.hasNext()) {
//				System.out.println("\t11111111\tin while");
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
					return null;
				}

//				String t_name = path.substring(path.lastIndexOf("\\") + 1);
//				String[] ver = t_name.split("\\.");
////				System.out.println(ver[1]);
//
//
//				String filename=ver[0]+".xls";		
//				System.out.println("1111111^^^^^upload file name=" + filename);
				ResourceBundle prop =null;
				String filename =  "/tmp/nccdata.sql";
				try{
					prop = ResourceBundle.getBundle("ncc-configuration");
					String dir= prop.getString("db.restore.upload.dir");
					String tmpfile = prop.getString("db.restore.tmp.file");
					filename = dir+"/"+tmpfile;
				}catch(Exception e){
					
				}
				filename =  (filename==null ||filename.trim().equals(""))?"/tmp/nccdata.sql":filename;
				saveTo = new File( filename);
				//InputStream fileStream = fileItem.getInputStream();
				try {
					fileItem.write(saveTo);
				  	//String filePath = request.getRealPath("/")+ "/uploadDir/"+filename;

					fileItem.delete();//remove the uploaded file.
				
				} catch (Exception e) {
					message = "Error on uploading file." ;
					logger.error("error on uploading file, "+e.getMessage());
					e.printStackTrace();
		    		//Log4jInit.ncsLog.error("InterruptedException in Export, message: " + e.getMessage());
				}
			} 
		}catch(Exception e){
			e.printStackTrace();
			}
		return saveTo;
	}







	private int restoreDB(File file, HttpServletResponse response) {
		int ret = -1; //return status;
		try{
			if (file != null){
				
				
				DbRestore.setDone(false);
				try {
					DbRestore.stopProcess();
				} catch (Exception e) {
				}
				DbRestore.setDbfile(file);	
				DbRestore.init();
				DbRestore.startProcess();
				//TakeEffectProcess.operations();  //or, synchornized same thread
				
				ret= 0;			
				//ret = DbRestore.restoreDb(file);
			}
			
		}catch(Exception e){
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		message ="Restore DB successed. total "+ret;
		model.put("message", message);
		return ret;
	}







	private int backupDB(HttpServletResponse response) {
		int ret = -1; //return status.
		
        File file = null;;
        try
        {
        	file = DbBackup.backupDb();
            if(file != null)
            {
                String fileName = "NccData.sql";
                response.setContentType("application/text;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                ServletOutputStream out = response.getOutputStream();
                FileInputStream input = new FileInputStream(file);
                int fileLength = (int)file.length();
                int bufferLength = 0x100000;
                int len = 0;
                byte buffer[] = new byte[bufferLength];
                boolean bFlush = false;
                while((len = input.read(buffer, 0, bufferLength)) != -1) 
                {
                    bFlush = true;
                    out.write(buffer, 0, len);
                }
                if(bFlush)
                    out.flush();
                out.close();
                input.close();
                file.delete();
            }
        }
        catch(IOException ee)
        {
            ee.printStackTrace();
        }
        return ret=1;
	}						
	






	public String getPageView() {
		return pageView;
	}









	public void setPageView(String pageView) {
		this.pageView = pageView;
	}







	public DbBackup getDbBackup() {
		return DbBackup;
	}







	public void setDbBackup(DbBackup dbBackup) {
		DbBackup = dbBackup;
	}







	public DbRestore getDbRestore() {
		return DbRestore;
	}







	public void setDbRestore(DbRestore dbRestore) {
		DbRestore = dbRestore;
	}














}
