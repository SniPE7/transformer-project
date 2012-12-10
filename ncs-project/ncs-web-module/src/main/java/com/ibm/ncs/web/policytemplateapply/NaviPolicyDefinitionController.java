package com.ibm.ncs.web.policytemplateapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.PolicyPublishInfo;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyPublishInfoDao;
import com.ibm.ncs.model.dao.TPolicyTemplateDao;
import com.ibm.ncs.model.dao.TPolicyTemplateVerDao;
import com.ibm.ncs.model.dto.PolicyTemplate;
import com.ibm.ncs.model.dto.PolicyTemplateVer;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

public class NaviPolicyDefinitionController implements Controller {

	private TPolicyPublishInfoDao policyPublishInfoDao;
	private TPolicyTemplateDao policyTemplateDao;
	private TPolicyTemplateVerDao policyTemplateVerDao;

	TPolicyBaseDao TPolicyBaseDao;

	public void setTPolicyBaseDao(TPolicyBaseDao policyBaseDao) {
		TPolicyBaseDao = policyBaseDao;
	}

	/**
	 * @param policyTemplateDao the policyTemplateDao to set
	 */
	public void setPolicyTemplateDao(TPolicyTemplateDao policyTemplateDao) {
		this.policyTemplateDao = policyTemplateDao;
	}

	/**
	 * @param policyTemplateVerDao the policyTemplateVerDao to set
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

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		Map<Long, PolicyTemplate> policyTemplateMap = new HashMap<Long, PolicyTemplate>();
		model.put("policyTemplateMap", policyTemplateMap);
		Map<Long, PolicyPublishInfo> policyPublishInfoMap = new HashMap<Long, PolicyPublishInfo>();
		model.put("policyPublishInfoMap", policyPublishInfoMap);
		
		try {
			PolicyPublishInfo draftVersionPolicyPublishInfo = this.policyPublishInfoDao.getDraftVersion();
			model.put("draftVersionPolicyPublishInfo", draftVersionPolicyPublishInfo);
			if (draftVersionPolicyPublishInfo != null) {
				 List<PolicyTemplateVer> draftPolicies = this.policyTemplateVerDao.findByPublishInfoId(Long.toString(draftVersionPolicyPublishInfo.getPpiid()));
				 model.put("draftPolicies", draftPolicies);
				 for (PolicyTemplateVer tv: draftPolicies) {
					 PolicyTemplate pt = this.policyTemplateDao.findById(Long.toString(tv.getPtid()));
					 policyTemplateMap.put(pt.getPtid(), pt);
					 PolicyPublishInfo ppi = this.policyPublishInfoDao.findById(Long.toString(tv.getPpiid()));
					 policyPublishInfoMap.put(ppi.getPpiid(), ppi);
				 }
			}
			PolicyPublishInfo releasedVersionPolicyPublishInfo = this.policyPublishInfoDao.getReleasedVersion();
			model.put("releasedVersionPolicyPublishInfo", releasedVersionPolicyPublishInfo);
			if (releasedVersionPolicyPublishInfo != null) {
				 List<PolicyTemplateVer> releasedPolicies = this.policyTemplateVerDao.findByPublishInfoId(Long.toString(releasedVersionPolicyPublishInfo.getPpiid()));
				 model.put("releasedPolicies", releasedPolicies);
				 for (PolicyTemplateVer tv: releasedPolicies) {
					 PolicyTemplate pt = this.policyTemplateDao.findById(Long.toString(tv.getPtid()));
					 policyTemplateMap.put(pt.getPtid(), pt);
					 PolicyPublishInfo ppi = this.policyPublishInfoDao.findById(Long.toString(tv.getPpiid()));
					 policyPublishInfoMap.put(ppi.getPpiid(), ppi);
				 }
			}

			List<PolicyPublishInfo> historyVersionPolicyPublishInfos = this.policyPublishInfoDao.getHistoryVersions();
			model.put("historyVersionPolicyPublishInfos", historyVersionPolicyPublishInfos);
			for (PolicyPublishInfo ppi: historyVersionPolicyPublishInfos) {
				 List<PolicyTemplateVer> releasedPolicies = this.policyTemplateVerDao.findByPublishInfoId(Long.toString(ppi.getPpiid()));
				 model.put("releasedPolicies", releasedPolicies);
				 for (PolicyTemplateVer tv: releasedPolicies) {
					 PolicyTemplate pt = this.policyTemplateDao.findById(Long.toString(tv.getPtid()));
					 policyTemplateMap.put(pt.getPtid(), pt);
					 policyPublishInfoMap.put(ppi.getPpiid(), this.policyPublishInfoDao.findById(Long.toString(tv.getPpiid())));
				 }
			}
			
			Map<Long, String> cateNameDef = new HashMap<Long, String>();
			cateNameDef.put(new Long(1), "policy.catename.device");
			cateNameDef.put(new Long(2), "policy.catename.line");
			cateNameDef.put(new Long(4), "policy.catename.port");
			cateNameDef.put(new Long(8), "policy.catename.tcp");
			model.put("cateNameDef", cateNameDef);

			String[] mock = new String[] { "PredMIB 0", "PredMIB 1", "PredMIB 2", "PredMIB 3", "PredMIB 4" };
			model.put("mock", mock); // mock for ui test.
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("/secure/policytemplateapply/naviPolicyDefinition.jsp", "model", model);
	}

}
