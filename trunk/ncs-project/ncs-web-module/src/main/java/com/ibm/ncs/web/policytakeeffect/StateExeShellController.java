package com.ibm.ncs.web.policytakeeffect;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.util.Log4jInit;

public class StateExeShellController implements Controller {

	ExeShellProcess exeShellProcess;
	String pageView;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map model = new HashMap();
		Map stat = null;
		boolean done = false;
		try{
			stat = exeShellProcess.getStat();
			done = exeShellProcess.isDone();
			//System.out.println( done +"= StateProgressController TakeEffectProcess boolean done="+done);
			if(done){exeShellProcess.stopProcess();}
			
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage(), e);
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

	private String setKS(int steps) {		
		String ss = (steps <10 ?"0":"")+steps;
		return ss;
	}

	public ExeShellProcess getExeShellProcess() {
		return exeShellProcess;
	}

	public void setExeShellProcess(ExeShellProcess exeShellProcess) {
		this.exeShellProcess = exeShellProcess;
	}


}
