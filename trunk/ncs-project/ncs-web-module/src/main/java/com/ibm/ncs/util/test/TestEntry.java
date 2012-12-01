package com.ibm.ncs.util.test;

import java.io.IOException;

import org.apache.log4j.Logger;


public class TestEntry {
//	private static final Logger LOGGER = Logger.getLogger("JobHandler");
//	private static final Logger LOGGER2 = Logger.getLogger("errorLog");
//	private static FileHandler filehler ,errorhandler;
	
	private static final Logger LOGGER = Logger.getLogger(TestEntry.class);
	private static final Logger LOGGER2 = Logger.getLogger("errorLog");
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try 
		{ 
			
			String[] commandString = args;
			System.out.println("Usage: java -jar jobhandler.jar \"Command String\"");
			System.out.println("++...commandString =["+commandString+"]...++");
			
//			String[] sid = IDmaker.makeID(commandString);
//			String jobmgrId = sid[0];
//			System.out.println("++...TestEntry .IDmaker =["+jobmgrId+"]...++");
			
//			startLogger();
			//Runtime runtime = Runtime.getRuntime();
			//ProcessBuilder proc = new ProcessBuilder();
			//			exec("d:/tmp/testB2B/bsbtest.bat"));//("cmd /C dir c:\\"));//("native2ascii")); 
			//JobHandler command = new JobHandler(Runtime.getRuntime().
			//		exec(commandString));
					//exec("d:/tmp/testB2B/bsbtest.bat"));//("cmd /C dir c:\\"));//("native2ascii"));

			JobHandler command = new JobHandler(Runtime.getRuntime().
					exec(commandString), "jobmgrId");
			command.startIn(); 
			command.startOut(); 
			command.startErr();
			command.startIn2();
			
			while(true){
				Thread.sleep(5000);
				if(command.isStopoutput()){
					command.interruptIn();
					command.interruptOut();
					command.interruptErr();
					command.interruptIn2();
					break;
				}
			}
			System.out.println("...System.exit(0) ...");
			System.exit(0);
		} 
		catch (Exception e) { 
			System.out.println("...TestEntry got Exception ...system.exit(-1) ...");
			e.printStackTrace();  
			System.exit(-1);
		}
		finally{
//			filehler.close();
//			errorhandler.close();
		}

		
		
	}
//	private static void startLogger() {
//		try {
////				filehler = new FileHandler("log/"+sid[1]+"-"+sid[4]+".log", true);//("../log/jobhandler.log", true);	
//			filehler.setFormatter(new SimpleFormatter());
//			LOGGER.setLevel(Level.FINEST);
//			LOGGER.addHandler(filehler);
//
//			errorhandler = new FileHandler("logs/errorhandler.log", true);
//			errorhandler.setFormatter(new SimpleFormatter());
//			LOGGER2.setLevel(Level.FINEST);
//			LOGGER2.addHandler(errorhandler);
//			
//		} catch (IOException e1) {
//			
//		}
//	}

}
