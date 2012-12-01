package com.ibm.ncs.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

 public  class Log4jInit extends HttpServlet {
	 
	 public static Logger ncsLog = Logger.getLogger(Log4jInit.class);
	
	 public void init(ServletConfig config) throws ServletException {
	     String path = config.getServletContext().getRealPath("");
		 String file = config.getInitParameter("log4j");
	     String filePath = path + file;

	     Properties props = new Properties();
	     try {
	    	 //read the log4j.properties
	           FileInputStream istream = new FileInputStream(filePath);
	           props.load(istream);
	           istream.close();
	           
	         //set the log file path
	           //log file path is in server installation directory
	           String logFile = path  +"logs/" + props.getProperty("log4j.appender.FILE.file");
	           props.setProperty("log4j.appender.FILE.file",logFile);
	           PropertyConfigurator.configure(props);//load log4j configuration
	        } catch (IOException e) {
	            System.out.println("Could not read configuration file [" + filePath + "].");
	            System.out.println("Ignoring configuration file [" + filePath + "].");
	        }
	    }

}
