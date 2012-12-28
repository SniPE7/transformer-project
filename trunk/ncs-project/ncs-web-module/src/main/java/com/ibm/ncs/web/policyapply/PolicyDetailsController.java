/**
 * 
 */
package com.ibm.ncs.web.policyapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.DspEventsFromPolicySyslogDao;
import com.ibm.ncs.model.dao.DspSyslogEventsDao;
import com.ibm.ncs.model.dao.PolDetailDspDao;
import com.ibm.ncs.model.dao.PolicySyslogDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TPolicyBaseDao;
import com.ibm.ncs.model.dao.TPolicyDetailsDao;
import com.ibm.ncs.model.dao.TPolicyDetailsWithRuleDao;
import com.ibm.ncs.model.dao.TPolicyPeriodDao;
import com.ibm.ncs.model.dto.DspEventsFromPolicySyslog;
import com.ibm.ncs.model.dto.DspSyslogEvents;
import com.ibm.ncs.model.dto.PolDetailDsp;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TPolicyBase;
import com.ibm.ncs.model.dto.TPolicyDetailsWithRule;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

/**
 * @author root
 * 
 */
public class PolicyDetailsController implements Controller {

	TPolicyDetailsWithRuleDao policyDetailsWithRuleDao;
	TGrpNetDao TGrpNetDao;
	TPolicyBaseDao TPolicyBaseDao;
	TPolicyPeriodDao TPolicyPeriodDao;
	TPolicyDetailsDao TPolicyDetailsDao;
	TModuleInfoInitDao TModuleInfoInitDao;
	TEventTypeInitDao TEventTypeInitDao;
	PolDetailDspDao PolDetailDspDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	DspSyslogEventsDao dspSyslogEventsDao;
	PolicySyslogDao policySyslogDao;
	DspEventsFromPolicySyslogDao DspEventsFromPolicySyslogDao;

	String pageView;
	String message;

	public void setTPolicyBaseDao(TPolicyBaseDao policyBaseDao) {
		TPolicyBaseDao = policyBaseDao;
	}

	public void setTPolicyPeriodDao(TPolicyPeriodDao policyPeriodDao) {
		TPolicyPeriodDao = policyPeriodDao;
	}

	public TPolicyDetailsWithRuleDao getPolicyDetailsWithRuleDao() {
		return policyDetailsWithRuleDao;
	}

