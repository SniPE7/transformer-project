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
import com.ibm.ncs.model.dto.TSvrmodMap;
import com.ibm.ncs.util.Log4jInit;

public class ServerSoftController implements Controller {


	TServerInfoDao   TServerInfoDao;
	TSvrmodMapDao  TSvrmodMapDao;
	TModuleInfoInitDao TModuleInfoInitDao;
	String pageView;


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		TServerInfo servInfo = new TServerInfo();
		List<TModuleInfoInit> modules = new ArrayList<TModuleInfoInit>();
		List<TModuleInfoInit> modulelist =  new ArrayList<TModuleInfoInit>();
		Map<String,Object> map = new HashMap<String , Object>();
		TModuleInfoInit  module= new TModuleInfoInit();
		List<TSvrmodMap> servermodule = new ArrayList<TSvrmodMap>();
		String nmsname = "";
		try{
			long nmsid = Long.parseLong(request.getParameter("nmsid"));
			nmsname = TServerInfoDao.findByPrimaryKey(nmsid).getNmsname();
//			System.out.println("the server name is ------"+nmsname);
			
			servermodule = TSvrmodMapDao.findWhereNmsidEquals(nmsid);
			
			modules = new ArrayList<TModuleInfoInit>();
			modulelist = TModuleInfoInitDao.findAll();
			map = new HashMap<String , Object>();
			
			for(int i=0;i<servermodule.size();i++){				
				module = TModuleInfoInitDao.findByPrimaryKey(servermodule.get(i).getModid());
				modules.add(module);	
				modulelist.remove(module);
			}
			servInfo = TServerInfoDao.findByPrimaryKey(nmsid);
			
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}

		model.put("servermodule", servermodule); 
		model.put("modules", modules);
		model.put("nmsname", nmsname);
		model.put("servInfo", servInfo);
		model.put("modulelist", modulelist);
		return new ModelAndView(getPageView(), "model", model);
	}


	public TServerInfoDao getTServerInfoDao() {
		return TServerInfoDao;
	}



	public void setTServerInfoDao(TServerInfoDao serverInfoDao) {
		TServerInfoDao = serverInfoDao;
	}



	public TModuleInfoInitDao getTModuleInfoInitDao() {
		return TModuleInfoInitDao;
	}



	public void setTModuleInfoInitDao(TModuleInfoInitDao moduleInfoInitDao) {
		TModuleInfoInitDao = moduleInfoInitDao;
	}



	public TSvrmodMapDao getTSvrmodMapDao() {
		return TSvrmodMapDao;
	}

	public void setTSvrmodMapDao(TSvrmodMapDao svrmodMapDao) {
		TSvrmodMapDao = svrmodMapDao;
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}
}
