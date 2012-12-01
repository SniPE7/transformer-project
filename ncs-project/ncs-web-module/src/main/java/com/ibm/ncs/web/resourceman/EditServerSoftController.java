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

public class EditServerSoftController implements Controller {


	TServerInfoDao   TServerInfoDao;
	TSvrmodMapDao  TSvrmodMapDao;
	String pageView;
	TModuleInfoInitDao TModuleInfoInitDao;
	GenPkNumber genPkNumber;
	String message = "";
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		message = "";
		Map<String, Object> model = new HashMap<String, Object>();
		List<TModuleInfoInit> modulelist = new ArrayList<TModuleInfoInit>();
		TSvrmodMap svrModMap = null;
		TServerInfo servInfo = new TServerInfo();
		List<TModuleInfoInit> modules = new ArrayList<TModuleInfoInit>();
		List<TSvrmodMap> servermodule = new ArrayList<TSvrmodMap>();
		try{
			modulelist = TModuleInfoInitDao.findAll();
			String nmsid = request.getParameter("nmsid");
			String modid = request.getParameter("modid");
			
			String type = request.getParameter("type");
			String sevName = request.getParameter("sevName");
			String path = request.getParameter("path");
			String mid = request.getParameter("mid");
			String desc = request.getParameter("desc");
			String servid = request.getParameter("servid");
			message = "";
			TSvrmodMap tsm;
//			System.out.println("*******In editServer SoftController*************");
//			System.out.println("NMSID=" + nmsid + "\tModid=" + modid + "\ttype=" +type + "\tmid=" + mid + "\tservid=" + servid);
			if(type!=null&&!type.equals("")){
				if(type.equals("save")){
					if(nmsid==null||nmsid.equals("")){
						if(TSvrmodMapDao.findByPrimaryKey(Long.parseLong(servid), Long.parseLong(mid))!=null){
//							System.out.println("in nmsid = null, and mid=" + mid + " \tserver id=" + servid);
							tsm = TSvrmodMapDao.findByPrimaryKey(Long.parseLong(servid), Long.parseLong(mid));
							tsm.setPath(path);
							tsm.setDescription(desc);
							TSvrmodMapPk pk = new TSvrmodMapPk(Long.parseLong(servid), Long.parseLong(mid));
							try{
							TSvrmodMapDao.update(pk, tsm);
							Log4jInit.ncsLog.info(this.getClass().getName() + " Updated to TSvrmodMapDao: pk=" +pk.toString() + "\tdto= " +tsm.toString());
							}catch(Exception e){
								message = "baseinfo.update.failed";
								Log4jInit.ncsLog.error(this.getClass().getName() + " Updated to TSvrmodMapDao Failed: dto=" + tsm.toString()
										+ "\n" + e.getMessage());
								model.put("message", message);
								try{
//									System.out.println("the server name is ------"+nmsname);
									TModuleInfoInit  module= new TModuleInfoInit();
									servermodule = TSvrmodMapDao.findWhereNmsidEquals(Long.parseLong(nmsid));
									
									modules = new ArrayList<TModuleInfoInit>();
									modulelist = TModuleInfoInitDao.findAll();
									
									for(int i=0;i<servermodule.size();i++){				
										module = TModuleInfoInitDao.findByPrimaryKey(servermodule.get(i).getModid());
										modules.add(module);	
										modulelist.remove(module);
									}
									servInfo = TServerInfoDao.findByPrimaryKey(Long.parseLong(nmsid));
									
								}catch (Exception ee) {
									Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ee.getMessage());
									ee.printStackTrace();
								}
								model.put("servInfo", servInfo);
								model.put("modulelist", modulelist);
								model.put("servermodule", servermodule); 
								model.put("modules", modules);
								
								
							}
							nmsid = servid;
							modid = mid;
						}else{
//							System.out.println("in nmsid !=null" + nmsid+", and mid=" + mid + " \tserver id=" + servid);
							tsm = new TSvrmodMap();
							tsm.setNmsid(Long.parseLong(servid));
							tsm.setModid(Long.parseLong(mid));
							tsm.setPath(path);
							tsm.setDescription(desc);
							try{
							TSvrmodMapDao.insert(tsm);
							Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to TSvrmodMapDao: " + tsm.toString());
							}catch(Exception e){
								message = "baseinfo.new.error";
								Log4jInit.ncsLog.error(this.getClass().getName() + " Inserted to TSvrmodMapDao Failed: dto=" + tsm.toString()
										+ "\n" + e.getMessage());
								model.put("message", message);
								try{
//									System.out.println("the server name is ------"+nmsname);
									TModuleInfoInit  module= new TModuleInfoInit();
									servermodule = TSvrmodMapDao.findWhereNmsidEquals(Long.parseLong(nmsid));
									
									modules = new ArrayList<TModuleInfoInit>();
									modulelist = TModuleInfoInitDao.findAll();
									
									for(int i=0;i<servermodule.size();i++){				
										module = TModuleInfoInitDao.findByPrimaryKey(servermodule.get(i).getModid());
										modules.add(module);	
										modulelist.remove(module);
									}
									servInfo = TServerInfoDao.findByPrimaryKey(Long.parseLong(nmsid));
									
								}catch (Exception ee) {
									Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ee.getMessage());
									ee.printStackTrace();
								}
								model.put("servInfo", servInfo);
								model.put("modulelist", modulelist);
								model.put("servermodule", servermodule); 
								model.put("modules", modules);
							}
							nmsid = servid;
							modid = mid;
						}
					}else{//nmsid is not null
						tsm = TSvrmodMapDao.findByPrimaryKey(Long.parseLong(nmsid), Long.parseLong(mid));
//						System.out.println("In else: nmsid" + nmsid + "\tmid=" + mid + "\tmodid=" + modid);
						if(tsm!=null && modid != null && mid!=null && !modid.equals(mid)){
							message = "resourceman.serversoft.edit.duplicateModid";
//							System.out.println("set message = duplicateModid");
							svrModMap = new TSvrmodMap();
							svrModMap.setModid(Long.parseLong(mid));
							svrModMap.setNmsid(Long.parseLong(nmsid));
							svrModMap.setPath(path);
							svrModMap.setDescription(desc);
						}else{
//							System.out.println("?????????????nmsid=" + nmsid + "\tmodid=" + modid + "\tmid=" + mid);
							tsm = TSvrmodMapDao.findByPrimaryKey(Long.parseLong(nmsid), Long.parseLong(modid));
//							if(tsm!= null) 
//								System.out.println("findByPrimaryKey(nmsid, modid) is not null");
							TSvrmodMapPk pk = new TSvrmodMapPk(Long.parseLong(nmsid), Long.parseLong(modid));
							if(!mid.equals(modid)){								
								TSvrmodMapDao.delete(pk);			
								Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted TSvrmodMapDao: pk=" +pk.toString() + "\tdto= " +tsm.toString());
								
								TSvrmodMap tsmInsert = new TSvrmodMap();
								tsmInsert.setDescription(desc);
								tsmInsert.setModid(Long.parseLong(mid));
								tsmInsert.setPath(path);
								tsmInsert.setNmsid(Long.parseLong(nmsid));
								TSvrmodMapPk pk1 = new TSvrmodMapPk(Long.parseLong(nmsid), Long.parseLong(mid));
								try{
								TSvrmodMapDao.insert(tsmInsert);
								Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted TSvrmodMapDao: pk=" +pk1.toString() + "\tdto= " +tsmInsert.toString());
								}catch(Exception e){

									message = "baseinfo.new.error";
									Log4jInit.ncsLog.error(this.getClass().getName() + " Inserted to TSvrmodMapDao Failed: dto=" + tsm.toString()
											+ "\n" + e.getMessage());
									model.put("message", message);
									try{
//										System.out.println("the server name is ------"+nmsname);
										TModuleInfoInit  module= new TModuleInfoInit();
										servermodule = TSvrmodMapDao.findWhereNmsidEquals(Long.parseLong(nmsid));
										
										modules = new ArrayList<TModuleInfoInit>();
										modulelist = TModuleInfoInitDao.findAll();
										
										for(int i=0;i<servermodule.size();i++){				
											module = TModuleInfoInitDao.findByPrimaryKey(servermodule.get(i).getModid());
											modules.add(module);	
											modulelist.remove(module);
										}
										servInfo = TServerInfoDao.findByPrimaryKey(Long.parseLong(nmsid));
										
									}catch (Exception ee) {
										Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ee.getMessage());
										ee.printStackTrace();
									}
									model.put("servInfo", servInfo);
									model.put("modulelist", modulelist);
									model.put("servermodule", servermodule); 
									model.put("modules", modules);
								
								}
							}else{
								tsm.setDescription(desc);
								tsm.setModid(Long.parseLong(mid));
								tsm.setPath(path);
								try{
								TSvrmodMapDao.update(pk, tsm);
								Log4jInit.ncsLog.info(this.getClass().getName() + " Updated to TSvrmodMapDao: pk=" +pk.toString() + "\tdto= " +tsm.toString());
								}catch(Exception e){

									message = "baseinfo.update.failed";
									Log4jInit.ncsLog.error(this.getClass().getName() + " Updated to TSvrmodMapDao Failed: dto=" + tsm.toString()
											+ "\n" + e.getMessage());
									model.put("message", message);
									try{
//										System.out.println("the server name is ------"+nmsname);
										TModuleInfoInit  module= new TModuleInfoInit();
										servermodule = TSvrmodMapDao.findWhereNmsidEquals(Long.parseLong(nmsid));
										
										modules = new ArrayList<TModuleInfoInit>();
										modulelist = TModuleInfoInitDao.findAll();
										
										for(int i=0;i<servermodule.size();i++){				
											module = TModuleInfoInitDao.findByPrimaryKey(servermodule.get(i).getModid());
											modules.add(module);	
											modulelist.remove(module);
										}
										servInfo = TServerInfoDao.findByPrimaryKey(Long.parseLong(nmsid));
										
									}catch (Exception ee) {
										Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + ee.getMessage());
										ee.printStackTrace();
									}
									model.put("servInfo", servInfo);
									model.put("modulelist", modulelist);
									model.put("servermodule", servermodule); 
									model.put("modules", modules);
									
									
								
								}
							}							
							nmsid = servid;
							modid = mid;
						}
					}
				}else if(type.equals("del")){
					TSvrmodMapPk pk = new TSvrmodMapPk(Long.parseLong(nmsid), Long.parseLong(modid));
					TSvrmodMapDao.delete(pk);
					Log4jInit.ncsLog.info(this.getClass().getName() + " deleted from TSvrmodMapDao: " + pk.toString());
					
				}
			}
			if((type!=null && !type.equalsIgnoreCase("preNew")) || type == null){
				servInfo = TServerInfoDao.findByPrimaryKey(Long.parseLong(nmsid));
				boolean svrModNotExist = false;
//				System.out.println("ServerMap=" + svrModMap);
				if(svrModMap ==null || svrModMap.getNmsid() <=0){
//					System.out.println("svrModMap.getNmsid() <=0 and modid=" + modid);
					svrModMap = TSvrmodMapDao.findByPrimaryKey(Long.parseLong(nmsid), Long.parseLong(modid));
					svrModNotExist = true;
				}
				TModuleInfoInit module = TModuleInfoInitDao.findByPrimaryKey( Long.parseLong(modid));
				servermodule = TSvrmodMapDao.findWhereNmsidEquals(Long.parseLong(nmsid));			
				modules = new ArrayList<TModuleInfoInit>();
				TModuleInfoInit  module1=null;
				for(int i=0;i<servermodule.size();i++){
					module1 = TModuleInfoInitDao.findByPrimaryKey(servermodule.get(i).getModid());
					modules.add(module1);
					if(svrModNotExist && Long.parseLong(modid) != module1.getModid()){
	//					System.out.println("****before remove from modulelist: size=" + modulelist.size() + "\t module=" + module1);
						modulelist.remove(module1);
	//					System.out.println("****after remove from modulelist: size=" + modulelist.size()+ "**************");
					}
				}
	
				model.put("modulelist", modulelist);
				model.put("svrModMap", svrModMap);
				model.put("module", module);
				model.put("servermodule", servermodule); 
				model.put("modules", modules);
				model.put("servInfo", servInfo);
				model.put("message", message);
			}else if(type.equalsIgnoreCase("preNew")){//prepare JSP to create new server soft
				servInfo = TServerInfoDao.findByPrimaryKey(Long.parseLong(servid));
				servermodule = TSvrmodMapDao.findWhereNmsidEquals(Long.parseLong(servid));			
				modules = new ArrayList<TModuleInfoInit>();
				TModuleInfoInit  module1=null;
				for(int i=0;i<servermodule.size();i++){
					module1 = TModuleInfoInitDao.findByPrimaryKey(servermodule.get(i).getModid());
					modules.add(module1);
					modulelist.remove(module1);					
				}
			}			
		}catch (Exception e) {
			Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
			e.printStackTrace();
		}

		model.put("modulelist", modulelist);
		model.put("svrModMap", svrModMap);
		model.put("servermodule", servermodule); 
		model.put("modules", modules);
		model.put("servInfo", servInfo);
		model.put("message", message);
		return new ModelAndView(getPageView(), "model", model);
	}


	
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
