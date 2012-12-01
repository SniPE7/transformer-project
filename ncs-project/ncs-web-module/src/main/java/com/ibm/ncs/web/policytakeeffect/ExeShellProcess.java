package com.ibm.ncs.web.policytakeeffect;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.ibm.ncs.export.IcmpPolicyExporter;
import com.ibm.ncs.export.IcmpPolicyExporterImpl;
import com.ibm.ncs.export.SnmpPolicyExporter;
import com.ibm.ncs.export.SnmpPolicyExporterImpl;
import com.ibm.ncs.export.SrcTypeExporter;
import com.ibm.ncs.export.SrcTypeExporterImpl;
import com.ibm.ncs.model.dao.EventsAttentionDao;
import com.ibm.ncs.model.dao.IcmpThresholdsDao;
import com.ibm.ncs.model.dao.LinesEventsNotcareDao;
import com.ibm.ncs.model.dao.PolicySyslogDao;
import com.ibm.ncs.model.dao.SnmpThresholdsDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessDao;
import com.ibm.ncs.model.dao.SyslogEventsProcessNsDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dao.TServerInfoDao;
import com.ibm.ncs.model.dao.TSvrmodMapDao;
import com.ibm.ncs.model.dto.PolicySyslog;
import com.ibm.ncs.model.dto.SyslogEventsProcess;
import com.ibm.ncs.model.dto.SyslogEventsProcessNs;
import com.ibm.ncs.model.dto.SyslogEventsProcessNsPk;
import com.ibm.ncs.model.dto.SyslogEventsProcessPk;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.model.dto.TPolicyPeriodPk;
import com.ibm.ncs.model.dto.TServerInfo;
import com.ibm.ncs.model.dto.TSvrmodMap;
import com.ibm.ncs.model.exceptions.LinesEventsNotcareDaoException;
import com.ibm.ncs.model.exceptions.PolicySyslogDaoException;
import com.ibm.ncs.model.exceptions.TPolicyPeriodDaoException;
import com.ibm.ncs.model.exceptions.TServerInfoDaoException;
import com.ibm.ncs.model.exceptions.TSvrmodMapDaoException;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.TimeToStr;
import com.ibm.ncs.util.test.JobHandler;

public class ExeShellProcess {
	
	Logger logger = Logger.getLogger(ExeShellProcess.class);
	
    TServerInfoDao TServerInfoDao;
    TSvrmodMapDao TSvrmodMapDao;
    Writer writer;
	String message;
	boolean done;
	
	Thread process;
	Map stat = new TreeMap();

	public ExeShellProcess() {
		//init();
	}

	public void init() {
		process = new Thread(){
			public void run(){
				operations();
			}
		};
	}
	
	public void startProcess(){
		process.start();
	}
	
	public void stopProcess(){
		process.interrupt();
		//process.stop();
		//process.destroy();
	}

