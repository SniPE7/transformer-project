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
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

public class NaviPolicyDefinitionController implements Controller {

	private TPolicyPublishInfoDao policyPublishInfoDao;

	TPolicyBaseDao TPolicyBaseDao;

	public void setTPolicyBaseDao(TPolicyBaseDao policyBaseDao) {
		TPolicyBaseDao = policyBaseDao;
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
		try {
			PolicyPublishInfo draftVersionPolicyPublishInfo = this.policyPublishInfoDao.getDraftVersion();
			model.put("draftVersionPolicyPublishInfo", draftVersionPolicyPublishInfo);

			List<TPolicyBase> _result1 = TPolicyBaseDao.findAll();

			Map<Long, Object> mtree = new TreeMap<Long, Object>();
			List<TPolicyBase> devDto = new ArrayList<TPolicyBase>();
			List<TPolicyBase> portDto = new ArrayList<TPolicyBase>();
			List<TPolicyBase> mibDto = new ArrayList<TPolicyBase>();
			List<TPolicyBase> othersDto = new ArrayList<TPolicyBase>();

			for (TPolicyBase dto : _result1) {
				Long catetmp = new Long(dto.getCategory());
				if (catetmp == 1) {
					devDto.add(dto);
				} else if (catetmp == 4) {
					portDto.add(dto);
				} else if (catetmp == 9) {
					mibDto.add(dto);
				} else {
					othersDto.add(dto);
				}
			}
			SortList<TPolicyBase> sorting = new SortList<TPolicyBase>();
			sorting.Sort(devDto, "getMpname", null);
			sorting.Sort(portDto, "getMpname", null);
			sorting.Sort(mibDto, "getMpname", null);
			sorting.Sort(othersDto, "getMpname", null);

			model.put("navitree", mtree);

			Map<Long, String> cateNameDef = new HashMap<Long, String>();
			cateNameDef.put(new Long(1), "policy.catename.device");
			cateNameDef.put(new Long(2), "policy.catename.line");
			cateNameDef.put(new Long(4), "policy.catename.port");
			cateNameDef.put(new Long(8), "policy.catename.tcp");
			model.put("cateNameDef", cateNameDef);
			model.put("mtree_Device", devDto);
			model.put("mtree_Others", othersDto);
			model.put("mtree_Port", portDto);
			model.put("mtree_MIB", mibDto);

			String[] mock = new String[] { "PredMIB 0", "PredMIB 1", "PredMIB 2", "PredMIB 3", "PredMIB 4" };
			model.put("mock", mock); // mock for ui test.
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("/secure/policytemplateapply/naviPolicyDefinition.jsp", "model", model);
	}

}
