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

public class ServerManagementSaveController implements Controller {


	TServerInfoDao   TServerInfoDao;
	TSvrmodMapDao  TSvrmodMapDao;
	String pageView;
	TModuleInfoInitDao TModuleInfoInitDao;
	GenPkNumber genPkNumber;
	String message;
	
	
	
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
		String type = "edit";
		message = "";
		TServerInfo server = null;
		try{
			String nmsid = request.getParameter("nmsid");
			String nmsip = request.getParameter("nmsip");
			String nmsname = request.getParameter("nmsname");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String description = request.getParameter("description");
			String ostype = request.getParameter("ostype");
			List<TServerInfo> serverTmp = TServerInfoDao.findWhereNmsipEquals(nmsip);
			List<TServerInfo> serverTemp = TServerInfoDao.findWhereNmsnameEquals(nmsname);
			System.out.println("ip size is "+serverTmp.size()+" name size is "+serverTemp.size());
			
			
			
//			System.out.println("the username from jsp is===="+username + "nmsid=" + nmsid + "nmsip" + nmsip);
			if(nmsid == null||nmsid.equals("")){
				server = new TServerInfo();

				server.setNmsip(nmsip);
				server.setNmsname(nmsname);
				server.setUsername(username);
				server.setPassword(password);
				server.setDescription(description);
				server.setOstype(ostype);
				
				if(serverTmp.size()>0 || serverTemp.size()>0){
					if(serverTmp.size()>0 && serverTemp.size()>0){
						System.out.println("both dul-------");
						message = "resourceman.serverMgmt.save.dul";
						model.put("nmsname", nmsname);
						model.put("username", username);
						model.put("nmsip", nmsip);
						model.put("password", password);
						model.put("description", description);
						setPageView("/secure/resourceman/serverManagement.jsp");
						model.put("message", message);
					}else{
						if(serverTmp.size()>0){
							System.out.println("IP dul------");
							message = "resourceman.serverMgmt.save.duplicateIP";
							model.put("nmsname", nmsname);
							model.put("username", username);
							model.put("nmsip", nmsip);
							model.put("password", password);
							model.put("description", description);
							setPageView("/secure/resourceman/serverManagement.jsp");
							model.put("message", message);
						}
						if(serverTemp.size()>0){
							System.out.println("name dul----------");
							message = "resourceman.serverMgmt.save.duplicateName";
							model.put("nmsname", nmsname);
							model.put("username", username);
							model.put("nmsip", nmsip);
							model.put("password", password);
							model.put("description", description);
							setPageView("/secure/resourceman/serverManagement.jsp");
							model.put("message", message);
						}
					}
					
				}else{
					server.setNmsid(genPkNumber.getID());	
					try{
					TServerInfoDao.insert(server);
					Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TServerInfoDao: " + server.toString());
					}catch(Exception e){
						message = "baseinfo.new.error";
						Log4jInit.ncsLog.error(this.getClass().getName() + " Inserted to TServerInfoDao Failed: dto=" + server.toString()
								+ "\n" + e.getMessage());
						model.put("message", message);
						List<TServerInfo>  serverinfo  = TServerInfoDao.findAll();
						
						int total = serverinfo.size();
						model.put("type", type);
						model.put("serverinfo", serverinfo);
						model.put("total", total);
						model.put("server",server);
						
						model.put("nmsname", nmsname);
						model.put("username", username);
						model.put("nmsip", nmsip);
						model.put("password", password);
						model.put("ostype", ostype);
						model.put("description", description);
						return new ModelAndView("/secure/resourceman/serverManagement.jsp","model",model);
					}
				
				}	
			}else{
				System.out.println("update invoked--------");
				server = TServerInfoDao.findByPrimaryKey(Long.parseLong(nmsid));
				System.out.println("name equal ? "+server.getNmsname().equalsIgnoreCase(nmsname));
				System.out.println("ip equal ? "+server.getNmsip().equals(nmsip));
				boolean dul = false;
				if(!server.getNmsname().equalsIgnoreCase(nmsname)&& !server.getNmsip().equalsIgnoreCase(nmsip)){
					System.out.println("both changed-----");
					if(serverTmp.size()>0 && serverTemp.size()>0){
					for(int i=0;i<serverTmp.size();i++){
						if(serverTmp.get(i).getNmsname().equalsIgnoreCase(nmsname)){
							 dul = true;
						}
						
					}
					if(dul == true){
						System.out.println("IP and name dul------");
						message = "resourceman.serverMgmt.save.dul";
						
						model.put("message", message);
						List<TServerInfo>  serverinfo  = TServerInfoDao.findAll();
						
						int total = serverinfo.size();
						model.put("type", type);
						model.put("serverinfo", serverinfo);
						model.put("total", total);
						model.put("server",server);
						
						return new ModelAndView("/secure/resourceman/showserverManagement.jsp","model",model);
						
					
						
					}
					}
				}else{
						System.out.println("name or ip changed----");
						if(! server.getNmsip().equalsIgnoreCase(nmsip)){
							System.out.println("only ip changed-----");
							if(serverTmp.size()>0){
								System.out.println(" only IP dul------");
								message = "resourceman.serverMgmt.save.duplicateIP";
								
								model.put("message", message);
								List<TServerInfo>  serverinfo  = TServerInfoDao.findAll();
								
								int total = serverinfo.size();
								model.put("type", type);
								model.put("serverinfo", serverinfo);
								model.put("total", total);
								model.put("server",server);
								
								return new ModelAndView("/secure/resourceman/showserverManagement.jsp","model",model);
							
						}
					}
						if(!server.getNmsname().equalsIgnoreCase(nmsname)){
							System.out.println("only name changed-----");
							if(serverTemp.size()>0){
								System.out.println("only name dul----------");
								message = "resourceman.serverMgmt.save.duplicateName";
								
								model.put("message", message);
								List<TServerInfo>  serverinfo  = TServerInfoDao.findAll();
								
								int total = serverinfo.size();
								model.put("type", type);
								model.put("serverinfo", serverinfo);
								model.put("total", total);
								model.put("server",server);
								
								return new ModelAndView("/secure/resourceman/showserverManagement.jsp","model",model);
								
							}
							
							}
						}
					
				
				
				TServerInfoPk pk = new TServerInfoPk(Long.parseLong(nmsid));
				server.setNmsip(nmsip);
				server.setNmsname(nmsname);
				server.setUsername(username);
				server.setPassword(password);
				server.setDescription(description);
				server.setOstype(ostype);
				
				
				
				try{
				TServerInfoDao.update(pk, server);
				Log4jInit.ncsLog.info(this.getClass().getName() + " Updated to TServerInfoDao: pk=" +pk.toString() + "\tdto= " +server.toString());
				}catch(Exception e){
					message = "baseinfo.update.fail";
					model.put("message", message);
					Log4jInit.ncsLog.error(this.getClass().getName() + " Updated to TServerInfoDao Failed: pk=" +pk.toString() + "\tdto= " +server.toString()
							+ "\n" + e.getMessage());
					List<TServerInfo>  serverinfo  = TServerInfoDao.findAll();
					
					int total = serverinfo.size();
					model.put("type", type);
					model.put("serverinfo", serverinfo);
					model.put("total", total);
					model.put("server",server);
					
					return new ModelAndView("/secure/resourceman/showserverManagement.jsp","model",model);
					
				}
			}
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}
		List<TServerInfo>  serverinfo  = TServerInfoDao.findAll();
		
		int total = serverinfo.size();
		model.put("type", type);
		model.put("serverinfo", serverinfo);
		model.put("total", total);
		model.put("server",server);
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