	public void operations(){
		System.out.println("ExeShellProcess start operation...");
		int steps=0 ;
		done=false;
		stat.clear();
		

		String [] syslogcmdstring = exeCommandStep1();//SYSLOG
		
		String [] icmpcmdstring   = exeCommandStep2();//ICMP
		
		String [] snmpcmdstring   = exeCommandStep3();//SNMP
		
		String [] restartcmdstring= exeCommandStep4();//Restart
		
		ResourceBundle prop = ResourceBundle.getBundle("ncc-configuration");
		String waituntil = prop.getString("exe.shell.at.time");
		waituntil = waituntil== null|| waituntil.equals("")?"5":waituntil;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ; 	
		String scr01 = "--------------------------- Shell Scripts Output ---------------------------";
		String scr02 = "--------------------------- End of Screen output ---------------------------";

		stat.put(setKS(steps++), sdf.format(new Date())+" 开始运行生效脚本 syslog shell script  ...");
		logger.info(" 开始运行生效脚本 syslog shell script  ...");
		try {
			//steps++;
			logger.info( "syslogcmdstring=" + ArrayPrint(syslogcmdstring));
			String output1st = waitUntillFireExeShell(syslogcmdstring, "syslog");
	//		System.out.println("output1st="+output1st);
			stat.put(setKS(steps++), "\n"+scr01+"\n"+output1st+"\n"+scr02);
		} catch (Exception e) {
			logger.error("syslogcmdstring... Error occured:\n" + e);
			e.printStackTrace();
		}
		stat.put(setKS(steps++), sdf.format(new Date())+" 完成 syslog shell script ");
		logger.info(" 完成 syslog shell script ");
		
		stat.put(setKS(steps++), sdf.format(new Date())+" 开始运行生效脚本 icmp shell script  ...");
		logger.info(" 开始运行生效脚本 icmp shell script  ...");
		try {
			//steps++;
			logger.info( "icmpcmdstring=" + ArrayPrint(icmpcmdstring));
			String output2nd = 
			waitUntillFireExeShell(icmpcmdstring, "icmp");
	//		System.out.println("output2nd="+output2nd);
			stat.put(setKS(steps++), "\n"+scr01+"\n"+output2nd+"\n"+scr02);
		} catch (Exception e) {
			logger.error( "icmpcmdstring... Error occured:\n" + e);
			e.printStackTrace();
		}
		stat.put(setKS(steps++), sdf.format(new Date())+" 完成 icmp shell script");
		logger.info(" 完成 icmp shell script");
		
		stat.put(setKS(steps++), sdf.format(new Date())+" 开始运行生效脚本 snmp shell script  ...");
		logger.info(" 开始运行生效脚本 snmp shell script  ...");
		try {
		//	steps++;
			logger.info( "snmpcmdstring=" + ArrayPrint(snmpcmdstring));
			String output3rd = 
			waitUntillFireExeShell(snmpcmdstring, "snmp");
	//		System.out.println("output3rd="+output3rd);
			stat.put(setKS(steps++), "\n"+scr01+"\n"+output3rd+"\n"+scr02);
		} catch (Exception e) {
			logger.error("snmpcmdstring... Error occured:\n" + e);
			e.printStackTrace();
		}
		stat.put(setKS(steps++), sdf.format(new Date())+" 完成 snmp shell script ");
		logger.info(" 完成 snmp shell script ");
		
		
		//-- only start when meet *5 minute
//		long delay = checkFireTime(waituntil);  // no more delay for stop service
//		
//		delay = 0;
//		stat.put(setKS(steps++), "重启服务脚本将在下个时刻运行 :" +sdf.format(System.currentTimeMillis() +delay));	
//		try {
//			Thread.sleep(delay);
//		} catch (InterruptedException e) {
//			//e.printStackTrace();
//		}
		stat.put(setKS(steps++), sdf.format(new Date()) + " 开始进行重启服务脚本 ");
		logger.info(" 开始进行重启服务脚本 ");
		try {
		//	steps++;
			logger.info( "restartcmdstring=" + ArrayPrint(restartcmdstring));
			String output4th = 
			waitUntillFireExeShell(restartcmdstring, "restart");
	//		System.out.println("output4th="+output4th);
			stat.put(setKS(steps++), "\n"+scr01+"\n"+output4th+"\n"+scr02);
		} catch (Exception e) {
			logger.error("restartcmdstring... Error occured:\n" + e);
			e.printStackTrace();
		}	
		stat.put(setKS(steps++), sdf.format(new Date())+" 完成重启服务脚本");
		logger.info(" 完成重启服务脚本");
		
		
		stat.put(setKS(steps++), "完成生效脚本");
		logger.info("完成生效脚本");
		System.out.println(stat);
		
		done=true;
		//model.put("done", "done");
	}

	
	private String ArrayPrint(String[] syslogcmdstring) {
		if(syslogcmdstring==null) return null;
		String ret = "";
		for (String tmp :syslogcmdstring){
			ret += tmp +" ";
		}
		return ret;
	}

	/**
	 * SYSLOG exe 
	 * @return
	 */
	private String[] exeCommandStep1(){
		//prepare server info
	//	String ip = "";
		String nmsname = "";
		String username = "";
		String password = "";
		String targetdir = "";
		String modname = "syslog";  //search for NCBF1
		String exeshell = "";
		String srcdir = "";
		String srcfile = "";
		try {
			List<TServerInfo> server =  TServerInfoDao.findByModuleName(modname);
			List<TSvrmodMap> mod = TSvrmodMapDao.findByModuleName(modname);
			ResourceBundle prop = ResourceBundle.getBundle("ncc-configuration");
			
			if(server.size()>0) {
		//		ip = server.get(0).getNmsip();
				nmsname  = server.get(0).getNmsname();
				username = server.get(0).getUsername();
				username = (username==null||username.equals(""))?"empty":username; //not used in this shell script
				password = server.get(0).getPassword();
				password = (password==null||password.equals(""))?"empty":password; //not used in this shell script
				targetdir = mod.get(0).getPath();
				exeshell = prop.getString("exe.shell.1st.script"); 
				srcdir  = prop.getString("export.xml.generate.dir");
				srcfile  = srcdir+"/SrcType";
			}
			
		} catch (TServerInfoDaoException e) {
			logger.error("exeCommandStep1 ... Error occured:\n" + e);
			e.printStackTrace();
		} catch (TSvrmodMapDaoException e) {
			logger.error("exeCommandStep1... Error occured:\n" + e);
			e.printStackTrace();
		}
		// Pattern as: sh 1st-script.sh nmsname user pass targetdir  src 
		// srcfile should have absolute dir + file 
		// $0 $1 $2 $3 $4 $5 
		String[] cmdstring = new String[]{  
											  exeshell
											, nmsname
											, username
											, password
											, targetdir
											, srcfile
											} ;
		System.out.println(cmdstring[0]+cmdstring[1]+cmdstring[2]+cmdstring[3]+cmdstring[4]+cmdstring[5]);
		return cmdstring;
	}
	
