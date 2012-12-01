package com.ibm.ncs.web.resourceman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.TModuleInfoInitDao;
import com.ibm.ncs.model.dao.TServerInfoDao;
import com.ibm.ncs.model.dao.TSvrmodMapDao;
import com.ibm.ncs.model.dto.TModuleInfoInit;
import com.ibm.ncs.model.dto.TServerInfo;
import com.ibm.ncs.model.dto.TSvrmodMap;
import com.ibm.ncs.util.Log4jInit;

public class NaviNetworkAdminController implements Controller {


	TServerInfoDao   TServerInfoDao;
	TSvrmodMapDao  TSvrmodMapDao;
	TModuleInfoInitDao TModuleInfoInitDao;
	String pageView;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		try{
			List<TServerInfo>  serverinfo  = TServerInfoDao.findAll();
			List<TSvrmodMap>   servermodule = TSvrmodMapDao.findAll();
			Map<String, Object> map = new HashMap<String , Object>();
			TModuleInfoInit moduleInfo = null;
			//Map<Long,Map<Long,List<TModuleInfoInit>>> moduleInfos = new HashMap<Long,Map<Long,List<TModuleInfoInit>>>();
			/*for (TSvrmodMap dto: servermodule) {
				List list;
				if (map.containsKey(dto.getNmsid()+"")){
					list = (List) map.get(dto.getNmsid()+"");
				}else{
					list = new ArrayList();
				}
				list.add(dto);
				map.put(dto.getNmsid()+"", list);
				
			}*/
			
			
			Map<Long,Object> moduleInfos = new HashMap<Long,Object>();
			List<TModuleInfoInit> infos = new ArrayList<TModuleInfoInit>();
			if(servermodule != null && servermodule.size()>0){
				for(int m=0;m<servermodule.size();m++){
					long nmsid = servermodule.get(m).getNmsid();
					if(moduleInfos.containsKey(nmsid)== false){
				//	System.out.println("nmsid is----"+servermodule.get(m).getNmsid());
					List<TSvrmodMap> serMod = TSvrmodMapDao.findWhereNmsidEquals(nmsid);
				//	System.out.println("serMod is---"+serMod);
					if(serMod != null && serMod.size()>0){
						for(int i=0;i<serMod.size();i++){
							long modid = serMod.get(i).getModid();
						//	System.out.println("modid is---"+serMod.get(i).getModid());
							moduleInfo = TModuleInfoInitDao.findByPrimaryKey(modid);
							
							if(moduleInfos.containsKey(nmsid)){
					        infos = (List<TModuleInfoInit>)moduleInfos.get(nmsid);
					        }else{
					        	infos = new ArrayList<TModuleInfoInit>();
					        }
							infos.add(moduleInfo);
						//	System.out.println("infos is----"+infos);
							moduleInfos.put(nmsid,infos);
						//	System.out.println("modInfos is---"+moduleInfos);
						}
						
					}
					}	
				}
				}
			
	
			
			
			model.put("server", serverinfo);
			model.put("servermodule", servermodule); 
			model.put("moduleInfos", moduleInfos);
			//model.put("map", map);
			System.out.println(model);
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

	
	public TModuleInfoInitDao getTModuleInfoInitDao() {
		return TModuleInfoInitDao;
	}

	public void setTModuleInfoInitDao(TModuleInfoInitDao moduleInfoInitDao) {
		TModuleInfoInitDao = moduleInfoInitDao;
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}
}
