/**
 * 
 */
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

import com.ibm.ncs.model.dao.PolDetailDspDao;
import com.ibm.ncs.model.dao.TEventTypeInitDao;
import com.ibm.ncs.model.dao.TManufacturerInfoInitDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dto.DspEventsFromPolicySyslog;
import com.ibm.ncs.model.dto.DspSyslogEvents;
import com.ibm.ncs.model.dto.PolDetailDsp;
import com.ibm.ncs.model.dto.TEventTypeInit;
import com.ibm.ncs.model.dto.TManufacturerInfoInit;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.util.Log4jInit;
import com.ibm.ncs.util.SortList;

/**
 * @author root
 *
 */
public class PolicyDetailsPDMController implements Controller {

	TModuleInfoInitDao  TModuleInfoInitDao;
	TEventTypeInitDao  TEventTypeInitDao;
	PolDetailDspDao  PolDetailDspDao;
	TManufacturerInfoInitDao TManufacturerInfoInitDao;
	
	String pageView;
	String pageView4ReadOnly;
	String message;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			message = request.getParameter("message");
			String ptvidstr = request.getParameter("ptvid");
			long ptvid = Long.parseLong(ptvidstr);
			
			String mpname = request.getParameter("mpname");
			
			String category = request.getParameter("cate");
			String displayOption = request.getParameter("listSeled");
			String selectedEveType = request.getParameter("listeve_type");
//			System.out.println("&&&&&&&&&&%%%%%%%%%%%%%%selEvetype=" + selectedEveType);
			String mode = request.getParameter("mode");
			
			if(mode == null || mode.equals(""))
				mode = "SNMP";
			
			//get manufacture list
			String manufacture = request.getParameter("manufselect");
//			System.out.println("Manufacture selection is: " + manufacture);
			Map<Integer,List<DspSyslogEvents>> unselectedSyslog = new HashMap<Integer,List<DspSyslogEvents>>();
			Map<String,Map> DspSyslogMap = new TreeMap<String,Map>();
			List<TManufacturerInfoInit> tmflist = TManufacturerInfoInitDao.findAll();
			SortList<TManufacturerInfoInit> sortmanu = new SortList<TManufacturerInfoInit>();
			sortmanu.Sort(tmflist, "getMrname", null);
			model.put("mflist", tmflist);
			
			
			List<PolDetailDsp> details = new ArrayList<PolDetailDsp>();
			Map<String, Object> detailMap = new HashMap<String, Object>();
			Map<Integer, Object> syslogdetailMap = new HashMap<Integer, Object>();
//			List<PolicySyslog> syslogDetails = new ArrayList<PolicySyslog>();
			List<DspEventsFromPolicySyslog> eventsSyslogDetails = new ArrayList<DspEventsFromPolicySyslog>();
			Map<String,Object> eventsDictionary = new HashMap<String,Object>();
			if(!mode.equalsIgnoreCase("syslog")){
				details = PolDetailDspDao.findByPtvid(ptvid);
				
				
				for(PolDetailDsp dto: details){
					String key = dto.getMname();key = (key==null)?"":key;
					List<PolDetailDsp> templst = null;
					if(detailMap.containsKey(key)){
						templst = (List<PolDetailDsp>)detailMap.get(key);	
					}else{
						templst = new ArrayList<PolDetailDsp>();
					}			
					templst.add(dto);
					detailMap.put(key, templst);
					
				}
//				System.out.println("detailMap="+detailMap);				
				details = (List<PolDetailDsp>) detailMap.get(mode.toLowerCase());
			}
			
			List<TModuleInfoInit> module = TModuleInfoInitDao.findAll();
		
			List<TEventTypeInit> unselected= null;  /* */			
			List<TEventTypeInit> snmplst= null;  /* */
			if(category.equals("1")){ //cate=1 as DEVECE policy
				snmplst = TEventTypeInitDao.listForDeviceSnmpRule(ptvid);				
			}
			else if (category.equals("4")) //treated as PORT policy
			{
				snmplst = TEventTypeInitDao.listForPortSnmpRule(ptvid);
			}
			else if (category.equals("9")) //treated as PreDefMib policy
			{
				snmplst = TEventTypeInitDao.listForPreDefMibSnmpRule(ptvid);
			}
			List<TEventTypeInit> icmplst= null;  /* */
			if(category.equals("1")){ //cate=1 as DEVECE policy
				icmplst = TEventTypeInitDao.listForDeviceIcmpRule(ptvid);				
			}
			else if (category.equals("4")) //treated as PORT policy
			{
				icmplst = TEventTypeInitDao.listForPortIcmpRule(ptvid);
			}
			else if (category.equals("9")) //treated as PreDefMib policy
			{
				icmplst = TEventTypeInitDao.listForPortIcmpRule(ptvid);
			}
			
			if(mode.equalsIgnoreCase("icmp")){
				unselected = icmplst;
			}else if(mode.equalsIgnoreCase("snmp")){
				unselected = snmplst;
			}
			if(mode.equalsIgnoreCase("syslog")){
				model.put("details", syslogdetailMap);
				model.put("unselected", unselectedSyslog);
			}else{
				model.put("details", details);
				model.put("unselected", unselected);
			}
			model.put("ptvid", ptvidstr);
			model.put("mpname", mpname);
			model.put("displayOption", displayOption);
			model.put("selectedEveType", selectedEveType);
			model.put("cate", category);
			model.put("mode", mode);
			model.put("eventType", getEventTypeMap());
			model.put("syslog", DspSyslogMap);
			model.put("pmpmanu", manufacture);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		String readOnly = request.getParameter("readOnly");
		if (readOnly != null && readOnly.equalsIgnoreCase("true")) {
			return new ModelAndView(getPageView4ReadOnly(), "model", model);
		} else {
			return new ModelAndView(getPageView(), "model", model);
		}
	}
	


	public Map getCompareTypeMap(){
		Map<String,String> map = new HashMap<String, String>();
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
	
	public Map<Integer,String> getEventTypeMap(){
		Map<Integer,String> map = new HashMap<Integer, String>();
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

	public void setTManufacturerInfoInitDao(
			TManufacturerInfoInitDao manufacturerInfoInitDao) {
		TManufacturerInfoInitDao = manufacturerInfoInitDao;
	}



	public String getPageView4ReadOnly() {
		return pageView4ReadOnly;
	}



	public void setPageView4ReadOnly(String pageView4ReadOnly) {
		this.pageView4ReadOnly = pageView4ReadOnly;
	}

}
