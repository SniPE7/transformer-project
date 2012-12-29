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

	ExeShellProcess ExeShellProcess;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse arg1) throws Exception {
		String operator = ((Map<String, String>)request.getSession(true).getAttribute("signOnFlag")).get("username");

		Map<String,Object> model = new HashMap<String,Object>();
		ExeShellProcess.getStat().clear();
		ExeShellProcess.setDone(false);
		try {
			ExeShellProcess.stopProcess();
		} catch (Exception e) {
		}
		ExeShellProcess.setOperator(operator);
		ExeShellProcess.init();
		ExeShellProcess.startProcess();

		Map<String,String> stat = ExeShellProcess.getStat();
		stat.clear();
		model.put("stat", stat);
		request.getSession().setAttribute("progress", ExeShellProcess);
		request.getSession().setAttribute("progressInfo", stat);
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
		return ExeShellProcess;
	}

	public void setExeShellProcess(ExeShellProcess exeShellProcess) {
		ExeShellProcess = exeShellProcess;
	}

}
