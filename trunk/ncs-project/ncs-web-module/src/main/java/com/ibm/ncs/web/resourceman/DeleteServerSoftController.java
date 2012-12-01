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

public class DeleteServerSoftController implements Controller {


	TServerInfoDao   TServerInfoDao;
	TSvrmodMapDao  TSvrmodMapDao;
	String pageView;
	TModuleInfoInitDao TModuleInfoInitDao;
	GenPkNumber genPkNumber;
	
	
	
	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}

	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}

	public TModuleInfoInitDao getTModuleInfoInitDao() {
		return TModuleInfoInitDao;
	}

	public void setTModuleInfoInitDao(TModuleInfoInitDao moduleInfoInitDao) {
		TModuleInfoInitDao = moduleInfoInitDao;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		//List<TServerInfo>  serverinfo  = TServerInfoDao.findAll();
		//List<TSvrmodMap>   servermodule = TSvrmodMapDao.findAll();
		try{
			String nmsid = request.getParameter("nmsid");
			String modid = request.getParameter("modid");

				TSvrmodMapPk pk = new TSvrmodMapPk(Long.parseLong(nmsid), Long.parseLong(modid));
				TSvrmodMapDao.delete(pk);
				Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TSvrmodMapDao: " + pk.toString());
				
			List<TModuleInfoInit> modulelist = TModuleInfoInitDao.findAll();
			
			List<TSvrmodMap> servermodule = TSvrmodMapDao.findWhereNmsidEquals(Long.parseLong(nmsid));
			TServerInfo servInfo = TServerInfoDao.findByPrimaryKey(Long.parseLong(nmsid));
			
			List<TModuleInfoInit> modules = new ArrayList<TModuleInfoInit>();
			Map<String,Object> map = new HashMap<String , Object>();
			TModuleInfoInit  module1=null;
			for(int i=0;i<servermodule.size();i++){
				
				 module1 = TModuleInfoInitDao.findByPrimaryKey(servermodule.get(i).getModid());
				modules.add(module1);
				 
			}
			
			
			model.put("servermodule", servermodule); 
			model.put("modules", modules);
			model.put("modulelist", modulelist);
			model.put("servInfo", servInfo);
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		//model.put("server", serverinfo);
		//model.put("servermodule", servermodule); 
		return new ModelAndView(getPageView(), "model", model);
	}

	public TServerInfoDao getTServerInfoDao() {
		return TServerInfoDao;
	}

	public void setTServerInfoDao(TServerInfoDao serverInfoDao) {
		TServerInfoDao = serverInfoDao;
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
