package com.ibm.ncs.util.excel;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * Servlet implementation class ExcelTODB
 */
public class ExcelTODB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int marke=0;
	DataSource datasource;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExcelTODB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获得服务器端上传文件存放目录路径
		final long MAX_SIZE = 900 * 1024 * 1024;// 设置上传文件�?大�??
		// 允许上传的文件格式的列表
		final String[] allowedExt = new String[] { "xlsx", "xls"};
		response.setContentType("text/html");
		// 设置字符编码为UTF-8, 统一编码，处理出现乱码问�?
		response.setCharacterEncoding("UTF-8");

		// 实例化一个硬盘文件工�?,用来配置上传组件ServletFileUpload
		DiskFileItemFactory dfif = new DiskFileItemFactory();

		dfif.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里�?4K.多于的部分将临时存在硬盘

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
		}
		// 没有文件上传
		if (fileList == null || fileList.size() == 0) {
		out.println("请�?�择上传文件<p />");
		out.println("<script language='javascript'>alert('导入的文件为空，请重新�?�择�?');history.go(-1);</script>");
		return;
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
		System.out.println("上传的文件为�?----"+fileItem.getName());
		
		// 得到文件的完整路�?
		path = fileItem.getName();
		// 得到文件的大�?
		size = fileItem.getSize();
		if ("".equals(path) || size == 0) {
		out.println("请�?�择上传文件<p />");
		out.println("<script language='javascript'>alert('导入的文件为空，请重新�?�择�?');history.go(-1);</script>");
		return;
		}

		// 得到去除路径的文件名
		String t_name = path.substring(path.lastIndexOf("\\") + 1);
		String[] ver = t_name.split("\\.");
		//System.out.println(ver[1]);
		// 得到文件的扩展名(无扩展名时将得到全名)
		String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);
		// 拒绝接受规定文件格式之外的文件类�?
		int allowFlag = 0;
		int allowedExtCount = allowedExt.length;
		for (; allowFlag < allowedExtCount; allowFlag++) {
		if (allowedExt[allowFlag].equals(t_ext))
		break;
		}
		if (allowFlag == allowedExtCount) {
		
	
		out.println("<script language='javascript'>alert('请导�?*.xlsx,*.xls类型的文件！');history.go(-1);</script>");
		return;
		}

     	//String filename = prefix + "." + t_ext;
		//根据原文件名保存文件
		String filename;
		if(ver[1].equals("xls")){
			//filename="SNMP_THRESHOLDS.xls";
			filename=ver[0]+".xls";
		}else{
			//filename="SNMP_THRESHOLDS.xlsx";
			filename=ver[0]+".xlsx";
		}
		 
		try {
		// 保存文件到目录下
	      fileItem.write(new File(request.getRealPath("/") + filename));
		  String filePath = request.getRealPath("/")+ filename;
		  String tableName = ver[0];
		  System.out.println("the table is =-==="+tableName);
		  marke = loadToDB(filePath,tableName);
		 /* if(ver[1].equals("xls")){
			    marke= loadToDB(filePath);
			}else{
				marke= loadToDB(filePath);
			}*/
				
		 System.out.println(filePath);
		 System.out.println("导入"+marke);  
		if(marke<1)
        	out.println("<script language='javascript'>alert('导入失败，请�?查导入的文件�?');history.go(-1);</script>");
		else
		   out.println("<script language='javascript'>alert('导入成功�?');history.go(-1);</script>");
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
	}
	
	public  int  loadToDB(String filePath,String tableName) {
		
		   int mark=-1;
        try {
            // 通过输入流创建Workbook(术语：工作薄)对象
        	
            InputStream inStr = new FileInputStream(filePath);
            
            Workbook workBook = Workbook.getWorkbook(inStr);
            // 这里有两种方法获取Sheet(术语：工作表)对象,�?种为通过工作表的名字，另�?种为通过工作表的下标，从0�?�?
  //            Sheet sheet = workBook.getSheet("name");  // 通过名字
            Sheet sheet = workBook.getSheet(1); // 通过下标
            // 获取工作表内容的行数和列�?
            int rows = sheet.getRows();
            int columns = sheet.getColumns();
            
            System.out.println("the columns of excel is ---"+columns);
       
       //  	 Connection con=JdbcUtils.getConnection();
            Connection con = datasource.getConnection();
         
            if(tableName.equalsIgnoreCase("SNMP_THRESHOLDS")){
         /*  if(!check.equals("PerformanceBtimeEtimeThresholdCompareTypeSeverity1Severity2FilterFlag1FilterFlag2")){
            	
            	return -1;
            }	 */
                       
            String sqls="insert into SNMP_THRESHOLDS(\"Performance\",\"Btime\",\"Etime\",\"Threshold\",\"CompareType\",\"Severity1\",\"Severity2\",\"FilterFlag1\",\"FilterFlag2\")"+"values(?,?,?,?,?,?,?,?,?)";
            
         //   if("PerformanceBtimeEtimeThresholdCompareTypeSeverity1Severity2FilterFlag1FilterFlag2".equals(check)){
            
                PreparedStatement pstmt=con.prepareStatement(sqls);
            	for (int m = 1; m < rows; m++) {
                        
            		    pstmt.setString(1,sheet.getCell(0, m).getContents());
        	            pstmt.setString(2,sheet.getCell(1, m).getContents());
        	            pstmt.setString(3,sheet.getCell(2, m).getContents());
        	            pstmt.setString(4,sheet.getCell(3, m).getContents());
        	            pstmt.setString(5,sheet.getCell(4, m).getContents());
        	            
        	            pstmt.setLong(6,Long.parseLong(sheet.getCell(5, m).getContents()));
        	            pstmt.setLong(7,Long.parseLong(sheet.getCell(6, m).getContents()));
        	            pstmt.setLong(8,Long.parseLong(sheet.getCell(7, m).getContents()));
        	            pstmt.setLong(9,Long.parseLong(sheet.getCell(8, m).getContents()));
         	           
        	       
            	      pstmt.addBatch();	 
                }
            	      pstmt.executeBatch();
	                  pstmt.close();
	                	 con.close();
	                	 mark=1;
	               	                  
      /*  }  else {
        	mark=-1;
                
            con.close();   
         //关闭对象，释放内�?
            workBook.close();
        }*/}else if(tableName.equalsIgnoreCase("SNMP_EVENTS_PROCESS")){
        	
        	 String sqls="insert into SNMP_EVENTS_PROCESS(\"MARK\",\"MANUFACTURE\",\"RESULTLIST\",\"WARNMESSAGE\",\"Summary\")"+"values(?,?,?,?,?)";
        	  PreparedStatement pstmt=con.prepareStatement(sqls);
          	for (int m = 1; m < rows; m++) {
                      
          		    pstmt.setString(1,sheet.getCell(0, m).getContents());
      	            pstmt.setString(2,sheet.getCell(1, m).getContents());
      	            pstmt.setString(3,sheet.getCell(2, m).getContents());
      	            pstmt.setString(4,sheet.getCell(3, m).getContents());
      	            pstmt.setString(5,sheet.getCell(4, m).getContents());
      	       
          	      pstmt.addBatch();	 
              }
          	      pstmt.executeBatch();
	                  pstmt.close();
	                	 con.close();
	                	 mark=1;
        }else if(tableName.equalsIgnoreCase("ICMP_THRESHOLDS")){
        	 String sqls="insert into ICMP_THRESHOLDS(\"IPADDRESS\",\"Btime\",\"Etime\",\"Threshold\",\"Severity1\"，,\"Severity2\"，,\"FilterFlag1\"，,\"FilterFlag2\")"+"values(?,?,?,?,?,?,?,?)";
        	 PreparedStatement pstmt=con.prepareStatement(sqls);
           	for (int m = 1; m < rows; m++) {
                       
           		    pstmt.setString(1,sheet.getCell(0, m).getContents());
       	            pstmt.setString(2,sheet.getCell(1, m).getContents());
       	            pstmt.setString(3,sheet.getCell(2, m).getContents());
       	            pstmt.setString(4,sheet.getCell(3, m).getContents());
       	            
       	           pstmt.setLong(5,Long.parseLong(sheet.getCell(4, m).getContents()));
 	               pstmt.setLong(6,Long.parseLong(sheet.getCell(5, m).getContents()));
 	               pstmt.setLong(7,Long.parseLong(sheet.getCell(6, m).getContents()));
 	               pstmt.setLong(8,Long.parseLong(sheet.getCell(7, m).getContents()));
       	       
           	      pstmt.addBatch();	 
               }
           	      pstmt.executeBatch();
 	                  pstmt.close();
 	                	 con.close();
 	                	 mark=1;
        }else if(tableName.equalsIgnoreCase("ICMP_THRESHOLDS_v2")){
        	String sqls="insert into ICMP_THRESHOLDS_v2(\"Threshold\",\"CompareType\",\"Severity1\",\"Severity2\",\"FilterFlag1\"，,\"FilterFlag2\"，,\"VARLIST\"，,\"SummaryCN\")"+"values(?,?,?,?,?,?,?,?)";
        	 PreparedStatement pstmt=con.prepareStatement(sqls);
            	for (int m = 1; m < rows; m++) {
                        
            		    pstmt.setString(1,sheet.getCell(0, m).getContents());
        	            pstmt.setString(2,sheet.getCell(1, m).getContents());
        	            pstmt.setString(3,sheet.getCell(2, m).getContents());
        	            pstmt.setString(4,sheet.getCell(3, m).getContents());
        	            
        	           pstmt.setLong(5,Long.parseLong(sheet.getCell(4, m).getContents()));
  	                   pstmt.setLong(6,Long.parseLong(sheet.getCell(5, m).getContents()));
  	                   pstmt.setString(7,sheet.getCell(6, m).getContents());
     	               pstmt.setString(8,sheet.getCell(7, m).getContents());
        	       
            	      pstmt.addBatch();	 
                }
            	      pstmt.executeBatch();
  	                  pstmt.close();
  	                	 con.close();
  	                	 mark=1;
        }else if(tableName.equalsIgnoreCase("Events_Attention")){
        	String sqls="insert into Events_Attention(\"EventsAttention\",\"Severity\",\"ProcessSuggest\")"+"values(?,?,?)";
        	 PreparedStatement pstmt=con.prepareStatement(sqls);
         	for (int m = 1; m < rows; m++) {
                     
        	 pstmt.setString(1,sheet.getCell(0, m).getContents());
        	 pstmt.setLong(2,Long.parseLong(sheet.getCell(1, m).getContents()));
        	 pstmt.setString(3,sheet.getCell(2, m).getContents());
        	 pstmt.addBatch();	 
            }
        	      pstmt.executeBatch();
	                  pstmt.close();
	                	 con.close();
	                	 mark=1;
        }else if(tableName.equalsIgnoreCase("Lines_Events_Notcare")){
        	String sqls = "insert into Lines_Events_Notcare(\"LinesNotCare\")"+"values(?)";
        
        	PreparedStatement pstmt=con.prepareStatement(sqls);
         	for (int m = 1; m < rows; m++) {
                     
        	 pstmt.setString(1,sheet.getCell(0, m).getContents());
        	
            }
        	      pstmt.executeBatch();
	                  pstmt.close();
	                	 con.close();
	                	 mark=1;
        	
        }else if(tableName.equalsIgnoreCase("SYSLOG_EVENTS_PROCESS")){
        	 String sqls="insert into SYSLOG_EVENTS_PROCESS(\"MARK\",\"VARLIST\",\"BTIMELIST\",\"ETIMELIST\",\"FILTERFLAG1\"，,\"FILTERFLAG2\"，,\"SEVERITY1\"，,\"SEVERITY2\",\"PORT\",\"NOTCAREFLAG\",\"TYPE\",\"EVENTTYPE\",\"SUBEVENTTYPE\",\"ALERTGROUP\",\"ALERTKEY\",\"SUMMARYCN\",\"PROCESSSUGGEST\",\"STATUS\",\"ATTENTIONFLAG\",\"EVENTS\",\"MANUFACTURE\",\"ORIGEVENT\")"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        	 PreparedStatement pstmt=con.prepareStatement(sqls);
         	for (int m = 1; m < rows; m++) {
                     
         		    pstmt.setString(1,sheet.getCell(0, m).getContents());
     	            pstmt.setString(2,sheet.getCell(1, m).getContents());
     	            pstmt.setString(3,sheet.getCell(2, m).getContents());
     	            pstmt.setString(4,sheet.getCell(3, m).getContents());
     	            
     	            pstmt.setLong(5,Long.parseLong(sheet.getCell(4, m).getContents()));
	                pstmt.setLong(6,Long.parseLong(sheet.getCell(5, m).getContents()));
	                pstmt.setLong(7,Long.parseLong(sheet.getCell(6, m).getContents()));
	                pstmt.setLong(8,Long.parseLong(sheet.getCell(7, m).getContents()));
	                
	                pstmt.setString(9,sheet.getCell(8, m).getContents());
	                
	                pstmt.setLong(10,Long.parseLong(sheet.getCell(9, m).getContents()));
	                pstmt.setLong(11,Long.parseLong(sheet.getCell(10, m).getContents()));
	                pstmt.setLong(12,Long.parseLong(sheet.getCell(11, m).getContents()));
	                pstmt.setLong(13,Long.parseLong(sheet.getCell(12, m).getContents()));
	                
	                pstmt.setString(14,sheet.getCell(13, m).getContents());
     	            pstmt.setString(15,sheet.getCell(14, m).getContents());
     	            pstmt.setString(16,sheet.getCell(15, m).getContents());
     	            pstmt.setString(17,sheet.getCell(16, m).getContents());
     	            pstmt.setString(18,sheet.getCell(17, m).getContents());
     	            
     	           pstmt.setLong(19,Long.parseLong(sheet.getCell(18, m).getContents()));
     	           
     	            pstmt.setLong(20,Long.parseLong(sheet.getCell(19, m).getContents()));
	                pstmt.setLong(21,Long.parseLong(sheet.getCell(20, m).getContents()));
	                pstmt.setLong(22,Long.parseLong(sheet.getCell(21, m).getContents()));
	                
	                
	                  
         	      pstmt.addBatch();	 
             }
         	      pstmt.executeBatch();
	                  pstmt.close();
	                	 con.close();
	                	 mark=1;
        }else if(tableName.equalsIgnoreCase("SYSLOG_EVENTS_PROCESS_NS")){
        	String sqls="insert into SYSLOG_EVENTS_PROCESS(\"MARK\",\"VARLIST\",\"BTIMELIST\",\"ETIMELIST\",\"FILTERFLAG1\"，,\"FILTERFLAG2\"，,\"SEVERITY1\"，,\"SEVERITY2\",\"PORT\",\"NOTCAREFLAG\",\"TYPE\",\"EVENTTYPE\",\"SUBEVENTTYPE\",\"ALERTGROUP\",\"ALERTKEY\",\"SUMMARYCN\",\"PROCESSSUGGEST\",\"STATUS\",\"ATTENTIONFLAG\",\"EVENTS\",\"MANUFACTURE\",\"ORIGEVENT\")"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        }else if(tableName.equalsIgnoreCase("T_Policy_Details")){
        	String sqls="\"Mpid\", \"Modid\", \"Eveid\", \"Poll\", \"Value1\", \"Severity1\", \"FilterA\", \"Value2\", \"Severity2\", \"FilterB\", \"SeverityA\", \"SeverityB\", \"Oidgroup\", \"Ogflag\", \"Value1Low\", \"Value2Low\", \"V1lSeverity1\", \"V1lSeverityA\", \"V2lSeverity2\", \"V2lSeverityB\")"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
       	 	
        	
        	PreparedStatement pstmt=con.prepareStatement(sqls);
        	for (int m = 1; m < rows; m++) {
                    
        		    pstmt.setString(1,sheet.getCell(0, m).getContents());
    	            pstmt.setString(2,sheet.getCell(1, m).getContents());
    	            pstmt.setString(3,sheet.getCell(2, m).getContents());
    	            pstmt.setString(4,sheet.getCell(3, m).getContents());
    	            
    	            pstmt.setLong(5,Long.parseLong(sheet.getCell(4, m).getContents()));
	                pstmt.setLong(6,Long.parseLong(sheet.getCell(5, m).getContents()));
	                pstmt.setLong(7,Long.parseLong(sheet.getCell(6, m).getContents()));
	                pstmt.setLong(8,Long.parseLong(sheet.getCell(7, m).getContents()));
	                
	                pstmt.setString(9,sheet.getCell(8, m).getContents());
	                
	                pstmt.setLong(10,Long.parseLong(sheet.getCell(9, m).getContents()));
	                pstmt.setLong(11,Long.parseLong(sheet.getCell(10, m).getContents()));
	                pstmt.setLong(12,Long.parseLong(sheet.getCell(11, m).getContents()));
	                pstmt.setLong(13,Long.parseLong(sheet.getCell(12, m).getContents()));
	                
	                pstmt.setString(14,sheet.getCell(13, m).getContents());
    	            pstmt.setString(15,sheet.getCell(14, m).getContents());
    	            pstmt.setString(16,sheet.getCell(15, m).getContents());
    	            pstmt.setString(17,sheet.getCell(16, m).getContents());
    	            pstmt.setString(18,sheet.getCell(17, m).getContents());
    	            
    	           pstmt.setLong(19,Long.parseLong(sheet.getCell(18, m).getContents()));
    	           
    	            pstmt.setLong(20,Long.parseLong(sheet.getCell(19, m).getContents()));
	                pstmt.setLong(21,Long.parseLong(sheet.getCell(20, m).getContents()));
	                pstmt.setLong(22,Long.parseLong(sheet.getCell(21, m).getContents()));
	                
	                
	                  
        	      pstmt.execute();;	 
            }
//        	      pstmt.executeBatch();
	        pstmt.close();
	        con.close();
	        mark=1;
	      }
        }catch (Exception e) {
            	System.out.println("异常内容如下�?");
            	mark=-1;
            	e.printStackTrace();
            }
		return mark;
		// TODO Auto-generated method stub
	}
	
	public static void clearDir(String delpath) {
		File dir = new File(delpath);
		if(dir.exists() == true){
			  String[] children = dir.list();
			  for(int i=0;i<children.length;i++){
				 File delfile = new File(delpath + "/"+ children[i]);
				 String type = children[i].substring(children[i].lastIndexOf(".") + 1);
				 if(type.equalsIgnoreCase("xls") || type.equalsIgnoreCase("xlsx")){
				   boolean result = delfile.delete();
				//   System.out.println("result = "+result);
				 }
			  }
			  
		   }
		
	}	

}