	/**
	 * ICMP upload ... exe
	 * @return
	 */
	private String[] exeCommandStep2(){
		//prepare server info
	//	String ip = "";
		String nmsname  = "";
		String username = "";
		String password = "";
		String targetdir = "";
		String modname = "icmp"; //search for NCBF3
		String exeshell = "";
		String srcdir = "";
		String srcfile = "";
		try {
			List<TServerInfo> server =  TServerInfoDao.findByModuleName(modname);
			List<TSvrmodMap> mod = TSvrmodMapDao.findByModuleName(modname);
			ResourceBundle prop = ResourceBundle.getBundle("ncc-configuration");
			
			if(server.size()>0) {
				nmsname = server.get(0).getNmsname();
				username = server.get(0).getUsername();
				password = server.get(0).getPassword();
				targetdir = mod.get(0).getPath();
				exeshell = prop.getString("exe.shell.2nd.script"); 
				srcdir  = prop.getString("export.xml.generate.dir") ;
				srcfile  = "icmp.xml";
			}
			
		} catch (TServerInfoDaoException e) {
			logger.error("exeCommandStep2... Error occured:\n" + e);
			e.printStackTrace();
		} catch (TSvrmodMapDaoException e) {
			logger.error("exeCommandStep2... Error occured:\n" + e);
			e.printStackTrace();
		}
		// Pattern as: sh 1st-script.sh ip user pass path  src
		// src only the name of the src
		// $0 $1 $2 $3 $4 $5 
		String[] cmdstring = new String[]{   
											  exeshell
											, nmsname
											, username
											, password
											, targetdir
											, srcfile
											} ;
		return cmdstring;
	}
	
	/**
	 * SNMP  upload ... exe
	 * @return
	 */
	private String[] exeCommandStep3(){//SNMP
		//prepare server info
		String nmsname = "";
		String username = "";
		String password = "";
		String targetdir = "";
		String modname = "snmp";//search for NCBF3
		String exeshell = "";
		String srcdir = "";
		String srcfile = "";
		try {
			List<TServerInfo> server =  TServerInfoDao.findByModuleName(modname);
			List<TSvrmodMap> mod = TSvrmodMapDao.findByModuleName(modname);
			ResourceBundle prop = ResourceBundle.getBundle("ncc-configuration");
			
			if(server.size()>0) {
				nmsname = server.get(0).getNmsname();
				username = server.get(0).getUsername();
				password = server.get(0).getPassword();
				targetdir = mod.get(0).getPath();
				exeshell = prop.getString("exe.shell.3rd.script"); 
				srcdir  = prop.getString("export.xml.generate.dir") ;
				srcfile  = "snmp.xml";
			}
			
		} catch (TServerInfoDaoException e) {
			logger.error("exeCommandStep3... Error occured:\n" + e);
			e.printStackTrace();
		} catch (TSvrmodMapDaoException e) {
			logger.error("exeCommandStep3... Error occured:\n" + e);
			e.printStackTrace();
		}
		// Pattern as: sh 1st-script.sh ip user pass path  src
		// $0 $1 $2 $3 $4 $5 
		String[] cmdstring = new String[]{  
											  exeshell
											, nmsname
											, username
											, password
											, targetdir
											, srcfile
											} ;
		return cmdstring;
	}
	
