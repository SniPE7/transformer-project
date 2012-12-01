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
import com.ibm.ncs.model.dto.TSvrmodMapPk;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class EditNetworkAdminController implements Controller {


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
		try{
			//List<TServerInfo>  serverinfo  = TServerInfoDao.findAll();
			//List<TSvrmodMap>   servermodule = TSvrmodMapDao.findAll();
			List<TModuleInfoInit> modulelist = TModuleInfoInitDao.findAll();
			String nmsid = request.getParameter("nmsid");
			String modid = request.getParameter("modid");
			
			String type = request.getParameter("type");
			String sevName = request.getParameter("sevName");
			String path = request.getParameter("path");
			String mid = request.getParameter("mid");
			String desc = request.getParameter("desc");
			String servid = request.getParameter("servid");
			TSvrmodMap tsm;
			
			if(type!=null&&!type.equals("")){
				if(type.equals("save")){
					if(nmsid==null||nmsid.equals("")){
						if(TSvrmodMapDao.findByPrimaryKey(Long.parseLong(servid), Long.parseLong(mid))!=null){
							tsm = TSvrmodMapDao.findByPrimaryKey(Long.parseLong(servid), Long.parseLong(mid));
							tsm.setPath(path);
							tsm.setDescription(desc);
							TSvrmodMapPk pk = new TSvrmodMapPk(Long.parseLong(servid), Long.parseLong(mid));
							TSvrmodMapDao.update(pk, tsm);
							Log4jInit.ncsLog.info(this.getClass().getName() + " Updated to TSvrmodMapDao: pk=" +pk.toString() + "\tdto= " +tsm.toString());
							
							nmsid = servid;
							modid = mid;
						}else{
							tsm = new TSvrmodMap();
							tsm.setNmsid(Long.parseLong(servid));
							tsm.setModid(Long.parseLong(mid));
							tsm.setPath(path);
							tsm.setDescription(desc);
							TSvrmodMapDao.insert(tsm);
							Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TSvrmodMapDao: " + tsm.toString());
							
							nmsid = servid;
							modid = mid;
						}
						
					   
						
					}else{
						tsm = TSvrmodMapDao.findByPrimaryKey(Long.parseLong(nmsid), Long.parseLong(modid));
						tsm.setDescription(desc);
						tsm.setModid(Long.parseLong(modid));
						tsm.setPath(path);
						TSvrmodMapPk pk = new TSvrmodMapPk(Long.parseLong(nmsid), Long.parseLong(modid));
						TSvrmodMapDao.update(pk , tsm);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Updated to TSvrmodMapDao: pk=" +pk.toString() + "\tdto= " +tsm.toString());
						
						nmsid = servid;
						modid = mid;
					}
				}else if(type.equals("del")){
					TSvrmodMapPk pk = new TSvrmodMapPk(Long.parseLong(nmsid), Long.parseLong(modid));
					TSvrmodMapDao.delete(pk);
					Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TSvrmodMapDao: " + pk.toString());
					
				}
			}
			
			TServerInfo servInfo = TServerInfoDao.findByPrimaryKey(Long.parseLong(nmsid));
			TSvrmodMap svrModMap = TSvrmodMapDao.findByPrimaryKey(Long.parseLong(nmsid), Long.parseLong(modid));
			TModuleInfoInit module = TModuleInfoInitDao.findByPrimaryKey( Long.parseLong(modid));
			int index = modulelist.indexOf(svrModMap);
			model.put("modulelist", modulelist);
			model.put("svrModMap", svrModMap);
			model.put("module", module);
			model.put("index", index);
			model.put("servInfo", servInfo);
			//model.put("server", serverinfo);
			//model.put("servermodule", servermodule); 
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
