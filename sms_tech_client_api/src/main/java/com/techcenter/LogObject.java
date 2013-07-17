package com.techcenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogObject {
	private static final Logger logger = LoggerFactory.getLogger(LogObject.class);
	
	public static void logSubmit(Object arg0){
		logger.debug(arg0.toString());
	}
	public static void logResponse(Object arg0){
		logger.info(arg0.toString());
	}
	public static void logReport(Object arg0){
		logger.warn(arg0.toString());
	}
	public static void logdeliver(Object arg0){
		logger.error(arg0.toString());
	}
}
