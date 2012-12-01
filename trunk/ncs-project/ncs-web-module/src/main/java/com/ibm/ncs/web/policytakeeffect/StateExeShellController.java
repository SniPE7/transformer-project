package com.ibm.ncs.web.policytakeeffect;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SnmpUtil;

public class StateExeShellController implements Controller {

	ExeShellProcess ExeShellProcess;
	String pageView;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map model = new HashMap();
		Map stat = null;
		boolean done = false;
		try{
			stat = ExeShellProcess.getStat();

			done = ExeShellProcess.isDone();
			//System.out.println( done +"= StateProgressController TakeEffectProcess boolean done="+done);
			if(done){ExeShellProcess.stopProcess();}
			
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		model.put("stat", stat);
		return new ModelAndView(getPageView(), "model", model);
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



	public ExeShellProcess getExeShellProcess() {
		return ExeShellProcess;
	}



	public void setExeShellProcess(ExeShellProcess exeShellProcess) {
		ExeShellProcess = exeShellProcess;
	}

}
