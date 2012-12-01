package com.ibm.ncs.web.policyapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyPeriod;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;
import com.ibm.ncs.util.SortMap;

public class NaviPolicyApplyController implements Controller {

	TPolicyBaseDao TPolicyBaseDao;
	TPolicyPeriodDao TPolicyPeriodDao;

	public void setTPolicyBaseDao(TPolicyBaseDao policyBaseDao) {
		TPolicyBaseDao = policyBaseDao;
	}

	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		
		try{
			List<TPolicyBase> _result1 = TPolicyBaseDao.findAll();
	
			Map<Long, Object> mtree = new TreeMap<Long, Object>();
			List<TPolicyBase> devDto = new ArrayList<TPolicyBase>();
			List<TPolicyBase> portDto = new ArrayList<TPolicyBase>();
			List<TPolicyBase> mibDto = new ArrayList<TPolicyBase>();
			List<TPolicyBase> othersDto = new ArrayList<TPolicyBase>();
			for (TPolicyBase dto : _result1) {
				Long catetmp = new Long(dto.getCategory());
				if(catetmp == 1){
					devDto.add(dto);
				}else if(catetmp == 4){
					portDto.add(dto);
				}else if(catetmp == 9){
					mibDto.add(dto);
				}else{
					othersDto.add(dto);
				}
			}
			SortList<TPolicyBase> sorting = new SortList<TPolicyBase>();
			sorting.Sort(devDto, "getMpname", null);
			sorting.Sort(portDto, "getMpname", null);
			sorting.Sort(mibDto, "getMpname", null);
			sorting.Sort(othersDto, "getMpname", null);
			
			List<TPolicyPeriod> _result2 = TPolicyPeriodDao.findAll();
			SortList<TPolicyPeriod> srt = new SortList<TPolicyPeriod>();
			srt.Sort(_result2, "getPpname", null);
			Map<Long, Object> period = new LinkedHashMap<Long, Object>();
			for (TPolicyPeriod dto : _result2) {
				Long ppidtmp = new Long(dto.getPpid());
				if (period.containsKey(ppidtmp) == false && !dto.getDefaultflag().equals("1")) {
					period.put(ppidtmp, dto);
				}
			}
//			SortMap<TPolicyPeriod> srt = new SortMap<TPolicyPeriod>();
//			Map<Long, Object> periodsorted = srt.sortByValue(period, "getPpname", null);
	
			model.put("navitree", mtree);
			model.put("period", period);
			
			Map<Long, String> cateNameDef = new HashMap<Long, String>();
			cateNameDef.put(new Long(1), "policy.catename.device");
			cateNameDef.put(new Long(2), "policy.catename.line");
			cateNameDef.put(new Long(4), "policy.catename.port");
			cateNameDef.put(new Long(8), "policy.catename.tcp");
			model.put("cateNameDef", cateNameDef);
			model.put("mtree_Device",devDto);
			model.put("mtree_Others", othersDto);
			model.put("mtree_Port", portDto);
			model.put("mtree_MIB", mibDto);
			
			String [] mock = new String[] {"PredMIB 0","PredMIB 1","PredMIB 2","PredMIB 3","PredMIB 4"};
			model.put("mock", mock); //mock for ui test.
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("/secure/policyapply/naviPolicyApply.jsp", "model", model);
	}

	

}
