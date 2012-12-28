/**
 * 
 */
package com.ibm.ncs.web.policytemplateapply;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.PolicyPublishInfo;
import com.ibm.ncs.model.dao.TPolicyPublishInfoDao;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

/**
 * @author root
 * 
 */
public class PolicyPublishInfoDefinitionController implements Controller {

	private TPolicyPublishInfoDao policyPublishInfoDao;
	private GenPkNumber genPkNumber;

	String pageView;
	String message = "";

	public TPolicyPublishInfoDao getPolicyPublishInfoDao() {
		return policyPublishInfoDao;
	}

	public void setPolicyPublishInfoDao(TPolicyPublishInfoDao policyPublishInfoDao) {
		this.policyPublishInfoDao = policyPublishInfoDao;
	}

	/**
	 * @return the genPkNumber
	 */
	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}

	/**
	 * @param genPkNumber
	 *          the genPkNumber to set
	 */
	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
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
		String formAction = request.getParameter("formAction");
		String creationMode = request.getParameter("creationMode");
		try {

			if (formAction != null && formAction.equalsIgnoreCase("showCreationForm")) {
				String newVersion4Draft = policyPublishInfoDao.getNewVersion4Draft();
				List<PolicyPublishInfo> policyPublishInfos = policyPublishInfoDao.getAllHistoryAndReleasedVersion();

				model.put("newVersion4Draft", newVersion4Draft);
				model.put("historyAndReleasedPolicyPublishInfos", policyPublishInfos);
				model.put("formAction", "create");
				model.put("refresh", "true");
				return new ModelAndView(getPageView(), "definition", model);
			} else if (formAction != null && formAction.equalsIgnoreCase("create")) {
				PolicyPublishInfo policyPublishInfo = new PolicyPublishInfo();
				policyPublishInfo.setPpiid(genPkNumber.getID());
				policyPublishInfo.setVersion(request.getParameter("version"));
				policyPublishInfo.setCreateTime(new Date());
				policyPublishInfo.setVersionTag(request.getParameter("versionTag"));
				policyPublishInfo.setDescription(request.getParameter("description"));
				policyPublishInfoDao.insert(policyPublishInfo);

				if (creationMode != null && creationMode.trim().length() > 0) {
					// Copy all policyTemplateVer
					PolicyPublishInfo ppi = this.policyPublishInfoDao.findById(creationMode);
					if (ppi != null) {
						this.policyPublishInfoDao.copyAllPolicyTemplateVer(Long.parseLong(creationMode), policyPublishInfo.getPpiid());
					}
				}
				model.put("message", "message.common.create.success");
				model.put("ppiid", policyPublishInfo.getPpiid());
				model.put("newVersion4Draft", policyPublishInfo.getVersion());
				model.put("versionTag", policyPublishInfo.getVersionTag());
				model.put("description", policyPublishInfo.getDescription());
				model.put("formAction", "update");
				model.put("refresh", "true");
			} else if (formAction != null && formAction.equalsIgnoreCase("showModifyForm")) {
				PolicyPublishInfo policyPublishInfo = policyPublishInfoDao.findById(request.getParameter("ppiid"));
				model.put("ppiid", policyPublishInfo.getPpiid());
				model.put("newVersion4Draft", policyPublishInfo.getVersion());
				model.put("versionTag", policyPublishInfo.getVersionTag());
				model.put("description", policyPublishInfo.getDescription());
				model.put("formAction", "update");
				model.put("refresh", "true");
				return new ModelAndView(getPageView(), "definition", model);
			} else if (formAction != null && formAction.equalsIgnoreCase("update")) {
				PolicyPublishInfo policyPublishInfo = new PolicyPublishInfo();
				// policyPublishInfo.setVersion(request.getParameter("version"));
				policyPublishInfo.setUpdateTime(new Date());
				policyPublishInfo.setVersionTag(request.getParameter("versionTag"));
				policyPublishInfo.setDescription(request.getParameter("description"));
				policyPublishInfoDao.update(Long.parseLong(request.getParameter("ppiid")), policyPublishInfo);
				model.put("message", "message.common.update.success");
				model.put("ppiid", policyPublishInfo.getPpiid());
				model.put("newVersion4Draft", policyPublishInfo.getVersion());
				model.put("versionTag", policyPublishInfo.getVersionTag());
				model.put("description", policyPublishInfo.getDescription());
				model.put("formAction", "update");
				model.put("refresh", "true");
			}
		} catch (UncategorizedSQLException ue) {
			if (formAction.equalsIgnoreCase("create")) {
				message = "policyDefinitionController.delete.error";
			} else if (formAction.equalsIgnoreCase("save")) {
				message = "policyDefinitionController.save.error";
			} else if (formAction.equalsIgnoreCase("add")) {
				message = "policyDefinitionController.add.error";
			}
			model.put("message", message);
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ue.getMessage());
			ue.printStackTrace();
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
