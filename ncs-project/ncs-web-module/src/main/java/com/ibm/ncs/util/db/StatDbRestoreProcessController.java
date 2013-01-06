package com.ibm.ncs.util.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SnmpUtil;
import com.ibm.ncs.web.policytakeeffect.TakeEffectProcessImpl;

public class StatDbRestoreProcessController implements Controller {

	DbRestore DbRestore;
	String pageView;
	long fintime = -1l;
	String  fin ="";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = "";// " 正在进行数据库恢复 ... 开始处理行... ";
		String icon = "<img border='0' src='../../images/icon_progress.gif' width='16' height='16' >";
		


		Map model = new HashMap();
		int stat =0;
		boolean done = false;
		String errormsg = "";
		try{
			stat = DbRestore.getRownum();

			done = DbRestore.isDone();
			if (stat == 0 && done == false){
				message = icon + " 正在上传文件,准备进行数据库恢复，请稍等．．．．．．";
			}
			if (stat>0){
				message = icon + " 正在进行数据库恢复 ... 处理行@ ";
			}
			errormsg = DbRestore.getErrMsg();
			//System.out.println( done +"= StateProgressController TakeEffectProcess boolean done="+done);
			if(done){
				DbRestore.stopProcess();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ; 
				
				if (fintime==-1){  //keep the complete time
					fintime = System.currentTimeMillis();
					fin = sdf.format(fintime) ; //sdf.format(new Date());
				}
				message = fin + "  数据库恢复操作完成!!  执行命令行数...";//"... download and check log file at /tmp/dbresore.log";
								
				//System.out.println("fin time="+fintime);
			}else{// done ==false
				fintime = -1l;
			}
			
			if(done && fintime!=-1 ){
				long tnow = System.currentTimeMillis();
				//System.out.println("delay now="+tnow);
				if( (tnow-fintime) > 5*60*1000){//5 min delay
					message = "";
					stat=0;
					
					DbRestore.setMessage("");
					DbRestore.setRownum(0);
					DbRestore.setErrMsg("");
				//	fintime=-1;
				}
			}
			
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e );
			e.printStackTrace();
		}
//		response.setHeader("Cache-Control","no-cache");
//		response.setHeader("Pragma","no-cache");
//		response.setDateHeader("Expires",-1);
//		response.getWriter().println(message +"; 处理进行 ..." +stat);
//		if (stat==0) return null;
		model.put("stat", stat==0?"":stat);
		model.put("message", message);
		model.put("errMsg", errormsg);
		return new ModelAndView(getPageView(), "model", model);
		//return null;
	}



	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public Map testDsp(){
		Map stat = new TreeMap();
		int steps = 0;
		stat.put(setKS(steps++), "process Syslog events process, set flags.");
		
		
		stat.put(setKS(steps++), "process Syslog events process NS, set flags.");


		stat.put(setKS(steps++), "process Syslog events attention...");


		stat.put(setKS(steps++), "process Syslog events LineEventsNotCare...");


		stat.put(setKS(steps++), "process SNMP thresholds process...");


		stat.put(setKS(steps++), "process ICMP thresholds process...");

		//System.out.println(model);
		
		stat.put(setKS(steps++), "Process Done!");
		return stat;
	}
	private String setKS(int steps) {		
		String ss = (steps <10 ?"0":"")+steps;
		return ss;
	}



	public DbRestore getDbRestore() {
		return DbRestore;
	}



	public void setDbRestore(DbRestore dbRestore) {
		DbRestore = dbRestore;
	}

}
