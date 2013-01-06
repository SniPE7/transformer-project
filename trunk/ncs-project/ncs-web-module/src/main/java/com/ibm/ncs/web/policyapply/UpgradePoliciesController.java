/**
 * 
 */
package com.ibm.ncs.web.policyapply;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ibm.ncs.model.dao.PolicyPublishInfo;
import com.ibm.ncs.model.dao.TPolicyPublishInfoDao;
import com.ibm.ncs.model.dao.TPolicyTemplateDao;
import com.ibm.ncs.model.dao.TPolicyTemplateVerDao;
import com.ibm.ncs.util.Log4jInit;

/**
 * @author root
 * 
 */
public class UpgradePoliciesController implements Controller {

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
		String operation = request.getParameter("operation");

		Map<String, Object> model = new HashMap<String, Object>();
		message = "";
		boolean doing = request.getSession().getServletContext().getAttribute("DOING_POLICY_UPGRATION") != null
		    && ((Boolean) request.getSession().getServletContext().getAttribute("DOING_POLICY_UPGRATION")).booleanValue();
		if (operation != null && operation.equals("upgrade")) {
			try {

				StringWriter writer = new StringWriter();
				writer.write("<pre>\n");
				if (!doing) {
					request.getSession().getServletContext().setAttribute("DOING_POLICY_UPGRATION", true);
					this.policyPublishInfoDao.upgrade(writer);
					//Thread.sleep(20000);
					request.getSession().getServletContext().removeAttribute("DOING_POLICY_UPGRATION");
					writer.write("升级成功!\n");
				} else {
					writer.write("<font color='red'>其它用户已经运行升级程序, 现在无法运行, 稍后请查询升级的结果!</font>");
					doing = true;
				}
				writer.write("</pre>\n");
				model.put("operationMessage", writer.toString());
			} catch (Exception e) {
				message = e.getMessage();
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage(), e);
			}
		} else if (operation != null && operation.equals("migrate")) {
			try {
				StringWriter writer = new StringWriter();
				writer.write("<pre>\n");
				this.policyPublishInfoDao.migrate(writer);
				writer.write("迁移成功!\n");
				writer.write("</pre>\n");
				model.put("operationMessage", writer.toString());
			} catch (Exception e) {
				message = e.getMessage();
				Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage(), e);
			}
		}

		boolean needUpgrade = false;
		boolean needMigrate = false;
		try {
			PolicyPublishInfo releasedPolicyPublishInfo = this.policyPublishInfoDao.getReleasedVersion();
			model.put("releasedPolicyPublishInfo", releasedPolicyPublishInfo);

			PolicyPublishInfo appliedPolicyPublishInfo = this.policyPublishInfoDao.getAppliedVersion();
			model.put("appliedPolicyPublishInfo", appliedPolicyPublishInfo);

			model.put("refresh", "true");

			if (releasedPolicyPublishInfo == null && appliedPolicyPublishInfo == null) {
				message = "未发布策略模板, 无需升级本地策略定义!";
			} else if (releasedPolicyPublishInfo == null) {
				message = "未发布策略模板, 无需升级本地策略定义!";
			} else if (appliedPolicyPublishInfo == null) {
				if (doing) {
					message = "已发布策略模板, 本地从未应用策略模板，需迁移本地策略定义 其他用户已经运行升级程序, 请稍候!";
					needMigrate = true;
				} else {
					message = "已发布策略模板, 本地从未应用策略模板，需迁移本地策略定义!";
					needMigrate = true;
				}
			} else if (releasedPolicyPublishInfo != null && appliedPolicyPublishInfo != null) {
				if (releasedPolicyPublishInfo.getPpiid() == appliedPolicyPublishInfo.getPpiid()) {
					// Not need upgrade
					message = "版本一致, 无需升级本地策略定义!";
				} else {
					if (doing) {
						message = "版本不一致, 其他用户已经运行升级程序, 请稍候!";
						needUpgrade = true;
					} else {
						message = "版本不一致, 需升级本地策略定义!";
						needUpgrade = true;
					}
				}
			}
		} catch (Exception e) {
			message = e.getMessage();
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage(), e);
		}
		model.put("needMigrate", needMigrate);
		model.put("needUpgrade", needUpgrade);
		model.put("message", message);
		return new ModelAndView(getPageView(), "model", model);
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

}