	public void setPolicyDetailsWithRuleDao(TPolicyDetailsWithRuleDao policyDetailsWithRuleDao) {
		this.policyDetailsWithRuleDao = policyDetailsWithRuleDao;
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
			message = request.getParameter("message");
			String mpidstr = request.getParameter("mpid");
			long mpid = Long.parseLong(mpidstr);
			TPolicyBase policyBase = TPolicyBaseDao.findByPrimaryKey(mpid);
			model.put("policyBase", policyBase);
			if (policyBase.getPtvid() > 0) {
				 model.put("ptvid", policyBase.getPtvid());
			}

			String mpname = request.getParameter("mpname");

			String category = request.getParameter("cate");
			String displayOption = request.getParameter("listSeled");
			String selectedEveType = request.getParameter("listeve_type");
			// System.out.println("&&&&&&&&&&%%%%%%%%%%%%%%selEvetype=" +
			// selectedEveType);
			String mode = request.getParameter("mode");

			if (mode == null || mode.equals(""))
				mode = "ICMP";

			// get manufacture list
			String manufacture = request.getParameter("manufselect");
			// System.out.println("Manufacture selection is: " + manufacture);
			Map<Integer, List<DspSyslogEvents>> unselectedSyslog = new HashMap<Integer, List<DspSyslogEvents>>();
			Map<String, Map> DspSyslogMap = new TreeMap<String, Map>();
			List<TManufacturerInfoInit> tmflist = TManufacturerInfoInitDao.findAll();
			SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
			sortmanu.Sort(tmflist, "getMrname", null);
			model.put("mflist", tmflist);
			if (mode.equalsIgnoreCase("syslog")) {
				List<DspSyslogEvents> sysloglst = null; /* */
				sysloglst = dspSyslogEventsDao.listSyslogEventsOnMpid(mpid);
				SortList<DspSyslogEvents> sort0002 = new SortList<DspSyslogEvents>();
				sort0002.Sort(sysloglst, "getEvents", null);
				for (DspSyslogEvents sdto : sysloglst) {
					String themark = sdto.getMark();
					String themanufacture = sdto.getManufacture();
					if (!themanufacture.equalsIgnoreCase(manufacture))
						continue;
					Long theeventtype = sdto.getEventtype();
					if (theeventtype == null || theeventtype <= 0 || theeventtype > 8)
						theeventtype = (long) 8;
					// if(!category.equalsIgnoreCase("4") && (theeventtype==2 ||
					// theeventtype==1)) //do not display port event for non port policy
					// continue;
					if (category.equalsIgnoreCase("1") && (theeventtype == 2 || theeventtype == 1)) // device
					{
						continue;
					}
					if (category.equalsIgnoreCase("4") && !(theeventtype == 2 || theeventtype == 1)) // port
					                                                                                 // events
					                                                                                 // only
					                                                                                 // ...ignore
					                                                                                 // others
					                                                                                 // except
					                                                                                 // 1wan&2lan
					{
						continue;
					}
					if (category.equalsIgnoreCase("9") && !(theeventtype == 2 || theeventtype == 1)) // predefmib
					                                                                                 // work
					                                                                                 // as
					                                                                                 // port
					                                                                                 // events
					                                                                                 // ??
					{
						continue;
					}
					// System.out.println("Begin For:\n\t****event type is:" +
					// theeventtype);
					Map<Integer, List<DspSyslogEvents>> theMFsyslog = null;
					try {
						theMFsyslog = DspSyslogMap.get(themanufacture.toUpperCase());
					} catch (Exception e) {
					}
					if (theMFsyslog == null) {
						theMFsyslog = new TreeMap<Integer, List<DspSyslogEvents>>();
						// System.out.println("\tthe MFsyslog is null");
					}

					List<DspSyslogEvents> sysloglstdsp = null;
					try {
						sysloglstdsp = theMFsyslog.get(Integer.parseInt(String.valueOf(theeventtype)));
					} catch (Exception e) {
					}
					if (sysloglstdsp == null) {
						sysloglstdsp = new ArrayList<DspSyslogEvents>();
						// System.out.println("\tthe sysloglstdsp is null");
					}
					sysloglstdsp.add(sdto);
					SortList<DspSyslogEvents> sortunselect = new SortList<DspSyslogEvents>();
					sortunselect.Sort(sysloglstdsp, "getEvents", null);

					// System.out.println("\tsyslog length=" + sysloglstdsp.size());
					theMFsyslog.put(Integer.parseInt(String.valueOf(theeventtype)), sysloglstdsp);
					DspSyslogMap.put(themanufacture.toUpperCase(), theMFsyslog);
					// System.out.println("End of for\n");
				}
				// ---
				unselectedSyslog = DspSyslogMap.get(manufacture.toUpperCase());

				// System.out.println("unselectedSyslog:" + unselectedSyslog + "\n" +
				// "DspSyslogMap"+DspSyslogMap);
			}

			List<PolDetailDsp> details = new ArrayList<PolDetailDsp>();
			Map<String, Object> detailMap = new HashMap<String, Object>();
			Map<Integer, Object> syslogdetailMap = new HashMap<Integer, Object>();
			// List<PolicySyslog> syslogDetails = new ArrayList<PolicySyslog>();
			List<DspEventsFromPolicySyslog> eventsSyslogDetails = new ArrayList<DspEventsFromPolicySyslog>();
			Map<String, Object> eventsDictionary = new HashMap<String, Object>();
			if (!mode.equalsIgnoreCase("syslog")) {
				details = PolDetailDspDao.findByMpid(mpid);
				SortList<PolDetailDsp> sortpdd = new SortList<PolDetailDsp>();
				sortpdd.Sort(details, "getMajor", null);

				for (PolDetailDsp dto : details) {
					String key = dto.getMname();
					key = (key == null) ? "" : key;
					List<PolDetailDsp> templst = null;
					if (detailMap.containsKey(key)) {
						templst = (List<PolDetailDsp>) detailMap.get(key);
					} else {
						templst = new ArrayList<PolDetailDsp>();
					}
					templst.add(dto);
					detailMap.put(key, templst);

				}
				// System.out.println("detailMap="+detailMap);
				details = (List<PolDetailDsp>) detailMap.get(mode.toLowerCase());

			} else { // syslog policies
				// syslogDetails =
				// policySyslogDao.findWhereManufactureAndMpidEquals(manufacture,mpid);
				// //
				// System.out.println("%%%%%%%%%%%%Get ppppolicy syslog by selected manufacture size="
				// + syslogDetails.size()+ "\n\tcontent:\n" + syslogDetails);
				// for(PolicySyslog dto: syslogDetails){
				// int key = Integer.parseInt(String.valueOf(dto.getEventtype()));
				// List<PolicySyslog> templst = null;
				// if(syslogdetailMap.containsKey(key)){
				// templst = (List<PolicySyslog>)syslogdetailMap.get(key);
				// }else{
				// templst = new ArrayList<PolicySyslog>();
				// }
				// templst.add(dto);
				// syslogdetailMap.put(key, templst);
				//
				// }

				try {
					eventsSyslogDetails = DspEventsFromPolicySyslogDao.findDspEventsByManufactureAndMpid(manufacture, mpid);
					SortList<DspEventsFromPolicySyslog> sortpol = new SortList<DspEventsFromPolicySyslog>();
					sortpol.Sort(eventsSyslogDetails, "getEvents", null);
				} catch (Exception e) {
					e.printStackTrace();
				}

				for (DspEventsFromPolicySyslog dto : eventsSyslogDetails) {
					int key = Integer.parseInt(String.valueOf(dto.getEventtype()));
					List<DspEventsFromPolicySyslog> templst = null;
					if (syslogdetailMap.containsKey(key)) {
						templst = (List<DspEventsFromPolicySyslog>) syslogdetailMap.get(key);
					} else {
						templst = new ArrayList<DspEventsFromPolicySyslog>();
					}
					templst.add(dto);
					syslogdetailMap.put(key, templst);

				}
				// List<DspSyslogEvents> occupiedSysloglst =
				// dspSyslogEventsDao.listOccupiedSyslogEvents();
				// for(DspSyslogEvents dto :occupiedSysloglst){
				// String kmark = dto.getMark();
				// String kmf = dto.getManufacture();
				// String keymarkmf = kmark +"_"+kmf;
				// String vEvents = dto.getEvents();
				// eventsDictionary.put(keymarkmf, vEvents);
				// }
				// System.out.println("syslogdetailMap="+syslogdetailMap);
			}

			List<TModuleInfoInit> module = TModuleInfoInitDao.findAll();

			List<TEventTypeInit> unselected = null; /* */
			List<TEventTypeInit> snmplst = null; /* */
			if (category.equals("1")) { // cate=1 as DEVECE policy
				snmplst = TEventTypeInitDao.listForDeviceSnmp(mpid);
				// SortList<TEventTypeInit> sortpdd = new SortList<TEventTypeInit>();
				// sortpdd.Sort(snmplst, "getMajor", null);
			} else if (category.equals("4")) // treated as PORT policy
			{
				snmplst = TEventTypeInitDao.listForPortSnmp(mpid);
				// SortList<TEventTypeInit> sortpdd = new SortList<TEventTypeInit>();
				// sortpdd.Sort(snmplst, "getMajor", null);
			} else if (category.equals("9")) // treated as PreDefMib policy
			{
				snmplst = TEventTypeInitDao.listForPreDefMibSnmp(mpid);
				// SortList<TEventTypeInit> sortpdd = new SortList<TEventTypeInit>();
				// sortpdd.Sort(snmplst, "getMajor", null);
			}
			List<TEventTypeInit> icmplst = null; /* */
			if (category.equals("1")) { // cate=1 as DEVECE policy
				icmplst = TEventTypeInitDao.listForDeviceIcmp(mpid);
			} else if (category.equals("4")) // treated as PORT policy
			{
				icmplst = TEventTypeInitDao.listForPortIcmp(mpid);
			} else if (category.equals("9")) // treated as PreDefMib policy
			{
				icmplst = TEventTypeInitDao.listForPortIcmp(mpid);
			}
			SortList<TEventTypeInit> sort0001 = new SortList<TEventTypeInit>();
			sort0001.Sort(snmplst, "getMajor", null);
			sort0001.Sort(icmplst, "getMajor", null);
			if (mode.equalsIgnoreCase("icmp")) {
				unselected = icmplst;
			} else if (mode.equalsIgnoreCase("snmp")) {
				unselected = snmplst;
			}
			if (mode.equalsIgnoreCase("syslog")) {
				model.put("details", syslogdetailMap);
				model.put("unselected", unselectedSyslog);
			} else {
				if (details != null) {
					for (PolDetailDsp pdd : details) {
						TPolicyDetailsWithRule policyDetailsWithRule = this.policyDetailsWithRuleDao.findByEveidAndModid(pdd.getPtvid(), pdd.getEveid(), pdd.getModid());
						pdd.setPolicyDetailsWithRule(policyDetailsWithRule);
					}
				}
				model.put("details", details);
				model.put("unselected", unselected);
			}
			model.put("mpid", mpidstr);
			model.put("mpname", mpname);
			model.put("displayOption", displayOption);
			model.put("selectedEveType", selectedEveType);
			model.put("cate", category);
			model.put("mode", mode);
			model.put("eventType", getEventTypeMap());
			model.put("syslog", DspSyslogMap);
			model.put("pmpmanu", manufacture);
		} catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}

		return new ModelAndView(getPageView(), "model", model);
	}

	public Map getCompareTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "==");
		map.put("2", "!=");
		map.put("3", "<");
		map.put("4", ">");
		map.put("5", "<=");
		map.put("6", ">=");
		map.put("7", "Like");
		map.put("8", "Not Like");

		return map;
	}

	public Map<Integer, String> getEventTypeMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, "****广域网端口事件");
		map.put(1, "广域网端口事件");
		map.put(2, "局域网端口类事件");
		map.put(3, "网络设备类事件");
		map.put(4, "SNASW事件");
		map.put(5, "路由事件");
		map.put(6, "阀值事件");
		map.put(7, "安全事件");
		map.put(8, "其他类事件");
		return map;
	}

	public TPolicyDetailsDao getTPolicyDetailsDao() {
		return TPolicyDetailsDao;
	}

	public void setTPolicyDetailsDao(TPolicyDetailsDao policyDetailsDao) {
		TPolicyDetailsDao = policyDetailsDao;
	}

	public TPolicyBaseDao getTPolicyBaseDao() {
		return TPolicyBaseDao;
	}

	public TPolicyPeriodDao getTPolicyPeriodDao() {
		return TPolicyPeriodDao;
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	public TModuleInfoInitDao getTModuleInfoInitDao() {
		return TModuleInfoInitDao;
	}

	public void setTModuleInfoInitDao(TModuleInfoInitDao moduleInfoInitDao) {
		TModuleInfoInitDao = moduleInfoInitDao;
	}

	public TGrpNetDao getTGrpNetDao() {
		return TGrpNetDao;
	}

	public void setTGrpNetDao(TGrpNetDao grpNetDao) {
		TGrpNetDao = grpNetDao;
	}

	public TEventTypeInitDao getTEventTypeInitDao() {
		return TEventTypeInitDao;
	}

	public void setTEventTypeInitDao(TEventTypeInitDao eventTypeInitDao) {
		TEventTypeInitDao = eventTypeInitDao;
	}

	public PolDetailDspDao getPolDetailDspDao() {
		return PolDetailDspDao;
	}

	public void setPolDetailDspDao(PolDetailDspDao polDetailDspDao) {
		PolDetailDspDao = polDetailDspDao;
	}

	public TManufacturerInfoInitDao getTManufacturerInfoInitDao() {
		return TManufacturerInfoInitDao;
	}

	public void setTManufacturerInfoInitDao(TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}

	public DspSyslogEventsDao getDspSyslogEventsDao() {
		return dspSyslogEventsDao;
	}

	public void setDspSyslogEventsDao(DspSyslogEventsDao dspSyslogEventsDao) {
		this.dspSyslogEventsDao = dspSyslogEventsDao;
	}

	public PolicySyslogDao getPolicySyslogDao() {
		return policySyslogDao;
	}

	public void setPolicySyslogDao(PolicySyslogDao policySyslogDao) {
		this.policySyslogDao = policySyslogDao;
	}

	public DspEventsFromPolicySyslogDao getDspEventsFromPolicySyslogDao() {
		return DspEventsFromPolicySyslogDao;
	}

	public void setDspEventsFromPolicySyslogDao(DspEventsFromPolicySyslogDao dspEventsFromPolicySyslogDao) {
		DspEventsFromPolicySyslogDao = dspEventsFromPolicySyslogDao;
	}

}
