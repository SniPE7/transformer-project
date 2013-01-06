package com.ibm.ncs.web.policyapply;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.web.policytakeeffect.TakeEffectProcess;
import com.ibm.ncs.web.policytakeeffect.TakeEffectProcessImpl;

public class ExportXMLFileController implements Controller {

	String pageView;
	DataSource datasource;

	TakeEffectProcess takeEffectProcess;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse arg1) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		if (takeEffectProcess.isDone()) {
			String operator = ((Map<String, String>) request.getSession(true).getAttribute("signOnFlag")).get("username");
			takeEffectProcess.setOperator(operator);
			takeEffectProcess.init();
			takeEffectProcess.startProcess();
		}

		//Map<?, ?> stat = TakeEffectProcess.getStat();
		//model.put("stat", stat);
		//request.getSession().setAttribute("progress", TakeEffectProcess);
		//request.getSession().setAttribute("progressInfo", stat);
		return new ModelAndView(getPageView(), "model", model);
	}

	public static void display(TCategoryMapInit dto) {
		StringBuffer buf = new StringBuffer();
		buf.append(dto.getId());
		buf.append(", ");
		buf.append(dto.getName());
		buf.append(", ");
		buf.append(dto.getFlag());
		System.out.println(buf.toString());
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

	public TakeEffectProcess getTakeEffectProcess() {
		return takeEffectProcess;
	}

	public void setTakeEffectProcess(TakeEffectProcess takeEffectProcess) {
		this.takeEffectProcess = takeEffectProcess;
	}

}