	/**
	 * Re-Start exe 
	 * @return
	 */
	private String[] exeCommandStep4(){//Re-Start services
		//prepare server info
		String nmsname = "";
		String username = "";
		String password = "";
//		String targetdir = "";
		String modname = "snmp"; //search for NCBF3  
		String exeshell = "";
//		String srcdir ="";
//		String srcfile = "";
		try {
			List<TServerInfo> server =  TServerInfoDao.findByModuleName(modname);
			List<TSvrmodMap> mod = TSvrmodMapDao.findByModuleName(modname);
			ResourceBundle prop = ResourceBundle.getBundle("ncc-configuration");
			
			if(server.size()>0) {
				nmsname = server.get(0).getNmsname();
				username = server.get(0).getUsername();
				password = server.get(0).getPassword();
//				targetdir = mod.get(0).getPath();
				exeshell = prop.getString("exe.shell.4th.script"); 
		//		srcdir  = prop.getString("export.xml.generate.dir") ;
				
			}
			
		} catch (TServerInfoDaoException e) {
			logger.error("exeCommandStep4... Error occured:\n" + e);
			e.printStackTrace();
		} catch (TSvrmodMapDaoException e) {
			logger.error("exeCommandStep4... Error occured:\n" + e);
			e.printStackTrace();
		}
		// Pattern as: sh 1st-script.sh ip user pass path  src
		// $0 $1 $2 $3 $4 $5 
		String[] cmdstring = new String[]{ 
											  exeshell
											, nmsname
											, username
											, password
			//								, targetdir
			//								, srcfile
											} ;
		return cmdstring;
	}
/*	
	//private void waitUntillFireExeShell(String[] cmdstring, String jobid, Writer output) 
	private void waitUntillFireExeShell(String[] cmdstring, String jobid) 
	{
		//cmdstring = new String []{"/B2B/test/testb2b.sh"};
		try {
			JobHandler command = new JobHandler(Runtime.getRuntime().
					exec(cmdstring), jobid);
	//		command.setTheOutput(output);
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
//			command.logclose();
		} catch (InterruptedException e) {
	//		Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e);
			//e.printStackTrace();
		} catch (IOException e) {
	//		Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e);
			e.printStackTrace();
		}
	}
*/

	//private void waitUntillFireExeShell(String[] cmdstring, String jobid, Writer output) 
	private String waitUntillFireExeShell(String[] cmdstring, String jobid) 
	{
		//cmdstring = new String []{"/B2B/test/testb2b.sh"};
		String output = "";
		try {
			JobHandler command = new JobHandler(Runtime.getRuntime().
					exec(cmdstring), jobid);
	//		command.setTheOutput(output);
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
			output = command.getStdOutText();
//			System.out.println("waitUntillFireExeShell...output="+output);
//			command.logclose();
		} catch (InterruptedException e) {
			output += "Interrupted occured:";
			logger.error("waitUntillFireExeShell... InterruptedException occured:\n" + e);
			//e.printStackTrace();
		} catch (IOException e) {
			output += "I/O Error occured:\n"+e;
			logger.error("waitUntillFireExeShell... I/O Error occured:\n" + e);
			//e.printStackTrace();
		} catch (Exception e) {
			output += "Error occured:\n"+e;
			logger.error("waitUntillFireExeShell... Error occured:\n" + e);
			//e.printStackTrace();
		}
		return output;
	}

	
	private long checkFireTime(String waituntil) {
		long waitlong = Long.parseLong(waituntil);
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss") ; 
		long current = System.currentTimeMillis();
		String minsec = sdf.format(current);
		String[] mmss = minsec.split(":");
		long mm = Long.parseLong(mmss[0]);
		long ss = Long.parseLong(mmss[1]);
	//	System.out.println("mmss="+mmss +" , ");
		long delay = waitlong -(mm % waitlong);
	//	System.out.println("diff  delay1="+delay);
		delay = delay * 60;
	//	System.out.println("minute delay2="+delay);
		delay = delay - ss;

	//	System.out.println("second delay3="+delay);
		
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ; 
		
	//	System.out.println(sdf.format(current));
	//	System.out.println(sdf.format(current+delay*1000));
		return delay*1000;
	}

	private String setKS(int steps) {		
		String ss = (steps <10 ?"0":"")+steps;
		return ss;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExeShellProcess exsh = new ExeShellProcess();
		String[] mycmd = new String[] {"/B2B/test/testb2b.sh"};//test only
		
		try {
			exsh.waitUntillFireExeShell(mycmd,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String ppath = System.getProperty("user.dir");
		// Pattern as: sh 1st-script.sh ip user pass path  src
		// $0 $1 $2 $3 $4 $5 
		String[] cmdstring = new String[]{  //"/usr/bin/ksh "
											"/B2B/test/exe-1st-step-syslog.sh" 
											, "10.10.10.88"
											, "root"
											, "passw0rd"
											, "/tmp"
											, ""
											} ;
		  
	
		try {
			exsh.waitUntillFireExeShell(cmdstring,"jobid");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

	

    private void sleeping2000(){
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public TServerInfoDao getTServerInfoDao() {
		return TServerInfoDao;
	}

	public void setTServerInfoDao(TServerInfoDao serverInfoDao) {
		TServerInfoDao = serverInfoDao;
	}

	public TSvrmodMapDao getTSvrmodMapDao() {
		return TSvrmodMapDao;
	}

	public void setTSvrmodMapDao(TSvrmodMapDao svrmodMapDao) {
		TSvrmodMapDao = svrmodMapDao;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map getStat() {
		return stat;
	}

	public void setStat(Map stat) {
		this.stat = stat;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Thread getProcess() {
		return process;
	}

	public void setProcess(Thread process) {
		this.process = process;
	}



}
