/**
 * 
 */
package com.ibm.ncs.web.policytemplateapply;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.PolicyPublishInfo;
import com.ibm.ncs.model.dao.TPolicyPublishInfoDao;
import com.ibm.ncs.model.dao.TPolicyTemplateDao;
import com.ibm.ncs.model.dao.TPolicyTemplateVerDao;
import com.ibm.ncs.util.Log4jInit;

/**
 * @author root
 * 
 */
public class ShowPolicyPublishInfoView4ReleaseController implements Controller {

	private TPolicyPublishInfoDao policyPublishInfoDao;
	private TPolicyTemplateDao policyTemplateDao;
	private TPolicyTemplateVerDao policyTemplateVerDao;

	String pageView;
	String message = "";

	public TPolicyPublishInfoDao getPolicyPublishInfoDao() {
		return policyPublishInfoDao;
	}

	public void setPolicyPublishInfoDao(TPolicyPublishInfoDao policyPublishInfoDao) {
		this.policyPublishInfoDao = policyPublishInfoDao;
	}

	public void setPolicyTemplateDao(TPolicyTemplateDao policyTemplateDao) {
		this.policyTemplateDao = policyTemplateDao;
	}

	public void setPolicyTemplateVerDao(TPolicyTemplateVerDao policyTemplateVerDao) {
		this.policyTemplateVerDao = policyTemplateVerDao;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		message = "";
		try {
      String ppiid = request.getParameter("ppiid");
      PolicyPublishInfo policyPublishInfo = this.policyPublishInfoDao.findById(ppiid);
      model.put("policyPublishInfo", policyPublishInfo);
			model.put("refresh", "false");
			return new ModelAndView(getPageView(), "definition", model);
		} catch (Exception e) {
			message = "policyDefinitionController.error";
			model.put("message", message);
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView(getPageView(), "definition", model);

	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

}
