package com.ibm.ncs.web.baseinfo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TCategoryMapInitDao;
import com.ibm.ncs.model.dao.TEventOidInitDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TEventOidInit;
import com.ibm.ncs.util.GenPkNumber;

/**
 * @author root
 *
 */
public class SaveParamController implements Controller {
	
	private TEventOidInitDao TEventOidInitDao;
	private GenPkNumber genPkNumber;
	private String pageView;
	private String name;
	
	
	
	
	public String getPageView() {
		return pageView;
	}



	public void setPageView(String pageView) {
		this.pageView = pageView;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	


  
	

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		Map params = new HashMap();
	//	TDeviceTypeInit cmi = new TDeviceTypeInit();
//		TEventOidInit toi = new TEventOidInit();
//		toi.setUnit(arg0.getParameter("unit"));
//		toi.setDescription(arg0.getParameter("description"));
//		toi.setEveid(eveid);
//		toi.setOidgroupname(oidgroupname);
//		toi.setId(genPkNumber.getID());
//		cmi.setName(arg0.getParameter("name"));
//		cmi.setFlag("0");
//		
//		TCategoryMapInitDao.insert(cmi);
//		devices.put("name", arg0.getParameter("name"));
//		
//		
		return new ModelAndView(getPageView());//, "model", devices);
	}



	



	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}



	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}



	public TEventOidInitDao getTEventOidInitDao() {
		return TEventOidInitDao;
	}



	public void setTEventOidInitDao(TEventOidInitDao eventOidInitDao) {
		TEventOidInitDao = eventOidInitDao;
	}



	

	

}
