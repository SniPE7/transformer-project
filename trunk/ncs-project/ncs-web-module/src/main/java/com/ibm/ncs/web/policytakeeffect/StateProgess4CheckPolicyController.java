package com.ibm.ncs.web.policytakeeffect;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.util.Log4jInit;

public class StateProgess4CheckPolicyController implements Controller {

	PolicyValidationProcess policyValidationProcess;
	String pageView;
	long fintime = -1l;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Map<String, String>> model = new HashMap<String, Map<String, String>>();
		Map<String, String> stat = null;
		boolean done = false;
		try {
			stat = policyValidationProcess.getStat();
			done = policyValidationProcess.isDone();
			if (done) {
				policyValidationProcess.stopProcess();
				fintime = System.currentTimeMillis();
			}
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		model.put("stat", stat);
		return new ModelAndView(getPageView(), "model", model);
	}

	public PolicyValidationProcess getPolicyValidationProcess() {
		return policyValidationProcess;
	}


	public void setPolicyValidationProcess(PolicyValidationProcess policyValidationProcess) {
		this.policyValidationProcess = policyValidationProcess;
	}


	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public Map<String, String> testDsp() {
		Map<String, String> stat = new TreeMap<String, String>();
		int steps = 0;
		stat.put(setKS(steps++), "process Syslog events process, set flags.");

		stat.put(setKS(steps++), "process Syslog events process NS, set flags.");

		stat.put(setKS(steps++), "process Syslog events attention...");

		stat.put(setKS(steps++), "process Syslog events LineEventsNotCare...");

		stat.put(setKS(steps++), "process SNMP thresholds process...");

		stat.put(setKS(steps++), "process ICMP thresholds process...");

		// System.out.println(model);

		stat.put(setKS(steps++), "Process Done!");
		return stat;
	}

	private String setKS(int steps) {
		String ss = (steps < 10 ? "0" : "") + steps;
		return ss;
	}

}
