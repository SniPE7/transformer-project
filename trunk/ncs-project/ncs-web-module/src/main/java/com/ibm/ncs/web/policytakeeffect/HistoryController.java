/**
 * 
 */
package com.ibm.ncs.web.policytakeeffect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TPolicyPublishInfoDao;
import com.ibm.ncs.model.dao.TPolicyTemplateDao;
import com.ibm.ncs.model.dao.TPolicyTemplateScopeDao;
import com.ibm.ncs.model.dao.TPolicyTemplateVerDao;
import com.ibm.ncs.model.dao.TServerNodeDao;
import com.ibm.ncs.model.dao.TTakeEffectHistoryDao;
import com.ibm.ncs.model.dao.TUserDao;
import com.ibm.ncs.model.dto.TTakeEffectHistory;
import com.ibm.ncs.util.Log4jInit;

/**
 * @author root
 * 
 */
public class HistoryController implements Controller {

	private TPolicyPublishInfoDao policyPublishInfoDao;
	private TPolicyTemplateDao policyTemplateDao;
	private TPolicyTemplateScopeDao policyTemplateScopeDao;
	private TPolicyTemplateVerDao policyTemplateVerDao;
	private TTakeEffectHistoryDao takeEffectHistoryDao;
	private TServerNodeDao serverNodeDao;
	private TUserDao userDao;

	String pageView;
	String message = "";

	public void setTakeEffectHistoryDao(TTakeEffectHistoryDao takeEffectHistoryDao) {
		this.takeEffectHistoryDao = takeEffectHistoryDao;
	}

	public void setServerNodeDao(TServerNodeDao serverNodeDao) {
		this.serverNodeDao = serverNodeDao;
	}

	public void setUserDao(TUserDao userDao) {
		this.userDao = userDao;
	}

	public void setPolicyTemplateScopeDao(TPolicyTemplateScopeDao policyTemplateScopeDao) {
		this.policyTemplateScopeDao = policyTemplateScopeDao;
	}

	/**
	 * @param policyTemplateDao
	 *          the policyTemplateDao to set
	 */
	public void setPolicyTemplateDao(TPolicyTemplateDao policyTemplateDao) {
		this.policyTemplateDao = policyTemplateDao;
	}

	/**
	 * @param policyTemplateVerDao
	 *          the policyTemplateVerDao to set
	 */
	public void setPolicyTemplateVerDao(TPolicyTemplateVerDao policyTemplateVerDao) {
		this.policyTemplateVerDao = policyTemplateVerDao;
	}

	/**
	 * @param policyPublishInfoDao
	 *          the policyPublishInfoDao to set
	 */
	public void setPolicyPublishInfoDao(TPolicyPublishInfoDao policyPublishInfoDao) {
		this.policyPublishInfoDao = policyPublishInfoDao;
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
		message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String ppiid = request.getParameter("ppiid");
			if (ppiid != null && ppiid.trim().length() > 0) {
				 List<TTakeEffectHistory> result = this.takeEffectHistoryDao.findBranchHistoryByPpiid(Long.parseLong(ppiid));
			}
		} catch (Exception e) {
			message = "policyDefinitionController.error";
			model.put("message", message);
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage(), e);
			e.printStackTrace();
		}
		return new ModelAndView(this.getPageView(), "definition", model);

	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}
}
