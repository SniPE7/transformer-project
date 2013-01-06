package com.ibm.ncs.web.policyapply;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.web.policytakeeffect.ExeShellProcess;

public class ExeShellController implements Controller {

	String pageView;
	DataSource datasource;

	ExeShellProcess exeShellProcess;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse arg1) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		if (this.exeShellProcess.isDone()) {
			String operator = ((Map<String, String>) request.getSession(true).getAttribute("signOnFlag")).get("username");
			exeShellProcess.setOperator(operator);
			exeShellProcess.init();
			exeShellProcess.startProcess();
		}

		return new ModelAndView(getPageView(), "model", model);
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public ExeShellProcess getExeShellProcess() {
		return exeShellProcess;
	}

	public void setExeShellProcess(ExeShellProcess exeShellProcess) {
		this.exeShellProcess = exeShellProcess;
	}

}
