package com.ibm.ncs.web.baseinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.ibm.ncs.model.dao.DefMibGrpDao;
import com.ibm.ncs.model.dao.PredefmibInfoDao;
import com.ibm.ncs.model.dao.TDeviceInfoDao;
import com.ibm.ncs.model.dao.TOidgroupDetailsInitDao;
import com.ibm.ncs.model.dao.TOidgroupInfoInitDao;
import com.ibm.ncs.model.dto.*;
import com.ibm.ncs.model.exceptions.DaoException;
import com.ibm.ncs.service.TCategoryMapInitManager;
import com.ibm.ncs.util.GenPkNumber;
import com.ibm.ncs.util.Log4jInit;

public class DefaultPolicyPreDefMIBDefinitionController implements Controller {
	String pageView;
	DefMibGrpDao defMibGrpDao;
	GenPkNumber genPkNumber;
	PredefmibInfoDao PredefmibInfoDao;
	TDeviceInfoDao TDeviceInfoDao;
	private String message="";
	




	

	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public PredefmibInfoDao getPredefmibInfoDao() {
		return PredefmibInfoDao;
	}



	public void setPredefmibInfoDao(PredefmibInfoDao predefmibInfoDao) {
		PredefmibInfoDao = predefmibInfoDao;
	}



	public TDeviceInfoDao getTDeviceInfoDao() {
		return TDeviceInfoDao;
	}



	public void setTDeviceInfoDao(TDeviceInfoDao deviceInfoDao) {
		TDeviceInfoDao = deviceInfoDao;
	}



	public GenPkNumber getGenPkNumber() {
		return genPkNumber;
	}



	public void setGenPkNumber(GenPkNumber genPkNumber) {
		this.genPkNumber = genPkNumber;
	}



	public DefMibGrpDao getDefMibGrpDao() {
		return defMibGrpDao;
	}



