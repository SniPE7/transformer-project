package com.ibm.ncs.web.resourceman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TGrpNetDao;
import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TServerInfoDao;
import com.ibm.ncs.model.dao.TSvrmodMapDao;
import com.ibm.ncs.model.dto.TCategoryMapInit;
import com.ibm.ncs.model.dto.TGrpNet;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TServerInfo;
import com.ibm.ncs.model.dto.TServerInfoPk;
import com.ibm.ncs.model.dto.TSvrmodMap;
import com.ibm.ncs.model.dto.TSvrmodMapPk;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class ServerManagementDeleteController implements Controller {


	TServerInfoDao   TServerInfoDao;
	String pageView;
	GenPkNumber genPkNumber;
	
	
	
	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}

	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}

	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		//String type = "new";
		
		try{
			String nmsid = request.getParameter("nmsid");
		
			
		
				TServerInfoPk pk = new TServerInfoPk(Long.parseLong(nmsid));
				TServerInfoDao.delete(pk);
				Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TServerInfoDao: " + pk.toString());
		
			List<TServerInfo>  serverinfo  = TServerInfoDao.findAll();
			 TServerInfo server = serverinfo.get(serverinfo.size()-1);
			int total = serverinfo.size();
			model.put("serverinfo", serverinfo);
			model.put("total", total);
			model.put("server", server);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView(getPageView(), "model", model);
	
}

	public TServerInfoDao getTServerInfoDao() {
		return TServerInfoDao;
	}

	public void setTServerInfoDao(TServerInfoDao serverInfoDao) {
		TServerInfoDao = serverInfoDao;
	}

	

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}
}