	public void setDefMibGrpDao(DefMibGrpDao defMibGrpDao) {
		this.defMibGrpDao = defMibGrpDao;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		System.out.println("controller invoked----------------");
		
		Map<String, Object> mfs = new HashMap<String,Object>();
		message = "";
		try{
		String type = request.getParameter("type");
		System.out.println("the type is-------"+type);
		String mid = request.getParameter("mid");
		System.out.println("mid from jsp is----"+mid);
	//	String[] delSel = (String[])request.getParameterValues("delSel");
		
		String gname = request.getParameter("gname");
		String indexoid = request.getParameter("indexoid");
		String indexvar = request.getParameter("indexvar");
		String descroid = request.getParameter("descroid");
		String descrvar = request.getParameter("descrvar");
		
		/*if (gname ==null||gname.equals("")||
				indexoid==null||indexoid.equals("")||
				indexvar==null||indexvar.equals("")||
				descroid==null||descroid.equals("")||
				descrvar==null||descrvar.equals("")
				) {
			return new ModelAndView(getPageView());
		}*/
		
		long nmid;
//		DefMibGrpPk defMibGrpPk = null;
		DefMibGrp dmg = null;
		
		/*String midcannotbedeletedfromjsp = request.getParameter("midcannotbedeleted");
		String[] midcannotbedeleted = null;
		if(midcannotbedeletedfromjsp != null){
		midcannotbedeleted = midcannotbedeletedfromjsp.split(";");
		}
		String midforreturncheck = "";*/
		
		boolean returntojsp = false;
	//	String midforalert = "";
		
		try{
			if(type!=null&&!type.equals("")){
				if(type.equals("modify")){
					//System.out.println("modify invoked-------");
				//	if(delSel!=null){
					if(mid != null){
					dmg = defMibGrpDao.findByPrimaryKey(Long.parseLong(mid));
					//dmg = defMibGrpDao.findWhereMidEquals(Long.parseLong(delSel[0])).get(0);
					mfs.put("mid", dmg.getMid());
					mfs.put("gname", dmg.getName());
					mfs.put("indexoid", dmg.getIndexoid());
					mfs.put("indexvar", dmg.getIndexvar());
					mfs.put("descroid", dmg.getDescroid());
					mfs.put("descrvar", dmg.getDescrvar());
					}
					
				}else if(type.equals("save")){
					
					if(mid==null||mid.equals("")){
						//System.out.println("new save invoked--------");
						nmid = genPkNumber.getID();
						dmg = new DefMibGrp();
						dmg.setMid(nmid);
						dmg.setIndexoid(indexoid);
						dmg.setIndexvar(indexvar);
						dmg.setName(gname);
						dmg.setDescroid(descroid);
						dmg.setDescrvar(descrvar);
						try{
						defMibGrpDao.insert(dmg);
						Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to DefMibGrpDao: " + dmg.toString());
						}catch(DataIntegrityViolationException ee){
							message = "baseinfo.oid.error";
							Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to DefMibGrpDao Failed : \tdto=" + dmg.toString() + "\n" + ee.getMessage());
							mfs.put("message", message);
							mfs.put("gname", gname);
							mfs.put("indexoid", indexoid);
							mfs.put("indexvar", indexvar);
							mfs.put("descroid", descroid);
							mfs.put("descrvar", descrvar);
							
							List<DefMibGrp> grplist = null;
							grplist = defMibGrpDao.findAll();
							mfs.put("total", grplist.size());
							mfs.put("grplist", grplist);
							
							return new ModelAndView(getPageView(), "model", mfs);
							
						}
						catch(Exception ee){
							ee.printStackTrace();
						    message = "baseinfo.new.failed";
							Log4jInit.ncsLog.info(this.getClass().getName() + " Inserted to DefMibGrpDao Failed : \tdto=" + dmg.toString() + "\n" + ee.getMessage());
							mfs.put("message", message);
							mfs.put("gname", gname);
							mfs.put("indexoid", indexoid);
							mfs.put("indexvar", indexvar);
							mfs.put("descroid", descroid);
							mfs.put("descrvar", descrvar);
							
						/*	try{
								List<DefMibGrp> grplist = null;
								grplist = defMibGrpDao.findAll();
								String mides = "";
								List<TDeviceInfo> devices = null;
								if(grplist != null && grplist.size()>0)
									for(int i=0;i<grplist.size();i++){
									long mid1 = grplist.get(i).getMid();
									List<PredefmibInfo> preinfos = PredefmibInfoDao.findByDefMibGrp(mid1);
									if(preinfos != null && preinfos.size()>0){
										for(int j=0;j<preinfos.size();j++){
										PredefmibInfo preinfo = preinfos.get(j);
										long devid = preinfo.getDevid();
										devices = TDeviceInfoDao.findWhereDevidEquals(devid);
										
										}
										if(devices != null && devices.size()>0){
											mides += mid1 + ";";
											mfs.put("mides", mides);
											//System.out.println("mides are----"+mides+" and mid is ---"+mid1);
										}
									}
								}
								if(grplist==null)
									grplist = new ArrayList();
								mfs.put("total", grplist.size());
								mfs.put("grplist", grplist);
							}catch(Exception e){
								Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
								e.printStackTrace();
							}*/
							
							List<DefMibGrp> grplist = null;
							grplist = defMibGrpDao.findAll();
							mfs.put("total", grplist.size());
							mfs.put("grplist", grplist);
							
							return new ModelAndView(getPageView(), "model", mfs);
						}
						
					}else{
						//System.out.println("update save invoked--------");
						//dmg //= //defMibGrpDao.findWhereIdEquals(Long.parseLong(id)).get(0);
						dmg	= defMibGrpDao.findByPrimaryKey(Long.parseLong(mid));
						
						dmg.setIndexoid(indexoid);
						dmg.setIndexvar(indexvar);
						dmg.setName(gname);
						dmg.setDescroid(descroid);
						dmg.setDescrvar(descrvar);
						try{
						defMibGrpDao.update(dmg);
						Log4jInit.ncsLog.info(this.getClass().getName() + " updated to DefMibGrpDao:dto=" + dmg.toString());
						}catch(DataIntegrityViolationException ee){
							String message = "baseinfo.oid.error";
							Log4jInit.ncsLog.error(this.getClass().getName() + " updated to DefMibGrpDao Failed: dto=" + dmg.toString()
									+ "\n" + ee.getMessage());
							mfs.put("message", message);
							mfs.put("gname", gname);
							mfs.put("indexoid", indexoid);
							mfs.put("indexvar", indexvar);
							mfs.put("descroid", descroid);
							mfs.put("descrvar", descrvar);
							
							List<DefMibGrp> grplist = null;
							grplist = defMibGrpDao.findAll();
							mfs.put("total", grplist.size());
							mfs.put("grplist", grplist);

							return new ModelAndView(getPageView(), "model", mfs);
						}
						catch(Exception ee){
							String message = "baseinfo.update.failed";
							Log4jInit.ncsLog.error(this.getClass().getName() + " updated to DefMibGrpDao Failed: dto=" + dmg.toString()
									+ "\n" + ee.getMessage());
							mfs.put("message", message);
							mfs.put("gname", gname);
							mfs.put("indexoid", indexoid);
							mfs.put("indexvar", indexvar);
							mfs.put("descroid", descroid);
							mfs.put("descrvar", descrvar);
							
							/*try{
								List<DefMibGrp> grplist = null;
								grplist = defMibGrpDao.findAll();
								String mides = "";
								List<TDeviceInfo> devices = null;
								if(grplist != null && grplist.size()>0)
									for(int i=0;i<grplist.size();i++){
									long mid1 = grplist.get(i).getMid();
									List<PredefmibInfo> preinfos = PredefmibInfoDao.findByDefMibGrp(mid1);
									if(preinfos != null && preinfos.size()>0){
										for(int j=0;j<preinfos.size();j++){
										PredefmibInfo preinfo = preinfos.get(j);
										long devid = preinfo.getDevid();
										devices = TDeviceInfoDao.findWhereDevidEquals(devid);
										
										}
										if(devices != null && devices.size()>0){
											mides += mid1 + ";";
											mfs.put("mides", mides);
											//System.out.println("mides are----"+mides+" and mid is ---"+mid1);
										}
									}
								}
								if(grplist==null)
									grplist = new ArrayList();
								mfs.put("total", grplist.size());
								mfs.put("grplist", grplist);
							}catch(Exception e){
								Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
								e.printStackTrace();
							}*/
							List<DefMibGrp> grplist = null;
							grplist = defMibGrpDao.findAll();
							mfs.put("total", grplist.size());
							mfs.put("grplist", grplist);

							return new ModelAndView(getPageView(), "model", mfs);
						}
						// to do update here
						
					}
				}else if(type.equalsIgnoreCase("delete")){/*

					
					
					if(delSel!=null){
						for(int i=0;i<delSel.length;i++)
						{
							//System.out.println("delSel is-------"+delSel[i]);
							//System.out.println("check if can not be deleted invoked-------");
							midforreturncheck += delSel[i]+",";
							//System.out.println("midforreturncheck are-------"+midforreturncheck);
							for(int j=0;j<midcannotbedeleted.length;j++){
								if(midcannotbedeleted[j].equalsIgnoreCase(delSel[i])){
									//System.out.println("equal-----------");
									if(! midforalert.contains(delSel[i])){
										midforalert += " "+delSel[i]+" ";
										//System.out.println("midforalert is-------"+midforalert);
										
										mfs.put("midforalert", midforalert);
										returntojsp = true;
										//System.out.println("returntojsp is--"+returntojsp);
									}
									
								}
							}
							//System.out.println("midforalert is ----------"+midforalert);
							
							
						}
						//System.out.println("returntojsp is -------"+returntojsp);
						
						if(returntojsp == true){
							//System.out.println("can not be deleted invoked----");
							message = "<script language='javascript'>alert('MID为"+midforalert+"的私有INDEX下含有设备！ ');</script>";
							//message = "devicesnmpparam.delete.forbidden";
							//System.out.println("message is---"+message);
							mfs.put("message", message);
							mfs.put("midforreturncheck", midforreturncheck);
							try{
								List<DefMibGrp> grplist = null;
								grplist = defMibGrpDao.findAll();
								String mides = "";
								List<TDeviceInfo> devices = null;
								if(grplist != null && grplist.size()>0)
									for(int i=0;i<grplist.size();i++){
									long mid1 = grplist.get(i).getMid();
									List<PredefmibInfo> preinfos = PredefmibInfoDao.findByDefMibGrp(mid1);
									if(preinfos != null && preinfos.size()>0){
										for(int j=0;j<preinfos.size();j++){
										PredefmibInfo preinfo = preinfos.get(j);
										long devid = preinfo.getDevid();
										devices = TDeviceInfoDao.findWhereDevidEquals(devid);
										
										}
										if(devices != null && devices.size()>0){
											mides += mid1 + ";";
											mfs.put("mides", mides);
											//System.out.println("mides are----"+mides+" and mid is ---"+mid1);
										}
									}
								}
								if(grplist==null)
									grplist = new ArrayList();
								mfs.put("total", grplist.size());
								mfs.put("grplist", grplist);
							}catch(Exception e){
								Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
								e.printStackTrace();
							}
							return new ModelAndView(getPageView(), "model", mfs);
						}
						
						for(int i=0;i<delSel.length;i++){
							//System.out.println("delete begin---------");
							defMibGrpDao.delete(Long.parseLong(delSel[i]));
							//to do the delete 
						}
					}
				
				*/
					try{
						/*List<DefMibGrp> grplist = null;
						grplist = defMibGrpDao.findAll();*/
					//	String mides = "";
						List<TDeviceInfo> devices = null;
						//if(grplist != null && grplist.size()>0)
					//		for(int i=0;i<grplist.size();i++){
					//		long mid1 = grplist.get(i).getMid();
							List<PredefmibInfo> preinfos = PredefmibInfoDao.findByDefMibGrp(Long.parseLong(mid));
							if(preinfos != null && preinfos.size()>0){
								for(int j=0;j<preinfos.size();j++){
								PredefmibInfo preinfo = preinfos.get(j);
								long devid = preinfo.getDevid();
								devices = TDeviceInfoDao.findWhereDevidEquals(devid);
								
								}
								if(devices != null && devices.size()>0){
									
									returntojsp = true;
									/*mides += mid1 + ";";
									mfs.put("mides", mides);*/
									//System.out.println("mides are----"+mides+" and mid is ---"+mid1);
								}
							}
					//	}
						
						System.out.println("returntojsp is---"+returntojsp);
						if(returntojsp == true){
							
							message = "baseinfo.delete.alert";
							mfs.put("message", message);
							/*mfs.put("total", grplist.size());
							mfs.put("grplist", grplist);
							return new ModelAndView(getPageView(), "model", mfs);*/
						}else{
							DefMibGrpPk pk = new DefMibGrpPk(Long.parseLong(mid));
							defMibGrpDao.delete(pk);
						    Log4jInit.ncsLog.info(this.getClass().getName() + " Deleted record from defMibGrpDao " + pk.toString());
						}
						
						
					}catch(Exception e){
						Log4jInit.ncsLog.error(this.getClass().getName() + " Error occured:\n" + e.getMessage());
						e.printStackTrace();
					}
				
				}		
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		List<DefMibGrp> grplist = null;
		grplist = defMibGrpDao.findAll();
		mfs.put("total", grplist.size());
		mfs.put("grplist", grplist);
		}catch(DaoException e){
			e.printStackTrace();
			message = "global.db.error";
			mfs.put("message", message);
			return new ModelAndView("/dberror.jsp","model",mfs);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ModelAndView(getPageView(), "model", mfs);
		
	}				



//	public static void display(TCategoryMapInit dto)
//	{
//		StringBuffer buf = new StringBuffer();
//		buf.append( dto.getId() );
//		buf.append( ", " );
//		buf.append( dto.getName() );
//		buf.append( ", " );
//		buf.append( dto.getFlag() );
//		//System.out.println( buf.toString() );
//	}


	public String getPageView() {
		return pageView;
	}


	public void setPageView(String pageView) {
		this.pageView = pageView;
	}



	
	
}
